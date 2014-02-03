package masses;

import org.jbox2d.common.Vec2;
import springies.EnvironmentForces;
import jboxGlue.*;
import jgame.JGColor;


public class Mass extends PhysicalObjectCircle {
 
    EnvironmentForces mForces;
    int mMassId;
    private static final String massID = "mass";
    private float mX, mY;

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
     * Radius for all masses will be 1.  
     * 
     * @param x_pos
     * @param y_pos
     * @param init_vel_x
     * @param init_vel_y
     * @param mass
     */
    public Mass (int mass_id, double x_pos, double y_pos, double init_vel_x, double init_vel_y, double mass) {
        super(massID, 0, JGColor.white, 1, mass);
        setPos(x_pos, y_pos);
        xspeed = init_vel_x;
        yspeed = init_vel_y;
        mMassId = mass_id;
        mForces = WorldManager.getWorldForces();
    }
    public Mass (int mass_id, double x_pos, double y_pos, double init_vel_x, double init_vel_y) {
        this(mass_id, x_pos, y_pos, init_vel_x, init_vel_y, 1);
    }
    public Mass (int mass_id, double x_pos, float y_pos) {
        this(mass_id, x_pos, y_pos, 0, 0, 1);
    }
    public Mass (int mass_id) {
        this(mass_id, 0, 0, 0, 0, 1);
    }

    /**
     * The Physical Object class will copy all the JBox positions onto JGame positions
     * applyAllWorldForces() will set the global force acting on the Mass object 
     */
    public void move() {
        applyAllWorldForces();
        super.move();
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
        Vec2 total_force = new Vec2();
        total_force.addLocal(mForces.getViscosity());
        total_force.addLocal(mForces.getCenterOfMass());
        total_force.addLocal(mForces.getWallRepulsion());
        
        return total_force;
    }
    public void setX(float x) {
    	mX = x;
    }
    public void setY(float y) {
    	mY = y;
    }
}