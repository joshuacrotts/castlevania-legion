package com.finalproject.entities.enemies;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.finalproject.entities.Coin;
import com.finalproject.entities.Player;
import com.finalproject.main.Game;
import com.finalproject.weapons.VampireKiller;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardCollisionHandler;
import com.joshuacrotts.standards.StandardDraw;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;
import com.joshuacrotts.standards.StdOps;
import com.joshuacrotts.standards.StandardGameObject.Direction;

public class OldJulius extends Enemy {
    //Constant sprites

    @SuppressWarnings("unused")
    private BufferedImage stillL;
    @SuppressWarnings("unused")
    private BufferedImage stillR;

    //Global instance variables
    private VampireKiller whip;

    private double dist = 0;

    public OldJulius(double x, double y, StandardHandler sh, Player player) {
        super(x, y, sh, player);

        this.whip = new VampireKiller((StandardCollisionHandler) this.sh, 50f, new Rectangle((int) 54, (int) (25), 140, 20));
        setInitialHealth(20);
        this.velX = -2;
    }

    public void tick() {
        if (this.health > 0) {

            this.x += this.velX;
            this.y += (int) this.velY;
            whip.attack(this, player);

            double dx = Math.abs(x - player.x);
            double dy = Math.abs(y - player.y);
            double lastDist = dist;

            this.dist = (dx > dy) ? dx : dy;

            this.velX = (player.x - this.x) * /*Math.random() * 0.01 +*/ 0.01;
            this.velY = (this.velY + StandardGameObject.gravity);

            if (Math.abs(this.player.x - this.x) > 500) {
                velX = 0;
            }

            if (dist >= lastDist && standing && Math.random() < 0.1) {
                velY -= 5;
            }

            if (this.whip.active) {
                this.velX = 0;
            }

            if (this.velX < 0) {
                lastDir = Direction.Left;
                this.lefts.animate();
            } else if (this.velX > 0) {
                lastDir = Direction.Right;
                this.rights.animate();
            }

            //Clause for if they're hurt
            if (this.hurt) {

                //If they're facing right, they'll fly left when hurt
                if (this.lastDir == Direction.Right) {
                    this.velX = -20f;
                } //If they're facing left, they'll fly right when flying
                else {
                    this.velX = 20f;
                }
                //Either way, when hurt, they'll fly up and back. ******NEEDS FIXING******
                //this.jump.execute();
            } else {

            }

            this.hurt = false; //Has to be set here so they won't continuously fly back.
        }

        this.checkDeath();

        this.dropCoins();

        if (this.deathParticles != null || (this.deathParticles != null && this.deathParticles.size() == 0)) {
            this.deathParticles.tick();
        }

        if (this.y > 1000 || (this.deathParticles != null && this.deathParticles.size() == 0)) {
            this.sh.removeEntity(this);
        }
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        if (this.health > 0) {
            double xo = (this.lastDir == Direction.Left) ? currentSprite.getWidth() - this.width : 0;
            g2.drawImage(this.currentSprite, (int) (x - xo), (int) y, null);
        }

        if (this.deathParticles != null) {
            StandardDraw.Handler(this.deathParticles);
        }

    }

    @Override
    void initImages() {

        this.leftImages = new BufferedImage[10];
        this.rightImages = new BufferedImage[this.leftImages.length];
        this.attackLeftImages = new BufferedImage[15];
        this.attackRightImages = new BufferedImage[this.attackLeftImages.length];

        try {
            this.stillL = ImageIO.read(new File("res/sprites/oldjulius/oj_s_l.png"));
            this.stillR = ImageIO.read(new File("res/sprites/oldjulius/oj_s_r.png"));

            //Load in the left & right walking imgs
            for (int i = 0; i < leftImages.length; i++) {
                this.leftImages[i] = ImageIO.read(new File("res/sprites/oldjulius/oj_l" + i + ".png"));
                this.rightImages[i] = ImageIO.read(new File("res/sprites/oldjulius/oj_r" + i + ".png"));

                this.width += this.leftImages[i].getWidth() + this.rightImages[i].getWidth();
                this.height += this.leftImages[i].getHeight() + this.rightImages[i].getHeight();
            }

            //Load in the left & right attack images
            for (int i = 0; i < attackLeftImages.length; i++) {
                this.attackLeftImages[i] = ImageIO.read(new File("res/sprites/oldjulius/oj_a_l" + i + ".png"));
                this.attackRightImages[i] = ImageIO.read(new File("res/sprites/oldjulius/oj_a_r" + i + ".png"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.width = (int) (this.width / (this.leftImages.length + this.rightImages.length));
        this.height = (int) (this.height / (this.leftImages.length + this.rightImages.length));
    }

    @Override
    void initAnimators() {
        this.lefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.leftImages)), 1 / 16d, this, StandardAnimator.PRIORITY_3RD);
        this.rights = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.rightImages)), 1 / 16d, this, StandardAnimator.PRIORITY_3RD);
        this.aLefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.attackLeftImages)), 1 / 16d, this, StandardAnimator.PRIORITY_3RD);
        this.aRights = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.attackRightImages)), 1 / 16d, this, StandardAnimator.PRIORITY_3RD);
    }

    public void collide(StandardGameObject sgo) {
        if (sgo instanceof Player && sgo.health >= 0) {

            sgo.hurtEntity(-10);
            Game.audioBuff.Play_Soma_Hurt(StdOps.rand(0, 3));

        }
    }

    private void dropCoins() {
        if (this.health < 0) {

            int amt = StdOps.rand(0, 5);

            if (this.fdp) {
                for (int i = 0; i < amt; i++) {
                    this.sh.addEntity(new Coin(this.x, this.y, (byte) StdOps.rand(0, 40), this.player, (StandardCollisionHandler) this.sh));
                }
            }
            this.fdp = false;
        }
    }
}
