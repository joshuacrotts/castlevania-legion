package com.finalproject.entities.projectiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.andrewmatzureff.constants.C;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardID;

public class Fireball extends StandardGameObject {

    private StandardGameObject obj;

    private static BufferedImage[] fireBalls;

    public Fireball(StandardGameObject obj, double velX, double velY) {
        super(obj.x + obj.velX, obj.y + obj.velY, StandardID.Weapon);

        Fireball.init();

        this.velX = velX;
        this.velY = velY;

        this.obj = obj;

    }

    @Override
    public void tick() {

        this.x += velX;
        this.y += velY;

        this.velX *= 1.05;
        this.velY = Math.sin((double) System.nanoTime() / C.NANO);

        this.currentSprite = (this.obj.lastDir == Direction.Right) ? Fireball.fireBalls[1] : Fireball.fireBalls[0];

    }

    @Override
    public void render(Graphics2D g2) {

        g2.drawImage(currentSprite, (int) x, (int) y, null);
    }

    private static void init() {
        fireBalls = new BufferedImage[2];
        try {
            fireBalls[0] = ImageIO.read(new File("res/sprites/weapons/fireball0_l.png"));
            fireBalls[1] = ImageIO.read(new File("res/sprites/weapons/fireball0_r.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
