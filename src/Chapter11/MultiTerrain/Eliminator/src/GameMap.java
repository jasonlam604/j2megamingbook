import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class GameMap {

  // Terrain 1
  private static final int[][] map1 = {
       {0,0,0,0,0,0},
       {3,0,0,0,0,0},
       {6,0,0,0,0,0},
       {6,0,0,0,1,2},
       {6,0,0,0,4,5},
       {6,0,0,0,7,8},
       {6,0,0,0,0,0},
       {9,0,1,2,3,0},
       {0,0,4,5,6,0},
       {0,0,7,8,9,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {3,0,0,0,0,0},
       {6,0,0,0,0,0},
       {6,0,0,0,1,2},
       {6,0,0,0,4,5},
       {6,0,0,0,7,8},
       {6,0,0,0,0,0},
       {9,0,1,2,3,0},
       {0,0,4,5,6,0},
       {0,0,7,8,9,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {3,0,0,0,0,0},
       {6,0,0,0,0,0},
       {6,0,0,0,1,2},
       {6,0,0,0,4,5},
       {6,0,0,0,7,8},
       {6,0,0,0,0,0},
       {9,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {3,0,0,0,0,1}
  };

  // Terrain 2
  private static final int[][] map2 = {
       {0,0,0,0,0,0},
       {3,0,0,0,0,0},
       {6,0,0,0,0,0},
       {6,0,0,0,1,2},
       {6,0,0,0,4,5},
       {6,0,0,0,7,8},
       {6,0,0,0,0,0},
       {9,0,1,2,3,0},
       {0,0,4,5,6,0},
       {0,0,7,8,9,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {3,0,0,0,0,0},
       {6,0,0,0,0,0},
       {6,0,0,0,1,2},
       {6,0,0,0,4,5},
       {6,0,0,0,7,8},
       {6,0,0,0,0,0},
       {9,0,1,2,3,0},
       {0,0,4,5,6,0},
       {0,0,7,8,9,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {3,0,0,0,0,0},
       {6,0,0,0,0,0},
       {6,0,0,0,1,2},
       {6,0,0,0,4,5},
       {6,0,0,0,7,8},
       {6,0,0,0,0,0},
       {9,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {0,0,0,0,0,0},
       {3,0,0,0,0,1}
  };

  // Set Constant values, the values are direct
  // relation to the actually tile sizes
  // defined for the terrain graphics
  private static final int TILE_WIDTH = 32;
  private static final int TILE_HEIGHT = 32;
  private static final int TILE_NUM_COL = 6;
  private static final int TILE_NUM_ROW = 36;

  // To hold the current map
  private int[][] currentMap;

  // To hold the current terrain
  private TiledLayer terrain;

  // To hold the current background/floor color
  private int groundColor;
  
  // To hold the current screen, value neeeded for scrolling calculation
  private int screenHeight;
  
  // To hold Y position for scrolling
  private int terrainScroll;   

  public GameMap(int screenHeight) throws Exception {
    this.screenHeight = screenHeight;
    setMap(1);  // default to set to terrain 1
  }

  // Set Appropriate Terrain and Map
  public void setMap(int level) throws Exception {
    Image tileImages = null;

    switch (level) {
      case 1:  tileImages = Image.createImage("/terrain1.png");
               currentMap = map1;
               groundColor = 0xF8DDBE;
               break;
      case 2:  tileImages = Image.createImage("/terrain2.png");
               currentMap = map2;
               groundColor = 0xDECE6B;
               break;
      default: tileImages = Image.createImage("/terrain1.png");
               currentMap = map1;
               groundColor = 0xF8DDBE;
               break;
    }

    terrain = new TiledLayer(TILE_NUM_COL,TILE_NUM_ROW,tileImages,TILE_WIDTH,TILE_HEIGHT);

    // Map Terrain Map with actual graphic from terrain.png
    for (int row=0; row<TILE_NUM_ROW; row++) {
      for (int col=0; col<TILE_NUM_COL; col++) {
      	terrain.setCell(col,row,currentMap[row][col]);
      }
    }
    
    terrainScroll = 1 - (terrain.getCellHeight() * terrain.getRows()) + screenHeight;
    terrain.setPosition(0,terrainScroll);    
  }
  
  public void scrollTerrain() {
    if (terrainScroll < 0) {
      terrainScroll += 2;
      terrain.setPosition(0,terrainScroll);
    }
  }  

  // Terrain Getter
  public TiledLayer getTerrain() {
    return terrain;
  }

  // Ground/Floor color Getter
  public int getGroundColor() {
    return groundColor;
  }    
}