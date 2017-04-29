import javax.microedition.lcdui.*;

public class SettingsScreen extends Form implements CommandListener {
  private Eliminator midlet;
  private Command backCommand = new Command("Back", Command.BACK, 1);
  private Command okCommand = new Command("OK", Command.SCREEN,1);


  private Settings settings;
  private static final String[] difficultyOptions = {"Easy","Medium","Hard"};
  private static final String[] autoFireOptions = {"Off","On"};
  private ChoiceGroup difficulty;
  private ChoiceGroup autoFire;


  public SettingsScreen (Eliminator midlet, Settings settings) throws Exception {
    super("Settings");
    this.midlet = midlet;
    this.settings = settings;
    difficulty = new ChoiceGroup("Difficulty", ChoiceGroup.POPUP, difficultyOptions,null);
    autoFire = new ChoiceGroup("Auto Fire", ChoiceGroup.POPUP, autoFireOptions,null);
    append(difficulty);
    append(autoFire);
    addCommand(backCommand);
    addCommand(okCommand);
    setCommandListener(this);
  }

  public void commandAction(Command c, Displayable d) {
    if (c == backCommand) {
      midlet.mainMenuScreenShow(null);
    } else if (c == okCommand) {
      processMenu();
    }
  }

  public void init() throws Exception {
    settings.loadSettings();
    difficulty.setSelectedIndex(settings.getDifficulty()-1,true);
    autoFire.setSelectedIndex(settings.getAutoFire(),true);
  }

  private void processMenu() {
    try {
      settings.updateSettings(difficulty.getSelectedIndex()+1,autoFire.getSelectedIndex());
      midlet.mainMenuScreenShow(null);
    }  catch (Exception ex) {
      midlet.showErrorMsg("null");
    }
  }
}