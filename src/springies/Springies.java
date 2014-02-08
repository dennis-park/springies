package springies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jboxGlue.PhysicalObject;
import jboxGlue.PhysicalObjectRect;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.JGObject;
import jgame.platform.JGEngine;
import org.jbox2d.common.Vec2;
import forces.ForceManager;
import springs.Spring;
import masses.Mass;


@SuppressWarnings("serial")
public class Springies extends JGEngine
{
    private ArrayList<Mass> mMassList;
    private ArrayList<Spring> mSpringsList;
    private ForceManager mForceManager;
    
    public Springies ()
    {
        // set the window size
        int height = 480;
        double aspect = 16.0 / 9.0;
        initEngineComponent((int) (height * aspect), height);
        
        mMassList = new ArrayList<Mass>();
        mSpringsList = new ArrayList<Spring>();
    }

    @Override
    public void initCanvas ()
    {
        // I have no idea what tiles do...
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
        setFrameRate(60, 2);
        // NOTE:
        //   world coordinates have y pointing down
        //   game coordinates have y pointing up
        // so gravity is up in world coords and down in game coords
        // so set all directions (e.g., forces, velocities) in world coords
        WorldManager.initWorld(this);
        WorldManager.getWorld().setGravity(new Vec2(0.0f, 0.5f));
        addTestSpring(); 
        for (Mass m: mMassList) {
            System.out.printf("Mass in mass list: %s\n", m.getAnimId());
        }
        mForceManager = new ForceManager(this);
        // NEED TO ADD CODE SO WE CAN UPDATE FORCE MANAGER AS NEW MASSES ARE ADDED
    }

    public void addTestSpring() {
        double xpos = displayWidth() / 2;
        double ypos = displayHeight() / 2;
        addMass(xpos, ypos);
        addMass(xpos + 100, ypos + 20);
        addMass(xpos + 100, ypos - 20);
        addMass(xpos - 100, ypos - 20);
        addWalls();
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
        // add a bouncy ball
        // NOTE: you could make this into a separate class, but I'm lazy
        Mass ball = new Mass("m0", xpos, ypos) {
        //PhysicalObject ball = new PhysicalObjectCircle("ball", 1, JGColor.blue, 10, 5) {
            @Override
            public void hit (JGObject other)
            {
                // we hit something! bounce off it!
                Vec2 velocity = myBody.getLinearVelocity();
                // is it a tall wall?
                final double DAMPING_FACTOR = 0.8;
                boolean isSide = other.getBBox().height > other.getBBox().width;
                if (isSide) {
                    velocity.x *= -DAMPING_FACTOR;
                }
                else {
                    velocity.y *= -DAMPING_FACTOR;
                }
                // apply the change
                myBody.setLinearVelocity(velocity);
            }
        };
        this.mMassList.add(ball);
    }

    private void addWalls ()
    {
//        // add walls to bounce off of
//        // NOTE: immovable objects must have no mass
//        final double WALL_MARGIN = 10;
//        final double WALL_THICKNESS = 10;
//        final double WALL_WIDTH = displayWidth() - WALL_MARGIN * 2 + WALL_THICKNESS;
//        final double WALL_HEIGHT = displayHeight() - WALL_MARGIN * 2 + WALL_THICKNESS;
//        PhysicalObject wall = new PhysicalObjectRect("wall", 2, JGColor.green,
//                                                     WALL_WIDTH, WALL_THICKNESS);
//        wall.setPos(displayWidth() / 2, WALL_MARGIN);
//        wall = new PhysicalObjectRect("wall", 2, JGColor.green,
//                                      WALL_WIDTH, WALL_THICKNESS);
//        wall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);
//        wall = new PhysicalObjectRect("wall", 2, JGColor.green,
//                                      WALL_THICKNESS, WALL_HEIGHT);
//        wall.setPos(WALL_MARGIN, displayHeight() / 2);
//        wall = new PhysicalObjectRect("wall", 2, JGColor.green,
//                                      WALL_THICKNESS, WALL_HEIGHT);
//        wall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);
    }

    @Override
    public void doFrame ()
    {
        // update game objects
        mForceManager.doForces();
        WorldManager.getWorld().step(1f, 1);
        moveObjects();
        checkCollision(1 + 2, 1);
    }

    @Override
    public void paintFrame ()
    {
        // nothing to do
        // the objects paint themselves
    }
    
    public List<Mass> getMassList() {
        return mMassList;
    }
    
    public void setMassMap(ArrayList<Mass> mass_list) {
        this.mMassList = mass_list;
    }

    public ArrayList<Spring>  getSpringsList () {
        return mSpringsList;
    }

    public void setSpringsList (ArrayList<Spring>  springs_list) {
        this.mSpringsList = springs_list;
    }
}