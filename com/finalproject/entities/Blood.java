package com.finalproject.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.finalproject.main.Game;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardID;

public class Blood extends StandardGameObject{

	private StandardGameObject obj;
	
	public Blood(double x, double y, StandardGameObject obj){
		super(x, y, StandardID.Particle);

		
		this.obj = obj;
		
		try{
			this.initImages();
			this.initAnimators();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public void tick() {
		if(this.obj.lastDir == Direction.Left){
			if(this.lefts.getFrame() != this.lefts.images.size() - 1){
				this.lefts.animate();
			}else{
				Game.bloodHandler.removeEntity(this);
			}
		}
		
		if(this.obj.lastDir == Direction.Right){
			if(this.lefts.getFrame() != this.lefts.images.size() - 1){
				this.lefts.animate();
			}else{
				Game.bloodHandler.removeEntity(this);
			}
		}
		
	}

	@Override
	public void render(Graphics2D g2) {
		//System.out.println("CALL?!?!?");
		g2.drawImage(this.currentSprite, (int)x, (int)y, null);
		
	}


	private void initImages() throws Exception{
		this.leftImages = new BufferedImage[6]; this.rightImages = new BufferedImage[this.leftImages.length];
		
		for(int i = 0; i < this.leftImages.length; i++){
			this.leftImages[i] = ImageIO.read(new File("res/sprites/miscobj/redblood2/b_l"+i+".png"));
			this.rightImages[i] = ImageIO.read(new File("res/sprites/miscobj/redblood2/b_l"+i+".png"));//LOADS IN LEFT BY DEFAULT
		}
	}
	
	private void initAnimators() throws Exception{
		
		this.lefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.leftImages)), 1/30d, this, StandardAnimator.PRIORITY_3RD);
		
	}

	
}
