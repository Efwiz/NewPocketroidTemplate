package org.apache.xerces.parsers;

import java.io.IOException;
import java.util.Locale;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.XMLDTDScannerImpl;
import org.apache.xerces.impl.XMLDocumentScannerImpl;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.XMLNamespaceBinder;
import org.apache.xerces.impl.dtd.XMLDTDProcessor;
import org.apache.xerces.impl.dtd.XMLDTDValidator;
import org.apache.xerces.impl.dv.DTDDVFactory;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XMLDTDContentModelHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDContentModelSource;
import org.apache.xerces.xni.parser.XMLDTDScanner;
import org.apache.xerces.xni.parser.XMLDocumentScanner;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLPullParserConfiguration;

/* JADX INFO: loaded from: classes13.dex */
public class DTDConfiguration extends BasicParserConfiguration implements XMLPullParserConfiguration {
    protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
    protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
    protected static final String DATATYPE_VALIDATOR_FACTORY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
    protected static final String DOCUMENT_SCANNER = "http://apache.org/xml/properties/internal/document-scanner";
    protected static final String DTD_PROCESSOR = "http://apache.org/xml/properties/internal/dtd-processor";
    protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
    protected static final String DTD_VALIDATOR = "http://apache.org/xml/properties/internal/validator/dtd";
    protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
    protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
    protected static final String LOCALE = "http://apache.org/xml/properties/locale";
    protected static final String NAMESPACE_BINDER = "http://apache.org/xml/properties/internal/namespace-binder";
    protected static final String NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
    protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
    protected static final boolean PRINT_EXCEPTION_STACK_TRACE = false;
    protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
    protected static final String WARN_ON_DUPLICATE_ENTITYDEF = "http://apache.org/xml/features/warn-on-duplicate-entitydef";
    protected static final String WARN_ON_UNDECLARED_ELEMDEF = "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef";
    protected static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
    protected XMLDTDProcessor fDTDProcessor;
    protected XMLDTDScanner fDTDScanner;
    protected XMLDTDValidator fDTDValidator;
    protected DTDDVFactory fDatatypeValidatorFactory;
    protected XMLEntityManager fEntityManager;
    protected XMLErrorReporter fErrorReporter;
    protected XMLGrammarPool fGrammarPool;
    protected XMLInputSource fInputSource;
    protected XMLLocator fLocator;
    protected XMLNamespaceBinder fNamespaceBinder;
    protected boolean fParseInProgress;
    protected XMLDocumentScanner fScanner;
    protected ValidationManager fValidationManager;

    public DTDConfiguration() {
        this(null, null, null);
    }

    public DTDConfiguration(SymbolTable symbolTable) {
        this(symbolTable, null, null);
    }

