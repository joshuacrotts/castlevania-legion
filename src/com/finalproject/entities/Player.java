package com.finalproject.entities;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.andrewmatzureff.input.Movement;
import com.finalproject.commands.Jump;
import com.finalproject.commands.MeleeCommand;
import com.finalproject.commands.Secondary;
import com.finalproject.main.Game;
import com.finalproject.weapons.Kitana;
import com.finalproject.weapons.MeleeWeapon;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardCollisionHandler;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;
import com.joshuacrotts.standards.StandardTrail;
import com.joshuacrotts.standards.StdOps;

public class Player extends StandardGameObject {

    private Game game;
    public StandardHandler trailH;
    private StandardHandler enemyHandler;

    //Commands specific to Player
    private Movement up, down, left, right;
    private Jump jump;
    private MeleeCommand meleeFight;
    private Secondary secondaryCmd;

    //The weapon(s) the player currently has
    private MeleeWeapon weapon;

    //Money
    public int money = 0;

    //Constant/Stagnant sprites//
    private BufferedImage stillR = null; //Still image for the right
    private BufferedImage stillL = null; //Still image for the left
    private BufferedImage stillAL = null; //Still Image for attack left
    private BufferedImage stillAR = null; //Still Image for attack right
    private BufferedImage duckL;
    private BufferedImage duckR;

    //Proprietary Images to Soma
    private BufferedImage[] jumpUpLeft = new BufferedImage[8];
    private StandardAnimator jumps = null;
    private BufferedImage[] jumpUpRight = new BufferedImage[jumpUpLeft.length];

    private BufferedImage[] fallLeft = new BufferedImage[3]; //private StandardAnimator fallsLeft = null;
    private BufferedImage[] fallRight = new BufferedImage[fallLeft.length]; //private StandardAnimator fallsRight = null;

    private BufferedImage[] hurtLeft = new BufferedImage[20];
    private StandardAnimator hurtsL = null;
    private BufferedImage[] hurtRight = new BufferedImage[this.hurtLeft.length];
    private StandardAnimator hurtsR = null;

    public boolean hurt; //Determines if they're in a "hurt" state
    public boolean usingMelee; //*****Boolean to determine if they're using the melee, this is different from this.attacking******

