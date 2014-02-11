package Parsers;

import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import springies.Springies;


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

    public double getGravityMag () {
        return mGravityMag;
    }

    public double getGravityDir () {
        return mGravityDir;
    }

    public double getViscosityMag () {
        return mViscosityMag;
    }

    public double getCOMMag () {
        return mCOMMag;
    }

    public double getCOMExp () {
        return mCOMExp;
    }

    public HashMap<Integer, Double> getWallMagMap () {
        return mWallMag;
    }

    public HashMap<Integer, Double> getWallExpMap () {
        return mWallExp;
    }
}
