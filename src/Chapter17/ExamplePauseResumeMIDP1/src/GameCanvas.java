import javax.microedition.lcdui.*;
import java.util.*;

public class GameCanvas extends Canvas implements Runnable, CommandListener {
  private static final int MILLIS_PER_TICK = 50;
  
  private Command backCommand = new Command("Exit", Command.BACK,1);
  private Command pauseCommand = new Command("Pause", Command.SCREEN,1);
  private Command resumeCommand = new Command("Resume", Command.SCREEN,1);
  
  private ExamplePauseResumeMIDP1 midlet; 

  private boolean isPlay;
  private int width;
  private int height;

  private Thread gameThread = null;
  private Image buffer;  
  
  // Pause flag
  private boolean isPause;

  // Variables used for demo animation
  private int posX;
  private int posY;
  private boolean isUp; 
  
  public GameCanvas(ExamplePauseResumeMIDP1 midlet) throws Exception {
    this.midlet = midlet;
    addCommand(backCommand);
    addCommand(pauseCommand);
    setCommandListener(this);
       
    width = getWidth();  // get screen width
    height = getHeight();  // get screen height

    isPlay = true;
    isPause = false;

    posX = width/2;
    posY = height/2;
    isUp = true;

    if (!isDoubleBuffered())
      buffer = Image.createImage(width,height);
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

    Thread currentThread = Thread.currentThread();

    try {
      while (currentThread == gameThread) {
      	long startTime = System.currentTimeMillis();
      	if (isShown()) {
      	  if (isPlay) {
      	    tick();
      	  }
          repaint(0,0,width,height);
          serviceRepaints();
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
      synchronized(this){
        if(gameThread == Thread.currentThread()) {
           gameThread = null;
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

  public void keyPressed(int keyCode)  {

  }

  // Handle Soft-Key reponses
  public void commandAction(Command c, Displayable d) {
    if (c == backCommand) {
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
  public void paint(Graphics g) {
    Graphics gr = g;
    try {
      if (buffer != null)
        g = buffer.getGraphics();
        g.setColor(0x00FFFFFF);
        g.fillRect(0,0,width,height);
        g.setColor(0x000000FF);
        g.drawString("X",posX,posY,Graphics.TOP|Graphics.LEFT);
      if(g!= gr)
        gr.drawImage(buffer,0,0,20);
    } catch (Exception e) {
      System.out.println("Animation Error");
    }
  }
  
  // This where you should handle game auto-save, save to either network server
  // and/or local RMS  
  public void autoSave() {
    // Auto Save Stuff should be here	
    System.out.println("Exiting and Auto Saving");
  }    
  
  public void pauseGame() {
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