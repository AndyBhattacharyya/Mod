package com.example;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
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
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicate;
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
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ServerPlayConnectionEvents.JOIN.register(this::onPlayerJoin);
		UseItemCallback.EVENT.register(this::mob_shooter);
		ServerEntityEvents.ENTITY_LOAD.register(this::on_mob_spawn);

		LOGGER.info("Hello Fabric world!");
	}
	TypedActionResult<ItemStack> mob_shooter(PlayerEntity player, World world, Hand hand){
		ItemStack stack = player.getMainHandStack();
		Item item_creeper = Registries.ITEM.get(Identifier.of("minecraft", "gunpowder"));
		Item item_zombiepigmen = Registries.ITEM.get(Identifier.of("minecraft", "gold_ingot"));
		Item item_skeleton =Registries.ITEM.get(Identifier.of("minecraft", "bone"));
		if (stack.getItem().equals(item_creeper)) {
//not required
//player.sendMessage(Text.literal("ur hholdinga " + item.getName()));
//Spawning a creeper
			CreeperEntity e = new CreeperEntity(EntityType.CREEPER, world);
			EnderDragonEntity ed = new EnderDragonEntity(EntityType.ENDER_DRAGON, world);
			ed.setPosition(player.getPos().add(new Vec3d(0,12,0)));
			world.spawnEntity(ed);
//Setting the velocity of the creeper
			e.setVelocity(player.getRotationVector().multiply(5));
// Setting the Spawn point of the creeper at the players position
			e.setPosition(player.getPos());
//This method will ignite the creeper
			//e.ignite();
			e.setFuseSpeed(30);
// Actually spawning the creeper
			world.spawnEntity(e);
		}
		else if (stack.getItem().equals(item_zombiepigmen)){
			//Random random = new Random();
			//int randomint = random.nextInt(10000);
			ZombifiedPiglinEntity zombiepigmen = new ZombifiedPiglinEntity(EntityType.ZOMBIFIED_PIGLIN, world);
			//zombiepigmen.setId(randomint);
			zombiepigmen.setPosition(player.getPos());
			zombiepigmen.setVelocity(player.getRotationVector().multiply(5));
			zombiepigmen.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
			StatusEffectInstance speed = new StatusEffectInstance(StatusEffects.SPEED, 1000000, 1);
			zombiepigmen.addStatusEffect(speed);
			world.spawnEntity(zombiepigmen);

		}

		else if (stack.getItem().equals(item_skeleton)){
			SkeletonEntity skele = new SkeletonEntity(EntityType.SKELETON, world);
			skele.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
			skele.setPosition(player.getPos());
			world.spawnEntity(skele);
		}
		return TypedActionResult.pass(ItemStack.EMPTY);


	}
	public void on_mob_spawn(Entity entity, ServerWorld world){
		if (entity instanceof ZombifiedPiglinEntity){

			((ZombifiedPiglinEntity) entity).setTarget(world.getClosestPlayer(entity, Double.MAX_VALUE));

		}
	}
	public void onPlayerJoin(
			ServerPlayNetworkHandler e,
			PacketSender sender,
			MinecraftServer srv) {

		// Get the player that triggered this event
		ServerPlayerEntity p = e.getPlayer();
		// Get their username
		String username = p.getEntityName();
		// Send them a greeting containing their username
		p.sendMessage(Text.literal("Hi "+username+"! Nice to see to you."));
	}
}