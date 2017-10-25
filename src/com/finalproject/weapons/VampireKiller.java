package com.finalproject.weapons;

import java.awt.Rectangle;

import com.andrewmatzureff.constants.C;
import com.finalproject.entities.Blood;
import com.finalproject.entities.Player;
import com.finalproject.entities.enemies.OldJulius;
import com.finalproject.entities.enemies.Richter;
import com.finalproject.entities.objects.Weapon;
import com.finalproject.main.Game;
import com.joshuacrotts.standards.StandardCollisionHandler;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StdOps;

public class VampireKiller extends Weapon
{
	public double damage;
	public Rectangle bounds;
	public boolean active = false;
	private long time = 0;
	private long delay = (long)(0.25 * C.NANO);

	public VampireKiller(StandardCollisionHandler stdHandler, double damage, Rectangle bounds)
	{
		this.damage = damage;
		this.bounds = bounds;
	}
	public VampireKiller(StandardCollisionHandler stdHandler, double damage, Rectangle bounds, double delay)
	{
		this.damage = damage;
		this.bounds = bounds;
		this.delay = (long)(delay * C.NANO);
	}
	@Override
	public void attack(StandardGameObject user, Player target)
	{
		long current = System.nanoTime();
		long interval = current - time;
		//active = false;
		if(interval - delay < 0 && !active) return;
		time = current;

		if(Math.abs(user.x - target.x) < bounds.width){

			boolean endFrame = false;

			if(target.x >= user.x){
				user.lastDir = StandardGameObject.Direction.Right;
				endFrame = user.aRights.animate();
			}else{
				user.lastDir = StandardGameObject.Direction.Left;
				endFrame = user.aLefts.animate();
			}



			active = true;

			if(!endFrame)
				return;
			//else active = false;
		}else{
			active = false;
			return;
		}

		if(user instanceof Richter || user instanceof OldJulius){
			Game.audioBuff.Play_Richter_SFX(StdOps.rand(0, 5));
		}

		Game.audioBuff.Play_Whip();
		int xoffset = bounds.x;
		int yoffset = bounds.y;
		Rectangle userBounds = user.getBounds();

		bounds.y += userBounds.y;
		if(user.lastDir == StandardGameObject.Direction.Right)
		{
			bounds.x += userBounds.x;
		}else//left
		{
			bounds.x = userBounds.x + userBounds.width - bounds.x - bounds.width;
		}

		if(bounds.intersects(target.getBounds()))
		{
			Game.bloodHandler.addEntity(new Blood(target.x, target.y, target));
			//System.err.println(Math.round(this.damage * 100) / 100);
			target.hurt = true;
			target.health -= damage;
		}else{
			target.hurt = false;
		}
		bounds.x = xoffset;
		bounds.y = yoffset;
		active = false;

		if(!target.hurt)
		{//System.out.println("no hurt");
			if(user.lastDir == StandardGameObject.Direction.Left)
				user.velX += 2;
			else
				user.velX -= 2;
		}
	}

}
