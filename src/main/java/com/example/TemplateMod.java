package com.example;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.registry.Registries;

import java.time.Year;
import java.util.Random;

public class TemplateMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("template-mod");

	@Override
	public void onInitialize() {
		//General Syntax: event.Event.register(class::static_method);
		ServerPlayConnectionEvents.JOIN.register(on_player_join::onPlayerJoin);
		UseItemCallback.EVENT.register(mobshooting::mob_shooter);
		//ServerEntityEvents.ENTITY_LOAD.register(mobspawning::onmobspawn);
		ServerTickEvents.START_SERVER_TICK.register(this::tickcheck);
		LOGGER.info("Hello Fabric world!");
	}


	void tickcheck(MinecraftServer server){
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

