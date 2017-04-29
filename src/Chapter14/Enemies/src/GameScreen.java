import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.*;

public class GameScreen extends GameCanvas implements Runnable, CommandListener {
  static final int MILLIS_PER_TICK = 50;

  Elminator midlet;  // Hold the Main Midlet
  Settings settings;  // Hold Game Settings
  Score score;        // Hold Game Score
  Command backCommand = new Command("Back", Command.BACK,1);
  GameMap gameMap;

  boolean isPlay;   // Game Loop runs when isPlay is true
  int width;        // To hold screen width
  int height;       // To hold screen height

  int scnViewWidth; // Hold Width Screen View Port
  int scnViewHeight; // Hold Height Screen View Port

  // Main Game loop/thread
  Thread gameThread = null;

  // Layer Manager to manager background (terrain)
  LayerManager layerManager;

  // TiledLayer - Terrain
  TiledLayer terrain;


  // Sprites
  PlayerSprite player;
  Bullet moveBullet, fireBullet;


  // Variables to hold bullet info
  Vector bullets;
  Image bulletImages;

  // Additional timer to control seperate NPC movements, this
  // needs to be seperate from the main game loop
  Timer gTimer;
  int autoFireTimer;

  // Set Max NPC count
  static final int NPC_CT = 34;
  NPCSprite[] npc;
  Image npcImages;
  
  // To hold platform sprite under canons 
  Sprite[] platform;
  
  // Randomizer
  Random rand = new java.util.Random();


