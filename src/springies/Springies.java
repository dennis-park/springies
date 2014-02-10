package springies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jboxGlue.*;
import jgame.*;
import jgame.platform.JGEngine;
import org.jbox2d.common.Vec2;
import Parsers.*;
import forces.EnvironmentManager;
import springs.*;
import walls.Wall;
import masses.*;

@SuppressWarnings("serial")
public class Springies extends JGEngine {
 
    public ArrayList<Assembly> assemblyList;
    private HashMap<String, Mass> mMassMap;
    private ArrayList<Mass> mMassList;
    private ArrayList<Spring> mSpringsList;
    private Wall[] mWallArray;
    private EnvironmentManager mForceManager;
    
    public Springies () {
        // set the window size
        int height = 480;
        double aspect = 16.0 / 9.0;
        initEngineComponent((int) (height * aspect), height);
        mMassMap = new HashMap<String, Mass>();
        mSpringsList = (new ArrayList<Spring>());
        assemblyList = new ArrayList<Assembly>();
    }

    @Override
    public void initCanvas ()
    {
        setCanvasSettings(1, // width of the canvas in tiles
                          1, // height of the canvas in tiles
                          displayWidth(), // width of one tile
                          displayHeight(), // height of one tile
                          null,// foreground colour -> use default colour white
                          null,// background colour -> use default colour black
                          null); // standard font -> use default font
    }

    @Override
    public void initGame ()
    {
        mMassList = new ArrayList<Mass>();
        mSpringsList = new ArrayList<Spring>();
        
        setFrameRate(60, 2);
        // NOTE:
        //   world coordinates have y pointing down
        //   game coordinates have y pointing up
        // so gravity is up in world coords and down in game coords
        // so set all directions (e.g., forces, velocities) in world coords
        WorldManager.initWorld(this);
        
        String model_filename = "assets/daintywalker.xml";
        String environment_filename = "assets/myEnvironment.xml";
        //addTestSpring();
        testSpringForce();
        makeModelFromXML(model_filename);
        
        //mForceManager = new ForceManager(this);
        mForceManager = new EnvironmentManager(this, environment_filename);
        // NEED TO ADD CODE SO WE CAN UPDATE FORCE MANAGER AS NEW MASSES ARE ADDED
    }

    private void makeModelFromXML (String filename) {
        XMLParserCaller caller = new XMLParserCaller();
        ModelParser parser = new ModelParser(this);
        try {
            caller.call(filename, parser);
        }
        catch (Exception e) {
            System.out.println("Error: Unable to parse XML file");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void testSpringForce() {
        double xpos = displayWidth() / 2;
        double ypos = displayHeight() / 2;
        addMass(xpos, ypos + 200);
        addFixedMass(xpos, ypos);
        Mass mass1 = this.mMassList.get(0);
        Mass mass2 = this.mMassList.get(1);
        Spring newSpring1 = new Spring (mass1, mass2, 150, 1);
        this.mSpringsList.add(newSpring1);
    }
    
    public void addTestSpring() {
        double xpos = displayWidth() / 2;
        double ypos = displayHeight() / 2;
        addMass(xpos, ypos);
        addMass(xpos + 100, ypos + 20);
        addMass(xpos + 100, ypos - 20);
        addMass(xpos - 100, ypos - 20);
        Mass mass1 = this.mMassList.get(0);
        Mass mass2 = this.mMassList.get(1);
        Mass mass3 = this.mMassList.get(2);
        Mass mass4 = this.mMassList.get(3);
        Spring newSpring1 = new Spring (mass1, mass2);
        Spring newSpring2 = new Spring (mass1, mass3, 2);
        Spring newSpring3 = new Spring (mass1, mass4, 2);
        Spring newSpring4 = new Spring (mass2, mass3, 2);
        this.mSpringsList.add(newSpring1);
        this.mSpringsList.add(newSpring2);
        this.mSpringsList.add(newSpring3);
        this.mSpringsList.add(newSpring4); 
    }
    
    public void addMass (double xpos, double ypos)
    {
        Mass ball = new Mass("m0", xpos, ypos);
        this.mMassList.add(ball);
    }
    public void addFixedMass (double xpos, double ypos)
    {
        FixedMass ball = new FixedMass("m0", xpos, ypos);
        this.mMassList.add(ball);
    }

    @Override
    public void doFrame ()
    {
        // update game objects
        mForceManager.doForces();
        WorldManager.getWorld().step(1f, 1);
        moveObjects();
        checkCollision(1 + 2, 1);
        
        /**
         * iterate through massmap to do forcemanager.doforces
         */
    }

    @Override
    public void paintFrame () {}
    
    public Wall[] getWalls() {
		return mWallArray;
	}
    
    public List<Mass> getMassList() {
        return mMassList;
    }
    
    public void addMassMap(HashMap<String, Mass> massList) {
    	/**
    	 * TODO
    	 */
    }
    
    public void setMassMap(HashMap<String, Mass> massList) {
    	this.mMassMap = massList;
    }
    
    public void setMassMap(ArrayList<Mass> mass_list) {
        this.mMassList = mass_list;
    }

    public ArrayList<Spring>  getSpringsList () {
        return mSpringsList;
    }

    public void setSpringsList (ArrayList<Spring>  springList) {
        this.mSpringsList = springList;
    }
    

//    public HashMap<String, Mass> getAssemblyMasses() {
//        return mMasses;
//    }
//    public ArrayList<Spring> getAssemblySprings() {
//        return mSprings;
//    }
//    
//    public void loadFile(File file) {
//        if (file != null && file.getAbsolutePath().equals(ASSETS+"environment.xml")) {
//                        ModelParser factory = new ModelParser(mSpringies);
//                        try {
//                                mCaller.call(ASSETS+file.getPath(), factory);
//                                Assembly a = new Assembly();
//                                for (Mass mass : factory.getAssemblyMasses().values()) {
//                                        a.add(mass);
//                                }
//                                for (Spring spring : factory.getAssemblySprings()) {
//                                        a.add(spring);
//                                }
//                                //a.addMuscles(factory.getAssemblyMuscles());
//                        } catch (Exception e) {
//                                // TODO Auto-generated catch block
//                                e.printStackTrace();
//                        }
//                }
//    }
}