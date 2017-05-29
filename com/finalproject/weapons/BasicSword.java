package com.finalproject.weapons;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.finalproject.entities.Player;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardHandler;

public class BasicSword extends MeleeWeapon{
	
	private static final byte IMAGES = 15;
	
	public BasicSword(double dmg, Player player, StandardHandler enemyHandler) {
		super(dmg, player, enemyHandler);
		
		this.initImages();
	}
	
	@Override
	public void initImages(){
		this.leftWeapons = new ArrayList<BufferedImage>();
		this.rightWeapons = new ArrayList<BufferedImage>();
		
		try{
			for(int i = 0, j = IMAGES - 1; i < IMAGES && j >= 0; i++, j--){
				//System.out.println("ADD??!!??!");
				this.leftWeapons.add(ImageIO.read(new File("res/sprites/weapons/basicsword/basics_r_"+j+".png")));
				this.rightWeapons.add(ImageIO.read(new File("res/sprites/weapons/basicsword/basics_r_"+i+".png")));
			}	
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		
		this.lefts = new StandardAnimator(leftWeapons, 1/55d, this, StandardAnimator.PRIORITY_3RD);
		this.rights = new StandardAnimator(rightWeapons, 1/55d, this, StandardAnimator.PRIORITY_3RD);
	}
	
	public void tick(){
		
		if(this.player.lastDir == Direction.Left){
			this.x = this.player.x - player.width + 10d;
			this.y = this.player.y + 5d;
		}else{
			this.x = this.player.x + 10d;
			this.y = this.player.y + 5d;
		}
		
		super.tick();
	}
	
}
