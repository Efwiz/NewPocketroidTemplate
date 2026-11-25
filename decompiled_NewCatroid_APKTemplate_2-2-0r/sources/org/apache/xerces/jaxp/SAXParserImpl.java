package org.apache.xerces.jaxp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.validation.Schema;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.jaxp.validation.XSGrammarPoolContainer;
import org.apache.xerces.util.SAXMessageFormatter;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.PSVIProvider;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/* JADX INFO: loaded from: classes13.dex */
public class SAXParserImpl extends SAXParser implements JAXPConstants, PSVIProvider {
    private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
    private static final String NAMESPACE_PREFIXES_FEATURE = "http://xml.org/sax/features/namespace-prefixes";
    private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
    private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
    private static final String XINCLUDE_FEATURE = "http://apache.org/xml/features/xinclude";
    private static final String XMLSCHEMA_VALIDATION_FEATURE = "http://apache.org/xml/features/validation/schema";
    private final EntityResolver fInitEntityResolver;
    private final ErrorHandler fInitErrorHandler;
    private final ValidationManager fSchemaValidationManager;
    private final XMLComponent fSchemaValidator;
    private final XMLComponentManager fSchemaValidatorComponentManager;
    private final UnparsedEntityHandler fUnparsedEntityHandler;
    private final Schema grammar;
    private String schemaLanguage;
    private final JAXPSAXParser xmlReader;

    public static class JAXPSAXParser extends org.apache.xerces.parsers.SAXParser {
        private final HashMap fInitFeatures;
        private final HashMap fInitProperties;
        private final SAXParserImpl fSAXParser;

        public JAXPSAXParser() {
            this(null);
        }

        JAXPSAXParser(SAXParserImpl sAXParserImpl) {
            this.fInitFeatures = new HashMap();
            this.fInitProperties = new HashMap();
            this.fSAXParser = sAXParserImpl;
        }

        private void resetSchemaValidator() throws SAXException {
            try {
                this.fSAXParser.fSchemaValidator.reset(this.fSAXParser.fSchemaValidatorComponentManager);
            } catch (XMLConfigurationException e) {
                throw new SAXException(e);
            }
        }

