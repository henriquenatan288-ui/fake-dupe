package com.armorclient.module;

public class Flight extends Module {
    public Flight() { super("Flight", Category.MOVEMENT); }

    @Override
    public void onTick() {
        // Minecraft mc = Minecraft.getInstance();
        // // Força o modo de voo do Minecraft (bypass simples)
        // mc.player.getAbilities().flying = true;
        //
        // // Se quiser voar sem a animação de "criativo", manipula-se o vetor de movimento
        // if (mc.player.keyPressed.isPressed(GLFW.GLFW_KEY_SPACE)) {
        //     mc.player.setDeltaMovement(0, 0.2, 0);
        // }
    }

    @Override
    public void onDisable() {
        // Minecraft.getInstance().player.getAbilities().flying = false;
    }
}
