import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.*;

public class GameScreen extends GameCanvas implements Runnable, CommandListener {
  private static final int MILLIS_PER_TICK = 50;

  private Eliminator midlet;  // Hold the Main Midlet
  private Settings settings;  // Hold Game Settings
  private Score score;        // Hold Game Score
  private Command backCommand = new Command("Back", Command.BACK,1);
  private GameMap gameMap;

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
  
  // Sprites
  private PlayerSprite player;  
    
  // Variables to hold bullet info
  private Vector bullets;
  private Image bulletImages; 
  

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

    // setup map
    gameMap = new GameMap(scnViewHeight);
    terrain = gameMap.getTerrain();
    
    // setup player sprite
    Image image = Image.createImage("/player.png");
    player = new PlayerSprite (image,24,18,width,height);  // 24 = width of sprite in pixels, 18 is height of sprite in pixels
    player.startPosition();

    // init bullets
    bullets = new Vector();
    bulletImages = midlet.createImage("/bullets.png");
    
    layerManager = new LayerManager();
    layerManager.append(player);    
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
    // Scroll Terrain	
    gameMap.scrollTerrain();   
   
    // Player Actions 
    int keyStates = getKeyStates();
    
    // Player Moves
    if ( (keyStates & LEFT_PRESSED) != 0)  {
      player.moveLeft();
    } else if ((keyStates & RIGHT_PRESSED) !=0 )  {
      player.moveRight();
    } else if ((keyStates & UP_PRESSED) != 0) {
      player.moveUp();
    } else if ((keyStates & DOWN_PRESSED) != 0) {
      player.moveDown();
    }
    
    // Player Fires
    if ((keyStates & FIRE_PRESSED) != 0) {
      Sprite bullet = player.fire(bulletImages);
      if (bullet != null) {
        bullets.addElement(bullet);
        layerManager.insert(bullet,1);    	
      }
    }

    // Update Bullet(s) Movement 
    for (int i = 0; i < bullets.size(); ++i)  {            
      for (int j = 0; j < 2; ++j) {
        Sprite bullet = (Sprite)(bullets.elementAt(i));
        bullet.move(0, -1);
        if (bullet.getY() < 0) {
          bullets.removeElementAt(i);
          layerManager.remove(bullet);
          i--;
          break;
         }
      }
    }
    
  }

  public void commandAction(Command c, Displayable d) {
    if (c == backCommand) {
      midlet.mainMenuScreenShow(null);
    }
  }

  // Method to Display Graphics
  private void render(Graphics g) {

    // Set Background color to beige
    //g.setColor(0xF8DDBE);
    g.setColor(gameMap.getGroundColor());
    g.fillRect(0,0,width,height);
    g.setColor(0x0000ff);

    // Get Current Map 
    terrain = gameMap.getTerrain();

    // LayerManager Paint Graphics
    layerManager.paint(g,0,0);

    flushGraphics();
  }  
}