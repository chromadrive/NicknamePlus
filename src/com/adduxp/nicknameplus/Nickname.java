package com.adduxp.nicknameplus;

import com.adduxp.nicknameplus.commands.DelNickCommand;
import com.adduxp.nicknameplus.commands.NickCommand;
import com.adduxp.nicknameplus.databases.MySQL;
import com.adduxp.nicknameplus.databases.SQL;
import com.adduxp.nicknameplus.databases.SQLite;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class Nickname extends JavaPlugin
{
    private static final String PREFIX = ChatColor.YELLOW + "[Nickname]" + ChatColor.GREEN + " ";

    private final Set<SQL> databases;
    private SQL database;

    public Nickname()
    {
        databases = new HashSet<SQL>();
    }

    @Override
    public void onEnable()
    {
        databases.add( new MySQL( this ) );
        databases.add( new SQLite( this ) );

        this.saveDefaultConfig(); // Makes a config is one does not exist.

        setupDatabase();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents( new PlayerListener( this ), this );

        getCommand( "nick" ).setExecutor( new NickCommand( this ) );
        getCommand( "delnick" ).setExecutor( new DelNickCommand( this ) );

        if( ! database.checkConnection() )
        {
            log( "Error with database" );
            pm.disablePlugin( this );
        }
    }

    public SQL getNickDatabase()
    {
        return database;
    }

    private boolean setupDatabase()
    {
        String type = getConfig().getString("type");

        database = null;

        for ( SQL database : databases )
        {
            if ( type.equalsIgnoreCase( database.getConfigName() ) )
            {
                this.database = database;

                log( "Database set to " + database.getConfigName() );

                break;
            }
        }

        if (database == null)
        {
            log( "Database type does not exist!" );

            return false;
        }

        return true;
    }

    public void log( String message ) {
        getLogger().info( message );
    }

    public static String getPrefix() { return PREFIX; }
}