package org.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.util.Enumeration;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import org.apache.xerces.dom.NodeImpl;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.validation.EntityState;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.impl.xs.util.SimpleLocator;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes13.dex */
final class DOMValidatorHelper implements ValidatorHelper, EntityState {
    private static final int CHUNK_MASK = 1023;
    private static final int CHUNK_SIZE = 1024;
    private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
    private static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
    private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
    private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
    private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
    private final XMLSchemaValidatorComponentManager fComponentManager;
    private Node fCurrentElement;
    private DOMDocumentHandler fDOMValidatorHandler;
    private final XMLErrorReporter fErrorReporter;
    private final NamespaceSupport fNamespaceContext;
    private Node fRoot;
    private final XMLSchemaValidator fSchemaValidator;
    private final SymbolTable fSymbolTable;
    private final ValidationManager fValidationManager;
    private final DOMNamespaceContext fDOMNamespaceContext = new DOMNamespaceContext();
    private final SimpleLocator fXMLLocator = new SimpleLocator(null, null, -1, -1, -1);
    private final DOMResultAugmentor fDOMResultAugmentor = new DOMResultAugmentor(this);
    private final DOMResultBuilder fDOMResultBuilder = new DOMResultBuilder();
    private NamedNodeMap fEntities = null;
    private final char[] fCharBuffer = new char[1024];
    final QName fElementQName = new QName();
    final QName fAttributeQName = new QName();
    final XMLAttributesImpl fAttributes = new XMLAttributesImpl();
    final XMLString fTempString = new XMLString();

    final class DOMNamespaceContext implements NamespaceContext {
        protected String[] fNamespace = new String[32];
        protected int fNamespaceSize = 0;
        protected boolean fDOMContextBuilt = false;

        DOMNamespaceContext() {
        }

        private void declarePrefix0(String str, String str2) {
            int i = this.fNamespaceSize;
            String[] strArr = this.fNamespace;
            if (i == strArr.length) {
                String[] strArr2 = new String[i * 2];
                System.arraycopy(strArr, 0, strArr2, 0, i);
                this.fNamespace = strArr2;
            }
            String[] strArr3 = this.fNamespace;
            int i2 = this.fNamespaceSize;
            int i3 = i2 + 1;
            this.fNamespaceSize = i3;
            strArr3[i2] = str;
            this.fNamespaceSize = i2 + 2;
            strArr3[i3] = str2;
        }

        /* JADX WARN: Code duplicated, block: B:23:0x0065 A[PHI: r4
          0x0065: PHI (r4v9 java.lang.String) = (r4v8 java.lang.String), (r4v13 java.lang.String) binds: [B:22:0x0063, B:19:0x005a] A[DONT_GENERATE, DONT_INLINE]] */
        private void fillNamespaceContext() {
            String str;
            if (DOMValidatorHelper.this.fRoot == null) {
                return;
            }
            Node parentNode = DOMValidatorHelper.this.fRoot;
            while (true) {
                parentNode = parentNode.getParentNode();
                if (parentNode == null) {
                    return;
                }
                if (1 == parentNode.getNodeType()) {
                    NamedNodeMap attributes = parentNode.getAttributes();
                    int length = attributes.getLength();
                    for (int i = 0; i < length; i++) {
                        Attr attr = (Attr) attributes.item(i);
                        String value = attr.getValue();
                        if (value == null) {
                            value = XMLSymbols.EMPTY_STRING;
                        }
                        DOMValidatorHelper dOMValidatorHelper = DOMValidatorHelper.this;
                        dOMValidatorHelper.fillQName(dOMValidatorHelper.fAttributeQName, attr);
                        if (DOMValidatorHelper.this.fAttributeQName.uri == NamespaceContext.XMLNS_URI) {
                            String strAddSymbol = null;
                            if (DOMValidatorHelper.this.fAttributeQName.prefix == XMLSymbols.PREFIX_XMLNS) {
                                str = DOMValidatorHelper.this.fAttributeQName.localpart;
                                if (value.length() != 0) {
                                    strAddSymbol = DOMValidatorHelper.this.fSymbolTable.addSymbol(value);
                                }
                            } else {
                                str = XMLSymbols.EMPTY_STRING;
                                if (value.length() != 0) {
                                    strAddSymbol = DOMValidatorHelper.this.fSymbolTable.addSymbol(value);
                                }
                            }
                            declarePrefix0(str, strAddSymbol);
                        }
                    }
                }
            }
        }

