package com.finalproject.main;

import java.awt.Color;
import java.awt.Graphics2D;

import com.joshuacrotts.standards.StandardAudio;
import com.joshuacrotts.standards.StandardDraw;

public class Transition {

	private StandardAudio aud = new StandardAudio("res/audio/sfx/whoosh.wav");
	private Menu menu;
	private Game game;
	private int alpha = 0; //alpha channel
	//Timing variables
	private double velX = 15; private double velXTwo = -15;
	private int x; private int xTwo = 1280;
	private short count = 0;
	
	private boolean first = true; private boolean playsfx = true; private boolean finalPass = false;
	
	
	public Transition(Game game, Menu menu){
		this.game = game;
		this.menu = menu;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics2D g2){
		if(Game.gameState == Game.GAME_STATE.Transition){
			if(alpha < 254 && first){
				//System.out.println("When does this not happen");
				
				if(game.firstPass){
					this.menu.render();
				}else{
					game.levels.get(Game.levelNum-2).render(StandardDraw.Renderer);
					game.fp = true; game.musicFirstPass = true;
				}
				
				g2.setColor(new Color(0,0,0,alpha+=2));
				g2.fillRect(0, 0, this.game.width(), this.game.height());
				return;
			}else{
				first = false;
			}
			
			if(count < 255 && !first){
				if(playsfx){
					aud.rAndP();
					playsfx = false;
				}
				velX *= 0.98;
				velXTwo *= 0.98;
				
				if(velX < 0.15){
					velX = 0.15; 
				}
				
				if(Math.abs(velXTwo) < 0.45){
					velXTwo = 0.45;
				}
				
				StandardDraw.text("Level: "+Game.levelNum, x += velX,300, Menu.starcraft, 30f, Color.WHITE);
				StandardDraw.text("Good luck!", xTwo += velXTwo,400, Menu.starcraft, 30f, Color.WHITE);
				count++;
				
			}else{
				alpha = 255;
				finalPass = true;
			}
			
			
			if(alpha > 0 && count >= 255 && xTwo > -250 || (finalPass)){
				velX += 0.25; velXTwo -= 0.25;
				
				StandardDraw.text("Level: "+Game.levelNum, x += velX,300, Menu.starcraft, 30f, Color.WHITE);
				StandardDraw.text("Good luck!", xTwo += velXTwo,400, Menu.starcraft, 30f, Color.WHITE);
				if(xTwo < -250){
					this.alpha = 0; this.count = 0; this.x = 0; this.xTwo = 1280; this.velX = 15; this.velXTwo = -15;
					this.first = true; this.playsfx = true; this.finalPass = false;
					Game.gameState = Game.GAME_STATE.Game;
					return;
					
				}
				
				//System.out.println(xTwo);
				
			}else{
				//Game.gameState = Game.GAME_STATE.Game;
				return;
			}
			
		}
	}
	
}
