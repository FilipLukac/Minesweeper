package minesweeper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Settings implements Serializable {

	public static final Settings BEGINNER = new Settings(9, 9, 10);
	public static final Settings INTERMEDIATE = new Settings(16, 16, 40);
	public static final Settings EXPERT = new Settings(16, 30, 99);

	private static final String SETTING_FILE = System.getProperty("user.home")
			+ System.getProperty("file.separator") + "minesweeper.settings";;

	private final int rowCount;
	private final int columnCount;
	private final int mineCount;

	public Settings(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
	}

	public boolean equals(Object o) {

		if (this.getColumnCount() == ((Settings) o).getColumnCount()
				&& this.getMineCount() == ((Settings) o).getMineCount()
				&& this.getRowCount() == ((Settings) o).getRowCount()) {
			return true;
		} else {
			return false;
		}

	}

	public int hashCode() {
		return this.columnCount * this.mineCount * this.rowCount;
	}

	public void save() {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(SETTING_FILE));
		
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(this);
		
		fos.close();
		oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Settings load() {
		ObjectInputStream ois = null;
		Settings settings = BEGINNER;
		
		try {
		FileInputStream fis = new FileInputStream(new File(SETTING_FILE));
		ois = new ObjectInputStream(fis);
		settings = (Settings) ois.readObject();
		
		} catch (IOException e) {
			settings = BEGINNER;
			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
		return settings;
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

}
