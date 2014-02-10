package masses;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import forces.EnvironmentManager;
import springs.Spring;
import jboxGlue.*;
import jgame.JGColor;
import jgame.JGObject;


public class Mass extends PhysicalObjectCircle {
    private static int mMassNumber;
    protected ArrayList<Spring> mSpringList = new ArrayList<Spring>();

    protected static final int COLLISION_ID = 1;
    protected static final int DEFAULT_RADIUS = 5;
    protected static final JGColor DEFAULT_COLOR = JGColor.white;
    protected static final double DEFAULT_XPOS = 0.0;
    protected static final double DEFAULT_YPOS = 0.0;
    protected static final double DEFAULT_XVEL = 0.0;
    protected static final double DEFAULT_YVEL = 0.0;
    protected static final double DEFAULT_MASS = 1.0 / (Math.PI * DEFAULT_RADIUS * DEFAULT_RADIUS);
    
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
        Vec2 initial_velocity = new Vec2((float) init_vel_x, (float) init_vel_y);
        this.myBody.setLinearVelocity(initial_velocity);
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
    
    @Override 
    public void hit(JGObject other) {
        // Do nothing 
    }
    
    public void move() {
//        System.out.printf("Speed of mass (%s): <%.2f, %.2f>\n", 
//                          this.getName(), this.getBody().m_linearVelocity.x, this.getBody().m_linearVelocity.y);
        super.move();
    }
    
    public void connectSpring(Spring spring) {
    	mSpringList.add(spring);
    }
    /**
     * Apply specified force vector
     * 
     */
    public void applyForceVector (Vec2 force)
    {
        myBody.applyForce(force, myBody.m_xf.position);
    }
}