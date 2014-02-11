package springies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import masses.Mass;

import org.jbox2d.common.Vec2;
import forces.COM;
import forces.Force;
import forces.Gravity;
import forces.Viscosity;
import forces.WallRepulsion;
import Parsers.*;
import walls.Wall;

public class EnvironmentManager {
    protected Springies mSpringies;
    protected Gravity mGravity;
    protected Viscosity mViscosity;
    protected COM mCOM;
    protected List<WallRepulsion> mWallRepulsionList;
    
    public static final double DEFAULT_GRAVITY_MAGNITUDE = 0.1;
    public static final double DEFAULT_VISCOSITY_MAGNITUDE = 0.05;
    public static final double DEFAULT_COM_MAGNITUDE = 0.1;
    public static final double DEFAULT_WALL_REPULSION_MAGNITUDE = 0.1;
    public static final double DEFAULT_EXPONENT = 2.0;
    
    public static final Vec2 ZERO_VECTOR = new Vec2(0.0f, 0.0f);
    
    public static final int TOP_ID = 1;
    public static final int RIGHT_ID = 2;
    public static final int BOTTOM_ID = 3;
    public static final int LEFT_ID = 4;
    
    public static final String GRAV = "gravity";
    public static final String VISC = "viscosity";
    public static final String COM = "com";
    public static final String WALL = "wall";
    
    protected HashMap<String, Boolean> mToggleMap = new HashMap<String, Boolean>();
    protected HashMap<Integer, Wall> mWallMap;
    
    private List<Assembly> mAssemblies;
    
    public EnvironmentManager(Springies s, String filename) {
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
        mAssemblies = s.getAssemblyList();
        initForceToggleMap();
    }
    
    public EnvironmentManager(Springies s) {
        mSpringies = s;
        mGravity = new Gravity(this.DEFAULT_GRAVITY_MAGNITUDE);
        mViscosity = new Viscosity(this.DEFAULT_VISCOSITY_MAGNITUDE);
        
        /**
         * replace getMassList with getAssemblyList
         */
        //mCOM = new COM(this.DEFAULT_COM_MAGNITUDE, this.DEFAULT_EXPONENT, s.getMassList());
        mWallRepulsionList = makeFourWallRepulsion(); 
        initForceToggleMap();
    }
    
    private void initForceToggleMap() {
        mToggleMap.put(GRAV, true);
        mToggleMap.put(VISC, true);
        mToggleMap.put(COM, true);
        mToggleMap.put(WALL, true);
    }

    public void toggleForces(String forceid) {
    	mToggleMap.put(forceid, !mToggleMap.get(forceid));
    }
    
    private List<WallRepulsion> makeFourWallRepulsion () {
        makeFourWalls();
        
        ArrayList<WallRepulsion> wall_repulsion_list= new ArrayList<WallRepulsion>();
        
        wall_repulsion_list.add(new WallRepulsion(mWallMap.get(TOP_ID)));
        wall_repulsion_list.add(new WallRepulsion(mWallMap.get(BOTTOM_ID)));
        wall_repulsion_list.add(new WallRepulsion(mWallMap.get(LEFT_ID)));
        wall_repulsion_list.add(new WallRepulsion(mWallMap.get(RIGHT_ID)));
        
        return wall_repulsion_list;
    }
    
    private void makeFourWalls() {
        mWallMap = new HashMap<Integer, Wall>();
        mWallMap.put(TOP_ID, new Wall(this.TOP_ID));
        mWallMap.put(BOTTOM_ID, new Wall(this.BOTTOM_ID));
        mWallMap.put(LEFT_ID, new Wall(this.LEFT_ID));
        mWallMap.put(RIGHT_ID, new Wall(this.RIGHT_ID));
    }

    public void doForces() {
    	int TEMP_INDEX = 0;
        for (Mass mass: (mSpringies.getAssemblyList().get(TEMP_INDEX)).getMassList()) {
            applyForce(GRAV, mGravity, mass);
            applyForce(VISC, mViscosity, mass);
            applyForce(COM, mCOM, mass);
            for (WallRepulsion w : mWallRepulsionList) {
                applyForce(WALL, w, mass);
            }
            //System.out.printf("Force applied on mass (%s): <%.2f, %.2f>\n", mass.getName(), mass.getBody().m_force.x, mass.getBody().m_force.y);
        }
    }
    
    public void applyForce(String force_id, Force force, Mass mass) {
        if (mToggleMap.get(force_id)) {
            mass.applyForceVector(force.calculateForce(mass));        
        } 
        else {
            mass.applyForceVector(ZERO_VECTOR);
        }
    }
    
    public void moveWalls (boolean move_out) {
        for (Wall wall: mWallMap.values()) {
            if (move_out) {
                wall.toggleOut();
            }
            else {
                wall.toggleIn();
            }
        }
    }
    public void toggleWallForces (int wall_id) {
        // TODO Auto-generated method stub
        
    }
    public void changeMuscleAmplitude (boolean increase) {
        // TODO Auto-generated method stub
        
    }
}
