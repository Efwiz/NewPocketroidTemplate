package org.apache.xml.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import javax.xml.XMLConstants;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.NamespaceContext;
import org.catrobat.catroid.BuildConfig;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/* JADX INFO: loaded from: classes13.dex */
public class XMLSerializer extends BaseMarkupSerializer {
    protected static final boolean DEBUG = false;
    protected static final String PREFIX = "NS";
    protected NamespaceSupport fLocalNSBinder;
    protected NamespaceSupport fNSBinder;
    protected boolean fNamespacePrefixes;
    protected boolean fNamespaces;
    private boolean fPreserveSpace;
    protected SymbolTable fSymbolTable;

    public XMLSerializer() {
        super(new OutputFormat("xml", (String) null, false));
        this.fNamespaces = false;
        this.fNamespacePrefixes = true;
    }

    public XMLSerializer(OutputStream outputStream, OutputFormat outputFormat) {
        super(outputFormat == null ? new OutputFormat("xml", (String) null, false) : outputFormat);
        this.fNamespaces = false;
        this.fNamespacePrefixes = true;
        this._format.setMethod("xml");
        setOutputByteStream(outputStream);
    }

    public XMLSerializer(Writer writer, OutputFormat outputFormat) {
        super(outputFormat == null ? new OutputFormat("xml", (String) null, false) : outputFormat);
        this.fNamespaces = false;
        this.fNamespacePrefixes = true;
        this._format.setMethod("xml");
        setOutputCharStream(writer);
    }

    public XMLSerializer(OutputFormat outputFormat) {
        super(outputFormat == null ? new OutputFormat("xml", (String) null, false) : outputFormat);
        this.fNamespaces = false;
        this.fNamespacePrefixes = true;
        this._format.setMethod("xml");
    }

    private Attributes extractNamespaces(Attributes attributes) throws SAXException {
        String strSubstring;
        if (attributes == null) {
            return null;
        }
        int length = attributes.getLength();
        AttributesImpl attributesImpl = new AttributesImpl(attributes);
        for (int i = length - 1; i >= 0; i--) {
            String qName = attributesImpl.getQName(i);
            if (qName.startsWith(XMLConstants.XMLNS_ATTRIBUTE)) {
                if (qName.length() == 5) {
                    strSubstring = "";
                } else if (qName.charAt(5) == ':') {
                    strSubstring = qName.substring(6);
                }
                startPrefixMapping(strSubstring, attributes.getValue(i));
                attributesImpl.removeAttribute(i);
            }
        }
        return attributesImpl;
    }

    private void printAttribute(String str, String str2, boolean z, Attr attr) throws IOException {
        short sAcceptNode;
        if (z || (this.features & 64) == 0) {
            if (this.fDOMFilter != null && (this.fDOMFilter.getWhatToShow() & 2) != 0 && ((sAcceptNode = this.fDOMFilter.acceptNode(attr)) == 2 || sAcceptNode == 3)) {
                return;
            }
            this._printer.printSpace();
            this._printer.printText(str);
            this._printer.printText("=\"");
            printEscaped(str2);
            this._printer.printText('\"');
        }
        if (str.equals("xml:space")) {
            this.fPreserveSpace = str2.equals("preserve") ? true : this._format.getPreserveSpace();
        }
    }

