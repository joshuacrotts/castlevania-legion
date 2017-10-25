package com.finalproject.entities.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;

public class Eagle extends StandardGameObject {

    //Constant sprites
    //private BufferedImage stillL; private BufferedImage stillR;
    //Global instance variables
    private StandardHandler sh;

    public Eagle(double x, double y, StandardHandler sh) {
        super(x, y, StandardID.Enemy);
        this.sh = sh;

        try {
            this.initImages();
            this.initAnimators();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //this.currentSprite = stillL;
        this.velX = -2;
    }

    public void tick() {
        this.x += (int) this.velX;

        if (this.velX < 0) {
            this.lefts.animate();
        } else {
            this.rights.animate();
        }

        if (this.y > 1000 || (this.deathParticles != null && this.deathParticles.size() == 0)) {
            this.sh.removeEntity(this);
        }
    }

    public void render(Graphics2D g2) {
        g2.drawImage(this.currentSprite, (int) x, (int) y, null);
    }

    private void initImages() {

        this.leftImages = new BufferedImage[3];
        this.rightImages = new BufferedImage[this.leftImages.length];

        try {
            //this.stillL = ImageIO.read(new File("res/sprites/eagle0/eagle_l0.png"));
            //this.stillR = ImageIO.read(new File("res/sprites/eagle0/eagle_r0.png"));

            //Loads in the left and right images for walking, as well as the attacking left & rights
            for (int i = 0; i < leftImages.length; i++) {
                this.leftImages[i] = ImageIO.read(new File("res/sprites/eagle0/eagle_smith_l" + i + ".png"));
                this.rightImages[i] = ImageIO.read(new File("res/sprites/eagle0/eagle_smith_r" + i + ".png"));

                this.width += this.leftImages[i].getWidth() + this.rightImages[i].getWidth();
                this.height += this.leftImages[i].getHeight() + this.rightImages[i].getHeight();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.width = (int) (this.width / (this.leftImages.length + this.rightImages.length));
        this.height = (int) (this.height / (this.leftImages.length + this.rightImages.length));
    }

    private void initAnimators() {
        this.lefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.leftImages)), 1 / 10d, this, StandardAnimator.PRIORITY_3RD);
        this.rights = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.rightImages)), 1 / 10d, this, StandardAnimator.PRIORITY_3RD);
    }
}
