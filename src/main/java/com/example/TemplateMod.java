package com.example;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.registry.Registries;

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

		LOGGER.info("Hello Fabric world!");
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
		p.sendMessage(Text.literal("Fuck you"));
	}
}