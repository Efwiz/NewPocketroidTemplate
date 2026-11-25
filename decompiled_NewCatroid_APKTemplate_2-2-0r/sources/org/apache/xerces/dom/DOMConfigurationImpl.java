package org.apache.xerces.dom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.dv.DTDDVFactory;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.util.DOMEntityResolverWrapper;
import org.apache.xerces.util.DOMErrorHandlerWrapper;
import org.apache.xerces.util.MessageFormatter;
import org.apache.xerces.util.ParserConfigurationSettings;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XMLDTDContentModelHandler;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.ls.LSResourceResolver;

/* JADX INFO: loaded from: classes13.dex */
public class DOMConfigurationImpl extends ParserConfigurationSettings implements XMLParserConfiguration, DOMConfiguration {
    protected static final String BALANCE_SYNTAX_TREES = "http://apache.org/xml/features/validation/balance-syntax-trees";
    protected static final short CDATA = 8;
    protected static final short COMMENTS = 32;
    protected static final String DISALLOW_DOCTYPE_DECL_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
    protected static final String DTD_VALIDATOR_FACTORY_PROPERTY = "http://apache.org/xml/properties/internal/datatype-validator-factory";
    protected static final String DTD_VALIDATOR_PROPERTY = "http://apache.org/xml/properties/internal/validator/dtd";
    protected static final short DTNORMALIZATION = 2;
    protected static final String DYNAMIC_VALIDATION = "http://apache.org/xml/features/validation/dynamic";
    protected static final short ENTITIES = 4;
    protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
    protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
    protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
    protected static final String GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
    protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
    protected static final short INFOSET_FALSE_PARAMS = 14;
    protected static final short INFOSET_MASK = 815;
    protected static final short INFOSET_TRUE_PARAMS = 801;
    protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    protected static final short NAMESPACES = 1;
    protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
    protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
    protected static final short NSDECL = 512;
    protected static final short PSVI = 128;
    protected static final String SCHEMA = "http://apache.org/xml/features/validation/schema";
    protected static final String SCHEMA_DV_FACTORY = "http://apache.org/xml/properties/internal/validation/schema/dv-factory";
    protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
    protected static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
    protected static final String SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-schemaLocation";
    protected static final String SCHEMA_NONS_LOCATION = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
    protected static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
    protected static final String SEND_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
    protected static final short SPLITCDATA = 16;
    protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
    protected static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
    protected static final short VALIDATE = 64;
    protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
    protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
    protected static final short WELLFORMED = 256;
    protected static final String XERCES_NAMESPACES = "http://xml.org/sax/features/namespaces";
    protected static final String XERCES_VALIDATION = "http://xml.org/sax/features/validation";
    protected static final String XML11_DATATYPE_VALIDATOR_FACTORY = "org.apache.xerces.impl.dv.dtd.XML11DTDDVFactoryImpl";
    protected static final String XML_STRING = "http://xml.org/sax/properties/xml-string";
    protected ArrayList fComponents;
    protected DTDDVFactory fCurrentDVFactory;
    protected DTDDVFactory fDatatypeValidatorFactory;
    XMLDocumentHandler fDocumentHandler;
    protected final DOMErrorHandlerWrapper fErrorHandlerWrapper;
    protected XMLErrorReporter fErrorReporter;
    protected Locale fLocale;
    private DOMStringList fRecognizedParameters;
    private String fSchemaLocation;
    protected SymbolTable fSymbolTable;
    protected ValidationManager fValidationManager;
    protected DTDDVFactory fXML11DatatypeFactory;
    protected short features;

    protected DOMConfigurationImpl() {
        this(null, null);
    }

    protected DOMConfigurationImpl(SymbolTable symbolTable) {
        this(symbolTable, null);
    }

