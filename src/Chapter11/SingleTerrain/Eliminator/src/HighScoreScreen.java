import javax.microedition.lcdui.*;

public class HighScoreScreen extends Form implements CommandListener {
  private Eliminator midlet;
  private Command backCommand = new Command("Back", Command.BACK, 1);
  private Command resetCommand = new Command("Reset", Command.SCREEN,1);
  private Score score;
  StringItem stringItem;

  public HighScoreScreen (Eliminator midlet,Score score) throws Exception {
    super("High Score");
    this.midlet = midlet;
    this.score = score;
    stringItem = new StringItem(null,"");
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
      processMenu();
    }
  }

  public void init() throws Exception {
    score.loadScores();
    stringItem.setText(buildHighScore());
  }

  private void processMenu() {
    try {
      score.reset();      
      midlet.mainMenuScreenShow(null);
    }  catch (Exception ex) {
      midlet.showErrorMsg("null");
    }
  }

  private String buildHighScore() {
    String result = "";
    String[] names = score.getNames();
    int[] values = score.getValues();
    for (int i=0; i<names.length; i++) {
      result = result + names[i] + "   " + values[i] + "\n";
    }
    return result;
  }
}