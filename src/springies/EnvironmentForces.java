package springies;

import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jgame.JGColor;
import org.jbox2d.common.Vec2;


public class EnvironmentForces {
    private Vec2 mGravity;
    private Vec2 mViscosity;
    private Vec2 mCenterOfMass;
    private Vec2 mWallRepulsion;

    public EnvironmentForces (Vec2 gravity, Vec2 viscosity, Vec2 center_of_mass, Vec2 wall_repulsion) {

    }

    public EnvironmentForces (String xml_fn) {
        
    }
    
    

    public Vec2 getGravity () {
        if (mGravity == null) { return new Vec2(0.0f, 0.0f); }
        return mGravity;
    }

    public Vec2 getViscosity () {
        if (mViscosity == null) { return new Vec2(0.0f, 0.0f); }
        return mViscosity;
    }

    public Vec2 getCenterOfMass () {
        if (mCenterOfMass == null) { return new Vec2(0.0f, 0.0f); }
        return mCenterOfMass;
    }

    public Vec2 getWallRepulsion () {
        if (mWallRepulsion == null) { return new Vec2(0.0f, 0.0f); }
        return mWallRepulsion;
    }
}
