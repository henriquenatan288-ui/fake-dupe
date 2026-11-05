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
    private static final int PANEL_WIDTH = 850;
    private static final int PANEL_HEIGHT = 550;
    private static final int SIDEBAR_WIDTH = 220;

    private static final List<ModuleEntry> MODULES = List.of(
            new ModuleEntry("killaura", "Kill Aura", "Combat", "UI demo only — no gameplay effect", false),
            new ModuleEntry("velocity", "Velocity", "Combat", "UI demo only — no gameplay effect", true),
            new ModuleEntry("flight", "Flight", "Movement", "UI demo only — no gameplay effect", false),
            new ModuleEntry("esp", "ESP", "Visual", "UI demo only — no gameplay effect", true),
            new ModuleEntry("autoclicker", "AutoClicker", "Combat", "UI demo only — no gameplay effect", false),
            new ModuleEntry("xray", "X-Ray", "Visual", "UI demo only — no gameplay effect", false),
            new ModuleEntry("speed", "Speed", "Movement", "UI demo only — no gameplay effect", false)
    );

    private static final String[] CATEGORIES = {"All", "Combat", "Movement", "Visual", "World", "Player"};
    private static final int[] COLORS = {
            0xFF09090B, 0xFF18181B, 0xFF27272A, 0xFFFFFFFF, 0xFF9CA3AF, 0xFF71717A
    };

    private final List<Boolean> enabled = new ArrayList<>();
    private final List<Button> moduleButtons = new ArrayList<>();
    private String selectedCategory = "All";
    private String searchQuery = "";
    private EditBox searchBox;

    public ArmorClientScreen() {
        super(Component.literal("Armor Client"));
        for (ModuleEntry module : MODULES) enabled.add(module.initiallyEnabled());
    }

    @Override
    protected void init() {
        moduleButtons.clear();

        int panelX = (this.width - Math.min(PANEL_WIDTH, this.width - 24)) / 2;
        int panelY = (this.height - Math.min(PANEL_HEIGHT, this.height - 24)) / 2;
        int panelWidth = Math.min(PANEL_WIDTH, this.width - 24);
        int sidebarWidth = Math.min(SIDEBAR_WIDTH, panelWidth / 3);
        int contentX = panelX + sidebarWidth;
        int contentWidth = panelWidth - sidebarWidth;

        this.searchBox = new EditBox(this.font, panelX + 14, panelY + 94, sidebarWidth - 28, 20,
                Component.literal("Search modules"));
        this.searchBox.setHint(Component.literal("Search modules..."));
        this.searchBox.setValue(this.searchQuery);
        this.searchBox.setResponder(value -> {
            this.searchQuery = value;
            updateModuleVisibility();
        });
        this.addRenderableWidget(this.searchBox);

        int categoryY = panelY + 132;
        for (String category : CATEGORIES) {
            Button categoryButton = Button.builder(Component.literal(category), button -> {
                this.selectedCategory = category;
                this.rebuildWidgets();
            }).bounds(panelX + 14, categoryY, sidebarWidth - 28, 22).build();
            this.addRenderableWidget(categoryButton);
            categoryY += 27;
        }

        Button closeButton = Button.builder(Component.literal("X"), button -> this.onClose())
                .bounds(panelX + panelWidth - 38, panelY + 12, 24, 20).build();
        this.addRenderableWidget(closeButton);

        int gridX = contentX + 18;
        int gridY = panelY + 92;
        int gap = 12;
        int cardWidth = Math.max(110, (contentWidth - 54) / 2);
        int cardHeight = 62;

        for (int i = 0; i < MODULES.size(); i++) {
            final int index = i;
            ModuleEntry module = MODULES.get(i);
            int column = i % 2;
            int row = i / 2;
            int x = gridX + column * (cardWidth + gap);
            int y = gridY + row * (cardHeight + gap);

            Button moduleButton = Button.builder(moduleButtonLabel(index), button -> {
                enabled.set(index, !enabled.get(index));
                button.setMessage(moduleButtonLabel(index));
            }).bounds(x, y, cardWidth, cardHeight).build();

            moduleButtons.add(moduleButton);
            this.addRenderableWidget(moduleButton);
        }

        updateModuleVisibility();
    }

    private Component moduleButtonLabel(int index) {
        return Component.literal(MODULES.get(index).name() + "   " + (enabled.get(index) ? "ON" : "OFF"));
    }

    private void updateModuleVisibility() {
        String query = searchQuery.toLowerCase(Locale.ROOT).trim();
        for (int i = 0; i < MODULES.size(); i++) {
            ModuleEntry module = MODULES.get(i);
            boolean categoryMatches = selectedCategory.equals("All") || module.category().equals(selectedCategory);
            boolean searchMatches = query.isEmpty()
                    || module.name().toLowerCase(Locale.ROOT).contains(query)
                    || module.description().toLowerCase(Locale.ROOT).contains(query);
            moduleButtons.get(i).visible = categoryMatches && searchMatches;
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        int panelWidth = Math.min(PANEL_WIDTH, this.width - 24);
        int panelHeight = Math.min(PANEL_HEIGHT, this.height - 24);
        int panelX = (this.width - panelWidth) / 2;
        int panelY = (this.height - panelHeight) / 2;
        int sidebarWidth = Math.min(SIDEBAR_WIDTH, panelWidth / 3);

        graphics.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, COLORS[0]);
        graphics.fill(panelX, panelY, panelX + sidebarWidth, panelY + panelHeight, COLORS[1]);
        graphics.fill(panelX + sidebarWidth, panelY, panelX + sidebarWidth + 1, panelY + panelHeight, COLORS[2]);
        graphics.fill(panelX + sidebarWidth, panelY + 64, panelX + panelWidth, panelY + 65, COLORS[2]);

        super.extractRenderState(graphics, mouseX, mouseY, delta);

        graphics.text(this.font, "ARMOR", panelX + 18, panelY + 24, COLORS[3], true);
        graphics.text(this.font, "CLIENT", panelX + 78, panelY + 24, COLORS[4], true);
        graphics.text(this.font, selectedCategory + " Modules", panelX + sidebarWidth + 18, panelY + 28, COLORS[3], true);
        graphics.text(this.font, "v26.2 Fabric", panelX + 16, panelY + panelHeight - 28, COLORS[5], false);
        graphics.text(this.font, "UI preview — toggles do not activate gameplay modules",
                panelX + sidebarWidth + 18, panelY + panelHeight - 24, COLORS[5], false);

        for (int i = 0; i < MODULES.size(); i++) {
            if (!moduleButtons.get(i).visible) continue;
            Button button = moduleButtons.get(i);
            graphics.text(this.font, MODULES.get(i).description(),
                    button.getX() + 10, button.getY() + 39, COLORS[5], false);
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
