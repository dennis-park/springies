package walls;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;
import springies.Constants;

/**
 * This class defines a Wall object for Mass objects in the simulation to 
 * be repelled by.  It is a subclass of PhysicalObjectRect, which is a physical 
 * JGOBject that conveniently draws a rectangular polygon to confine free space in 
 * the sandbox.  Toggle variables are included to provide controls for increasing or 
 * decreasing the simulation's 2D box dimensions.
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 */
public class Wall extends PhysicalObjectRect {
    public final static double DEFAULT_WALL_HEIGHT = 1; // In order to initialize w/o a calculated
    // width/height
    public final static double DEFAULT_WALL_WIDTH = 1;  // In order to initialize w/o a calculated
    // width/height
    private final double WALL_WIDTH = eng.displayWidth() - Constants.DEFAULT_WALL_MARGIN * 2 +
                                      Constants.DEFAULT_WALL_THICKNESS;
    private final double WALL_HEIGHT = eng.displayHeight() - Constants.DEFAULT_WALL_MARGIN *
                                       2 +
                                       Constants.DEFAULT_WALL_THICKNESS;

    protected int mWallId;
    protected double mDirection;
    protected boolean mToggleOut = false;
    protected boolean mToggleIn = false;

    /**
     * Constructor creates a green wall object with default wall thickness and wall height.
     * Assigned a wall id and calls helper method createWall to return a rectangle.
     * @param wall_id
     * @return 
     */
    public Wall (int wall_id) {
        super("wall", 2, JGColor.green, Constants.DEFAULT_WALL_THICKNESS, DEFAULT_WALL_HEIGHT,
              Constants.WALL_MASS);
        mWallId = wall_id;
        createWall();
    }
    
    /**
     * Creates four walls (top, bottom, right, left) and places them inside the display.
     */
    private void createWall () {
        if (mWallId == Constants.TOP_ID) {
            init(WALL_WIDTH, Constants.DEFAULT_WALL_THICKNESS, Constants.WALL_MASS);
            setPos(eng.displayWidth() / 2, Constants.DEFAULT_WALL_MARGIN);
            mDirection = Constants.TOP_DIRECTION;
        }
        if (mWallId == Constants.BOTTOM_ID) {
            init(WALL_WIDTH, Constants.DEFAULT_WALL_THICKNESS, Constants.WALL_MASS);
            setPos(eng.displayWidth() / 2, eng.displayHeight() - Constants.DEFAULT_WALL_MARGIN);
            mDirection = Constants.BOTTOM_DIRECTION;
        }
        else if (mWallId == Constants.RIGHT_ID) {
            init(Constants.DEFAULT_WALL_THICKNESS, WALL_HEIGHT, Constants.WALL_MASS);
            setPos(eng.displayWidth() - Constants.DEFAULT_WALL_MARGIN, eng.displayHeight() / 2);
            mDirection = Constants.RIGHT_DIRECTION;
        }
        else if (mWallId == Constants.LEFT_ID) {
            init(Constants.DEFAULT_WALL_THICKNESS, WALL_HEIGHT, Constants.WALL_MASS);
            setPos(Constants.DEFAULT_WALL_MARGIN, eng.displayHeight() / 2);
            mDirection = Constants.LEFT_DIRECTION;
        }
    }

    @Override  
    /**
     * Overrides no motion to allow for toggled expansion/contraction of bounds
     * based on user input handled by EnvironmentManager.  Calculates the object's position 
     * and sets it.
     * 
     */
    public void move () {
        float xpos = myBody.getPosition().x;
        float ypos = myBody.getPosition().y;

        if (mToggleOut) {
            mToggleOut = false;
            xpos += (-1 * Math.cos(mDirection) * Constants.PIXEL_MOVEMENT);
            ypos += (-1 * Math.sin(mDirection) * Constants.PIXEL_MOVEMENT);
            setPos(xpos, ypos);

            // System.out.printf("Wall %d moved OUT to (%.2f, %.2f)\n", mWallId, xpos, ypos);
        }
        else if (mToggleIn) {
            mToggleIn = false;
            xpos -= (-1 * Math.cos(mDirection) * Constants.PIXEL_MOVEMENT);
            ypos -= (-1 * Math.sin(mDirection) * Constants.PIXEL_MOVEMENT);
            setPos(xpos, ypos);

            // System.out.printf("Wall %d moved IN to (%.2f, %.2f)\n", mWallId, xpos, ypos);
        }

    }

    /**
     * Sets toggling movement outward to be true.
     * Allows expansion.
     *
     */
    public void toggleOut () {
        mToggleOut = true;
    }

    /**
     * Sets toggling movement inward to be true.
     * Allows contraction.
     * 
     */
    public void toggleIn () {
        mToggleIn = true;
    }
    
    /**
     * Returns the wall id.
     * @param 
     * @return mWallId
     */
    public int getWallId () {
        return mWallId;
    }

    /**
     * Returns the direction of the wall.
     * @param 
     * @return mDirection
     */
    public double getWallDirection () {
        return mDirection;
    }
}
