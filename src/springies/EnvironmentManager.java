package springies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import masses.Mass;
import org.jbox2d.common.Vec2;
import springs.Spring;
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
    protected List<WallRepulsion> mWallRepulsionList;
    protected List<COM> mCOMList;
    public static final double DEFAULT_GRAVITY_MAGNITUDE = 1;
    public static final double DEFAULT_VISCOSITY_MAGNITUDE = 0.2;
    public static final double DEFAULT_COM_MAGNITUDE = 0.1;
    public static final double DEFAULT_WALL_REPULSION_MAGNITUDE = 10;
    public static final double DEFAULT_EXPONENT = 1.0;
    
    public static final Vec2 ZERO_VECTOR = new Vec2(0.0f, 0.0f);
    
    public static final int TOP_ID = 1;
    public static final int RIGHT_ID = 2;
    public static final int BOTTOM_ID = 3;
    public static final int LEFT_ID = 4;
    
    public static final String GRAV_ID = "gravity";
    public static final String VISC_ID = "viscosity";
    public static final String COM_ID = "com";
    public static final String WALL_ID = "wall";
    
    protected HashMap<String, Boolean> mToggleMap = new HashMap<String, Boolean>();
    protected HashMap<Integer, Wall> mWallMap;
 
    
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
        mCOMList = parser.getCOMList();
        mWallRepulsionList = parser.getWallRepulsionList();
        mToggleMap = initForceToggleMap();
    }
    
    public EnvironmentManager(Springies s) {
        mSpringies = s;
        mGravity = new Gravity(this.DEFAULT_GRAVITY_MAGNITUDE);
        mViscosity = new Viscosity(this.DEFAULT_VISCOSITY_MAGNITUDE);
        
        /**
         * replace getMassList with getAssemblyList
         */
        mCOMList = makeCOMForAllForces();
        mWallRepulsionList = makeFourWallRepulsion(); 
        mToggleMap = initForceToggleMap();
    }
    
    private ArrayList<COM> makeCOMForAllForces () {
        ArrayList<COM> com_list = new ArrayList<COM>();
        ArrayList<Assembly> assembly_list = mSpringies.getAssemblyList();
        for (Assembly assembly: assembly_list) {
            com_list.add(new COM(DEFAULT_COM_MAGNITUDE, assembly));
        }
        return com_list;
    }

    private HashMap<String, Boolean> initForceToggleMap() {
        HashMap<String, Boolean> toggle_map = new HashMap<String, Boolean>();
        toggle_map.put(GRAV_ID, true);
        toggle_map.put(VISC_ID, true);
        toggle_map.put(COM_ID, true);
        for (WallRepulsion w: mWallRepulsionList) {
            toggle_map.put(String.format("%d", w.getWallId()), true);
        }
        return toggle_map;
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
            applyForce(GRAV_ID, mGravity, mass);
            applyForce(VISC_ID, mViscosity, mass);
            for (COM c: mCOMList) {
                applyForce(COM_ID, c, mass);
            }
            for (WallRepulsion w : mWallRepulsionList) {
                applyForce(String.format("%d", w.getWallId()), w, mass);
            }
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
        for (Wall wall : mWallMap.values()) {
            if (move_out) {
                wall.toggleOut();
            }
            else {
                wall.toggleIn();
            }
        }
    }
    public void changeMuscleAmplitude (boolean increase) {
        ArrayList<Spring> spring_list = getSpringsList();
        for (Spring s: spring_list) {
            s.changeAmplitude(increase);
        }
    }

    public ArrayList<Mass> getMassList () {
        ArrayList<Mass> all_masses = new ArrayList<Mass>();
        ArrayList<Assembly> all_assemblies = mSpringies.getAssemblyList();
        for (Assembly assembly: all_assemblies) {
            ArrayList<Mass> mass_list = assembly.getMassList(); 
            for (Mass mass: mass_list) {
                all_masses.add(mass);
            }
        }
        return all_masses;
    }
    
    public ArrayList<Spring> getSpringsList () {
        ArrayList<Spring> all_springs = new ArrayList<Spring>(); 
        ArrayList<Assembly> all_assemblies = mSpringies.getAssemblyList();
        for (Assembly assembly: all_assemblies) {
            ArrayList<Spring> springs_list = assembly.getSpringList(); 
            for (Spring springs: springs_list) {
                all_springs.add(springs);
            }
        }
        return all_springs;
    }
}
