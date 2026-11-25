package org.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.TypeInfoProvider;
import javax.xml.validation.ValidatorHandler;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.validation.EntityState;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.util.AttributesProxy;
import org.apache.xerces.util.SAXLocatorWrapper;
import org.apache.xerces.util.SAXMessageFormatter;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.URI;
import org.apache.xerces.util.XMLAttributesImpl;
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
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLParseException;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.ItemPSVI;
import org.apache.xerces.xs.PSVIProvider;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.Attributes2;
import org.xml.sax.ext.EntityResolver2;
import org.xml.sax.ext.LexicalHandler;

/* JADX INFO: loaded from: classes13.dex */
final class ValidatorHandlerImpl extends ValidatorHandler implements DTDHandler, EntityState, PSVIProvider, ValidatorHelper, XMLDocumentHandler {
    private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    private static final String LEXICAL_HANDLER = "http://xml.org/sax/properties/lexical-handler";
    private static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
    private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
    private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
    private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
    private static final String STRINGS_INTERNED = "http://apache.org/xml/features/internal/strings-interned";
    private static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
    private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    private final AttributesProxy fAttrAdapter;
    private final QName fAttributeQName;
    private final XMLAttributesImpl fAttributes;
    private final XMLSchemaValidatorComponentManager fComponentManager;
    private ContentHandler fContentHandler;
    private final QName fElementQName;
    private final XMLErrorReporter fErrorReporter;
    private final NamespaceContext fNamespaceContext;
    private boolean fNeedPushNSContext;
    private final ResolutionForwarder fResolutionForwarder;
    private final SAXLocatorWrapper fSAXLocatorWrapper;
    private final XMLSchemaValidator fSchemaValidator;
    private boolean fStringsInternalized;
    private final SymbolTable fSymbolTable;
    private final XMLString fTempString;
    private final XMLSchemaTypeInfoProvider fTypeInfoProvider;
    private HashMap fUnparsedEntities;
    private final ValidationManager fValidationManager;

    static final class ResolutionForwarder implements EntityResolver2 {
        private static final String XML_TYPE = "http://www.w3.org/TR/REC-xml";
        protected LSResourceResolver fEntityResolver;

        public ResolutionForwarder() {
        }

        public ResolutionForwarder(LSResourceResolver lSResourceResolver) {
            setEntityResolver(lSResourceResolver);
        }

        private String resolveSystemId(String str, String str2) {
            try {
                return XMLEntityManager.expandSystemId(str, str2, false);
            } catch (URI.MalformedURIException unused) {
                return str;
            }
        }

        public LSResourceResolver getEntityResolver() {
            return this.fEntityResolver;
        }

        @Override // org.xml.sax.ext.EntityResolver2
        public InputSource getExternalSubset(String str, String str2) throws SAXException, IOException {
            return null;
        }

        @Override // org.xml.sax.EntityResolver
        public InputSource resolveEntity(String str, String str2) throws SAXException, IOException {
            return resolveEntity(null, str, null, str2);
        }

        @Override // org.xml.sax.ext.EntityResolver2
        public InputSource resolveEntity(String str, String str2, String str3, String str4) throws SAXException, IOException {
            LSInput lSInputResolveResource;
            LSResourceResolver lSResourceResolver = this.fEntityResolver;
            if (lSResourceResolver == null || (lSInputResolveResource = lSResourceResolver.resolveResource("http://www.w3.org/TR/REC-xml", null, str2, str4, str3)) == null) {
                return null;
            }
            String publicId = lSInputResolveResource.getPublicId();
            String systemId = lSInputResolveResource.getSystemId();
            String baseURI = lSInputResolveResource.getBaseURI();
            Reader characterStream = lSInputResolveResource.getCharacterStream();
            InputStream byteStream = lSInputResolveResource.getByteStream();
            String stringData = lSInputResolveResource.getStringData();
            String encoding = lSInputResolveResource.getEncoding();
            InputSource inputSource = new InputSource();
            inputSource.setPublicId(publicId);
            if (baseURI != null) {
                systemId = resolveSystemId(systemId, baseURI);
            }
            inputSource.setSystemId(systemId);
            if (characterStream != null) {
                inputSource.setCharacterStream(characterStream);
            } else if (byteStream != null) {
                inputSource.setByteStream(byteStream);
            } else if (stringData != null && stringData.length() != 0) {
                inputSource.setCharacterStream(new StringReader(stringData));
            }
            inputSource.setEncoding(encoding);
            return inputSource;
        }

