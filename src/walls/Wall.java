package walls;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;


public class Wall extends PhysicalObjectRect {
    public final static double WALL_MARGIN = 10;
    public final static double WALL_THICKNESS = 10;
    public final static double WALL_MASS = 0.0;
    
    public final static double DEFAULT_WALL_HEIGHT = 1; // In order to initialize w/o a calculated width/height
    public final static double DEFAULT_WALL_WIDTH = 1;  // In order to initialize w/o a calculated width/height
    public final double WALL_WIDTH = eng.displayWidth() - WALL_MARGIN * 2 + WALL_THICKNESS;
    public final double WALL_HEIGHT = eng.displayHeight() - WALL_MARGIN * 2 + WALL_THICKNESS;
    
    public static final int TOP_ID = 1;
    public static final int RIGHT_ID = 2;
    public static final int BOTTOM_ID = 3;
    public static final int LEFT_ID = 4;
    
    private int mWallId; 


    public Wall (int wall_id) {
        super("wall", 2, JGColor.green, WALL_THICKNESS, DEFAULT_WALL_HEIGHT, WALL_MASS);
        mWallId = wall_id;
        
        // WILL NEED TO REFACTOR THIS OUT INTO LEFT, RIGHT, BOTTOM, TOP WALLS!!! 
        if (mWallId == TOP_ID) {
            this.init(WALL_WIDTH, WALL_THICKNESS, WALL_MASS);
            this.setPos(eng.displayWidth() / 2, WALL_MARGIN);
        }
        if (mWallId == BOTTOM_ID) {
            this.init(WALL_WIDTH, WALL_THICKNESS, WALL_MASS);
            this.setPos(eng.displayWidth() / 2, eng.displayHeight() - WALL_MARGIN);
        }
        else if (mWallId == RIGHT_ID) {
            this.init(WALL_THICKNESS, WALL_WIDTH, WALL_MASS);
            this.setPos(eng.displayWidth() - WALL_MARGIN, eng.displayHeight() / 2);
        }
        else if (mWallId == LEFT_ID) {
            this.init(WALL_THICKNESS, WALL_WIDTH, WALL_MASS);
            this.setPos(WALL_MARGIN, eng.displayHeight() / 2);
        }
    }

    public int getWallId () {
        return mWallId;
    }
}
