import javax.microedition.lcdui.*;

public class MenuScreen extends Canvas implements Runnable { 	
	
  static final Font lowFont  = Font.getFont (Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL);  
  static final Font highFont = Font.getFont (Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM);  
  
  static final int lowColor  = 0x000000FF;
  static final int highColor = 0x00FF0000;
  static final int highBGColor = 0x00CCCCCC;
	
  static int width;
  static int height;
  
  static int startHeight;
  
  static final int spacing = highFont.getHeight()/2;
  
  static final int NEW_GAME = 0;
  static final int HIGH_SCORE = 1;
  static final int SETTINGS = 2;
  static final int HELP = 3;
  static final int ABOUT = 4;
  static final int MENU_ITEM_COUNT = 5;
  static String[] mainMenu = new String[MENU_ITEM_COUNT];
  
  static int menuIdx;
  
  Thread menuThread;
     
  public MenuScreen() {
    width = getWidth();
    height = getHeight();

    mainMenu[0] = "New Game";
    mainMenu[1] = "High Score";
    mainMenu[2] = "Settings";
    mainMenu[3] = "Help";
    mainMenu[4] = "About";    
    
    startHeight = (highFont.getHeight() * mainMenu.length) + ((mainMenu.length-1) * spacing);
    startHeight = (height - startHeight) / 2;  
    
    menuIdx = 0;
    
    menuThread = new Thread(this);
    menuThread.start();       
  }
  
  public void run() {
    while(true) {
      repaint();	    
    }    
  }
  
  public void paint(Graphics g) {
    	
    g.setColor(0x00FFFFFF);
    g.fillRect(0,0,width,height);
    
    
    for (int i=0; i<mainMenu.length; i++) {
    	
      if (i==menuIdx) {
      	g.setColor(highBGColor);
      	g.fillRect(0,startHeight + (i*highFont.getHeight()) + spacing,width,highFont.getHeight());
        g.setFont(highFont);
        g.setColor(highColor);	
        g.drawString(mainMenu[i],(width - highFont.stringWidth(mainMenu[i])   ) / 2,startHeight + (i*highFont.getHeight()) + spacing,20);      	
      } else {
        g.setFont(lowFont);
        g.setColor(lowColor);	
        g.drawString(mainMenu[i],(width - lowFont.stringWidth(mainMenu[i])   ) / 2,startHeight + (i*highFont.getHeight()) + spacing,20);
      } 
    } 
  }
  
  protected void keyPressed (int code) {
    if (getGameAction(code) == Canvas.UP && menuIdx - 1 >= 0) {      
      menuIdx--;	      
    } else if (getGameAction(code) == Canvas.DOWN && menuIdx + 1 < mainMenu.length) {
      menuIdx++;      		
    } else if (getGameAction(code) == Canvas.FIRE) {
      	
       switch(menuIdx) {
         case NEW_GAME:   System.out.println("Start New Game"); break;
         case HIGH_SCORE: System.out.println("Display High Score"); break;
         case SETTINGS:   System.out.println("Display Settings"); break;
         case HELP:       System.out.println("Display Help"); break;
         case ABOUT:      System.out.println("Display About Info."); break;      	
       }
    }
  }  
}