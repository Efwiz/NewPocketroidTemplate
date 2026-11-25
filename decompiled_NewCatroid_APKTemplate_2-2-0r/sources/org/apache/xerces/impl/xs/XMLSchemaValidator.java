package org.apache.xerces.impl.xs;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import javax.xml.namespace.QName;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.RevalidationHandler;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.dv.DatatypeException;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.impl.validation.ConfigurableValidationState;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.validation.ValidationState;
import org.apache.xerces.impl.xs.identity.Field;
import org.apache.xerces.impl.xs.identity.FieldActivator;
import org.apache.xerces.impl.xs.identity.IdentityConstraint;
import org.apache.xerces.impl.xs.identity.KeyRef;
import org.apache.xerces.impl.xs.identity.Selector;
import org.apache.xerces.impl.xs.identity.UniqueOrKey;
import org.apache.xerces.impl.xs.identity.ValueStore;
import org.apache.xerces.impl.xs.identity.XPathMatcher;
import org.apache.xerces.impl.xs.models.CMBuilder;
import org.apache.xerces.impl.xs.models.CMNodeFactory;
import org.apache.xerces.impl.xs.models.XSCMValidator;
import org.apache.xerces.impl.xs.util.XS10TypeHelper;
import org.apache.xerces.util.AugmentationsImpl;
import org.apache.xerces.util.IntStack;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.URI;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTypeDefinition;

/* JADX INFO: loaded from: classes13.dex */
public class XMLSchemaValidator implements XMLComponent, XMLDocumentFilter, FieldActivator, RevalidationHandler, XSElementDeclHelper {
    private static final int BUFFER_SIZE = 20;
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_NORMALIZATION = false;
    protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
    public static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    public static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
    protected static final int ID_CONSTRAINT_NUM = 1;
    static final int INC_STACK_SIZE = 8;
    static final int INITIAL_STACK_SIZE = 8;
    protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
    protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
    protected static final String SCHEMA_AUGMENT_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
    protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
    public static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    public static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
    private boolean fAppendBuffer;
    private final StringBuffer fBuffer;
    private final CMBuilder fCMBuilder;
    private XSCMValidator[] fCMStack;
    private int[][] fCMStateStack;
    private int[] fCurrCMState;
    private XSCMValidator fCurrentCM;
    private XSElementDecl fCurrentElemDecl;
    private XSTypeDefinition fCurrentType;
    protected XMLString fDefaultValue;
    protected XMLDocumentHandler fDocumentHandler;
    protected XMLDocumentSource fDocumentSource;
    private XSElementDecl[] fElemDeclStack;
    private int fElementDepth;
    private final XMLString fEmptyXMLStr;
    protected XMLEntityResolver fEntityResolver;
    protected final Hashtable fExpandedLocationPairs;
    protected String fExternalNoNamespaceSchema;
    protected String fExternalSchemas;
    private boolean fFirstChunk;
    private final XSGrammarBucket fGrammarBucket;
    protected XMLGrammarPool fGrammarPool;
    private boolean fIDCChecking;
    private int fIgnoreXSITypeDepth;
    protected Object fJaxpSchemaSource;
    protected final Hashtable fLocationPairs;
    private XMLLocator fLocator;
    protected XPathMatcherStack fMatcherStack;
    private int fNFullValidationDepth;
    private int fNNoneValidationDepth;
    private boolean fNil;
    private boolean[] fNilStack;
    private final XMLString fNormalizedStr;
    private XSNotationDecl fNotation;
    private XSNotationDecl[] fNotationStack;
    private final XSSimpleType fQNameDV;
    private QName fRootElementDeclQName;
    private XSElementDecl fRootElementDeclaration;
    private XSTypeDefinition fRootTypeDefinition;
    private QName fRootTypeQName;
    private boolean fSawCharacters;
    private boolean fSawText;
    private boolean[] fSawTextStack;
    private final XMLSchemaLoader fSchemaLoader;
    private int fSkipValidationDepth;
    private ValidationState fState4ApplyDefault;
    private ValidationState fState4XsiType;
    private boolean fStrictAssess;
    private boolean[] fStrictAssessStack;
    private boolean[] fStringContent;
    private boolean fSubElement;
    private boolean[] fSubElementStack;
    private final SubstitutionGroupHandler fSubGroupHandler;
    protected SymbolTable fSymbolTable;
    private final org.apache.xerces.xni.QName fTempQName;
    private boolean fTrailing;
    private XSTypeDefinition[] fTypeStack;
    private boolean fUnionType;
    protected final ArrayList fUnparsedLocations;
    private ValidatedInfo fValidatedInfo;
    protected ValidationManager fValidationManager;
    private String fValidationRoot;
    protected ConfigurableValidationState fValidationState;
    protected ValueStoreCache fValueStoreCache;
    private short fWhiteSpace;
    protected final XSDDescription fXSDDescription;
    protected final XSIErrorReporter fXSIErrorReporter;
    private final CMNodeFactory nodeFactory;
    protected static final String VALIDATION = "http://xml.org/sax/features/validation";
    protected static final String SCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
    protected static final String DYNAMIC_VALIDATION = "http://apache.org/xml/features/validation/dynamic";
    protected static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
    protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
    protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
    protected static final String STANDARD_URI_CONFORMANT_FEATURE = "http://apache.org/xml/features/standard-uri-conformant";
    protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
    protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
    protected static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
    protected static final String IGNORE_XSI_TYPE = "http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl";
    protected static final String ID_IDREF_CHECKING = "http://apache.org/xml/features/validation/id-idref-checking";
    protected static final String IDENTITY_CONSTRAINT_CHECKING = "http://apache.org/xml/features/validation/identity-constraint-checking";
    protected static final String UNPARSED_ENTITY_CHECKING = "http://apache.org/xml/features/validation/unparsed-entity-checking";
    protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
    protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
    private static final String[] RECOGNIZED_FEATURES = {VALIDATION, SCHEMA_VALIDATION, DYNAMIC_VALIDATION, SCHEMA_FULL_CHECKING, ALLOW_JAVA_ENCODINGS, CONTINUE_AFTER_FATAL_ERROR, STANDARD_URI_CONFORMANT_FEATURE, "http://apache.org/xml/features/generate-synthetic-annotations", VALIDATE_ANNOTATIONS, HONOUR_ALL_SCHEMALOCATIONS, USE_GRAMMAR_POOL_ONLY, IGNORE_XSI_TYPE, ID_IDREF_CHECKING, IDENTITY_CONSTRAINT_CHECKING, UNPARSED_ENTITY_CHECKING, NAMESPACE_GROWTH, TOLERATE_DUPLICATES};
    private static final Boolean[] FEATURE_DEFAULTS = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
    protected static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    protected static final String SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-schemaLocation";
    protected static final String SCHEMA_NONS_LOCATION = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
    protected static final String ROOT_TYPE_DEF = "http://apache.org/xml/properties/validation/schema/root-type-definition";
    protected static final String ROOT_ELEMENT_DECL = "http://apache.org/xml/properties/validation/schema/root-element-declaration";
    protected static final String SCHEMA_DV_FACTORY = "http://apache.org/xml/properties/internal/validation/schema/dv-factory";
    private static final String[] RECOGNIZED_PROPERTIES = {"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/entity-resolver", VALIDATION_MANAGER, SCHEMA_LOCATION, SCHEMA_NONS_LOCATION, "http://java.sun.com/xml/jaxp/properties/schemaSource", "http://java.sun.com/xml/jaxp/properties/schemaLanguage", ROOT_TYPE_DEF, ROOT_ELEMENT_DECL, SCHEMA_DV_FACTORY};
    private static final Object[] PROPERTY_DEFAULTS = {null, null, null, null, null, null, null, null, null, null, null};
    static final XSAttributeDecl XSI_TYPE = SchemaGrammar.SG_XSI.getGlobalAttributeDecl(SchemaSymbols.XSI_TYPE);
    static final XSAttributeDecl XSI_NIL = SchemaGrammar.SG_XSI.getGlobalAttributeDecl(SchemaSymbols.XSI_NIL);
    static final XSAttributeDecl XSI_SCHEMALOCATION = SchemaGrammar.SG_XSI.getGlobalAttributeDecl(SchemaSymbols.XSI_SCHEMALOCATION);
    static final XSAttributeDecl XSI_NONAMESPACESCHEMALOCATION = SchemaGrammar.SG_XSI.getGlobalAttributeDecl(SchemaSymbols.XSI_NONAMESPACESCHEMALOCATION);
    private static final Hashtable EMPTY_TABLE = new Hashtable();
    protected ElementPSVImpl fCurrentPSVI = new ElementPSVImpl();
    protected final AugmentationsImpl fAugmentations = new AugmentationsImpl();
    protected boolean fDynamicValidation = false;
    protected boolean fSchemaDynamicValidation = false;
    protected boolean fDoValidation = false;
    protected boolean fFullChecking = false;
    protected boolean fNormalizeData = true;
    protected boolean fSchemaElementDefault = true;
    protected boolean fAugPSVI = true;
    protected boolean fIdConstraint = false;
    protected boolean fUseGrammarPoolOnly = false;
    protected boolean fNamespaceGrowth = false;
    private String fSchemaType = null;
    protected boolean fEntityRef = false;
    protected boolean fInCDATA = false;

    protected class KeyRefValueStore extends ValueStoreBase {
        protected ValueStoreBase fKeyValueStore;

        public KeyRefValueStore(KeyRef keyRef, KeyValueStore keyValueStore) {
            super(keyRef);
            this.fKeyValueStore = keyValueStore;
        }

        @Override // org.apache.xerces.impl.xs.XMLSchemaValidator.ValueStoreBase
        public void endDocument() {
            super.endDocument();
        }

        @Override // org.apache.xerces.impl.xs.XMLSchemaValidator.ValueStoreBase
        public void endDocumentFragment() {
            super.endDocumentFragment();
            ValueStoreBase valueStoreBase = (ValueStoreBase) XMLSchemaValidator.this.fValueStoreCache.fGlobalIDConstraintMap.get(((KeyRef) this.fIdentityConstraint).getKey());
            this.fKeyValueStore = valueStoreBase;
            if (valueStoreBase == null) {
                XMLSchemaValidator.this.reportSchemaError("KeyRefOutOfScope", new Object[]{this.fIdentityConstraint.getName()});
                return;
            }
            int iContains = valueStoreBase.contains(this);
            if (iContains != -1) {
                String string = toString(this.fValues, iContains, this.fFieldCount);
                String elementName = this.fIdentityConstraint.getElementName();
                XMLSchemaValidator.this.reportSchemaError("KeyNotFound", new Object[]{this.fIdentityConstraint.getName(), string, elementName});
            }
        }
    }

    protected class KeyValueStore extends ValueStoreBase {
        public KeyValueStore(UniqueOrKey uniqueOrKey) {
            super(uniqueOrKey);
        }

        @Override // org.apache.xerces.impl.xs.XMLSchemaValidator.ValueStoreBase
        protected void checkDuplicateValues() {
            if (contains()) {
                XMLSchemaValidator.this.reportSchemaError("DuplicateKey", new Object[]{toString(this.fLocalValues), this.fIdentityConstraint.getElementName(), this.fIdentityConstraint.getIdentityConstraintName()});
            }
        }
    }

    protected static final class LocalIDKey {
        public int fDepth;
        public IdentityConstraint fId;

        public LocalIDKey() {
        }

