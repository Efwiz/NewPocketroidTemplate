package org.apache.xerces.dom;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.RevalidationHandler;
import org.apache.xerces.impl.dtd.XMLDTDLoader;
import org.apache.xerces.impl.dtd.XMLDTDValidator;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.util.SimpleLocator;
import org.apache.xerces.jaxp.JAXPConstants;
import org.apache.xerces.util.AugmentationsImpl;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLComponent;
import org.apache.xerces.xni.parser.XMLDocumentSource;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/* JADX INFO: loaded from: classes13.dex */
public class DOMNormalizer implements XMLDocumentHandler {
    protected static final boolean DEBUG = false;
    protected static final boolean DEBUG_EVENTS = false;
    protected static final boolean DEBUG_ND = false;
    protected static final String PREFIX = "NS";
    protected DOMErrorHandler fErrorHandler;
    protected SymbolTable fSymbolTable;
    protected RevalidationHandler fValidationHandler;
    public static final RuntimeException abort = new RuntimeException() { // from class: org.apache.xerces.dom.DOMNormalizer.1
        private static final long serialVersionUID = 5361322877988412432L;

        @Override // java.lang.Throwable
        public Throwable fillInStackTrace() {
            return this;
        }
    };
    public static final XMLString EMPTY_STRING = new XMLString();
    protected DOMConfigurationImpl fConfiguration = null;
    protected CoreDocumentImpl fDocument = null;
    protected final XMLAttributesProxy fAttrProxy = new XMLAttributesProxy();
    protected final QName fQName = new QName();
    private final DOMErrorImpl fError = new DOMErrorImpl();
    protected boolean fNamespaceValidation = false;
    protected boolean fPSVI = false;
    protected final NamespaceContext fNamespaceContext = new NamespaceSupport();
    protected final NamespaceContext fLocalNSBinder = new NamespaceSupport();
    protected final ArrayList fAttributeList = new ArrayList(5);
    protected final DOMLocatorImpl fLocator = new DOMLocatorImpl();
    protected Node fCurrentNode = null;
    private final QName fAttrQName = new QName();
    final XMLString fNormalizedValue = new XMLString(new char[16], 0, 0);
    private boolean fAllWhitespace = false;

    protected final class XMLAttributesProxy implements XMLAttributes {
        protected AttributeMap fAttributes;
        protected CoreDocumentImpl fDocument;
        protected ElementImpl fElement;
        protected final Vector fDTDTypes = new Vector(5);
        protected final Vector fAugmentations = new Vector(5);

        protected XMLAttributesProxy() {
        }

