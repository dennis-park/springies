package forces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import masses.Mass;
import org.jbox2d.common.Vec2;
import jboxGlue.WorldManager;
import Parsers.*;
import springies.Springies;
import walls.Wall;

public class ForceManager {
    protected Springies mSpringies;
    protected Gravity mGravity;
    protected Viscosity mViscosity;
    protected COM mCOM;
    protected List<WallRepulsion> mWallRepulsionList;
    
    public static final double DEFAULT_GRAVITY_MAGNITUDE = 20.0;
    public static final double DEFAULT_VISCOSITY_MAGNITUDE = 0.8;
    public static final double DEFAULT_COM_MAGNITUDE = 0.8;
    public static final double DEFAULT_WALL_REPULSION_MAGNITUDE = 50;
    public static final double DEFAULT_EXPONENT = 0.0;
    
    public static final Vec2 ZERO_VECTOR = new Vec2(0.0f, 0.0f);
    
    public static final int TOP_ID = 1;
    public static final int RIGHT_ID = 2;
    public static final int BOTTOM_ID = 3;
    public static final int LEFT_ID = 4;
    
    private String GRAV = "gravity";
    private String VISC = "viscosity";
    private String COM = "com";
    private String WALL = "wall";
    
    HashMap<String, Boolean> toggleMap = new HashMap<String, Boolean>();
    
    public ForceManager(Springies s, Gravity g, Viscosity v, COM com, List<WallRepulsion> walls) {
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
    
    public ForceManager(Springies s) {
        mSpringies = s;
        mGravity = new Gravity(this.DEFAULT_GRAVITY_MAGNITUDE);
        mViscosity = new Viscosity(this.DEFAULT_VISCOSITY_MAGNITUDE);
        mCOM = new COM(this.DEFAULT_COM_MAGNITUDE, s.getMassList());
        mWallRepulsionList = makeFourWalls(); 
    }
    
    private List<WallRepulsion> makeFourWalls () {
        // TODO Auto-generated method stub
        Wall top_wall = new Wall(this.TOP_ID);
        Wall bottom_wall = new Wall(this.BOTTOM_ID);
        Wall left_wall = new Wall(this.LEFT_ID);
        Wall right_wall = new Wall(this.RIGHT_ID);
        
        WallRepulsion top_repulsion = new WallRepulsion(top_wall, DEFAULT_WALL_REPULSION_MAGNITUDE, DEFAULT_EXPONENT);
        WallRepulsion bottom_repulsion = new WallRepulsion(bottom_wall, DEFAULT_WALL_REPULSION_MAGNITUDE, DEFAULT_EXPONENT);
        WallRepulsion left_repulsion = new WallRepulsion(left_wall, DEFAULT_WALL_REPULSION_MAGNITUDE, DEFAULT_EXPONENT);
        WallRepulsion right_repulsion = new WallRepulsion(right_wall, DEFAULT_WALL_REPULSION_MAGNITUDE, DEFAULT_EXPONENT);
        
        ArrayList<WallRepulsion> wall_list= new ArrayList<WallRepulsion>();
        wall_list.add(top_repulsion);
        wall_list.add(bottom_repulsion);
        wall_list.add(left_repulsion);
        wall_list.add(right_repulsion);
        return null;
    }
    
    public void toggleForces(String forceid) {
    	toggleMap.put(forceid, !toggleMap.get(forceid));
    }
    
    public void doForces() {
        for (Mass mass: mSpringies.getMassList()) {
            System.out.printf("Forces being applied on mass %s\n", mass.getAnimId());
            applyForce(GRAV, mGravity, mass);
            applyForce(VISC, mViscosity, mass);
            applyForce(COM, mCOM, mass);
            for (WallRepulsion w: mWallRepulsionList) {
                applyForce(WALL, w, mass);
            }
        }
    }
    
    public void applyForce(String force_id, Force force, Mass mass) {
        if (toggleMap.get(force_id)) {
            WorldManager.getWorld().setGravity(force.calculateForce(mass));
        } 
        else {
            WorldManager.getWorld().setGravity(ZERO_VECTOR);
        }
    }
}
