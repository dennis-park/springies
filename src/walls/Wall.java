package walls;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;


public class Wall extends PhysicalObjectRect {
    public static final int TOP_ID = 1;
    public static final int RIGHT_ID = 2;
    public static final int BOTTOM_ID = 3;
    public static final int LEFT_ID = 4;
    public static final double TOP_DIRECTION = Math.PI / 2;
    public static final double RIGHT_DIRECTION = Math.PI;
    public static final double BOTTOM_DIRECTION = 3 * Math.PI / 2;
    public static final double LEFT_DIRECTION = 0.0;

    public final static double WALL_MASS = 0.0;
    public final static double DEFAULT_WALL_MARGIN = -2;
    public final static double DEFAULT_WALL_THICKNESS = 10;
    public final static double DEFAULT_WALL_HEIGHT = 1; // In order to initialize w/o a calculated
                                                        // width/height
    public final static double DEFAULT_WALL_WIDTH = 1;  // In order to initialize w/o a calculated
                                                       // width/height
    public final double WALL_WIDTH = eng.displayWidth() - DEFAULT_WALL_MARGIN * 2 +
                                     DEFAULT_WALL_THICKNESS;
    public final double WALL_HEIGHT = eng.displayHeight() - DEFAULT_WALL_MARGIN * 2 +
                                      DEFAULT_WALL_THICKNESS;
    public final double PIXEL_MOVEMENT = 5.0;

    protected int mWallId;
    protected double mDirection;
    protected boolean mToggleOut = false;
    protected boolean mToggleIn = false;

    public Wall (int wall_id) {
        super("wall", 2, JGColor.green, DEFAULT_WALL_THICKNESS, DEFAULT_WALL_HEIGHT, WALL_MASS);
        mWallId = wall_id;
        createWallJGObject();
    }

    public void createWallJGObject () {
        if (mWallId == TOP_ID) {
            init(WALL_WIDTH, DEFAULT_WALL_THICKNESS, WALL_MASS);
            setPos(eng.displayWidth() / 2, DEFAULT_WALL_MARGIN);
            mDirection = TOP_DIRECTION;
        }
        if (mWallId == BOTTOM_ID) {
            init(WALL_WIDTH, DEFAULT_WALL_THICKNESS, WALL_MASS);
            setPos(eng.displayWidth() / 2, eng.displayHeight() - DEFAULT_WALL_MARGIN);
            mDirection = BOTTOM_DIRECTION;
        }
        else if (mWallId == RIGHT_ID) {
            init(DEFAULT_WALL_THICKNESS, WALL_WIDTH, WALL_MASS);
            setPos(eng.displayWidth() - DEFAULT_WALL_MARGIN, eng.displayHeight() / 2);
            mDirection = RIGHT_DIRECTION;
        }
        else if (mWallId == LEFT_ID) {
            init(DEFAULT_WALL_THICKNESS, WALL_WIDTH, WALL_MASS);
            setPos(DEFAULT_WALL_MARGIN, eng.displayHeight() / 2);
            mDirection = LEFT_DIRECTION;
        }
    }

    @Override
    public void move () {
        float xpos = myBody.getPosition().x;
        float ypos = myBody.getPosition().y;

        if (mToggleOut) {
            mToggleOut = false;
            xpos += (Math.cos(mDirection) * PIXEL_MOVEMENT);
            ypos += (-1 * Math.sin(mDirection) * PIXEL_MOVEMENT);
            setPos(xpos, ypos);
        }
        else if (mToggleIn) {
            mToggleIn = false;
            xpos -= (Math.cos(mDirection) * PIXEL_MOVEMENT);
            ypos -= (-1 * Math.sin(mDirection) * PIXEL_MOVEMENT);
            setPos(xpos, ypos);
        }
    }

    public void toggleOut () {
        mToggleOut = true;
    }

    public void toggleIn () {
        mToggleIn = true;
    }

    public int getWallId () {
        return mWallId;
    }

    public double getWallDirection () {
        return mDirection;
    }
}
