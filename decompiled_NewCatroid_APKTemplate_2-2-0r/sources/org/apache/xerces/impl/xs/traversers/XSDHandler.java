package org.apache.xerces.impl.xs.traversers;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Stack;
import java.util.Vector;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.dv.SchemaDVFactory;
import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaNamespaceSupport;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XMLSchemaException;
import org.apache.xerces.impl.xs.XMLSchemaLoader;
import org.apache.xerces.impl.xs.XSAttributeDecl;
import org.apache.xerces.impl.xs.XSAttributeGroupDecl;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.XSDDescription;
import org.apache.xerces.impl.xs.XSDeclarationPool;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.XSGrammarBucket;
import org.apache.xerces.impl.xs.XSGroupDecl;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.impl.xs.XSModelGroupImpl;
import org.apache.xerces.impl.xs.XSNotationDecl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.identity.IdentityConstraint;
import org.apache.xerces.impl.xs.opti.ElementImpl;
import org.apache.xerces.impl.xs.opti.SchemaDOM;
import org.apache.xerces.impl.xs.opti.SchemaDOMParser;
import org.apache.xerces.impl.xs.opti.SchemaParsingConfig;
import org.apache.xerces.impl.xs.util.SimpleLocator;
import org.apache.xerces.impl.xs.util.XSInputSource;
import org.apache.xerces.parsers.XML11Configuration;
import org.apache.xerces.util.DOMInputSource;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.DefaultErrorHandler;
import org.apache.xerces.util.ErrorHandlerWrapper;
import org.apache.xerces.util.SAXInputSource;
import org.apache.xerces.util.StAXInputSource;
import org.apache.xerces.util.StAXLocationWrapper;
import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.URI;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.grammars.XMLSchemaDescription;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParseException;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSAttributeGroupDefinition;
import org.apache.xerces.xs.XSAttributeUse;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSModelGroup;
import org.apache.xerces.xs.XSModelGroupDefinition;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSParticle;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTerm;
import org.apache.xerces.xs.XSTypeDefinition;
import org.apache.xerces.xs.datatypes.ObjectList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/* JADX INFO: loaded from: classes13.dex */
public class XSDHandler {
    protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
    static final int ATTRIBUTEGROUP_TYPE = 2;
    static final int ATTRIBUTE_TYPE = 1;
    protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
    protected static final boolean DEBUG_NODE_POOL = false;
    protected static final String DISALLOW_DOCTYPE = "http://apache.org/xml/features/disallow-doctype-decl";
    static final int ELEMENT_TYPE = 3;
    protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
    public static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
    public static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
    static final int GROUP_TYPE = 4;
    protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
    static final int IDENTITYCONSTRAINT_TYPE = 5;
    private static final int INC_KEYREF_STACK_AMOUNT = 2;
    private static final int INC_STACK_SIZE = 10;
    private static final int INIT_KEYREF_STACK = 2;
    private static final int INIT_STACK_SIZE = 30;
    protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    protected static final String LOCALE = "http://apache.org/xml/properties/locale";
    protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
    private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
    static final int NOTATION_TYPE = 6;
    public static final String REDEF_IDENTIFIER = "_fn3dktizrknc9pi";
    protected static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
    protected static final String STANDARD_URI_CONFORMANT_FEATURE = "http://apache.org/xml/features/standard-uri-conformant";
    protected static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
    public static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
    static final int TYPEDECL_TYPE = 7;
    protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
    protected static final String VALIDATION = "http://xml.org/sax/features/validation";
    public static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
    protected static final String XMLSCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
    private int[] fAllContext;
    private Vector fAllTNSs;
    XML11Configuration fAnnotationValidator;
    private XSAttributeChecker fAttributeChecker;
    XSDAttributeGroupTraverser fAttributeGroupTraverser;
    XSDAttributeTraverser fAttributeTraverser;
    XSDComplexTypeTraverser fComplexTypeTraverser;
    SchemaDVFactory fDVFactory;
    protected XSDeclarationPool fDeclPool;
    private Hashtable fDependencyMap;
    private Hashtable fDoc2SystemId;
    private Hashtable fDoc2XSDocumentMap;
    XSDElementTraverser fElementTraverser;
    private XMLEntityResolver fEntityResolver;
    private XMLErrorReporter fErrorReporter;
    SymbolHash fGlobalAttrDecls;
    SymbolHash fGlobalAttrGrpDecls;
    SymbolHash fGlobalElemDecls;
    SymbolHash fGlobalGroupDecls;
    SymbolHash fGlobalIDConstraintDecls;
    SymbolHash fGlobalNotationDecls;
    SymbolHash fGlobalTypeDecls;
    private XSGrammarBucket fGrammarBucket;
    XSAnnotationGrammarPool fGrammarBucketAdapter;
    private XMLGrammarPool fGrammarPool;
    XSDGroupTraverser fGroupTraverser;
    Hashtable fHiddenNodes;
    private boolean fHonourAllSchemaLocations;
    private Hashtable fImportMap;
    private XSElementDecl[] fKeyrefElems;
    private String[][] fKeyrefNamespaceContext;
    private int fKeyrefStackPos;
    XSDKeyrefTraverser fKeyrefTraverser;
    private Element[] fKeyrefs;
    private XSDocumentInfo[] fKeyrefsMapXSDocumentInfo;
    private boolean fLastSchemaWasDuplicate;
    private String[][] fLocalElemNamespaceContext;
    private int fLocalElemStackPos;
    private Element[] fLocalElementDecl;
    private XSDocumentInfo[] fLocalElementDecl_schema;
    private Hashtable fLocationPairs;
    boolean fNamespaceGrowth;
    protected Hashtable fNotationRegistry;
    XSDNotationTraverser fNotationTraverser;
    private XSObject[] fParent;
    private XSParticleDecl[] fParticle;
    private Hashtable fRedefine2NSSupport;
    private Hashtable fRedefine2XSDMap;
    private Hashtable fRedefinedRestrictedAttributeGroupRegistry;
    private Hashtable fRedefinedRestrictedGroupRegistry;
    private Vector fReportedTNS;
    private XSDocumentInfo fRoot;
    private XSDDescription fSchemaGrammarDescription;
    SchemaDOMParser fSchemaParser;
    XSDSimpleTypeTraverser fSimpleTypeTraverser;
    StAXSchemaParser fStAXSchemaParser;
    private SymbolTable fSymbolTable;
    boolean fTolerateDuplicates;
    private Hashtable fTraversed;
    XSDUniqueOrKeyTraverser fUniqueOrKeyTraverser;
    private Hashtable fUnparsedAttributeGroupRegistry;
    private Hashtable fUnparsedAttributeGroupRegistrySub;
    private Hashtable fUnparsedAttributeRegistry;
    private Hashtable fUnparsedAttributeRegistrySub;
    private Hashtable fUnparsedElementRegistry;
    private Hashtable fUnparsedElementRegistrySub;
    private Hashtable fUnparsedGroupRegistry;
    private Hashtable fUnparsedGroupRegistrySub;
    private Hashtable fUnparsedIdentityConstraintRegistry;
    private Hashtable fUnparsedIdentityConstraintRegistrySub;
    private Hashtable fUnparsedNotationRegistry;
    private Hashtable fUnparsedNotationRegistrySub;
    private Hashtable[] fUnparsedRegistriesExt;
    private Hashtable fUnparsedTypeRegistry;
    private Hashtable fUnparsedTypeRegistrySub;
    private boolean fValidateAnnotations;
    XSDWildcardTraverser fWildCardTraverser;
    SchemaContentHandler fXSContentHandler;
    private Hashtable fXSDocumentInfoRegistry;
    private SimpleLocator xl;
    private static final Hashtable EMPTY_TABLE = new Hashtable();
    private static final String[][] NS_ERROR_CODES = {new String[]{"src-include.2.1", "src-include.2.1"}, new String[]{"src-redefine.3.1", "src-redefine.3.1"}, new String[]{"src-import.3.1", "src-import.3.2"}, null, new String[]{"TargetNamespace.1", "TargetNamespace.2"}, new String[]{"TargetNamespace.1", "TargetNamespace.2"}, new String[]{"TargetNamespace.1", "TargetNamespace.2"}, new String[]{"TargetNamespace.1", "TargetNamespace.2"}};
    private static final String[] ELE_ERROR_CODES = {"src-include.1", "src-redefine.2", "src-import.2", "schema_reference.4", "schema_reference.4", "schema_reference.4", "schema_reference.4", "schema_reference.4"};
    private static final String[] COMP_TYPE = {null, "attribute declaration", "attribute group", "element declaration", "group", "identity constraint", "notation", "type definition"};
    private static final String[] CIRCULAR_CODES = {"Internal-Error", "Internal-Error", "src-attribute_group.3", "e-props-correct.6", "mg-props-correct.2", "Internal-Error", "Internal-Error", "st-props-correct.2"};

    private static final class SAX2XNIUtil extends ErrorHandlerWrapper {
        private SAX2XNIUtil() {
        }

        public static XMLParseException createXMLParseException0(SAXParseException sAXParseException) {
            return createXMLParseException(sAXParseException);
        }

        public static XNIException createXNIException0(SAXException sAXException) {
            return createXNIException(sAXException);
        }
    }

    private static class XSAnnotationGrammarPool implements XMLGrammarPool {
        private XSGrammarBucket fGrammarBucket;
        private Grammar[] fInitialGrammarSet;

