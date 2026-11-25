package org.apache.xerces.jaxp.validation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.xml.XMLConstants;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.util.DOMEntityResolverWrapper;
import org.apache.xerces.util.ErrorHandlerWrapper;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.ParserConfigurationSettings;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;

/* JADX INFO: loaded from: classes13.dex */
final class XMLSchemaValidatorComponentManager extends ParserConfigurationSettings implements XMLComponentManager {
    private static final String DISALLOW_DOCTYPE_DECL_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
    private static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
    private static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    private static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
    private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    private static final String IDENTITY_CONSTRAINT_CHECKING = "http://apache.org/xml/features/validation/identity-constraint-checking";
    private static final String ID_IDREF_CHECKING = "http://apache.org/xml/features/validation/id-idref-checking";
    private static final String IGNORE_XSI_TYPE = "http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl";
    private static final String LOCALE = "http://apache.org/xml/properties/locale";
    private static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
    private static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
    private static final String SCHEMA_AUGMENT_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
    private static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
    private static final String SCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
    private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
    private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
    private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    private static final String UNPARSED_ENTITY_CHECKING = "http://apache.org/xml/features/validation/unparsed-entity-checking";
    private static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
    private static final String VALIDATION = "http://xml.org/sax/features/validation";
    private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    private static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
    private final HashMap fComponents;
    private boolean fConfigUpdated = true;
    private final XMLEntityManager fEntityManager;
    private ErrorHandler fErrorHandler;
    private final XMLErrorReporter fErrorReporter;
    private final HashMap fInitFeatures;
    private final HashMap fInitProperties;
    private final SecurityManager fInitSecurityManager;
    private Locale fLocale;
    private final NamespaceContext fNamespaceContext;
    private LSResourceResolver fResourceResolver;
    private final XMLSchemaValidator fSchemaValidator;
    private boolean fUseGrammarPoolOnly;
    private final ValidationManager fValidationManager;

