<img width="1280" height="640" alt="eye_of_nemesis_social_cover" src="https://github.com/user-attachments/assets/a24bc92f-4dc2-4594-93c9-d056130f9695" />

![Modrinth link](https://img.shields.io/badge/Modrinth-Black?style=social&logo=Modrinth&logoColor=green&link=https%3A%2F%2Fmodrinth.com%2Fplugin%2Feye-of-nemesis%2F)
[![Build status badge](https://github.com/adrianvic/NemesisEye/actions/workflows/build.yml/badge.svg)](https://github.com/adrianvic/NemesisEye/actions/workflows/build.yml)
![GitHub Release](https://img.shields.io/github/v/release/adrianvic/NemesisEye?include_prereleases&style=flat&label=Latest%20Release)
![English Wiki Badge](https://img.shields.io/badge/English-White?style=flat-square&label=Wiki&color=black&link=https%3A%2F%2Fgithub.com%2Fadrianvic%2FNemesisEye%2Fwiki)
![Portuguese Wiki Badge](https://img.shields.io/badge/Portuguese-White?style=flat-square&label=Wiki&color=black&link=https%3A%2F%2Fmgr.rf.gd%2Fw%2FEye_of_Nemesis)

# Eye of Nemesis
Eye of Nemesis is a plugin that allows server admins to write policies that will deny or allow (black/whitelist) players to do specific things based on the value of nodes.

## Warnings
- This plugin is in a very early stage.
- Even though running `/eye` will tell you to run `/eye help` to list all available commands, this is not implemented yet, however all commands are available as tab-complete of `/eye`.

## Motivations
I made this plugin as an effort to preserve a village from my private server. Originally from beta 1.7.3 Betalands server, then transferred to RetroMC, and then finally we downloaded the chunks to merge into our server, I was afraid it would not have the same feeling after all the updates, so I had the idea to make a plugin that can block the newer features.

## Game version and loaders
Since version 1.0.3-SNAPSHOT, Eye of Nemesis has _reflection_, a technique that allows me to target multiple versions of the game while sharing the codebase across versions.

Currently, we support the following Minecraft versions/loaders:
- **PaperMC** `1.21, 1.21.1, 1.21.2, 1.21.3, 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8, 1.21.9, 1.21.10`
- **Bukkit** `b1.7.3 (CB1060)`

## Performance
This plugin is not scalable as it is and will end up running unoptimized checks when your players do things with policies in effect, I made it for a server with a few friends. I'll look forward into writing more performant code after all my other priorities are implemented.
