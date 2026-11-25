package org.apache.xerces.parsers;

import java.io.IOException;
import java.util.Locale;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.XMLDTDScannerImpl;
import org.apache.xerces.impl.XMLDocumentScannerImpl;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.XMLNSDocumentScannerImpl;
import org.apache.xerces.impl.dv.DTDDVFactory;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDScanner;
import org.apache.xerces.xni.parser.XMLDocumentScanner;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLPullParserConfiguration;

/* JADX INFO: loaded from: classes13.dex */
public class NonValidatingConfiguration extends BasicParserConfiguration implements XMLPullParserConfiguration {
    protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
    protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
    protected static final String DATATYPE_VALIDATOR_FACTORY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
    protected static final String DOCUMENT_SCANNER = "http://apache.org/xml/properties/internal/document-scanner";
    protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
    protected static final String DTD_VALIDATOR = "http://apache.org/xml/properties/internal/validator/dtd";
    protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
    protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
    protected static final String LOCALE = "http://apache.org/xml/properties/locale";
    protected static final String NAMESPACE_BINDER = "http://apache.org/xml/properties/internal/namespace-binder";
    protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
    protected static final String NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
    protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
    private static final boolean PRINT_EXCEPTION_STACK_TRACE = false;
    protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
    protected static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
    protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
    protected static final String WARN_ON_DUPLICATE_ENTITYDEF = "http://apache.org/xml/features/warn-on-duplicate-entitydef";
    protected static final String WARN_ON_UNDECLARED_ELEMDEF = "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef";
    protected static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
    protected boolean fConfigUpdated;
    protected XMLDTDScanner fDTDScanner;
    protected DTDDVFactory fDatatypeValidatorFactory;
    protected XMLEntityManager fEntityManager;
    protected XMLErrorReporter fErrorReporter;
    protected XMLGrammarPool fGrammarPool;
    protected XMLInputSource fInputSource;
    protected XMLLocator fLocator;
    private XMLNSDocumentScannerImpl fNamespaceScanner;
    private XMLDocumentScannerImpl fNonNSScanner;
    protected boolean fParseInProgress;
    protected XMLDocumentScanner fScanner;
    protected ValidationManager fValidationManager;

    public NonValidatingConfiguration() {
        this(null, null, null);
    }

    public NonValidatingConfiguration(SymbolTable symbolTable) {
        this(symbolTable, null, null);
    }

