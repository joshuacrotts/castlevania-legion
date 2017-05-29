package com.finalproject.entities.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardID;

public class Candle extends StandardGameObject{

	public Candle(double x, double y){
		
		super(x, y, StandardID.Object);
		
		try{
			this.initImages();
			this.initAnimators();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void tick(){
		this.lefts.animate();
	}

	public void render(Graphics2D g2){
		g2.drawImage(this.currentSprite, (int) x, (int) y, null);
		g2.setColor(Color.red);
		g2.draw(this.getBounds());
	}

	private void initImages() throws Exception{
		this.leftImages = new BufferedImage[3];

		for(int i = 0; i < this.leftImages.length; i++){
			this.leftImages[i] = ImageIO.read(new File("res/sprites/obj/torch1/torch1_"+i+".png"));
			this.width += this.leftImages[i].getWidth(); this.height += this.leftImages[i].getHeight();
		}
		
		this.width /= this.leftImages.length;
		this.height /= this.leftImages.length;
	}
	
	private void initAnimators(){
		
		this.lefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.leftImages)), 1/6d, this, StandardAnimator.PRIORITY_3RD);
		
	}
}