    public Player(double x, double y, Game g, StandardHandler enemyHandler) {
        super(x, y, StandardID.Player);

        try {
            this.initImages();
            this.initAnimators();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Assigns the game to an instance variable to keep track of when it's paused
        this.game = g;

        //Assigns the enemy handler for collisions with weapons to them
        this.enemyHandler = enemyHandler;

        //Set the trail
        this.trailH = new StandardHandler();

        //Initialize the commands
        this.up = new Movement(this, null, 0f, 0f);
        this.down = new Movement(this, null, 0f, 0f);
        this.left = new Movement(this, this.lefts, -.25f, -0.0f);
        this.right = new Movement(this, this.rights, .25f, -0.0f);

        this.jump = new Jump(this, this.jumps, -8f);

        this.meleeFight = new MeleeCommand(this, this.weapon, 1d);

        this.secondaryCmd = new Secondary(this, (StandardCollisionHandler) this.enemyHandler);

        //Bind them all to keys
        this.up.bind(g.getKeyboard(), KeyEvent.VK_W);
        this.down.bind(g.getKeyboard(), KeyEvent.VK_S);
        this.left.bind(g.getKeyboard(), KeyEvent.VK_A);
        this.right.bind(g.getKeyboard(), KeyEvent.VK_D);

        this.jump.bind(g.getKeyboard(), KeyEvent.VK_SPACE); //Jumps

        this.meleeFight.bind(g.getKeyboard(), KeyEvent.VK_UP);//Spawns the Melee Weapon currently in possession

        this.secondaryCmd.bind(g.getKeyboard(), KeyEvent.VK_LEFT); //Spawns the Cross; similar to fireball

        //Set the common gravity for this object
        StandardGameObject.gravity = .30;

        //Sets the health (300 "hits");
        this.health = 300;

        this.currentSprite = this.stillR;

        this.weapon = new Kitana(1, this, this.enemyHandler);

    }

    @Override
    public void tick() {
        if (!this.game.paused && health > 0) {

            this.x += (int) this.velX;
            this.y += (int) this.velY;
            this.velY = (this.velY + StandardGameObject.gravity);

            this.velX *= 0.95;

            //Just a local variable to, in the end, set the instance moving boolean
            boolean moving = false;

            //If they're not hurt, they can move
            if (this.left.on() && !this.hurt) {
                this.lastDir = Direction.Left;
                moving = true;
            }

            //If they're not hurt, they can move
            if (right.on() && !hurt) {
                this.lastDir = Direction.Right;
                moving = true;
            }

            //Clause for if they're hurt
            if (this.hurt && health > 0) {

                Game.audioBuff.Play_Soma_Hurt(StdOps.rand(0, 3));

                //If they're facing right, they'll fly left when hurt
                if (this.lastDir == Direction.Right) {
                    this.hurtsL.animate();
                    this.velX = -10f;
                } //If they're facing left, they'll fly right when flying
                else {
                    this.hurtsR.animate();
                    this.velX = 10f;
                }
                //Either way, when hurt, they'll fly up and back. ******NEEDS FIXING******
                //this.jump.execute();
            } else {

            }
            this.hurt = false; //Has to be set here so they won't continuously fly back.

            //Clause for if they're attacking in general (IF G OR C (secondary) is pressed)
            if (this.usingMelee || this.attacking) {
                this.currentSprite = (this.lastDir == Direction.Right) ? this.stillAR : this.stillAL;

                //Clause for if they're ONLY using the **melee** weapon, NOT the secondary.
                if (this.usingMelee) {
                    this.weapon.tick();

                }
            }

            //Determines if the player is ducking.
            /*
			 * TODO Implement; not in the demo.
			 * 
			 * This one is kind of buggy because since the width and height of the player are stagnant, the image seems to 
			 * make him appear flying.
             */
            if (down.on()) {
                moving = false;
                //this.standing = false;

                //if(this.lastDir == Direction.Right){
                //	this.setCurrentSprite(this.duckR);
                //}else{
                //	this.setCurrentSprite(this.duckL);
                //}
                return;

            }

            moving = moving || this.up.on() || this.down.on();

            /*If all of these are true, just make him still*/
            if (!this.attacking && !this.usingMelee && this.standing && !moving && !this.hurt && !this.left.on() && !this.right.on()) {
                if (this.lastDir == Direction.Right) {
                    this.setCurrentSprite(stillR);
                } else {
                    this.setCurrentSprite(stillL);
                }
                return;
            }

            //Checks to see if the player is hurting, if they aren't, they have the ability
            //to have jump animations
            if (!this.hurt) {
                if (this.lastDir == Direction.Left) {
                    this.jumps.setImages(new ArrayList<BufferedImage>(Arrays.asList(this.jumpUpLeft)));
                } else {
                    this.jumps.setImages(new ArrayList<BufferedImage>(Arrays.asList(this.jumpUpRight)));
                }

            }
            this.jump.update();

            this.attacking = false;
        }

        /*If he's dead, set his health to zero, stop the track, and change the state
		 * Other changes to his state and the level may have to be made*/
        if (this.health <= 0) {

            Game.audioBuff.Play_Soma_Dead();
            Game.music[Game.levelNum - 1].FXStop();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "You died! No functioning death screen so just restart the JAR. Thanks! ~Joshua");
            System.exit(0);
            return;
            //Game.gameState = GAME_STATE.Menu;
        }
    }

    @Override
    public void render(Graphics2D g2) {
        if (this.usingMelee) {
            this.weapon.render(g2);
        }

        g2.drawImage(this.currentSprite, (int) x, (int) y, null);

        new StandardTrail(this.x, this.y, this.width, this.height, null, 0.10f, this, this.trailH, null, true);

    }

