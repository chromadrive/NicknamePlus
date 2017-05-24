package com.adduxp.nicknameplus;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener
{
    Nickname plugin;

    public PlayerListener( Nickname plugin )
    {
        this.plugin = plugin;
    }

    public void onJoin( PlayerJoinEvent event )
    {
        Nick nick = new Nick( plugin, event.getPlayer() );

        nick.loadNick();
    }
}