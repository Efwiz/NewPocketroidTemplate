package org.apache.xml.serialize;

import com.huawei.hms.framework.common.ContainerUtils;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Vector;
import kotlin.text.Typography;
import org.apache.xerces.dom.DOMErrorImpl;
import org.apache.xerces.dom.DOMLocatorImpl;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.util.XMLChar;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSSerializerFilter;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

/* JADX INFO: loaded from: classes13.dex */
public abstract class BaseMarkupSerializer implements ContentHandler, DocumentHandler, LexicalHandler, DTDHandler, DeclHandler, DOMSerializer, Serializer {
    protected String _docTypePublicId;
    protected String _docTypeSystemId;
    private int _elementStateCount;
    protected EncodingInfo _encodingInfo;
    protected OutputFormat _format;
    protected boolean _indenting;
    private OutputStream _output;
    private Vector _preRoot;
    protected Hashtable _prefixes;
    private boolean _prepared;
    protected Printer _printer;
    protected boolean _started;
    private Writer _writer;
    protected DOMErrorHandler fDOMErrorHandler;
    protected LSSerializerFilter fDOMFilter;
    protected short features = -1;
    protected final DOMErrorImpl fDOMError = new DOMErrorImpl();
    protected final StringBuffer fStrBuffer = new StringBuffer(40);
    protected Node fCurrentNode = null;
    private ElementState[] _elementStates = new ElementState[10];

    protected BaseMarkupSerializer(OutputFormat outputFormat) {
        int i = 0;
        while (true) {
            ElementState[] elementStateArr = this._elementStates;
            if (i >= elementStateArr.length) {
                this._format = outputFormat;
                return;
            } else {
                elementStateArr[i] = new ElementState();
                i++;
            }
        }
    }

    @Override // org.apache.xml.serialize.Serializer
    public ContentHandler asContentHandler() throws IOException {
        prepare();
        return this;
    }

    @Override // org.apache.xml.serialize.Serializer
    public DOMSerializer asDOMSerializer() throws IOException {
        prepare();
        return this;
    }

    @Override // org.apache.xml.serialize.Serializer
    public DocumentHandler asDocumentHandler() throws IOException {
        prepare();
        return this;
    }

