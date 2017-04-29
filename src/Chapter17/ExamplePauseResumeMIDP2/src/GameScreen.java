import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.*;

public class GameScreen extends GameCanvas implements Runnable, CommandListener {
  private static final int MILLIS_PER_TICK = 50;

  private Command exitCommand = new Command("Exit", Command.BACK,1);
  private Command pauseCommand = new Command("Pause", Command.SCREEN,1);
  private Command resumeCommand = new Command("Resume", Command.SCREEN,1);

  private ExamplePauseResumeMIDP2 midlet;

  private boolean isPlay;   // Game Loop runs when isPlay is true
  private int width;        // To hold screen width
  private int height;       // To hold screen height

  private Thread gameThread = null; // Main Game Thread/loop

  // Layer Manager
  private LayerManager layerManager;

  // Pause flag
  private boolean isPause;

  // Variables used for demo animation
  private int posX;
  private int posY;
  private boolean isUp;
  
  public GameScreen(ExamplePauseResumeMIDP2 midlet) throws Exception {
    super(true);
    this.midlet = midlet;
    addCommand(exitCommand);
    addCommand(pauseCommand);
    setCommandListener(this);

    width = getWidth();  // get screen width
    height = getHeight();  // get screen height

    isPlay = true;
    isPause = false;

    posX = width/2;
    posY = height/2;
    isUp = true;

    layerManager = new LayerManager();
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
    try {
      if (isUp) {
      	if (posY-3 > 0) {
      	  posY-=3;
      	} else {
      	  isUp = false;
        }
      }

      if (!isUp) {
      	if (posY+3 < height) {
      	  posY+=3;
      	} else {
      	 isUp = true;
      	}
      }

    } catch (Exception e) {
      stop();
    }
  }

  // Handle Soft-Key reponses
  public void commandAction(Command c, Displayable d) {
    if (c == exitCommand) {
      midlet.exitGame();
    }

    if (c == pauseCommand) {
      pauseGame();
    }

    if (c == resumeCommand) {
      resumeGame();
    }
  }

  // Method to Display Graphics
  private void render(Graphics g) {
    g.setColor(0x00FFFFFF);
    g.fillRect(0,0,width,height);
    g.setColor(0x000000FF);
    g.drawString("X",posX,posY,Graphics.TOP|Graphics.LEFT);
    layerManager.paint(g,0,0);
    flushGraphics();
  }

  // This where you should handle game auto-save, save to either network server
  // and/or local RMS
  public void autoSave() {
    // Auto Save Stuff should be here
    System.out.println("Auto Saving Logic Should Be Here");
  }

  public void pauseGame() {
    // You may or may not want to have auto-save done at Pause, that is why when you exit
    // the system console outpus and extra 2 auto saving messages, total 3 including the
    // the call to autosave when exiting
    autoSave();
    removeCommand(pauseCommand);
    addCommand(resumeCommand);
    isPause=true;
    stop();
  }

  public void resumeGame() {
    removeCommand(resumeCommand);
    addCommand(pauseCommand);
    isPause=false;
    start();
  }

  public void hideNotify() {
    pauseGame();
  }

  public void showNotify() {
    resumeGame();
  }
}