import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class GameScreen extends GameCanvas implements Runnable, CommandListener {
  private static final int MILLIS_PER_TICK = 50;
  private static final int TILE_WIDTH = 32;
  private static final int TILE_HEIGHT = 32;
  private static final int TILE_NUM_COL = 6;
  private static final int TILE_NUM_ROW = 36;


  private Eliminator midlet;  // Hold the Main Midlet
  private Settings settings;  // Hold Game Settings
  private Score score;        // Hold Game Score
  private Command backCommand = new Command("Back", Command.BACK,1);

  private boolean isPlay;   // Game Loop runs when isPlay is true
  private int width;        // To hold screen width
  private int height;       // To hold screen height

  private int scnViewWidth; // Hold Width Screen View Port
  private int scnViewHeight; // Hold Height Screen View Port

  private Thread gameThread = null;

  // Layer Manager to manager background (terrain)
  private LayerManager layerManager;

  // TiledLayer - Terrain
  private TiledLayer terrain;
  private int terrainScroll;   // Hold Y position for scrolling

  // Constructor and initialization
  public GameScreen(Eliminator midlet,Settings settings,Score score) throws Exception {
    super(true);
    this.midlet = midlet;
    addCommand(backCommand);
    setCommandListener(this);

    width = getWidth();  // get screen width
    height = getHeight();  // get screen height
    scnViewWidth = width; // Set View Port width to screen width
    scnViewHeight = height; // Set View Port height to screen height

    isPlay = true;

    initTerrain();

    layerManager = new LayerManager();
    layerManager.append(terrain);

  }

  // Start thread for game loop
  public void start() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  // Stop thread for game loop
  public void stop() {
    gameThread = null;
  }

  // Main Game Loop
  public void run() {
    Graphics g = getGraphics();

    Thread currentThread = Thread.currentThread();

    try {
      while (currentThread == gameThread) {
      	long startTime = System.currentTimeMillis();
      	if (isShown()) {
      	  if (isPlay) {
      	    tick();
      	  }
          render(g);
      	}
      	long timeTake = System.currentTimeMillis() - startTime;
      	if (timeTake < MILLIS_PER_TICK) {
      	  synchronized (this) {
      	    wait(MILLIS_PER_TICK - timeTake);
      	  }
      	} else {
      	  currentThread.yield();
      	}
      }
    } catch (InterruptedException ex) {
      // won't be thrown
    }

  }

  // Handle dynamic changes to game including user input
  public void tick() {
    scrollTerrain();
  }

  public void commandAction(Command c, Displayable d) {
    if (c == backCommand) {
      midlet.mainMenuScreenShow(null);
    }
  }

  // Method to Display Graphics
  private void render(Graphics g) {

    // Set Background color to beige
    g.setColor(0xF8DDBE);
    g.fillRect(0,0,width,height);
    g.setColor(0x0000ff);

    // LayerManager Paint Graphics
    layerManager.paint(g,0,0);

    flushGraphics();
  }

  private TiledLayer loadTerrain() throws Exception {
    Image tileImages = Image.createImage("/terrain.png");
    TiledLayer tiledLayer = new TiledLayer(TILE_NUM_COL,TILE_NUM_ROW,tileImages,TILE_WIDTH,TILE_HEIGHT);

    // Define Terrain Map
    int[][] map = {
       {0,0,0,0,0,0},
       {3,0,0,0,0,0},
       {6,0,0,0,0,0},
       {6,0,0,0,1,2},
       {6,0,0,0,4,5},
       {6,0,0,0,7,8},
       {6,0,0,0,0,0},
       {9,0,1,2,3,0},
       {0,0,4,5,6,0},
       {0,0,7,8,9,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {3,0,0,0,0,0},
       {6,0,0,0,0,0},
       {6,0,0,0,1,2},
       {6,0,0,0,4,5},
       {6,0,0,0,7,8},
       {6,0,0,0,0,0},
       {9,0,1,2,3,0},
       {0,0,4,5,6,0},
       {0,0,7,8,9,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {3,0,0,0,0,0},
       {6,0,0,0,0,0},
       {6,0,0,0,1,2},
       {6,0,0,0,4,5},
       {6,0,0,0,7,8},
       {6,0,0,0,0,0},
       {9,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {3,0,0,0,0,1}
    };

    // Map Terrain Map with actual graphic from terrain.png
    for (int row=0; row<TILE_NUM_ROW; row++) {
      for (int col=0; col<TILE_NUM_COL; col++) {
      	tiledLayer.setCell(col,row,map[row][col]);
      }
    }
    return tiledLayer;
  }

  // Scroll Terrain
  private void scrollTerrain() {
    if (terrainScroll < 0) {
      terrainScroll += 2;
      terrain.setPosition(0,terrainScroll);
    }
  }

  // Init Terrain and set Terrain at the bottom of the map
  private void initTerrain() throws Exception {
    try {
      terrain = loadTerrain();
      terrainScroll = 1 - (TILE_HEIGHT * terrain.getRows()) + scnViewHeight;
      terrain.setPosition(0,terrainScroll);
    } catch (Exception e) {
      throw new Exception("GameScreen::initTerrain::" + e);
    }
  }
}