package Parsers;
import masses.Mass;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


public class ParseModel extends XMLParser {
    boolean isEnvXML;
    int mNodeNum;
    ArrayList<Mass> mMasses;
    ArrayList<FixedMass> mFixedMasses;
    ArrayList<Spring> mSprings;
    ArrayList<Muscle> mMuscles;

    public ParseModel () {
        isEnvXML = false;
        mMasses = new ArrayList<Mass>();
        mFixedMasses = new ArrayList<FixedMass>();
        mSprings = new ArrayList<Spring>();
        mMuscles = new ArrayList<Muscle>();
    }

    public void startElement (String namespaceURI,
                              String localName,
                              String qName,
                              Attributes atts)
                                              throws SAXException {

        if (localName.equals("model")) {
            isEnvXML = true;
        }
        else if (localName.equals("mass")) {
            parseMass(atts);
        }
        else if (localName.equals("fixed")) {
            parseFixedMass(atts);
        }
        else if (localName.equals("spring")) {
            parseSpring(atts);
        }
        else if (localName.equals("muscle")) {
            parseMuscle(atts);
        }
        mNodeNum++;
    }

    public void endDocument () throws SAXException {
        System.out
                .printf("%d nodes read, %d masses added, %d fixed masses added, %d springs added, %d muscles added\n",
                        mNodeNum, mMasses.size(), mFixedMasses.size(), mSprings.size(),
                        mMuscles.size());
    }

    private void parseMass (Attributes a) {
        checkIdXY(a);
        Mass newMass = createMassObj(a.getValue("id"), a.getValue("x"), a.getValue("y"),
                                      a.getValue("vx"), a.getValue("vy"), a.getValue("mass"));
        mMasses.add(newMass);
    }

    private void parseFixedMass (Attributes a) {
        checkIdXY(a);
        FixedMassDef newMass =
                new FixedMassDef(a.getValue("id"), a.getValue("x"), a.getValue("mass"));
        mFixedMasses.add(newMass);
    }

    private void parseSpring (Attributes a) {
        checkSpringMasses(a);
        SpringDef newSpring =
                new SpringDef(a.getValue("a"), a.getValue("b"), a.getValue("restlength"));
        mSprings.add(newSpring);
    }

    private void parseMuscle (Attributes a) {
        checkSpringMasses(a);
        MuscleDef newMuscle =
                new MuscleDef(a.getValue("a"), a.getValue("b"), a.getValue("restlength"));
        mMuscles.add(newMuscle);
    }

    private void checkSpringMasses (Attributes a) {
        if (a.getValue("a") == null || a.getValue("b") == null || a.getValue("restlength") == null) {
            malformedXML(a);
        }
        if (!checkMassesExist(a.getValue("a")) || !checkMassesExist(a.getValue("b"))) {
            malformedXML(a);
        }
    }

    private boolean checkMassesExist (String mass_id) {
        boolean exist = false;
        for (MassDef m : mMasses) {
            if (m.mId.equals(mass_id)) {
                exist = true;
                break;
            }
        }
        for (FixedMassDef f : mFixedMasses) {
            if (f.mId.equals(mass_id)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    private void checkIdXY (Attributes a) {
        if (a.getValue("id") == null) {
            malformedXML(a);
        }
        if (a.getValue("x") == null) {
            malformedXML(a);
        }
        if (a.getValue("y") == null) {
            malformedXML(a);
        }
    }
    
    public Mass createMassObj(String id, String x, String y, String init_x, String init_y, String mass)  {
        if (id == null || x == null || y == null) {
            System.out.println("Mass not properly created");
        }
        String mass_id = id;
        double x_pos = Double.parseDouble(x);
        double y_pos = Double.parseDouble(y); 
        double init_x_pos = 0.0;
        double init_y_pos = 0.0;
        double obj_mass = 0.0;
        
        if (init_x != null && init_y != null) {
            init_x_pos = Double.parseDouble(init_x);
            init_y_pos = Double.parseDouble(init_y);
        }        
        if (mass != null) {
            obj_mass = Double.parseDouble(mass);
        }
        
        return new Mass(mass_id, x_pos, y_pos, init_x_pos, init_y_pos, obj_mass);
    }

}
 