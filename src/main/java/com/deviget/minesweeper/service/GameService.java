package com.deviget.minesweeper.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.deviget.minesweeper.dao.GameRepository;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Cell.CellInfo;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.model.Game.GameState;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;
	
	public List<Game> allGames(UUID sessionId) {
		Game game = new Game(sessionId);
		return gameRepository.findAll(Example.of(game));
	}
	
	public Game newGame(UUID sessionId, Integer x, Integer y, Integer mines) {
		Game game = new Game(sessionId);
		game.init(x, y, mines);
		Game gamePaused = new Game(sessionId);
		gamePaused.setState(GameState.Playing);
		Optional<Game> o = gameRepository.findOne(Example.of(gamePaused));
		if(o.isPresent()) {
			gamePaused = o.get();
			gamePaused.setState(GameState.Paused);
			setTimeCount(gamePaused);	
			gameRepository.save(gamePaused);
		}
		gameRepository.save(game);

		return game;
	}
	
	public Game openGame(UUID gameId, UUID sessionId) {
		Game game = new Game();
		game.setId(gameId);
		Optional<Game> o = gameRepository.findOne(Example.of(game));
		if(o.isPresent()) {
			game = o.get();
			if(game.getState() == GameState.Paused) {
				game.setState(GameState.Playing);
				game.setLastActivity(new Date());
			}
			Game gamePaused = new Game(sessionId);
			gamePaused.setState(GameState.Playing);
			o = gameRepository.findOne(Example.of(gamePaused));
			if(o.isPresent()) {
				gamePaused = o.get();
				gamePaused.setState(GameState.Paused);
				setTimeCount(gamePaused);
				gameRepository.save(gamePaused);
			}
			gameRepository.save(game);
		} else {
			game = null;
		}

		return game;
	}
	
	public Game pauseGame(UUID gameId) {
		Game game = new Game();
		game.setId(gameId);
		Optional<Game> o = gameRepository.findOne(Example.of(game));
		if(o.isPresent()) {
			game = o.get();
			if(game.getState() == GameState.Playing) {
				game.setState(GameState.Paused);
				setTimeCount(game);
			}
			gameRepository.save(game);
		} else {
			game = null;
		}

		return game;
	}
	
	public Game flagCell(UUID gameId, Integer index) {
		Game game = new Game();
		game.setId(gameId);
		game = gameRepository.findAll(Example.of(game)).get(0);
		if(game.getBoard().getCells().get(index).getRevealed() == true) {
			return game;
		}
		if(game.getBoard().getCells().get(index).getInfo() == CellInfo.Flag) {
			game.getBoard().getCells().get(index).setInfo(CellInfo.NotRevealed);
		} else {
			game.getBoard().getCells().get(index).setInfo(CellInfo.Flag);
		}
		gameRepository.save(game);
		
		return game;
	}
	
	public Game digCell(UUID gameId, Integer index) {
		Game game = new Game();
		game.setId(gameId);
		game = gameRepository.findAll(Example.of(game)).get(0);
		if(game.getBoard().getCells().get(index).getRevealed() == true) {
			return game;
		}
		if(game.getBoard().getCells().get(index).getMine() == true) {
			game.setState(GameState.Lose);
			setTimeCount(game);
			gameRepository.save(game);
			return game;
		}
		this.revelMine(game, index, true);
		if(this.isOver(game)) {
			setTimeCount(game);
			game.setState(GameState.Win);
		}
		gameRepository.save(game);
		
		return game;
	}
	
	public void reset(UUID gameId) {
		Game game = new Game();
		game.setId(gameId);
		game = gameRepository.findAll(Example.of(game)).get(0);
		game.getBoard().getCells().stream().forEach(c->c.setRevealed(false));
		game.getBoard().getCells().stream().forEach(c->c.setInfo(CellInfo.NotRevealed));
		game.setState(GameState.Playing);
		gameRepository.save(game);
	}
	
	private void setTimeCount(Game game) {
		Date now = new Date();
		Long seconds = (now.getTime() - game.getLastActivity().getTime())/1000;
		game.setTimeCount(game.getTimeCount() + seconds.intValue());
		game.setLastActivity(now);
	}
	
	private Boolean isOver(Game game) {
		Optional<Cell> o = game.getBoard().getCells().stream().filter(c->c.getRevealed() == false &&
					c.getMine() == false).findFirst();
		
		if(o.isPresent()) {
			return false;
		}
		
		return true;
	}
	
	private Integer getIndex(Game game, Integer x, Integer y) {
		return  (game.getBoard().getSizeX() * y) + x;
	}
	
	private void revelMine(Game game, Integer index, Boolean original) {
		Boolean reveleadAdjacent = true;
		int adjacentMines = 0;
		Integer x = index%game.getBoard().getSizeX();
		Integer y = index/game.getBoard().getSizeX();
		
		if(x + 1 < game.getBoard().getSizeX() && game.getBoard().getCells().get(getIndex(game,x+1,y)).getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(x + 1 < game.getBoard().getSizeX() && y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells().get(getIndex(game,x+1,y+1)).getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells().get(getIndex(game,x,y+1)).getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(x - 1 >= 0 && y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells().get(getIndex(game,x-1,y+1)).getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(x - 1 >= 0 && game.getBoard().getCells().get(getIndex(game,x-1,y)).getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(x - 1 >= 0 && y - 1 >= 0 && game.getBoard().getCells().get(getIndex(game,x-1,y-1)).getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(y - 1 >= 0 && game.getBoard().getCells().get(getIndex(game,x,y-1)).getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		if(x + 1 < game.getBoard().getSizeX() && y - 1 >= 0 && game.getBoard().getCells().get(getIndex(game,x+1,y-1)).getMine() == true) {
			reveleadAdjacent = false;
			adjacentMines++;
		}
		game.getBoard().getCells().get(index).setRevealed(true);
		game.getBoard().getCells().get(index).setInfo(getCellInfo(adjacentMines));
		
		if(reveleadAdjacent == false) {
			return;
		}
		
		if(x + 1 < game.getBoard().getSizeX() && game.getBoard().getCells().get(getIndex(game,x+1,y)).getRevealed() == false ) {
			revelMine(game,getIndex(game,x+1,y),false);
		}
		if(x + 1 < game.getBoard().getSizeX() && y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells().get(getIndex(game,x+1,y+1)).getRevealed() == false) {
			revelMine(game, getIndex(game,x+1,y+1),false);
		}
		if(y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells().get(getIndex(game,x,y+1)).getRevealed() == false) {
			revelMine(game, getIndex(game,x,y+1),false);
		}
		if(x - 1 >= 0 && y + 1 < game.getBoard().getSizeY() && game.getBoard().getCells().get(getIndex(game,x-1,y+1)).getRevealed() == false) {
			revelMine(game, getIndex(game,x-1,y+1),false);
		}
		if(x - 1 >= 0 && game.getBoard().getCells().get(getIndex(game,x-1,y)).getRevealed() == false) {
			revelMine(game, getIndex(game,x-1,y),false);
		}
		if(x - 1 >= 0 && y - 1 >= 0 && game.getBoard().getCells().get(getIndex(game,x-1,y-1)).getRevealed() == false) {
			revelMine(game,getIndex(game,x-1,y-1),false);
		}
		if(y - 1 >= 0 && game.getBoard().getCells().get(getIndex(game,x,y-1)).getRevealed() == false) {
			revelMine(game, getIndex(game,x,y-1),false);
		}
		if(x + 1 < game.getBoard().getSizeX() && y - 1 >= 0 && game.getBoard().getCells().get(getIndex(game,x+1,y-1)).getRevealed() == false) {
			revelMine(game,getIndex(game,x+1,y-1),false);
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
