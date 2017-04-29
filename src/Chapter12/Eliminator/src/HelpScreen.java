import javax.microedition.lcdui.*;

public class HelpScreen extends Form implements CommandListener {
  private Eliminator midlet;

  private Command backCommand = new Command("Back", Command.BACK, 1);

  public HelpScreen (Eliminator midlet) throws Exception {
    super("Help");
    this.midlet = midlet;
    StringItem stringItem = new StringItem(null,
    "It is the year 3023, many things have changed over the years " +
    "including the Great Migration to Earth II.  " +
    "But the one thing that hasn't changed is the struggle for " +
    "Power and Wealth.  You are one the last remaning hopes " +
    "to defend Earth II from total dominance by Evil Ruler named " +
    "Zikron.  You mission is to eliminate Zikron and his " +
    "minions from the face of the Earth II"
                                                );
    append(stringItem);
    addCommand(backCommand);
    setCommandListener(this);
  }  

  public void commandAction(Command c, Displayable d) {
    if (c == backCommand) {
      midlet.mainMenuScreenShow(null);
      return;
    }
  }
}