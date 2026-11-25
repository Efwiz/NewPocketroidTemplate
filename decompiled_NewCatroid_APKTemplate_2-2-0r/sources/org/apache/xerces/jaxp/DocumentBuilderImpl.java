package org.apache.xerces.jaxp;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.validation.Schema;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.jaxp.validation.XSGrammarPoolContainer;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/* JADX INFO: loaded from: classes13.dex */
public class DocumentBuilderImpl extends DocumentBuilder implements JAXPConstants {
    private static final String CREATE_CDATA_NODES_FEATURE = "http://apache.org/xml/features/create-cdata-nodes";
    private static final String CREATE_ENTITY_REF_NODES_FEATURE = "http://apache.org/xml/features/dom/create-entity-ref-nodes";
    private static final String INCLUDE_COMMENTS_FEATURE = "http://apache.org/xml/features/include-comments";
    private static final String INCLUDE_IGNORABLE_WHITESPACE = "http://apache.org/xml/features/dom/include-ignorable-whitespace";
    private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
    private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
    private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
    private static final String XINCLUDE_FEATURE = "http://apache.org/xml/features/xinclude";
    private static final String XMLSCHEMA_VALIDATION_FEATURE = "http://apache.org/xml/features/validation/schema";
    private final DOMParser domParser;
    private final EntityResolver fInitEntityResolver;
    private final ErrorHandler fInitErrorHandler;
    private final ValidationManager fSchemaValidationManager;
    private final XMLComponent fSchemaValidator;
    private final XMLComponentManager fSchemaValidatorComponentManager;
    private final UnparsedEntityHandler fUnparsedEntityHandler;
    private final Schema grammar;

