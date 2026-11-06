import React, { useState, useMemo } from 'react';
import { 
  Settings, Shield, Zap, Eye, Crosshair, Move, 
  ChevronRight, X, Search, Layout, Sword, 
  Compass, User, Globe, Box, Cpu, Activity
} from 'lucide-react';

// Banco de dados de 60 módulos divididos por categorias
const MODULE_DATABASE = [
  // COMBAT (12)
  { id: 'killaura', name: 'Kill Aura', cat: 'Combat', desc: 'Ataca entidades automaticamente ao redor', default: false },
  { id: 'velocity', name: 'Velocity', cat: 'Combat', desc: 'Reduz ou anula o knockback recebido', default: true },
  { id: 'autoclicker', name: 'AutoClicker', cat: 'Combat', desc: 'Clica automaticamente em alta velocidade', default: false },
  { id: 'reach', name: 'Reach', cat: 'Combat', desc: 'Aumenta a distância de ataque', default: false },
  { id: 'criticals', name: 'Criticals', cat: 'Combat', desc: 'Sempre causa dano crítico nos ataques', default: false },
  { id: 'autoblock', name: 'AutoBlock', cat: 'Combat', desc: 'Bloqueia automaticamente com a espada', default: false },
  { id: 'triggerbot', name: 'TriggerBot', cat: 'Combat', desc: 'Ataca instantaneamente ao mirar em alvo', default: false },
  { id: 'bowspam', name: 'BowSpam', cat: 'Combat', desc: 'Dispara flechas rapidamente', default: false },
  { id: 'aura_range', name: 'Aura Range', cat: 'Combat', desc: 'Ajusta o raio de detecção da Aura', default: false },
  { id: 'target_hud', name: 'Target HUD', cat: 'Combat', desc: 'Exibe informações do alvo atual', default: true },
  { id: 'auto_crit', name: 'AutoCrit', cat: 'Combat', desc: 'Força saltos para críticos', default: false },
  { id: 'combat_log', name: 'Combat Log', cat: 'Combat', desc: 'Evita punições ao deslogar em luta', default: false },

  // MOVEMENT (12)
  { id: 'flight', name: 'Flight', cat: 'Movement', desc: 'Permite voar livremente pelo mapa', default: false },
  { id: 'speed', name: 'Speed', cat: 'Movement', desc: 'Aumenta a velocidade de caminhada', default: false },
  { id: 'step', name: 'Step', cat: 'Movement', desc: 'Sobe blocos de 1 altura automaticamente', default: true },
  { id: 'spider', name: 'Spider', cat: 'Movement', desc: 'Escala paredes como uma aranha', default: false },
  { id: 'longjump', name: 'LongJump', cat: 'Movement', desc: 'Aumenta a distância do salto', default: false },
  { id: 'no_slow', name: 'NoSlow', cat: 'Movement', desc: 'Remove lentidão ao comer ou usar itens', default: true },
  { id: 'safe_walk', name: 'SafeWalk', cat: 'Movement', desc: 'Impede de cair de bordas de blocos', default: false },
  { id: 'boat_fly', name: 'BoatFly', cat: 'Movement', desc: 'Permite voar enquanto está em barcos', default: false },
  { id: 'glide', name: 'Glide', cat: 'Movement', desc: 'Cai lentamente como se tivesse elytra', default: false },
  { id: 'blink', name: 'Blink', cat: 'Movement', desc: 'Congela sua posição para o servidor', default: false },
  { id: 'jetpack', name: 'Jetpack', cat: 'Movement', desc: 'Propulsão vertical constante', default: false },
  { id: 'water_walk', name: 'WaterWalk', cat: 'Movement', desc: 'Caminha sobre a água', default: false },

  // VISUAL (12)
  { id: 'esp', name: 'ESP', cat: 'Visual', desc: 'Destaca jogadores através de paredes', default: true },
  { id: 'xray', name: 'X-Ray', cat: 'Visual', desc: 'Torna blocos comuns invisíveis', default: false },
  { id: 'tracers', name: 'Tracers', cat: 'Visual', desc: 'Linhas conectando você aos jogadores', default: false },
  { id: 'fullbright', name: 'FullBright', cat: 'Visual', desc: 'Remove a escuridão de cavernas', default: true },
  { id: 'nametags', name: 'NameTags', cat: 'Visual', desc: 'Modifica a aparência dos nomes', default: false },
  { id: 'chams', name: 'Chams', cat: 'Visual', desc: 'Altera a cor dos modelos de entidades', default: false },
  { id: 'storage_esp', name: 'Storage ESP', cat: 'Visual', desc: 'Destaca baús e fornalhas', default: false },
  { id: 'breadcrumbs', name: 'Breadcrumbs', cat: 'Visual', desc: 'Deixa rastro por onde você passou', default: false },
  { id: 'hud', name: 'Custom HUD', cat: 'Visual', desc: 'Interface de informações personalizada', default: true },
  { id: 'fps_boost', name: 'FPS Boost', cat: 'Visual', desc: 'Desativa partículas inúteis', default: true },
  { id: 'skybox', name: 'Custom Sky', cat: 'Visual', desc: 'Altera a aparência do céu', default: false },
  { id: 'outline', name: 'Outline', cat: 'Visual', desc: 'Cria contornos em entidades', default: false },

  // WORLD (12)
  { id: 'nuker', name: 'Nuker', cat: 'World', desc: 'Quebra blocos ao redor rapidamente', default: false },
  { id: 'fastbreak', name: 'FastBreak', cat: 'World', desc: 'Aumenta a velocidade de mineração', default: false },
  { id: 'auto_mine', name: 'AutoMine', cat: 'World', desc: 'Minera minérios automaticamente', default: false },
  { id: 'scaffold', name: 'Scaffold', cat: 'World', desc: 'Coloca blocos sob seus pés ao andar', default: false },
  { id: 'auto_place', name: 'AutoPlace', cat: 'World', desc: 'Coloca blocos automaticamente', default: false },
  { id: 'timer', name: 'Timer', cat: 'World', desc: 'Acelera a velocidade do jogo', default: false },
  { id: 'anti_ghost', name: 'AntiGhost', cat: 'World', desc: 'Evita detecção de hacks de movimento', default: false },
  { id: 'auto_eat', name: 'AutoEat', cat: 'World', desc: 'Come comida automaticamente', default: false },
  { id: 'auto_tool', name: 'AutoTool', cat: 'World', desc: 'Troca para a melhor ferramenta', default: true },
  { id: 'fast_place', name: 'FastPlace', cat: 'World', desc: 'Coloca blocos sem delay', default: false },
  { id: 'block_hit', name: 'BlockHit', cat: 'World', desc: 'Ataca e coloca bloco simultaneamente', default: false },
  { id: 'auto_fish', name: 'AutoFish', cat: 'World', desc: 'Pesca automaticamente', default: false },

  // PLAYER (12)
  { id: 'auto_armor', name: 'AutoArmor', cat: 'Player', desc: 'Equipa a melhor armadura do inventário', default: false },
  { id: 'chest_stealer', name: 'ChestStealer', cat: 'Player', desc: 'Rouba itens de baús instantaneamente', default: false },
  { id: 'auto_drop', name: 'AutoDrop', cat: 'Player', desc: 'Descarta itens inúteis automaticamente', default: false },
  { id: 'no_fall', name: 'NoFall', cat: 'Player', desc: 'Remove dano de queda', default: true },
  { id: 'auto_respawn', name: 'AutoRespawn', cat: 'Player', desc: 'Renasce instantaneamente após morrer', default: true },
  { id: 'anti_knock', name: 'AntiKnock', cat: 'Player', desc: 'Modifica a resposta ao impacto', default: false },
  { id: 'auto_log', name: 'AutoLog', cat: 'Player', desc: 'Desloga ao detectar perigo', default: false },
  { id: 'fast_respawn', name: 'FastRespawn', cat: 'Player', desc: 'Pula a tela de morte', default: true },
  { id: 'auto_swap', name: 'AutoSwap', cat: 'Player', desc: 'Troca de itens rapidamente', default: false },
  { id: 'inventory_sort', name: 'InvSort', cat: 'Player', desc: 'Organiza o inventário automaticamente', default: false },
  { id: 'auto_click', name: 'AutoClick', cat: 'Player', desc: 'Clica em intervalos fixos', default: false },
  { id: 'no_hurt_cam', name: 'NoHurtCam', cat: 'Player', desc: 'Remove o tremor da tela ao levar dano', default: true },
];

