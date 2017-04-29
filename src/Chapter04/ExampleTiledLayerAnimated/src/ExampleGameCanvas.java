import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class ExampleGameCanvas extends GameCanvas implements Runnable {
  private boolean isPlay;   // Game Loop runs when isPlay is true
  private long delay;       // To give thread consistency
  private int currentX, currentY;  // To hold current position of the 'X'
  private int width;        // To hold screen width
  private int height;       // To hold screen height


  // Layer Manager
  private LayerManager layerManager;

  // TiledLayer
  private TiledLayer tiledBackground;
  
  // Flag to indicate tile switch
  private boolean switchTile;
  
  // To hold the animated tile index
  private int animatedIdx;

  // Constructor and initialization
  public ExampleGameCanvas() throws Exception {
    super(true);
    width = getWidth();
    height = getHeight();

    currentX = width / 2;
    currentY = height / 2;
    delay = 20;

    tiledBackground = initBackground();
    layerManager = new LayerManager();
    layerManager.append(tiledBackground);
  }

  // Automatically start thread for game loop
  public void start() {
    isPlay = true;
    Thread t = new Thread(this);
    t.start();
  }

  public void stop() { isPlay = false; }

  // Main Game Loop
  public void run() {
    Graphics g = getGraphics();

    while (isPlay == true) {
      input();
      drawScreen(g);
      try {
        Thread.sleep(delay);
      } catch (InterruptedException ie) {
      }
    }
  }

  // Method to Handle User Inputs
  private void input() {
     // no inputs
  }

  // Method to Display Graphics
  private void drawScreen(Graphics g) {
    g.setColor(0xffffff);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.setColor(0x0000ff);
    
    // Determine which tile to show
    if (switchTile) {
      tiledBackground.setAnimatedTile(animatedIdx,3);      
    } else {
      tiledBackground.setAnimatedTile(animatedIdx,4);      
    }
    
    // Set tile file to opposite boolean value
    switchTile = !switchTile;   
    
    layerManager.paint(g,0,0);
    flushGraphics();
  }

  private TiledLayer initBackground() throws Exception {
    Image tileImages = Image.createImage("/tiles.png");
    TiledLayer tiledLayer = new TiledLayer(10,10,tileImages,32,32);

    int[] map = {
       5,  1,  1,  4,  1,  1,  1,  1,  1,  6,
       5,  1,  3,  1,  1,  3,  1,  1,  1,  6,
       5,  1,  2,  1,  1,  2,  1,  1,  1,  6,
       5,  1,  2,  3,  1,  2,  1,  1,  1,  6,
       5,  1,  4,  2,  1,  2,  1,  1,  1,  6,
       5,  1,  1,  4,  1,  2,  1,  1,  1,  6,
       5,  1,  1,  1,  1,  4,  1,  1,  1,  6,
       5,  1,  1,  1,  1,  1,  1,  1,  1,  6,
       5,  1,  1,  1,  1,  1,  1,  1,  1,  6,
       5,  1,  1,  1,  1,  1,  1,  1,  1,  6
    };
    for (int i=0; i < map.length; i++) {
      int column = i % 10;
      int row = (i - column) / 10;
      tiledLayer.setCell(column,row,map[i]);
    }   
    
    // Created animate tile and hold animated tile index
    animatedIdx = tiledLayer.createAnimatedTile(5);
    
    // Set Cell with animated tile index
    tiledLayer.setCell(1,1,animatedIdx);

    return tiledLayer;
  }
}