        private XSAnnotationGrammarPool() {
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void cacheGrammars(String str, Grammar[] grammarArr) {
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void clear() {
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void lockPool() {
        }

        public void refreshGrammars(XSGrammarBucket xSGrammarBucket) {
            this.fGrammarBucket = xSGrammarBucket;
            this.fInitialGrammarSet = null;
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public Grammar retrieveGrammar(XMLGrammarDescription xMLGrammarDescription) {
            SchemaGrammar grammar;
            if (xMLGrammarDescription.getGrammarType() != "http://www.w3.org/2001/XMLSchema") {
                return null;
            }
            String targetNamespace = ((XMLSchemaDescription) xMLGrammarDescription).getTargetNamespace();
            XSGrammarBucket xSGrammarBucket = this.fGrammarBucket;
            if (xSGrammarBucket != null && (grammar = xSGrammarBucket.getGrammar(targetNamespace)) != null) {
                return grammar;
            }
            if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(targetNamespace)) {
                return SchemaGrammar.Schema4Annotations.INSTANCE;
            }
            return null;
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public Grammar[] retrieveInitialGrammarSet(String str) {
            if (str != "http://www.w3.org/2001/XMLSchema") {
                return new Grammar[0];
            }
            if (this.fInitialGrammarSet == null) {
                XSGrammarBucket xSGrammarBucket = this.fGrammarBucket;
                if (xSGrammarBucket == null) {
                    this.fInitialGrammarSet = new Grammar[]{SchemaGrammar.Schema4Annotations.INSTANCE};
                } else {
                    SchemaGrammar[] grammars = xSGrammarBucket.getGrammars();
                    for (SchemaGrammar schemaGrammar : grammars) {
                        if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(schemaGrammar.getTargetNamespace())) {
                            this.fInitialGrammarSet = grammars;
                            return grammars;
                        }
                    }
                    int length = grammars.length;
                    Grammar[] grammarArr = new Grammar[length + 1];
                    System.arraycopy(grammars, 0, grammarArr, 0, grammars.length);
                    grammarArr[length] = SchemaGrammar.Schema4Annotations.INSTANCE;
                    this.fInitialGrammarSet = grammarArr;
                }
            }
            return this.fInitialGrammarSet;
        }

        @Override // org.apache.xerces.xni.grammars.XMLGrammarPool
        public void unlockPool() {
        }
    }

    private static class XSDKey {
        String referNS;
        short referType;
        String systemId;

        XSDKey(String str, short s, String str2) {
            this.systemId = str;
            this.referType = s;
            this.referNS = str2;
        }

        public boolean equals(Object obj) {
            String str;
            if (!(obj instanceof XSDKey)) {
                return false;
            }
            XSDKey xSDKey = (XSDKey) obj;
            return this.referNS == xSDKey.referNS && (str = this.systemId) != null && str.equals(xSDKey.systemId);
        }

        public int hashCode() {
            String str = this.referNS;
            if (str == null) {
                return 0;
            }
            return str.hashCode();
        }
    }

    public XSDHandler() {
        this.fNotationRegistry = new Hashtable();
        this.fDeclPool = null;
        this.fUnparsedAttributeRegistry = new Hashtable();
        this.fUnparsedAttributeGroupRegistry = new Hashtable();
        this.fUnparsedElementRegistry = new Hashtable();
        this.fUnparsedGroupRegistry = new Hashtable();
        this.fUnparsedIdentityConstraintRegistry = new Hashtable();
        this.fUnparsedNotationRegistry = new Hashtable();
        this.fUnparsedTypeRegistry = new Hashtable();
        this.fUnparsedAttributeRegistrySub = new Hashtable();
        this.fUnparsedAttributeGroupRegistrySub = new Hashtable();
        this.fUnparsedElementRegistrySub = new Hashtable();
        this.fUnparsedGroupRegistrySub = new Hashtable();
        this.fUnparsedIdentityConstraintRegistrySub = new Hashtable();
        this.fUnparsedNotationRegistrySub = new Hashtable();
        this.fUnparsedTypeRegistrySub = new Hashtable();
        this.fUnparsedRegistriesExt = new Hashtable[]{null, new Hashtable(), new Hashtable(), new Hashtable(), new Hashtable(), new Hashtable(), new Hashtable(), new Hashtable()};
        this.fXSDocumentInfoRegistry = new Hashtable();
        this.fDependencyMap = new Hashtable();
        this.fImportMap = new Hashtable();
        this.fAllTNSs = new Vector();
        this.fLocationPairs = null;
        this.fHiddenNodes = null;
        this.fTraversed = new Hashtable();
        this.fDoc2SystemId = new Hashtable();
        this.fRoot = null;
        this.fDoc2XSDocumentMap = new Hashtable();
        this.fRedefine2XSDMap = new Hashtable();
        this.fRedefine2NSSupport = new Hashtable();
        this.fRedefinedRestrictedAttributeGroupRegistry = new Hashtable();
        this.fRedefinedRestrictedGroupRegistry = new Hashtable();
        this.fValidateAnnotations = false;
        this.fHonourAllSchemaLocations = false;
        this.fNamespaceGrowth = false;
        this.fTolerateDuplicates = false;
        this.fLocalElemStackPos = 0;
        this.fParticle = new XSParticleDecl[30];
        this.fLocalElementDecl = new Element[30];
        this.fLocalElementDecl_schema = new XSDocumentInfo[30];
        this.fAllContext = new int[30];
        this.fParent = new XSObject[30];
        this.fLocalElemNamespaceContext = (String[][]) Array.newInstance((Class<?>) String.class, 30, 1);
        this.fKeyrefStackPos = 0;
        this.fKeyrefs = new Element[2];
        this.fKeyrefsMapXSDocumentInfo = new XSDocumentInfo[2];
        this.fKeyrefElems = new XSElementDecl[2];
        this.fKeyrefNamespaceContext = (String[][]) Array.newInstance((Class<?>) String.class, 2, 1);
        this.fGlobalAttrDecls = new SymbolHash(12);
        this.fGlobalAttrGrpDecls = new SymbolHash(5);
        this.fGlobalElemDecls = new SymbolHash(25);
        this.fGlobalGroupDecls = new SymbolHash(5);
        this.fGlobalNotationDecls = new SymbolHash(1);
        this.fGlobalIDConstraintDecls = new SymbolHash(3);
        this.fGlobalTypeDecls = new SymbolHash(25);
        this.fReportedTNS = null;
        this.xl = new SimpleLocator();
        this.fHiddenNodes = new Hashtable();
        this.fSchemaParser = new SchemaDOMParser(new SchemaParsingConfig());
    }

    public XSDHandler(XSGrammarBucket xSGrammarBucket) {
        this();
        this.fGrammarBucket = xSGrammarBucket;
        this.fSchemaGrammarDescription = new XSDDescription();
    }

    private void addGlobalAttributeDecls(SchemaGrammar schemaGrammar, SchemaGrammar schemaGrammar2) {
        XSNamedMap components = schemaGrammar.getComponents((short) 1);
        int length = components.getLength();
        for (int i = 0; i < length; i++) {
            XSAttributeDecl xSAttributeDecl = (XSAttributeDecl) components.item(i);
            XSAttributeDecl globalAttributeDecl = schemaGrammar2.getGlobalAttributeDecl(xSAttributeDecl.getName());
            if (globalAttributeDecl == null) {
                schemaGrammar2.addGlobalAttributeDecl(xSAttributeDecl);
            } else if (globalAttributeDecl != xSAttributeDecl && !this.fTolerateDuplicates) {
                reportSharingError(xSAttributeDecl.getNamespace(), xSAttributeDecl.getName());
            }
        }
        ObjectList componentsExt = schemaGrammar.getComponentsExt((short) 1);
        int length2 = componentsExt.getLength();
        for (int i2 = 0; i2 < length2; i2 += 2) {
            String str = (String) componentsExt.item(i2);
            int iIndexOf = str.indexOf(44);
            String strSubstring = str.substring(0, iIndexOf);
            String strSubstring2 = str.substring(iIndexOf + 1, str.length());
            XSAttributeDecl xSAttributeDecl2 = (XSAttributeDecl) componentsExt.item(i2 + 1);
            if (schemaGrammar2.getGlobalAttributeDecl(strSubstring2, strSubstring) == null) {
                schemaGrammar2.addGlobalAttributeDecl(xSAttributeDecl2, strSubstring);
            }
        }
    }

    private void addGlobalAttributeGroupDecls(SchemaGrammar schemaGrammar, SchemaGrammar schemaGrammar2) {
        XSNamedMap components = schemaGrammar.getComponents((short) 5);
        int length = components.getLength();
        for (int i = 0; i < length; i++) {
            XSAttributeGroupDecl xSAttributeGroupDecl = (XSAttributeGroupDecl) components.item(i);
            XSAttributeGroupDecl globalAttributeGroupDecl = schemaGrammar2.getGlobalAttributeGroupDecl(xSAttributeGroupDecl.getName());
            if (globalAttributeGroupDecl == null) {
                schemaGrammar2.addGlobalAttributeGroupDecl(xSAttributeGroupDecl);
            } else if (globalAttributeGroupDecl != xSAttributeGroupDecl && !this.fTolerateDuplicates) {
                reportSharingError(xSAttributeGroupDecl.getNamespace(), xSAttributeGroupDecl.getName());
            }
        }
        ObjectList componentsExt = schemaGrammar.getComponentsExt((short) 5);
        int length2 = componentsExt.getLength();
        for (int i2 = 0; i2 < length2; i2 += 2) {
            String str = (String) componentsExt.item(i2);
            int iIndexOf = str.indexOf(44);
            String strSubstring = str.substring(0, iIndexOf);
            String strSubstring2 = str.substring(iIndexOf + 1, str.length());
            XSAttributeGroupDecl xSAttributeGroupDecl2 = (XSAttributeGroupDecl) componentsExt.item(i2 + 1);
            if (schemaGrammar2.getGlobalAttributeGroupDecl(strSubstring2, strSubstring) == null) {
                schemaGrammar2.addGlobalAttributeGroupDecl(xSAttributeGroupDecl2, strSubstring);
            }
        }
    }

    private void addGlobalComponent(XSObject xSObject, XSDDescription xSDDescription) {
        xSDDescription.setNamespace(xSObject.getNamespace());
        SchemaGrammar schemaGrammar = getSchemaGrammar(xSDDescription);
        short type = xSObject.getType();
        String name2 = xSObject.getName();
        if (type == 1) {
            XSAttributeDecl xSAttributeDecl = (XSAttributeDecl) xSObject;
            if (xSAttributeDecl.getScope() == 1) {
                if (schemaGrammar.getGlobalAttributeDecl(name2) == null) {
                    schemaGrammar.addGlobalAttributeDecl(xSAttributeDecl);
                }
                if (schemaGrammar.getGlobalAttributeDecl(name2, "") == null) {
                    schemaGrammar.addGlobalAttributeDecl(xSAttributeDecl, "");
                    return;
                }
                return;
            }
            return;
        }
        if (type == 2) {
            XSElementDecl xSElementDecl = (XSElementDecl) xSObject;
            if (xSElementDecl.getScope() == 1) {
                schemaGrammar.addGlobalElementDeclAll(xSElementDecl);
                if (schemaGrammar.getGlobalElementDecl(name2) == null) {
                    schemaGrammar.addGlobalElementDecl(xSElementDecl);
                }
                if (schemaGrammar.getGlobalElementDecl(name2, "") == null) {
                    schemaGrammar.addGlobalElementDecl(xSElementDecl, "");
                    return;
                }
                return;
            }
            return;
        }
        if (type == 3) {
            XSTypeDefinition xSTypeDefinition = (XSTypeDefinition) xSObject;
            if (xSTypeDefinition.getAnonymous()) {
                return;
            }
            if (schemaGrammar.getGlobalTypeDecl(name2) == null) {
                schemaGrammar.addGlobalTypeDecl(xSTypeDefinition);
            }
            if (schemaGrammar.getGlobalTypeDecl(name2, "") == null) {
                schemaGrammar.addGlobalTypeDecl(xSTypeDefinition, "");
                return;
            }
            return;
        }
        if (type == 5) {
            if (schemaGrammar.getGlobalAttributeDecl(name2) == null) {
                schemaGrammar.addGlobalAttributeGroupDecl((XSAttributeGroupDecl) xSObject);
            }
            if (schemaGrammar.getGlobalAttributeDecl(name2, "") == null) {
                schemaGrammar.addGlobalAttributeGroupDecl((XSAttributeGroupDecl) xSObject, "");
                return;
            }
            return;
        }
        if (type == 6) {
            if (schemaGrammar.getGlobalGroupDecl(name2) == null) {
                schemaGrammar.addGlobalGroupDecl((XSGroupDecl) xSObject);
            }
            if (schemaGrammar.getGlobalGroupDecl(name2, "") == null) {
                schemaGrammar.addGlobalGroupDecl((XSGroupDecl) xSObject, "");
                return;
            }
            return;
        }
        if (type != 11) {
            return;
        }
        if (schemaGrammar.getGlobalNotationDecl(name2) == null) {
            schemaGrammar.addGlobalNotationDecl((XSNotationDecl) xSObject);
        }
        if (schemaGrammar.getGlobalNotationDecl(name2, "") == null) {
            schemaGrammar.addGlobalNotationDecl((XSNotationDecl) xSObject, "");
        }
    }

    private void addGlobalComponents(Vector vector, Hashtable hashtable) {
        XSDDescription xSDDescription = new XSDDescription();
        int size = vector.size();
        for (int i = 0; i < size; i++) {
            addGlobalComponent((XSObject) vector.elementAt(i), xSDDescription);
        }
        updateImportDependencies(hashtable);
    }

    private void addGlobalElementDecls(SchemaGrammar schemaGrammar, SchemaGrammar schemaGrammar2) {
        XSNamedMap components = schemaGrammar.getComponents((short) 2);
        int length = components.getLength();
        for (int i = 0; i < length; i++) {
            XSElementDecl xSElementDecl = (XSElementDecl) components.item(i);
            if (schemaGrammar2.getGlobalElementDecl(xSElementDecl.getName()) == null) {
                schemaGrammar2.addGlobalElementDecl(xSElementDecl);
            }
        }
        ObjectList componentsExt = schemaGrammar.getComponentsExt((short) 2);
        int length2 = componentsExt.getLength();
        for (int i2 = 0; i2 < length2; i2 += 2) {
            String str = (String) componentsExt.item(i2);
            int iIndexOf = str.indexOf(44);
            String strSubstring = str.substring(0, iIndexOf);
            String strSubstring2 = str.substring(iIndexOf + 1, str.length());
            XSElementDecl xSElementDecl2 = (XSElementDecl) componentsExt.item(i2 + 1);
            if (schemaGrammar2.getGlobalElementDecl(strSubstring2, strSubstring) == null) {
                schemaGrammar2.addGlobalElementDecl(xSElementDecl2, strSubstring);
            }
        }
    }

    private void addGlobalGroupDecls(SchemaGrammar schemaGrammar, SchemaGrammar schemaGrammar2) {
        XSNamedMap components = schemaGrammar.getComponents((short) 6);
        int length = components.getLength();
        for (int i = 0; i < length; i++) {
            XSGroupDecl xSGroupDecl = (XSGroupDecl) components.item(i);
            XSGroupDecl globalGroupDecl = schemaGrammar2.getGlobalGroupDecl(xSGroupDecl.getName());
            if (globalGroupDecl == null) {
                schemaGrammar2.addGlobalGroupDecl(xSGroupDecl);
            } else if (xSGroupDecl != globalGroupDecl && !this.fTolerateDuplicates) {
                reportSharingError(xSGroupDecl.getNamespace(), xSGroupDecl.getName());
            }
        }
        ObjectList componentsExt = schemaGrammar.getComponentsExt((short) 6);
        int length2 = componentsExt.getLength();
        for (int i2 = 0; i2 < length2; i2 += 2) {
            String str = (String) componentsExt.item(i2);
            int iIndexOf = str.indexOf(44);
            String strSubstring = str.substring(0, iIndexOf);
            String strSubstring2 = str.substring(iIndexOf + 1, str.length());
            XSGroupDecl xSGroupDecl2 = (XSGroupDecl) componentsExt.item(i2 + 1);
            if (schemaGrammar2.getGlobalGroupDecl(strSubstring2, strSubstring) == null) {
                schemaGrammar2.addGlobalGroupDecl(xSGroupDecl2, strSubstring);
            }
        }
    }

    private void addGlobalNotationDecls(SchemaGrammar schemaGrammar, SchemaGrammar schemaGrammar2) {
        XSNamedMap components = schemaGrammar.getComponents((short) 11);
        int length = components.getLength();
        for (int i = 0; i < length; i++) {
            XSNotationDecl xSNotationDecl = (XSNotationDecl) components.item(i);
            XSNotationDecl globalNotationDecl = schemaGrammar2.getGlobalNotationDecl(xSNotationDecl.getName());
            if (globalNotationDecl == null) {
                schemaGrammar2.addGlobalNotationDecl(xSNotationDecl);
            } else if (globalNotationDecl != xSNotationDecl && !this.fTolerateDuplicates) {
                reportSharingError(xSNotationDecl.getNamespace(), xSNotationDecl.getName());
            }
        }
        ObjectList componentsExt = schemaGrammar.getComponentsExt((short) 11);
        int length2 = componentsExt.getLength();
        for (int i2 = 0; i2 < length2; i2 += 2) {
            String str = (String) componentsExt.item(i2);
            int iIndexOf = str.indexOf(44);
            String strSubstring = str.substring(0, iIndexOf);
            String strSubstring2 = str.substring(iIndexOf + 1, str.length());
            XSNotationDecl xSNotationDecl2 = (XSNotationDecl) componentsExt.item(i2 + 1);
            if (schemaGrammar2.getGlobalNotationDecl(strSubstring2, strSubstring) == null) {
                schemaGrammar2.addGlobalNotationDecl(xSNotationDecl2, strSubstring);
            }
        }
    }

    private void addGlobalTypeDecls(SchemaGrammar schemaGrammar, SchemaGrammar schemaGrammar2) {
        XSNamedMap components = schemaGrammar.getComponents((short) 3);
        int length = components.getLength();
        for (int i = 0; i < length; i++) {
            XSTypeDefinition xSTypeDefinition = (XSTypeDefinition) components.item(i);
            XSTypeDefinition globalTypeDecl = schemaGrammar2.getGlobalTypeDecl(xSTypeDefinition.getName());
            if (globalTypeDecl == null) {
                schemaGrammar2.addGlobalTypeDecl(xSTypeDefinition);
            } else if (globalTypeDecl != xSTypeDefinition && !this.fTolerateDuplicates) {
                reportSharingError(xSTypeDefinition.getNamespace(), xSTypeDefinition.getName());
            }
        }
        ObjectList componentsExt = schemaGrammar.getComponentsExt((short) 3);
        int length2 = componentsExt.getLength();
        for (int i2 = 0; i2 < length2; i2 += 2) {
            String str = (String) componentsExt.item(i2);
            int iIndexOf = str.indexOf(44);
            String strSubstring = str.substring(0, iIndexOf);
            String strSubstring2 = str.substring(iIndexOf + 1, str.length());
            XSTypeDefinition xSTypeDefinition2 = (XSTypeDefinition) componentsExt.item(i2 + 1);
            if (schemaGrammar2.getGlobalTypeDecl(strSubstring2, strSubstring) == null) {
                schemaGrammar2.addGlobalTypeDecl(xSTypeDefinition2, strSubstring);
            }
        }
    }

    private void addGrammarComponents(SchemaGrammar schemaGrammar, SchemaGrammar schemaGrammar2) {
        if (schemaGrammar2 == null) {
            createGrammarFrom(schemaGrammar);
            return;
        }
        if (schemaGrammar2.isImmutable()) {
            schemaGrammar2 = createGrammarFrom(schemaGrammar2);
        }
        addNewGrammarLocations(schemaGrammar, schemaGrammar2);
        addNewImportedGrammars(schemaGrammar, schemaGrammar2);
        addNewGrammarComponents(schemaGrammar, schemaGrammar2);
    }

    private void addGrammars(Vector vector) {
        int size = vector.size();
        XSDDescription xSDDescription = new XSDDescription();
        for (int i = 0; i < size; i++) {
            SchemaGrammar schemaGrammar = (SchemaGrammar) vector.elementAt(i);
            xSDDescription.setNamespace(schemaGrammar.getTargetNamespace());
            SchemaGrammar schemaGrammarFindGrammar = findGrammar(xSDDescription, this.fNamespaceGrowth);
            if (schemaGrammar != schemaGrammarFindGrammar) {
                addGrammarComponents(schemaGrammar, schemaGrammarFindGrammar);
            }
        }
    }

    private void addImportList(SchemaGrammar schemaGrammar, Vector vector, Vector vector2) {
        int size = vector2.size();
        for (int i = 0; i < size; i++) {
            SchemaGrammar grammar = this.fGrammarBucket.getGrammar((String) vector2.elementAt(i));
            if (grammar != null) {
                vector.add(grammar);
            }
        }
    }

    private void addNamespaceDependency(String str, String str2, Vector vector) {
        String strNull2EmptyString = null2EmptyString(str);
        String strNull2EmptyString2 = null2EmptyString(str2);
        if (strNull2EmptyString.equals(strNull2EmptyString2) || vector.contains(strNull2EmptyString2)) {
            return;
        }
        vector.add(strNull2EmptyString2);
    }

    private void addNewGrammarComponents(SchemaGrammar schemaGrammar, SchemaGrammar schemaGrammar2) {
        schemaGrammar2.resetComponents();
        addGlobalElementDecls(schemaGrammar, schemaGrammar2);
        addGlobalAttributeDecls(schemaGrammar, schemaGrammar2);
        addGlobalAttributeGroupDecls(schemaGrammar, schemaGrammar2);
        addGlobalGroupDecls(schemaGrammar, schemaGrammar2);
        addGlobalTypeDecls(schemaGrammar, schemaGrammar2);
        addGlobalNotationDecls(schemaGrammar, schemaGrammar2);
    }

    private void addNewGrammarLocations(SchemaGrammar schemaGrammar, SchemaGrammar schemaGrammar2) {
        StringList documentLocations = schemaGrammar.getDocumentLocations();
        int size = documentLocations.size();
        StringList documentLocations2 = schemaGrammar2.getDocumentLocations();
        for (int i = 0; i < size; i++) {
            String strItem = documentLocations.item(i);
            if (!documentLocations2.contains(strItem)) {
                schemaGrammar2.addDocument(null, strItem);
            }
        }
    }

    private void addNewImportedGrammars(SchemaGrammar schemaGrammar, SchemaGrammar schemaGrammar2) {
        Vector importedGrammars = schemaGrammar.getImportedGrammars();
        if (importedGrammars != null) {
            Vector importedGrammars2 = schemaGrammar2.getImportedGrammars();
            if (importedGrammars2 == null) {
                importedGrammars2 = new Vector();
                schemaGrammar2.setImportedGrammars(importedGrammars2);
            }
            int size = importedGrammars.size();
            for (int i = 0; i < size; i++) {
                SchemaGrammar schemaGrammar3 = (SchemaGrammar) importedGrammars.elementAt(i);
                SchemaGrammar grammar = this.fGrammarBucket.getGrammar(schemaGrammar3.getTargetNamespace());
                if (grammar != null) {
                    schemaGrammar3 = grammar;
                }
                if (!containedImportedGrammar(importedGrammars2, schemaGrammar3)) {
                    importedGrammars2.add(schemaGrammar3);
                }
            }
        }
    }

    private void addRelatedAttribute(XSAttributeDeclaration xSAttributeDeclaration, Vector vector, String str, Hashtable hashtable) {
        if (xSAttributeDeclaration.getScope() != 1) {
            expandRelatedAttributeComponents(xSAttributeDeclaration, vector, str, hashtable);
        } else {
            if (vector.contains(xSAttributeDeclaration)) {
                return;
            }
            addNamespaceDependency(str, xSAttributeDeclaration.getNamespace(), findDependentNamespaces(str, hashtable));
            vector.add(xSAttributeDeclaration);
        }
    }

    private void addRelatedElement(XSElementDeclaration xSElementDeclaration, Vector vector, String str, Hashtable hashtable) {
        if (xSElementDeclaration.getScope() != 1) {
            expandRelatedElementComponents(xSElementDeclaration, vector, str, hashtable);
        } else {
            if (vector.contains(xSElementDeclaration)) {
                return;
            }
            addNamespaceDependency(str, xSElementDeclaration.getNamespace(), findDependentNamespaces(str, hashtable));
            vector.add(xSElementDeclaration);
        }
    }

    private void addRelatedType(XSTypeDefinition xSTypeDefinition, Vector vector, String str, Hashtable hashtable) {
        if (xSTypeDefinition.getAnonymous()) {
            expandRelatedTypeComponents(xSTypeDefinition, vector, str, hashtable);
        } else {
            if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(xSTypeDefinition.getNamespace()) || vector.contains(xSTypeDefinition)) {
                return;
            }
            addNamespaceDependency(str, xSTypeDefinition.getNamespace(), findDependentNamespaces(str, hashtable));
            vector.add(xSTypeDefinition);
        }
    }

    private boolean canAddComponent(XSObject xSObject, XSDDescription xSDDescription) {
        xSDDescription.setNamespace(xSObject.getNamespace());
        SchemaGrammar schemaGrammarFindGrammar = findGrammar(xSDDescription, false);
        if (schemaGrammarFindGrammar == null) {
            return true;
        }
        if (schemaGrammarFindGrammar.isImmutable()) {
            return false;
        }
        short type = xSObject.getType();
        String name2 = xSObject.getName();
        if (type != 1) {
            if (type != 2) {
                if (type != 3) {
                    if (type != 5) {
                        if (type != 6) {
                            if (type != 11 || schemaGrammarFindGrammar.getGlobalNotationDecl(name2) == xSObject) {
                                return true;
                            }
                        } else if (schemaGrammarFindGrammar.getGlobalGroupDecl(name2) == xSObject) {
                            return true;
                        }
                    } else if (schemaGrammarFindGrammar.getGlobalAttributeDecl(name2) == xSObject) {
                        return true;
                    }
                } else if (schemaGrammarFindGrammar.getGlobalTypeDecl(name2) == xSObject) {
                    return true;
                }
            } else if (schemaGrammarFindGrammar.getGlobalElementDecl(name2) == xSObject) {
                return true;
            }
        } else if (schemaGrammarFindGrammar.getGlobalAttributeDecl(name2) == xSObject) {
            return true;
        }
        return false;
    }

    private boolean canAddComponents(Vector vector) {
        int size = vector.size();
        XSDDescription xSDDescription = new XSDDescription();
        for (int i = 0; i < size; i++) {
            if (!canAddComponent((XSObject) vector.elementAt(i), xSDDescription)) {
                return false;
            }
        }
        return true;
    }

    private int changeRedefineGroup(String str, String str2, String str3, Element element, XSDocumentInfo xSDocumentInfo) {
        int iChangeRedefineGroup = 0;
        for (Element firstChildElement = DOMUtil.getFirstChildElement(element); firstChildElement != null; firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement)) {
            if (DOMUtil.getLocalName(firstChildElement).equals(str2)) {
                String attribute = firstChildElement.getAttribute(SchemaSymbols.ATT_REF);
                if (attribute.length() != 0 && str.equals(findQName(attribute, xSDocumentInfo))) {
                    String str4 = XMLSymbols.EMPTY_STRING;
                    int iIndexOf = attribute.indexOf(":");
                    if (iIndexOf > 0) {
                        firstChildElement.setAttribute(SchemaSymbols.ATT_REF, attribute.substring(0, iIndexOf) + ":" + str3);
                    } else {
                        firstChildElement.setAttribute(SchemaSymbols.ATT_REF, str3);
                    }
                    iChangeRedefineGroup++;
                    if (str2.equals(SchemaSymbols.ELT_GROUP)) {
                        String attribute2 = firstChildElement.getAttribute(SchemaSymbols.ATT_MINOCCURS);
                        String attribute3 = firstChildElement.getAttribute(SchemaSymbols.ATT_MAXOCCURS);
                        if ((attribute3.length() != 0 && !attribute3.equals(SchemaSymbols.ATTVAL_TRUE_1)) || (attribute2.length() != 0 && !attribute2.equals(SchemaSymbols.ATTVAL_TRUE_1))) {
                            reportSchemaError("src-redefine.6.1.2", new Object[]{attribute}, firstChildElement);
                        }
                    }
                }
            } else {
                iChangeRedefineGroup += changeRedefineGroup(str, str2, str3, firstChildElement, xSDocumentInfo);
            }
        }
        return iChangeRedefineGroup;
    }

    private boolean containedImportedGrammar(Vector vector, SchemaGrammar schemaGrammar) {
        int size = vector.size();
        for (int i = 0; i < size; i++) {
            if (null2EmptyString(((SchemaGrammar) vector.elementAt(i)).getTargetNamespace()).equals(null2EmptyString(schemaGrammar.getTargetNamespace()))) {
                return true;
            }
        }
        return false;
    }

    private void createAnnotationValidator() {
        this.fAnnotationValidator = new XML11Configuration();
        this.fGrammarBucketAdapter = new XSAnnotationGrammarPool();
        this.fAnnotationValidator.setFeature(VALIDATION, true);
        this.fAnnotationValidator.setFeature(XMLSCHEMA_VALIDATION, true);
        this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarBucketAdapter);
        XMLErrorHandler errorHandler = this.fErrorReporter.getErrorHandler();
        XML11Configuration xML11Configuration = this.fAnnotationValidator;
        if (errorHandler == null) {
            errorHandler = new DefaultErrorHandler();
        }
        xML11Configuration.setProperty(ERROR_HANDLER, errorHandler);
        this.fAnnotationValidator.setProperty(LOCALE, this.fErrorReporter.getLocale());
    }

