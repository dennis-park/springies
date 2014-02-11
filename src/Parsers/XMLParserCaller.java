package Parsers;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;


/**
 * Calls the SAX XML parser to parse the given filename.
 * 
 * @author Thanh-Ha Nguyen
 * 
 */
public class XMLParserCaller {

    /**
     * Given a filename and XMLParser, the caller will call methods necessary to parse the XML
     * document
     * 
     * @param filename
     * @param parser
     * @throws Exception
     */
    public void call (String filename, XMLParser parser) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(parser);
        xmlReader.parse(XMLParser.convertToFileURL(filename));
    }
}
