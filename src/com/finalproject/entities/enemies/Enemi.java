/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finalproject.entities.enemies;

import com.finalproject.entities.Player;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Alisson
 */
public abstract class Enemi extends StandardGameObject {

    protected Player player;
    protected StandardHandler sh;
    protected int maxHealth;

    public Enemi(double x, double y, StandardHandler sh, Player player) {
        super(x, y, StandardID.Enemy);

        this.player = player;
        this.sh = sh;

        try {
            this.initImages();
            this.initAnimators();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setInitialHealth(int h) {
        this.health = h;
        this.maxHealth = h;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect((int) this.x, (int) this.y, this.width, 3);
        g2.setColor(Color.GREEN);
        g2.fillRect((int) this.x, (int) this.y, (int) (this.width / maxHealth * health), 3);
    }

    abstract void initImages() throws Exception;

    abstract void initAnimators() throws Exception;
}
