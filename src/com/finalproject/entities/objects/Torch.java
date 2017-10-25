package com.finalproject.entities.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.joshuacrotts.particles.DragParticle;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardDraw;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;
import com.joshuacrotts.standards.StandardParticleHandler;

public class Torch extends StandardGameObject{

	private BufferedImage[] img = null; private StandardAnimator animator = null;
	
	private Color r;
	
	private StandardParticleHandler p = new StandardParticleHandler(100);
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param redValue - determines the torch type, 86 <= n <= 91
	 * 
	 * redValue 86 = Small Blue Torch
	 * redValue 87 = Small Red Torch
	 * redValue 88 = Small Green Torch
	 * 89 = NULL (DOES NOT WORK; tried a different torch image, but failed)
	 * 90 = Standard Torch
	 * 91 = NULL
	 */
	public Torch(double x, double y, int redValue){
		super(x, y, StandardID.Object);
		
		this.currentSprite = null;

		if(redValue == 89) redValue = 87;
		
		this.initImages(redValue);
		this.initAnimators();
		
		/*Make sure to adjust this to account for other r values...
		 * I spent like 20 minutes trying to debug this lol
		 * I put a default case in the instance that you forget
		 */
		switch(redValue){
			case 86: r = new Color(0x0000ff); break; //B
			case 87: r = new Color(0xff0000); break; //R
			case 88: r = new Color(0x00ff00); break; //G
			case 89: r = new Color(0xff0000); break;
			case 90: r = new Color(0xFFA500); break; //Orange
			default: System.err.println("R IS NOT VALID : "+redValue);
		}
	}

	public void tick(){

		this.animator.animate();
		
		StandardHandler.Handler(this.p);
		
	}

	public void render(Graphics2D g2){
		g2.drawImage(currentSprite,(int) x, (int) y, null);
		
		//Adds a new particle and draws it
		new DragParticle(x + 18, y + 30, .5, p, this.r);

		StandardDraw.Handler(this.p);	
	}

	/**
	 * Kind of a clumsy way to do this, but I had no other way.
	 * @param r
	 */
	private void initImages(int r){
		if(r < 86 || r > 91){
			System.err.println("Error! Torch is not a valid number.");
			System.exit(1);
		}

		this.img = new BufferedImage[4];

		try{
			for(int i = 0; i < this.img.length; i++){
				this.img[i] = ImageIO.read(new File("res/sprites/obj/torch/"+r+"_"+i+".png"));
				this.width += this.img[i].getWidth();
				this.height += this.img[i].getHeight();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		this.width /= (int) this.img.length;
		this.height /= (int) this.img.length;
	}

	private void initAnimators(){
		
		this.animator = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.img)),1/10d, this, StandardAnimator.PRIORITY_3RD);
	}
}
