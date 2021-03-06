package springies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import masses.Mass;
import springs.Spring;
import walls.Wall;
import forces.*;
import Parsers.*;


/**
 * This class is a manager for the environment specified by either default values or those parsed in
 * the environment XML. It organizes forces, applies their computed values to objects living in the
 * Springies environment, and calls toggle methods if necessary.
 * 
 * Known dependency: EnvironmentManager can only be initialized after the first assembly has been
 * created.
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 */
public class EnvironmentManager {
    protected Springies mSpringies;

    protected Gravity mGravity;
    protected Viscosity mViscosity;
    protected List<COM> mCOMList = new ArrayList<COM>();
    protected HashMap<Integer, Wall> mWallMap = new HashMap<Integer, Wall>();
    protected HashMap<Integer, WallRepulsion> mWallRepulsionMap =
            new HashMap<Integer, WallRepulsion>();

    protected double mGravityMag;
    protected double mGravityDir;
    protected double mViscosityMag;
    protected double mCOMMag;
    protected double mCOMExp;
    protected HashMap<Integer, Double> mWallMagMap = new HashMap<Integer, Double>();
    protected HashMap<Integer, Double> mWallExpMap = new HashMap<Integer, Double>();

    protected HashMap<String, Boolean> mToggleMap = new HashMap<String, Boolean>();
    protected HashMap<Integer, Boolean> mWallToggleMap = new HashMap<Integer, Boolean>();

    /**
     * Constructor for EnvironmentManager.
     * Copies Springies object and sets default values for forces.
     * Calls helper method to initialize environment.
     * 
     * @param s
     */
    public EnvironmentManager (Springies s) {
        mSpringies = s;

        mGravityMag = Constants.DEFAULT_GRAVITY_MAGNITUDE;
        mGravityDir = Constants.DEFAULT_GRAVITY_DIRECTION;
        mViscosityMag = Constants.DEFAULT_VISCOSITY_MAGNITUDE;
        mCOMMag = Constants.DEFAULT_COM_MAGNITUDE;
        mCOMExp = Constants.DEFAULT_EXPONENT;
        for (int i = 1; i <= Constants.NUM_WALLS; i++) {
            mWallMagMap.put(i, Constants.DEFAULT_WALL_REPULSION_MAGNITUDE);
            mWallExpMap.put(i, Constants.DEFAULT_EXPONENT);
        }

        initEnvironment();
    }

    /**
     * Constructor for EnvironmentManager.
     * Copies Springies object and sets values for forces as
     * defined by the XML document from the specified file path.
     * Calls helper method to initialize environment.
     * 
     * @param s
     * @param filename
     */
    public EnvironmentManager (Springies s, String filename) {
        mSpringies = s;
        EnvironmentParser parser = setEnvironmentFromParser(filename);

        mGravityMag = parser.getGravityMag();
        mGravityDir = parser.getGravityDir();
        mViscosityMag = parser.getViscosityMag();
        mCOMMag = parser.getCOMMag();
        mCOMExp = parser.getCOMExp();
        mWallMagMap = parser.getWallMagMap();
        mWallExpMap = parser.getWallExpMap();

        initEnvironment();
    }

    private void initEnvironment () {
        for (int i = 1; i <= Constants.NUM_WALLS; i++) {
            Wall new_wall = new Wall(i);
            WallRepulsion new_wall_force =
                    new WallRepulsion(new_wall, mWallMagMap.get(i), mWallExpMap.get(i));
            mWallMap.put(i, new_wall);
            mWallRepulsionMap.put(i, new_wall_force);
        }
        mGravity = new Gravity(mGravityMag, mGravityDir);
        mViscosity = new Viscosity(mViscosityMag);
        mToggleMap = initForceToggleMap();
        mWallToggleMap = initWallToggleMap();
    }

    /**
     * Updates Center of Mass force for all Assembly objects. Must be called
     * after every assembly is made or else the center of mass force will not be called on those
     * masses
     */
    public void updateCOM () {
        mCOMList = makeCOMForAllAssemblies();
    }

    private List<COM> makeCOMForAllAssemblies () {
        List<COM> com_list = new ArrayList<COM>();
        ArrayList<Assembly> assembly_list = mSpringies.getAssemblyList();
        for (Assembly assembly : assembly_list) {
            com_list.add(new COM(mCOMMag, assembly));
        }
        return com_list;
    }

    private EnvironmentParser setEnvironmentFromParser (String filename) {
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
        return parser;
    }

