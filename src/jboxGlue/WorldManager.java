package jboxGlue;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import jgame.platform.JGEngine;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.xml.sax.XMLReader;
import Parsers.ParseEnvironment;
import Parsers.XMLParser;



/**
 * The WorldManager is adapted from WorldManager.java given as source code for this
 * project. This class is responsible for setting up the JBox World that the Springies
 * Objects "live" and run in.
 * 
 * This class also controls all the global environment forces which act upon all the
 * objects with mass in the Springies world.
 * 
 * @author Thanh-Ha
 * 
 */
public class WorldManager
{
    public String mEnvFilename;
    public static World ourWorld;
    static {
        ourWorld = null;
    }

    WorldManager(String env_xml_fn) {
        mEnvFilename = env_xml_fn;
    }

    public void callXMLParser(String filename, XMLParser parser) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();   

        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(parser);
        xmlReader.parse(XMLParser.convertToFileURL(filename));
    }

    public static World getWorld ()
    {
        // make sure we have a world, just in case...
        if (ourWorld == null) { throw new RuntimeException("call initWorld() before getWorld()!"); }
        return ourWorld;
    }

    public static void initWorld (JGEngine engine)
    {
        AABB worldBounds = new AABB(new Vec2(0, 0),
                                    new Vec2(engine.displayWidth(), engine.displayHeight()));
        Vec2 gravity = new Vec2(0.0f, 0.0f);
        ourWorld = new World(worldBounds, gravity, true);
        setWorldForces(mEnvFilename);        
    }

    /**
     * Given the global environmental forces.
     * 
     * @param forces
     */
    public void setWorldForces () {
        try {
            callXMLParser(mEnvFilename, new ParseEnvironment());
        }
        catch (Exception e) {}

        ourWorld.setGravity();
        //ourWorld.setGravity(mForces.getGravity());

    }
}
