package Parsers;
import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler{     
    boolean isCorrectModel = false;
    protected int mNodeNum;
    
    public static String convertToFileURL(String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }
   
    public void startElement(String namespaceURI,
                             String localName,
                             String qName, 
                             Attributes atts)
        throws SAXException {

        System.out.printf("Local Name = %s, qName = %s, # Attributes = %d\t", localName, qName, atts.getLength());
        for (int i=0; i < atts.getLength(); i++) {
            System.out.printf("%s=%s\t", atts.getLocalName(i), atts.getValue(i));
        }
        System.out.print("\n");
    }
    
    public void endDocument () throws SAXException {
        if (!isCorrectModel) {
            System.out.printf("Entered wrong XML document for model/environment\n");
            System.exit(0);
        }
    }
    
    protected void malformedXML(Attributes a) {
        System.out.printf("Malformed XML file at node # %d\n", mNodeNum);
        System.exit(0);
    }
}