    private void printNamespaceAttr(String str, String str2) throws IOException {
        this._printer.printSpace();
        if (str == XMLSymbols.EMPTY_STRING) {
            this._printer.printText(XMLSymbols.PREFIX_XMLNS);
        } else {
            this._printer.printText("xmlns:" + str);
        }
        this._printer.printText("=\"");
        printEscaped(str2);
        this._printer.printText('\"');
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    protected void checkUnboundNamespacePrefixedNode(Node node) throws IOException {
        if (this.fNamespaces) {
            Node firstChild = node.getFirstChild();
            while (firstChild != null) {
                Node nextSibling = firstChild.getNextSibling();
                String prefix = firstChild.getPrefix();
                String strAddSymbol = (prefix == null || prefix.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix);
                if (this.fNSBinder.getURI(strAddSymbol) == null && strAddSymbol != null) {
                    fatalError("The replacement text of the entity node '" + node.getNodeName() + "' contains an element node '" + firstChild.getNodeName() + "' with an undeclared prefix '" + strAddSymbol + "'.");
                }
                if (firstChild.getNodeType() == 1) {
                    NamedNodeMap attributes = firstChild.getAttributes();
                    for (int i = 0; i < attributes.getLength(); i++) {
                        String prefix2 = attributes.item(i).getPrefix();
                        String strAddSymbol2 = (prefix2 == null || prefix2.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix2);
                        if (this.fNSBinder.getURI(strAddSymbol2) == null && strAddSymbol2 != null) {
                            fatalError("The replacement text of the entity node '" + node.getNodeName() + "' contains an element node '" + firstChild.getNodeName() + "' with an attribute '" + attributes.item(i).getNodeName() + "' an undeclared prefix '" + strAddSymbol2 + "'.");
                        }
                    }
                }
                if (firstChild.hasChildNodes()) {
                    checkUnboundNamespacePrefixedNode(firstChild);
                }
                firstChild = nextSibling;
            }
        }
    }

    @Override // org.xml.sax.DocumentHandler
    public void endElement(String str) throws SAXException {
        endElement(null, null, str);
    }

    @Override // org.xml.sax.ContentHandler
    public void endElement(String str, String str2, String str3) throws SAXException {
        try {
            endElementIO(str, str2, str3);
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    public void endElementIO(String str, String str2, String str3) throws IOException {
        this._printer.unindent();
        ElementState elementState = getElementState();
        if (elementState.empty) {
            this._printer.printText("/>");
        } else {
            if (elementState.inCData) {
                this._printer.printText("]]>");
            }
            if (this._indenting && !elementState.preserveSpace && (elementState.afterElement || elementState.afterComment)) {
                this._printer.breakLine();
            }
            this._printer.printText("</");
            this._printer.printText(elementState.rawName);
            this._printer.printText('>');
        }
        ElementState elementStateLeaveElementState = leaveElementState();
        elementStateLeaveElementState.afterElement = true;
        elementStateLeaveElementState.afterComment = false;
        elementStateLeaveElementState.empty = false;
        if (isDocumentState()) {
            this._printer.flush();
        }
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    protected String getEntityRef(int i) {
        if (i == 34) {
            return "quot";
        }
        if (i == 60) {
            return "lt";
        }
        if (i == 62) {
            return "gt";
        }
        if (i == 38) {
            return "amp";
        }
        if (i != 39) {
            return null;
        }
        return "apos";
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0076  */
    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    protected void printEscaped(String str) throws IOException {
        Printer printer;
        String str2;
        int length = str.length();
        int i = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (!XMLChar.isValid(cCharAt)) {
                i++;
                if (i < length) {
                    surrogates(cCharAt, str.charAt(i), false);
                } else {
                    fatalError("The character '" + cCharAt + "' is an invalid XML character");
                }
            } else if (cCharAt == '\n' || cCharAt == '\r' || cCharAt == '\t') {
                printHex(cCharAt);
            } else {
                if (cCharAt == '<') {
                    printer = this._printer;
                    str2 = "&lt;";
                } else if (cCharAt == '&') {
                    printer = this._printer;
                    str2 = "&amp;";
                } else if (cCharAt == '\"') {
                    printer = this._printer;
                    str2 = "&quot;";
                } else if (cCharAt >= ' ') {
                    char c = cCharAt;
                    if (this._encodingInfo.isPrintable(c)) {
                        this._printer.printText(c);
                    } else {
                        printHex(cCharAt);
                    }
                } else {
                    printHex(cCharAt);
                }
                printer.printText(str2);
            }
            i++;
        }
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    protected void printText(String str, boolean z, boolean z2) throws IOException {
        int length = str.length();
        int i = 0;
        if (z) {
            while (i < length) {
                char cCharAt = str.charAt(i);
                if (!XMLChar.isValid(cCharAt)) {
                    i++;
                    if (i < length) {
                        surrogates(cCharAt, str.charAt(i), true);
                    } else {
                        fatalError("The character '" + cCharAt + "' is an invalid XML character");
                    }
                } else if (z2) {
                    this._printer.printText(cCharAt);
                } else {
                    printXMLChar(cCharAt);
                }
                i++;
            }
            return;
        }
        while (i < length) {
            char cCharAt2 = str.charAt(i);
            if (!XMLChar.isValid(cCharAt2)) {
                i++;
                if (i < length) {
                    surrogates(cCharAt2, str.charAt(i), true);
                } else {
                    fatalError("The character '" + cCharAt2 + "' is an invalid XML character");
                }
            } else if (z2) {
                this._printer.printText(cCharAt2);
            } else {
                printXMLChar(cCharAt2);
            }
            i++;
        }
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    protected void printText(char[] cArr, int i, int i2, boolean z, boolean z2) throws IOException {
        if (z) {
            while (true) {
                int i3 = i2 - 1;
                if (i2 <= 0) {
                    return;
                }
                int i4 = i + 1;
                char c = cArr[i];
                if (XMLChar.isValid(c)) {
                    if (z2) {
                        this._printer.printText(c);
                    } else {
                        printXMLChar(c);
                    }
                    i2 = i3;
                } else {
                    i2 -= 2;
                    if (i3 > 0) {
                        i += 2;
                        surrogates(c, cArr[i4], true);
                    } else {
                        fatalError("The character '" + c + "' is an invalid XML character");
                    }
                }
                i = i4;
            }
        } else {
            while (true) {
                int i5 = i2 - 1;
                if (i2 <= 0) {
                    return;
                }
                int i6 = i + 1;
                char c2 = cArr[i];
                if (XMLChar.isValid(c2)) {
                    if (z2) {
                        this._printer.printText(c2);
                    } else {
                        printXMLChar(c2);
                    }
                    i2 = i5;
                } else {
                    i2 -= 2;
                    if (i5 > 0) {
                        i += 2;
                        surrogates(c2, cArr[i6], true);
                    } else {
                        fatalError("The character '" + c2 + "' is an invalid XML character");
                    }
                }
                i = i6;
            }
        }
    }

    protected void printXMLChar(int i) throws IOException {
        Printer printer;
        String str;
        if (i != 13) {
            if (i == 60) {
                printer = this._printer;
                str = "&lt;";
            } else if (i == 38) {
                printer = this._printer;
                str = "&amp;";
            } else if (i == 62) {
                printer = this._printer;
                str = "&gt;";
            } else if (i == 10 || i == 9 || (i >= 32 && this._encodingInfo.isPrintable((char) i))) {
                this._printer.printText((char) i);
                return;
            }
            printer.printText(str);
            return;
        }
        printHex(i);
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    public boolean reset() {
        super.reset();
        NamespaceSupport namespaceSupport = this.fNSBinder;
        if (namespaceSupport == null) {
            return true;
        }
        namespaceSupport.reset();
        this.fNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:117:0x0213  */
    /* JADX WARN: Code duplicated, block: B:122:0x0233  */
    /* JADX WARN: Code duplicated, block: B:124:0x0237  */
    /* JADX WARN: Code duplicated, block: B:126:0x023b  */
    /* JADX WARN: Code duplicated, block: B:128:0x0241  */
    /* JADX WARN: Code duplicated, block: B:129:0x0244  */
    /* JADX WARN: Code duplicated, block: B:156:0x02c9  */
    /* JADX WARN: Code duplicated, block: B:158:0x02db A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:159:0x02dd  */
    /* JADX WARN: Code duplicated, block: B:161:0x02ed  */
    /* JADX WARN: Code duplicated, block: B:164:0x0307  */
    /* JADX WARN: Code duplicated, block: B:166:0x030b  */
    /* JADX WARN: Code duplicated, block: B:169:0x0316  */
    /* JADX WARN: Code duplicated, block: B:172:0x0327 A[LOOP:4: B:170:0x031f->B:172:0x0327, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:176:0x035c  */
    /* JADX WARN: Code duplicated, block: B:179:0x0371  */
    /* JADX WARN: Code duplicated, block: B:182:0x037c  */
    /* JADX WARN: Code duplicated, block: B:191:0x03b2  */
    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    protected void serializeElement(Element element) throws IOException {
        int length;
        NamedNodeMap attributes;
        int i;
        Attr attr;
        String value;
        String nodeName;
        String namespaceURI;
        String str;
        char c;
        String prefix;
        String strAddSymbol;
        String strAddSymbol2;
        String strAddSymbol3;
        String prefix2;
        int i2;
        if (this.fNamespaces) {
            this.fLocalNSBinder.reset();
            this.fNSBinder.pushContext();
        }
        String tagName = element.getTagName();
        ElementState elementState = getElementState();
        if (!isDocumentState()) {
            if (elementState.empty) {
                this._printer.printText('>');
            }
            if (elementState.inCData) {
                this._printer.printText("]]>");
                elementState.inCData = false;
            }
            if (this._indenting && !elementState.preserveSpace && (elementState.empty || elementState.afterElement || elementState.afterComment)) {
                this._printer.breakLine();
            }
        } else if (!this._started) {
            startDocument(tagName);
        }
        this.fPreserveSpace = elementState.preserveSpace;
        String str2 = null;
        if (element.hasAttributes()) {
            attributes = element.getAttributes();
            length = attributes.getLength();
        } else {
            length = 0;
            attributes = null;
        }
        if (this.fNamespaces) {
            for (int i3 = 0; i3 < length; i3++) {
                Attr attr2 = (Attr) attributes.item(i3);
                String namespaceURI2 = attr2.getNamespaceURI();
                if (namespaceURI2 != null && namespaceURI2.equals(NamespaceContext.XMLNS_URI)) {
                    String nodeValue = attr2.getNodeValue();
                    if (nodeValue == null) {
                        nodeValue = XMLSymbols.EMPTY_STRING;
                    }
                    if (!nodeValue.equals(NamespaceContext.XMLNS_URI)) {
                        String prefix3 = attr2.getPrefix();
                        String strAddSymbol4 = (prefix3 == null || prefix3.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix3);
                        String strAddSymbol5 = this.fSymbolTable.addSymbol(attr2.getLocalName());
                        if (strAddSymbol4 == XMLSymbols.PREFIX_XMLNS) {
                            String strAddSymbol6 = this.fSymbolTable.addSymbol(nodeValue);
                            if (strAddSymbol6.length() != 0) {
                                this.fNSBinder.declarePrefix(strAddSymbol5, strAddSymbol6);
                            }
                        } else {
                            this.fNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, this.fSymbolTable.addSymbol(nodeValue));
                        }
                    } else if (this.fDOMErrorHandler != null) {
                        modifyDOMError(DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CantBindXMLNS", null), (short) 2, null, attr2);
                        if (!this.fDOMErrorHandler.handleError(this.fDOMError)) {
                            throw new RuntimeException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "SerializationStopped", null));
                        }
                    } else {
                        continue;
                    }
                }
            }
            String namespaceURI3 = element.getNamespaceURI();
            String prefix4 = element.getPrefix();
            if (namespaceURI3 == null || prefix4 == null || namespaceURI3.length() != 0 || prefix4.length() == 0) {
                this._printer.printText('<');
                this._printer.printText(tagName);
                this._printer.indent();
            } else {
                this._printer.printText('<');
                this._printer.printText(element.getLocalName());
                this._printer.indent();
                prefix4 = null;
            }
            if (namespaceURI3 != null) {
                String strAddSymbol7 = this.fSymbolTable.addSymbol(namespaceURI3);
                String strAddSymbol8 = (prefix4 == null || prefix4.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix4);
                if (this.fNSBinder.getURI(strAddSymbol8) != strAddSymbol7) {
                    if (this.fNamespacePrefixes) {
                        printNamespaceAttr(strAddSymbol8, strAddSymbol7);
                    }
                    this.fLocalNSBinder.declarePrefix(strAddSymbol8, strAddSymbol7);
                    this.fNSBinder.declarePrefix(strAddSymbol8, strAddSymbol7);
                }
            } else {
                if (element.getLocalName() != null) {
                    String uri = this.fNSBinder.getURI(XMLSymbols.EMPTY_STRING);
                    if (uri != null && uri.length() > 0) {
                        if (this.fNamespacePrefixes) {
                            printNamespaceAttr(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
                        }
                        this.fLocalNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
                        this.fNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING);
                    }
                } else if (this.fDOMErrorHandler != null) {
                    modifyDOMError(DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NullLocalElementName", new Object[]{element.getNodeName()}), (short) 2, null, element);
                    if (!this.fDOMErrorHandler.handleError(this.fDOMError)) {
                        throw new RuntimeException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "SerializationStopped", null));
                    }
                }
                i = 0;
                while (i < length) {
                    attr = (Attr) attributes.item(i);
                    value = attr.getValue();
                    nodeName = attr.getNodeName();
                    namespaceURI = attr.getNamespaceURI();
                    if (namespaceURI == null && namespaceURI.length() == 0) {
                        nodeName = attr.getLocalName();
                        str = str2;
                    } else {
                        str = namespaceURI;
                    }
                    if (value == null) {
                        value = XMLSymbols.EMPTY_STRING;
                    }
                    if (str != null) {
                        prefix = attr.getPrefix();
                        if (prefix == null) {
                            strAddSymbol = XMLSymbols.EMPTY_STRING;
                        } else {
                            strAddSymbol = this.fSymbolTable.addSymbol(prefix);
                        }
                        strAddSymbol2 = this.fSymbolTable.addSymbol(attr.getLocalName());
                        if (str == null && str.equals(NamespaceContext.XMLNS_URI)) {
                            String prefix5 = attr.getPrefix();
                            String strAddSymbol9 = (prefix5 == null || prefix5.length() == 0) ? XMLSymbols.EMPTY_STRING : this.fSymbolTable.addSymbol(prefix5);
                            String strAddSymbol10 = this.fSymbolTable.addSymbol(attr.getLocalName());
                            if (strAddSymbol9 == XMLSymbols.PREFIX_XMLNS) {
                                String uri2 = this.fLocalNSBinder.getURI(strAddSymbol10);
                                String strAddSymbol11 = this.fSymbolTable.addSymbol(value);
                                if (strAddSymbol11.length() != 0 && uri2 == null) {
                                    if (this.fNamespacePrefixes) {
                                        printNamespaceAttr(strAddSymbol10, strAddSymbol11);
                                    }
                                    this.fLocalNSBinder.declarePrefix(strAddSymbol10, strAddSymbol11);
                                }
                            } else {
                                this.fNSBinder.getURI(XMLSymbols.EMPTY_STRING);
                                String uri3 = this.fLocalNSBinder.getURI(XMLSymbols.EMPTY_STRING);
                                String strAddSymbol12 = this.fSymbolTable.addSymbol(value);
                                if (uri3 == null && this.fNamespacePrefixes) {
                                    printNamespaceAttr(XMLSymbols.EMPTY_STRING, strAddSymbol12);
                                }
                            }
                        } else {
                            strAddSymbol3 = this.fSymbolTable.addSymbol(str);
                            String uri4 = this.fNSBinder.getURI(strAddSymbol);
                            if (strAddSymbol != XMLSymbols.EMPTY_STRING || uri4 != strAddSymbol3) {
                                String nodeName2 = attr.getNodeName();
                                prefix2 = this.fNSBinder.getPrefix(strAddSymbol3);
                                if (prefix2 != null || prefix2 == XMLSymbols.EMPTY_STRING) {
                                    if (strAddSymbol == XMLSymbols.EMPTY_STRING && this.fLocalNSBinder.getURI(strAddSymbol) == null) {
                                        nodeName = nodeName2;
                                    } else {
                                        strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                        i2 = 2;
                                        while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                            strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                            i2++;
                                        }
                                        nodeName = strAddSymbol + ":" + strAddSymbol2;
                                    }
                                    if (this.fNamespacePrefixes) {
                                        printNamespaceAttr(strAddSymbol, strAddSymbol3);
                                    }
                                    value = this.fSymbolTable.addSymbol(value);
                                    this.fLocalNSBinder.declarePrefix(strAddSymbol, value);
                                    this.fNSBinder.declarePrefix(strAddSymbol, strAddSymbol3);
                                } else {
                                    nodeName = prefix2 + ":" + strAddSymbol2;
                                }
                            }
                            if (value == null) {
                                value = XMLSymbols.EMPTY_STRING;
                            }
                            printAttribute(nodeName, value, attr.getSpecified(), attr);
                        }
                        c = 2;
                    } else {
                        length = length;
                        if (attr.getLocalName() == null || this.fDOMErrorHandler == null) {
                            c = 2;
                        } else {
                            c = 2;
                            modifyDOMError(DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NullLocalAttrName", new Object[]{attr.getNodeName()}), (short) 2, null, attr);
                            if (!this.fDOMErrorHandler.handleError(this.fDOMError)) {
                                throw new RuntimeException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "SerializationStopped", null));
                            }
                        }
                        printAttribute(nodeName, value, attr.getSpecified(), attr);
                    }
                    i++;
                    attributes = attributes;
                    length = length;
                    str2 = null;
                }
            }
            i = 0;
            while (i < length) {
                attr = (Attr) attributes.item(i);
                value = attr.getValue();
                nodeName = attr.getNodeName();
                namespaceURI = attr.getNamespaceURI();
                if (namespaceURI == null) {
                    str = namespaceURI;
                } else {
                    str = namespaceURI;
                }
                if (value == null) {
                    value = XMLSymbols.EMPTY_STRING;
                }
                if (str != null) {
                    prefix = attr.getPrefix();
                    if (prefix == null) {
                        strAddSymbol = XMLSymbols.EMPTY_STRING;
                    } else {
                        strAddSymbol = this.fSymbolTable.addSymbol(prefix);
                    }
                    strAddSymbol2 = this.fSymbolTable.addSymbol(attr.getLocalName());
                    if (str == null) {
                        strAddSymbol3 = this.fSymbolTable.addSymbol(str);
                        String uri5 = this.fNSBinder.getURI(strAddSymbol);
                        if (strAddSymbol != XMLSymbols.EMPTY_STRING) {
                            String nodeName3 = attr.getNodeName();
                            prefix2 = this.fNSBinder.getPrefix(strAddSymbol3);
                            if (prefix2 != null) {
                                if (strAddSymbol == XMLSymbols.EMPTY_STRING) {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                } else {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                }
                                if (this.fNamespacePrefixes) {
                                    printNamespaceAttr(strAddSymbol, strAddSymbol3);
                                }
                                value = this.fSymbolTable.addSymbol(value);
                                this.fLocalNSBinder.declarePrefix(strAddSymbol, value);
                                this.fNSBinder.declarePrefix(strAddSymbol, strAddSymbol3);
                            } else {
                                if (strAddSymbol == XMLSymbols.EMPTY_STRING) {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                } else {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                }
                                if (this.fNamespacePrefixes) {
                                    printNamespaceAttr(strAddSymbol, strAddSymbol3);
                                }
                                value = this.fSymbolTable.addSymbol(value);
                                this.fLocalNSBinder.declarePrefix(strAddSymbol, value);
                                this.fNSBinder.declarePrefix(strAddSymbol, strAddSymbol3);
                            }
                        } else {
                            String nodeName4 = attr.getNodeName();
                            prefix2 = this.fNSBinder.getPrefix(strAddSymbol3);
                            if (prefix2 != null) {
                                if (strAddSymbol == XMLSymbols.EMPTY_STRING) {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                } else {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                }
                                if (this.fNamespacePrefixes) {
                                    printNamespaceAttr(strAddSymbol, strAddSymbol3);
                                }
                                value = this.fSymbolTable.addSymbol(value);
                                this.fLocalNSBinder.declarePrefix(strAddSymbol, value);
                                this.fNSBinder.declarePrefix(strAddSymbol, strAddSymbol3);
                            } else {
                                if (strAddSymbol == XMLSymbols.EMPTY_STRING) {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                } else {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                }
                                if (this.fNamespacePrefixes) {
                                    printNamespaceAttr(strAddSymbol, strAddSymbol3);
                                }
                                value = this.fSymbolTable.addSymbol(value);
                                this.fLocalNSBinder.declarePrefix(strAddSymbol, value);
                                this.fNSBinder.declarePrefix(strAddSymbol, strAddSymbol3);
                            }
                        }
                        if (value == null) {
                            value = XMLSymbols.EMPTY_STRING;
                        }
                        printAttribute(nodeName, value, attr.getSpecified(), attr);
                    } else {
                        strAddSymbol3 = this.fSymbolTable.addSymbol(str);
                        String uri6 = this.fNSBinder.getURI(strAddSymbol);
                        if (strAddSymbol != XMLSymbols.EMPTY_STRING) {
                            String nodeName5 = attr.getNodeName();
                            prefix2 = this.fNSBinder.getPrefix(strAddSymbol3);
                            if (prefix2 != null) {
                                if (strAddSymbol == XMLSymbols.EMPTY_STRING) {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                } else {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                }
                                if (this.fNamespacePrefixes) {
                                    printNamespaceAttr(strAddSymbol, strAddSymbol3);
                                }
                                value = this.fSymbolTable.addSymbol(value);
                                this.fLocalNSBinder.declarePrefix(strAddSymbol, value);
                                this.fNSBinder.declarePrefix(strAddSymbol, strAddSymbol3);
                            } else {
                                if (strAddSymbol == XMLSymbols.EMPTY_STRING) {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                } else {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                }
                                if (this.fNamespacePrefixes) {
                                    printNamespaceAttr(strAddSymbol, strAddSymbol3);
                                }
                                value = this.fSymbolTable.addSymbol(value);
                                this.fLocalNSBinder.declarePrefix(strAddSymbol, value);
                                this.fNSBinder.declarePrefix(strAddSymbol, strAddSymbol3);
                            }
                        } else {
                            String nodeName6 = attr.getNodeName();
                            prefix2 = this.fNSBinder.getPrefix(strAddSymbol3);
                            if (prefix2 != null) {
                                if (strAddSymbol == XMLSymbols.EMPTY_STRING) {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                } else {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                }
                                if (this.fNamespacePrefixes) {
                                    printNamespaceAttr(strAddSymbol, strAddSymbol3);
                                }
                                value = this.fSymbolTable.addSymbol(value);
                                this.fLocalNSBinder.declarePrefix(strAddSymbol, value);
                                this.fNSBinder.declarePrefix(strAddSymbol, strAddSymbol3);
                            } else {
                                if (strAddSymbol == XMLSymbols.EMPTY_STRING) {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                } else {
                                    strAddSymbol = this.fSymbolTable.addSymbol("NS1");
                                    i2 = 2;
                                    while (this.fLocalNSBinder.getURI(strAddSymbol) != null) {
                                        strAddSymbol = this.fSymbolTable.addSymbol(PREFIX + i2);
                                        i2++;
                                    }
                                    nodeName = strAddSymbol + ":" + strAddSymbol2;
                                }
                                if (this.fNamespacePrefixes) {
                                    printNamespaceAttr(strAddSymbol, strAddSymbol3);
                                }
                                value = this.fSymbolTable.addSymbol(value);
                                this.fLocalNSBinder.declarePrefix(strAddSymbol, value);
                                this.fNSBinder.declarePrefix(strAddSymbol, strAddSymbol3);
                            }
                        }
                        if (value == null) {
                            value = XMLSymbols.EMPTY_STRING;
                        }
                        printAttribute(nodeName, value, attr.getSpecified(), attr);
                    }
                    c = 2;
                } else {
                    length = length;
                    if (attr.getLocalName() == null) {
                        c = 2;
                    } else {
                        c = 2;
                    }
                    printAttribute(nodeName, value, attr.getSpecified(), attr);
                }
                i++;
                attributes = attributes;
                length = length;
                str2 = null;
            }
        } else {
            this._printer.printText('<');
            this._printer.printText(tagName);
            this._printer.indent();
            for (int i4 = 0; i4 < length; i4++) {
                Attr attr3 = (Attr) attributes.item(i4);
                String name2 = attr3.getName();
                String value2 = attr3.getValue();
                if (value2 == null) {
                    value2 = "";
                }
                printAttribute(name2, value2, attr3.getSpecified(), attr3);
            }
        }
        if (element.hasChildNodes()) {
            ElementState elementStateEnterElementState = enterElementState(null, null, tagName, this.fPreserveSpace);
            elementStateEnterElementState.doCData = this._format.isCDataElement(tagName);
            elementStateEnterElementState.unescaped = this._format.isNonEscapingElement(tagName);
            for (Node firstChild = element.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                serializeNode(firstChild);
            }
            if (this.fNamespaces) {
                this.fNSBinder.popContext();
            }
            endElementIO(null, null, tagName);
            return;
        }
        if (this.fNamespaces) {
            this.fNSBinder.popContext();
        }
        this._printer.unindent();
        this._printer.printText("/>");
        elementState.afterElement = true;
        elementState.afterComment = false;
        elementState.empty = false;
        if (isDocumentState()) {
            this._printer.flush();
        }
    }

    public void setNamespaces(boolean z) {
        this.fNamespaces = z;
        if (this.fNSBinder == null) {
            this.fNSBinder = new NamespaceSupport();
            this.fLocalNSBinder = new NamespaceSupport();
            this.fSymbolTable = new SymbolTable();
        }
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer, org.apache.xml.serialize.Serializer
    public void setOutputFormat(OutputFormat outputFormat) {
        if (outputFormat == null) {
            outputFormat = new OutputFormat("xml", (String) null, false);
        }
        super.setOutputFormat(outputFormat);
    }

    protected void startDocument(String str) throws IOException {
        Printer printer;
        String str2;
        String strLeaveDTD = this._printer.leaveDTD();
        if (!this._started) {
            if (!this._format.getOmitXMLDeclaration()) {
                StringBuffer stringBuffer = new StringBuffer("<?xml version=\"");
                stringBuffer.append(this._format.getVersion() != null ? this._format.getVersion() : BuildConfig.VERSION_NAME);
                stringBuffer.append('\"');
                String encoding = this._format.getEncoding();
                if (encoding != null) {
                    stringBuffer.append(" encoding=\"");
                    stringBuffer.append(encoding);
                    stringBuffer.append('\"');
                }
                if (this._format.getStandalone() && this._docTypeSystemId == null && this._docTypePublicId == null) {
                    stringBuffer.append(" standalone=\"yes\"");
                }
                stringBuffer.append("?>");
                this._printer.printText(stringBuffer);
                this._printer.breakLine();
            }
            if (!this._format.getOmitDocumentType()) {
                if (this._docTypeSystemId != null) {
                    this._printer.printText("<!DOCTYPE ");
                    this._printer.printText(str);
                    if (this._docTypePublicId != null) {
                        this._printer.printText(" PUBLIC ");
                        printDoctypeURL(this._docTypePublicId);
                        if (this._indenting) {
                            this._printer.breakLine();
                            for (int i = 0; i < str.length() + 18; i++) {
                                this._printer.printText(" ");
                            }
                        } else {
                            this._printer.printText(" ");
                        }
                    } else {
                        this._printer.printText(" SYSTEM ");
                    }
                    printDoctypeURL(this._docTypeSystemId);
                    if (strLeaveDTD != null && strLeaveDTD.length() > 0) {
                        this._printer.printText(" [");
                        printText(strLeaveDTD, true, true);
                        this._printer.printText(']');
                    }
                    printer = this._printer;
                    str2 = ">";
                } else if (strLeaveDTD != null && strLeaveDTD.length() > 0) {
                    this._printer.printText("<!DOCTYPE ");
                    this._printer.printText(str);
                    this._printer.printText(" [");
                    printText(strLeaveDTD, true, true);
                    printer = this._printer;
                    str2 = "]>";
                }
                printer.printText(str2);
                this._printer.breakLine();
            }
        }
        this._started = true;
        serializePreRoot();
    }

    @Override // org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        String prefix;
        Printer printer;
        String prefix2;
        try {
            if (this._printer == null) {
                throw new IllegalStateException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "NoWriterSupplied", null));
            }
            ElementState elementState = getElementState();
            if (!isDocumentState()) {
                if (elementState.empty) {
                    this._printer.printText('>');
                }
                if (elementState.inCData) {
                    this._printer.printText("]]>");
                    elementState.inCData = false;
                }
                if (this._indenting && !elementState.preserveSpace && (elementState.empty || elementState.afterElement || elementState.afterComment)) {
                    this._printer.breakLine();
                }
            } else if (!this._started) {
                startDocument((str2 == null || str2.length() == 0) ? str3 : str2);
            }
            boolean preserveSpace = elementState.preserveSpace;
            Attributes attributesExtractNamespaces = extractNamespaces(attributes);
            if (str3 == null || str3.length() == 0) {
                if (str2 == null) {
                    throw new SAXException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "NoName", null));
                }
                str3 = (str == null || str.equals("") || (prefix = getPrefix(str)) == null || prefix.length() <= 0) ? str2 : prefix + ":" + str2;
            }
            this._printer.printText('<');
            this._printer.printText(str3);
            this._printer.indent();
            if (attributesExtractNamespaces != null) {
                for (int i = 0; i < attributesExtractNamespaces.getLength(); i++) {
                    this._printer.printSpace();
                    String qName = attributesExtractNamespaces.getQName(i);
                    if (qName != null && qName.length() == 0) {
                        qName = attributesExtractNamespaces.getLocalName(i);
                        String uri = attributesExtractNamespaces.getURI(i);
                        if (uri != null && uri.length() != 0 && ((str == null || str.length() == 0 || !uri.equals(str)) && (prefix2 = getPrefix(uri)) != null && prefix2.length() > 0)) {
                            qName = prefix2 + ":" + qName;
                        }
                    }
                    String value = attributesExtractNamespaces.getValue(i);
                    if (value == null) {
                        value = "";
                    }
                    this._printer.printText(qName);
                    this._printer.printText("=\"");
                    printEscaped(value);
                    this._printer.printText('\"');
                    if (qName.equals("xml:space")) {
                        preserveSpace = value.equals("preserve") ? true : this._format.getPreserveSpace();
                    }
                }
            }
            if (this._prefixes != null) {
                for (Map.Entry entry : this._prefixes.entrySet()) {
                    this._printer.printSpace();
                    String str4 = (String) entry.getKey();
                    String str5 = (String) entry.getValue();
                    if (str5.length() == 0) {
                        this._printer.printText("xmlns=\"");
                        printEscaped(str4);
                        printer = this._printer;
                    } else {
                        this._printer.printText("xmlns:");
                        this._printer.printText(str5);
                        this._printer.printText("=\"");
                        printEscaped(str4);
                        printer = this._printer;
                    }
                    printer.printText('\"');
                }
            }
            ElementState elementStateEnterElementState = enterElementState(str, str2, str3, preserveSpace);
            if (str2 != null && str2.length() != 0) {
                str3 = str + "^" + str2;
            }
            elementStateEnterElementState.doCData = this._format.isCDataElement(str3);
            elementStateEnterElementState.unescaped = this._format.isNonEscapingElement(str3);
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    @Override // org.xml.sax.DocumentHandler
    public void startElement(String str, AttributeList attributeList) throws SAXException {
        try {
            if (this._printer == null) {
                throw new IllegalStateException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "NoWriterSupplied", null));
            }
            ElementState elementState = getElementState();
            if (!isDocumentState()) {
                if (elementState.empty) {
                    this._printer.printText('>');
                }
                if (elementState.inCData) {
                    this._printer.printText("]]>");
                    elementState.inCData = false;
                }
                if (this._indenting && !elementState.preserveSpace && (elementState.empty || elementState.afterElement || elementState.afterComment)) {
                    this._printer.breakLine();
                }
            } else if (!this._started) {
                startDocument(str);
            }
            boolean preserveSpace = elementState.preserveSpace;
            this._printer.printText('<');
            this._printer.printText(str);
            this._printer.indent();
            if (attributeList != null) {
                for (int i = 0; i < attributeList.getLength(); i++) {
                    this._printer.printSpace();
                    String name2 = attributeList.getName(i);
                    String value = attributeList.getValue(i);
                    if (value != null) {
                        this._printer.printText(name2);
                        this._printer.printText("=\"");
                        printEscaped(value);
                        this._printer.printText('\"');
                    }
                    if (name2.equals("xml:space")) {
                        preserveSpace = value.equals("preserve") ? true : this._format.getPreserveSpace();
                    }
                }
            }
            ElementState elementStateEnterElementState = enterElementState(null, null, str, preserveSpace);
            elementStateEnterElementState.doCData = this._format.isCDataElement(str);
            elementStateEnterElementState.unescaped = this._format.isNonEscapingElement(str);
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }
}
