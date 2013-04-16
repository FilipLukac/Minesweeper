package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;
import minesweeper.swingui.SwingUI;

/**
 * Main application class.
 */
public class Minesweeper {
	
	private BestTimes bestTimes = new BestTimes();
	long startMillis;
	private Settings setting;
	private static Minesweeper instance;
	private static final String DEFAULT_UI = "swing";
	
    /** User interface. */
    private UserInterface userInterface;
 
    /**
     * Constructor.
     */
    private Minesweeper() {
    	instance = this;
    	setting = Settings.load();//new Settings(9, 9, 10);
    	System.out.printf("settingy %d %d %d\n",setting.getColumnCount(), setting.getRowCount(), setting.getMineCount());
        userInterface = create(DEFAULT_UI);
        
        
        newGame();
        
       
        startMillis = System.currentTimeMillis();
    }

    private UserInterface create(String name){
    	switch (name){
    	case "swing" : return new SwingUI();
    	case "console": return new ConsoleUI();
    	default : throw new RuntimeException("No valid UI specified!");
    	}
    }
    
    public int getPlayingSeconds(){
    	return (int)(startMillis/1000);
    	
    }
    
    public void newGame() {
        Field field = new Field(setting.getRowCount(), setting.getColumnCount(), setting.getMineCount());
        startMillis = System.currentTimeMillis();
        userInterface.newGameStarted(field);
    }
    
        
    /**
     * Main method.
     * @param args arguments
     */
    public static void main(String[] args) {
    	
    	new Minesweeper();
    	
        
    }
    
    public static Minesweeper getInstance(){
    	return instance;
    }

	public BestTimes getBestTimes() {
		return bestTimes;
	}

	public void setBestTimes(BestTimes bestTimes) {
		this.bestTimes = bestTimes;
	}

	public Settings getSetting() {
		return setting;
	}

	public void setSetting(Settings setting) {
		
		this.setting = setting;
		setting.save();
	}
}
