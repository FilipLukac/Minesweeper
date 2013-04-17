package minesweeper.core;

import java.util.Random;

import minesweeper.core.Tile.State;

/**
 * Field represents playing field and game logic.
 */
public class Field {
	/** Playing field tiles. */
	public final Tile[][] tiles;

	/** Field row count. Rows are indexed from 0 to (rowCount - 1). */
	private final int rowCount;

	/** Column count. Columns are indexed from 0 to (columnCount - 1). */
	private final int columnCount;

	/** Mine count. */
	private final int mineCount;

	/** Game state. */
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor.
	 * 
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 * @param mineCount
	 *            mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];
		generate();
	}

	/**
	 * Opens tile at specified indeces.
	 * 
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			if (!(tile instanceof Mine)) {
				openAdjacentTile(row, column);
			}
			// tile.setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				tile.setState(Tile.State.OPEN);

				setState(GameState.FAILED);
				return;
			}

			/*
			 * if(isSolved()) { setState(GameState.SOLVED); return; }
			 */
		}

		if (isSolved()) {
			setState(GameState.SOLVED);
			return;
		}

	}

	/**
	 * Marks tile at specified indeces.
	 * 
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void markTile(int row, int column) {
		Tile currentTile = tiles[row][column];
		if (currentTile.getState() == Tile.State.CLOSED) {
			currentTile.setState(Tile.State.MARKED);
		} else if (currentTile.getState() == Tile.State.MARKED) {
			currentTile.setState(Tile.State.CLOSED);
		}

	}

	/**
	 * Generates playing field.
	 */
	private void generate() {
		Random randomNum = new Random();
		int counter = mineCount;
		while (counter > 0) {
			int randomCol = randomNum.nextInt(this.getColumnCount());
			int randomRow = randomNum.nextInt(this.getRowCount());

			if (tiles[randomRow][randomCol] == null) {
				tiles[randomRow][randomCol] = new Mine();
				tiles[randomRow][randomCol].setState(State.CLOSED);
				counter--;
			}

		}

		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getColumnCount(); j++) {
				if (!(tiles[i][j] instanceof Mine)) {

					tiles[i][j] = new Clue(countAdjacentMines(i, j));
				}
			}
		}

	}

	/**
	 * Returns true if game is solved, false otherwise.
	 * 
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {
		if ((getRowCount() * getColumnCount()) - getNumberOf(State.OPEN) == getMineCount()) {
			return true;
		} else
			return false;
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 * 
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return number of adjacent mines.
	 */
	public int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int actRow = row + i;
				int actColumn = column + j;
				if (actRow >= 0 && actRow < getRowCount() && actColumn >= 0
						&& actColumn < getColumnCount()) {
					if (tiles[actRow][actColumn] instanceof Mine) {
						count++;
					}
				}
			}
		}

		return count;
	}

	public void openAdjacentTile(int row, int column) {
		
		Tile tile = tiles[row][column];
		
		System.out.println("opening ["+row+","+column+"]");
		tile.setState(State.OPEN);
		if (((Clue) tile).getValue() == 0) {

			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					int actRow = row + i;
					int actColumn = column + j;
					if (actRow >= 0 && actRow < getRowCount() && actColumn >= 0
							&& actColumn < getColumnCount()) {
						openTile(actRow, actColumn);

					}
				}
			}

		}

	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	private void setState(GameState state) {
		this.state = state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	private int getNumberOf(Tile.State state) {
		int count = 0;
		for (int i = 0; i < getRowCount(); i++) {
			for (int j = 0; j < getColumnCount(); j++) {
				if (tiles[i][j].getState() == state)
					count++;
			}
		}

		return count;
	}

	public int getRemainingMineCount() {
		int remainingMineCount = mineCount;

		remainingMineCount -= this.getNumberOf(State.MARKED);

		return remainingMineCount;
	}

}