        private String getURI0(String str) {
            for (int i = 0; i < this.fNamespaceSize; i += 2) {
                String[] strArr = this.fNamespace;
                if (strArr[i] == str) {
                    return strArr[i + 1];
                }
            }
            return null;
        }

        @Override // org.apache.xerces.xni.NamespaceContext
        public boolean declarePrefix(String str, String str2) {
            return DOMValidatorHelper.this.fNamespaceContext.declarePrefix(str, str2);
        }

        @Override // org.apache.xerces.xni.NamespaceContext
        public Enumeration getAllPrefixes() {
            return DOMValidatorHelper.this.fNamespaceContext.getAllPrefixes();
        }

        @Override // org.apache.xerces.xni.NamespaceContext
        public String getDeclaredPrefixAt(int i) {
            return DOMValidatorHelper.this.fNamespaceContext.getDeclaredPrefixAt(i);
        }

        @Override // org.apache.xerces.xni.NamespaceContext
        public int getDeclaredPrefixCount() {
            return DOMValidatorHelper.this.fNamespaceContext.getDeclaredPrefixCount();
        }

        @Override // org.apache.xerces.xni.NamespaceContext
        public String getPrefix(String str) {
            return DOMValidatorHelper.this.fNamespaceContext.getPrefix(str);
        }

        @Override // org.apache.xerces.xni.NamespaceContext
        public String getURI(String str) {
            String uri = DOMValidatorHelper.this.fNamespaceContext.getURI(str);
            if (uri != null) {
                return uri;
            }
            if (!this.fDOMContextBuilt) {
                fillNamespaceContext();
                this.fDOMContextBuilt = true;
            }
            return (this.fNamespaceSize <= 0 || DOMValidatorHelper.this.fNamespaceContext.containsPrefix(str)) ? uri : getURI0(str);
        }

        @Override // org.apache.xerces.xni.NamespaceContext
        public void popContext() {
            DOMValidatorHelper.this.fNamespaceContext.popContext();
        }

        @Override // org.apache.xerces.xni.NamespaceContext
        public void pushContext() {
            DOMValidatorHelper.this.fNamespaceContext.pushContext();
        }

        @Override // org.apache.xerces.xni.NamespaceContext
        public void reset() {
            this.fDOMContextBuilt = false;
            this.fNamespaceSize = 0;
        }
    }

    public DOMValidatorHelper(XMLSchemaValidatorComponentManager xMLSchemaValidatorComponentManager) {
        this.fComponentManager = xMLSchemaValidatorComponentManager;
        this.fErrorReporter = (XMLErrorReporter) xMLSchemaValidatorComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
        this.fNamespaceContext = (NamespaceSupport) xMLSchemaValidatorComponentManager.getProperty(NAMESPACE_CONTEXT);
        this.fSchemaValidator = (XMLSchemaValidator) xMLSchemaValidatorComponentManager.getProperty(SCHEMA_VALIDATOR);
        this.fSymbolTable = (SymbolTable) xMLSchemaValidatorComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
        this.fValidationManager = (ValidationManager) xMLSchemaValidatorComponentManager.getProperty(VALIDATION_MANAGER);
    }

