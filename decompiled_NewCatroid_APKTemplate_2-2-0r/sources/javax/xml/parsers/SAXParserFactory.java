package javax.xml.parsers;

import javax.xml.validation.Schema;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SAXParserFactory {
    private boolean validating = false;
    private boolean namespaceAware = false;

    protected SAXParserFactory() {
    }

    public static SAXParserFactory newInstance() {
        try {
            return (SAXParserFactory) FactoryFinder.find("javax.xml.parsers.SAXParserFactory", "org.apache.xerces.jaxp.SAXParserFactoryImpl");
        } catch (FactoryFinder.ConfigurationError e) {
            throw new FactoryConfigurationError(e.getException(), e.getMessage());
        }
    }

    public static SAXParserFactory newInstance(String str, ClassLoader classLoader) {
        if (str == null) {
            throw new FactoryConfigurationError("factoryClassName cannot be null.");
        }
        if (classLoader == null) {
            classLoader = SecuritySupport.getContextClassLoader();
        }
        try {
            return (SAXParserFactory) FactoryFinder.newInstance(str, classLoader, false);
        } catch (FactoryFinder.ConfigurationError e) {
            throw new FactoryConfigurationError(e.getException(), e.getMessage());
        }
    }

    public abstract boolean getFeature(String str) throws SAXNotRecognizedException, SAXNotSupportedException, ParserConfigurationException;

    public Schema getSchema() {
        throw new UnsupportedOperationException(new StringBuffer("This parser does not support specification \"").append(getClass().getPackage().getSpecificationTitle()).append("\" version \"").append(getClass().getPackage().getSpecificationVersion()).append("\"").toString());
    }

    public boolean isNamespaceAware() {
        return this.namespaceAware;
    }

    public boolean isValidating() {
        return this.validating;
    }

    public boolean isXIncludeAware() {
        throw new UnsupportedOperationException(new StringBuffer("This parser does not support specification \"").append(getClass().getPackage().getSpecificationTitle()).append("\" version \"").append(getClass().getPackage().getSpecificationVersion()).append("\"").toString());
    }

    public abstract SAXParser newSAXParser() throws ParserConfigurationException, SAXException;

    public abstract void setFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException, ParserConfigurationException;

    public void setNamespaceAware(boolean z) {
        this.namespaceAware = z;
    }

    public void setSchema(Schema schema) {
        throw new UnsupportedOperationException(new StringBuffer("This parser does not support specification \"").append(getClass().getPackage().getSpecificationTitle()).append("\" version \"").append(getClass().getPackage().getSpecificationVersion()).append("\"").toString());
    }

    public void setValidating(boolean z) {
        this.validating = z;
    }

    public void setXIncludeAware(boolean z) {
        throw new UnsupportedOperationException(new StringBuffer("This parser does not support specification \"").append(getClass().getPackage().getSpecificationTitle()).append("\" version \"").append(getClass().getPackage().getSpecificationVersion()).append("\"").toString());
    }
}
