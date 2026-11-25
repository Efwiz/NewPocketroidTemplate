package org.apache.xerces.parsers;

import java.io.CharConversionException;
import java.io.IOException;
import java.util.Locale;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.util.EntityResolver2Wrapper;
import org.apache.xerces.util.EntityResolverWrapper;
import org.apache.xerces.util.ErrorHandlerWrapper;
import org.apache.xerces.util.SAXMessageFormatter;
import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParseException;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.PSVIProvider;
import org.catrobat.catroid.BuildConfig;
import org.xml.sax.AttributeList;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.Attributes2;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.EntityResolver2;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.ext.Locator2;
import org.xml.sax.ext.Locator2Impl;

/* JADX INFO: loaded from: classes13.dex */
public abstract class AbstractSAXParser extends AbstractXMLDocumentParser implements PSVIProvider, Parser, XMLReader {
    protected static final String ALLOW_UE_AND_NOTATION_EVENTS = "http://xml.org/sax/features/allow-dtd-events-after-endDTD";
    private final AttributesProxy fAttributesProxy;
    private Augmentations fAugmentations;
    protected ContentHandler fContentHandler;
    protected DTDHandler fDTDHandler;
    protected DeclHandler fDeclHandler;
    protected SymbolHash fDeclaredAttrs;
    protected DocumentHandler fDocumentHandler;
    protected LexicalHandler fLexicalHandler;
    protected boolean fLexicalHandlerParameterEntities;
    protected NamespaceContext fNamespaceContext;
    protected boolean fNamespacePrefixes;
    protected boolean fNamespaces;
    protected boolean fParseInProgress;
    protected final QName fQName;
    protected boolean fResolveDTDURIs;
    protected boolean fStandalone;
    protected boolean fUseEntityResolver2;
    protected String fVersion;
    protected boolean fXMLNSURIs;
    protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    protected static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
    private static final String[] RECOGNIZED_FEATURES = {NAMESPACES, STRING_INTERNING};
    protected static final String LEXICAL_HANDLER = "http://xml.org/sax/properties/lexical-handler";
    protected static final String DECLARATION_HANDLER = "http://xml.org/sax/properties/declaration-handler";
    protected static final String DOM_NODE = "http://xml.org/sax/properties/dom-node";
    private static final String[] RECOGNIZED_PROPERTIES = {LEXICAL_HANDLER, DECLARATION_HANDLER, DOM_NODE};

    protected static final class AttributesProxy implements AttributeList, Attributes2 {
        protected XMLAttributes fAttributes;

        protected AttributesProxy() {
        }

        @Override // org.xml.sax.Attributes
        public int getIndex(String str) {
            return this.fAttributes.getIndex(str);
        }

        @Override // org.xml.sax.Attributes
        public int getIndex(String str, String str2) {
            return str.length() == 0 ? this.fAttributes.getIndex(null, str2) : this.fAttributes.getIndex(str, str2);
        }

        @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
        public int getLength() {
            return this.fAttributes.getLength();
        }

        @Override // org.xml.sax.Attributes
        public String getLocalName(int i) {
            return this.fAttributes.getLocalName(i);
        }

        @Override // org.xml.sax.AttributeList
        public String getName(int i) {
            return this.fAttributes.getQName(i);
        }

        @Override // org.xml.sax.Attributes
        public String getQName(int i) {
            return this.fAttributes.getQName(i);
        }

        @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
        public String getType(int i) {
            return this.fAttributes.getType(i);
        }

        @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
        public String getType(String str) {
            return this.fAttributes.getType(str);
        }

        @Override // org.xml.sax.Attributes
        public String getType(String str, String str2) {
            return str.length() == 0 ? this.fAttributes.getType(null, str2) : this.fAttributes.getType(str, str2);
        }

        @Override // org.xml.sax.Attributes
        public String getURI(int i) {
            String uri = this.fAttributes.getURI(i);
            return uri != null ? uri : "";
        }

        @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
        public String getValue(int i) {
            return this.fAttributes.getValue(i);
        }

        @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
        public String getValue(String str) {
            return this.fAttributes.getValue(str);
        }

        @Override // org.xml.sax.Attributes
        public String getValue(String str, String str2) {
            return str.length() == 0 ? this.fAttributes.getValue(null, str2) : this.fAttributes.getValue(str, str2);
        }

        @Override // org.xml.sax.ext.Attributes2
        public boolean isDeclared(int i) {
            if (i < 0 || i >= this.fAttributes.getLength()) {
                throw new ArrayIndexOutOfBoundsException(i);
            }
            return Boolean.TRUE.equals(this.fAttributes.getAugmentations(i).getItem(Constants.ATTRIBUTE_DECLARED));
        }

        @Override // org.xml.sax.ext.Attributes2
        public boolean isDeclared(String str) {
            int index = getIndex(str);
            if (index != -1) {
                return Boolean.TRUE.equals(this.fAttributes.getAugmentations(index).getItem(Constants.ATTRIBUTE_DECLARED));
            }
            throw new IllegalArgumentException(str);
        }

