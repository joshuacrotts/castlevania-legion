package com.finalproject.commands;

import com.andrewmatzureff.commands.Command;
import com.andrewmatzureff.constants.C;
import com.finalproject.entities.projectiles.Cross;
import com.finalproject.main.Game;
import com.joshuacrotts.standards.StandardCollisionHandler;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardGameObject.Direction;

public class Secondary extends Command{

	private StandardCollisionHandler sch;
	private StandardGameObject o;
	private long time = 0;
	
	public Secondary(StandardGameObject o, StandardCollisionHandler enemyHandler){
		this.sch = enemyHandler;
		this.o = o;
	}
	
	@Override
	public void execute() {
		
		long current = System.nanoTime();
		long interval = current - time;
		if(interval - C.NANO < 0) return;
		else{
			
		}
		time = current;
		
		o.attacking = true;
		Game.audioBuff.Play_Soma_Attack();
		
		if(this.o.lastDir == Direction.Right){
			this.sch.addEntity(new Cross(o, 5f, 0f, this.sch));
		}
		else{
			this.sch.addEntity(new Cross(o, -5f, 0f, this.sch));
		}
		
	}

	
}
