package com.finalproject.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardHandler;
import com.joshuacrotts.standards.StandardID;
import com.joshuacrotts.standards.StandardParticleHandler;

public class BoxParticle extends StandardGameObject{

	private StandardParticleHandler sph;

	public static StandardHandler trailH = new StandardHandler();
	
	private Color c;
	
	public BoxParticle(double x, double y, double dimension, double velocity, Color c, double life, StandardParticleHandler sph){
		super(x, y, StandardID.Particle);
		
		this.width = (int) dimension;
		this.height = this.width;
		
		this.setSph(sph);
		this.c = c;
		
		this.velX = velocity;
		this.velY = this.velX;
	}

	@Override
	public void tick() {
		this.x += velX;
		this.y += velY;
		
	}
	

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(this.c);
		g2.fillRect((int) x, (int) y, (int) width, (int) height);
		
		//new StandardTrail(this.x, this.y, this.width, this.health, Color.white, 1000000f, this, this.trailH, "Square", false);
	}

	public StandardParticleHandler getSph() {
		return sph;
	}

	public void setSph(StandardParticleHandler sph) {
		this.sph = sph;
	}
	
	
}
