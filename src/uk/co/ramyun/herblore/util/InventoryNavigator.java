package uk.co.ramyun.herblore.util;

/**
 * A Utility class for traversing a grid in different styles. OSRS inventory is
 * 4x7 (28 slots in total).
 */
public class InventoryNavigator {

	/**
	 * @author © Michael 31 Dec 2017
	 * @file InventoryNavigator.java
	 */

	private final int rows, cols;
	private int row = 0, col = 0;

	public InventoryNavigator(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	/** 4x7
	 * >── ─── ─── ───
	 * >── ─── ─── ───
	 * >── ─── ─── ───
	 * >── ─── ─── ───
	 * >── ─── ─── ───
	 * >── ─── ─── ───
	 * >── ─── ─── ──■
	 */
	public int nextLine() {
		if (row < 0 || row > (rows - 1)) row = 0;
		if (col < 0 || col > (cols - 1)) col = 0;
		int slot = (cols * row) + col;
		if (col++ % cols == (col - 1)) {
			col = 0;
			row++;
		}
		return slot;
	}

	/** 4x7
	 * >── ─── ─── ──┐
	 * ┌── ─── ─── ──┘
	 * └── ─── ─── ──┐
	 * ┌── ─── ─── ──┘
	 * └── ─── ─── ──┐
	 * ┌── ─── ─── ──┘
	 * └── ─── ─── ──■
	 */
	public int nextSnakeHorizontal() {
		if (row < 0 || row > (rows - 1)) row = 0;
		if (col < 0 || col > (cols - 1)) col = 0;
		int slot = (cols * row) + col;
		if (row % 2 == 0) {
			if (col < (cols - 1)) col++;
			else row++;
		} else {
			if (col > 0) col--;
			else row++;
		}
		return slot;
	}

	/** 4x7
	 * |   ┌─ ─┐   ■
	 * |   |   |   |
	 * |   |   |   |
	 * |   |   |   |
	 * |   |   |   |
	 * |   |   |   |
	 * └───┘   └───┘
	 */
	public int nextSnakeVertical() {
		if (row < 0 || row > (rows - 1)) row = 0;
		if (col < 0 || col > (cols - 1)) col = 0;
		int slot = (cols * row) + col;
		if (col % 2 == 0) {
			if (row < (rows - 1)) row++;
			else col++;
		} else {
			if (row > 0) row--;
			else col++;
		}
		return slot;
	}

	public void reset() {
		row = 0;
		col = 0;
	}
}
