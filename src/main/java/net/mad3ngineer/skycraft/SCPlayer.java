package net.mad3ngineer.skycraft;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SCPlayer {

	public static int RANK_OWNER = 1;
	public static int RANK_OFFICER = 2;
	public static int RANK_MEMBER = 3;
	public static int HAS_ISLAND = 1;
	public static int NO_ISLAND = 0;
	public String name;
	public String invited;
	public int hasIsland;
	public int IX;
	public int IY;
	public int IslandRank;
	
	public Island getIsland(){
		
		if(!this.hasIsland()){
			return null;
		}else{
			return SkyCraft.db().getIsland(this.IX+";"+this.IY);
		}
		
	}
	
	public void kickPlayer(String name){
		
		if(this.IslandRank<=SCPlayer.RANK_OFFICER){
			getIsland().removeMember(SkyCraft.db().getPlayer(name));
			sendMessage(ChatColor.DARK_AQUA+"Player kicked from your island");
		}else{
			sendMessage(ChatColor.RED+"You are not a high enough rank to kick that person!");
		}
		
	}
	
	public void save(){
		
		if(!name.equals(""));
			SkyCraft.db().updatePlayer(this);

        SkyCraft.getInstance().getServer().getPluginManager().callEvent(new IslandEvent(this.name, this.IX, this.IY, "kick"));
		
	}
	
	public void leaveIsland(){
		
		this.hasIsland = SCPlayer.NO_ISLAND;
        this.IX = 0;
        this.IY = 0;

        this.save();

		Location spawn = SkyCraft.getInstance().getServer().getWorld(SkyCraft.getInstance().getConfig().getString("spawnworld")).getSpawnLocation();
		this.getPlayer().teleport(spawn);
		
	}
	
	public void deleteIsland(){
		
		if(this.IslandRank==SCPlayer.RANK_OWNER){
			this.getIsland().delete();
		}else{
			sendMessage(ChatColor.RED+"You can only delete your own island!");
		}
		
	}
	
	public void sendHome(Player p){
		
		if(this.hasIsland()){
			p.teleport(this.getIsland().getHome());
		}
		
	}
	
	public boolean hasIsland(){
		
		if(this.hasIsland==SCPlayer.HAS_ISLAND){
			return true;
		}else{
			return false;
		}
		
	}
	
	public void setBiome(String name){
		
		if(this.IslandRank<=SCPlayer.RANK_OFFICER){
			this.getIsland().setBiome(name, this.name);
            this.sendMessage(ChatColor.GREEN+"Island biome changed to " + name);
            this.sendMessage(ChatColor.YELLOW+"Please relog to update your island. This is required due to the limitations of minecraft's engine.");
		}else{
			this.sendMessage(ChatColor.RED+"You are not a high enough rank to set your island's biome type!");
		}
		
	}

    public Player getPlayer(){

        return SkyCraft.getPlayer(this.name);

    }
	
	public void sendMessage(String message){
		
		try{
			getPlayer().sendMessage(message);
		}catch(Exception e){

		}
		
	}
	
	public void invite(String name){
		
		this.invited = name;
		sendMessage(ChatColor.BLUE + "You have been invited to join " + name + "'s island! Type /island <" + ChatColor.GREEN + "accept " + ChatColor.YELLOW + "|" + ChatColor.RED + " deny" + ChatColor.BLUE + "> to respond to the invitation.");
                save();
		
	}
	
	public void decline(){
		
		if(this.invited.equals("")){
			sendMessage(ChatColor.RED+"You have no invitations");
		}else{
			sendMessage(ChatColor.DARK_RED+"You declined the invitation from "+this.invited);
			this.invited = "";
		}
		
	}
	
	public void accept(){
		
		if(!this.invited.equals("")){
			if(this.hasIsland()){
				sendMessage(ChatColor.RED+"You already have an island! Delete it to join someone");
			}else{
				SCPlayer joining = SkyCraft.db().getPlayer(this.invited);
				if(joining.hasIsland()){
					sendMessage(ChatColor.GREEN+"You joined "+this.invited+"'s island!");
					SkyCraft.db().getPlayer(this.invited).getIsland().addMember(this);
				}else{
					sendMessage(ChatColor.RED+joining.name+" has no island!");
				}
			}
		}else{
			sendMessage(ChatColor.RED+"You have no invitations.");
		}
		
		
	}
	
	public void createIsland(){
		
		Island i = IslandFactory.createNewIsland(this.name);
		this.IslandRank = SCPlayer.RANK_OWNER;
		this.hasIsland = SCPlayer.HAS_ISLAND;
		this.IX = i.lx;
		this.IY = i.ly;
		this.save();
		
	}
	
	public voxel getLowCorner(){
		
		voxel corner = new voxel();
		corner.x = (IX * SkyCraft.getInstance().getConfig().getInt("islandsize"))-(SkyCraft.getInstance().getConfig().getInt("islandsize")/2);
		corner.y = (IY * SkyCraft.getInstance().getConfig().getInt("islandsize"))-(SkyCraft.getInstance().getConfig().getInt("islandsize")/2);
		
		return corner;
		
	}
	
	public voxel getHighCorner(){
		
		voxel corner = new voxel();
		corner.x = (IX * SkyCraft.getInstance().getConfig().getInt("islandsize"))+(SkyCraft.getInstance().getConfig().getInt("islandsize")/2);
		corner.y = (IY * SkyCraft.getInstance().getConfig().getInt("islandsize"))+(SkyCraft.getInstance().getConfig().getInt("islandsize")/2);
		
		return corner;
		
	}
	
}
