package com.finalproject.main;


/**
 * The course of this project was over about a month or so, the original
 * Standards library & idea was created by me, and my friend: Andrew Matzureff
 * did a lot of the core functionality behind the input & collision detection;
 * we also worked on a lot of optimization on the handlers and how they spray/
 * distribute particles throughout the world. AI was done as a cooperative effort
 * as well. All files and images are rightfully owned by their creators; most of 
 * them coming straight from other Castlevania games such as Aria of Sorrow, Dawn
 * of Sorrow, etc. I have to thank Andrew for doing the physics, because, the 
 * restitution methods and functionality was incredibly difficult and most likely
 * couldn't have done it without him.
 * 
 * Honestly... It's pretty much done. It's kinda wonky and broken but... I've ran 
 * out of time. Calling it a demo as it only has one functional level.
 * 
 * Even with utilizing Standards 2.0 (the library that we made), it still was incredibly
 * hard as we had to keep changing the library and alternating it to make it work
 * with this project. However, I think it's a steady improvement over last year's game.
 * I hope everyone enjoys it!
 * 
 * Important: The JAR file attached to this game is the JAR for the Standards 2.0 library;
 * I've included the source code for both. If you're going to post this on the website
 * for others to play, use the JAR for CastlevaniaLegion.
 * 
 * @authors Joshua Crotts & Andrew Matzureff
 *
 * @version (5/24/17)
 *
 */
public class CastlevaniaLegionRunner {

	public static void main(String[] args) {
		new Game(1280, "Castlevania: Legion - Demo/Alpha - Version 1.0.0");
	}
}
