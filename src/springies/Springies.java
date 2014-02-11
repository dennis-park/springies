package springies;

//import jgame.JGColor;
//import jgame.JGFont;
import java.io.File;
import java.util.ArrayList;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;
import listeners.OnClickListener;
import listeners.OnKeyListener;
import masses.Mass;
import springs.Spring;


/**
 * This is the engine of Springies.  It initializes the canvas, 
 * creates settings for the simulation, and 
 * lends control to the EnvironmentManager, Factory, and Listener objects.
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 */
@SuppressWarnings("serial")
public class Springies extends JGEngine {

    public ArrayList<Assembly> mAssemblyList;
    private AssemblyFactory mFactory;
    private EnvironmentManager mEnvironmentManager;
    private OnClickListener mMouseListener;
    private OnKeyListener mKeyListener;

    private static final int FPS = 10;
    private static final int FRAME_SKIP = 2;

	/**
     * Springies constructor that sets the window size and 
     * overall components of the engine.
     */
    public Springies () {
        // set the window size
        int height = 480;
        double aspect = 16.0 / 9.0;
        initEngineComponent((int) (height * aspect), height);
    }

    
    @Override
    /**
     * Initializes the canvas by setting default settings.
     *
     */
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
    /**
     * Initializes simulation by calling WorldManager,
     * creating a Factory for Assembly objects, and instantiating 
     * listener objects that will communicate with the EnvironmentManager.
     * Loads the environment based on the xml file in Assets.
     */
    public void initGame () {
        setFrameRate(FPS, FRAME_SKIP);
        WorldManager.initWorld(this);
        mAssemblyList = new ArrayList<Assembly>();
        mFactory = new AssemblyFactory(this);
        String environment_filename = "assets/environment.xml";
        // mEnvironmentManager = new EnvironmentManager(this);
        mEnvironmentManager = new EnvironmentManager(this, environment_filename);
        mKeyListener = new OnKeyListener(this, mEnvironmentManager);
        mMouseListener = new OnClickListener(mEnvironmentManager);
        
    }

    @Override
    /**
     * In each frame, Springies will check all the user input events and perform actions accordingly.
     * It will also iterate through all the masses and apply the Forces acting upon the masses at the 
     * moment. Then the JBox world will take 1 time step and update JGame accordingly.  
     */
    public void doFrame () {
        doListenerEvents();
        //toggleView();
        if (!mAssemblyList.isEmpty()) {
            mEnvironmentManager.doForces();
            WorldManager.getWorld().step(1f, 1);
            moveObjects();
            checkCollision(1 + 2, 1);
        }
    }

    /*private static final String[] force_id = { Constants.GRAV_ID, Constants.VISC_ID, Constants.COM_ID,
                                  Constants.WALL_ID };
	private String[] force_view;

    private void toggleView () {
    	force_view = new String[force_id.length];
    	for (int index = 0; index < force_id.length; index ++) {
    		int offset = index*10;
    		force_view[index] = force_id[1] + " " +
    				(mEnvironmentManager.getToggleMap().get(force_id[1])).toString().charAt(0);
    		drawString(force_view[index], 20+offset, 20+offset, 0, JGFont.ITALIC, JGColor.green);
    	}	
    }
     */
    
    /**
     * This is a helper method to call the built in JEngine listeners. This way
     * we don't have to worry about coordinates, etc. This method will send the lastKeyPressed
     * and the mouseEvents to the Listener class and the Listener class will perform the appropriate
     * actions
     */
    private void doListenerEvents () {
        int last_key = getLastKey();
        clearLastKey(); // last key has to be cleared every time
        mKeyListener.doKeyEvent(last_key);
        mMouseListener
                .doMouseEvent(getMouseButton(1), getMouseButton(3), getMouseX(), getMouseY());
    }

    /**
     * This makes a new Assembly object by calling the already instantiated
     * Factory object.  It then adds its product to the Assembly List.
     */
    public void makeAssembly () {
        mFactory.loadAssembly();
        // mFactory.loadFromFile(new File("assets/daintywalker.xml"));
        mAssemblyList.add(mFactory.getAssembly());
    }

    private void removeAllObjects () {
        for (Assembly a : mAssemblyList) {
            for (Mass mass : a.getMassList()) {
                removeObject(mass);
            }
            for (Spring spring : a.getSpringList()) {
                removeObject(spring);
            }
        }
    }

    /**
     * Clears the list of all Assemblies being stored in Springies. 
     * Removes all objects from canvas.
     * @param 
     * @return 
     */
    public void clearLoadedAssemblies () {
        removeAllObjects();
        mAssemblyList.clear();
    }

    /**
     * Getter for other classes to iterate through this list of 
     * Assembly objects
     * @param 
     * @return mAssemblyList
     */
    public ArrayList<Assembly> getAssemblyList () {
        return mAssemblyList;
    }

}
