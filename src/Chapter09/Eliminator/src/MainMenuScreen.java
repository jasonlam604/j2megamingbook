import javax.microedition.lcdui.*;

public class MainMenuScreen  extends List implements CommandListener {
  private Eliminator midlet;
  private Command selectCommand = new Command("Select", Command.ITEM,1);
  private Command exitCommand = new Command("Exit", Command.EXIT,1);
  
  public MainMenuScreen(Eliminator midlet) throws Exception {
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
    } else if (c == selectCommand) {
      processMenu();
    } else {
      processMenu();
    }
  }

  private void processMenu() {
    try {
       List down = (List)midlet.display.getCurrent();
       switch (down.getSelectedIndex()) {
         case 0: scnNewGame(); break;
         case 1: scnSettings(); break;
         case 2: scnHighScore(); break;
         case 3: scnHelp(); break;
         case 4: scnAbout(); break;
       };
    } catch (Exception ex) {      
      midlet.showErrorMsg("null");      
    }
  }

  private void scnNewGame() {
    midlet.mainMenuScreenShow(null);
  }

  private void scnSettings() {
    midlet.settingsScreenShow();
  }

  private void scnHighScore() {
    midlet.highScoreScreenShow();
  }

  private void scnHelp() {
    midlet.helpScreenShow();
  }

  private void scnAbout() {
    midlet.aboutScreenShow();
  }
}