        public void setEntityResolver(LSResourceResolver lSResourceResolver) {
            this.fEntityResolver = lSResourceResolver;
        }
    }

    private class XMLSchemaTypeInfoProvider extends TypeInfoProvider {
        private XMLAttributes fAttributes;
        private Augmentations fElementAugs;
        private boolean fInEndElement;
        private boolean fInStartElement;

        private XMLSchemaTypeInfoProvider() {
            this.fInStartElement = false;
            this.fInEndElement = false;
        }

        private void checkStateAttribute() {
            if (!this.fInStartElement) {
                throw new IllegalStateException(JAXPValidationMessageFormatter.formatMessage(ValidatorHandlerImpl.this.fComponentManager.getLocale(), "TypeInfoProviderIllegalStateAttribute", null));
            }
        }

        private void checkStateElement() {
            if (!this.fInStartElement && !this.fInEndElement) {
                throw new IllegalStateException(JAXPValidationMessageFormatter.formatMessage(ValidatorHandlerImpl.this.fComponentManager.getLocale(), "TypeInfoProviderIllegalStateElement", null));
            }
        }

        private TypeInfo getAttributeType(int i) {
            checkStateAttribute();
            if (i < 0 || this.fAttributes.getLength() <= i) {
                throw new IndexOutOfBoundsException(Integer.toString(i));
            }
            Augmentations augmentations = this.fAttributes.getAugmentations(i);
            if (augmentations == null) {
                return null;
            }
            return getTypeInfoFromPSVI((AttributePSVI) augmentations.getItem(Constants.ATTRIBUTE_PSVI));
        }

        private TypeInfo getTypeInfoFromPSVI(ItemPSVI itemPSVI) {
            XSSimpleTypeDefinition memberTypeDefinition;
            if (itemPSVI == null) {
                return null;
            }
            if (itemPSVI.getValidity() == 2 && (memberTypeDefinition = itemPSVI.getMemberTypeDefinition()) != null) {
                if (memberTypeDefinition instanceof TypeInfo) {
                    return (TypeInfo) memberTypeDefinition;
                }
                return null;
            }
            XSTypeDefinition typeDefinition = itemPSVI.getTypeDefinition();
            if (typeDefinition == null || !(typeDefinition instanceof TypeInfo)) {
                return null;
            }
            return (TypeInfo) typeDefinition;
        }

        void beginEndElement(Augmentations augmentations) {
            this.fInEndElement = true;
            this.fElementAugs = augmentations;
        }

        void beginStartElement(Augmentations augmentations, XMLAttributes xMLAttributes) {
            this.fInStartElement = true;
            this.fElementAugs = augmentations;
            this.fAttributes = xMLAttributes;
        }

        void finishEndElement() {
            this.fInEndElement = false;
            this.fElementAugs = null;
        }

        void finishStartElement() {
            this.fInStartElement = false;
            this.fElementAugs = null;
            this.fAttributes = null;
        }

        AttributePSVI getAttributePSVI(int i) {
            Augmentations augmentations;
            XMLAttributes xMLAttributes = this.fAttributes;
            if (xMLAttributes == null || (augmentations = xMLAttributes.getAugmentations(i)) == null) {
                return null;
            }
            return (AttributePSVI) augmentations.getItem(Constants.ATTRIBUTE_PSVI);
        }

        AttributePSVI getAttributePSVIByName(String str, String str2) {
            Augmentations augmentations;
            XMLAttributes xMLAttributes = this.fAttributes;
            if (xMLAttributes == null || (augmentations = xMLAttributes.getAugmentations(str, str2)) == null) {
                return null;
            }
            return (AttributePSVI) augmentations.getItem(Constants.ATTRIBUTE_PSVI);
        }

        @Override // javax.xml.validation.TypeInfoProvider
        public TypeInfo getAttributeTypeInfo(int i) {
            checkStateAttribute();
            return getAttributeType(i);
        }

        public TypeInfo getAttributeTypeInfo(String str) {
            checkStateAttribute();
            return getAttributeTypeInfo(this.fAttributes.getIndex(str));
        }

        public TypeInfo getAttributeTypeInfo(String str, String str2) {
            checkStateAttribute();
            return getAttributeTypeInfo(this.fAttributes.getIndex(str, str2));
        }

