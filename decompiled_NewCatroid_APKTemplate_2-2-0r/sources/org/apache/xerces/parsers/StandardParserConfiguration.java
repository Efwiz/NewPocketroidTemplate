package org.apache.xerces.parsers;

import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;

/* JADX INFO: loaded from: classes13.dex */
public class StandardParserConfiguration extends DTDConfiguration {
    protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
    protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
    protected static final String IDENTITY_CONSTRAINT_CHECKING = "http://apache.org/xml/features/validation/identity-constraint-checking";
    protected static final String ID_IDREF_CHECKING = "http://apache.org/xml/features/validation/id-idref-checking";
    protected static final String IGNORE_XSI_TYPE = "http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl";
    protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
    protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
    protected static final String ROOT_ELEMENT_DECL = "http://apache.org/xml/properties/validation/schema/root-element-declaration";
    protected static final String ROOT_TYPE_DEF = "http://apache.org/xml/properties/validation/schema/root-type-definition";
    protected static final String SCHEMA_AUGMENT_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
    protected static final String SCHEMA_DV_FACTORY = "http://apache.org/xml/properties/internal/validation/schema/dv-factory";
    protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
    protected static final String SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-schemaLocation";
    protected static final String SCHEMA_NONS_LOCATION = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
    protected static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
    protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
    protected static final String UNPARSED_ENTITY_CHECKING = "http://apache.org/xml/features/validation/unparsed-entity-checking";
    protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
    protected static final String XMLSCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
    protected static final String XMLSCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
    protected XMLSchemaValidator fSchemaValidator;

    public StandardParserConfiguration() {
        this(null, null, null);
    }

    public StandardParserConfiguration(SymbolTable symbolTable) {
        this(symbolTable, null, null);
    }

    public StandardParserConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool) {
        this(symbolTable, xMLGrammarPool, null);
    }

    public StandardParserConfiguration(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool, XMLComponentManager xMLComponentManager) {
        super(symbolTable, xMLGrammarPool, xMLComponentManager);
        addRecognizedFeatures(new String[]{NORMALIZE_DATA, SCHEMA_ELEMENT_DEFAULT, SCHEMA_AUGMENT_PSVI, "http://apache.org/xml/features/generate-synthetic-annotations", VALIDATE_ANNOTATIONS, HONOUR_ALL_SCHEMALOCATIONS, NAMESPACE_GROWTH, TOLERATE_DUPLICATES, XMLSCHEMA_VALIDATION, XMLSCHEMA_FULL_CHECKING, IGNORE_XSI_TYPE, ID_IDREF_CHECKING, IDENTITY_CONSTRAINT_CHECKING, UNPARSED_ENTITY_CHECKING});
        setFeature(SCHEMA_ELEMENT_DEFAULT, true);
        setFeature(NORMALIZE_DATA, true);
        setFeature(SCHEMA_AUGMENT_PSVI, true);
        setFeature("http://apache.org/xml/features/generate-synthetic-annotations", false);
        setFeature(VALIDATE_ANNOTATIONS, false);
        setFeature(HONOUR_ALL_SCHEMALOCATIONS, false);
        setFeature(NAMESPACE_GROWTH, false);
        setFeature(TOLERATE_DUPLICATES, false);
        setFeature(IGNORE_XSI_TYPE, false);
        setFeature(ID_IDREF_CHECKING, true);
        setFeature(IDENTITY_CONSTRAINT_CHECKING, true);
        setFeature(UNPARSED_ENTITY_CHECKING, true);
        addRecognizedProperties(new String[]{SCHEMA_LOCATION, SCHEMA_NONS_LOCATION, ROOT_TYPE_DEF, ROOT_ELEMENT_DECL, SCHEMA_DV_FACTORY});
    }

    @Override // org.apache.xerces.parsers.DTDConfiguration, org.apache.xerces.parsers.BasicParserConfiguration, org.apache.xerces.util.ParserConfigurationSettings
    protected void checkFeature(String str) throws XMLConfigurationException {
        if (str.startsWith(Constants.XERCES_FEATURE_PREFIX)) {
            int length = str.length() - Constants.XERCES_FEATURE_PREFIX.length();
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
        }
        super.checkFeature(str);
    }

    @Override // org.apache.xerces.parsers.DTDConfiguration, org.apache.xerces.parsers.BasicParserConfiguration, org.apache.xerces.util.ParserConfigurationSettings
    protected void checkProperty(String str) throws XMLConfigurationException {
        if (str.startsWith(Constants.XERCES_PROPERTY_PREFIX)) {
            int length = str.length() - Constants.XERCES_PROPERTY_PREFIX.length();
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
        super.checkProperty(str);
    }

    @Override // org.apache.xerces.parsers.DTDConfiguration
    protected void configurePipeline() {
        super.configurePipeline();
        if (getFeature(XMLSCHEMA_VALIDATION)) {
            if (this.fSchemaValidator == null) {
                this.fSchemaValidator = new XMLSchemaValidator();
                this.fProperties.put(SCHEMA_VALIDATOR, this.fSchemaValidator);
                addComponent(this.fSchemaValidator);
                if (this.fErrorReporter.getMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN) == null) {
                    this.fErrorReporter.putMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN, new XSMessageFormatter());
                }
            }
            this.fLastComponent = this.fSchemaValidator;
            this.fNamespaceBinder.setDocumentHandler(this.fSchemaValidator);
            this.fSchemaValidator.setDocumentHandler(this.fDocumentHandler);
            this.fSchemaValidator.setDocumentSource(this.fNamespaceBinder);
        }
    }
}
