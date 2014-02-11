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
    
    private String GRAV = "gravity";
    private String VISC = "viscosity";
    private String COM = "com";
    private String WALL = "wall";
    //private String SPR = "spring";
    
    HashMap<String, Boolean> mToggleMap = new HashMap<String, Boolean>();
    
    public EnvironmentManager(Springies s, Gravity g, Viscosity v, COM com, List<WallRepulsion> walls) {
        mSpringies = s;
        mGravity = g;
        mViscosity = v;
        mCOM = com;
        mWallRepulsionList = walls;        
        initForceToggleMap();
    }
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
        initForceToggleMap();
    }
    
    public EnvironmentManager(Springies s) {
        mSpringies = s;
        mGravity = new Gravity(this.DEFAULT_GRAVITY_MAGNITUDE);
        mViscosity = new Viscosity(this.DEFAULT_VISCOSITY_MAGNITUDE);
        mCOM = new COM(this.DEFAULT_COM_MAGNITUDE, this.DEFAULT_EXPONENT, s.getMassList());
        mWallRepulsionList = makeFourWalls(); 
        for (Mass m: mSpringies.getMassList()) {
            System.out.printf("In Force Manager: Mass in mass list: %s\n", m.getName());
        }
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
        
        return wall_list;
    }

    public void doForces() {
        for (Mass mass: mSpringies.getMassList()) {
            applyForce(GRAV, mGravity, mass);
            applyForce(VISC, mViscosity, mass);
            applyForce(COM, mCOM, mass);
            for (WallRepulsion w : mWallRepulsionList) {
                applyForce(WALL, w, mass);
            }
            System.out.printf("Force applied on mass (%s): <%.2f, %.2f>\n", mass.getName(), mass.getBody().m_force.x, mass.getBody().m_force.y);
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
}