        public LocalIDKey(IdentityConstraint identityConstraint, int i) {
            this.fId = identityConstraint;
            this.fDepth = i;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof LocalIDKey)) {
                return false;
            }
            LocalIDKey localIDKey = (LocalIDKey) obj;
            return localIDKey.fId == this.fId && localIDKey.fDepth == this.fDepth;
        }

        public int hashCode() {
            return this.fId.hashCode() + this.fDepth;
        }
    }

    protected static final class ShortVector {
        private short[] fData;
        private int fLength;

        public ShortVector() {
        }

        public ShortVector(int i) {
            this.fData = new short[i];
        }

        private void ensureCapacity(int i) {
            short[] sArr = this.fData;
            if (sArr == null) {
                this.fData = new short[8];
            } else if (sArr.length <= i) {
                short[] sArr2 = new short[sArr.length * 2];
                System.arraycopy(sArr, 0, sArr2, 0, sArr.length);
                this.fData = sArr2;
            }
        }

        public void add(short s) {
            ensureCapacity(this.fLength + 1);
            short[] sArr = this.fData;
            int i = this.fLength;
            this.fLength = i + 1;
            sArr[i] = s;
        }

        public void clear() {
            this.fLength = 0;
        }

        public boolean contains(short s) {
            for (int i = 0; i < this.fLength; i++) {
                if (this.fData[i] == s) {
                    return true;
                }
            }
            return false;
        }

        public int length() {
            return this.fLength;
        }

        public short valueAt(int i) {
            return this.fData[i];
        }
    }

    protected class UniqueValueStore extends ValueStoreBase {
        public UniqueValueStore(UniqueOrKey uniqueOrKey) {
            super(uniqueOrKey);
        }

        @Override // org.apache.xerces.impl.xs.XMLSchemaValidator.ValueStoreBase
        protected void checkDuplicateValues() {
            if (contains()) {
                XMLSchemaValidator.this.reportSchemaError("DuplicateUnique", new Object[]{toString(this.fLocalValues), this.fIdentityConstraint.getElementName(), this.fIdentityConstraint.getIdentityConstraintName()});
            }
        }
    }

    protected abstract class ValueStoreBase implements ValueStore {
        protected int fFieldCount;
        protected Field[] fFields;
        protected IdentityConstraint fIdentityConstraint;
        protected ShortList[] fLocalItemValueTypes;
        protected short[] fLocalValueTypes;
        protected Object[] fLocalValues;
        protected int fValuesCount;
        protected boolean fHasValue = false;
        public final Vector fValues = new Vector();
        public ShortVector fValueTypes = null;
        public Vector fItemValueTypes = null;
        private boolean fUseValueTypeVector = false;
        private int fValueTypesLength = 0;
        private short fValueType = 0;
        private boolean fUseItemValueTypeVector = false;
        private int fItemValueTypesLength = 0;
        private ShortList fItemValueType = null;
        final StringBuffer fTempBuffer = new StringBuffer();

        protected ValueStoreBase(IdentityConstraint identityConstraint) {
            this.fFieldCount = 0;
            this.fFields = null;
            this.fLocalValues = null;
            this.fLocalValueTypes = null;
            this.fLocalItemValueTypes = null;
            this.fIdentityConstraint = identityConstraint;
            int fieldCount = identityConstraint.getFieldCount();
            this.fFieldCount = fieldCount;
            this.fFields = new Field[fieldCount];
            this.fLocalValues = new Object[fieldCount];
            this.fLocalValueTypes = new short[fieldCount];
            this.fLocalItemValueTypes = new ShortList[fieldCount];
            for (int i = 0; i < this.fFieldCount; i++) {
                this.fFields[i] = this.fIdentityConstraint.getFieldAt(i);
            }
        }

        private void addItemValueType(ShortList shortList) {
            if (!this.fUseItemValueTypeVector) {
                int i = this.fItemValueTypesLength;
                this.fItemValueTypesLength = i + 1;
                if (i == 0) {
                    this.fItemValueType = shortList;
                    return;
                }
                ShortList shortList2 = this.fItemValueType;
                if (shortList2 == shortList) {
                    return;
                }
                if (shortList2 != null && shortList2.equals(shortList)) {
                    return;
                }
                this.fUseItemValueTypeVector = true;
                if (this.fItemValueTypes == null) {
                    this.fItemValueTypes = new Vector(this.fItemValueTypesLength * 2);
                }
                for (int i2 = 1; i2 < this.fItemValueTypesLength; i2++) {
                    this.fItemValueTypes.add(this.fItemValueType);
                }
            }
            this.fItemValueTypes.add(shortList);
        }

        private void addValueType(short s) {
            if (!this.fUseValueTypeVector) {
                int i = this.fValueTypesLength;
                this.fValueTypesLength = i + 1;
                if (i == 0) {
                    this.fValueType = s;
                    return;
                }
                if (this.fValueType == s) {
                    return;
                }
                this.fUseValueTypeVector = true;
                if (this.fValueTypes == null) {
                    this.fValueTypes = new ShortVector(this.fValueTypesLength * 2);
                }
                for (int i2 = 1; i2 < this.fValueTypesLength; i2++) {
                    this.fValueTypes.add(this.fValueType);
                }
            }
            this.fValueTypes.add(s);
        }

        private ShortList getItemValueTypeAt(int i) {
            return this.fUseItemValueTypeVector ? (ShortList) this.fItemValueTypes.elementAt(i) : this.fItemValueType;
        }

        private short getValueTypeAt(int i) {
            return this.fUseValueTypeVector ? this.fValueTypes.valueAt(i) : this.fValueType;
        }

        private boolean itemValueTypeContains(ShortList shortList) {
            if (this.fUseItemValueTypeVector) {
                return this.fItemValueTypes.contains(shortList);
            }
            ShortList shortList2 = this.fItemValueType;
            return shortList2 == shortList || (shortList2 != null && shortList2.equals(shortList));
        }

        private boolean valueTypeContains(short s) {
            if (this.fUseValueTypeVector) {
                return this.fValueTypes.contains(s);
            }
            return this.fValueType == s;
        }

        @Override // org.apache.xerces.impl.xs.identity.ValueStore
        public void addValue(Field field, boolean z, Object obj, short s, ShortList shortList) {
            int i = this.fFieldCount - 1;
            while (i > -1 && this.fFields[i] != field) {
                i--;
            }
            if (i == -1) {
                XMLSchemaValidator.this.reportSchemaError("UnknownField", new Object[]{field.toString(), this.fIdentityConstraint.getElementName(), this.fIdentityConstraint.getIdentityConstraintName()});
                return;
            }
            if (z) {
                this.fValuesCount++;
                this.fHasValue = true;
            } else {
                XMLSchemaValidator.this.reportSchemaError("FieldMultipleMatch", new Object[]{field.toString(), this.fIdentityConstraint.getIdentityConstraintName()});
            }
            this.fLocalValues[i] = obj;
            this.fLocalValueTypes[i] = s;
            this.fLocalItemValueTypes[i] = shortList;
            if (this.fValuesCount == this.fFieldCount) {
                checkDuplicateValues();
                for (int i2 = 0; i2 < this.fFieldCount; i2++) {
                    this.fValues.addElement(this.fLocalValues[i2]);
                    addValueType(this.fLocalValueTypes[i2]);
                    addItemValueType(this.fLocalItemValueTypes[i2]);
                }
            }
        }

        public void append(ValueStoreBase valueStoreBase) {
            for (int i = 0; i < valueStoreBase.fValues.size(); i++) {
                this.fValues.addElement(valueStoreBase.fValues.elementAt(i));
            }
        }

        protected void checkDuplicateValues() {
        }

        public void clear() {
            this.fValuesCount = 0;
            this.fUseValueTypeVector = false;
            this.fValueTypesLength = 0;
            this.fValueType = (short) 0;
            this.fUseItemValueTypeVector = false;
            this.fItemValueTypesLength = 0;
            this.fItemValueType = null;
            this.fValues.setSize(0);
            ShortVector shortVector = this.fValueTypes;
            if (shortVector != null) {
                shortVector.clear();
            }
            Vector vector = this.fItemValueTypes;
            if (vector != null) {
                vector.setSize(0);
            }
        }

        public int contains(ValueStoreBase valueStoreBase) {
            Vector vector = valueStoreBase.fValues;
            int size = vector.size();
            if (this.fFieldCount <= 1) {
                for (int i = 0; i < size; i++) {
                    short valueTypeAt = valueStoreBase.getValueTypeAt(i);
                    if (!valueTypeContains(valueTypeAt) || !this.fValues.contains(vector.elementAt(i))) {
                        return i;
                    }
                    if ((valueTypeAt == 44 || valueTypeAt == 43) && !itemValueTypeContains(valueStoreBase.getItemValueTypeAt(i))) {
                        return i;
                    }
                }
                return -1;
            }
            int size2 = this.fValues.size();
            int i2 = 0;
            while (i2 < size) {
                int i3 = 0;
                while (i3 < size2) {
                    int i4 = 0;
                    while (true) {
                        int i5 = this.fFieldCount;
                        if (i4 < i5) {
                            int i6 = i2 + i4;
                            Object objElementAt = vector.elementAt(i6);
                            int i7 = i3 + i4;
                            Object objElementAt2 = this.fValues.elementAt(i7);
                            short valueTypeAt2 = valueStoreBase.getValueTypeAt(i6);
                            short valueTypeAt3 = getValueTypeAt(i7);
                            if (objElementAt == objElementAt2 || (valueTypeAt2 == valueTypeAt3 && objElementAt != null && objElementAt.equals(objElementAt2))) {
                                if (valueTypeAt2 == 44 || valueTypeAt2 == 43) {
                                    ShortList itemValueTypeAt = valueStoreBase.getItemValueTypeAt(i6);
                                    ShortList itemValueTypeAt2 = getItemValueTypeAt(i7);
                                    if (itemValueTypeAt == null || itemValueTypeAt2 == null || !itemValueTypeAt.equals(itemValueTypeAt2)) {
                                    }
                                }
                                i4++;
                            }
                            i3 += this.fFieldCount;
                        } else {
                            i2 += i5;
                        }
                    }
                }
                return i2;
            }
            return -1;
        }

        public boolean contains() {
            int size = this.fValues.size();
            int i = 0;
            while (i < size) {
                int i2 = this.fFieldCount + i;
                for (int i3 = 0; i3 < this.fFieldCount; i3++) {
                    Object obj = this.fLocalValues[i3];
                    Object objElementAt = this.fValues.elementAt(i);
                    short s = this.fLocalValueTypes[i3];
                    short valueTypeAt = getValueTypeAt(i);
                    if (obj != null && objElementAt != null && s == valueTypeAt && obj.equals(objElementAt)) {
                        if (s == 44 || s == 43) {
                            ShortList shortList = this.fLocalItemValueTypes[i3];
                            ShortList itemValueTypeAt = getItemValueTypeAt(i);
                            if (shortList == null || itemValueTypeAt == null || !shortList.equals(itemValueTypeAt)) {
                            }
                        }
                        i++;
                    }
                    i = i2;
                }
                return true;
            }
            return false;
        }

        public void endDocument() {
        }

        public void endDocumentFragment() {
        }

        public void endValueScope() {
            int i = this.fValuesCount;
            if (i == 0) {
                if (this.fIdentityConstraint.getCategory() == 1) {
                    XMLSchemaValidator.this.reportSchemaError("AbsentKeyValue", new Object[]{this.fIdentityConstraint.getElementName(), this.fIdentityConstraint.getIdentityConstraintName()});
                    return;
                }
                return;
            }
            if (i == this.fFieldCount || this.fIdentityConstraint.getCategory() != 1) {
                return;
            }
            IdentityConstraint identityConstraint = this.fIdentityConstraint;
            UniqueOrKey uniqueOrKey = (UniqueOrKey) identityConstraint;
            XMLSchemaValidator.this.reportSchemaError("KeyNotEnoughValues", new Object[]{identityConstraint.getElementName(), uniqueOrKey.getIdentityConstraintName()});
        }

        @Override // org.apache.xerces.impl.xs.identity.ValueStore
        public void reportError(String str, Object[] objArr) {
            XMLSchemaValidator.this.reportSchemaError(str, objArr);
        }

        public void startValueScope() {
            this.fValuesCount = 0;
            for (int i = 0; i < this.fFieldCount; i++) {
                this.fLocalValues[i] = null;
                this.fLocalValueTypes[i] = 0;
                this.fLocalItemValueTypes[i] = null;
            }
        }

        public String toString() {
            String string = super.toString();
            int iLastIndexOf = string.lastIndexOf(36);
            if (iLastIndexOf != -1) {
                string = string.substring(iLastIndexOf + 1);
            }
            int iLastIndexOf2 = string.lastIndexOf(46);
            if (iLastIndexOf2 != -1) {
                string = string.substring(iLastIndexOf2 + 1);
            }
            return string + '[' + this.fIdentityConstraint + ']';
        }

        protected String toString(Vector vector, int i, int i2) {
            if (i2 == 0) {
                return "";
            }
            if (i2 == 1) {
                return String.valueOf(vector.elementAt(i));
            }
            StringBuffer stringBuffer = new StringBuffer();
            for (int i3 = 0; i3 < i2; i3++) {
                if (i3 > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(vector.elementAt(i + i3));
            }
            return stringBuffer.toString();
        }

        protected String toString(Object[] objArr) {
            int length = objArr.length;
            if (length == 0) {
                return "";
            }
            this.fTempBuffer.setLength(0);
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    this.fTempBuffer.append(',');
                }
                this.fTempBuffer.append(objArr[i]);
            }
            return this.fTempBuffer.toString();
        }
    }

    protected class ValueStoreCache {
        final LocalIDKey fLocalId = new LocalIDKey();
        protected final ArrayList fValueStores = new ArrayList();
        protected final HashMap fIdentityConstraint2ValueStoreMap = new HashMap();
        protected final Stack fGlobalMapStack = new Stack();
        protected final HashMap fGlobalIDConstraintMap = new HashMap();

        public ValueStoreCache() {
        }

        public void endDocument() {
            int size = this.fValueStores.size();
            for (int i = 0; i < size; i++) {
                ((ValueStoreBase) this.fValueStores.get(i)).endDocument();
            }
        }

        public void endElement() {
            HashMap map;
            if (this.fGlobalMapStack.isEmpty() || (map = (HashMap) this.fGlobalMapStack.pop()) == null) {
                return;
            }
            for (Map.Entry entry : map.entrySet()) {
                IdentityConstraint identityConstraint = (IdentityConstraint) entry.getKey();
                ValueStoreBase valueStoreBase = (ValueStoreBase) entry.getValue();
                if (valueStoreBase != null) {
                    ValueStoreBase valueStoreBase2 = (ValueStoreBase) this.fGlobalIDConstraintMap.get(identityConstraint);
                    if (valueStoreBase2 == null) {
                        this.fGlobalIDConstraintMap.put(identityConstraint, valueStoreBase);
                    } else if (valueStoreBase2 != valueStoreBase) {
                        valueStoreBase2.append(valueStoreBase);
                    }
                }
            }
        }

        public ValueStoreBase getGlobalValueStoreFor(IdentityConstraint identityConstraint) {
            return (ValueStoreBase) this.fGlobalIDConstraintMap.get(identityConstraint);
        }

        public ValueStoreBase getValueStoreFor(IdentityConstraint identityConstraint, int i) {
            this.fLocalId.fDepth = i;
            this.fLocalId.fId = identityConstraint;
            return (ValueStoreBase) this.fIdentityConstraint2ValueStoreMap.get(this.fLocalId);
        }

        public void initValueStoresFor(XSElementDecl xSElementDecl, FieldActivator fieldActivator) {
            XMLSchemaValidator xMLSchemaValidator;
            IdentityConstraint identityConstraint;
            IdentityConstraint[] identityConstraintArr = xSElementDecl.fIDConstraints;
            int i = xSElementDecl.fIDCPos;
            for (int i2 = 0; i2 < i; i2++) {
                short category = identityConstraintArr[i2].getCategory();
                if (category == 1) {
                    UniqueOrKey uniqueOrKey = (UniqueOrKey) identityConstraintArr[i2];
                    LocalIDKey localIDKey = new LocalIDKey(uniqueOrKey, XMLSchemaValidator.this.fElementDepth);
                    KeyValueStore keyValueStore = (KeyValueStore) this.fIdentityConstraint2ValueStoreMap.get(localIDKey);
                    if (keyValueStore == null) {
                        keyValueStore = XMLSchemaValidator.this.new KeyValueStore(uniqueOrKey);
                        this.fIdentityConstraint2ValueStoreMap.put(localIDKey, keyValueStore);
                    } else {
                        keyValueStore.clear();
                    }
                    this.fValueStores.add(keyValueStore);
                    xMLSchemaValidator = XMLSchemaValidator.this;
                    identityConstraint = identityConstraintArr[i2];
                } else if (category != 2) {
                    if (category == 3) {
                        UniqueOrKey uniqueOrKey2 = (UniqueOrKey) identityConstraintArr[i2];
                        LocalIDKey localIDKey2 = new LocalIDKey(uniqueOrKey2, XMLSchemaValidator.this.fElementDepth);
                        UniqueValueStore uniqueValueStore = (UniqueValueStore) this.fIdentityConstraint2ValueStoreMap.get(localIDKey2);
                        if (uniqueValueStore == null) {
                            uniqueValueStore = XMLSchemaValidator.this.new UniqueValueStore(uniqueOrKey2);
                            this.fIdentityConstraint2ValueStoreMap.put(localIDKey2, uniqueValueStore);
                        } else {
                            uniqueValueStore.clear();
                        }
                        this.fValueStores.add(uniqueValueStore);
                        xMLSchemaValidator = XMLSchemaValidator.this;
                        identityConstraint = identityConstraintArr[i2];
                    }
                } else {
                    KeyRef keyRef = (KeyRef) identityConstraintArr[i2];
                    LocalIDKey localIDKey3 = new LocalIDKey(keyRef, XMLSchemaValidator.this.fElementDepth);
                    KeyRefValueStore keyRefValueStore = (KeyRefValueStore) this.fIdentityConstraint2ValueStoreMap.get(localIDKey3);
                    if (keyRefValueStore == null) {
                        keyRefValueStore = XMLSchemaValidator.this.new KeyRefValueStore(keyRef, null);
                        this.fIdentityConstraint2ValueStoreMap.put(localIDKey3, keyRefValueStore);
                    } else {
                        keyRefValueStore.clear();
                    }
                    this.fValueStores.add(keyRefValueStore);
                    xMLSchemaValidator = XMLSchemaValidator.this;
                    identityConstraint = identityConstraintArr[i2];
                }
                xMLSchemaValidator.activateSelectorFor(identityConstraint);
            }
        }

        public void startDocument() {
            this.fValueStores.clear();
            this.fIdentityConstraint2ValueStoreMap.clear();
            this.fGlobalIDConstraintMap.clear();
            this.fGlobalMapStack.removeAllElements();
        }

        public void startElement() {
            Stack stack;
            Object objClone;
            if (this.fGlobalIDConstraintMap.size() > 0) {
                stack = this.fGlobalMapStack;
                objClone = this.fGlobalIDConstraintMap.clone();
            } else {
                stack = this.fGlobalMapStack;
                objClone = null;
            }
            stack.push(objClone);
            this.fGlobalIDConstraintMap.clear();
        }

        public String toString() {
            String string = super.toString();
            int iLastIndexOf = string.lastIndexOf(36);
            return (iLastIndexOf == -1 && (iLastIndexOf = string.lastIndexOf(46)) == -1) ? string : string.substring(iLastIndexOf + 1);
        }

        public void transplant(IdentityConstraint identityConstraint, int i) {
            this.fLocalId.fDepth = i;
            this.fLocalId.fId = identityConstraint;
            ValueStoreBase valueStoreBase = (ValueStoreBase) this.fIdentityConstraint2ValueStoreMap.get(this.fLocalId);
            if (identityConstraint.getCategory() == 2) {
                return;
            }
            ValueStoreBase valueStoreBase2 = (ValueStoreBase) this.fGlobalIDConstraintMap.get(identityConstraint);
            if (valueStoreBase2 == null) {
                this.fGlobalIDConstraintMap.put(identityConstraint, valueStoreBase);
            } else {
                valueStoreBase2.append(valueStoreBase);
                this.fGlobalIDConstraintMap.put(identityConstraint, valueStoreBase2);
            }
        }
    }

    protected static class XPathMatcherStack {
        protected int fMatchersCount;
        protected XPathMatcher[] fMatchers = new XPathMatcher[4];
        protected IntStack fContextStack = new IntStack();

        private void ensureMatcherCapacity() {
            int i = this.fMatchersCount;
            XPathMatcher[] xPathMatcherArr = this.fMatchers;
            if (i == xPathMatcherArr.length) {
                XPathMatcher[] xPathMatcherArr2 = new XPathMatcher[xPathMatcherArr.length * 2];
                System.arraycopy(xPathMatcherArr, 0, xPathMatcherArr2, 0, xPathMatcherArr.length);
                this.fMatchers = xPathMatcherArr2;
            }
        }

        public void addMatcher(XPathMatcher xPathMatcher) {
            ensureMatcherCapacity();
            XPathMatcher[] xPathMatcherArr = this.fMatchers;
            int i = this.fMatchersCount;
            this.fMatchersCount = i + 1;
            xPathMatcherArr[i] = xPathMatcher;
        }

        public void clear() {
            for (int i = 0; i < this.fMatchersCount; i++) {
                this.fMatchers[i] = null;
            }
            this.fMatchersCount = 0;
            this.fContextStack.clear();
        }

        public XPathMatcher getMatcherAt(int i) {
            return this.fMatchers[i];
        }

        public int getMatcherCount() {
            return this.fMatchersCount;
        }

        public void popContext() {
            this.fMatchersCount = this.fContextStack.pop();
        }

        public void pushContext() {
            this.fContextStack.push(this.fMatchersCount);
        }

        public int size() {
            return this.fContextStack.size();
        }
    }

    protected final class XSIErrorReporter {
        int fContextCount;
        XMLErrorReporter fErrorReporter;
        Vector fErrors = new Vector();
        int[] fContext = new int[8];

        protected XSIErrorReporter() {
        }

        public String[] mergeContext() {
            if (!XMLSchemaValidator.this.fAugPSVI) {
                return null;
            }
            int[] iArr = this.fContext;
            int i = this.fContextCount - 1;
            this.fContextCount = i;
            int i2 = iArr[i];
            int size = this.fErrors.size() - i2;
            if (size == 0) {
                return null;
            }
            String[] strArr = new String[size];
            for (int i3 = 0; i3 < size; i3++) {
                strArr[i3] = (String) this.fErrors.elementAt(i2 + i3);
            }
            return strArr;
        }

        public String[] popContext() {
            if (!XMLSchemaValidator.this.fAugPSVI) {
                return null;
            }
            int[] iArr = this.fContext;
            int i = this.fContextCount - 1;
            this.fContextCount = i;
            int i2 = iArr[i];
            int size = this.fErrors.size() - i2;
            if (size == 0) {
                return null;
            }
            String[] strArr = new String[size];
            for (int i3 = 0; i3 < size; i3++) {
                strArr[i3] = (String) this.fErrors.elementAt(i2 + i3);
            }
            this.fErrors.setSize(i2);
            return strArr;
        }

        public void pushContext() {
            if (XMLSchemaValidator.this.fAugPSVI) {
                int i = this.fContextCount;
                int[] iArr = this.fContext;
                if (i == iArr.length) {
                    int[] iArr2 = new int[i + 8];
                    System.arraycopy(iArr, 0, iArr2, 0, i);
                    this.fContext = iArr2;
                }
                int[] iArr3 = this.fContext;
                int i2 = this.fContextCount;
                this.fContextCount = i2 + 1;
                iArr3[i2] = this.fErrors.size();
            }
        }

        public void reportError(String str, String str2, Object[] objArr, short s) throws XNIException {
            String strReportError = this.fErrorReporter.reportError(str, str2, objArr, s);
            if (XMLSchemaValidator.this.fAugPSVI) {
                this.fErrors.addElement(str2);
                this.fErrors.addElement(strReportError);
            }
        }

        public void reportError(XMLLocator xMLLocator, String str, String str2, Object[] objArr, short s) throws XNIException {
            String strReportError = this.fErrorReporter.reportError(xMLLocator, str, str2, objArr, s);
            if (XMLSchemaValidator.this.fAugPSVI) {
                this.fErrors.addElement(str2);
                this.fErrors.addElement(strReportError);
            }
        }

        public void reset(XMLErrorReporter xMLErrorReporter) {
            this.fErrorReporter = xMLErrorReporter;
            this.fErrors.removeAllElements();
            this.fContextCount = 0;
        }
    }

    public XMLSchemaValidator() {
        XSIErrorReporter xSIErrorReporter = new XSIErrorReporter();
        this.fXSIErrorReporter = xSIErrorReporter;
        this.fValidationManager = null;
        this.fValidationState = new ConfigurableValidationState();
        this.fExternalSchemas = null;
        this.fExternalNoNamespaceSchema = null;
        this.fJaxpSchemaSource = null;
        this.fXSDDescription = new XSDDescription();
        this.fLocationPairs = new Hashtable();
        this.fExpandedLocationPairs = new Hashtable();
        this.fUnparsedLocations = new ArrayList();
        this.fEmptyXMLStr = new XMLString(null, 0, -1);
        this.fNormalizedStr = new XMLString();
        this.fFirstChunk = true;
        this.fTrailing = false;
        this.fWhiteSpace = (short) -1;
        this.fUnionType = false;
        XSGrammarBucket xSGrammarBucket = new XSGrammarBucket();
        this.fGrammarBucket = xSGrammarBucket;
        SubstitutionGroupHandler substitutionGroupHandler = new SubstitutionGroupHandler(this);
        this.fSubGroupHandler = substitutionGroupHandler;
        this.fQNameDV = (XSSimpleType) SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(SchemaSymbols.ATTVAL_QNAME);
        CMNodeFactory cMNodeFactory = new CMNodeFactory();
        this.nodeFactory = cMNodeFactory;
        CMBuilder cMBuilder = new CMBuilder(cMNodeFactory);
        this.fCMBuilder = cMBuilder;
        this.fSchemaLoader = new XMLSchemaLoader(xSIErrorReporter.fErrorReporter, xSGrammarBucket, substitutionGroupHandler, cMBuilder);
        this.fSubElementStack = new boolean[8];
        this.fElemDeclStack = new XSElementDecl[8];
        this.fNilStack = new boolean[8];
        this.fNotationStack = new XSNotationDecl[8];
        this.fTypeStack = new XSTypeDefinition[8];
        this.fCMStack = new XSCMValidator[8];
        this.fCMStateStack = new int[8][];
        this.fStrictAssess = true;
        this.fStrictAssessStack = new boolean[8];
        this.fBuffer = new StringBuffer();
        this.fAppendBuffer = true;
        this.fSawText = false;
        this.fSawTextStack = new boolean[8];
        this.fSawCharacters = false;
        this.fStringContent = new boolean[8];
        this.fTempQName = new org.apache.xerces.xni.QName();
        this.fRootTypeQName = null;
        this.fRootTypeDefinition = null;
        this.fRootElementDeclQName = null;
        this.fRootElementDeclaration = null;
        this.fValidatedInfo = new ValidatedInfo();
        this.fState4XsiType = new ValidationState();
        this.fState4ApplyDefault = new ValidationState();
        this.fMatcherStack = new XPathMatcherStack();
        this.fValueStoreCache = new ValueStoreCache();
        this.fState4XsiType.setExtraChecking(false);
        this.fState4ApplyDefault.setFacetChecking(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void activateSelectorFor(IdentityConstraint identityConstraint) {
        Selector selector = identityConstraint.getSelector();
        if (selector == null) {
            return;
        }
        XPathMatcher xPathMatcherCreateMatcher = selector.createMatcher(this, this.fElementDepth);
        this.fMatcherStack.addMatcher(xPathMatcherCreateMatcher);
        xPathMatcherCreateMatcher.startDocumentFragment();
    }

    private String expectedStr(Vector vector) {
        StringBuffer stringBuffer = new StringBuffer("{");
        int size = vector.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(vector.elementAt(i).toString());
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    private boolean hasSchemaComponent(SchemaGrammar schemaGrammar, short s, org.apache.xerces.xni.QName qName) {
        String str;
        if (schemaGrammar == null || qName == null || (str = qName.localpart) == null || str.length() <= 0) {
            return false;
        }
        if (s == 5) {
            return schemaGrammar.getElementDeclaration(str) != null;
        }
        if (s != 6) {
            return s == 7 && schemaGrammar.getTypeDefinition(str) != null;
        }
        return schemaGrammar.getAttributeDeclaration(str) != null;
    }

    private void normalizeWhitespace(String str, boolean z) {
        int length = str.length();
        if (this.fNormalizedStr.ch == null || this.fNormalizedStr.ch.length < length) {
            this.fNormalizedStr.ch = new char[length];
        }
        this.fNormalizedStr.offset = 0;
        this.fNormalizedStr.length = 0;
        boolean z2 = z;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (!XMLChar.isSpace(cCharAt)) {
                char[] cArr = this.fNormalizedStr.ch;
                XMLString xMLString = this.fNormalizedStr;
                int i2 = xMLString.length;
                xMLString.length = i2 + 1;
                cArr[i2] = cCharAt;
                z2 = false;
            } else if (!z2) {
                char[] cArr2 = this.fNormalizedStr.ch;
                XMLString xMLString2 = this.fNormalizedStr;
                int i3 = xMLString2.length;
                xMLString2.length = i3 + 1;
                cArr2[i3] = StringUtil.SPACE;
                z2 = z;
            }
        }
        if (!z2 || this.fNormalizedStr.length == 0) {
            return;
        }
        this.fNormalizedStr.length--;
    }

    /* JADX WARN: Code duplicated, block: B:27:0x007b  */
    private void normalizeWhitespace(XMLString xMLString, boolean z) {
        boolean z2;
        int i = xMLString.offset + xMLString.length;
        if (this.fNormalizedStr.ch == null || this.fNormalizedStr.ch.length < xMLString.length + 1) {
            this.fNormalizedStr.ch = new char[xMLString.length + 1];
        }
        this.fNormalizedStr.offset = 1;
        this.fNormalizedStr.length = 1;
        boolean z3 = z;
        boolean z4 = false;
        boolean z5 = false;
        for (int i2 = xMLString.offset; i2 < i; i2++) {
            char c = xMLString.ch[i2];
            if (XMLChar.isSpace(c)) {
                if (!z3) {
                    char[] cArr = this.fNormalizedStr.ch;
                    XMLString xMLString2 = this.fNormalizedStr;
                    int i3 = xMLString2.length;
                    xMLString2.length = i3 + 1;
                    cArr[i3] = StringUtil.SPACE;
                    z3 = z;
                }
                if (!z5) {
                    z4 = true;
                }
            } else {
                char[] cArr2 = this.fNormalizedStr.ch;
                XMLString xMLString3 = this.fNormalizedStr;
                int i4 = xMLString3.length;
                xMLString3.length = i4 + 1;
                cArr2[i4] = c;
                z5 = true;
                z3 = false;
            }
        }
        if (z3) {
            if (this.fNormalizedStr.length > 1) {
                this.fNormalizedStr.length--;
            } else if (!z4 || this.fFirstChunk) {
                z2 = false;
            }
            z2 = true;
        } else {
            z2 = false;
        }
        if (this.fNormalizedStr.length > 1 && !this.fFirstChunk && this.fWhiteSpace == 2 && (this.fTrailing || z4)) {
            this.fNormalizedStr.offset = 0;
            this.fNormalizedStr.ch[0] = StringUtil.SPACE;
        }
        this.fNormalizedStr.length -= this.fNormalizedStr.offset;
        this.fTrailing = z2;
        if (z2 || z5) {
            this.fFirstChunk = false;
        }
    }

    private void setLocationHints(XSDDescription xSDDescription, String[] strArr, SchemaGrammar schemaGrammar) {
        int length = strArr.length;
        if (schemaGrammar != null) {
            setLocationHints(xSDDescription, strArr, schemaGrammar.getDocumentLocations());
            return;
        }
        this.fXSDDescription.fLocationHints = new String[length];
        System.arraycopy(strArr, 0, this.fXSDDescription.fLocationHints, 0, length);
    }

    private void setLocationHints(XSDDescription xSDDescription, String[] strArr, StringList stringList) {
        int length = strArr.length;
        String[] strArr2 = new String[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (!stringList.contains(strArr[i2])) {
                strArr2[i] = strArr[i2];
                i++;
            }
        }
        if (i > 0) {
            if (i == length) {
                this.fXSDDescription.fLocationHints = strArr2;
                return;
            }
            this.fXSDDescription.fLocationHints = new String[i];
            System.arraycopy(strArr2, 0, this.fXSDDescription.fLocationHints, 0, i);
        }
    }

    @Override // org.apache.xerces.impl.xs.identity.FieldActivator
    public XPathMatcher activateField(Field field, int i) {
        XPathMatcher xPathMatcherCreateMatcher = field.createMatcher(this.fValueStoreCache.getValueStoreFor(field.getIdentityConstraint(), i));
        this.fMatcherStack.addMatcher(xPathMatcherCreateMatcher);
        xPathMatcherCreateMatcher.startDocumentFragment();
        return xPathMatcherCreateMatcher;
    }

    void addDefaultAttributes(org.apache.xerces.xni.QName qName, XMLAttributes xMLAttributes, XSAttributeGroupDecl xSAttributeGroupDecl) {
        int iAddAttribute;
        XSObjectList attributeUses = xSAttributeGroupDecl.getAttributeUses();
        int length = attributeUses.getLength();
        for (int i = 0; i < length; i++) {
            XSAttributeUseImpl xSAttributeUseImpl = (XSAttributeUseImpl) attributeUses.item(i);
            XSAttributeDecl xSAttributeDecl = xSAttributeUseImpl.fAttrDecl;
            short constraintType = xSAttributeUseImpl.fConstraintType;
            ValidatedInfo validatedInfo = xSAttributeUseImpl.fDefault;
            if (constraintType == 0) {
                constraintType = xSAttributeDecl.getConstraintType();
                validatedInfo = xSAttributeDecl.fDefault;
            }
            boolean z = xMLAttributes.getValue(xSAttributeDecl.fTargetNamespace, xSAttributeDecl.fName) != null;
            if (xSAttributeUseImpl.fUse == 1 && !z) {
                if (xSAttributeDecl.fTargetNamespace != null) {
                    reportSchemaError("cvc-complex-type.4_ns", new Object[]{qName.rawname, xSAttributeDecl.fName, xSAttributeDecl.fTargetNamespace});
                } else {
                    reportSchemaError("cvc-complex-type.4", new Object[]{qName.rawname, xSAttributeDecl.fName});
                }
            }
            if (!z && constraintType != 0) {
                org.apache.xerces.xni.QName qName2 = new org.apache.xerces.xni.QName(null, xSAttributeDecl.fName, xSAttributeDecl.fName, xSAttributeDecl.fTargetNamespace);
                String strStringValue = validatedInfo != null ? validatedInfo.stringValue() : "";
                if (xMLAttributes instanceof XMLAttributesImpl) {
                    XMLAttributesImpl xMLAttributesImpl = (XMLAttributesImpl) xMLAttributes;
                    iAddAttribute = xMLAttributesImpl.getLength();
                    xMLAttributesImpl.addAttributeNS(qName2, "CDATA", strStringValue);
                } else {
                    iAddAttribute = xMLAttributes.addAttribute(qName2, "CDATA", strStringValue);
                }
                if (this.fAugPSVI) {
                    Augmentations augmentations = xMLAttributes.getAugmentations(iAddAttribute);
                    AttributePSVImpl attributePSVImpl = new AttributePSVImpl();
                    augmentations.putItem(Constants.ATTRIBUTE_PSVI, attributePSVImpl);
                    attributePSVImpl.fDeclaration = xSAttributeDecl;
                    attributePSVImpl.fTypeDecl = xSAttributeDecl.fType;
                    attributePSVImpl.fValue.copyFrom(validatedInfo);
                    attributePSVImpl.fValidationContext = this.fValidationRoot;
                    attributePSVImpl.fValidity = (short) 2;
                    attributePSVImpl.fValidationAttempted = (short) 2;
                    attributePSVImpl.fSpecified = true;
                }
            }
        }
    }

    @Override // org.apache.xerces.impl.RevalidationHandler
    public boolean characterData(String str, Augmentations augmentations) {
        short s;
        this.fSawText = this.fSawText || str.length() > 0;
        if (this.fNormalizeData && (s = this.fWhiteSpace) != -1 && s != 0) {
            normalizeWhitespace(str, s == 2);
            this.fBuffer.append(this.fNormalizedStr.ch, this.fNormalizedStr.offset, this.fNormalizedStr.length);
        } else if (this.fAppendBuffer) {
            this.fBuffer.append(str);
        }
        XSTypeDefinition xSTypeDefinition = this.fCurrentType;
        if (xSTypeDefinition != null && xSTypeDefinition.getTypeCategory() == 15 && ((XSComplexTypeDecl) this.fCurrentType).fContentType == 2) {
            for (int i = 0; i < str.length(); i++) {
                if (!XMLChar.isSpace(str.charAt(i))) {
                    this.fSawCharacters = true;
                    return false;
                }
            }
        }
        return true;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        XMLString xMLStringHandleCharacters = handleCharacters(xMLString);
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            if (this.fNormalizeData && this.fUnionType) {
                if (augmentations == null) {
                    return;
                } else {
                    xMLStringHandleCharacters = this.fEmptyXMLStr;
                }
            }
            xMLDocumentHandler.characters(xMLStringHandleCharacters, augmentations);
        }
    }

    void checkElementMatchesRootElementDecl(XSElementDecl xSElementDecl, org.apache.xerces.xni.QName qName) {
        if (qName.localpart == xSElementDecl.fName && qName.uri == xSElementDecl.fTargetNamespace) {
            return;
        }
        reportSchemaError("cvc-elt.1.b", new Object[]{qName.rawname, xSElementDecl.fName});
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.comment(xMLString, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.doctypeDecl(str, str2, str3, augmentations);
        }
    }

    public void elementDefault(String str) {
    }

    Object elementLocallyValidComplexType(org.apache.xerces.xni.QName qName, Object obj) throws InvalidDatatypeValueException {
        int i;
        int i2;
        XSComplexTypeDecl xSComplexTypeDecl = (XSComplexTypeDecl) this.fCurrentType;
        Object objValidate = null;
        if (!this.fNil) {
            if (xSComplexTypeDecl.fContentType == 0 && (this.fSubElement || this.fSawText)) {
                reportSchemaError("cvc-complex-type.2.1", new Object[]{qName.rawname});
            } else if (xSComplexTypeDecl.fContentType == 1) {
                if (this.fSubElement) {
                    reportSchemaError("cvc-complex-type.2.2", new Object[]{qName.rawname});
                }
                XSSimpleType xSSimpleType = xSComplexTypeDecl.fXSSimpleType;
                try {
                    if (!this.fNormalizeData || this.fUnionType) {
                        this.fValidationState.setNormalizationRequired(true);
                    }
                    objValidate = xSSimpleType.validate(obj, this.fValidationState, this.fValidatedInfo);
                } catch (InvalidDatatypeValueException e) {
                    reportSchemaError(e.getKey(), e.getArgs());
                    reportSchemaError("cvc-complex-type.2.2", new Object[]{qName.rawname});
                }
            } else if (xSComplexTypeDecl.fContentType == 2 && this.fSawCharacters) {
                reportSchemaError("cvc-complex-type.2.3", new Object[]{qName.rawname});
            }
            if (xSComplexTypeDecl.fContentType == 2 || xSComplexTypeDecl.fContentType == 3) {
                int[] iArr = this.fCurrCMState;
                if (iArr[0] >= 0 && !this.fCurrentCM.endContentModel(iArr)) {
                    String strExpectedStr = expectedStr(this.fCurrentCM.whatCanGoHere(this.fCurrCMState));
                    int[] iArrOccurenceInfo = this.fCurrentCM.occurenceInfo(this.fCurrCMState);
                    if (iArrOccurenceInfo == null || (i2 = iArrOccurenceInfo[2]) >= (i = iArrOccurenceInfo[0])) {
                        reportSchemaError("cvc-complex-type.2.4.b", new Object[]{qName.rawname, strExpectedStr});
                    } else {
                        int i3 = i - i2;
                        String str = qName.rawname;
                        if (i3 > 1) {
                            reportSchemaError("cvc-complex-type.2.4.j", new Object[]{str, this.fCurrentCM.getTermName(iArrOccurenceInfo[3]), Integer.toString(i), Integer.toString(i3)});
                        } else {
                            reportSchemaError("cvc-complex-type.2.4.i", new Object[]{str, this.fCurrentCM.getTermName(iArrOccurenceInfo[3]), Integer.toString(i)});
                        }
                    }
                }
            }
        }
        return objValidate;
    }

    Object elementLocallyValidType(org.apache.xerces.xni.QName qName, Object obj) {
        XSTypeDefinition xSTypeDefinition = this.fCurrentType;
        if (xSTypeDefinition == null) {
            return null;
        }
        if (xSTypeDefinition.getTypeCategory() != 16) {
            return elementLocallyValidComplexType(qName, obj);
        }
        if (this.fSubElement) {
            reportSchemaError("cvc-type.3.1.2", new Object[]{qName.rawname});
        }
        if (this.fNil) {
            return null;
        }
        XSSimpleType xSSimpleType = (XSSimpleType) this.fCurrentType;
        try {
            if (!this.fNormalizeData || this.fUnionType) {
                this.fValidationState.setNormalizationRequired(true);
            }
            return xSSimpleType.validate(obj, this.fValidationState, this.fValidatedInfo);
        } catch (InvalidDatatypeValueException e) {
            reportSchemaError(e.getKey(), e.getArgs());
            reportSchemaError("cvc-type.3.1.3", new Object[]{qName.rawname, obj});
            return null;
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void emptyElement(org.apache.xerces.xni.QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        Augmentations augmentationsHandleStartElement = handleStartElement(qName, xMLAttributes, augmentations);
        this.fDefaultValue = null;
        if (this.fElementDepth != -2) {
            augmentationsHandleStartElement = handleEndElement(qName, augmentationsHandleStartElement);
        }
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            if (!this.fSchemaElementDefault || this.fDefaultValue == null) {
                xMLDocumentHandler.emptyElement(qName, xMLAttributes, augmentationsHandleStartElement);
                return;
            }
            xMLDocumentHandler.startElement(qName, xMLAttributes, augmentationsHandleStartElement);
            this.fDocumentHandler.characters(this.fDefaultValue, null);
            this.fDocumentHandler.endElement(qName, augmentationsHandleStartElement);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endCDATA(Augmentations augmentations) throws XNIException {
        this.fInCDATA = false;
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.endCDATA(augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endDocument(Augmentations augmentations) throws XNIException {
        handleEndDocument();
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.endDocument(augmentations);
        }
        this.fLocator = null;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(org.apache.xerces.xni.QName qName, Augmentations augmentations) throws XNIException {
        XMLString xMLString;
        this.fDefaultValue = null;
        Augmentations augmentationsHandleEndElement = handleEndElement(qName, augmentations);
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            if (!this.fSchemaElementDefault || (xMLString = this.fDefaultValue) == null) {
                xMLDocumentHandler.endElement(qName, augmentationsHandleEndElement);
            } else {
                xMLDocumentHandler.characters(xMLString, null);
                this.fDocumentHandler.endElement(qName, augmentationsHandleEndElement);
            }
        }
    }

    final Augmentations endElementPSVI(boolean z, SchemaGrammar[] schemaGrammarArr, Augmentations augmentations) {
        if (this.fAugPSVI) {
            augmentations = getEmptyAugs(augmentations);
            this.fCurrentPSVI.fDeclaration = this.fCurrentElemDecl;
            this.fCurrentPSVI.fTypeDecl = this.fCurrentType;
            this.fCurrentPSVI.fNotation = this.fNotation;
            this.fCurrentPSVI.fValidationContext = this.fValidationRoot;
            this.fCurrentPSVI.fNil = this.fNil;
            int i = this.fElementDepth;
            if (i > this.fNFullValidationDepth) {
                this.fCurrentPSVI.fValidationAttempted = (short) 2;
            } else if (i > this.fNNoneValidationDepth) {
                this.fCurrentPSVI.fValidationAttempted = (short) 0;
            } else {
                this.fCurrentPSVI.fValidationAttempted = (short) 1;
            }
            int i2 = this.fNFullValidationDepth;
            int i3 = this.fElementDepth;
            if (i2 == i3) {
                this.fNFullValidationDepth = i3 - 1;
            }
            if (this.fNNoneValidationDepth == i3) {
                this.fNNoneValidationDepth = i3 - 1;
            }
            if (this.fDefaultValue != null) {
                this.fCurrentPSVI.fSpecified = true;
            }
            this.fCurrentPSVI.fValue.copyFrom(this.fValidatedInfo);
            if (this.fStrictAssess) {
                String[] strArrMergeContext = this.fXSIErrorReporter.mergeContext();
                this.fCurrentPSVI.fErrors = strArrMergeContext;
                this.fCurrentPSVI.fValidity = strArrMergeContext != null ? (short) 1 : (short) 2;
            } else {
                this.fCurrentPSVI.fValidity = (short) 0;
                this.fXSIErrorReporter.popContext();
            }
            if (z) {
                this.fCurrentPSVI.fGrammars = schemaGrammarArr;
                this.fCurrentPSVI.fSchemaInformation = null;
            }
        }
        return augmentations;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endGeneralEntity(String str, Augmentations augmentations) throws XNIException {
        this.fEntityRef = false;
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.endGeneralEntity(str, augmentations);
        }
    }

    @Override // org.apache.xerces.impl.xs.identity.FieldActivator
    public void endValueScopeFor(IdentityConstraint identityConstraint, int i) {
        this.fValueStoreCache.getValueStoreFor(identityConstraint, i).endValueScope();
    }

    void ensureStackCapacity() {
        int i = this.fElementDepth;
        if (i == this.fElemDeclStack.length) {
            int i2 = i + 8;
            boolean[] zArr = new boolean[i2];
            System.arraycopy(this.fSubElementStack, 0, zArr, 0, i);
            this.fSubElementStack = zArr;
            XSElementDecl[] xSElementDeclArr = new XSElementDecl[i2];
            System.arraycopy(this.fElemDeclStack, 0, xSElementDeclArr, 0, this.fElementDepth);
            this.fElemDeclStack = xSElementDeclArr;
            boolean[] zArr2 = new boolean[i2];
            System.arraycopy(this.fNilStack, 0, zArr2, 0, this.fElementDepth);
            this.fNilStack = zArr2;
            XSNotationDecl[] xSNotationDeclArr = new XSNotationDecl[i2];
            System.arraycopy(this.fNotationStack, 0, xSNotationDeclArr, 0, this.fElementDepth);
            this.fNotationStack = xSNotationDeclArr;
            XSTypeDefinition[] xSTypeDefinitionArr = new XSTypeDefinition[i2];
            System.arraycopy(this.fTypeStack, 0, xSTypeDefinitionArr, 0, this.fElementDepth);
            this.fTypeStack = xSTypeDefinitionArr;
            XSCMValidator[] xSCMValidatorArr = new XSCMValidator[i2];
            System.arraycopy(this.fCMStack, 0, xSCMValidatorArr, 0, this.fElementDepth);
            this.fCMStack = xSCMValidatorArr;
            boolean[] zArr3 = new boolean[i2];
            System.arraycopy(this.fSawTextStack, 0, zArr3, 0, this.fElementDepth);
            this.fSawTextStack = zArr3;
            boolean[] zArr4 = new boolean[i2];
            System.arraycopy(this.fStringContent, 0, zArr4, 0, this.fElementDepth);
            this.fStringContent = zArr4;
            boolean[] zArr5 = new boolean[i2];
            System.arraycopy(this.fStrictAssessStack, 0, zArr5, 0, this.fElementDepth);
            this.fStrictAssessStack = zArr5;
            int[][] iArr = new int[i2][];
            System.arraycopy(this.fCMStateStack, 0, iArr, 0, this.fElementDepth);
            this.fCMStateStack = iArr;
        }
    }

    SchemaGrammar findSchemaGrammar(short s, String str, org.apache.xerces.xni.QName qName, org.apache.xerces.xni.QName qName2, XMLAttributes xMLAttributes) {
        SchemaGrammar grammar = this.fGrammarBucket.getGrammar(str);
        boolean zContains = true;
        if (grammar == null) {
            this.fXSDDescription.setNamespace(str);
            XMLGrammarPool xMLGrammarPool = this.fGrammarPool;
            if (xMLGrammarPool != null && (grammar = (SchemaGrammar) xMLGrammarPool.retrieveGrammar(this.fXSDDescription)) != null && !this.fGrammarBucket.putGrammar(grammar, true, this.fNamespaceGrowth)) {
                this.fXSIErrorReporter.fErrorReporter.reportError(XSMessageFormatter.SCHEMA_DOMAIN, "GrammarConflict", null, (short) 0);
                grammar = null;
            }
        }
        if (this.fUseGrammarPoolOnly) {
            return grammar;
        }
        if (grammar != null && (!this.fNamespaceGrowth || hasSchemaComponent(grammar, s, qName2))) {
            return grammar;
        }
        this.fXSDDescription.reset();
        this.fXSDDescription.fContextType = s;
        this.fXSDDescription.setNamespace(str);
        this.fXSDDescription.fEnclosedElementName = qName;
        this.fXSDDescription.fTriggeringComponent = qName2;
        this.fXSDDescription.fAttributes = xMLAttributes;
        XMLLocator xMLLocator = this.fLocator;
        if (xMLLocator != null) {
            this.fXSDDescription.setBaseSystemId(xMLLocator.getExpandedSystemId());
        }
        Hashtable hashtable = this.fLocationPairs;
        if (str == null) {
            str = XMLSymbols.EMPTY_STRING;
        }
        Object obj = hashtable.get(str);
        if (obj != null) {
            String[] locationArray = ((XMLSchemaLoader.LocationArray) obj).getLocationArray();
            if (locationArray.length != 0) {
                setLocationHints(this.fXSDDescription, locationArray, grammar);
            }
        }
        if (grammar != null && this.fXSDDescription.fLocationHints == null) {
            return grammar;
        }
        if (grammar != null) {
            hashtable = EMPTY_TABLE;
        }
        try {
            XMLInputSource xMLInputSourceResolveDocument = XMLSchemaLoader.resolveDocument(this.fXSDDescription, hashtable, this.fEntityResolver);
            if (grammar != null && this.fNamespaceGrowth) {
                try {
                    zContains = true ^ grammar.getDocumentLocations().contains(XMLEntityManager.expandSystemId(xMLInputSourceResolveDocument.getSystemId(), xMLInputSourceResolveDocument.getBaseSystemId(), false));
                } catch (URI.MalformedURIException unused) {
                }
            }
            return zContains ? this.fSchemaLoader.loadSchema(this.fXSDDescription, xMLInputSourceResolveDocument, this.fLocationPairs) : grammar;
        } catch (IOException e) {
            String[] locationHints = this.fXSDDescription.getLocationHints();
            this.fXSIErrorReporter.fErrorReporter.reportError(XSMessageFormatter.SCHEMA_DOMAIN, "schema_reference.4", new Object[]{locationHints != null ? locationHints[0] : XMLSymbols.EMPTY_STRING}, (short) 0, (Exception) e);
            return grammar;
        }
    }

    XSTypeDefinition getAndCheckXsiType(org.apache.xerces.xni.QName qName, String str, XMLAttributes xMLAttributes) {
        SchemaGrammar schemaGrammarFindSchemaGrammar;
        try {
            org.apache.xerces.xni.QName qName2 = (org.apache.xerces.xni.QName) this.fQNameDV.validate(str, (ValidationContext) this.fValidationState, (ValidatedInfo) null);
            XSTypeDefinition globalTypeDecl = qName2.uri == SchemaSymbols.URI_SCHEMAFORSCHEMA ? SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(qName2.localpart) : null;
            if (globalTypeDecl == null && (schemaGrammarFindSchemaGrammar = findSchemaGrammar((short) 7, qName2.uri, qName, qName2, xMLAttributes)) != null) {
                globalTypeDecl = schemaGrammarFindSchemaGrammar.getGlobalTypeDecl(qName2.localpart);
            }
            if (globalTypeDecl == null) {
                reportSchemaError("cvc-elt.4.2", new Object[]{qName.rawname, str});
                return null;
            }
            if (this.fCurrentType != null) {
                XSElementDecl xSElementDecl = this.fCurrentElemDecl;
                short s = xSElementDecl != null ? xSElementDecl.fBlock : (short) 0;
                if (this.fCurrentType.getTypeCategory() == 15) {
                    s = (short) (s | ((XSComplexTypeDecl) this.fCurrentType).fBlock);
                }
                if (!XSConstraints.checkTypeDerivationOk(globalTypeDecl, this.fCurrentType, s)) {
                    reportSchemaError("cvc-elt.4.3", new Object[]{qName.rawname, str, XS10TypeHelper.getSchemaTypeName(this.fCurrentType)});
                }
            }
            return globalTypeDecl;
        } catch (InvalidDatatypeValueException e) {
            reportSchemaError(e.getKey(), e.getArgs());
            reportSchemaError("cvc-elt.4.1", new Object[]{qName.rawname, SchemaSymbols.URI_XSI + "," + SchemaSymbols.XSI_TYPE, str});
            return null;
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLDocumentSource
    public XMLDocumentHandler getDocumentHandler() {
        return this.fDocumentHandler;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public XMLDocumentSource getDocumentSource() {
        return this.fDocumentSource;
    }

    Augmentations getEmptyAugs(Augmentations augmentations) {
        if (augmentations == null) {
            augmentations = this.fAugmentations;
            augmentations.removeAllItems();
        }
        augmentations.putItem(Constants.ELEMENT_PSVI, this.fCurrentPSVI);
        this.fCurrentPSVI.reset();
        return augmentations;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public Boolean getFeatureDefault(String str) {
        int i = 0;
        while (true) {
            String[] strArr = RECOGNIZED_FEATURES;
            if (i >= strArr.length) {
                return null;
            }
            if (strArr[i].equals(str)) {
                return FEATURE_DEFAULTS[i];
            }
            i++;
        }
    }

    @Override // org.apache.xerces.impl.xs.XSElementDeclHelper
    public XSElementDecl getGlobalElementDecl(org.apache.xerces.xni.QName qName) {
        SchemaGrammar schemaGrammarFindSchemaGrammar = findSchemaGrammar((short) 5, qName.uri, null, qName, null);
        if (schemaGrammarFindSchemaGrammar != null) {
            return schemaGrammarFindSchemaGrammar.getGlobalElementDecl(qName.localpart);
        }
        return null;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public Object getPropertyDefault(String str) {
        int i = 0;
        while (true) {
            String[] strArr = RECOGNIZED_PROPERTIES;
            if (i >= strArr.length) {
                return null;
            }
            if (strArr[i].equals(str)) {
                return PROPERTY_DEFAULTS[i];
            }
            i++;
        }
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedFeatures() {
        return (String[]) RECOGNIZED_FEATURES.clone();
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public String[] getRecognizedProperties() {
        return (String[]) RECOGNIZED_PROPERTIES.clone();
    }

    boolean getXsiNil(org.apache.xerces.xni.QName qName, String str) {
        XSElementDecl xSElementDecl = this.fCurrentElemDecl;
        if (xSElementDecl != null && !xSElementDecl.getNillable()) {
            reportSchemaError("cvc-elt.3.1", new Object[]{qName.rawname, SchemaSymbols.URI_XSI + "," + SchemaSymbols.XSI_NIL});
            return false;
        }
        String strTrim = XMLChar.trim(str);
        if (!strTrim.equals("true") && !strTrim.equals(SchemaSymbols.ATTVAL_TRUE_1)) {
            return false;
        }
        XSElementDecl xSElementDecl2 = this.fCurrentElemDecl;
        if (xSElementDecl2 == null || xSElementDecl2.getConstraintType() != 2) {
            return true;
        }
        reportSchemaError("cvc-elt.3.2.2", new Object[]{qName.rawname, SchemaSymbols.URI_XSI + "," + SchemaSymbols.XSI_NIL});
        return true;
    }

    XMLString handleCharacters(XMLString xMLString) {
        short s;
        if (this.fSkipValidationDepth >= 0) {
            return xMLString;
        }
        this.fSawText = this.fSawText || xMLString.length > 0;
        if (this.fNormalizeData && (s = this.fWhiteSpace) != -1 && s != 0) {
            normalizeWhitespace(xMLString, s == 2);
            xMLString = this.fNormalizedStr;
        }
        if (this.fAppendBuffer) {
            this.fBuffer.append(xMLString.ch, xMLString.offset, xMLString.length);
        }
        XSTypeDefinition xSTypeDefinition = this.fCurrentType;
        if (xSTypeDefinition != null && xSTypeDefinition.getTypeCategory() == 15 && ((XSComplexTypeDecl) this.fCurrentType).fContentType == 2) {
            for (int i = xMLString.offset; i < xMLString.offset + xMLString.length; i++) {
                if (!XMLChar.isSpace(xMLString.ch[i])) {
                    this.fSawCharacters = true;
                    break;
                }
            }
        }
        return xMLString;
    }

    void handleEndDocument() {
        if (this.fIDCChecking) {
            this.fValueStoreCache.endDocument();
        }
    }

    Augmentations handleEndElement(org.apache.xerces.xni.QName qName, Augmentations augmentations) {
        Selector.Matcher matcher;
        IdentityConstraint identityConstraint;
        ValueStoreBase valueStoreFor;
        Selector.Matcher matcher2;
        IdentityConstraint identityConstraint2;
        boolean nillable;
        Object obj;
        short s;
        ShortList shortList;
        int i = this.fSkipValidationDepth;
        if (i >= 0) {
            int i2 = this.fElementDepth;
            if (i != i2 || i <= 0) {
                this.fElementDepth = i2 - 1;
            } else {
                this.fNFullValidationDepth = i - 1;
                this.fSkipValidationDepth = -1;
                int i3 = i2 - 1;
                this.fElementDepth = i3;
                this.fSubElement = this.fSubElementStack[i3];
                this.fCurrentElemDecl = this.fElemDeclStack[i3];
                this.fNil = this.fNilStack[i3];
                this.fNotation = this.fNotationStack[i3];
                this.fCurrentType = this.fTypeStack[i3];
                this.fCurrentCM = this.fCMStack[i3];
                this.fStrictAssess = this.fStrictAssessStack[i3];
                this.fCurrCMState = this.fCMStateStack[i3];
                this.fSawText = this.fSawTextStack[i3];
                this.fSawCharacters = this.fStringContent[i3];
            }
            if (this.fElementDepth == -1 && this.fFullChecking && !this.fUseGrammarPoolOnly) {
                XSConstraints.fullSchemaChecking(this.fGrammarBucket, this.fSubGroupHandler, this.fCMBuilder, this.fXSIErrorReporter.fErrorReporter);
            }
            return this.fAugPSVI ? getEmptyAugs(augmentations) : augmentations;
        }
        processElementContent(qName);
        if (this.fIDCChecking) {
            int matcherCount = this.fMatcherStack.getMatcherCount() - 1;
            for (int i4 = matcherCount; i4 >= 0; i4--) {
                XPathMatcher matcherAt = this.fMatcherStack.getMatcherAt(i4);
                XSElementDecl xSElementDecl = this.fCurrentElemDecl;
                XSTypeDefinition xSTypeDefinition = this.fCurrentType;
                if (xSElementDecl == null) {
                    obj = this.fValidatedInfo.actualValue;
                    s = this.fValidatedInfo.actualValueType;
                    shortList = this.fValidatedInfo.itemValueTypes;
                    nillable = false;
                } else {
                    nillable = xSElementDecl.getNillable();
                    obj = (this.fDefaultValue == null ? this.fValidatedInfo : this.fCurrentElemDecl.fDefault).actualValue;
                    s = (this.fDefaultValue == null ? this.fValidatedInfo : this.fCurrentElemDecl.fDefault).actualValueType;
                    shortList = (this.fDefaultValue == null ? this.fValidatedInfo : this.fCurrentElemDecl.fDefault).itemValueTypes;
                }
                matcherAt.endElement(qName, xSTypeDefinition, nillable, obj, s, shortList);
            }
            if (this.fMatcherStack.size() > 0) {
                this.fMatcherStack.popContext();
            }
            int matcherCount2 = this.fMatcherStack.getMatcherCount();
            for (int i5 = matcherCount; i5 >= matcherCount2; i5--) {
                XPathMatcher matcherAt2 = this.fMatcherStack.getMatcherAt(i5);
                if ((matcherAt2 instanceof Selector.Matcher) && (identityConstraint2 = (matcher2 = (Selector.Matcher) matcherAt2).getIdentityConstraint()) != null && identityConstraint2.getCategory() != 2) {
                    this.fValueStoreCache.transplant(identityConstraint2, matcher2.getInitialDepth());
                }
            }
            while (matcherCount >= matcherCount2) {
                XPathMatcher matcherAt3 = this.fMatcherStack.getMatcherAt(matcherCount);
                if ((matcherAt3 instanceof Selector.Matcher) && (identityConstraint = (matcher = (Selector.Matcher) matcherAt3).getIdentityConstraint()) != null && identityConstraint.getCategory() == 2 && (valueStoreFor = this.fValueStoreCache.getValueStoreFor(identityConstraint, matcher.getInitialDepth())) != null && valueStoreFor.fHasValue) {
                    valueStoreFor.endDocumentFragment();
                }
                matcherCount--;
            }
            this.fValueStoreCache.endElement();
        }
        int i6 = this.fElementDepth;
        int i7 = this.fIgnoreXSITypeDepth;
        if (i6 < i7) {
            this.fIgnoreXSITypeDepth = i7 - 1;
        }
        if (i6 == 0) {
            Iterator itCheckIDRefID = this.fValidationState.checkIDRefID();
            this.fValidationState.resetIDTables();
            if (itCheckIDRefID != null) {
                while (itCheckIDRefID.hasNext()) {
                    reportSchemaError("cvc-id.1", new Object[]{itCheckIDRefID.next()});
                }
            }
            if (this.fFullChecking && !this.fUseGrammarPoolOnly) {
                XSConstraints.fullSchemaChecking(this.fGrammarBucket, this.fSubGroupHandler, this.fCMBuilder, this.fXSIErrorReporter.fErrorReporter);
            }
            SchemaGrammar[] grammars = this.fGrammarBucket.getGrammars();
            if (this.fGrammarPool != null) {
                for (SchemaGrammar schemaGrammar : grammars) {
                    schemaGrammar.setImmutable(true);
                }
                this.fGrammarPool.cacheGrammars("http://www.w3.org/2001/XMLSchema", grammars);
            }
            return endElementPSVI(true, grammars, augmentations);
        }
        Augmentations augmentationsEndElementPSVI = endElementPSVI(false, null, augmentations);
        int i8 = this.fElementDepth - 1;
        this.fElementDepth = i8;
        this.fSubElement = this.fSubElementStack[i8];
        this.fCurrentElemDecl = this.fElemDeclStack[i8];
        this.fNil = this.fNilStack[i8];
        this.fNotation = this.fNotationStack[i8];
        this.fCurrentType = this.fTypeStack[i8];
        this.fCurrentCM = this.fCMStack[i8];
        this.fStrictAssess = this.fStrictAssessStack[i8];
        this.fCurrCMState = this.fCMStateStack[i8];
        this.fSawText = this.fSawTextStack[i8];
        this.fSawCharacters = this.fStringContent[i8];
        this.fWhiteSpace = (short) -1;
        this.fAppendBuffer = false;
        this.fUnionType = false;
        return augmentationsEndElementPSVI;
    }

    void handleIgnorableWhitespace(XMLString xMLString) {
    }

    void handleStartDocument(XMLLocator xMLLocator, String str) {
        if (this.fIDCChecking) {
            this.fValueStoreCache.startDocument();
        }
        if (this.fAugPSVI) {
            this.fCurrentPSVI.fGrammars = null;
            this.fCurrentPSVI.fSchemaInformation = null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:183:0x035c  */
    /* JADX WARN: Code duplicated, block: B:44:0x0111  */
    Augmentations handleStartElement(org.apache.xerces.xni.QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) {
        Object objOneTransition;
        XSWildcardDecl xSWildcardDecl;
        int i;
        short whitespace;
        SchemaGrammar schemaGrammarFindSchemaGrammar;
        int[] iArrOccurenceInfo;
        int i2;
        if (this.fElementDepth == -1 && this.fValidationManager.isGrammarFound() && this.fSchemaType == null) {
            this.fSchemaDynamicValidation = true;
        }
        if (!this.fUseGrammarPoolOnly) {
            storeLocations(xMLAttributes.getValue(SchemaSymbols.URI_XSI, SchemaSymbols.XSI_SCHEMALOCATION), xMLAttributes.getValue(SchemaSymbols.URI_XSI, SchemaSymbols.XSI_NONAMESPACESCHEMALOCATION));
        }
        if (this.fSkipValidationDepth >= 0) {
            this.fElementDepth++;
            return this.fAugPSVI ? getEmptyAugs(augmentations) : augmentations;
        }
        XSCMValidator xSCMValidator = this.fCurrentCM;
        if (xSCMValidator != null) {
            objOneTransition = xSCMValidator.oneTransition(qName, this.fCurrCMState, this.fSubGroupHandler);
            if (this.fCurrCMState[0] == -1) {
                if (((XSComplexTypeDecl) this.fCurrentType).fParticle != null) {
                    Vector vectorWhatCanGoHere = this.fCurrentCM.whatCanGoHere(this.fCurrCMState);
                    if (vectorWhatCanGoHere.size() > 0) {
                        String strExpectedStr = expectedStr(vectorWhatCanGoHere);
                        int[] iArrOccurenceInfo2 = this.fCurrentCM.occurenceInfo(this.fCurrCMState);
                        String str = qName.uri != null ? "{\"" + qName.uri + "\":" + qName.localpart + "}" : qName.localpart;
                        if (iArrOccurenceInfo2 != null) {
                            int i3 = iArrOccurenceInfo2[0];
                            int i4 = iArrOccurenceInfo2[1];
                            int i5 = iArrOccurenceInfo2[2];
                            if (i5 < i3) {
                                int i6 = i3 - i5;
                                if (i6 > 1) {
                                    reportSchemaError("cvc-complex-type.2.4.h", new Object[]{qName.rawname, this.fCurrentCM.getTermName(iArrOccurenceInfo2[3]), Integer.toString(i3), Integer.toString(i6)});
                                } else {
                                    reportSchemaError("cvc-complex-type.2.4.g", new Object[]{qName.rawname, this.fCurrentCM.getTermName(iArrOccurenceInfo2[3]), Integer.toString(i3)});
                                }
                            } else if (i5 < i4 || i4 == -1) {
                                reportSchemaError("cvc-complex-type.2.4.a", new Object[]{str, strExpectedStr});
                            } else {
                                reportSchemaError("cvc-complex-type.2.4.e", new Object[]{qName.rawname, strExpectedStr, Integer.toString(i4)});
                            }
                        } else {
                            reportSchemaError("cvc-complex-type.2.4.a", new Object[]{str, strExpectedStr});
                        }
                    } else {
                        iArrOccurenceInfo = this.fCurrentCM.occurenceInfo(this.fCurrCMState);
                        if (iArrOccurenceInfo != null || iArrOccurenceInfo[2] < (i2 = iArrOccurenceInfo[1]) || i2 == -1) {
                            reportSchemaError("cvc-complex-type.2.4.d", new Object[]{qName.rawname});
                        } else {
                            reportSchemaError("cvc-complex-type.2.4.f", new Object[]{this.fCurrentCM.getTermName(iArrOccurenceInfo[3]), Integer.toString(i2)});
                        }
                    }
                } else {
                    iArrOccurenceInfo = this.fCurrentCM.occurenceInfo(this.fCurrCMState);
                    if (iArrOccurenceInfo != null) {
                        reportSchemaError("cvc-complex-type.2.4.d", new Object[]{qName.rawname});
                    } else {
                        reportSchemaError("cvc-complex-type.2.4.d", new Object[]{qName.rawname});
                    }
                }
            }
        } else {
            objOneTransition = null;
        }
        if (this.fElementDepth != -1) {
            ensureStackCapacity();
            boolean[] zArr = this.fSubElementStack;
            int i7 = this.fElementDepth;
            zArr[i7] = true;
            this.fSubElement = false;
            this.fElemDeclStack[i7] = this.fCurrentElemDecl;
            this.fNilStack[i7] = this.fNil;
            this.fNotationStack[i7] = this.fNotation;
            this.fTypeStack[i7] = this.fCurrentType;
            this.fStrictAssessStack[i7] = this.fStrictAssess;
            this.fCMStack[i7] = this.fCurrentCM;
            this.fCMStateStack[i7] = this.fCurrCMState;
            this.fSawTextStack[i7] = this.fSawText;
            this.fStringContent[i7] = this.fSawCharacters;
        }
        this.fElementDepth++;
        this.fCurrentElemDecl = null;
        this.fCurrentType = null;
        this.fStrictAssess = true;
        this.fNil = false;
        this.fNotation = null;
        this.fBuffer.setLength(0);
        this.fSawText = false;
        this.fSawCharacters = false;
        if (objOneTransition == null) {
            xSWildcardDecl = null;
        } else if (objOneTransition instanceof XSElementDecl) {
            this.fCurrentElemDecl = (XSElementDecl) objOneTransition;
            xSWildcardDecl = null;
        } else {
            xSWildcardDecl = (XSWildcardDecl) objOneTransition;
        }
        if (xSWildcardDecl != null && xSWildcardDecl.fProcessContents == 2) {
            this.fSkipValidationDepth = this.fElementDepth;
            return this.fAugPSVI ? getEmptyAugs(augmentations) : augmentations;
        }
        if (this.fElementDepth == 0) {
            XSElementDecl xSElementDecl = this.fRootElementDeclaration;
            if (xSElementDecl != null) {
                this.fCurrentElemDecl = xSElementDecl;
                checkElementMatchesRootElementDecl(xSElementDecl, qName);
            } else {
                QName qName2 = this.fRootElementDeclQName;
                if (qName2 != null) {
                    processRootElementDeclQName(qName2, qName);
                } else {
                    XSTypeDefinition xSTypeDefinition = this.fRootTypeDefinition;
                    if (xSTypeDefinition != null) {
                        this.fCurrentType = xSTypeDefinition;
                    } else {
                        QName qName3 = this.fRootTypeQName;
                        if (qName3 != null) {
                            processRootTypeQName(qName3);
                        }
                    }
                }
            }
        }
        if (this.fCurrentType == null) {
            if (this.fCurrentElemDecl == null && (schemaGrammarFindSchemaGrammar = findSchemaGrammar((short) 5, qName.uri, null, qName, xMLAttributes)) != null) {
                this.fCurrentElemDecl = schemaGrammarFindSchemaGrammar.getGlobalElementDecl(qName.localpart);
            }
            XSElementDecl xSElementDecl2 = this.fCurrentElemDecl;
            if (xSElementDecl2 != null) {
                this.fCurrentType = xSElementDecl2.fType;
            }
        }
        int i8 = this.fElementDepth;
        int i9 = this.fIgnoreXSITypeDepth;
        if (i8 == i9 && this.fCurrentElemDecl == null) {
            this.fIgnoreXSITypeDepth = i9 + 1;
        }
        String value = i8 >= this.fIgnoreXSITypeDepth ? xMLAttributes.getValue(SchemaSymbols.URI_XSI, SchemaSymbols.XSI_TYPE) : null;
        if (this.fCurrentType == null && value == null) {
            int i10 = this.fElementDepth;
            if (i10 == 0) {
                if (this.fDynamicValidation || this.fSchemaDynamicValidation) {
                    XMLDocumentSource xMLDocumentSource = this.fDocumentSource;
                    if (xMLDocumentSource == null) {
                        this.fSkipValidationDepth = i10;
                        return this.fAugPSVI ? getEmptyAugs(augmentations) : augmentations;
                    }
                    xMLDocumentSource.setDocumentHandler(this.fDocumentHandler);
                    XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
                    if (xMLDocumentHandler != null) {
                        xMLDocumentHandler.setDocumentSource(this.fDocumentSource);
                    }
                    this.fElementDepth = -2;
                    return augmentations;
                }
                this.fXSIErrorReporter.fErrorReporter.reportError(XSMessageFormatter.SCHEMA_DOMAIN, "cvc-elt.1.a", new Object[]{qName.rawname}, (short) 1);
            } else if (xSWildcardDecl != null && xSWildcardDecl.fProcessContents == 1) {
                reportSchemaError("cvc-complex-type.2.4.c", new Object[]{qName.rawname});
            }
            this.fCurrentType = SchemaGrammar.fAnyType;
            this.fStrictAssess = false;
            this.fNFullValidationDepth = this.fElementDepth;
            this.fAppendBuffer = false;
            this.fXSIErrorReporter.pushContext();
        } else {
            this.fXSIErrorReporter.pushContext();
            if (value != null) {
                XSTypeDefinition xSTypeDefinition2 = this.fCurrentType;
                XSTypeDefinition andCheckXsiType = getAndCheckXsiType(qName, value, xMLAttributes);
                this.fCurrentType = andCheckXsiType;
                if (andCheckXsiType == null) {
                    if (xSTypeDefinition2 == null) {
                        xSTypeDefinition2 = SchemaGrammar.fAnyType;
                    }
                    this.fCurrentType = xSTypeDefinition2;
                }
            }
            this.fNNoneValidationDepth = this.fElementDepth;
            XSElementDecl xSElementDecl3 = this.fCurrentElemDecl;
            if ((xSElementDecl3 == null || xSElementDecl3.getConstraintType() != 2) && this.fCurrentType.getTypeCategory() != 16) {
                this.fAppendBuffer = ((XSComplexTypeDecl) this.fCurrentType).fContentType == 1;
            } else {
                this.fAppendBuffer = true;
            }
        }
        XSElementDecl xSElementDecl4 = this.fCurrentElemDecl;
        if (xSElementDecl4 != null && xSElementDecl4.getAbstract()) {
            reportSchemaError("cvc-elt.2", new Object[]{qName.rawname});
        }
        if (this.fElementDepth == 0) {
            this.fValidationRoot = qName.rawname;
        }
        if (this.fNormalizeData) {
            this.fFirstChunk = true;
            i = 0;
            this.fTrailing = false;
            this.fUnionType = false;
            this.fWhiteSpace = (short) -1;
        } else {
            i = 0;
        }
        try {
            if (this.fCurrentType.getTypeCategory() == 15) {
                XSComplexTypeDecl xSComplexTypeDecl = (XSComplexTypeDecl) this.fCurrentType;
                if (xSComplexTypeDecl.getAbstract()) {
                    reportSchemaError("cvc-type.2", new Object[]{qName.rawname});
                }
                if (this.fNormalizeData && xSComplexTypeDecl.fContentType == 1) {
                    if (xSComplexTypeDecl.fXSSimpleType.getVariety() == 3) {
                        this.fUnionType = true;
                    } else {
                        whitespace = xSComplexTypeDecl.fXSSimpleType.getWhitespace();
                        this.fWhiteSpace = whitespace;
                    }
                }
            } else if (this.fNormalizeData) {
                XSSimpleType xSSimpleType = (XSSimpleType) this.fCurrentType;
                if (xSSimpleType.getVariety() == 3) {
                    this.fUnionType = true;
                } else {
                    whitespace = xSSimpleType.getWhitespace();
                    this.fWhiteSpace = whitespace;
                }
            }
        } catch (DatatypeException unused) {
        }
        this.fCurrentCM = null;
        if (this.fCurrentType.getTypeCategory() == 15) {
            this.fCurrentCM = ((XSComplexTypeDecl) this.fCurrentType).getContentModel(this.fCMBuilder);
        }
        this.fCurrCMState = null;
        XSCMValidator xSCMValidator2 = this.fCurrentCM;
        if (xSCMValidator2 != null) {
            this.fCurrCMState = xSCMValidator2.startContentModel();
        }
        String value2 = xMLAttributes.getValue(SchemaSymbols.URI_XSI, SchemaSymbols.XSI_NIL);
        if (value2 != null && this.fCurrentElemDecl != null) {
            this.fNil = getXsiNil(qName, value2);
        }
        XSAttributeGroupDecl attrGrp = this.fCurrentType.getTypeCategory() == 15 ? ((XSComplexTypeDecl) this.fCurrentType).getAttrGrp() : null;
        if (this.fIDCChecking) {
            this.fValueStoreCache.startElement();
            this.fMatcherStack.pushContext();
            XSElementDecl xSElementDecl5 = this.fCurrentElemDecl;
            if (xSElementDecl5 != null && xSElementDecl5.fIDCPos > 0) {
                this.fIdConstraint = true;
                this.fValueStoreCache.initValueStoresFor(this.fCurrentElemDecl, this);
            }
        }
        processAttributes(qName, xMLAttributes, attrGrp);
        if (attrGrp != null) {
            addDefaultAttributes(qName, xMLAttributes, attrGrp);
        }
        int matcherCount = this.fMatcherStack.getMatcherCount();
        for (int i11 = i; i11 < matcherCount; i11++) {
            this.fMatcherStack.getMatcherAt(i11).startElement(qName, xMLAttributes);
        }
        if (!this.fAugPSVI) {
            return augmentations;
        }
        Augmentations emptyAugs = getEmptyAugs(augmentations);
        this.fCurrentPSVI.fValidationContext = this.fValidationRoot;
        this.fCurrentPSVI.fDeclaration = this.fCurrentElemDecl;
        this.fCurrentPSVI.fTypeDecl = this.fCurrentType;
        this.fCurrentPSVI.fNotation = this.fNotation;
        this.fCurrentPSVI.fNil = this.fNil;
        return emptyAugs;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void ignorableWhitespace(XMLString xMLString, Augmentations augmentations) throws XNIException {
        handleIgnorableWhitespace(xMLString);
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.ignorableWhitespace(xMLString, augmentations);
        }
    }

    /* JADX WARN: Code duplicated, block: B:43:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:44:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:46:0x00cc  */
    void processAttributes(org.apache.xerces.xni.QName qName, XMLAttributes xMLAttributes, XSAttributeGroupDecl xSAttributeGroupDecl) {
        XSWildcardDecl xSWildcardDecl;
        XSObjectList xSObjectList;
        int length;
        String str;
        int i;
        XSWildcardDecl xSWildcardDecl2;
        int i2;
        XSObjectList xSObjectList2;
        XSAttributeUseImpl xSAttributeUseImpl;
        XSObjectList xSObjectList3;
        boolean z;
        char c;
        String str2;
        XSAttributeDecl xSAttributeDecl;
        String str3;
        XSAttributeDecl xSAttributeDecl2;
        XSAttributeDecl xSAttributeDecl3;
        int length2 = xMLAttributes.getLength();
        XSTypeDefinition xSTypeDefinition = this.fCurrentType;
        boolean z2 = xSTypeDefinition == null || xSTypeDefinition.getTypeCategory() == 16;
        if (z2) {
            xSWildcardDecl = null;
            xSObjectList = null;
            length = 0;
        } else {
            XSObjectList attributeUses = xSAttributeGroupDecl.getAttributeUses();
            xSObjectList = attributeUses;
            length = attributeUses.getLength();
            xSWildcardDecl = xSAttributeGroupDecl.fAttributeWC;
        }
        AttributePSVImpl attributePSVImpl = null;
        String str4 = null;
        int i3 = 0;
        while (i3 < length2) {
            xMLAttributes.getName(i3, this.fTempQName);
            if (this.fAugPSVI || this.fIdConstraint) {
                Augmentations augmentations = xMLAttributes.getAugmentations(i3);
                AttributePSVImpl attributePSVImpl2 = (AttributePSVImpl) augmentations.getItem(Constants.ATTRIBUTE_PSVI);
                if (attributePSVImpl2 != null) {
                    attributePSVImpl2.reset();
                    attributePSVImpl = attributePSVImpl2;
                } else {
                    AttributePSVImpl attributePSVImpl3 = new AttributePSVImpl();
                    augmentations.putItem(Constants.ATTRIBUTE_PSVI, attributePSVImpl3);
                    attributePSVImpl = attributePSVImpl3;
                }
                attributePSVImpl.fValidationContext = this.fValidationRoot;
            }
            AttributePSVImpl attributePSVImpl4 = attributePSVImpl;
            if (this.fTempQName.uri != SchemaSymbols.URI_XSI) {
                str = str4;
                i = i3;
                xSWildcardDecl2 = xSWildcardDecl;
                i2 = length;
                xSObjectList2 = xSObjectList;
                if (this.fTempQName.rawname != XMLSymbols.PREFIX_XMLNS || this.fTempQName.rawname.startsWith("xmlns:")) {
                    xSObjectList3 = xSObjectList2;
                    str3 = str;
                    c = 16;
                    str4 = str3;
                } else {
                    if (z2) {
                        reportSchemaError("cvc-type.3.1.1", new Object[]{qName.rawname, this.fTempQName.rawname});
                    } else {
                        int i4 = 0;
                        while (true) {
                            if (i4 >= i2) {
                                xSAttributeUseImpl = null;
                                break;
                            }
                            XSAttributeUseImpl xSAttributeUseImpl2 = (XSAttributeUseImpl) xSObjectList2.item(i4);
                            if (xSAttributeUseImpl2.fAttrDecl.fName == this.fTempQName.localpart && xSAttributeUseImpl2.fAttrDecl.fTargetNamespace == this.fTempQName.uri) {
                                xSAttributeUseImpl = xSAttributeUseImpl2;
                                break;
                            }
                            i4++;
                        }
                        if (xSAttributeUseImpl != null || (xSWildcardDecl2 != null && xSWildcardDecl2.allowNamespace(this.fTempQName.uri))) {
                            if (xSAttributeUseImpl != null) {
                                xSAttributeDecl = xSAttributeUseImpl.fAttrDecl;
                                xSObjectList3 = xSObjectList2;
                                z = true;
                                c = 16;
                            } else {
                                if (xSWildcardDecl2.fProcessContents != 2) {
                                    xSObjectList3 = xSObjectList2;
                                    SchemaGrammar schemaGrammarFindSchemaGrammar = findSchemaGrammar((short) 6, this.fTempQName.uri, qName, this.fTempQName, xMLAttributes);
                                    XSAttributeDecl globalAttributeDecl = schemaGrammarFindSchemaGrammar != null ? schemaGrammarFindSchemaGrammar.getGlobalAttributeDecl(this.fTempQName.localpart) : null;
                                    if (globalAttributeDecl == null) {
                                        if (xSWildcardDecl2.fProcessContents == 1) {
                                            reportSchemaError("cvc-complex-type.3.2.2", new Object[]{qName.rawname, this.fTempQName.rawname});
                                        }
                                        str3 = str;
                                        c = 16;
                                    } else {
                                        z = true;
                                        c = 16;
                                        if (globalAttributeDecl.fType.getTypeCategory() == 16 && globalAttributeDecl.fType.isIDType()) {
                                            str2 = str;
                                            if (str2 != null) {
                                                reportSchemaError("cvc-complex-type.5.1", new Object[]{qName.rawname, globalAttributeDecl.fName, str2});
                                            } else {
                                                str2 = globalAttributeDecl.fName;
                                            }
                                        } else {
                                            str2 = str;
                                        }
                                        xSAttributeDecl = globalAttributeDecl;
                                        str = str2;
                                    }
                                }
                                str4 = str3;
                            }
                            processOneAttribute(qName, xMLAttributes, i, xSAttributeDecl, xSAttributeUseImpl, attributePSVImpl4);
                            str4 = str;
                        } else {
                            reportSchemaError("cvc-complex-type.3.2.2", new Object[]{qName.rawname, this.fTempQName.rawname});
                            this.fNFullValidationDepth = this.fElementDepth;
                        }
                    }
                    xSObjectList3 = xSObjectList2;
                    str3 = str;
                    c = 16;
                    str4 = str3;
                }
            } else {
                if (this.fTempQName.localpart == SchemaSymbols.XSI_TYPE) {
                    xSAttributeDecl3 = XSI_TYPE;
                } else if (this.fTempQName.localpart == SchemaSymbols.XSI_NIL) {
                    xSAttributeDecl3 = XSI_NIL;
                } else if (this.fTempQName.localpart == SchemaSymbols.XSI_SCHEMALOCATION) {
                    xSAttributeDecl3 = XSI_SCHEMALOCATION;
                } else {
                    if (this.fTempQName.localpart == SchemaSymbols.XSI_NONAMESPACESCHEMALOCATION) {
                        xSAttributeDecl3 = XSI_NONAMESPACESCHEMALOCATION;
                    } else {
                        xSAttributeDecl2 = null;
                    }
                    if (xSAttributeDecl2 != null) {
                        str = str4;
                        i = i3;
                        xSWildcardDecl2 = xSWildcardDecl;
                        i2 = length;
                        xSObjectList2 = xSObjectList;
                        processOneAttribute(qName, xMLAttributes, i3, xSAttributeDecl2, null, attributePSVImpl4);
                    } else {
                        str = str4;
                        i = i3;
                        xSWildcardDecl2 = xSWildcardDecl;
                        i2 = length;
                        xSObjectList2 = xSObjectList;
                        if (this.fTempQName.rawname != XMLSymbols.PREFIX_XMLNS) {
                        }
                    }
                    xSObjectList3 = xSObjectList2;
                    str3 = str;
                    c = 16;
                    str4 = str3;
                }
                xSAttributeDecl2 = xSAttributeDecl3;
                if (xSAttributeDecl2 != null) {
                    str = str4;
                    i = i3;
                    xSWildcardDecl2 = xSWildcardDecl;
                    i2 = length;
                    xSObjectList2 = xSObjectList;
                    processOneAttribute(qName, xMLAttributes, i3, xSAttributeDecl2, null, attributePSVImpl4);
                } else {
                    str = str4;
                    i = i3;
                    xSWildcardDecl2 = xSWildcardDecl;
                    i2 = length;
                    xSObjectList2 = xSObjectList;
                    if (this.fTempQName.rawname != XMLSymbols.PREFIX_XMLNS) {
                    }
                }
                xSObjectList3 = xSObjectList2;
                str3 = str;
                c = 16;
                str4 = str3;
            }
            i3 = i + 1;
            xSWildcardDecl = xSWildcardDecl2;
            attributePSVImpl = attributePSVImpl4;
            length = i2;
            xSObjectList = xSObjectList3;
        }
        String str5 = str4;
        if (z2 || xSAttributeGroupDecl.fIDAttrName == null || str5 == null) {
            return;
        }
        reportSchemaError("cvc-complex-type.5.2", new Object[]{qName.rawname, str5, xSAttributeGroupDecl.fIDAttrName});
    }

    void processElementContent(org.apache.xerces.xni.QName qName) {
        XSElementDecl xSElementDecl = this.fCurrentElemDecl;
        if (xSElementDecl != null && xSElementDecl.fDefault != null && !this.fSawText && !this.fSubElement && !this.fNil) {
            String strStringValue = this.fCurrentElemDecl.fDefault.stringValue();
            int length = strStringValue.length();
            if (this.fNormalizedStr.ch == null || this.fNormalizedStr.ch.length < length) {
                this.fNormalizedStr.ch = new char[length];
            }
            strStringValue.getChars(0, length, this.fNormalizedStr.ch, 0);
            this.fNormalizedStr.offset = 0;
            this.fNormalizedStr.length = length;
            this.fDefaultValue = this.fNormalizedStr;
        }
        this.fValidatedInfo.normalizedValue = null;
        if (this.fNil && (this.fSubElement || this.fSawText)) {
            reportSchemaError("cvc-elt.3.2.1", new Object[]{qName.rawname, SchemaSymbols.URI_XSI + "," + SchemaSymbols.XSI_NIL});
        }
        this.fValidatedInfo.reset();
        XSElementDecl xSElementDecl2 = this.fCurrentElemDecl;
        if (xSElementDecl2 == null || xSElementDecl2.getConstraintType() == 0 || this.fSubElement || this.fSawText || this.fNil) {
            Object objElementLocallyValidType = elementLocallyValidType(qName, this.fBuffer);
            XSElementDecl xSElementDecl3 = this.fCurrentElemDecl;
            if (xSElementDecl3 != null && xSElementDecl3.getConstraintType() == 2 && !this.fNil) {
                String string = this.fBuffer.toString();
                if (this.fSubElement) {
                    reportSchemaError("cvc-elt.5.2.2.1", new Object[]{qName.rawname});
                }
                if (this.fCurrentType.getTypeCategory() == 15) {
                    XSComplexTypeDecl xSComplexTypeDecl = (XSComplexTypeDecl) this.fCurrentType;
                    if (xSComplexTypeDecl.fContentType == 3) {
                        if (!this.fCurrentElemDecl.fDefault.normalizedValue.equals(string)) {
                            reportSchemaError("cvc-elt.5.2.2.2.1", new Object[]{qName.rawname, string, this.fCurrentElemDecl.fDefault.normalizedValue});
                        }
                    } else if (xSComplexTypeDecl.fContentType == 1 && objElementLocallyValidType != null && (!ValidatedInfo.isComparable(this.fValidatedInfo, this.fCurrentElemDecl.fDefault) || !objElementLocallyValidType.equals(this.fCurrentElemDecl.fDefault.actualValue))) {
                        reportSchemaError("cvc-elt.5.2.2.2.2", new Object[]{qName.rawname, string, this.fCurrentElemDecl.fDefault.stringValue()});
                    }
                } else if (this.fCurrentType.getTypeCategory() == 16 && objElementLocallyValidType != null && (!ValidatedInfo.isComparable(this.fValidatedInfo, this.fCurrentElemDecl.fDefault) || !objElementLocallyValidType.equals(this.fCurrentElemDecl.fDefault.actualValue))) {
                    reportSchemaError("cvc-elt.5.2.2.2.2", new Object[]{qName.rawname, string, this.fCurrentElemDecl.fDefault.stringValue()});
                }
            }
        } else {
            if (this.fCurrentType != this.fCurrentElemDecl.fType && XSConstraints.ElementDefaultValidImmediate(this.fCurrentType, this.fCurrentElemDecl.fDefault.stringValue(), this.fState4XsiType, null) == null) {
                reportSchemaError("cvc-elt.5.1.1", new Object[]{qName.rawname, this.fCurrentType.getName(), this.fCurrentElemDecl.fDefault.stringValue()});
            }
            elementLocallyValidType(qName, this.fCurrentElemDecl.fDefault.stringValue());
        }
        if (this.fDefaultValue == null && this.fNormalizeData && this.fDocumentHandler != null && this.fUnionType) {
            String string2 = this.fValidatedInfo.normalizedValue;
            if (string2 == null) {
                string2 = this.fBuffer.toString();
            }
            int length2 = string2.length();
            if (this.fNormalizedStr.ch == null || this.fNormalizedStr.ch.length < length2) {
                this.fNormalizedStr.ch = new char[length2];
            }
            string2.getChars(0, length2, this.fNormalizedStr.ch, 0);
            this.fNormalizedStr.offset = 0;
            this.fNormalizedStr.length = length2;
            this.fDocumentHandler.characters(this.fNormalizedStr, null);
        }
    }

    void processOneAttribute(org.apache.xerces.xni.QName qName, XMLAttributes xMLAttributes, int i, XSAttributeDecl xSAttributeDecl, XSAttributeUseImpl xSAttributeUseImpl, AttributePSVImpl attributePSVImpl) {
        String value = xMLAttributes.getValue(i);
        this.fXSIErrorReporter.pushContext();
        XSSimpleType xSSimpleType = xSAttributeDecl.fType;
        Object objValidate = null;
        try {
            objValidate = xSSimpleType.validate(value, (ValidationContext) this.fValidationState, this.fValidatedInfo);
            if (this.fNormalizeData) {
                xMLAttributes.setValue(i, this.fValidatedInfo.normalizedValue);
            }
            if (xSSimpleType.getVariety() == 1 && xSSimpleType.getPrimitiveKind() == 20) {
                org.apache.xerces.xni.QName qName2 = (org.apache.xerces.xni.QName) objValidate;
                SchemaGrammar grammar = this.fGrammarBucket.getGrammar(qName2.uri);
                if (grammar != null) {
                    this.fNotation = grammar.getGlobalNotationDecl(qName2.localpart);
                }
            }
        } catch (InvalidDatatypeValueException e) {
            reportSchemaError(e.getKey(), e.getArgs());
            reportSchemaError("cvc-attribute.3", new Object[]{qName.rawname, this.fTempQName.rawname, value, xSSimpleType instanceof XSSimpleTypeDecl ? ((XSSimpleTypeDecl) xSSimpleType).getTypeName() : xSSimpleType.getName()});
        }
        if (objValidate != null && xSAttributeDecl.getConstraintType() == 2 && (!ValidatedInfo.isComparable(this.fValidatedInfo, xSAttributeDecl.fDefault) || !objValidate.equals(xSAttributeDecl.fDefault.actualValue))) {
            reportSchemaError("cvc-attribute.4", new Object[]{qName.rawname, this.fTempQName.rawname, value, xSAttributeDecl.fDefault.stringValue()});
        }
        if (objValidate != null && xSAttributeUseImpl != null && xSAttributeUseImpl.fConstraintType == 2 && (!ValidatedInfo.isComparable(this.fValidatedInfo, xSAttributeUseImpl.fDefault) || !objValidate.equals(xSAttributeUseImpl.fDefault.actualValue))) {
            reportSchemaError("cvc-complex-type.3.1", new Object[]{qName.rawname, this.fTempQName.rawname, value, xSAttributeUseImpl.fDefault.stringValue()});
        }
        if (this.fIdConstraint) {
            attributePSVImpl.fValue.copyFrom(this.fValidatedInfo);
        }
        if (this.fAugPSVI) {
            attributePSVImpl.fDeclaration = xSAttributeDecl;
            attributePSVImpl.fTypeDecl = xSSimpleType;
            attributePSVImpl.fValue.copyFrom(this.fValidatedInfo);
            attributePSVImpl.fValidationAttempted = (short) 2;
            this.fNNoneValidationDepth = this.fElementDepth;
            String[] strArrMergeContext = this.fXSIErrorReporter.mergeContext();
            attributePSVImpl.fErrors = strArrMergeContext;
            attributePSVImpl.fValidity = strArrMergeContext == null ? (short) 2 : (short) 1;
        }
    }

    void processRootElementDeclQName(QName qName, org.apache.xerces.xni.QName qName2) {
        String strAddSymbol = this.fSymbolTable.addSymbol(qName.getNamespaceURI());
        if (strAddSymbol != null && strAddSymbol.equals("")) {
            strAddSymbol = null;
        }
        SchemaGrammar schemaGrammarFindSchemaGrammar = findSchemaGrammar((short) 5, strAddSymbol, null, null, null);
        if (schemaGrammarFindSchemaGrammar != null) {
            this.fCurrentElemDecl = schemaGrammarFindSchemaGrammar.getGlobalElementDecl(qName.getLocalPart());
        }
        XSElementDecl xSElementDecl = this.fCurrentElemDecl;
        if (xSElementDecl == null) {
            reportSchemaError("cvc-elt.1.a", new Object[]{qName.getPrefix().equals("") ? qName.getLocalPart() : qName.getPrefix() + ":" + qName.getLocalPart()});
        } else {
            checkElementMatchesRootElementDecl(xSElementDecl, qName2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0043  */
    /* JADX WARN: Code duplicated, block: B:18:0x004d  */
    /* JADX WARN: Code duplicated, block: B:19:0x0052  */
    /* JADX WARN: Code duplicated, block: B:22:? A[RETURN, SYNTHETIC] */
    void processRootTypeQName(QName qName) {
        XSTypeDefinition globalTypeDecl;
        String localPart;
        String strAddSymbol = this.fSymbolTable.addSymbol(qName.getNamespaceURI());
        if (strAddSymbol != null && strAddSymbol.equals("")) {
            strAddSymbol = null;
        }
        String str = strAddSymbol;
        if (!SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(str)) {
            SchemaGrammar schemaGrammarFindSchemaGrammar = findSchemaGrammar((short) 5, str, null, null, null);
            if (schemaGrammarFindSchemaGrammar != null) {
                globalTypeDecl = schemaGrammarFindSchemaGrammar.getGlobalTypeDecl(qName.getLocalPart());
            }
            if (this.fCurrentType == null) {
                if (qName.getPrefix().equals("")) {
                    localPart = qName.getLocalPart();
                } else {
                    localPart = qName.getPrefix() + ":" + qName.getLocalPart();
                }
                reportSchemaError("cvc-type.1", new Object[]{localPart});
            }
        }
        globalTypeDecl = SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(qName.getLocalPart());
        this.fCurrentType = globalTypeDecl;
        if (this.fCurrentType == null) {
            if (qName.getPrefix().equals("")) {
                localPart = qName.getLocalPart();
            } else {
                localPart = qName.getPrefix() + ":" + qName.getLocalPart();
            }
            reportSchemaError("cvc-type.1", new Object[]{localPart});
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.processingInstruction(str, xMLString, augmentations);
        }
    }

    void reportSchemaError(String str, Object[] objArr) {
        if (this.fDoValidation) {
            this.fXSIErrorReporter.reportError(XSMessageFormatter.SCHEMA_DOMAIN, str, objArr, (short) 1);
        }
    }

    /* JADX WARN: Code duplicated, block: B:58:0x013a A[Catch: XMLConfigurationException -> 0x014f, TryCatch #7 {XMLConfigurationException -> 0x014f, blocks: (B:56:0x0132, B:58:0x013a, B:59:0x013c, B:60:0x013f, B:62:0x0143, B:63:0x0148), top: B:109:0x0132 }] */
    /* JADX WARN: Code duplicated, block: B:60:0x013f A[Catch: XMLConfigurationException -> 0x014f, TryCatch #7 {XMLConfigurationException -> 0x014f, blocks: (B:56:0x0132, B:58:0x013a, B:59:0x013c, B:60:0x013f, B:62:0x0143, B:63:0x0148), top: B:109:0x0132 }] */
    /* JADX WARN: Code duplicated, block: B:62:0x0143 A[Catch: XMLConfigurationException -> 0x014f, TryCatch #7 {XMLConfigurationException -> 0x014f, blocks: (B:56:0x0132, B:58:0x013a, B:59:0x013c, B:60:0x013f, B:62:0x0143, B:63:0x0148), top: B:109:0x0132 }] */
    /* JADX WARN: Code duplicated, block: B:63:0x0148 A[Catch: XMLConfigurationException -> 0x014f, TRY_LEAVE, TryCatch #7 {XMLConfigurationException -> 0x014f, blocks: (B:56:0x0132, B:58:0x013a, B:59:0x013c, B:60:0x013f, B:62:0x0143, B:63:0x0148), top: B:109:0x0132 }] */
    /* JADX WARN: Code duplicated, block: B:71:0x015e  */
    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void reset(XMLComponentManager xMLComponentManager) throws XMLConfigurationException {
        boolean feature;
        boolean feature2;
        Object property;
        this.fIdConstraint = false;
        this.fLocationPairs.clear();
        this.fExpandedLocationPairs.clear();
        this.fValidationState.resetIDTables();
        this.fSchemaLoader.reset(xMLComponentManager);
        this.fCurrentElemDecl = null;
        this.fCurrentCM = null;
        this.fCurrCMState = null;
        this.fSkipValidationDepth = -1;
        this.fNFullValidationDepth = -1;
        this.fNNoneValidationDepth = -1;
        this.fElementDepth = -1;
        this.fSubElement = false;
        this.fSchemaDynamicValidation = false;
        this.fEntityRef = false;
        this.fInCDATA = false;
        this.fMatcherStack.clear();
        this.fXSIErrorReporter.reset((XMLErrorReporter) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter"));
        try {
            feature = xMLComponentManager.getFeature(PARSER_SETTINGS);
        } catch (XMLConfigurationException unused) {
            feature = true;
        }
        if (!feature) {
            this.fValidationManager.addValidationState(this.fValidationState);
            this.nodeFactory.reset();
            XMLSchemaLoader.processExternalHints(this.fExternalSchemas, this.fExternalNoNamespaceSchema, this.fLocationPairs, this.fXSIErrorReporter.fErrorReporter);
            return;
        }
        this.nodeFactory.reset(xMLComponentManager);
        SymbolTable symbolTable = (SymbolTable) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
        if (symbolTable != this.fSymbolTable) {
            this.fSymbolTable = symbolTable;
        }
        try {
            this.fNamespaceGrowth = xMLComponentManager.getFeature(NAMESPACE_GROWTH);
        } catch (XMLConfigurationException unused2) {
            this.fNamespaceGrowth = false;
        }
        try {
            this.fDynamicValidation = xMLComponentManager.getFeature(DYNAMIC_VALIDATION);
        } catch (XMLConfigurationException unused3) {
            this.fDynamicValidation = false;
        }
        if (this.fDynamicValidation) {
            this.fDoValidation = true;
        } else {
            try {
                this.fDoValidation = xMLComponentManager.getFeature(VALIDATION);
            } catch (XMLConfigurationException unused4) {
                this.fDoValidation = false;
            }
        }
        if (this.fDoValidation) {
            try {
                this.fDoValidation = xMLComponentManager.getFeature(SCHEMA_VALIDATION);
            } catch (XMLConfigurationException unused5) {
            }
        }
        try {
            this.fFullChecking = xMLComponentManager.getFeature(SCHEMA_FULL_CHECKING);
        } catch (XMLConfigurationException unused6) {
            this.fFullChecking = false;
        }
        try {
            this.fNormalizeData = xMLComponentManager.getFeature(NORMALIZE_DATA);
        } catch (XMLConfigurationException unused7) {
            this.fNormalizeData = false;
        }
        try {
            this.fSchemaElementDefault = xMLComponentManager.getFeature(SCHEMA_ELEMENT_DEFAULT);
        } catch (XMLConfigurationException unused8) {
            this.fSchemaElementDefault = false;
        }
        try {
            this.fAugPSVI = xMLComponentManager.getFeature(SCHEMA_AUGMENT_PSVI);
        } catch (XMLConfigurationException unused9) {
            this.fAugPSVI = true;
        }
        try {
            this.fSchemaType = (String) xMLComponentManager.getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
        } catch (XMLConfigurationException unused10) {
            this.fSchemaType = null;
        }
        try {
            this.fUseGrammarPoolOnly = xMLComponentManager.getFeature(USE_GRAMMAR_POOL_ONLY);
        } catch (XMLConfigurationException unused11) {
            this.fUseGrammarPoolOnly = false;
        }
        this.fEntityResolver = (XMLEntityResolver) xMLComponentManager.getProperty(ENTITY_MANAGER);
        ValidationManager validationManager = (ValidationManager) xMLComponentManager.getProperty(VALIDATION_MANAGER);
        this.fValidationManager = validationManager;
        validationManager.addValidationState(this.fValidationState);
        this.fValidationState.setSymbolTable(this.fSymbolTable);
        try {
            try {
                try {
                    try {
                        try {
                            Object property2 = xMLComponentManager.getProperty(ROOT_TYPE_DEF);
                            try {
                                try {
                                    try {
                                        try {
                                            if (property2 != null) {
                                                if (property2 instanceof QName) {
                                                    this.fRootTypeQName = (QName) property2;
                                                } else {
                                                    this.fRootTypeDefinition = (XSTypeDefinition) property2;
                                                    this.fRootTypeQName = null;
                                                }
                                                property = xMLComponentManager.getProperty(ROOT_ELEMENT_DECL);
                                                if (property == null) {
                                                    if (property instanceof QName) {
                                                        this.fRootElementDeclQName = (QName) property;
                                                    } else {
                                                        this.fRootElementDeclaration = (XSElementDecl) property;
                                                        this.fRootElementDeclQName = null;
                                                    }
                                                    feature2 = xMLComponentManager.getFeature(IGNORE_XSI_TYPE);
                                                    this.fIgnoreXSITypeDepth = feature2 ? 0 : -1;
                                                    this.fIDCChecking = xMLComponentManager.getFeature(IDENTITY_CONSTRAINT_CHECKING);
                                                    this.fValidationState.setIdIdrefChecking(xMLComponentManager.getFeature(ID_IDREF_CHECKING));
                                                    this.fValidationState.setUnparsedEntityChecking(xMLComponentManager.getFeature(UNPARSED_ENTITY_CHECKING));
                                                    this.fExternalSchemas = (String) xMLComponentManager.getProperty(SCHEMA_LOCATION);
                                                    this.fExternalNoNamespaceSchema = (String) xMLComponentManager.getProperty(SCHEMA_NONS_LOCATION);
                                                    XMLSchemaLoader.processExternalHints(this.fExternalSchemas, this.fExternalNoNamespaceSchema, this.fLocationPairs, this.fXSIErrorReporter.fErrorReporter);
                                                    this.fJaxpSchemaSource = xMLComponentManager.getProperty("http://java.sun.com/xml/jaxp/properties/schemaSource");
                                                    this.fGrammarPool = (XMLGrammarPool) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool");
                                                    this.fState4XsiType.setSymbolTable(symbolTable);
                                                    this.fState4ApplyDefault.setSymbolTable(symbolTable);
                                                }
                                                this.fRootElementDeclQName = null;
                                                this.fRootElementDeclaration = null;
                                                feature2 = xMLComponentManager.getFeature(IGNORE_XSI_TYPE);
                                                this.fIgnoreXSITypeDepth = feature2 ? 0 : -1;
                                                this.fIDCChecking = xMLComponentManager.getFeature(IDENTITY_CONSTRAINT_CHECKING);
                                                this.fValidationState.setIdIdrefChecking(xMLComponentManager.getFeature(ID_IDREF_CHECKING));
                                                this.fValidationState.setUnparsedEntityChecking(xMLComponentManager.getFeature(UNPARSED_ENTITY_CHECKING));
                                                this.fExternalSchemas = (String) xMLComponentManager.getProperty(SCHEMA_LOCATION);
                                                this.fExternalNoNamespaceSchema = (String) xMLComponentManager.getProperty(SCHEMA_NONS_LOCATION);
                                                XMLSchemaLoader.processExternalHints(this.fExternalSchemas, this.fExternalNoNamespaceSchema, this.fLocationPairs, this.fXSIErrorReporter.fErrorReporter);
                                                this.fJaxpSchemaSource = xMLComponentManager.getProperty("http://java.sun.com/xml/jaxp/properties/schemaSource");
                                                this.fGrammarPool = (XMLGrammarPool) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool");
                                                this.fState4XsiType.setSymbolTable(symbolTable);
                                                this.fState4ApplyDefault.setSymbolTable(symbolTable);
                                            }
                                            this.fRootTypeQName = null;
                                            if (property == null) {
                                                if (property instanceof QName) {
                                                    this.fRootElementDeclQName = (QName) property;
                                                } else {
                                                    this.fRootElementDeclaration = (XSElementDecl) property;
                                                    this.fRootElementDeclQName = null;
                                                }
                                                feature2 = xMLComponentManager.getFeature(IGNORE_XSI_TYPE);
                                                this.fIgnoreXSITypeDepth = feature2 ? 0 : -1;
                                                this.fIDCChecking = xMLComponentManager.getFeature(IDENTITY_CONSTRAINT_CHECKING);
                                                this.fValidationState.setIdIdrefChecking(xMLComponentManager.getFeature(ID_IDREF_CHECKING));
                                                this.fValidationState.setUnparsedEntityChecking(xMLComponentManager.getFeature(UNPARSED_ENTITY_CHECKING));
                                                this.fExternalSchemas = (String) xMLComponentManager.getProperty(SCHEMA_LOCATION);
                                                this.fExternalNoNamespaceSchema = (String) xMLComponentManager.getProperty(SCHEMA_NONS_LOCATION);
                                                XMLSchemaLoader.processExternalHints(this.fExternalSchemas, this.fExternalNoNamespaceSchema, this.fLocationPairs, this.fXSIErrorReporter.fErrorReporter);
                                                this.fJaxpSchemaSource = xMLComponentManager.getProperty("http://java.sun.com/xml/jaxp/properties/schemaSource");
                                                this.fGrammarPool = (XMLGrammarPool) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool");
                                                this.fState4XsiType.setSymbolTable(symbolTable);
                                                this.fState4ApplyDefault.setSymbolTable(symbolTable);
                                            }
                                            this.fRootElementDeclQName = null;
                                            this.fExternalSchemas = (String) xMLComponentManager.getProperty(SCHEMA_LOCATION);
                                            this.fExternalNoNamespaceSchema = (String) xMLComponentManager.getProperty(SCHEMA_NONS_LOCATION);
                                        } catch (XMLConfigurationException unused12) {
                                            this.fExternalSchemas = null;
                                            this.fExternalNoNamespaceSchema = null;
                                        }
                                        feature2 = xMLComponentManager.getFeature(IGNORE_XSI_TYPE);
                                    } catch (XMLConfigurationException unused13) {
                                        feature2 = false;
                                    }
                                    this.fJaxpSchemaSource = xMLComponentManager.getProperty("http://java.sun.com/xml/jaxp/properties/schemaSource");
                                } catch (XMLConfigurationException unused14) {
                                    this.fJaxpSchemaSource = null;
                                }
                                this.fValidationState.setIdIdrefChecking(xMLComponentManager.getFeature(ID_IDREF_CHECKING));
                            } catch (XMLConfigurationException unused15) {
                                this.fValidationState.setIdIdrefChecking(true);
                            }
                            this.fRootTypeDefinition = null;
                        } catch (XMLConfigurationException unused16) {
                            this.fRootTypeQName = null;
                            this.fRootTypeDefinition = null;
                        }
                        property = xMLComponentManager.getProperty(ROOT_ELEMENT_DECL);
                        this.fRootElementDeclaration = null;
                    } catch (XMLConfigurationException unused17) {
                        this.fRootElementDeclQName = null;
                        this.fRootElementDeclaration = null;
                    }
                    this.fIDCChecking = xMLComponentManager.getFeature(IDENTITY_CONSTRAINT_CHECKING);
                } catch (XMLConfigurationException unused18) {
                    this.fIDCChecking = true;
                }
                this.fGrammarPool = (XMLGrammarPool) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool");
            } catch (XMLConfigurationException unused19) {
                this.fGrammarPool = null;
            }
            this.fValidationState.setUnparsedEntityChecking(xMLComponentManager.getFeature(UNPARSED_ENTITY_CHECKING));
        } catch (XMLConfigurationException unused20) {
            this.fValidationState.setUnparsedEntityChecking(true);
        }
        this.fIgnoreXSITypeDepth = feature2 ? 0 : -1;
        XMLSchemaLoader.processExternalHints(this.fExternalSchemas, this.fExternalNoNamespaceSchema, this.fLocationPairs, this.fXSIErrorReporter.fErrorReporter);
        this.fState4XsiType.setSymbolTable(symbolTable);
        this.fState4ApplyDefault.setSymbolTable(symbolTable);
    }

    @Override // org.apache.xerces.xni.parser.XMLDocumentSource
    public void setDocumentHandler(XMLDocumentHandler xMLDocumentHandler) {
        this.fDocumentHandler = xMLDocumentHandler;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void setDocumentSource(XMLDocumentSource xMLDocumentSource) {
        this.fDocumentSource = xMLDocumentSource;
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void setFeature(String str, boolean z) throws XMLConfigurationException {
    }

    @Override // org.apache.xerces.xni.parser.XMLComponent
    public void setProperty(String str, Object obj) throws XMLConfigurationException {
        if (str.equals(ROOT_TYPE_DEF)) {
            if (obj == null) {
                this.fRootTypeQName = null;
            } else {
                if (!(obj instanceof QName)) {
                    this.fRootTypeDefinition = (XSTypeDefinition) obj;
                    this.fRootTypeQName = null;
                    return;
                }
                this.fRootTypeQName = (QName) obj;
            }
            this.fRootTypeDefinition = null;
            return;
        }
        if (str.equals(ROOT_ELEMENT_DECL)) {
            if (obj == null) {
                this.fRootElementDeclQName = null;
            } else {
                if (!(obj instanceof QName)) {
                    this.fRootElementDeclaration = (XSElementDecl) obj;
                    this.fRootElementDeclQName = null;
                    return;
                }
                this.fRootElementDeclQName = (QName) obj;
            }
            this.fRootElementDeclaration = null;
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startCDATA(Augmentations augmentations) throws XNIException {
        this.fInCDATA = true;
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.startCDATA(augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startDocument(XMLLocator xMLLocator, String str, NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
        this.fValidationState.setNamespaceSupport(namespaceContext);
        this.fState4XsiType.setNamespaceSupport(namespaceContext);
        this.fState4ApplyDefault.setNamespaceSupport(namespaceContext);
        this.fLocator = xMLLocator;
        handleStartDocument(xMLLocator, str);
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.startDocument(xMLLocator, str, namespaceContext, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(org.apache.xerces.xni.QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        Augmentations augmentationsHandleStartElement = handleStartElement(qName, xMLAttributes, augmentations);
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.startElement(qName, xMLAttributes, augmentationsHandleStartElement);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startGeneralEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        this.fEntityRef = true;
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.startGeneralEntity(str, xMLResourceIdentifier, str2, augmentations);
        }
    }

    @Override // org.apache.xerces.impl.xs.identity.FieldActivator
    public void startValueScopeFor(IdentityConstraint identityConstraint, int i) {
        this.fValueStoreCache.getValueStoreFor(identityConstraint, i).startValueScope();
    }

    void storeLocations(String str, String str2) {
        if (str != null) {
            Hashtable hashtable = this.fLocationPairs;
            XMLLocator xMLLocator = this.fLocator;
            if (!XMLSchemaLoader.tokenizeSchemaLocationStr(str, hashtable, xMLLocator == null ? null : xMLLocator.getExpandedSystemId())) {
                this.fXSIErrorReporter.reportError(XSMessageFormatter.SCHEMA_DOMAIN, "SchemaLocation", new Object[]{str}, (short) 0);
            }
        }
        if (str2 != null) {
            XMLSchemaLoader.LocationArray locationArray = (XMLSchemaLoader.LocationArray) this.fLocationPairs.get(XMLSymbols.EMPTY_STRING);
            if (locationArray == null) {
                locationArray = new XMLSchemaLoader.LocationArray();
                this.fLocationPairs.put(XMLSymbols.EMPTY_STRING, locationArray);
            }
            XMLLocator xMLLocator2 = this.fLocator;
            if (xMLLocator2 != null) {
                try {
                    str2 = XMLEntityManager.expandSystemId(str2, xMLLocator2.getExpandedSystemId(), false);
                } catch (URI.MalformedURIException unused) {
                }
            }
            locationArray.addLocation(str2);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void textDecl(String str, String str2, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.textDecl(str, str2, augmentations);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void xmlDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
        XMLDocumentHandler xMLDocumentHandler = this.fDocumentHandler;
        if (xMLDocumentHandler != null) {
            xMLDocumentHandler.xmlDecl(str, str2, str3, augmentations);
        }
    }
}
