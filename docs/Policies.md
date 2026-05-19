# Policies
Policies are a list of nodes with a specific condition to be met, they also determine what effect a node is going to have. Below is an example policy:
```yaml
  - name: "Allow example"
    type: "location"
    effect: ALLOW
    weight: 1
    worlds: [world]
    locations:
      coordinates:
        - corner1: { x: 2100, y: 256, z: 1400 }
          corner2: { x: 1000, y: -64, z: 2200 }
    nodes:
      - [INTERACT, BREAK, HIT, PLACE]:
          values:
            - AIR
```

## Properties
### `name`
Name of the policy, must not contain spaces or else commands that require you to type the policy name will not work.

Example: `name: "disable-axe-damage"`

### `type`
One of the policy [types](#Types).

Example: `type: location`

### `effect`
Can be `deny` to block the action if a node check returns true, `ALLOW` to revert `DENY` effects if the node check is true or `ALLOWONLY` to allow the action **only** if the node check returns true.

Example: `effect: DENY`

### `weight`
A policy with greater weight will override a conflicting policy of lower weight.

Example: `weight: 0`

### `nodes`
Must contain a list of node mappings to evaluate if the policy applies to a player.

Example:
```yaml
nodes:
  - [HIT]:
      values:
        - '.*_AXE$'
```

### `locations`
Only used for `type: "location"`, is a list of mappings where corner1 and corner2 should map x, y and z to two corners of a rectangular area where the policy will take effect; `worlds` may be defined here.

Example:

```yaml
locations:
  worlds: [world]
  coordinates:
    - corner1: { x: 2100, y: 256, z: 1400 }
      corner2: { x: 1000, y: -64, z: 2200 }
```

### `permissions`
Only used for `type: "permission"`, is a list of permissions.

Example:

```yaml
permissions:
  - "server.axehit"
```

### `names`
Only used for `type: "playerName"`, is a list of player names.

## Types

### `global`
Always matches.

### `location`
Matches if the player location is inside any rectangle defined in the `locations` property.

### `playerName`
Matches if the player name is in the `names` property.

### `permission`
Matches if the player has a permission in the `permissions` property.
