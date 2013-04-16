package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.*;

import minesweeper.UserInterface;
import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import minesweeper.core.Tile;
import minesweeper.core.Tile.State;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;
	private Tile[][] tileset;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(
			System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.Field
	 * )
	 */
	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		do {
			update();
			processInput();

			if (field.getState().equals(GameState.SOLVED)) {
				System.out.println("A JE TO!!!");
				System.exit(0);
			}
			if (field.getState().equals(GameState.FAILED)) {
				update();
				System.out.println("BOOM!!!");
								
				System.exit(0);
			}

		} while (true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
	@Override
	public void update() {

		char closedTileChar = '-';
		char mineTilechar = 'X';
		char markedTileChar = 'M';

		tileset = field.tiles;
		System.out.print("\\");
		for (int i = 0; i < field.getRowCount(); i++)
			System.out.format("% 4d", i);
		System.out.println();

		int rowLabel = 0;
		for (Tile[] rows : tileset) {
			System.out.print(Character.toChars(rowLabel + 65));

			rowLabel++;
			for (Tile cols : rows) {

				if (cols.getState().equals(Tile.State.CLOSED)) {
					System.out.format("%4c", closedTileChar);
				} else if (cols.getState().equals(Tile.State.MARKED)) {
					System.out.format("%4c", markedTileChar);
				} else if (cols instanceof Mine) {
					System.out.format("%4c", mineTilechar);
				} else if (cols instanceof Clue) {
					System.out.format("%4d", ((Clue) cols).getValue());
				}

			}
			System.out.println();

		}

		System.out.println("Neoznacenych min je: "
				+ field.getRemainingMineCount());

	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {

		try {
			handleInput(readLine().toUpperCase());
		} catch (WrongFormatException e) {

			System.out.println(e.getMessage());
		}

	}

	public void handleInput(String input) throws WrongFormatException {

		// input.toUpperCase();

		int fieldRows = field.getRowCount();
		int fieldCols = field.getColumnCount();
		char[] rowChar = Character.toChars(fieldCols + 64);

		

		Pattern patt = Pattern.compile("(M|O)([A-"
				+ Character.toString(rowChar[0]) + "])([0-9]*)");
		Matcher match = patt.matcher(input);
		if (input.equals("X")) {
			System.out.println("Ukoncene pouzivatelom");
			System.exit(0);
		} else if (match.matches()) {

			if (match.group(1).equals("M"))
				field.markTile(((int) (match.group(2).charAt(0)) - 65),
						(Integer.valueOf(match.group(3))));
			if (match.group(1).equals("O"))
				field.openTile(((int) (match.group(2).charAt(0)) - 65),
						(Integer.valueOf(match.group(3))));
		} else			throw new WrongFormatException("Zle zadany vstup!");

	}

}
