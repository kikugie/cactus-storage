<center><div>
<img alt="logo" width="256" src="https://cdn.modrinth.com/data/bUuQYcGw/216c6586a0774769232bf6f49c505bd4c3f87163.png">

<h1>Cactus storage</h1>
<p>
<b>An absurd eco-friendly infinite interconnected transdimensional storage solution.</b>
</p>
<a href="https://modrinth.com/mod/cactus-storage">
<img alt="modrinth" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/modrinth_vector.svg">
</a>

[//]: # (<a href="https://www.curseforge.com/minecraft/mc-mods/cactus-storage">)

[//]: # (<img alt="curseforge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/curseforge_vector.svg">)

[//]: # (</a>)

<a href="https://github.com/kikugie/cactus-storage">
<img alt="github" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/available/github_vector.svg">
</a>

<br>

<img alt="fabric" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/supported/fabric_vector.svg">
<img alt="quilt" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/supported/quilt_vector.svg">
<img alt="forge" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/unsupported/forge_vector.svg">

<hr>
</div></center>

## Description
This mod brings an old Storage Tech community meme to life. With it enabled any item destroyed by a cactus will be stored globally, and can be extracted using hoppers.  

*Requires [Carpet mod](https://modrinth.com/mod/carpet)*

## Carpet settings
### `cactusStorage`
Enable or disable cactus storage functionality.
- Type: `Boolean`
- Categories: `CACTUS`, `FEATURE`
- Options: `true`, `false`
- Default: `false`

### `allowCactusOnHoppers`
Allow cacti to be placed on top of hoppers.
- Type: `Boolean`
- Categories: `CACTUS`, `FEATURE`
- Options: `true`, `false`
- Default: `false`

### `dropCactusContents`
Drop ALL cactus storage contents when a cactus is broken by a player without silk touch.
Doesn't affect creative mode.
- Type: `Boolean`
- Categories: `CACTUS`, `FEATURE`
- Options: `true`, `false`
- Default: `false`

### `alwaysDropCactusContents`
Drop cactus contents when any cactus is broken.
- Type: `Boolean`
- Categories: `CACTUS`, `FEATURE`
- Options: `true`, `false`
- Default: `false`

## Implementation details
To support practically infinite capacity, cactus doesn't actually store items in stacks, which means they are unordered inside the storage.  
When a hopper tries to pull an item into an empty slot, it will choose a random one from the storage. The chosen item depends on an RNG, not hashmap key order. When first available slot has an item, it will try to pull the same one.

If multiple hoppers pull items from the storage, changes are processed sequentialy for each ticked hopper.

## Reviews
### Enjarai:
> Cactus is black hole as deep as your mother.

### m³:
> Enderstorage lol.

### Crec0:
> I am not chat gpt.

### Inspector Talon:
> You had me at cart where do I sign.

### Moony:
> That's the funniest fucking thing ive ever read.

### Obi:
> Really fucking cool. Surprised nobody has done this before.

### Ners:
> Not original, Raysworks invented cactus storage.

### Ners (alter ego):
> They have threatened me to not speak.

### Gelo1238:
> Wait what.

### Oriol:
> ME REPRESENTAAAA this feels so catibular, doesn't it? Me he fumado una alcachofa!

### Ilmango:
> *Your message could not be delivered. This is usually because you don't share a server with the recipient or the recipient is only accepting direct messages from friends.*

### ChatGPT:
> In your inventive Minecraft mod, you've leveraged the humor of 'cactus storage' to create a globally accessible system where items can be stored within cacti and retrieved anywhere using hoppers and minecarts.
