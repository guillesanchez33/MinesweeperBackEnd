package com.deviget.minesweeper.model;

public class Cell {
	private Boolean mine;
	private Boolean revealed;
	private CellInfo info;
	
	public Cell(){
		this.mine = false;
		this.revealed = false;
		this.info = CellInfo.NotRevealed;
	}
	
	public Boolean getMine() {
		return mine;
	}

	public void setMine(Boolean mine) {
		this.mine = mine;
	}

	public Boolean getRevealed() {
		return revealed;
	}

	public void setRevealed(Boolean revealed) {
		this.revealed = revealed;
	}

	public CellInfo getInfo() {
		return info;
	}

	public void setInfo(CellInfo info) {
		this.info = info;
	}

	public enum CellInfo{
		NotRevealed, Empty, Flag, One, Two, Three, Four, Five, Six, Seven, Eight
	}
}
