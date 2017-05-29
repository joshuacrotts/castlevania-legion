package com.finalproject.commands;

import com.andrewmatzureff.commands.Command;
import com.finalproject.main.Game;
import com.joshuacrotts.standards.StandardAnimator;
import com.joshuacrotts.standards.StandardGameObject;

public class Jump extends Command{

	public StandardGameObject player;
	public StandardAnimator jump; //Jump animation
	public double height = 0;
//	private boolean jumping = false;
	public Jump(StandardGameObject obj, StandardAnimator j, double height){
		this.player = obj;
		this.jump = j;
		this.height = height;
	}
	public void update()
	{
		if(player.jumping)
		{
			if(this.jump.animate())
			{
				this.jump.setAnimating(false);
				player.jumping = false;
			}
		}//else
		//if(!player.standing && player.velY > 5)fall.animate();
	}
	public void execute(){
		if(player.standing)
		{
			Game.audioBuff.Play_Soma_Jump();
			player.velY += height;
			if(this.jump != null){
			    this.jump.reset();
				this.jump.setAnimating(true);
				this.jump.animate();
			}
			player.jumping = true;
		}
		//if((player.velY > height))//<height, upward or 0 velocity,
		//{
		//    player.velY += height;
		/*if(height * Math.signum(maxHeight) <= maxHeight * Math.signum(maxHeight))
            {
                height--;
            }else
            if(height < 0)
            {

            }*/
		//}
		//lastV = player.velY;

		//if(this.jump != null && jumping){
		//    this.jump.animate();
		//}
	}
}
