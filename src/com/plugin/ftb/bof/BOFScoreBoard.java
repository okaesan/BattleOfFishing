package com.plugin.ftb.bof;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class BOFScoreBoard {

	// top5をスコアボードに表示する際に使用
	static int topFive = 1;

	public static void setBoard(List<Entry<UUID, Integer>> sortPoint) {
		HashMap<UUID, Integer> gamePoint = BattleOfFishing.gamePoint;
		int time = BattleOfFishing.gameTime;

		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();

		Objective obj = board.registerNewObjective("bof","dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("Battle Of Fishing");
		for(Entry<UUID, Integer> entry : sortPoint){
			if(Bukkit.getPlayer(entry.getKey()).isOnline() == true){
				// スコア順に並ぶ(5人)
				Player player = (Player) Bukkit.getPlayer(entry.getKey());

				Score score = obj.getScore(player.getName());
				score.setScore(entry.getValue());
			}

			topFive += 1;
			if(5 < topFive){
				break;
			}
		}

		obj.getScore("").setScore(0);
		obj.getScore("残り時間: " + time).setScore(-1);

		for(Entry<UUID, Integer> entry : gamePoint.entrySet()){
			if(Bukkit.getPlayer(entry.getKey()).isOnline() == true){
				// スコアボードの表示
				Player player = (Player) Bukkit.getPlayer(entry.getKey());
				player.setScoreboard(board);
			}
		}
	}
}