        @Override // org.xml.sax.ext.Attributes2
        public boolean isDeclared(String str, String str2) {
            int index = getIndex(str, str2);
            if (index != -1) {
                return Boolean.TRUE.equals(this.fAttributes.getAugmentations(index).getItem(Constants.ATTRIBUTE_DECLARED));
            }
            throw new IllegalArgumentException(str2);
        }

        @Override // org.xml.sax.ext.Attributes2
        public boolean isSpecified(int i) {
            if (i < 0 || i >= this.fAttributes.getLength()) {
                throw new ArrayIndexOutOfBoundsException(i);
            }
            return this.fAttributes.isSpecified(i);
        }

        @Override // org.xml.sax.ext.Attributes2
        public boolean isSpecified(String str) {
            int index = getIndex(str);
            if (index != -1) {
                return this.fAttributes.isSpecified(index);
            }
            throw new IllegalArgumentException(str);
        }

        @Override // org.xml.sax.ext.Attributes2
        public boolean isSpecified(String str, String str2) {
            int index = getIndex(str, str2);
            if (index != -1) {
                return this.fAttributes.isSpecified(index);
            }
            throw new IllegalArgumentException(str2);
        }

        public void setAttributes(XMLAttributes xMLAttributes) {
            this.fAttributes = xMLAttributes;
        }
    }

    protected static final class LocatorProxy implements Locator2 {
        protected XMLLocator fLocator;

        public LocatorProxy(XMLLocator xMLLocator) {
            this.fLocator = xMLLocator;
        }

        @Override // org.xml.sax.Locator
        public int getColumnNumber() {
            return this.fLocator.getColumnNumber();
        }

        @Override // org.xml.sax.ext.Locator2
        public String getEncoding() {
            return this.fLocator.getEncoding();
        }

        @Override // org.xml.sax.Locator
        public int getLineNumber() {
            return this.fLocator.getLineNumber();
        }

        @Override // org.xml.sax.Locator
        public String getPublicId() {
            return this.fLocator.getPublicId();
        }

        @Override // org.xml.sax.Locator
        public String getSystemId() {
            return this.fLocator.getExpandedSystemId();
        }

        @Override // org.xml.sax.ext.Locator2
        public String getXMLVersion() {
            return this.fLocator.getXMLVersion();
        }
    }

