package springies;

import Parsers.*;
import jboxGlue.*;
import jgame.JGColor;
import jgame.JGObject;
import jgame.platform.JGEngine;
import org.jbox2d.common.Vec2;
import masses.Mass;
import javax.xml.parsers.*;
import org.xml.sax.XMLReader;

@SuppressWarnings("serial")
public class Springies extends JGEngine
{
    public Springies ()
    {
        // set the window size
        int height = 480;
        double aspect = 16.0 / 9.0;
        initEngineComponent((int) (height * aspect), height);
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
        WorldManager.getWorld().setGravity(new Vec2(0.0f, 1.0f));
        addBall();
        addWalls();
    }

    public void addBall ()
    {
        // add a bouncy ball
        // NOTE: you could make this into a separate class, but I'm lazy
        PhysicalObject ball = new Mass("m0", (double) displayWidth() / 2, (double) displayHeight() / 2, 1.0, 0.0) {
 
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
        ball.setForce(8000, -10000);
    }
    
    
    public void callXMLParser(String filename, XMLParser parser) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();   
        
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(parser);
        xmlReader.parse(XMLParser.convertToFileURL(filename));
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
    }

    @Override
    public void paintFrame ()
    {
        // nothing to do
        // the objects paint themselves
    }
}
