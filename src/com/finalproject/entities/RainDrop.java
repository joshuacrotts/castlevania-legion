package com.finalproject.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardID;
import com.joshuacrotts.standards.StdOps;

/**
 * Typical Rain Drop for the Rain effect.
 *
 * @author Andrew Matzureff
 * @version (5/23/2017)
 */
public class RainDrop extends StandardGameObject {

    public static int alpha = 255 << 24;
    public static int red = 255 << 16;
    public static int green = 255 << 8;
    public static int blue = 255;
    public int vanish;

    public RainDrop(double x, double y, double direction, double speed, int vanish) {
        super(x, y, StandardID.Particle);
        this.velX = speed * Math.sin(direction); //Solve for horizontal leg of right triangle formed by velocity vector
        this.y = speed * Math.cos(direction);
        this.vanish = vanish;
    }

    public void tick() {

        if (this.y > vanish) {
            this.alive = false;
        }

        this.x += this.velX;
        this.y += this.velY;

        this.velY += StandardGameObject.gravity;

        x += velX;
        y += velY;
    }

    public void render(Graphics2D g2) {
        int rg = 100;

        g2.setColor(new Color(rg, rg, StdOps.rand(rg, 255)));

        g2.drawLine((int) x, (int) y, (int) (x - velX * 2), (int) (y - velY * 2));
    }
}
