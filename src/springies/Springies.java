package springies;

import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<String, Mass> mMassMap;
    private ArrayList<Spring> mSpringsList;
    //public ForceManager mFManager = new ForceManager();
    
    public Springies ()
    {
        // set the window size
        int height = 480;
        double aspect = 16.0 / 9.0;
        initEngineComponent((int) (height * aspect), height);
        
        mMassMap = new HashMap<String, Mass>();
        mSpringsList = (new ArrayList<Spring>());
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
        addBall();
        addWalls();
    }

    public void addBall ()
    {
        // add a bouncy ball
        // NOTE: you could make this into a separate class, but I'm lazy
        double xpos = displayWidth() / 2;
        double ypos = displayHeight() / 2;
        PhysicalObject ball = new Mass("m0", xpos, ypos) {
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
        //ball.setForce(80, -10);
    }

    private void addWalls ()
    {
        // add walls to bounce off of
        // NOTE: immovable objects must have no mass
        final double WALL_MARGIN = 10;
        final double WALL_THICKNESS = 10;
        final double WALL_WIDTH = displayWidth() - WALL_MARGIN * 2 + WALL_THICKNESS;
        final double WALL_HEIGHT = displayHeight() - WALL_MARGIN * 2 + WALL_THICKNESS;
        PhysicalObject wall = new PhysicalObjectRect("wall", 2, JGColor.green,
                                                     WALL_WIDTH, WALL_THICKNESS);
        wall.setPos(displayWidth() / 2, WALL_MARGIN);
        wall = new PhysicalObjectRect("wall", 2, JGColor.green,
                                      WALL_WIDTH, WALL_THICKNESS);
        wall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);
        wall = new PhysicalObjectRect("wall", 2, JGColor.green,
                                      WALL_THICKNESS, WALL_HEIGHT);
        wall.setPos(WALL_MARGIN, displayHeight() / 2);
        wall = new PhysicalObjectRect("wall", 2, JGColor.green,
                                      WALL_THICKNESS, WALL_HEIGHT);
        wall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);
    }

    @Override
    public void doFrame ()
    {
        // update game objects
        WorldManager.getWorld().step(1f, 1);
        moveObjects();
        checkCollision(1 + 2, 1);
        /**
         * iterate through massmap to do forcemanager.doforces
         */
    }

    @Override
    public void paintFrame ()
    {
        // nothing to do
        // the objects paint themselves
    }
    
    public HashMap<String, Mass> getMassMap() {
        return mMassMap;
    }
    
    public void setMassMap(HashMap<String, Mass> mass_list) {
        this.mMassMap = mass_list;
    }

    public ArrayList<Spring>  getSpringsList () {
        return mSpringsList;
    }

    public void setSpringsList (ArrayList<Spring>  springs_list) {
        this.mSpringsList = springs_list;
    }
}