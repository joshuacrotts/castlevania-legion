package com.finalproject.entities.objects;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardID;

public class Tree extends StandardGameObject{

	
	
	public Tree(int x, int y, int greenValue){
		super(x, y, StandardID.Object);
			
		try {
			this.currentSprite = ImageIO.read(new File("res/sprites/nature/tree"+greenValue+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.width = this.currentSprite.getWidth();
		this.height = this.currentSprite.getHeight();
		
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics2D g2){
		g2.drawImage(this.currentSprite, (int) this.x, (int) this.y, null);
	}
}