    DocumentBuilderImpl(DocumentBuilderFactoryImpl documentBuilderFactoryImpl, Hashtable hashtable, Hashtable hashtable2) throws SAXNotRecognizedException, SAXNotSupportedException {
        this(documentBuilderFactoryImpl, hashtable, hashtable2, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    DocumentBuilderImpl(DocumentBuilderFactoryImpl documentBuilderFactoryImpl, Hashtable hashtable, Hashtable hashtable2, boolean z) throws SAXNotRecognizedException, SAXNotSupportedException {
        DOMParser dOMParser = new DOMParser();
        this.domParser = dOMParser;
        if (documentBuilderFactoryImpl.isValidating()) {
            DefaultValidationErrorHandler defaultValidationErrorHandler = new DefaultValidationErrorHandler();
            this.fInitErrorHandler = defaultValidationErrorHandler;
            setErrorHandler(defaultValidationErrorHandler);
        } else {
            this.fInitErrorHandler = dOMParser.getErrorHandler();
        }
        dOMParser.setFeature(VALIDATION_FEATURE, documentBuilderFactoryImpl.isValidating());
        dOMParser.setFeature(NAMESPACES_FEATURE, documentBuilderFactoryImpl.isNamespaceAware());
        dOMParser.setFeature(INCLUDE_IGNORABLE_WHITESPACE, !documentBuilderFactoryImpl.isIgnoringElementContentWhitespace());
        dOMParser.setFeature(CREATE_ENTITY_REF_NODES_FEATURE, !documentBuilderFactoryImpl.isExpandEntityReferences());
        dOMParser.setFeature(INCLUDE_COMMENTS_FEATURE, !documentBuilderFactoryImpl.isIgnoringComments());
        dOMParser.setFeature(CREATE_CDATA_NODES_FEATURE, !documentBuilderFactoryImpl.isCoalescing());
        if (documentBuilderFactoryImpl.isXIncludeAware()) {
            dOMParser.setFeature(XINCLUDE_FEATURE, true);
        }
        if (z) {
            dOMParser.setProperty(SECURITY_MANAGER, new SecurityManager());
        }
        Schema schema = documentBuilderFactoryImpl.getSchema();
        this.grammar = schema;
        XMLComponent xMLSchemaValidator = null;
        if (schema != 0) {
            XMLParserConfiguration xMLParserConfiguration = dOMParser.getXMLParserConfiguration();
            if (schema instanceof XSGrammarPoolContainer) {
                xMLSchemaValidator = new XMLSchemaValidator();
                ValidationManager validationManager = new ValidationManager();
                this.fSchemaValidationManager = validationManager;
                UnparsedEntityHandler unparsedEntityHandler = new UnparsedEntityHandler(validationManager);
                this.fUnparsedEntityHandler = unparsedEntityHandler;
                xMLParserConfiguration.setDTDHandler(unparsedEntityHandler);
                unparsedEntityHandler.setDTDHandler(dOMParser);
                dOMParser.setDTDSource(unparsedEntityHandler);
                this.fSchemaValidatorComponentManager = new SchemaValidatorConfiguration(xMLParserConfiguration, (XSGrammarPoolContainer) schema, validationManager);
            } else {
                JAXPValidatorComponent jAXPValidatorComponent = new JAXPValidatorComponent(schema.newValidatorHandler());
                this.fSchemaValidationManager = null;
                this.fUnparsedEntityHandler = null;
                this.fSchemaValidatorComponentManager = xMLParserConfiguration;
                xMLSchemaValidator = jAXPValidatorComponent;
            }
            xMLParserConfiguration.addRecognizedFeatures(xMLSchemaValidator.getRecognizedFeatures());
            xMLParserConfiguration.addRecognizedProperties(xMLSchemaValidator.getRecognizedProperties());
            xMLParserConfiguration.setDocumentHandler((XMLDocumentHandler) xMLSchemaValidator);
            XMLDocumentSource xMLDocumentSource = (XMLDocumentSource) xMLSchemaValidator;
            xMLDocumentSource.setDocumentHandler(dOMParser);
            dOMParser.setDocumentSource(xMLDocumentSource);
        } else {
            this.fSchemaValidationManager = null;
            this.fUnparsedEntityHandler = null;
            this.fSchemaValidatorComponentManager = null;
        }
        this.fSchemaValidator = xMLSchemaValidator;
        setFeatures(hashtable2);
        setDocumentBuilderFactoryAttributes(hashtable);
        this.fInitEntityResolver = dOMParser.getEntityResolver();
    }

    private void resetSchemaValidator() throws SAXException {
        try {
            this.fSchemaValidator.reset(this.fSchemaValidatorComponentManager);
        } catch (XMLConfigurationException e) {
            throw new SAXException(e);
        }
    }

    private void setDocumentBuilderFactoryAttributes(Hashtable hashtable) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (hashtable == null) {
            return;
        }
        for (Map.Entry entry : hashtable.entrySet()) {
            String str = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Boolean) {
                this.domParser.setFeature(str, ((Boolean) value).booleanValue());
            } else if (!JAXPConstants.JAXP_SCHEMA_LANGUAGE.equals(str)) {
                if (JAXPConstants.JAXP_SCHEMA_SOURCE.equals(str)) {
                    if (isValidating()) {
                        String str2 = (String) hashtable.get(JAXPConstants.JAXP_SCHEMA_LANGUAGE);
                        if (str2 == null || !"http://www.w3.org/2001/XMLSchema".equals(str2)) {
                            throw new IllegalArgumentException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "jaxp-order-not-supported", new Object[]{JAXPConstants.JAXP_SCHEMA_LANGUAGE, JAXPConstants.JAXP_SCHEMA_SOURCE}));
                        }
                    } else {
                        continue;
                    }
                }
                this.domParser.setProperty(str, value);
            } else if ("http://www.w3.org/2001/XMLSchema".equals(value) && isValidating()) {
                this.domParser.setFeature(XMLSCHEMA_VALIDATION_FEATURE, true);
                this.domParser.setProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE, "http://www.w3.org/2001/XMLSchema");
            }
        }
    }

    private void setFeatures(Hashtable hashtable) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (hashtable != null) {
            for (Map.Entry entry : hashtable.entrySet()) {
                this.domParser.setFeature((String) entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
            }
        }
    }

    @Override // javax.xml.parsers.DocumentBuilder
    public DOMImplementation getDOMImplementation() {
        return DOMImplementationImpl.getDOMImplementation();
    }

    DOMParser getDOMParser() {
        return this.domParser;
    }

    @Override // javax.xml.parsers.DocumentBuilder
    public Schema getSchema() {
        return this.grammar;
    }

    @Override // javax.xml.parsers.DocumentBuilder
    public boolean isNamespaceAware() {
        try {
            return this.domParser.getFeature(NAMESPACES_FEATURE);
        } catch (SAXException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override // javax.xml.parsers.DocumentBuilder
    public boolean isValidating() {
        try {
            return this.domParser.getFeature(VALIDATION_FEATURE);
        } catch (SAXException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override // javax.xml.parsers.DocumentBuilder
    public boolean isXIncludeAware() {
        try {
            return this.domParser.getFeature(XINCLUDE_FEATURE);
        } catch (SAXException unused) {
            return false;
        }
    }

    @Override // javax.xml.parsers.DocumentBuilder
    public Document newDocument() {
        return new DocumentImpl();
    }

    @Override // javax.xml.parsers.DocumentBuilder
    public Document parse(InputSource inputSource) throws SAXException, IOException {
        if (inputSource == null) {
            throw new IllegalArgumentException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "jaxp-null-input-source", null));
        }
        if (this.fSchemaValidator != null) {
            ValidationManager validationManager = this.fSchemaValidationManager;
            if (validationManager != null) {
                validationManager.reset();
                this.fUnparsedEntityHandler.reset();
            }
            resetSchemaValidator();
        }
        this.domParser.parse(inputSource);
        Document document = this.domParser.getDocument();
        this.domParser.dropDocumentReferences();
        return document;
    }

    @Override // javax.xml.parsers.DocumentBuilder
    public void reset() {
        ErrorHandler errorHandler = this.domParser.getErrorHandler();
        ErrorHandler errorHandler2 = this.fInitErrorHandler;
        if (errorHandler != errorHandler2) {
            this.domParser.setErrorHandler(errorHandler2);
        }
        EntityResolver entityResolver = this.domParser.getEntityResolver();
        EntityResolver entityResolver2 = this.fInitEntityResolver;
        if (entityResolver != entityResolver2) {
            this.domParser.setEntityResolver(entityResolver2);
        }
    }

    @Override // javax.xml.parsers.DocumentBuilder
    public void setEntityResolver(EntityResolver entityResolver) {
        this.domParser.setEntityResolver(entityResolver);
    }

    @Override // javax.xml.parsers.DocumentBuilder
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.domParser.setErrorHandler(errorHandler);
    }
}
