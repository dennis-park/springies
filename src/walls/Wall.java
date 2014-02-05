package walls;

import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;


public class Wall extends PhysicalObjectRect {

    private static final double DEFAULT_MASS = 0.0;
    private int mWallId; 
    private double mWidth;
    private double mHeight;


    public Wall (double width, double height) {
        super("wall", 2, JGColor.green, width, height, DEFAULT_MASS);
        this.mWidth = width;
        this.mHeight = height;
    }

    @Override
    public void move () {

    }
    /*
     * public void applyForce() {
     * for (Mass m : mSpringies.getAssemblyMap().values) {
     * 
     * }
     * }
     */

    public int getWallId () {
        return mWallId;
    }

    /*
     * wall.setPos(displayWidth() / 2, WALL_MARGIN);
     * wall = new PhysicalObjectRect("wall", 2, JGColor.green,
     * WALL_WIDTH, WALL_THICKNESS);
     * wall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);
     * wall = new PhysicalObjectRect("wall", 2, JGColor.green,
     * WALL_THICKNESS, WALL_HEIGHT);
     * wall.setPos(WALL_MARGIN, displayHeight() / 2);
     * wall = new PhysicalObjectRect("wall", 2, JGColor.green,
     * WALL_THICKNESS, WALL_HEIGHT);
     * wall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);
     */
}
