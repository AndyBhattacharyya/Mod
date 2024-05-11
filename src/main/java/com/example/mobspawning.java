package com.example;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class mobspawning {
    public static void onmobspawn(Entity entity, ServerWorld world){
        PlayerEntity spawner = world.getClosestPlayer(entity,3);
        int tmp_tick = world.getServer().getTicks();//player who spawned the entity

        if(spawner!=null && tmp_tick+20>world.getServer().getTicks()) {
            /*
            PlayerEntity closestPlr = mobshooting.closest_player(mobshooting.projectileFinalPos(mobshooting.INIT_MAGN, spawner), world);
            if (entity instanceof ZombifiedPiglinEntity) {
                //Set the target for the zombiepigmen to the player where it launchest closest
                ((ZombifiedPiglinEntity) entity).setTarget(closestPlr);
            } else if (entity instanceof SkeletonEntity) {
                ((SkeletonEntity) entity).setTarget(closestPlr);


            }

            Vec3d tmp = mobshooting.projectileFinalPos(mobshooting.INIT_MAGN, spawner);
            Vec3i int_tmp = new Vec3i((int) tmp.getX(), (int) tmp.getY(), (int) tmp.getZ());
            BlockPos test = new BlockPos(int_tmp);
            world.setBlockState(test, Blocks.POPPY.getDefaultState());

            Vec3d tmp = mobshooting.projectileFinalPos(mobshooting.INIT_MAGN, spawner);
            //spawner.teleport(tmp.getX(), tmp.getY(), tmp.getZ());
            LightningEntity l = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            l.setPosition(tmp);
            world.spawnEntity(l);
             */

            LightningEntity l = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            l.setPosition(spawner.getPos());
            world.spawnEntity(l);
        }



    }
}
