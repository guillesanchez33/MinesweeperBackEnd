package com.deviget.minesweeper.model;

import java.util.Random;

public class Board {
	private Integer sizeX;
	private Integer sizeY;
	private Integer minesQty;
	private Cell[] cells;
	
	public Board(Integer sizeX, Integer sizeY, Integer minesQty) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.minesQty = minesQty;
		this.cells = this.buildBoard();
	}
	
	private Cell[] buildBoard() {
		Cell[] cells = new Cell[this.sizeX*this.sizeY];
		
		for(int i = 0; i < cells.length; i++) {
				cells[i] = new Cell();
		}
		Random r = new Random();
		for(int i=0; i < this.minesQty;) {
			int random = r.nextInt(cells.length-1);
			if(cells[random].getMine() == true){
				continue;
			}
			i++;
			cells[random].setMine(true);	
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
	
	public Cell[] getCells() {
		return cells;
	}
	
	public void setCells(Cell[] cells) {
		this.cells = cells;
	}
}
