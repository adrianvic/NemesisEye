> [!IMPORTANT]
> This project is in a very early stage, expect bugs until `Release 1.0`.
>
>  Documentation will soon be moved from README.md.

# Eye of Nemesis
Eye of Nemesis is a plugin that allows server admins to write [policies](#Policies) that will deny or allow (black/whitelist) players to do specific things based on the value of [nodes](#Nodes).

## Warnings
- Even though running `/eye` will tell you to run `/eye help` to list all available commands, this is not implemented yet, however all commands are available as tab-complete of `/eye`.
- This plugin is in a very early stage.

## Policies
Policy is a structure that holds nodes and tell them _where_ to act. For example, a Location policy will tell its child nodes that they work from coordinates `x1 y1 z1` to `x2 y2 z2`.

Currently, the only policy type is Location.

## Nodes
Nodes are specific rules that rely on it's value to know _when_ to act. For example, a useItem policy with value `cookie` will prevent users from doing anything with a cookie.

### `useItem`
**Triggered:** breaking/placing/interacting with anything using this item.

**Expects:** list of strings.

### `useEnchantment`
**Triggered:** breaking/placing/interacting with anything using an item with the specified enchantment, **will allow items without enchantment even on allowlist mode**.

**Expects:** map of _string: string_.

### `attackWith`
**Triggered:** attacking with this item in hand.

**Expects:** list of strings.

## Performance
This plugin is not scalable as it is and will run unoptimized checks when your players do certain things in the server if you have policies enabled, I made it for a server with a few friends.

For every policy there's a check, for every matching policy there are its child nodes, each one introducing new checks. Keep that in mind.
