package com.finalproject.entities.objects;

import com.finalproject.entities.Player;
import com.joshuacrotts.standards.StandardGameObject;

public abstract class Weapon {

	public abstract void attack(StandardGameObject user, Player target);
}
