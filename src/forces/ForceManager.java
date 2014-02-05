package forces;

import java.util.ArrayList;
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
    }
    
}