    private void initImages() throws Exception {

        this.leftImages = new BufferedImage[17];
        this.rightImages = new BufferedImage[this.leftImages.length];

        //*Loads in the stagnant images*//
        this.stillR = ImageIO.read(new File("res/sprites/soma/soma0.png"));//Still Left
        this.stillL = ImageIO.read(new File("res/sprites/soma/soma1.png"));//Still Right

        this.stillAL = ImageIO.read(new File("res/sprites/soma/s_still_a_l0.png"));//still attacking left
        this.stillAR = ImageIO.read(new File("res/sprites/soma/s_still_a_r0.png"));//still attacking right

        this.duckR = ImageIO.read(new File("res/sprites/soma/s_dr.png"));
        this.duckL = ImageIO.read(new File("res/sprites/soma/s_dl.png"));

        //Load in the jumping frames
        for (int i = 0; i < this.jumpUpLeft.length; i++) {
            this.jumpUpLeft[i] = ImageIO.read(new File("res/sprites/soma/s_j_l" + i + ".png"));
            this.jumpUpRight[i] = ImageIO.read(new File("res/sprites/soma/s_j_r" + i + ".png"));
        }

        //This loads in the walking animations
        for (int i = 0; i < this.leftImages.length; i++) {
            this.leftImages[i] = ImageIO.read(new File("res/sprites/soma/s_l" + i + ".png"));
            this.rightImages[i] = ImageIO.read(new File("res/sprites/soma/s_r" + i + ".png"));

            this.width += this.leftImages[i].getWidth() + this.rightImages[i].getWidth();
            this.height += this.leftImages[i].getHeight() + this.rightImages[i].getHeight();
        }

        //This loads in the falling animations
        for (int i = 0; i < this.fallLeft.length; i++) {
            this.fallLeft[i] = ImageIO.read(new File("res/sprites/soma/s_j_l" + (i + 5) + ".png"));
            this.fallRight[i] = ImageIO.read(new File("res/sprites/soma/s_j_r" + (i + 5) + ".png"));
        }

        //This loads in the hurt image
        for (int i = 0; i < this.hurtLeft.length; i++) {
            this.hurtLeft[i] = ImageIO.read(new File("res/sprites/soma/s_h_l0.png"));
            this.hurtRight[i] = ImageIO.read(new File("res/sprites/soma/s_h_r0.png"));
        }

        //Calculates the width and heights based off of an approximation of the walking sprites
        this.width = (this.width / (leftImages.length + rightImages.length));
        this.height = (this.height / (leftImages.length + rightImages.length));

    }

    /**
     * Initializes the animators for the object with the bufferedimage arrays
     * passed into it with the appropriate delay.
     *
     * @throws Exception possible exception if there is a nullptr, etc.
     */
    private void initAnimators() throws Exception {

        this.rights = new StandardAnimator((new ArrayList<BufferedImage>(Arrays.asList(this.rightImages))), 1 / 30d, this, StandardAnimator.PRIORITY_3RD);
        this.lefts = new StandardAnimator((new ArrayList<BufferedImage>(Arrays.asList(this.leftImages))), 1 / 30d, this, StandardAnimator.PRIORITY_3RD);
        this.jumps = new StandardAnimator((new ArrayList<BufferedImage>(Arrays.asList(this.jumpUpLeft))), 1 / 7d, this, StandardAnimator.PRIORITY_3RD);
        //this.fallsLeft = new StandardAnimator((new ArrayList<BufferedImage>(Arrays.asList(this.fallLeft))), 1/30d, this, StandardAnimator.PRIORITY_3RD);
        //this.fallsRight = new StandardAnimator((new ArrayList<BufferedImage>(Arrays.asList(this.fallRight))), 1/30d, this, StandardAnimator.PRIORITY_3RD);
        this.hurtsL = new StandardAnimator((new ArrayList<BufferedImage>(Arrays.asList(this.hurtLeft))), 1 / 5d, this, StandardAnimator.PRIORITY_3RD);
        this.hurtsR = new StandardAnimator((new ArrayList<BufferedImage>(Arrays.asList(this.hurtRight))), 1 / 5d, this, StandardAnimator.PRIORITY_3RD);
    }

    @Override
    public double getRestitution() {
        return 1.5;
    }

}
