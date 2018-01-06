package com.plugin.ftb.bof;

import java.util.Map;
import java.util.Map.Entry;

public class BOFConfig {

	static BattleOfFishing bof = BattleOfFishing.bof;

	public static void reloadConfig(){
		bof.reloadConfig();
	}

	//@SuppressWarnings("unchecked")
	public static void readConfig(){
		// 残り時間の取得
		BattleOfFishing.gameTime = bof.getConfig().getInt("GameTime");
		// 釣った物のポイントの取得
		Map<String, Object> conf = bof.getConfig().getConfigurationSection("Point").getValues(false);
		for(Entry<String, Object> entry : conf.entrySet()){
			BattleOfFishing.fishPoint.put(entry.getKey(), (Integer) entry.getValue());
		}
	}
}