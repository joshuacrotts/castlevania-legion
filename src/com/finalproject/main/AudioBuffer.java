package com.finalproject.main;

import com.joshuacrotts.standards.StandardAudio;

/**
 * This class will be an abstract class that will have references to
 * a bunch of StandardAudio buffer arrays that many classes will call;
 * it's so multiple audio files can be called at the same time and not
 * have them interlace each other/overlap/cut each other off.
 * 
 * The buffers will automatically choose the most available sfx in the array,
 * and play it. When the sfx is over, it will be turned "off", and available to use.
 * 
 * @author Joshua
 *
 */
public class AudioBuffer {

	//These numbers may need to be lowered to conserve memory perhaps...
	private static StandardAudio[] BUTTON_BUFFER;
	private static StandardAudio[] WHIP_BUFFER;
	public static StandardAudio[] SWORD_BUFFER;
	public static StandardAudio[] RICHTER_SFX;
	public static StandardAudio[] SOMA_JUMP_BUFFER;
	public static StandardAudio[] SOMA_HURT_BUFFER;
	public static StandardAudio SOMA_DEAD;
	public static StandardAudio[] SOMA_ATTACK;
	public static StandardAudio[] COIN_SFX;


	public AudioBuffer(){

		AudioBuffer.BUTTON_BUFFER = new StandardAudio[2];
		AudioBuffer.WHIP_BUFFER = new StandardAudio[10];
		AudioBuffer.SWORD_BUFFER = new StandardAudio[1];
		AudioBuffer.RICHTER_SFX = new StandardAudio[6];
		AudioBuffer.SOMA_JUMP_BUFFER = new StandardAudio[2];
		AudioBuffer.SOMA_HURT_BUFFER = new StandardAudio[4];
		AudioBuffer.SOMA_ATTACK = new StandardAudio[2];
		AudioBuffer.SOMA_DEAD = new StandardAudio("res/audio/sfx/soma_dead.wav");
		AudioBuffer.COIN_SFX = new StandardAudio[10];

		initAudioBuffers();
	}

	/**
	 * This method MUST be called to initialize the sound effect buffers!
	 */
	public static void initAudioBuffers(){
		//Inits the button buffers & soma's jump buffers (length of 2)
		for(int i = 0; i < BUTTON_BUFFER.length; i++){
			BUTTON_BUFFER[i] = new StandardAudio("res/audio/sfx/menuselect.wav");
		}

		//Inits the whip buffers
		for(int i = 0; i < WHIP_BUFFER.length; i++){
			WHIP_BUFFER[i] = new StandardAudio("res/audio/sfx/whip1.wav");
		}

		for(int i = 0; i < SWORD_BUFFER.length; i++){
			SWORD_BUFFER[i] = new StandardAudio("res/audio/sfx/glashsh.wav");;
		}

		/*Facepalm*/
		for(int i = 0; i < RICHTER_SFX.length; i++){
			RICHTER_SFX[0] = new StandardAudio("res/audio/sfx/ric_huah.wav");
			RICHTER_SFX[1] = new StandardAudio("res/audio/sfx/ric_huh.wav");
			RICHTER_SFX[2] = new StandardAudio("res/audio/sfx/ric_raah.wav");
			RICHTER_SFX[3] = new StandardAudio("res/audio/sfx/ric_huah.wav");
			RICHTER_SFX[4] = new StandardAudio("res/audio/sfx/ric_huh.wav");
			RICHTER_SFX[5] = new StandardAudio("res/audio/sfx/ric_raah.wav");

		}

		//Loads in the jump buffers
		for(int i = 0; i < SOMA_JUMP_BUFFER.length; i++){
			SOMA_JUMP_BUFFER[i] = new StandardAudio("res/audio/sfx/soma_jump.wav");
		}

		//Painstakingly hardcoding (not really) the hurt buffers
		for(int i = 0; i < SOMA_HURT_BUFFER.length; i++){
			SOMA_HURT_BUFFER[0] = new StandardAudio("res/audio/sfx/soma_hurt0.wav");
			SOMA_HURT_BUFFER[1] = new StandardAudio("res/audio/sfx/soma_hurt1.wav");
			SOMA_HURT_BUFFER[2] = new StandardAudio("res/audio/sfx/soma_hurt0.wav");
			SOMA_HURT_BUFFER[3] = new StandardAudio("res/audio/sfx/soma_hurt1.wav");
		}
		
		//Soma's attack sfx
		for(int i = 0; i < SOMA_ATTACK.length; i++){
			SOMA_ATTACK[i] = new StandardAudio("res/audio/sfx/soma_attack.wav");
		}
		
		//Coin sfx
		for(int i = 0; i < COIN_SFX.length; i++){
			COIN_SFX[i] = new StandardAudio("res/audio/sfx/ncoinsfx.wav");
		}

	}

