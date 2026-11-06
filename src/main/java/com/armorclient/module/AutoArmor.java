package com.armorclient.module;

public class AutoArmor extends Module {
    public AutoArmor() { super("AutoArmor", Category.PLAYER); }

    @Override
    public void onTick() {
        // Escaneia o inventário em busca de armaduras de Diamante/Netherite
        for (int i = 0; i < 36; i++) {
            // ItemStack stack = Minecraft.getInstance().player.getInventory().getItem(i);
            // if (isBestArmor(stack)) {
            //     // Move o item para o slot de armadura correspondente
            //     InventoryUtils.equipArmor(stack);
            // }
        }
    }
}
