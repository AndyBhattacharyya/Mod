package com.example;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.server.world.ServerWorld;

public class mobspawning {
    public static void onmobspawn(Entity entity, ServerWorld world){
        if (entity instanceof ZombifiedPiglinEntity){
            //Set the target for the zombiepigmen to the player where it launchest closest
            ((ZombifiedPiglinEntity) entity).setTarget(world.getClosestPlayer(entity, Double.MAX_VALUE));
        }

    }
}
