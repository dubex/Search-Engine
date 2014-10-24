import java.io.File;
import java.io.PrintStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.*;

public class TryDOM
    implements ErrorHandler
{
    Document xmlDoc;

    TryDOM()
    {
        xmlDoc = null;
    }

    public void buildDocument(File file)
    {
        DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
        documentbuilderfactory.setNamespaceAware(false);
        documentbuilderfactory.setValidating(false);
        DocumentBuilder documentbuilder = null;
        try
        {
            documentbuilder = documentbuilderfactory.newDocumentBuilder();
            documentbuilder.setErrorHandler(new TryDOM());
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            //System.exit(1);
        }
        try
        {
            xmlDoc = documentbuilder.parse(file);
        }
        catch(Exception exception1)
        {
            exception1.printStackTrace();
          //  System.exit(0);
        }
    }

    public String getName()
    {
        return xmlDoc.getDocumentURI();
    }

	public String getDocNum()
	{
		NodeList nodelist = xmlDoc.getElementsByTagName("DOCNO");
        String s = nodelist.item(0).getTextContent();
        return s;
    
	}
    public String getTitle()
    {
        NodeList nodelist = xmlDoc.getElementsByTagName("title");
        String s = nodelist.item(0).getTextContent();
        return s;
    }

    public String getFirstPara()
    {
        NodeList nodelist = xmlDoc.getElementsByTagName("fp");
        String s = nodelist.item(0).getTextContent();
        return s;
    }

    public String getSecondPara()
    {
        NodeList nodelist = xmlDoc.getElementsByTagName("sp");
        String s = nodelist.item(0).getTextContent();
        return s;
    }

    public String getWholeDocument()
    {
        String s = (new StringBuilder()).append(getTitle()).append(getFirstPara()).append(getSecondPara()).toString();
        return s;
    }

    public void fatalError(SAXParseException saxparseexception)
        throws SAXException
    {
        System.out.println((new StringBuilder()).append("Fatal error at line").append(saxparseexception.getLineNumber()).toString());
        throw saxparseexception;
    }

    public void warning(SAXParseException saxparseexception)
    {
        System.out.println((new StringBuilder()).append("Warning at the line ").append(saxparseexception.getLineNumber()).toString());
        System.out.println(saxparseexception.getMessage());
    }

    public void error(SAXParseException saxparseexception)
    {
        System.out.println((new StringBuilder()).append("Error at the line ").append(saxparseexception.getLineNumber()).toString());
        System.out.println(saxparseexception.getMessage());
    }
}