  // Constructor and initialization
  public GameScreen(Elminator midlet,Settings settings,Score score) throws Exception {
    super(true);
    this.midlet = midlet;
    this.score = score;
    this.settings = settings;

    this.settings.loadSettings();
    this.score.loadScores();

    addCommand(backCommand);
    setCommandListener(this);

    width = getWidth();     // get screen width
    height = getHeight();   // get screen height
    scnViewWidth = width;   // Set View Port width to screen width
    scnViewHeight = height; // Set View Port height to screen height

    isPlay = true;

    // setup map
    gameMap = new GameMap(scnViewHeight + 50);    
    terrain = gameMap.getTerrain();

    // setup player sprite
    Image xImage = Image.createImage("/player.png");
    
    player = new PlayerSprite (xImage,24,24,width,height);  // 24 = width of sprite in pixels, 18 is height of sprite in pixels
    player.setVisible(true);    

    // Init bullets
    bullets = new Vector();
    bulletImages = Utils.createImage("/bullets.png");

    // Init Extra objects
    xImage = Image.createImage("/extra.png");
    platform = new Sprite[8];
    for (int i=0; i<platform.length; i++) {
      platform[i] = new Sprite(xImage,20,20);      
    }
    
    // Init NPC images
    npcImages = Utils.createImage("/npc.png");
    
    // create NPC sprites
    npc = new NPCSprite[NPC_CT];  
    
    // Cleaner to use several for loops but efficient to individual
    // create each array, these objects should only be instantiate once
    // but re-used for each level and possible re-used in the same level
    npc[0] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_CANON1, width, height);
    npc[1] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_CANON1, width, height);
    npc[2] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_CANON1, width, height);
    npc[3] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_CANON1, width, height);
    npc[4] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_CANON1, width, height);
    
    npc[5] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_CANON2, width, height);
    npc[6] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_CANON2, width, height);
    npc[7] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_CANON2, width, height);
    npc[8] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_CANON2, width, height);
    npc[9] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_CANON2, width, height);
    
    npc[10] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER1, width, height);
    npc[11] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER1, width, height);
    npc[12] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER1, width, height);
    npc[13] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER1, width, height);
    
    npc[14] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER2, width, height);
    npc[15] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER2, width, height);
    npc[16] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER2, width, height);
    npc[17] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER2, width, height);
    
    npc[18] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER3, width, height);
    npc[19] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER3, width, height);
    npc[20] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER3, width, height);
    npc[21] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER3, width, height);
    
    npc[22] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER4, width, height);
    npc[23] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER4, width, height);
    npc[24] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER4, width, height);
    npc[25] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_FLYER4, width, height);
    
    npc[26] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_TANK1, width, height);
    npc[27] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_TANK1, width, height);
    npc[28] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_TANK1, width, height);
    npc[29] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_TANK1, width, height);

    npc[30] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_TANK2, width, height);
    npc[31] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_TANK2, width, height);
    npc[32] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_TANK2, width, height);
    npc[33] = new NPCSprite(npcImages,16,16,NPCSprite.NPC_TANK2, width, height);          
        
    initNPC(1);
    
    // Set NPC timer
    gTimer = new Timer();
    gTimer.schedule(new CountDown(this,CountDown.TASK_TIMER),100,100);
    autoFireTimer = 0;
    
    // dump load images
    xImage = null;
  }
  
  //  Method to initialize NPC position for each map, primarily this is for
  //  stationary NPCS ie: Canons
  private void initNPC(int level) {
    for (int i=0; i<npc.length; i++) {
      npc[i].setVisible(false);	
    }	
  	
    layerManager = new LayerManager();
    layerManager.append(player);  	
    switch(level) {
      case 1:      
        npc[0].setPosition(14,-170);       
        npc[0].setVisible(true);
        platform[0].setPosition(11,-172);
        platform[0].setVisible(true);
        
        layerManager.append(npc[0]);      
        layerManager.append(platform[0]);        
                
        npc[1].setPosition(width - 26,-70);              
        npc[1].setVisible(true);
        platform[1].setPosition(width - 29,-72);
        platform[1].setVisible(true);
        
        layerManager.append(npc[1]);
        layerManager.append(platform[1]);
        
        npc[2].setPosition(width/2,-270);              
        npc[2].setVisible(true);
        platform[2].setPosition(width - 29,-72);
        platform[2].setVisible(true);
        
        layerManager.append(npc[2]);
        layerManager.append(platform[2]);       
        
        
        break;
        
      case 2:
        break;
      case 3:
        break;
      case 4:
        break;
      case 5:
        break;
    }	
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
    try {
    	
      // Scroll Terrain
      gameMap.scrollTerrain();


      // Move NPCs
      for (int i=0; i<npc.length; i++) {
      	if (npc[i].isVisible()) {
      		
      	  // Move NPCS at the same rate as the map, of course this
      	  // only applies to stationary NPCs, this will be changed
      	  // for NPCs that are NOT stationary
      	  
      	  // Move Canon 	
          npc[i].move(0,gameMap.getMapScrollAmount());
          
          // Move respective Canon Platform
          platform[i].move(0,gameMap.getMapScrollAmount());                 
          
          // Possibly Fire Bullets
          Bullet bulletx = npc[i].fire(player,bulletImages);
          if (bulletx != null) {
            bullets.addElement(bulletx);
            layerManager.insert(bulletx,1);
          }
        }
      }

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
      } else {
      	player.setTiltOff();      
      }


      // Player Fires
      if ((keyStates & FIRE_PRESSED) != 0 && settings.autoFire != 1) {
        playerFire();        
      }


      // move bullets
      for (int i = 0; i < bullets.size(); ++i)  {
        for (int j = 0; j < 2; ++j) {
          moveBullet = (Bullet)(bullets.elementAt(i));
          moveBullet.forward(2);
   
          if (moveBullet.getY() < 0 || moveBullet.getY() > height) {
            bullets.removeElementAt(i);
            layerManager.remove(moveBullet);
            i--;
            break;
          }
        }
      }
      
    } catch (Exception e) {
      stop();
      midlet.showErrorMsg(null);
    }
  }

  public void commandAction(Command c, Displayable d) {
    if (c == backCommand) {
      midlet.mainMenuScreenShow(null);
    }
  }

  // Method to Display Graphics
  private void render(Graphics g) {

    g.setFont(Utils.cFont);

    g.setColor(gameMap.getGroundColor());
    g.fillRect(0,0,width,height);
    g.setColor(0x0000ff);

    // Get Current Map
    terrain = gameMap.getTerrain();

    // LayerManager Paint Graphics
    layerManager.paint(g,0,0);


    ////////// Score /////////////////
    String currentScore = "583950"; // just a place holder for now
    g.setColor(0x00FFFFFF);
    g.drawString(currentScore,width/2 - 1 - Utils.cFont.stringWidth(currentScore) / 2 ,3,20);
    g.setColor(0x00000000);
    g.drawString(currentScore,width/2 - Utils.cFont.stringWidth(currentScore) / 2,2,20);
   

    // Display White BG for Energy Bar  (not complete)
    g.setColor(0x00FFFFFF);
    g.fillRect(2,height-10,80,9);

    int e = 39;
    g.setColor(0x00000000);
    g.fillRect(3,height-9,40,7);
    for (int i=0; i<e; i++) {
      if (i>=0)
        g.setColor(0x00FF0000);
      if (i>5)
        g.setColor(0x00FFFF00);
      if (i>18)
        g.setColor(0x0000FF00);
      g.fillRect(3+ (i*2),height-9,2,7);
      g.setColor(0x00000000);
    }

    flushGraphics();


  }


  private void playerFire() throws Exception {
    fireBullet = player.fire(bulletImages);
    if (fireBullet != null) {
      bullets.addElement(fireBullet);
      layerManager.insert(fireBullet,1);

    }
  }

  protected void timerTask() {

    autoFireTimer += 100;
    try {
      if (autoFireTimer >= 250) {
      	if (settings.autoFire == 1)
          playerFire();
        autoFireTimer = 0;
      }
    } catch (Exception e) {
      System.out.println("timer exception :: " + e);
    }
  }  

}