    private SchemaGrammar createGrammarFrom(SchemaGrammar schemaGrammar) {
        SchemaGrammar schemaGrammar2 = new SchemaGrammar(schemaGrammar);
        this.fGrammarBucket.putGrammar(schemaGrammar2);
        updateImportListWith(schemaGrammar2);
        updateImportListFor(schemaGrammar2);
        return schemaGrammar2;
    }

    private void createTraversers() {
        this.fAttributeChecker = new XSAttributeChecker(this);
        this.fAttributeGroupTraverser = new XSDAttributeGroupTraverser(this, this.fAttributeChecker);
        this.fAttributeTraverser = new XSDAttributeTraverser(this, this.fAttributeChecker);
        this.fComplexTypeTraverser = new XSDComplexTypeTraverser(this, this.fAttributeChecker);
        this.fElementTraverser = new XSDElementTraverser(this, this.fAttributeChecker);
        this.fGroupTraverser = new XSDGroupTraverser(this, this.fAttributeChecker);
        this.fKeyrefTraverser = new XSDKeyrefTraverser(this, this.fAttributeChecker);
        this.fNotationTraverser = new XSDNotationTraverser(this, this.fAttributeChecker);
        this.fSimpleTypeTraverser = new XSDSimpleTypeTraverser(this, this.fAttributeChecker);
        this.fUniqueOrKeyTraverser = new XSDUniqueOrKeyTraverser(this, this.fAttributeChecker);
        this.fWildCardTraverser = new XSDWildcardTraverser(this, this.fAttributeChecker);
    }

    private String doc2SystemId(Element element) {
        String documentURI = element.getOwnerDocument() instanceof SchemaDOM ? ((SchemaDOM) element.getOwnerDocument()).getDocumentURI() : null;
        return documentURI != null ? documentURI : (String) this.fDoc2SystemId.get(element);
    }

    private String emptyString2Null(String str) {
        if (str == XMLSymbols.EMPTY_STRING) {
            return null;
        }
        return str;
    }

    private boolean existingGrammars(Vector vector) {
        int size = vector.size();
        XSDDescription xSDDescription = new XSDDescription();
        for (int i = 0; i < size; i++) {
            xSDDescription.setNamespace(((SchemaGrammar) vector.elementAt(i)).getTargetNamespace());
            if (findGrammar(xSDDescription, false) != null) {
                return true;
            }
        }
        return false;
    }

    private Vector expandComponents(XSObject[] xSObjectArr, Hashtable hashtable) {
        Vector vector = new Vector();
        for (int i = 0; i < xSObjectArr.length; i++) {
            if (!vector.contains(xSObjectArr[i])) {
                vector.add(xSObjectArr[i]);
            }
        }
        for (int i2 = 0; i2 < vector.size(); i2++) {
            expandRelatedComponents((XSObject) vector.elementAt(i2), vector, hashtable);
        }
        return vector;
    }

    private Vector expandGrammars(SchemaGrammar[] schemaGrammarArr) {
        Vector vector = new Vector();
        for (int i = 0; i < schemaGrammarArr.length; i++) {
            if (!vector.contains(schemaGrammarArr[i])) {
                vector.add(schemaGrammarArr[i]);
            }
        }
        for (int i2 = 0; i2 < vector.size(); i2++) {
            Vector importedGrammars = ((SchemaGrammar) vector.elementAt(i2)).getImportedGrammars();
            if (importedGrammars != null) {
                for (int size = importedGrammars.size() - 1; size >= 0; size--) {
                    SchemaGrammar schemaGrammar = (SchemaGrammar) importedGrammars.elementAt(size);
                    if (!vector.contains(schemaGrammar)) {
                        vector.addElement(schemaGrammar);
                    }
                }
            }
        }
        return vector;
    }

    private void expandImportList(String str, Vector vector) {
        SchemaGrammar grammar = this.fGrammarBucket.getGrammar(str);
        if (grammar != null) {
            Vector importedGrammars = grammar.getImportedGrammars();
            if (importedGrammars != null) {
                updateImportList(grammar, importedGrammars, vector);
                return;
            }
            Vector vector2 = new Vector();
            addImportList(grammar, vector2, vector);
            grammar.setImportedGrammars(vector2);
        }
    }

    private void expandRelatedAttributeComponents(XSAttributeDeclaration xSAttributeDeclaration, Vector vector, String str, Hashtable hashtable) {
        addRelatedType(xSAttributeDeclaration.getTypeDefinition(), vector, str, hashtable);
    }

    private void expandRelatedAttributeGroupComponents(XSAttributeGroupDefinition xSAttributeGroupDefinition, Vector vector, String str, Hashtable hashtable) {
        expandRelatedAttributeUsesComponents(xSAttributeGroupDefinition.getAttributeUses(), vector, str, hashtable);
    }

    private void expandRelatedAttributeUseComponents(XSAttributeUse xSAttributeUse, Vector vector, String str, Hashtable hashtable) {
        addRelatedAttribute(xSAttributeUse.getAttrDeclaration(), vector, str, hashtable);
    }

    private void expandRelatedAttributeUsesComponents(XSObjectList xSObjectList, Vector vector, String str, Hashtable hashtable) {
        int size = xSObjectList == null ? 0 : xSObjectList.size();
        for (int i = 0; i < size; i++) {
            expandRelatedAttributeUseComponents((XSAttributeUse) xSObjectList.item(i), vector, str, hashtable);
        }
    }

    private void expandRelatedComplexTypeComponents(XSComplexTypeDecl xSComplexTypeDecl, Vector vector, String str, Hashtable hashtable) {
        addRelatedType(xSComplexTypeDecl.getBaseType(), vector, str, hashtable);
        expandRelatedAttributeUsesComponents(xSComplexTypeDecl.getAttributeUses(), vector, str, hashtable);
        XSParticle particle = xSComplexTypeDecl.getParticle();
        if (particle != null) {
            expandRelatedParticleComponents(particle, vector, str, hashtable);
        }
    }

    private void expandRelatedComponents(XSObject xSObject, Vector vector, Hashtable hashtable) {
        short type = xSObject.getType();
        if (type == 1) {
            expandRelatedAttributeComponents((XSAttributeDeclaration) xSObject, vector, xSObject.getNamespace(), hashtable);
            return;
        }
        if (type != 2) {
            if (type == 3) {
                expandRelatedTypeComponents((XSTypeDefinition) xSObject, vector, xSObject.getNamespace(), hashtable);
                return;
            } else {
                if (type != 5) {
                    if (type != 6) {
                        return;
                    }
                    expandRelatedModelGroupDefinitionComponents((XSModelGroupDefinition) xSObject, vector, xSObject.getNamespace(), hashtable);
                    return;
                }
                expandRelatedAttributeGroupComponents((XSAttributeGroupDefinition) xSObject, vector, xSObject.getNamespace(), hashtable);
            }
        }
        expandRelatedElementComponents((XSElementDeclaration) xSObject, vector, xSObject.getNamespace(), hashtable);
    }

    private void expandRelatedElementComponents(XSElementDeclaration xSElementDeclaration, Vector vector, String str, Hashtable hashtable) {
        addRelatedType(xSElementDeclaration.getTypeDefinition(), vector, str, hashtable);
        XSElementDeclaration substitutionGroupAffiliation = xSElementDeclaration.getSubstitutionGroupAffiliation();
        if (substitutionGroupAffiliation != null) {
            addRelatedElement(substitutionGroupAffiliation, vector, str, hashtable);
        }
    }

    private void expandRelatedModelGroupComponents(XSModelGroup xSModelGroup, Vector vector, String str, Hashtable hashtable) {
        XSObjectList particles = xSModelGroup.getParticles();
        int length = particles == null ? 0 : particles.getLength();
        for (int i = 0; i < length; i++) {
            expandRelatedParticleComponents((XSParticle) particles.item(i), vector, str, hashtable);
        }
    }

    private void expandRelatedModelGroupDefinitionComponents(XSModelGroupDefinition xSModelGroupDefinition, Vector vector, String str, Hashtable hashtable) {
        expandRelatedModelGroupComponents(xSModelGroupDefinition.getModelGroup(), vector, str, hashtable);
    }

    private void expandRelatedParticleComponents(XSParticle xSParticle, Vector vector, String str, Hashtable hashtable) {
        XSTerm term = xSParticle.getTerm();
        short type = term.getType();
        if (type == 2) {
            addRelatedElement((XSElementDeclaration) term, vector, str, hashtable);
        } else {
            if (type != 7) {
                return;
            }
            expandRelatedModelGroupComponents((XSModelGroup) term, vector, str, hashtable);
        }
    }

    private void expandRelatedSimpleTypeComponents(XSSimpleTypeDefinition xSSimpleTypeDefinition, Vector vector, String str, Hashtable hashtable) {
        XSTypeDefinition baseType = xSSimpleTypeDefinition.getBaseType();
        if (baseType != null) {
            addRelatedType(baseType, vector, str, hashtable);
        }
        XSSimpleTypeDefinition itemType = xSSimpleTypeDefinition.getItemType();
        if (itemType != null) {
            addRelatedType(itemType, vector, str, hashtable);
        }
        XSSimpleTypeDefinition primitiveType = xSSimpleTypeDefinition.getPrimitiveType();
        if (primitiveType != null) {
            addRelatedType(primitiveType, vector, str, hashtable);
        }
        XSObjectList memberTypes = xSSimpleTypeDefinition.getMemberTypes();
        if (memberTypes.size() > 0) {
            for (int i = 0; i < memberTypes.size(); i++) {
                addRelatedType((XSTypeDefinition) memberTypes.item(i), vector, str, hashtable);
            }
        }
    }

    private void expandRelatedTypeComponents(XSTypeDefinition xSTypeDefinition, Vector vector, String str, Hashtable hashtable) {
        if (xSTypeDefinition instanceof XSComplexTypeDecl) {
            expandRelatedComplexTypeComponents((XSComplexTypeDecl) xSTypeDefinition, vector, str, hashtable);
        } else if (xSTypeDefinition instanceof XSSimpleTypeDecl) {
            expandRelatedSimpleTypeComponents((XSSimpleTypeDefinition) xSTypeDefinition, vector, str, hashtable);
        }
    }

    private Vector findDependentNamespaces(String str, Hashtable hashtable) {
        String strNull2EmptyString = null2EmptyString(str);
        Vector vector = (Vector) hashtable.get(strNull2EmptyString);
        if (vector != null) {
            return vector;
        }
        Vector vector2 = new Vector();
        hashtable.put(strNull2EmptyString, vector2);
        return vector2;
    }

    private String findQName(String str, XSDocumentInfo xSDocumentInfo) {
        SchemaNamespaceSupport schemaNamespaceSupport = xSDocumentInfo.fNamespaceSupport;
        int iIndexOf = str.indexOf(58);
        String strSubstring = XMLSymbols.EMPTY_STRING;
        if (iIndexOf > 0) {
            strSubstring = str.substring(0, iIndexOf);
        }
        String uri = schemaNamespaceSupport.getURI(this.fSymbolTable.addSymbol(strSubstring));
        if (iIndexOf != 0) {
            str = str.substring(iIndexOf + 1);
        }
        if (strSubstring == XMLSymbols.EMPTY_STRING && uri == null && xSDocumentInfo.fIsChameleonSchema) {
            uri = xSDocumentInfo.fTargetNamespace;
        }
        return (uri == null ? new StringBuilder(",").append(str) : new StringBuilder().append(uri).append(",").append(str)).toString();
    }

    private XSDocumentInfo findXSDocumentForDecl(XSDocumentInfo xSDocumentInfo, Element element, XSDocumentInfo xSDocumentInfo2) {
        if (xSDocumentInfo2 == null) {
            return null;
        }
        return xSDocumentInfo2;
    }

    private XSAttributeDecl getGlobalAttributeDecl(String str) {
        return (XSAttributeDecl) this.fGlobalAttrDecls.get(str);
    }

    private XSAttributeGroupDecl getGlobalAttributeGroupDecl(String str) {
        return (XSAttributeGroupDecl) this.fGlobalAttrGrpDecls.get(str);
    }

    private XSElementDecl getGlobalElementDecl(String str) {
        return (XSElementDecl) this.fGlobalElemDecls.get(str);
    }

    private XSGroupDecl getGlobalGroupDecl(String str) {
        return (XSGroupDecl) this.fGlobalGroupDecls.get(str);
    }

    private XSNotationDecl getGlobalNotationDecl(String str) {
        return (XSNotationDecl) this.fGlobalNotationDecls.get(str);
    }

    private XSTypeDefinition getGlobalTypeDecl(String str) {
        return (XSTypeDefinition) this.fGlobalTypeDecls.get(str);
    }

