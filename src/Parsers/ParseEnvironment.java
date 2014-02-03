package Parsers;

import java.util.ArrayList;
import java.util.HashMap;
import masses.Mass;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import springs.Muscle;
import springs.Spring;

public class ParseEnvironment extends XMLParser {
    HashMap<String, Mass> mMasses;
    ArrayList<Spring> mSprings;
    ArrayList<Muscle> mMuscles;

    public ParseEnvironment() {
   
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
        if (a.getValue("id") == null ||  a.getValue("magnitude") == null|| a.getValue("exponent") == null) {
            this.malformedXML(a);
        }
        Mass newMass = createMassObj(a.getValue("id"), a.getValue("x"), a.getValue("y"),
                                     a.getValue("vx"), a.getValue("vy"), a.getValue("mass"));
        mMasses.put(a.getValue("id"), newMass);
        
    }

    private void parseCenterMass (Attributes atts) {
        // TODO Auto-generated method stub
        
    }

    private void parseViscosity (Attributes atts) {
        // TODO Auto-generated method stub
        
    }

    private void parseGravity (Attributes atts) {
        // TODO Auto-generated method stub
        
    }

}
