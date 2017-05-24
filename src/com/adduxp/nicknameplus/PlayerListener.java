package com.adduxp.nicknameplus;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;

public class PlayerListener implements Listener
{
    Nickname plugin;

    public PlayerListener( Nickname plugin )
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin( PlayerJoinEvent event )
    {
        Nick nick = new Nick( plugin, event.getPlayer() );

        nick.loadNick();
    }

    @EventHandler
    public void onExit( PlayerQuitEvent event )
    {
        Nick nick = new Nick( plugin, event.getPlayer() );

        nick.unLoadNick();
    }
}