const ArmorClient = () => {
  const [modules, setModules] = useState(() => 
    MODULE_DATABASE.map(m => ({ ...m, enabled: m.default }))
  );
  const [search, setSearch] = useState('');
  const [category, setCategory] = useState('All');
  const [isOpen, setIsOpen] = useState(true);

  const toggleModule = (id) => {
    setModules(prev => prev.map(m => m.id === id ? { ...m, enabled: !m.enabled } : m));
  };

  const filteredModules = useMemo(() => {
    return modules.filter(m => {
      const matchesSearch = m.name.toLowerCase().includes(search.toLowerCase());
      const matchesCat = category === 'All' || m.cat === category;
      return matchesSearch && matchesCat;
    });
  }, [modules, search, category]);

  const categories = [
    { name: 'All', icon: Layout },
    { name: 'Combat', icon: Sword },
    { name: 'Movement', icon: Move },
    { name: 'Visual', icon: Eye },
    { name: 'World', icon: Globe },
    { name: 'Player', icon: User },
  ];

  if (!isOpen) return (
    <div className="fixed inset-0 flex items-center justify-center bg-black/40 backdrop-blur-md z-50">
      <button 
        onClick={() => setIsOpen(true)}
        className="group relative px-8 py-4 bg-zinc-900 text-zinc-400 border border-zinc-800 rounded-full hover:text-white transition-all duration-300 flex items-center gap-3 font-medium overflow-hidden"
      >
        <div className="absolute inset-0 bg-gradient-to-r from-white/0 via-white/10 to-white/0 -translate-x-full group-hover:translate-x-full transition-transform duration-1000" />
        <Shield size={20} className="group-hover:scale-110 transition-transform" /> 
        <span>Launch Armor Client</span>
      </button>
    </div>
  );

  return (
    <div className="fixed inset-0 flex items-center justify-center p-6 font-sans text-zinc-400 select-none bg-zinc-900/20">
      <div className="w-[1000px] h-[650px] bg-zinc-950/85 backdrop-blur-2xl border border-zinc-800 rounded-3xl shadow-[0_0_50px_rgba(0,0,0,0.8)] flex overflow-hidden animate-in fade-in zoom-in duration-300">
        
        {/* Sidebar Slim */}
        <div className="w-72 bg-zinc-900/40 border-r border-zinc-800 flex flex-col">
          <div className="p-8 flex items-center gap-4">
            <div className="w-10 h-10 bg-white rounded-xl flex items-center justify-center shadow-[0_0_15px_rgba(255,255,255,0.3)]">
              <Shield className="text-black" size={22} strokeWidth={2.5} />
            </div>
            <div className="flex flex-col">
              <span className="text-xl font-black tracking-tighter text-white leading-none">ARMOR</span>
              <span className="text-[10px] font-bold tracking-widest text-zinc-500 uppercase">Client v26.2</span>
            </div>
          </div>

          <div className="px-6 mb-6">
            <div className="relative group">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-zinc-600 group-focus-within:text-white transition-colors" size={14} />
              <input 
                type="text" 
                placeholder="Search modules..." 
                className="w-full bg-zinc-800/30 border border-zinc-800 rounded-xl py-2.5 pl-9 pr-4 text-xs focus:outline-none focus:border-zinc-600 focus:bg-zinc-800/50 transition-all placeholder:text-zinc-700"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />
            </div>
          </div>

          <nav className="flex-1 px-4 space-y-1.5">
            {categories.map(({ name, icon: Icon }) => (
              <button 
                key={name}
                onClick={() => setCategory(name)}
                className={`w-full flex items-center justify-between px-4 py-3 rounded-xl text-xs font-medium transition-all duration-200 ${
                  category === name 
                  ? 'bg-white text-black shadow-lg' 
                  : 'text-zinc-500 hover:bg-zinc-800/50 hover:text-zinc-300'
                }`}
              >
                <div className="flex items-center gap-3">
                  <Icon size={16} strokeWidth={category === name ? 2.5 : 2} />
                  {name}
                </div>
                {category === name && <ChevronRight size={14} />}
              </button>
            ))}
          </nav>

          <div className="p-6 border-t border-zinc-800 flex items-center justify-between">
            <div className="flex items-center gap-2 text-[10px] font-mono text-zinc-600">
              <Activity size={12} className="text-green-500" />
              <span>Fabric Loader</span>
            </div>
            <div className="px-2 py-1 bg-zinc-800 rounded text-[9px] font-bold text-zinc-400 uppercase tracking-tighter">
              Stable
            </div>
          </div>
        </div>

        {/* Main Content */}
        <div className="flex-1 flex flex-col bg-zinc-950/30">
          <div className="h-20 border-b border-zinc-800 flex items-center justify-between px-8">
            <div>
              <h2 className="text-lg font-bold text-white tracking-tight">{category} Modules</h2>
              <p className="text-[11px] text-zinc-600">{filteredModules.length} modules available</p>
            </div>
            <button 
              onClick={() => setIsOpen(false)}
              className="p-2.5 hover:bg-zinc-800 rounded-xl transition-all text-zinc-500 hover:text-white"
            >
              <X size={20} />
            </button>
          </div>

          <div className="p-8 overflow-y-auto grid grid-cols-2 gap-4 content-start custom-scrollbar">
            {filteredModules.map(mod => (
              <div 
                key={mod.id}
                className={`group relative p-5 rounded-2xl border transition-all duration-300 cursor-pointer ${
                  mod.enabled 
                  ? 'bg-zinc-900/60 border-zinc-700 shadow-xl' 
                  : 'bg-zinc-900/20 border-zinc-800/50 hover:border-zinc-700 hover:bg-zinc-900/30'
                }`}
                onClick={() => toggleModule(mod.id)}
              >
                <div className="flex items-center justify-between mb-3">
                  <span className={`font-bold text-sm transition-colors ${mod.enabled ? 'text-white' : 'text-zinc-500'}`}>
                    {mod.name}
                  </span>
                  <div className={`w-10 h-5 rounded-full relative transition-all duration-300 ${mod.enabled ? 'bg-white' : 'bg-zinc-800'}`}>
                    <div className={`absolute top-1 w-3 h-3 bg-zinc-950 rounded-full transition-all duration-300 ${mod.enabled ? 'right-1' : 'left-1'}`} />
                  </div>
                </div>
                <p className="text-xs text-zinc-600 leading-relaxed group-hover:text-zinc-500 transition-colors">
                  {mod.desc}
                </p>
                {mod.enabled && (
                  <div className="mt-4 pt-4 border-t border-zinc-800/50 flex items-center justify-between">
                    <span className="text-[9px] font-bold uppercase tracking-widest text-zinc-500">Status: <span className="text-white">Active</span></span>
                    <div className="flex gap-1">
                      <div className="w-1 h-1 rounded-full bg-white animate-pulse" />
                      <div className="w-1 h-1 rounded-full bg-white animate-pulse delay-75" />
                      <div className="w-1 h-1 rounded-full bg-white animate-pulse delay-150" />
                    </div>
                  </div>
                )}
              </div>
            ))}
            {filteredModules.length === 0 && (
              <div className="col-span-2 py-32 text-center">
                <div className="inline-flex p-4 bg-zinc-900 rounded-full text-zinc-700 mb-4">
                  <Search size={32} />
                </div>
                <p className="text-zinc-600 font-medium">No modules found matching your search.</p>
              </div>
            )}
          </div>
        </div>
      </div>

<style dangerouslySetInnerHTML={{ __html: `
        .custom-scrollbar::-webkit-scrollbar {
          width: 4px;
        }
        .custom-scrollbar::-webkit-scrollbar-track {
          background: transparent;
        }
        .custom-scrollbar::-webkit-scrollbar-thumb {
          background: #27272a;
          border-radius: 10px;
        }
        .custom-scrollbar::-webkit-scrollbar-thumb:hover {
          background: #3f3f46;
        }
      ` }} />
    </div>
  );
};

export default ArmorClient;