        private String getReportableType(String str) {
            return str.charAt(0) == '(' ? SchemaSymbols.ATTVAL_NMTOKEN : str;
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public int addAttribute(QName qName, String str, String str2) {
            int xercesAttribute = this.fElement.getXercesAttribute(qName.uri, qName.localpart);
            if (xercesAttribute >= 0) {
                return xercesAttribute;
            }
            AttrImpl attrImpl = (AttrImpl) ((CoreDocumentImpl) this.fElement.getOwnerDocument()).createAttributeNS(qName.uri, qName.rawname, qName.localpart);
            attrImpl.setNodeValue(str2);
            int xercesAttributeNode = this.fElement.setXercesAttributeNode(attrImpl);
            this.fDTDTypes.insertElementAt(str, xercesAttributeNode);
            this.fAugmentations.insertElementAt(new AugmentationsImpl(), xercesAttributeNode);
            attrImpl.setSpecified(false);
            return xercesAttributeNode;
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public Augmentations getAugmentations(int i) {
            return (Augmentations) this.fAugmentations.elementAt(i);
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public Augmentations getAugmentations(String str) {
            return null;
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public Augmentations getAugmentations(String str, String str2) {
            return null;
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public int getIndex(String str) {
            return -1;
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public int getIndex(String str, String str2) {
            return -1;
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public int getLength() {
            AttributeMap attributeMap = this.fAttributes;
            if (attributeMap != null) {
                return attributeMap.getLength();
            }
            return 0;
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public String getLocalName(int i) {
            String localName;
            AttributeMap attributeMap = this.fAttributes;
            if (attributeMap == null || (localName = ((Node) attributeMap.getItem(i)).getLocalName()) == null) {
                return null;
            }
            return DOMNormalizer.this.fSymbolTable.addSymbol(localName);
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public void getName(int i, QName qName) {
            AttributeMap attributeMap = this.fAttributes;
            if (attributeMap != null) {
                DOMNormalizer.this.updateQName((Node) attributeMap.getItem(i), qName);
            }
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public String getNonNormalizedValue(int i) {
            return null;
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public String getPrefix(int i) {
            String prefix;
            AttributeMap attributeMap = this.fAttributes;
            if (attributeMap == null || (prefix = ((Node) attributeMap.getItem(i)).getPrefix()) == null || prefix.length() == 0) {
                return null;
            }
            return DOMNormalizer.this.fSymbolTable.addSymbol(prefix);
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public String getQName(int i) {
            AttributeMap attributeMap = this.fAttributes;
            if (attributeMap == null) {
                return null;
            }
            return DOMNormalizer.this.fSymbolTable.addSymbol(((Node) attributeMap.getItem(i)).getNodeName());
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public String getType(int i) {
            String str = (String) this.fDTDTypes.elementAt(i);
            return str != null ? getReportableType(str) : "CDATA";
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public String getType(String str) {
            return "CDATA";
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public String getType(String str, String str2) {
            return "CDATA";
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public String getURI(int i) {
            String namespaceURI;
            AttributeMap attributeMap = this.fAttributes;
            if (attributeMap == null || (namespaceURI = ((Node) attributeMap.getItem(i)).getNamespaceURI()) == null) {
                return null;
            }
            return DOMNormalizer.this.fSymbolTable.addSymbol(namespaceURI);
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public String getValue(int i) {
            AttributeMap attributeMap = this.fAttributes;
            return attributeMap != null ? attributeMap.item(i).getNodeValue() : "";
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public String getValue(String str) {
            return null;
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public String getValue(String str, String str2) {
            Node namedItemNS;
            AttributeMap attributeMap = this.fAttributes;
            if (attributeMap == null || (namedItemNS = attributeMap.getNamedItemNS(str, str2)) == null) {
                return null;
            }
            return namedItemNS.getNodeValue();
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public boolean isSpecified(int i) {
            return ((Attr) this.fAttributes.getItem(i)).getSpecified();
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public void removeAllAttributes() {
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public void removeAttributeAt(int i) {
        }

        public void setAttributes(AttributeMap attributeMap, CoreDocumentImpl coreDocumentImpl, ElementImpl elementImpl) {
            this.fDocument = coreDocumentImpl;
            this.fAttributes = attributeMap;
            this.fElement = elementImpl;
            if (attributeMap == null) {
                this.fDTDTypes.setSize(0);
                this.fAugmentations.setSize(0);
                return;
            }
            int length = attributeMap.getLength();
            this.fDTDTypes.setSize(length);
            this.fAugmentations.setSize(length);
            for (int i = 0; i < length; i++) {
                this.fAugmentations.setElementAt(new AugmentationsImpl(), i);
            }
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public void setAugmentations(int i, Augmentations augmentations) {
            this.fAugmentations.setElementAt(augmentations, i);
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public void setName(int i, QName qName) {
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public void setNonNormalizedValue(int i, String str) {
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public void setSpecified(int i, boolean z) {
            ((AttrImpl) this.fAttributes.getItem(i)).setSpecified(z);
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public void setType(int i, String str) {
            this.fDTDTypes.setElementAt(str, i);
        }

        @Override // org.apache.xerces.xni.XMLAttributes
        public void setValue(int i, String str) {
            AttributeMap attributeMap = this.fAttributes;
            if (attributeMap != null) {
                AttrImpl attrImpl = (AttrImpl) attributeMap.getItem(i);
                boolean specified = attrImpl.getSpecified();
                attrImpl.setValue(str);
                attrImpl.setSpecified(specified);
            }
        }
    }

    public static final void isAttrValueWF(DOMErrorHandler dOMErrorHandler, DOMErrorImpl dOMErrorImpl, DOMLocatorImpl dOMLocatorImpl, NamedNodeMap namedNodeMap, Attr attr, String str, boolean z) {
        DocumentType doctype;
        if ((attr instanceof AttrImpl) && ((AttrImpl) attr).hasStringValue()) {
            isXMLCharWF(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, str, z);
            return;
        }
        NodeList childNodes = attr.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node nodeItem = childNodes.item(i);
            if (nodeItem.getNodeType() == 5) {
                Document ownerDocument = attr.getOwnerDocument();
                if (((ownerDocument == null || (doctype = ownerDocument.getDoctype()) == null) ? null : (Entity) doctype.getEntities().getNamedItemNS("*", nodeItem.getNodeName())) == null) {
                    reportDOMError(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "UndeclaredEntRefInAttrValue", new Object[]{attr.getNodeName()}), (short) 2, "UndeclaredEntRefInAttrValue");
                }
            } else {
                isXMLCharWF(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, nodeItem.getNodeValue(), z);
            }
        }
    }

    public static final void isCDataWF(DOMErrorHandler dOMErrorHandler, DOMErrorImpl dOMErrorImpl, DOMLocatorImpl dOMLocatorImpl, String str, boolean z) {
        String message;
        String message2;
        if (str == null || str.length() == 0) {
            return;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = 0;
        if (z) {
            while (i < length) {
                int i2 = i + 1;
                char c = charArray[i];
                if (XML11Char.isXML11Invalid(c)) {
                    if (XMLChar.isHighSurrogate(c) && i2 < length) {
                        i += 2;
                        char c2 = charArray[i2];
                        if (!XMLChar.isLowSurrogate(c2) || !XMLChar.isSupplemental(XMLChar.supplemental(c, c2))) {
                            i2 = i;
                        }
                    }
                    message2 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInCDSect", new Object[]{Integer.toString(c, 16)});
                } else {
                    if (c == ']' && i2 < length && charArray[i2] == ']') {
                        int i3 = i2;
                        do {
                            i3++;
                            if (i3 >= length) {
                                break;
                            }
                        } while (charArray[i3] == ']');
                        if (i3 < length && charArray[i3] == '>') {
                            message2 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CDEndInContent", null);
                        }
                    }
                    i = i2;
                }
                reportDOMError(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, message2, (short) 2, "wf-invalid-character");
                i = i2;
            }
            return;
        }
        while (i < length) {
            int i4 = i + 1;
            char c3 = charArray[i];
            if (XMLChar.isInvalid(c3)) {
                if (XMLChar.isHighSurrogate(c3) && i4 < length) {
                    i += 2;
                    char c4 = charArray[i4];
                    if (!XMLChar.isLowSurrogate(c4) || !XMLChar.isSupplemental(XMLChar.supplemental(c3, c4))) {
                        i4 = i;
                    }
                }
                message = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInCDSect", new Object[]{Integer.toString(c3, 16)});
            } else {
                if (c3 == ']' && i4 < length && charArray[i4] == ']') {
                    int i5 = i4;
                    do {
                        i5++;
                        if (i5 >= length) {
                            break;
                        }
                    } while (charArray[i5] == ']');
                    if (i5 < length && charArray[i5] == '>') {
                        message = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CDEndInContent", null);
                    }
                }
                i = i4;
            }
            reportDOMError(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, message, (short) 2, "wf-invalid-character");
            i = i4;
        }
    }

    public static final void isCommentWF(DOMErrorHandler dOMErrorHandler, DOMErrorImpl dOMErrorImpl, DOMLocatorImpl dOMLocatorImpl, String str, boolean z) {
        String message;
        String message2;
        if (str == null || str.length() == 0) {
            return;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = 0;
        if (z) {
            while (i < length) {
                int i2 = i + 1;
                char c = charArray[i];
                if (XML11Char.isXML11Invalid(c)) {
                    if (XMLChar.isHighSurrogate(c) && i2 < length) {
                        i += 2;
                        char c2 = charArray[i2];
                        if (!XMLChar.isLowSurrogate(c2) || !XMLChar.isSupplemental(XMLChar.supplemental(c, c2))) {
                            i2 = i;
                        }
                    }
                    message2 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInComment", new Object[]{Integer.toString(charArray[i2 - 1], 16)});
                } else {
                    if (c == '-' && i2 < length && charArray[i2] == '-') {
                        message2 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "DashDashInComment", null);
                    }
                    i = i2;
                }
                reportDOMError(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, message2, (short) 2, "wf-invalid-character");
                i = i2;
            }
            return;
        }
        while (i < length) {
            int i3 = i + 1;
            char c3 = charArray[i];
            if (XMLChar.isInvalid(c3)) {
                if (XMLChar.isHighSurrogate(c3) && i3 < length) {
                    i += 2;
                    char c4 = charArray[i3];
                    if (!XMLChar.isLowSurrogate(c4) || !XMLChar.isSupplemental(XMLChar.supplemental(c3, c4))) {
                        i3 = i;
                    }
                }
                message = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInComment", new Object[]{Integer.toString(charArray[i3 - 1], 16)});
            } else {
                if (c3 == '-' && i3 < length && charArray[i3] == '-') {
                    message = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "DashDashInComment", null);
                }
                i = i3;
            }
            reportDOMError(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, message, (short) 2, "wf-invalid-character");
            i = i3;
        }
    }

    public static final void isXMLCharWF(DOMErrorHandler dOMErrorHandler, DOMErrorImpl dOMErrorImpl, DOMLocatorImpl dOMLocatorImpl, String str, boolean z) {
        if (str == null || str.length() == 0) {
            return;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = 0;
        if (z) {
            while (i < length) {
                int i2 = i + 1;
                if (XML11Char.isXML11Invalid(charArray[i])) {
                    char c = charArray[i];
                    if (!XMLChar.isHighSurrogate(c) || i2 >= length) {
                        i = i2;
                        reportDOMError(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "InvalidXMLCharInDOM", new Object[]{Integer.toString(charArray[i - 1], 16)}), (short) 2, "wf-invalid-character");
                    } else {
                        i += 2;
                        char c2 = charArray[i2];
                        if (!XMLChar.isLowSurrogate(c2) || !XMLChar.isSupplemental(XMLChar.supplemental(c, c2))) {
                            reportDOMError(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "InvalidXMLCharInDOM", new Object[]{Integer.toString(charArray[i - 1], 16)}), (short) 2, "wf-invalid-character");
                        }
                    }
                } else {
                    i = i2;
                }
            }
            return;
        }
        while (i < length) {
            int i3 = i + 1;
            if (XMLChar.isInvalid(charArray[i])) {
                char c3 = charArray[i];
                if (!XMLChar.isHighSurrogate(c3) || i3 >= length) {
                    i = i3;
                    reportDOMError(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "InvalidXMLCharInDOM", new Object[]{Integer.toString(charArray[i - 1], 16)}), (short) 2, "wf-invalid-character");
                } else {
                    i += 2;
                    char c4 = charArray[i3];
                    if (!XMLChar.isLowSurrogate(c4) || !XMLChar.isSupplemental(XMLChar.supplemental(c3, c4))) {
                        reportDOMError(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "InvalidXMLCharInDOM", new Object[]{Integer.toString(charArray[i - 1], 16)}), (short) 2, "wf-invalid-character");
                    }
                }
            } else {
                i = i3;
            }
        }
    }

    private void processDTD(String str, String str2) throws Throwable {
        String str3;
        String str4;
        String str5;
        String internalSubset;
        String documentURI = this.fDocument.getDocumentURI();
        DocumentType doctype = this.fDocument.getDoctype();
        XMLDTDLoader xMLDTDLoader = null;
        if (doctype != null) {
            String name2 = doctype.getName();
            String publicId = doctype.getPublicId();
            if (str2 == null || str2.length() == 0) {
                str2 = doctype.getSystemId();
            }
            str3 = str2;
            internalSubset = doctype.getInternalSubset();
            str5 = publicId;
            str4 = name2;
        } else {
            Element documentElement = this.fDocument.getDocumentElement();
            if (documentElement == null) {
                return;
            }
            String nodeName = documentElement.getNodeName();
            if (str2 == null || str2.length() == 0) {
                return;
            }
            str3 = str2;
            str4 = nodeName;
            str5 = null;
            internalSubset = null;
        }
        try {
            this.fValidationHandler.doctypeDecl(str4, str5, str3, null);
            XMLDTDLoader dTDLoader = CoreDOMImplementationImpl.singleton.getDTDLoader(str);
            try {
                dTDLoader.setFeature("http://xml.org/sax/features/validation", true);
                dTDLoader.setEntityResolver(this.fConfiguration.getEntityResolver());
                dTDLoader.setErrorHandler(this.fConfiguration.getErrorHandler());
                dTDLoader.loadGrammarWithContext((XMLDTDValidator) this.fValidationHandler, str4, str5, str3, documentURI, internalSubset);
                if (dTDLoader != null) {
                    CoreDOMImplementationImpl.singleton.releaseDTDLoader(str, dTDLoader);
                }
            } catch (IOException unused) {
                xMLDTDLoader = dTDLoader;
                if (xMLDTDLoader != null) {
                    CoreDOMImplementationImpl.singleton.releaseDTDLoader(str, xMLDTDLoader);
                }
            } catch (Throwable th) {
                th = th;
                xMLDTDLoader = dTDLoader;
                if (xMLDTDLoader != null) {
                    CoreDOMImplementationImpl.singleton.releaseDTDLoader(str, xMLDTDLoader);
                }
                throw th;
            }
        } catch (IOException unused2) {
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static final void reportDOMError(DOMErrorHandler dOMErrorHandler, DOMErrorImpl dOMErrorImpl, DOMLocatorImpl dOMLocatorImpl, String str, short s, String str2) {
        if (dOMErrorHandler != null) {
            dOMErrorImpl.reset();
            dOMErrorImpl.fMessage = str;
            dOMErrorImpl.fSeverity = s;
            dOMErrorImpl.fLocator = dOMLocatorImpl;
            dOMErrorImpl.fType = str2;
            dOMErrorImpl.fRelatedData = dOMLocatorImpl.fRelatedNode;
            if (!dOMErrorHandler.handleError(dOMErrorImpl)) {
                throw abort;
            }
        }
        if (s == 3) {
            throw abort;
        }
    }

    protected final void addNamespaceDecl(String str, String str2, ElementImpl elementImpl) {
        if (str == XMLSymbols.EMPTY_STRING) {
            elementImpl.setAttributeNS(NamespaceContext.XMLNS_URI, XMLSymbols.PREFIX_XMLNS, str2);
        } else {
            elementImpl.setAttributeNS(NamespaceContext.XMLNS_URI, "xmlns:" + str, str2);
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void characters(XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void comment(XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void doctypeDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void emptyElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        startElement(qName, xMLAttributes, augmentations);
        endElement(qName, augmentations);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endCDATA(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endDocument(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endElement(QName qName, Augmentations augmentations) throws XNIException {
        ElementPSVI elementPSVI;
        if (augmentations == null || (elementPSVI = (ElementPSVI) augmentations.getItem(Constants.ELEMENT_PSVI)) == null) {
            Node node = this.fCurrentNode;
            if (node instanceof ElementNSImpl) {
                ((ElementNSImpl) node).setType(null);
                return;
            }
            return;
        }
        Node node2 = this.fCurrentNode;
        ElementImpl elementImpl = (ElementImpl) node2;
        if (this.fPSVI) {
            ((PSVIElementNSImpl) node2).setPSVI(elementPSVI);
        }
        if (elementImpl instanceof ElementNSImpl) {
            XSTypeDefinition memberTypeDefinition = elementPSVI.getMemberTypeDefinition();
            if (memberTypeDefinition == null) {
                memberTypeDefinition = elementPSVI.getTypeDefinition();
            }
            ((ElementNSImpl) elementImpl).setType(memberTypeDefinition);
        }
        String schemaNormalizedValue = elementPSVI.getSchemaNormalizedValue();
        if ((this.fConfiguration.features & 2) != 0) {
            if (schemaNormalizedValue == null) {
                return;
            }
        } else if (elementImpl.getTextContent().length() != 0 || schemaNormalizedValue == null) {
            return;
        }
        elementImpl.setTextContent(schemaNormalizedValue);
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void endGeneralEntity(String str, Augmentations augmentations) throws XNIException {
    }

    protected final void expandEntityRef(Node node, Node node2) {
        Node firstChild = node2.getFirstChild();
        while (firstChild != null) {
            Node nextSibling = firstChild.getNextSibling();
            node.insertBefore(firstChild, node2);
            firstChild = nextSibling;
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public XMLDocumentSource getDocumentSource() {
        return null;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void ignorableWhitespace(XMLString xMLString, Augmentations augmentations) throws XNIException {
        this.fAllWhitespace = true;
    }

    protected final void namespaceFixUp(ElementImpl elementImpl, AttributeMap attributeMap) {
        String message;
        DOMErrorHandler dOMErrorHandler;
        DOMErrorImpl dOMErrorImpl;
        DOMLocatorImpl dOMLocatorImpl;
        short s;
        String message2;
        DOMErrorHandler dOMErrorHandler2;
        DOMErrorImpl dOMErrorImpl2;
        DOMLocatorImpl dOMLocatorImpl2;
        short s2;
        if (attributeMap != null) {
            for (int i = 0; i < attributeMap.getLength(); i++) {
                Attr attr = (Attr) attributeMap.getItem(i);
                String namespaceURI = attr.getNamespaceURI();
                if (namespaceURI != null && namespaceURI.equals(NamespaceContext.XMLNS_URI)) {
                    String nodeValue = attr.getNodeValue();
                    if (nodeValue == null) {
                        nodeValue = XMLSymbols.EMPTY_STRING;
                    }
                    if (this.fDocument.errorChecking && nodeValue.equals(NamespaceContext.XMLNS_URI)) {
                        this.fLocator.fRelatedNode = attr;
                        reportDOMError(this.fErrorHandler, this.fError, this.fLocator, DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CantBindXMLNS", null), (short) 2, "CantBindXMLNS");
                    } else {
                        String prefix = attr.getPrefix();
                        String strAddSymbol = (prefix == null || prefix.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix);
                        String strAddSymbol2 = this.fSymbolTable.addSymbol(attr.getLocalName());
                        if (strAddSymbol == XMLSymbols.PREFIX_XMLNS) {
                            String strAddSymbol3 = this.fSymbolTable.addSymbol(nodeValue);
                            if (strAddSymbol3.length() != 0) {
                                this.fNamespaceContext.declarePrefix(strAddSymbol2, strAddSymbol3);
                            }
                        } else {
                            String strAddSymbol4 = this.fSymbolTable.addSymbol(nodeValue);
                            NamespaceContext namespaceContext = this.fNamespaceContext;
                            String str = XMLSymbols.EMPTY_STRING;
                            if (strAddSymbol4.length() == 0) {
                                strAddSymbol4 = null;
                            }
                            namespaceContext.declarePrefix(str, strAddSymbol4);
                        }
                    }
                }
            }
        }
        String namespaceURI2 = elementImpl.getNamespaceURI();
        String prefix2 = elementImpl.getPrefix();
        if (namespaceURI2 != null) {
            String strAddSymbol5 = this.fSymbolTable.addSymbol(namespaceURI2);
            String strAddSymbol6 = (prefix2 == null || prefix2.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix2);
            if (this.fNamespaceContext.getURI(strAddSymbol6) != strAddSymbol5) {
                addNamespaceDecl(strAddSymbol6, strAddSymbol5, elementImpl);
                this.fLocalNSBinder.declarePrefix(strAddSymbol6, strAddSymbol5);
                this.fNamespaceContext.declarePrefix(strAddSymbol6, strAddSymbol5);
            }
        } else if (elementImpl.getLocalName() == null) {
            if (this.fNamespaceValidation) {
                message = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NullLocalElementName", new Object[]{elementImpl.getNodeName()});
                dOMErrorHandler = this.fErrorHandler;
                dOMErrorImpl = this.fError;
                dOMLocatorImpl = this.fLocator;
                s = 3;
            } else {
                message = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NullLocalElementName", new Object[]{elementImpl.getNodeName()});
                dOMErrorHandler = this.fErrorHandler;
                dOMErrorImpl = this.fError;
                dOMLocatorImpl = this.fLocator;
                s = 2;
            }
            reportDOMError(dOMErrorHandler, dOMErrorImpl, dOMLocatorImpl, message, s, "NullLocalElementName");
        } else {
            String uri = this.fNamespaceContext.getURI(XMLSymbols.EMPTY_STRING);
            if (uri != null && uri.length() > 0) {
                addNamespaceDecl(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING, elementImpl);
                this.fLocalNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, null);
                this.fNamespaceContext.declarePrefix(XMLSymbols.EMPTY_STRING, null);
            }
        }
        if (attributeMap != null) {
            attributeMap.cloneMap(this.fAttributeList);
            for (int i2 = 0; i2 < this.fAttributeList.size(); i2++) {
                Attr attr2 = (Attr) this.fAttributeList.get(i2);
                this.fLocator.fRelatedNode = attr2;
                attr2.normalize();
                String value = attr2.getValue();
                String namespaceURI3 = attr2.getNamespaceURI();
                if (value == null) {
                    value = XMLSymbols.EMPTY_STRING;
                }
                String str2 = value;
                if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0) {
                    isAttrValueWF(this.fErrorHandler, this.fError, this.fLocator, attributeMap, attr2, str2, this.fDocument.isXML11Version());
                    if (this.fDocument.isXMLVersionChanged()) {
                        if (!(this.fNamespaceValidation ? CoreDocumentImpl.isValidQName(attr2.getPrefix(), attr2.getLocalName(), this.fDocument.isXML11Version()) : CoreDocumentImpl.isXMLName(attr2.getNodeName(), this.fDocument.isXML11Version()))) {
                            reportDOMError(this.fErrorHandler, this.fError, this.fLocator, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "wf-invalid-character-in-node-name", new Object[]{"Attr", attr2.getNodeName()}), (short) 2, "wf-invalid-character-in-node-name");
                        }
                    }
                }
                if (namespaceURI3 != null) {
                    String prefix3 = attr2.getPrefix();
                    String strAddSymbol7 = (prefix3 == null || prefix3.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix3);
                    this.fSymbolTable.addSymbol(attr2.getLocalName());
                    if (namespaceURI3 == null || !namespaceURI3.equals(NamespaceContext.XMLNS_URI)) {
                        ((AttrImpl) attr2).setIdAttribute(false);
                        String strAddSymbol8 = this.fSymbolTable.addSymbol(namespaceURI3);
                        String uri2 = this.fNamespaceContext.getURI(strAddSymbol7);
                        if (strAddSymbol7 == XMLSymbols.EMPTY_STRING || uri2 != strAddSymbol8) {
                            String prefix4 = this.fNamespaceContext.getPrefix(strAddSymbol8);
                            if (prefix4 == null || prefix4 == XMLSymbols.EMPTY_STRING) {
                                if (strAddSymbol7 == XMLSymbols.EMPTY_STRING || this.fLocalNSBinder.getURI(strAddSymbol7) != null) {
                                    strAddSymbol7 = this.fSymbolTable.addSymbol("NS1");
                                    int i3 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol7) != null) {
                                        strAddSymbol7 = this.fSymbolTable.addSymbol(PREFIX + i3);
                                        i3++;
                                    }
                                }
                                addNamespaceDecl(strAddSymbol7, strAddSymbol8, elementImpl);
                                this.fLocalNSBinder.declarePrefix(strAddSymbol7, this.fSymbolTable.addSymbol(str2));
                                this.fNamespaceContext.declarePrefix(strAddSymbol7, strAddSymbol8);
                                prefix4 = strAddSymbol7;
                            }
                            attr2.setPrefix(prefix4);
                        }
                    }
                } else {
                    ((AttrImpl) attr2).setIdAttribute(false);
                    if (attr2.getLocalName() == null) {
                        if (this.fNamespaceValidation) {
                            message2 = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NullLocalAttrName", new Object[]{attr2.getNodeName()});
                            dOMErrorHandler2 = this.fErrorHandler;
                            dOMErrorImpl2 = this.fError;
                            dOMLocatorImpl2 = this.fLocator;
                            s2 = 3;
                        } else {
                            message2 = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NullLocalAttrName", new Object[]{attr2.getNodeName()});
                            dOMErrorHandler2 = this.fErrorHandler;
                            dOMErrorImpl2 = this.fError;
                            dOMLocatorImpl2 = this.fLocator;
                            s2 = 2;
                        }
                        reportDOMError(dOMErrorHandler2, dOMErrorImpl2, dOMLocatorImpl2, message2, s2, "NullLocalAttrName");
                    }
                }
            }
        }
    }

    final String normalizeAttributeValue(String str, Attr attr) {
        if (!attr.getSpecified()) {
            return str;
        }
        int length = str.length();
        if (this.fNormalizedValue.ch.length < length) {
            this.fNormalizedValue.ch = new char[length];
        }
        int i = 0;
        this.fNormalizedValue.length = 0;
        boolean z = false;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\t' || cCharAt == '\n') {
                char[] cArr = this.fNormalizedValue.ch;
                XMLString xMLString = this.fNormalizedValue;
                int i2 = xMLString.length;
                xMLString.length = i2 + 1;
                cArr[i2] = StringUtil.SPACE;
            } else {
                if (cCharAt == '\r') {
                    char[] cArr2 = this.fNormalizedValue.ch;
                    XMLString xMLString2 = this.fNormalizedValue;
                    int i3 = xMLString2.length;
                    xMLString2.length = i3 + 1;
                    cArr2[i3] = StringUtil.SPACE;
                    int i4 = i + 1;
                    if (i4 < length && str.charAt(i4) == '\n') {
                        i = i4;
                    }
                } else {
                    char[] cArr3 = this.fNormalizedValue.ch;
                    XMLString xMLString3 = this.fNormalizedValue;
                    int i5 = xMLString3.length;
                    xMLString3.length = i5 + 1;
                    cArr3[i5] = cCharAt;
                }
                i++;
            }
            z = true;
            i++;
        }
        if (!z) {
            return str;
        }
        String string = this.fNormalizedValue.toString();
        attr.setValue(string);
        return string;
    }

    protected void normalizeDocument(CoreDocumentImpl coreDocumentImpl, DOMConfigurationImpl dOMConfigurationImpl) {
        String[] strArr;
        String str;
        String str2;
        this.fDocument = coreDocumentImpl;
        this.fConfiguration = dOMConfigurationImpl;
        this.fAllWhitespace = false;
        this.fNamespaceValidation = false;
        String xmlVersion = coreDocumentImpl.getXmlVersion();
        this.fSymbolTable = (SymbolTable) this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/symbol-table");
        this.fNamespaceContext.reset();
        this.fNamespaceContext.declarePrefix(XMLSymbols.EMPTY_STRING, null);
        if ((this.fConfiguration.features & 64) != 0) {
            String str3 = (String) this.fConfiguration.getProperty(JAXPConstants.JAXP_SCHEMA_LANGUAGE);
            if (str3 == null || !str3.equals(Constants.NS_XMLSCHEMA)) {
                strArr = str3 != null ? (String[]) this.fConfiguration.getProperty(JAXPConstants.JAXP_SCHEMA_SOURCE) : null;
                this.fConfiguration.setDTDValidatorFactory(xmlVersion);
                this.fValidationHandler = CoreDOMImplementationImpl.singleton.getValidator("http://www.w3.org/TR/REC-xml", xmlVersion);
                this.fPSVI = false;
                str = "http://www.w3.org/TR/REC-xml";
            } else {
                str = "http://www.w3.org/2001/XMLSchema";
                this.fValidationHandler = CoreDOMImplementationImpl.singleton.getValidator("http://www.w3.org/2001/XMLSchema", xmlVersion);
                this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema", true);
                this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
                this.fNamespaceValidation = true;
                this.fPSVI = (this.fConfiguration.features & 128) != 0;
                strArr = null;
            }
            this.fConfiguration.setFeature("http://xml.org/sax/features/validation", true);
            this.fDocument.clearIdentifiers();
            RevalidationHandler revalidationHandler = this.fValidationHandler;
            if (revalidationHandler != null) {
                ((XMLComponent) revalidationHandler).reset(this.fConfiguration);
            }
        } else {
            this.fValidationHandler = null;
            strArr = null;
            str = null;
        }
        this.fErrorHandler = (DOMErrorHandler) this.fConfiguration.getParameter(Constants.DOM_ERROR_HANDLER);
        RevalidationHandler revalidationHandler2 = this.fValidationHandler;
        if (revalidationHandler2 != null) {
            revalidationHandler2.setDocumentHandler(this);
            this.fValidationHandler.startDocument(new SimpleLocator(this.fDocument.fDocumentURI, this.fDocument.fDocumentURI, -1, -1), this.fDocument.encoding, this.fNamespaceContext, null);
            this.fValidationHandler.xmlDecl(this.fDocument.getXmlVersion(), this.fDocument.getXmlEncoding(), this.fDocument.getXmlStandalone() ? "yes" : "no", null);
        }
        if (str == "http://www.w3.org/TR/REC-xml") {
            if (strArr != null) {
                try {
                    str2 = strArr[0];
                } catch (RuntimeException e) {
                    RevalidationHandler revalidationHandler3 = this.fValidationHandler;
                    if (revalidationHandler3 != null) {
                        revalidationHandler3.setDocumentHandler(null);
                        CoreDOMImplementationImpl.singleton.releaseValidator(str, xmlVersion, this.fValidationHandler);
                        this.fValidationHandler = null;
                    }
                    if (e != abort) {
                        throw e;
                    }
                    return;
                }
            } else {
                str2 = null;
            }
            processDTD(xmlVersion, str2);
        }
        Node firstChild = this.fDocument.getFirstChild();
        while (firstChild != null) {
            Node nextSibling = firstChild.getNextSibling();
            firstChild = normalizeNode(firstChild);
            if (firstChild == null) {
                firstChild = nextSibling;
            }
        }
        RevalidationHandler revalidationHandler4 = this.fValidationHandler;
        if (revalidationHandler4 != null) {
            revalidationHandler4.endDocument(null);
            this.fValidationHandler.setDocumentHandler(null);
            CoreDOMImplementationImpl.singleton.releaseValidator(str, xmlVersion, this.fValidationHandler);
            this.fValidationHandler = null;
        }
    }

    protected Node normalizeNode(Node node) {
        Node nextSibling;
        Node parentNode;
        short nodeType = node.getNodeType();
        this.fLocator.fRelatedNode = node;
        int i = 0;
        if (nodeType == 1) {
            if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0 && this.fDocument.isXMLVersionChanged()) {
                if (!(this.fNamespaceValidation ? CoreDocumentImpl.isValidQName(node.getPrefix(), node.getLocalName(), this.fDocument.isXML11Version()) : CoreDocumentImpl.isXMLName(node.getNodeName(), this.fDocument.isXML11Version()))) {
                    reportDOMError(this.fErrorHandler, this.fError, this.fLocator, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "wf-invalid-character-in-node-name", new Object[]{"Element", node.getNodeName()}), (short) 2, "wf-invalid-character-in-node-name");
                }
            }
            this.fNamespaceContext.pushContext();
            this.fLocalNSBinder.reset();
            ElementImpl elementImpl = (ElementImpl) node;
            if (elementImpl.needsSyncChildren()) {
                elementImpl.synchronizeChildren();
            }
            AttributeMap attributeMap = elementImpl.hasAttributes() ? (AttributeMap) elementImpl.getAttributes() : null;
            if ((this.fConfiguration.features & 1) != 0) {
                namespaceFixUp(elementImpl, attributeMap);
                if ((this.fConfiguration.features & XSSimpleTypeDefinition.FACET_TOTALDIGITS) == 0) {
                    if (attributeMap == null) {
                        attributeMap = elementImpl.hasAttributes() ? (AttributeMap) elementImpl.getAttributes() : null;
                    }
                    if (attributeMap != null) {
                        while (i < attributeMap.getLength()) {
                            Attr attr = (Attr) attributeMap.getItem(i);
                            if (XMLSymbols.PREFIX_XMLNS.equals(attr.getPrefix()) || XMLSymbols.PREFIX_XMLNS.equals(attr.getName())) {
                                elementImpl.removeAttributeNode(attr);
                                i--;
                            }
                            i++;
                        }
                    }
                }
            } else if (attributeMap != null) {
                while (i < attributeMap.getLength()) {
                    Attr attr2 = (Attr) attributeMap.item(i);
                    attr2.normalize();
                    if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0) {
                        isAttrValueWF(this.fErrorHandler, this.fError, this.fLocator, attributeMap, attr2, attr2.getValue(), this.fDocument.isXML11Version());
                        if (this.fDocument.isXMLVersionChanged()) {
                            if (!(this.fNamespaceValidation ? CoreDocumentImpl.isValidQName(node.getPrefix(), node.getLocalName(), this.fDocument.isXML11Version()) : CoreDocumentImpl.isXMLName(node.getNodeName(), this.fDocument.isXML11Version()))) {
                                reportDOMError(this.fErrorHandler, this.fError, this.fLocator, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "wf-invalid-character-in-node-name", new Object[]{"Attr", node.getNodeName()}), (short) 2, "wf-invalid-character-in-node-name");
                            }
                        }
                    }
                    i++;
                }
            }
            if (this.fValidationHandler != null) {
                this.fAttrProxy.setAttributes(attributeMap, this.fDocument, elementImpl);
                updateQName(elementImpl, this.fQName);
                this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
                this.fCurrentNode = node;
                this.fValidationHandler.startElement(this.fQName, this.fAttrProxy, null);
            }
            Node firstChild = elementImpl.getFirstChild();
            while (firstChild != null) {
                Node nextSibling2 = firstChild.getNextSibling();
                firstChild = normalizeNode(firstChild);
                if (firstChild == null) {
                    firstChild = nextSibling2;
                }
            }
            if (this.fValidationHandler != null) {
                updateQName(elementImpl, this.fQName);
                this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
                this.fCurrentNode = node;
                this.fValidationHandler.endElement(this.fQName, null);
            }
            this.fNamespaceContext.popContext();
        } else if (nodeType == 3) {
            nextSibling = node.getNextSibling();
            if (nextSibling != null && nextSibling.getNodeType() == 3) {
                ((Text) node).appendData(nextSibling.getNodeValue());
                parentNode = node.getParentNode();
                parentNode.removeChild(nextSibling);
                return node;
            }
            if (node.getNodeValue().length() == 0) {
                node.getParentNode().removeChild(node);
            } else {
                short nodeType2 = nextSibling != null ? nextSibling.getNodeType() : (short) -1;
                if (nodeType2 == -1 || (((this.fConfiguration.features & 4) != 0 || nodeType2 != 6) && (((this.fConfiguration.features & 32) != 0 || nodeType2 != 8) && ((this.fConfiguration.features & 8) != 0 || nodeType2 != 4)))) {
                    if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0) {
                        isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, node.getNodeValue(), this.fDocument.isXML11Version());
                    }
                    if (this.fValidationHandler != null) {
                        this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
                        this.fCurrentNode = node;
                        this.fValidationHandler.characterData(node.getNodeValue(), null);
                        if (!this.fNamespaceValidation) {
                            if (this.fAllWhitespace) {
                                this.fAllWhitespace = false;
                                ((TextImpl) node).setIgnorableWhitespace(true);
                            } else {
                                ((TextImpl) node).setIgnorableWhitespace(false);
                            }
                        }
                    }
                }
            }
        } else if (nodeType != 4) {
            if (nodeType != 5) {
                if (nodeType == 7) {
                    if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0) {
                        ProcessingInstruction processingInstruction = (ProcessingInstruction) node;
                        String target = processingInstruction.getTarget();
                        if (!(this.fDocument.isXML11Version() ? XML11Char.isXML11ValidName(target) : XMLChar.isValidName(target))) {
                            reportDOMError(this.fErrorHandler, this.fError, this.fLocator, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "wf-invalid-character-in-node-name", new Object[]{"Element", node.getNodeName()}), (short) 2, "wf-invalid-character-in-node-name");
                        }
                        isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, processingInstruction.getData(), this.fDocument.isXML11Version());
                    }
                    RevalidationHandler revalidationHandler = this.fValidationHandler;
                    if (revalidationHandler != null) {
                        revalidationHandler.processingInstruction(((ProcessingInstruction) node).getTarget(), EMPTY_STRING, null);
                    }
                } else if (nodeType == 8) {
                    if ((this.fConfiguration.features & 32) == 0) {
                        nextSibling = node.getPreviousSibling();
                        parentNode = node.getParentNode();
                        parentNode.removeChild(node);
                        if (nextSibling != null && nextSibling.getNodeType() == 3 && (node = nextSibling.getNextSibling()) != null && node.getNodeType() == 3) {
                            ((TextImpl) node).insertData(0, nextSibling.getNodeValue());
                            parentNode.removeChild(nextSibling);
                            return node;
                        }
                    } else {
                        if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0) {
                            isCommentWF(this.fErrorHandler, this.fError, this.fLocator, ((Comment) node).getData(), this.fDocument.isXML11Version());
                        }
                        RevalidationHandler revalidationHandler2 = this.fValidationHandler;
                        if (revalidationHandler2 != null) {
                            revalidationHandler2.comment(EMPTY_STRING, null);
                        }
                    }
                }
            } else {
                if ((this.fConfiguration.features & 4) == 0) {
                    Node previousSibling = node.getPreviousSibling();
                    Node parentNode2 = node.getParentNode();
                    ((EntityReferenceImpl) node).setReadOnly(false, true);
                    expandEntityRef(parentNode2, node);
                    parentNode2.removeChild(node);
                    Node nextSibling3 = previousSibling != null ? previousSibling.getNextSibling() : parentNode2.getFirstChild();
                    return (previousSibling == null || nextSibling3 == null || previousSibling.getNodeType() != 3 || nextSibling3.getNodeType() != 3) ? nextSibling3 : previousSibling;
                }
                if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0 && this.fDocument.isXMLVersionChanged()) {
                    CoreDocumentImpl.isXMLName(node.getNodeName(), this.fDocument.isXML11Version());
                }
            }
        } else {
            if ((this.fConfiguration.features & 8) == 0) {
                Node previousSibling2 = node.getPreviousSibling();
                if (previousSibling2 == null || previousSibling2.getNodeType() != 3) {
                    Text textCreateTextNode = this.fDocument.createTextNode(node.getNodeValue());
                    node.getParentNode().replaceChild(textCreateTextNode, node);
                    return textCreateTextNode;
                }
                ((Text) previousSibling2).appendData(node.getNodeValue());
                node.getParentNode().removeChild(node);
                return previousSibling2;
            }
            if (this.fValidationHandler != null) {
                this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = node;
                this.fCurrentNode = node;
                this.fValidationHandler.startCDATA(null);
                this.fValidationHandler.characterData(node.getNodeValue(), null);
                this.fValidationHandler.endCDATA(null);
            }
            String nodeValue = node.getNodeValue();
            if ((this.fConfiguration.features & 16) != 0) {
                Node parentNode3 = node.getParentNode();
                if (this.fDocument.errorChecking) {
                    isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, node.getNodeValue(), this.fDocument.isXML11Version());
                }
                while (true) {
                    int iIndexOf = nodeValue.indexOf("]]>");
                    if (iIndexOf < 0) {
                        break;
                    }
                    int i2 = iIndexOf + 2;
                    node.setNodeValue(nodeValue.substring(0, i2));
                    nodeValue = nodeValue.substring(i2);
                    CDATASection cDATASectionCreateCDATASection = this.fDocument.createCDATASection(nodeValue);
                    parentNode3.insertBefore(cDATASectionCreateCDATASection, node.getNextSibling());
                    this.fLocator.fRelatedNode = node;
                    reportDOMError(this.fErrorHandler, this.fError, this.fLocator, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "cdata-sections-splitted", null), (short) 1, "cdata-sections-splitted");
                    node = cDATASectionCreateCDATASection;
                }
            } else if (this.fDocument.errorChecking) {
                isCDataWF(this.fErrorHandler, this.fError, this.fLocator, nodeValue, this.fDocument.isXML11Version());
            }
        }
        return null;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void processingInstruction(String str, XMLString xMLString, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void setDocumentSource(XMLDocumentSource xMLDocumentSource) {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startCDATA(Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startDocument(XMLLocator xMLLocator, String str, NamespaceContext namespaceContext, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startElement(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations) throws XNIException {
        String type;
        String schemaNormalizedValue;
        Element element = (Element) this.fCurrentNode;
        int length = xMLAttributes.getLength();
        for (int i = 0; i < length; i++) {
            xMLAttributes.getName(i, this.fAttrQName);
            Attr attributeNodeNS = element.getAttributeNodeNS(this.fAttrQName.uri, this.fAttrQName.localpart);
            if (attributeNodeNS == null) {
                attributeNodeNS = element.getAttributeNode(this.fAttrQName.rawname);
            }
            AttributePSVI attributePSVI = (AttributePSVI) xMLAttributes.getAugmentations(i).getItem(Constants.ATTRIBUTE_PSVI);
            if (attributePSVI != null) {
                XSTypeDefinition memberTypeDefinition = attributePSVI.getMemberTypeDefinition();
                if ((memberTypeDefinition == null && (memberTypeDefinition = attributePSVI.getTypeDefinition()) == null) ? false : ((XSSimpleType) memberTypeDefinition).isIDType()) {
                    ((ElementImpl) element).setIdAttributeNode(attributeNodeNS, true);
                }
                if (this.fPSVI) {
                    ((PSVIAttrNSImpl) attributeNodeNS).setPSVI(attributePSVI);
                }
                AttrImpl attrImpl = (AttrImpl) attributeNodeNS;
                attrImpl.setType(memberTypeDefinition);
                if ((this.fConfiguration.features & 2) != 0 && (schemaNormalizedValue = attributePSVI.getSchemaNormalizedValue()) != null) {
                    boolean specified = attributeNodeNS.getSpecified();
                    attributeNodeNS.setValue(schemaNormalizedValue);
                    if (!specified) {
                        attrImpl.setSpecified(specified);
                    }
                }
            } else {
                if (Boolean.TRUE.equals(xMLAttributes.getAugmentations(i).getItem(Constants.ATTRIBUTE_DECLARED))) {
                    type = xMLAttributes.getType(i);
                    if (SchemaSymbols.ATTVAL_ID.equals(type)) {
                        ((ElementImpl) element).setIdAttributeNode(attributeNodeNS, true);
                    }
                } else {
                    type = null;
                }
                ((AttrImpl) attributeNodeNS).setType(type);
            }
        }
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void startGeneralEntity(String str, XMLResourceIdentifier xMLResourceIdentifier, String str2, Augmentations augmentations) throws XNIException {
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void textDecl(String str, String str2, Augmentations augmentations) throws XNIException {
    }

    protected final void updateQName(Node node, QName qName) {
        String prefix = node.getPrefix();
        String namespaceURI = node.getNamespaceURI();
        String localName = node.getLocalName();
        qName.prefix = (prefix == null || prefix.length() == 0) ? null : this.fSymbolTable.addSymbol(prefix);
        qName.localpart = localName != null ? this.fSymbolTable.addSymbol(localName) : null;
        qName.rawname = this.fSymbolTable.addSymbol(node.getNodeName());
        qName.uri = namespaceURI != null ? this.fSymbolTable.addSymbol(namespaceURI) : null;
    }

    @Override // org.apache.xerces.xni.XMLDocumentHandler
    public void xmlDecl(String str, String str2, String str3, Augmentations augmentations) throws XNIException {
    }
}
