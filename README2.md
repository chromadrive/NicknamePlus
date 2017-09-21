NicknamePlus
=====
A simple nicknaming plugin that supports tab list/chat/player nametag renaming. Right now it has fairly bare-bones functionality (add/delete names, that's about it) but hopefully that'll change soon. At least it works!

Requires [iTag](https://ci.herocc.com/job/iTag-API/) (or another TagAPI fork) to run. May require [ProtocolLib](https://www.spigotmc.org/resources/protocollib.1997/) depending on what other plugins you have - best to have it just in case. This plugin was made for a private server (and it works fine there tyvm) so support will be very limited.

Commands

    /nick [user] <nick>
    /delnick [user] <nick>

Permissions

    Set a nick name.
    nicky.set
    nicky.set.other

    Delete a nickname.
    nicky.del
    nicky.del.other

    Allow nicknames to have color.
    nicky.color