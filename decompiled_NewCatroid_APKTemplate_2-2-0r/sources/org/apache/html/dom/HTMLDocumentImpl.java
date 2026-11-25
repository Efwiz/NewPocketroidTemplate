package org.apache.html.dom;

import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Locale;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.bouncycastle.asn1.ASN1Encoding;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLBodyElement;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLDocument;
import org.w3c.dom.html.HTMLElement;
import org.w3c.dom.html.HTMLFrameSetElement;
import org.w3c.dom.html.HTMLHeadElement;
import org.w3c.dom.html.HTMLHtmlElement;
import org.w3c.dom.html.HTMLTitleElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLDocumentImpl extends DocumentImpl implements HTMLDocument {
    private static final Class[] _elemClassSigHTML = {HTMLDocumentImpl.class, String.class};
    private static Hashtable _elementTypesHTML = null;
    private static final long serialVersionUID = 4285791750126227180L;
    private HTMLCollectionImpl _anchors;
    private HTMLCollectionImpl _applets;
    private HTMLCollectionImpl _forms;
    private HTMLCollectionImpl _images;
    private HTMLCollectionImpl _links;
    private StringWriter _writer;

    public HTMLDocumentImpl() {
        populateElementTypes();
    }

    private Element getElementById(String str, Node node) {
        for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof Element) {
                Element element = (Element) firstChild;
                if (str.equals(element.getAttribute("id"))) {
                    return element;
                }
                Element elementById = getElementById(str, firstChild);
                if (elementById != null) {
                    return elementById;
                }
            }
        }
        return null;
    }

    private static void populateElementType(String str, String str2) {
        try {
            _elementTypesHTML.put(str, ObjectFactory.findProviderClass("org.apache.html.dom." + str2, HTMLDocumentImpl.class.getClassLoader(), true));
        } catch (Exception unused) {
            throw new RuntimeException("HTM019 OpenXML Error: Could not find or execute class " + str2 + " implementing HTML element " + str + "\n" + str2 + "\t" + str);
        }
    }

    private static synchronized void populateElementTypes() {
        if (_elementTypesHTML != null) {
            return;
        }
        _elementTypesHTML = new Hashtable(63);
        populateElementType("A", "HTMLAnchorElementImpl");
        populateElementType("APPLET", "HTMLAppletElementImpl");
        populateElementType("AREA", "HTMLAreaElementImpl");
        populateElementType("BASE", "HTMLBaseElementImpl");
        populateElementType("BASEFONT", "HTMLBaseFontElementImpl");
        populateElementType("BLOCKQUOTE", "HTMLQuoteElementImpl");
        populateElementType("BODY", "HTMLBodyElementImpl");
        populateElementType("BR", "HTMLBRElementImpl");
        populateElementType("BUTTON", "HTMLButtonElementImpl");
        populateElementType("DEL", "HTMLModElementImpl");
        populateElementType("DIR", "HTMLDirectoryElementImpl");
        populateElementType("DIV", "HTMLDivElementImpl");
        populateElementType(ASN1Encoding.DL, "HTMLDListElementImpl");
        populateElementType("FIELDSET", "HTMLFieldSetElementImpl");
        populateElementType("FONT", "HTMLFontElementImpl");
        populateElementType("FORM", "HTMLFormElementImpl");
        populateElementType("FRAME", "HTMLFrameElementImpl");
        populateElementType("FRAMESET", "HTMLFrameSetElementImpl");
        populateElementType("HEAD", "HTMLHeadElementImpl");
        populateElementType("H1", "HTMLHeadingElementImpl");
        populateElementType("H2", "HTMLHeadingElementImpl");
        populateElementType("H3", "HTMLHeadingElementImpl");
        populateElementType("H4", "HTMLHeadingElementImpl");
        populateElementType("H5", "HTMLHeadingElementImpl");
        populateElementType("H6", "HTMLHeadingElementImpl");
        populateElementType("HR", "HTMLHRElementImpl");
        populateElementType("HTML", "HTMLHtmlElementImpl");
        populateElementType("IFRAME", "HTMLIFrameElementImpl");
        populateElementType("IMG", "HTMLImageElementImpl");
        populateElementType("INPUT", "HTMLInputElementImpl");
        populateElementType("INS", "HTMLModElementImpl");
        populateElementType("ISINDEX", "HTMLIsIndexElementImpl");
        populateElementType("LABEL", "HTMLLabelElementImpl");
        populateElementType("LEGEND", "HTMLLegendElementImpl");
        populateElementType("LI", "HTMLLIElementImpl");
        populateElementType("LINK", "HTMLLinkElementImpl");
        populateElementType("MAP", "HTMLMapElementImpl");
        populateElementType("MENU", "HTMLMenuElementImpl");
        populateElementType("META", "HTMLMetaElementImpl");
        populateElementType(Namer.CLASS_KIND_OBJECT, "HTMLObjectElementImpl");
        populateElementType("OL", "HTMLOListElementImpl");
        populateElementType("OPTGROUP", "HTMLOptGroupElementImpl");
        populateElementType("OPTION", "HTMLOptionElementImpl");
        populateElementType("P", "HTMLParagraphElementImpl");
        populateElementType("PARAM", "HTMLParamElementImpl");
        populateElementType("PRE", "HTMLPreElementImpl");
        populateElementType("Q", "HTMLQuoteElementImpl");
        populateElementType("SCRIPT", "HTMLScriptElementImpl");
        populateElementType("SELECT", "HTMLSelectElementImpl");
        populateElementType("STYLE", "HTMLStyleElementImpl");
        populateElementType("TABLE", "HTMLTableElementImpl");
        populateElementType("CAPTION", "HTMLTableCaptionElementImpl");
        populateElementType("TD", "HTMLTableCellElementImpl");
        populateElementType("TH", "HTMLTableCellElementImpl");
        populateElementType("COL", "HTMLTableColElementImpl");
        populateElementType("COLGROUP", "HTMLTableColElementImpl");
        populateElementType("TR", "HTMLTableRowElementImpl");
        populateElementType("TBODY", "HTMLTableSectionElementImpl");
        populateElementType("THEAD", "HTMLTableSectionElementImpl");
        populateElementType("TFOOT", "HTMLTableSectionElementImpl");
        populateElementType("TEXTAREA", "HTMLTextAreaElementImpl");
        populateElementType("TITLE", "HTMLTitleElementImpl");
        populateElementType("UL", "HTMLUListElementImpl");
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    protected boolean canRenameElements(String str, String str2, ElementImpl elementImpl) {
        if (elementImpl.getNamespaceURI() != null) {
            return str != null;
        }
        return ((Class) _elementTypesHTML.get(str2.toUpperCase(Locale.ENGLISH))) == ((Class) _elementTypesHTML.get(elementImpl.getTagName()));
    }

    @Override // org.apache.xerces.dom.DocumentImpl, org.apache.xerces.dom.CoreDocumentImpl, org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.ChildNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node cloneNode(boolean z) {
        CoreDocumentImpl hTMLDocumentImpl = new HTMLDocumentImpl();
        callUserDataHandlers(this, hTMLDocumentImpl, (short) 1);
        cloneNode(hTMLDocumentImpl, z);
        return hTMLDocumentImpl;
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public void close() {
        if (this._writer != null) {
            this._writer = null;
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl, org.w3c.dom.Document
    public Attr createAttribute(String str) throws DOMException {
        return super.createAttribute(str.toLowerCase(Locale.ENGLISH));
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl, org.w3c.dom.Document
    public Element createElement(String str) throws DOMException {
        String upperCase = str.toUpperCase(Locale.ENGLISH);
        Class cls = (Class) _elementTypesHTML.get(upperCase);
        if (cls == null) {
            return new HTMLElementImpl(this, upperCase);
        }
        try {
            return (Element) cls.getConstructor(_elemClassSigHTML).newInstance(this, upperCase);
        } catch (Exception unused) {
            throw new IllegalStateException("HTM15 Tag '" + upperCase + "' associated with an Element class that failed to construct.\n" + upperCase);
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl, org.w3c.dom.Document
    public Element createElementNS(String str, String str2) {
        return (str == null || str.length() == 0) ? createElement(str2) : super.createElementNS(str, str2);
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    public Element createElementNS(String str, String str2, String str3) throws DOMException {
        return createElementNS(str, str2);
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public HTMLCollection getAnchors() {
        if (this._anchors == null) {
            this._anchors = new HTMLCollectionImpl(getBody(), (short) 1);
        }
        return this._anchors;
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public HTMLCollection getApplets() {
        if (this._applets == null) {
            this._applets = new HTMLCollectionImpl(getBody(), (short) 4);
        }
        return this._applets;
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public synchronized HTMLElement getBody() {
        Element documentElement = getDocumentElement();
        HTMLElement head = getHead();
        synchronized (documentElement) {
            Node nextSibling = head.getNextSibling();
            while (nextSibling != null && !(nextSibling instanceof HTMLBodyElement) && !(nextSibling instanceof HTMLFrameSetElement)) {
                nextSibling = nextSibling.getNextSibling();
            }
            if (nextSibling == null) {
                HTMLBodyElementImpl hTMLBodyElementImpl = new HTMLBodyElementImpl(this, "BODY");
                documentElement.appendChild(hTMLBodyElementImpl);
                return hTMLBodyElementImpl;
            }
            synchronized (nextSibling) {
                Node nextSibling2 = head.getNextSibling();
                while (nextSibling2 != null && nextSibling2 != nextSibling) {
                    Node nextSibling3 = nextSibling2.getNextSibling();
                    nextSibling.insertBefore(nextSibling2, nextSibling.getFirstChild());
                    nextSibling2 = nextSibling3;
                }
            }
            return (HTMLElement) nextSibling;
        }
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public String getCookie() {
        return null;
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl, org.w3c.dom.Document
    public synchronized Element getDocumentElement() {
        for (Node firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof HTMLHtmlElement) {
                return (HTMLElement) firstChild;
            }
        }
        HTMLHtmlElementImpl hTMLHtmlElementImpl = new HTMLHtmlElementImpl(this, "HTML");
        Node firstChild2 = getFirstChild();
        while (firstChild2 != null) {
            Node nextSibling = firstChild2.getNextSibling();
            hTMLHtmlElementImpl.appendChild(firstChild2);
            firstChild2 = nextSibling;
        }
        appendChild(hTMLHtmlElementImpl);
        return hTMLHtmlElementImpl;
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public String getDomain() {
        return null;
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl, org.w3c.dom.Document, org.w3c.dom.html.HTMLDocument
    public synchronized Element getElementById(String str) {
        Element elementById = super.getElementById(str);
        if (elementById != null) {
            return elementById;
        }
        return getElementById(str, this);
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public NodeList getElementsByName(String str) {
        return new NameNodeListImpl(this, str);
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl, org.w3c.dom.Document
    public final NodeList getElementsByTagName(String str) {
        return super.getElementsByTagName(str.toUpperCase(Locale.ENGLISH));
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl, org.w3c.dom.Document
    public final NodeList getElementsByTagNameNS(String str, String str2) {
        return (str == null || str.length() <= 0) ? super.getElementsByTagName(str2.toUpperCase(Locale.ENGLISH)) : super.getElementsByTagNameNS(str, str2.toUpperCase(Locale.ENGLISH));
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public HTMLCollection getForms() {
        if (this._forms == null) {
            this._forms = new HTMLCollectionImpl(getBody(), (short) 2);
        }
        return this._forms;
    }

    public synchronized HTMLElement getHead() {
        Element documentElement = getDocumentElement();
        synchronized (documentElement) {
            Node firstChild = documentElement.getFirstChild();
            while (firstChild != null && !(firstChild instanceof HTMLHeadElement)) {
                firstChild = firstChild.getNextSibling();
            }
            if (firstChild == null) {
                HTMLHeadElementImpl hTMLHeadElementImpl = new HTMLHeadElementImpl(this, "HEAD");
                documentElement.insertBefore(hTMLHeadElementImpl, documentElement.getFirstChild());
                return hTMLHeadElementImpl;
            }
            synchronized (firstChild) {
                Node firstChild2 = documentElement.getFirstChild();
                while (firstChild2 != null && firstChild2 != firstChild) {
                    Node nextSibling = firstChild2.getNextSibling();
                    firstChild.insertBefore(firstChild2, firstChild.getFirstChild());
                    firstChild2 = nextSibling;
                }
            }
            return (HTMLElement) firstChild;
        }
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public HTMLCollection getImages() {
        if (this._images == null) {
            this._images = new HTMLCollectionImpl(getBody(), (short) 3);
        }
        return this._images;
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public HTMLCollection getLinks() {
        if (this._links == null) {
            this._links = new HTMLCollectionImpl(getBody(), (short) 5);
        }
        return this._links;
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public String getReferrer() {
        return null;
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public synchronized String getTitle() {
        NodeList elementsByTagName = getHead().getElementsByTagName("TITLE");
        if (elementsByTagName.getLength() <= 0) {
            return "";
        }
        return ((HTMLTitleElement) elementsByTagName.item(0)).getText();
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public String getURL() {
        return null;
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public void open() {
        if (this._writer == null) {
            this._writer = new StringWriter();
        }
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public synchronized void setBody(HTMLElement hTMLElement) {
        synchronized (hTMLElement) {
            Element documentElement = getDocumentElement();
            synchronized (documentElement) {
                NodeList elementsByTagName = getElementsByTagName("BODY");
                if (elementsByTagName.getLength() <= 0) {
                    documentElement.appendChild(hTMLElement);
                    return;
                }
                Node nodeItem = elementsByTagName.item(0);
                synchronized (nodeItem) {
                    for (Node head = getHead(); head != null; head = head.getNextSibling()) {
                        if (head instanceof Element) {
                            if (head != nodeItem) {
                                documentElement.insertBefore(hTMLElement, head);
                            } else {
                                documentElement.replaceChild(hTMLElement, nodeItem);
                            }
                            return;
                        }
                    }
                    documentElement.appendChild(hTMLElement);
                }
            }
        }
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public void setCookie(String str) {
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public synchronized void setTitle(String str) {
        HTMLElement head = getHead();
        NodeList elementsByTagName = head.getElementsByTagName("TITLE");
        if (elementsByTagName.getLength() > 0) {
            Node nodeItem = elementsByTagName.item(0);
            if (nodeItem.getParentNode() != head) {
                head.appendChild(nodeItem);
            }
            ((HTMLTitleElement) nodeItem).setText(str);
        } else {
            HTMLTitleElementImpl hTMLTitleElementImpl = new HTMLTitleElementImpl(this, "TITLE");
            hTMLTitleElementImpl.setText(str);
            head.appendChild(hTMLTitleElementImpl);
        }
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public void write(String str) {
        StringWriter stringWriter = this._writer;
        if (stringWriter != null) {
            stringWriter.write(str);
        }
    }

    @Override // org.w3c.dom.html.HTMLDocument
    public void writeln(String str) {
        StringWriter stringWriter = this._writer;
        if (stringWriter != null) {
            stringWriter.write(str + "\n");
        }
    }
}
