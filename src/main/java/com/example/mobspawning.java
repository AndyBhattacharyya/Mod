package com.example;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class mobspawning {
  /*
  Required data that will allow us to impose a wait-time, so the mob can land, before attacking the nearest player
   */
    private MobEntity tmp;
    private int spawn_tick;
    private int mob_id;
    //Delay time is set to 20 ticks or 1 second
    private final int delay = 20;

    public mobspawning(MobEntity tmp, int spawn_tick, int mob_id){
      this.tmp = tmp;
      //Wait 2 seconds (40 ticks)
      this.spawn_tick = spawn_tick + delay;
      this.mob_id = mob_id;
    }

    public int getSpawn_tick() {
        return spawn_tick;
    }

  public int getMob_id() {
    return mob_id;
  }

  public MobEntity getMob(){
      return tmp;
  }
}