        ElementPSVI getElementPSVI() {
            Augmentations augmentations = this.fElementAugs;
            if (augmentations != null) {
                return (ElementPSVI) augmentations.getItem(Constants.ELEMENT_PSVI);
            }
            return null;
        }

        @Override // javax.xml.validation.TypeInfoProvider
        public TypeInfo getElementTypeInfo() {
            checkStateElement();
            Augmentations augmentations = this.fElementAugs;
            if (augmentations == null) {
                return null;
            }
            return getTypeInfoFromPSVI((ElementPSVI) augmentations.getItem(Constants.ELEMENT_PSVI));
        }

        @Override // javax.xml.validation.TypeInfoProvider
        public boolean isIdAttribute(int i) {
            checkStateAttribute();
            XSSimpleType xSSimpleType = (XSSimpleType) getAttributeType(i);
            if (xSSimpleType == null) {
                return false;
            }
            return xSSimpleType.isIDType();
        }

        @Override // javax.xml.validation.TypeInfoProvider
        public boolean isSpecified(int i) {
            checkStateAttribute();
            return this.fAttributes.isSpecified(i);
        }
    }

    public ValidatorHandlerImpl(XMLSchemaValidatorComponentManager xMLSchemaValidatorComponentManager) {
        this.fSAXLocatorWrapper = new SAXLocatorWrapper();
        this.fNeedPushNSContext = true;
        this.fUnparsedEntities = null;
        this.fStringsInternalized = false;
        this.fElementQName = new QName();
        this.fAttributeQName = new QName();
        XMLAttributesImpl xMLAttributesImpl = new XMLAttributesImpl();
        this.fAttributes = xMLAttributesImpl;
        this.fAttrAdapter = new AttributesProxy(xMLAttributesImpl);
        this.fTempString = new XMLString();
        this.fContentHandler = null;
        this.fTypeInfoProvider = new XMLSchemaTypeInfoProvider();
        this.fResolutionForwarder = new ResolutionForwarder(null);
        this.fComponentManager = xMLSchemaValidatorComponentManager;
        this.fErrorReporter = (XMLErrorReporter) xMLSchemaValidatorComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
        this.fNamespaceContext = (NamespaceContext) xMLSchemaValidatorComponentManager.getProperty(NAMESPACE_CONTEXT);
        this.fSchemaValidator = (XMLSchemaValidator) xMLSchemaValidatorComponentManager.getProperty(SCHEMA_VALIDATOR);
        this.fSymbolTable = (SymbolTable) xMLSchemaValidatorComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
        this.fValidationManager = (ValidationManager) xMLSchemaValidatorComponentManager.getProperty(VALIDATION_MANAGER);
    }

    public ValidatorHandlerImpl(XSGrammarPoolContainer xSGrammarPoolContainer) {
        this(new XMLSchemaValidatorComponentManager(xSGrammarPoolContainer));
        this.fComponentManager.addRecognizedFeatures(new String[]{NAMESPACE_PREFIXES});
        this.fComponentManager.setFeature(NAMESPACE_PREFIXES, false);
        setErrorHandler(null);
        setResourceResolver(null);
    }

    private void fillQName(QName qName, String str, String str2, String str3) {
        String strAddSymbol;
        String strAddSymbol2 = null;
        if (this.fStringsInternalized) {
            if (str != null && str.length() == 0) {
                str = null;
            }
            if (str2 == null) {
                str2 = XMLSymbols.EMPTY_STRING;
            }
            if (str3 == null) {
                str3 = XMLSymbols.EMPTY_STRING;
            }
            strAddSymbol2 = str;
            strAddSymbol = str2;
        } else {
            if (str != null && str.length() > 0) {
                strAddSymbol2 = this.fSymbolTable.addSymbol(str);
            }
            strAddSymbol = str2 != null ? this.fSymbolTable.addSymbol(str2) : XMLSymbols.EMPTY_STRING;
            str3 = str3 != null ? this.fSymbolTable.addSymbol(str3) : XMLSymbols.EMPTY_STRING;
        }
        String strAddSymbol3 = XMLSymbols.EMPTY_STRING;
        int iIndexOf = str3.indexOf(58);
        if (iIndexOf != -1) {
            strAddSymbol3 = this.fSymbolTable.addSymbol(str3.substring(0, iIndexOf));
        }
        qName.setValues(strAddSymbol3, strAddSymbol, str3, strAddSymbol2);
    }

