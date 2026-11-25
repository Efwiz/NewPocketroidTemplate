package org.apache.xerces.parsers;

import com.reandroid.apk.ApkUtil;
import io.netty.util.internal.StringUtil;
import java.util.Locale;
import java.util.Stack;
import javax.xml.XMLConstants;
import org.apache.xerces.dom.AttrImpl;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.DOMErrorImpl;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.dom.DeferredDocumentImpl;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.DocumentTypeImpl;
import org.apache.xerces.dom.ElementDefinitionImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.ElementNSImpl;
import org.apache.xerces.dom.EntityImpl;
import org.apache.xerces.dom.EntityReferenceImpl;
import org.apache.xerces.dom.NodeImpl;
import org.apache.xerces.dom.NotationImpl;
import org.apache.xerces.dom.PSVIAttrNSImpl;
import org.apache.xerces.dom.PSVIDocumentImpl;
import org.apache.xerces.dom.PSVIElementNSImpl;
import org.apache.xerces.dom.TextImpl;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.util.DOMErrorHandlerWrapper;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLParserConfiguration;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.ls.LSParserFilter;

/* JADX INFO: loaded from: classes13.dex */
public class AbstractDOMParser extends AbstractXMLDocumentParser {
    protected static final String CORE_DOCUMENT_CLASS_NAME = "org.apache.xerces.dom.CoreDocumentImpl";
    private static final boolean DEBUG_BASEURI = false;
    private static final boolean DEBUG_EVENTS = false;
    protected static final String DEFAULT_DOCUMENT_CLASS_NAME = "org.apache.xerces.dom.DocumentImpl";
    protected static final String PSVI_DOCUMENT_CLASS_NAME = "org.apache.xerces.dom.PSVIDocumentImpl";
    private final QName fAttrQName;
    protected final Stack fBaseURIStack;
    protected boolean fCreateCDATANodes;
    protected boolean fCreateEntityRefNodes;
    protected CDATASection fCurrentCDATASection;
    protected int fCurrentCDATASectionIndex;
    protected EntityImpl fCurrentEntityDecl;
    protected Node fCurrentNode;
    protected int fCurrentNodeIndex;
    protected LSParserFilter fDOMFilter;
    protected boolean fDeferNodeExpansion;
    protected DeferredDocumentImpl fDeferredDocumentImpl;
    protected int fDeferredEntityDecl;
    protected Document fDocument;
    protected String fDocumentClassName;
    protected CoreDocumentImpl fDocumentImpl;
    protected int fDocumentIndex;
    protected DocumentType fDocumentType;
    protected int fDocumentTypeIndex;
    protected DOMErrorHandlerWrapper fErrorHandler;
    protected boolean fFilterReject;
    protected boolean fFirstChunk;
    protected boolean fInCDATASection;
    protected boolean fInDTD;
    protected boolean fInDTDExternalSubset;
    protected boolean fInEntityRef;
    protected boolean fIncludeComments;
    protected boolean fIncludeIgnorableWhitespace;
    protected StringBuffer fInternalSubset;
    private XMLLocator fLocator;
    protected boolean fNamespaceAware;
    protected int fRejectedElementDepth;
    protected Node fRoot;
    protected Stack fSkippedElemStack;
    protected boolean fStorePSVI;
    protected final StringBuffer fStringBuffer;
    protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    protected static final String CREATE_ENTITY_REF_NODES = "http://apache.org/xml/features/dom/create-entity-ref-nodes";
    protected static final String INCLUDE_COMMENTS_FEATURE = "http://apache.org/xml/features/include-comments";
    protected static final String CREATE_CDATA_NODES_FEATURE = "http://apache.org/xml/features/create-cdata-nodes";
    protected static final String INCLUDE_IGNORABLE_WHITESPACE = "http://apache.org/xml/features/dom/include-ignorable-whitespace";
    protected static final String DEFER_NODE_EXPANSION = "http://apache.org/xml/features/dom/defer-node-expansion";
    private static final String[] RECOGNIZED_FEATURES = {NAMESPACES, CREATE_ENTITY_REF_NODES, INCLUDE_COMMENTS_FEATURE, CREATE_CDATA_NODES_FEATURE, INCLUDE_IGNORABLE_WHITESPACE, DEFER_NODE_EXPANSION};
    protected static final String DOCUMENT_CLASS_NAME = "http://apache.org/xml/properties/dom/document-class-name";
    protected static final String CURRENT_ELEMENT_NODE = "http://apache.org/xml/properties/dom/current-element-node";
    private static final String[] RECOGNIZED_PROPERTIES = {DOCUMENT_CLASS_NAME, CURRENT_ELEMENT_NODE};

    static final class Abort extends RuntimeException {
        static final Abort INSTANCE = new Abort();
        private static final long serialVersionUID = 1687848994976808490L;

        private Abort() {
        }

        @Override // java.lang.Throwable
        public Throwable fillInStackTrace() {
            return this;
        }
    }

    protected AbstractDOMParser(XMLParserConfiguration xMLParserConfiguration) {
        super(xMLParserConfiguration);
        this.fErrorHandler = null;
        this.fStringBuffer = new StringBuffer(50);
        this.fFirstChunk = false;
        this.fFilterReject = false;
        this.fBaseURIStack = new Stack();
        this.fRejectedElementDepth = 0;
        this.fSkippedElemStack = null;
        this.fInEntityRef = false;
        this.fAttrQName = new QName();
        this.fDOMFilter = null;
        this.fConfiguration.addRecognizedFeatures(RECOGNIZED_FEATURES);
        this.fConfiguration.setFeature(CREATE_ENTITY_REF_NODES, true);
        this.fConfiguration.setFeature(INCLUDE_IGNORABLE_WHITESPACE, true);
        this.fConfiguration.setFeature(DEFER_NODE_EXPANSION, true);
        this.fConfiguration.setFeature(INCLUDE_COMMENTS_FEATURE, true);
        this.fConfiguration.setFeature(CREATE_CDATA_NODES_FEATURE, true);
        this.fConfiguration.addRecognizedProperties(RECOGNIZED_PROPERTIES);
        this.fConfiguration.setProperty(DOCUMENT_CLASS_NAME, DEFAULT_DOCUMENT_CLASS_NAME);
    }

