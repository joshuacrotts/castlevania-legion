package com.finalproject.entities.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.finalproject.entities.Player;
import com.finalproject.main.Game;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StdOps;

/**
 * Lava class
 *
 * r == 255 g == 127 b == 1
 *
 * @author Joshua
 *
 */
public class Lava extends Block {

    private BufferedImage[] img = null;
    private StandardAnimator lava = null;

    public Lava(int x, int y, double width, double height) {
        super(x, y, width, height);

        this.img = new BufferedImage[12];

        for (int i = 0; i < this.img.length; i++) {

            try {
                this.img[i] = ImageIO.read(new File("res/sprites/obj/lava/256x128/256128_" + i + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            this.lava = new StandardAnimator(new ArrayList<BufferedImage>(Arrays.asList(img)), 1 / 8d, this, StandardAnimator.PRIORITY_3RD);
        }

    }

    public void tick() {
        this.lava.animate();
    }

    public void render(Graphics2D g2) {
        super.render(g2);

        //g2.setColor(Color.YELLOW);
        //g2.draw(this.getBounds());
    }

    public void collide(StandardGameObject sgo) {
        if (sgo.health >= 0) {

            sgo.hurtEntity(-40);

            if (sgo instanceof Player) {
                Game.audioBuff.Play_Soma_Hurt(StdOps.rand(0, 3));
            }

        }
    }

}