    public NonValidatingConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool) {
        this(symbolTable, xMLGrammarPool, null);
    }

    public NonValidatingConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool, XMLComponentManager xMLComponentManager) {
        super(symbolTable, xMLComponentManager);
        this.fConfigUpdated = false;
        this.fParseInProgress = false;
        addRecognizedFeatures(new String[]{"http://apache.org/xml/features/internal/parser-settings", "http://xml.org/sax/features/namespaces", CONTINUE_AFTER_FATAL_ERROR});
        this.fFeatures.put(CONTINUE_AFTER_FATAL_ERROR, Boolean.FALSE);
        this.fFeatures.put("http://apache.org/xml/features/internal/parser-settings", Boolean.TRUE);
        this.fFeatures.put("http://xml.org/sax/features/namespaces", Boolean.TRUE);
        addRecognizedProperties(new String[]{"http://apache.org/xml/properties/internal/error-reporter", ENTITY_MANAGER, DOCUMENT_SCANNER, DTD_SCANNER, DTD_VALIDATOR, NAMESPACE_BINDER, "http://apache.org/xml/properties/internal/grammar-pool", DATATYPE_VALIDATOR_FACTORY, VALIDATION_MANAGER, LOCALE});
        this.fGrammarPool = xMLGrammarPool;
        if (xMLGrammarPool != null) {
            this.fProperties.put("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
        }
        this.fEntityManager = createEntityManager();
        this.fProperties.put(ENTITY_MANAGER, this.fEntityManager);
        addComponent(this.fEntityManager);
        XMLErrorReporter xMLErrorReporterCreateErrorReporter = createErrorReporter();
        this.fErrorReporter = xMLErrorReporterCreateErrorReporter;
        xMLErrorReporterCreateErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
        this.fProperties.put("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
        addComponent(this.fErrorReporter);
        XMLDTDScanner xMLDTDScannerCreateDTDScanner = createDTDScanner();
        this.fDTDScanner = xMLDTDScannerCreateDTDScanner;
        if (xMLDTDScannerCreateDTDScanner != null) {
            this.fProperties.put(DTD_SCANNER, this.fDTDScanner);
            XMLDTDScanner xMLDTDScanner = this.fDTDScanner;
            if (xMLDTDScanner instanceof XMLComponent) {
                addComponent((XMLComponent) xMLDTDScanner);
            }
        }
        DTDDVFactory dTDDVFactoryCreateDatatypeValidatorFactory = createDatatypeValidatorFactory();
        this.fDatatypeValidatorFactory = dTDDVFactoryCreateDatatypeValidatorFactory;
        if (dTDDVFactoryCreateDatatypeValidatorFactory != null) {
            this.fProperties.put(DATATYPE_VALIDATOR_FACTORY, this.fDatatypeValidatorFactory);
        }
        ValidationManager validationManagerCreateValidationManager = createValidationManager();
        this.fValidationManager = validationManagerCreateValidationManager;
        if (validationManagerCreateValidationManager != null) {
            this.fProperties.put(VALIDATION_MANAGER, this.fValidationManager);
        }
        if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
            XMLMessageFormatter xMLMessageFormatter = new XMLMessageFormatter();
            this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xMLMessageFormatter);
            this.fErrorReporter.putMessageFormatter(XMLMessageFormatter.XMLNS_DOMAIN, xMLMessageFormatter);
        }
        this.fConfigUpdated = false;
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
        if (str.startsWith(Constants.JAXP_PROPERTY_PREFIX) && str.length() - Constants.JAXP_PROPERTY_PREFIX.length() == Constants.SCHEMA_SOURCE.length() && str.endsWith(Constants.SCHEMA_SOURCE)) {
            return;
        }
        super.checkProperty(str);
    }

    @Override // org.apache.xerces.xni.parser.XMLPullParserConfiguration
    public void cleanup() {
        this.fEntityManager.closeReaders();
    }

    protected void configurePipeline() {
        XMLDocumentScanner xMLDocumentScanner;
        if (this.fFeatures.get("http://xml.org/sax/features/namespaces") == Boolean.TRUE) {
            if (this.fNamespaceScanner == null) {
                XMLNSDocumentScannerImpl xMLNSDocumentScannerImpl = new XMLNSDocumentScannerImpl();
                this.fNamespaceScanner = xMLNSDocumentScannerImpl;
                addComponent(xMLNSDocumentScannerImpl);
            }
            this.fProperties.put(DOCUMENT_SCANNER, this.fNamespaceScanner);
            this.fNamespaceScanner.setDTDValidator(null);
            xMLDocumentScanner = this.fNamespaceScanner;
        } else {
            if (this.fNonNSScanner == null) {
                XMLDocumentScannerImpl xMLDocumentScannerImpl = new XMLDocumentScannerImpl();
                this.fNonNSScanner = xMLDocumentScannerImpl;
                addComponent(xMLDocumentScannerImpl);
            }
            this.fProperties.put(DOCUMENT_SCANNER, this.fNonNSScanner);
            xMLDocumentScanner = this.fNonNSScanner;
        }
        this.fScanner = xMLDocumentScanner;
        this.fScanner.setDocumentHandler(this.fDocumentHandler);
        this.fLastComponent = this.fScanner;
        XMLDTDScanner xMLDTDScanner = this.fDTDScanner;
        if (xMLDTDScanner != null) {
            xMLDTDScanner.setDTDHandler(this.fDTDHandler);
            this.fDTDScanner.setDTDContentModelHandler(this.fDTDContentModelHandler);
        }
    }

    protected XMLDTDScanner createDTDScanner() {
        return new XMLDTDScannerImpl();
    }

    protected DTDDVFactory createDatatypeValidatorFactory() {
        return DTDDVFactory.getInstance();
    }

    protected XMLDocumentScanner createDocumentScanner() {
        return null;
    }

    protected XMLEntityManager createEntityManager() {
        return new XMLEntityManager();
    }

    protected XMLErrorReporter createErrorReporter() {
        return new XMLErrorReporter();
    }

    protected ValidationManager createValidationManager() {
        return new ValidationManager();
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLComponentManager, org.apache.xerces.xni.parser.XMLParserConfiguration
    public boolean getFeature(String str) throws XMLConfigurationException {
        return str.equals("http://apache.org/xml/features/internal/parser-settings") ? this.fConfigUpdated : super.getFeature(str);
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

    @Override // org.apache.xerces.parsers.BasicParserConfiguration, org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
        this.fConfigUpdated = true;
        super.setFeature(str, z);
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
        this.fConfigUpdated = true;
        if (LOCALE.equals(str)) {
            setLocale((Locale) obj);
        }
        super.setProperty(str, obj);
    }
}
