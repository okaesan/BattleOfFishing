package com.plugin.ftb.bof;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public class BattleOfFishing extends JavaPlugin {

	public static BattleOfFishing bof;

	// 残り時間
	public static int gameTime = -1;
	// 釣った物のポイント
	public static HashMap<String, Integer> fishPoint = new HashMap<String, Integer>();
	// ゲーム中のポイント
	public static HashMap<UUID, Integer> gamePoint = new HashMap<UUID, Integer>();

	// [BattleOfFishing]
	public static final String TITLE_BOF = "[" + ChatColor.BOLD + ChatColor.AQUA + "BoF" + ChatColor.RESET + "]";

	@Override
	public void onEnable() {
		bof = this;

		getLogger().info("BattleOfFishingが起動しました。");

		// コマンド登録
		getCommand("bof").setExecutor(new BOFCommand());

		// イベントリスナ登録
		getServer().getPluginManager().registerEvents(new BOFListener(), this);

		// コンフィグの書き出し
		this.saveDefaultConfig();
		// コンフィグのリロード
		BOFConfig.readConfig();
	}

	@Override
	public void onDisable(){
		getLogger().info("BattleOfFishingが終了しました。");
	}

	public static void resetVariable() {
		gameTime = -1;
		gamePoint.clear();
		fishPoint.clear();

		for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
			if(offlinePlayer.isOnline() == true){
				Player player = (Player) offlinePlayer;

				player.getInventory().clear();
				player.setHealth(20);
				player.setFoodLevel(20);
				for (PotionEffect effect : player.getActivePotionEffects()){
			        player.removePotionEffect(effect.getType());
			    }
			}
		}
	}
}
