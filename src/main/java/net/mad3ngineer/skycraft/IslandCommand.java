package net.mad3ngineer.skycraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class IslandCommand implements CommandExecutor{
	
	String userperm = "skycraft.island.";
	String devperm = "skycraft.dev.";
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length < 1){
			
			return false;
			
		}

		if (sender instanceof Player) {
			
	        Player player = (Player) sender;
	        
	        if(cmd.getName().equalsIgnoreCase("island")){
	        	
	        	if(args[0].equalsIgnoreCase("sethome")){
	        		if(player.hasPermission(userperm+"sethome")){
	        			SCOperations.setHome(player);
	        			return true;
	        		}else{
	        			player.sendMessage(ChatColor.RED+"You do not have permission!");
	        			return true;
	        		}
	        	}
	        	
	        	else if(args[0].equalsIgnoreCase("visitable")){
	        		if(player.hasPermission(userperm+"visitable")){
	        			SCOperations.visitable(player);
	        			return true;
	        		}
	        	}
	        	
	        	else if(args[0].equalsIgnoreCase("visit")){
	        		if(player.hasPermission(userperm+"visit")){
	        			if(args.length>=2){
	        				SCOperations.visit(player, args[1]);
	        				return true;
	        			}else{
	        				player.sendMessage(ChatColor.RED+"Usage: /island visit <player>");
	        				return true;
        				}
	        		}else{
	        			player.sendMessage(ChatColor.RED+"You do not have permission!");
	        			return true;
	        		}
	        	}
	        	
	        	else if(args[0].equalsIgnoreCase("home")){
	        		if(player.hasPermission(userperm+"home")){
	        		    SCOperations.home(player);
	        		    return true;
	        		}else{
	        			player.sendMessage(ChatColor.RED+"You do not have permission!");
	        			return true;
	        		}
	        	}
	        	
	        	else if(args[0].equalsIgnoreCase("create")){
	        		if(player.hasPermission(userperm+"create")){
	        			SCOperations.createIsland(player);
	        			return true;
	        		}else{
	        			player.sendMessage(ChatColor.RED+"You do not have Permission!");
	        			return true;
	        		}
	        	}
	        	
	        	else if(args[0].equalsIgnoreCase("delete")){
	        		if(player.hasPermission(userperm+"delete")){
	        			SCOperations.deleteIsland(player);
	        			player.sendMessage(ChatColor.GREEN+"Island deleted!");
	        			return true;
	        		}else{
	        			player.sendMessage(ChatColor.RED+"You do not have Permission!");
	        			return true;
	        		}
	        	}
	        	
	        	else if(args[0].equalsIgnoreCase("setbiome")){
	        		if(player.hasPermission(userperm+"biome")){
	        			if(args.length>=2){
	        				SCOperations.setBiome(player, args[1]);
	        			}else{
	        				player.sendMessage(ChatColor.RED+"Usage: /island setbiome");
	        				player.sendMessage(ChatColor.AQUA+"Valid biomes are: "+ChatColor.YELLOW+"DESERT, "+ChatColor.GREEN+"FOREST, "+ChatColor.BLUE+"OCEAN, "+ChatColor.WHITE+"TAIGA, "+ChatColor.GREEN+"PLAINS, "+ChatColor.DARK_GREEN+"JUNGLE, "+ChatColor.GRAY+"MUSHROOM_ISLAND"); 
	        				return true;
	        			}
	        			return true;
	        		}else{
	        			player.sendMessage(ChatColor.RED+"You do not have permission!");
	        			return true;
	        		}
	        	}
	        	
	        	else if(args[0].equalsIgnoreCase("kick")){
	        		if(player.hasPermission(userperm+"kick")){
	        			if(args.length>=2){
	        				SCOperations.kickPlayer(player, args[1]);
	        				return true;
	        			}else{
	        				player.sendMessage(ChatColor.RED+"Usage: /island kick <player>");
	        				return true;
        				}
	        		}else{
	        			player.sendMessage(ChatColor.RED+"You do not have Permission!");
	        			return true;
	        		}
	        	}
	        	
	        	else if(args[0].equalsIgnoreCase("invite")){
	        		if(player.hasPermission(userperm+"invite")){
	        			if(args.length>=2){
	        				SCOperations.invitePlayer(player, args[1]);
	        				return true;
	        			}else{
	        				player.sendMessage(ChatColor.RED+"Usage: /island invite <player");
	        				return true;
	        			}
	        		}else{
	        			player.sendMessage(ChatColor.RED+"You do not have Permission!");
	        			return true;
	        		}
	        	}
	        	
	        	else if(args[0].equalsIgnoreCase("accept")){
	        		if(player.hasPermission(userperm+"accept")){
	        			SCOperations.acceptInvite(player);
	        			return true;
	        		}else{
	        			player.sendMessage(ChatColor.RED+"You do not have Permission!");
	        			return true;
	        		}
	        	}
	        	
	        	else if(args[0].equalsIgnoreCase("decline")){
	        		if(player.hasPermission(userperm+"decline")){
	        			SCOperations.declineInvite(player);
	        			return true;
	        		}else{
	        			player.sendMessage(ChatColor.RED+"You do not have Permission!");
	        			return true;
	        		}
	        	}

                else if(args[0].equalsIgnoreCase("leave")){
                    if(player.hasPermission(userperm+"leave")) {
                        SCOperations.leaveIsland(player);
                        return true;
                    }else{
                        player.sendMessage(ChatColor.RED+"You do not have Permission!");
                    }
                }
	        	
	        }else if(cmd.getName().equalsIgnoreCase("islandev")){
	        	
	        	if(args[0].equalsIgnoreCase("tp")){
	        		if(player.hasPermission(devperm+"tp")){
	        			SCOperations.tp(player, args[1]);
	        			return true;
	        		}else{
	        			player.sendMessage(ChatColor.RED+"You do not have permission!");
	        			return true;
	        		}
	        	}else if(args[0].equalsIgnoreCase("transfer")){
	        		//Transfer an island from player 1 to player 2
	        		if(3<=args.length){
	        			String player1 = args[1];
	        			String player2 = args[2];
	        			if(player.hasPermission(devperm+"transfer")){
	        				SCOperations.transferIsland(player, player1, player2);
	        				return true;
	        			}else{
	        				player.sendMessage(ChatColor.RED+"You do not have permission!");
	        				return true;
	        			}
	        		}else{//Not enough arguments
	        			return false;
	        		}
	        	}else if(args[0].equalsIgnoreCase("delete")){
	        		//Delete "player"'s island. Usage: /islandev delete <player>
	        		if(2<=args.length){ //Check if there are enough arguments
	        			if(player.hasPermission(devperm+"delete")){
	        				SCOperations.deleteIsland(player, args[1]);
	        				return true;
	        			}else{
	        				player.sendMessage(ChatColor.RED+"You do not have permission!");
	        				return true;
	        			}
	        		}else{//if loop failed, there werent enough arguments
	        			return false;
	        		}
	        	}else if(args[0].equalsIgnoreCase("generate")){
	        		if(2<=args.length){
	        			if(player.hasPermission(devperm+"generate")){
	        				try{
	        					SCOperations.generateBlank(Integer.parseInt(args[1]));
	        					player.sendMessage(ChatColor.GREEN+args[1]+" islands generated!");
	        					return true;
	        				}catch(Exception e){
	        					player.sendMessage(ChatColor.RED+"Invalid format! /islandev generate <number>");
	        					return true;
	        				}
	        			}else{
	        				player.sendMessage(ChatColor.RED+"You do not have permission!");
	        				return true;
	        			}
	        		}else{//if loop failed, there werent enough arguments
	        			return false;
	        		}
	        	}else if(args[0].equalsIgnoreCase("setspawn")){
                    if(2<=args.length) {
                        if (player.hasPermission(devperm + "setspawn")) {
                            try {
                                SCOperations.setHome(SkyCraft.getPlayer(args[1]));
                                player.sendMessage(ChatColor.GREEN + "Island home set for player " + args[1]);
                                return true;
                            } catch (Exception e) {
                                player.sendMessage(ChatColor.RED + "Could not find player " + args[1] + " on the server.");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "You do not have permission!");
                            return true;
                        }
                    }else{
                        player.sendMessage(ChatColor.RED + "Invalid command format");
                        player.sendMessage(ChatColor.RED + "Proper usage: /islandev setspawn <player>");
                    }
                }else if(args[0].equalsIgnoreCase("info")){
                    if(player.hasPermission(devperm + "info")){
                        try{
                            SCOperations.getInfo(player);
                            return true;
                        }catch(Exception e){
                            SkyCraft.getInstance().getLogger().warning("Could not get island info at " + player.getDisplayName() + "'s position.");
                        }
                    }else{
                        player.sendMessage(ChatColor.RED + "You do not have permission!");
                        return true;
                    }
                }

	        }
	        
	    } else {
	    	
	        sender.sendMessage("Cannot run this command from console. Console commands will be implemented later.");
	        return false;
	        
	    }
		
	    return false;
		
	}

}
