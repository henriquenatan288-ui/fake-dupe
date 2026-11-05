package com.armorclient.client;

import com.armorclient.ArmorClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public final class ArmorClientClient implements ClientModInitializer {
    private static KeyMapping openMenuKey;

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = KeyMapping.Category.register(
                net.minecraft.resources.Identifier.fromNamespaceAndPath(ArmorClient.MOD_ID, "general")
        );

        openMenuKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.armor-client.open_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.consumeClick()) {
                if (client.gui.screen() instanceof ArmorClientScreen) {
                    client.gui.setScreen(null);
                } else {
                    client.gui.setScreen(new ArmorClientScreen());
                }
            }
        });

        ArmorClient.LOGGER.info("Armor Client initialized. Open the menu with Right Shift.");
    }
}
