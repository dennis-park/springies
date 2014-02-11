package walls;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;
import springies.Constants;


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

    public Wall (int wall_id) {
        super("wall", 2, JGColor.green, Constants.DEFAULT_WALL_THICKNESS, DEFAULT_WALL_HEIGHT,
              Constants.WALL_MASS);
        mWallId = wall_id;
        createWallJGObject();
    }

    private void createWallJGObject () {
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
