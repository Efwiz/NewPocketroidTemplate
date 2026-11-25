package org.apache.xerces.impl;

import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLDocumentSource;

/* JADX INFO: loaded from: classes13.dex */
public class XMLNamespaceBinder implements XMLComponent, XMLDocumentFilter {
    protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    private final QName fAttributeQName = new QName();
    protected XMLDocumentHandler fDocumentHandler;
    protected XMLDocumentSource fDocumentSource;
    protected XMLErrorReporter fErrorReporter;
    private NamespaceContext fNamespaceContext;
    protected boolean fNamespaces;
    protected boolean fOnlyPassPrefixMappingEvents;
    protected SymbolTable fSymbolTable;
    protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    private static final String[] RECOGNIZED_FEATURES = {NAMESPACES};
    private static final Boolean[] FEATURE_DEFAULTS = {null};
    private static final String[] RECOGNIZED_PROPERTIES = {"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter"};
    private static final Object[] PROPERTY_DEFAULTS = {null, null};

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.characters(xMLString, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.comment(xMLString, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.doctypeDecl(str, str2, str3, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void emptyElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        if (this.fNamespaces) {
            handleStartElement(qName, xMLAttributes, augmentations, true);
            handleEndElement(qName, augmentations, true);
        } else {
            XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
            if (xMLDocumentHandler != null) {
                xMLDocumentHandler.emptyElement(qName, xMLAttributes, augmentations);
            }
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endCDATA(Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.endCDATA(augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endDocument(Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.endDocument(augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(QName qName, Augmentations augmentations) throws XNIException {
        if (this.fNamespaces) {
            handleEndElement(qName, augmentations, false);
            return;
        }
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.endElement(qName, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endGeneralEntity(String str, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.endGeneralEntity(str, augmentations);
    }

    @Override // org.apache.xerces.xni.parser.XMLDocumentSource
    public XMLDocumentHandler getDocumentHandler() {
        return this.fDocumentHandler;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public XMLDocumentSource getDocumentSource() {
        return this.fDocumentSource;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public Boolean getFeatureDefault(String str) {
        int i = 0;
        while (true) {
            String[] strArr = RECOGNIZED_FEATURES;
            if (i >= strArr.length) {
                return null;
            }
            if (strArr[i].equals(str)) {
                return FEATURE_DEFAULTS[i];
            }
            i++;
        }
    }

    public boolean getOnlyPassPrefixMappingEvents() {
        return this.fOnlyPassPrefixMappingEvents;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public Object getPropertyDefault(String str) {
        int i = 0;
        while (true) {
            String[] strArr = RECOGNIZED_PROPERTIES;
            if (i >= strArr.length) {
                return null;
            }
            if (strArr[i].equals(str)) {
                return PROPERTY_DEFAULTS[i];
            }
            i++;
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedFeatures() {
        return (String[]) RECOGNIZED_FEATURES.clone();
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedProperties() {
        return (String[]) RECOGNIZED_PROPERTIES.clone();
    }

    protected void handleEndElement(QName qName, Augmentations augmentations, boolean z) throws XNIException {
        String str = qName.prefix != null ? qName.prefix : XMLSymbols.EMPTY_STRING;
        qName.uri = this.fNamespaceContext.getURI(str);
        if (qName.uri != null) {
            qName.prefix = str;
        }
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null && !this.fOnlyPassPrefixMappingEvents && !z) {
            xMLDocumentHandler.endElement(qName, augmentations);
        }
        this.fNamespaceContext.popContext();
    }

    protected void handleStartElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations, boolean z) throws XNIException {
        this.fNamespaceContext.pushContext();
        if (qName.prefix == XMLSymbols.PREFIX_XMLNS) {
            this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "ElementXMLNSPrefix", new Object[]{qName.rawname}, (short) 2);
        }
        int length = xMLAttributes.getLength();
        for (int i = 0; i < length; i++) {
            String localName = xMLAttributes.getLocalName(i);
            String prefix = xMLAttributes.getPrefix(i);
            if (prefix == XMLSymbols.PREFIX_XMLNS || (prefix == XMLSymbols.EMPTY_STRING && localName == XMLSymbols.PREFIX_XMLNS)) {
                String strAddSymbol = this.fSymbolTable.addSymbol(xMLAttributes.getValue(i));
                if (prefix == XMLSymbols.PREFIX_XMLNS && localName == XMLSymbols.PREFIX_XMLNS) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXMLNS", new Object[]{xMLAttributes.getQName(i)}, (short) 2);
                }
                if (strAddSymbol == NamespaceContext.XMLNS_URI) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXMLNS", new Object[]{xMLAttributes.getQName(i)}, (short) 2);
                }
                if (localName == XMLSymbols.PREFIX_XML) {
                    if (strAddSymbol != NamespaceContext.XML_URI) {
                        this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXML", new Object[]{xMLAttributes.getQName(i)}, (short) 2);
                    }
                } else if (strAddSymbol == NamespaceContext.XML_URI) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "CantBindXML", new Object[]{xMLAttributes.getQName(i)}, (short) 2);
                }
                String str = localName != XMLSymbols.PREFIX_XMLNS ? localName : XMLSymbols.EMPTY_STRING;
                if (prefixBoundToNullURI(strAddSymbol, localName)) {
                    this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "EmptyPrefixedAttName", new Object[]{xMLAttributes.getQName(i)}, (short) 2);
                } else {
                    NamespaceContext namespaceContext = this.fNamespaceContext;
                    if (strAddSymbol.length() == 0) {
                        strAddSymbol = null;
                    }
                    namespaceContext.declarePrefix(str, strAddSymbol);
                }
            }
        }
        qName.uri = this.fNamespaceContext.getURI(qName.prefix != null ? qName.prefix : XMLSymbols.EMPTY_STRING);
        if (qName.prefix == null && qName.uri != null) {
            qName.prefix = XMLSymbols.EMPTY_STRING;
        }
        if (qName.prefix != null && qName.uri == null) {
            this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "ElementPrefixUnbound", new Object[]{qName.prefix, qName.rawname}, (short) 2);
        }
        for (int i2 = 0; i2 < length; i2++) {
            xMLAttributes.getName(i2, this.fAttributeQName);
            String str2 = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
            String str3 = this.fAttributeQName.rawname;
            if (str3 == XMLSymbols.PREFIX_XMLNS) {
                this.fAttributeQName.uri = this.fNamespaceContext.getURI(XMLSymbols.PREFIX_XMLNS);
            } else {
                if (str2 != XMLSymbols.EMPTY_STRING) {
                    this.fAttributeQName.uri = this.fNamespaceContext.getURI(str2);
                    if (this.fAttributeQName.uri == null) {
                        this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "AttributePrefixUnbound", new Object[]{qName.rawname, str3, str2}, (short) 2);
                    }
                }
            }
            xMLAttributes.setName(i2, this.fAttributeQName);
        }
        int length2 = xMLAttributes.getLength();
        for (int i3 = 0; i3 < length2 - 1; i3++) {
            String uri = xMLAttributes.getURI(i3);
            if (uri != null && uri != NamespaceContext.XMLNS_URI) {
                String localName2 = xMLAttributes.getLocalName(i3);
                for (int i4 = i3 + 1; i4 < length2; i4++) {
                    String localName3 = xMLAttributes.getLocalName(i4);
                    String uri2 = xMLAttributes.getURI(i4);
                    if (localName2 == localName3 && uri == uri2) {
                        this.fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN, "AttributeNSNotUnique", new Object[]{qName.rawname, localName2, uri}, (short) 2);
                    }
                }
            }
        }
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        if (z) {
            xMLDocumentHandler.emptyElement(qName, xMLAttributes, augmentations);
        } else {
            xMLDocumentHandler.startElement(qName, xMLAttributes, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void ignorableWhitespace(XMLString xMLString, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.ignorableWhitespace(xMLString, augmentations);
    }

    protected boolean prefixBoundToNullURI(String str, String str2) {
        return str == XMLSymbols.EMPTY_STRING && str2 != XMLSymbols.PREFIX_XMLNS;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.processingInstruction(str, xMLString, augmentations);
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void reset(XMLComponentManager xMLComponentManager) throws XNIException {
        try {
            this.fNamespaces = xMLComponentManager.getFeature(NAMESPACES);
        } catch (XMLConfigurationException unused) {
            this.fNamespaces = true;
        }
        this.fSymbolTable = (SymbolTable) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
        this.fErrorReporter = (XMLErrorReporter) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
    }

    @Override // org.apache.xerces.xni.parser.XMLDocumentSource
    public void setDocumentHandler(XMLDocumentHandler xMLDocumentHandler) {
        this.fDocumentHandler = xMLDocumentHandler;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void setDocumentSource(XMLDocumentSource xMLDocumentSource) {
        this.fDocumentSource = xMLDocumentSource;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
    }

    public void setOnlyPassPrefixMappingEvents(boolean z) {
        this.fOnlyPassPrefixMappingEvents = z;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        if (str.startsWith(Constants.XERCES_PROPERTY_PREFIX)) {
            int length = str.length() - Constants.XERCES_PROPERTY_PREFIX.length();
            if (length == Constants.SYMBOL_TABLE_PROPERTY.length() && str.endsWith(Constants.SYMBOL_TABLE_PROPERTY)) {
                this.fSymbolTable = (SymbolTable) obj;
            } else if (length == Constants.ERROR_REPORTER_PROPERTY.length() && str.endsWith(Constants.ERROR_REPORTER_PROPERTY)) {
                this.fErrorReporter = (XMLErrorReporter) obj;
            }
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startCDATA(Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.startCDATA(augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startDocument(XMLLocator xMLLocator, String str, NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
        this.fNamespaceContext = namespaceContext;
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.startDocument(xMLLocator, str, namespaceContext, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        if (this.fNamespaces) {
            handleStartElement(qName, xMLAttributes, augmentations, false);
            return;
        }
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.startElement(qName, xMLAttributes, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startGeneralEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.startGeneralEntity(str, xMLResourceIdentifier, str2, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void textDecl(String str, String str2, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.textDecl(str, str2, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void xmlDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler == null || this.fOnlyPassPrefixMappingEvents) {
            return;
        }
        xMLDocumentHandler.xmlDecl(str, str2, str3, augmentations);
    }
}
