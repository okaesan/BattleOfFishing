package com.plugin.ftb.bof;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BOFCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] args) {
		if(!(cmdSender instanceof Player)){
			cmdSender.sendMessage("コマンドはゲーム内力してください");
			return false;
		}

		if(args.length == 0){
			return false;
		}

		switch(args[0]){
		case "start": // ゲームを開始する
			BOFGame.startGame();
			return true;

		case "reset": // 表示されたスコアボードを削除する
			for(OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
				if(offlinePlayer.isOnline() == true){
					Player player = (Player) offlinePlayer;

					player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
				}
			}
			return true;

		default:

		}

		return false;
	}
}
