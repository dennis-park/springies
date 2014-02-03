package Parsers;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import forces.*;
public class ParseEnvironment extends XMLParser {
    private Gravity mGravity; 
    private Viscosity mViscosity;
    
    protected static final String MAGNITUDE = "magnitude";
    protected static final String DIRECTION = "direction";
    protected static final String EXPONENT = "exponent";
    
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
        if (a.getValue("id") == null ||  a.getValue(MAGNITUDE) == null|| a.getValue(EXPONENT) == null) {
            this.malformedXML(a);
        }
    }

    private void parseCenterMass (Attributes a) {
        if (a.getValue(MAGNITUDE) == null|| a.getValue(EXPONENT) == null) {
            this.malformedXML(a);
        }
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
            System.out.println("Warning. Gravity has not been initialized yet.");
            System.exit(1);
        }
        return mGravity;
    }
    
    public Viscosity getViscosity() {
        if (mViscosity == null) {
            System.out.println("Warning. Viscosity has not been initialized yet.");
            System.exit(1);
        }
        return mViscosity;
    }
}
