package com.example;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class on_player_join {
    public static void onPlayerJoin(
            ServerPlayNetworkHandler e,
            PacketSender sender,
            MinecraftServer srv) {

        // Get the player that triggered this event
        ServerPlayerEntity p = e.getPlayer();
        // Get their username
        String username = p.getEntityName();
        // Send them a greeting containing their username
        p.sendMessage(Text.literal("Hi "+username+"! Nice to see to you."));
        // Additional Greeting
        p.sendMessage(Text.literal("You suck" +username+"! terrible to see to you."));
        // 2 noobs
        p.sendMessage(Text.literal("We suck at minecraft modding"));
        p.sendMessage(Text.literal("But we do be learning <3"));
    }
}
