package com.adduxp.nicknameplus.commands;

import com.adduxp.nicknameplus.Nick;
import com.adduxp.nicknameplus.Nickname;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor
{
    Nickname plugin;

    public NickCommand( Nickname plugin )
    {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if( ! (sender instanceof Player) )
        {
            runAsConsole( args );
        }
        else if( args.length >= 2 )
        {
            runAsAdmin( sender, args );
        }
        else
        {
            runAsPlayer( sender, args );
        }

        return true;
    }

    @SuppressWarnings("deprecation")
    private void runAsConsole( String[] args )
    {
        if( args.length >= 2  )
        {
            Player receiver = plugin.getServer().getPlayer( args[0] );

            if( receiver == null )
            {
                plugin.log( "Could not find '" + args[0] + "', are you sure they are online?");
                return;
            }

            String nname = ChatColor.translateAlternateColorCodes( '&', args[1] );

            Nick nick = new Nick( plugin, receiver );

            nick.setNick( nname );
            nick.loadNick();

            receiver.sendMessage( Nickname.getPrefix() + "Your nickname has been set to " + ChatColor.YELLOW + nname + ChatColor.GREEN + " by console!" );
            plugin.log( receiver.getName() + "'s nick has been set to " + nname );
        }
        else
        {
            plugin.log( "Usage: /nick <player> <nickname>" );
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

        String nname = args[1];

        if( sender.hasPermission( "nickname.set.other" ) )
        {
            if( sender.hasPermission( "nickname.color" ) )
            {
                nname = ChatColor.translateAlternateColorCodes( '&', nname );
            }

            Nick nick = new Nick( plugin, receiver );

            nick.setNick( nname );
            nick.loadNick();

            receiver.sendMessage( Nickname.getPrefix() + "Your nickname has been set to " + ChatColor.YELLOW + nname + ChatColor.GREEN + " by " + ChatColor.YELLOW + sender.getName() + ChatColor.GREEN + "!" );
            sender.sendMessage( Nickname.getPrefix() + "You have set " + ChatColor.YELLOW + sender.getName() + ChatColor.GREEN + "'s nname to " + ChatColor.YELLOW + nname + ChatColor.GREEN + "." );
        }
        else
        {
            sender.sendMessage( Nickname.getPrefix() + ChatColor.RED + "Sorry, you don't have permission to set other players nicks." );
        }
    }

    private void runAsPlayer( CommandSender sender, String[] args )
    {
        Player player = (Player) sender;

        if( sender.hasPermission( "nickname.set" ) )
        {
            if( args.length >= 1 )
            {
                String nname = args[0];

                if( sender.hasPermission( "nickname.color" ) )
                {
                    nname = ChatColor.translateAlternateColorCodes( '&', nname );
                }

                Nick nick = new Nick( plugin, player );

                nick.setNick( nname );
                nick.loadNick();

                player.sendMessage( Nickname.getPrefix() + "Your nickname has been set to " + ChatColor.YELLOW + nname + ChatColor.GREEN + " !" );
            }
            else
            {
                player.sendMessage( "To set a nick do " + ChatColor.YELLOW + "/nick <nickname>" );
            }
        }
        else
        {
            player.sendMessage( Nickname.getPrefix() + ChatColor.RED + "Sorry, you don't have permission to set a nick." );
        }
    }
}