    public DTDConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool) {
        this(symbolTable, xMLGrammarPool, null);
    }

    public DTDConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool, XMLComponentManager xMLComponentManager) {
        super(symbolTable, xMLComponentManager);
        this.fParseInProgress = false;
        addRecognizedFeatures(new String[]{CONTINUE_AFTER_FATAL_ERROR, LOAD_EXTERNAL_DTD});
        setFeature(CONTINUE_AFTER_FATAL_ERROR, false);
        setFeature(LOAD_EXTERNAL_DTD, true);
        addRecognizedProperties(new String[]{"http://apache.org/xml/properties/internal/error-reporter", ENTITY_MANAGER, DOCUMENT_SCANNER, DTD_SCANNER, DTD_PROCESSOR, DTD_VALIDATOR, NAMESPACE_BINDER, "http://apache.org/xml/properties/internal/grammar-pool", DATATYPE_VALIDATOR_FACTORY, VALIDATION_MANAGER, "http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/jaxp/properties/schemaLanguage", LOCALE});
        this.fGrammarPool = xMLGrammarPool;
        if (xMLGrammarPool != null) {
            setProperty("http://apache.org/xml/properties/internal/grammar-pool", xMLGrammarPool);
        }
        XMLEntityManager xMLEntityManagerCreateEntityManager = createEntityManager();
        this.fEntityManager = xMLEntityManagerCreateEntityManager;
        setProperty(ENTITY_MANAGER, xMLEntityManagerCreateEntityManager);
        addComponent(this.fEntityManager);
        XMLErrorReporter xMLErrorReporterCreateErrorReporter = createErrorReporter();
        this.fErrorReporter = xMLErrorReporterCreateErrorReporter;
        xMLErrorReporterCreateErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
        setProperty("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
        addComponent(this.fErrorReporter);
        XMLDocumentScanner xMLDocumentScannerCreateDocumentScanner = createDocumentScanner();
        this.fScanner = xMLDocumentScannerCreateDocumentScanner;
        setProperty(DOCUMENT_SCANNER, xMLDocumentScannerCreateDocumentScanner);
        XMLDocumentScanner xMLDocumentScanner = this.fScanner;
        if (xMLDocumentScanner instanceof XMLComponent) {
            addComponent((XMLComponent) xMLDocumentScanner);
        }
        XMLDTDScanner xMLDTDScannerCreateDTDScanner = createDTDScanner();
        this.fDTDScanner = xMLDTDScannerCreateDTDScanner;
        if (xMLDTDScannerCreateDTDScanner != null) {
            setProperty(DTD_SCANNER, xMLDTDScannerCreateDTDScanner);
            XMLDTDScanner xMLDTDScanner = this.fDTDScanner;
            if (xMLDTDScanner instanceof XMLComponent) {
                addComponent((XMLComponent) xMLDTDScanner);
            }
        }
        XMLDTDProcessor xMLDTDProcessorCreateDTDProcessor = createDTDProcessor();
        this.fDTDProcessor = xMLDTDProcessorCreateDTDProcessor;
        if (xMLDTDProcessorCreateDTDProcessor != null) {
            setProperty(DTD_PROCESSOR, xMLDTDProcessorCreateDTDProcessor);
            addComponent(this.fDTDProcessor);
        }
        XMLDTDValidator xMLDTDValidatorCreateDTDValidator = createDTDValidator();
        this.fDTDValidator = xMLDTDValidatorCreateDTDValidator;
        if (xMLDTDValidatorCreateDTDValidator != null) {
            setProperty(DTD_VALIDATOR, xMLDTDValidatorCreateDTDValidator);
            addComponent(this.fDTDValidator);
        }
        XMLNamespaceBinder xMLNamespaceBinderCreateNamespaceBinder = createNamespaceBinder();
        this.fNamespaceBinder = xMLNamespaceBinderCreateNamespaceBinder;
        if (xMLNamespaceBinderCreateNamespaceBinder != null) {
            setProperty(NAMESPACE_BINDER, xMLNamespaceBinderCreateNamespaceBinder);
            addComponent(this.fNamespaceBinder);
        }
        DTDDVFactory dTDDVFactoryCreateDatatypeValidatorFactory = createDatatypeValidatorFactory();
        this.fDatatypeValidatorFactory = dTDDVFactoryCreateDatatypeValidatorFactory;
        if (dTDDVFactoryCreateDatatypeValidatorFactory != null) {
            setProperty(DATATYPE_VALIDATOR_FACTORY, dTDDVFactoryCreateDatatypeValidatorFactory);
        }
        ValidationManager validationManagerCreateValidationManager = createValidationManager();
        this.fValidationManager = validationManagerCreateValidationManager;
        if (validationManagerCreateValidationManager != null) {
            setProperty(VALIDATION_MANAGER, validationManagerCreateValidationManager);
        }
        if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
            XMLMessageFormatter xMLMessageFormatter = new XMLMessageFormatter();
            this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xMLMessageFormatter);
            this.fErrorReporter.putMessageFormatter(XMLMessageFormatter.XMLNS_DOMAIN, xMLMessageFormatter);
        }
        try {
            setLocale(Locale.getDefault());
        } catch (XNIException unused) {
        }
    }

    @Override // org.apache.xerces.parsers.BasicParserConfiguration, org.apache.xerces.util.ParserConfigurationSettings
    protected void checkFeature(String str) throws XMLConfigurationException {
        if (str.startsWith(Constants.XERCES_FEATURE_PREFIX)) {
            int length = str.length() - Constants.XERCES_FEATURE_PREFIX.length();
            if (length == Constants.DYNAMIC_VALIDATION_FEATURE.length() && str.endsWith(Constants.DYNAMIC_VALIDATION_FEATURE)) {
                return;
            }
            if (length == Constants.DEFAULT_ATTRIBUTE_VALUES_FEATURE.length() && str.endsWith(Constants.DEFAULT_ATTRIBUTE_VALUES_FEATURE)) {
                throw new XMLConfigurationException((short) 1, str);
            }
            if (length == Constants.VALIDATE_CONTENT_MODELS_FEATURE.length() && str.endsWith(Constants.VALIDATE_CONTENT_MODELS_FEATURE)) {
                throw new XMLConfigurationException((short) 1, str);
            }
            if (length == Constants.LOAD_DTD_GRAMMAR_FEATURE.length() && str.endsWith(Constants.LOAD_DTD_GRAMMAR_FEATURE)) {
                return;
            }
            if (length == Constants.LOAD_EXTERNAL_DTD_FEATURE.length() && str.endsWith(Constants.LOAD_EXTERNAL_DTD_FEATURE)) {
                return;
            }
            if (length == Constants.VALIDATE_DATATYPES_FEATURE.length() && str.endsWith(Constants.VALIDATE_DATATYPES_FEATURE)) {
                throw new XMLConfigurationException((short) 1, str);
            }
        }
        super.checkFeature(str);
    }

    @Override // org.apache.xerces.parsers.BasicParserConfiguration, org.apache.xerces.util.ParserConfigurationSettings
    protected void checkProperty(String str) throws XMLConfigurationException {
        if (str.startsWith(Constants.XERCES_PROPERTY_PREFIX) && str.length() - Constants.XERCES_PROPERTY_PREFIX.length() == Constants.DTD_SCANNER_PROPERTY.length() && str.endsWith(Constants.DTD_SCANNER_PROPERTY)) {
            return;
        }
        super.checkProperty(str);
    }

    @Override // org.apache.xerces.xni.parser.XMLPullParserConfiguration
    public void cleanup() {
        this.fEntityManager.closeReaders();
    }

    protected void configureDTDPipeline() {
        XMLDTDContentModelHandler xMLDTDContentModelHandler;
        XMLDTDContentModelSource xMLDTDContentModelSource;
        if (this.fDTDScanner != null) {
            this.fProperties.put(DTD_SCANNER, this.fDTDScanner);
            if (this.fDTDProcessor != null) {
                this.fProperties.put(DTD_PROCESSOR, this.fDTDProcessor);
                this.fDTDScanner.setDTDHandler(this.fDTDProcessor);
                this.fDTDProcessor.setDTDSource(this.fDTDScanner);
                this.fDTDProcessor.setDTDHandler(this.fDTDHandler);
                if (this.fDTDHandler != null) {
                    this.fDTDHandler.setDTDSource(this.fDTDProcessor);
                }
                this.fDTDScanner.setDTDContentModelHandler(this.fDTDProcessor);
                this.fDTDProcessor.setDTDContentModelSource(this.fDTDScanner);
                this.fDTDProcessor.setDTDContentModelHandler(this.fDTDContentModelHandler);
                if (this.fDTDContentModelHandler == null) {
                    return;
                }
                xMLDTDContentModelHandler = this.fDTDContentModelHandler;
                xMLDTDContentModelSource = this.fDTDProcessor;
            } else {
                this.fDTDScanner.setDTDHandler(this.fDTDHandler);
                if (this.fDTDHandler != null) {
                    this.fDTDHandler.setDTDSource(this.fDTDScanner);
                }
                this.fDTDScanner.setDTDContentModelHandler(this.fDTDContentModelHandler);
                if (this.fDTDContentModelHandler == null) {
                    return;
                }
                xMLDTDContentModelHandler = this.fDTDContentModelHandler;
                xMLDTDContentModelSource = this.fDTDScanner;
            }
            xMLDTDContentModelHandler.setDTDContentModelSource(xMLDTDContentModelSource);
        }
    }

    protected void configurePipeline() {
        XMLDocumentSource xMLDocumentSource;
        XMLNamespaceBinder xMLNamespaceBinder;
        XMLDocumentSource xMLDocumentSource2;
        XMLDTDValidator xMLDTDValidator = this.fDTDValidator;
        if (xMLDTDValidator != null) {
            this.fScanner.setDocumentHandler(xMLDTDValidator);
            if (this.fFeatures.get("http://xml.org/sax/features/namespaces") == Boolean.TRUE) {
                this.fDTDValidator.setDocumentHandler(this.fNamespaceBinder);
                this.fDTDValidator.setDocumentSource(this.fScanner);
                this.fNamespaceBinder.setDocumentHandler(this.fDocumentHandler);
                xMLNamespaceBinder = this.fNamespaceBinder;
                xMLDocumentSource2 = this.fDTDValidator;
                xMLNamespaceBinder.setDocumentSource(xMLDocumentSource2);
                xMLDocumentSource = this.fNamespaceBinder;
            } else {
                this.fDTDValidator.setDocumentHandler(this.fDocumentHandler);
                this.fDTDValidator.setDocumentSource(this.fScanner);
                xMLDocumentSource = this.fDTDValidator;
            }
        } else if (this.fFeatures.get("http://xml.org/sax/features/namespaces") == Boolean.TRUE) {
            this.fScanner.setDocumentHandler(this.fNamespaceBinder);
            this.fNamespaceBinder.setDocumentHandler(this.fDocumentHandler);
            xMLNamespaceBinder = this.fNamespaceBinder;
            xMLDocumentSource2 = this.fScanner;
            xMLNamespaceBinder.setDocumentSource(xMLDocumentSource2);
            xMLDocumentSource = this.fNamespaceBinder;
        } else {
            this.fScanner.setDocumentHandler(this.fDocumentHandler);
            xMLDocumentSource = this.fScanner;
        }
        this.fLastComponent = xMLDocumentSource;
        configureDTDPipeline();
    }

    protected XMLDTDProcessor createDTDProcessor() {
        return new XMLDTDProcessor();
    }

    protected XMLDTDScanner createDTDScanner() {
        return new XMLDTDScannerImpl();
    }

    protected XMLDTDValidator createDTDValidator() {
        return new XMLDTDValidator();
    }

    protected DTDDVFactory createDatatypeValidatorFactory() {
        return DTDDVFactory.getInstance();
    }

    protected XMLDocumentScanner createDocumentScanner() {
        return new XMLDocumentScannerImpl();
    }

    protected XMLEntityManager createEntityManager() {
        return new XMLEntityManager();
    }

    protected XMLErrorReporter createErrorReporter() {
        return new XMLErrorReporter();
    }

    protected XMLNamespaceBinder createNamespaceBinder() {
        return new XMLNamespaceBinder();
    }

    protected ValidationManager createValidationManager() {
        return new ValidationManager();
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLComponentManager, org.apache.xerces.xni.parser.XMLParserConfiguration
    public Object getProperty(String str) throws XMLConfigurationException {
        return LOCALE.equals(str) ? getLocale() : super.getProperty(str);
    }

    @Override // org.apache.xerces.parsers.BasicParserConfiguration, org.apache.xerces.xni.parser.XMLParserConfiguration
    public void parse(XMLInputSource xMLInputSource) throws IOException, XNIException {
        if (this.fParseInProgress) {
            throw new XNIException("FWK005 parse may not be called while parsing.");
        }
        this.fParseInProgress = true;
        try {
            try {
                try {
                    try {
                        try {
                            setInputSource(xMLInputSource);
                            parse(true);
                            this.fParseInProgress = false;
                            cleanup();
                        } catch (RuntimeException e) {
                            throw e;
                        }
                    } catch (IOException e2) {
                        throw e2;
                    }
                } catch (Exception e3) {
                    throw new XNIException(e3);
                }
            } catch (XNIException e4) {
                throw e4;
            }
        } catch (Throwable th) {
            this.fParseInProgress = false;
            cleanup();
            throw th;
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLPullParserConfiguration
    public boolean parse(boolean z) throws IOException, XNIException {
        if (this.fInputSource != null) {
            try {
                reset();
                this.fScanner.setInputSource(this.fInputSource);
                this.fInputSource = null;
            } catch (IOException e) {
                throw e;
            } catch (XNIException e2) {
                throw e2;
            } catch (RuntimeException e3) {
                throw e3;
            } catch (Exception e4) {
                throw new XNIException(e4);
            }
        }
        try {
            return this.fScanner.scanDocument(z);
        } catch (IOException e5) {
            throw e5;
        } catch (XNIException e6) {
            throw e6;
        } catch (RuntimeException e7) {
            throw e7;
        } catch (Exception e8) {
            throw new XNIException(e8);
        }
    }

    @Override // org.apache.xerces.parsers.BasicParserConfiguration
    protected void reset() throws XNIException {
        ValidationManager validationManager = this.fValidationManager;
        if (validationManager != null) {
            validationManager.reset();
        }
        configurePipeline();
        super.reset();
    }

    @Override // org.apache.xerces.xni.parser.XMLPullParserConfiguration
    public void setInputSource(XMLInputSource xMLInputSource) throws XMLConfigurationException, IOException {
        this.fInputSource = xMLInputSource;
    }

    @Override // org.apache.xerces.parsers.BasicParserConfiguration, org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setLocale(Locale locale) throws XNIException {
        super.setLocale(locale);
        this.fErrorReporter.setLocale(locale);
    }

    @Override // org.apache.xerces.parsers.BasicParserConfiguration, org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        if (LOCALE.equals(str)) {
            setLocale((Locale) obj);
        }
        super.setProperty(str, obj);
    }
}
