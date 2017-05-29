package com.finalproject.commands;

import com.andrewmatzureff.commands.Command;
import com.finalproject.main.Game;

public class Pause extends Command{

	private Game game;
	
	public Pause(Game game){
		this.game = game;
	}
	
	@Override
	public void execute() {

		if(this.game.paused){
			this.game.paused = false;
		}else{
			this.game.paused = true;
		}
		
	}

}
