import javax.microedition.rms.*;
import java.util.*;
import java.io.*;

public class Settings {

  private String rmsName;
  private RecordStore recordStore;
  
  // save room not getters/setters
  public int music = 1;
  public int sound = 1;
  public int vibrate = 1;
  public int autoFire = 1;
  
  public int level = 0;
  public int score = 0;

  public Settings(String rmsName) {
    this.rmsName = rmsName;
  }
  
  public void open() throws Exception {
    try {
      recordStore = RecordStore.openRecordStore(this.rmsName,true);
      if (recordStore.getNumRecords() > 0) {
        loadData();
      } else {
        createDefaultData();
      }
    } catch (Exception e) {
      throw new Exception(this.rmsName+"::open::"+e);
    }
  }

  public void close() throws Exception {
    if (recordStore != null) {
      try {
        recordStore.closeRecordStore();
      } catch(Exception e) {
      	throw new Exception(this.rmsName+"::close::"+e);
      }
    }
  }

  public void loadSettings() throws Exception {
    try {
      this.open();    
      if (this.recordStore != null)
        this.close();
    } catch (Exception e) {
      throw new Exception("Error loading Settings" + e);
    }
  }

  public void updateSettings(int music, int sound, int vibrate,int autoFire) throws Exception {
    try {      
      this.open();      
      this.music = music;
      this.sound = sound;
      this.vibrate = vibrate;
      this.autoFire = autoFire;
      updateData();
      if (this.recordStore != null)
        this.close();
    } catch (Exception e) {
      throw new Exception("updateSettings::" + e);
    }
  }
  
  public void updateUserSave(int level, int score) throws Exception {
    try {      
      this.open();      
      this.level = level;
      this.score = score;
      updateData();
      if (this.recordStore != null)
        this.close();
    } catch (Exception e) {
      throw new Exception("updateSettings::" + e);
    }
  }  

  protected void loadData() throws Exception {
    try {
        byte[] record = this.recordStore.getRecord(1);
        DataInputStream istream = new DataInputStream(new ByteArrayInputStream(record,0,record.length));
        music = istream.readInt();
        sound = istream.readInt();
        vibrate = istream.readInt();
        autoFire = istream.readInt();
        level = istream.readInt();
        score = istream.readInt();
    } catch (Exception e) {
      throw new Exception ("loadData::" + e);
    }
  }

  protected void createDefaultData() throws Exception {
    try {
      ByteArrayOutputStream bstream = new ByteArrayOutputStream();
      DataOutputStream ostream = new DataOutputStream(bstream);
      ostream.writeInt(music);
      ostream.writeInt(sound);
      ostream.writeInt(vibrate);
      ostream.writeInt(autoFire);
      ostream.writeInt(level);
      ostream.writeInt(score);
      ostream.flush();
      ostream.close();
      byte[] record = bstream.toByteArray();
      this.recordStore.addRecord(record, 0, record.length);
    } catch (Exception e) {
      throw new Exception("createDefaultData::" + e);
    }
  }

  protected void updateData() throws Exception {
    try {
      ByteArrayOutputStream bstream = new ByteArrayOutputStream();
      DataOutputStream ostream = new DataOutputStream(bstream);
      ostream.writeInt(music);
      ostream.writeInt(sound);
      ostream.writeInt(vibrate);
      ostream.writeInt(autoFire);
      ostream.writeInt(level);
      ostream.writeInt(score);
      ostream.flush();
      ostream.close();
      byte[] record = bstream.toByteArray();
      this.recordStore.setRecord(1, record, 0, record.length);
    } catch(Exception e) {
      throw new Exception("updateData::" + e);
    }
  }
}