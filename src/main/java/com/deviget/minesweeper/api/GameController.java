package com.deviget.minesweeper.api;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.service.GameService;

@CrossOrigin(origins = "*")
@RequestMapping("game")
@RestController
public class GameController {
	@Autowired
	GameService gameService;

	@ResponseBody
	@GetMapping (path = "/all")
	public ResponseEntity<List<Game>> allGames(@RequestHeader("sessionId") String session) {
		UUID sessionId = UUID.randomUUID();
		if(session != null && !session.isEmpty()) {
			sessionId = UUID.fromString(session);
		}	
		List<Game> games = gameService.allGames(sessionId);
		
		return ResponseEntity.ok().body(games);
	}
	
	@ResponseBody
	@PostMapping(path = "/new/{x}/{y}/{mines}",
			produces = {MediaType.APPLICATION_JSON_VALUE} )
	public ResponseEntity<Game> newGame(@RequestHeader("sessionId") String session, @PathVariable("x")Integer x, 
			@PathVariable("y")Integer y, @PathVariable("mines")Integer mines) {
		UUID sessionId = UUID.randomUUID();
		if(session != null && !session.isEmpty()) {
			sessionId = UUID.fromString(session);
		}
		Game game = gameService.newGame(sessionId, x, y, mines);
		
		return ResponseEntity.ok().body(game);
	}
	
	@ResponseBody
	@GetMapping (path = "/open/{gameId}")
	public ResponseEntity<Game> openGame(@RequestHeader("sessionId") String session, @PathVariable("gameId")String idGame) {
		if(idGame == null || idGame.isEmpty() || session == null || session.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		UUID gameId = UUID.fromString(idGame);
		UUID sessionId = UUID.fromString(session);
		Game game = gameService.openGame(gameId, sessionId);
		
		return ResponseEntity.ok().body(game);
	}
	
	@ResponseBody
	@PutMapping (path = "/dig/{gameId}/{index}",
			produces = {MediaType.APPLICATION_JSON_VALUE} )
	public ResponseEntity<Game> digCell(@RequestHeader("sessionId") String session, @PathVariable("gameId")String idGame
			, @PathVariable("index")Integer index) {
		if(idGame == null || idGame.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} 
		UUID gameId = UUID.fromString(idGame);
		Game game = gameService.digCell(gameId, index);
		
		return ResponseEntity.ok().body(game);
	}
	
	@ResponseBody
	@PutMapping (path = "/pause/{gameId}",
			produces = {MediaType.APPLICATION_JSON_VALUE} )
	public ResponseEntity<Game> pauseGame(@RequestHeader("sessionId") String session, @PathVariable("gameId")String idGame) {
		if(idGame == null || idGame.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} 
		UUID gameId = UUID.fromString(idGame);
		Game game = gameService.pauseGame(gameId);
		
		return ResponseEntity.ok().body(game);
	}
	
	@ResponseBody
	@PutMapping (path = "/flag/{gameId}/{index}",
			produces = {MediaType.APPLICATION_JSON_VALUE} )
	public ResponseEntity<Game> flagCell(@RequestHeader("sessionId") String session, @PathVariable("gameId")String idGame
			, @PathVariable("index")Integer index) {
		if(idGame == null || idGame.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} 
		UUID gameId = UUID.fromString(idGame);
		Game game = gameService.flagCell(gameId, index);
		
		return ResponseEntity.ok().body(game);
	}
}
