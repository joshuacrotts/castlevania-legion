package com.finalproject.weapons;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.finalproject.entities.Player;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardHandler;

public class ClaimhSolias extends MeleeWeapon{
	
	private static final byte IMAGES = 14;
	
	public ClaimhSolias(double dmg, Player player, StandardHandler enemyHandler) {
		super(dmg, player, enemyHandler);
		
		this.initImages();
	}
	
	@Override
	public void initImages(){
		this.leftWeapons = new ArrayList<BufferedImage>();
		this.rightWeapons = new ArrayList<BufferedImage>();
		
		try{
			for(int i = 0; i < IMAGES; i++){
				//System.out.println("ADD??!!??!");
				this.leftWeapons.add(ImageIO.read(new File("res/sprites/weapons/claimh/c_l"+i+".png")));
				this.rightWeapons.add(ImageIO.read(new File("res/sprites/weapons/claimh/c_r"+i+".png")));
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
			this.x = this.player.x - player.width - 30d;
			this.y = this.player.y - 50d;
		}else{
			this.x = this.player.x + 50;
			this.y = this.player.y - 50d;
		}
		
		super.tick();
	}
	
}
