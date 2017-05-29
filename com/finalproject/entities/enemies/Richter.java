package com.finalproject.entities.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.finalproject.entities.Coin;
import com.finalproject.entities.Player;
import com.finalproject.main.Menu;
import com.finalproject.weapons.VampireKiller;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardCollisionHandler;
import com.joshuacrotts.standards.StandardDraw;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;
import com.joshuacrotts.standards.StdOps;

public class Richter extends StandardGameObject{

	//Other animators and sprites
	private BufferedImage[] hurtLeft = null; private StandardAnimator hurtsL = null;
	private BufferedImage[] hurtRight = null; private StandardAnimator hurtsR = null;

	//Global instance variables
	public StandardHandler sh;
	private Player player;
	private VampireKiller whip;
	public boolean tryJump = false;

	//TODO Rework the ai and stuff	
	public double dist = 0;

	//bool for coins
	private boolean fdp = true;

	public Richter(double x, double y, StandardHandler sh, Player player){
		super(x, y, StandardID.Enemy);

		this.sh = sh;
		this.player = player;
		this.whip = new VampireKiller((StandardCollisionHandler) this.sh,20, new Rectangle((int) 54, (int) (25), 170, 20), 0.35);

		try{
			this.initImages();
			this.initAnimators();
		}catch(Exception e){
			e.printStackTrace();
		}

		this.health = 40;

		this.firstDeathPass = true;

		this.velX = StdOps.rand(-3, -1);
	}

	public void tick(){
		if(this.health > 0){

			this.x +=  this.velX;
			this.y += (int) this.velY;
			whip.attack(this, player);

			double dx = Math.abs(x - player.x);
			double dy = Math.abs(y - player.y);
			double lastDist = dist;
			
			this.dist = (dx > dy) ? dx : dy;

			
			
			this.velX = (player.x - this.x) * /*Math.random() * 0.01 +*/ 0.01;
			this.velY = (this.velY + StandardGameObject.gravity);

			if(Math.abs(this.player.x - this.x) > 500) velX = 0;
			
			if(dist >= lastDist && standing && Math.random() < 0.1)
				velY -= 5;

			if(this.whip.active){
				this.velX = 0;
			}

			if(this.velX < 0){
				lastDir = Direction.Left;
				this.lefts.animate();
			}else if(this.velX > 0){
				lastDir = Direction.Right;
				this.rights.animate();
			}

			//Clause for if they're hurt
			if(this.hurt){

				//If they're facing right, they'll fly left when hurt
				if(this.lastDir == Direction.Right) {
					this.hurtsL.animate();
					this.velX = -20f;
				}
				//If they're facing left, they'll fly right when flying
				else{ 
					this.hurtsR.animate();
					this.velX = 20f;
				}
				//Either way, when hurt, they'll fly up and back. ******NEEDS FIXING******
				//this.jump.execute();
			}else{

			}

			this.hurt = false; //Has to be set here so they won't continuously fly back.
		}
		this.checkDeath();
		
		this.dropCoins();
		
		if(this.deathParticles != null){
			this.deathParticles.tick();
		}

		if(this.y > 1000 || (this.deathParticles != null && this.deathParticles.size() == 0)){
			this.sh.removeEntity(this);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(null, "Glad you had fun!, end of tech demo!");
			System.exit(0);
		}

	}

	public void render(Graphics2D g2){
		if(this.health > 0){
			double xo = (lastDir == Direction.Left) ? currentSprite.getWidth() - this.width : 0;
			g2.drawImage(this.currentSprite, (int) (x - xo), (int) y, null);
			StandardDraw.text(""+this.health, (int) (this.x + this.width / 2), (int) (this.y - 10), Menu.starcraft, 20f, Color.red);
			
			
		}

		if(this.deathParticles != null){
			this.deathParticles.render(g2);
		}
		
		
	}

	private void initImages(){

		this.leftImages = new BufferedImage[8]; this.rightImages = new BufferedImage[this.leftImages.length];
		this.attackLeftImages = new BufferedImage[8]; this.attackRightImages = new BufferedImage[this.attackLeftImages.length];
		this.hurtLeft = new BufferedImage[20]; this.hurtRight = new BufferedImage[this.hurtLeft.length];

		try{
			ImageIO.read(new File("res/sprites/richter/ri_s_l.png"));
			ImageIO.read(new File("res/sprites/richter/ri_s_r.png"));

			//Loads in the left and right images for walking, as well as the attacking left & rights
			for(int i = 0; i < leftImages.length; i++){
				this.leftImages[i] = ImageIO.read(new File("res/sprites/richter/ri_l"+i+".png"));
				this.rightImages[i] = ImageIO.read(new File("res/sprites/richter/ri_r"+i+".png"));

				this.width += this.leftImages[i].getWidth() + this.rightImages[i].getWidth();
				this.height += this.leftImages[i].getHeight() + this.rightImages[i].getHeight();
			}
			
			//Loads in the attack images
			for(int i = 0; i < this.attackLeftImages.length; i++){
				this.attackLeftImages[i] = ImageIO.read(new File("res/sprites/richter/ri_a_l"+i+".png"));
				this.attackRightImages[i] = ImageIO.read(new File("res/sprites/richter/ri_a_r"+i+".png"));
			}

			//Loads in the hurt imgs
			//This loads in the hurt image
			for(int i = 0; i < this.hurtLeft.length; i++){
				this.hurtLeft[i] = ImageIO.read(new File("res/sprites/richter/ri_h_l.png"));
				this.hurtRight[i] = ImageIO.read(new File("res/sprites/richter/ri_h_r.png"));
			}

		}catch(Exception e){ e.printStackTrace(); }

		this.width = (int) (this.width / (this.leftImages.length + this.rightImages.length));
		this.height = (int) (this.height / (this.leftImages.length + this.rightImages.length));
	}

	private void initAnimators(){
		this.lefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.leftImages)), 1/8d, this, StandardAnimator.PRIORITY_3RD);
		this.rights = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.rightImages)), 1/8d, this, StandardAnimator.PRIORITY_3RD);
		this.aLefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.attackLeftImages)), 1/30d, this, StandardAnimator.PRIORITY_3RD);
		this.aRights = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.attackRightImages)), 1/30d, this, StandardAnimator.PRIORITY_3RD);
		this.hurtsL = new StandardAnimator((new ArrayList<BufferedImage>(Arrays.asList(this.hurtLeft))), 1/2d, this, StandardAnimator.PRIORITY_3RD);
		this.hurtsR = new StandardAnimator((new ArrayList<BufferedImage>(Arrays.asList(this.hurtRight))), 1/2d, this, StandardAnimator.PRIORITY_3RD);
	}

	private void dropCoins(){
		if(this.health <= 0){
			
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