    protected DOMConfigurationImpl(SymbolTable symbolTable, XMLComponentManager xMLComponentManager) {
        MessageFormatter messageFormatter;
        super(xMLComponentManager);
        this.features = (short) 0;
        this.fErrorHandlerWrapper = new DOMErrorHandlerWrapper();
        this.fSchemaLocation = null;
        this.fRecognizedFeatures = new ArrayList();
        this.fRecognizedProperties = new ArrayList();
        this.fFeatures = new HashMap();
        this.fProperties = new HashMap();
        addRecognizedFeatures(new String[]{XERCES_VALIDATION, XERCES_NAMESPACES, SCHEMA, SCHEMA_FULL_CHECKING, DYNAMIC_VALIDATION, NORMALIZE_DATA, SCHEMA_ELEMENT_DEFAULT, SEND_PSVI, "http://apache.org/xml/features/generate-synthetic-annotations", VALIDATE_ANNOTATIONS, HONOUR_ALL_SCHEMALOCATIONS, USE_GRAMMAR_POOL_ONLY, DISALLOW_DOCTYPE_DECL_FEATURE, BALANCE_SYNTAX_TREES, WARN_ON_DUPLICATE_ATTDEF, "http://apache.org/xml/features/internal/parser-settings", NAMESPACE_GROWTH, TOLERATE_DUPLICATES});
        setFeature(XERCES_VALIDATION, false);
        setFeature(SCHEMA, false);
        setFeature(SCHEMA_FULL_CHECKING, false);
        setFeature(DYNAMIC_VALIDATION, false);
        setFeature(NORMALIZE_DATA, false);
        setFeature(SCHEMA_ELEMENT_DEFAULT, false);
        setFeature(XERCES_NAMESPACES, true);
        setFeature(SEND_PSVI, true);
        setFeature("http://apache.org/xml/features/generate-synthetic-annotations", false);
        setFeature(VALIDATE_ANNOTATIONS, false);
        setFeature(HONOUR_ALL_SCHEMALOCATIONS, false);
        setFeature(USE_GRAMMAR_POOL_ONLY, false);
        setFeature(DISALLOW_DOCTYPE_DECL_FEATURE, false);
        setFeature(BALANCE_SYNTAX_TREES, false);
        setFeature(WARN_ON_DUPLICATE_ATTDEF, false);
        setFeature("http://apache.org/xml/features/internal/parser-settings", true);
        setFeature(NAMESPACE_GROWTH, false);
        setFeature(TOLERATE_DUPLICATES, false);
        addRecognizedProperties(new String[]{XML_STRING, "http://apache.org/xml/properties/internal/symbol-table", ERROR_HANDLER, "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/error-reporter", ENTITY_MANAGER, VALIDATION_MANAGER, "http://apache.org/xml/properties/internal/grammar-pool", SECURITY_MANAGER, "http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/jaxp/properties/schemaLanguage", SCHEMA_LOCATION, SCHEMA_NONS_LOCATION, DTD_VALIDATOR_PROPERTY, DTD_VALIDATOR_FACTORY_PROPERTY, SCHEMA_DV_FACTORY});
        this.features = (short) (((short) (((short) (((short) (((short) (((short) (((short) (this.features | 1)) | 4)) | 32)) | 8)) | 16)) | 256)) | 512);
        this.fSymbolTable = symbolTable == null ? new SymbolTable() : symbolTable;
        this.fComponents = new ArrayList();
        setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fSymbolTable);
        XMLErrorReporter xMLErrorReporter = new XMLErrorReporter();
        this.fErrorReporter = xMLErrorReporter;
        setProperty("http://apache.org/xml/properties/internal/error-reporter", xMLErrorReporter);
        addComponent(this.fErrorReporter);
        this.fDatatypeValidatorFactory = DTDDVFactory.getInstance();
        this.fXML11DatatypeFactory = DTDDVFactory.getInstance(XML11_DATATYPE_VALIDATOR_FACTORY);
        DTDDVFactory dTDDVFactory = this.fDatatypeValidatorFactory;
        this.fCurrentDVFactory = dTDDVFactory;
        setProperty(DTD_VALIDATOR_FACTORY_PROPERTY, dTDDVFactory);
        XMLComponent xMLEntityManager = new XMLEntityManager();
        setProperty(ENTITY_MANAGER, xMLEntityManager);
        addComponent(xMLEntityManager);
        ValidationManager validationManagerCreateValidationManager = createValidationManager();
        this.fValidationManager = validationManagerCreateValidationManager;
        setProperty(VALIDATION_MANAGER, validationManagerCreateValidationManager);
        if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
            XMLMessageFormatter xMLMessageFormatter = new XMLMessageFormatter();
            this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", xMLMessageFormatter);
            this.fErrorReporter.putMessageFormatter(XMLMessageFormatter.XMLNS_DOMAIN, xMLMessageFormatter);
        }
        if (this.fErrorReporter.getMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN) == null) {
            try {
                MessageFormatter messageFormatter2 = (MessageFormatter) ObjectFactory.newInstance("org.apache.xerces.impl.xs.XSMessageFormatter", ObjectFactory.findClassLoader(), true);
                messageFormatter = messageFormatter2;
            } catch (Exception unused) {
                messageFormatter = null;
            }
            if (messageFormatter != null) {
                this.fErrorReporter.putMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN, messageFormatter);
            }
        }
        try {
            setLocale(Locale.getDefault());
        } catch (XNIException unused2) {
        }
    }

    private static DOMException newFeatureNotFoundError(String str) {
        return new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_FOUND", new Object[]{str}));
    }

    private static DOMException newFeatureNotSupportedError(String str) {
        return new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_SUPPORTED", new Object[]{str}));
    }

    private static DOMException newTypeMismatchError(String str) {
        return new DOMException((short) 17, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "TYPE_MISMATCH_ERR", new Object[]{str}));
    }

    protected void addComponent(XMLComponent xMLComponent) {
        if (this.fComponents.contains(xMLComponent)) {
            return;
        }
        this.fComponents.add(xMLComponent);
        addRecognizedFeatures(xMLComponent.getRecognizedFeatures());
        addRecognizedProperties(xMLComponent.getRecognizedProperties());
    }

    @Override // org.w3c.dom.DOMConfiguration
    public boolean canSetParameter(String str, Object obj) {
        if (obj == null) {
            return true;
        }
        if (!(obj instanceof Boolean)) {
            if (str.equalsIgnoreCase(Constants.DOM_ERROR_HANDLER)) {
                return obj instanceof DOMErrorHandler;
            }
            if (str.equalsIgnoreCase(Constants.DOM_RESOURCE_RESOLVER)) {
                return obj instanceof LSResourceResolver;
            }
            if (str.equalsIgnoreCase(Constants.DOM_SCHEMA_LOCATION)) {
                return obj instanceof String;
            }
            if (str.equalsIgnoreCase(Constants.DOM_SCHEMA_TYPE)) {
                return (obj instanceof String) && (obj.equals(Constants.NS_XMLSCHEMA) || obj.equals(Constants.NS_DTD));
            }
            if (str.equalsIgnoreCase("http://apache.org/xml/properties/internal/entity-resolver")) {
                return obj instanceof XMLEntityResolver;
            }
            if (str.equalsIgnoreCase("http://apache.org/xml/properties/internal/symbol-table")) {
                return obj instanceof SymbolTable;
            }
            if (str.equalsIgnoreCase("http://apache.org/xml/properties/internal/grammar-pool")) {
                return obj instanceof XMLGrammarPool;
            }
            if (str.equalsIgnoreCase(SECURITY_MANAGER)) {
                return obj instanceof SecurityManager;
            }
            return false;
        }
        if (str.equalsIgnoreCase(Constants.DOM_COMMENTS) || str.equalsIgnoreCase(Constants.DOM_DATATYPE_NORMALIZATION) || str.equalsIgnoreCase(Constants.DOM_CDATA_SECTIONS) || str.equalsIgnoreCase(Constants.DOM_ENTITIES) || str.equalsIgnoreCase(Constants.DOM_SPLIT_CDATA) || str.equalsIgnoreCase("namespaces") || str.equalsIgnoreCase(Constants.DOM_VALIDATE) || str.equalsIgnoreCase(Constants.DOM_WELLFORMED) || str.equalsIgnoreCase(Constants.DOM_INFOSET) || str.equalsIgnoreCase(Constants.DOM_NAMESPACE_DECLARATIONS)) {
            return true;
        }
        if (str.equalsIgnoreCase(Constants.DOM_NORMALIZE_CHARACTERS) || str.equalsIgnoreCase(Constants.DOM_CANONICAL_FORM) || str.equalsIgnoreCase(Constants.DOM_VALIDATE_IF_SCHEMA) || str.equalsIgnoreCase(Constants.DOM_CHECK_CHAR_NORMALIZATION)) {
            return !obj.equals(Boolean.TRUE);
        }
        if (str.equalsIgnoreCase(Constants.DOM_ELEMENT_CONTENT_WHITESPACE) || str.equalsIgnoreCase(SEND_PSVI)) {
            return obj.equals(Boolean.TRUE);
        }
        return false;
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings
    protected void checkProperty(String str) throws XMLConfigurationException {
        if (str.startsWith(Constants.SAX_PROPERTY_PREFIX) && str.length() - Constants.SAX_PROPERTY_PREFIX.length() == Constants.XML_STRING_PROPERTY.length() && str.endsWith(Constants.XML_STRING_PROPERTY)) {
            throw new XMLConfigurationException((short) 1, str);
        }
        super.checkProperty(str);
    }

    protected ValidationManager createValidationManager() {
        return new ValidationManager();
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public XMLDTDContentModelHandler getDTDContentModelHandler() {
        return null;
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public XMLDTDHandler getDTDHandler() {
        return null;
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
        if (str.equals("http://apache.org/xml/features/internal/parser-settings")) {
            return true;
        }
        return super.getFeature(str);
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public Locale getLocale() {
        return this.fLocale;
    }

    @Override // org.w3c.dom.DOMConfiguration
    public Object getParameter(String str) throws DOMException {
        if (str.equalsIgnoreCase(Constants.DOM_COMMENTS)) {
            return (this.features & 32) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase("namespaces")) {
            return (this.features & 1) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_DATATYPE_NORMALIZATION)) {
            return (this.features & 2) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_CDATA_SECTIONS)) {
            return (this.features & 8) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_ENTITIES)) {
            return (this.features & 4) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_SPLIT_CDATA)) {
            return (this.features & 16) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_VALIDATE)) {
            return (this.features & 64) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_WELLFORMED)) {
            return (this.features & 256) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_NAMESPACE_DECLARATIONS)) {
            return (this.features & 512) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_INFOSET)) {
            return (this.features & INFOSET_MASK) == 801 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_NORMALIZE_CHARACTERS) || str.equalsIgnoreCase(Constants.DOM_CANONICAL_FORM) || str.equalsIgnoreCase(Constants.DOM_VALIDATE_IF_SCHEMA) || str.equalsIgnoreCase(Constants.DOM_CHECK_CHAR_NORMALIZATION)) {
            return Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(SEND_PSVI)) {
            return Boolean.TRUE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_PSVI)) {
            return (this.features & 128) != 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_ELEMENT_CONTENT_WHITESPACE)) {
            return Boolean.TRUE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_ERROR_HANDLER)) {
            return this.fErrorHandlerWrapper.getErrorHandler();
        }
        if (str.equalsIgnoreCase(Constants.DOM_RESOURCE_RESOLVER)) {
            XMLEntityResolver entityResolver = getEntityResolver();
            if (entityResolver == null || !(entityResolver instanceof DOMEntityResolverWrapper)) {
                return null;
            }
            return ((DOMEntityResolverWrapper) entityResolver).getEntityResolver();
        }
        if (str.equalsIgnoreCase(Constants.DOM_SCHEMA_TYPE)) {
            return getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
        }
        if (str.equalsIgnoreCase(Constants.DOM_SCHEMA_LOCATION)) {
            return this.fSchemaLocation;
        }
        if (str.equalsIgnoreCase("http://apache.org/xml/properties/internal/entity-resolver")) {
            return getEntityResolver();
        }
        if (str.equalsIgnoreCase("http://apache.org/xml/properties/internal/symbol-table")) {
            return getProperty("http://apache.org/xml/properties/internal/symbol-table");
        }
        if (str.equalsIgnoreCase("http://apache.org/xml/properties/internal/grammar-pool")) {
            return getProperty("http://apache.org/xml/properties/internal/grammar-pool");
        }
        if (str.equalsIgnoreCase(SECURITY_MANAGER)) {
            return getProperty(SECURITY_MANAGER);
        }
        throw newFeatureNotFoundError(str);
    }

    @Override // org.w3c.dom.DOMConfiguration
    public DOMStringList getParameterNames() {
        if (this.fRecognizedParameters == null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Constants.DOM_COMMENTS);
            arrayList.add(Constants.DOM_DATATYPE_NORMALIZATION);
            arrayList.add(Constants.DOM_CDATA_SECTIONS);
            arrayList.add(Constants.DOM_ENTITIES);
            arrayList.add(Constants.DOM_SPLIT_CDATA);
            arrayList.add("namespaces");
            arrayList.add(Constants.DOM_VALIDATE);
            arrayList.add(Constants.DOM_INFOSET);
            arrayList.add(Constants.DOM_NORMALIZE_CHARACTERS);
            arrayList.add(Constants.DOM_CANONICAL_FORM);
            arrayList.add(Constants.DOM_VALIDATE_IF_SCHEMA);
            arrayList.add(Constants.DOM_CHECK_CHAR_NORMALIZATION);
            arrayList.add(Constants.DOM_WELLFORMED);
            arrayList.add(Constants.DOM_NAMESPACE_DECLARATIONS);
            arrayList.add(Constants.DOM_ELEMENT_CONTENT_WHITESPACE);
            arrayList.add(Constants.DOM_ERROR_HANDLER);
            arrayList.add(Constants.DOM_SCHEMA_TYPE);
            arrayList.add(Constants.DOM_SCHEMA_LOCATION);
            arrayList.add(Constants.DOM_RESOURCE_RESOLVER);
            arrayList.add("http://apache.org/xml/properties/internal/entity-resolver");
            arrayList.add("http://apache.org/xml/properties/internal/grammar-pool");
            arrayList.add(SECURITY_MANAGER);
            arrayList.add("http://apache.org/xml/properties/internal/symbol-table");
            arrayList.add(SEND_PSVI);
            this.fRecognizedParameters = new DOMStringListImpl(arrayList);
        }
        return this.fRecognizedParameters;
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void parse(XMLInputSource xMLInputSource) throws IOException, XNIException {
    }

    protected void reset() throws XNIException {
        ValidationManager validationManager = this.fValidationManager;
        if (validationManager != null) {
            validationManager.reset();
        }
        int size = this.fComponents.size();
        for (int i = 0; i < size; i++) {
            ((XMLComponent) this.fComponents.get(i)).reset(this);
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setDTDContentModelHandler(XMLDTDContentModelHandler xMLDTDContentModelHandler) {
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setDTDHandler(XMLDTDHandler xMLDTDHandler) {
    }

    protected final void setDTDValidatorFactory(String str) {
        DTDDVFactory dTDDVFactory;
        if (CompilerOptions.VERSION_1_1.equals(str)) {
            DTDDVFactory dTDDVFactory2 = this.fCurrentDVFactory;
            dTDDVFactory = this.fXML11DatatypeFactory;
            if (dTDDVFactory2 == dTDDVFactory) {
                return;
            }
        } else {
            DTDDVFactory dTDDVFactory3 = this.fCurrentDVFactory;
            dTDDVFactory = this.fDatatypeValidatorFactory;
            if (dTDDVFactory3 == dTDDVFactory) {
                return;
            }
        }
        this.fCurrentDVFactory = dTDDVFactory;
        setProperty(DTD_VALIDATOR_FACTORY_PROPERTY, dTDDVFactory);
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setDocumentHandler(XMLDocumentHandler xMLDocumentHandler) {
        this.fDocumentHandler = xMLDocumentHandler;
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setEntityResolver(XMLEntityResolver xMLEntityResolver) {
        this.fProperties.put("http://apache.org/xml/properties/internal/entity-resolver", xMLEntityResolver);
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setErrorHandler(XMLErrorHandler xMLErrorHandler) {
        if (xMLErrorHandler != null) {
            this.fProperties.put(ERROR_HANDLER, xMLErrorHandler);
        }
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
        super.setFeature(str, z);
    }

    @Override // org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setLocale(Locale locale) throws XNIException {
        this.fLocale = locale;
        this.fErrorReporter.setLocale(locale);
    }

    /* JADX WARN: Code duplicated, block: B:183:0x027b A[ORIG_RETURN, RETURN] */
    @Override // org.w3c.dom.DOMConfiguration
    public void setParameter(String str, Object obj) throws DOMException {
        String str2;
        Object array;
        int i;
        boolean z = obj instanceof Boolean;
        if (z) {
            boolean zBooleanValue = ((Boolean) obj).booleanValue();
            if (str.equalsIgnoreCase(Constants.DOM_COMMENTS)) {
                i = zBooleanValue ? this.features | 32 : this.features & (-33);
            } else if (str.equalsIgnoreCase(Constants.DOM_DATATYPE_NORMALIZATION)) {
                setFeature(NORMALIZE_DATA, zBooleanValue);
                short s = this.features;
                short s2 = (short) (zBooleanValue ? s | 2 : s & (-3));
                this.features = s2;
                if (zBooleanValue) {
                    i = s2 | 64;
                }
                if (z) {
                    return;
                }
            } else if (str.equalsIgnoreCase("namespaces")) {
                i = zBooleanValue ? this.features | 1 : this.features & (-2);
            } else if (str.equalsIgnoreCase(Constants.DOM_CDATA_SECTIONS)) {
                i = zBooleanValue ? this.features | 8 : this.features & (-9);
            } else if (str.equalsIgnoreCase(Constants.DOM_ENTITIES)) {
                i = zBooleanValue ? this.features | 4 : this.features & (-5);
            } else if (str.equalsIgnoreCase(Constants.DOM_SPLIT_CDATA)) {
                i = zBooleanValue ? this.features | 16 : this.features & (-17);
            } else if (str.equalsIgnoreCase(Constants.DOM_VALIDATE)) {
                i = zBooleanValue ? this.features | 64 : this.features & (-65);
            } else if (str.equalsIgnoreCase(Constants.DOM_WELLFORMED)) {
                i = zBooleanValue ? this.features | 256 : this.features & (-257);
            } else if (str.equalsIgnoreCase(Constants.DOM_NAMESPACE_DECLARATIONS)) {
                i = zBooleanValue ? this.features | 512 : this.features & (-513);
            } else {
                if (str.equalsIgnoreCase(Constants.DOM_INFOSET)) {
                    if (zBooleanValue) {
                        this.features = (short) (((short) (this.features | INFOSET_TRUE_PARAMS)) & (-15));
                        setFeature(NORMALIZE_DATA, false);
                    }
                } else if (str.equalsIgnoreCase(Constants.DOM_NORMALIZE_CHARACTERS) || str.equalsIgnoreCase(Constants.DOM_CANONICAL_FORM) || str.equalsIgnoreCase(Constants.DOM_VALIDATE_IF_SCHEMA) || str.equalsIgnoreCase(Constants.DOM_CHECK_CHAR_NORMALIZATION)) {
                    if (zBooleanValue) {
                        throw newFeatureNotSupportedError(str);
                    }
                } else if (str.equalsIgnoreCase(Constants.DOM_ELEMENT_CONTENT_WHITESPACE)) {
                    if (!zBooleanValue) {
                        throw newFeatureNotSupportedError(str);
                    }
                } else if (str.equalsIgnoreCase(SEND_PSVI)) {
                    if (!zBooleanValue) {
                        throw newFeatureNotSupportedError(str);
                    }
                } else if (str.equalsIgnoreCase(Constants.DOM_PSVI)) {
                    i = zBooleanValue ? this.features | 128 : this.features & (-129);
                }
                if (z) {
                    return;
                }
            }
            this.features = (short) i;
            if (z) {
                return;
            }
        } else if (z) {
            return;
        }
        if (str.equalsIgnoreCase(Constants.DOM_ERROR_HANDLER)) {
            if (!(obj instanceof DOMErrorHandler) && obj != null) {
                throw newTypeMismatchError(str);
            }
            this.fErrorHandlerWrapper.setErrorHandler((DOMErrorHandler) obj);
            setErrorHandler(this.fErrorHandlerWrapper);
            return;
        }
        try {
            if (str.equalsIgnoreCase(Constants.DOM_RESOURCE_RESOLVER)) {
                if (!(obj instanceof LSResourceResolver) && obj != null) {
                    throw newTypeMismatchError(str);
                }
                setEntityResolver(new DOMEntityResolverWrapper((LSResourceResolver) obj));
                return;
            }
            if (str.equalsIgnoreCase(Constants.DOM_SCHEMA_LOCATION)) {
                if (!(obj instanceof String) && obj != null) {
                    throw newTypeMismatchError(str);
                }
                str2 = "http://java.sun.com/xml/jaxp/properties/schemaSource";
                if (obj == null) {
                    this.fSchemaLocation = null;
                    setProperty(str2, null);
                    return;
                }
                this.fSchemaLocation = (String) obj;
                StringTokenizer stringTokenizer = new StringTokenizer(this.fSchemaLocation, " \n\t\r");
                if (!stringTokenizer.hasMoreTokens()) {
                    setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", new String[]{(String) obj});
                    return;
                }
                ArrayList arrayList = new ArrayList();
                do {
                    arrayList.add(stringTokenizer.nextToken());
                } while (stringTokenizer.hasMoreTokens());
                array = arrayList.toArray(new String[arrayList.size()]);
                setProperty(str2, array);
                return;
            }
            if (str.equalsIgnoreCase(Constants.DOM_SCHEMA_TYPE)) {
                if (!(obj instanceof String) && obj != null) {
                    throw newTypeMismatchError(str);
                }
                str2 = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
                if (obj != null) {
                    if (obj.equals(Constants.NS_XMLSCHEMA)) {
                        array = Constants.NS_XMLSCHEMA;
                    } else if (!obj.equals(Constants.NS_DTD)) {
                        return;
                    } else {
                        array = Constants.NS_DTD;
                    }
                    setProperty(str2, array);
                    return;
                }
                setProperty(str2, null);
                return;
            }
            if (str.equalsIgnoreCase("http://apache.org/xml/properties/internal/entity-resolver")) {
                if (!(obj instanceof XMLEntityResolver) && obj != null) {
                    throw newTypeMismatchError(str);
                }
                setEntityResolver((XMLEntityResolver) obj);
                return;
            }
            String str3 = "http://apache.org/xml/properties/internal/symbol-table";
            if (!str.equalsIgnoreCase("http://apache.org/xml/properties/internal/symbol-table")) {
                str3 = "http://apache.org/xml/properties/internal/grammar-pool";
                if (!str.equalsIgnoreCase("http://apache.org/xml/properties/internal/grammar-pool")) {
                    str3 = SECURITY_MANAGER;
                    if (!str.equalsIgnoreCase(SECURITY_MANAGER)) {
                        throw newFeatureNotFoundError(str);
                    }
                    if (!(obj instanceof SecurityManager) && obj != null) {
                        throw newTypeMismatchError(str);
                    }
                } else if (!(obj instanceof XMLGrammarPool) && obj != null) {
                    throw newTypeMismatchError(str);
                }
            } else if (!(obj instanceof SymbolTable)) {
                throw newTypeMismatchError(str);
            }
            setProperty(str3, obj);
        } catch (XMLConfigurationException unused) {
        }
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        super.setProperty(str, obj);
    }
}