    private HashMap<String, Boolean> initForceToggleMap () {
        HashMap<String, Boolean> toggle_map = new HashMap<String, Boolean>();
        toggle_map.put(Constants.GRAV_ID, true);
        toggle_map.put(Constants.VISC_ID, true);
        toggle_map.put(Constants.COM_ID, true);
        return toggle_map;
    }

    private HashMap<Integer, Boolean> initWallToggleMap () {
        HashMap<Integer, Boolean> toggle_map = new HashMap<Integer, Boolean>();
        for (int i : mWallRepulsionMap.keySet()) {
            toggle_map.put(i, true);
        }
        return toggle_map;
    }

    /**
     * Getter for the member HashMap of toggles for gravity, viscosity,
     * and COM forces.
     * 
     * @return mToggleMap
     */
    public HashMap<String, Boolean> getToggleMap () {
        return mToggleMap;
    }

    /**
     * Stores one of the forces based on its ID and
     * toggles its boolean toggle value.
     * 
     * @param forceid
     */
    public void toggleForces (String forceid) {
        mToggleMap.put(forceid, !mToggleMap.get(forceid));
    }

    /**
     * Stores one of the wall forces based on its ID and
     * toggles its boolean toggle value.
     * 
     * @param wall_id
     */
    public void toggleWallForces (int wall_id) {
        mWallToggleMap.put(wall_id, !mWallToggleMap.get(wall_id));
    }

    /**
     * Applies gravity, viscosity, center of mass, and wall repulsion
     * forces on every mass in all assemblies.
     * 
     */
    public void doForces () {
        for (Assembly assembly : mSpringies.getAssemblyList()) {
            for (Mass mass : assembly.getMassList()) {
                applyForce(Constants.GRAV_ID, mGravity, mass);
                applyForce(Constants.VISC_ID, mViscosity, mass);
                for (COM c : mCOMList) {
                    applyForce(Constants.COM_ID, c, mass);
                }
                for (int i : mWallRepulsionMap.keySet()) {
                    applyWallForce(i, mass);
                }
            }
            
            for (Spring spring: assembly.getSpringList()) {
                spring.doSpringForce();
            }
        }
    }

    private void applyWallForce (int wallId, Mass mass) {
        if (mWallToggleMap.get(wallId)) {
            WallRepulsion force = mWallRepulsionMap.get(wallId);
            mass.applyForceVector(force.calculateForce(mass));
        }
        else {
            mass.applyForceVector(Constants.ZERO_VECTOR);
        }
    }

    /**
     * This will apply the computed force on the mass depending on whether
     * the force_id exists in the map of all toggle values for the forces.
     * If the id does not exist, it will apply the zero vector.
     * 
     * @param force_id
     * @param force
     * @param mass
     * @return com_list
     */
    public void applyForce (String force_id, Force force, Mass mass) {
        if (mToggleMap.get(force_id)) {
            mass.applyForceVector(force.calculateForce(mass));
        }
        else {
            mass.applyForceVector(Constants.ZERO_VECTOR);
        }
    }

    /**
     * Moves walls based on a boolean for toggling motion outward.
     * If move_out is true, the bounds will expand outward.
     * If move_out is false, the bounds will contract inward.
     * 
     * @param move_out
     */
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

    /**
     * Changes muscle-spring's amplitude in sinusoidal motion
     * based on specified offset. Iterates through manager's
     * comprehensive spring list.
     * 
     * @param increase
     */
    public void changeMuscleAmplitude (boolean increase) {
        List<Spring> spring_list = getSpringsList();
        for (Spring s : spring_list) {
            s.changeAmplitude(increase);
        }
    }

    /**
     * Iterates through all assemblies obtained from Springies object
     * to add to comprehensive mass list for storage in the environment.
     * Returns this mass list.
     * 
     * @return all_masses
     */
    public List<Mass> getMassList () {
        List<Mass> all_masses = new ArrayList<Mass>();
        List<Assembly> all_assemblies = mSpringies.getAssemblyList();
        for (Assembly assembly : all_assemblies) {
            List<Mass> mass_list = assembly.getMassList();
            for (Mass mass : mass_list) {
                all_masses.add(mass);
            }
        }
        return all_masses;
    }

    /**
     * Iterates through all assemblies obtained from Springies object
     * to add to comprehensive spring list for storage in the environment.
     * Returns this spring list.
     * 
     * @return all_springs
     */
    public List<Spring> getSpringsList () {
        List<Spring> all_springs = new ArrayList<Spring>();
        List<Assembly> all_assemblies = mSpringies.getAssemblyList();
        for (Assembly assembly : all_assemblies) {
            List<Spring> springs_list = assembly.getSpringList();
            for (Spring springs : springs_list) {
                all_springs.add(springs);
            }
        }
        return all_springs;
    }

}
