package org.apache.xerces.parsers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Stack;
import java.util.StringTokenizer;
import org.apache.commons.lang3.CharEncoding;
import org.apache.xerces.dom.DOMErrorImpl;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.dom.DOMStringListImpl;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.jaxp.JAXPConstants;
import org.apache.xerces.util.DOMEntityResolverWrapper;
import org.apache.xerces.util.DOMErrorHandlerWrapper;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLDTDContentModelHandler;
import org.apache.xerces.xni.XMLDTDHandler;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLDTDContentModelSource;
import org.apache.xerces.xni.parser.XMLDTDSource;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParseException;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSParserFilter;
import org.w3c.dom.ls.LSResourceResolver;

/* JADX INFO: loaded from: classes13.dex */
public class DOMParserImpl extends AbstractDOMParser implements LSParser, DOMConfiguration {
    protected static final boolean DEBUG = false;
    protected static final String DISALLOW_DOCTYPE_DECL_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
    protected static final String DYNAMIC_VALIDATION = "http://apache.org/xml/features/validation/dynamic";
    protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
    protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
    protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
    protected static final String PSVI_AUGMENT = "http://apache.org/xml/features/validation/schema/augment-psvi";
    protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
    protected static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
    protected static final String XMLSCHEMA = "http://apache.org/xml/features/validation/schema";
    protected static final String XMLSCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
    private AbortHandler abortHandler;
    private boolean abortNow;
    private Thread currentThread;
    protected boolean fBusy;
    protected boolean fNamespaceDeclarations;
    private boolean fNullFilterInUse;
    private DOMStringList fRecognizedParameters;
    private String fSchemaLocation;
    protected String fSchemaType;

    private static final class AbortHandler implements XMLDocumentHandler, XMLDTDHandler, XMLDTDContentModelHandler {
        private XMLDocumentSource documentSource;
        private XMLDTDContentModelSource dtdContentSource;
        private XMLDTDSource dtdSource;

