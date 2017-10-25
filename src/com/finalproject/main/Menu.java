package com.finalproject.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.andrewmatzureff.input.Mouse;
import com.finalproject.entities.BoxParticle;
import com.joshuacrotts.standards.StandardAudio;
import com.joshuacrotts.standards.StandardDraw;
import com.joshuacrotts.standards.StandardFade;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardParticleHandler;
import com.joshuacrotts.standards.StdOps;

public class Menu implements MouseListener{

	private Mouse mouse;
	private Game stdGame;

	//Graphics/Fonts/Colors

	public static Font starcraft = StdOps.initFont("res/fonts/starcraft.ttf", 100f);
	//private StandardParticleHandler dripHandler = null;
	private StandardParticleHandler bgParticles = null;

	//Rectangle Buttons and colors alongside them if applicable
	private Rectangle play = null; //private Color pButton = Color.RED;
	private Rectangle help = null; //private Color hButton = Color.GREEN;
	private Rectangle quit = null; //private Color qButton = Color.BLUE;
	private Rectangle back = null;
	private Rectangle credits = null;

	//StandardFades
	public static StandardFade mainFade = new StandardFade(StandardDraw.DARK_PURPLE, StandardDraw.PURPLE, 0.005f);
	private StandardFade logoFade = new StandardFade(StandardDraw.FIRE_BRICK, StandardDraw.YELLOW, 0.01f);
	private StandardFade pButton= new StandardFade(StandardDraw.BURNT_ORANGE, StandardDraw.BARN_RED, 0.005f);
	private StandardFade hButton= new StandardFade(StandardDraw.GREEN, StandardDraw.DARK_MOSS_GREEN, 0.005f);
	private StandardFade qButton= new StandardFade(StandardDraw.BLUE_MUNSELL, StandardDraw.BLUE_PANTONE, 0.005f);
	private StandardFade bButton = new StandardFade(StandardDraw.YELLOW_PANTONE, StandardDraw.YELLOW_CRAYOLA, 0.005f);
	private StandardFade cButton = new StandardFade(StandardDraw.SALMON_PINK, StandardDraw.RED_CRAYOLA, 0.005f);
	private StandardFade cTextFade = new StandardFade(Color.white, StandardDraw.CAROLINA_BLUE, 0.0005f);

	//Enum for the state that the menu is currently on.
	public enum MenuState {MainMenu, Difficulty, Help, Credits};
	public MenuState currentState = MenuState.MainMenu;

	//Logo image
	public static BufferedImage bgImage = null;
	private BufferedImage logoImage = null;

	//Audio for sounds
	private StandardAudio menuMusic = null;
	//private StandardAudio[] buttonBuffer = null;

	//Turns the sound on ONLY on the first pass
	private boolean firstPass = true;

