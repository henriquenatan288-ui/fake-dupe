package com.armorclient.module;

public class ESP extends Module {
    public ESP() { super("ESP", Category.VISUAL); }

    @Override
    public void onRender() {
        // Itera sobre todas as entidades renderizadas
        // for (Entity entity : Minecraft.getInstance().level.entities()) {
        //     if (entity instanceof Player) {
        //         // Desenha uma caixa (Bounding Box) ao redor do jogador
        //         RenderUtils.drawBox(entity.getBoundingBox(), Color.WHITE, 2.0f);
        //     }
        // }
    }
}
