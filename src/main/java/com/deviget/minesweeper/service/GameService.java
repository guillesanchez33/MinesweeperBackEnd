package com.deviget.minesweeper.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.deviget.minesweeper.dao.GameRepository;
import com.deviget.minesweeper.model.Cell.CellInfo;
import com.deviget.minesweeper.model.Game;

@Service
public class GameService {
	
	@Autowired
	GameRepository gameRepository;
	
	public List<Game> allGames(UUID sessionId) {
		Game game = new Game(sessionId);
		return gameRepository.findAll(Example.of(game));
	}
	
	public Game newGame(UUID sessionId) {
		if(sessionId == null) {
			sessionId = UUID.randomUUID();
		}
		Game game = new Game(sessionId);
		game.init();
		gameRepository.save(game);
		
		//setear todos los games q estan en playing en pause
		
		return game;
	}
	
	public void digCell(UUID gameId, Integer x, Integer y) {
		Game game = new Game();
		game.setId(gameId);
		
		game = gameRepository.findAll(Example.of(game)).get(0);
		
		if(game.getBoard().getCells()[x][y].getRevealed() == true) {
			//esta celda ya estaba descubierta, quizas podria largar una custom exception
			return;
		}
		
		if(game.getBoard().getCells()[x][y].getMine() == true) {
			//pierde
			return;
		}
		
		this.revelMine(game, x, y);
		
		gameRepository.save(game);
	}
	
	public void flagCell(UUID gameId, Integer x, Integer y) {
		
	}
	
	private void revelMine(Game game, Integer x, Integer y) {
		Boolean reveleadAdjacent = true;
		int adjacentMines = 0;
		
		
		if(x + 1 < game.getBoard().getSizeX() && game.getBoard().getCells()[x+1][y].getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(x + 1 < game.getBoard().getSizeX() && y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells()[x+1][y+1].getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells()[x][y+1].getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(x - 1 >= 0 && y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells()[x-1][y+1].getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(x - 1 >= 0 && game.getBoard().getCells()[x-1][y].getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(x - 1 >= 0 && y - 1 >= 0 && game.getBoard().getCells()[x-1][y-1].getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(y - 1 >= 0 && game.getBoard().getCells()[x][y-1].getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(x + 1 < game.getBoard().getSizeX() && y - 1 >= 0 && game.getBoard().getCells()[x+1][y-1].getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		
		game.getBoard().getCells()[x][y].setRevealed(true);
		game.getBoard().getCells()[x][y].setInfo(getCellInfo(adjacentMines));
		
		if(reveleadAdjacent == false) {
			return;
		}
		
		if(x + 1 < game.getBoard().getSizeX() && game.getBoard().getCells()[x+1][y].getRevealed() == false ) {
			revelMine(game, x+1, y);
		}
		if(x + 1 < game.getBoard().getSizeX() && y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells()[x+1][y+1].getRevealed() == false) {
			revelMine(game, x+1, y+1);
		}
		if(y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells()[x][y+1].getRevealed() == false) {
			revelMine(game, x, y+1);
		}
		if(x - 1 >= 0 && y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells()[x-1][y+1].getRevealed() == false) {
			revelMine(game, x-1, y+1);
		}
		if(x - 1 >= 0 && game.getBoard().getCells()[x-1][y].getRevealed() == false) {
			revelMine(game, x-1, y);
		}
		if(x - 1 >= 0 && y - 1 >= 0 && game.getBoard().getCells()[x-1][y-1].getRevealed() == false) {
			revelMine(game, x-1, y-1);
		}
		if(y - 1 >= 0 && game.getBoard().getCells()[x][y-1].getRevealed() == false) {
			revelMine(game, x, y-1);
		}
		if(x + 1 < game.getBoard().getSizeX() && y - 1 >= 0 && game.getBoard().getCells()[x+1][y-1].getRevealed() == false) {
			revelMine(game, x+1, y-1);
		}
	}
	
	private CellInfo getCellInfo(int adjacentMines) {
		switch(adjacentMines) {
		case 0:
			return CellInfo.Empty;
		case 1:
			return CellInfo.One;
		case 2:
			return CellInfo.Two;
		case 3:
			return CellInfo.Three;
		case 4:
			return CellInfo.Four;
		case 5:
			return CellInfo.Five;
		case 6:
			return CellInfo.Six;
		case 7:
			return CellInfo.Seven;
		default:
			return CellInfo.Eight;
		}
	}
}
