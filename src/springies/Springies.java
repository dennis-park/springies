package springies;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import jboxGlue.WorldManager;
import jgame.platform.JGEngine;
import listeners.OnClickListener;
import listeners.OnKeyListener;
import masses.Mass;
import springs.Spring;
import walls.Wall;
import Parsers.ModelParser;
import Parsers.XMLParserCaller;


@SuppressWarnings("serial")
public class Springies extends JGEngine {

    public ArrayList<Assembly> mAssemblyList;
    private AssemblyFactory mFactory;
    private Wall[] mWallArray;
    private EnvironmentManager mEnvironmentManager;
    private OnClickListener mMouseListener;
    private OnKeyListener mKeyListener;

    private final int FPS = 40;
	private final int FRAME_SKIP = 2;
	
    public Springies () {
        // set the window size
        int height = 480;
        double aspect = 16.0 / 9.0;
        initEngineComponent((int) (height * aspect), height);
    }

    @Override
    public void initCanvas () {
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
    	setFrameRate(FPS, FRAME_SKIP);
    	WorldManager.initWorld(this);
    	mAssemblyList = new ArrayList<Assembly>();
        mFactory = new AssemblyFactory(this);
    	// notifyAssemblyFactory();
    	String environment_filename = "assets/environment.xml";
    	mFactory.loadAssemblyFromFile(new File("assets/daintywalker.xml"));
    	//mEnvironmentManager = new EnvironmentManager(this);
    	mEnvironmentManager = new EnvironmentManager(this, environment_filename);
    	mKeyListener = new OnKeyListener(this, mEnvironmentManager);
    	mMouseListener = new OnClickListener(mEnvironmentManager);
    }


    @Override
    /**
     * In each frame, Springies will check all the user input events and perform actions accordingly.
     * It will also iterate through all the masses and apply the Forces acting upon the masses at the 
     * moment. Then the JBox world will take 1 time step and update JGame accordingly.  
     * 
     */
    public void doFrame () {
        doListenerEvents();
        // update game objects
        if (!mAssemblyList.isEmpty()) {
			mEnvironmentManager.doForces();
			WorldManager.getWorld().step(1f, 1);
			moveObjects();
			checkCollision(1 + 2, 1);
		}
    }

    // This is a helper method to call the built in JEngine listeners. This way
    // we don't have to worry about coordinates, etc. This method will send the lastKeyPressed
    // and the mouseEvents to the Listener class and the Listener class will perform
    // the appropriate actions
    private void doListenerEvents () {
        int last_key = getLastKey();
        clearLastKey(); // last key has to be cleared every time
        mKeyListener.doKeyEvent(last_key);
        mMouseListener
                .doMouseEvent(getMouseButton(1), getMouseButton(3), getMouseX(), getMouseY());
    }
    
    public void notifyAssemblyFactory() {
		mFactory.makeAssembly();
	}

	private void removeAllObjects() {
		for (Assembly a : mAssemblyList) {
			for (Mass mass : a.getMassList()) {
				removeObject(mass);
			}
			for (Spring spring : a.getSpringList()) {
				removeObject(spring);
			}
		}
	}

	public void clearLoadedAssemblies () {
		removeAllObjects();
		mAssemblyList.clear();
	}

	public ArrayList<Assembly> getAssemblyList() {
		return mAssemblyList;
	}

	
}