    public XMLSchemaValidatorComponentManager(XSGrammarPoolContainer xSGrammarPoolContainer) {
        HashMap map = new HashMap();
        this.fComponents = map;
        this.fInitFeatures = new HashMap();
        this.fInitProperties = new HashMap();
        this.fErrorHandler = null;
        this.fResourceResolver = null;
        this.fLocale = null;
        XMLEntityManager xMLEntityManager = new XMLEntityManager();
        this.fEntityManager = xMLEntityManager;
        map.put(ENTITY_MANAGER, xMLEntityManager);
        XMLErrorReporter xMLErrorReporter = new XMLErrorReporter();
        this.fErrorReporter = xMLErrorReporter;
        map.put("http://apache.org/xml/properties/internal/error-reporter", xMLErrorReporter);
        NamespaceSupport namespaceSupport = new NamespaceSupport();
        this.fNamespaceContext = namespaceSupport;
        map.put(NAMESPACE_CONTEXT, namespaceSupport);
        XMLSchemaValidator xMLSchemaValidator = new XMLSchemaValidator();
        this.fSchemaValidator = xMLSchemaValidator;
        map.put(SCHEMA_VALIDATOR, xMLSchemaValidator);
        ValidationManager validationManager = new ValidationManager();
        this.fValidationManager = validationManager;
        map.put(VALIDATION_MANAGER, validationManager);
        map.put("http://apache.org/xml/properties/internal/entity-resolver", null);
        map.put(ERROR_HANDLER, null);
        map.put(SECURITY_MANAGER, null);
        map.put("http://apache.org/xml/properties/internal/symbol-table", new SymbolTable());
        map.put("http://apache.org/xml/properties/internal/grammar-pool", xSGrammarPoolContainer.getGrammarPool());
        this.fUseGrammarPoolOnly = xSGrammarPoolContainer.isFullyComposed();
        xMLErrorReporter.putMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN, new XSMessageFormatter());
        addRecognizedFeatures(new String[]{DISALLOW_DOCTYPE_DECL_FEATURE, NORMALIZE_DATA, SCHEMA_ELEMENT_DEFAULT, SCHEMA_AUGMENT_PSVI});
        this.fFeatures.put(DISALLOW_DOCTYPE_DECL_FEATURE, Boolean.FALSE);
        this.fFeatures.put(NORMALIZE_DATA, Boolean.FALSE);
        this.fFeatures.put(SCHEMA_ELEMENT_DEFAULT, Boolean.FALSE);
        this.fFeatures.put(SCHEMA_AUGMENT_PSVI, Boolean.TRUE);
        addRecognizedParamsAndSetDefaults(xMLEntityManager, xSGrammarPoolContainer);
        addRecognizedParamsAndSetDefaults(xMLErrorReporter, xSGrammarPoolContainer);
        addRecognizedParamsAndSetDefaults(xMLSchemaValidator, xSGrammarPoolContainer);
        if (Boolean.TRUE.equals(xSGrammarPoolContainer.getFeature(XMLConstants.FEATURE_SECURE_PROCESSING))) {
            this.fInitSecurityManager = new SecurityManager();
        } else {
            this.fInitSecurityManager = null;
        }
        map.put(SECURITY_MANAGER, this.fInitSecurityManager);
        this.fFeatures.put(IGNORE_XSI_TYPE, Boolean.FALSE);
        this.fFeatures.put(ID_IDREF_CHECKING, Boolean.TRUE);
        this.fFeatures.put(IDENTITY_CONSTRAINT_CHECKING, Boolean.TRUE);
        this.fFeatures.put(UNPARSED_ENTITY_CHECKING, Boolean.TRUE);
    }

    private void setFeatureDefaults(XMLComponent xMLComponent, String[] strArr, XSGrammarPoolContainer xSGrammarPoolContainer) {
        if (strArr != null) {
            for (String str : strArr) {
                Boolean feature = xSGrammarPoolContainer.getFeature(str);
                if (feature == null) {
                    feature = xMLComponent.getFeatureDefault(str);
                }
                if (feature != null && !this.fFeatures.containsKey(str)) {
                    this.fFeatures.put(str, feature);
                    this.fConfigUpdated = true;
                }
            }
        }
    }

    private void setPropertyDefaults(XMLComponent xMLComponent, String[] strArr) {
        if (strArr != null) {
            for (String str : strArr) {
                Object propertyDefault = xMLComponent.getPropertyDefault(str);
                if (propertyDefault != null && !this.fProperties.containsKey(str)) {
                    this.fProperties.put(str, propertyDefault);
                    this.fConfigUpdated = true;
                }
            }
        }
    }

    public void addRecognizedParamsAndSetDefaults(XMLComponent xMLComponent, XSGrammarPoolContainer xSGrammarPoolContainer) {
        String[] recognizedFeatures = xMLComponent.getRecognizedFeatures();
        addRecognizedFeatures(recognizedFeatures);
        String[] recognizedProperties = xMLComponent.getRecognizedProperties();
        addRecognizedProperties(recognizedProperties);
        setFeatureDefaults(xMLComponent, recognizedFeatures, xSGrammarPoolContainer);
        setPropertyDefaults(xMLComponent, recognizedProperties);
    }

    ErrorHandler getErrorHandler() {
        return this.fErrorHandler;
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLComponentManager, org.apache.xerces.xni.parser.XMLParserConfiguration
    public boolean getFeature(String str) throws XMLConfigurationException {
        if ("http://apache.org/xml/features/internal/parser-settings".equals(str)) {
            return this.fConfigUpdated;
        }
        if (VALIDATION.equals(str) || SCHEMA_VALIDATION.equals(str)) {
            return true;
        }
        if (USE_GRAMMAR_POOL_ONLY.equals(str)) {
            return this.fUseGrammarPoolOnly;
        }
        if (XMLConstants.FEATURE_SECURE_PROCESSING.equals(str)) {
            return getProperty(SECURITY_MANAGER) != null;
        }
        return super.getFeature(str);
    }

    Locale getLocale() {
        return this.fLocale;
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLComponentManager, org.apache.xerces.xni.parser.XMLParserConfiguration
    public Object getProperty(String str) throws XMLConfigurationException {
        if (LOCALE.equals(str)) {
            return getLocale();
        }
        Object obj = this.fComponents.get(str);
        if (obj != null) {
            return obj;
        }
        if (this.fComponents.containsKey(str)) {
            return null;
        }
        return super.getProperty(str);
    }

    LSResourceResolver getResourceResolver() {
        return this.fResourceResolver;
    }

    public void reset() throws XNIException {
        this.fNamespaceContext.reset();
        this.fValidationManager.reset();
        this.fEntityManager.reset(this);
        this.fErrorReporter.reset(this);
        this.fSchemaValidator.reset(this);
        this.fConfigUpdated = false;
    }

    void restoreInitialState() {
        this.fConfigUpdated = true;
        this.fComponents.put("http://apache.org/xml/properties/internal/entity-resolver", null);
        this.fComponents.put(ERROR_HANDLER, null);
        this.fComponents.put(SECURITY_MANAGER, this.fInitSecurityManager);
        setLocale(null);
        this.fComponents.put(LOCALE, null);
        if (!this.fInitFeatures.isEmpty()) {
            for (Map.Entry entry : this.fInitFeatures.entrySet()) {
                super.setFeature((String) entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
            }
            this.fInitFeatures.clear();
        }
        if (this.fInitProperties.isEmpty()) {
            return;
        }
        for (Map.Entry entry2 : this.fInitProperties.entrySet()) {
            super.setProperty((String) entry2.getKey(), entry2.getValue());
        }
        this.fInitProperties.clear();
    }

    void setErrorHandler(ErrorHandler errorHandler) {
        this.fErrorHandler = errorHandler;
        setProperty(ERROR_HANDLER, errorHandler != null ? new ErrorHandlerWrapper(errorHandler) : new ErrorHandlerWrapper(DraconianErrorHandler.getInstance()));
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
        if ("http://apache.org/xml/features/internal/parser-settings".equals(str)) {
            throw new XMLConfigurationException((short) 1, str);
        }
        if (!z && (VALIDATION.equals(str) || SCHEMA_VALIDATION.equals(str))) {
            throw new XMLConfigurationException((short) 1, str);
        }
        if (USE_GRAMMAR_POOL_ONLY.equals(str) && z != this.fUseGrammarPoolOnly) {
            throw new XMLConfigurationException((short) 1, str);
        }
        if (XMLConstants.FEATURE_SECURE_PROCESSING.equals(str)) {
            setProperty(SECURITY_MANAGER, z ? new SecurityManager() : null);
            return;
        }
        this.fConfigUpdated = true;
        this.fEntityManager.setFeature(str, z);
        this.fErrorReporter.setFeature(str, z);
        this.fSchemaValidator.setFeature(str, z);
        if (!this.fInitFeatures.containsKey(str)) {
            this.fInitFeatures.put(str, super.getFeature(str) ? Boolean.TRUE : Boolean.FALSE);
        }
        super.setFeature(str, z);
    }

    void setLocale(Locale locale) {
        this.fLocale = locale;
        this.fErrorReporter.setLocale(locale);
    }

    @Override // org.apache.xerces.util.ParserConfigurationSettings, org.apache.xerces.xni.parser.XMLParserConfiguration
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        if (ENTITY_MANAGER.equals(str) || "http://apache.org/xml/properties/internal/error-reporter".equals(str) || NAMESPACE_CONTEXT.equals(str) || SCHEMA_VALIDATOR.equals(str) || "http://apache.org/xml/properties/internal/symbol-table".equals(str) || VALIDATION_MANAGER.equals(str) || "http://apache.org/xml/properties/internal/grammar-pool".equals(str)) {
            throw new XMLConfigurationException((short) 1, str);
        }
        this.fConfigUpdated = true;
        this.fEntityManager.setProperty(str, obj);
        this.fErrorReporter.setProperty(str, obj);
        this.fSchemaValidator.setProperty(str, obj);
        if (!"http://apache.org/xml/properties/internal/entity-resolver".equals(str) && !ERROR_HANDLER.equals(str) && !SECURITY_MANAGER.equals(str)) {
            if (!LOCALE.equals(str)) {
                if (!this.fInitProperties.containsKey(str)) {
                    this.fInitProperties.put(str, super.getProperty(str));
                }
                super.setProperty(str, obj);
                return;
            }
            setLocale((Locale) obj);
        }
        this.fComponents.put(str, obj);
    }

    void setResourceResolver(LSResourceResolver lSResourceResolver) {
        this.fResourceResolver = lSResourceResolver;
        setProperty("http://apache.org/xml/properties/internal/entity-resolver", new DOMEntityResolverWrapper(lSResourceResolver));
    }
}
