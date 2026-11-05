# Armor Client GUI port reference

The uploaded `armor-client-gui.jsx` is the visual and interaction reference for the native Minecraft screen. It is not included as executable mod code: React and browser DOM components cannot run inside a Fabric client.

## Required layout

- Slim left sidebar with the ARMOR CLIENT wordmark.
- Search field for filtering modules.
- Categories: All, Combat, Movement, Visual, World, Player.
- Main content area with a two-column module grid.
- Each module has a title, short description, and enabled/disabled switch.
- Close control in the top-right corner.

## Visual tokens

- Main background: Zinc 950 (`#09090b`).
- Borders: Zinc 800 (`#27272a`).
- Panel opacity target: 180–220 / 255.
- Rounded corners: 8–12 px where supported by the Minecraft UI rendering API.
- Muted zinc text for descriptions and inactive navigation; white for active elements.

## Initial sample modules from the supplied reference

Kill Aura, Velocity, Flight, ESP, AutoClicker, X-Ray, and Speed.

The initial port should preserve the UI structure and local toggle state. A toggle in this screen is only a UI state until a separately implemented, documented module is connected to it.