    private IdentityConstraint getIDConstraintDecl(String str) {
        return (IdentityConstraint) this.fGlobalIDConstraintDecls.get(str);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0021  */
    /* JADX WARN: Code duplicated, block: B:17:0x0032  */
    /* JADX WARN: Code duplicated, block: B:18:0x0034  */
    /* JADX WARN: Code duplicated, block: B:24:0x0043  */
    /* JADX WARN: Code duplicated, block: B:27:0x0047 A[Catch: IOException -> 0x005f, TryCatch #0 {IOException -> 0x005f, blocks: (B:15:0x0024, B:20:0x0037, B:22:0x003d, B:27:0x0047, B:29:0x0056, B:32:0x005a), top: B:39:0x0024 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0056 A[Catch: IOException -> 0x005f, TryCatch #0 {IOException -> 0x005f, blocks: (B:15:0x0024, B:20:0x0037, B:22:0x003d, B:27:0x0047, B:29:0x0056, B:32:0x005a), top: B:39:0x0024 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x0059  */
    /* JADX WARN: Code duplicated, block: B:36:0x0064  */
    /* JADX WARN: Code duplicated, block: B:39:0x0024 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private Element getSchemaDocument(String str, DOMInputSource dOMInputSource, boolean z, short s, Element element) {
        short nodeType;
        Element root;
        IOException iOException;
        boolean z2;
        String strExpandSystemId;
        boolean z3;
        Element element2;
        Node parentNode;
        Node node = dOMInputSource.getNode();
        XSDKey xSDKey = null;
        if (node != null) {
            nodeType = node.getNodeType();
            if (nodeType == 9) {
                root = DOMUtil.getRoot((Document) node);
            } else if (nodeType == 1) {
                root = (Element) node;
            }
            if (root != null) {
                if (s != 3) {
                    try {
                        strExpandSystemId = XMLEntityManager.expandSystemId(dOMInputSource.getSystemId(), dOMInputSource.getBaseSystemId(), false);
                        if (nodeType == 9) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if (!z3 && (parentNode = root.getParentNode()) != null) {
                            z3 = parentNode.getNodeType() == 9;
                        }
                        if (z3) {
                            xSDKey = new XSDKey(strExpandSystemId, s, str);
                            element2 = (Element) this.fTraversed.get(xSDKey);
                            if (element2 != null) {
                                this.fLastSchemaWasDuplicate = true;
                                return element2;
                            }
                        }
                    } catch (IOException e) {
                        z2 = true;
                        iOException = e;
                    }
                } else {
                    strExpandSystemId = null;
                }
                return getSchemaDocument0(xSDKey, strExpandSystemId, root);
            }
            iOException = null;
            z2 = false;
            return getSchemaDocument1(z, z2, dOMInputSource, element, iOException);
        }
        nodeType = -1;
        root = null;
        if (root != null) {
            if (s != 3) {
                strExpandSystemId = XMLEntityManager.expandSystemId(dOMInputSource.getSystemId(), dOMInputSource.getBaseSystemId(), false);
                if (nodeType == 9) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (!z3) {
                    z3 = parentNode.getNodeType() == 9;
                }
                if (z3) {
                    xSDKey = new XSDKey(strExpandSystemId, s, str);
                    element2 = (Element) this.fTraversed.get(xSDKey);
                    if (element2 != null) {
                        this.fLastSchemaWasDuplicate = true;
                        return element2;
                    }
                }
            } else {
                strExpandSystemId = null;
            }
            return getSchemaDocument0(xSDKey, strExpandSystemId, root);
        }
        iOException = null;
        z2 = false;
        return getSchemaDocument1(z, z2, dOMInputSource, element, iOException);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:22:0x004d
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1478)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    private org.w3c.dom.Element getSchemaDocument(java.lang.String r10, org.apache.xerces.util.SAXInputSource r11, boolean r12, short r13, org.w3c.dom.Element r14) {
        /*
            Method dump skipped, instruction units count: 203
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xerces.impl.xs.traversers.XSDHandler.getSchemaDocument(java.lang.String, org.apache.xerces.util.SAXInputSource, boolean, short, org.w3c.dom.Element):org.w3c.dom.Element");
    }

    private Element getSchemaDocument(String str, StAXInputSource stAXInputSource, boolean z, short s, Element element) {
        IOException e;
        String strExpandSystemId;
        XSDKey xSDKey;
        try {
            boolean zShouldConsumeRemainingContent = stAXInputSource.shouldConsumeRemainingContent();
            XMLStreamReader xMLStreamReader = stAXInputSource.getXMLStreamReader();
            XMLEventReader xMLEventReader = stAXInputSource.getXMLEventReader();
            if (s != 3) {
                boolean zIsStartDocument = false;
                strExpandSystemId = XMLEntityManager.expandSystemId(stAXInputSource.getSystemId(), stAXInputSource.getBaseSystemId(), false);
                if (zShouldConsumeRemainingContent) {
                    zIsStartDocument = zShouldConsumeRemainingContent;
                } else if (xMLStreamReader == null) {
                    zIsStartDocument = xMLEventReader.peek().isStartDocument();
                } else if (xMLStreamReader.getEventType() == 7) {
                    zIsStartDocument = true;
                }
                if (zIsStartDocument) {
                    xSDKey = new XSDKey(strExpandSystemId, s, str);
                    Element element2 = (Element) this.fTraversed.get(xSDKey);
                    if (element2 != null) {
                        this.fLastSchemaWasDuplicate = true;
                        return element2;
                    }
                } else {
                    xSDKey = null;
                }
            } else {
                strExpandSystemId = null;
                xSDKey = null;
            }
            if (this.fStAXSchemaParser == null) {
                this.fStAXSchemaParser = new StAXSchemaParser();
            }
            this.fStAXSchemaParser.reset(this.fSchemaParser, this.fSymbolTable);
            if (xMLStreamReader != null) {
                this.fStAXSchemaParser.parse(xMLStreamReader);
                if (zShouldConsumeRemainingContent) {
                    while (xMLStreamReader.hasNext()) {
                        xMLStreamReader.next();
                    }
                }
            } else {
                this.fStAXSchemaParser.parse(xMLEventReader);
                if (zShouldConsumeRemainingContent) {
                    while (xMLEventReader.hasNext()) {
                        xMLEventReader.nextEvent();
                    }
                }
            }
            Document document = this.fStAXSchemaParser.getDocument();
            return getSchemaDocument0(xSDKey, strExpandSystemId, document != null ? DOMUtil.getRoot(document) : null);
        } catch (IOException e2) {
            e = e2;
            return getSchemaDocument1(z, true, stAXInputSource, element, e);
        } catch (XMLStreamException e3) {
            Throwable nestedException = e3.getNestedException();
            if (nestedException instanceof IOException) {
                e = (IOException) nestedException;
                return getSchemaDocument1(z, true, stAXInputSource, element, e);
            }
            StAXLocationWrapper stAXLocationWrapper = new StAXLocationWrapper();
            stAXLocationWrapper.setLocation(e3.getLocation());
            throw new XMLParseException(stAXLocationWrapper, e3.getMessage(), e3);
        }
    }

    private Element getSchemaDocument(String str, XMLInputSource xMLInputSource, boolean z, short s, Element element) {
        boolean z2;
        IOException iOException;
        String strExpandSystemId;
        XSDKey xSDKey;
        if (xMLInputSource != null) {
            try {
                if (xMLInputSource.getSystemId() != null || xMLInputSource.getByteStream() != null || xMLInputSource.getCharacterStream() != null) {
                    if (s != 3) {
                        strExpandSystemId = XMLEntityManager.expandSystemId(xMLInputSource.getSystemId(), xMLInputSource.getBaseSystemId(), false);
                        xSDKey = new XSDKey(strExpandSystemId, s, str);
                        Element element2 = (Element) this.fTraversed.get(xSDKey);
                        if (element2 != null) {
                            this.fLastSchemaWasDuplicate = true;
                            return element2;
                        }
                    } else {
                        strExpandSystemId = null;
                        xSDKey = null;
                    }
                    this.fSchemaParser.parse(xMLInputSource);
                    Document document = this.fSchemaParser.getDocument();
                    return getSchemaDocument0(xSDKey, strExpandSystemId, document != null ? DOMUtil.getRoot(document) : null);
                }
                z2 = false;
                iOException = null;
            } catch (IOException e) {
                iOException = e;
                z2 = true;
            }
        } else {
            z2 = false;
            iOException = null;
        }
        return getSchemaDocument1(z, z2, xMLInputSource, element, iOException);
    }

    private Element getSchemaDocument(XSInputSource xSInputSource, XSDDescription xSDDescription) {
        String namespace;
        SchemaGrammar[] grammars = xSInputSource.getGrammars();
        short contextType = xSDDescription.getContextType();
        if (grammars == null || grammars.length <= 0) {
            XSObject[] components = xSInputSource.getComponents();
            if (components == null || components.length <= 0) {
                return null;
            }
            Hashtable hashtable = new Hashtable();
            Vector vectorExpandComponents = expandComponents(components, hashtable);
            if (!this.fNamespaceGrowth && !canAddComponents(vectorExpandComponents)) {
                return null;
            }
            addGlobalComponents(vectorExpandComponents, hashtable);
            if (contextType != 3) {
                return null;
            }
            namespace = components[0].getNamespace();
        } else {
            Vector vectorExpandGrammars = expandGrammars(grammars);
            if (!this.fNamespaceGrowth && existingGrammars(vectorExpandGrammars)) {
                return null;
            }
            addGrammars(vectorExpandGrammars);
            if (contextType != 3) {
                return null;
            }
            namespace = grammars[0].getTargetNamespace();
        }
        xSDDescription.setTargetNamespace(namespace);
        return null;
    }

    private Element getSchemaDocument0(XSDKey xSDKey, String str, Element element) {
        if (xSDKey != null) {
            this.fTraversed.put(xSDKey, element);
        }
        if (str != null) {
            this.fDoc2SystemId.put(element, str);
        }
        this.fLastSchemaWasDuplicate = false;
        return element;
    }

    private Element getSchemaDocument1(boolean z, boolean z2, XMLInputSource xMLInputSource, Element element, IOException iOException) {
        if (z) {
            if (z2) {
                reportSchemaError("schema_reference.4", new Object[]{xMLInputSource.getSystemId()}, element, iOException);
            } else {
                reportSchemaError("schema_reference.4", new Object[]{xMLInputSource == null ? "" : xMLInputSource.getSystemId()}, element, iOException);
            }
        } else if (z2) {
            reportSchemaWarning("schema_reference.4", new Object[]{xMLInputSource.getSystemId()}, element, iOException);
        }
        this.fLastSchemaWasDuplicate = false;
        return null;
    }

    private SchemaGrammar getSchemaGrammar(XSDDescription xSDDescription) {
        SchemaGrammar schemaGrammarFindGrammar = findGrammar(xSDDescription, this.fNamespaceGrowth);
        if (schemaGrammarFindGrammar != null) {
            return schemaGrammarFindGrammar.isImmutable() ? createGrammarFrom(schemaGrammarFindGrammar) : schemaGrammarFindGrammar;
        }
        SchemaGrammar schemaGrammar = new SchemaGrammar(xSDDescription.getNamespace(), xSDDescription.makeClone(), this.fSymbolTable);
        this.fGrammarBucket.putGrammar(schemaGrammar);
        return schemaGrammar;
    }

    private boolean isExistingGrammar(XSDDescription xSDDescription, boolean z) {
        SchemaGrammar grammar = this.fGrammarBucket.getGrammar(xSDDescription.getTargetNamespace());
        if (grammar == null) {
            return findGrammar(xSDDescription, z) != null;
        }
        if (grammar.isImmutable()) {
            return true;
        }
        try {
            return grammar.getDocumentLocations().contains(XMLEntityManager.expandSystemId(xSDDescription.getLiteralSystemId(), xSDDescription.getBaseSystemId(), false));
        } catch (URI.MalformedURIException unused) {
            return false;
        }
    }

    private final boolean needReportTNSError(String str) {
        Vector vector = this.fReportedTNS;
        if (vector == null) {
            this.fReportedTNS = new Vector();
        } else if (vector.contains(str)) {
            return false;
        }
        this.fReportedTNS.addElement(str);
        return true;
    }

    private boolean nonAnnotationContent(Element element) {
        for (Element firstChildElement = DOMUtil.getFirstChildElement(element); firstChildElement != null; firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement)) {
            if (!DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                return true;
            }
        }
        return false;
    }

    private String null2EmptyString(String str) {
        return str == null ? XMLSymbols.EMPTY_STRING : str;
    }

    private boolean removeParticle(XSModelGroupImpl xSModelGroupImpl, XSParticleDecl xSParticleDecl) {
        int i = 0;
        while (i < xSModelGroupImpl.fParticleCount) {
            XSParticleDecl xSParticleDecl2 = xSModelGroupImpl.fParticles[i];
            if (xSParticleDecl2 == xSParticleDecl) {
                while (i < xSModelGroupImpl.fParticleCount - 1) {
                    int i2 = i + 1;
                    xSModelGroupImpl.fParticles[i] = xSModelGroupImpl.fParticles[i2];
                    i = i2;
                }
                xSModelGroupImpl.fParticleCount--;
                return true;
            }
            if (xSParticleDecl2.fType == 3 && removeParticle((XSModelGroupImpl) xSParticleDecl2.fValue, xSParticleDecl)) {
                return true;
            }
            i++;
        }
        return false;
    }

    private void renameRedefiningComponents(XSDocumentInfo xSDocumentInfo, Element element, String str, String str2, String str3) {
        String string;
        Hashtable hashtable;
        StringBuilder sb;
        StringBuilder sb2;
        String str4;
        if (str.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
            Element firstChildElement = DOMUtil.getFirstChildElement(element);
            str4 = "src-redefine.5.a.a";
            if (firstChildElement != null) {
                if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                }
                if (firstChildElement != null) {
                    String localName = DOMUtil.getLocalName(firstChildElement);
                    if (!localName.equals(SchemaSymbols.ELT_RESTRICTION)) {
                        reportSchemaError("src-redefine.5.a.b", new Object[]{localName}, element);
                        return;
                    }
                    Object[] objArrCheckAttributes = this.fAttributeChecker.checkAttributes(firstChildElement, false, xSDocumentInfo);
                    QName qName = (QName) objArrCheckAttributes[XSAttributeChecker.ATTIDX_BASE];
                    if (qName == null || qName.uri != xSDocumentInfo.fTargetNamespace || !qName.localpart.equals(str2)) {
                        reportSchemaError("src-redefine.5.a.c", new Object[]{localName, (xSDocumentInfo.fTargetNamespace != null ? xSDocumentInfo.fTargetNamespace : "") + "," + str2}, element);
                    } else if (qName.prefix == null || qName.prefix.length() <= 0) {
                        firstChildElement.setAttribute(SchemaSymbols.ATT_BASE, str3);
                    } else {
                        firstChildElement.setAttribute(SchemaSymbols.ATT_BASE, qName.prefix + ":" + str3);
                    }
                    this.fAttributeChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    return;
                }
            }
        } else {
            if (!str.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
                if (str.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                    string = (xSDocumentInfo.fTargetNamespace == null ? new StringBuilder(",") : new StringBuilder().append(xSDocumentInfo.fTargetNamespace).append(",")).append(str2).toString();
                    int iChangeRedefineGroup = changeRedefineGroup(string, str, str3, element, xSDocumentInfo);
                    if (iChangeRedefineGroup > 1) {
                        reportSchemaError("src-redefine.7.1", new Object[]{new Integer(iChangeRedefineGroup)}, element);
                        return;
                    }
                    if (iChangeRedefineGroup == 1) {
                        return;
                    }
                    if (xSDocumentInfo.fTargetNamespace == null) {
                        hashtable = this.fRedefinedRestrictedAttributeGroupRegistry;
                        sb2 = new StringBuilder(",");
                    } else {
                        hashtable = this.fRedefinedRestrictedAttributeGroupRegistry;
                        sb = new StringBuilder();
                        sb2 = sb.append(xSDocumentInfo.fTargetNamespace).append(",");
                    }
                } else {
                    if (!str.equals(SchemaSymbols.ELT_GROUP)) {
                        reportSchemaError("Internal-Error", new Object[]{"could not handle this particular <redefine>; please submit your schemas and instance document in a bug report!"}, element);
                        return;
                    }
                    string = (xSDocumentInfo.fTargetNamespace == null ? new StringBuilder(",") : new StringBuilder().append(xSDocumentInfo.fTargetNamespace).append(",")).append(str2).toString();
                    int iChangeRedefineGroup2 = changeRedefineGroup(string, str, str3, element, xSDocumentInfo);
                    if (iChangeRedefineGroup2 > 1) {
                        reportSchemaError("src-redefine.6.1.1", new Object[]{new Integer(iChangeRedefineGroup2)}, element);
                        return;
                    }
                    if (iChangeRedefineGroup2 == 1) {
                        return;
                    }
                    if (xSDocumentInfo.fTargetNamespace == null) {
                        hashtable = this.fRedefinedRestrictedGroupRegistry;
                        sb2 = new StringBuilder(",");
                    } else {
                        hashtable = this.fRedefinedRestrictedGroupRegistry;
                        sb = new StringBuilder();
                        sb2 = sb.append(xSDocumentInfo.fTargetNamespace).append(",");
                    }
                }
                hashtable.put(string, sb2.append(str3).toString());
                return;
            }
            Element firstChildElement2 = DOMUtil.getFirstChildElement(element);
            str4 = "src-redefine.5.b.a";
            if (firstChildElement2 != null) {
                if (DOMUtil.getLocalName(firstChildElement2).equals(SchemaSymbols.ELT_ANNOTATION)) {
                    firstChildElement2 = DOMUtil.getNextSiblingElement(firstChildElement2);
                }
                if (firstChildElement2 != null) {
                    Element firstChildElement3 = DOMUtil.getFirstChildElement(firstChildElement2);
                    if (firstChildElement3 != null) {
                        if (DOMUtil.getLocalName(firstChildElement3).equals(SchemaSymbols.ELT_ANNOTATION)) {
                            firstChildElement3 = DOMUtil.getNextSiblingElement(firstChildElement3);
                        }
                        if (firstChildElement3 != null) {
                            String localName2 = DOMUtil.getLocalName(firstChildElement3);
                            if (!localName2.equals(SchemaSymbols.ELT_RESTRICTION) && !localName2.equals(SchemaSymbols.ELT_EXTENSION)) {
                                reportSchemaError("src-redefine.5.b.c", new Object[]{localName2}, firstChildElement3);
                                return;
                            }
                            QName qName2 = (QName) this.fAttributeChecker.checkAttributes(firstChildElement3, false, xSDocumentInfo)[XSAttributeChecker.ATTIDX_BASE];
                            if (qName2 == null || qName2.uri != xSDocumentInfo.fTargetNamespace || !qName2.localpart.equals(str2)) {
                                reportSchemaError("src-redefine.5.b.d", new Object[]{localName2, (xSDocumentInfo.fTargetNamespace != null ? xSDocumentInfo.fTargetNamespace : "") + "," + str2}, firstChildElement3);
                                return;
                            } else if (qName2.prefix == null || qName2.prefix.length() <= 0) {
                                firstChildElement3.setAttribute(SchemaSymbols.ATT_BASE, str3);
                                return;
                            } else {
                                firstChildElement3.setAttribute(SchemaSymbols.ATT_BASE, qName2.prefix + ":" + str3);
                                return;
                            }
                        }
                    }
                    reportSchemaError("src-redefine.5.b.b", null, firstChildElement2);
                    return;
                }
            }
        }
        reportSchemaError(str4, null, element);
    }

    private void reportSharingError(String str, String str2) {
        reportSchemaError("sch-props-correct.2", new Object[]{(str == null ? new StringBuilder(",") : new StringBuilder().append(str).append(",")).append(str2).toString()}, null);
    }

    private Element resolveSchema(XSDDescription xSDDescription, boolean z, Element element, boolean z2) {
        XMLInputSource xMLInputSourceResolveDocument;
        try {
            xMLInputSourceResolveDocument = XMLSchemaLoader.resolveDocument(xSDDescription, z2 ? this.fLocationPairs : EMPTY_TABLE, this.fEntityResolver);
        } catch (IOException unused) {
            String[] locationHints = xSDDescription.getLocationHints();
            if (z) {
                reportSchemaError("schema_reference.4", new Object[]{locationHints[0]}, element);
            } else {
                reportSchemaWarning("schema_reference.4", new Object[]{locationHints[0]}, element);
            }
            xMLInputSourceResolveDocument = null;
        }
        XMLInputSource xMLInputSource = xMLInputSourceResolveDocument;
        if (xMLInputSource instanceof DOMInputSource) {
            return getSchemaDocument(xSDDescription.getTargetNamespace(), (DOMInputSource) xMLInputSource, z, xSDDescription.getContextType(), element);
        }
        if (xMLInputSource instanceof SAXInputSource) {
            return getSchemaDocument(xSDDescription.getTargetNamespace(), (SAXInputSource) xMLInputSource, z, xSDDescription.getContextType(), element);
        }
        if (xMLInputSource instanceof StAXInputSource) {
            return getSchemaDocument(xSDDescription.getTargetNamespace(), (StAXInputSource) xMLInputSource, z, xSDDescription.getContextType(), element);
        }
        return xMLInputSource instanceof XSInputSource ? getSchemaDocument((XSInputSource) xMLInputSource, xSDDescription) : getSchemaDocument(xSDDescription.getTargetNamespace(), xMLInputSource, z, xSDDescription.getContextType(), element);
    }

    private Element resolveSchema(XMLInputSource xMLInputSource, XSDDescription xSDDescription, boolean z, Element element) {
        if (xMLInputSource instanceof DOMInputSource) {
            return getSchemaDocument(xSDDescription.getTargetNamespace(), (DOMInputSource) xMLInputSource, z, xSDDescription.getContextType(), element);
        }
        if (xMLInputSource instanceof SAXInputSource) {
            return getSchemaDocument(xSDDescription.getTargetNamespace(), (SAXInputSource) xMLInputSource, z, xSDDescription.getContextType(), element);
        }
        if (xMLInputSource instanceof StAXInputSource) {
            return getSchemaDocument(xSDDescription.getTargetNamespace(), (StAXInputSource) xMLInputSource, z, xSDDescription.getContextType(), element);
        }
        return xMLInputSource instanceof XSInputSource ? getSchemaDocument((XSInputSource) xMLInputSource, xSDDescription) : getSchemaDocument(xSDDescription.getTargetNamespace(), xMLInputSource, z, xSDDescription.getContextType(), element);
    }

    private XMLInputSource resolveSchemaSource(XSDDescription xSDDescription, boolean z, Element element, boolean z2) {
        try {
            return XMLSchemaLoader.resolveDocument(xSDDescription, z2 ? this.fLocationPairs : EMPTY_TABLE, this.fEntityResolver);
        } catch (IOException unused) {
            String[] locationHints = xSDDescription.getLocationHints();
            if (z) {
                reportSchemaError("schema_reference.4", new Object[]{locationHints[0]}, element);
            } else {
                reportSchemaWarning("schema_reference.4", new Object[]{locationHints[0]}, element);
            }
            return null;
        }
    }

    private void setSchemasVisible(XSDocumentInfo xSDocumentInfo) {
        if (DOMUtil.isHidden(xSDocumentInfo.fSchemaElement, this.fHiddenNodes)) {
            DOMUtil.setVisible(xSDocumentInfo.fSchemaElement, this.fHiddenNodes);
            Vector vector = (Vector) this.fDependencyMap.get(xSDocumentInfo);
            for (int i = 0; i < vector.size(); i++) {
                setSchemasVisible((XSDocumentInfo) vector.elementAt(i));
            }
        }
    }

    private void updateImportDependencies(Hashtable hashtable) {
        Enumeration enumerationKeys = hashtable.keys();
        while (enumerationKeys.hasMoreElements()) {
            String str = (String) enumerationKeys.nextElement();
            Vector vector = (Vector) hashtable.get(null2EmptyString(str));
            if (vector.size() > 0) {
                expandImportList(str, vector);
            }
        }
    }

    private void updateImportList(Vector vector, Vector vector2) {
        int size = vector.size();
        for (int i = 0; i < size; i++) {
            SchemaGrammar schemaGrammar = (SchemaGrammar) vector.elementAt(i);
            if (!containedImportedGrammar(vector2, schemaGrammar)) {
                vector2.add(schemaGrammar);
            }
        }
    }

    private void updateImportList(SchemaGrammar schemaGrammar, Vector vector, Vector vector2) {
        int size = vector2.size();
        for (int i = 0; i < size; i++) {
            SchemaGrammar grammar = this.fGrammarBucket.getGrammar((String) vector2.elementAt(i));
            if (grammar != null && !containedImportedGrammar(vector, grammar)) {
                vector.add(grammar);
            }
        }
    }

    private void updateImportListFor(SchemaGrammar schemaGrammar) {
        Vector importedGrammars = schemaGrammar.getImportedGrammars();
        if (importedGrammars != null) {
            for (int i = 0; i < importedGrammars.size(); i++) {
                SchemaGrammar schemaGrammar2 = (SchemaGrammar) importedGrammars.elementAt(i);
                SchemaGrammar grammar = this.fGrammarBucket.getGrammar(schemaGrammar2.getTargetNamespace());
                if (grammar != null && schemaGrammar2 != grammar) {
                    importedGrammars.set(i, grammar);
                }
            }
        }
    }

    private void updateImportListWith(SchemaGrammar schemaGrammar) {
        Vector importedGrammars;
        for (SchemaGrammar schemaGrammar2 : this.fGrammarBucket.getGrammars()) {
            if (schemaGrammar2 != schemaGrammar && (importedGrammars = schemaGrammar2.getImportedGrammars()) != null) {
                for (int i = 0; i < importedGrammars.size(); i++) {
                    SchemaGrammar schemaGrammar3 = (SchemaGrammar) importedGrammars.elementAt(i);
                    if (null2EmptyString(schemaGrammar3.getTargetNamespace()).equals(null2EmptyString(schemaGrammar.getTargetNamespace()))) {
                        if (schemaGrammar3 == schemaGrammar) {
                            break;
                        }
                        importedGrammars.set(i, schemaGrammar);
                        break;
                    }
                }
            }
        }
    }

    private void validateAnnotations(ArrayList arrayList) {
        if (this.fAnnotationValidator == null) {
            createAnnotationValidator();
        }
        int size = arrayList.size();
        XMLInputSource xMLInputSource = new XMLInputSource(null, null, null);
        this.fGrammarBucketAdapter.refreshGrammars(this.fGrammarBucket);
        for (int i = 0; i < size; i += 2) {
            xMLInputSource.setSystemId((String) arrayList.get(i));
            for (XSAnnotationInfo xSAnnotationInfo = (XSAnnotationInfo) arrayList.get(i + 1); xSAnnotationInfo != null; xSAnnotationInfo = xSAnnotationInfo.next) {
                xMLInputSource.setCharacterStream(new StringReader(xSAnnotationInfo.fAnnotation));
                try {
                    this.fAnnotationValidator.parse(xMLInputSource);
                } catch (IOException unused) {
                }
            }
        }
    }

    void addGlobalAttributeDecl(XSAttributeDecl xSAttributeDecl) {
        String namespace = xSAttributeDecl.getNamespace();
        String string = ((namespace == null || namespace.length() == 0) ? new StringBuilder(",") : new StringBuilder().append(namespace).append(",")).append(xSAttributeDecl.getName()).toString();
        if (this.fGlobalAttrDecls.get(string) == null) {
            this.fGlobalAttrDecls.put(string, xSAttributeDecl);
        }
    }

    void addGlobalAttributeGroupDecl(XSAttributeGroupDecl xSAttributeGroupDecl) {
        String namespace = xSAttributeGroupDecl.getNamespace();
        String string = ((namespace == null || namespace.length() == 0) ? new StringBuilder(",") : new StringBuilder().append(namespace).append(",")).append(xSAttributeGroupDecl.getName()).toString();
        if (this.fGlobalAttrGrpDecls.get(string) == null) {
            this.fGlobalAttrGrpDecls.put(string, xSAttributeGroupDecl);
        }
    }

    void addGlobalElementDecl(XSElementDecl xSElementDecl) {
        String namespace = xSElementDecl.getNamespace();
        String string = ((namespace == null || namespace.length() == 0) ? new StringBuilder(",") : new StringBuilder().append(namespace).append(",")).append(xSElementDecl.getName()).toString();
        if (this.fGlobalElemDecls.get(string) == null) {
            this.fGlobalElemDecls.put(string, xSElementDecl);
        }
    }

    void addGlobalGroupDecl(XSGroupDecl xSGroupDecl) {
        String namespace = xSGroupDecl.getNamespace();
        String string = ((namespace == null || namespace.length() == 0) ? new StringBuilder(",") : new StringBuilder().append(namespace).append(",")).append(xSGroupDecl.getName()).toString();
        if (this.fGlobalGroupDecls.get(string) == null) {
            this.fGlobalGroupDecls.put(string, xSGroupDecl);
        }
    }

    void addGlobalNotationDecl(XSNotationDecl xSNotationDecl) {
        String namespace = xSNotationDecl.getNamespace();
        String string = ((namespace == null || namespace.length() == 0) ? new StringBuilder(",") : new StringBuilder().append(namespace).append(",")).append(xSNotationDecl.getName()).toString();
        if (this.fGlobalNotationDecls.get(string) == null) {
            this.fGlobalNotationDecls.put(string, xSNotationDecl);
        }
    }

    void addGlobalTypeDecl(XSTypeDefinition xSTypeDefinition) {
        String namespace = xSTypeDefinition.getNamespace();
        String string = ((namespace == null || namespace.length() == 0) ? new StringBuilder(",") : new StringBuilder().append(namespace).append(",")).append(xSTypeDefinition.getName()).toString();
        if (this.fGlobalTypeDecls.get(string) == null) {
            this.fGlobalTypeDecls.put(string, xSTypeDefinition);
        }
    }

    void addIDConstraintDecl(IdentityConstraint identityConstraint) {
        String namespace = identityConstraint.getNamespace();
        String string = ((namespace == null || namespace.length() == 0) ? new StringBuilder(",") : new StringBuilder().append(namespace).append(",")).append(identityConstraint.getIdentityConstraintName()).toString();
        if (this.fGlobalIDConstraintDecls.get(string) == null) {
            this.fGlobalIDConstraintDecls.put(string, identityConstraint);
        }
    }

    protected void buildGlobalNameRegistries() {
        int i;
        Stack stack;
        Element element;
        Hashtable hashtable;
        Hashtable hashtable2;
        int i2;
        Stack stack2;
        String str;
        String str2;
        Stack stack3 = new Stack();
        stack3.push(this.fRoot);
        while (!stack3.empty()) {
            XSDocumentInfo xSDocumentInfo = (XSDocumentInfo) stack3.pop();
            Element element2 = xSDocumentInfo.fSchemaElement;
            if (!DOMUtil.isHidden(element2, this.fHiddenNodes)) {
                Element firstChildElement = DOMUtil.getFirstChildElement(element2);
                boolean z = true;
                while (true) {
                    if (firstChildElement == null) {
                        break;
                    }
                    if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                        stack = stack3;
                        element = element2;
                    } else if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_INCLUDE) || DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_IMPORT)) {
                        stack = stack3;
                        element = element2;
                        if (!z) {
                            reportSchemaError("s4s-elt-invalid-content.3", new Object[]{DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
                        }
                        DOMUtil.setHidden(firstChildElement, this.fHiddenNodes);
                    } else if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_REDEFINE)) {
                        if (!z) {
                            reportSchemaError("s4s-elt-invalid-content.3", new Object[]{DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
                        }
                        Element firstChildElement2 = DOMUtil.getFirstChildElement(firstChildElement);
                        while (firstChildElement2 != null) {
                            String attrValue = DOMUtil.getAttrValue(firstChildElement2, SchemaSymbols.ATT_NAME);
                            if (attrValue.length() == 0) {
                                stack2 = stack3;
                            } else {
                                String strTrim = XMLChar.trim((xSDocumentInfo.fTargetNamespace == null ? new StringBuilder(",") : new StringBuilder().append(xSDocumentInfo.fTargetNamespace).append(",")).append(attrValue).toString());
                                String localName = DOMUtil.getLocalName(firstChildElement2);
                                if (localName.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                                    stack2 = stack3;
                                    checkForDuplicateNames(strTrim, 2, this.fUnparsedAttributeGroupRegistry, this.fUnparsedAttributeGroupRegistrySub, firstChildElement2, xSDocumentInfo);
                                    str = DOMUtil.getAttrValue(firstChildElement2, SchemaSymbols.ATT_NAME) + REDEF_IDENTIFIER;
                                    str2 = SchemaSymbols.ELT_ATTRIBUTEGROUP;
                                } else {
                                    stack2 = stack3;
                                    if (localName.equals(SchemaSymbols.ELT_COMPLEXTYPE) || localName.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                                        checkForDuplicateNames(strTrim, 7, this.fUnparsedTypeRegistry, this.fUnparsedTypeRegistrySub, firstChildElement2, xSDocumentInfo);
                                        renameRedefiningComponents(xSDocumentInfo, firstChildElement2, localName.equals(SchemaSymbols.ELT_COMPLEXTYPE) ? SchemaSymbols.ELT_COMPLEXTYPE : SchemaSymbols.ELT_SIMPLETYPE, attrValue, DOMUtil.getAttrValue(firstChildElement2, SchemaSymbols.ATT_NAME) + REDEF_IDENTIFIER);
                                    } else if (localName.equals(SchemaSymbols.ELT_GROUP)) {
                                        checkForDuplicateNames(strTrim, 4, this.fUnparsedGroupRegistry, this.fUnparsedGroupRegistrySub, firstChildElement2, xSDocumentInfo);
                                        str = DOMUtil.getAttrValue(firstChildElement2, SchemaSymbols.ATT_NAME) + REDEF_IDENTIFIER;
                                        str2 = SchemaSymbols.ELT_GROUP;
                                    }
                                    firstChildElement2 = DOMUtil.getNextSiblingElement(firstChildElement2);
                                    stack3 = stack2;
                                    element2 = element2;
                                }
                                renameRedefiningComponents(xSDocumentInfo, firstChildElement2, str2, attrValue, str);
                            }
                            firstChildElement2 = DOMUtil.getNextSiblingElement(firstChildElement2);
                            stack3 = stack2;
                            element2 = element2;
                        }
                        stack = stack3;
                        element = element2;
                    } else {
                        stack = stack3;
                        element = element2;
                        String attrValue2 = DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                        if (attrValue2.length() != 0) {
                            String strTrim2 = XMLChar.trim((xSDocumentInfo.fTargetNamespace == null ? new StringBuilder(",") : new StringBuilder().append(xSDocumentInfo.fTargetNamespace).append(",")).append(attrValue2).toString());
                            String localName2 = DOMUtil.getLocalName(firstChildElement);
                            if (localName2.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
                                hashtable = this.fUnparsedAttributeRegistry;
                                hashtable2 = this.fUnparsedAttributeRegistrySub;
                                i2 = 1;
                            } else if (localName2.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                                hashtable = this.fUnparsedAttributeGroupRegistry;
                                hashtable2 = this.fUnparsedAttributeGroupRegistrySub;
                                i2 = 2;
                            } else if (localName2.equals(SchemaSymbols.ELT_COMPLEXTYPE) || localName2.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                                hashtable = this.fUnparsedTypeRegistry;
                                hashtable2 = this.fUnparsedTypeRegistrySub;
                                i2 = 7;
                            } else if (localName2.equals(SchemaSymbols.ELT_ELEMENT)) {
                                hashtable = this.fUnparsedElementRegistry;
                                hashtable2 = this.fUnparsedElementRegistrySub;
                                i2 = 3;
                            } else if (localName2.equals(SchemaSymbols.ELT_GROUP)) {
                                hashtable = this.fUnparsedGroupRegistry;
                                hashtable2 = this.fUnparsedGroupRegistrySub;
                                i2 = 4;
                            } else if (localName2.equals(SchemaSymbols.ELT_NOTATION)) {
                                hashtable = this.fUnparsedNotationRegistry;
                                hashtable2 = this.fUnparsedNotationRegistrySub;
                                i2 = 6;
                            }
                            checkForDuplicateNames(strTrim2, i2, hashtable, hashtable2, firstChildElement, xSDocumentInfo);
                        }
                        z = false;
                    }
                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                    stack3 = stack;
                    element2 = element;
                }
                Stack stack4 = stack3;
                DOMUtil.setHidden(element2, this.fHiddenNodes);
                Vector vector = (Vector) this.fDependencyMap.get(xSDocumentInfo);
                for (i = 0; i < vector.size(); i++) {
                    stack4.push(vector.elementAt(i));
                }
                stack3 = stack4;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x005b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:24:0x005d  */
    /* JADX WARN: Code duplicated, block: B:26:0x0065  */
    /* JADX WARN: Code duplicated, block: B:28:0x0087  */
    /* JADX WARN: Code duplicated, block: B:30:0x0090  */
    /* JADX WARN: Code duplicated, block: B:31:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:34:0x00de  */
    /* JADX WARN: Code duplicated, block: B:35:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:36:0x00ea A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:37:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:39:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:40:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:43:0x0117  */
    /* JADX WARN: Code duplicated, block: B:44:0x011f  */
    /* JADX WARN: Code duplicated, block: B:48:0x012d  */
    void checkForDuplicateNames(String str, int i, Hashtable hashtable, Hashtable hashtable2, Element element, XSDocumentInfo xSDocumentInfo) {
        XSDocumentInfo xSDocumentInfo2;
        boolean z;
        String str2;
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sbAppend;
        Object obj = hashtable.get(str);
        if (obj == null) {
            if (this.fNamespaceGrowth && !this.fTolerateDuplicates) {
                checkForDuplicateNames(str, i, element);
            }
            hashtable.put(str, element);
            hashtable2.put(str, xSDocumentInfo);
        } else {
            Element element2 = (Element) obj;
            XSDocumentInfo xSDocumentInfo3 = (XSDocumentInfo) hashtable2.get(str);
            if (element2 == element) {
                return;
            }
            Element parent = DOMUtil.getParent(element2);
            if (DOMUtil.getLocalName(parent).equals(SchemaSymbols.ELT_REDEFINE)) {
                xSDocumentInfo2 = (XSDocumentInfo) this.fRedefine2XSDMap.get(parent);
            } else {
                if (DOMUtil.getLocalName(DOMUtil.getParent(element)).equals(SchemaSymbols.ELT_REDEFINE)) {
                    z = false;
                    xSDocumentInfo2 = xSDocumentInfo3;
                } else {
                    xSDocumentInfo2 = null;
                }
                if (xSDocumentInfo2 != null) {
                    if (xSDocumentInfo3 == xSDocumentInfo) {
                        reportSchemaError("sch-props-correct.2", new Object[]{str}, element);
                        return;
                    }
                    str2 = str.substring(str.lastIndexOf(44) + 1) + REDEF_IDENTIFIER;
                    if (xSDocumentInfo2 == xSDocumentInfo) {
                        element.setAttribute(SchemaSymbols.ATT_NAME, str2);
                        if (xSDocumentInfo.fTargetNamespace == null) {
                            hashtable.put("," + str2, element);
                            sbAppend = new StringBuilder(",");
                        } else {
                            hashtable.put(xSDocumentInfo.fTargetNamespace + "," + str2, element);
                            sbAppend = new StringBuilder().append(xSDocumentInfo.fTargetNamespace).append(",");
                        }
                        hashtable2.put(sbAppend.append(str2).toString(), xSDocumentInfo);
                        if (xSDocumentInfo.fTargetNamespace == null) {
                            sb2 = new StringBuilder(",");
                        } else {
                            sb = new StringBuilder();
                            sb2 = sb.append(xSDocumentInfo.fTargetNamespace).append(",");
                        }
                        checkForDuplicateNames(sb2.append(str2).toString(), i, hashtable, hashtable2, element, xSDocumentInfo);
                    } else if (z) {
                        if (xSDocumentInfo.fTargetNamespace == null) {
                            sb2 = new StringBuilder(",");
                        } else {
                            sb = new StringBuilder();
                            sb2 = sb.append(xSDocumentInfo.fTargetNamespace).append(",");
                        }
                        checkForDuplicateNames(sb2.append(str2).toString(), i, hashtable, hashtable2, element, xSDocumentInfo);
                    } else {
                        reportSchemaError("sch-props-correct.2", new Object[]{str}, element);
                    }
                } else if (this.fTolerateDuplicates || this.fUnparsedRegistriesExt[i].get(str) == xSDocumentInfo) {
                    reportSchemaError("sch-props-correct.2", new Object[]{str}, element);
                }
            }
            z = true;
            if (xSDocumentInfo2 != null) {
                if (xSDocumentInfo3 == xSDocumentInfo) {
                    reportSchemaError("sch-props-correct.2", new Object[]{str}, element);
                    return;
                }
                str2 = str.substring(str.lastIndexOf(44) + 1) + REDEF_IDENTIFIER;
                if (xSDocumentInfo2 == xSDocumentInfo) {
                    element.setAttribute(SchemaSymbols.ATT_NAME, str2);
                    if (xSDocumentInfo.fTargetNamespace == null) {
                        hashtable.put("," + str2, element);
                        sbAppend = new StringBuilder(",");
                    } else {
                        hashtable.put(xSDocumentInfo.fTargetNamespace + "," + str2, element);
                        sbAppend = new StringBuilder().append(xSDocumentInfo.fTargetNamespace).append(",");
                    }
                    hashtable2.put(sbAppend.append(str2).toString(), xSDocumentInfo);
                    if (xSDocumentInfo.fTargetNamespace == null) {
                        sb2 = new StringBuilder(",");
                    } else {
                        sb = new StringBuilder();
                        sb2 = sb.append(xSDocumentInfo.fTargetNamespace).append(",");
                    }
                    checkForDuplicateNames(sb2.append(str2).toString(), i, hashtable, hashtable2, element, xSDocumentInfo);
                } else if (z) {
                    if (xSDocumentInfo.fTargetNamespace == null) {
                        sb2 = new StringBuilder(",");
                    } else {
                        sb = new StringBuilder();
                        sb2 = sb.append(xSDocumentInfo.fTargetNamespace).append(",");
                    }
                    checkForDuplicateNames(sb2.append(str2).toString(), i, hashtable, hashtable2, element, xSDocumentInfo);
                } else {
                    reportSchemaError("sch-props-correct.2", new Object[]{str}, element);
                }
            } else if (this.fTolerateDuplicates) {
                reportSchemaError("sch-props-correct.2", new Object[]{str}, element);
            } else {
                reportSchemaError("sch-props-correct.2", new Object[]{str}, element);
            }
        }
        if (this.fTolerateDuplicates) {
            this.fUnparsedRegistriesExt[i].put(str, xSDocumentInfo);
        }
    }

    void checkForDuplicateNames(String str, int i, Element element) {
        int iIndexOf = str.indexOf(44);
        SchemaGrammar grammar = this.fGrammarBucket.getGrammar(emptyString2Null(str.substring(0, iIndexOf)));
        if (grammar == null || getGlobalDeclFromGrammar(grammar, i, str.substring(iIndexOf + 1)) == null) {
            return;
        }
        reportSchemaError("sch-props-correct.2", new Object[]{str}, element);
    }

    /* JADX WARN: Code duplicated, block: B:101:0x0227  */
    /* JADX WARN: Code duplicated, block: B:106:0x0241  */
    /* JADX WARN: Code duplicated, block: B:107:0x0243  */
    /* JADX WARN: Code duplicated, block: B:109:0x0247  */
    /* JADX WARN: Code duplicated, block: B:112:0x0254  */
    /* JADX WARN: Code duplicated, block: B:115:0x0259  */
    /* JADX WARN: Code duplicated, block: B:116:0x025b  */
    /* JADX WARN: Code duplicated, block: B:171:0x03b6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:172:0x03b8  */
    /* JADX WARN: Code duplicated, block: B:173:0x03ba  */
    /* JADX WARN: Code duplicated, block: B:174:0x03c3  */
    /* JADX WARN: Code duplicated, block: B:180:0x03da A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:181:0x03dc  */
    /* JADX WARN: Code duplicated, block: B:183:0x03e2  */
    /* JADX WARN: Code duplicated, block: B:194:0x022b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:79:0x01a7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:80:0x01a9  */
    /* JADX WARN: Code duplicated, block: B:81:0x01ac  */
    /* JADX WARN: Code duplicated, block: B:84:0x01b8  */
    /* JADX WARN: Code duplicated, block: B:86:0x01be  */
    /* JADX WARN: Code duplicated, block: B:91:0x01c7  */
    /* JADX WARN: Code duplicated, block: B:94:0x01da  */
    /* JADX WARN: Code duplicated, block: B:96:0x01ed  */
    protected XSDocumentInfo constructTrees(Element element, String str, XSDDescription xSDDescription, boolean z) {
        SchemaGrammar grammar;
        SchemaGrammar schemaGrammar;
        String str2;
        short s;
        boolean zNonAnnotationContent;
        boolean z2;
        boolean zContains;
        boolean z3;
        boolean z4;
        String str3;
        Element element2;
        Element elementResolveSchema;
        XSDocumentInfo xSDocumentInfoConstructTrees;
        String strNull2EmptyString;
        Vector vector;
        SchemaGrammar schemaGrammarFindGrammar;
        boolean z5;
        boolean z6;
        String str4;
        if (element == null) {
            return null;
        }
        String targetNamespace = xSDDescription.getTargetNamespace();
        short contextType = xSDDescription.getContextType();
        try {
            XSDocumentInfo xSDocumentInfo = new XSDocumentInfo(element, this.fAttributeChecker, this.fSymbolTable);
            if (xSDocumentInfo.fTargetNamespace != null && xSDocumentInfo.fTargetNamespace.length() == 0) {
                reportSchemaWarning("EmptyTargetNamespace", new Object[]{str}, element);
                xSDocumentInfo.fTargetNamespace = null;
            }
            boolean z7 = true;
            if (targetNamespace != null) {
                if (contextType == 0 || contextType == 1) {
                    if (xSDocumentInfo.fTargetNamespace == null) {
                        xSDocumentInfo.fTargetNamespace = targetNamespace;
                        xSDocumentInfo.fIsChameleonSchema = true;
                    } else if (targetNamespace != xSDocumentInfo.fTargetNamespace) {
                        reportSchemaError(NS_ERROR_CODES[contextType][0], new Object[]{targetNamespace, xSDocumentInfo.fTargetNamespace}, element);
                        return null;
                    }
                } else if (contextType != 3 && targetNamespace != xSDocumentInfo.fTargetNamespace) {
                    reportSchemaError(NS_ERROR_CODES[contextType][0], new Object[]{targetNamespace, xSDocumentInfo.fTargetNamespace}, element);
                    return null;
                }
            } else if (xSDocumentInfo.fTargetNamespace != null) {
                if (contextType != 3) {
                    reportSchemaError(NS_ERROR_CODES[contextType][1], new Object[]{targetNamespace, xSDocumentInfo.fTargetNamespace}, element);
                    return null;
                }
                xSDDescription.setTargetNamespace(xSDocumentInfo.fTargetNamespace);
                targetNamespace = xSDocumentInfo.fTargetNamespace;
            }
            xSDocumentInfo.addAllowedNS(xSDocumentInfo.fTargetNamespace);
            if (z) {
                grammar = this.fGrammarBucket.getGrammar(xSDocumentInfo.fTargetNamespace);
                if (grammar.isImmutable()) {
                    SchemaGrammar schemaGrammar2 = new SchemaGrammar(grammar);
                    this.fGrammarBucket.putGrammar(schemaGrammar2);
                    updateImportListWith(schemaGrammar2);
                    grammar = schemaGrammar2;
                }
                updateImportListFor(grammar);
            } else if (contextType == 0 || contextType == 1) {
                grammar = this.fGrammarBucket.getGrammar(xSDocumentInfo.fTargetNamespace);
            } else {
                if (!this.fHonourAllSchemaLocations || contextType != 2 || (schemaGrammar = findGrammar(xSDDescription, false)) == null) {
                    schemaGrammar = new SchemaGrammar(xSDocumentInfo.fTargetNamespace, xSDDescription.makeClone(), this.fSymbolTable);
                    this.fGrammarBucket.putGrammar(schemaGrammar);
                }
                grammar = schemaGrammar;
            }
            grammar.addDocument(null, (String) this.fDoc2SystemId.get(xSDocumentInfo.fSchemaElement));
            this.fDoc2XSDocumentMap.put(element, xSDocumentInfo);
            Vector vector2 = new Vector();
            Element element3 = null;
            Element firstChildElement = DOMUtil.getFirstChildElement(element);
            while (firstChildElement != null) {
                String localName = DOMUtil.getLocalName(firstChildElement);
                if (localName.equals(SchemaSymbols.ELT_ANNOTATION)) {
                    firstChildElement = firstChildElement;
                } else {
                    if (!localName.equals(SchemaSymbols.ELT_IMPORT)) {
                        localName = localName;
                        firstChildElement = firstChildElement;
                        if (!localName.equals(SchemaSymbols.ELT_INCLUDE) && !localName.equals(SchemaSymbols.ELT_REDEFINE)) {
                            break;
                        }
                        Object[] objArrCheckAttributes = this.fAttributeChecker.checkAttributes(firstChildElement, true, xSDocumentInfo);
                        str2 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_SCHEMALOCATION];
                        if (localName.equals(SchemaSymbols.ELT_REDEFINE)) {
                            this.fRedefine2NSSupport.put(firstChildElement, new SchemaNamespaceSupport(xSDocumentInfo.fNamespaceSupport));
                        }
                        if (localName.equals(SchemaSymbols.ELT_INCLUDE)) {
                            Element firstChildElement2 = DOMUtil.getFirstChildElement(firstChildElement);
                            if (firstChildElement2 != null) {
                                String localName2 = DOMUtil.getLocalName(firstChildElement2);
                                if (localName2.equals(SchemaSymbols.ELT_ANNOTATION)) {
                                    grammar.addAnnotation(this.fElementTraverser.traverseAnnotationDecl(firstChildElement2, objArrCheckAttributes, true, xSDocumentInfo));
                                } else {
                                    reportSchemaError("s4s-elt-must-match.1", new Object[]{localName, "annotation?", localName2}, firstChildElement);
                                }
                                if (DOMUtil.getNextSiblingElement(firstChildElement2) != null) {
                                    reportSchemaError("s4s-elt-must-match.1", new Object[]{localName, "annotation?", DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(firstChildElement2))}, firstChildElement);
                                }
                            } else {
                                String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(firstChildElement);
                                if (syntheticAnnotation != null) {
                                    grammar.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(firstChildElement, syntheticAnnotation, objArrCheckAttributes, true, xSDocumentInfo));
                                }
                            }
                        } else {
                            for (Element firstChildElement3 = DOMUtil.getFirstChildElement(firstChildElement); firstChildElement3 != null; firstChildElement3 = DOMUtil.getNextSiblingElement(firstChildElement3)) {
                                if (DOMUtil.getLocalName(firstChildElement3).equals(SchemaSymbols.ELT_ANNOTATION)) {
                                    grammar.addAnnotation(this.fElementTraverser.traverseAnnotationDecl(firstChildElement3, objArrCheckAttributes, true, xSDocumentInfo));
                                    DOMUtil.setHidden(firstChildElement3, this.fHiddenNodes);
                                } else {
                                    String syntheticAnnotation2 = DOMUtil.getSyntheticAnnotation(firstChildElement);
                                    if (syntheticAnnotation2 != null) {
                                        grammar.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(firstChildElement, syntheticAnnotation2, objArrCheckAttributes, true, xSDocumentInfo));
                                    }
                                }
                            }
                        }
                        this.fAttributeChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        if (str2 == null) {
                            reportSchemaError("s4s-att-must-appear", new Object[]{"<include> or <redefine>", "schemaLocation"}, firstChildElement);
                        }
                        if (localName.equals(SchemaSymbols.ELT_REDEFINE)) {
                            zNonAnnotationContent = nonAnnotationContent(firstChildElement);
                            s = 1;
                        } else {
                            s = 0;
                            zNonAnnotationContent = false;
                        }
                        this.fSchemaGrammarDescription.reset();
                        this.fSchemaGrammarDescription.setContextType(s);
                        this.fSchemaGrammarDescription.setBaseSystemId(doc2SystemId(element));
                        this.fSchemaGrammarDescription.setLocationHints(new String[]{str2});
                        this.fSchemaGrammarDescription.setTargetNamespace(targetNamespace);
                        XMLInputSource xMLInputSourceResolveSchemaSource = resolveSchemaSource(this.fSchemaGrammarDescription, zNonAnnotationContent, firstChildElement, true);
                        if (this.fNamespaceGrowth && s == 0) {
                            try {
                                z2 = false;
                                try {
                                    zContains = grammar.getDocumentLocations().contains(XMLEntityManager.expandSystemId(xMLInputSourceResolveSchemaSource.getSystemId(), xMLInputSourceResolveSchemaSource.getBaseSystemId(), false));
                                } catch (URI.MalformedURIException unused) {
                                    zContains = z2;
                                }
                            } catch (URI.MalformedURIException unused2) {
                                z2 = false;
                            }
                        } else {
                            z2 = false;
                            zContains = z2;
                        }
                        if (zContains) {
                            z3 = true;
                            this.fLastSchemaWasDuplicate = true;
                            z4 = z2;
                            str3 = str2;
                            element2 = element3;
                        } else {
                            elementResolveSchema = resolveSchema(xMLInputSourceResolveSchemaSource, this.fSchemaGrammarDescription, zNonAnnotationContent, firstChildElement);
                            String str5 = xSDocumentInfo.fTargetNamespace;
                            z4 = z2;
                        }
                        if (this.fLastSchemaWasDuplicate) {
                            xSDocumentInfoConstructTrees = constructTrees(element2, str3, this.fSchemaGrammarDescription, z4);
                        } else if (element2 == null) {
                            xSDocumentInfoConstructTrees = null;
                        } else {
                            xSDocumentInfoConstructTrees = (XSDocumentInfo) this.fDoc2XSDocumentMap.get(element2);
                        }
                        if (localName.equals(SchemaSymbols.ELT_REDEFINE) && xSDocumentInfoConstructTrees != null) {
                            this.fRedefine2XSDMap.put(firstChildElement, xSDocumentInfoConstructTrees);
                        }
                        if (element2 != null) {
                            if (xSDocumentInfoConstructTrees != null) {
                                vector2.addElement(xSDocumentInfoConstructTrees);
                            }
                            element3 = null;
                        } else {
                            element3 = element2;
                        }
                        firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                        z7 = z3;
                    } else {
                        Object[] objArrCheckAttributes2 = this.fAttributeChecker.checkAttributes(firstChildElement, z7, xSDocumentInfo);
                        str2 = (String) objArrCheckAttributes2[XSAttributeChecker.ATTIDX_SCHEMALOCATION];
                        String strAddSymbol = (String) objArrCheckAttributes2[XSAttributeChecker.ATTIDX_NAMESPACE];
                        if (strAddSymbol != null) {
                            strAddSymbol = this.fSymbolTable.addSymbol(strAddSymbol);
                        }
                        Element firstChildElement4 = DOMUtil.getFirstChildElement(firstChildElement);
                        if (firstChildElement4 != null) {
                            String localName3 = DOMUtil.getLocalName(firstChildElement4);
                            if (localName3.equals(SchemaSymbols.ELT_ANNOTATION)) {
                                grammar.addAnnotation(this.fElementTraverser.traverseAnnotationDecl(firstChildElement4, objArrCheckAttributes2, true, xSDocumentInfo));
                            } else {
                                reportSchemaError("s4s-elt-must-match.1", new Object[]{localName, "annotation?", localName3}, firstChildElement);
                            }
                            if (DOMUtil.getNextSiblingElement(firstChildElement4) != null) {
                                reportSchemaError("s4s-elt-must-match.1", new Object[]{localName, "annotation?", DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(firstChildElement4))}, firstChildElement);
                            }
                        } else {
                            String syntheticAnnotation3 = DOMUtil.getSyntheticAnnotation(firstChildElement);
                            if (syntheticAnnotation3 != null) {
                                grammar.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(firstChildElement, syntheticAnnotation3, objArrCheckAttributes2, true, xSDocumentInfo));
                            }
                            this.fAttributeChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                            if (strAddSymbol == xSDocumentInfo.fTargetNamespace) {
                                if (strAddSymbol != null) {
                                    str4 = "src-import.1.1";
                                } else {
                                    str4 = "src-import.1.2";
                                }
                                reportSchemaError(str4, new Object[]{strAddSymbol}, firstChildElement);
                            } else {
                                if (xSDocumentInfo.isAllowedNS(strAddSymbol)) {
                                    xSDocumentInfo.addAllowedNS(strAddSymbol);
                                } else if (!this.fHonourAllSchemaLocations || this.fNamespaceGrowth) {
                                }
                                strNull2EmptyString = null2EmptyString(xSDocumentInfo.fTargetNamespace);
                                vector = (Vector) this.fImportMap.get(strNull2EmptyString);
                                if (vector == null) {
                                    this.fAllTNSs.addElement(strNull2EmptyString);
                                    vector = new Vector();
                                    this.fImportMap.put(strNull2EmptyString, vector);
                                } else {
                                    if (!vector.contains(strAddSymbol)) {
                                    }
                                    this.fSchemaGrammarDescription.reset();
                                    this.fSchemaGrammarDescription.setContextType((short) 2);
                                    this.fSchemaGrammarDescription.setBaseSystemId(doc2SystemId(element));
                                    this.fSchemaGrammarDescription.setLiteralSystemId(str2);
                                    this.fSchemaGrammarDescription.setLocationHints(new String[]{str2});
                                    this.fSchemaGrammarDescription.setTargetNamespace(strAddSymbol);
                                    schemaGrammarFindGrammar = findGrammar(this.fSchemaGrammarDescription, this.fNamespaceGrowth);
                                    if (schemaGrammarFindGrammar == null) {
                                        z5 = false;
                                    } else if (this.fNamespaceGrowth) {
                                        try {
                                            if (schemaGrammarFindGrammar.getDocumentLocations().contains(XMLEntityManager.expandSystemId(str2, this.fSchemaGrammarDescription.getBaseSystemId(), false))) {
                                                z5 = true;
                                            }
                                        } catch (URI.MalformedURIException unused3) {
                                            z5 = false;
                                        }
                                        firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                                        z7 = z3;
                                    } else {
                                        if (!this.fHonourAllSchemaLocations && !isExistingGrammar(this.fSchemaGrammarDescription, false)) {
                                            z5 = false;
                                        }
                                        firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                                        z7 = z3;
                                    }
                                    XSDDescription xSDDescription2 = this.fSchemaGrammarDescription;
                                    if (schemaGrammarFindGrammar == null) {
                                        z6 = true;
                                    } else {
                                        z6 = false;
                                    }
                                    elementResolveSchema = resolveSchema(xSDDescription2, false, firstChildElement, z6);
                                    z4 = z5;
                                    z2 = false;
                                }
                                vector.addElement(strAddSymbol);
                                this.fSchemaGrammarDescription.reset();
                                this.fSchemaGrammarDescription.setContextType((short) 2);
                                this.fSchemaGrammarDescription.setBaseSystemId(doc2SystemId(element));
                                this.fSchemaGrammarDescription.setLiteralSystemId(str2);
                                this.fSchemaGrammarDescription.setLocationHints(new String[]{str2});
                                this.fSchemaGrammarDescription.setTargetNamespace(strAddSymbol);
                                schemaGrammarFindGrammar = findGrammar(this.fSchemaGrammarDescription, this.fNamespaceGrowth);
                                if (schemaGrammarFindGrammar == null) {
                                    z5 = false;
                                } else if (this.fNamespaceGrowth) {
                                    if (schemaGrammarFindGrammar.getDocumentLocations().contains(XMLEntityManager.expandSystemId(str2, this.fSchemaGrammarDescription.getBaseSystemId(), false))) {
                                        z5 = true;
                                    }
                                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                                    z7 = z3;
                                } else if (!this.fHonourAllSchemaLocations) {
                                }
                                XSDDescription xSDDescription3 = this.fSchemaGrammarDescription;
                                if (schemaGrammarFindGrammar == null) {
                                    z6 = true;
                                } else {
                                    z6 = false;
                                }
                                elementResolveSchema = resolveSchema(xSDDescription3, false, firstChildElement, z6);
                                z4 = z5;
                                z2 = false;
                            }
                        }
                        this.fAttributeChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        if (strAddSymbol == xSDocumentInfo.fTargetNamespace) {
                            if (strAddSymbol != null) {
                                str4 = "src-import.1.1";
                            } else {
                                str4 = "src-import.1.2";
                            }
                            reportSchemaError(str4, new Object[]{strAddSymbol}, firstChildElement);
                        } else {
                            if (xSDocumentInfo.isAllowedNS(strAddSymbol)) {
                                xSDocumentInfo.addAllowedNS(strAddSymbol);
                            } else if (!this.fHonourAllSchemaLocations) {
                            }
                            strNull2EmptyString = null2EmptyString(xSDocumentInfo.fTargetNamespace);
                            vector = (Vector) this.fImportMap.get(strNull2EmptyString);
                            if (vector == null) {
                                this.fAllTNSs.addElement(strNull2EmptyString);
                                vector = new Vector();
                                this.fImportMap.put(strNull2EmptyString, vector);
                            } else {
                                if (!vector.contains(strAddSymbol)) {
                                }
                                this.fSchemaGrammarDescription.reset();
                                this.fSchemaGrammarDescription.setContextType((short) 2);
                                this.fSchemaGrammarDescription.setBaseSystemId(doc2SystemId(element));
                                this.fSchemaGrammarDescription.setLiteralSystemId(str2);
                                this.fSchemaGrammarDescription.setLocationHints(new String[]{str2});
                                this.fSchemaGrammarDescription.setTargetNamespace(strAddSymbol);
                                schemaGrammarFindGrammar = findGrammar(this.fSchemaGrammarDescription, this.fNamespaceGrowth);
                                if (schemaGrammarFindGrammar == null) {
                                    z5 = false;
                                } else if (this.fNamespaceGrowth) {
                                    if (schemaGrammarFindGrammar.getDocumentLocations().contains(XMLEntityManager.expandSystemId(str2, this.fSchemaGrammarDescription.getBaseSystemId(), false))) {
                                        z5 = true;
                                    }
                                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                                    z7 = z3;
                                } else if (!this.fHonourAllSchemaLocations) {
                                }
                                XSDDescription xSDDescription4 = this.fSchemaGrammarDescription;
                                if (schemaGrammarFindGrammar == null) {
                                    z6 = true;
                                } else {
                                    z6 = false;
                                }
                                elementResolveSchema = resolveSchema(xSDDescription4, false, firstChildElement, z6);
                                z4 = z5;
                                z2 = false;
                            }
                            vector.addElement(strAddSymbol);
                            this.fSchemaGrammarDescription.reset();
                            this.fSchemaGrammarDescription.setContextType((short) 2);
                            this.fSchemaGrammarDescription.setBaseSystemId(doc2SystemId(element));
                            this.fSchemaGrammarDescription.setLiteralSystemId(str2);
                            this.fSchemaGrammarDescription.setLocationHints(new String[]{str2});
                            this.fSchemaGrammarDescription.setTargetNamespace(strAddSymbol);
                            schemaGrammarFindGrammar = findGrammar(this.fSchemaGrammarDescription, this.fNamespaceGrowth);
                            if (schemaGrammarFindGrammar == null) {
                                z5 = false;
                            } else if (this.fNamespaceGrowth) {
                                if (schemaGrammarFindGrammar.getDocumentLocations().contains(XMLEntityManager.expandSystemId(str2, this.fSchemaGrammarDescription.getBaseSystemId(), false))) {
                                    z5 = true;
                                }
                                firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                                z7 = z3;
                            } else if (!this.fHonourAllSchemaLocations) {
                            }
                            XSDDescription xSDDescription5 = this.fSchemaGrammarDescription;
                            if (schemaGrammarFindGrammar == null) {
                                z6 = true;
                            } else {
                                z6 = false;
                            }
                            elementResolveSchema = resolveSchema(xSDDescription5, false, firstChildElement, z6);
                            z4 = z5;
                            z2 = false;
                        }
                    }
                    str3 = str2;
                    element2 = elementResolveSchema;
                    z3 = true;
                    if (this.fLastSchemaWasDuplicate) {
                        xSDocumentInfoConstructTrees = constructTrees(element2, str3, this.fSchemaGrammarDescription, z4);
                    } else if (element2 == null) {
                        xSDocumentInfoConstructTrees = null;
                    } else {
                        xSDocumentInfoConstructTrees = (XSDocumentInfo) this.fDoc2XSDocumentMap.get(element2);
                    }
                    if (localName.equals(SchemaSymbols.ELT_REDEFINE)) {
                        this.fRedefine2XSDMap.put(firstChildElement, xSDocumentInfoConstructTrees);
                    }
                    if (element2 != null) {
                        if (xSDocumentInfoConstructTrees != null) {
                            vector2.addElement(xSDocumentInfoConstructTrees);
                        }
                        element3 = null;
                    } else {
                        element3 = element2;
                    }
                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                    z7 = z3;
                }
                z3 = true;
                z2 = false;
                firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                z7 = z3;
            }
            this.fDependencyMap.put(xSDocumentInfo, vector2);
            return xSDocumentInfo;
        } catch (XMLSchemaException unused4) {
            reportSchemaError(ELE_ERROR_CODES[contextType], new Object[]{str}, element);
            return null;
        }
    }

    public SimpleLocator element2Locator(Element element) {
        if (!(element instanceof ElementImpl)) {
            return null;
        }
        SimpleLocator simpleLocator = new SimpleLocator();
        if (element2Locator(element, simpleLocator)) {
            return simpleLocator;
        }
        return null;
    }

    public boolean element2Locator(Element element, SimpleLocator simpleLocator) {
        if (simpleLocator == null || !(element instanceof ElementImpl)) {
            return false;
        }
        ElementImpl elementImpl = (ElementImpl) element;
        String str = (String) this.fDoc2SystemId.get(DOMUtil.getRoot(elementImpl.getOwnerDocument()));
        simpleLocator.setValues(str, str, elementImpl.getLineNumber(), elementImpl.getColumnNumber(), elementImpl.getCharacterOffset());
        return true;
    }

    void fillInLocalElemInfo(Element element, XSDocumentInfo xSDocumentInfo, int i, XSObject xSObject, XSParticleDecl xSParticleDecl) {
        XSParticleDecl[] xSParticleDeclArr = this.fParticle;
        int length = xSParticleDeclArr.length;
        int i2 = this.fLocalElemStackPos;
        if (length == i2) {
            XSParticleDecl[] xSParticleDeclArr2 = new XSParticleDecl[i2 + 10];
            System.arraycopy(xSParticleDeclArr, 0, xSParticleDeclArr2, 0, i2);
            this.fParticle = xSParticleDeclArr2;
            int i3 = this.fLocalElemStackPos;
            Element[] elementArr = new Element[i3 + 10];
            System.arraycopy(this.fLocalElementDecl, 0, elementArr, 0, i3);
            this.fLocalElementDecl = elementArr;
            int i4 = this.fLocalElemStackPos;
            XSDocumentInfo[] xSDocumentInfoArr = new XSDocumentInfo[i4 + 10];
            System.arraycopy(this.fLocalElementDecl_schema, 0, xSDocumentInfoArr, 0, i4);
            this.fLocalElementDecl_schema = xSDocumentInfoArr;
            int i5 = this.fLocalElemStackPos;
            int[] iArr = new int[i5 + 10];
            System.arraycopy(this.fAllContext, 0, iArr, 0, i5);
            this.fAllContext = iArr;
            int i6 = this.fLocalElemStackPos;
            XSObject[] xSObjectArr = new XSObject[i6 + 10];
            System.arraycopy(this.fParent, 0, xSObjectArr, 0, i6);
            this.fParent = xSObjectArr;
            int i7 = this.fLocalElemStackPos;
            String[][] strArr = new String[i7 + 10][];
            System.arraycopy(this.fLocalElemNamespaceContext, 0, strArr, 0, i7);
            this.fLocalElemNamespaceContext = strArr;
        }
        XSParticleDecl[] xSParticleDeclArr3 = this.fParticle;
        int i8 = this.fLocalElemStackPos;
        xSParticleDeclArr3[i8] = xSParticleDecl;
        this.fLocalElementDecl[i8] = element;
        this.fLocalElementDecl_schema[i8] = xSDocumentInfo;
        this.fAllContext[i8] = i;
        this.fParent[i8] = xSObject;
        String[][] strArr2 = this.fLocalElemNamespaceContext;
        this.fLocalElemStackPos = i8 + 1;
        strArr2[i8] = xSDocumentInfo.fNamespaceSupport.getEffectiveLocalContext();
    }

    protected SchemaGrammar findGrammar(XSDDescription xSDDescription, boolean z) {
        XMLGrammarPool xMLGrammarPool;
        SchemaGrammar grammar = this.fGrammarBucket.getGrammar(xSDDescription.getTargetNamespace());
        if (grammar != null || (xMLGrammarPool = this.fGrammarPool) == null) {
            return grammar;
        }
        SchemaGrammar schemaGrammar = (SchemaGrammar) xMLGrammarPool.retrieveGrammar(xSDDescription);
        if (schemaGrammar == null || this.fGrammarBucket.putGrammar(schemaGrammar, true, z)) {
            return schemaGrammar;
        }
        reportSchemaWarning("GrammarConflict", null, null);
        return null;
    }

    protected Object getGlobalDecl(String str, int i) {
        switch (i) {
            case 1:
                return getGlobalAttributeDecl(str);
            case 2:
                return getGlobalAttributeGroupDecl(str);
            case 3:
                return getGlobalElementDecl(str);
            case 4:
                return getGlobalGroupDecl(str);
            case 5:
                return getIDConstraintDecl(str);
            case 6:
                return getGlobalNotationDecl(str);
            case 7:
                return getGlobalTypeDecl(str);
            default:
                return null;
        }
    }

    protected Object getGlobalDecl(XSDocumentInfo xSDocumentInfo, int i, QName qName, Element element) {
        Hashtable hashtable;
        XSDocumentInfo xSDocumentInfo2;
        XSTypeDefinition globalTypeDecl;
        if (qName.uri != null && qName.uri == SchemaSymbols.URI_SCHEMAFORSCHEMA && i == 7 && (globalTypeDecl = SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(qName.localpart)) != null) {
            return globalTypeDecl;
        }
        if (!xSDocumentInfo.isAllowedNS(qName.uri) && xSDocumentInfo.needReportTNSError(qName.uri)) {
            reportSchemaError(qName.uri == null ? "src-resolve.4.1" : "src-resolve.4.2", new Object[]{this.fDoc2SystemId.get(xSDocumentInfo.fSchemaElement), qName.uri, qName.rawname}, element);
        }
        SchemaGrammar grammar = this.fGrammarBucket.getGrammar(qName.uri);
        Element element2 = null;
        if (grammar == null) {
            if (needReportTNSError(qName.uri)) {
                reportSchemaError("src-resolve", new Object[]{qName.rawname, COMP_TYPE[i]}, element);
            }
            return null;
        }
        Object globalDeclFromGrammar = getGlobalDeclFromGrammar(grammar, i, qName.localpart);
        String string = (qName.uri == null ? new StringBuilder(",") : new StringBuilder().append(qName.uri).append(",")).append(qName.localpart).toString();
        if (this.fTolerateDuplicates) {
            Object globalDecl = getGlobalDecl(string, i);
            if (globalDecl != null) {
                return globalDecl;
            }
        } else if (globalDeclFromGrammar != null) {
            return globalDeclFromGrammar;
        }
        switch (i) {
            case 1:
                element2 = (Element) this.fUnparsedAttributeRegistry.get(string);
                hashtable = this.fUnparsedAttributeRegistrySub;
                xSDocumentInfo2 = (XSDocumentInfo) hashtable.get(string);
                break;
            case 2:
                element2 = (Element) this.fUnparsedAttributeGroupRegistry.get(string);
                hashtable = this.fUnparsedAttributeGroupRegistrySub;
                xSDocumentInfo2 = (XSDocumentInfo) hashtable.get(string);
                break;
            case 3:
                element2 = (Element) this.fUnparsedElementRegistry.get(string);
                hashtable = this.fUnparsedElementRegistrySub;
                xSDocumentInfo2 = (XSDocumentInfo) hashtable.get(string);
                break;
            case 4:
                element2 = (Element) this.fUnparsedGroupRegistry.get(string);
                hashtable = this.fUnparsedGroupRegistrySub;
                xSDocumentInfo2 = (XSDocumentInfo) hashtable.get(string);
                break;
            case 5:
                element2 = (Element) this.fUnparsedIdentityConstraintRegistry.get(string);
                hashtable = this.fUnparsedIdentityConstraintRegistrySub;
                xSDocumentInfo2 = (XSDocumentInfo) hashtable.get(string);
                break;
            case 6:
                element2 = (Element) this.fUnparsedNotationRegistry.get(string);
                hashtable = this.fUnparsedNotationRegistrySub;
                xSDocumentInfo2 = (XSDocumentInfo) hashtable.get(string);
                break;
            case 7:
                element2 = (Element) this.fUnparsedTypeRegistry.get(string);
                hashtable = this.fUnparsedTypeRegistrySub;
                xSDocumentInfo2 = (XSDocumentInfo) hashtable.get(string);
                break;
            default:
                reportSchemaError("Internal-Error", new Object[]{"XSDHandler asked to locate component of type " + i + "; it does not recognize this type!"}, element);
                xSDocumentInfo2 = null;
                break;
        }
        if (element2 == null) {
            if (globalDeclFromGrammar == null) {
                reportSchemaError("src-resolve", new Object[]{qName.rawname, COMP_TYPE[i]}, element);
            }
            return globalDeclFromGrammar;
        }
        XSDocumentInfo xSDocumentInfoFindXSDocumentForDecl = findXSDocumentForDecl(xSDocumentInfo, element2, xSDocumentInfo2);
        if (xSDocumentInfoFindXSDocumentForDecl == null) {
            if (globalDeclFromGrammar == null) {
                reportSchemaError(qName.uri != null ? "src-resolve.4.2" : "src-resolve.4.1", new Object[]{this.fDoc2SystemId.get(xSDocumentInfo.fSchemaElement), qName.uri, qName.rawname}, element);
            }
            return globalDeclFromGrammar;
        }
        if (!DOMUtil.isHidden(element2, this.fHiddenNodes)) {
            return traverseGlobalDecl(i, element2, xSDocumentInfoFindXSDocumentForDecl, grammar);
        }
        if (globalDeclFromGrammar == null) {
            String str = CIRCULAR_CODES[i];
            if (i == 7 && SchemaSymbols.ELT_COMPLEXTYPE.equals(DOMUtil.getLocalName(element2))) {
                str = "ct-props-correct.3";
            }
            reportSchemaError(str, new Object[]{qName.prefix + ":" + qName.localpart}, element);
        }
        return globalDeclFromGrammar;
    }

    protected Object getGlobalDeclFromGrammar(SchemaGrammar schemaGrammar, int i, String str) {
        switch (i) {
            case 1:
                return schemaGrammar.getGlobalAttributeDecl(str);
            case 2:
                return schemaGrammar.getGlobalAttributeGroupDecl(str);
            case 3:
                return schemaGrammar.getGlobalElementDecl(str);
            case 4:
                return schemaGrammar.getGlobalGroupDecl(str);
            case 5:
                return schemaGrammar.getIDConstraintDecl(str);
            case 6:
                return schemaGrammar.getGlobalNotationDecl(str);
            case 7:
                return schemaGrammar.getGlobalTypeDecl(str);
            default:
                return null;
        }
    }

    protected Object getGlobalDeclFromGrammar(SchemaGrammar schemaGrammar, int i, String str, String str2) {
        switch (i) {
            case 1:
                return schemaGrammar.getGlobalAttributeDecl(str, str2);
            case 2:
                return schemaGrammar.getGlobalAttributeGroupDecl(str, str2);
            case 3:
                return schemaGrammar.getGlobalElementDecl(str, str2);
            case 4:
                return schemaGrammar.getGlobalGroupDecl(str, str2);
            case 5:
                return schemaGrammar.getIDConstraintDecl(str, str2);
            case 6:
                return schemaGrammar.getGlobalNotationDecl(str, str2);
            case 7:
                return schemaGrammar.getGlobalTypeDecl(str, str2);
            default:
                return null;
        }
    }

    SchemaGrammar getGrammar(String str) {
        return this.fGrammarBucket.getGrammar(str);
    }

    Object getGrpOrAttrGrpRedefinedByRestriction(int i, QName qName, XSDocumentInfo xSDocumentInfo, Element element) {
        Hashtable hashtable;
        String string = (qName.uri != null ? new StringBuilder().append(qName.uri).append(",") : new StringBuilder(",")).append(qName.localpart).toString();
        if (i == 2) {
            hashtable = this.fRedefinedRestrictedAttributeGroupRegistry;
        } else {
            if (i != 4) {
                return null;
            }
            hashtable = this.fRedefinedRestrictedGroupRegistry;
        }
        String str = (String) hashtable.get(string);
        if (str == null) {
            return null;
        }
        int iIndexOf = str.indexOf(",");
        Object globalDecl = getGlobalDecl(xSDocumentInfo, i, new QName(XMLSymbols.EMPTY_STRING, str.substring(iIndexOf + 1), str.substring(iIndexOf), iIndexOf == 0 ? null : str.substring(0, iIndexOf)), element);
        if (globalDecl != null) {
            return globalDecl;
        }
        if (i == 2) {
            reportSchemaError("src-redefine.7.2.1", new Object[]{qName.localpart}, element);
        } else if (i == 4) {
            reportSchemaError("src-redefine.6.2.1", new Object[]{qName.localpart}, element);
        }
        return null;
    }

    protected Hashtable getIDRegistry() {
        return this.fUnparsedIdentityConstraintRegistry;
    }

    protected Hashtable getIDRegistry_sub() {
        return this.fUnparsedIdentityConstraintRegistrySub;
    }

    public SchemaGrammar parseSchema(XMLInputSource xMLInputSource, XSDDescription xSDDescription, Hashtable hashtable) throws IOException {
        String targetNamespace;
        SchemaGrammar schemaGrammarFindGrammar;
        Element schemaDocument;
        String strAddSymbol;
        this.fLocationPairs = hashtable;
        this.fSchemaParser.resetNodePool();
        short contextType = xSDDescription.getContextType();
        if (contextType != 3) {
            SchemaGrammar grammar = (this.fHonourAllSchemaLocations && contextType == 2 && isExistingGrammar(xSDDescription, this.fNamespaceGrowth)) ? this.fGrammarBucket.getGrammar(xSDDescription.getTargetNamespace()) : findGrammar(xSDDescription, this.fNamespaceGrowth);
            if (grammar != null) {
                if (!this.fNamespaceGrowth) {
                    return grammar;
                }
                try {
                    if (grammar.getDocumentLocations().contains(XMLEntityManager.expandSystemId(xMLInputSource.getSystemId(), xMLInputSource.getBaseSystemId(), false))) {
                        return grammar;
                    }
                } catch (URI.MalformedURIException unused) {
                }
            }
            targetNamespace = xSDDescription.getTargetNamespace();
            if (targetNamespace != null) {
                targetNamespace = this.fSymbolTable.addSymbol(targetNamespace);
            }
            schemaGrammarFindGrammar = grammar;
        } else {
            targetNamespace = null;
            schemaGrammarFindGrammar = null;
        }
        prepareForParse();
        if (xMLInputSource instanceof DOMInputSource) {
            schemaDocument = getSchemaDocument(targetNamespace, (DOMInputSource) xMLInputSource, contextType == 3, contextType, (Element) null);
        } else if (xMLInputSource instanceof SAXInputSource) {
            schemaDocument = getSchemaDocument(targetNamespace, (SAXInputSource) xMLInputSource, contextType == 3, contextType, (Element) null);
        } else if (xMLInputSource instanceof StAXInputSource) {
            schemaDocument = getSchemaDocument(targetNamespace, (StAXInputSource) xMLInputSource, contextType == 3, contextType, (Element) null);
        } else if (xMLInputSource instanceof XSInputSource) {
            schemaDocument = getSchemaDocument((XSInputSource) xMLInputSource, xSDDescription);
        } else {
            schemaDocument = getSchemaDocument(targetNamespace, xMLInputSource, contextType == 3, contextType, (Element) null);
        }
        if (schemaDocument == null) {
            if (!(xMLInputSource instanceof XSInputSource)) {
                return schemaGrammarFindGrammar;
            }
            XSInputSource xSInputSource = (XSInputSource) xMLInputSource;
            SchemaGrammar[] grammars = xSInputSource.getGrammars();
            if (grammars != null && grammars.length > 0) {
                return this.fGrammarBucket.getGrammar(grammars[0].getTargetNamespace());
            }
            XSObject[] components = xSInputSource.getComponents();
            return (components == null || components.length <= 0) ? schemaGrammarFindGrammar : this.fGrammarBucket.getGrammar(components[0].getNamespace());
        }
        if (contextType == 3) {
            String attrValue = DOMUtil.getAttrValue(schemaDocument, SchemaSymbols.ATT_TARGETNAMESPACE);
            if (attrValue == null || attrValue.length() <= 0) {
                strAddSymbol = null;
            } else {
                strAddSymbol = this.fSymbolTable.addSymbol(attrValue);
                xSDDescription.setTargetNamespace(strAddSymbol);
            }
            schemaGrammarFindGrammar = findGrammar(xSDDescription, this.fNamespaceGrowth);
            String strExpandSystemId = XMLEntityManager.expandSystemId(xMLInputSource.getSystemId(), xMLInputSource.getBaseSystemId(), false);
            if (schemaGrammarFindGrammar != null && (!this.fNamespaceGrowth || (strExpandSystemId != null && schemaGrammarFindGrammar.getDocumentLocations().contains(strExpandSystemId)))) {
                return schemaGrammarFindGrammar;
            }
            this.fTraversed.put(new XSDKey(strExpandSystemId, contextType, strAddSymbol), schemaDocument);
            if (strExpandSystemId != null) {
                this.fDoc2SystemId.put(schemaDocument, strExpandSystemId);
            }
        }
        prepareForTraverse();
        XSDocumentInfo xSDocumentInfoConstructTrees = constructTrees(schemaDocument, xMLInputSource.getSystemId(), xSDDescription, schemaGrammarFindGrammar != null);
        this.fRoot = xSDocumentInfoConstructTrees;
        if (xSDocumentInfoConstructTrees == null) {
            return null;
        }
        buildGlobalNameRegistries();
        ArrayList arrayList = this.fValidateAnnotations ? new ArrayList() : null;
        traverseSchemas(arrayList);
        traverseLocalElements();
        resolveKeyRefs();
        for (int size = this.fAllTNSs.size() - 1; size >= 0; size--) {
            String str = (String) this.fAllTNSs.elementAt(size);
            Vector vector = (Vector) this.fImportMap.get(str);
            SchemaGrammar grammar2 = this.fGrammarBucket.getGrammar(emptyString2Null(str));
            if (grammar2 != null) {
                int i = 0;
                for (int i2 = 0; i2 < vector.size(); i2++) {
                    SchemaGrammar grammar3 = this.fGrammarBucket.getGrammar((String) vector.elementAt(i2));
                    if (grammar3 != null) {
                        vector.setElementAt(grammar3, i);
                        i++;
                    }
                }
                vector.setSize(i);
                grammar2.setImportedGrammars(vector);
            }
        }
        if (this.fValidateAnnotations && arrayList.size() > 0) {
            validateAnnotations(arrayList);
        }
        return this.fGrammarBucket.getGrammar(this.fRoot.fTargetNamespace);
    }

    void prepareForParse() {
        this.fTraversed.clear();
        this.fDoc2SystemId.clear();
        this.fHiddenNodes.clear();
        this.fLastSchemaWasDuplicate = false;
    }

    void prepareForTraverse() {
        this.fUnparsedAttributeRegistry.clear();
        this.fUnparsedAttributeGroupRegistry.clear();
        this.fUnparsedElementRegistry.clear();
        this.fUnparsedGroupRegistry.clear();
        this.fUnparsedIdentityConstraintRegistry.clear();
        this.fUnparsedNotationRegistry.clear();
        this.fUnparsedTypeRegistry.clear();
        this.fUnparsedAttributeRegistrySub.clear();
        this.fUnparsedAttributeGroupRegistrySub.clear();
        this.fUnparsedElementRegistrySub.clear();
        this.fUnparsedGroupRegistrySub.clear();
        this.fUnparsedIdentityConstraintRegistrySub.clear();
        this.fUnparsedNotationRegistrySub.clear();
        this.fUnparsedTypeRegistrySub.clear();
        for (int i = 1; i <= 7; i++) {
            this.fUnparsedRegistriesExt[i].clear();
        }
        this.fXSDocumentInfoRegistry.clear();
        this.fDependencyMap.clear();
        this.fDoc2XSDocumentMap.clear();
        this.fRedefine2XSDMap.clear();
        this.fRedefine2NSSupport.clear();
        this.fAllTNSs.removeAllElements();
        this.fImportMap.clear();
        this.fRoot = null;
        for (int i2 = 0; i2 < this.fLocalElemStackPos; i2++) {
            this.fParticle[i2] = null;
            this.fLocalElementDecl[i2] = null;
            this.fLocalElementDecl_schema[i2] = null;
            this.fLocalElemNamespaceContext[i2] = null;
        }
        this.fLocalElemStackPos = 0;
        for (int i3 = 0; i3 < this.fKeyrefStackPos; i3++) {
            this.fKeyrefs[i3] = null;
            this.fKeyrefElems[i3] = null;
            this.fKeyrefNamespaceContext[i3] = null;
            this.fKeyrefsMapXSDocumentInfo[i3] = null;
        }
        this.fKeyrefStackPos = 0;
        if (this.fAttributeChecker == null) {
            createTraversers();
        }
        Locale locale = this.fErrorReporter.getLocale();
        this.fAttributeChecker.reset(this.fSymbolTable);
        this.fAttributeGroupTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale);
        this.fAttributeTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale);
        this.fComplexTypeTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale);
        this.fElementTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale);
        this.fGroupTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale);
        this.fKeyrefTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale);
        this.fNotationTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale);
        this.fSimpleTypeTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale);
        this.fUniqueOrKeyTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale);
        this.fWildCardTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, locale);
        this.fRedefinedRestrictedAttributeGroupRegistry.clear();
        this.fRedefinedRestrictedGroupRegistry.clear();
        this.fGlobalAttrDecls.clear();
        this.fGlobalAttrGrpDecls.clear();
        this.fGlobalElemDecls.clear();
        this.fGlobalGroupDecls.clear();
        this.fGlobalNotationDecls.clear();
        this.fGlobalIDConstraintDecls.clear();
        this.fGlobalTypeDecls.clear();
    }

    void reportSchemaError(String str, Object[] objArr, Element element) {
        reportSchemaError(str, objArr, element, null);
    }

    void reportSchemaError(String str, Object[] objArr, Element element, Exception exc) {
        if (element2Locator(element, this.xl)) {
            this.fErrorReporter.reportError(this.xl, XSMessageFormatter.SCHEMA_DOMAIN, str, objArr, (short) 1, exc);
        } else {
            this.fErrorReporter.reportError(XSMessageFormatter.SCHEMA_DOMAIN, str, objArr, (short) 1, exc);
        }
    }

    void reportSchemaWarning(String str, Object[] objArr, Element element) {
        reportSchemaWarning(str, objArr, element, null);
    }

    void reportSchemaWarning(String str, Object[] objArr, Element element, Exception exc) {
        if (element2Locator(element, this.xl)) {
            this.fErrorReporter.reportError(this.xl, XSMessageFormatter.SCHEMA_DOMAIN, str, objArr, (short) 0, exc);
        } else {
            this.fErrorReporter.reportError(XSMessageFormatter.SCHEMA_DOMAIN, str, objArr, (short) 0, exc);
        }
    }

    public void reset(XMLComponentManager xMLComponentManager) {
        this.fSymbolTable = (SymbolTable) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
        this.fEntityResolver = (XMLEntityResolver) xMLComponentManager.getProperty(ENTITY_MANAGER);
        XMLEntityResolver xMLEntityResolver = (XMLEntityResolver) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
        if (xMLEntityResolver != null) {
            this.fSchemaParser.setEntityResolver(xMLEntityResolver);
        }
        XMLErrorReporter xMLErrorReporter = (XMLErrorReporter) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
        this.fErrorReporter = xMLErrorReporter;
        try {
            XMLErrorHandler errorHandler = xMLErrorReporter.getErrorHandler();
            if (errorHandler != this.fSchemaParser.getProperty(ERROR_HANDLER)) {
                this.fSchemaParser.setProperty(ERROR_HANDLER, errorHandler != null ? errorHandler : new DefaultErrorHandler());
                XML11Configuration xML11Configuration = this.fAnnotationValidator;
                if (xML11Configuration != null) {
                    if (errorHandler == null) {
                        errorHandler = new DefaultErrorHandler();
                    }
                    xML11Configuration.setProperty(ERROR_HANDLER, errorHandler);
                }
            }
            Locale locale = this.fErrorReporter.getLocale();
            if (locale != this.fSchemaParser.getProperty(LOCALE)) {
                this.fSchemaParser.setProperty(LOCALE, locale);
                XML11Configuration xML11Configuration2 = this.fAnnotationValidator;
                if (xML11Configuration2 != null) {
                    xML11Configuration2.setProperty(LOCALE, locale);
                }
            }
        } catch (XMLConfigurationException unused) {
        }
        try {
            this.fValidateAnnotations = xMLComponentManager.getFeature(VALIDATE_ANNOTATIONS);
        } catch (XMLConfigurationException unused2) {
            this.fValidateAnnotations = false;
        }
        try {
            this.fHonourAllSchemaLocations = xMLComponentManager.getFeature(HONOUR_ALL_SCHEMALOCATIONS);
        } catch (XMLConfigurationException unused3) {
            this.fHonourAllSchemaLocations = false;
        }
        try {
            this.fNamespaceGrowth = xMLComponentManager.getFeature(NAMESPACE_GROWTH);
        } catch (XMLConfigurationException unused4) {
            this.fNamespaceGrowth = false;
        }
        try {
            this.fTolerateDuplicates = xMLComponentManager.getFeature(TOLERATE_DUPLICATES);
        } catch (XMLConfigurationException unused5) {
            this.fTolerateDuplicates = false;
        }
        try {
            this.fSchemaParser.setFeature(CONTINUE_AFTER_FATAL_ERROR, this.fErrorReporter.getFeature(CONTINUE_AFTER_FATAL_ERROR));
        } catch (XMLConfigurationException unused6) {
        }
        try {
            this.fSchemaParser.setFeature(ALLOW_JAVA_ENCODINGS, xMLComponentManager.getFeature(ALLOW_JAVA_ENCODINGS));
        } catch (XMLConfigurationException unused7) {
        }
        try {
            this.fSchemaParser.setFeature(STANDARD_URI_CONFORMANT_FEATURE, xMLComponentManager.getFeature(STANDARD_URI_CONFORMANT_FEATURE));
        } catch (XMLConfigurationException unused8) {
        }
        try {
            this.fGrammarPool = (XMLGrammarPool) xMLComponentManager.getProperty("http://apache.org/xml/properties/internal/grammar-pool");
        } catch (XMLConfigurationException unused9) {
            this.fGrammarPool = null;
        }
        try {
            this.fSchemaParser.setFeature(DISALLOW_DOCTYPE, xMLComponentManager.getFeature(DISALLOW_DOCTYPE));
        } catch (XMLConfigurationException unused10) {
        }
        try {
            Object property = xMLComponentManager.getProperty(SECURITY_MANAGER);
            if (property != null) {
                this.fSchemaParser.setProperty(SECURITY_MANAGER, property);
            }
        } catch (XMLConfigurationException unused11) {
        }
    }

    protected void resolveKeyRefs() {
        for (int i = 0; i < this.fKeyrefStackPos; i++) {
            XSDocumentInfo xSDocumentInfo = this.fKeyrefsMapXSDocumentInfo[i];
            xSDocumentInfo.fNamespaceSupport.makeGlobal();
            xSDocumentInfo.fNamespaceSupport.setEffectiveContext(this.fKeyrefNamespaceContext[i]);
            SchemaGrammar grammar = this.fGrammarBucket.getGrammar(xSDocumentInfo.fTargetNamespace);
            DOMUtil.setHidden(this.fKeyrefs[i], this.fHiddenNodes);
            this.fKeyrefTraverser.traverse(this.fKeyrefs[i], this.fKeyrefElems[i], xSDocumentInfo, grammar);
        }
    }

    public String schemaDocument2SystemId(XSDocumentInfo xSDocumentInfo) {
        return (String) this.fDoc2SystemId.get(xSDocumentInfo.fSchemaElement);
    }

    public void setDVFactory(SchemaDVFactory schemaDVFactory) {
        this.fDVFactory = schemaDVFactory;
    }

    public void setDeclPool(XSDeclarationPool xSDeclarationPool) {
        this.fDeclPool = xSDeclarationPool;
    }

    public void setGenerateSyntheticAnnotations(boolean z) {
        this.fSchemaParser.setFeature("http://apache.org/xml/features/generate-synthetic-annotations", z);
    }

    protected void storeKeyRef(Element element, XSDocumentInfo xSDocumentInfo, XSElementDecl xSElementDecl) {
        String attrValue = DOMUtil.getAttrValue(element, SchemaSymbols.ATT_NAME);
        if (attrValue.length() != 0) {
            checkForDuplicateNames((xSDocumentInfo.fTargetNamespace == null ? new StringBuilder(",") : new StringBuilder().append(xSDocumentInfo.fTargetNamespace).append(",")).append(attrValue).toString(), 5, this.fUnparsedIdentityConstraintRegistry, this.fUnparsedIdentityConstraintRegistrySub, element, xSDocumentInfo);
        }
        int i = this.fKeyrefStackPos;
        Element[] elementArr = this.fKeyrefs;
        if (i == elementArr.length) {
            Element[] elementArr2 = new Element[i + 2];
            System.arraycopy(elementArr, 0, elementArr2, 0, i);
            this.fKeyrefs = elementArr2;
            int i2 = this.fKeyrefStackPos;
            XSElementDecl[] xSElementDeclArr = new XSElementDecl[i2 + 2];
            System.arraycopy(this.fKeyrefElems, 0, xSElementDeclArr, 0, i2);
            this.fKeyrefElems = xSElementDeclArr;
            int i3 = this.fKeyrefStackPos;
            String[][] strArr = new String[i3 + 2][];
            System.arraycopy(this.fKeyrefNamespaceContext, 0, strArr, 0, i3);
            this.fKeyrefNamespaceContext = strArr;
            int i4 = this.fKeyrefStackPos;
            XSDocumentInfo[] xSDocumentInfoArr = new XSDocumentInfo[i4 + 2];
            System.arraycopy(this.fKeyrefsMapXSDocumentInfo, 0, xSDocumentInfoArr, 0, i4);
            this.fKeyrefsMapXSDocumentInfo = xSDocumentInfoArr;
        }
        Element[] elementArr3 = this.fKeyrefs;
        int i5 = this.fKeyrefStackPos;
        elementArr3[i5] = element;
        this.fKeyrefElems[i5] = xSElementDecl;
        this.fKeyrefNamespaceContext[i5] = xSDocumentInfo.fNamespaceSupport.getEffectiveLocalContext();
        XSDocumentInfo[] xSDocumentInfoArr2 = this.fKeyrefsMapXSDocumentInfo;
        int i6 = this.fKeyrefStackPos;
        this.fKeyrefStackPos = i6 + 1;
        xSDocumentInfoArr2[i6] = xSDocumentInfo;
    }

    protected Object traverseGlobalDecl(int i, Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        DOMUtil.setHidden(element, this.fHiddenNodes);
        Element parent = DOMUtil.getParent(element);
        Object objTraverseGlobal = null;
        xSDocumentInfo.backupNSSupport(DOMUtil.getLocalName(parent).equals(SchemaSymbols.ELT_REDEFINE) ? (SchemaNamespaceSupport) this.fRedefine2NSSupport.get(parent) : null);
        if (i == 1) {
            objTraverseGlobal = this.fAttributeTraverser.traverseGlobal(element, xSDocumentInfo, schemaGrammar);
        } else if (i == 2) {
            objTraverseGlobal = this.fAttributeGroupTraverser.traverseGlobal(element, xSDocumentInfo, schemaGrammar);
        } else if (i == 3) {
            objTraverseGlobal = this.fElementTraverser.traverseGlobal(element, xSDocumentInfo, schemaGrammar);
        } else if (i == 4) {
            objTraverseGlobal = this.fGroupTraverser.traverseGlobal(element, xSDocumentInfo, schemaGrammar);
        } else if (i == 6) {
            objTraverseGlobal = this.fNotationTraverser.traverse(element, xSDocumentInfo, schemaGrammar);
        } else if (i == 7) {
            objTraverseGlobal = DOMUtil.getLocalName(element).equals(SchemaSymbols.ELT_COMPLEXTYPE) ? this.fComplexTypeTraverser.traverseGlobal(element, xSDocumentInfo, schemaGrammar) : this.fSimpleTypeTraverser.traverseGlobal(element, xSDocumentInfo, schemaGrammar);
        }
        xSDocumentInfo.restoreNSSupport();
        return objTraverseGlobal;
    }

    void traverseLocalElements() {
        XSModelGroupImpl xSModelGroupImpl;
        this.fElementTraverser.fDeferTraversingLocalElements = false;
        for (int i = 0; i < this.fLocalElemStackPos; i++) {
            Element element = this.fLocalElementDecl[i];
            XSDocumentInfo xSDocumentInfo = this.fLocalElementDecl_schema[i];
            this.fElementTraverser.traverseLocal(this.fParticle[i], element, xSDocumentInfo, this.fGrammarBucket.getGrammar(xSDocumentInfo.fTargetNamespace), this.fAllContext[i], this.fParent[i], this.fLocalElemNamespaceContext[i]);
            if (this.fParticle[i].fType == 0) {
                XSObject xSObject = this.fParent[i];
                if (xSObject instanceof XSComplexTypeDecl) {
                    XSParticle particle = ((XSComplexTypeDecl) xSObject).getParticle();
                    xSModelGroupImpl = particle != null ? (XSModelGroupImpl) particle.getTerm() : null;
                } else {
                    xSModelGroupImpl = ((XSGroupDecl) xSObject).fModelGroup;
                }
                if (xSModelGroupImpl != null) {
                    removeParticle(xSModelGroupImpl, this.fParticle[i]);
                }
            }
        }
    }

    protected void traverseSchemas(ArrayList arrayList) {
        XSAnnotationInfo annotations;
        String syntheticAnnotation;
        setSchemasVisible(this.fRoot);
        Stack stack = new Stack();
        stack.push(this.fRoot);
        while (!stack.empty()) {
            XSDocumentInfo xSDocumentInfo = (XSDocumentInfo) stack.pop();
            Element element = xSDocumentInfo.fSchemaElement;
            SchemaGrammar grammar = this.fGrammarBucket.getGrammar(xSDocumentInfo.fTargetNamespace);
            if (!DOMUtil.isHidden(element, this.fHiddenNodes)) {
                Element firstVisibleChildElement = DOMUtil.getFirstVisibleChildElement(element, this.fHiddenNodes);
                boolean z = false;
                while (firstVisibleChildElement != null) {
                    DOMUtil.setHidden(firstVisibleChildElement, this.fHiddenNodes);
                    String localName = DOMUtil.getLocalName(firstVisibleChildElement);
                    if (DOMUtil.getLocalName(firstVisibleChildElement).equals(SchemaSymbols.ELT_REDEFINE)) {
                        xSDocumentInfo.backupNSSupport((SchemaNamespaceSupport) this.fRedefine2NSSupport.get(firstVisibleChildElement));
                        Element firstVisibleChildElement2 = DOMUtil.getFirstVisibleChildElement(firstVisibleChildElement, this.fHiddenNodes);
                        while (firstVisibleChildElement2 != null) {
                            String localName2 = DOMUtil.getLocalName(firstVisibleChildElement2);
                            DOMUtil.setHidden(firstVisibleChildElement2, this.fHiddenNodes);
                            if (localName2.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                                this.fAttributeGroupTraverser.traverseGlobal(firstVisibleChildElement2, xSDocumentInfo, grammar);
                            } else if (localName2.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
                                this.fComplexTypeTraverser.traverseGlobal(firstVisibleChildElement2, xSDocumentInfo, grammar);
                            } else if (localName2.equals(SchemaSymbols.ELT_GROUP)) {
                                this.fGroupTraverser.traverseGlobal(firstVisibleChildElement2, xSDocumentInfo, grammar);
                            } else if (localName2.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                                this.fSimpleTypeTraverser.traverseGlobal(firstVisibleChildElement2, xSDocumentInfo, grammar);
                            } else {
                                reportSchemaError("s4s-elt-must-match.1", new Object[]{DOMUtil.getLocalName(firstVisibleChildElement), "(annotation | (simpleType | complexType | group | attributeGroup))*", localName2}, firstVisibleChildElement2);
                            }
                            firstVisibleChildElement2 = DOMUtil.getNextVisibleSiblingElement(firstVisibleChildElement2, this.fHiddenNodes);
                        }
                        xSDocumentInfo.restoreNSSupport();
                    } else if (localName.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
                        this.fAttributeTraverser.traverseGlobal(firstVisibleChildElement, xSDocumentInfo, grammar);
                    } else if (localName.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                        this.fAttributeGroupTraverser.traverseGlobal(firstVisibleChildElement, xSDocumentInfo, grammar);
                    } else if (localName.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
                        this.fComplexTypeTraverser.traverseGlobal(firstVisibleChildElement, xSDocumentInfo, grammar);
                    } else if (localName.equals(SchemaSymbols.ELT_ELEMENT)) {
                        this.fElementTraverser.traverseGlobal(firstVisibleChildElement, xSDocumentInfo, grammar);
                    } else if (localName.equals(SchemaSymbols.ELT_GROUP)) {
                        this.fGroupTraverser.traverseGlobal(firstVisibleChildElement, xSDocumentInfo, grammar);
                    } else if (localName.equals(SchemaSymbols.ELT_NOTATION)) {
                        this.fNotationTraverser.traverse(firstVisibleChildElement, xSDocumentInfo, grammar);
                    } else if (localName.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                        this.fSimpleTypeTraverser.traverseGlobal(firstVisibleChildElement, xSDocumentInfo, grammar);
                    } else if (localName.equals(SchemaSymbols.ELT_ANNOTATION)) {
                        grammar.addAnnotation(this.fElementTraverser.traverseAnnotationDecl(firstVisibleChildElement, xSDocumentInfo.getSchemaAttrs(), true, xSDocumentInfo));
                        z = true;
                    } else {
                        reportSchemaError("s4s-elt-invalid-content.1", new Object[]{SchemaSymbols.ELT_SCHEMA, DOMUtil.getLocalName(firstVisibleChildElement)}, firstVisibleChildElement);
                    }
                    firstVisibleChildElement = DOMUtil.getNextVisibleSiblingElement(firstVisibleChildElement, this.fHiddenNodes);
                }
                if (!z && (syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element)) != null) {
                    grammar.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(element, syntheticAnnotation, xSDocumentInfo.getSchemaAttrs(), true, xSDocumentInfo));
                }
                if (arrayList != null && (annotations = xSDocumentInfo.getAnnotations()) != null) {
                    arrayList.add(doc2SystemId(element));
                    arrayList.add(annotations);
                }
                xSDocumentInfo.returnSchemaAttrs();
                DOMUtil.setHidden(element, this.fHiddenNodes);
                Vector vector = (Vector) this.fDependencyMap.get(xSDocumentInfo);
                for (int i = 0; i < vector.size(); i++) {
                    stack.push(vector.elementAt(i));
                }
            }
        }
    }
}
