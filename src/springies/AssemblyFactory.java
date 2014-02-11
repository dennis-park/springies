package springies;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import masses.Mass;
import springs.Spring;
import Parsers.ModelParser;
import Parsers.XMLParserCaller;


/**
 * This class implements a factory for creating assemblies.
 * It loads (xml) files into a new Assembly object and returns 
 * an instance of it.
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 * 
 */

public class AssemblyFactory {

    private Springies mSpringies;
    private Assembly mAssembly;

    /**
     * Constructor for factory. Takes in Springies object 
     * and creates an instance of new Assembly object.
     * 
     * @param Springies s
     * @return
     */
    public AssemblyFactory (Springies s) {
        mSpringies = s;
        mAssembly = new Assembly();
    }

    private ModelParser makeModelFromXML (String filename) {
        XMLParserCaller caller = new XMLParserCaller();
        ModelParser parser = new ModelParser(mSpringies);
        try {
            caller.call(filename, parser);
        }
        catch (Exception e) {
            System.out.println("Error: Unable to parse XML file");
            e.printStackTrace();
            System.exit(1);
        }
        return parser;
    }

    /**
     * Opens a JFileChooser object that prompts user for file path
     * to be parsed.  Calls helper method loadFromFile(File file)
     * 
     * @param 
     * @return void 
     */
    public void loadAssembly () {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter XMLfilter = new FileNameExtensionFilter("XML documents", "xml");
        chooser.setFileFilter(XMLfilter);
        int returnVal = chooser.showDialog(null, "Load new Assembly file");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            loadFromFile(file);
        }
    }

    /**
     * Helper method to loadAssembly().
     * Loads XML document from file, calls a factory that 
     * parses through all models and stores in Assembly object.
     * 
     * @param file
     * @return
     */
    public void loadFromFile (File file) {
        if (file != null) {
            try {
                ModelParser factory = makeModelFromXML(file.getAbsolutePath());
                for (Mass mass : factory.getMasses()) {
                    mAssembly.add(mass);
                }
                for (Spring spring : factory.getSprings()) {
                    mAssembly.add(spring);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Getter for Assembly object created from file.
     * 
     * @param 
     * @return this
     */
    public Assembly getAssembly () {
        return mAssembly;
    }

}
