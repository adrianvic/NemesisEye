# Eye of Nemesis
Eye of Nemesis is a plugin that allows server admins to write policies that will deny or allow (black/whitelist) players to do specific things based on the value of nodes.

## Warnings
- Even though running `/eye` will tell you to run `/eye help` to list all available commands, this is not implemented yet, however all commands are available as tab-complete of `/eye`.
- This plugin is in a very early stage.

## Motivations
I made this plugin as an effort to preserve a village from my private server. Originally from beta 1.7.3 Betalands server, then transferred to RetroMC, and then finally we downloaded the chunks to merge into our server, I was afraid it would not have the same feeling after all the updates, so I had the idea to make a plugin that can block the newer features.

## Performance
This plugin is not scalable as it is and will end up running unoptimized checks when your players do things with policies in effect, I made it for a server with a few friends. I'll look forward into writing more performant code after all my other priorities are implemented.
