package springies;

import jgame.JGColor;
import org.jbox2d.common.Vec2;


public class Constants {
    // Default environment forces
    public static final double DEFAULT_GRAVITY_MAGNITUDE = 1.0;
    public static final double DEFAULT_GRAVITY_DIRECTION = Math.PI / 2;
    public static final double DEFAULT_VISCOSITY_MAGNITUDE = 0.1;
    public static final double DEFAULT_COM_MAGNITUDE = 1.0;
    public static final double DEFAULT_WALL_REPULSION_MAGNITUDE = 500;
    public static final double DEFAULT_EXPONENT = 1.0;

    // Useful vector(s)
    public static final Vec2 ZERO_VECTOR = new Vec2(0.0f, 0.0f);

    // Wall ID's
    public static final int NUM_WALLS = 4;
    public static final int TOP_ID = 1;
    public static final int RIGHT_ID = 2;
    public static final int BOTTOM_ID = 3;
    public static final int LEFT_ID = 4;

    // Wall directions
    public static final double TOP_DIRECTION = Math.PI / 2;
    public static final double RIGHT_DIRECTION = Math.PI;
    public static final double BOTTOM_DIRECTION = 3 * Math.PI / 2;
    public static final double LEFT_DIRECTION = 0.0;

    // Wall Sizes
    public static final double WALL_MASS = 0.0;
    public static final double DEFAULT_WALL_MARGIN = -2;
    public static final double DEFAULT_WALL_THICKNESS = 10;
    public static final double PIXEL_MOVEMENT = 5.0;

    // Force ID's
    public static final String GRAV_ID = "gravity";
    public static final String VISC_ID = "viscosity";
    public static final String COM_ID = "com";
    public static final String WALL_ID = "wall";

    // For masses
    public static final int COLLISION_ID = 1;
    public static final int DEFAULT_RADIUS = 5;
    public static final JGColor DEFAULT_COLOR = JGColor.white;
    public static final double DEFAULT_XPOS = 0.0;
    public static final double DEFAULT_YPOS = 0.0;
    public static final double DEFAULT_XVEL = 0.0;
    public static final double DEFAULT_YVEL = 0.0;
    public static final double DEFAULT_MASS = 1.0 / (Math.PI * DEFAULT_RADIUS * DEFAULT_RADIUS);

    // For springs & muscles
    public static final double DEFAULT_KVAL = 1;
    public static final double DEFAULT_PERIOD = 3 * Math.PI / 2;
    public static final double AMPLITUDE_INCREMENT = 0.25;

}
