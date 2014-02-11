package springies;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;
import listeners.JGameActionListener;
import masses.Mass;
import springs.Spring;
import walls.Wall;
import Parsers.ModelParser;
import Parsers.XMLParserCaller;


@SuppressWarnings("serial")
public class Springies extends JGEngine {
    public ArrayList<Assembly> mAssemblyList;
    private Wall[] mWallArray;
    private EnvironmentManager mEnvironmentManager;
    private JGameActionListener mActionListener;

    private final int FPS = 40;
    private final int FRAME_SKIP = 2;
    
    public Springies () {
        // set the window size
        int height = 480;
        double aspect = 16.0 / 9.0;
        initEngineComponent((int) (height * aspect), height);
        mAssemblyList = new ArrayList<Assembly>();
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
    public void initGame () {
        // setFrameRate(60, 2); // given
        setFrameRate(5, 2);

        // NOTE:
        // world coordinates have y pointing down
        // game coordinates have y pointing up
        // so gravity is up in world coords and down in game coords
        // so set all directions (e.g., forces, velocities) in world coords
        WorldManager.initWorld(this);
        mAssemblyList = new ArrayList<Assembly>();
        makeAssembly();
        mEnvironmentManager = new EnvironmentManager(this);
        // mForceManager = new EnvironmentManager(this, environment_filename);
        mActionListener = new JGameActionListener(this, mEnvironmentManager);
        setFrameRate(FPS, FRAME_SKIP);
    }

    private void testAssembly () {
        String model_filename = "assets/lamp.xml";
        String model_filename2 = "assets/daintywalker.xml";
        makeModelFromXML(model_filename);
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        makeModelFromXML(model_filename2);
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

    @Override
    /**
     * In each frame, Springies will check all the user input events and perform actions accordingly.
     * It will also iterate through all the masses and apply the Forces acting upon the masses at the 
     * moment. Then the JBox world will take 1 time step and update JGame accordingly.  
     * 
     */
    public void doFrame ()
    {
        doListenerEvents();
        // update game objects
        mEnvironmentManager.doForces();
        WorldManager.getWorld().step(1f, 1);
        moveObjects();
        checkCollision(1 + 2, 1);
    }

    // This is a helper method to call the built in JEngine listeners. This way
    // we don't have to worry about coordinates, etc. This method will send the lastKeyPressed
    // and the mouseEvents to the Listener class and the Listener class will perform
    // the appropriate actions
    private void doListenerEvents () {
    	int last_key = getLastKey();
        mActionListener.doKeyEvent(last_key);
        clearLastKey();
        mActionListener
                .doMouseEvent(getMouseButton(1), getMouseButton(3), getMouseX(), getMouseY());
    }

    public Wall[] getWalls () {
        return mWallArray;
    }

    public void makeAssembly () {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML documents", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showDialog(null, "Load new Assembly file");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            this.loadAssemblyFromFile(file);
        }
    }

    public void loadAssemblyFromFile (File file) {
        if (file != null) {
            ModelParser factory = new ModelParser(this);
            try {
                makeModelFromXML(file.getAbsolutePath());
                Assembly a = new Assembly();
                for (Mass mass : factory.getMasses()) {
                    a.add(mass);
                }
                for (Spring spring : factory.getSprings()) {
                    a.add(spring);
                }
                mAssemblyList.add(a);
                // a.addMuscles(factory.getAssemblyMuscles());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Assembly> getAssemblyList () {
//        if (mAssemblyList.size() == 0) {
//            makeAssembly();
//        }
        return mAssemblyList;
    }
    
    public Assembly getAssembly(int index) {
        return mAssemblyList.get(index);
    }

    public void clearLoadedAssemblies () {
        mAssemblyList.clear();
    }
}
