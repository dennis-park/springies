package forces;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.common.Vec2;

import jboxGlue.WorldManager;
import Parsers.*;
import springies.Springies;

public class ForceManager {
    protected Springies mSpringies;
    protected Gravity mGravity;
    protected Viscosity mViscosity;
    protected COM mCOM;
    protected ArrayList<WallRepulsion> mWallRepulsionList;
    
    protected static final double DEFAULT_GRAVITY = 20.0;
    protected static final double DEFAULT_VISCOSITY = 0.8;
    protected static final double DEFAULT_COM_MAGNITUDE = 0.8;
    protected static final double DEFAULT_WALL_REPULSION_MAGNITUDE = 50;
    
    private String GRAV = "gravity";
    private String VISC = "viscosity";
    private String COM = "com";
    private String WALL = "wall";
    
    HashMap<String, Boolean> toggleMap = new HashMap<String, Boolean>();
    
    public ForceManager(Springies s, Gravity g, Viscosity v, COM com, ArrayList<WallRepulsion> walls) {
        mSpringies = s;
        mGravity = g;
        mViscosity = v;
        mCOM = com;
        mWallRepulsionList = walls;
        
    }
    public ForceManager(Springies s, String filename) {
        mSpringies = s;
        XMLParserCaller caller = new XMLParserCaller();
        EnvironmentParser parser = new EnvironmentParser(mSpringies);
        try {
            caller.call(filename, parser);
        }
        catch (Exception e) {
            System.out.println("Error: Unable to parse XML file");
            e.printStackTrace();
            System.exit(1);
        }
        
        mGravity = parser.getGravity();
        mViscosity = parser.getViscosity();
        mCOM = parser.getCOM();
        mWallRepulsionList = parser.getWallRepulsionList();
        toggleMap.put(GRAV, true);
    	toggleMap.put(VISC, true);
    	toggleMap.put(COM, true);
    	toggleMap.put(WALL, true);
    }
    
    public void toggleForces(String forceid) {
    	toggleMap.put(forceid, !toggleMap.get(forceid));
    }
    public void doForces() {
    	/**
    	 * TODO: list all forces
    	 * applyGravity()
    	 * applyViscosity()
    	 * applyCOM()
    	 * applyWallRepulsion()
    	 */
    }
    public void applyGravity() {
    	if (toggleMap.get(GRAV)) {
    		WorldManager.getWorld().setGravity(mGravity.calculateForce());
    	} else {
    		WorldManager.getWorld().setGravity(new Vec2(0f,0f));
    	}
    }
    public void applyViscosity() {
    	if (toggleMap.get(VISC)) {
    		/**
    		 * TODO: iterate through hashmap of masses
    		 */
    	}
    }
    public void applyCOM() {
    	if (toggleMap.get(VISC)) {
    		/**
    		 * TODO: iterate through hashmap of masses
    		 */
    	}
    }
    public void applyWallRepulsion() {
    	if (toggleMap.get(VISC)) {
    		/**
    		 * TODO: iterate through hashmap of masses
    		 */
    	}
    }

    
}