    private void fillXMLAttribute(Attributes attributes, int i) {
        fillQName(this.fAttributeQName, attributes.getURI(i), attributes.getLocalName(i), attributes.getQName(i));
        String type = attributes.getType(i);
        XMLAttributesImpl xMLAttributesImpl = this.fAttributes;
        QName qName = this.fAttributeQName;
        if (type == null) {
            type = XMLSymbols.fCDATASymbol;
        }
        xMLAttributesImpl.addAttributeNS(qName, type, attributes.getValue(i));
    }

    private void fillXMLAttributes(Attributes attributes) {
        this.fAttributes.removeAllAttributes();
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            fillXMLAttribute(attributes, i);
            this.fAttributes.setSpecified(i, true);
        }
    }

    private void fillXMLAttributes2(Attributes2 attributes2) {
        this.fAttributes.removeAllAttributes();
        int length = attributes2.getLength();
        for (int i = 0; i < length; i++) {
            fillXMLAttribute(attributes2, i);
            this.fAttributes.setSpecified(i, attributes2.isSpecified(i));
            if (attributes2.isDeclared(i)) {
                this.fAttributes.getAugmentations(i).putItem(Constants.ATTRIBUTE_DECLARED, Boolean.TRUE);
            }
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fContentHandler == null || xMLString.length == 0) {
            return;
        }
        try {
            this.fContentHandler.characters(xMLString.ch, xMLString.offset, xMLString.length);
        } catch (SAXException e) {
            throw new XNIException(e);
        }
    }

    @Override // javax.xml.validation.ValidatorHandler, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        try {
            this.fTempString.setValues(cArr, i, i2);
            this.fSchemaValidator.characters(this.fTempString, null);
        } catch (XMLParseException e) {
            throw Util.toSAXParseException(e);
        } catch (XNIException e2) {
            throw Util.toSAXException(e2);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void emptyElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        startElement(qName, xMLAttributes, augmentations);
        endElement(qName, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endCDATA(Augmentations augmentations) throws XNIException {
    }

    @Override // javax.xml.validation.ValidatorHandler, org.xml.sax.ContentHandler
    public void endDocument() throws SAXException {
        this.fSAXLocatorWrapper.setLocator(null);
        try {
            this.fSchemaValidator.endDocument(null);
        } catch (XMLParseException e) {
            throw Util.toSAXParseException(e);
        } catch (XNIException e2) {
            throw Util.toSAXException(e2);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endDocument(Augmentations augmentations) throws XNIException {
        ContentHandler contentHandler = this.fContentHandler;
        if (contentHandler != null) {
            try {
                contentHandler.endDocument();
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        }
    }

    @Override // javax.xml.validation.ValidatorHandler, org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException {
        fillQName(this.fElementQName, str, str2, str3);
        try {
            try {
                this.fSchemaValidator.endElement(this.fElementQName, null);
                this.fNamespaceContext.popContext();
            } catch (XMLParseException e) {
                throw Util.toSAXParseException(e);
            } catch (XNIException e2) {
                throw Util.toSAXException(e2);
            }
        } catch (Throwable th) {
            this.fNamespaceContext.popContext();
            throw th;
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(QName qName, Augmentations augmentations) throws XNIException {
        try {
            if (this.fContentHandler != null) {
                try {
                    this.fTypeInfoProvider.beginEndElement(augmentations);
                    this.fContentHandler.endElement(qName.uri != null ? qName.uri : XMLSymbols.EMPTY_STRING, qName.localpart, qName.rawname);
                    this.fTypeInfoProvider.finishEndElement();
                } catch (SAXException e) {
                    throw new XNIException(e);
                }
            }
        } catch (Throwable th) {
            this.fTypeInfoProvider.finishEndElement();
            throw th;
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endGeneralEntity(String str, Augmentations augmentations) throws XNIException {
    }

    @Override // javax.xml.validation.ValidatorHandler, org.xml.sax.ContentHandler
    public void endPrefixMapping(String str) throws SAXException {
        ContentHandler contentHandler = this.fContentHandler;
        if (contentHandler != null) {
            contentHandler.endPrefixMapping(str);
        }
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public AttributePSVI getAttributePSVI(int i) {
        return this.fTypeInfoProvider.getAttributePSVI(i);
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public AttributePSVI getAttributePSVIByName(String str, String str2) {
        return this.fTypeInfoProvider.getAttributePSVIByName(str, str2);
    }

    @Override // javax.xml.validation.ValidatorHandler
    public ContentHandler getContentHandler() {
        return this.fContentHandler;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public XMLDocumentSource getDocumentSource() {
        return this.fSchemaValidator;
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public ElementPSVI getElementPSVI() {
        return this.fTypeInfoProvider.getElementPSVI();
    }

    @Override // javax.xml.validation.ValidatorHandler
    public ErrorHandler getErrorHandler() {
        return this.fComponentManager.getErrorHandler();
    }

    @Override // javax.xml.validation.ValidatorHandler
    public boolean getFeature(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "FeatureNameNull", null));
        }
        if (STRINGS_INTERNED.equals(str)) {
            return this.fStringsInternalized;
        }
        try {
            return this.fComponentManager.getFeature(str);
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() == 0) {
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-recognized", new Object[]{identifier}));
            }
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-supported", new Object[]{identifier}));
        }
    }

    @Override // javax.xml.validation.ValidatorHandler
    public Object getProperty(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "ProperyNameNull", null));
        }
        try {
            return this.fComponentManager.getProperty(str);
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() == 0) {
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-recognized", new Object[]{identifier}));
            }
            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-supported", new Object[]{identifier}));
        }
    }

    @Override // javax.xml.validation.ValidatorHandler
    public LSResourceResolver getResourceResolver() {
        return this.fComponentManager.getResourceResolver();
    }

    @Override // javax.xml.validation.ValidatorHandler
    public TypeInfoProvider getTypeInfoProvider() {
        return this.fTypeInfoProvider;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void ignorableWhitespace(XMLString xMLString, Augmentations augmentations) throws XNIException {
        ContentHandler contentHandler = this.fContentHandler;
        if (contentHandler != null) {
            try {
                contentHandler.ignorableWhitespace(xMLString.ch, xMLString.offset, xMLString.length);
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        }
    }

    @Override // javax.xml.validation.ValidatorHandler, org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        try {
            this.fTempString.setValues(cArr, i, i2);
            this.fSchemaValidator.ignorableWhitespace(this.fTempString, null);
        } catch (XMLParseException e) {
            throw Util.toSAXParseException(e);
        } catch (XNIException e2) {
            throw Util.toSAXException(e2);
        }
    }

    @Override // org.apache.xerces.impl.validation.EntityState
    public boolean isEntityDeclared(String str) {
        return false;
    }

    @Override // org.apache.xerces.impl.validation.EntityState
    public boolean isEntityUnparsed(String str) {
        HashMap map = this.fUnparsedEntities;
        if (map != null) {
            return map.containsKey(str);
        }
        return false;
    }

    @Override // org.xml.sax.DTDHandler
    public void notationDecl(String str, String str2, String str3) throws SAXException {
    }

    @Override // javax.xml.validation.ValidatorHandler, org.xml.sax.ContentHandler
    public void processingInstruction(String str, String str2) throws SAXException {
        ContentHandler contentHandler = this.fContentHandler;
        if (contentHandler != null) {
            contentHandler.processingInstruction(str, str2);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
        ContentHandler contentHandler = this.fContentHandler;
        if (contentHandler != null) {
            try {
                contentHandler.processingInstruction(str, xMLString.toString());
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        }
    }

    @Override // javax.xml.validation.ValidatorHandler
    public void setContentHandler(ContentHandler contentHandler) {
        this.fContentHandler = contentHandler;
    }

    @Override // javax.xml.validation.ValidatorHandler, org.xml.sax.ContentHandler
    public void setDocumentLocator(Locator locator) {
        this.fSAXLocatorWrapper.setLocator(locator);
        ContentHandler contentHandler = this.fContentHandler;
        if (contentHandler != null) {
            contentHandler.setDocumentLocator(locator);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void setDocumentSource(XMLDocumentSource xMLDocumentSource) {
    }

    @Override // javax.xml.validation.ValidatorHandler
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.fComponentManager.setErrorHandler(errorHandler);
    }

    @Override // javax.xml.validation.ValidatorHandler
    public void setFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "FeatureNameNull", null));
        }
        if (STRINGS_INTERNED.equals(str)) {
            this.fStringsInternalized = z;
            return;
        }
        try {
            this.fComponentManager.setFeature(str, z);
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() != 0) {
                throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-supported", new Object[]{identifier}));
            }
            throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-recognized", new Object[]{identifier}));
        }
    }

    @Override // javax.xml.validation.ValidatorHandler
    public void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (str == null) {
            throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "ProperyNameNull", null));
        }
        try {
            this.fComponentManager.setProperty(str, obj);
        } catch (XMLConfigurationException e) {
            String identifier = e.getIdentifier();
            if (e.getType() != 0) {
                throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-supported", new Object[]{identifier}));
            }
            throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-recognized", new Object[]{identifier}));
        }
    }

    @Override // javax.xml.validation.ValidatorHandler
    public void setResourceResolver(LSResourceResolver lSResourceResolver) {
        this.fComponentManager.setResourceResolver(lSResourceResolver);
    }

    @Override // javax.xml.validation.ValidatorHandler, org.xml.sax.ContentHandler
    public void skippedEntity(String str) throws SAXException {
        ContentHandler contentHandler = this.fContentHandler;
        if (contentHandler != null) {
            contentHandler.skippedEntity(str);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startCDATA(Augmentations augmentations) throws XNIException {
    }

    @Override // javax.xml.validation.ValidatorHandler, org.xml.sax.ContentHandler
    public void startDocument() throws SAXException {
        this.fComponentManager.reset();
        this.fSchemaValidator.setDocumentHandler(this);
        this.fValidationManager.setEntityState(this);
        this.fTypeInfoProvider.finishStartElement();
        this.fNeedPushNSContext = true;
        HashMap map = this.fUnparsedEntities;
        if (map != null && !map.isEmpty()) {
            this.fUnparsedEntities.clear();
        }
        this.fErrorReporter.setDocumentLocator(this.fSAXLocatorWrapper);
        try {
            XMLSchemaValidator xMLSchemaValidator = this.fSchemaValidator;
            SAXLocatorWrapper sAXLocatorWrapper = this.fSAXLocatorWrapper;
            xMLSchemaValidator.startDocument(sAXLocatorWrapper, sAXLocatorWrapper.getEncoding(), this.fNamespaceContext, null);
        } catch (XMLParseException e) {
            throw Util.toSAXParseException(e);
        } catch (XNIException e2) {
            throw Util.toSAXException(e2);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startDocument(XMLLocator xMLLocator, String str, NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
        ContentHandler contentHandler = this.fContentHandler;
        if (contentHandler != null) {
            try {
                contentHandler.startDocument();
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        }
    }

    @Override // javax.xml.validation.ValidatorHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        if (this.fNeedPushNSContext) {
            this.fNamespaceContext.pushContext();
        }
        this.fNeedPushNSContext = true;
        fillQName(this.fElementQName, str, str2, str3);
        if (attributes instanceof Attributes2) {
            fillXMLAttributes2((Attributes2) attributes);
        } else {
            fillXMLAttributes(attributes);
        }
        try {
            this.fSchemaValidator.startElement(this.fElementQName, this.fAttributes, null);
        } catch (XMLParseException e) {
            throw Util.toSAXParseException(e);
        } catch (XNIException e2) {
            throw Util.toSAXException(e2);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        try {
            if (this.fContentHandler != null) {
                try {
                    this.fTypeInfoProvider.beginStartElement(augmentations, xMLAttributes);
                    this.fContentHandler.startElement(qName.uri != null ? qName.uri : XMLSymbols.EMPTY_STRING, qName.localpart, qName.rawname, this.fAttrAdapter);
                    this.fTypeInfoProvider.finishStartElement();
                } catch (SAXException e) {
                    throw new XNIException(e);
                }
            }
        } catch (Throwable th) {
            this.fTypeInfoProvider.finishStartElement();
            throw th;
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startGeneralEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
    }

    @Override // javax.xml.validation.ValidatorHandler, org.xml.sax.ContentHandler
    public void startPrefixMapping(String str, String str2) throws SAXException {
        String strAddSymbol;
        String strAddSymbol2 = null;
        if (this.fStringsInternalized) {
            strAddSymbol = str != null ? str : XMLSymbols.EMPTY_STRING;
            if (str2 != null && str2.length() > 0) {
                strAddSymbol2 = str2;
            }
        } else {
            strAddSymbol = str != null ? this.fSymbolTable.addSymbol(str) : XMLSymbols.EMPTY_STRING;
            if (str2 != null && str2.length() > 0) {
                strAddSymbol2 = this.fSymbolTable.addSymbol(str2);
            }
        }
        if (this.fNeedPushNSContext) {
            this.fNeedPushNSContext = false;
            this.fNamespaceContext.pushContext();
        }
        this.fNamespaceContext.declarePrefix(strAddSymbol, strAddSymbol2);
        ContentHandler contentHandler = this.fContentHandler;
        if (contentHandler != null) {
            contentHandler.startPrefixMapping(str, str2);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void textDecl(String str, String str2, Augmentations augmentations) throws XNIException {
    }

    @Override // org.xml.sax.DTDHandler
    public void unparsedEntityDecl(String str, String str2, String str3, String str4) throws SAXException {
        if (this.fUnparsedEntities == null) {
            this.fUnparsedEntities = new HashMap();
        }
        this.fUnparsedEntities.put(str, str);
    }

    @Override // org.apache.xerces.jaxp.validation.ValidatorHelper
    public void validate(Source source, Result result) throws Throwable {
        LexicalHandler lexicalHandler;
        XMLReader xMLReader;
        Object property;
        if (!(result instanceof SAXResult) && result != null) {
            throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceResultMismatch", new Object[]{source.getClass().getName(), result.getClass().getName()}));
        }
        SAXSource sAXSource = (SAXSource) source;
        SAXResult sAXResult = (SAXResult) result;
        if (result != null) {
            ContentHandler handler = sAXResult.getHandler();
            lexicalHandler = sAXResult.getLexicalHandler();
            if (lexicalHandler == null && (handler instanceof LexicalHandler)) {
                lexicalHandler = (LexicalHandler) handler;
            }
            setContentHandler(handler);
        } else {
            lexicalHandler = null;
        }
        try {
            xMLReader = sAXSource.getXMLReader();
            if (xMLReader == null) {
                try {
                    SAXParserFactory sAXParserFactoryNewInstance = SAXParserFactory.newInstance();
                    sAXParserFactoryNewInstance.setNamespaceAware(true);
                    try {
                        xMLReader = sAXParserFactoryNewInstance.newSAXParser().getXMLReader();
                        if ((xMLReader instanceof SAXParser) && (property = this.fComponentManager.getProperty(SECURITY_MANAGER)) != null) {
                            try {
                                xMLReader.setProperty(SECURITY_MANAGER, property);
                            } catch (SAXException unused) {
                            }
                        }
                    } catch (Exception e) {
                        throw new FactoryConfigurationError(e);
                    }
                } catch (Throwable th) {
                    th = th;
                    setContentHandler(null);
                    if (xMLReader != null) {
                        try {
                            xMLReader.setContentHandler(null);
                            xMLReader.setDTDHandler(null);
                            xMLReader.setErrorHandler(null);
                            xMLReader.setEntityResolver(null);
                            this.fResolutionForwarder.setEntityResolver(null);
                            xMLReader.setProperty(LEXICAL_HANDLER, null);
                        } catch (Exception unused2) {
                        }
                    }
                    throw th;
                }
            }
            try {
                this.fStringsInternalized = xMLReader.getFeature(STRING_INTERNING);
            } catch (SAXException unused3) {
                this.fStringsInternalized = false;
            }
            ErrorHandler errorHandler = this.fComponentManager.getErrorHandler();
            if (errorHandler == null) {
                errorHandler = DraconianErrorHandler.getInstance();
            }
            xMLReader.setErrorHandler(errorHandler);
            xMLReader.setEntityResolver(this.fResolutionForwarder);
            this.fResolutionForwarder.setEntityResolver(this.fComponentManager.getResourceResolver());
            xMLReader.setContentHandler(this);
            xMLReader.setDTDHandler(this);
            try {
                xMLReader.setProperty(LEXICAL_HANDLER, lexicalHandler);
            } catch (SAXException unused4) {
            }
            xMLReader.parse(sAXSource.getInputSource());
            setContentHandler(null);
            if (xMLReader != null) {
                try {
                    xMLReader.setContentHandler(null);
                    xMLReader.setDTDHandler(null);
                    xMLReader.setErrorHandler(null);
                    xMLReader.setEntityResolver(null);
                    this.fResolutionForwarder.setEntityResolver(null);
                    xMLReader.setProperty(LEXICAL_HANDLER, null);
                } catch (Exception unused5) {
                }
            }
        } catch (Throwable th2) {
            th = th2;
            xMLReader = null;
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void xmlDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
    }
}