	public Menu(Game stdGame, Mouse mouse){

		this.stdGame = stdGame;
		this.mouse = mouse;

		this.bgParticles = new StandardParticleHandler(1000);

		this.initButtons();

		try {
			this.logoImage = ImageIO.read(new File("res/sprites/miscobj/resizeLogo.png"));
			Menu.bgImage = ImageIO.read(new File("res/sprites/miscobj/wallpaper2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

		this.menuMusic = new StandardAudio("res/audio/music/menu2.mp3", false);

	}

	public void tick(){
		if(firstPass){
			this.menuMusic.FXPlay();
			this.firstPass = false;
		}

		this.bgParticles.addEntity(new BoxParticle(StdOps.rand(0, 2000), StdOps.rand(800, 1000), StdOps.rand(1, 5), StdOps.rand(-30, -15), Color.WHITE, 0f, this.bgParticles));

		StandardHandler.Handler(this.bgParticles);
		StandardHandler.Handler(BoxParticle.trailH);

		//System.out.println(BoxParticle.trailH.size());

	}

	public void render(){

		this.initButtons(); //This is only here for debug purposes with hotswapping

		StandardDraw.rect(0, 0, stdGame.width(), stdGame.height(), mainFade.combine(), true);//The background fade
		//StandardDraw.rect(20, 20, stdGame.width() - 45, stdGame.height()-70, Color.black, true);//Black rect overtop
		StandardDraw.Renderer.drawImage(Menu.bgImage, 20, 20, this.stdGame.width()-45, this.stdGame.height()-70, null);

		//	StandardDraw.Handler(this.bgParticles);
		//	StandardDraw.Handler(BoxParticle.trailH);

		//StandardDraw.text("SINISTER", StdOps.rand(360, 361), StdOps.rand(199, 200), starcraft, starcraft.getSize2D(),mainFade.combine());//Draws the title text
		StandardDraw.Renderer.drawImage(this.logoImage, StdOps.rand(20, 20), StdOps.rand(20, 20), null);
		StandardDraw.text("MADE BY: JOSHUA CROTTS AND ANDREW MATZUREFF", 30, 650, Menu.starcraft, 35, logoFade.combine());


		//Render buttons with appropriate colors
		if(this.currentState == MenuState.MainMenu){
			//Play Button
			//StandardDraw.Renderer.setColor(this.buttonFade.combine());
			StandardDraw.Renderer.setColor(this.pButton.combine());
		//	StandardDraw.Renderer.draw(this.play);

			//Help Button
			StandardDraw.Renderer.setColor(this.hButton.combine());
		//	StandardDraw.Renderer.draw(this.help);

			//Quit button
			StandardDraw.Renderer.setColor(this.qButton.combine());
		//	StandardDraw.Renderer.draw(this.quit);
			
			//Credits button
			StandardDraw.Renderer.setColor(this.cButton.combine());
		//	StandardDraw.Renderer.draw(this.credits);

			//Text for the appropriate buttons
			/*
			 * These clauses test if the mouse is over the appropriate button.
			 * If true, set the color to the fade and draw the string.
			 * Else, set color to white, and draw it.
			 */

			//Clause for the play button
			if(StdOps.mouseOver(this.mouse.x, this.mouse.y, this.play)){
				StandardDraw.text("PLAY", 125, 465, Menu.starcraft, 60, this.pButton.combine());

			}else{
				StandardDraw.text("PLAY", 125, 465, Menu.starcraft, 60, Color.WHITE);
			}

			//Clause for the help button
			if(StdOps.mouseOver(this.mouse.x, this.mouse.y, this.help)){
				StandardDraw.text("HELP", 555, 465, Menu.starcraft, 60, this.hButton.combine());
			}else{
				StandardDraw.text("HELP", 555, 465, Menu.starcraft, 60, Color.WHITE);
			}

			//Clause for the quit button
			if(StdOps.mouseOver(this.mouse.x, this.mouse.y, this.quit)){
				StandardDraw.text("QUIT", 975, 465, Menu.starcraft, 60, this.qButton.combine());
			}else{
				StandardDraw.text("QUIT", 975, 465, Menu.starcraft, 60, Color.WHITE);
			}
			
			//Clause for the credits button.
			if(StdOps.mouseOver(this.mouse.x, this.mouse.y, this.credits)){
				StandardDraw.text("CREDITS", 530, 555, Menu.starcraft, 40, this.cButton.combine());
			}else{
				StandardDraw.text("CREDITS", 530, 555, Menu.starcraft, 40, Color.WHITE);
			}

			return;

		}

		/***This clause is for when the player is on the help screen***/
		if(this.currentState == MenuState.Help){
			StandardDraw.text("This is a demo so please be lenient on this.", 225, 300, Menu.starcraft, 30, hButton.combine());
			StandardDraw.text("Press W, A, S, D to move Left, Right, Up, and Down respectively.", 225, 350, Menu.starcraft, 20, hButton.combine());
			StandardDraw.text("Use the UP key to attack, and the LEFT key to throw your projectile, Space to Jump.", 85, 400, Menu.starcraft, 20, hButton.combine());
			StandardDraw.Renderer.setColor(this.bButton.combine());

			//Clause for the back button
			if(StdOps.mouseOver(this.mouse.x, this.mouse.y, this.back)){
				StandardDraw.text("BACK", 555, 465, Menu.starcraft, 50, this.bButton.combine());
			}else{
				StandardDraw.text("BACK", 555, 465, Menu.starcraft, 50, Color.WHITE);
			}

			return; 
		}
		
		/***This clause is for when the player is on the Credits screen***/
		if(this.currentState == MenuState.Credits){
			StandardDraw.text("Credits:", 560, 280, Menu.starcraft, 30, Color.WHITE);
			StandardDraw.text("Game was made by Joshua Crotts and Andrew Matzureff (friend from WV).", 115, 310, Menu.starcraft, 20, this.cTextFade.combine());
			StandardDraw.text("Textures taken from different sources; artwork does not belong to myself.", 110, 340, Menu.starcraft, 20, this.cTextFade.combine());
			StandardDraw.text("*Engine for game is Standards 2.0 (made by Joshua and Andrew); No Longer for Public Use*" ,80, 370, Menu.starcraft, 18, this.cTextFade.combine());
			StandardDraw.text("*Games accredited for:Castlevania Aria of Sorrow, Dawn of Sorrow, Order of Ecclesia, Portrait of Ruin*" ,90, 395, Menu.starcraft, 15, this.cTextFade.combine());
			
			StandardDraw.Renderer.setColor(this.bButton.combine());
			//Clause for the back button
			if(StdOps.mouseOver(this.mouse.x, this.mouse.y, this.back)){
				StandardDraw.text("BACK", 555, 465, Menu.starcraft, 50, this.bButton.combine());
			}else{
				StandardDraw.text("BACK", 555, 465, Menu.starcraft, 50, Color.WHITE);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(Game.gameState == Game.GAME_STATE.Menu){
			int mx = e.getX(); int my = e.getY();

			//If the mouse clicks Play
			if(StdOps.mouseOver(mx, my, this.play)){
				Game.audioBuff.Play_Button();
				Game.gameState = Game.GAME_STATE.Transition;
				this.menuMusic.FXStop();
				//return;
			}

			//Clicks Help
			if(StdOps.mouseOver(mx, my, this.help) && this.currentState == MenuState.MainMenu){
				Game.audioBuff.Play_Button();
				this.currentState = MenuState.Help;
				return;
			}
			
			//Clicks credits
			if(StdOps.mouseOver(mx, my, this.credits) && this.currentState == MenuState.MainMenu){
				Game.audioBuff.Play_Button();
				this.currentState = MenuState.Credits;
				return;
			}
			
			//Clicks Back FROM Help OR Credits
			if(StdOps.mouseOver(mx, my, this.back) && (this.currentState == MenuState.Help || this.currentState == MenuState.Credits)){
				Game.audioBuff.Play_Button();
				this.currentState = MenuState.MainMenu;
				return;
			}

			//Clicks quit
			if(StdOps.mouseOver(mx, my, this.quit)){
				Game.audioBuff.Play_Button();
				System.exit(0);
			}

		}

	}

	private void initButtons(){
		this.play = new Rectangle(100,400, 240, 100);
		this.help = new Rectangle(520,400, 240, 100);
		this.quit = new Rectangle(940,400, 240, 100);
		this.back = new Rectangle(520,400, 240, 100);
		this.credits = new Rectangle(520, 520, 240, 50);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
