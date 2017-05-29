package com.finalproject.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.finalproject.main.AudioBuffer;
import com.finalproject.main.Game;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardCollisionHandler;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardID;
import com.joshuacrotts.standards.StdOps;

public class Coin extends StandardGameObject{

	private String type;
	private Player player;
	private StandardCollisionHandler sch;
	
	private byte value;

	public Coin(double x, double y, byte typeB, Player player, StandardCollisionHandler sch){
		super(x, y, StandardID.Powerup);
		
		this.player = player;
		this.sch = sch;
		
		if(typeB >= 0 && typeB < 20){
			this.type = "blue";
			this.value = 1;
		}
		if(typeB >= 20 && typeB < 30){
			this.type = "brass";
			this.value = 5;
		}
		if(typeB >= 30 && typeB < 35){
			this.type = "green";
			this.value = 10;
		}
		if(typeB >= 35 && typeB <= 40){
			this.type = "gold";
			this.value = 100;
		}
		
		try{
			this.initImages();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}

		this.lefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.leftImages)), 1/5d, this, StandardAnimator.PRIORITY_3RD);
		
		do{
			this.velX = StdOps.rand(-2, 2);
		}while(this.velX == 0);
		
		this.width = 14;
		this.height = 14;
	}


	public void tick(){
		
		if(this.player.getBounds().intersects(this.getBounds())){
			Game.audioBuff.Play_Coin_SFX();
			this.player.money += value;
			this.sch.removeEntity(this);
			return;
		}
		
		this.x += velX;
		this.y += velY;
		
		this.velX *= 0.99;
		this.velY = (this.velY + StandardGameObject.gravity);
		
		this.lefts.animate();
	}

	public void render(Graphics2D g2){
		g2.drawImage(this.currentSprite,(int) this.x, (int)this.y, null);
	}
	

	private void initImages() throws IOException {

		this.leftImages = new BufferedImage[4];
		
		for(int i = 0; i < this.leftImages.length; i++){
			if(i != 3){
				//System.err.println("res/sprites/miscobj/coins/"+this.type+"_coin"+i+".png");
				this.leftImages[i] = ImageIO.read(new File("res/sprites/miscobj/coins/"+this.type+"_coin"+i+".png"));
			}
			else
				this.leftImages[3] = this.leftImages[1];
		}	
		
	}
}