        private void setSchemaValidatorFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
            try {
                this.fSAXParser.fSchemaValidator.setFeature(str, z);
            } catch (XMLConfigurationException e) {
                String identifier = e.getIdentifier();
                if (e.getType() != 0) {
                    throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-supported", new Object[]{identifier}));
                }
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "feature-not-recognized", new Object[]{identifier}));
            }
        }

        private void setSchemaValidatorProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
            try {
                this.fSAXParser.fSchemaValidator.setProperty(str, obj);
            } catch (XMLConfigurationException e) {
                String identifier = e.getIdentifier();
                if (e.getType() != 0) {
                    throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-supported", new Object[]{identifier}));
                }
                throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "property-not-recognized", new Object[]{identifier}));
            }
        }

        @Override // org.apache.xerces.parsers.AbstractSAXParser, org.xml.sax.XMLReader
        public synchronized boolean getFeature(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
            try {
                if (str == null) {
                    throw new NullPointerException();
                }
                if (!str.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
                    return super.getFeature(str);
                }
                try {
                    return super.getProperty(SAXParserImpl.SECURITY_MANAGER) != null;
                } catch (SAXException unused) {
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }

        boolean getFeature0(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
            return super.getFeature(str);
        }

        @Override // org.apache.xerces.parsers.AbstractSAXParser, org.xml.sax.XMLReader
        public synchronized Object getProperty(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
            try {
                if (str == null) {
                    throw new NullPointerException();
                }
                if (this.fSAXParser == null || !JAXPConstants.JAXP_SCHEMA_LANGUAGE.equals(str)) {
                    return super.getProperty(str);
                }
                return this.fSAXParser.schemaLanguage;
            } catch (Throwable th) {
                throw th;
            }
        }

        Object getProperty0(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
            return super.getProperty(str);
        }

        XMLParserConfiguration getXMLParserConfiguration() {
            return this.fConfiguration;
        }

        @Override // org.apache.xerces.parsers.AbstractSAXParser, org.xml.sax.Parser, org.xml.sax.XMLReader
        public void parse(String str) throws SAXException, IOException {
            SAXParserImpl sAXParserImpl = this.fSAXParser;
            if (sAXParserImpl != null && sAXParserImpl.fSchemaValidator != null) {
                if (this.fSAXParser.fSchemaValidationManager != null) {
                    this.fSAXParser.fSchemaValidationManager.reset();
                    this.fSAXParser.fUnparsedEntityHandler.reset();
                }
                resetSchemaValidator();
            }
            super.parse(str);
        }

        @Override // org.apache.xerces.parsers.AbstractSAXParser, org.xml.sax.Parser, org.xml.sax.XMLReader
        public void parse(InputSource inputSource) throws SAXException, IOException {
            SAXParserImpl sAXParserImpl = this.fSAXParser;
            if (sAXParserImpl != null && sAXParserImpl.fSchemaValidator != null) {
                if (this.fSAXParser.fSchemaValidationManager != null) {
                    this.fSAXParser.fSchemaValidationManager.reset();
                    this.fSAXParser.fUnparsedEntityHandler.reset();
                }
                resetSchemaValidator();
            }
            super.parse(inputSource);
        }

        synchronized void restoreInitState() throws SAXNotRecognizedException, SAXNotSupportedException {
            if (!this.fInitFeatures.isEmpty()) {
                for (Map.Entry entry : this.fInitFeatures.entrySet()) {
                    super.setFeature((String) entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
                }
                this.fInitFeatures.clear();
            }
            if (!this.fInitProperties.isEmpty()) {
                for (Map.Entry entry2 : this.fInitProperties.entrySet()) {
                    super.setProperty((String) entry2.getKey(), entry2.getValue());
                }
                this.fInitProperties.clear();
            }
        }

        @Override // org.apache.xerces.parsers.AbstractSAXParser, org.xml.sax.XMLReader
        public synchronized void setFeature(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
            SecurityManager securityManager;
            if (str == null) {
                throw new NullPointerException();
            }
            if (!str.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
                if (!this.fInitFeatures.containsKey(str)) {
                    this.fInitFeatures.put(str, super.getFeature(str) ? Boolean.TRUE : Boolean.FALSE);
                }
                SAXParserImpl sAXParserImpl = this.fSAXParser;
                if (sAXParserImpl != null && sAXParserImpl.fSchemaValidator != null) {
                    setSchemaValidatorFeature(str, z);
                }
                super.setFeature(str, z);
                return;
            }
            if (z) {
                try {
                    securityManager = new SecurityManager();
                } catch (SAXNotRecognizedException e) {
                    if (z) {
                        throw e;
                    }
                } catch (SAXNotSupportedException e2) {
                    if (z) {
                        throw e2;
                    }
                }
            } else {
                securityManager = null;
            }
            setProperty(SAXParserImpl.SECURITY_MANAGER, securityManager);
        }

        void setFeature0(String str, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
            super.setFeature(str, z);
        }

        @Override // org.apache.xerces.parsers.AbstractSAXParser, org.xml.sax.XMLReader
        public synchronized void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
            try {
                if (str == null) {
                    throw new NullPointerException();
                }
                if (this.fSAXParser != null) {
                    if (JAXPConstants.JAXP_SCHEMA_LANGUAGE.equals(str)) {
                        if (this.fSAXParser.grammar != null) {
                            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "schema-already-specified", new Object[]{str}));
                        }
                        if ("http://www.w3.org/2001/XMLSchema".equals(obj)) {
                            if (this.fSAXParser.isValidating()) {
                                this.fSAXParser.schemaLanguage = "http://www.w3.org/2001/XMLSchema";
                                setFeature(SAXParserImpl.XMLSCHEMA_VALIDATION_FEATURE, true);
                                if (!this.fInitProperties.containsKey(JAXPConstants.JAXP_SCHEMA_LANGUAGE)) {
                                    this.fInitProperties.put(JAXPConstants.JAXP_SCHEMA_LANGUAGE, super.getProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE));
                                }
                                super.setProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE, "http://www.w3.org/2001/XMLSchema");
                            }
                        } else {
                            if (obj != null) {
                                throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "schema-not-supported", null));
                            }
                            this.fSAXParser.schemaLanguage = null;
                            setFeature(SAXParserImpl.XMLSCHEMA_VALIDATION_FEATURE, false);
                        }
                        return;
                    }
                    if (JAXPConstants.JAXP_SCHEMA_SOURCE.equals(str)) {
                        if (this.fSAXParser.grammar != null) {
                            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "schema-already-specified", new Object[]{str}));
                        }
                        String str2 = (String) getProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE);
                        if (str2 == null || !"http://www.w3.org/2001/XMLSchema".equals(str2)) {
                            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fConfiguration.getLocale(), "jaxp-order-not-supported", new Object[]{JAXPConstants.JAXP_SCHEMA_LANGUAGE, JAXPConstants.JAXP_SCHEMA_SOURCE}));
                        }
                        if (!this.fInitProperties.containsKey(JAXPConstants.JAXP_SCHEMA_SOURCE)) {
                            this.fInitProperties.put(JAXPConstants.JAXP_SCHEMA_SOURCE, super.getProperty(JAXPConstants.JAXP_SCHEMA_SOURCE));
                        }
                        super.setProperty(str, obj);
                        return;
                    }
                }
                if (!this.fInitProperties.containsKey(str)) {
                    this.fInitProperties.put(str, super.getProperty(str));
                }
                SAXParserImpl sAXParserImpl = this.fSAXParser;
                if (sAXParserImpl != null && sAXParserImpl.fSchemaValidator != null) {
                    setSchemaValidatorProperty(str, obj);
                }
                super.setProperty(str, obj);
            } catch (Throwable th) {
                throw th;
            }
        }

        void setProperty0(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
            super.setProperty(str, obj);
        }
    }

    SAXParserImpl(SAXParserFactoryImpl sAXParserFactoryImpl, Hashtable hashtable) throws SAXException {
        this(sAXParserFactoryImpl, hashtable, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    SAXParserImpl(SAXParserFactoryImpl sAXParserFactoryImpl, Hashtable hashtable, boolean z) throws SAXException {
        XMLComponent jAXPValidatorComponent;
        this.schemaLanguage = null;
        JAXPSAXParser jAXPSAXParser = new JAXPSAXParser(this);
        this.xmlReader = jAXPSAXParser;
        jAXPSAXParser.setFeature0(NAMESPACES_FEATURE, sAXParserFactoryImpl.isNamespaceAware());
        jAXPSAXParser.setFeature0(NAMESPACE_PREFIXES_FEATURE, !sAXParserFactoryImpl.isNamespaceAware());
        if (sAXParserFactoryImpl.isXIncludeAware()) {
            jAXPSAXParser.setFeature0(XINCLUDE_FEATURE, true);
        }
        if (z) {
            jAXPSAXParser.setProperty0(SECURITY_MANAGER, new SecurityManager());
        }
        setFeatures(hashtable);
        if (sAXParserFactoryImpl.isValidating()) {
            ErrorHandler defaultValidationErrorHandler = new DefaultValidationErrorHandler();
            this.fInitErrorHandler = defaultValidationErrorHandler;
            jAXPSAXParser.setErrorHandler(defaultValidationErrorHandler);
        } else {
            this.fInitErrorHandler = jAXPSAXParser.getErrorHandler();
        }
        jAXPSAXParser.setFeature0(VALIDATION_FEATURE, sAXParserFactoryImpl.isValidating());
        Schema schema = sAXParserFactoryImpl.getSchema();
        this.grammar = schema;
        if (schema != 0) {
            XMLParserConfiguration xMLParserConfiguration = jAXPSAXParser.getXMLParserConfiguration();
            if (schema instanceof XSGrammarPoolContainer) {
                jAXPValidatorComponent = new XMLSchemaValidator();
                ValidationManager validationManager = new ValidationManager();
                this.fSchemaValidationManager = validationManager;
                UnparsedEntityHandler unparsedEntityHandler = new UnparsedEntityHandler(validationManager);
                this.fUnparsedEntityHandler = unparsedEntityHandler;
                xMLParserConfiguration.setDTDHandler(unparsedEntityHandler);
                unparsedEntityHandler.setDTDHandler(jAXPSAXParser);
                jAXPSAXParser.setDTDSource(unparsedEntityHandler);
                this.fSchemaValidatorComponentManager = new SchemaValidatorConfiguration(xMLParserConfiguration, (XSGrammarPoolContainer) schema, validationManager);
            } else {
                jAXPValidatorComponent = new JAXPValidatorComponent(schema.newValidatorHandler());
                this.fSchemaValidationManager = null;
                this.fUnparsedEntityHandler = null;
                this.fSchemaValidatorComponentManager = xMLParserConfiguration;
            }
            xMLParserConfiguration.addRecognizedFeatures(jAXPValidatorComponent.getRecognizedFeatures());
            xMLParserConfiguration.addRecognizedProperties(jAXPValidatorComponent.getRecognizedProperties());
            xMLParserConfiguration.setDocumentHandler((XMLDocumentHandler) jAXPValidatorComponent);
            XMLDocumentSource xMLDocumentSource = (XMLDocumentSource) jAXPValidatorComponent;
            xMLDocumentSource.setDocumentHandler(jAXPSAXParser);
            jAXPSAXParser.setDocumentSource(xMLDocumentSource);
            this.fSchemaValidator = jAXPValidatorComponent;
        } else {
            this.fSchemaValidationManager = null;
            this.fUnparsedEntityHandler = null;
            this.fSchemaValidatorComponentManager = null;
            this.fSchemaValidator = null;
        }
        this.fInitEntityResolver = jAXPSAXParser.getEntityResolver();
    }

    private void setFeatures(Hashtable hashtable) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (hashtable != null) {
            for (Map.Entry entry : hashtable.entrySet()) {
                this.xmlReader.setFeature0((String) entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
            }
        }
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public AttributePSVI getAttributePSVI(int i) {
        return this.xmlReader.getAttributePSVI(i);
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public AttributePSVI getAttributePSVIByName(String str, String str2) {
        return this.xmlReader.getAttributePSVIByName(str, str2);
    }

    @Override // org.apache.xerces.xs.PSVIProvider
    public ElementPSVI getElementPSVI() {
        return this.xmlReader.getElementPSVI();
    }

    @Override // javax.xml.parsers.SAXParser
    public Parser getParser() throws SAXException {
        return this.xmlReader;
    }

    @Override // javax.xml.parsers.SAXParser
    public Object getProperty(String str) throws SAXNotRecognizedException, SAXNotSupportedException {
        return this.xmlReader.getProperty(str);
    }

    @Override // javax.xml.parsers.SAXParser
    public Schema getSchema() {
        return this.grammar;
    }

    @Override // javax.xml.parsers.SAXParser
    public XMLReader getXMLReader() {
        return this.xmlReader;
    }

    @Override // javax.xml.parsers.SAXParser
    public boolean isNamespaceAware() {
        try {
            return this.xmlReader.getFeature(NAMESPACES_FEATURE);
        } catch (SAXException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override // javax.xml.parsers.SAXParser
    public boolean isValidating() {
        try {
            return this.xmlReader.getFeature(VALIDATION_FEATURE);
        } catch (SAXException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override // javax.xml.parsers.SAXParser
    public boolean isXIncludeAware() {
        try {
            return this.xmlReader.getFeature(XINCLUDE_FEATURE);
        } catch (SAXException unused) {
            return false;
        }
    }

    @Override // javax.xml.parsers.SAXParser
    public void parse(InputSource inputSource, HandlerBase handlerBase) throws SAXException, IOException {
        if (inputSource == null) {
            throw new IllegalArgumentException();
        }
        if (handlerBase != null) {
            this.xmlReader.setDocumentHandler(handlerBase);
            this.xmlReader.setEntityResolver(handlerBase);
            this.xmlReader.setErrorHandler(handlerBase);
            this.xmlReader.setDTDHandler(handlerBase);
            this.xmlReader.setContentHandler(null);
        }
        this.xmlReader.parse(inputSource);
    }

    @Override // javax.xml.parsers.SAXParser
    public void parse(InputSource inputSource, DefaultHandler defaultHandler) throws SAXException, IOException {
        if (inputSource == null) {
            throw new IllegalArgumentException();
        }
        if (defaultHandler != null) {
            this.xmlReader.setContentHandler(defaultHandler);
            this.xmlReader.setEntityResolver(defaultHandler);
            this.xmlReader.setErrorHandler(defaultHandler);
            this.xmlReader.setDTDHandler(defaultHandler);
            this.xmlReader.setDocumentHandler(null);
        }
        this.xmlReader.parse(inputSource);
    }

    @Override // javax.xml.parsers.SAXParser
    public void reset() {
        try {
            this.xmlReader.restoreInitState();
        } catch (SAXException unused) {
        }
        this.xmlReader.setContentHandler(null);
        this.xmlReader.setDTDHandler(null);
        ErrorHandler errorHandler = this.xmlReader.getErrorHandler();
        ErrorHandler errorHandler2 = this.fInitErrorHandler;
        if (errorHandler != errorHandler2) {
            this.xmlReader.setErrorHandler(errorHandler2);
        }
        EntityResolver entityResolver = this.xmlReader.getEntityResolver();
        EntityResolver entityResolver2 = this.fInitEntityResolver;
        if (entityResolver != entityResolver2) {
            this.xmlReader.setEntityResolver(entityResolver2);
        }
    }

    @Override // javax.xml.parsers.SAXParser
    public void setProperty(String str, Object obj) throws SAXNotRecognizedException, SAXNotSupportedException {
        this.xmlReader.setProperty(str, obj);
    }
}
