package Parsers;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;


public class XMLParserCaller {

    public void call (String filename, XMLParser parser) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(parser);
        xmlReader.parse(XMLParser.convertToFileURL(filename));
    }
}
