package com.plugin.ftb.bof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class BOFGame {

	static BattleOfFishing bof = BattleOfFishing.bof;
	static String TITLE_BOF = BattleOfFishing.TITLE_BOF;

	// top5をスコアボードに表示する際に使用
	static int topFive = 1;

	static List<Entry<UUID, Integer>> sortPoint;

	public static void startGame(){
		// コンフィグのリロード
		BOFConfig.readConfig();

		// オンラインのプレイヤーを参加者に登録
		for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
			if(offlinePlayer.isOnline() == true){
				Player player = (Player) offlinePlayer;
				ItemStack rod = new ItemStack(Material.FISHING_ROD, 1);
				rod.getItemMeta().setUnbreakable(true);

				BattleOfFishing.gamePoint.put(player.getUniqueId(), 0);
				player.getInventory().clear();
				player.getInventory().addItem(rod);
				player.setHealth(20);
				player.setFoodLevel(20);
				for (PotionEffect effect : player.getActivePotionEffects()){
			        player.removePotionEffect(effect.getType());
			    }
			}
		}

		// スコアボードの設定
		sortPoint = new ArrayList<Entry<UUID, Integer>>(BattleOfFishing.gamePoint.entrySet());
		BOFScoreBoard.setBoard(sortPoint);

		Bukkit.broadcastMessage(TITLE_BOF + "ゲーム開始");

		new BukkitRunnable(){
			@Override
			public void run() {
				// ポイントを昇順にソート(コードの内容は理解してないからとりあえずの状態)
				sortPoint = new ArrayList<Entry<UUID, Integer>>(BattleOfFishing.gamePoint.entrySet());
				Collections.sort(sortPoint, new Comparator<Entry<UUID, Integer>>() {
					public int compare(Entry<UUID, Integer> obj1, Entry<UUID, Integer> obj2) {
						return obj1.getValue().compareTo(obj2.getValue());
					}
				});
				// ------------------------------------------- //

				BattleOfFishing.gameTime -= 1;
				BOFScoreBoard.setBoard(sortPoint);

				if(BattleOfFishing.gameTime == 0){
					this.cancel();
					finishGame();
				}
			}
		}.runTaskTimer(bof, 20, 20);
	}

	public static void finishGame() {
		Bukkit.broadcastMessage(TITLE_BOF + "ゲーム終了");

		Bukkit.broadcastMessage("トップ5");
		for(Entry<UUID, Integer> entry : sortPoint) {
			Bukkit.broadcastMessage(Bukkit.getPlayer(entry.getKey()).getName() + ": " + entry.getValue());

			topFive += 1;
			if(5 < topFive){
				break;
			}
		}

		BattleOfFishing.resetVariable();
	}
}
