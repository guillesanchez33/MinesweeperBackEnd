package com.deviget.minesweeper;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.deviget.minesweeper.dao.GameRepository;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.service.GameService;

@SpringBootApplication
public class MinesweeperApplication implements CommandLineRunner{

	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	GameService gameService;
	
	public static void main(String[] args) {
		SpringApplication.run(MinesweeperApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		//Game game = new Game(UUID.randomUUID());
		//gameRepository.save(game);
		
	/*	try {
			Game game = gameRepository.findAll().get(0);
			gameService.reset(game.getId());
			
			
			System.out.println("***********");
			
			List<Game> games = gameRepository.findAll();
			games.stream().forEach(g->printGame(g));
		}catch(Exception e) {
			
		}*/
		
	}
	
	private void printGame(Game game) {
		System.out.println(game.getId());	
		
		int index = 0;
		for(int y = 0; y < game.getBoard().getSizeY(); y++) {
			for(int x = 0; x < game.getBoard().getSizeX(); x++) {
				System.out.print(printCellInfo(game, index++));
			}
			System.out.println("");
		}
		System.out.println("***********");
		index = 0;
		for(int y = 0; y < game.getBoard().getSizeY(); y++) {
			for(int x = 0; x < game.getBoard().getSizeX(); x++) {
				System.out.print(printCellState(game, index++));
			}
			System.out.println("");
		}
	}

	private String printCellInfo(Game game, int index) {
		switch(game.getBoard().getCells()[index].getInfo()) {
		case NotRevealed:
			return "-";
		case Empty:
			return "0";
		case Flag:
			return "?";
		case One:
			return "1";
		case Two:
			return "2";
		case Three:
			return "3";
		case Four:
			return "4";
		case Five:
			return "5";
		case Six:
			return "6";
		case Seven:
			return "7";
		case Eight:
			return "8";
		}
		return " ";
	}
	
	private String printCellState(Game game, int index) {
		if(game.getBoard().getCells()[index].getMine())
			return "x";
		else
			return "-";
	}
}
