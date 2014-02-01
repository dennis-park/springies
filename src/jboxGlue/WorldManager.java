package jboxGlue;

import jgame.platform.JGEngine;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import springies.EnvironmentForces;


/**
 * The WorldManager is adapted from WorldManager.java given as source code for this
 * project. This class is responsible for setting up the JBox World that the Springies
 * Objects "live" and run in.
 * 
 * This class also controls all the global environment forces which act upon all the
 * objects with mass in the Springies world.
 * 
 * @author Thanh-Ha
 * 
 */
public class WorldManager
{
    public static EnvironmentForces mForces;
    public static World ourWorld;
    static {
        ourWorld = null;
    }

    public static World getWorld ()
    {
        // make sure we have a world, just in case...
        if (ourWorld == null) { throw new RuntimeException("call initWorld() before getWorld()!"); }
        return ourWorld;
    }

    public static EnvironmentForces getWorldForces () {
        return mForces;
    }

    public static void initWorld (JGEngine engine)
    {
        AABB worldBounds = new AABB(new Vec2(0, 0),
                                    new Vec2(engine.displayWidth(), engine.displayHeight()));
        Vec2 gravity = new Vec2(0.0f, 0.0f);
        ourWorld = new World(worldBounds, gravity, true);
    }

    /**
     * Given the global environmental forces.
     * 
     * @param forces
     */
    public static void setWorldForces (EnvironmentForces forces) {
        mForces = forces;
    }

    public void setGravity () {
        ourWorld.setGravity(mForces.getGravity());
    }
}
