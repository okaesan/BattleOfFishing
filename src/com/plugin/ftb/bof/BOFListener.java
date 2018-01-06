package com.plugin.ftb.bof;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class BOFListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public static void onPlayerFishEvent(PlayerFishEvent event){
		HashMap<String, Integer> fishPoint = BattleOfFishing.fishPoint;
		Player player = event.getPlayer();

		if(BattleOfFishing.gamePoint.containsKey(player.getUniqueId())){
			if(event.getCaught() != null){
				Item i = (Item) event.getCaught();
				ItemStack item = i.getItemStack();
				String getItem;

				if(item.getType().equals(Material.RAW_FISH)){
					getItem = "RAW_FISH_" + item.getData().getData();
				}else{
					getItem = item.getType().toString();
				}

				if(fishPoint.containsKey(getItem)){
					int nowPoint = BattleOfFishing.gamePoint.get(player.getUniqueId());
					int getPoint = fishPoint.get(getItem);

					// ポイントの加算
					BattleOfFishing.gamePoint.put(player.getUniqueId(), nowPoint + getPoint);
				}
			}
		}
	}
}
