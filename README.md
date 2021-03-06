# README #

This is the repository for the Fluidity Minecraft mod. The goal of this mod is to add content based on what mods are running alongside it. Think of it as an extreme integration layer, bridging gaps between mods and smoothing out inconsistencies.

### What is this repository for? ###

* This is a repository for the Fluidity Minecraft mod
* Fluidity is currently being developed for Minecraft 1.10.2 running [Forge 12.18.2.2116](http://adfoc.us/serve/sitelinks/?id=271228&url=http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.10.2-12.18.2.2116/forge-1.10.2-12.18.2.2116-mdk.zip)

### How do I get set up? ###

* First, clone the repo
* Second, open the command window and run gradle setupDecompWorkspace idea
* Third, open Fluidity.ipr in Idea

### Contribution guidelines ###

* I have a few simple guidelines for contributing to the mod:
* If you do something that requires a certain Forge version, and that version is higher than the one listed in mcmod.info, update the one in mcmod.info
* You cannot add any hard dependency(required mod) other than Forge. Any interaction with other mods must be designed with the other mods as soft-dependencies.
* When adding content, feel free to look through the extraassets folder to see if there are any textures or sounds you can use or modify for use with that content. You don't have to use something from that folder, but it may have something useful.

### Who do I talk to? ###

* [The_Fireplace](http://minecraft.curseforge.com/members/The_Fireplace)

### Contributors: ###
* The_Fireplace
