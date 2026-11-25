package org.apache.xerces.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.XML11DTDScannerImpl;
import org.apache.xerces.impl.XML11DocumentScannerImpl;
import org.apache.xerces.impl.XML11NSDocumentScannerImpl;
import org.apache.xerces.impl.XMLDTDScannerImpl;
import org.apache.xerces.impl.XMLDocumentScannerImpl;
import org.apache.xerces.impl.XMLEntityHandler;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.XMLNSDocumentScannerImpl;
import org.apache.xerces.impl.XMLVersionDetector;
import org.apache.xerces.impl.dtd.XML11DTDProcessor;
import org.apache.xerces.impl.dtd.XML11DTDValidator;
import org.apache.xerces.impl.dtd.XML11NSDTDValidator;
import org.apache.xerces.impl.dtd.XMLDTDProcessor;
import org.apache.xerces.impl.dtd.XMLDTDValidator;
import org.apache.xerces.impl.dtd.XMLNSDTDValidator;
import org.apache.xerces.impl.dv.DTDDVFactory;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.util.ParserConfigurationSettings;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XMLDTDContentModelHandler;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDScanner;
import org.apache.xerces.xni.parser.XMLDocumentScanner;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLPullParserConfiguration;

/* JADX INFO: loaded from: classes13.dex */
public class XML11Configuration extends ParserConfigurationSettings implements XMLPullParserConfiguration, XML11Configurable {
    protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
    protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
    protected static final String DATATYPE_VALIDATOR_FACTORY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
    protected static final String DOCUMENT_SCANNER = "http://apache.org/xml/properties/internal/document-scanner";
    protected static final String DTD_PROCESSOR = "http://apache.org/xml/properties/internal/dtd-processor";
    protected static final String DTD_SCANNER = "http://apache.org/xml/properties/internal/dtd-scanner";
    protected static final String DTD_VALIDATOR = "http://apache.org/xml/properties/internal/validator/dtd";
    protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
    protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
    protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    protected static final String EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
    protected static final String EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
    protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
    protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
    protected static final String IDENTITY_CONSTRAINT_CHECKING = "http://apache.org/xml/features/validation/identity-constraint-checking";
    protected static final String ID_IDREF_CHECKING = "http://apache.org/xml/features/validation/id-idref-checking";
    protected static final String IGNORE_XSI_TYPE = "http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl";
    protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    protected static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
    protected static final String LOCALE = "http://apache.org/xml/properties/locale";
    protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    protected static final String NAMESPACE_BINDER = "http://apache.org/xml/properties/internal/namespace-binder";
    protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
    protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
    protected static final String NOTIFY_BUILTIN_REFS = "http://apache.org/xml/features/scanner/notify-builtin-refs";
    protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
    protected static final boolean PRINT_EXCEPTION_STACK_TRACE = false;
    protected static final String ROOT_ELEMENT_DECL = "http://apache.org/xml/properties/validation/schema/root-element-declaration";
    protected static final String ROOT_TYPE_DEF = "http://apache.org/xml/properties/validation/schema/root-type-definition";
    protected static final String SCHEMA_AUGMENT_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
    protected static final String SCHEMA_DV_FACTORY = "http://apache.org/xml/properties/internal/validation/schema/dv-factory";
    protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
    protected static final String SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-schemaLocation";
    protected static final String SCHEMA_NONS_LOCATION = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
    protected static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
    protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
    protected static final String UNPARSED_ENTITY_CHECKING = "http://apache.org/xml/features/validation/unparsed-entity-checking";
    protected static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
    protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
    protected static final String VALIDATION = "http://xml.org/sax/features/validation";
    protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
    protected static final String WARN_ON_DUPLICATE_ENTITYDEF = "http://apache.org/xml/features/warn-on-duplicate-entitydef";
    protected static final String WARN_ON_UNDECLARED_ELEMDEF = "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef";
    protected static final String XML11_DATATYPE_VALIDATOR_FACTORY = "org.apache.xerces.impl.dv.dtd.XML11DTDDVFactoryImpl";
    protected static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
    protected static final String XMLSCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
    protected static final String XMLSCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
    protected static final String XML_STRING = "http://xml.org/sax/properties/xml-string";
    private boolean f11Initialized;
    protected final ArrayList fCommonComponents;
    protected final ArrayList fComponents;
    protected boolean fConfigUpdated;
    protected XMLDTDScanner fCurrentDTDScanner;
    protected DTDDVFactory fCurrentDVFactory;
    protected XMLDocumentScanner fCurrentScanner;
    protected XMLDTDContentModelHandler fDTDContentModelHandler;
    protected XMLDTDHandler fDTDHandler;
    protected final XMLDTDProcessor fDTDProcessor;
    protected final XMLDTDScanner fDTDScanner;
    protected final XMLDTDValidator fDTDValidator;
    protected final DTDDVFactory fDatatypeValidatorFactory;
    protected XMLDocumentHandler fDocumentHandler;
    protected XMLEntityManager fEntityManager;
    protected XMLErrorReporter fErrorReporter;
    protected XMLGrammarPool fGrammarPool;
    protected XMLInputSource fInputSource;
    protected XMLDocumentSource fLastComponent;
    protected Locale fLocale;
    protected final XMLNSDocumentScannerImpl fNamespaceScanner;
    protected XMLDTDValidator fNonNSDTDValidator;
    protected XMLDocumentScannerImpl fNonNSScanner;
    protected boolean fParseInProgress;
    protected XMLSchemaValidator fSchemaValidator;
    protected SymbolTable fSymbolTable;
    protected final ValidationManager fValidationManager;
    protected final XMLVersionDetector fVersionDetector;
    protected final ArrayList fXML11Components;
    protected XML11DTDProcessor fXML11DTDProcessor;
    protected XML11DTDScannerImpl fXML11DTDScanner;
    protected XML11DTDValidator fXML11DTDValidator;
    protected DTDDVFactory fXML11DatatypeFactory;
    protected XML11DocumentScannerImpl fXML11DocScanner;
    protected XML11NSDTDValidator fXML11NSDTDValidator;
    protected XML11NSDocumentScannerImpl fXML11NSDocScanner;

