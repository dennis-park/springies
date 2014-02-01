package masses;

import org.jbox2d.collision.ShapeDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import jboxGlue.*;
import jgame.JGColor;


public class Mass extends PhysicalObject {
    float mInitVelX;
    float mInitVelY;
    float mMass;

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
     * @param x_pos
     * @param y_pos
     * @param init_vel_x
     * @param init_vel_y
     * @param mass
     */
    public Mass (float x_pos, float y_pos, float init_vel_x, float init_vel_y, float mass) {
        super("mass", 0, JGColor.black);
        setPos(x, y);
        mInitVelX = init_vel_x;
        mInitVelY = init_vel_y;
        mMass = mass;
    }

    public Mass (float x_pos, float y_pos, float init_vel_x, float init_vel_y) {
        this(x_pos, y_pos, init_vel_x, init_vel_y, 1);
    }

    public Mass (float x_pos, float y_pos) {
        this(x_pos, y_pos, 0, 0, 1);
    }

    public Mass () {
        this(0, 0, 0, 0, 1);
    }

    @Override
    protected void createBody (ShapeDef shapeDefinition)
    {
        myBody = WorldManager.getWorld().createBody(new BodyDef());
        myBody.createShape(shapeDefinition);
        myBody.setUserData(this); // for following body back to JGObject
        myBody.setMassFromShapes();
        myBody.m_world = WorldManager.getWorld();
    }

    @Override
    public void move ()
    {
        // if the JGame object was deleted, remove the physical object too
        if (myBody.m_world != WorldManager.getWorld()) {
            remove();
            return;
        }
        // copy the position and rotation from the JBox world to the JGame world
        Vec2 position = myBody.getPosition();
        x = position.x;
        y = position.y;
        myRotation = -myBody.getAngle();
    }

    @Override
    public void setPos (double x, double y)
    {
        // there's no body while the game object is initializing
        if (myBody != null) {
            // set position of jbox2d object, not jgame object
            myBody.setXForm(new Vec2((float) x, (float) y), -myRotation);
        }
    }

    public void setForce (double x, double y)
    {
        myBody.applyForce(new Vec2((float) x, (float) y), myBody.m_xf.position);
    }

    @Override
    protected void paintShape () {
        // TODO Auto-generated method stub

    }
}
