package com.finalproject.entities.projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.andrewmatzureff.constants.C;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;

/**
 * Just a Cross projectile; use C to do.
 *
 * KEEP IN MIND: MELEE WEAPONS USE THE player.usingMelee.
 *
 * Cross uses sgo.attacking**********************
 *
 * @author Joshua
 *
 */
public class Cross extends StandardGameObject {

    public int damage = 1;
    public boolean reverse = false;
    public StandardGameObject origin;
    public int collisions = 0;
    private StandardHandler handler;

    public Cross(StandardGameObject obj, double velX, double velY, StandardHandler handler) {
        super(obj.x + obj.velX, obj.y + obj.velY, StandardID.Projectile);

        this.origin = obj;
        this.handler = handler;
        this.ignore = obj.getId();
        this.init();

        this.velX = velX;
        this.velY = velY;

    }

    @Override
    public void tick() {

        if (Math.signum(velX) == 1) {
            this.rights.animate();
        } else {
            this.lefts.animate();
        }

        this.x += velX;
        this.y += velY;
        double difx = origin.x - this.x;
        double dify = origin.y - this.y;
        if (!reverse) {

            this.velX *= 1.05;

            this.velY = Math.cos((double) System.nanoTime() / C.NANO);

            reverse = (Math.abs(difx) > 1000) || collisions > 5 || Math.abs(velX) < 1;

            return;
        }
        this.velX = (difx) * 0.25 + Math.signum(difx);
        this.velY = (dify) * 0.25 + Math.signum(dify);
    }

    @Override
    public void render(Graphics2D g2) {

        g2.drawImage(currentSprite, (int) x, (int) y, null);
    }

    private void init() {
        leftImages = new BufferedImage[12];
        rightImages = new BufferedImage[leftImages.length];
        try {
            for (int i = 0; i < leftImages.length; i++) {
                leftImages[i] = ImageIO.read(new File("res/sprites/weapons/cross/cross_l" + i + ".png"));
                rightImages[i] = ImageIO.read(new File("res/sprites/weapons/cross/cross_l" + i + ".png"));

                this.width += this.leftImages[i].getWidth() + this.rightImages[i].getWidth();
                this.height += this.leftImages[i].getHeight() + this.rightImages[i].getHeight();
            }

            this.width = (this.width / (leftImages.length + rightImages.length));
            this.height = (this.height / (leftImages.length + rightImages.length));
        } catch (Exception e) {
            e.printStackTrace();
        }

        lefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(leftImages)), 1 / 50d, this, StandardAnimator.PRIORITY_3RD);
        rights = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(rightImages)), 1 / 50d, this, StandardAnimator.PRIORITY_3RD);
    }

    public void collide(StandardGameObject sgo) {
        if (sgo.getId() == this.ignore) {
            //if(reverse)
            //{
            handler.removeEntity(this);
            //}else
            return;
        } else {

            sgo.health -= damage;
            collisions++;
            //reverse = true;

        }
    }
}
