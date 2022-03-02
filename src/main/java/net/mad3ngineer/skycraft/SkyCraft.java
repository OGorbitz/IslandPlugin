package net.mad3ngineer.skycraft;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyCraft extends JavaPlugin {
	
	public static Plugin instance;
	public static DBInterface db;

	
	@Override
	public void onEnable() {
	    
		instance = this;
		
		this.saveDefaultConfig();

        SkyCraft.getInstance().getServer().getPluginManager().registerEvents(new PInterface(), SkyCraft.getInstance());

		db = new DBInterface();
		db.init(this);
		ClaimInterface.init();
        getCommand("island").setExecutor(new IslandCommand());
        getCommand("islandev").setExecutor(new IslandCommand());
		
	}
	
	public static DBInterface db(){
		
		return db;
		
	}
	
	@Override
	public void onDisable() {
		
	    getLogger().info("SkyCraft has been disabled!");
		
	}
	
	public void SetEnabled(boolean e){
		
		this.setEnabled(e);
		
	}
	
	public static Plugin getInstance(){
		
		return instance;
		
	}
	
	public static World getWorld(){
		
		return getInstance().getServer().getWorld(getInstance().getConfig().getString("worldname"));
		
	}

    public static Player getPlayer(String name){

        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getName().equalsIgnoreCase(name)){
                return p;
            }
        }

        return null;

    }
	
	public static void exit(){
		//If the plugin fails, call this function to close it.
		SkyCraft.getInstance().onDisable();
		SkyCraft.getInstance().getServer().getPluginManager().disablePlugin(getInstance());
		
	}
	
}
