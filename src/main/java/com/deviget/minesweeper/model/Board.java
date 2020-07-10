package com.deviget.minesweeper.model;

import java.util.Random;

public class Board {
	private Integer sizeX;
	private Integer sizeY;
	private Integer minesQty;
	private Cell[][] cells;
	
	public Board(Integer sizeX, Integer sizeY, Integer minesQty) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.minesQty = minesQty;
		this.cells = this.buildBoard();
	}
	
	private Cell[][] buildBoard() {
		Cell[][] cells = new Cell[this.sizeX][this.sizeY];
		
		for(int x = 0; x < sizeX; x++) {
			for(int y = 0; y < sizeY; y++) {
				cells[x][y] = new Cell();
			}
		}
		Random r = new Random();
		for(int i=0; i < this.minesQty;) {
			int x = r.nextInt(sizeX-1);
			int y = r.nextInt(sizeY-1);
			if(cells[x][y].getMine() == true){
				continue;
			}
			i++;
			cells[x][y].setMine(true);	
		}
		
		return cells;
	}
	
	public Integer getSizeX() {
		return sizeX;
	}
	
	public void setSizeX(Integer sizeX) {
		this.sizeX = sizeX;
	}
	
	public Integer getSizeY() {
		return sizeY;
	}
	public void setSizeY(Integer sizeY) {
		this.sizeY = sizeY;
	}
	
	public Integer getMinesQty() {
		return minesQty;
	}
	
	public void setMinesQty(Integer minesQty) {
		this.minesQty = minesQty;
	}
	
	public Cell[][] getCells() {
		return cells;
	}
	
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
}