    public XML11Configuration() {
        this(null, null, null);
    }

    public XML11Configuration(SymbolTable symbolTable) {
        this(symbolTable, null, null);
    }

    public XML11Configuration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool) {
        this(symbolTable, xMLGrammarPool, null);
    }

    public XML11Configuration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool, XMLComponentManager xMLComponentManager) {
        super(xMLComponentManager);
        this.fParseInProgress = false;
        this.fConfigUpdated = false;
        this.fXML11DatatypeFactory = null;
        this.fXML11NSDocScanner = null;
        this.fXML11DocScanner = null;
        this.fXML11NSDTDValidator = null;
        this.fXML11DTDValidator = null;
        this.fXML11DTDScanner = null;
        this.fXML11DTDProcessor = null;
        this.f11Initialized = false;
        this.fComponents = new ArrayList();
        this.fXML11Components = new ArrayList();
        this.fCommonComponents = new ArrayList();
        this.fRecognizedFeatures = new ArrayList();
        this.fRecognizedProperties = new ArrayList();
        this.fFeatures = new HashMap();
        this.fProperties = new HashMap();
        addRecognizedFeatures(new String[]{CONTINUE_AFTER_FATAL_ERROR, LOAD_EXTERNAL_DTD, VALIDATION, NAMESPACES, NORMALIZE_DATA, SCHEMA_ELEMENT_DEFAULT, SCHEMA_AUGMENT_PSVI, "http://apache.org/xml/features/generate-synthetic-annotations", VALIDATE_ANNOTATIONS, HONOUR_ALL_SCHEMALOCATIONS, NAMESPACE_GROWTH, TOLERATE_DUPLICATES, IGNORE_XSI_TYPE, ID_IDREF_CHECKING, IDENTITY_CONSTRAINT_CHECKING, UNPARSED_ENTITY_CHECKING, USE_GRAMMAR_POOL_ONLY, XMLSCHEMA_VALIDATION, XMLSCHEMA_FULL_CHECKING, EXTERNAL_GENERAL_ENTITIES, EXTERNAL_PARAMETER_ENTITIES, "http://apache.org/xml/features/internal/parser-settings"});
        this.fFeatures.put(VALIDATION, Boolean.FALSE);
        this.fFeatures.put(NAMESPACES, Boolean.TRUE);
        this.fFeatures.put(EXTERNAL_GENERAL_ENTITIES, Boolean.TRUE);
        this.fFeatures.put(EXTERNAL_PARAMETER_ENTITIES, Boolean.TRUE);
        this.fFeatures.put(CONTINUE_AFTER_FATAL_ERROR, Boolean.FALSE);
        this.fFeatures.put(LOAD_EXTERNAL_DTD, Boolean.TRUE);
        this.fFeatures.put(SCHEMA_ELEMENT_DEFAULT, Boolean.TRUE);
        this.fFeatures.put(NORMALIZE_DATA, Boolean.TRUE);
        this.fFeatures.put(SCHEMA_AUGMENT_PSVI, Boolean.TRUE);
        this.fFeatures.put("http://apache.org/xml/features/generate-synthetic-annotations", Boolean.FALSE);
        this.fFeatures.put(VALIDATE_ANNOTATIONS, Boolean.FALSE);
        this.fFeatures.put(HONOUR_ALL_SCHEMALOCATIONS, Boolean.FALSE);
        this.fFeatures.put(NAMESPACE_GROWTH, Boolean.FALSE);
        this.fFeatures.put(TOLERATE_DUPLICATES, Boolean.FALSE);
        this.fFeatures.put(IGNORE_XSI_TYPE, Boolean.FALSE);
        this.fFeatures.put(ID_IDREF_CHECKING, Boolean.TRUE);
        this.fFeatures.put(IDENTITY_CONSTRAINT_CHECKING, Boolean.TRUE);
        this.fFeatures.put(UNPARSED_ENTITY_CHECKING, Boolean.TRUE);
        this.fFeatures.put(USE_GRAMMAR_POOL_ONLY, Boolean.FALSE);
        this.fFeatures.put("http://apache.org/xml/features/internal/parser-settings", Boolean.TRUE);
        addRecognizedProperties(new String[]{"http://apache.org/xml/properties/internal/symbol-table", ERROR_HANDLER, "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/error-reporter", ENTITY_MANAGER, DOCUMENT_SCANNER, DTD_SCANNER, DTD_PROCESSOR, DTD_VALIDATOR, DATATYPE_VALIDATOR_FACTORY, VALIDATION_MANAGER, SCHEMA_VALIDATOR, XML_STRING, "http://apache.org/xml/properties/internal/grammar-pool", "http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/jaxp/properties/schemaLanguage", SCHEMA_LOCATION, SCHEMA_NONS_LOCATION, LOCALE, ROOT_TYPE_DEF, ROOT_ELEMENT_DECL, SCHEMA_DV_FACTORY});
        this.fSymbolTable = symbolTable == null ? new SymbolTable() : symbolTable;
        this.fProperties.put("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
        this.fGrammarPool = xMLGrammarPool;
        if (xMLGrammarPool != null) {
            this.fProperties.put("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarPool);
        }
        this.fEntityManager = new XMLEntityManager();
        this.fProperties.put(ENTITY_MANAGER, this.fEntityManager);
        addCommonComponent(this.fEntityManager);
        XMLErrorReporter xMLErrorReporter = new XMLErrorReporter();
        this.fErrorReporter = xMLErrorReporter;
        xMLErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
        this.fProperties.put("http://apache.org/xml/properties/internal/error-reporter", this.fErrorReporter);
        addCommonComponent(this.fErrorReporter);
        XMLNSDocumentScannerImpl xMLNSDocumentScannerImpl = new XMLNSDocumentScannerImpl();
        this.fNamespaceScanner = xMLNSDocumentScannerImpl;
        this.fProperties.put(DOCUMENT_SCANNER, xMLNSDocumentScannerImpl);
        addComponent(xMLNSDocumentScannerImpl);
        XMLDTDScannerImpl xMLDTDScannerImpl = new XMLDTDScannerImpl();
        this.fDTDScanner = xMLDTDScannerImpl;
        this.fProperties.put(DTD_SCANNER, xMLDTDScannerImpl);
        addComponent(xMLDTDScannerImpl);
        XMLDTDProcessor xMLDTDProcessor = new XMLDTDProcessor();
        this.fDTDProcessor = xMLDTDProcessor;
        this.fProperties.put(DTD_PROCESSOR, xMLDTDProcessor);
        addComponent(xMLDTDProcessor);
        XMLNSDTDValidator xMLNSDTDValidator = new XMLNSDTDValidator();
        this.fDTDValidator = xMLNSDTDValidator;
        this.fProperties.put(DTD_VALIDATOR, xMLNSDTDValidator);
        addComponent(xMLNSDTDValidator);
        DTDDVFactory dTDDVFactory = DTDDVFactory.getInstance();
        this.fDatatypeValidatorFactory = dTDDVFactory;
        this.fProperties.put(DATATYPE_VALIDATOR_FACTORY, dTDDVFactory);
        ValidationManager validationManager = new ValidationManager();
        this.fValidationManager = validationManager;
        this.fProperties.put(VALIDATION_MANAGER, validationManager);
        this.fVersionDetector = new XMLVersionDetector();
        if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
            XMLMessageFormatter xMLMessageFormatter = new XMLMessageFormatter();
            this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xMLMessageFormatter);
            this.fErrorReporter.putMessageFormatter(XMLMessageFormatter.XMLNS_DOMAIN, xMLMessageFormatter);
        }
        try {
            setLocale(Locale.getDefault());
        } catch (XNIException unused) {
        }
        this.fConfigUpdated = false;
    }

    private void initXML11Components() {
        if (this.f11Initialized) {
            return;
        }
        this.fXML11DatatypeFactory = DTDDVFactory.getInstance(XML11_DATATYPE_VALIDATOR_FACTORY);
        XML11DTDScannerImpl xML11DTDScannerImpl = new XML11DTDScannerImpl();
        this.fXML11DTDScanner = xML11DTDScannerImpl;
        addXML11Component(xML11DTDScannerImpl);
        XML11DTDProcessor xML11DTDProcessor = new XML11DTDProcessor();
        this.fXML11DTDProcessor = xML11DTDProcessor;
        addXML11Component(xML11DTDProcessor);
        XML11NSDocumentScannerImpl xML11NSDocumentScannerImpl = new XML11NSDocumentScannerImpl();
        this.fXML11NSDocScanner = xML11NSDocumentScannerImpl;
        addXML11Component(xML11NSDocumentScannerImpl);
        XML11NSDTDValidator xML11NSDTDValidator = new XML11NSDTDValidator();
        this.fXML11NSDTDValidator = xML11NSDTDValidator;
        addXML11Component(xML11NSDTDValidator);
        this.f11Initialized = true;
    }

    protected void addCommonComponent(XMLComponent xMLComponent) {
        if (this.fCommonComponents.contains(xMLComponent)) {
            return;
        }
        this.fCommonComponents.add(xMLComponent);
        addRecognizedParamsAndSetDefaults(xMLComponent);
    }

    protected void addComponent(XMLComponent xMLComponent) {
        if (this.fComponents.contains(xMLComponent)) {
            return;
        }
        this.fComponents.add(xMLComponent);
        addRecognizedParamsAndSetDefaults(xMLComponent);
    }

    protected void addRecognizedParamsAndSetDefaults(XMLComponent xMLComponent) {
        String[] recognizedFeatures = xMLComponent.getRecognizedFeatures();
        addRecognizedFeatures(recognizedFeatures);
        String[] recognizedProperties = xMLComponent.getRecognizedProperties();
        addRecognizedProperties(recognizedProperties);
        if (recognizedFeatures != null) {
            for (String str : recognizedFeatures) {
                Boolean featureDefault = xMLComponent.getFeatureDefault(str);
                if (featureDefault != null && !this.fFeatures.containsKey(str)) {
                    this.fFeatures.put(str, featureDefault);
                    this.fConfigUpdated = true;
                }
            }
        }
        if (recognizedProperties != null) {
            for (String str2 : recognizedProperties) {
                Object propertyDefault = xMLComponent.getPropertyDefault(str2);
                if (propertyDefault != null && !this.fProperties.containsKey(str2)) {
                    this.fProperties.put(str2, propertyDefault);
                    this.fConfigUpdated = true;
                }
            }
        }
    }

    protected void addXML11Component(XMLComponent xMLComponent) {
        if (this.fXML11Components.contains(xMLComponent)) {
            return;
        }
        this.fXML11Components.add(xMLComponent);
        addRecognizedParamsAndSetDefaults(xMLComponent);
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings
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
            if (length == Constants.SCHEMA_VALIDATION_FEATURE.length() && str.endsWith(Constants.SCHEMA_VALIDATION_FEATURE)) {
                return;
            }
            if (length == Constants.SCHEMA_FULL_CHECKING.length() && str.endsWith(Constants.SCHEMA_FULL_CHECKING)) {
                return;
            }
            if (length == Constants.SCHEMA_NORMALIZED_VALUE.length() && str.endsWith(Constants.SCHEMA_NORMALIZED_VALUE)) {
                return;
            }
            if (length == Constants.SCHEMA_ELEMENT_DEFAULT.length() && str.endsWith(Constants.SCHEMA_ELEMENT_DEFAULT)) {
                return;
            }
            if (length == Constants.PARSER_SETTINGS.length() && str.endsWith(Constants.PARSER_SETTINGS)) {
                throw new XMLConfigurationException((short) 1, str);
            }
        }
        super.checkFeature(str);
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings
    protected void checkProperty(String str) throws XMLConfigurationException {
        if (str.startsWith(Constants.XERCES_PROPERTY_PREFIX)) {
            int length = str.length() - Constants.XERCES_PROPERTY_PREFIX.length();
            if (length == Constants.DTD_SCANNER_PROPERTY.length() && str.endsWith(Constants.DTD_SCANNER_PROPERTY)) {
                return;
            }
            if (length == Constants.SCHEMA_LOCATION.length() && str.endsWith(Constants.SCHEMA_LOCATION)) {
                return;
            }
            if (length == Constants.SCHEMA_NONS_LOCATION.length() && str.endsWith(Constants.SCHEMA_NONS_LOCATION)) {
                return;
            }
        }
        if (str.startsWith(Constants.JAXP_PROPERTY_PREFIX) && str.length() - Constants.JAXP_PROPERTY_PREFIX.length() == Constants.SCHEMA_SOURCE.length() && str.endsWith(Constants.SCHEMA_SOURCE)) {
            return;
        }
        if (str.startsWith(Constants.SAX_PROPERTY_PREFIX) && str.length() - Constants.SAX_PROPERTY_PREFIX.length() == Constants.XML_STRING_PROPERTY.length() && str.endsWith(Constants.XML_STRING_PROPERTY)) {
            throw new XMLConfigurationException((short) 1, str);
        }
        super.checkProperty(str);
    }

    @Override // org.apache.xerces.xni.parser.XMLPullParserConfiguration
    public void cleanup() {
        this.fEntityManager.closeReaders();
    }

    protected void configurePipeline() {
        XMLDTDValidator xMLDTDValidator;
        DTDDVFactory dTDDVFactory = this.fCurrentDVFactory;
        DTDDVFactory dTDDVFactory2 = this.fDatatypeValidatorFactory;
        if (dTDDVFactory != dTDDVFactory2) {
            this.fCurrentDVFactory = dTDDVFactory2;
            setProperty(DATATYPE_VALIDATOR_FACTORY, dTDDVFactory2);
        }
        XMLDTDScanner xMLDTDScanner = this.fCurrentDTDScanner;
        XMLDTDScanner xMLDTDScanner2 = this.fDTDScanner;
        if (xMLDTDScanner != xMLDTDScanner2) {
            this.fCurrentDTDScanner = xMLDTDScanner2;
            setProperty(DTD_SCANNER, xMLDTDScanner2);
            setProperty(DTD_PROCESSOR, this.fDTDProcessor);
        }
        this.fDTDScanner.setDTDHandler(this.fDTDProcessor);
        this.fDTDProcessor.setDTDSource(this.fDTDScanner);
        this.fDTDProcessor.setDTDHandler(this.fDTDHandler);
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.setDTDSource(this.fDTDProcessor);
        }
        this.fDTDScanner.setDTDContentModelHandler(this.fDTDProcessor);
        this.fDTDProcessor.setDTDContentModelSource(this.fDTDScanner);
        this.fDTDProcessor.setDTDContentModelHandler(this.fDTDContentModelHandler);
        XMLDTDContentModelHandler xMLDTDContentModelHandler = this.fDTDContentModelHandler;
        if (xMLDTDContentModelHandler != null) {
            xMLDTDContentModelHandler.setDTDContentModelSource(this.fDTDProcessor);
        }
        if (this.fFeatures.get(NAMESPACES) == Boolean.TRUE) {
            XMLDocumentScanner xMLDocumentScanner = this.fCurrentScanner;
            XMLNSDocumentScannerImpl xMLNSDocumentScannerImpl = this.fNamespaceScanner;
            if (xMLDocumentScanner != xMLNSDocumentScannerImpl) {
                this.fCurrentScanner = xMLNSDocumentScannerImpl;
                setProperty(DOCUMENT_SCANNER, xMLNSDocumentScannerImpl);
                setProperty(DTD_VALIDATOR, this.fDTDValidator);
            }
            this.fNamespaceScanner.setDTDValidator(this.fDTDValidator);
            this.fNamespaceScanner.setDocumentHandler(this.fDTDValidator);
            this.fDTDValidator.setDocumentSource(this.fNamespaceScanner);
            this.fDTDValidator.setDocumentHandler(this.fDocumentHandler);
            XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
            if (xMLDocumentHandler != null) {
                xMLDocumentHandler.setDocumentSource(this.fDTDValidator);
            }
            xMLDTDValidator = this.fDTDValidator;
        } else {
            if (this.fNonNSScanner == null) {
                this.fNonNSScanner = new XMLDocumentScannerImpl();
                this.fNonNSDTDValidator = new XMLDTDValidator();
                addComponent(this.fNonNSScanner);
                addComponent(this.fNonNSDTDValidator);
            }
            XMLDocumentScanner xMLDocumentScanner2 = this.fCurrentScanner;
            XMLDocumentScannerImpl xMLDocumentScannerImpl = this.fNonNSScanner;
            if (xMLDocumentScanner2 != xMLDocumentScannerImpl) {
                this.fCurrentScanner = xMLDocumentScannerImpl;
                setProperty(DOCUMENT_SCANNER, xMLDocumentScannerImpl);
                setProperty(DTD_VALIDATOR, this.fNonNSDTDValidator);
            }
            this.fNonNSScanner.setDocumentHandler(this.fNonNSDTDValidator);
            this.fNonNSDTDValidator.setDocumentSource(this.fNonNSScanner);
            this.fNonNSDTDValidator.setDocumentHandler(this.fDocumentHandler);
            XMLDocumentHandler xMLDocumentHandler2 = this.fDocumentHandler;
            if (xMLDocumentHandler2 != null) {
                xMLDocumentHandler2.setDocumentSource(this.fNonNSDTDValidator);
            }
            xMLDTDValidator = this.fNonNSDTDValidator;
        }
        this.fLastComponent = xMLDTDValidator;
        if (this.fFeatures.get(XMLSCHEMA_VALIDATION) == Boolean.TRUE) {
            if (this.fSchemaValidator == null) {
                XMLSchemaValidator xMLSchemaValidator = new XMLSchemaValidator();
                this.fSchemaValidator = xMLSchemaValidator;
                setProperty(SCHEMA_VALIDATOR, xMLSchemaValidator);
                addCommonComponent(this.fSchemaValidator);
                this.fSchemaValidator.reset(this);
                if (this.fErrorReporter.getMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN) == null) {
                    this.fErrorReporter.putMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN, new XSMessageFormatter());
                }
            }
            this.fLastComponent.setDocumentHandler(this.fSchemaValidator);
            this.fSchemaValidator.setDocumentSource(this.fLastComponent);
            this.fSchemaValidator.setDocumentHandler(this.fDocumentHandler);
            XMLDocumentHandler xMLDocumentHandler3 = this.fDocumentHandler;
            if (xMLDocumentHandler3 != null) {
                xMLDocumentHandler3.setDocumentSource(this.fSchemaValidator);
            }
            this.fLastComponent = this.fSchemaValidator;
        }
    }

    protected void configureXML11Pipeline() {
        XMLDocumentSource xMLDocumentSource;
        DTDDVFactory dTDDVFactory = this.fCurrentDVFactory;
        DTDDVFactory dTDDVFactory2 = this.fXML11DatatypeFactory;
        if (dTDDVFactory != dTDDVFactory2) {
            this.fCurrentDVFactory = dTDDVFactory2;
            setProperty(DATATYPE_VALIDATOR_FACTORY, dTDDVFactory2);
        }
        XMLDTDScanner xMLDTDScanner = this.fCurrentDTDScanner;
        XML11DTDScannerImpl xML11DTDScannerImpl = this.fXML11DTDScanner;
        if (xMLDTDScanner != xML11DTDScannerImpl) {
            this.fCurrentDTDScanner = xML11DTDScannerImpl;
            setProperty(DTD_SCANNER, xML11DTDScannerImpl);
            setProperty(DTD_PROCESSOR, this.fXML11DTDProcessor);
        }
        this.fXML11DTDScanner.setDTDHandler(this.fXML11DTDProcessor);
        this.fXML11DTDProcessor.setDTDSource(this.fXML11DTDScanner);
        this.fXML11DTDProcessor.setDTDHandler(this.fDTDHandler);
        XMLDTDHandler xMLDTDHandler = this.fDTDHandler;
        if (xMLDTDHandler != null) {
            xMLDTDHandler.setDTDSource(this.fXML11DTDProcessor);
        }
        this.fXML11DTDScanner.setDTDContentModelHandler(this.fXML11DTDProcessor);
        this.fXML11DTDProcessor.setDTDContentModelSource(this.fXML11DTDScanner);
        this.fXML11DTDProcessor.setDTDContentModelHandler(this.fDTDContentModelHandler);
        XMLDTDContentModelHandler xMLDTDContentModelHandler = this.fDTDContentModelHandler;
        if (xMLDTDContentModelHandler != null) {
            xMLDTDContentModelHandler.setDTDContentModelSource(this.fXML11DTDProcessor);
        }
        if (this.fFeatures.get(NAMESPACES) == Boolean.TRUE) {
            XMLDocumentScanner xMLDocumentScanner = this.fCurrentScanner;
            XML11NSDocumentScannerImpl xML11NSDocumentScannerImpl = this.fXML11NSDocScanner;
            if (xMLDocumentScanner != xML11NSDocumentScannerImpl) {
                this.fCurrentScanner = xML11NSDocumentScannerImpl;
                setProperty(DOCUMENT_SCANNER, xML11NSDocumentScannerImpl);
                setProperty(DTD_VALIDATOR, this.fXML11NSDTDValidator);
            }
            this.fXML11NSDocScanner.setDTDValidator(this.fXML11NSDTDValidator);
            this.fXML11NSDocScanner.setDocumentHandler(this.fXML11NSDTDValidator);
            this.fXML11NSDTDValidator.setDocumentSource(this.fXML11NSDocScanner);
            this.fXML11NSDTDValidator.setDocumentHandler(this.fDocumentHandler);
            XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
            if (xMLDocumentHandler != null) {
                xMLDocumentHandler.setDocumentSource(this.fXML11NSDTDValidator);
            }
            xMLDocumentSource = this.fXML11NSDTDValidator;
        } else {
            if (this.fXML11DocScanner == null) {
                XML11DocumentScannerImpl xML11DocumentScannerImpl = new XML11DocumentScannerImpl();
                this.fXML11DocScanner = xML11DocumentScannerImpl;
                addXML11Component(xML11DocumentScannerImpl);
                XML11DTDValidator xML11DTDValidator = new XML11DTDValidator();
                this.fXML11DTDValidator = xML11DTDValidator;
                addXML11Component(xML11DTDValidator);
            }
            XMLDocumentScanner xMLDocumentScanner2 = this.fCurrentScanner;
            XML11DocumentScannerImpl xML11DocumentScannerImpl2 = this.fXML11DocScanner;
            if (xMLDocumentScanner2 != xML11DocumentScannerImpl2) {
                this.fCurrentScanner = xML11DocumentScannerImpl2;
                setProperty(DOCUMENT_SCANNER, xML11DocumentScannerImpl2);
                setProperty(DTD_VALIDATOR, this.fXML11DTDValidator);
            }
            this.fXML11DocScanner.setDocumentHandler(this.fXML11DTDValidator);
            this.fXML11DTDValidator.setDocumentSource(this.fXML11DocScanner);
            this.fXML11DTDValidator.setDocumentHandler(this.fDocumentHandler);
            XMLDocumentHandler xMLDocumentHandler2 = this.fDocumentHandler;
            if (xMLDocumentHandler2 != null) {
                xMLDocumentHandler2.setDocumentSource(this.fXML11DTDValidator);
            }
            xMLDocumentSource = this.fXML11DTDValidator;
        }
        this.fLastComponent = xMLDocumentSource;
        if (this.fFeatures.get(XMLSCHEMA_VALIDATION) == Boolean.TRUE) {
            if (this.fSchemaValidator == null) {
                XMLSchemaValidator xMLSchemaValidator = new XMLSchemaValidator();
                this.fSchemaValidator = xMLSchemaValidator;
                setProperty(SCHEMA_VALIDATOR, xMLSchemaValidator);
                addCommonComponent(this.fSchemaValidator);
                this.fSchemaValidator.reset(this);
                if (this.fErrorReporter.getMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN) == null) {
                    this.fErrorReporter.putMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN, new XSMessageFormatter());
                }
            }
            this.fLastComponent.setDocumentHandler(this.fSchemaValidator);
            this.fSchemaValidator.setDocumentSource(this.fLastComponent);
            this.fSchemaValidator.setDocumentHandler(this.fDocumentHandler);
            XMLDocumentHandler xMLDocumentHandler3 = this.fDocumentHandler;
            if (xMLDocumentHandler3 != null) {
                xMLDocumentHandler3.setDocumentSource(this.fSchemaValidator);
            }
            this.fLastComponent = this.fSchemaValidator;
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public XMLDTDContentModelHandler getDTDContentModelHandler() {
        return this.fDTDContentModelHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public XMLDTDHandler getDTDHandler() {
        return this.fDTDHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public XMLDocumentHandler getDocumentHandler() {
        return this.fDocumentHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public XMLEntityResolver getEntityResolver() {
        return (XMLEntityResolver) this.fProperties.get("http://apache.org/xml/properties/internal/entity-resolver");
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public XMLErrorHandler getErrorHandler() {
        return (XMLErrorHandler) this.fProperties.get(ERROR_HANDLER);
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLComponentManager, org.apache.xerces.xni.parser.XMLParserConfiguration
    public boolean getFeature(String str) throws XMLConfigurationException {
        return str.equals("http://apache.org/xml/features/internal/parser-settings") ? this.fConfigUpdated : super.getFeature(str);
    }

    boolean getFeature0(String str) throws XMLConfigurationException {
        return super.getFeature(str);
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public Locale getLocale() {
        return this.fLocale;
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLComponentManager, org.apache.xerces.xni.parser.XMLParserConfiguration
    public Object getProperty(String str) throws XMLConfigurationException {
        return LOCALE.equals(str) ? getLocale() : super.getProperty(str);
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
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
                this.fValidationManager.reset();
                this.fVersionDetector.reset(this);
                resetCommon();
                short sDetermineDocVersion = this.fVersionDetector.determineDocVersion(this.fInputSource);
                if (sDetermineDocVersion == 1) {
                    configurePipeline();
                    reset();
                } else {
                    if (sDetermineDocVersion != 2) {
                        return false;
                    }
                    initXML11Components();
                    configureXML11Pipeline();
                    resetXML11();
                }
                this.fConfigUpdated = false;
                this.fVersionDetector.startDocumentParsing((XMLEntityHandler) this.fCurrentScanner, sDetermineDocVersion);
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
            return this.fCurrentScanner.scanDocument(z);
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

    protected void reset() throws XNIException {
        int size = this.fComponents.size();
        for (int i = 0; i < size; i++) {
            ((XMLComponent) this.fComponents.get(i)).reset(this);
        }
    }

    protected void resetCommon() throws XNIException {
        int size = this.fCommonComponents.size();
        for (int i = 0; i < size; i++) {
            ((XMLComponent) this.fCommonComponents.get(i)).reset(this);
        }
    }

    protected void resetXML11() throws XNIException {
        int size = this.fXML11Components.size();
        for (int i = 0; i < size; i++) {
            ((XMLComponent) this.fXML11Components.get(i)).reset(this);
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setDTDContentModelHandler(XMLDTDContentModelHandler xMLDTDContentModelHandler) {
        this.fDTDContentModelHandler = xMLDTDContentModelHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setDTDHandler(XMLDTDHandler xMLDTDHandler) {
        this.fDTDHandler = xMLDTDHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setDocumentHandler(XMLDocumentHandler xMLDocumentHandler) {
        this.fDocumentHandler = xMLDocumentHandler;
        XMLDocumentSource xMLDocumentSource = this.fLastComponent;
        if (xMLDocumentSource != null) {
            xMLDocumentSource.setDocumentHandler(xMLDocumentHandler);
            XMLDocumentHandler xMLDocumentHandler2 = this.fDocumentHandler;
            if (xMLDocumentHandler2 != null) {
                xMLDocumentHandler2.setDocumentSource(this.fLastComponent);
            }
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setEntityResolver(XMLEntityResolver xMLEntityResolver) {
        this.fProperties.put("http://apache.org/xml/properties/internal/entity-resolver", xMLEntityResolver);
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setErrorHandler(XMLErrorHandler xMLErrorHandler) {
        this.fProperties.put(ERROR_HANDLER, xMLErrorHandler);
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
        this.fConfigUpdated = true;
        int size = this.fComponents.size();
        for (int i = 0; i < size; i++) {
            ((XMLComponent) this.fComponents.get(i)).setFeature(str, z);
        }
        int size2 = this.fCommonComponents.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((XMLComponent) this.fCommonComponents.get(i2)).setFeature(str, z);
        }
        int size3 = this.fXML11Components.size();
        for (int i3 = 0; i3 < size3; i3++) {
            try {
                ((XMLComponent) this.fXML11Components.get(i3)).setFeature(str, z);
            } catch (Exception unused) {
            }
        }
        super.setFeature(str, z);
    }

    @Override // org.apache.xerces.xni.parser.XMLPullParserConfiguration
    public void setInputSource(XMLInputSource xMLInputSource) throws XMLConfigurationException, IOException {
        this.fInputSource = xMLInputSource;
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setLocale(Locale locale) throws XNIException {
        this.fLocale = locale;
        this.fErrorReporter.setLocale(locale);
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        this.fConfigUpdated = true;
        if (LOCALE.equals(str)) {
            setLocale((Locale) obj);
        }
        int size = this.fComponents.size();
        for (int i = 0; i < size; i++) {
            ((XMLComponent) this.fComponents.get(i)).setProperty(str, obj);
        }
        int size2 = this.fCommonComponents.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((XMLComponent) this.fCommonComponents.get(i2)).setProperty(str, obj);
        }
        int size3 = this.fXML11Components.size();
        for (int i3 = 0; i3 < size3; i3++) {
            try {
                ((XMLComponent) this.fXML11Components.get(i3)).setProperty(str, obj);
            } catch (Exception unused) {
            }
        }
        super.setProperty(str, obj);
    }
}
