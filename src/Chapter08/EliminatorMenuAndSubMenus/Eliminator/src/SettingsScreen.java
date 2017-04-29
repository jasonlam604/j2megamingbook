import javax.microedition.lcdui.*;

public class SettingsScreen extends List implements CommandListener {
  private Eliminator midlet;
  private Command backCommand = new Command("Back", Command.BACK, 1);
  private Command selectCommand = new Command("Select", Command.SCREEN,1);

  public SettingsScreen (Eliminator midlet) {
    super("Settings",Choice.IMPLICIT);
    append("Difficulty",null);
    append("Controls",null);
    this.midlet = midlet;
    addCommand(backCommand);
    addCommand(selectCommand);
    setCommandListener(this);
  }

  public void commandAction(Command c, Displayable d) {
    if (c == backCommand) {
      midlet.mainMenuScreenShow();
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
         case 0: setDifficulty(); break;
         case 1: setControls(); break;
       };
    } catch (Exception ex) {
      // Proper Error Handling should be done here
      System.out.println("processMenu::"+ex);
    }
  }

  private void setDifficulty() {
    System.out.println("Difficutly Settings Not Implemented Yet");
  }

  private void setControls() {
    System.out.println("Controls Settings Not Implemented Yet");
  }
}