        private AbortHandler() {
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public void any(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void attributeDecl(String str, String str2, String str3, String[] strArr, String str4, XMLString xMLString, XMLString xMLString2, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public void element(String str, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void elementDecl(String str, String str2, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public void empty(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void emptyElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void endAttlist(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void endCDATA(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void endConditional(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public void endContentModel(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void endDTD(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void endDocument(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void endElement(QName qName, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void endExternalSubset(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void endGeneralEntity(String str, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public void endGroup(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void endParameterEntity(String str, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void externalEntityDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public XMLDTDContentModelSource getDTDContentModelSource() {
            return this.dtdContentSource;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public XMLDTDSource getDTDSource() {
            return this.dtdSource;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public XMLDocumentSource getDocumentSource() {
            return this.documentSource;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void ignorableWhitespace(XMLString xMLString, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void ignoredCharacters(XMLString xMLString, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void internalEntityDecl(String str, XMLString xMLString, XMLString xMLString2, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void notationDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public void occurrence(short s, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public void pcdata(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public void separator(short s, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public void setDTDContentModelSource(XMLDTDContentModelSource xMLDTDContentModelSource) {
            this.dtdContentSource = xMLDTDContentModelSource;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void setDTDSource(XMLDTDSource xMLDTDSource) {
            this.dtdSource = xMLDTDSource;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void setDocumentSource(XMLDocumentSource xMLDocumentSource) {
            this.documentSource = xMLDocumentSource;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void startAttlist(String str, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void startCDATA(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void startConditional(short s, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public void startContentModel(String str, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void startDTD(XMLLocator xMLLocator, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void startDocument(XMLLocator xMLLocator, String str, NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void startExternalSubset(XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void startGeneralEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDContentModelHandler
        public void startGroup(Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void startParameterEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void textDecl(String str, String str2, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDTDHandler
        public void unparsedEntityDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }

        @Override // org.apache.xerces.xni.XMLDocumentHandler
        public void xmlDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
            throw AbstractDOMParser.Abort.INSTANCE;
        }
    }

    static final class NullLSParserFilter implements LSParserFilter {
        static final NullLSParserFilter INSTANCE = new NullLSParserFilter();

        private NullLSParserFilter() {
        }

        @Override // org.w3c.dom.ls.LSParserFilter
        public short acceptNode(Node node) {
            return (short) 1;
        }

        @Override // org.w3c.dom.ls.LSParserFilter
        public int getWhatToShow() {
            return -1;
        }

        @Override // org.w3c.dom.ls.LSParserFilter
        public short startElement(Element element) {
            return (short) 1;
        }
    }

    public DOMParserImpl(String str, String str2) {
        this((XMLParserConfiguration) ObjectFactory.createObject("org.apache.xerces.xni.parser.XMLParserConfiguration", str));
        if (str2 != null) {
            if (str2.equals(Constants.NS_DTD)) {
                this.fConfiguration.setProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE, Constants.NS_DTD);
                this.fSchemaType = Constants.NS_DTD;
            } else if (str2.equals(Constants.NS_XMLSCHEMA)) {
                this.fConfiguration.setProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE, Constants.NS_XMLSCHEMA);
            }
        }
    }

    public DOMParserImpl(SymbolTable symbolTable) {
        this((XMLParserConfiguration) ObjectFactory.createObject("org.apache.xerces.xni.parser.XMLParserConfiguration", "org.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
        this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", symbolTable);
    }

    public DOMParserImpl(SymbolTable symbolTable, XMLGrammarPool xMLGrammarPool) {
        this((XMLParserConfiguration) ObjectFactory.createObject("org.apache.xerces.xni.parser.XMLParserConfiguration", "org.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
        this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", symbolTable);
        this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/grammar-pool", xMLGrammarPool);
    }

    public DOMParserImpl(XMLParserConfiguration xMLParserConfiguration) {
        super(xMLParserConfiguration);
        this.fNamespaceDeclarations = true;
        this.fSchemaType = null;
        this.fBusy = false;
        this.abortNow = false;
        this.fSchemaLocation = null;
        this.fNullFilterInUse = false;
        this.abortHandler = null;
        this.fConfiguration.addRecognizedFeatures(new String[]{Constants.DOM_CANONICAL_FORM, Constants.DOM_CDATA_SECTIONS, Constants.DOM_CHARSET_OVERRIDES_XML_ENCODING, Constants.DOM_INFOSET, Constants.DOM_NAMESPACE_DECLARATIONS, Constants.DOM_SPLIT_CDATA, Constants.DOM_SUPPORTED_MEDIATYPES_ONLY, Constants.DOM_CERTIFIED, Constants.DOM_WELLFORMED, Constants.DOM_IGNORE_UNKNOWN_CHARACTER_DENORMALIZATIONS});
        this.fConfiguration.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
        this.fConfiguration.setFeature(Constants.DOM_NAMESPACE_DECLARATIONS, true);
        this.fConfiguration.setFeature(Constants.DOM_WELLFORMED, true);
        this.fConfiguration.setFeature("http://apache.org/xml/features/include-comments", true);
        this.fConfiguration.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", true);
        this.fConfiguration.setFeature(NAMESPACES, true);
        this.fConfiguration.setFeature(DYNAMIC_VALIDATION, false);
        this.fConfiguration.setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes", false);
        this.fConfiguration.setFeature("http://apache.org/xml/features/create-cdata-nodes", false);
        this.fConfiguration.setFeature(Constants.DOM_CANONICAL_FORM, false);
        this.fConfiguration.setFeature(Constants.DOM_CHARSET_OVERRIDES_XML_ENCODING, true);
        this.fConfiguration.setFeature(Constants.DOM_SPLIT_CDATA, true);
        this.fConfiguration.setFeature(Constants.DOM_SUPPORTED_MEDIATYPES_ONLY, false);
        this.fConfiguration.setFeature(Constants.DOM_IGNORE_UNKNOWN_CHARACTER_DENORMALIZATIONS, true);
        this.fConfiguration.setFeature(Constants.DOM_CERTIFIED, true);
        try {
            this.fConfiguration.setFeature(NORMALIZE_DATA, false);
        } catch (XMLConfigurationException unused) {
        }
    }

    private static DOMException newFeatureNotFoundError(String str) {
        return new DOMException((short) 8, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_FOUND", new Object[]{str}));
    }

    private static DOMException newFeatureNotSupportedError(String str) {
        return new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "FEATURE_NOT_SUPPORTED", new Object[]{str}));
    }

    private static DOMException newInvalidStateError() {
        throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
    }

    private static DOMException newTypeMismatchError(String str) {
        return new DOMException((short) 17, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "TYPE_MISMATCH_ERR", new Object[]{str}));
    }

    private void restoreHandlers() {
        this.fConfiguration.setDocumentHandler(this);
        this.fConfiguration.setDTDHandler(this);
        this.fConfiguration.setDTDContentModelHandler(this);
    }

    @Override // org.apache.xerces.parsers.AbstractDOMParser, org.w3c.dom.ls.LSParser
    public void abort() {
        if (this.fBusy) {
            this.fBusy = false;
            if (this.currentThread != null) {
                this.abortNow = true;
                if (this.abortHandler == null) {
                    this.abortHandler = new AbortHandler();
                }
                this.fConfiguration.setDocumentHandler(this.abortHandler);
                this.fConfiguration.setDTDHandler(this.abortHandler);
                this.fConfiguration.setDTDContentModelHandler(this.abortHandler);
                if (this.currentThread == Thread.currentThread()) {
                    throw AbstractDOMParser.Abort.INSTANCE;
                }
                this.currentThread.interrupt();
            }
        }
    }

    @Override // org.w3c.dom.DOMConfiguration
    public boolean canSetParameter(String str, Object obj) {
        String lowerCase = TOLERATE_DUPLICATES;
        if (obj == null) {
            return true;
        }
        if (!(obj instanceof Boolean)) {
            if (str.equalsIgnoreCase(Constants.DOM_ERROR_HANDLER)) {
                return (obj instanceof DOMErrorHandler) || obj == null;
            }
            if (str.equalsIgnoreCase(Constants.DOM_RESOURCE_RESOLVER)) {
                return (obj instanceof LSResourceResolver) || obj == null;
            }
            if (str.equalsIgnoreCase(Constants.DOM_SCHEMA_TYPE)) {
                return ((obj instanceof String) && (obj.equals(Constants.NS_XMLSCHEMA) || obj.equals(Constants.NS_DTD))) || obj == null;
            }
            if (str.equalsIgnoreCase(Constants.DOM_SCHEMA_LOCATION)) {
                return (obj instanceof String) || obj == null;
            }
            if (str.equalsIgnoreCase("http://apache.org/xml/properties/dom/document-class-name")) {
                return true;
            }
            try {
                this.fConfiguration.getProperty(str.toLowerCase(Locale.ENGLISH));
                return true;
            } catch (XMLConfigurationException unused) {
                return false;
            }
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        if (str.equalsIgnoreCase(Constants.DOM_SUPPORTED_MEDIATYPES_ONLY) || str.equalsIgnoreCase(Constants.DOM_NORMALIZE_CHARACTERS) || str.equalsIgnoreCase(Constants.DOM_CHECK_CHAR_NORMALIZATION) || str.equalsIgnoreCase(Constants.DOM_CANONICAL_FORM)) {
            return !zBooleanValue;
        }
        if (str.equalsIgnoreCase(Constants.DOM_WELLFORMED) || str.equalsIgnoreCase(Constants.DOM_IGNORE_UNKNOWN_CHARACTER_DENORMALIZATIONS)) {
            return zBooleanValue;
        }
        if (str.equalsIgnoreCase(Constants.DOM_CDATA_SECTIONS) || str.equalsIgnoreCase(Constants.DOM_CHARSET_OVERRIDES_XML_ENCODING) || str.equalsIgnoreCase(Constants.DOM_COMMENTS) || str.equalsIgnoreCase(Constants.DOM_DATATYPE_NORMALIZATION) || str.equalsIgnoreCase(Constants.DOM_DISALLOW_DOCTYPE) || str.equalsIgnoreCase(Constants.DOM_ENTITIES) || str.equalsIgnoreCase(Constants.DOM_INFOSET) || str.equalsIgnoreCase("namespaces") || str.equalsIgnoreCase(Constants.DOM_NAMESPACE_DECLARATIONS) || str.equalsIgnoreCase(Constants.DOM_VALIDATE) || str.equalsIgnoreCase(Constants.DOM_VALIDATE_IF_SCHEMA) || str.equalsIgnoreCase(Constants.DOM_ELEMENT_CONTENT_WHITESPACE) || str.equalsIgnoreCase(Constants.DOM_XMLDECL)) {
            return true;
        }
        try {
            if (str.equalsIgnoreCase(HONOUR_ALL_SCHEMALOCATIONS)) {
                lowerCase = HONOUR_ALL_SCHEMALOCATIONS;
            } else if (str.equalsIgnoreCase(NAMESPACE_GROWTH)) {
                lowerCase = NAMESPACE_GROWTH;
            } else if (!str.equalsIgnoreCase(TOLERATE_DUPLICATES)) {
                lowerCase = str.toLowerCase(Locale.ENGLISH);
            }
            this.fConfiguration.getFeature(lowerCase);
            return true;
        } catch (XMLConfigurationException unused2) {
            return false;
        }
    }

    XMLInputSource dom2xmlInputSource(LSInput lSInput) {
        if (lSInput.getCharacterStream() != null) {
            return new XMLInputSource(lSInput.getPublicId(), lSInput.getSystemId(), lSInput.getBaseURI(), lSInput.getCharacterStream(), CharEncoding.UTF_16);
        }
        if (lSInput.getByteStream() != null) {
            return new XMLInputSource(lSInput.getPublicId(), lSInput.getSystemId(), lSInput.getBaseURI(), lSInput.getByteStream(), lSInput.getEncoding());
        }
        if (lSInput.getStringData() != null && lSInput.getStringData().length() > 0) {
            return new XMLInputSource(lSInput.getPublicId(), lSInput.getSystemId(), lSInput.getBaseURI(), new StringReader(lSInput.getStringData()), CharEncoding.UTF_16);
        }
        if ((lSInput.getSystemId() != null && lSInput.getSystemId().length() > 0) || (lSInput.getPublicId() != null && lSInput.getPublicId().length() > 0)) {
            return new XMLInputSource(lSInput.getPublicId(), lSInput.getSystemId(), lSInput.getBaseURI());
        }
        if (this.fErrorHandler != null) {
            DOMErrorImpl dOMErrorImpl = new DOMErrorImpl();
            dOMErrorImpl.fType = "no-input-specified";
            dOMErrorImpl.fMessage = "no-input-specified";
            dOMErrorImpl.fSeverity = (short) 3;
            this.fErrorHandler.getErrorHandler().handleError(dOMErrorImpl);
        }
        throw new LSException((short) 81, "no-input-specified");
    }

    @Override // org.w3c.dom.ls.LSParser
    public boolean getAsync() {
        return false;
    }

    @Override // org.w3c.dom.ls.LSParser
    public boolean getBusy() {
        return this.fBusy;
    }

    @Override // org.w3c.dom.ls.LSParser
    public DOMConfiguration getDomConfig() {
        return this;
    }

    @Override // org.w3c.dom.ls.LSParser
    public LSParserFilter getFilter() {
        if (this.fNullFilterInUse) {
            return null;
        }
        return this.fDOMFilter;
    }

    @Override // org.w3c.dom.DOMConfiguration
    public Object getParameter(String str) throws DOMException {
        if (str.equalsIgnoreCase(Constants.DOM_COMMENTS)) {
            return this.fConfiguration.getFeature("http://apache.org/xml/features/include-comments") ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_DATATYPE_NORMALIZATION)) {
            return this.fConfiguration.getFeature(NORMALIZE_DATA) ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_ENTITIES)) {
            return this.fConfiguration.getFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes") ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase("namespaces")) {
            return this.fConfiguration.getFeature(NAMESPACES) ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_VALIDATE)) {
            return this.fConfiguration.getFeature(VALIDATION_FEATURE) ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_VALIDATE_IF_SCHEMA)) {
            return this.fConfiguration.getFeature(DYNAMIC_VALIDATION) ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_ELEMENT_CONTENT_WHITESPACE)) {
            return this.fConfiguration.getFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace") ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_DISALLOW_DOCTYPE)) {
            return this.fConfiguration.getFeature(DISALLOW_DOCTYPE_DECL_FEATURE) ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_INFOSET)) {
            return (!this.fConfiguration.getFeature(NAMESPACES) || !this.fConfiguration.getFeature(Constants.DOM_NAMESPACE_DECLARATIONS) || !this.fConfiguration.getFeature("http://apache.org/xml/features/include-comments") || !this.fConfiguration.getFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace") || this.fConfiguration.getFeature(DYNAMIC_VALIDATION) || this.fConfiguration.getFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes") || this.fConfiguration.getFeature(NORMALIZE_DATA) || this.fConfiguration.getFeature("http://apache.org/xml/features/create-cdata-nodes")) ? Boolean.FALSE : Boolean.TRUE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_CDATA_SECTIONS)) {
            return this.fConfiguration.getFeature("http://apache.org/xml/features/create-cdata-nodes") ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_CHECK_CHAR_NORMALIZATION) || str.equalsIgnoreCase(Constants.DOM_NORMALIZE_CHARACTERS)) {
            return Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_NAMESPACE_DECLARATIONS) || str.equalsIgnoreCase(Constants.DOM_WELLFORMED) || str.equalsIgnoreCase(Constants.DOM_IGNORE_UNKNOWN_CHARACTER_DENORMALIZATIONS) || str.equalsIgnoreCase(Constants.DOM_CANONICAL_FORM) || str.equalsIgnoreCase(Constants.DOM_SUPPORTED_MEDIATYPES_ONLY) || str.equalsIgnoreCase(Constants.DOM_SPLIT_CDATA) || str.equalsIgnoreCase(Constants.DOM_CHARSET_OVERRIDES_XML_ENCODING)) {
            return this.fConfiguration.getFeature(str.toLowerCase(Locale.ENGLISH)) ? Boolean.TRUE : Boolean.FALSE;
        }
        if (str.equalsIgnoreCase(Constants.DOM_ERROR_HANDLER)) {
            if (this.fErrorHandler != null) {
                return this.fErrorHandler.getErrorHandler();
            }
            return null;
        }
        if (str.equalsIgnoreCase(Constants.DOM_RESOURCE_RESOLVER)) {
            try {
                XMLEntityResolver xMLEntityResolver = (XMLEntityResolver) this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
                if (xMLEntityResolver != null && (xMLEntityResolver instanceof DOMEntityResolverWrapper)) {
                    return ((DOMEntityResolverWrapper) xMLEntityResolver).getEntityResolver();
                }
            } catch (XMLConfigurationException unused) {
            }
            return null;
        }
        if (str.equalsIgnoreCase(Constants.DOM_SCHEMA_TYPE)) {
            return this.fConfiguration.getProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE);
        }
        if (str.equalsIgnoreCase(Constants.DOM_SCHEMA_LOCATION)) {
            return this.fSchemaLocation;
        }
        if (str.equalsIgnoreCase("http://apache.org/xml/properties/internal/symbol-table")) {
            return this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/symbol-table");
        }
        if (str.equalsIgnoreCase("http://apache.org/xml/properties/dom/document-class-name")) {
            return this.fConfiguration.getProperty("http://apache.org/xml/properties/dom/document-class-name");
        }
        String lowerCase = HONOUR_ALL_SCHEMALOCATIONS;
        if (!str.equalsIgnoreCase(HONOUR_ALL_SCHEMALOCATIONS)) {
            lowerCase = NAMESPACE_GROWTH;
            if (!str.equals(NAMESPACE_GROWTH)) {
                lowerCase = TOLERATE_DUPLICATES;
                if (!str.equals(TOLERATE_DUPLICATES)) {
                    lowerCase = str.toLowerCase(Locale.ENGLISH);
                }
            }
        }
        try {
            try {
                return this.fConfiguration.getFeature(lowerCase) ? Boolean.TRUE : Boolean.FALSE;
            } catch (XMLConfigurationException unused2) {
                throw newFeatureNotFoundError(str);
            }
        } catch (XMLConfigurationException unused3) {
            return this.fConfiguration.getProperty(lowerCase);
        }
    }

    @Override // org.w3c.dom.DOMConfiguration
    public DOMStringList getParameterNames() {
        if (this.fRecognizedParameters == null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("namespaces");
            arrayList.add(Constants.DOM_CDATA_SECTIONS);
            arrayList.add(Constants.DOM_CANONICAL_FORM);
            arrayList.add(Constants.DOM_NAMESPACE_DECLARATIONS);
            arrayList.add(Constants.DOM_SPLIT_CDATA);
            arrayList.add(Constants.DOM_ENTITIES);
            arrayList.add(Constants.DOM_VALIDATE_IF_SCHEMA);
            arrayList.add(Constants.DOM_VALIDATE);
            arrayList.add(Constants.DOM_DATATYPE_NORMALIZATION);
            arrayList.add(Constants.DOM_CHARSET_OVERRIDES_XML_ENCODING);
            arrayList.add(Constants.DOM_CHECK_CHAR_NORMALIZATION);
            arrayList.add(Constants.DOM_SUPPORTED_MEDIATYPES_ONLY);
            arrayList.add(Constants.DOM_IGNORE_UNKNOWN_CHARACTER_DENORMALIZATIONS);
            arrayList.add(Constants.DOM_NORMALIZE_CHARACTERS);
            arrayList.add(Constants.DOM_WELLFORMED);
            arrayList.add(Constants.DOM_INFOSET);
            arrayList.add(Constants.DOM_DISALLOW_DOCTYPE);
            arrayList.add(Constants.DOM_ELEMENT_CONTENT_WHITESPACE);
            arrayList.add(Constants.DOM_COMMENTS);
            arrayList.add(Constants.DOM_ERROR_HANDLER);
            arrayList.add(Constants.DOM_RESOURCE_RESOLVER);
            arrayList.add(Constants.DOM_SCHEMA_LOCATION);
            arrayList.add(Constants.DOM_SCHEMA_TYPE);
            this.fRecognizedParameters = new DOMStringListImpl(arrayList);
        }
        return this.fRecognizedParameters;
    }

    @Override // org.w3c.dom.ls.LSParser
    public Document parse(LSInput lSInput) throws LSException {
        XMLInputSource xMLInputSourceDom2xmlInputSource = dom2xmlInputSource(lSInput);
        if (this.fBusy) {
            throw newInvalidStateError();
        }
        try {
            this.currentThread = Thread.currentThread();
            this.fBusy = true;
            parse(xMLInputSourceDom2xmlInputSource);
            this.fBusy = false;
            if (this.abortNow && this.currentThread.isInterrupted()) {
                this.abortNow = false;
                Thread.interrupted();
            }
        } catch (Exception e) {
            this.fBusy = false;
            if (this.abortNow && this.currentThread.isInterrupted()) {
                Thread.interrupted();
            }
            if (this.abortNow) {
                this.abortNow = false;
                restoreHandlers();
                return null;
            }
            if (e != AbstractDOMParser.Abort.INSTANCE) {
                if (!(e instanceof XMLParseException) && this.fErrorHandler != null) {
                    DOMErrorImpl dOMErrorImpl = new DOMErrorImpl();
                    dOMErrorImpl.fException = e;
                    dOMErrorImpl.fMessage = e.getMessage();
                    dOMErrorImpl.fSeverity = (short) 3;
                    this.fErrorHandler.getErrorHandler().handleError(dOMErrorImpl);
                }
                throw ((LSException) DOMUtil.createLSException((short) 81, e).fillInStackTrace());
            }
        }
        Document document = getDocument();
        dropDocumentReferences();
        return document;
    }

    @Override // org.w3c.dom.ls.LSParser
    public Document parseURI(String str) throws LSException {
        if (this.fBusy) {
            throw newInvalidStateError();
        }
        XMLInputSource xMLInputSource = new XMLInputSource(null, str, null);
        try {
            this.currentThread = Thread.currentThread();
            this.fBusy = true;
            parse(xMLInputSource);
            this.fBusy = false;
            if (this.abortNow && this.currentThread.isInterrupted()) {
                this.abortNow = false;
                Thread.interrupted();
            }
        } catch (Exception e) {
            this.fBusy = false;
            if (this.abortNow && this.currentThread.isInterrupted()) {
                Thread.interrupted();
            }
            if (this.abortNow) {
                this.abortNow = false;
                restoreHandlers();
                return null;
            }
            if (e != AbstractDOMParser.Abort.INSTANCE) {
                if (!(e instanceof XMLParseException) && this.fErrorHandler != null) {
                    DOMErrorImpl dOMErrorImpl = new DOMErrorImpl();
                    dOMErrorImpl.fException = e;
                    dOMErrorImpl.fMessage = e.getMessage();
                    dOMErrorImpl.fSeverity = (short) 3;
                    this.fErrorHandler.getErrorHandler().handleError(dOMErrorImpl);
                }
                throw ((LSException) DOMUtil.createLSException((short) 81, e).fillInStackTrace());
            }
        }
        Document document = getDocument();
        dropDocumentReferences();
        return document;
    }

    @Override // org.w3c.dom.ls.LSParser
    public Node parseWithContext(LSInput lSInput, Node node, short s) throws DOMException, LSException {
        throw new DOMException((short) 9, "Not supported");
    }

    @Override // org.apache.xerces.parsers.AbstractDOMParser, org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.parsers.XMLParser
    public void reset() {
        super.reset();
        this.fNamespaceDeclarations = this.fConfiguration.getFeature(Constants.DOM_NAMESPACE_DECLARATIONS);
        if (this.fNullFilterInUse) {
            this.fDOMFilter = null;
            this.fNullFilterInUse = false;
        }
        if (this.fSkippedElemStack != null) {
            this.fSkippedElemStack.removeAllElements();
        }
        this.fRejectedElementDepth = 0;
        this.fFilterReject = false;
        this.fSchemaType = null;
    }

    @Override // org.w3c.dom.ls.LSParser
    public void setFilter(LSParserFilter lSParserFilter) {
        if (this.fBusy && lSParserFilter == null && this.fDOMFilter != null) {
            this.fNullFilterInUse = true;
            lSParserFilter = NullLSParserFilter.INSTANCE;
        }
        this.fDOMFilter = lSParserFilter;
        if (this.fSkippedElemStack == null) {
            this.fSkippedElemStack = new Stack();
        }
    }

    @Override // org.w3c.dom.DOMConfiguration
    public void setParameter(String str, Object obj) throws DOMException {
        String str2;
        XMLParserConfiguration xMLParserConfiguration;
        String lowerCase;
        Object array = obj;
        boolean z = array instanceof Boolean;
        String str3 = TOLERATE_DUPLICATES;
        if (!z) {
            try {
                if (str.equalsIgnoreCase(Constants.DOM_ERROR_HANDLER)) {
                    if (!(array instanceof DOMErrorHandler) && array != null) {
                        throw newTypeMismatchError(str);
                    }
                    this.fErrorHandler = new DOMErrorHandlerWrapper((DOMErrorHandler) array);
                    this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/error-handler", this.fErrorHandler);
                    return;
                }
                if (str.equalsIgnoreCase(Constants.DOM_RESOURCE_RESOLVER)) {
                    if (!(array instanceof LSResourceResolver) && array != null) {
                        throw newTypeMismatchError(str);
                    }
                    this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/entity-resolver", new DOMEntityResolverWrapper((LSResourceResolver) array));
                    return;
                }
                if (str.equalsIgnoreCase(Constants.DOM_SCHEMA_LOCATION)) {
                    if (!(array instanceof String) && array != null) {
                        throw newTypeMismatchError(str);
                    }
                    if (array == null) {
                        this.fSchemaLocation = null;
                        this.fConfiguration.setProperty(JAXPConstants.JAXP_SCHEMA_SOURCE, null);
                        return;
                    }
                    this.fSchemaLocation = (String) array;
                    StringTokenizer stringTokenizer = new StringTokenizer(this.fSchemaLocation, " \n\t\r");
                    if (stringTokenizer.hasMoreTokens()) {
                        ArrayList arrayList = new ArrayList();
                        do {
                            arrayList.add(stringTokenizer.nextToken());
                        } while (stringTokenizer.hasMoreTokens());
                        xMLParserConfiguration = this.fConfiguration;
                        array = arrayList.toArray();
                    } else {
                        xMLParserConfiguration = this.fConfiguration;
                    }
                    xMLParserConfiguration.setProperty(JAXPConstants.JAXP_SCHEMA_SOURCE, array);
                    return;
                }
                if (!str.equalsIgnoreCase(Constants.DOM_SCHEMA_TYPE)) {
                    if (str.equalsIgnoreCase("http://apache.org/xml/properties/dom/document-class-name")) {
                        this.fConfiguration.setProperty("http://apache.org/xml/properties/dom/document-class-name", array);
                        return;
                    }
                    String lowerCase2 = str.toLowerCase(Locale.ENGLISH);
                    try {
                        try {
                            this.fConfiguration.setProperty(lowerCase2, array);
                            return;
                        } catch (XMLConfigurationException unused) {
                            if (str.equalsIgnoreCase(HONOUR_ALL_SCHEMALOCATIONS)) {
                                str3 = HONOUR_ALL_SCHEMALOCATIONS;
                            } else if (str.equals(NAMESPACE_GROWTH)) {
                                str3 = NAMESPACE_GROWTH;
                            } else if (!str.equals(TOLERATE_DUPLICATES)) {
                                str3 = lowerCase2;
                            }
                            this.fConfiguration.getFeature(str3);
                            throw newTypeMismatchError(str);
                        }
                    } catch (XMLConfigurationException unused2) {
                        throw newFeatureNotFoundError(str);
                    }
                }
                if (!(array instanceof String) && array != null) {
                    throw newTypeMismatchError(str);
                }
                if (array == null) {
                    this.fConfiguration.setFeature(XMLSCHEMA, false);
                    this.fConfiguration.setFeature(XMLSCHEMA_FULL_CHECKING, false);
                    this.fConfiguration.setProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE, null);
                    this.fSchemaType = null;
                    return;
                }
                if (array.equals(Constants.NS_XMLSCHEMA)) {
                    this.fConfiguration.setFeature(XMLSCHEMA, true);
                    this.fConfiguration.setFeature(XMLSCHEMA_FULL_CHECKING, true);
                    this.fConfiguration.setProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE, Constants.NS_XMLSCHEMA);
                    str2 = Constants.NS_XMLSCHEMA;
                } else {
                    if (!array.equals(Constants.NS_DTD)) {
                        return;
                    }
                    this.fConfiguration.setFeature(XMLSCHEMA, false);
                    this.fConfiguration.setFeature(XMLSCHEMA_FULL_CHECKING, false);
                    this.fConfiguration.setProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE, Constants.NS_DTD);
                    str2 = Constants.NS_DTD;
                }
                this.fSchemaType = str2;
                return;
            } catch (XMLConfigurationException unused3) {
                return;
            }
        }
        boolean zBooleanValue = ((Boolean) array).booleanValue();
        try {
            if (str.equalsIgnoreCase(Constants.DOM_COMMENTS)) {
                this.fConfiguration.setFeature("http://apache.org/xml/features/include-comments", zBooleanValue);
                return;
            }
            if (str.equalsIgnoreCase(Constants.DOM_DATATYPE_NORMALIZATION)) {
                this.fConfiguration.setFeature(NORMALIZE_DATA, zBooleanValue);
                return;
            }
            if (str.equalsIgnoreCase(Constants.DOM_ENTITIES)) {
                this.fConfiguration.setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes", zBooleanValue);
                return;
            }
            if (str.equalsIgnoreCase(Constants.DOM_DISALLOW_DOCTYPE)) {
                this.fConfiguration.setFeature(DISALLOW_DOCTYPE_DECL_FEATURE, zBooleanValue);
                return;
            }
            if (!str.equalsIgnoreCase(Constants.DOM_SUPPORTED_MEDIATYPES_ONLY) && !str.equalsIgnoreCase(Constants.DOM_NORMALIZE_CHARACTERS) && !str.equalsIgnoreCase(Constants.DOM_CHECK_CHAR_NORMALIZATION) && !str.equalsIgnoreCase(Constants.DOM_CANONICAL_FORM)) {
                if (str.equalsIgnoreCase("namespaces")) {
                    this.fConfiguration.setFeature(NAMESPACES, zBooleanValue);
                    return;
                }
                if (str.equalsIgnoreCase(Constants.DOM_INFOSET)) {
                    if (zBooleanValue) {
                        this.fConfiguration.setFeature(NAMESPACES, true);
                        this.fConfiguration.setFeature(Constants.DOM_NAMESPACE_DECLARATIONS, true);
                        this.fConfiguration.setFeature("http://apache.org/xml/features/include-comments", true);
                        this.fConfiguration.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", true);
                        this.fConfiguration.setFeature(DYNAMIC_VALIDATION, false);
                        this.fConfiguration.setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes", false);
                        this.fConfiguration.setFeature(NORMALIZE_DATA, false);
                        this.fConfiguration.setFeature("http://apache.org/xml/features/create-cdata-nodes", false);
                        return;
                    }
                    return;
                }
                if (str.equalsIgnoreCase(Constants.DOM_CDATA_SECTIONS)) {
                    this.fConfiguration.setFeature("http://apache.org/xml/features/create-cdata-nodes", zBooleanValue);
                    return;
                }
                if (str.equalsIgnoreCase(Constants.DOM_NAMESPACE_DECLARATIONS)) {
                    this.fConfiguration.setFeature(Constants.DOM_NAMESPACE_DECLARATIONS, zBooleanValue);
                    return;
                }
                if (!str.equalsIgnoreCase(Constants.DOM_WELLFORMED) && !str.equalsIgnoreCase(Constants.DOM_IGNORE_UNKNOWN_CHARACTER_DENORMALIZATIONS)) {
                    if (str.equalsIgnoreCase(Constants.DOM_VALIDATE)) {
                        this.fConfiguration.setFeature(VALIDATION_FEATURE, zBooleanValue);
                        if (this.fSchemaType != Constants.NS_DTD) {
                            this.fConfiguration.setFeature(XMLSCHEMA, zBooleanValue);
                            this.fConfiguration.setFeature(XMLSCHEMA_FULL_CHECKING, zBooleanValue);
                        }
                        if (zBooleanValue) {
                            this.fConfiguration.setFeature(DYNAMIC_VALIDATION, false);
                            return;
                        }
                        return;
                    }
                    if (str.equalsIgnoreCase(Constants.DOM_VALIDATE_IF_SCHEMA)) {
                        this.fConfiguration.setFeature(DYNAMIC_VALIDATION, zBooleanValue);
                        if (zBooleanValue) {
                            this.fConfiguration.setFeature(VALIDATION_FEATURE, false);
                            return;
                        }
                        return;
                    }
                    if (str.equalsIgnoreCase(Constants.DOM_ELEMENT_CONTENT_WHITESPACE)) {
                        this.fConfiguration.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", zBooleanValue);
                        return;
                    }
                    if (str.equalsIgnoreCase(Constants.DOM_PSVI)) {
                        this.fConfiguration.setFeature(PSVI_AUGMENT, true);
                        this.fConfiguration.setProperty("http://apache.org/xml/properties/dom/document-class-name", "org.apache.xerces.dom.PSVIDocumentImpl");
                        return;
                    }
                    if (str.equalsIgnoreCase(HONOUR_ALL_SCHEMALOCATIONS)) {
                        lowerCase = HONOUR_ALL_SCHEMALOCATIONS;
                    } else if (str.equals(NAMESPACE_GROWTH)) {
                        lowerCase = NAMESPACE_GROWTH;
                    } else {
                        lowerCase = TOLERATE_DUPLICATES;
                        if (!str.equals(lowerCase)) {
                            lowerCase = str.toLowerCase(Locale.ENGLISH);
                        }
                    }
                    this.fConfiguration.setFeature(lowerCase, zBooleanValue);
                    return;
                }
                if (!zBooleanValue) {
                    throw newFeatureNotSupportedError(str);
                }
                return;
            }
            if (zBooleanValue) {
                throw newFeatureNotSupportedError(str);
            }
        } catch (XMLConfigurationException unused4) {
            throw newFeatureNotFoundError(str);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractDOMParser, org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) {
        if (!this.fNamespaceDeclarations && this.fNamespaceAware) {
            for (int length = xMLAttributes.getLength() - 1; length >= 0; length--) {
                if (XMLSymbols.PREFIX_XMLNS == xMLAttributes.getPrefix(length) || XMLSymbols.PREFIX_XMLNS == xMLAttributes.getQName(length)) {
                    xMLAttributes.removeAttributeAt(length);
                }
            }
        }
        super.startElement(qName, xMLAttributes, augmentations);
    }
}
