import React, { useState, useEffect } from 'react';
import { 
  Settings, 
  Shield, 
  Zap, 
  Eye, 
  Crosshair, 
  Move, 
  ChevronRight, 
  X, 
  Search,
  Layout
} from 'lucide-react';

// Simulação da estrutura de módulos do Armor Client
const INITIAL_MODULES = [
  { id: 'killaura', name: 'Kill Aura', category: 'Combat', enabled: false, description: 'Ataca entidades automaticamente' },
  { id: 'velocity', name: 'Velocity', category: 'Combat', enabled: true, description: 'Reduz o knockback recebido' },
  { id: 'flight', name: 'Flight', category: 'Movement', enabled: false, description: 'Permite voar pelo mapa' },
  { id: 'esp', name: 'ESP', category: 'Visual', enabled: true, description: 'Destaca jogadores através de paredes' },
  { id: 'autoclicker', name: 'AutoClicker', category: 'Combat', enabled: false, description: 'Clica automaticamente em alta velocidade' },
  { id: 'xray', name: 'X-Ray', category: 'Visual', enabled: false, description: 'Mostra minérios preciosos' },
  { id: 'speed', name: 'Speed', category: 'Movement', enabled: false, description: 'Aumenta a velocidade de movimento' },
];

const ArmorClientGUI = () => {
  const [modules, setModules] = useState(INITIAL_MODULES);
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('All');
  const [isOpen, setIsOpen] = useState(true);

  const toggleModule = (id) => {
    setModules(prev => prev.map(m => {
      if (m.id === id) {
        const newState = !m.enabled;
        if (newState) console.log(`[Armor Client] ${m.name} enabled: Executing logic...`);
        else console.log(`[Armor Client] ${m.name} disabled.`);
        return { ...m, enabled: newState };
      }
      return m;
    }));
  };

  const filteredModules = modules.filter(m => {
    const matchesSearch = m.name.toLowerCase().includes(searchQuery.toLowerCase());
    const matchesCat = selectedCategory === 'All' || m.category === selectedCategory;
    return matchesSearch && matchesCat;
  });

  const categories = ['All', 'Combat', 'Movement', 'Visual', 'World', 'Player'];

  if (!isOpen) return (
    <div className="fixed inset-0 flex items-center justify-center bg-black/20 backdrop-blur-sm">
      <button 
        onClick={() => setIsOpen(true)}
        className="px-6 py-3 bg-zinc-900 text-zinc-400 border border-zinc-800 rounded-full hover:bg-zinc-800 transition-all flex items-center gap-2 font-medium"
      >
        <Shield size={18} /> Open Armor Client
      </button>
    </div>
  );

  return (
    <div className="fixed inset-0 flex items-center justify-center p-4 font-sans text-zinc-300 select-none">
      {/* Main Window */}
      <div className="w-[850px] h-[550px] bg-zinc-950/90 backdrop-blur-xl border border-zinc-800 rounded-2xl shadow-2xl flex overflow-hidden">
        
        {/* Sidebar - Slim Minimalist */}
        <div className="w-64 bg-zinc-900/50 border-r border-zinc-800 flex flex-col">
          <div className="p-6 flex items-center gap-3">
            <div className="w-8 h-8 bg-white rounded-lg flex items-center justify-center">
              <Shield className="text-black" size={20} />
            </div>
              <span className="text-xl font-bold tracking-tight text-white">ARMOR <span className="text-zinc-500 font-light">CLIENT</span></span>
          </div>

          <div className="px-4 py-2">
            <div className="relative group">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-zinc-500 group-focus-within:text-white transition-colors" size={14} />
              <input 
                type="text" 
                placeholder="Search modules..." 
                className="w-full bg-zinc-800/50 border border-zinc-700 rounded-md py-2 pl-9 pr-4 text-xs focus:outline-none focus:border-zinc-500 transition-all"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
            </div>
          </div>

          <nav className="flex-1 px-3 py-4 space-y-1">
            {categories.map(cat => (
              <button 
                key={cat}
                onClick={() => setSelectedCategory(cat)}
                className={`w-full flex items-center justify-between px-3 py-2 rounded-md text-sm transition-all ${
                  selectedCategory === cat 
                  ? 'bg-zinc-800 text-white shadow-sm' 
                  : 'text-zinc-500 hover:bg-zinc-800/50 hover:text-zinc-300'
                }`}
              >
                <div className="flex items-center gap-3">
                  {cat === 'All' && <Layout size={16} />}
                  {cat === 'Combat' && <Zap size={16} />}
                  {cat === 'Movement' && <Move size={16} />}
                  {cat === 'Visual' && <Eye size={16} />}
                  {cat === 'World' && <Settings size={16} />}
                  {cat === 'Player' && <Crosshair size={16} />}
                  {cat}
                </div>
                {selectedCategory === cat && <ChevronRight size={14} />}
              </button>
            ))}
          </nav>

          <div className="p-4 border-t border-zinc-800 text-[10px] text-zinc-600 flex justify-between items-center">
            <span>v26.2 Fabric</span>
            <span className="text-zinc-400 font-mono">Stable</span>
          </div>
        </div>

        {/* Content Area */}
        <div className="flex-1 flex flex-col bg-zinc-950/40">
          {/* Header */}
          <div className="h-16 border-b border-zinc-800 flex items-center justify-between px-6">
            <h2 className="text-lg font-medium text-white">{selectedCategory} Modules</h2>
            <button 
              onClick={() => setIsOpen(false)}
              className="p-2 hover:bg-zinc-800 rounded-lg transition-colors text-zinc-500 hover:text-white"
            >
              <X size={20} />
            </button>
          </div>

          {/* Modules Grid */}
           <div className="p-6 overflow-y-auto grid grid-cols-2 gap-4 content-start">
             {filteredModules.map(mod => (
               <div 
                 key={mod.id}
                 className={`group p-4 rounded-xl border transition-all cursor-pointer ${
                   mod.enabled 
                   ? 'bg-zinc-900/80 border-zinc-700 shadow-lg' 
                   : 'bg-zinc-900/30 border-zinc-800/50 hover:border-zinc-700'
                 }`}
                 onClick={() => toggleModule(mod.id)}
               >
                 <div className="flex items-center justify-between mb-2">
                   <span className={`font-semibold text-sm ${mod.enabled ? 'text-white' : 'text-zinc-500'}`}>
                     {mod.name}
                   </span>
                   <div className={`w-8 h-4 rounded-full relative transition-colors ${mod.enabled ? 'bg-white' : 'bg-zinc-700'}`}>
                     <div className={`absolute top-1 w-2 h-2 bg-zinc-950 rounded-full transition-all ${mod.enabled ? 'right-1' : 'left-1'}`} />
                   </div>
                 </div>
                 <p className="text-xs text-zinc-500 leading-relaxed">
                   {mod.description}
                 </p>
                 {mod.enabled && (
                   <div className="mt-3 pt-3 border-t border-zinc-800 flex flex-col gap-2">
                     <div className="flex justify-between text-[10px] text-zinc-400">
                       <span>Status: <span className="text-green-500">Active</span></span>
                       <span>{mod.id === 'killaura' ? 'Range: 3.8' : mod.id === 'speed' ? 'Value: 2.0' : 'Default'}</span>
                     </div>
                   </div>
                 )}
               </div>
             ))}
            {filteredModules.length === 0 && (
              <div className="col-span-2 py-20 text-center text-zinc-600 italic">
                No modules found matching your criteria.
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ArmorClientGUI;
