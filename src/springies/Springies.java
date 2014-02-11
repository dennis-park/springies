package springies;

import java.util.ArrayList;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;
import listeners.OnClickListener;
import listeners.OnKeyListener;
import masses.Mass;
import springs.Spring;


@SuppressWarnings("serial")
public class Springies extends JGEngine {

    public ArrayList<Assembly> mAssemblyList;
    private AssemblyFactory mFactory;
    private EnvironmentManager mEnvironmentManager;
    private OnClickListener mMouseListener;
    private OnKeyListener mKeyListener;

    private static final int FPS = 10;
    private static final int FRAME_SKIP = 2;

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
        // mFactory.loadAssemblyFromFile(new File("assets/daintywalker.xml"));
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
     * 
     */
    public void doFrame () {
        doListenerEvents();
        updateToggleView();
        if (!mAssemblyList.isEmpty()) {
            mEnvironmentManager.doForces();
            WorldManager.getWorld().step(1f, 1);
            moveObjects();
            checkCollision(1 + 2, 1);
        }
    }

    private static final String[] force_id = { Constants.GRAV_ID, Constants.VISC_ID, Constants.COM_ID,
                                  Constants.WALL_ID };
	private String[] force_view;
	
    private void updateToggleView () {
    	force_view = new String[force_id.length];
    	for (int index = 0; index < force_id.length; index ++) {
    		int offset = index*10;
    		force_view[index] = force_id[1] + " " +
    				(mEnvironmentManager.getToggleMap().get(force_id[1])).toString().charAt(0);
    		this.drawString(force_view[index], 20+offset, 20+offset, 0);
    	}	
    }

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

    public void makeAssembly () {
        mFactory.loadAssembly();
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

    public void clearLoadedAssemblies () {
        removeAllObjects();
        mAssemblyList.clear();
    }

    public ArrayList<Assembly> getAssemblyList () {
        return mAssemblyList;
    }

}
