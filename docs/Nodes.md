# Nodes
Nodes narrow down even further if an action can be done or not. A node needs _properties_ to know what it should check against, for example, a HIT node needs a _list_ of blocks that it will check if the player is holding; while a USE_ENCHANTMENT node needs a mapping of enchantment to level. Some nodes like glyde don't need a value and will ignore if you provide one.
```yaml
nodes:
  - [HIT]:
      values:
        - 'GOLD_SWORD'
  - [USE_ENCHANTMENT]:
      values:
        - "UNBREAKING": "1"
  - [GLYDE]
```

## Available nodes
### INTERACT
Triggered on `InteractionEvent`, returns true when the provided `list` contains the material used to interact.

### HIT
Triggered on `EntityDamageByEntityEvent`, returns true when the provided `list` contains the material used to hit.

### PLACE
Triggered on `BlockPlaceEvent`, returns true when the provided `list` contains the material being placed.

### BREAK
Triggered on `BlockBreakEvent`, returns true when the provided `list` contains the material used to break.

### EQUIP
Triggered on `InventoryClickEvent` and `PlayerInteractEvent` (only if item is an armor), returns true when the provided `list` contains the material being equipped.

### GLYDE
Triggered on `PlayerMoveEvent`, returns true if player is gliding.

### USE_ENCHANTMENT
Triggered on `BlockBreakEvent` and `onEntityDamageByEntityEvent`, returns true the used item has an enchantment matching the provided mapping of enchantment-level.