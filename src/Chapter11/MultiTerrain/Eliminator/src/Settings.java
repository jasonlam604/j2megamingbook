import javax.microedition.rms.*;
import java.util.*;
import java.io.*;

public class Settings extends BaseRMS {
  private int difficulty = 1;
  private int autoFire = 1;

  public Settings(String rmsName) {
    super(rmsName);
  }

  public void loadSettings() throws Exception {
    try {
      // Will call either loadData() or createDefaultData()
      this.open();

      // Add any other neccessary processing, in this case there is none

      // Close
      if (this.getRecordStore() != null)
        this.close();
    } catch (Exception e) {
      throw new Exception("Error loading Settings" + e);
    }
  }

  public int getDifficulty() {
    return this.difficulty;
  }

  public int getAutoFire() {
    return this.autoFire;
  }

  public void updateSettings(int difficulty,int autoFire) throws Exception {
    try {
      // load current scores
      this.open();

      // update data
      this.difficulty = difficulty;
      this.autoFire = autoFire;

      // Update High Scores
      updateData();

      // close
      if (this.getRecordStore() != null)
        this.close();
    } catch (Exception e) {
      throw new Exception(this.getRMSName() + "::updateSettings::" + e);
    }
  }

  protected void loadData() throws Exception {
    try {
        byte[] record = this.getRecordStore().getRecord(1);
        DataInputStream istream = new DataInputStream(new ByteArrayInputStream(record,0,record.length));
        difficulty = istream.readInt();
        autoFire = istream.readInt();
    } catch (Exception e) {
      throw new Exception (this.getRMSName() + "::loadData::" + e);
    }
  }

  protected void createDefaultData() throws Exception {
    try {
      ByteArrayOutputStream bstream = new ByteArrayOutputStream(12);
      DataOutputStream ostream = new DataOutputStream(bstream);
      ostream.writeInt(difficulty);
      ostream.writeInt(autoFire);
      ostream.flush();
      ostream.close();
      byte[] record = bstream.toByteArray();
      this.getRecordStore().addRecord(record, 0, record.length);
    } catch (Exception e) {
      throw new Exception(this.getRMSName() + "::createDefaultData::" + e);
    }
  }

  protected void updateData() throws Exception {
    try {
      ByteArrayOutputStream bstream = new ByteArrayOutputStream(12);
      DataOutputStream ostream = new DataOutputStream(bstream);
      ostream.writeInt(difficulty);
      ostream.writeInt(autoFire);
      ostream.flush();
      ostream.close();
      byte[] record = bstream.toByteArray();
      this.getRecordStore().setRecord(1, record, 0, record.length);
    } catch(Exception e) {
      throw new Exception(this.getRMSName() + "::updateData::" + e);
    }
  }
}