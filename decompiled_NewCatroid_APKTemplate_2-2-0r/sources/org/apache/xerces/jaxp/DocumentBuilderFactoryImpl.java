package org.apache.xerces.jaxp;

import java.util.Hashtable;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.util.SAXMessageFormatter;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/* JADX INFO: loaded from: classes13.dex */
public class DocumentBuilderFactoryImpl extends DocumentBuilderFactory {
    private static final String CREATE_CDATA_NODES_FEATURE = "http://apache.org/xml/features/create-cdata-nodes";
    private static final String CREATE_ENTITY_REF_NODES_FEATURE = "http://apache.org/xml/features/dom/create-entity-ref-nodes";
    private static final String INCLUDE_COMMENTS_FEATURE = "http://apache.org/xml/features/include-comments";
    private static final String INCLUDE_IGNORABLE_WHITESPACE = "http://apache.org/xml/features/dom/include-ignorable-whitespace";
    private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
    private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
    private static final String XINCLUDE_FEATURE = "http://apache.org/xml/features/xinclude";
    private Hashtable attributes;
    private boolean fSecureProcess = false;
    private Hashtable features;
    private Schema grammar;
    private boolean isXIncludeAware;

    @Override // javax.xml.parsers.DocumentBuilderFactory
    public Object getAttribute(String str) throws IllegalArgumentException {
        Object obj;
        Hashtable hashtable = this.attributes;
        if (hashtable != null && (obj = hashtable.get(str)) != null) {
            return obj;
        }
        DOMParser dOMParser = null;
        try {
            dOMParser = new DocumentBuilderImpl(this, this.attributes, this.features).getDOMParser();
            return dOMParser.getProperty(str);
        } catch (SAXException e) {
            try {
                return dOMParser.getFeature(str) ? Boolean.TRUE : Boolean.FALSE;
            } catch (SAXException unused) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    @Override // javax.xml.parsers.DocumentBuilderFactory
    public boolean getFeature(String str) throws ParserConfigurationException {
        Object obj;
        if (str.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
            return this.fSecureProcess;
        }
        if (str.equals(NAMESPACES_FEATURE)) {
            return isNamespaceAware();
        }
        if (str.equals(VALIDATION_FEATURE)) {
            return isValidating();
        }
        if (str.equals(XINCLUDE_FEATURE)) {
            return isXIncludeAware();
        }
        if (str.equals(INCLUDE_IGNORABLE_WHITESPACE)) {
            return !isIgnoringElementContentWhitespace();
        }
        if (str.equals(CREATE_ENTITY_REF_NODES_FEATURE)) {
            return !isExpandEntityReferences();
        }
        if (str.equals(INCLUDE_COMMENTS_FEATURE)) {
            return !isIgnoringComments();
        }
        if (str.equals(CREATE_CDATA_NODES_FEATURE)) {
            return !isCoalescing();
        }
        Hashtable hashtable = this.features;
        if (hashtable != null && (obj = hashtable.get(str)) != null) {
            return ((Boolean) obj).booleanValue();
        }
        try {
            return new DocumentBuilderImpl(this, this.attributes, this.features).getDOMParser().getFeature(str);
        } catch (SAXException e) {
            throw new ParserConfigurationException(e.getMessage());
        }
    }

    @Override // javax.xml.parsers.DocumentBuilderFactory
    public Schema getSchema() {
        return this.grammar;
    }

    @Override // javax.xml.parsers.DocumentBuilderFactory
    public boolean isXIncludeAware() {
        return this.isXIncludeAware;
    }

    @Override // javax.xml.parsers.DocumentBuilderFactory
    public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        Hashtable hashtable;
        if (this.grammar != null && (hashtable = this.attributes) != null) {
            if (hashtable.containsKey(JAXPConstants.JAXP_SCHEMA_LANGUAGE)) {
                throw new ParserConfigurationException(SAXMessageFormatter.formatMessage(null, "schema-already-specified", new Object[]{JAXPConstants.JAXP_SCHEMA_LANGUAGE}));
            }
            if (this.attributes.containsKey(JAXPConstants.JAXP_SCHEMA_SOURCE)) {
                throw new ParserConfigurationException(SAXMessageFormatter.formatMessage(null, "schema-already-specified", new Object[]{JAXPConstants.JAXP_SCHEMA_SOURCE}));
            }
        }
        try {
            return new DocumentBuilderImpl(this, this.attributes, this.features, this.fSecureProcess);
        } catch (SAXException e) {
            throw new ParserConfigurationException(e.getMessage());
        }
    }

    @Override // javax.xml.parsers.DocumentBuilderFactory
    public void setAttribute(String str, Object obj) throws IllegalArgumentException {
        if (obj == null) {
            Hashtable hashtable = this.attributes;
            if (hashtable != null) {
                hashtable.remove(str);
                return;
            }
            return;
        }
        if (this.attributes == null) {
            this.attributes = new Hashtable();
        }
        this.attributes.put(str, obj);
        try {
            new DocumentBuilderImpl(this, this.attributes, this.features);
        } catch (Exception e) {
            this.attributes.remove(str);
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override // javax.xml.parsers.DocumentBuilderFactory
    public void setFeature(String str, boolean z) throws ParserConfigurationException {
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
        if (str.equals(INCLUDE_IGNORABLE_WHITESPACE)) {
            setIgnoringElementContentWhitespace(!z);
            return;
        }
        if (str.equals(CREATE_ENTITY_REF_NODES_FEATURE)) {
            setExpandEntityReferences(!z);
            return;
        }
        if (str.equals(INCLUDE_COMMENTS_FEATURE)) {
            setIgnoringComments(!z);
            return;
        }
        if (str.equals(CREATE_CDATA_NODES_FEATURE)) {
            setCoalescing(!z);
            return;
        }
        if (this.features == null) {
            this.features = new Hashtable();
        }
        this.features.put(str, z ? Boolean.TRUE : Boolean.FALSE);
        try {
            new DocumentBuilderImpl(this, this.attributes, this.features);
        } catch (SAXNotRecognizedException e) {
            this.features.remove(str);
            throw new ParserConfigurationException(e.getMessage());
        } catch (SAXNotSupportedException e2) {
            this.features.remove(str);
            throw new ParserConfigurationException(e2.getMessage());
        }
    }

    @Override // javax.xml.parsers.DocumentBuilderFactory
    public void setSchema(Schema schema) {
        this.grammar = schema;
    }

    @Override // javax.xml.parsers.DocumentBuilderFactory
    public void setXIncludeAware(boolean z) {
        this.isXIncludeAware = z;
    }
}
