package com.deviget.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
	private Integer sizeX;
	private Integer sizeY;
	private Integer minesQty;
	private List<Cell> cells;
	
	public Board(Integer sizeX, Integer sizeY, Integer minesQty) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.minesQty = minesQty;
		this.cells = this.buildBoard();
	}
	
	private List<Cell> buildBoard() {
		List<Cell> cells = new ArrayList<Cell>();
		for(int i = 0; i < this.sizeX*this.sizeY; i++) {
			cells.add(new Cell());
		}
		Random r = new Random();
		for(int i=0; i < this.minesQty;) {
			int random = r.nextInt(cells.size()-1);
			if(cells.get(random).getMine() == true){
				continue;
			}
			i++;
			cells.get(random).setMine(true);	
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
	
	public List<Cell> getCells() {
		return cells;
	}
	
	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
}
