package net.mad3ngineer.skycraft;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SCOperations {
	
	//User Operations
	
	public static void home(Player player){
		
		SCPlayer scplayer = SkyCraft.db().getPlayer(player.getName());
		
		if(scplayer.hasIsland()){
			Island island = scplayer.getIsland();
			Location home = island.getHome();
			player.sendMessage(ChatColor.GREEN+"Teleporting to your island");
			player.teleport(home);
		}else{
			player.sendMessage(ChatColor.RED+"You do not have an island! Create one with "+ChatColor.GOLD+"/island create");
		}
		
	}
	
	public static void setHome(Player player){
		
		SCPlayer scplayer = SkyCraft.db().getPlayer(player.getName());
		if(scplayer.hasIsland()){
			if(scplayer.IslandRank<=SCPlayer.RANK_OFFICER){
			
				Island island = scplayer.getIsland();
				voxel ixy = new voxel();
				ixy.x = (int) island.x;
				ixy.y = (int) island.y;
			
				Location loc = player.getLocation();
				voxel xy = new voxel();
				xy.x = (int) loc.getX();
				xy.y = (int) loc.getZ();
			
				if(island.insideIsland(xy)){
					
					island.x = loc.getX();
					island.y = loc.getY();
					island.z = loc.getZ();
			    	island.save();
			    	player.sendMessage(ChatColor.GREEN+"Island home set!");
			    
				}else{
					player.sendMessage(ChatColor.RED+"You may only set island homes on your own island!");
				}
				
			}else{
				player.sendMessage(ChatColor.RED+"You are not a high enough rank to set your island's home");
			}
			
		}else{
			player.sendMessage(ChatColor.RED+"You have no island!");
		}
		
	}
	
	public static void setBiome(Player player, String biome){
		
		SCPlayer scp = SkyCraft.db().getPlayer(player.getName());
		scp.setBiome(biome);
		
	}
	
	public static void visit(Player player, String target){
		
		SCPlayer sctarget = SkyCraft.db().getPlayer(target);
		
		if(sctarget.hasIsland()){
			if(sctarget.getIsland().visitable){
				player.sendMessage(ChatColor.GREEN+"Tepeporting to "+target+"'s island");
				//player.sendMessage(ChatColor.BLUE+"Island Message:");
				//player.sendMessage(ChatColor.GREEN+sctarget.getIsland().message);
				sctarget.sendHome(player);
			}else{
				player.sendMessage(ChatColor.BLUE+target+"'s island is closed to visitors");
			}
		}else{
			player.sendMessage(ChatColor.RED+"That player doesn't have an island");
		}
		
	}
	
	public static void createIsland(Player player){
		
		SCPlayer scplayer = SkyCraft.db().getPlayer(player.getName());
		
		if(scplayer.hasIsland()){
			player.sendMessage(ChatColor.RED+"You already have an island! Delete it if you want to create a new one.");
		}else{
			scplayer.createIsland();
			player.sendMessage(ChatColor.GREEN+"Island Created!");
			scplayer.sendHome(player);
		}
		
	}
	
	public static void invitePlayer(Player player, String invited){
		
		SCPlayer scp = SkyCraft.db().getPlayer(player.getName());
		SCPlayer sci = SkyCraft.db().getPlayer(invited);
		
		if(scp.hasIsland()){
			sci.invite(scp.name);
			player.sendMessage(ChatColor.BLUE+"You have invited "+sci.name+" to your island!");
		}else{
			player.sendMessage(ChatColor.RED+"You can only invite players if you have an island!");
		}
		
	}
	
	public static void acceptInvite(Player player){
		
		SkyCraft.db().getPlayer(player.getName()).accept();
		
	}
	
	public static void declineInvite(Player player){
		
		SkyCraft.db().getPlayer(player.getName()).decline();
		
	}
	
	public static void kickPlayer(Player player, String target){
		
		SkyCraft.db().getPlayer(player.getName()).kickPlayer(target);
		
	}
	
	public static void deleteIsland(Player player){
		
		SkyCraft.db().getPlayer(player.getName()).deleteIsland();
		
	}
	
	//Dev Operations
	
	public static void tp(Player player, String target){
		
		SCPlayer p = SkyCraft.db().getPlayer(target);
		
		if(p.hasIsland()){
			player.sendMessage(ChatColor.GREEN+"Teleporting to "+target+"'s island");
			SkyCraft.db().getPlayer(target).sendHome(player);
		}else{
			player.sendMessage(ChatColor.RED+"That person doesn't have an island");
		}
		
	}
	
	public static void transferIsland(Player player, String player1, String player2){
		
		SCPlayer owner = SkyCraft.db().getPlayer(player1);
		SCPlayer newowner = SkyCraft.db().getPlayer(player2);
		Island island = owner.getIsland();
		
		if(owner.hasIsland()){
			if(!newowner.hasIsland()){
				player.sendMessage(ChatColor.GREEN+"Island transferred from "+player1+" to "+player2+".");
				island.addMember(newowner);
				island.setOwner(player2);
			}else{
				player.sendMessage(ChatColor.RED+player2+" already has an island");
			}
		}else{
			player.sendMessage(ChatColor.RED+player1+" has no island to transfer");
		}
			
	}
	
	public static void deleteIsland(Player player, String target){
		//Admin command to delete island
		SCPlayer t = SkyCraft.db().getPlayer(target);
		
		if(t.hasIsland()){
			t.deleteIsland();
			player.sendMessage(ChatColor.GREEN+target+"'s island has been deleted!");
		}else{
			player.sendMessage(ChatColor.RED+target+" has no island!");
		}
		
	}
	
	public static void generateBlank(int num){
		
		for(int i = 1; i <= num; i++){
			
			IslandFactory.createNewIsland("ownedbyscience");
			
		}
		
	}

	public static void visitable(Player player) {
		
		SCPlayer scp = SkyCraft.db().getPlayer(player.getName());
		if(scp.hasIsland()){
			if(scp.IslandRank>=SCPlayer.RANK_OFFICER){
				scp.getIsland().toggleVisitable(scp);
			}else{
				player.sendMessage(ChatColor.RED+"You are not a high enough rank to do this");
			}
		}else{
			player.sendMessage(ChatColor.RED+"You do not have an island!");
		}
		
	}

    public static void leaveIsland(Player player) {

        SCPlayer scp = SkyCraft.db().getPlayer(player.getName());
        if(scp.hasIsland()){
            if(scp.IslandRank == SCPlayer.RANK_OWNER){
                player.sendMessage(ChatColor.RED+"You are the owner! You must assign someone else as the owner to leave this island!");
            }else{
                scp.leaveIsland();
            }
        }

    }

    public static void getInfo(Player player) {

        final int scale = SkyCraft.getInstance().getConfig().getInt("islandsize");
        int halfscale;

        if(player.getLocation().getX() >= 0) {
            halfscale = (scale / 2);
        }else{
            halfscale = -(scale / 2);
        }
        int x = (int) (player.getLocation().getX() + halfscale) / scale;

        if(player.getLocation().getZ() >= 0) {
            halfscale = (scale / 2);
        }else{
            halfscale = -(scale / 2);
        }
        int y = (int) (player.getLocation().getZ() + halfscale) / scale;

        String coords = "" + x + ";" + y;
        Island i = SkyCraft.db().getIsland(coords);

        player.sendMessage(ChatColor.BLUE+"Island at location " + x + ", " + y + " belongs to " + i.owner);

        if(i.members.size()>=1) {
            String members = new String();
            for (String s : i.members) {

                members = members + s + ", ";

            }
            player.sendMessage(ChatColor.BLUE + "" + i.members.size() + " members: " + ChatColor.YELLOW + members);
        }

    }
}
