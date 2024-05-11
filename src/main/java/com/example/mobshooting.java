package com.example;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class mobshooting {
    //func: Vec3d comp(double init_vel, Player player);
    static final double INIT_MAGN = 2;   //magnitude of initial velocity
    public static PlayerEntity closest_player(Vec3d pos, World world){
        double closestDist = Math.pow(10,5);    //some large number
        return world.getClosestPlayer(pos.getX(),pos.getY(),pos.getZ(),closestDist,true);
    }
    public static Vec3d projectileFinalPos(double initial, PlayerEntity player) {
        double g = 0.08;    //gravity (blocks per tick?)
        Vec3d plrPos = player.getPos();
        double pitch = Math.toRadians(-1*player.getPitch());
        double yaw = Math.toRadians(player.getYaw());
        double deltaZ = Math.toDegrees(2*Math.pow(initial,2)*Math.sin(pitch)*Math.cos(pitch)*Math.cos(yaw)/g);
        double deltaX= Math.toDegrees(2*Math.pow(initial,2)*Math.sin(pitch)*Math.cos(pitch)*Math.sin(yaw)/g);
        if (pitch <= 0) {   //if player is aiming at the ground
            deltaX = 0;
            deltaZ = 0;
        }
        return new Vec3d(plrPos.getX() + deltaX, plrPos.getY(), plrPos.getZ() + deltaZ);
    }
    public static TypedActionResult<ItemStack> mob_shooter(PlayerEntity player, World world, Hand hand){
        ItemStack stack = player.getMainHandStack();
        //Item item_creeper = Registries.ITEM.get(Identifier.of("minecraft", "gunpowder"));
        Item item_creeper = Items.GUNPOWDER;
        //Item item_zombiepigmen = Registries.ITEM.get(Identifier.of("minecraft", "gold_ingot"));
        Item item_zombiepigmen = Items.GOLD_INGOT;
        //Item item_skeleton =Registries.ITEM.get(Identifier.of("minecraft", "bone"));
        Item item_skeleton = Items.BONE;
        if (stack.getItem().equals(item_creeper)) {
            //Spawning a creeper
            CreeperEntity e = new CreeperEntity(EntityType.CREEPER, world);
            //Setting the velocity of the creeper
            //Getting Velocity Vector
            //Vec3d init_vel = player.getRotationVector().normalize().multiply(INIT_MAGN);
            //e.setVelocity(init_vel);
            // Setting the Spawn point of the creeper at the players position
            e.setPosition(player.getPos());
            //This method will ignite the creeper
            //e.ignite();
            // Actually spawning the creeper
            world.spawnEntity(e);
            player.sendMessage(Text.literal("Yaw:" + player.getYaw()));
            player.sendMessage(Text.literal("Pitch:" + player.getPitch()));

        }
        else if (stack.getItem().equals(item_zombiepigmen)){
            ZombifiedPiglinEntity zombiepigmen = new ZombifiedPiglinEntity(EntityType.ZOMBIFIED_PIGLIN, world);
            Vec3d tmp = player.getPos();
            zombiepigmen.setPosition(tmp);
            Vec3d tmp2 = player.getRotationVector().multiply(5);
            zombiepigmen.setVelocity(tmp2);
            zombiepigmen.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            StatusEffectInstance speed = new StatusEffectInstance(StatusEffects.SPEED, 1000000, 1);
            zombiepigmen.addStatusEffect(speed);
            world.spawnEntity(zombiepigmen);
           // String current_tick = String.valueOf(world.getServer().getTicks());
           // player.sendMessage(Text.literal(current_tick));

        }
        else if (stack.getItem().equals(item_skeleton)){
            SkeletonEntity skele = new SkeletonEntity(EntityType.SKELETON, world);
            skele.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            skele.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
            skele.setPosition(player.getPos());
            Vec3d init_vel = player.getRotationVector().normalize().multiply(INIT_MAGN);
            skele.setVelocity(init_vel);
            world.spawnEntity(skele);
        }
        return TypedActionResult.pass(ItemStack.EMPTY);


    }
}
