import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Bullet extends Sprite {
  public static final int TYPE_PLAYER = 0;
  public static final int TYPE_NPC1_DAMAGE = 1;
  int type;
	
  private int mDirection;
  private int mKX, mKY;
  
  private int mLastDelta;
  private boolean mLastWasTurn;
  
  private static final int[] kTransformLookup = {  
    Sprite.TRANS_NONE, Sprite.TRANS_NONE, Sprite.TRANS_NONE, Sprite.TRANS_MIRROR_ROT90,
    Sprite.TRANS_ROT90, Sprite.TRANS_ROT90, Sprite.TRANS_ROT90,Sprite.TRANS_MIRROR_ROT180,Sprite.TRANS_ROT180, Sprite.TRANS_ROT180, Sprite.TRANS_ROT180,
    Sprite.TRANS_MIRROR_ROT270,Sprite.TRANS_ROT270, Sprite.TRANS_ROT270, Sprite.TRANS_ROT270,Sprite.TRANS_MIRROR
  };
  
  private static final int[] kFrameLookup = { 0, 1, 2, 1, 0, 1, 2, 1, 0, 1, 2, 1, 0, 1, 2, 1 };  
  private static final int[] kCosLookup = { 0,  383,  707,  924,1000,  924,  707,  383, 0, -383, -707, -924, -1000, -924, -707, -383 };
  private static final int[] kSinLookup = { 1000,  924,  707,  383,  0, -383, -707, -924, -1000, -924, -707, -383,  0,  383,  707,  924 };

  public Bullet(Image image, int frameWidth, int frameHeight, int type) {
    super(image, frameWidth, frameHeight);
    defineReferencePixel(frameWidth / 2, frameHeight / 2);
    mDirection = 0;    
    this.type = type;
  }
  
  public void setDirection(int mDirection) {  	  	
    this.mDirection = mDirection;
    setFrame(kFrameLookup[this.mDirection]);
    setTransform(kTransformLookup[this.mDirection]); 	
  }
  
  public int getDirection() {
    return this.mDirection;
  }
  
  public void turn(int delta) {  	
    mDirection += delta;
    if (mDirection < 0) mDirection += 16;
    if (mDirection > 15) mDirection %= 16;
    setFrame(kFrameLookup[mDirection]);
    setTransform(kTransformLookup[mDirection]);
    mLastDelta = delta;
    mLastWasTurn = true;
  }
  
  public void forward(int delta) {    
    fineMove(kCosLookup[mDirection] * delta,-kSinLookup[mDirection] * delta);
    mLastDelta = delta;
    mLastWasTurn = false;
  }
  
  public void undo() {
    if (mLastWasTurn)
      turn(-mLastDelta);
    else
      forward(-mLastDelta);
  }
  
  private void fineMove(int kx, int ky) {    
    int x = getX();
    int y = getY();  
    int errorX = Math.abs(mKX - x * 1000);
    int errorY = Math.abs(mKY - y * 1000);
    if (errorX > 1000 || errorY > 1000) {
      mKX = x * 1000;
      mKY = y * 1000;
    }
    // Now add the deltas.
    mKX += kx;
    mKY += ky;
    // Set the actual position.
    setPosition(mKX / 1000, mKY / 1000);
  }
}