    @Override // org.xml.sax.ext.DeclHandler
    public void attributeDecl(String str, String str2, String str3, String str4, String str5) throws SAXException {
        try {
            this._printer.enterDTD();
            this._printer.printText("<!ATTLIST ");
            this._printer.printText(str);
            this._printer.printText(StringUtil.SPACE);
            this._printer.printText(str2);
            this._printer.printText(StringUtil.SPACE);
            this._printer.printText(str3);
            if (str4 != null) {
                this._printer.printText(StringUtil.SPACE);
                this._printer.printText(str4);
            }
            if (str5 != null) {
                this._printer.printText(" \"");
                printEscaped(str5);
                this._printer.printText('\"');
            }
            this._printer.printText('>');
            if (this._indenting) {
                this._printer.breakLine();
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    protected void characters(String str) throws IOException {
        ElementState elementStateContent = content();
        if (elementStateContent.inCData || elementStateContent.doCData) {
            if (!elementStateContent.inCData) {
                this._printer.printText("<![CDATA[");
                elementStateContent.inCData = true;
            }
            int nextIndent = this._printer.getNextIndent();
            this._printer.setNextIndent(0);
            printCDATAText(str);
            this._printer.setNextIndent(nextIndent);
            return;
        }
        if (!elementStateContent.preserveSpace) {
            printText(str, false, elementStateContent.unescaped);
            return;
        }
        int nextIndent2 = this._printer.getNextIndent();
        this._printer.setNextIndent(0);
        printText(str, true, elementStateContent.unescaped);
        this._printer.setNextIndent(nextIndent2);
    }

    @Override // org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        int i3;
        try {
            ElementState elementStateContent = content();
            if (!elementStateContent.inCData && !elementStateContent.doCData) {
                if (!elementStateContent.preserveSpace) {
                    printText(cArr, i, i2, false, elementStateContent.unescaped);
                    return;
                }
                int nextIndent = this._printer.getNextIndent();
                this._printer.setNextIndent(0);
                printText(cArr, i, i2, true, elementStateContent.unescaped);
                this._printer.setNextIndent(nextIndent);
                return;
            }
            if (!elementStateContent.inCData) {
                this._printer.printText("<![CDATA[");
                elementStateContent.inCData = true;
            }
            int nextIndent2 = this._printer.getNextIndent();
            this._printer.setNextIndent(0);
            int i4 = i2 + i;
            while (i < i4) {
                char c = cArr[i];
                if (c == ']' && (i3 = i + 2) < i4 && cArr[i + 1] == ']' && cArr[i3] == '>') {
                    this._printer.printText("]]]]><![CDATA[>");
                    i = i3;
                } else if (!XMLChar.isValid(c)) {
                    i++;
                    if (i < i4) {
                        surrogates(c, cArr[i], true);
                    } else {
                        fatalError("The character '" + c + "' is an invalid XML character");
                    }
                } else if ((c >= ' ' && this._encodingInfo.isPrintable(c) && c != 127) || c == '\n' || c == '\r' || c == '\t') {
                    this._printer.printText(c);
                } else {
                    this._printer.printText("]]>&#x");
                    this._printer.printText(Integer.toHexString(c));
                    this._printer.printText(";<![CDATA[");
                }
                i++;
            }
            this._printer.setNextIndent(nextIndent2);
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    protected void checkUnboundNamespacePrefixedNode(Node node) throws IOException {
    }

    protected void cleanup() {
        this.fCurrentNode = null;
    }

    final void clearDocumentState() {
        this._elementStateCount = 0;
    }

    public void comment(String str) throws IOException {
        if (this._format.getOmitComments()) {
            return;
        }
        ElementState elementStateContent = content();
        int iIndexOf = str.indexOf("-->");
        (iIndexOf >= 0 ? this.fStrBuffer.append("<!--").append(str.substring(0, iIndexOf)) : this.fStrBuffer.append("<!--").append(str)).append("-->");
        if (isDocumentState()) {
            if (this._preRoot == null) {
                this._preRoot = new Vector();
            }
            this._preRoot.addElement(this.fStrBuffer.toString());
        } else {
            if (this._indenting && !elementStateContent.preserveSpace) {
                this._printer.breakLine();
            }
            this._printer.indent();
            printText(this.fStrBuffer.toString(), true, true);
            this._printer.unindent();
            if (this._indenting) {
                elementStateContent.afterElement = true;
            }
        }
        this.fStrBuffer.setLength(0);
        elementStateContent.afterComment = true;
        elementStateContent.afterElement = false;
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void comment(char[] cArr, int i, int i2) throws SAXException {
        try {
            comment(new String(cArr, i, i2));
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    protected ElementState content() throws IOException {
        ElementState elementState = getElementState();
        if (!isDocumentState()) {
            if (elementState.inCData && !elementState.doCData) {
                this._printer.printText("]]>");
                elementState.inCData = false;
            }
            if (elementState.empty) {
                this._printer.printText('>');
                elementState.empty = false;
            }
            elementState.afterElement = false;
            elementState.afterComment = false;
        }
        return elementState;
    }

    @Override // org.xml.sax.ext.DeclHandler
    public void elementDecl(String str, String str2) throws SAXException {
        try {
            this._printer.enterDTD();
            this._printer.printText("<!ELEMENT ");
            this._printer.printText(str);
            this._printer.printText(StringUtil.SPACE);
            this._printer.printText(str2);
            this._printer.printText('>');
            if (this._indenting) {
                this._printer.breakLine();
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void endCDATA() {
        getElementState().doCData = false;
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void endDTD() {
    }

    @Override // org.xml.sax.ContentHandler
    public void endDocument() throws SAXException {
        try {
            serializePreRoot();
            this._printer.flush();
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void endEntity(String str) {
    }

    public void endNonEscaping() {
        getElementState().unescaped = false;
    }

    @Override // org.xml.sax.ContentHandler
    public void endPrefixMapping(String str) throws SAXException {
    }

    public void endPreserving() {
        getElementState().preserveSpace = false;
    }

    protected ElementState enterElementState(String str, String str2, String str3, boolean z) {
        ElementState[] elementStateArr;
        int i = this._elementStateCount + 1;
        ElementState[] elementStateArr2 = this._elementStates;
        if (i == elementStateArr2.length) {
            int length = elementStateArr2.length + 10;
            ElementState[] elementStateArr3 = new ElementState[length];
            int i2 = 0;
            while (true) {
                elementStateArr = this._elementStates;
                if (i2 >= elementStateArr.length) {
                    break;
                }
                elementStateArr3[i2] = elementStateArr[i2];
                i2++;
            }
            for (int length2 = elementStateArr.length; length2 < length; length2++) {
                elementStateArr3[length2] = new ElementState();
            }
            this._elementStates = elementStateArr3;
        }
        int i3 = this._elementStateCount + 1;
        this._elementStateCount = i3;
        ElementState elementState = this._elementStates[i3];
        elementState.namespaceURI = str;
        elementState.localName = str2;
        elementState.rawName = str3;
        elementState.preserveSpace = z;
        elementState.empty = true;
        elementState.afterElement = false;
        elementState.afterComment = false;
        elementState.inCData = false;
        elementState.doCData = false;
        elementState.unescaped = false;
        elementState.prefixes = this._prefixes;
        this._prefixes = null;
        return elementState;
    }

    @Override // org.xml.sax.ext.DeclHandler
    public void externalEntityDecl(String str, String str2, String str3) throws SAXException {
        try {
            this._printer.enterDTD();
            unparsedEntityDecl(str, str2, str3, null);
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    protected void fatalError(String str) throws IOException {
        if (this.fDOMErrorHandler == null) {
            throw new IOException(str);
        }
        modifyDOMError(str, (short) 3, null, this.fCurrentNode);
        this.fDOMErrorHandler.handleError(this.fDOMError);
    }

    protected ElementState getElementState() {
        return this._elementStates[this._elementStateCount];
    }

    protected abstract String getEntityRef(int i);

    protected String getPrefix(String str) {
        String str2;
        String str3;
        Hashtable hashtable = this._prefixes;
        if (hashtable != null && (str3 = (String) hashtable.get(str)) != null) {
            return str3;
        }
        int i = this._elementStateCount;
        if (i == 0) {
            return null;
        }
        while (i > 0) {
            if (this._elementStates[i].prefixes != null && (str2 = (String) this._elementStates[i].prefixes.get(str)) != null) {
                return str2;
            }
            i--;
        }
        return null;
    }

    @Override // org.xml.sax.ContentHandler
    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        try {
            content();
            if (!this._indenting) {
                return;
            }
            this._printer.setThisIndent(0);
            while (true) {
                int i3 = i2 - 1;
                if (i2 <= 0) {
                    return;
                }
                this._printer.printText(cArr[i]);
                i++;
                i2 = i3;
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    @Override // org.xml.sax.ext.DeclHandler
    public void internalEntityDecl(String str, String str2) throws SAXException {
        try {
            this._printer.enterDTD();
            this._printer.printText("<!ENTITY ");
            this._printer.printText(str);
            this._printer.printText(" \"");
            printEscaped(str2);
            this._printer.printText("\">");
            if (this._indenting) {
                this._printer.breakLine();
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    protected boolean isDocumentState() {
        return this._elementStateCount == 0;
    }

    protected ElementState leaveElementState() {
        int i = this._elementStateCount;
        if (i <= 0) {
            throw new IllegalStateException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "Internal", null));
        }
        this._prefixes = null;
        int i2 = i - 1;
        this._elementStateCount = i2;
        return this._elementStates[i2];
    }

    protected DOMError modifyDOMError(String str, short s, String str2, Node node) {
        this.fDOMError.reset();
        this.fDOMError.fMessage = str;
        this.fDOMError.fType = str2;
        this.fDOMError.fSeverity = s;
        this.fDOMError.fLocator = new DOMLocatorImpl(-1, -1, -1, node, null);
        return this.fDOMError;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0047 A[Catch: IOException -> 0x004d, TRY_LEAVE, TryCatch #0 {IOException -> 0x004d, blocks: (B:2:0x0000, B:5:0x0009, B:7:0x001f, B:8:0x0026, B:10:0x003c, B:12:0x0047, B:9:0x002a), top: B:17:0x0000 }] */
    /* JADX WARN: Code duplicated, block: B:19:? A[RETURN, SYNTHETIC] */
    @Override // org.xml.sax.DTDHandler
    public void notationDecl(String str, String str2, String str3) throws SAXException {
        try {
            this._printer.enterDTD();
            if (str2 != null) {
                this._printer.printText("<!NOTATION ");
                this._printer.printText(str);
                this._printer.printText(" PUBLIC ");
                printDoctypeURL(str2);
                if (str3 != null) {
                    this._printer.printText(StringUtil.SPACE);
                }
                this._printer.printText('>');
                if (this._indenting) {
                    this._printer.breakLine();
                }
            }
            this._printer.printText("<!NOTATION ");
            this._printer.printText(str);
            this._printer.printText(" SYSTEM ");
            printDoctypeURL(str3);
            this._printer.printText('>');
            if (this._indenting) {
                this._printer.breakLine();
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    protected void prepare() throws IOException {
        Printer printer;
        if (this._prepared) {
            return;
        }
        if (this._writer == null && this._output == null) {
            throw new IOException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "NoWriterSupplied", null));
        }
        EncodingInfo encodingInfo = this._format.getEncodingInfo();
        this._encodingInfo = encodingInfo;
        OutputStream outputStream = this._output;
        if (outputStream != null) {
            this._writer = encodingInfo.getWriter(outputStream);
        }
        if (this._format.getIndenting()) {
            this._indenting = true;
            printer = new IndentPrinter(this._writer, this._format);
        } else {
            this._indenting = false;
            printer = new Printer(this._writer, this._format);
        }
        this._printer = printer;
        this._elementStateCount = 0;
        ElementState elementState = this._elementStates[0];
        elementState.namespaceURI = null;
        elementState.localName = null;
        elementState.rawName = null;
        elementState.preserveSpace = this._format.getPreserveSpace();
        elementState.empty = true;
        elementState.afterElement = false;
        elementState.afterComment = false;
        elementState.inCData = false;
        elementState.doCData = false;
        elementState.prefixes = null;
        this._docTypePublicId = this._format.getDoctypePublic();
        this._docTypeSystemId = this._format.getDoctypeSystem();
        this._started = false;
        this._prepared = true;
    }

    protected void printCDATAText(String str) throws IOException {
        int i;
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == ']' && (i = i2 + 2) < length && str.charAt(i2 + 1) == ']' && str.charAt(i) == '>') {
                if (this.fDOMErrorHandler != null) {
                    if ((this.features & 16) == 0) {
                        String message = DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "EndingCDATA", null);
                        if ((this.features & 2) != 0) {
                            modifyDOMError(message, (short) 3, "wf-invalid-character", this.fCurrentNode);
                            this.fDOMErrorHandler.handleError(this.fDOMError);
                            throw new LSException((short) 82, message);
                        }
                        modifyDOMError(message, (short) 2, "cdata-section-not-splitted", this.fCurrentNode);
                        if (!this.fDOMErrorHandler.handleError(this.fDOMError)) {
                            throw new LSException((short) 82, message);
                        }
                    } else {
                        modifyDOMError(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "SplittingCDATA", null), (short) 1, null, this.fCurrentNode);
                        this.fDOMErrorHandler.handleError(this.fDOMError);
                    }
                }
                this._printer.printText("]]]]><![CDATA[>");
                i2 = i;
            } else if (!XMLChar.isValid(cCharAt)) {
                i2++;
                if (i2 < length) {
                    surrogates(cCharAt, str.charAt(i2), true);
                } else {
                    fatalError("The character '" + cCharAt + "' is an invalid XML character");
                }
            } else if ((cCharAt >= ' ' && this._encodingInfo.isPrintable(cCharAt) && cCharAt != 127) || cCharAt == '\n' || cCharAt == '\r' || cCharAt == '\t') {
                this._printer.printText(cCharAt);
            } else {
                this._printer.printText("]]>&#x");
                this._printer.printText(Integer.toHexString(cCharAt));
                this._printer.printText(";<![CDATA[");
            }
            i2++;
        }
    }

    protected void printDoctypeURL(String str) throws IOException {
        this._printer.printText('\"');
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\"' || str.charAt(i) < ' ' || str.charAt(i) > 127) {
                this._printer.printText(MangleConstant.PLATFORM_FUNCTION_MARKER);
                this._printer.printText(Integer.toHexString(str.charAt(i)));
            } else {
                this._printer.printText(str.charAt(i));
            }
        }
        this._printer.printText('\"');
    }

    protected void printEscaped(int i) throws IOException {
        Printer printer;
        String entityRef = getEntityRef(i);
        if (entityRef != null) {
            this._printer.printText(Typography.amp);
            this._printer.printText(entityRef);
            this._printer.printText(';');
            return;
        }
        if ((i < 32 || !this._encodingInfo.isPrintable((char) i) || i == 127) && i != 10 && i != 13 && i != 9) {
            printHex(i);
            return;
        }
        if (i < 65536) {
            printer = this._printer;
        } else {
            int i2 = i - 65536;
            this._printer.printText((char) ((i2 >> 10) + 55296));
            printer = this._printer;
            i = (i2 & 1023) + 56320;
        }
        printer.printText((char) i);
    }

    protected void printEscaped(String str) throws IOException {
        int i;
        int i2 = 0;
        while (i2 < str.length()) {
            int iCharAt = str.charAt(i2);
            if ((iCharAt & 64512) == 55296 && (i = i2 + 1) < str.length()) {
                char cCharAt = str.charAt(i);
                if ((64512 & cCharAt) == 56320) {
                    iCharAt = ((((iCharAt - 55296) << 10) + 65536) + cCharAt) - 56320;
                    i2 = i;
                }
            }
            printEscaped(iCharAt);
            i2++;
        }
    }

    final void printHex(int i) throws IOException {
        this._printer.printText("&#x");
        this._printer.printText(Integer.toHexString(i));
        this._printer.printText(';');
    }

    protected void printText(String str, boolean z, boolean z2) throws IOException {
        int i = 0;
        if (z) {
            while (i < str.length()) {
                char cCharAt = str.charAt(i);
                if (cCharAt == '\n' || cCharAt == '\r' || z2) {
                    this._printer.printText(cCharAt);
                } else {
                    printEscaped(cCharAt);
                }
                i++;
            }
            return;
        }
        while (i < str.length()) {
            char cCharAt2 = str.charAt(i);
            if (cCharAt2 == ' ' || cCharAt2 == '\f' || cCharAt2 == '\t' || cCharAt2 == '\n' || cCharAt2 == '\r') {
                this._printer.printSpace();
            } else if (z2) {
                this._printer.printText(cCharAt2);
            } else {
                printEscaped(cCharAt2);
            }
            i++;
        }
    }

    protected void printText(char[] cArr, int i, int i2, boolean z, boolean z2) throws IOException {
        if (z) {
            while (true) {
                int i3 = i2 - 1;
                if (i2 <= 0) {
                    return;
                }
                char c = cArr[i];
                i++;
                if (c == '\n' || c == '\r' || z2) {
                    this._printer.printText(c);
                } else {
                    printEscaped(c);
                }
                i2 = i3;
            }
        } else {
            while (true) {
                int i4 = i2 - 1;
                if (i2 <= 0) {
                    return;
                }
                char c2 = cArr[i];
                i++;
                if (c2 == ' ' || c2 == '\f' || c2 == '\t' || c2 == '\n' || c2 == '\r') {
                    this._printer.printSpace();
                } else if (z2) {
                    this._printer.printText(c2);
                } else {
                    printEscaped(c2);
                }
                i2 = i4;
            }
        }
    }

    @Override // org.xml.sax.ContentHandler
    public final void processingInstruction(String str, String str2) throws SAXException {
        try {
            processingInstructionIO(str, str2);
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    public void processingInstructionIO(String str, String str2) throws IOException {
        ElementState elementStateContent = content();
        int iIndexOf = str.indexOf("?>");
        if (iIndexOf >= 0) {
            this.fStrBuffer.append("<?").append(str.substring(0, iIndexOf));
        } else {
            this.fStrBuffer.append("<?").append(str);
        }
        if (str2 != null) {
            this.fStrBuffer.append(StringUtil.SPACE);
            int iIndexOf2 = str2.indexOf("?>");
            if (iIndexOf2 >= 0) {
                this.fStrBuffer.append(str2.substring(0, iIndexOf2));
            } else {
                this.fStrBuffer.append(str2);
            }
        }
        this.fStrBuffer.append("?>");
        if (isDocumentState()) {
            if (this._preRoot == null) {
                this._preRoot = new Vector();
            }
            this._preRoot.addElement(this.fStrBuffer.toString());
        } else {
            this._printer.indent();
            printText(this.fStrBuffer.toString(), true, true);
            this._printer.unindent();
            if (this._indenting) {
                elementStateContent.afterElement = true;
            }
        }
        this.fStrBuffer.setLength(0);
    }

    public boolean reset() {
        if (this._elementStateCount > 1) {
            throw new IllegalStateException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "ResetInMiddle", null));
        }
        this._prepared = false;
        this.fCurrentNode = null;
        this.fStrBuffer.setLength(0);
        return true;
    }

    @Override // org.apache.xml.serialize.DOMSerializer
    public void serialize(Document document) throws IOException {
        reset();
        prepare();
        serializeNode(document);
        serializePreRoot();
        cleanup();
        this._printer.flush();
        if (this._printer.getException() != null) {
            throw this._printer.getException();
        }
    }

    @Override // org.apache.xml.serialize.DOMSerializer
    public void serialize(DocumentFragment documentFragment) throws IOException {
        reset();
        prepare();
        serializeNode(documentFragment);
        cleanup();
        this._printer.flush();
        if (this._printer.getException() != null) {
            throw this._printer.getException();
        }
    }

    @Override // org.apache.xml.serialize.DOMSerializer
    public void serialize(Element element) throws IOException {
        reset();
        prepare();
        serializeNode(element);
        cleanup();
        this._printer.flush();
        if (this._printer.getException() != null) {
            throw this._printer.getException();
        }
    }

    protected abstract void serializeElement(Element element) throws IOException;

    protected void serializeNode(Node node) throws IOException {
        String nodeValue;
        short sAcceptNode;
        short sAcceptNode2;
        String nodeValue2;
        short sAcceptNode3;
        String str;
        this.fCurrentNode = node;
        short nodeType = node.getNodeType();
        if (nodeType == 1) {
            LSSerializerFilter lSSerializerFilter = this.fDOMFilter;
            if (lSSerializerFilter != null && (lSSerializerFilter.getWhatToShow() & 1) != 0) {
                short sAcceptNode4 = this.fDOMFilter.acceptNode(node);
                if (sAcceptNode4 == 2) {
                    return;
                }
                if (sAcceptNode4 == 3) {
                    for (Node firstChild = node.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                        serializeNode(firstChild);
                    }
                    return;
                }
            }
            serializeElement((Element) node);
            return;
        }
        if (nodeType != 11) {
            if (nodeType == 3) {
                nodeValue = node.getNodeValue();
                if (nodeValue == null) {
                    return;
                }
                LSSerializerFilter lSSerializerFilter2 = this.fDOMFilter;
                if (lSSerializerFilter2 != null && (4 & lSSerializerFilter2.getWhatToShow()) != 0) {
                    short sAcceptNode5 = this.fDOMFilter.acceptNode(node);
                    if (sAcceptNode5 == 2 || sAcceptNode5 == 3) {
                        return;
                    }
                } else if (this._indenting && !getElementState().preserveSpace && nodeValue.replace('\n', StringUtil.SPACE).trim().length() == 0) {
                    return;
                }
            } else if (nodeType == 4) {
                nodeValue = node.getNodeValue();
                if ((this.features & 8) != 0) {
                    if (nodeValue != null) {
                        LSSerializerFilter lSSerializerFilter3 = this.fDOMFilter;
                        if (lSSerializerFilter3 == null || (lSSerializerFilter3.getWhatToShow() & 8) == 0 || !((sAcceptNode = this.fDOMFilter.acceptNode(node)) == 2 || sAcceptNode == 3)) {
                            startCDATA();
                            characters(nodeValue);
                            endCDATA();
                            return;
                        }
                        return;
                    }
                    return;
                }
            } else {
                if (nodeType == 5) {
                    endCDATA();
                    content();
                    if ((this.features & 4) == 0 && node.getFirstChild() != null) {
                        for (Node firstChild2 = node.getFirstChild(); firstChild2 != null; firstChild2 = firstChild2.getNextSibling()) {
                            serializeNode(firstChild2);
                        }
                        return;
                    }
                    LSSerializerFilter lSSerializerFilter4 = this.fDOMFilter;
                    if (lSSerializerFilter4 != null && (lSSerializerFilter4.getWhatToShow() & 16) != 0) {
                        short sAcceptNode6 = this.fDOMFilter.acceptNode(node);
                        if (sAcceptNode6 == 2) {
                            return;
                        }
                        if (sAcceptNode6 == 3) {
                            for (Node firstChild3 = node.getFirstChild(); firstChild3 != null; firstChild3 = firstChild3.getNextSibling()) {
                                serializeNode(firstChild3);
                            }
                            return;
                        }
                    }
                    checkUnboundNamespacePrefixedNode(node);
                    this._printer.printText(ContainerUtils.FIELD_DELIMITER);
                    this._printer.printText(node.getNodeName());
                    this._printer.printText(";");
                    return;
                }
                if (nodeType == 7) {
                    LSSerializerFilter lSSerializerFilter5 = this.fDOMFilter;
                    if (lSSerializerFilter5 == null || (lSSerializerFilter5.getWhatToShow() & 64) == 0 || !((sAcceptNode2 = this.fDOMFilter.acceptNode(node)) == 2 || sAcceptNode2 == 3)) {
                        processingInstructionIO(node.getNodeName(), node.getNodeValue());
                        return;
                    }
                    return;
                }
                if (nodeType == 8) {
                    if (this._format.getOmitComments() || (nodeValue2 = node.getNodeValue()) == null) {
                        return;
                    }
                    LSSerializerFilter lSSerializerFilter6 = this.fDOMFilter;
                    if (lSSerializerFilter6 == null || (lSSerializerFilter6.getWhatToShow() & 128) == 0 || !((sAcceptNode3 = this.fDOMFilter.acceptNode(node)) == 2 || sAcceptNode3 == 3)) {
                        comment(nodeValue2);
                        return;
                    }
                    return;
                }
                if (nodeType != 9) {
                    return;
                }
                DocumentType doctype = ((Document) node).getDoctype();
                if (doctype != null) {
                    try {
                        this._printer.enterDTD();
                        this._docTypePublicId = doctype.getPublicId();
                        this._docTypeSystemId = doctype.getSystemId();
                        String internalSubset = doctype.getInternalSubset();
                        if (internalSubset != null && internalSubset.length() > 0) {
                            this._printer.printText(internalSubset);
                        }
                        endDTD();
                    } catch (NoSuchMethodError unused) {
                        Class<?> cls = doctype.getClass();
                        String str2 = null;
                        try {
                            java.lang.reflect.Method method = cls.getMethod("getPublicId", null);
                            if (method.getReturnType().equals(String.class)) {
                                str = (String) method.invoke(doctype, null);
                            } else {
                                str = null;
                            }
                        } catch (Exception unused2) {
                        }
                        try {
                            java.lang.reflect.Method method2 = cls.getMethod("getSystemId", null);
                            if (method2.getReturnType().equals(String.class)) {
                                str2 = (String) method2.invoke(doctype, null);
                            }
                        } catch (Exception unused3) {
                        }
                        this._printer.enterDTD();
                        this._docTypePublicId = str;
                        this._docTypeSystemId = str2;
                        endDTD();
                    }
                }
            }
            characters(nodeValue);
            return;
        }
        for (Node firstChild4 = node.getFirstChild(); firstChild4 != null; firstChild4 = firstChild4.getNextSibling()) {
            serializeNode(firstChild4);
        }
    }

    protected void serializePreRoot() throws IOException {
        if (this._preRoot != null) {
            for (int i = 0; i < this._preRoot.size(); i++) {
                printText((String) this._preRoot.elementAt(i), true, true);
                if (this._indenting) {
                    this._printer.breakLine();
                }
            }
            this._preRoot.removeAllElements();
        }
    }

    @Override // org.xml.sax.ContentHandler
    public void setDocumentLocator(Locator locator) {
    }

    @Override // org.apache.xml.serialize.Serializer
    public void setOutputByteStream(OutputStream outputStream) {
        if (outputStream == null) {
            throw new NullPointerException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "ArgumentIsNull", new Object[]{"output"}));
        }
        this._output = outputStream;
        this._writer = null;
        reset();
    }

    @Override // org.apache.xml.serialize.Serializer
    public void setOutputCharStream(Writer writer) {
        if (writer == null) {
            throw new NullPointerException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "ArgumentIsNull", new Object[]{"writer"}));
        }
        this._writer = writer;
        this._output = null;
        reset();
    }

    @Override // org.apache.xml.serialize.Serializer
    public void setOutputFormat(OutputFormat outputFormat) {
        if (outputFormat == null) {
            throw new NullPointerException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "ArgumentIsNull", new Object[]{ConfigConstants.CONFIG_KEY_FORMAT}));
        }
        this._format = outputFormat;
        reset();
    }

    @Override // org.xml.sax.ContentHandler
    public void skippedEntity(String str) throws SAXException {
        try {
            endCDATA();
            content();
            this._printer.printText(Typography.amp);
            this._printer.printText(str);
            this._printer.printText(';');
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void startCDATA() {
        getElementState().doCData = true;
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public final void startDTD(String str, String str2, String str3) throws SAXException {
        try {
            this._printer.enterDTD();
            this._docTypePublicId = str2;
            this._docTypeSystemId = str3;
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    @Override // org.xml.sax.ContentHandler
    public void startDocument() throws SAXException {
        try {
            prepare();
        } catch (IOException e) {
            throw new SAXException(e.toString());
        }
    }

    @Override // org.xml.sax.ext.LexicalHandler
    public void startEntity(String str) {
    }

    public void startNonEscaping() {
        getElementState().unescaped = true;
    }

    @Override // org.xml.sax.ContentHandler
    public void startPrefixMapping(String str, String str2) throws SAXException {
        if (this._prefixes == null) {
            this._prefixes = new Hashtable();
        }
        Hashtable hashtable = this._prefixes;
        if (str == null) {
            str = "";
        }
        hashtable.put(str2, str);
    }

    public void startPreserving() {
        getElementState().preserveSpace = true;
    }

    protected void surrogates(int i, int i2, boolean z) throws IOException {
        StringBuilder sb;
        StringBuilder sbAppend;
        if (XMLChar.isHighSurrogate(i)) {
            if (XMLChar.isLowSurrogate(i2)) {
                i = XMLChar.supplemental((char) i, (char) i2);
                if (XMLChar.isValid(i)) {
                    if (!z || !content().inCData) {
                        printHex(i);
                        return;
                    }
                    this._printer.printText("]]>&#x");
                    this._printer.printText(Integer.toHexString(i));
                    this._printer.printText(";<![CDATA[");
                    return;
                }
                sb = new StringBuilder("The character '");
            } else {
                sbAppend = new StringBuilder("The character '").append((char) i2);
            }
            fatalError(sbAppend.append("' is an invalid XML character").toString());
        }
        sb = new StringBuilder("The character '");
        sbAppend = sb.append((char) i);
        fatalError(sbAppend.append("' is an invalid XML character").toString());
    }

    @Override // org.xml.sax.DTDHandler
    public void unparsedEntityDecl(String str, String str2, String str3, String str4) throws SAXException {
        try {
            this._printer.enterDTD();
            if (str2 == null) {
                this._printer.printText("<!ENTITY ");
                this._printer.printText(str);
                this._printer.printText(" SYSTEM ");
            } else {
                this._printer.printText("<!ENTITY ");
                this._printer.printText(str);
                this._printer.printText(" PUBLIC ");
                printDoctypeURL(str2);
                this._printer.printText(StringUtil.SPACE);
            }
            printDoctypeURL(str3);
            if (str4 != null) {
                this._printer.printText(" NDATA ");
                this._printer.printText(str4);
            }
            this._printer.printText('>');
            if (this._indenting) {
                this._printer.breakLine();
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }
}
