package com.example;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
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
import org.apache.commons.lang3.ObjectUtils;

import java.util.Random;

public class mobshooting {

    /*
    custom obj = {Entity tmp, int Tick_Wait, int GetTick_wait}

    custom_obj[] data_struc = new int[1];
     */
    private static final int INIT_MAGN = 4;
    public static TypedActionResult<ItemStack> mob_shooter(PlayerEntity player, World world, Hand hand){
        //Getting the item that the player right clicked with
        ItemStack stack = player.getMainHandStack();
        Item PlayerHand = stack.getItem();
        //Setting items to later compare if player right clicked with this item
        Item item_creeper = Items.GUNPOWDER;
        Item item_zombiepigmen = Items.GOLD_INGOT;
        Item item_skeleton = Items.BONE;
        //Setting projectile speed that mobs will be launched at
        Vec3d init_vel = player.getRotationVector().multiply(INIT_MAGN);
        //Getting player position at the time they right click a particular item. Required for projectile
        Vec3d play_pos = player.getPos();
        //Making mob reference variable for cleaner code
        MobEntity mob = null;
        //Getting the tick when player right clicks on an item. Requires try-catch
        int spawntick=0;
        try {
            spawntick = world.getServer().getTicks();
        } catch(NullPointerException e){
            return TypedActionResult.pass(ItemStack.EMPTY);
        }

        //Creeper is spawned immediately since it is ignited
        if (PlayerHand.equals(item_creeper)) {
             mob = new CreeperEntity(EntityType.CREEPER, world);
             ((CreeperEntity) mob).ignite();
            mob.setVelocity(init_vel);
            mob.setPosition(play_pos);
             world.spawnEntity(mob);
        }
        else{
            //Checking if they spawned zombiepigmen
            if (PlayerHand.equals(item_zombiepigmen)){
                mob = new ZombifiedPiglinEntity(EntityType.ZOMBIFIED_PIGLIN, world);
                mob.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
                StatusEffectInstance speed = new StatusEffectInstance(StatusEffects.SPEED, 1000000, 1);
                mob.addStatusEffect(speed);
                //mobstack.push(new mobspawning(zombiepigmen, spawntick));
            }
            //checking if they spawned skeleton
            else if (stack.getItem().equals(item_skeleton)){
                mob = new SkeletonEntity(EntityType.SKELETON, world);
                mob.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                mob.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
                //mobstack.push(new mobspawning(skele, spawntick));
            }

            try {
                //Setting properties of these mobs when they spawn
                mob.setVelocity(init_vel);
                mob.setPosition(play_pos);
                //Uniquely identifying these mobs so that we can reference them on the world whenever
                int mob_id = new Random().nextInt(100000);
                mob.setId(mob_id);
                //Adding mob onto the stack, where they will provided a target to attack at a given time
                mobstack.push(new mobspawning(mob, spawntick, mob_id));
                //Spawning the mob
                world.spawnEntity(mob);
            } catch (NullPointerException e){
                return TypedActionResult.pass(ItemStack.EMPTY);

            }
        }
        return TypedActionResult.pass(ItemStack.EMPTY);
    }
}
