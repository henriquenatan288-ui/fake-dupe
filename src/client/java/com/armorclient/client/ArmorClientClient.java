package com.armorclient.client;

import net.fabricmc.api.ClientModInitializer;
import com.armorclient.ArmorClient;

public final class ArmorClientClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArmorClient.LOGGER.info("Armor Client client components initialized.");
    }
}
