package springies;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import masses.Mass;
import springs.Spring;
import Parsers.ModelParser;
import Parsers.XMLParserCaller;

public class AssemblyFactory {
	
	private Springies mSpringies;
	private Assembly mAssembly;
	
	public AssemblyFactory(Springies s) {
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
                //mEnvironmentManager.updateCOM(a);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public Assembly getAssembly() {
    	return mAssembly;
    }
    
}
