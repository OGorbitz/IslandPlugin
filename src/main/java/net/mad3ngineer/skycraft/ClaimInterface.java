package net.mad3ngineer.skycraft;

public class ClaimInterface {

	static String type;
	
	public static void init(){
		
		type = SkyCraft.getInstance().getConfig().getString("claimsplugin");
		if(type.equalsIgnoreCase("builtin")){
			type = "builtin";
		}else{
			type = "builtin";
			SkyCraft.getInstance().getLogger().info("Config setting 'claimsplugin' was an invalid value. Defaulting to builtin protection.");
		}

        PInterface.init();

	}
	
}
