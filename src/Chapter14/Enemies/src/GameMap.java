import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class GameMap {

  // Terrain 1
   private static final int[][] map1 = {
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,12,10,1,2},
       {10,10,10,10,4,5},
       {3,10,10,10,7,8},
       {6,10,10,10,10,10},
       {9,10,1,2,3,10},
       {10,10,4,5,6,12},
       {10,10,7,8,9,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,12,10,10},
       {10,10,10,10,10,1},
       {10,10,10,10,10,4},
       {10,10,10,10,10,4},
       {10,10,10,10,10,4},
       {10,10,10,10,10,4},
       {10,11,10,10,10,7},
       {10,10,10,10,10,10},
       {10,10,10,12,10,10},
       {10,12,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {3,10,10,10,10,10},
       {6,10,10,10,10,10},
       {6,10,11,10,1,2},
       {6,12,10,10,4,5},
       {6,10,10,10,7,8},
       {6,10,10,10,10,10},
       {9,10,1,2,3,10},
       {10,10,4,5,6,10},
       {10,10,7,8,9,10},
       {10,10,10,10,10,10},
       {12,10,10,10,12,10},
       {10,10,10,10,10,10},
       {2,3,10,11,10,10},
       {5,6,10,10,10,10},
       {5,6,11,10,1,2},
       {5,6,10,10,4,5},
       {5,6,10,10,7,8},
       {5,6,10,10,10,10},
       {8,9,10,10,10,10},
       {10,10,10,12,10,10},
       {10,10,10,10,10,10},
       {11,10,10,10,10,10},
       {10,10,10,10,10,10}
  };

  // Terrain 2
  private static final int[][] map2 = {
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,12,12,10,10},
       {10,10,12,12,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       { 3,10,10,10,10,10},
       { 6,10,10,10,10,10},
       { 6,10,12,10, 1, 2},
       { 6,10,10,10, 4, 5},
       { 6,10,10,10, 7, 8},
       { 6,10,10,10,10,10},
       { 9,10, 1, 2, 3,10},
       {10,10, 4, 5, 6,12},
       {10,10, 7, 8, 9,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,12,10,10,10,10},
       { 2, 3,10,10,10,10},
       { 5, 6,10,10,10,10},
       { 5, 6,11,10, 1, 2},
       { 5, 6,10,10, 4, 5},
       { 8, 9,10,10, 7, 8},
       {10,10,10,10,10,10},
       {10,10, 1, 2, 3,10},
       {10,10, 4, 5, 6,10},
       {10,10, 7, 8, 9,10},
       {10,10,10,10,10,10},
       {12,10,10,10,12,10},
       {10,10,10,10,10,10},
       { 2, 3,10,11,10,10},
       { 5, 6,10,10,10,10},
       { 5, 6,11,10, 1, 2},
       { 5, 6,10,10, 4, 5},
       { 5, 6,10,10, 7, 8},
       { 5, 6,10,10,10,10},
       { 5, 6,10,10,10,10},
       { 5, 6,10,11,10,10},
       { 5, 6,10,10,10,10},
       { 5, 6,10,10,10,10},
       { 8, 9,10,10,10,10},
       {10,10,10,12,10,10}
  };

  private static final int[][] map3 = {
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       { 3,10,10,10,10, 1},
       { 6,10,10,10,10, 4},
       { 6,10,10,10,10, 4},
       { 6,10,10,10,10, 4},
       { 6,10,10,10,10, 4},
       { 6,10,10,10,10, 4},
       { 6,10,10,10,10, 4},
       { 6,10,10,10,10, 4},
       { 6,10,10,10,10, 4},
       { 9,10,10,10,10, 7},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10, 1, 2, 3},
       {10,10,10, 4, 5, 5},
       {10,10,10, 4, 5, 5},
       {10,10,10, 4, 5, 5},
       {10,10,10, 7, 8, 8},
       {10,10,10,10,10,10},
       {10,10,10,10,11,10},
       {10,10,10,10,10,10},
       {10,12,10,10,10,10},
       {10,10,10,10,10,10},
       { 2, 2, 3,10,10,10},
       { 5, 5, 6,10,11,10},
       { 5, 5, 6,10,10,10},
       { 5, 5, 6,10,10,10},
       { 5, 5, 6,10,10,10},
       { 5, 5, 6,10,10,10},
       { 5, 5, 6,10,10,10},
       { 5, 5, 6,10,10,12},
       { 5, 5, 6,10,10,10},
       { 8, 8, 9,10,10,10},
       {10,10,10,10,10,10}
  };

  private static final int[][] map4 = {
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10},
       {10,10,10,10,10,10}
  };

  // Set Constant values, the values are direct
  // relation to the actually tile sizes
  // defined for the terrain graphics
  static final int TILE_WIDTH = 32;
  static final int TILE_HEIGHT = 32;
  static final int TILE_NUM_COL = 6;
  int TILE_NUM_ROW;
  
  static final int MAP_MOVEMENT = 2;  // the rate which the map scrolls, 2 pixels per cycle

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

  private int[][] mergeMaps(int[][] map1, int[][] map2) {
    int[][] xMap = new int[map1.length+map2.length][6];
    System.arraycopy(map1,0,xMap,0,map1.length);
    System.arraycopy(map2,0,xMap,map1.length - 1, map2.length);
    return xMap;
  }

  // Set Appropriate Terrain and Map
  public void setMap(int level) throws Exception {
    Image tileImages = null;

    switch (level) {
      case 1:  tileImages = Image.createImage("/terrain4.png");
               currentMap = mergeMaps(map1,map2);
               break;

      case 2:  tileImages = Image.createImage("/terrain2.png");
               currentMap = mergeMaps(map2,map3);
               groundColor = 0x00DECE6B;

               break;

      case 3:  tileImages = Image.createImage("/terrain3.png");
               currentMap = mergeMaps(map3,map1);
               groundColor = 0x0024B400;
               break;

      case 4:  tileImages = Image.createImage("/terrain1.png");
               currentMap = mergeMaps(map2,map1);
               groundColor = 0x0024B400;
               break;     
    }
    TILE_NUM_ROW = currentMap.length;
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
  
  public int getMapLength() {
    return this.currentMap.length;
  }
  
  public int getMapType(int x, int y) {
    return this.currentMap[x][y];
  }

  public void scrollTerrain() {
    if (terrainScroll < 0) {
      terrainScroll += MAP_MOVEMENT;      
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
  
  // Map Movement Getter
  public int getMapScrollAmount() {
    return MAP_MOVEMENT;	
  }
}