    protected AbstractSAXParser(XMLParserConfiguration xMLParserConfiguration) {
        super(xMLParserConfiguration);
        this.fNamespacePrefixes = false;
        this.fLexicalHandlerParameterEntities = true;
        this.fResolveDTDURIs = true;
        this.fUseEntityResolver2 = true;
        this.fXMLNSURIs = false;
        this.fQName = new QName();
        this.fParseInProgress = false;
        this.fAttributesProxy = new AttributesProxy();
        this.fAugmentations = null;
        this.fDeclaredAttrs = null;
        xMLParserConfiguration.addRecognizedFeatures(RECOGNIZED_FEATURES);
        xMLParserConfiguration.addRecognizedProperties(RECOGNIZED_PROPERTIES);
        try {
            xMLParserConfiguration.setFeature(ALLOW_UE_AND_NOTATION_EVENTS, false);
        } catch (XMLConfigurationException unused) {
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void attributeDecl(String str, String str2, String str3, String[] strArr, String str4, XMLString xMLString, XMLString xMLString2, Augmentations augmentations) throws XNIException {
        try {
            if (this.fDeclHandler != null) {
                String string = new StringBuffer(str).append('<').append(str2).toString();
                if (this.fDeclaredAttrs.get(string) != null) {
                    return;
                }
                this.fDeclaredAttrs.put(string, Boolean.TRUE);
                if (str3.equals(SchemaSymbols.ATTVAL_NOTATION) || str3.equals("ENUMERATION")) {
                    StringBuffer stringBuffer = new StringBuffer();
                    if (str3.equals(SchemaSymbols.ATTVAL_NOTATION)) {
                        stringBuffer.append(str3);
                        stringBuffer.append(" (");
                    } else {
                        stringBuffer.append('(');
                    }
                    for (int i = 0; i < strArr.length; i++) {
                        stringBuffer.append(strArr[i]);
                        if (i < strArr.length - 1) {
                            stringBuffer.append('|');
                        }
                    }
                    stringBuffer.append(')');
                    str3 = stringBuffer.toString();
                }
                this.fDeclHandler.attributeDecl(str, str2, str3, str4, xMLString == null ? null : xMLString.toString());
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (xMLString.length == 0) {
            return;
        }
        try {
            DocumentHandler documentHandler = this.fDocumentHandler;
            if (documentHandler != null) {
                documentHandler.characters(xMLString.ch, xMLString.offset, xMLString.length);
            }
            ContentHandler contentHandler = this.fContentHandler;
            if (contentHandler != null) {
                contentHandler.characters(xMLString.ch, xMLString.offset, xMLString.length);
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
        try {
            LexicalHandler lexicalHandler = this.fLexicalHandler;
            if (lexicalHandler != null) {
                lexicalHandler.comment(xMLString.ch, 0, xMLString.length);
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
        this.fInDTD = true;
        try {
            LexicalHandler lexicalHandler = this.fLexicalHandler;
            if (lexicalHandler != null) {
                lexicalHandler.startDTD(str, str2, str3);
            }
            if (this.fDeclHandler != null) {
                this.fDeclaredAttrs = new SymbolHash(25);
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void elementDecl(String str, String str2, Augmentations augmentations) throws XNIException {
        try {
            DeclHandler declHandler = this.fDeclHandler;
            if (declHandler != null) {
                declHandler.elementDecl(str, str2);
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void endCDATA(Augmentations augmentations) throws XNIException {
        try {
            LexicalHandler lexicalHandler = this.fLexicalHandler;
            if (lexicalHandler != null) {
                lexicalHandler.endCDATA();
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void endDTD(Augmentations augmentations) throws XNIException {
        this.fInDTD = false;
        try {
            LexicalHandler lexicalHandler = this.fLexicalHandler;
            if (lexicalHandler != null) {
                lexicalHandler.endDTD();
            }
            SymbolHash symbolHash = this.fDeclaredAttrs;
            if (symbolHash != null) {
                symbolHash.clear();
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void endDocument(Augmentations augmentations) throws XNIException {
        try {
            DocumentHandler documentHandler = this.fDocumentHandler;
            if (documentHandler != null) {
                documentHandler.endDocument();
            }
            ContentHandler contentHandler = this.fContentHandler;
            if (contentHandler != null) {
                contentHandler.endDocument();
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(QName qName, Augmentations augmentations) throws XNIException {
        try {
            DocumentHandler documentHandler = this.fDocumentHandler;
            if (documentHandler != null) {
                documentHandler.endElement(qName.rawname);
            }
            if (this.fContentHandler != null) {
                this.fAugmentations = augmentations;
                this.fContentHandler.endElement(qName.uri != null ? qName.uri : "", this.fNamespaces ? qName.localpart : "", qName.rawname);
                if (this.fNamespaces) {
                    endNamespaceMapping();
                }
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void endExternalSubset(Augmentations augmentations) throws XNIException {
        endParameterEntity("[dtd]", augmentations);
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void endGeneralEntity(String str, Augmentations augmentations) throws XNIException {
        if (augmentations != null) {
            try {
                if (Boolean.TRUE.equals(augmentations.getItem(Constants.ENTITY_SKIPPED))) {
                    return;
                }
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        }
        LexicalHandler lexicalHandler = this.fLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endEntity(str);
        }
    }

    protected final void endNamespaceMapping() throws SAXException {
        int declaredPrefixCount = this.fNamespaceContext.getDeclaredPrefixCount();
        if (declaredPrefixCount > 0) {
            for (int i = 0; i < declaredPrefixCount; i++) {
                this.fContentHandler.endPrefixMapping(this.fNamespaceContext.getDeclaredPrefixAt(i));
            }
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void endParameterEntity(String str, Augmentations augmentations) throws XNIException {
        if (augmentations != null) {
            try {
                if (Boolean.TRUE.equals(augmentations.getItem(Constants.ENTITY_SKIPPED))) {
                    return;
                }
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        }
        LexicalHandler lexicalHandler = this.fLexicalHandler;
        if (lexicalHandler == null || !this.fLexicalHandlerParameterEntities) {
            return;
        }
        lexicalHandler.endEntity(str);
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void externalEntityDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        try {
            if (this.fDeclHandler != null) {
                this.fDeclHandler.externalEntityDecl(str, xMLResourceIdentifier.getPublicId(), this.fResolveDTDURIs ? xMLResourceIdentifier.getExpandedSystemId() : xMLResourceIdentifier.getLiteralSystemId());
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public AttributePSVI getAttributePSVI(int i) {
        return (AttributePSVI) this.fAttributesProxy.fAttributes.getAugmentations(i).getItem(Constants.ATTRIBUTE_PSVI);
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public AttributePSVI getAttributePSVIByName(String str, String str2) {
        return (AttributePSVI) this.fAttributesProxy.fAttributes.getAugmentations(str, str2).getItem(Constants.ATTRIBUTE_PSVI);
    }

    @Override // org.xml.sax.XMLReader
    public ContentHandler getContentHandler() {
        return this.fContentHandler;
    }

    @Override // org.xml.sax.XMLReader
    public DTDHandler getDTDHandler() {
        return this.fDTDHandler;
    }

    protected DeclHandler getDeclHandler() throws SAXNotRecognizedException, SAXNotSupportedException {
        return this.fDeclHandler;
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public ElementPSVI getElementPSVI() {
        Augmentations augmentations = this.fAugmentations;
        if (augmentations != null) {
            return (ElementPSVI) augmentations.getItem(Constants.ELEMENT_PSVI);
        }
        return null;
    }

    @Override // org.xml.sax.XMLReader
    public EntityResolver getEntityResolver() {
        EntityResolver entityResolver = null;
        try {
            XMLEntityResolver xMLEntityResolver = (XMLEntityResolver) this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
            if (xMLEntityResolver != null) {
                if (xMLEntityResolver instanceof EntityResolverWrapper) {
                    entityResolver = ((EntityResolverWrapper) xMLEntityResolver).getEntityResolver();
                } else if (xMLEntityResolver instanceof EntityResolver2Wrapper) {
                    entityResolver = ((EntityResolver2Wrapper) xMLEntityResolver).getEntityResolver();
                }
            }
        } catch (XMLConfigurationException unused) {
        }
        return entityResolver;
    }

    @Override // org.xml.sax.XMLReader
    public ErrorHandler getErrorHandler() {
        try {
            XMLErrorHandler xMLErrorHandler = (XMLErrorHandler) this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/error-handler");
            if (xMLErrorHandler == null || !(xMLErrorHandler instanceof ErrorHandlerWrapper)) {
                return null;
            }
            return ((ErrorHandlerWrapper) xMLErrorHandler).getErrorHandler();
        } catch (XMLConfigurationException unused) {
            return null;
        }
    }

    public boolean getFeature(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        try {
            if (str.startsWith(Constants.SAX_FEATURE_PREFIX)) {
                int length = str.length() - Constants.SAX_FEATURE_PREFIX.length();
                if (length == Constants.NAMESPACE_PREFIXES_FEATURE.length() && str.endsWith(Constants.NAMESPACE_PREFIXES_FEATURE)) {
                    return this.fNamespacePrefixes;
                }
                if (length == Constants.STRING_INTERNING_FEATURE.length() && str.endsWith(Constants.STRING_INTERNING_FEATURE)) {
                    return true;
                }
                if (length == Constants.IS_STANDALONE_FEATURE.length() && str.endsWith(Constants.IS_STANDALONE_FEATURE)) {
                    return this.fStandalone;
                }
                if (length == Constants.XML_11_FEATURE.length() && str.endsWith(Constants.XML_11_FEATURE)) {
                    return this.fConfiguration instanceof XML11Configurable;
                }
                if (length == Constants.LEXICAL_HANDLER_PARAMETER_ENTITIES_FEATURE.length() && str.endsWith(Constants.LEXICAL_HANDLER_PARAMETER_ENTITIES_FEATURE)) {
                    return this.fLexicalHandlerParameterEntities;
                }
                if (length == Constants.RESOLVE_DTD_URIS_FEATURE.length() && str.endsWith(Constants.RESOLVE_DTD_URIS_FEATURE)) {
                    return this.fResolveDTDURIs;
                }
                if (length == Constants.XMLNS_URIS_FEATURE.length() && str.endsWith(Constants.XMLNS_URIS_FEATURE)) {
                    return this.fXMLNSURIs;
                }
                if (length == Constants.UNICODE_NORMALIZATION_CHECKING_FEATURE.length() && str.endsWith(Constants.UNICODE_NORMALIZATION_CHECKING_FEATURE)) {
                    return false;
                }
                if (length == Constants.USE_ENTITY_RESOLVER2_FEATURE.length() && str.endsWith(Constants.USE_ENTITY_RESOLVER2_FEATURE)) {
                    return this.fUseEntityResolver2;
                }
                if ((length == Constants.USE_ATTRIBUTES2_FEATURE.length() && str.endsWith(Constants.USE_ATTRIBUTES2_FEATURE)) || (length == Constants.USE_LOCATOR2_FEATURE.length() && str.endsWith(Constants.USE_LOCATOR2_FEATURE))) {
                    return true;
                }
            }
            return this.fConfiguration.getFeature(str);
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() == 0) {
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-recognized", new Object[]{identifier}));
            }
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-supported", new Object[]{identifier}));
        }
    }

    protected LexicalHandler getLexicalHandler() throws SAXNotRecognizedException, SAXNotSupportedException {
        return this.fLexicalHandler;
    }

    public Object getProperty(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        try {
            if (str.startsWith(Constants.SAX_PROPERTY_PREFIX)) {
                int length = str.length() - Constants.SAX_PROPERTY_PREFIX.length();
                if (length == Constants.DOCUMENT_XML_VERSION_PROPERTY.length() && str.endsWith(Constants.DOCUMENT_XML_VERSION_PROPERTY)) {
                    return this.fVersion;
                }
                if (length == Constants.LEXICAL_HANDLER_PROPERTY.length() && str.endsWith(Constants.LEXICAL_HANDLER_PROPERTY)) {
                    return getLexicalHandler();
                }
                if (length == Constants.DECLARATION_HANDLER_PROPERTY.length() && str.endsWith(Constants.DECLARATION_HANDLER_PROPERTY)) {
                    return getDeclHandler();
                }
                if (length == Constants.DOM_NODE_PROPERTY.length() && str.endsWith(Constants.DOM_NODE_PROPERTY)) {
                    throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "dom-node-read-not-supported", null));
                }
            }
            return this.fConfiguration.getProperty(str);
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() == 0) {
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-recognized", new Object[]{identifier}));
            }
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-supported", new Object[]{identifier}));
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void ignorableWhitespace(XMLString xMLString, Augmentations augmentations) throws XNIException {
        try {
            DocumentHandler documentHandler = this.fDocumentHandler;
            if (documentHandler != null) {
                documentHandler.ignorableWhitespace(xMLString.ch, xMLString.offset, xMLString.length);
            }
            ContentHandler contentHandler = this.fContentHandler;
            if (contentHandler != null) {
                contentHandler.ignorableWhitespace(xMLString.ch, xMLString.offset, xMLString.length);
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void internalEntityDecl(String str, XMLString xMLString, XMLString xMLString2, Augmentations augmentations) throws XNIException {
        try {
            DeclHandler declHandler = this.fDeclHandler;
            if (declHandler != null) {
                declHandler.internalEntityDecl(str, xMLString.toString());
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void notationDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        try {
            if (this.fDTDHandler != null) {
                this.fDTDHandler.notationDecl(str, xMLResourceIdentifier.getPublicId(), this.fResolveDTDURIs ? xMLResourceIdentifier.getExpandedSystemId() : xMLResourceIdentifier.getLiteralSystemId());
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    public void parse(String str) throws SAXException, IOException {
        try {
            parse(new XMLInputSource(null, str, null));
        } catch (XMLParseException e) {
            Exception exception = e.getException();
            if (exception != null && !(exception instanceof CharConversionException)) {
                if (exception instanceof SAXException) {
                    throw ((SAXException) exception);
                }
                if (!(exception instanceof IOException)) {
                    throw new SAXException(exception);
                }
                throw ((IOException) exception);
            }
            Locator2Impl locator2Impl = new Locator2Impl();
            locator2Impl.setXMLVersion(this.fVersion);
            locator2Impl.setPublicId(e.getPublicId());
            locator2Impl.setSystemId(e.getExpandedSystemId());
            locator2Impl.setLineNumber(e.getLineNumber());
            locator2Impl.setColumnNumber(e.getColumnNumber());
            if (exception != null) {
                throw new SAXParseException(e.getMessage(), locator2Impl, exception);
            }
        } catch (XNIException e2) {
            Exception exception2 = e2.getException();
            if (exception2 == null) {
                throw new SAXException(e2.getMessage());
            }
            if (exception2 instanceof SAXException) {
                throw ((SAXException) exception2);
            }
            if (!(exception2 instanceof IOException)) {
                throw new SAXException(exception2);
            }
            throw ((IOException) exception2);
        }
    }

    public void parse(InputSource inputSource) throws SAXException, IOException {
        try {
            XMLInputSource xMLInputSource = new XMLInputSource(inputSource.getPublicId(), inputSource.getSystemId(), null);
            xMLInputSource.setByteStream(inputSource.getByteStream());
            xMLInputSource.setCharacterStream(inputSource.getCharacterStream());
            xMLInputSource.setEncoding(inputSource.getEncoding());
            parse(xMLInputSource);
        } catch (XMLParseException e) {
            Exception exception = e.getException();
            if (exception != null && !(exception instanceof CharConversionException)) {
                if (exception instanceof SAXException) {
                    throw ((SAXException) exception);
                }
                if (!(exception instanceof IOException)) {
                    throw new SAXException(exception);
                }
                throw ((IOException) exception);
            }
            Locator2Impl locator2Impl = new Locator2Impl();
            locator2Impl.setXMLVersion(this.fVersion);
            locator2Impl.setPublicId(e.getPublicId());
            locator2Impl.setSystemId(e.getExpandedSystemId());
            locator2Impl.setLineNumber(e.getLineNumber());
            locator2Impl.setColumnNumber(e.getColumnNumber());
            if (exception != null) {
                throw new SAXParseException(e.getMessage(), locator2Impl, exception);
            }
        } catch (XNIException e2) {
            Exception exception2 = e2.getException();
            if (exception2 == null) {
                throw new SAXException(e2.getMessage());
            }
            if (exception2 instanceof SAXException) {
                throw ((SAXException) exception2);
            }
            if (!(exception2 instanceof IOException)) {
                throw new SAXException(exception2);
            }
            throw ((IOException) exception2);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
        try {
            DocumentHandler documentHandler = this.fDocumentHandler;
            if (documentHandler != null) {
                documentHandler.processingInstruction(str, xMLString.toString());
            }
            ContentHandler contentHandler = this.fContentHandler;
            if (contentHandler != null) {
                contentHandler.processingInstruction(str, xMLString.toString());
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.parsers.XMLParser
    public void reset() throws XNIException {
        super.reset();
        this.fInDTD = false;
        this.fVersion = BuildConfig.VERSION_NAME;
        this.fStandalone = false;
        this.fNamespaces = this.fConfiguration.getFeature(NAMESPACES);
        this.fAugmentations = null;
        this.fDeclaredAttrs = null;
    }

    @Override // org.xml.sax.XMLReader
    public void setContentHandler(ContentHandler contentHandler) {
        this.fContentHandler = contentHandler;
    }

    @Override // org.xml.sax.Parser, org.xml.sax.XMLReader
    public void setDTDHandler(DTDHandler dTDHandler) {
        this.fDTDHandler = dTDHandler;
    }

    protected void setDeclHandler(DeclHandler declHandler) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (this.fParseInProgress) {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-parsing-supported", new Object[]{DECLARATION_HANDLER}));
        }
        this.fDeclHandler = declHandler;
    }

    @Override // org.xml.sax.Parser
    public void setDocumentHandler(DocumentHandler documentHandler) {
        this.fDocumentHandler = documentHandler;
    }

    @Override // org.xml.sax.Parser, org.xml.sax.XMLReader
    public void setEntityResolver(EntityResolver entityResolver) {
        XMLParserConfiguration xMLParserConfiguration;
        Object entityResolverWrapper;
        try {
            XMLEntityResolver xMLEntityResolver = (XMLEntityResolver) this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
            if (this.fUseEntityResolver2 && (entityResolver instanceof EntityResolver2)) {
                if (xMLEntityResolver instanceof EntityResolver2Wrapper) {
                    ((EntityResolver2Wrapper) xMLEntityResolver).setEntityResolver((EntityResolver2) entityResolver);
                    return;
                } else {
                    xMLParserConfiguration = this.fConfiguration;
                    entityResolverWrapper = new EntityResolver2Wrapper((EntityResolver2) entityResolver);
                }
            } else if (xMLEntityResolver instanceof EntityResolverWrapper) {
                ((EntityResolverWrapper) xMLEntityResolver).setEntityResolver(entityResolver);
                return;
            } else {
                xMLParserConfiguration = this.fConfiguration;
                entityResolverWrapper = new EntityResolverWrapper(entityResolver);
            }
            xMLParserConfiguration.setProperty("http://apache.org/xml/properties/internal/entity-resolver", entityResolverWrapper);
        } catch (XMLConfigurationException unused) {
        }
    }

    @Override // org.xml.sax.Parser, org.xml.sax.XMLReader
    public void setErrorHandler(ErrorHandler errorHandler) {
        try {
            XMLErrorHandler xMLErrorHandler = (XMLErrorHandler) this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/error-handler");
            if (xMLErrorHandler instanceof ErrorHandlerWrapper) {
                ((ErrorHandlerWrapper) xMLErrorHandler).setErrorHandler(errorHandler);
            } else {
                this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/error-handler", new ErrorHandlerWrapper(errorHandler));
            }
        } catch (XMLConfigurationException unused) {
        }
    }

    public void setFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
        try {
            if (str.startsWith(Constants.SAX_FEATURE_PREFIX)) {
                int length = str.length() - Constants.SAX_FEATURE_PREFIX.length();
                if (length == "namespaces".length() && str.endsWith("namespaces")) {
                    this.fConfiguration.setFeature(str, z);
                    this.fNamespaces = z;
                    return;
                }
                if (length == Constants.NAMESPACE_PREFIXES_FEATURE.length() && str.endsWith(Constants.NAMESPACE_PREFIXES_FEATURE)) {
                    this.fNamespacePrefixes = z;
                    return;
                }
                if (length == Constants.STRING_INTERNING_FEATURE.length() && str.endsWith(Constants.STRING_INTERNING_FEATURE)) {
                    if (!z) {
                        throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "false-not-supported", new Object[]{str}));
                    }
                    return;
                }
                if (length == Constants.LEXICAL_HANDLER_PARAMETER_ENTITIES_FEATURE.length() && str.endsWith(Constants.LEXICAL_HANDLER_PARAMETER_ENTITIES_FEATURE)) {
                    this.fLexicalHandlerParameterEntities = z;
                    return;
                }
                if (length == Constants.RESOLVE_DTD_URIS_FEATURE.length() && str.endsWith(Constants.RESOLVE_DTD_URIS_FEATURE)) {
                    this.fResolveDTDURIs = z;
                    return;
                }
                if (length == Constants.UNICODE_NORMALIZATION_CHECKING_FEATURE.length() && str.endsWith(Constants.UNICODE_NORMALIZATION_CHECKING_FEATURE)) {
                    if (z) {
                        throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "true-not-supported", new Object[]{str}));
                    }
                    return;
                }
                if (length == Constants.XMLNS_URIS_FEATURE.length() && str.endsWith(Constants.XMLNS_URIS_FEATURE)) {
                    this.fXMLNSURIs = z;
                    return;
                }
                if (length == Constants.USE_ENTITY_RESOLVER2_FEATURE.length() && str.endsWith(Constants.USE_ENTITY_RESOLVER2_FEATURE)) {
                    if (z != this.fUseEntityResolver2) {
                        this.fUseEntityResolver2 = z;
                        setEntityResolver(getEntityResolver());
                        return;
                    }
                    return;
                }
                if ((length == Constants.IS_STANDALONE_FEATURE.length() && str.endsWith(Constants.IS_STANDALONE_FEATURE)) || ((length == Constants.USE_ATTRIBUTES2_FEATURE.length() && str.endsWith(Constants.USE_ATTRIBUTES2_FEATURE)) || ((length == Constants.USE_LOCATOR2_FEATURE.length() && str.endsWith(Constants.USE_LOCATOR2_FEATURE)) || (length == Constants.XML_11_FEATURE.length() && str.endsWith(Constants.XML_11_FEATURE))))) {
                    throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-read-only", new Object[]{str}));
                }
            }
            this.fConfiguration.setFeature(str, z);
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() != 0) {
                throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-supported", new Object[]{identifier}));
            }
            throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-recognized", new Object[]{identifier}));
        }
    }

    protected void setLexicalHandler(LexicalHandler lexicalHandler) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (this.fParseInProgress) {
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-parsing-supported", new Object[]{LEXICAL_HANDLER}));
        }
        this.fLexicalHandler = lexicalHandler;
    }

    @Override // org.xml.sax.Parser
    public void setLocale(Locale locale) throws SAXException {
        this.fConfiguration.setLocale(locale);
    }

    /* JADX WARN: Code duplicated, block: B:38:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:40:0x00c1  */
    public void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
        String identifier;
        try {
            if (str.startsWith(Constants.SAX_PROPERTY_PREFIX)) {
                int length = str.length() - Constants.SAX_PROPERTY_PREFIX.length();
                if (length == Constants.LEXICAL_HANDLER_PROPERTY.length() && str.endsWith(Constants.LEXICAL_HANDLER_PROPERTY)) {
                    try {
                        setLexicalHandler((LexicalHandler) obj);
                        return;
                    } catch (ClassCastException unused) {
                        throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "incompatible-class", new Object[]{str, "org.xml.sax.ext.LexicalHandler"}));
                    }
                } else if (length == Constants.DECLARATION_HANDLER_PROPERTY.length() && str.endsWith(Constants.DECLARATION_HANDLER_PROPERTY)) {
                    try {
                        setDeclHandler((DeclHandler) obj);
                        return;
                    } catch (ClassCastException unused2) {
                        throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "incompatible-class", new Object[]{str, "org.xml.sax.ext.DeclHandler"}));
                    }
                } else if ((length == Constants.DOM_NODE_PROPERTY.length() && str.endsWith(Constants.DOM_NODE_PROPERTY)) || (length == Constants.DOCUMENT_XML_VERSION_PROPERTY.length() && str.endsWith(Constants.DOCUMENT_XML_VERSION_PROPERTY))) {
                    throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-read-only", new Object[]{str}));
                }
                identifier = e.getIdentifier();
                if (e.getType() == 0) {
                    throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-supported", new Object[]{identifier}));
                }
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-recognized", new Object[]{identifier}));
            }
            this.fConfiguration.setProperty(str, obj);
        } catch (XMLConfigurationException e) {
            identifier = e.getIdentifier();
            if (e.getType() == 0) {
                throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-supported", new Object[]{identifier}));
            }
            throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-recognized", new Object[]{identifier}));
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void startCDATA(Augmentations augmentations) throws XNIException {
        try {
            LexicalHandler lexicalHandler = this.fLexicalHandler;
            if (lexicalHandler != null) {
                lexicalHandler.startCDATA();
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void startDocument(XMLLocator xMLLocator, String str, NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
        this.fNamespaceContext = namespaceContext;
        try {
            DocumentHandler documentHandler = this.fDocumentHandler;
            if (documentHandler != null) {
                if (xMLLocator != null) {
                    documentHandler.setDocumentLocator(new LocatorProxy(xMLLocator));
                }
                DocumentHandler documentHandler2 = this.fDocumentHandler;
                if (documentHandler2 != null) {
                    documentHandler2.startDocument();
                }
            }
            ContentHandler contentHandler = this.fContentHandler;
            if (contentHandler != null) {
                if (xMLLocator != null) {
                    contentHandler.setDocumentLocator(new LocatorProxy(xMLLocator));
                }
                ContentHandler contentHandler2 = this.fContentHandler;
                if (contentHandler2 != null) {
                    contentHandler2.startDocument();
                }
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        try {
            if (this.fDocumentHandler != null) {
                this.fAttributesProxy.setAttributes(xMLAttributes);
                this.fDocumentHandler.startElement(qName.rawname, this.fAttributesProxy);
            }
            if (this.fContentHandler != null) {
                if (this.fNamespaces) {
                    startNamespaceMapping();
                    int length = xMLAttributes.getLength();
                    if (!this.fNamespacePrefixes) {
                        for (int i = length - 1; i >= 0; i--) {
                            xMLAttributes.getName(i, this.fQName);
                            if (this.fQName.prefix == XMLSymbols.PREFIX_XMLNS || this.fQName.rawname == XMLSymbols.PREFIX_XMLNS) {
                                xMLAttributes.removeAttributeAt(i);
                            }
                        }
                    } else if (!this.fXMLNSURIs) {
                        for (int i2 = length - 1; i2 >= 0; i2--) {
                            xMLAttributes.getName(i2, this.fQName);
                            if (this.fQName.prefix == XMLSymbols.PREFIX_XMLNS || this.fQName.rawname == XMLSymbols.PREFIX_XMLNS) {
                                this.fQName.prefix = "";
                                this.fQName.uri = "";
                                this.fQName.localpart = "";
                                xMLAttributes.setName(i2, this.fQName);
                            }
                        }
                    }
                }
                this.fAugmentations = augmentations;
                String str = qName.uri != null ? qName.uri : "";
                String str2 = this.fNamespaces ? qName.localpart : "";
                this.fAttributesProxy.setAttributes(xMLAttributes);
                this.fContentHandler.startElement(str, str2, qName.rawname, this.fAttributesProxy);
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void startExternalSubset(XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        startParameterEntity("[dtd]", null, null, augmentations);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001c A[Catch: SAXException -> 0x0020, TRY_LEAVE, TryCatch #0 {SAXException -> 0x0020, blocks: (B:3:0x0002, B:5:0x0010, B:7:0x0014, B:8:0x0018, B:10:0x001c), top: B:16:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:8:0x0018 A[Catch: SAXException -> 0x0020, TryCatch #0 {SAXException -> 0x0020, blocks: (B:3:0x0002, B:5:0x0010, B:7:0x0014, B:8:0x0018, B:10:0x001c), top: B:16:0x0002 }] */
    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void startGeneralEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        LexicalHandler lexicalHandler;
        if (augmentations != null) {
            try {
                if (Boolean.TRUE.equals(augmentations.getItem(Constants.ENTITY_SKIPPED))) {
                    ContentHandler contentHandler = this.fContentHandler;
                    if (contentHandler != null) {
                        contentHandler.skippedEntity(str);
                    }
                } else {
                    lexicalHandler = this.fLexicalHandler;
                    if (lexicalHandler != null) {
                        lexicalHandler.startEntity(str);
                    }
                }
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        } else {
            lexicalHandler = this.fLexicalHandler;
            if (lexicalHandler != null) {
                lexicalHandler.startEntity(str);
            }
        }
    }

    protected final void startNamespaceMapping() throws SAXException {
        int declaredPrefixCount = this.fNamespaceContext.getDeclaredPrefixCount();
        if (declaredPrefixCount > 0) {
            for (int i = 0; i < declaredPrefixCount; i++) {
                String declaredPrefixAt = this.fNamespaceContext.getDeclaredPrefixAt(i);
                String uri = this.fNamespaceContext.getURI(declaredPrefixAt);
                ContentHandler contentHandler = this.fContentHandler;
                if (uri == null) {
                    uri = "";
                }
                contentHandler.startPrefixMapping(declaredPrefixAt, uri);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0018 A[Catch: SAXException -> 0x0024, TryCatch #0 {SAXException -> 0x0024, blocks: (B:3:0x0002, B:5:0x0010, B:7:0x0014, B:8:0x0018, B:10:0x001c, B:12:0x0020), top: B:18:0x0002 }] */
    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void startParameterEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        LexicalHandler lexicalHandler;
        if (augmentations != null) {
            try {
                if (Boolean.TRUE.equals(augmentations.getItem(Constants.ENTITY_SKIPPED))) {
                    ContentHandler contentHandler = this.fContentHandler;
                    if (contentHandler != null) {
                        contentHandler.skippedEntity(str);
                    }
                } else {
                    lexicalHandler = this.fLexicalHandler;
                    if (lexicalHandler != null && this.fLexicalHandlerParameterEntities) {
                        lexicalHandler.startEntity(str);
                    }
                }
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        } else {
            lexicalHandler = this.fLexicalHandler;
            if (lexicalHandler != null) {
                lexicalHandler.startEntity(str);
            }
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void unparsedEntityDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        try {
            if (this.fDTDHandler != null) {
                this.fDTDHandler.unparsedEntityDecl(str, xMLResourceIdentifier.getPublicId(), this.fResolveDTDURIs ? xMLResourceIdentifier.getExpandedSystemId() : xMLResourceIdentifier.getLiteralSystemId(), str2);
            }
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void xmlDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
        this.fVersion = str;
        this.fStandalone = "yes".equals(str3);
    }
}
