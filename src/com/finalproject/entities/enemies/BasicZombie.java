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

public class BasicZombie extends Enemy {
    
    public BasicZombie(double x, double y, StandardHandler sh, Player player) {
        super(x, y, sh, player);
        
        this.setInitialHealth(5);

        //this.velX = -1;
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
            
            this.velX = dif * 0.005 + Math.signum(dif);
            
            if (this.velX < 0) {
                this.lefts.animate();
            } else {
                this.rights.animate();
            }
        }
        
        this.checkDeath();
        
        this.dropCoins();
        
        if (this.deathParticles != null) {
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
            this.deathParticles.render(g2);
        }
    }
    
    @Override
    void initImages() throws Exception {
        this.leftImages = new BufferedImage[4];
        this.rightImages = new BufferedImage[4];
        
        for (int i = 0; i < this.leftImages.length; i++) {
            this.leftImages[i] = ImageIO.read(new File("res/sprites/zombie0/zo_l_" + i + ".png"));
            this.rightImages[i] = ImageIO.read(new File("res/sprites/zombie0/zo_r_" + i + ".png"));
            
            this.width += this.leftImages[i].getWidth() + this.rightImages[i].getWidth();
            this.height += this.leftImages[i].getHeight() + this.rightImages[i].getHeight();
        }
        
        this.width = (this.width / (leftImages.length + rightImages.length));
        this.height = (this.height / (leftImages.length + rightImages.length));
    }
    
    @Override
    void initAnimators() throws Exception {
        this.lefts = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.leftImages)), 1 / 8d, this, StandardAnimator.PRIORITY_3RD);
        this.rights = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(this.rightImages)), 1 / 8d, this, StandardAnimator.PRIORITY_3RD);
    }
    
    public void collide(StandardGameObject sgo) {
        if (sgo instanceof Player && sgo.health >= 0) {
            
            sgo.hurtEntity(-15);
            Game.audioBuff.Play_Soma_Hurt(StdOps.rand(0, 3));
            
        }
    }
    
    private void dropCoins() {
        if (this.health < 0) {
            
            int amt = StdOps.rand(0, 2);
            
            if (this.fdp) {
                for (int i = 0; i < amt; i++) {
                    this.sh.addEntity(new Coin(this.x, this.y, (byte) StdOps.rand(0, 40), this.player, (StandardCollisionHandler) this.sh));
                }
            }
            this.fdp = false;
        }
    }
}
