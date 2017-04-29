import javax.microedition.lcdui.*;

public class HighScoreScreen extends Form implements CommandListener {
  private Eliminator midlet;
  private Command backCommand = new Command("Back", Command.BACK, 1);
  private Command resetCommand = new Command("Rest", Command.SCREEN,1);

  public HighScoreScreen (Eliminator midlet) throws Exception {
    super("High Score");
    this.midlet = midlet;
    StringItem stringItem = new StringItem(null,"JL    100\nJL      50\nJL      10");
    append(stringItem);
    addCommand(backCommand);
    addCommand(resetCommand);
    setCommandListener(this);
  }

  public void commandAction(Command c, Displayable d) {
    if (c == backCommand) {
      midlet.mainMenuScreenShow(null);
      return;
    }
    if (c == resetCommand) {
      // not implemented yet
      System.out.println("Reset High Scores Not Implemented Yet");
    }
  }
}