package com.plugin.ftb.bof;

public class BOFConfig {

	static BattleOfFishing bof = BattleOfFishing.bof;

	public static void reloadConfig(){
		bof.reloadConfig();
	}

	public static void readConfig(){
		BattleOfFishing.gameTime = bof.getConfig().getInt("GameTime");
	}
}
