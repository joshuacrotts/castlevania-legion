package com.finalproject.commands;

import com.andrewmatzureff.commands.Command;
import com.finalproject.entities.projectiles.Fireball;
import com.joshuacrotts.standards.StandardCollisionHandler;
import com.joshuacrotts.standards.StandardGameObject;
import com.joshuacrotts.standards.StandardGameObject.Direction;

public class Attack extends Command {

    private StandardCollisionHandler sch;
    private StandardGameObject o;

    public Attack(StandardGameObject o, StandardCollisionHandler sch) {
        this.sch = sch;
        this.o = o;
    }

    @Override
    public void execute() {
        o.attacking = true;

        if (this.o.lastDir == Direction.Right) {
            this.sch.addEntity(new Fireball(o, 5f, 0f));
        } else {
            this.sch.addEntity(new Fireball(o, -5f, 0f));
        }

    }

}
