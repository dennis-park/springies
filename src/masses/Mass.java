package masses;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import springies.EnvironmentForces;
import springs.Spring;
import jboxGlue.*;
import jgame.JGColor;


public class Mass extends PhysicalObjectCircle {
    private double mMass;
    protected ArrayList<Spring> mSpringList = new ArrayList<Spring>();

    private static final int COLLISION_ID = 0;
    private static final int DEFAULT_RADIUS = 5;
    private static final JGColor DEFAULT_COLOR = JGColor.white;
    private static final double DEFAULT_XPOS = 0.0;
    private static final double DEFAULT_YPOS = 0.0;
    private static final double DEFAULT_XVEL = 0.0;
    private static final double DEFAULT_YVEL = 0.0;
    private static final double DEFAULT_MASS = 0.0;
    
    /**
     * This class represents the Mass objects in our Spring-Mass assemblies.
     * 
     * This element moves in response to the forces applied by the springs connected to it.
     * 
     * In the data file, this mass is indicated by the keyword mass followed by its id, its
     * x- and y-coordinates, an initial x- and y-velocites, and a mass. If the velocities
     * are not given, the default value should be 0. If the mass is not given, the default
     * value should be 1.
     * 
     * Radius for all masses will be 10 and default collision  
     * 
     * @param x_pos
     * @param y_pos
     * @param init_vel_x
     * @param init_vel_y
     * @param mass
     */

    public Mass (String mass_id, double x_pos, double y_pos, double init_vel_x, double init_vel_y, double mass) {
        super(mass_id, COLLISION_ID, DEFAULT_COLOR, DEFAULT_RADIUS, mass);
        setPos(x_pos, y_pos);
        xspeed = init_vel_x;
        yspeed = init_vel_y;
        mForces = WorldManager.getWorldForces();
    }
    public Mass (String mass_id, double x_pos, double y_pos, double init_vel_x, double init_vel_y) {
        this(mass_id, x_pos, y_pos, init_vel_x, init_vel_y, 1);
    }

    public Mass (String mass_id, double x_pos, double y_pos, double mass) {
        this(mass_id, x_pos, y_pos, DEFAULT_XVEL, DEFAULT_YVEL, mass); 
    }
    public Mass (String mass_id, double x_pos, double y_pos) {
        this(mass_id, x_pos, y_pos, DEFAULT_XVEL, DEFAULT_YVEL, DEFAULT_MASS);
    }
    public Mass (String mass_id) {
        this(mass_id, DEFAULT_XPOS, DEFAULT_YPOS, DEFAULT_XVEL, DEFAULT_YVEL, DEFAULT_MASS);
    }

    /**
     * The Physical Object class will copy all the JBox positions onto JGame positions
     * applyAllWorldForces() will set the global force acting on the Mass object 
     */
    public void move() {
        // applyAllWorldForces();
        super.move();
    }
    
    public void connectSpring(Spring spring) {
    	mSpringList.add(spring);
    }
    /**
     * Apply all the world forces
     * 
     */
    public void applyAllWorldForces ()
    {
        Vec2 all_world_forces = calcAllWorldForces();
        myBody.applyForce(all_world_forces, myBody.m_xf.position);
    }
    
    /**
     * Calculate all the environmental forces that will (not including gravity)
     * Spring force is NOT an environmental force.
     * 
     */
    protected Vec2 calcAllWorldForces() {

    }
    public double getMass() {
    	return this.mMass;
    }
}