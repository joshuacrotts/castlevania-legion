package com.finalproject.commands;

import com.andrewmatzureff.commands.Command;
import com.andrewmatzureff.constants.C;
import com.finalproject.entities.Player;
import com.finalproject.main.Game;
import com.finalproject.weapons.MeleeWeapon;

public class MeleeCommand extends Command {

    private Player player;
    //private MeleeWeapon weapon;
    private long time = 0;
    private long delay;

    public MeleeCommand(Player player, MeleeWeapon weapon, double delay) {
        this.player = player;
        this.delay = (long) (delay * C.NANO);
        //this.weapon = weapon;
    }

    @Override
    public void execute() {

        long current = System.nanoTime();
        long interval = current - time;
        if (this.player.usingMelee || interval - delay < 0) {
            return;
        }

        time = current;
        this.player.usingMelee = true;
        Game.audioBuff.Play_Soma_Attack();

    }
}
