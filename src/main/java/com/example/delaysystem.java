package com.example;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class delaysystem {

    public static void tickcheck(MinecraftServer server){
        //40 ticks in 1 seconds is the time its going wait before setting the target of the mob
        int current_tick = server.getTicks();
        //Get most recent mob spawned by item right-clicked
        mobspawning tmp = mobstack.pop();
        //Ensuring that it isn't null
        if(tmp!=null) {
            //Checking if 1 second has passed yet.
            if (current_tick == tmp.getSpawn_tick()) {
                //Get the world where players exist
                World tmp_world = server.getWorld(World.OVERWORLD);
                //Get the mob entity by the it's unique identifier provided in mobshooting
                MobEntity tmp_mob = (MobEntity) tmp_world.getEntityById(tmp.getMob_id());
                //Set the target to the closest player where the mob lands
                tmp_mob.setTarget(tmp_world.getClosestPlayer(tmp_mob, Math.pow(5,10)));
            }
            //If it is too early, then add the mob back into the stack, to check again the next tick
            else {
                mobstack.push(tmp);
            }
        }
    }
}
