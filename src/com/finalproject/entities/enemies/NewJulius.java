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
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;
import com.joshuacrotts.standards.StdOps;

public class NewJulius extends StandardGameObject{

	//Constant sprites
	@SuppressWarnings("unused")
	private BufferedImage stillL;
	@SuppressWarnings("unused")
	private BufferedImage stillR;

	//Global instance variables
	public StandardHandler sh;
	private Player player;

	public NewJulius(double x, double y, StandardHandler sh, Player player){
		super(x, y, StandardID.Enemy);
		this.sh = sh;
		this.player = player;
		try{
			this.initImages();
			this.initAnimators();
		}catch(Exception e){
			e.printStackTrace();
		}

		this.health = 250;

		this.velX = -2;
	}

	public void tick(){
		if(this.health > 0){
			this.x += (int) this.velX;
			this.y += (int) this.velY;
			this.velY = (this.velY + StandardGameObject.gravity);

			this.velX = (player.x - this.x) * 0.01;
			
			if(this.velX < 0){
				this.lefts.animate();
			}else{
				this.rights.animate();
			}
		}

		this.checkDeath();

		this.dropCoins();
		
		if(this.deathParticles != null || (this.deathParticles != null && this.deathParticles.size() == 0)){
			this.deathParticles.tick();

		}

		if(this.y > 1000){
			this.sh.removeEntity(this);
		}
	}

	public void render(Graphics2D g2){
		g2.drawImage(this.currentSprite, (int) x, (int) y, null);
	}

	private void initImages(){

		this.leftImages = new BufferedImage[10]; this.rightImages = new BufferedImage[this.leftImages.length];

		try{
			this.stillL = ImageIO.read(new File("res/sprites/newjulius/nj_s_l.png"));
			this.stillR = ImageIO.read(new File("res/sprites/newjulius/nj_s_r.png"));

			for(int i = 0; i < leftImages.length; i++){
				this.leftImages[i] = ImageIO.read(new File("res/sprites/newjulius/nj_l"+i+".png"));
				this.rightImages[i] = ImageIO.read(new File("res/sprites/newjulius/nj_r"+i+".png"));

				this.width += this.leftImages[i].getWidth() + this.rightImages[i].getWidth();
				this.height += this.leftImages[i].getHeight() + this.rightImages[i].getHeight();
			}
		}catch(Exception e){ e.printStackTrace(); }

		this.width = (int) (this.width / (this.leftImages.length + this.rightImages.length));
		this.height = (int) (this.height / (this.leftImages.length + this.rightImages.length));
	}

	private void initAnimators(){
		this.lefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.leftImages)), 1/16d, this, StandardAnimator.PRIORITY_3RD);
		this.rights = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.rightImages)), 1/16d, this, StandardAnimator.PRIORITY_3RD);
	}
	
    
    public void collide(StandardGameObject sgo)
    {
        if(sgo instanceof Player && sgo.health >= 0){
        	
        	sgo.hurtEntity(-50);
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
