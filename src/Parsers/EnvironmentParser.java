package Parsers;

import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import springies.Springies;


/**
 * <p>
 * The EnvironmentParser is a subclass of XMLParser (a SAX Handler). The EnvironmentParser parses
 * through an XML file with the tag "environment" and parses out the settings for the environment.
 * These settings can then be retrieved by calling the appropriate get methods to initialize an
 * EnvironmentManager for your Springies simulation. This assumes that the XML documents follows the
 * conventions set by the Springies Part 2 assignment and the example XML documents given.
 * </p>
 * <p>
 * In order to access the parameters read by the EnvironmentParser, user must call the appropriate
 * get methods after EnvironmentParser is called.
 * </p>
 * <p>
 * private void useEnvironmentParser(Springies s, String filename) { </br> mSpringies = s; </br>
 * EnvironmentParser parser = setEnvironmentFromParser(filename); </br>
 * 
 * double mGravityMag = parser.getGravityMag(); </br> double mGravityDir = parser.getGravityDir();
 * </br> double mViscosityMag = parser.getViscosityMag(); </br> double mCOMMag = parser.getCOMMag();
 * </br> double mCOMExp = parser.getCOMExp(); </br> HashMap<Integer, Double> mWallMagMap =
 * parser.getWallMagMap(); </br> HashMap<Integer, Double> mWallExpMap = parser.getWallExpMap();
 * </br> }
 * </p>
 * <p>
 * private EnvironmentParser setEnvironmentFromParser (String filename) { </br> XMLParserCaller
 * caller = new XMLParserCaller(); </br> EnvironmentParser parser = new
 * EnvironmentParser(mSpringies); </br> try { </br> caller.call(filename, parser); </br> } </br>
 * catch (Exception e) { </br> System.out.println("Error: Unable to parse XML file"); </br>
 * e.printStackTrace(); </br> System.exit(1); </br> } </br> return parser; </br> }
 * </p>
 * 
 * @author Thanh-Ha Nguyen
 * 
 */
public class EnvironmentParser extends XMLParser {

    protected Springies mSpringies;

    protected double mGravityMag;
    protected double mGravityDir;
    protected double mViscosityMag;
    protected double mCOMMag;
    protected double mCOMExp;
    protected HashMap<Integer, Double> mWallMag;
    protected HashMap<Integer, Double> mWallExp;

    protected static final String ID = "id";
    protected static final String MAGNITUDE = "magnitude";
    protected static final String DIRECTION = "direction";
    protected static final String EXPONENT = "exponent";

    /**
     * EnvironmentParser takes only Springies as a parameter. Filename is parsed through by caller
     * (XMLParserCaller)
     * 
     * @param s
     */
    public EnvironmentParser (Springies s) {
        mSpringies = s;
        mWallMag = new HashMap<Integer, Double>();
        mWallExp = new HashMap<Integer, Double>();
    }

    @Override
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
        if (a.getValue(ID) == null || a.getValue(MAGNITUDE) == null || a.getValue(EXPONENT) == null) {
            this.malformedXML(a);
        }

        int id = Integer.parseInt(a.getValue(ID));
        double mag = Double.parseDouble(a.getValue(MAGNITUDE));
        double exp = Double.parseDouble(a.getValue(EXPONENT));
        mWallMag.put(id, mag);
        mWallExp.put(id, exp);
    }

    private void parseCenterMass (Attributes a) {
        if (a.getValue(MAGNITUDE) == null || a.getValue(EXPONENT) == null) {
            this.malformedXML(a);
        }
        mCOMMag = Double.parseDouble(a.getValue(MAGNITUDE));
        mCOMExp = Double.parseDouble(a.getValue(EXPONENT));

    }

    private void parseViscosity (Attributes a) {
        if (a.getValue(MAGNITUDE) == null) {
            this.malformedXML(a);
        }
        mViscosityMag = Double.parseDouble(a.getValue(MAGNITUDE));
    }

    private void parseGravity (Attributes a) {
        if (a.getValue(DIRECTION) == null || a.getValue(MAGNITUDE) == null) {
            this.malformedXML(a);
        }
        mGravityMag = Double.parseDouble(a.getValue(MAGNITUDE));
        mGravityDir = Double.parseDouble(a.getValue(DIRECTION));
    }

    /**
     * To get parameters passed in
     * 
     * @return double gravity_magnitude
     */
    public double getGravityMag () {
        return mGravityMag;
    }

    /**
     * To get parameters passed in
     * 
     * @return double gravity_direction
     */
    public double getGravityDir () {
        return mGravityDir;
    }

    /**
     * To get parameters passed in
     * 
     * @return double viscosity_magnitude
     */
    public double getViscosityMag () {
        return mViscosityMag;
    }

    /**
     * To get parameters passed in
     * 
     * @return double center_of_mass_magnitude
     */
    public double getCOMMag () {
        return mCOMMag;
    }

    /**
     * To get parameters passed in
     * 
     * @return double center_of_mass_exponent
     */
    public double getCOMExp () {
        return mCOMExp;
    }

    /**
     * To get parameters passed in
     * 
     * @return HashMap<Integer, Double> wallMagnitudeMap
     */
    public HashMap<Integer, Double> getWallMagMap () {
        return mWallMag;
    }

    /**
     * To get parameters passed in
     * 
     * @return HashMap<Integer, Double> wallExponentMap
     */
    public HashMap<Integer, Double> getWallExpMap () {
        return mWallExp;
    }
}
