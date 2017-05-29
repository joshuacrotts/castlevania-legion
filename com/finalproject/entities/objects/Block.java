package com.finalproject.entities.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;

import javax.imageio.ImageIO;

import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardID;

public class Block extends StandardGameObject{

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param redValue - determines the brick type
	 */
	public Block(double x, double y, double width, double height, int redValue){
		super(x, y, StandardID.Obstacle);
		this.currentSprite = null;
		this.width = (int) width;
		this.height = (int) height;

		try{
			if(redValue == 255){
				this.currentSprite = ImageIO.read(new File("res/sprites/obj/wall0.png"));
			}

			if(redValue == 254){
				this.currentSprite = ImageIO.read(new File("res/sprites/obj/brownwall0.png"));
			}

			if(redValue == 253){
				this.currentSprite = ImageIO.read(new File("res/sprites/obj/redwall0.png"));
			}

			if(redValue == 252){
				this.currentSprite = ImageIO.read(new File("res/sprites/obj/grass2.png"));
			}
			
			if(redValue == 251){
				this.currentSprite = ImageIO.read(new File("res/sprites/obj/dirt0.png"));
			}
			
			if(redValue == 250){
				this.currentSprite = ImageIO.read(new File("res/sprites/obj/wood0.png"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Block(double x, double y, double width, double height){
		super(x, y, StandardID.Obstacle);
		this.currentSprite = null;
		this.width = (int) width;
		this.height = (int) height;
	}

	public void tick(){

	}

	public void render(Graphics2D g2){
		for(int i = 0, xx = (int)x; i < width/32; i++, xx+=32){
			for(int j = 0, yy = (int)y; j < height/32; j++, yy+=32){
				g2.drawImage(currentSprite, (int)xx, (int)yy, null);
				
			}
		}
//		
//		g2.setColor(Color.red);
//		g2.draw(this.getBounds());

	}
	@Override
	public double getRestitution(){
		return 1.5;
	}
	
}
