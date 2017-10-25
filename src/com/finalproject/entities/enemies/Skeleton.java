package com.finalproject.entities.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.finalproject.entities.Coin;
import com.finalproject.entities.Player;
import com.finalproject.main.Game;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardCollisionHandler;
import com.joshuacrotts.standards.StandardGame;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;
import com.joshuacrotts.standards.StdOps;

public class Skeleton extends StandardGameObject{
	@SuppressWarnings("unused")
	private StandardGame stdGame;
	public StandardHandler sh;
	private Player player;

	//	public static final double MAX_SPEED_X = 2.0;
	//	public static final double ACC_X = 0.75;
	@SuppressWarnings("unused")
	private static double BOOST;

	public Skeleton(double x, double y, StandardHandler sh, Player player){
		super(x, y, StandardID.Enemy);

		this.player = player;

		try{
			this.initImages();
			this.initAnimators();
		}catch(Exception e){
			e.printStackTrace();
		}

		this.sh = sh;

		this.currentSprite = this.leftImages[0];

		this.health = 10;

		this.velX = -1;
	}


	@Override
	public void tick() {

		if(this.health > 0){

			this.x += (int) this.velX;
			this.y += (int) this.velY;
			this.velY = (this.velY + StandardGameObject.gravity);

			this.velX = (player.x - this.x) * 0.01;
			
			if(Math.abs(this.player.x - this.x) > 500) velX = 0;
			
			if(this.velX < 0){
				this.lefts.animate();
			}else{
				this.rights.animate();
			}
		}
		
		this.checkDeath();
		
		this.dropCoins();

		if(this.deathParticles != null){
			this.deathParticles.tick();
		}

		if(this.y > 1000 || (this.deathParticles != null && this.deathParticles.size() == 0)){
			this.sh.removeEntity(this);
		}

	}


	@Override
	public void render(Graphics2D g2) {
		if(this.health > 0){
			g2.drawImage(this.currentSprite, (int) this.x, (int) this.y, null);
		}
		
		if(this.deathParticles != null){
			this.deathParticles.render(g2);
		}

	}

	private void initImages(){

		this.leftImages = new BufferedImage[4];
		this.rightImages = new BufferedImage[4];

		try{
			for(int i = 0; i < this.leftImages.length; i++){
				this.leftImages[i] = ImageIO.read(new File("res/sprites/skeleton0/sk_l_"+i+".png"));
				this.rightImages[i] = ImageIO.read(new File("res/sprites/skeleton0/sk_r_"+i+".png"));
				this.width += leftImages[i].getWidth() + rightImages[i].getWidth();
				this.height += leftImages[i].getHeight() + rightImages[i].getHeight();
			}	
		}catch(Exception e){
			e.printStackTrace();
		}

		this.width =  (this.width / (leftImages.length + rightImages.length));
		this.height = (this.height / (leftImages.length + rightImages.length));
	}

	public void initAnimators(){

		this.lefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.leftImages)),1/5d, this, StandardAnimator.PRIORITY_3RD);
		this.rights = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.rightImages)),1/5d, this, StandardAnimator.PRIORITY_3RD);
	}
	
    
    public void collide(StandardGameObject sgo)
    {
        if(sgo instanceof Player && sgo.health >= 0){
        	
        	sgo.hurtEntity(-45);
        	Game.audioBuff.Play_Soma_Hurt(StdOps.rand(0, 3));
        	
        }
    }

	
	private void dropCoins(){
		if(this.health < 0){
			
			int amt = StdOps.rand(0, 5);
			
			if(this.fdp){
				for(int i = 0; i < amt; i++){
					this.sh.addEntity(new Coin(this.x, this.y, (byte) StdOps.rand(0, 40), this.player, (StandardCollisionHandler) this.sh));
				}
			}
			this.fdp = false;
		}
	}

}
