package com.snakeladderGame.com.snakeladdergame;

import com.snakeladderGame.com.snakeladdergame.config.GameConfig;
import com.snakeladderGame.com.snakeladdergame.exception.PlayersException;
import com.snakeladderGame.com.snakeladdergame.model.Dice;
import com.snakeladderGame.com.snakeladdergame.model.Ladder;
import com.snakeladderGame.com.snakeladdergame.model.Player;
import com.snakeladderGame.com.snakeladdergame.model.Snake;
import com.snakeladderGame.com.snakeladdergame.service.SnakeAndLadder;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.yaml.snakeyaml.Yaml;

public class Application {

	public static void main(String[] args) throws PlayersException {
		System.out.println("Welcome to Snake and ladder game");
		Scanner sc = new Scanner(System.in);
//		System.out.println("Please enter board size:");
//		int size = sc.nextInt();
//		System.out.println("Please enter number of snakes:");
//		int snakesCount = sc.nextInt();
//		List<Snake> snakes = new ArrayList<>();
//		for(int i = 0; i < snakesCount; i++){
//			System.out.println("Please enter snakes head:");
//			int snakeHead = sc.nextInt();
//			System.out.println("Please enter snakes tail:");
//			int snakeTail = sc.nextInt();
//			snakes.add(new Snake(snakeHead, snakeTail));
//		}
//		System.out.println("Please enter number of ladders:");
//		int laddersCount = sc.nextInt();
//		List<Ladder> ladders = new ArrayList<>();
//		for(int i = 0; i < laddersCount; i++){
//			System.out.println("Please enter ladder bottom:");
//			int ladderBottom = sc.nextInt();
//			System.out.println("Please enter ladder top:");
//			int ladderTop = sc.nextInt();
//			ladders.add(new Ladder(ladderBottom, ladderTop));
//		}
//
//
//		System.out.println("Please enter number of Players:");
//		int players = sc.nextInt();
//		if(players <2){
//			throw new PlayersException("Minimum 2 players are required for this game");
//		}
//		SnakeAndLadder snakeAndLadder = new SnakeAndLadder(size, snakes, ladders, new Dice(6));
//		for(int i = 0; i < players; i++){
//			System.out.println("Please enter player-" + i+1 +" name:");
//			String name = sc.next();
//			System.out.println("Please enter player-" + i+1 +" position:");
//			int pos = sc.nextInt();
//			snakeAndLadder.addPlayer(new Player(name, pos));
//		}
		GameConfig gameConfig = readConfig();
		SnakeAndLadder snakeAndLadder = new SnakeAndLadder(gameConfig.boardSize, gameConfig.snakes, gameConfig.ladders, new Dice(6));
		for(Player player: gameConfig.players){
			snakeAndLadder.addPlayer(player);
		}
		snakeAndLadder.start();
	}
	public static GameConfig readConfig() {
		Yaml yaml = new Yaml();
		InputStream inputStream = Application.class.getClassLoader().getResourceAsStream("game-config.yaml");
		return yaml.loadAs(inputStream, GameConfig.class);
	}

}
