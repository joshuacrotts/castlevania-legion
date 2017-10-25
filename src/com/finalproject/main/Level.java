package com.finalproject.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.finalproject.entities.Player;
import com.finalproject.entities.enemies.BasicZombie;
import com.finalproject.entities.enemies.BlueKnight;
import com.finalproject.entities.enemies.Eagle;
import com.finalproject.entities.enemies.NewJulius;
import com.finalproject.entities.enemies.OldJulius;
import com.finalproject.entities.enemies.Richter;
import com.finalproject.entities.enemies.Simon;
import com.finalproject.entities.enemies.Skeleton;
import com.finalproject.entities.enemies.SwordMan;
import com.finalproject.entities.objects.Block;
import com.finalproject.entities.objects.Candle;
import com.finalproject.entities.objects.Lava;
import com.finalproject.entities.objects.Torch;
import com.finalproject.entities.objects.Tree;
import com.joshuacrotts.standards.StandardCollisionHandler;
import com.joshuacrotts.standards.StandardLevel;

public class Level extends StandardLevel{

	private Player player;
	private BufferedImage img;
	private BufferedImage img2;
	
	public boolean complete = false;

	public Level(String fileLocation, String bgImagePath, String bgImagePath2, StandardCollisionHandler stdHandler, Player player) {
		super(fileLocation, bgImagePath, stdHandler);

		this.player = player;

		try {	
				this.img = ImageIO.read(new File(bgImagePath));
				this.img2 = ImageIO.read(new File(bgImagePath2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.loadLevelData();
	}

	@Override
	public void loadLevelData() {

		int w = this.getLevelData().getWidth();
		int h = this.getLevelData().getHeight();

		for(int x = 0; x < w; x++){
			for(int y = 0; y < h; y++){

				int pixel = this.getLevelData().getRGB(x, y);

				int r = ((pixel >> 16) & 0xff);
				int g = ((pixel >> 8) & 0xff);
				int b = ((pixel) & 0xff);

				if(r == 255 && g == 0 && b == 255){
					continue;
				}
				
				if(r == 0 && g == 0 && b == 0){
					continue;
				}
				
				/**ENEMY TESTING
				 * 
				 * If the red pixel value is 0, it will be a skeleton for now.
				 * Table:
				 * 0 - Skeleton
				 * 1 - Richter
				 * 2 - Blue Knight
				 * 3 - Old Julius
				 * 4 - New Julius
				 * 5 - Simona
				 * 6 - Eagle
				 * 7 - Basic Zombie
				 * 8 - Sword Man
				 */
				
				//System.out.println(r + "\t"+g+"\t"+"\t"+b);
				
				if(r <= 30 && g == 255 && b == 0){
					/**Switch case blocks hate me**/
					
					//System.out.println(r);
					if(r == 0)
						this.stdHandler.addEntity(new Skeleton(x * 32, y * 32, this.stdHandler, this.player));
					if(r == 1)
						this.stdHandler.addEntity(new Richter(x * 32, y * 32,  this.stdHandler, this.player));
					if(r == 2)
						this.stdHandler.addEntity(new BlueKnight(x * 32, y * 32,  this.stdHandler, this.player));
					if(r == 3)
						this.stdHandler.addEntity(new OldJulius(x * 32, y * 32,  this.stdHandler, this.player)); 
					if(r == 4)
						this.stdHandler.addEntity(new NewJulius(x * 32, y * 32,  this.stdHandler, this.player));
					if(r == 5)
						this.stdHandler.addEntity(new Simon(x * 32, y * 32,  this.stdHandler, this.player)); 
					if(r == 6)
						this.stdHandler.addEntity(new Eagle(x * 32, y * 32, this.stdHandler));
					if(r == 7)
						this.stdHandler.addEntity(new BasicZombie(x * 32, y * 32,this.stdHandler ,this.player)); 
					if(r == 8)this.stdHandler.addEntity(new SwordMan(x * 32, y * 32,this.stdHandler ,this.player)); 
					
				}

				/**OBJECT TESTING*
				 * Testing r values once again
				 * 86 - S Blue Torch
				 * 87 - S Red Torch
				 * 88 - S Green Torch
				 * 89 - NULL (nonworking sprite)
				 * 90 - Standard Torch 0
				 * 91 - Torch 1 (Candle class)
				 * 
				 * */
				if(r>=86 && r <=170 && g == 255 && b == 255){
					

					//Adds a candle
					if(r == 91){
						System.out.println("DOES THIS SHIT HAPPEN EVENR!?!?!?");
						this.stdHandler.addEntity(new Candle(x * 32, y * 32)); 
					}
					
					//Adds a torch
					else if(r >= 86 && r <= 90){
						this.stdHandler.addEntity(new Torch(x * 32, y * 32, r));
					}
					
					//break loop;

				}


				/** BRICK TESTING**
				/* When r >= 230, we reserve g >= 100 && b >= 100 for bricks.
				/* G will represent y values, b will represent x values.
				/*/
				if(r >= 230 && g >= 100 && b >= 100){
					//	System.err.println("R: "+r+" G: "+g+" B: "+b);



					//Special case for when the brick is only 1 block wide.
					if(255 - b == 0 && g - 255 == 0){
						this.stdHandler.addEntity(new Block(x * 32, y * 32, 32 , 32, r));
					}else{
						//Special case for when the brick is only 1 block tall.
						if(255 - b == 0)
							this.stdHandler.addEntity(new Block(x * 32, y * 32, 32, 32 * (255 - g), r));
						//Special case for when the brick is only 1 block wide.
						else if(255 - g == 0)
							this.stdHandler.addEntity(new Block(x * 32, y * 32, 32 * (255 - b), 32, r));
						else
							this.stdHandler.addEntity(new Block(x * 32, y * 32, 32 * (255 - b), 32 * (255 - g), r));
					}
					
					//break loop;
				}

				/**ETC TESTING
				 * 
				 * R will be between 30 and 40, determining block.
				 * 
				 * b == 31 is Lava
				 * b == 32 128
				 * b == 33 64 etc 
				 */
				if(r > 30 && r <= 40 && g == 127 && b == 0){
					switch(r){

					case 31: this.stdHandler.addEntity(new Lava(x * 32, y * 32, 1248 , 32)); break;

					}
					//break loop;
				}

				/**
				 * NATURE TESTING
				 */
				if(r == 0 && g < 10 && b == 255){
					this.stdHandler.addEntity(new Tree(x * 32, y * 32, g));
					
					//break loop;
				}

			}
		}

	}

	int x = 0;
	int xx = 0;
        
        double x_modifier = 0.01;
        double xX_modifier = 0.02;

	@Override
	public void render(Graphics2D g2) {

		if(this.img != null && this.img2 != null){

			x -= (int) this.player.velX * x_modifier;
			xx -= (int) this.player.velX * xX_modifier;
			if(this.x <= 0){
				g2.drawImage(this.img, x, 0, null);
			}else{
				g2.drawImage(this.img, 0, 0, null);
			}
		
			if(this.xx <= 0){
				g2.drawImage(this.img2, xx, 0, null);
			}else{
				g2.drawImage(this.img2, 0, 0, null);
			}

		}
	}


	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