	/**
	 * Plays a buffer for the buttons
	 */
	public void Play_Button(){
		for(int i = 0; i<BUTTON_BUFFER.length; i++){
			//System.out.println(BUTTON_BUFFER[i] + "\t"+BUTTON_BUFFER+"i");
			if(!BUTTON_BUFFER[i].isPlaying()){
				BUTTON_BUFFER[i].rAndP();
				return;
			}
		}
	}

	/**
	 * Plays a buffer for the whip
	 */
	public void Play_Whip(){
		for(int i = 0; i<WHIP_BUFFER.length; i++){
			//System.out.println(BUTTON_BUFFER[i] + "\t"+BUTTON_BUFFER+"i");
			if(!WHIP_BUFFER[i].isPlaying()){
				WHIP_BUFFER[i].rAndP();
				return;
			}
		}
	}

	/**
	 * Plays a buffer for the swords
	 */
	public void Play_Sword(){
		for(int i = 0; i<SWORD_BUFFER.length; i++){
			//System.out.println(BUTTON_BUFFER[i] + "\t"+BUTTON_BUFFER+"i");
			if(!SWORD_BUFFER[i].isPlaying()){
				SWORD_BUFFER[i].rAndP();
				return;
			}
		}
	}

	/**
	 * Plays a random buffer for Richter
	 * @param x 0 to 6; plays one of the 3; equal chance for them all
	 */
	public void Play_Richter_SFX(int x){
		if(!RICHTER_SFX[x].isPlaying())
			RICHTER_SFX[x].rAndP();
		else{
			for(int i = 0; i < RICHTER_SFX.length; i++){
				if(!RICHTER_SFX[i].isPlaying()){
					RICHTER_SFX[i].rAndP();
					return;
				}
			}
		}
	}


	public void Play_Soma_Jump(){
		for(int i = 0; i<SOMA_JUMP_BUFFER.length; i++){
			if(!SOMA_JUMP_BUFFER[i].isPlaying()){
				SOMA_JUMP_BUFFER[i].rAndP();
				return;
			}
		}
	}

	/**
	 * Plays a random hurt buffer for Soma
	 * @param x 0 to 4; plays one of the 2; equal chance for both of them
	 */
	public void Play_Soma_Hurt(int x){
		if(!SOMA_HURT_BUFFER[x].isPlaying())
			SOMA_HURT_BUFFER[x].rAndP();
		else{
			for(int i = 0; i < SOMA_HURT_BUFFER.length; i++){
				if(!SOMA_HURT_BUFFER[i].isPlaying()){
					SOMA_HURT_BUFFER[i].rAndP();
					return;
				}
			}
		}
	}

	/**
	 * Plays the sound effect for Soma's death
	 * 
	 * @prequisite Player's health <= 0
	 */
	public void Play_Soma_Dead(){
		AudioBuffer.SOMA_DEAD.J2DPlay();
	}
	
	/**
	 * Plays the sfx for Soma attacking
	 */
	public void Play_Soma_Attack(){
		for(int i = 0; i<SOMA_ATTACK.length; i++){
			if(!SOMA_ATTACK[i].isPlaying()){
				SOMA_ATTACK[i].rAndP();
				return;
			}
		}
	}
	
	/**
	 * Plays the sfx for collecting a coin
	 */
	public void Play_Coin_SFX(){
		for(int i = 0; i<COIN_SFX.length; i++){
			if(!COIN_SFX[i].isPlaying()){
				COIN_SFX[i].rAndP();
				return;
			}
		}
	}


}
