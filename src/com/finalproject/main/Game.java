package com.finalproject.main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.finalproject.entities.Player;
import com.finalproject.entities.Rain;
import com.joshuacrotts.standards.StandardAudio;
import com.joshuacrotts.standards.StandardCamera;
import com.joshuacrotts.standards.StandardCollisionHandler;
import com.joshuacrotts.standards.StandardDraw;
import com.joshuacrotts.standards.StandardGame;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StdOps;

public class Game extends StandardGame implements KeyListener {

    private static final long serialVersionUID = -5818574634821450064L;

    private final short MIN_X = 640;//These numbers are just guess&check..
    private final short MAX_Y = 350;

    private Menu menu = null;
    private StandardCollisionHandler stdHandler = null;
    private StandardCamera camera = null;
    private GUI gui = null;
    private Player player = null;
    public static AudioBuffer audioBuff = null;
    private Transition menuToGame = null;
    private Rain rain;

    //Blood handler, to keep things easier
    public static StandardHandler bloodHandler = null;

    //Levels
    public ArrayList<Level> levels = null;
    public static byte levelNum = 1;
    private final byte amtOfLevels = 4;

    public static StandardAudio[] music = null;

    //States for controlling the Game itself
    public enum GAME_STATE {
        Menu, Paused, Game, Transition
    };
    public static GAME_STATE gameState = GAME_STATE.Menu;

    public boolean paused = false;
    public boolean fp = true;
    public boolean firstPass = true;
    public boolean musicFirstPass = true;

    public Game(int width, String title) {
        super(width, title);

        this.consoleFPS = false;

        this.menu = new Menu(this, this.getMouse());
        this.stdHandler = new StandardCollisionHandler(null);
        this.player = new Player(400, 0, this, this.stdHandler);
        this.camera = new StandardCamera(player, 1, this.getWidth(), this.getHeight());
        this.menuToGame = new Transition(this, this.menu);
        this.stdHandler.stdCamera = this.camera;
        this.rain = new Rain(this.camera, 1000);

        Game.bloodHandler = new StandardHandler();
        //Game.coinHandler = new StandardCollisionHandler(this.camera);
        Game.audioBuff = new AudioBuffer();
        /**
         * This is just to prevent the camera from going too far.
         */
        this.camera.restrict(Short.MAX_VALUE, MAX_Y, MIN_X, -Short.MAX_VALUE);

        this.gui = new GUI(this, this.player, this.camera);

        this.stdHandler.addEntity(player);

        this.levels = new ArrayList<Level>();

        this.initLevels();

        try {
            this.window.getFrame().setIconImage(ImageIO.read(new File("res/sprites/miscobj/imageicon_C_fire.png")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.addListener(this);//Adds the keyboard listener for the stupid pause button.
        this.addListener(this.menu); //Adds the mouse listener for simplicity

        this.StartGame();
    }

    @Override
    public void tick() {
//    	
//    	if(player.x > 1500000 && fp){
//   
//    		levelNum++;
//    		
//    		Game.gameState = GAME_STATE.Transition;
//    		Game.music[levelNum-2].FXStop();
//    		
//    		
//    		
//    		this.stdHandler.clearEntities();
//    		this.player.x = 1200;
//    		this.player.y = 0;
//    		
//    		
//    		fp = false;
//    	}

        if (Game.gameState == GAME_STATE.Menu) {
            this.menu.tick();
            return;
        }

        if (Game.gameState == GAME_STATE.Game && !this.paused) {
            StandardHandler.Handler(Game.bloodHandler);
            StandardHandler.Handler(this.stdHandler);
            StandardHandler.Object(this.camera);
            StandardHandler.Handler(this.player.trailH);
            this.rain.tick();
            this.gui.tick();
            return;
        }

    }

    @Override
    public void render() {
        if (Game.gameState == GAME_STATE.Menu) {
            this.menu.render();
            return;
        }

        if (Game.gameState == GAME_STATE.Transition) {
            this.menuToGame.render(StandardDraw.Renderer);
            return;
        }

        if (Game.gameState == GAME_STATE.Game) {

            if (musicFirstPass) {

                Game.music[levelNum - 1].FXPlay();
                Game.music[levelNum - 1].FXLoop();
                this.musicFirstPass = false;
                this.firstPass = false;

            }

            this.levels.get(Game.levelNum - 1).render(StandardDraw.Renderer);
            StandardDraw.Object(this.camera);
            StandardDraw.Handler(Game.bloodHandler);
            StandardDraw.Handler(this.stdHandler);
            StandardDraw.Handler(this.player.trailH);
            this.rain.render(StandardDraw.Renderer);
            this.gui.render(StandardDraw.Renderer);
            //return;
        }

        if (this.paused) {
            //this.player.velX = 0;
            Color c = StandardDraw.Renderer.getColor();
            StandardDraw.Renderer.setColor(new Color(0, 0, 0, 0.5f));
            StandardDraw.Renderer.fillRect((int) -200, -200, 50000, 10000);//Just draws a HUGE rectangle over hopefully affected areas
            StandardDraw.text("PAUSED", (int) StdOps.rand(this.camera.x - 20, this.camera.x - 19), (int) StdOps.rand(this.camera.y, this.camera.y + 1), Menu.starcraft, 24f, Color.white);
            StandardDraw.Renderer.setColor(c);
            //this.displayControls();
        }

    }

    private void initLevels() {

        Game.music = new StandardAudio[this.amtOfLevels];

        /**
         * Hard coded levels && music Volume levels go between 0.0 and 1.0
         * inclusive.
         *
         * Using adjustFXVolume takes in a positive or negative number; the
         * default value of volume is 1, so if you wanted the volume to be 70%
         * quieter, do adjustFXVolume(-0.7); the new volume per the file will be
         * 0.3.
         */
        Game.music[0] = new StandardAudio("res/audio/music/level5.mp3", false);
        music[0].adjustFXVolume(-0.7);
        this.levels.add(new Level("res/levels/test13.png", "res/levels/bg/startest0.png", "res/levels/bg/gatetest0.png", this.stdHandler, this.player));

        //Other levels will be added on the fly.
        //Game.music[1] = new StandardAudio("res/audio/music/menu1.mp3", false); music[1].adjustFXVolume(-0.7);
        //Game.music[2] = new StandardAudio("res/audio/music/level3.mp3", false); music[2].adjustFXVolume(-0.7);
        //Game.music[3] = new StandardAudio("res/audio/music/level4.mp3", false); music[3].adjustFXVolume(-0.7);
    }

    private void displayControls() {
        StandardDraw.text("Press W, A, S, D to move Left, Right, Up, and Down respectively.", 225, 400, Menu.starcraft, 20, Color.WHITE);
        StandardDraw.text("Use the G key to attack, and the C key to throw your projectile, Space to Jump.", 85, 440, Menu.starcraft, 20, Color.WHITE);
        StandardDraw.text("Also, to prevent conflictions, do not press any movement/attack keys whilst paused.", 15, 480, Menu.starcraft, 20, Color.WHITE);
    }

    /**
     * I just finally included the KeyListener interface because your
     * Command/Input framework wasn't working properly; I would press P to
     * pause, then it would work sometimes, othertimes it would just unpause
     * immediately.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (paused & e.getKeyCode() == KeyEvent.VK_P) {
            this.paused = false;
            return;
        }

        switch (e.getKeyCode()) {

            case KeyEvent.VK_P:
                this.paused = true;

            default:
                return;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
