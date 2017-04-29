import javax.microedition.lcdui.*;

public class MenuScreen extends Canvas implements Runnable { 	

  // Set Fonts	
  static final Font lowFont  = Font.getFont (Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL);  
  static final Font highFont = Font.getFont (Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_MEDIUM);  
  
  // Set Color
  static final int lowColor  = 0x0000FF00;    // Not Highlighted  
  static final int highColor = 0x000000FF;    // Highlighted
  static final int highBGColor = 0x00CCCCCC;  // Highlighted Background 
    
	
  static int width;   // screen width
  static int height;  // screen height
  
  static int startHeight;  // height where the menu starts
  
  static final int spacing = highFont.getHeight()/2;  // spacing between menu items
  
  // Menu Item Labels
  static final String[] mainMenu = {"New Game","High Score","Settings","Help","About"};
  
  // To hold the current highlighted menu option
  static int menuIdx;
  
  // Menu Thread
  Thread menuThread;
  
  // BG Image
  Image bgImage;
     
  // Constructor   
  public MenuScreen() {
  	
    // Get Width and Height of Canvas 	
    width = getWidth();
    height = getHeight();
    
    // Calculate Start Height of Menu
    startHeight = (highFont.getHeight() * mainMenu.length) + ((mainMenu.length-1) * spacing);
    startHeight = (height - startHeight) / 2;
    
    // Set Selected Menu Item to the first menu item
    menuIdx = 0;
    
    // Set BG Image
    try {
      bgImage = Image.createImage("/bg.png");   
    } catch (Exception e) {
      System.out.println("Error creating Background Image :: " + e);	
    }
    
    // Create Thread and Start
    menuThread = new Thread(this);
    menuThread.start();       
  }
  
  // Simple Run -- Should be modified for better performance/efficiency
  public void run() {
    while(true) {
      repaint();	    
    }    
  }
  
  // Paint Main Menu
  public void paint(Graphics g) {
    	
    g.setColor(0x00000000);
    g.fillRect(0,0,width,height);
    
    // Draw Background Image
    g.drawImage(bgImage,(width - bgImage.getWidth()) / 2, (height - bgImage.getHeight())/2,20); 
    
    for (int i=0; i<mainMenu.length; i++) {
    	
      if (i==menuIdx) {
      	// Removed Highlight Bar
      	//g.setColor(highBGColor);
      	//g.fillRect(0,startHeight + (i*highFont.getHeight()) + spacing,width,highFont.getHeight());
        g.setFont(highFont);
   
        g.setColor(highColor);	
        g.drawString(mainMenu[i],
                     (width - highFont.stringWidth(mainMenu[i])) / 2,
                      startHeight + (i*highFont.getHeight()) + spacing,
                      20
                     );      	
                     
                     
      } else {
        g.setFont(lowFont);
        g.setColor(lowColor);	
        g.drawString(mainMenu[i],
                     (width - lowFont.stringWidth(mainMenu[i])   ) / 2,
                     startHeight + (i*highFont.getHeight()) + spacing,
                     20
                    );
      } 
    } 
  }  
  
  // Capture Keypresses for Menu Selection
  protected void keyPressed (int code) {
    if (getGameAction(code) == Canvas.UP && menuIdx - 1 >= 0) {      
      menuIdx--;	      
    } else if (getGameAction(code) == Canvas.DOWN && menuIdx + 1 < mainMenu.length) {
      menuIdx++;      		
    }
  }  
}