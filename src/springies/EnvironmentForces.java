package springies;
import jboxGlue.WorldManager;

import org.jbox2d.common.Vec2;


public class EnvironmentForces {
    private Vec2 mGravity;
    private Vec2 mViscosity;
    private Vec2 mCenterOfMass;
    private Vec2 mWallRepulsion;
    private Springies springies;

    double mGravMag;
    double mViscMag;
    double mCOMMag;
    double[] mWallMag = new double[4];
    double[] mWallExp = new double[4];
    
    public EnvironmentForces() {
    	readXML();
    }

    private void readXML(String xml_fn) {
        EnvironmentXMLReader envreader = new EnvironmentXMLReader(xml_fn);
        
    }
    
    public void doForces() {
    	WorldManager.getWorld().
    }

    
    
    public Vec2 Gravity () {
        if (mGravity != null) {
        	WorldManager.getWorld().setGravity(
        			new Vec2(0.0f, 0.0f)
        			);
        }
        return new Vec2(0.0f, 0.0f);
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
