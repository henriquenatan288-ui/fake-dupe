package com.armorclient.module;

public class KillAura extends Module {
    public KillAura() { super("Kill Aura", Category.COMBAT); }

    @Override
    public void onTick() {
        // 1. Localiza a entidade mais próxima no raio de 3.8 blocos
        // Entity target = findClosestEntity(3.8f);
        //
        // if (target != null) {
        //     // 2. Rotaciona a câmera do jogador para o alvo (Silent Rotation)
        //     rotateToEntity(target);
        //
        //     // 3. Envia o pacote de ataque ao servidor
        //     Minecraft.getInstance().player.attack(target);
        //     Minecraft.getInstance().player.swing(Hand.MAIN_HAND);
        // }
    }
}
