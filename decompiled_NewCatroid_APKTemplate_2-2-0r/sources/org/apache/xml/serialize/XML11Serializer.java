package org.apache.xml.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes13.dex */
public class XML11Serializer extends XMLSerializer {
    protected static final boolean DEBUG = false;
    protected static final String PREFIX = "NS";
    protected boolean fDOML1;
    protected NamespaceSupport fLocalNSBinder;
    protected NamespaceSupport fNSBinder;
    protected int fNamespaceCounter;
    protected boolean fNamespaces;
    protected SymbolTable fSymbolTable;

    public XML11Serializer() {
        this.fDOML1 = false;
        this.fNamespaceCounter = 1;
        this.fNamespaces = false;
        this._format.setVersion(CompilerOptions.VERSION_1_1);
    }

    public XML11Serializer(OutputStream outputStream, OutputFormat outputFormat) {
        super(outputStream, outputFormat == null ? new OutputFormat("xml", (String) null, false) : outputFormat);
        this.fDOML1 = false;
        this.fNamespaceCounter = 1;
        this.fNamespaces = false;
        this._format.setVersion(CompilerOptions.VERSION_1_1);
    }

    public XML11Serializer(Writer writer, OutputFormat outputFormat) {
        super(writer, outputFormat);
        this.fDOML1 = false;
        this.fNamespaceCounter = 1;
        this.fNamespaces = false;
        this._format.setVersion(CompilerOptions.VERSION_1_1);
    }

    public XML11Serializer(OutputFormat outputFormat) {
        super(outputFormat);
        this.fDOML1 = false;
        this.fNamespaceCounter = 1;
        this.fNamespaces = false;
        this._format.setVersion(CompilerOptions.VERSION_1_1);
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer, org.xml.sax.ContentHandler
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
                } else if (!XML11Char.isXML11Valid(c)) {
                    i++;
                    if (i < i4) {
                        surrogates(c, cArr[i], true);
                    } else {
                        fatalError("The character '" + c + "' is an invalid XML character");
                    }
                } else if (this._encodingInfo.isPrintable(c) && XML11Char.isXML11ValidLiteral(c)) {
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

    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    protected final void printCDATAText(String str) throws IOException {
        int i;
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == ']' && (i = i2 + 2) < length && str.charAt(i2 + 1) == ']' && str.charAt(i) == '>') {
                if (this.fDOMErrorHandler != null) {
                    if ((this.features & 16) == 0 && (this.features & 2) == 0) {
                        modifyDOMError(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "EndingCDATA", null), (short) 3, null, this.fCurrentNode);
                        if (!this.fDOMErrorHandler.handleError(this.fDOMError)) {
                            throw new IOException();
                        }
                    } else {
                        modifyDOMError(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "SplittingCDATA", null), (short) 1, null, this.fCurrentNode);
                        this.fDOMErrorHandler.handleError(this.fDOMError);
                    }
                }
                this._printer.printText("]]]]><![CDATA[>");
                i2 = i;
            } else if (!XML11Char.isXML11Valid(cCharAt)) {
                i2++;
                if (i2 < length) {
                    surrogates(cCharAt, str.charAt(i2), true);
                } else {
                    fatalError("The character '" + cCharAt + "' is an invalid XML character");
                }
            } else if (this._encodingInfo.isPrintable(cCharAt) && XML11Char.isXML11ValidLiteral(cCharAt)) {
                this._printer.printText(cCharAt);
            } else {
                this._printer.printText("]]>&#x");
                this._printer.printText(Integer.toHexString(cCharAt));
                this._printer.printText(";<![CDATA[");
            }
            i2++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x007e  */
    @Override // org.apache.xml.serialize.XMLSerializer, org.apache.xml.serialize.BaseMarkupSerializer
    protected void printEscaped(String str) throws IOException {
        Printer printer;
        String str2;
        int length = str.length();
        int i = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (!XML11Char.isXML11Valid(cCharAt)) {
                i++;
                if (i < length) {
                    surrogates(cCharAt, str.charAt(i), false);
                } else {
                    fatalError("The character '" + cCharAt + "' is an invalid XML character");
                }
            } else if (cCharAt == '\n' || cCharAt == '\r' || cCharAt == '\t' || cCharAt == 133 || cCharAt == 8232) {
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

    @Override // org.apache.xml.serialize.XMLSerializer, org.apache.xml.serialize.BaseMarkupSerializer
    protected void printText(String str, boolean z, boolean z2) throws IOException {
        int length = str.length();
        int i = 0;
        if (z) {
            while (i < length) {
                char cCharAt = str.charAt(i);
                if (!XML11Char.isXML11Valid(cCharAt)) {
                    i++;
                    if (i < length) {
                        surrogates(cCharAt, str.charAt(i), true);
                    } else {
                        fatalError("The character '" + cCharAt + "' is an invalid XML character");
                    }
                } else if (z2 && XML11Char.isXML11ValidLiteral(cCharAt)) {
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
            if (!XML11Char.isXML11Valid(cCharAt2)) {
                i++;
                if (i < length) {
                    surrogates(cCharAt2, str.charAt(i), true);
                } else {
                    fatalError("The character '" + cCharAt2 + "' is an invalid XML character");
                }
            } else if (z2 && XML11Char.isXML11ValidLiteral(cCharAt2)) {
                this._printer.printText(cCharAt2);
            } else {
                printXMLChar(cCharAt2);
            }
            i++;
        }
    }

    @Override // org.apache.xml.serialize.XMLSerializer, org.apache.xml.serialize.BaseMarkupSerializer
    protected void printText(char[] cArr, int i, int i2, boolean z, boolean z2) throws IOException {
        if (z) {
            while (true) {
                int i3 = i2 - 1;
                if (i2 <= 0) {
                    return;
                }
                int i4 = i + 1;
                char c = cArr[i];
                if (XML11Char.isXML11Valid(c)) {
                    if (z2 && XML11Char.isXML11ValidLiteral(c)) {
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
                if (XML11Char.isXML11Valid(c2)) {
                    if (z2 && XML11Char.isXML11ValidLiteral(c2)) {
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

    @Override // org.apache.xml.serialize.XMLSerializer
    protected final void printXMLChar(int i) throws IOException {
        Printer printer;
        String str;
        if (i != 13 && i != 133 && i != 8232) {
            if (i == 60) {
                printer = this._printer;
                str = "&lt;";
            } else if (i == 38) {
                printer = this._printer;
                str = "&amp;";
            } else if (i == 62) {
                printer = this._printer;
                str = "&gt;";
            } else {
                char c = (char) i;
                if (this._encodingInfo.isPrintable(c) && XML11Char.isXML11ValidLiteral(i)) {
                    this._printer.printText(c);
                    return;
                }
            }
            printer.printText(str);
            return;
        }
        printHex(i);
    }

    @Override // org.apache.xml.serialize.XMLSerializer, org.apache.xml.serialize.BaseMarkupSerializer
    public boolean reset() {
        super.reset();
        return true;
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    protected final void surrogates(int i, int i2, boolean z) throws IOException {
        StringBuilder sb;
        StringBuilder sbAppend;
        if (XMLChar.isHighSurrogate(i)) {
            if (XMLChar.isLowSurrogate(i2)) {
                i = XMLChar.supplemental((char) i, (char) i2);
                if (XML11Char.isXML11Valid(i)) {
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
}