    private void beginNode(Node node) {
        DOMDocumentHandler dOMDocumentHandler;
        short nodeType = node.getNodeType();
        if (nodeType == 1) {
            this.fCurrentElement = node;
            this.fNamespaceContext.pushContext();
            fillQName(this.fElementQName, node);
            processAttributes(node.getAttributes());
            this.fSchemaValidator.startElement(this.fElementQName, this.fAttributes, null);
            return;
        }
        if (nodeType == 10) {
            DOMDocumentHandler dOMDocumentHandler2 = this.fDOMValidatorHandler;
            if (dOMDocumentHandler2 != null) {
                dOMDocumentHandler2.doctypeDecl((DocumentType) node);
                return;
            }
            return;
        }
        if (nodeType == 3) {
            DOMDocumentHandler dOMDocumentHandler3 = this.fDOMValidatorHandler;
            if (dOMDocumentHandler3 == null) {
                sendCharactersToValidator(node.getNodeValue());
                return;
            }
            dOMDocumentHandler3.setIgnoringCharacters(true);
            sendCharactersToValidator(node.getNodeValue());
            this.fDOMValidatorHandler.setIgnoringCharacters(false);
            this.fDOMValidatorHandler.characters((Text) node);
            return;
        }
        if (nodeType != 4) {
            if (nodeType != 7) {
                if (nodeType == 8 && (dOMDocumentHandler = this.fDOMValidatorHandler) != null) {
                    dOMDocumentHandler.comment((Comment) node);
                    return;
                }
                return;
            }
            DOMDocumentHandler dOMDocumentHandler4 = this.fDOMValidatorHandler;
            if (dOMDocumentHandler4 != null) {
                dOMDocumentHandler4.processingInstruction((ProcessingInstruction) node);
                return;
            }
            return;
        }
        DOMDocumentHandler dOMDocumentHandler5 = this.fDOMValidatorHandler;
        if (dOMDocumentHandler5 == null) {
            this.fSchemaValidator.startCDATA(null);
            sendCharactersToValidator(node.getNodeValue());
            this.fSchemaValidator.endCDATA(null);
        } else {
            dOMDocumentHandler5.setIgnoringCharacters(true);
            this.fSchemaValidator.startCDATA(null);
            sendCharactersToValidator(node.getNodeValue());
            this.fSchemaValidator.endCDATA(null);
            this.fDOMValidatorHandler.setIgnoringCharacters(false);
            this.fDOMValidatorHandler.cdata((CDATASection) node);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillQName(QName qName, Node node) {
        String prefix = node.getPrefix();
        String localName = node.getLocalName();
        String nodeName = node.getNodeName();
        String namespaceURI = node.getNamespaceURI();
        qName.prefix = prefix != null ? this.fSymbolTable.addSymbol(prefix) : XMLSymbols.EMPTY_STRING;
        qName.localpart = localName != null ? this.fSymbolTable.addSymbol(localName) : XMLSymbols.EMPTY_STRING;
        qName.rawname = nodeName != null ? this.fSymbolTable.addSymbol(nodeName) : XMLSymbols.EMPTY_STRING;
        qName.uri = (namespaceURI == null || namespaceURI.length() <= 0) ? null : this.fSymbolTable.addSymbol(namespaceURI);
    }

    private void finishNode(Node node) {
        if (node.getNodeType() == 1) {
            this.fCurrentElement = node;
            fillQName(this.fElementQName, node);
            this.fSchemaValidator.endElement(this.fElementQName, null);
            this.fNamespaceContext.popContext();
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0059 A[PHI: r2 r4
      0x0059: PHI (r2v8 org.apache.xerces.util.NamespaceSupport) = (r2v7 org.apache.xerces.util.NamespaceSupport), (r2v10 org.apache.xerces.util.NamespaceSupport) binds: [B:15:0x0057, B:12:0x004c] A[DONT_GENERATE, DONT_INLINE]
      0x0059: PHI (r4v6 java.lang.String) = (r4v5 java.lang.String), (r4v9 java.lang.String) binds: [B:15:0x0057, B:12:0x004c] A[DONT_GENERATE, DONT_INLINE]] */
    private void processAttributes(NamedNodeMap namedNodeMap) {
        NamespaceSupport namespaceSupport;
        String str;
        int length = namedNodeMap.getLength();
        this.fAttributes.removeAllAttributes();
        for (int i = 0; i < length; i++) {
            Attr attr = (Attr) namedNodeMap.item(i);
            String value = attr.getValue();
            if (value == null) {
                value = XMLSymbols.EMPTY_STRING;
            }
            fillQName(this.fAttributeQName, attr);
            this.fAttributes.addAttributeNS(this.fAttributeQName, XMLSymbols.fCDATASymbol, value);
            this.fAttributes.setSpecified(i, attr.getSpecified());
            if (this.fAttributeQName.uri == NamespaceContext.XMLNS_URI) {
                String strAddSymbol = null;
                if (this.fAttributeQName.prefix == XMLSymbols.PREFIX_XMLNS) {
                    namespaceSupport = this.fNamespaceContext;
                    str = this.fAttributeQName.localpart;
                    if (value.length() != 0) {
                        strAddSymbol = this.fSymbolTable.addSymbol(value);
                    }
                } else {
                    namespaceSupport = this.fNamespaceContext;
                    str = XMLSymbols.EMPTY_STRING;
                    if (value.length() != 0) {
                        strAddSymbol = this.fSymbolTable.addSymbol(value);
                    }
                }
                namespaceSupport.declarePrefix(str, strAddSymbol);
            }
        }
    }

    private void sendCharactersToValidator(String str) {
        if (str != null) {
            int length = str.length();
            int i = length & CHUNK_MASK;
            if (i > 0) {
                str.getChars(0, i, this.fCharBuffer, 0);
                this.fTempString.setValues(this.fCharBuffer, 0, i);
                this.fSchemaValidator.characters(this.fTempString, null);
            }
            while (i < length) {
                int i2 = i + 1024;
                str.getChars(i, i2, this.fCharBuffer, 0);
                this.fTempString.setValues(this.fCharBuffer, 0, 1024);
                this.fSchemaValidator.characters(this.fTempString, null);
                i = i2;
            }
        }
    }

    private void setupDOMResultHandler(DOMSource dOMSource, DOMResult dOMResult) throws SAXException {
        if (dOMResult == null) {
            this.fDOMValidatorHandler = null;
            this.fSchemaValidator.setDocumentHandler(null);
            return;
        }
        if (dOMSource.getNode() == dOMResult.getNode()) {
            DOMResultAugmentor dOMResultAugmentor = this.fDOMResultAugmentor;
            this.fDOMValidatorHandler = dOMResultAugmentor;
            dOMResultAugmentor.setDOMResult(dOMResult);
            this.fSchemaValidator.setDocumentHandler(this.fDOMResultAugmentor);
            return;
        }
        if (dOMResult.getNode() == null) {
            try {
                DocumentBuilderFactory documentBuilderFactoryNewInstance = DocumentBuilderFactory.newInstance();
                documentBuilderFactoryNewInstance.setNamespaceAware(true);
                dOMResult.setNode(documentBuilderFactoryNewInstance.newDocumentBuilder().newDocument());
            } catch (ParserConfigurationException e) {
                throw new SAXException(e);
            }
        }
        DOMResultBuilder dOMResultBuilder = this.fDOMResultBuilder;
        this.fDOMValidatorHandler = dOMResultBuilder;
        dOMResultBuilder.setDOMResult(dOMResult);
        this.fSchemaValidator.setDocumentHandler(this.fDOMResultBuilder);
    }

    private void setupEntityMap(Document document) {
        DocumentType doctype;
        this.fEntities = (document == null || (doctype = document.getDoctype()) == null) ? null : doctype.getEntities();
    }

    private boolean useIsSameNode(Node node) {
        if (node instanceof NodeImpl) {
            return false;
        }
        Document ownerDocument = node.getNodeType() == 9 ? (Document) node : node.getOwnerDocument();
        return ownerDocument != null && ownerDocument.getImplementation().hasFeature("Core", "3.0");
    }

    private void validate(Node node) {
        boolean zUseIsSameNode = useIsSameNode(node);
        Node parentNode = node;
        while (parentNode != null) {
            beginNode(parentNode);
            Node firstChild = parentNode.getFirstChild();
            while (true) {
                if (firstChild == null) {
                    finishNode(parentNode);
                    if (node != parentNode) {
                        firstChild = parentNode.getNextSibling();
                        if (firstChild == null) {
                            parentNode = parentNode.getParentNode();
                            if (parentNode != null) {
                                if (zUseIsSameNode) {
                                    if (node.isSameNode(parentNode)) {
                                    }
                                } else if (node == parentNode) {
                                }
                            }
                            if (parentNode != null) {
                                finishNode(parentNode);
                            }
                            parentNode = null;
                        }
                    }
                }
                parentNode = firstChild;
            }
        }
    }

    Node getCurrentElement() {
        return this.fCurrentElement;
    }

    @Override // org.apache.xerces.impl.validation.EntityState
    public boolean isEntityDeclared(String str) {
        return false;
    }

    @Override // org.apache.xerces.impl.validation.EntityState
    public boolean isEntityUnparsed(String str) {
        Entity entity;
        NamedNodeMap namedNodeMap = this.fEntities;
        return (namedNodeMap == null || (entity = (Entity) namedNodeMap.getNamedItem(str)) == null || entity.getNotationName() == null) ? false : true;
    }

    @Override // org.apache.xerces.jaxp.validation.ValidatorHelper
    public void validate(Source source, Result result) throws SAXException, IOException {
        if (!(result instanceof DOMResult) && result != null) {
            throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceResultMismatch", new Object[]{source.getClass().getName(), result.getClass().getName()}));
        }
        DOMSource dOMSource = (DOMSource) source;
        DOMResult dOMResult = (DOMResult) result;
        Node node = dOMSource.getNode();
        this.fRoot = node;
        if (node != null) {
            this.fComponentManager.reset();
            this.fValidationManager.setEntityState(this);
            this.fDOMNamespaceContext.reset();
            String systemId = dOMSource.getSystemId();
            this.fXMLLocator.setLiteralSystemId(systemId);
            this.fXMLLocator.setExpandedSystemId(systemId);
            this.fErrorReporter.setDocumentLocator(this.fXMLLocator);
            try {
                try {
                    setupEntityMap(node.getNodeType() == 9 ? (Document) node : node.getOwnerDocument());
                    setupDOMResultHandler(dOMSource, dOMResult);
                    this.fSchemaValidator.startDocument(this.fXMLLocator, null, this.fDOMNamespaceContext, null);
                    validate(node);
                    this.fSchemaValidator.endDocument(null);
                    this.fRoot = null;
                    this.fCurrentElement = null;
                    this.fEntities = null;
                    DOMDocumentHandler dOMDocumentHandler = this.fDOMValidatorHandler;
                    if (dOMDocumentHandler != null) {
                        dOMDocumentHandler.setDOMResult(null);
                    }
                } catch (XMLParseException e) {
                    throw Util.toSAXParseException(e);
                } catch (XNIException e2) {
                    throw Util.toSAXException(e2);
                }
            } catch (Throwable th) {
                this.fRoot = null;
                this.fCurrentElement = null;
                this.fEntities = null;
                DOMDocumentHandler dOMDocumentHandler2 = this.fDOMValidatorHandler;
                if (dOMDocumentHandler2 != null) {
                    dOMDocumentHandler2.setDOMResult(null);
                }
                throw th;
            }
        }
    }
}
