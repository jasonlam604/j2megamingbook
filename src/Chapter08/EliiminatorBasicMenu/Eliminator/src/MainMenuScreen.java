import javax.microedition.lcdui.*;

public class MainMenuScreen  extends List implements CommandListener {
  private Eliminator midlet;

  private Command selectCommand = new Command("Select", Command.ITEM,1);
  private Command exitCommand = new Command("Exit", Command.EXIT,1);
  
  
  private Alert alert;

  public MainMenuScreen(Eliminator midlet) {
    super("Eliminator",Choice.IMPLICIT);
    this.midlet = midlet;
    append("New Game",null);
    append("Settings",null);
    append("High Scores", null);
    append("Help",null);
    append("About",null);
    addCommand(exitCommand);
    addCommand(selectCommand);
    setCommandListener(this);
  }


  public void commandAction(Command c, Displayable d) {
    if (c == exitCommand) {    	
      midlet.mainMenuScreenQuit();
      return;
    } else if (c == selectCommand) {
      processMenu();  return;
    } else {
      processMenu();  return;
    }
  }

  private void processMenu() {
    try {
       List down = (List)midlet.display.getCurrent();
       switch (down.getSelectedIndex()) {
         case 0: scnNewGame(); break;         
         case 1: scnSettings(); break;        
         case 2: scnHighScores(); break;
         case 3: scnHelp(); break;
         case 4: scnAbout(); break;
       };
    } catch (Exception ex) {
      // Proper Error Handling should be done here	
      System.out.println("processMenu::"+ex);
    }
  }
  
  private void scnNewGame() {        
    midlet.mainMenuScreenShow(null);
  }
  
  private void scnSettings() {
    alert = new Alert("Settings"
                     ,"Settings......."
                     ,null
                     ,null);
    alert.setTimeout(Alert.FOREVER);
    alert.setType(AlertType.INFO);
    midlet.mainMenuScreenShow(alert);   
  }  

  private void scnHighScores() {
    alert = new Alert("High Scores"
                     ,"High Scores......."
                     ,null
                     ,null);
    alert.setTimeout(Alert.FOREVER);
    alert.setType(AlertType.INFO);
    midlet.mainMenuScreenShow(alert);
  }

  private void scnHelp() {
    alert = new Alert("Help"
                     ,"Help...................."
                     ,null
                     ,null);
    alert.setTimeout(Alert.FOREVER);
    alert.setType(AlertType.INFO);
    midlet.mainMenuScreenShow(alert);
  }

  private void scnAbout() {
    alert = new Alert("About"
                     ,"Eliminator\nVersion 1.0.0\nby Jason Lam"
                     ,null
                     ,null);
    alert.setTimeout(Alert.FOREVER);
    alert.setType(AlertType.INFO);
    midlet.mainMenuScreenShow(alert);
  }
}