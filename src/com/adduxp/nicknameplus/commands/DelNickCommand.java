package com.adduxp.nicknameplus.commands;

import com.adduxp.nicknameplus.Nick;
import com.adduxp.nicknameplus.Nickname;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelNickCommand implements CommandExecutor
{
    Nickname plugin;

    public DelNickCommand( Nickname plugin )
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if( ! (sender instanceof Player) )
        {
            runAsConsole( args );
        }
        else if( args.length >= 1 )
        {
            runAsAdmin( sender, args );
        }
        else
        {
            runAsPlayer( sender );
        }

        return true;
    }

    @SuppressWarnings("deprecation")
    private void runAsConsole( String[] args )
    {
        if( args.length >= 1 )
        {
            Player receiver = plugin.getServer().getPlayer( args[0] );

            if( receiver == null )
            {
                plugin.log( "Could not find '" + args[0] + "', are you sure they are online?");
                return;
            }

            Nick nick = new Nick( plugin, receiver );

            nick.unLoadNick();

            receiver.sendMessage( Nickname.getPrefix() + "Your nickname has been deleted by console." );
            plugin.log( receiver.getName() + "'s nickname has been deleted!" );
        }
        else
        {
            plugin.log( "Usage: /delnick <name>" );
        }
    }

    @SuppressWarnings("deprecation")
    private void runAsAdmin( CommandSender sender, String[] args )
    {
        Player receiver = plugin.getServer().getPlayer( args[0] );

        if( receiver == null )
        {
            sender.sendMessage( Nickname.getPrefix() + "Could not find " + ChatColor.YELLOW + args[0] + ChatColor.GREEN + ", are you sure they are online?");
            return;
        }

        if( sender.hasPermission( "nickname.del.other" ) )
        {
            Nick nick = new Nick( plugin, receiver );

            nick.unLoadNick();

            receiver.sendMessage( Nickname.getPrefix() + "Your nickname has been deleted by " + ChatColor.YELLOW + sender.getName() );
            sender.sendMessage( Nickname.getPrefix() + ChatColor.YELLOW + receiver.getName() + ChatColor.GREEN + "'s nickname has been deleted!" );
        }
        else
        {
            sender.sendMessage( Nickname.getPrefix() + ChatColor.RED + "Sorry, you don't have permission to delete other players nicks." );
        }
    }

    private void runAsPlayer( CommandSender sender )
    {
        if( sender.hasPermission( "nickname.del" ) )
        {
            Nick nick = new Nick( plugin, (Player) sender );

            nick.unLoadNick();

            sender.sendMessage( Nickname.getPrefix() + "Your nickname has been deleted." );
        }
        else
        {
            sender.sendMessage( Nickname.getPrefix() + ChatColor.RED + "Sorry, you don't have permission to delete a nick." );
        }
    }
}