    public void abort() {
        throw Abort.INSTANCE;
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void attributeDecl(String str, String str2, String str3, String[] strArr, String str4, XMLString xMLString, XMLString xMLString2, Augmentations augmentations) throws XNIException {
        Attr attrCreateAttribute;
        StringBuffer stringBuffer = this.fInternalSubset;
        if (stringBuffer != null && !this.fInDTDExternalSubset) {
            stringBuffer.append("<!ATTLIST ");
            this.fInternalSubset.append(str);
            this.fInternalSubset.append(StringUtil.SPACE);
            this.fInternalSubset.append(str2);
            this.fInternalSubset.append(StringUtil.SPACE);
            if (str3.equals("ENUMERATION")) {
                this.fInternalSubset.append('(');
                for (int i = 0; i < strArr.length; i++) {
                    if (i > 0) {
                        this.fInternalSubset.append('|');
                    }
                    this.fInternalSubset.append(strArr[i]);
                }
                this.fInternalSubset.append(')');
            } else {
                this.fInternalSubset.append(str3);
            }
            if (str4 != null) {
                this.fInternalSubset.append(StringUtil.SPACE);
                this.fInternalSubset.append(str4);
            }
            if (xMLString != null) {
                this.fInternalSubset.append(" '");
                for (int i2 = 0; i2 < xMLString.length; i2++) {
                    char c = xMLString.ch[xMLString.offset + i2];
                    if (c == '\'') {
                        this.fInternalSubset.append("&apos;");
                    } else {
                        this.fInternalSubset.append(c);
                    }
                }
                this.fInternalSubset.append('\'');
            }
            this.fInternalSubset.append(">\n");
        }
        DeferredDocumentImpl deferredDocumentImpl = this.fDeferredDocumentImpl;
        String str5 = null;
        if (deferredDocumentImpl != null) {
            if (xMLString != null) {
                int iLookupElementDefinition = deferredDocumentImpl.lookupElementDefinition(str);
                if (iLookupElementDefinition == -1) {
                    iLookupElementDefinition = this.fDeferredDocumentImpl.createDeferredElementDefinition(str);
                    this.fDeferredDocumentImpl.appendChild(this.fDocumentTypeIndex, iLookupElementDefinition);
                }
                if (this.fNamespaceAware) {
                    if (str2.startsWith("xmlns:") || str2.equals(XMLConstants.XMLNS_ATTRIBUTE)) {
                        str5 = NamespaceContext.XMLNS_URI;
                    } else if (str2.startsWith("xml:")) {
                        str5 = NamespaceContext.XML_URI;
                    }
                }
                int iCreateDeferredAttribute = this.fDeferredDocumentImpl.createDeferredAttribute(str2, str5, xMLString.toString(), false);
                if (SchemaSymbols.ATTVAL_ID.equals(str3)) {
                    this.fDeferredDocumentImpl.setIdAttribute(iCreateDeferredAttribute);
                }
                this.fDeferredDocumentImpl.appendChild(iLookupElementDefinition, iCreateDeferredAttribute);
                return;
            }
            return;
        }
        if (this.fDocumentImpl == null || xMLString == null) {
            return;
        }
        ElementDefinitionImpl elementDefinitionImplCreateElementDefinition = (ElementDefinitionImpl) ((DocumentTypeImpl) this.fDocumentType).getElements().getNamedItem(str);
        if (elementDefinitionImplCreateElementDefinition == null) {
            elementDefinitionImplCreateElementDefinition = this.fDocumentImpl.createElementDefinition(str);
            ((DocumentTypeImpl) this.fDocumentType).getElements().setNamedItem(elementDefinitionImplCreateElementDefinition);
        }
        boolean z = this.fNamespaceAware;
        if (z) {
            if (str2.startsWith("xmlns:") || str2.equals(XMLConstants.XMLNS_ATTRIBUTE)) {
                str5 = NamespaceContext.XMLNS_URI;
            } else if (str2.startsWith("xml:")) {
                str5 = NamespaceContext.XML_URI;
            }
            attrCreateAttribute = this.fDocumentImpl.createAttributeNS(str5, str2);
        } else {
            attrCreateAttribute = this.fDocumentImpl.createAttribute(str2);
        }
        AttrImpl attrImpl = (AttrImpl) attrCreateAttribute;
        attrImpl.setValue(xMLString.toString());
        attrImpl.setSpecified(false);
        attrImpl.setIdAttribute(SchemaSymbols.ATTVAL_ID.equals(str3));
        if (z) {
            elementDefinitionImplCreateElementDefinition.getAttributes().setNamedItemNS(attrImpl);
        } else {
            elementDefinitionImplCreateElementDefinition.getAttributes().setNamedItem(attrImpl);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
        String string;
        DeferredDocumentImpl deferredDocumentImpl;
        if (this.fDeferNodeExpansion) {
            if (this.fInCDATASection && this.fCreateCDATANodes) {
                if (this.fCurrentCDATASectionIndex == -1) {
                    int iCreateDeferredCDATASection = this.fDeferredDocumentImpl.createDeferredCDATASection(xMLString.toString());
                    this.fDeferredDocumentImpl.appendChild(this.fCurrentNodeIndex, iCreateDeferredCDATASection);
                    this.fCurrentCDATASectionIndex = iCreateDeferredCDATASection;
                    this.fCurrentNodeIndex = iCreateDeferredCDATASection;
                    return;
                }
                deferredDocumentImpl = this.fDeferredDocumentImpl;
                string = xMLString.toString();
            } else {
                if (this.fInDTD || xMLString.length == 0) {
                    return;
                }
                string = xMLString.toString();
                deferredDocumentImpl = this.fDeferredDocumentImpl;
            }
            this.fDeferredDocumentImpl.appendChild(this.fCurrentNodeIndex, deferredDocumentImpl.createDeferredTextNode(string, false));
            return;
        }
        if (this.fFilterReject) {
            return;
        }
        if (this.fInCDATASection && this.fCreateCDATANodes) {
            CDATASection cDATASection = this.fCurrentCDATASection;
            if (cDATASection != null) {
                cDATASection.appendData(xMLString.toString());
                return;
            }
            CDATASection cDATASectionCreateCDATASection = this.fDocument.createCDATASection(xMLString.toString());
            this.fCurrentCDATASection = cDATASectionCreateCDATASection;
            this.fCurrentNode.appendChild(cDATASectionCreateCDATASection);
            this.fCurrentNode = this.fCurrentCDATASection;
            return;
        }
        if (this.fInDTD || xMLString.length == 0) {
            return;
        }
        Node lastChild = this.fCurrentNode.getLastChild();
        if (lastChild == null || lastChild.getNodeType() != 3) {
            this.fFirstChunk = true;
            this.fCurrentNode.appendChild(this.fDocument.createTextNode(xMLString.toString()));
            return;
        }
        if (this.fFirstChunk) {
            if (this.fDocumentImpl != null) {
                this.fStringBuffer.append(((TextImpl) lastChild).removeData());
            } else {
                Text text = (Text) lastChild;
                this.fStringBuffer.append(text.getData());
                text.setNodeValue(null);
            }
            this.fFirstChunk = false;
        }
        if (xMLString.length > 0) {
            this.fStringBuffer.append(xMLString.ch, xMLString.offset, xMLString.length);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fInDTD) {
            StringBuffer stringBuffer = this.fInternalSubset;
            if (stringBuffer == null || this.fInDTDExternalSubset) {
                return;
            }
            stringBuffer.append("<!--");
            if (xMLString.length > 0) {
                this.fInternalSubset.append(xMLString.ch, xMLString.offset, xMLString.length);
            }
            this.fInternalSubset.append("-->");
            return;
        }
        if (!this.fIncludeComments || this.fFilterReject) {
            return;
        }
        if (this.fDeferNodeExpansion) {
            this.fDeferredDocumentImpl.appendChild(this.fCurrentNodeIndex, this.fDeferredDocumentImpl.createDeferredComment(xMLString.toString()));
            return;
        }
        Comment commentCreateComment = this.fDocument.createComment(xMLString.toString());
        setCharacterData(false);
        this.fCurrentNode.appendChild(commentCreateComment);
        LSParserFilter lSParserFilter = this.fDOMFilter;
        if (lSParserFilter == null || this.fInEntityRef || (lSParserFilter.getWhatToShow() & 128) == 0) {
            return;
        }
        short sAcceptNode = this.fDOMFilter.acceptNode(commentCreateComment);
        if (sAcceptNode == 2 || sAcceptNode == 3) {
            this.fCurrentNode.removeChild(commentCreateComment);
            this.fFirstChunk = true;
        } else if (sAcceptNode == 4) {
            throw Abort.INSTANCE;
        }
    }

    protected Attr createAttrNode(QName qName) {
        if (!this.fNamespaceAware) {
            return this.fDocument.createAttribute(qName.rawname);
        }
        CoreDocumentImpl coreDocumentImpl = this.fDocumentImpl;
        return coreDocumentImpl != null ? coreDocumentImpl.createAttributeNS(qName.uri, qName.rawname, qName.localpart) : this.fDocument.createAttributeNS(qName.uri, qName.rawname);
    }

    protected Element createElementNode(QName qName) {
        if (!this.fNamespaceAware) {
            return this.fDocument.createElement(qName.rawname);
        }
        CoreDocumentImpl coreDocumentImpl = this.fDocumentImpl;
        return coreDocumentImpl != null ? coreDocumentImpl.createElementNS(qName.uri, qName.rawname, qName.localpart) : this.fDocument.createElementNS(qName.uri, qName.rawname);
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
        if (this.fDeferNodeExpansion) {
            int iCreateDeferredDocumentType = this.fDeferredDocumentImpl.createDeferredDocumentType(str, str2, str3);
            this.fDocumentTypeIndex = iCreateDeferredDocumentType;
            this.fDeferredDocumentImpl.appendChild(this.fCurrentNodeIndex, iCreateDeferredDocumentType);
        } else {
            CoreDocumentImpl coreDocumentImpl = this.fDocumentImpl;
            if (coreDocumentImpl != null) {
                DocumentType documentTypeCreateDocumentType = coreDocumentImpl.createDocumentType(str, str2, str3);
                this.fDocumentType = documentTypeCreateDocumentType;
                this.fCurrentNode.appendChild(documentTypeCreateDocumentType);
            }
        }
    }

    public final void dropDocumentReferences() {
        this.fDocument = null;
        this.fDocumentImpl = null;
        this.fDeferredDocumentImpl = null;
        this.fDocumentType = null;
        this.fCurrentNode = null;
        this.fCurrentCDATASection = null;
        this.fCurrentEntityDecl = null;
        this.fRoot = null;
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void elementDecl(String str, String str2, Augmentations augmentations) throws XNIException {
        StringBuffer stringBuffer = this.fInternalSubset;
        if (stringBuffer == null || this.fInDTDExternalSubset) {
            return;
        }
        stringBuffer.append("<!ELEMENT ");
        this.fInternalSubset.append(str);
        this.fInternalSubset.append(StringUtil.SPACE);
        this.fInternalSubset.append(str2);
        this.fInternalSubset.append(">\n");
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void emptyElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        startElement(qName, xMLAttributes, augmentations);
        endElement(qName, augmentations);
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void endAttlist(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void endCDATA(Augmentations augmentations) throws XNIException {
        this.fInCDATASection = false;
        if (this.fDeferNodeExpansion) {
            if (this.fCurrentCDATASectionIndex != -1) {
                this.fCurrentNodeIndex = this.fDeferredDocumentImpl.getParentNode(this.fCurrentNodeIndex, false);
                this.fCurrentCDATASectionIndex = -1;
                return;
            }
            return;
        }
        if (this.fFilterReject || this.fCurrentCDATASection == null) {
            return;
        }
        LSParserFilter lSParserFilter = this.fDOMFilter;
        if (lSParserFilter != null && !this.fInEntityRef && (lSParserFilter.getWhatToShow() & 8) != 0) {
            short sAcceptNode = this.fDOMFilter.acceptNode(this.fCurrentCDATASection);
            if (sAcceptNode == 2 || sAcceptNode == 3) {
                Node parentNode = this.fCurrentNode.getParentNode();
                parentNode.removeChild(this.fCurrentCDATASection);
                this.fCurrentNode = parentNode;
                return;
            } else if (sAcceptNode == 4) {
                throw Abort.INSTANCE;
            }
        }
        this.fCurrentNode = this.fCurrentNode.getParentNode();
        this.fCurrentCDATASection = null;
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void endConditional(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void endDTD(Augmentations augmentations) throws XNIException {
        this.fInDTD = false;
        if (!this.fBaseURIStack.isEmpty()) {
            this.fBaseURIStack.pop();
        }
        StringBuffer stringBuffer = this.fInternalSubset;
        String string = (stringBuffer == null || stringBuffer.length() <= 0) ? null : this.fInternalSubset.toString();
        if (this.fDeferNodeExpansion) {
            if (string != null) {
                this.fDeferredDocumentImpl.setInternalSubset(this.fDocumentTypeIndex, string);
            }
        } else {
            if (this.fDocumentImpl == null || string == null) {
                return;
            }
            ((DocumentTypeImpl) this.fDocumentType).setInternalSubset(string);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void endDocument(Augmentations augmentations) throws XNIException {
        if (this.fDeferNodeExpansion) {
            XMLLocator xMLLocator = this.fLocator;
            if (xMLLocator != null) {
                this.fDeferredDocumentImpl.setInputEncoding(xMLLocator.getEncoding());
            }
            this.fCurrentNodeIndex = -1;
            return;
        }
        CoreDocumentImpl coreDocumentImpl = this.fDocumentImpl;
        if (coreDocumentImpl != null) {
            XMLLocator xMLLocator2 = this.fLocator;
            if (xMLLocator2 != null) {
                coreDocumentImpl.setInputEncoding(xMLLocator2.getEncoding());
            }
            this.fDocumentImpl.setStrictErrorChecking(true);
        }
        this.fCurrentNode = null;
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(QName qName, Augmentations augmentations) throws XNIException {
        ElementPSVI elementPSVI;
        Node parentNode;
        ElementPSVI elementPSVI2;
        if (this.fDeferNodeExpansion) {
            if (augmentations != null && (elementPSVI = (ElementPSVI) augmentations.getItem(Constants.ELEMENT_PSVI)) != null) {
                XSTypeDefinition memberTypeDefinition = elementPSVI.getMemberTypeDefinition();
                if (memberTypeDefinition == null) {
                    memberTypeDefinition = elementPSVI.getTypeDefinition();
                }
                this.fDeferredDocumentImpl.setTypeInfo(this.fCurrentNodeIndex, memberTypeDefinition);
            }
            this.fCurrentNodeIndex = this.fDeferredDocumentImpl.getParentNode(this.fCurrentNodeIndex, false);
            return;
        }
        if (augmentations != null && this.fDocumentImpl != null && ((this.fNamespaceAware || this.fStorePSVI) && (elementPSVI2 = (ElementPSVI) augmentations.getItem(Constants.ELEMENT_PSVI)) != null)) {
            if (this.fNamespaceAware) {
                XSTypeDefinition memberTypeDefinition2 = elementPSVI2.getMemberTypeDefinition();
                if (memberTypeDefinition2 == null) {
                    memberTypeDefinition2 = elementPSVI2.getTypeDefinition();
                }
                ((ElementNSImpl) this.fCurrentNode).setType(memberTypeDefinition2);
            }
            if (this.fStorePSVI) {
                ((PSVIElementNSImpl) this.fCurrentNode).setPSVI(elementPSVI2);
            }
        }
        if (this.fDOMFilter == null) {
            setCharacterData(false);
        } else {
            if (this.fFilterReject) {
                int i = this.fRejectedElementDepth;
                this.fRejectedElementDepth = i - 1;
                if (i == 0) {
                    this.fFilterReject = false;
                    return;
                }
                return;
            }
            if (!this.fSkippedElemStack.isEmpty() && this.fSkippedElemStack.pop() == Boolean.TRUE) {
                return;
            }
            setCharacterData(false);
            if (this.fCurrentNode != this.fRoot && !this.fInEntityRef && (this.fDOMFilter.getWhatToShow() & 1) != 0) {
                short sAcceptNode = this.fDOMFilter.acceptNode(this.fCurrentNode);
                if (sAcceptNode == 2) {
                    parentNode = this.fCurrentNode.getParentNode();
                } else if (sAcceptNode == 3) {
                    this.fFirstChunk = true;
                    parentNode = this.fCurrentNode.getParentNode();
                    NodeList childNodes = this.fCurrentNode.getChildNodes();
                    int length = childNodes.getLength();
                    for (int i2 = 0; i2 < length; i2++) {
                        parentNode.appendChild(childNodes.item(0));
                    }
                } else if (sAcceptNode == 4) {
                    throw Abort.INSTANCE;
                }
                parentNode.removeChild(this.fCurrentNode);
                this.fCurrentNode = parentNode;
                return;
            }
        }
        this.fCurrentNode = this.fCurrentNode.getParentNode();
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void endExternalSubset(Augmentations augmentations) throws XNIException {
        this.fInDTDExternalSubset = false;
        this.fBaseURIStack.pop();
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void endGeneralEntity(String str, Augmentations augmentations) throws XNIException {
        boolean z;
        if (this.fDeferNodeExpansion) {
            int i = this.fDocumentTypeIndex;
            if (i != -1) {
                int lastChild = this.fDeferredDocumentImpl.getLastChild(i, false);
                while (lastChild != -1) {
                    if (this.fDeferredDocumentImpl.getNodeType(lastChild, false) == 6 && this.fDeferredDocumentImpl.getNodeName(lastChild, false).equals(str)) {
                        this.fDeferredEntityDecl = lastChild;
                        break;
                    }
                    lastChild = this.fDeferredDocumentImpl.getRealPrevSibling(lastChild, false);
                }
            }
            int i2 = this.fDeferredEntityDecl;
            if (i2 != -1 && this.fDeferredDocumentImpl.getLastChild(i2, false) == -1) {
                int lastChild2 = this.fDeferredDocumentImpl.getLastChild(this.fCurrentNodeIndex, false);
                int i3 = -1;
                while (lastChild2 != -1) {
                    int iCloneNode = this.fDeferredDocumentImpl.cloneNode(lastChild2, true);
                    this.fDeferredDocumentImpl.insertBefore(this.fDeferredEntityDecl, iCloneNode, i3);
                    lastChild2 = this.fDeferredDocumentImpl.getRealPrevSibling(lastChild2, false);
                    i3 = iCloneNode;
                }
            }
            if (this.fCreateEntityRefNodes) {
                this.fCurrentNodeIndex = this.fDeferredDocumentImpl.getParentNode(this.fCurrentNodeIndex, false);
            } else {
                int lastChild3 = this.fDeferredDocumentImpl.getLastChild(this.fCurrentNodeIndex, false);
                int parentNode = this.fDeferredDocumentImpl.getParentNode(this.fCurrentNodeIndex, false);
                int i4 = this.fCurrentNodeIndex;
                int i5 = lastChild3;
                while (i5 != -1) {
                    handleBaseURI(i5);
                    int realPrevSibling = this.fDeferredDocumentImpl.getRealPrevSibling(i5, false);
                    this.fDeferredDocumentImpl.insertBefore(parentNode, i5, i4);
                    i4 = i5;
                    i5 = realPrevSibling;
                }
                if (lastChild3 == -1) {
                    lastChild3 = this.fDeferredDocumentImpl.getRealPrevSibling(i4, false);
                }
                this.fDeferredDocumentImpl.setAsLastChild(parentNode, lastChild3);
                this.fCurrentNodeIndex = parentNode;
            }
            this.fDeferredEntityDecl = -1;
            return;
        }
        if (this.fFilterReject) {
            return;
        }
        setCharacterData(true);
        DocumentType documentType = this.fDocumentType;
        if (documentType != null) {
            EntityImpl entityImpl = (EntityImpl) documentType.getEntities().getNamedItem(str);
            this.fCurrentEntityDecl = entityImpl;
            if (entityImpl != null) {
                if (entityImpl != null && entityImpl.getFirstChild() == null) {
                    this.fCurrentEntityDecl.setReadOnly(false, true);
                    for (Node firstChild = this.fCurrentNode.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                        this.fCurrentEntityDecl.appendChild(firstChild.cloneNode(true));
                    }
                    this.fCurrentEntityDecl.setReadOnly(true, true);
                }
                this.fCurrentEntityDecl = null;
            }
        }
        this.fInEntityRef = false;
        if (this.fCreateEntityRefNodes) {
            if (this.fDocumentImpl != null) {
                ((NodeImpl) this.fCurrentNode).setReadOnly(true, true);
            }
            LSParserFilter lSParserFilter = this.fDOMFilter;
            if (lSParserFilter != null && (lSParserFilter.getWhatToShow() & 16) != 0) {
                short sAcceptNode = this.fDOMFilter.acceptNode(this.fCurrentNode);
                if (sAcceptNode == 2) {
                    Node parentNode2 = this.fCurrentNode.getParentNode();
                    parentNode2.removeChild(this.fCurrentNode);
                    this.fCurrentNode = parentNode2;
                    return;
                } else if (sAcceptNode == 3) {
                    this.fFirstChunk = true;
                    z = true;
                } else if (sAcceptNode == 4) {
                    throw Abort.INSTANCE;
                }
            }
            this.fCurrentNode = this.fCurrentNode.getParentNode();
            z = false;
        } else {
            z = false;
        }
        if (!this.fCreateEntityRefNodes || z) {
            NodeList childNodes = this.fCurrentNode.getChildNodes();
            Node parentNode3 = this.fCurrentNode.getParentNode();
            int length = childNodes.getLength();
            if (length > 0) {
                Node previousSibling = this.fCurrentNode.getPreviousSibling();
                Node nodeItem = childNodes.item(0);
                if (previousSibling != null && previousSibling.getNodeType() == 3 && nodeItem.getNodeType() == 3) {
                    ((Text) previousSibling).appendData(nodeItem.getNodeValue());
                    this.fCurrentNode.removeChild(nodeItem);
                } else {
                    handleBaseURI(parentNode3.insertBefore(nodeItem, this.fCurrentNode));
                }
                for (int i6 = 1; i6 < length; i6++) {
                    handleBaseURI(parentNode3.insertBefore(childNodes.item(0), this.fCurrentNode));
                }
            }
            parentNode3.removeChild(this.fCurrentNode);
            this.fCurrentNode = parentNode3;
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void endParameterEntity(String str, Augmentations augmentations) throws XNIException {
        this.fBaseURIStack.pop();
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void externalEntityDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        String str2;
        String publicId = xMLResourceIdentifier.getPublicId();
        String literalSystemId = xMLResourceIdentifier.getLiteralSystemId();
        StringBuffer stringBuffer = this.fInternalSubset;
        if (stringBuffer != null && !this.fInDTDExternalSubset) {
            stringBuffer.append("<!ENTITY ");
            if (str.startsWith("%")) {
                this.fInternalSubset.append("% ");
                this.fInternalSubset.append(str.substring(1));
            } else {
                this.fInternalSubset.append(str);
            }
            this.fInternalSubset.append(StringUtil.SPACE);
            StringBuffer stringBuffer2 = this.fInternalSubset;
            if (publicId != null) {
                stringBuffer2.append("PUBLIC '");
                this.fInternalSubset.append(publicId);
                stringBuffer2 = this.fInternalSubset;
                str2 = "' '";
            } else {
                str2 = "SYSTEM '";
            }
            stringBuffer2.append(str2);
            this.fInternalSubset.append(literalSystemId);
            this.fInternalSubset.append("'>\n");
        }
        if (str.startsWith("%")) {
            return;
        }
        DocumentType documentType = this.fDocumentType;
        if (documentType != null) {
            NamedNodeMap entities = documentType.getEntities();
            if (((EntityImpl) entities.getNamedItem(str)) == null) {
                EntityImpl entityImpl = (EntityImpl) this.fDocumentImpl.createEntity(str);
                entityImpl.setPublicId(publicId);
                entityImpl.setSystemId(literalSystemId);
                entityImpl.setBaseURI(xMLResourceIdentifier.getBaseSystemId());
                entities.setNamedItem(entityImpl);
            }
        }
        int i = this.fDocumentTypeIndex;
        if (i != -1) {
            int lastChild = this.fDeferredDocumentImpl.getLastChild(i, false);
            while (lastChild != -1) {
                if (this.fDeferredDocumentImpl.getNodeType(lastChild, false) == 6 && this.fDeferredDocumentImpl.getNodeName(lastChild, false).equals(str)) {
                    return;
                } else {
                    lastChild = this.fDeferredDocumentImpl.getRealPrevSibling(lastChild, false);
                }
            }
            this.fDeferredDocumentImpl.appendChild(this.fDocumentTypeIndex, this.fDeferredDocumentImpl.createDeferredEntity(str, publicId, literalSystemId, null, xMLResourceIdentifier.getBaseSystemId()));
        }
    }

    public Document getDocument() {
        return this.fDocument;
    }

    protected String getDocumentClassName() {
        return this.fDocumentClassName;
    }

    protected final void handleBaseURI(int i) {
        short nodeType = this.fDeferredDocumentImpl.getNodeType(i, false);
        if (nodeType == 1) {
            String nodeValueString = this.fDeferredDocumentImpl.getNodeValueString(this.fCurrentNodeIndex, false);
            if (nodeValueString == null) {
                nodeValueString = this.fDeferredDocumentImpl.getDeferredEntityBaseURI(this.fDeferredEntityDecl);
            }
            String str = nodeValueString;
            if (str == null || str.equals(this.fDeferredDocumentImpl.getDocumentURI())) {
                return;
            }
            this.fDeferredDocumentImpl.setDeferredAttribute(i, "xml:base", "http://www.w3.org/XML/1998/namespace", str, true);
            return;
        }
        if (nodeType == 7) {
            String nodeValueString2 = this.fDeferredDocumentImpl.getNodeValueString(this.fCurrentNodeIndex, false);
            if (nodeValueString2 == null) {
                nodeValueString2 = this.fDeferredDocumentImpl.getDeferredEntityBaseURI(this.fDeferredEntityDecl);
            }
            if (nodeValueString2 == null || this.fErrorHandler == null) {
                return;
            }
            DOMErrorImpl dOMErrorImpl = new DOMErrorImpl();
            dOMErrorImpl.fType = "pi-base-uri-not-preserved";
            dOMErrorImpl.fRelatedData = nodeValueString2;
            dOMErrorImpl.fSeverity = (short) 1;
            this.fErrorHandler.getErrorHandler().handleError(dOMErrorImpl);
        }
    }

    protected final void handleBaseURI(Node node) {
        String baseURI;
        if (this.fDocumentImpl != null) {
            short nodeType = node.getNodeType();
            if (nodeType != 1) {
                if (nodeType != 7 || (baseURI = ((EntityReferenceImpl) this.fCurrentNode).getBaseURI()) == null || this.fErrorHandler == null) {
                    return;
                }
                DOMErrorImpl dOMErrorImpl = new DOMErrorImpl();
                dOMErrorImpl.fType = "pi-base-uri-not-preserved";
                dOMErrorImpl.fRelatedData = baseURI;
                dOMErrorImpl.fSeverity = (short) 1;
                this.fErrorHandler.getErrorHandler().handleError(dOMErrorImpl);
                return;
            }
            if (this.fNamespaceAware) {
                if (((Element) node).getAttributeNodeNS("http://www.w3.org/XML/1998/namespace", ApkUtil.DEF_MODULE_NAME) != null) {
                    return;
                }
            } else if (((Element) node).getAttributeNode("xml:base") != null) {
                return;
            }
            String baseURI2 = ((EntityReferenceImpl) this.fCurrentNode).getBaseURI();
            if (baseURI2 == null || baseURI2.equals(this.fDocumentImpl.getDocumentURI())) {
                return;
            }
            Element element = (Element) node;
            if (this.fNamespaceAware) {
                element.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:base", baseURI2);
            } else {
                element.setAttribute("xml:base", baseURI2);
            }
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void ignorableWhitespace(XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (!this.fIncludeIgnorableWhitespace || this.fFilterReject) {
            return;
        }
        if (this.fDeferNodeExpansion) {
            this.fDeferredDocumentImpl.appendChild(this.fCurrentNodeIndex, this.fDeferredDocumentImpl.createDeferredTextNode(xMLString.toString(), true));
            return;
        }
        Node lastChild = this.fCurrentNode.getLastChild();
        if (lastChild != null && lastChild.getNodeType() == 3) {
            ((Text) lastChild).appendData(xMLString.toString());
            return;
        }
        Text textCreateTextNode = this.fDocument.createTextNode(xMLString.toString());
        if (this.fDocumentImpl != null) {
            ((TextImpl) textCreateTextNode).setIgnorableWhitespace(true);
        }
        this.fCurrentNode.appendChild(textCreateTextNode);
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void ignoredCharacters(XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void internalEntityDecl(String str, XMLString xMLString, XMLString xMLString2, Augmentations augmentations) throws XNIException {
        StringBuffer stringBuffer = this.fInternalSubset;
        if (stringBuffer != null && !this.fInDTDExternalSubset) {
            stringBuffer.append("<!ENTITY ");
            if (str.startsWith("%")) {
                this.fInternalSubset.append("% ");
                this.fInternalSubset.append(str.substring(1));
            } else {
                this.fInternalSubset.append(str);
            }
            this.fInternalSubset.append(StringUtil.SPACE);
            String string = xMLString2.toString();
            boolean z = string.indexOf(39) == -1;
            this.fInternalSubset.append(z ? '\'' : '\"');
            this.fInternalSubset.append(string);
            this.fInternalSubset.append(z ? '\'' : '\"');
            this.fInternalSubset.append(">\n");
        }
        if (str.startsWith("%")) {
            return;
        }
        DocumentType documentType = this.fDocumentType;
        if (documentType != null) {
            NamedNodeMap entities = documentType.getEntities();
            if (((EntityImpl) entities.getNamedItem(str)) == null) {
                EntityImpl entityImpl = (EntityImpl) this.fDocumentImpl.createEntity(str);
                entityImpl.setBaseURI((String) this.fBaseURIStack.peek());
                entities.setNamedItem(entityImpl);
            }
        }
        int i = this.fDocumentTypeIndex;
        if (i != -1) {
            int lastChild = this.fDeferredDocumentImpl.getLastChild(i, false);
            while (lastChild != -1) {
                if (this.fDeferredDocumentImpl.getNodeType(lastChild, false) == 6 && this.fDeferredDocumentImpl.getNodeName(lastChild, false).equals(str)) {
                    return;
                } else {
                    lastChild = this.fDeferredDocumentImpl.getRealPrevSibling(lastChild, false);
                }
            }
            this.fDeferredDocumentImpl.appendChild(this.fDocumentTypeIndex, this.fDeferredDocumentImpl.createDeferredEntity(str, null, null, null, (String) this.fBaseURIStack.peek()));
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void notationDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        DocumentType documentType;
        String str2;
        String publicId = xMLResourceIdentifier.getPublicId();
        String literalSystemId = xMLResourceIdentifier.getLiteralSystemId();
        StringBuffer stringBuffer = this.fInternalSubset;
        if (stringBuffer != null && !this.fInDTDExternalSubset) {
            stringBuffer.append("<!NOTATION ");
            this.fInternalSubset.append(str);
            StringBuffer stringBuffer2 = this.fInternalSubset;
            if (publicId != null) {
                stringBuffer2.append(" PUBLIC '");
                this.fInternalSubset.append(publicId);
                if (literalSystemId != null) {
                    stringBuffer2 = this.fInternalSubset;
                    str2 = "' '";
                }
                this.fInternalSubset.append("'>\n");
            } else {
                str2 = " SYSTEM '";
            }
            stringBuffer2.append(str2);
            this.fInternalSubset.append(literalSystemId);
            this.fInternalSubset.append("'>\n");
        }
        if (this.fDocumentImpl != null && (documentType = this.fDocumentType) != null) {
            NamedNodeMap notations = documentType.getNotations();
            if (notations.getNamedItem(str) == null) {
                NotationImpl notationImpl = (NotationImpl) this.fDocumentImpl.createNotation(str);
                notationImpl.setPublicId(publicId);
                notationImpl.setSystemId(literalSystemId);
                notationImpl.setBaseURI(xMLResourceIdentifier.getBaseSystemId());
                notations.setNamedItem(notationImpl);
            }
        }
        int i = this.fDocumentTypeIndex;
        if (i != -1) {
            int lastChild = this.fDeferredDocumentImpl.getLastChild(i, false);
            while (lastChild != -1) {
                if (this.fDeferredDocumentImpl.getNodeType(lastChild, false) == 12 && this.fDeferredDocumentImpl.getNodeName(lastChild, false).equals(str)) {
                    return;
                } else {
                    lastChild = this.fDeferredDocumentImpl.getPrevSibling(lastChild, false);
                }
            }
            this.fDeferredDocumentImpl.appendChild(this.fDocumentTypeIndex, this.fDeferredDocumentImpl.createDeferredNotation(str, publicId, literalSystemId, xMLResourceIdentifier.getBaseSystemId()));
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
        if (this.fInDTD) {
            StringBuffer stringBuffer = this.fInternalSubset;
            if (stringBuffer == null || this.fInDTDExternalSubset) {
                return;
            }
            stringBuffer.append("<?");
            this.fInternalSubset.append(str);
            if (xMLString.length > 0) {
                this.fInternalSubset.append(StringUtil.SPACE).append(xMLString.ch, xMLString.offset, xMLString.length);
            }
            this.fInternalSubset.append("?>");
            return;
        }
        if (this.fDeferNodeExpansion) {
            this.fDeferredDocumentImpl.appendChild(this.fCurrentNodeIndex, this.fDeferredDocumentImpl.createDeferredProcessingInstruction(str, xMLString.toString()));
            return;
        }
        if (this.fFilterReject) {
            return;
        }
        ProcessingInstruction processingInstructionCreateProcessingInstruction = this.fDocument.createProcessingInstruction(str, xMLString.toString());
        setCharacterData(false);
        this.fCurrentNode.appendChild(processingInstructionCreateProcessingInstruction);
        LSParserFilter lSParserFilter = this.fDOMFilter;
        if (lSParserFilter == null || this.fInEntityRef || (lSParserFilter.getWhatToShow() & 64) == 0) {
            return;
        }
        short sAcceptNode = this.fDOMFilter.acceptNode(processingInstructionCreateProcessingInstruction);
        if (sAcceptNode == 2 || sAcceptNode == 3) {
            this.fCurrentNode.removeChild(processingInstructionCreateProcessingInstruction);
            this.fFirstChunk = true;
        } else if (sAcceptNode == 4) {
            throw Abort.INSTANCE;
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.parsers.XMLParser
    public void reset() throws XNIException {
        super.reset();
        this.fCreateEntityRefNodes = this.fConfiguration.getFeature(CREATE_ENTITY_REF_NODES);
        this.fIncludeIgnorableWhitespace = this.fConfiguration.getFeature(INCLUDE_IGNORABLE_WHITESPACE);
        this.fDeferNodeExpansion = this.fConfiguration.getFeature(DEFER_NODE_EXPANSION);
        this.fNamespaceAware = this.fConfiguration.getFeature(NAMESPACES);
        this.fIncludeComments = this.fConfiguration.getFeature(INCLUDE_COMMENTS_FEATURE);
        this.fCreateCDATANodes = this.fConfiguration.getFeature(CREATE_CDATA_NODES_FEATURE);
        setDocumentClassName((String) this.fConfiguration.getProperty(DOCUMENT_CLASS_NAME));
        this.fDocument = null;
        this.fDocumentImpl = null;
        this.fStorePSVI = false;
        this.fDocumentType = null;
        this.fDocumentTypeIndex = -1;
        this.fDeferredDocumentImpl = null;
        this.fCurrentNode = null;
        this.fStringBuffer.setLength(0);
        this.fRoot = null;
        this.fInDTD = false;
        this.fInDTDExternalSubset = false;
        this.fInCDATASection = false;
        this.fFirstChunk = false;
        this.fCurrentCDATASection = null;
        this.fCurrentCDATASectionIndex = -1;
        this.fBaseURIStack.removeAllElements();
    }

    protected void setCharacterData(boolean z) {
        this.fFirstChunk = z;
        Node lastChild = this.fCurrentNode.getLastChild();
        if (lastChild != null) {
            if (this.fStringBuffer.length() > 0) {
                if (lastChild.getNodeType() == 3) {
                    if (this.fDocumentImpl != null) {
                        ((TextImpl) lastChild).replaceData(this.fStringBuffer.toString());
                    } else {
                        ((Text) lastChild).setData(this.fStringBuffer.toString());
                    }
                }
                this.fStringBuffer.setLength(0);
            }
            if (this.fDOMFilter == null || this.fInEntityRef || lastChild.getNodeType() != 3 || (this.fDOMFilter.getWhatToShow() & 4) == 0) {
                return;
            }
            short sAcceptNode = this.fDOMFilter.acceptNode(lastChild);
            if (sAcceptNode == 2 || sAcceptNode == 3) {
                this.fCurrentNode.removeChild(lastChild);
            } else if (sAcceptNode == 4) {
                throw Abort.INSTANCE;
            }
        }
    }

    protected void setDocumentClassName(String str) {
        if (str == null) {
            str = DEFAULT_DOCUMENT_CLASS_NAME;
        }
        if (!str.equals(DEFAULT_DOCUMENT_CLASS_NAME) && !str.equals(PSVI_DOCUMENT_CLASS_NAME)) {
            try {
                if (!Document.class.isAssignableFrom(ObjectFactory.findProviderClass(str, ObjectFactory.findClassLoader(), true))) {
                    throw new IllegalArgumentException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "InvalidDocumentClassName", new Object[]{str}));
                }
            } catch (ClassNotFoundException unused) {
                throw new IllegalArgumentException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "MissingDocumentClassName", new Object[]{str}));
            }
        }
        this.fDocumentClassName = str;
        if (str.equals(DEFAULT_DOCUMENT_CLASS_NAME)) {
            return;
        }
        this.fDeferNodeExpansion = false;
    }

    public void setLocale(Locale locale) {
        this.fConfiguration.setLocale(locale);
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void startAttlist(String str, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void startCDATA(Augmentations augmentations) throws XNIException {
        this.fInCDATASection = true;
        if (this.fDeferNodeExpansion || this.fFilterReject || !this.fCreateCDATANodes) {
            return;
        }
        setCharacterData(false);
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void startConditional(short s, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void startDTD(XMLLocator xMLLocator, Augmentations augmentations) throws XNIException {
        this.fInDTD = true;
        if (xMLLocator != null) {
            this.fBaseURIStack.push(xMLLocator.getBaseSystemId());
        }
        if (this.fDeferNodeExpansion || this.fDocumentImpl != null) {
            this.fInternalSubset = new StringBuffer(1024);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void startDocument(XMLLocator xMLLocator, String str, NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
        CoreDocumentImpl coreDocumentImpl;
        this.fLocator = xMLLocator;
        if (this.fDeferNodeExpansion) {
            DeferredDocumentImpl deferredDocumentImpl = new DeferredDocumentImpl(this.fNamespaceAware);
            this.fDeferredDocumentImpl = deferredDocumentImpl;
            this.fDocument = deferredDocumentImpl;
            this.fDocumentIndex = deferredDocumentImpl.createDeferredDocument();
            this.fDeferredDocumentImpl.setInputEncoding(str);
            this.fDeferredDocumentImpl.setDocumentURI(xMLLocator.getExpandedSystemId());
            this.fCurrentNodeIndex = this.fDocumentIndex;
            return;
        }
        if (!this.fDocumentClassName.equals(DEFAULT_DOCUMENT_CLASS_NAME)) {
            if (this.fDocumentClassName.equals(PSVI_DOCUMENT_CLASS_NAME)) {
                CoreDocumentImpl pSVIDocumentImpl = new PSVIDocumentImpl();
                this.fDocument = pSVIDocumentImpl;
                coreDocumentImpl = pSVIDocumentImpl;
                this.fDocumentImpl = coreDocumentImpl;
                this.fStorePSVI = true;
            } else {
                try {
                    ClassLoader classLoaderFindClassLoader = ObjectFactory.findClassLoader();
                    Class<?> clsFindProviderClass = ObjectFactory.findProviderClass(this.fDocumentClassName, classLoaderFindClassLoader, true);
                    this.fDocument = (Document) clsFindProviderClass.newInstance();
                    if (ObjectFactory.findProviderClass(CORE_DOCUMENT_CLASS_NAME, classLoaderFindClassLoader, true).isAssignableFrom(clsFindProviderClass)) {
                        this.fDocumentImpl = (CoreDocumentImpl) this.fDocument;
                        if (ObjectFactory.findProviderClass(PSVI_DOCUMENT_CLASS_NAME, classLoaderFindClassLoader, true).isAssignableFrom(clsFindProviderClass)) {
                            this.fStorePSVI = true;
                        }
                        this.fDocumentImpl.setStrictErrorChecking(false);
                        this.fDocumentImpl.setInputEncoding(str);
                        if (xMLLocator != null) {
                            this.fDocumentImpl.setDocumentURI(xMLLocator.getExpandedSystemId());
                        }
                    }
                } catch (ClassNotFoundException unused) {
                } catch (Exception unused2) {
                    throw new RuntimeException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "CannotCreateDocumentClass", new Object[]{this.fDocumentClassName}));
                }
            }
            this.fCurrentNode = this.fDocument;
        }
        CoreDocumentImpl documentImpl = new DocumentImpl();
        this.fDocument = documentImpl;
        coreDocumentImpl = documentImpl;
        this.fDocumentImpl = coreDocumentImpl;
        coreDocumentImpl.setStrictErrorChecking(false);
        this.fDocumentImpl.setInputEncoding(str);
        this.fDocumentImpl.setDocumentURI(xMLLocator.getExpandedSystemId());
        this.fCurrentNode = this.fDocument;
    }

    /* JADX WARN: Code duplicated, block: B:44:0x00b9  */
    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        Object type;
        boolean zEquals;
        boolean zIsIDType;
        ElementPSVI elementPSVI;
        Object type2;
        boolean zEquals2;
        if (this.fDeferNodeExpansion) {
            int iCreateDeferredElement = this.fDeferredDocumentImpl.createDeferredElement(this.fNamespaceAware ? qName.uri : null, qName.rawname);
            Object obj = null;
            for (int length = xMLAttributes.getLength() - 1; length >= 0; length--) {
                AttributePSVI attributePSVI = (AttributePSVI) xMLAttributes.getAugmentations(length).getItem(Constants.ATTRIBUTE_PSVI);
                if (attributePSVI != null && this.fNamespaceAware) {
                    XSSimpleTypeDefinition memberTypeDefinition = attributePSVI.getMemberTypeDefinition();
                    if (memberTypeDefinition == null) {
                        type = attributePSVI.getTypeDefinition();
                        if (type != null) {
                            zEquals = ((XSSimpleType) type).isIDType();
                            obj = type;
                            zIsIDType = zEquals;
                        } else {
                            obj = type;
                            zIsIDType = false;
                        }
                    } else {
                        zIsIDType = ((XSSimpleType) memberTypeDefinition).isIDType();
                        obj = memberTypeDefinition;
                    }
                } else if (Boolean.TRUE.equals(xMLAttributes.getAugmentations(length).getItem(Constants.ATTRIBUTE_DECLARED))) {
                    type = xMLAttributes.getType(length);
                    zEquals = SchemaSymbols.ATTVAL_ID.equals(type);
                    obj = type;
                    zIsIDType = zEquals;
                } else {
                    zIsIDType = false;
                }
                this.fDeferredDocumentImpl.setDeferredAttribute(iCreateDeferredElement, xMLAttributes.getQName(length), xMLAttributes.getURI(length), xMLAttributes.getValue(length), xMLAttributes.isSpecified(length), zIsIDType, obj);
            }
            this.fDeferredDocumentImpl.appendChild(this.fCurrentNodeIndex, iCreateDeferredElement);
            this.fCurrentNodeIndex = iCreateDeferredElement;
            return;
        }
        if (this.fFilterReject) {
            this.fRejectedElementDepth++;
            return;
        }
        Element elementCreateElementNode = createElementNode(qName);
        int length2 = xMLAttributes.getLength();
        boolean z = false;
        for (int i = 0; i < length2; i++) {
            xMLAttributes.getName(i, this.fAttrQName);
            Attr attrCreateAttrNode = createAttrNode(this.fAttrQName);
            String value = xMLAttributes.getValue(i);
            AttributePSVI attributePSVI2 = (AttributePSVI) xMLAttributes.getAugmentations(i).getItem(Constants.ATTRIBUTE_PSVI);
            if (this.fStorePSVI && attributePSVI2 != null) {
                ((PSVIAttrNSImpl) attrCreateAttrNode).setPSVI(attributePSVI2);
            }
            attrCreateAttrNode.setValue(value);
            boolean zIsSpecified = xMLAttributes.isSpecified(i);
            if (zIsSpecified || (!z && (this.fAttrQName.uri == null || this.fAttrQName.uri == NamespaceContext.XMLNS_URI || this.fAttrQName.prefix != null))) {
                elementCreateElementNode.setAttributeNode(attrCreateAttrNode);
            } else {
                elementCreateElementNode.setAttributeNodeNS(attrCreateAttrNode);
                z = true;
            }
            if (this.fDocumentImpl != null) {
                AttrImpl attrImpl = (AttrImpl) attrCreateAttrNode;
                if (attributePSVI2 != null && this.fNamespaceAware) {
                    type2 = attributePSVI2.getMemberTypeDefinition();
                    if (type2 == null && (type2 = attributePSVI2.getTypeDefinition()) == null) {
                        zEquals2 = false;
                    } else {
                        zEquals2 = ((XSSimpleType) type2).isIDType();
                    }
                    if (zEquals2) {
                        ((ElementImpl) elementCreateElementNode).setIdAttributeNode(attrCreateAttrNode, true);
                    }
                    attrImpl.setSpecified(zIsSpecified);
                } else if (Boolean.TRUE.equals(xMLAttributes.getAugmentations(i).getItem(Constants.ATTRIBUTE_DECLARED))) {
                    type2 = xMLAttributes.getType(i);
                    zEquals2 = SchemaSymbols.ATTVAL_ID.equals(type2);
                } else {
                    type2 = null;
                    zEquals2 = false;
                }
                attrImpl.setType(type2);
                if (zEquals2) {
                    ((ElementImpl) elementCreateElementNode).setIdAttributeNode(attrCreateAttrNode, true);
                }
                attrImpl.setSpecified(zIsSpecified);
            }
        }
        setCharacterData(false);
        if (augmentations != null && (elementPSVI = (ElementPSVI) augmentations.getItem(Constants.ELEMENT_PSVI)) != null && this.fNamespaceAware) {
            XSTypeDefinition memberTypeDefinition2 = elementPSVI.getMemberTypeDefinition();
            if (memberTypeDefinition2 == null) {
                memberTypeDefinition2 = elementPSVI.getTypeDefinition();
            }
            ((ElementNSImpl) elementCreateElementNode).setType(memberTypeDefinition2);
        }
        LSParserFilter lSParserFilter = this.fDOMFilter;
        if (lSParserFilter != null && !this.fInEntityRef) {
            if (this.fRoot == null) {
                this.fRoot = elementCreateElementNode;
            } else {
                short sStartElement = lSParserFilter.startElement(elementCreateElementNode);
                if (sStartElement == 2) {
                    this.fFilterReject = true;
                    this.fRejectedElementDepth = 0;
                    return;
                } else if (sStartElement == 3) {
                    this.fFirstChunk = true;
                    this.fSkippedElemStack.push(Boolean.TRUE);
                    return;
                } else {
                    if (sStartElement == 4) {
                        throw Abort.INSTANCE;
                    }
                    if (!this.fSkippedElemStack.isEmpty()) {
                        this.fSkippedElemStack.push(Boolean.FALSE);
                    }
                }
            }
        }
        this.fCurrentNode.appendChild(elementCreateElementNode);
        this.fCurrentNode = elementCreateElementNode;
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void startExternalSubset(XMLResourceIdentifier xMLResourceIdentifier, Augmentations augmentations) throws XNIException {
        this.fBaseURIStack.push(xMLResourceIdentifier.getBaseSystemId());
        this.fInDTDExternalSubset = true;
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void startGeneralEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        if (this.fDeferNodeExpansion) {
            int iCreateDeferredEntityReference = this.fDeferredDocumentImpl.createDeferredEntityReference(str, xMLResourceIdentifier.getExpandedSystemId());
            int i = this.fDocumentTypeIndex;
            if (i != -1) {
                int lastChild = this.fDeferredDocumentImpl.getLastChild(i, false);
                while (lastChild != -1) {
                    if (this.fDeferredDocumentImpl.getNodeType(lastChild, false) == 6 && this.fDeferredDocumentImpl.getNodeName(lastChild, false).equals(str)) {
                        this.fDeferredEntityDecl = lastChild;
                        this.fDeferredDocumentImpl.setInputEncoding(lastChild, str2);
                        break;
                    }
                    lastChild = this.fDeferredDocumentImpl.getRealPrevSibling(lastChild, false);
                }
            }
            this.fDeferredDocumentImpl.appendChild(this.fCurrentNodeIndex, iCreateDeferredEntityReference);
            this.fCurrentNodeIndex = iCreateDeferredEntityReference;
            return;
        }
        if (this.fFilterReject) {
            return;
        }
        setCharacterData(true);
        EntityReference entityReferenceCreateEntityReference = this.fDocument.createEntityReference(str);
        if (this.fDocumentImpl != null) {
            EntityReferenceImpl entityReferenceImpl = (EntityReferenceImpl) entityReferenceCreateEntityReference;
            entityReferenceImpl.setBaseURI(xMLResourceIdentifier.getExpandedSystemId());
            DocumentType documentType = this.fDocumentType;
            if (documentType != null) {
                EntityImpl entityImpl = (EntityImpl) documentType.getEntities().getNamedItem(str);
                this.fCurrentEntityDecl = entityImpl;
                if (entityImpl != null) {
                    entityImpl.setInputEncoding(str2);
                }
            }
            entityReferenceImpl.needsSyncChildren(false);
        }
        this.fInEntityRef = true;
        this.fCurrentNode.appendChild(entityReferenceCreateEntityReference);
        this.fCurrentNode = entityReferenceCreateEntityReference;
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void startParameterEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        if (augmentations != null && this.fInternalSubset != null && !this.fInDTDExternalSubset && Boolean.TRUE.equals(augmentations.getItem(Constants.ENTITY_SKIPPED))) {
            this.fInternalSubset.append(str).append(";\n");
        }
        this.fBaseURIStack.push(xMLResourceIdentifier.getExpandedSystemId());
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void textDecl(String str, String str2, Augmentations augmentations) throws XNIException {
        if (this.fInDTD) {
            return;
        }
        if (this.fDeferNodeExpansion) {
            int i = this.fDeferredEntityDecl;
            if (i != -1) {
                this.fDeferredDocumentImpl.setEntityInfo(i, str, str2);
                return;
            }
            return;
        }
        EntityImpl entityImpl = this.fCurrentEntityDecl;
        if (entityImpl == null || this.fFilterReject) {
            return;
        }
        entityImpl.setXmlEncoding(str2);
        if (str != null) {
            this.fCurrentEntityDecl.setXmlVersion(str);
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDTDHandler
    public void unparsedEntityDecl(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
        String str3;
        String publicId = xMLResourceIdentifier.getPublicId();
        String literalSystemId = xMLResourceIdentifier.getLiteralSystemId();
        StringBuffer stringBuffer = this.fInternalSubset;
        if (stringBuffer != null && !this.fInDTDExternalSubset) {
            stringBuffer.append("<!ENTITY ");
            this.fInternalSubset.append(str);
            this.fInternalSubset.append(StringUtil.SPACE);
            StringBuffer stringBuffer2 = this.fInternalSubset;
            if (publicId != null) {
                stringBuffer2.append("PUBLIC '");
                this.fInternalSubset.append(publicId);
                if (literalSystemId != null) {
                    stringBuffer2 = this.fInternalSubset;
                    str3 = "' '";
                }
                this.fInternalSubset.append("' NDATA ");
                this.fInternalSubset.append(str2);
                this.fInternalSubset.append(">\n");
            } else {
                str3 = "SYSTEM '";
            }
            stringBuffer2.append(str3);
            this.fInternalSubset.append(literalSystemId);
            this.fInternalSubset.append("' NDATA ");
            this.fInternalSubset.append(str2);
            this.fInternalSubset.append(">\n");
        }
        DocumentType documentType = this.fDocumentType;
        if (documentType != null) {
            NamedNodeMap entities = documentType.getEntities();
            if (((EntityImpl) entities.getNamedItem(str)) == null) {
                EntityImpl entityImpl = (EntityImpl) this.fDocumentImpl.createEntity(str);
                entityImpl.setPublicId(publicId);
                entityImpl.setSystemId(literalSystemId);
                entityImpl.setNotationName(str2);
                entityImpl.setBaseURI(xMLResourceIdentifier.getBaseSystemId());
                entities.setNamedItem(entityImpl);
            }
        }
        int i = this.fDocumentTypeIndex;
        if (i != -1) {
            int lastChild = this.fDeferredDocumentImpl.getLastChild(i, false);
            while (lastChild != -1) {
                if (this.fDeferredDocumentImpl.getNodeType(lastChild, false) == 6 && this.fDeferredDocumentImpl.getNodeName(lastChild, false).equals(str)) {
                    return;
                } else {
                    lastChild = this.fDeferredDocumentImpl.getRealPrevSibling(lastChild, false);
                }
            }
            this.fDeferredDocumentImpl.appendChild(this.fDocumentTypeIndex, this.fDeferredDocumentImpl.createDeferredEntity(str, publicId, literalSystemId, str2, xMLResourceIdentifier.getBaseSystemId()));
        }
    }

    @Override // org.apache.xerces.parsers.AbstractXMLDocumentParser, org.apache.xerces.xni.XMLDocumentHandler
    public void xmlDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
        if (this.fDeferNodeExpansion) {
            if (str != null) {
                this.fDeferredDocumentImpl.setXmlVersion(str);
            }
            this.fDeferredDocumentImpl.setXmlEncoding(str2);
            this.fDeferredDocumentImpl.setXmlStandalone("yes".equals(str3));
            return;
        }
        CoreDocumentImpl coreDocumentImpl = this.fDocumentImpl;
        if (coreDocumentImpl != null) {
            if (str != null) {
                coreDocumentImpl.setXmlVersion(str);
            }
            this.fDocumentImpl.setXmlEncoding(str2);
            this.fDocumentImpl.setXmlStandalone("yes".equals(str3));
        }
    }
}
