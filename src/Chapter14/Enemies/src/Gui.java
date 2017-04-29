import javax.microedition.lcdui.*;

public class Gui implements CommandListener {
  Command back = new Command("Back", Command.BACK, 1);
  Command cancel = new Command("Cancel", Command.BACK, 1);
  Command saveSettings = new Command(Utils.getStr(Utils.ID_OK), Command.SCREEN,1);
  Command resetHighScore = new Command(Utils.getStr(Utils.ID_RESET), Command.SCREEN,1);

  Elminator midlet;
  Settings settings;
  Score score;

  Form form;

  ChoiceGroup music = new ChoiceGroup("",Choice.MULTIPLE);
  ChoiceGroup sound = new ChoiceGroup("",Choice.MULTIPLE);
  ChoiceGroup vibrate = new ChoiceGroup("",Choice.MULTIPLE);
  ChoiceGroup autoFire = new ChoiceGroup("",Choice.MULTIPLE);  

  public Gui(Elminator midlet) {
    this.midlet = midlet;
  }  

  public Form textForm(String title, String text) {
    form = new Form(title);
    form.append(text);
    form.addCommand(back);
    form.setCommandListener(this);
    return form;
  }

  public Form settingsForm(Settings settings) throws Exception {
    this.settings = settings;
    music.append(Utils.getStr(Utils.ID_MUSIC),null);
    sound.append(Utils.getStr(Utils.ID_SOUND),null);
    vibrate.append(Utils.getStr(Utils.ID_VIBRATE),null);
    autoFire.append(Utils.getStr(Utils.ID_AUTOFIRE),null);
    form = new Form(Utils.getStr(Utils.ID_SETTINGS));
    form.append(music);
    form.append(sound);
    form.append(vibrate);
    form.append(autoFire);
    form.addCommand(cancel);
    form.addCommand(saveSettings);
    form.setCommandListener(this);

    this.settings.loadSettings();
    if (settings.music == 1)
      music.setSelectedIndex(0,true);
    if (settings.sound == 1)
      sound.setSelectedIndex(0,true);
    if (settings.vibrate == 1)
      vibrate.setSelectedIndex(0,true);
    if (settings.autoFire == 1)
      autoFire.setSelectedIndex(0,true);
    return form;
  }
  
  public Form highScoreForm(Score score) throws Exception {
    this.score = score;
    this.score.loadScores();
    String result = "";
    String[] names = score.getNames();
    int[] values = score.getValues();
    for (int i=0; i<names.length; i++) {
      result = result + (i+1) + " " + names[i] + " - " + values[i] + "\n";
    }
    form = new Form(Utils.getStr(Utils.ID_HIGH_SCORE));
    form.append(result);
    form.addCommand(back);
    form.addCommand(resetHighScore);
    form.setCommandListener(this);
    return form;        
  	
  }

  public void commandAction(Command cmd, Displayable displayable) {
     if (cmd == back || cmd == cancel) {
       midlet.mainMenuScreenShow(null);
     }

     if (cmd == saveSettings) {
       try {
      	 int m=0,s=0,v=0,a = 0;
      	 if(music.isSelected(0))
      	   m=1;
      	 if(sound.isSelected(0))
      	   s=1;
      	 if(vibrate.isSelected(0))
      	   v=1;
         if(autoFire.isSelected(0))
     	    a=1;
         settings.updateSettings(m,s,v,a);
         midlet.mainMenuScreenShow(null);
       }  catch (Exception ex) {
         midlet.showErrorMsg("null");
       }
     }
     
     if (cmd == resetHighScore) {
       try {
         score.reset();      
         midlet.mainMenuScreenShow(null);
       }  catch (Exception ex) {
         midlet.showErrorMsg("null");
       }     	
     }
  }
}
