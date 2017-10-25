package com.finalproject.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.finalproject.entities.Player;
import com.joshuacrotts.standards.StandardCamera;
import com.joshuacrotts.standards.StandardDraw;
import com.joshuacrotts.standards.StandardFade;
import com.joshuacrotts.standards.StandardGame;
import com.joshuacrotts.standards.StdOps;

public class GUI {

	private StandardGame g;
	private Player player;
	private StandardCamera cam;

	private StandardFade fontFade = null;
	private StandardFade fader = null;
	private StandardFade healthFader = null;
	
	private BufferedImage fillHeart = null;
	private BufferedImage guiBar = null;
	private BufferedImage healthBar = null;

	public static Font titleFont = null;
	public static Font arial = null;

	public GUI(StandardGame g, Player player, StandardCamera cam){
		this.g = g;
		this.player = player;
		this.cam = cam;

		try{
			//Init images
			this.fillHeart = ImageIO.read(new File("res/sprites/miscobj/fullheart.png"));
			//ImageIO.read(new File("res/sprites/miscobj/emptyheart.png"));
			//ImageIO.read(new File("res/sprites/miscobj/smalllogo.png"));
			this.guiBar = ImageIO.read(new File("res/sprites/miscobj/GUI_BAR.png"));
			this.healthBar = ImageIO.read(new File("res/sprites/obj/healthgui.png"));
			
			//Init fonts
			GUI.titleFont = StdOps.initFont("res/fonts/oldeng.TTF", 40f);
			GUI.arial = new Font("Arial", Font.TRUETYPE_FONT, 15);
			
			//Init fades
			this.fader = new StandardFade(Color.BLUE, StandardDraw.BLUE_CRAYOLA, 0.01f);
			this.fontFade = new StandardFade(StandardDraw.ORANGE, StandardDraw.YELLOW, 0.03f);
			this.healthFader = new StandardFade(Color.GREEN, StandardDraw.DARK_GREEN, 0.01f);
		}catch(Exception e){ e.printStackTrace(); System.exit(1);}
	}

	public void tick(){
		
	}

	public void render(Graphics2D g2){
		g2.setColor(fader.combine());

		final int sizeFactor = 20;
		
		int x = (int) this.cam.x - g.width() / 2;
		int y = (int) this.cam.y - g.height() / 2;

		//Draw the bar
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2.fillRect(x, y + 10, this.g.width(), 120);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		//Draw the two background GUI bars
		g2.drawImage(this.guiBar, x+5, y+20, 600, 100, null); //1st Bar
		g2.drawImage(this.guiBar, x+675, y+20, 600, 100, null); //2nd Bar
		
		

		/* These coordinates were serious trial and error to establish... */
		/**ACTUAL GUI STUFF**/
		StandardDraw.text("Castlevania - Legion", x + 180, y + 60, GUI.titleFont, 30, this.fontFade.combine());
		StandardDraw.text("Money: "+this.player.money, x + 720, y + 60, GUI.titleFont, 30, Color.ORANGE);
		/**BELOW IS BUG INFORMATION**/
		//StandardDraw.text("BUG: If you can... maybe try and fix the animation thing with Richter's damage...?", x + 165, y + 145, GUI.arial, 20f, Color.BLACK);
		//StandardDraw.text("BUG: Maybe try and fix the crappy jump mechanic?", x + 380, y + 185, GUI.arial, 20f, Color.BLACK);
		//StandardDraw.text("Uh...... hmm... what now?", x + 380, y + 205, GUI.arial, 20f, Color.black);
		//StandardDraw.text("BUG: Crosses & projectiles don't do dmg... can you fix that?", x + 380, y + 205, GUI.arial, 20f, Color.BLACK);
		//StandardDraw.text("BUG******: You can whip and swing 500000x a second. Can you fix?", x + 540, y + 225, GUI.arial, 20f, Color.yellow);
		//StandardDraw.text("Oh yeah one more thing: Can you try and clean up the death crap? Comment out checkDeath() and you'll be okay", x + 240, y + 245, GUI.arial, 20f, Color.yellow);
		
		if(this.player.health == 0) return;

		StandardDraw.text("Health: ", x + 50, y + 100, GUI.titleFont, 30, Color.ORANGE);
		
		
		StandardDraw.rect(x + 145, y + 75, this.player.health, 28, healthFader.combine(), true);
		StandardDraw.rect(x + 145, y + 75, 300, 28, Color.black, false);
		
//		/**
//		 * This just draws the hearts in a 2 x 10 pattern.
//		 */
//		for(int i = 0, xx = 50, yy = 46; i < this.player.health; i++, xx += sizeFactor + 3){
//			if(i % 10 == 0){
//				xx = 50;
//				yy += sizeFactor;
//			}
//			g2.drawImage(fillHeart, x + xx, y + yy, sizeFactor + 3, sizeFactor, null);
//		}
	}
}

