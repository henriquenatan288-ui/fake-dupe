package com.armorclient.module;

import java.util.ArrayList;
import java.util.List;

// Classe base para todos os 60 módulos
public abstract class Module {
    private String name;
    private Category category;
    private boolean enabled;

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
        this.enabled = false;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (enabled) onEnable(); else onDisable();
    }

    // Métodos que cada módulo irá sobrescrever para ter funcionalidade
    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {} // Executado a cada tick do jogo (20x por segundo)
    public void onRender() {} // Executado a cada frame de renderização

    public boolean isEnabled() { return enabled; }
    public String getName() { return name; }
    public Category getCategory() { return category; }

    public enum Category {
        COMBAT, MOVEMENT, VISUAL, WORLD, PLAYER
    }
}

// Gerenciador que controla a lista de 60 módulos
class ModuleManager {
    public List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Aqui registramos os 60 módulos
        modules.add(new KillAura());
        modules.add(new Flight());
        modules.add(new ESP());
        // ... adicionar os outros 57 módulos aqui
    }

    public void onTick() {
        for (Module m : modules) {
            if (m.isEnabled()) m.onTick();
        }
    }
}
