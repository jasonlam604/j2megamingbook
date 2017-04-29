import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class NPCSprite extends Sprite {
  public static final int NPC_CANON1 = 1;
  public static final int NPC_CANON2 = 2;
  public static final int NPC_CANON3 = 3;
  public static final int NPC_FLYER1 = 4;
  public static final int NPC_FLYER2 = 5;
  public static final int NPC_FLYER3 = 6;
  public static final int NPC_FLYER4 = 7;  
  public static final int NPC_TANK1 = 8;
  public static final int NPC_TANK2 = 9;
  int npcType;

  int scnWidth,scnHeight;

  private int mDirection;
  private int mKX, mKY;

  private int mLastDelta;
  private boolean mLastWasTurn;

  static final int SPC = 6;

  boolean firing;
  int firingDelay=500;
  long timeLastFired;
  
  int frameOffset;

  private static final int[] kTransformLookup = {
    Sprite.TRANS_NONE, Sprite.TRANS_NONE, Sprite.TRANS_NONE, Sprite.TRANS_MIRROR_ROT90,
    Sprite.TRANS_ROT90, Sprite.TRANS_ROT90, Sprite.TRANS_ROT90,Sprite.TRANS_MIRROR_ROT180,Sprite.TRANS_ROT180, Sprite.TRANS_ROT180, Sprite.TRANS_ROT180,
    Sprite.TRANS_MIRROR_ROT270,Sprite.TRANS_ROT270, Sprite.TRANS_ROT270, Sprite.TRANS_ROT270,Sprite.TRANS_MIRROR
  };

  private static final int[] kFrameLookup = { 0, 1, 2, 1, 0, 1, 2, 1, 0, 1, 2, 1, 0, 1, 2, 1 };
  private static final int[] kCosLookup = { 0,  383,  707,  924,1000,  924,  707,  383, 0, -383, -707, -924, -1000, -924, -707, -383 };
  private static final int[] kSinLookup = { 1000,  924,  707,  383,  0, -383, -707, -924, -1000, -924, -707, -383,  0,  383,  707,  924 };

  public NPCSprite(Image image, int frameWidth, int frameHeight,int npcType, int scnWidth, int scnHeight) {
    super(image, frameWidth, frameHeight);
    defineReferencePixel(frameWidth / 2, frameHeight / 2);
    mDirection = 0;
    this.npcType = npcType;
    this.scnWidth = scnWidth;
    this.scnHeight = scnHeight;
    
    
    // Hack 1:1 relation to the PNG file 3 frames of cannons first then a flyer... as we add more NPCs
    // this switch needs to be updated accordingly
    switch(npcType) {
      case NPC_CANON1: this.frameOffset = 0; this.setFrame(0); break; 	
      case NPC_CANON2: this.frameOffset = 3; this.setFrame(3); break;
      case NPC_CANON3: this.frameOffset = 6; this.setFrame(6); break;      
      case NPC_FLYER1: this.frameOffset = 9; this.setFrame(9); break;
      case NPC_FLYER2: this.frameOffset = 12; this.setFrame(12); break;
      case NPC_FLYER3: this.frameOffset = 15; this.setFrame(15); break;
      case NPC_FLYER4: this.frameOffset = 18; this.setFrame(18); break;      
      case NPC_TANK1: this.frameOffset = 21; this.setFrame(21); break;
      case NPC_TANK2: this.frameOffset = 24; this.setFrame(24); break;               	
    }
  }

  public int getMidX() {
    return getX() + (getHeight()/2);
  }

  public int getMidY() {
    return getY() + (getWidth()/2);
  }


  public void turn(int delta) {
    mDirection += delta;
    if (mDirection < 0) mDirection += 16;
    if (mDirection > 15) mDirection %= 16;
    
    setFrame(kFrameLookup[mDirection] + frameOffset);
    setTransform(kTransformLookup[mDirection]);

    mLastDelta = delta;
    mLastWasTurn = true;
  }

  public void forward(int delta) {    
    fineMove(kCosLookup[mDirection] * delta,
        -kSinLookup[mDirection] * delta);
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

  public Bullet fire(PlayerSprite player, Image bullets) throws Exception {
    Bullet bullet = null;
    if (getY() > 0 && getY() < scnHeight && !collidesWith(player,false)) {      
      int pDir = getPlayerDirection(player);

      if (pDir == mDirection) {
      	long timeSinceLastFire = (System.currentTimeMillis() - timeLastFired);
      	if (!firing && timeSinceLastFire > firingDelay) {
      	  bullet = new Bullet(bullets,2,2,Bullet.TYPE_NPC1_DAMAGE);
      	  bullet.setPosition(getMidX(),getMidY());      	  
      	  bullet.setDirection(mDirection);
      	  timeLastFired = System.currentTimeMillis();
      	} else {
      	  firing = false;
      	}
      } else if (pDir > mDirection) {
      	if ( pDir - mDirection > 8)
      	  turn(-1);
      	else
      	  turn(1);
      } else if (pDir < mDirection) {
      	if ( mDirection - pDir < 8)
      	  turn(-1);
      	else
      	  turn(1);
      }
    }
    return bullet;
  }

  // Major hack and magic numbers here, sorry I'll try and make this more readable later
  private int getPlayerDirection(PlayerSprite player) {

    // Beside the NPC
    if (player.getMidY() <= (getMidY() + SPC) && player.getMidY() >= (getMidY()-SPC) ) {
      if (player.getX() < getX())
        return 12;
      else
        return 4;
    } else if (player.getMidX() >= getMidX() - SPC && player.getMidX() <= getMidX()+SPC ) {
      if (player.getY() > getY())
        return 8;
      else
        return 0;

    // Quad 4
    } else if (player.getMidX() <= getMidX() && player.getMidY() <= getMidY()   ) {

      int qC = quadCalc(player);
      if (qC <= SPC)
        return 14;
      else if ( Math.abs(player.getMidX() - getMidX()) > Math.abs(player.getY() - getMidY())   )
        return 13;
      else
        return 15;

    // Quad 3
    } else if (player.getMidX() <= getMidX() && player.getMidY() >= getMidY()   ) {
      int qC = quadCalc(player);
      if (qC <= SPC)
        return 10;
      else if ( Math.abs(player.getMidX() - getMidX()) > Math.abs(player.getY() - getMidY())   )
        return 11;
      else
        return 9;      

    // Quad 2
    } else if (player.getMidX() >= getMidX() && player.getMidY() >= getMidY()   ) {
      int qC = quadCalc(player);
      if (qC <= SPC)
        return 6;
      else if ( Math.abs(player.getMidX() - getMidX()) > Math.abs(player.getY() - getMidY())   )
        return 5;
      else
        return 7;

    // Quad 1
    } else if (player.getMidX() >= getMidX() && player.getMidY() <= getMidY()   ) {
      int qC = quadCalc(player);
      if (qC <= 5)
        return 2;
      else if ( Math.abs(player.getMidX() - getMidX()) > Math.abs(player.getY() - getMidY())   )
        return 3;
      else
        return 1;
    }

    // Should never reach here
    return -1;
  }

  private int quadCalc(PlayerSprite player) {
    return Math.abs((Math.abs(player.getMidX() - getMidX())) - (Math.abs(player.getY() - getMidY())));
  }

}
