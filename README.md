# Armor Client

Client-side Minecraft Java 26.2 mod built with Fabric.

## Features

- Native Minecraft GUI based on the supplied Armor Client JSX reference.
- Searchable module list and category filters (All, Combat, Movement, Visual, World, Player).
- Local UI toggles for the sample module cards.
- Open/close the menu with **Right Shift** (rebindable in Options → Controls).
- English and Brazilian Portuguese keybind labels.

## Build

Requirements:
- Java 25
- Gradle 9.5.1 (or a compatible Gradle installation)
- Minecraft 26.2 / Fabric toolchain configured in Gradle

Run:

```bash
gradle build
```

The JAR is generated in `build/libs/`.

## Important scope note

The supplied React/JSX file is a design reference; Fabric cannot execute React components. The interface has been ported to a native Java `Screen`.

The sample cards (Kill Aura, Velocity, Flight, ESP, AutoClicker, X-Ray and Speed) currently only demonstrate local UI state. They do not activate gameplay modules or communicate with servers. No item duplication, packet spoofing, or server-exploit behavior is included.
