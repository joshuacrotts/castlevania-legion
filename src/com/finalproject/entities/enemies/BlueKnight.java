package com.finalproject.entities.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.finalproject.entities.Coin;
import com.finalproject.entities.Player;
import com.finalproject.main.Game;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardCollisionHandler;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;
import com.joshuacrotts.standards.StdOps;

public class BlueKnight extends Enemy {

    //private BufferedImage stillL; private BufferedImage stillR;
    public BlueKnight(double x, double y, StandardHandler sh, Player player) {
        super(x, y, sh, player);

        this.setInitialHealth(45);

        this.velX = -2;
    }

    public void tick() {
        if (this.health > 0) {
            this.x += (int) this.velX;
            this.y += (int) this.velY;
            this.velY = (this.velY + StandardGameObject.gravity);

            double dif = player.x - this.x;

            if (Math.abs(this.player.x - this.x) > 500) {
                dif = 0;
            }

            this.velX = dif * 0.003 + Math.signum(dif);

            if (this.velX < 0) {
                this.lefts.animate();
            } else {
                this.rights.animate();
            }
        }

        this.checkDeath();

        this.dropCoins();

        if (this.deathParticles != null || (this.deathParticles != null && this.deathParticles.size() == 0)) {

            //System.out.println(this.deathParticles.size());
            this.deathParticles.tick();

        }

        if (this.y > 1000) {
            this.sh.removeEntity(this);
        }
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        if (this.health > 0) {
            g2.drawImage(this.currentSprite, (int) x, (int) y, null);
        }

        if (this.deathParticles != null) {
            this.deathParticles.render(g2);
        }
    }

    @Override
    void initImages() {

        this.leftImages = new BufferedImage[4];
        this.rightImages = new BufferedImage[this.leftImages.length];

        try {
            ImageIO.read(new File("res/sprites/knight0/kn_s_l.png"));
            ImageIO.read(new File("res/sprites/knight0/kn_s_r.png"));

            for (int i = 0; i < leftImages.length; i++) {
                this.leftImages[i] = ImageIO.read(new File("res/sprites/knight0/kn_l" + i + ".png"));
                this.rightImages[i] = ImageIO.read(new File("res/sprites/knight0/kn_r" + i + ".png"));

                this.width += this.leftImages[i].getWidth() + this.rightImages[i].getWidth();
                this.height += this.leftImages[i].getHeight() + this.rightImages[i].getHeight();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.width = (int) (this.width / (this.leftImages.length + this.rightImages.length));
        this.height = (int) (this.height / (this.leftImages.length + this.rightImages.length));
    }

    @Override
    void initAnimators() {
        this.lefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.leftImages)), 1 / 8d, this, StandardAnimator.PRIORITY_3RD);
        this.rights = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.rightImages)), 1 / 8d, this, StandardAnimator.PRIORITY_3RD);
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
