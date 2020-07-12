package com.deviget.minesweeper.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Game {
	@Id
	private UUID id;
	private UUID session;
	private Date creationDate;
	private Integer timeCount;
	private Date lastActivity;
	private GameState state;
	private Board board;
	
	public Game() {
		
	}
	
	public Game(UUID session) {
		this.session = session;
	}
	
	/*public Game(UUID session, UUID id) {
		this.session = session;
		this.id = id;
	}
	*/
	public void init(int xSize, int ySize, int mines) {
		this.id = UUID.randomUUID();
		this.timeCount = 0;
		this.lastActivity = new Date();
		this.creationDate = new Date();
		this.state = GameState.Playing;
		this.board = new Board(xSize, ySize, mines);
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getSession() {
		return session;
	}

	public void setSession(UUID session) {
		this.session = session;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getTimeCount() {
		return timeCount;
	}

	public void setTimeCount(Integer timeCount) {
		this.timeCount = timeCount;
	}

	public Date getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public enum GameState{
		Playing, Paused, Lose, Win;
	}
}
