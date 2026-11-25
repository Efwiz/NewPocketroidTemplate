package org.apache.xerces.jaxp;

import java.util.Hashtable;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/* JADX INFO: loaded from: classes13.dex */
public class SAXParserFactoryImpl extends SAXParserFactory {
    private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
    private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
    private static final String XINCLUDE_FEATURE = "http://apache.org/xml/features/xinclude";
    private boolean fSecureProcess = false;
    private Hashtable features;
    private Schema grammar;
    private boolean isXIncludeAware;

    private SAXParserImpl newSAXParserImpl() throws SAXNotRecognizedException, SAXNotSupportedException, ParserConfigurationException {
        try {
            return new SAXParserImpl(this, this.features);
        } catch (SAXNotRecognizedException e) {
            throw e;
        } catch (SAXNotSupportedException e2) {
            throw e2;
        } catch (SAXException e3) {
            throw new ParserConfigurationException(e3.getMessage());
        }
    }

    @Override // javax.xml.parsers.SAXParserFactory
    public boolean getFeature(String str) throws SAXNotRecognizedException, SAXNotSupportedException, ParserConfigurationException {
        str.getClass();
        if (str.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
            return this.fSecureProcess;
        }
        if (str.equals(NAMESPACES_FEATURE)) {
            return isNamespaceAware();
        }
        if (str.equals(VALIDATION_FEATURE)) {
            return isValidating();
        }
        return str.equals(XINCLUDE_FEATURE) ? isXIncludeAware() : newSAXParserImpl().getXMLReader().getFeature(str);
    }

    @Override // javax.xml.parsers.SAXParserFactory
    public Schema getSchema() {
        return this.grammar;
    }

    @Override // javax.xml.parsers.SAXParserFactory
    public boolean isXIncludeAware() {
        return this.isXIncludeAware;
    }

    @Override // javax.xml.parsers.SAXParserFactory
    public SAXParser newSAXParser() throws ParserConfigurationException {
        try {
            return new SAXParserImpl(this, this.features, this.fSecureProcess);
        } catch (SAXException e) {
            throw new ParserConfigurationException(e.getMessage());
        }
    }

    @Override // javax.xml.parsers.SAXParserFactory
    public void setFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException, ParserConfigurationException {
        str.getClass();
        if (str.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
            this.fSecureProcess = z;
            return;
        }
        if (str.equals(NAMESPACES_FEATURE)) {
            setNamespaceAware(z);
            return;
        }
        if (str.equals(VALIDATION_FEATURE)) {
            setValidating(z);
            return;
        }
        if (str.equals(XINCLUDE_FEATURE)) {
            setXIncludeAware(z);
            return;
        }
        if (this.features == null) {
            this.features = new Hashtable();
        }
        this.features.put(str, z ? Boolean.TRUE : Boolean.FALSE);
        try {
            newSAXParserImpl();
        } catch (SAXNotRecognizedException e) {
            this.features.remove(str);
            throw e;
        } catch (SAXNotSupportedException e2) {
            this.features.remove(str);
            throw e2;
        }
    }

    @Override // javax.xml.parsers.SAXParserFactory
    public void setSchema(Schema schema) {
        this.grammar = schema;
    }

    @Override // javax.xml.parsers.SAXParserFactory
    public void setXIncludeAware(boolean z) {
        this.isXIncludeAware = z;
    }
}
