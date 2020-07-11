package com.deviget.minesweeper.api;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.service.GameService;


@CrossOrigin(origins = "*", maxAge = 3600)
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
	@GetMapping
	public ResponseEntity<Game> newGame(@RequestHeader("sessionId") String session) {
		UUID sessionId = UUID.randomUUID();
		if(session != null && !session.isEmpty()) {
			sessionId = UUID.fromString(session);
		}
		Game game = gameService.newGame(sessionId);
		
		return ResponseEntity.ok().body(game);
	}
	
	@ResponseBody
	@GetMapping (path = "{gameId}")
	public ResponseEntity<Object> openGame(@PathVariable("gameId")String gameId) {
		
		return null;
	}
	
	@ResponseBody
	@PostMapping (path = "{gameId}/{x}/{y}")//TODO: x e y deberian se variable spero no del path
	public ResponseEntity<Object> digCell(@PathVariable("gameId")String gameId) {
		
		return null;
	}
	
	//setFlag
	
	
	//pauseGame
}
