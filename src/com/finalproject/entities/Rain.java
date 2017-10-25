package com.finalproject.entities;

import com.joshuacrotts.standards.*;
/**
 * Write a description of class Rain here.
 * 
 * @author (Andrew Matzureff) 
 * @version (5/23/2017)
 */
public class Rain extends StandardGameObject
{
    public StandardParticleHandler stormcloud;
    private StandardCamera camera;
    public Rain(StandardCamera camera, int maxRainDrops)
    {
        super(camera.x, camera.y, StandardID.Object);
        
        this.camera = camera;
        
        this.stormcloud = new StandardParticleHandler(maxRainDrops);
    }
    public void tick()
    {
        x = camera.x - this.camera.vpw;
        y = camera.y - this.camera.vph;
        stormcloud.tick();
        stormcloud.addEntity(new RainDrop(Math.random() * this.camera.vpw * 4 + this.x - this.camera.vpw, y, -Math.PI * 1.5, Math.random() * 5, (int) (y + this.camera.vph * 2)));
    }
    public void render(java.awt.Graphics2D g2)
    {
    	stormcloud.render(g2);
    }
}
