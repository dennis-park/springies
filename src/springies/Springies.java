package springies;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;
import listeners.JGameActionListener;
import masses.FixedMass;
import masses.Mass;
import springs.Spring;
import walls.Wall;
import Parsers.ModelParser;
import Parsers.XMLParserCaller;

@SuppressWarnings("serial")
public class Springies extends JGEngine {
	public ArrayList<Assembly> assemblyList;
	private HashMap<String, Mass> mMassMap;
	private ArrayList<Mass> mMassList;
	private ArrayList<Spring> mSpringsList;
	private Wall[] mWallArray;
	private EnvironmentManager mEnvironmentManager;
	private JGameActionListener mActionListener;

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
	public void initGame () {
        mMassList = new ArrayList<Mass>();
        mSpringsList = new ArrayList<Spring>();
        
        // setFrameRate(60, 2); // given
        setFrameRate(5, 2);
        
        // NOTE:
        //   world coordinates have y pointing down
        //   game coordinates have y pointing up
        // so gravity is up in world coords and down in game coords
        // so set all directions (e.g., forces, velocities) in world coords
        WorldManager.initWorld(this);
        //addTestSpring();
        testSpringForce();
		testAssembly();

		mEnvironmentManager = new EnvironmentManager(this);
		//mForceManager = new EnvironmentManager(this, environment_filename);
		mActionListener = new JGameActionListener(mEnvironmentManager);

		setFrameRate(5, 2);
	}
	
	private void testAssembly() {
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
	/**
	 * In each frame, Springies will check all the user input events and perform actions accordingly.
	 * It will also iterate through all the masses and apply the Forces acting upon the masses at the 
	 * moment. Then the JBox world will take 1 time step and update JGame accordingly.  
	 * 
	 */
	public void doFrame ()
	{
		doListenerEvents();
		mEnvironmentManager.doForces();
		WorldManager.getWorld().step(1f, 1);
		moveObjects();
		checkCollision(1 + 2, 1);
	}

	// This is a helper method to call the built in JEngine listeners. This way 
	// we don't have to worry about coordinates, etc. This method will send the lastKeyPressed
	// and the mouseEvents to the Listener class and the Listener class will perform
	// the appropriate actions
	private void doListenerEvents() {
		mActionListener.doKeyEvent(getLastKey());
		this.clearLastKey(); // last key has to be cleared every time
		mActionListener.doMouseEvent(getMouseButton(1), getMouseButton(3), getMouseX(), getMouseY());
	}

	@Override
	public void paintFrame () {}

	public Wall[] getWalls() {
		return mWallArray;
	}
	public List<Mass> getMassList() {
		return mMassList;
	}

	private static final String ASSETS = "assets/";

	public void makeAssembly() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"XML documents", "xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showDialog(null, "Load new Assembly file");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			this.loadAssemblyFromFile(file);
		}
	}
	public void loadAssemblyFromFile(File file) {
		if (file != null && file.getAbsolutePath().equals(ASSETS+"environment.xml")) {
			ModelParser factory = new ModelParser(this);
			try {
				makeModelFromXML(ASSETS+file.getPath());
				Assembly a = new Assembly();
				for (Mass mass : factory.getMasses()) {
					a.add(mass);
				}
				for (Spring spring : factory.getSprings()) {
					a.add(spring);
				}
				assemblyList.add(a);
				//a.addMuscles(factory.getAssemblyMuscles());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setMassMap(HashMap<String, Mass> massList) {
		this.mMassMap = massList;
	}

	public void setMassMap(ArrayList<Mass> mass_list) {
		this.mMassList = mass_list;
	}

	public void setSpringsList (ArrayList<Spring> springList) {
		this.mSpringsList = springList;
	}

	public List<Spring>  getSpringsList () {
		return mSpringsList;
	}
	
	public List<Assembly> getAssembly() {
		return assemblyList;
	}

	public void clearLoadedAssemblies() {
		assemblyList = new ArrayList<Assembly>();
	}
}