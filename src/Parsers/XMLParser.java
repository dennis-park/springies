package Parsers;

import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class provides a general SAX handler for an XML file. Mainly here for the malformed
 * XML error handler but will be useful if there are other general xml parsing methods.  
 * 
 * @author Thanh-Ha
 *
 */
public class XMLParser extends DefaultHandler {
    boolean isCorrectModel = false;
    protected int mNodeNum;

    /**
     * Convert filename to URL for handler to open and parse.
     * 
     * @param filename
     * @return
     */
    public static String convertToFileURL (String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    /**
     * General code called when element is reached. 
     */
    public void startElement (String namespaceURI,
                              String localName,
                              String qName,
                              Attributes atts)
                                              throws SAXException {
        System.out.printf("Local Name = %s, qName = %s, # Attributes = %d\t", localName, qName,
                          atts.getLength());
        for (int i = 0; i < atts.getLength(); i++) {
            System.out.printf("%s=%s\t", atts.getLocalName(i), atts.getValue(i));
        }
        System.out.print("\n");
    }

    /** 
     * General code for XMLParsers when document is done. 
     */
    public void endDocument () throws SAXException {
        if (!isCorrectModel) {
            System.out.printf("Entered wrong XML document for model/environment\n");
            System.exit(0);
        }
    }

    /** 
     * This error is thrown when the parser is unable to read the necessary elements to build
     * the model or environment. 
     * 
     * @param a
     */
    protected void malformedXML (Attributes a) {
        System.out.printf("Malformed XML file at node # %d\n", mNodeNum);
        for (int i = 0; i < a.getLength(); i++) {
            System.out.printf("Attributes: (%s) - (%s)\n", a.getLocalName(i), a.getValue(i));
        }
        System.exit(0);
    }
}
