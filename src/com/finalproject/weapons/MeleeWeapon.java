package com.finalproject.weapons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.finalproject.entities.Player;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;

public abstract class MeleeWeapon extends StandardGameObject {

    //Player instance
    public Player player;
    //private StandardHandler trailH;
    private StandardHandler enemyHandler;

    //Images
    public ArrayList<BufferedImage> leftWeapons = null;
    public StandardAnimator lefts = null;
    public ArrayList<BufferedImage> rightWeapons = null;
    public StandardAnimator rights = null;

    //Determines if it's active or not
    public boolean active = false;

    //Amt of damage the weapon does
    public double damage;

    public MeleeWeapon(double dmg, Player player, StandardHandler enemyHandler) {
        super(0, 0, StandardID.Weapon);

        this.player = player;
        this.damage = dmg;
        //this.trailH = new StandardHandler();
        this.enemyHandler = enemyHandler;

        if (this.player.lastDir == Direction.Left) {
            this.x = this.player.x;
            this.y = this.player.y + this.player.height / 2;
        } else {
            this.x = this.player.x + this.player.width;
            this.y = this.player.y + this.player.height / 2;
        }

        this.initImages();

        for (int i = 0; i < leftWeapons.size(); i++) {
            this.width += leftWeapons.get(i).getWidth() + rightWeapons.get(i).getWidth();
            this.height += leftWeapons.get(i).getHeight() + rightWeapons.get(i).getHeight();
        }

        this.width = (int) (this.width / (leftWeapons.size() + rightWeapons.size()));
        this.height = (int) (this.height / (leftWeapons.size() + rightWeapons.size()));
    }

    /**
     * Make SURE you add this method in your subclass; it needs to initialize
     * and load in the images for the image.
     *
     * It also HAS to initialize the StandardAnimations. PASS IN THE OBJECT THAT
     * IT IS; NOT THE PLAYER. Made that mistake myself lol
     */
    public abstract void initImages();

    public void tick() {
        //Loop through the handler and detect a collision
        //Sure, runs at O(n) but what else do we have to work with?
        for (int i = 0; i < enemyHandler.size(); i++) {
            if (this.getBounds().intersects(enemyHandler.get(i).getBounds())
                    && enemyHandler.get(i).getId() != StandardID.Player
                    && enemyHandler.get(i).getId() == StandardID.Enemy) {//You HAVE to check to make sure the player doesn't hit himself!
                enemyHandler.get(i).health -= this.damage;
                enemyHandler.get(i).hurt = true;
                //Game.bloodHandler.addEntity(new Blood(enemyHandler.get(i).x, enemyHandler.get(i).y, enemyHandler.get(i)));
            } else {
                enemyHandler.get(i).hurt = false;
            }
        }

        /**
         * Clause for if they're using their MELEE WEAPON!
         */
        if (this.player.usingMelee) {
            if (this.player.lastDir == Direction.Left) {
                if (lefts.getFrame() != lefts.images.size() - 1) {
                    lefts.animate();
                } else {
                    this.lefts.setFrame(0);
                    this.player.usingMelee = false;
                    return;

                }
            } else {
                if (rights.getFrame() != rights.images.size() - 1) {
                    rights.animate();
                } else {
                    this.rights.setFrame(0);

                    this.player.usingMelee = false;
                    return;

                }
            }

        }
    }

    public void render(Graphics2D g2) {
        g2.drawImage(this.currentSprite, (int) x, (int) y, null);
    }
}
