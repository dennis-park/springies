package Parsers;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import springies.Springies;
import forces.*;
public class EnvironmentParser extends XMLParser {
    private Springies mSpringies;
    private Gravity mGravity; 
    private Viscosity mViscosity;
    private COM mCOM;
    private ArrayList<WallRepulsion> mWallRepulsionList;
    
    protected static final String ID = "id";
    protected static final String MAGNITUDE = "magnitude";
    protected static final String DIRECTION = "direction";
    protected static final String EXPONENT = "exponent";
    
    public EnvironmentParser(Springies s) {
        mSpringies = s;
    }
    
    public void startElement (String namespaceURI,
                              String localName,
                              String qName,
                              Attributes atts)
                                              throws SAXException {

        if (localName.equals("environment")) {
            isCorrectModel = true;
        }
        else if (localName.equals("gravity")) {
            parseGravity(atts);
        }
        else if (localName.equals("viscosity")) {
            parseViscosity(atts);
        }
        else if (localName.equals("centermass")) {
            parseCenterMass(atts);
        }
        else if (localName.equals("wall")) {
            parseWalls(atts);
        }
        mNodeNum++;
    }

    private void parseWalls (Attributes a) {
        if (a.getValue(ID) == null ||  a.getValue(MAGNITUDE) == null|| a.getValue(EXPONENT) == null) {
            this.malformedXML(a);
        }
        int id = Integer.parseInt(a.getValue(ID));
        double mag = Double.parseDouble(a.getValue(MAGNITUDE));
        double exp = Double.parseDouble(a.getValue(EXPONENT));
        WallRepulsion wall = new WallRepulsion(id, mag, exp);
        mWallRepulsionList.add(wall);
    }

    private void parseCenterMass (Attributes a) {
        if (a.getValue(MAGNITUDE) == null|| a.getValue(EXPONENT) == null) {
            this.malformedXML(a);
        }
        double mag = Double.parseDouble(a.getValue(MAGNITUDE));
        double exp = Double.parseDouble(a.getValue(EXPONENT));
        mCOM = new COM(mag, exp, mSpringies.getMassList());
    }

    private void parseViscosity (Attributes a) {
        if (a.getValue(MAGNITUDE) == null) {
            this.malformedXML(a);
        }
        double mag = Double.parseDouble(a.getValue(MAGNITUDE));
        mViscosity = new Viscosity(mag);
    }

    private void parseGravity (Attributes a) {
        if (a.getValue(DIRECTION) == null ||  a.getValue(MAGNITUDE) == null) {
            this.malformedXML(a);
        }
        double mag = Double.parseDouble(a.getValue(MAGNITUDE));
        double dir = Double.parseDouble(a.getValue(DIRECTION));
        mGravity = new Gravity(mag, dir);
    }
    
    public Gravity getGravity() {
        if (mGravity == null) {
            System.out.println("Error. Gravity has not been initialized yet.");
            System.exit(1);
        }
        return mGravity;
    }
    
    public Viscosity getViscosity() {
        if (mViscosity == null) {
            System.out.println("Error. Viscosity has not been initialized yet.");
            System.exit(1);
        }
        return mViscosity;
    }
    
    public COM getCOM() {
        if (mCOM == null) {
            System.out.println("Error. Center of mass has not been initialized yet.");
            System.exit(1);
        }
        return mCOM;
    }
    
    public ArrayList<WallRepulsion> getWallRepulsionList() {
        if (mWallRepulsionList == null) {
            System.out.println("Error. Wall repulsion has not been initialized yet.");
            System.exit(1);
        }
        return mWallRepulsionList;
    }
}
