package com.armorclient.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class ArmorClientScreen extends Screen {
    private static final int PANEL_WIDTH = 1000;
    private static final int PANEL_HEIGHT = 650;
    private static final int SIDEBAR_WIDTH = 240;
    private static final int PAGE_SIZE = 8;

    // All 60 entries from the supplied Armor Client JSX. Toggles are UI state only.
    private static final List<ModuleEntry> MODULES = List.of(
            new ModuleEntry("killaura", "Kill Aura", "Combat", "Ataca entidades automaticamente ao redor", false),
            new ModuleEntry("velocity", "Velocity", "Combat", "Reduz ou anula o knockback recebido", true),
            new ModuleEntry("autoclicker", "AutoClicker", "Combat", "Clica automaticamente em alta velocidade", false),
            new ModuleEntry("reach", "Reach", "Combat", "Aumenta a distância de ataque", false),
            new ModuleEntry("criticals", "Criticals", "Combat", "Sempre causa dano crítico nos ataques", false),
            new ModuleEntry("autoblock", "AutoBlock", "Combat", "Bloqueia automaticamente com a espada", false),
            new ModuleEntry("triggerbot", "TriggerBot", "Combat", "Ataca instantaneamente ao mirar em alvo", false),
            new ModuleEntry("bowspam", "BowSpam", "Combat", "Dispara flechas rapidamente", false),
            new ModuleEntry("aura_range", "Aura Range", "Combat", "Ajusta o raio de detecção da Aura", false),
            new ModuleEntry("target_hud", "Target HUD", "Combat", "Exibe informações do alvo atual", true),
            new ModuleEntry("auto_crit", "AutoCrit", "Combat", "Força saltos para críticos", false),
            new ModuleEntry("combat_log", "Combat Log", "Combat", "Evita punições ao deslogar em luta", false),
            new ModuleEntry("flight", "Flight", "Movement", "Permite voar livremente pelo mapa", false),
            new ModuleEntry("speed", "Speed", "Movement", "Aumenta a velocidade de caminhada", false),
            new ModuleEntry("step", "Step", "Movement", "Sobe blocos de 1 altura automaticamente", true),
            new ModuleEntry("spider", "Spider", "Movement", "Escala paredes como uma aranha", false),
            new ModuleEntry("longjump", "LongJump", "Movement", "Aumenta a distância do salto", false),
            new ModuleEntry("no_slow", "NoSlow", "Movement", "Remove lentidão ao comer ou usar itens", true),
            new ModuleEntry("safe_walk", "SafeWalk", "Movement", "Impede de cair de bordas de blocos", false),
            new ModuleEntry("boat_fly", "BoatFly", "Movement", "Permite voar enquanto está em barcos", false),
            new ModuleEntry("glide", "Glide", "Movement", "Cai lentamente como se tivesse elytra", false),
            new ModuleEntry("blink", "Blink", "Movement", "Congela sua posição para o servidor", false),
            new ModuleEntry("jetpack", "Jetpack", "Movement", "Propulsão vertical constante", false),
            new ModuleEntry("water_walk", "WaterWalk", "Movement", "Caminha sobre a água", false),
            new ModuleEntry("esp", "ESP", "Visual", "Destaca jogadores através de paredes", true),
            new ModuleEntry("xray", "X-Ray", "Visual", "Torna blocos comuns invisíveis", false),
            new ModuleEntry("tracers", "Tracers", "Visual", "Linhas conectando você aos jogadores", false),
            new ModuleEntry("fullbright", "FullBright", "Visual", "Remove a escuridão de cavernas", true),
            new ModuleEntry("nametags", "NameTags", "Visual", "Modifica a aparência dos nomes", false),
            new ModuleEntry("chams", "Chams", "Visual", "Altera a cor dos modelos de entidades", false),
            new ModuleEntry("storage_esp", "Storage ESP", "Visual", "Destaca baús e fornalhas", false),
            new ModuleEntry("breadcrumbs", "Breadcrumbs", "Visual", "Deixa rastro por onde você passou", false),
            new ModuleEntry("hud", "Custom HUD", "Visual", "Interface de informações personalizada", true),
            new ModuleEntry("fps_boost", "FPS Boost", "Visual", "Desativa partículas inúteis", true),
            new ModuleEntry("skybox", "Custom Sky", "Visual", "Altera a aparência do céu", false),
            new ModuleEntry("outline", "Outline", "Visual", "Cria contornos em entidades", false),
            new ModuleEntry("nuker", "Nuker", "World", "Quebra blocos ao redor rapidamente", false),
            new ModuleEntry("fastbreak", "FastBreak", "World", "Aumenta a velocidade de mineração", false),
            new ModuleEntry("auto_mine", "AutoMine", "World", "Minera minérios automaticamente", false),
            new ModuleEntry("scaffold", "Scaffold", "World", "Coloca blocos sob seus pés ao andar", false),
            new ModuleEntry("auto_place", "AutoPlace", "World", "Coloca blocos automaticamente", false),
            new ModuleEntry("timer", "Timer", "World", "Acelera a velocidade do jogo", false),
            new ModuleEntry("anti_ghost", "AntiGhost", "World", "Evita detecção de hacks de movimento", false),
            new ModuleEntry("auto_eat", "AutoEat", "World", "Come comida automaticamente", false),
            new ModuleEntry("auto_tool", "AutoTool", "World", "Troca para a melhor ferramenta", true),
            new ModuleEntry("fast_place", "FastPlace", "World", "Coloca blocos sem delay", false),
            new ModuleEntry("block_hit", "BlockHit", "World", "Ataca e coloca bloco simultaneamente", false),
            new ModuleEntry("auto_fish", "AutoFish", "World", "Pesca automaticamente", false),
            new ModuleEntry("auto_armor", "AutoArmor", "Player", "Equipa a melhor armadura do inventário", false),
            new ModuleEntry("chest_stealer", "ChestStealer", "Player", "Rouba itens de baús instantaneamente", false),
            new ModuleEntry("auto_drop", "AutoDrop", "Player", "Descarta itens inúteis automaticamente", false),
            new ModuleEntry("no_fall", "NoFall", "Player", "Remove dano de queda", true),
            new ModuleEntry("auto_respawn", "AutoRespawn", "Player", "Renasce instantaneamente após morrer", true),
            new ModuleEntry("anti_knock", "AntiKnock", "Player", "Modifica a resposta ao impacto", false),
            new ModuleEntry("auto_log", "AutoLog", "Player", "Desloga ao detectar perigo", false),
            new ModuleEntry("fast_respawn", "FastRespawn", "Player", "Pula a tela de morte", true),
            new ModuleEntry("auto_swap", "AutoSwap", "Player", "Troca de itens rapidamente", false),
            new ModuleEntry("inventory_sort", "InvSort", "Player", "Organiza o inventário automaticamente", false),
            new ModuleEntry("auto_click", "AutoClick", "Player", "Clica em intervalos fixos", false),
            new ModuleEntry("no_hurt_cam", "NoHurtCam", "Player", "Remove o tremor da tela ao levar dano", true)
    );

    private static final String[] CATEGORIES = {"All", "Combat", "Movement", "Visual", "World", "Player"};
    private static final int[] COLORS = {
            0xFF09090B, 0xFF18181B, 0xFF27272A, 0xFFFFFFFF, 0xFF9CA3AF, 0xFF71717A
    };

    private final List<Boolean> enabled = new ArrayList<>();
    private final List<Integer> visibleIndices = new ArrayList<>();
    private final List<Button> moduleButtons = new ArrayList<>();
    private String selectedCategory = "All";
    private String searchQuery = "";
    private int page = 0;
    private Button previousButton;
    private Button nextButton;

    public ArmorClientScreen() {
        super(Component.literal("Armor Client"));
        for (ModuleEntry module : MODULES) enabled.add(module.initiallyEnabled());
    }

    @Override
    protected void init() {
        moduleButtons.clear();
        visibleIndices.clear();

        int panelWidth = Math.min(PANEL_WIDTH, this.width - 24);
        int panelHeight = Math.min(PANEL_HEIGHT, this.height - 24);
        int panelX = (this.width - panelWidth) / 2;
        int panelY = (this.height - panelHeight) / 2;
        int sidebarWidth = Math.min(SIDEBAR_WIDTH, panelWidth / 3);
        int contentX = panelX + sidebarWidth;
        int contentWidth = panelWidth - sidebarWidth;

        EditBox searchBox = new EditBox(this.font, panelX + 16, panelY + 100, sidebarWidth - 32, 24,
                Component.literal("Search modules"));
        searchBox.setHint(Component.literal("Search modules..."));
        searchBox.setValue(this.searchQuery);
        searchBox.setResponder(value -> {
            this.searchQuery = value;
            this.page = 0;
            refreshVisibleModules();
        });
        this.addRenderableWidget(searchBox);

        int categoryY = panelY + 150;
        for (String category : CATEGORIES) {
            Button categoryButton = Button.builder(Component.literal(category), button -> {
                this.selectedCategory = category;
                this.page = 0;
                this.rebuildWidgets();
            }).bounds(panelX + 16, categoryY, sidebarWidth - 32, 28).build();
            this.addRenderableWidget(categoryButton);
            categoryY += 36;
        }

        this.addRenderableWidget(Button.builder(Component.literal("X"), button -> this.onClose())
                .bounds(panelX + panelWidth - 42, panelY + 18, 26, 24).build());

        int gridX = contentX + 22;
        int gridY = panelY + 108;
        int gapX = 14;
        int gapY = 14;
        int cardWidth = Math.max(120, (contentWidth - 62) / 2);
        int cardHeight = 94;

        for (int slot = 0; slot < PAGE_SIZE; slot++) {
            final int visibleSlot = slot;
            int column = slot % 2;
            int row = slot / 2;
            int x = gridX + column * (cardWidth + gapX);
            int y = gridY + row * (cardHeight + gapY);

            Button moduleButton = Button.builder(Component.literal(""), button -> {
                if (visibleSlot >= visibleIndices.size()) return;
                int moduleIndex = visibleIndices.get(visibleSlot);
                enabled.set(moduleIndex, !enabled.get(moduleIndex));
                button.setMessage(moduleButtonLabel(moduleIndex));
            }).bounds(x, y, cardWidth, cardHeight).build();
            moduleButton.visible = false;
            moduleButtons.add(moduleButton);
            this.addRenderableWidget(moduleButton);
        }

        int footerY = panelY + panelHeight - 52;
        previousButton = Button.builder(Component.literal("< Previous"), button -> {
            if (page > 0) {
                page--;
                refreshVisibleModules();
            }
        }).bounds(contentX + 22, footerY, 110, 28).build();
        nextButton = Button.builder(Component.literal("Next >"), button -> {
            int maxPage = Math.max(0, (visibleIndices.size() - 1) / PAGE_SIZE);
            if (page < maxPage) {
                page++;
                refreshVisibleModules();
            }
        }).bounds(contentX + 140, footerY, 100, 28).build();
        this.addRenderableWidget(previousButton);
        this.addRenderableWidget(nextButton);

        refreshVisibleModules();
    }

    private Component moduleButtonLabel(int index) {
        ModuleEntry module = MODULES.get(index);
        return Component.literal(module.name() + "   " + (enabled.get(index) ? "ON" : "OFF"));
    }

    private void refreshVisibleModules() {
        visibleIndices.clear();
        String query = searchQuery.toLowerCase(Locale.ROOT).trim();

        for (int i = 0; i < MODULES.size(); i++) {
            ModuleEntry module = MODULES.get(i);
            boolean categoryMatches = selectedCategory.equals("All") || module.category().equals(selectedCategory);
            boolean searchMatches = query.isEmpty()
                    || module.name().toLowerCase(Locale.ROOT).contains(query)
                    || module.description().toLowerCase(Locale.ROOT).contains(query);
            if (categoryMatches && searchMatches) visibleIndices.add(i);
        }

        int maxPage = Math.max(0, (visibleIndices.size() - 1) / PAGE_SIZE);
        page = Math.min(page, maxPage);
        for (int slot = 0; slot < PAGE_SIZE; slot++) {
            Button button = moduleButtons.get(slot);
            int visibleSlot = page * PAGE_SIZE + slot;
            boolean hasModule = visibleSlot < visibleIndices.size();
            button.visible = hasModule;
            if (hasModule) {
                int moduleIndex = visibleIndices.get(visibleSlot);
                button.setMessage(moduleButtonLabel(moduleIndex));
            }
        }
        previousButton.active = page > 0;
        nextButton.active = page < maxPage;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractBackground(graphics, mouseX, mouseY, delta);
        int panelWidth = Math.min(PANEL_WIDTH, this.width - 24);
        int panelHeight = Math.min(PANEL_HEIGHT, this.height - 24);
        int panelX = (this.width - panelWidth) / 2;
        int panelY = (this.height - panelHeight) / 2;
        int sidebarWidth = Math.min(SIDEBAR_WIDTH, panelWidth / 3);

        graphics.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, COLORS[0]);
        graphics.fill(panelX, panelY, panelX + sidebarWidth, panelY + panelHeight, COLORS[1]);
        graphics.fill(panelX + sidebarWidth, panelY, panelX + sidebarWidth + 1, panelY + panelHeight, COLORS[2]);
        graphics.fill(panelX + sidebarWidth, panelY + 80, panelX + panelWidth, panelY + 81, COLORS[2]);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        int panelWidth = Math.min(PANEL_WIDTH, this.width - 24);
        int panelHeight = Math.min(PANEL_HEIGHT, this.height - 24);
        int panelX = (this.width - panelWidth) / 2;
        int panelY = (this.height - panelHeight) / 2;
        int sidebarWidth = Math.min(SIDEBAR_WIDTH, panelWidth / 3);

        super.extractRenderState(graphics, mouseX, mouseY, delta);

        graphics.text(this.font, "ARMOR", panelX + 22, panelY + 30, COLORS[3], true);
        graphics.text(this.font, "CLIENT v26.2", panelX + 22, panelY + 48, COLORS[4], true);
        graphics.text(this.font, selectedCategory + " Modules", panelX + sidebarWidth + 22, panelY + 30, COLORS[3], true);
        graphics.text(this.font, visibleIndices.size() + " modules available",
                panelX + sidebarWidth + 22, panelY + 49, COLORS[5], false);
        graphics.text(this.font, "Fabric Loader", panelX + 18, panelY + panelHeight - 28, COLORS[5], false);
        graphics.text(this.font, "UI preview — module switches are visual only",
                panelX + sidebarWidth + 22, panelY + panelHeight - 32, COLORS[5], false);
        if (!visibleIndices.isEmpty()) {
            int maxPage = (visibleIndices.size() - 1) / PAGE_SIZE;
            graphics.text(this.font, "Page " + (page + 1) + " / " + (maxPage + 1),
                    panelX + sidebarWidth + 260, panelY + panelHeight - 42, COLORS[4], false);
        } else {
            graphics.text(this.font, "No modules found matching your search.",
                    panelX + sidebarWidth + 22, panelY + 150, COLORS[5], false);
        }

        for (int slot = 0; slot < PAGE_SIZE; slot++) {
            if (slot >= visibleIndices.size() - page * PAGE_SIZE) continue;
            int moduleIndex = visibleIndices.get(page * PAGE_SIZE + slot);
            Button button = moduleButtons.get(slot);
            if (!button.visible) continue;
            graphics.text(this.font, MODULES.get(moduleIndex).description(),
                    button.getX() + 10, button.getY() + 60, COLORS[5], false);
        }
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(null);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private record ModuleEntry(String id, String name, String category, String description, boolean initiallyEnabled) {}
}
