package Parsers;

import masses.*;
import springs.*;
import springies.Springies;
import java.util.List;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ModelParser extends XMLParser {
    protected Springies mSpringies;
    protected List<Mass> mMassList;
    protected List<Spring> mSprings;
    protected HashMap<String, Mass> mMassMap;

    public ModelParser (Springies s) {
        mSpringies = s;
        mMassList = s.getMassList();
        mSprings = s.getSpringsList();
        initMassMap();
    }

    private void initMassMap () {
        mMassMap = new HashMap<String, Mass>();
    }

    public void makeMassHashMap () {
        
    }
    
    public void startElement (String namespaceURI,
                              String localName,
                              String qName,
                              Attributes atts)
                                              throws SAXException {

        if (localName.equals("model")) {
            this.isCorrectModel = true;
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

    private void parseMass (Attributes a) {
        checkIdXY(a);
        Mass newMass = createMassObj(a.getValue("id"), a.getValue("x"), a.getValue("y"),
                                     a.getValue("vx"), a.getValue("vy"), a.getValue("mass"));
        mMassMap.put(a.getValue("id"), newMass);
    }

    private void parseFixedMass (Attributes a) {
        checkIdXY(a);
        FixedMass newMass =
                createFixedMassObj(a.getValue("id"), a.getValue("x"), a.getValue("y"),
                                   a.getValue("mass"));
        mMassMap.put(a.getValue("id"), newMass);
    }

    private void parseSpring (Attributes a) {
        checkSpringMasses(a);
        Spring newSpring =
                createSpringObj(a.getValue("a"), a.getValue("b"), a.getValue("restlength"),
                                a.getValue("constant"));
        mSprings.add(newSpring);
    }

    private void parseMuscle (Attributes a) {
        checkSpringMasses(a);
        Muscle newMuscle =
                createMuscleObj(a.getValue("a"), a.getValue("b"), a.getValue("restlength"),
                                a.getValue("amplitude"));
        mSprings.add(newMuscle);
    }

    private void checkSpringMasses (Attributes a) {
        if (a.getValue("a") == null || a.getValue("b") == null) {
            malformedXML(a);
        }
        if (!checkMassesExist(a.getValue("a")) || !checkMassesExist(a.getValue("b"))) {
            malformedXML(a);
        }
    }

    private boolean checkMassesExist (String mass_id) {
        boolean exist = false;
        for (String m_id : mMassMap.keySet()) {
            if (m_id.equals(mass_id)) {
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

    public FixedMass createFixedMassObj (String id, String x, String y, String mass) {
        if (id == null || x == null || y == null) {
            System.out.println("Mass not properly created");
        }
        String mass_id = id;
        double x_pos = Double.parseDouble(x);
        double y_pos = mSpringies.displayHeight() - Double.parseDouble(y);
        double obj_mass = 0.0;
        if (mass != null) {
            obj_mass = Double.parseDouble(mass);
        }

        return new FixedMass(mass_id, x_pos, y_pos, obj_mass);
    }

    public Mass createMassObj (String id,
                               String x,
                               String y,
                               String init_x,
                               String init_y,
                               String mass) {
        if (id == null || x == null || y == null) {
            System.out.println("Mass not properly created");
        }
        String mass_id = id;
        double x_pos = Double.parseDouble(x);
        double y_pos = mSpringies.displayHeight() - Double.parseDouble(y);

        if (init_x == null && init_y == null && mass == null) { return new Mass(mass_id, x_pos,
                                                                                y_pos); }
        if (init_x == null && init_y == null) { return new Mass(mass_id, x_pos, y_pos,
                                                                Double.parseDouble(mass)); }
        if (mass == null) { return new Mass(mass_id, x_pos, y_pos, Double.parseDouble(init_x),
                                            Double.parseDouble(init_y)); }
        return new Mass(mass_id, x_pos, y_pos, Double.parseDouble(init_x),
                        Double.parseDouble(init_y), Double.parseDouble(mass));
    }

    private Spring createSpringObj (String m1_id, String m2_id, String restlength, String constant) {
        System.out.printf("Parsing ... : %s\t%s\t%s\t%s\n", m1_id, m2_id, restlength, constant);
        
        Mass m1 = mMassMap.get(m1_id);
        Mass m2 = mMassMap.get(m2_id);
        
        Spring newSpring;
        if (restlength == null && constant == null) { 
            newSpring = new Spring(m1, m2); 
        }
        else if (constant == null) { 
            newSpring = new Spring(m1, m2, Double.parseDouble(restlength)); 
        }
        else {
            newSpring = new Spring(m1, m2, Double.parseDouble(restlength), Double.parseDouble(constant));
        }
        m1.connectSpring(newSpring);
        m2.connectSpring(newSpring);
        return newSpring;
    }

    private Muscle createMuscleObj (String m1_id, String m2_id, String restlength, String amplitude) {
        Mass m1 = mMassMap.get(m1_id);
        Mass m2 = mMassMap.get(m2_id);
        double amp = Double.parseDouble(amplitude);

        Muscle newMuscle; 
        if (restlength == null) { 
            newMuscle = new Muscle(m1, m2, amp); 
        }
        newMuscle = new Muscle(m1, m2, Double.parseDouble(restlength), amp);
        m1.connectSpring(newMuscle);
        m2.connectSpring(newMuscle);
        return newMuscle;
    }

}
