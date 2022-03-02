package net.mad3ngineer.skycraft;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PInterface implements Listener{

	static ArrayList<SCPlayer> players;
	
	public static void init() {
		
		players = new ArrayList<SCPlayer>();
		for(Player p : SkyCraft.getInstance().getServer().getOnlinePlayers()){
			players.add(SkyCraft.db().getPlayer(p.getName()));
		}
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		
		for(SCPlayer p:players){
			if(p.name.equals(event.getPlayer().getName())){
				players.remove(p);
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event){
		
		for(SCPlayer p:players){
			if(p.name.equals(event.getPlayer().getName())){
				players.remove(p);
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){

        try {
            players.add(SkyCraft.db().getPlayer(event.getPlayer().getName()));
        }catch(Exception e){
            SkyCraft.getInstance().getServer().getLogger().log(Level.SEVERE, "PLAYERJOINEVENT FAILED");
            SkyCraft.getInstance().getServer().getLogger().log(Level.SEVERE, e.getMessage());
        }
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		
		if(checkWorld(event.getBlock().getLocation().getWorld().getName())){
			
			Player player = event.getPlayer();
			Block block = event.getBlock();
			
			for(SCPlayer p : players){
				
				if(p.name.equals(player.getName())){
					if(!inside(block, p)){
						if(!player.hasPermission("skycraft.islandev.edit")){
							event.setCancelled(true);
							player.sendMessage(protect());
						}
					}
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onBlockExplode(EntityExplodeEvent event){
		
		if(checkWorld(event.getLocation().getWorld().getName())){
			
			event.blockList().clear();
			
		}
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		try {
            if (event.getClickedBlock() != null) {
                if (checkWorld(event.getClickedBlock().getLocation().getWorld().getName())) {

                    Player player = event.getPlayer();
                    Block block = event.getClickedBlock();

                    for (SCPlayer p : players) {

                        if (p.name.equals(player.getName())) {
                            if (!inside(block, p)) {
                                if (!player.hasPermission("skycraft.islandev.edit")) {
                                    event.setCancelled(true);
                                    player.sendMessage(protect());
                                }
                            }
                        }

                    }

                }
            }
        }catch(Exception e){
            SkyCraft.getInstance().getServer().getLogger().log(Level.SEVERE, "PLAYERINTERACTEVENT FAILED:");
            SkyCraft.getInstance().getServer().getLogger().log(Level.SEVERE, e.getMessage());
        }
	}
	
	@EventHandler
	public void onPlayerDamageByEntity(EntityDamageByEntityEvent event){
		
		if(checkWorld(event.getEntity().getWorld().getName())){
			
			if(event.getDamager() instanceof Player){
				
				Player player = (Player) event.getDamager();
				Entity entity = event.getEntity();
			
				for(SCPlayer p : players){
				
					if(p.name.equals(player.getName())){
						if(!inside(entity.getLocation().getBlock(), p)){
							
							EntityType[] mobs = {EntityType.CHICKEN,EntityType.COW,EntityType.HORSE,EntityType.ITEM_FRAME,EntityType.LEASH_HITCH,EntityType.MINECART,EntityType.MINECART_CHEST,EntityType.MINECART_FURNACE,EntityType.MINECART_HOPPER,EntityType.MINECART_MOB_SPAWNER,EntityType.MINECART_TNT,EntityType.MUSHROOM_COW,EntityType.OCELOT,EntityType.PAINTING,EntityType.PIG,EntityType.SHEEP,EntityType.SNOWMAN,EntityType.SQUID,EntityType.VILLAGER,EntityType.WOLF};
							
							for(EntityType t:mobs){
							
								if(entity.getType().equals(t)){
									if(!player.hasPermission("skycraft.islandev.edit")){
										event.setCancelled(true);
										player.sendMessage(protect());
									}
								}
							}
						}
					}
				
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerBucketFill(PlayerBucketFillEvent event){
		
		if(checkWorld(event.getBlockClicked().getWorld().getName())){
			
			Player player = event.getPlayer();
			Block block = event.getBlockClicked();
			
			for(SCPlayer p : players){
				
				if(p.name.equals(player.getName())){
					if(!inside(block, p)){
						if(!player.hasPermission("skycraft.islandev.edit")){
							event.setCancelled(true);
							player.sendMessage(protect());
						}
					}
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event){
		
		if(checkWorld(event.getBlockClicked().getWorld().getName())){
			
			Player player = event.getPlayer();
			Block block = event.getBlockClicked();
			
			for(SCPlayer p : players){
				
				if(p.name.equals(player.getName())){
					if(!inside(block, p)){
						if(!player.hasPermission("skycraft.islandev.edit")){
							event.setCancelled(true);
							player.sendMessage(protect());
						}
					}
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onIslandEvent(IslandEvent event){
		try {
            for (SCPlayer p : players) {
                if (p.name.equals(event.getPlayerName())) {
                    players.remove(p);
                }
            }

            players.add(SkyCraft.db().getPlayer(event.getPlayerName()));
        }catch (Exception e){
            SkyCraft.getInstance().getServer().getLogger().log(Level.SEVERE, "ISLANDEVENT FAILED");
            SkyCraft.getInstance().getServer().getLogger().log(Level.SEVERE, e.getMessage());
        }
	}
	
	public static String protect(){
		
		return SkyCraft.getInstance().getConfig().getString("protected");
		
	}
	
	public static boolean checkWorld(String world){
		
		if(world.equals(SkyCraft.getInstance().getConfig().getString("worldname"))){
			return true;
		}
		return false;
		
	}
	
	public static boolean inside(Block block, SCPlayer p){
		
		Location min = p.getLowCorner().toLocation();
		Location max = p.getHighCorner().toLocation();
		
		if(p.hasIsland()){
			if(min.getBlockX()<block.getX()&&block.getX()<max.getBlockX()){
				if(min.getBlockZ()<block.getZ()&&block.getZ()<max.getBlockZ()){
					return true;
				}
			}
		}
		return false;
		
	}

}
