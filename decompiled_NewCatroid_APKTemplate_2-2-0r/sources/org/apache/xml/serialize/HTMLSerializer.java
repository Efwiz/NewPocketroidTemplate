package org.apache.xml.serialize;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLSerializer extends BaseMarkupSerializer {
    public static final String XHTMLNamespace = "http://www.w3.org/1999/xhtml";
    private boolean _xhtml;
    private String fUserXHTMLNamespace;

    public HTMLSerializer() {
        this(false, new OutputFormat(Method.HTML, "ISO-8859-1", false));
    }

    public HTMLSerializer(OutputStream outputStream, OutputFormat outputFormat) {
        this(false, outputFormat == null ? new OutputFormat(Method.HTML, "ISO-8859-1", false) : outputFormat);
        setOutputByteStream(outputStream);
    }

    public HTMLSerializer(Writer writer, OutputFormat outputFormat) {
        this(false, outputFormat == null ? new OutputFormat(Method.HTML, "ISO-8859-1", false) : outputFormat);
        setOutputCharStream(writer);
    }

    public HTMLSerializer(OutputFormat outputFormat) {
        this(false, outputFormat == null ? new OutputFormat(Method.HTML, "ISO-8859-1", false) : outputFormat);
    }

    protected HTMLSerializer(boolean z, OutputFormat outputFormat) {
        super(outputFormat);
        this.fUserXHTMLNamespace = null;
        this._xhtml = z;
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    protected void characters(String str) throws IOException {
        content();
        super.characters(str);
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer, org.xml.sax.ContentHandler
    public void characters(char[] cArr, int i, int i2) throws SAXException {
        try {
            content().doCData = false;
            super.characters(cArr, i, i2);
        } catch (IOException e) {
            throw new SAXException(e);
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
        String str4;
        Printer printer;
        String lowerCase;
        String str5;
        this._printer.unindent();
        ElementState elementState = getElementState();
        if (elementState.namespaceURI == null || elementState.namespaceURI.length() == 0) {
            str4 = elementState.rawName;
        } else {
            str4 = (elementState.namespaceURI.equals(XHTMLNamespace) || ((str5 = this.fUserXHTMLNamespace) != null && str5.equals(elementState.namespaceURI))) ? elementState.localName : null;
        }
        if (!this._xhtml) {
            if (elementState.empty) {
                this._printer.printText('>');
            }
            if (str4 == null || !HTMLdtd.isOnlyOpening(str4)) {
                if (this._indenting && !elementState.preserveSpace && elementState.afterElement) {
                    this._printer.breakLine();
                }
                if (elementState.inCData) {
                    this._printer.printText("]]>");
                }
                this._printer.printText("</");
                printer = this._printer;
                lowerCase = elementState.rawName;
                printer.printText(lowerCase);
                this._printer.printText('>');
            }
        } else if (elementState.empty) {
            this._printer.printText(" />");
        } else {
            if (elementState.inCData) {
                this._printer.printText("]]>");
            }
            this._printer.printText("</");
            printer = this._printer;
            lowerCase = elementState.rawName.toLowerCase(Locale.ENGLISH);
            printer.printText(lowerCase);
            this._printer.printText('>');
        }
        ElementState elementStateLeaveElementState = leaveElementState();
        if (str4 == null || (!str4.equalsIgnoreCase("A") && !str4.equalsIgnoreCase("TD"))) {
            elementStateLeaveElementState.afterElement = true;
        }
        elementStateLeaveElementState.empty = false;
        if (isDocumentState()) {
            this._printer.flush();
        }
    }

    protected String escapeURI(String str) {
        int iIndexOf = str.indexOf("\"");
        return iIndexOf >= 0 ? str.substring(0, iIndexOf) : str;
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    protected String getEntityRef(int i) {
        return HTMLdtd.fromChar(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e3, code lost:
    
        if (org.apache.xml.serialize.HTMLdtd.isBoolean(r0, r8) != false) goto L43;
     */
    @Override // org.apache.xml.serialize.BaseMarkupSerializer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void serializeElement(org.w3c.dom.Element r13) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 364
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xml.serialize.HTMLSerializer.serializeElement(org.w3c.dom.Element):void");
    }

    @Override // org.apache.xml.serialize.BaseMarkupSerializer, org.apache.xml.serialize.Serializer
    public void setOutputFormat(OutputFormat outputFormat) {
        if (outputFormat == null) {
            outputFormat = new OutputFormat(Method.HTML, "ISO-8859-1", false);
        }
        super.setOutputFormat(outputFormat);
    }

    public void setXHTMLNamespace(String str) {
        this.fUserXHTMLNamespace = str;
    }

    protected void startDocument(String str) throws IOException {
        Printer printer;
        String str2;
        Printer printer2;
        String str3;
        String str4;
        this._printer.leaveDTD();
        if (!this._started) {
            if (this._docTypePublicId == null && this._docTypeSystemId == null) {
                if (this._xhtml) {
                    this._docTypePublicId = "-//W3C//DTD XHTML 1.0 Strict//EN";
                    str4 = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd";
                } else {
                    this._docTypePublicId = "-//W3C//DTD HTML 4.01//EN";
                    str4 = "http://www.w3.org/TR/html4/strict.dtd";
                }
                this._docTypeSystemId = str4;
            }
            if (!this._format.getOmitDocumentType()) {
                if (this._docTypePublicId != null && (!this._xhtml || this._docTypeSystemId != null)) {
                    if (this._xhtml) {
                        printer2 = this._printer;
                        str3 = "<!DOCTYPE html PUBLIC ";
                    } else {
                        printer2 = this._printer;
                        str3 = "<!DOCTYPE HTML PUBLIC ";
                    }
                    printer2.printText(str3);
                    printDoctypeURL(this._docTypePublicId);
                    if (this._docTypeSystemId != null) {
                        if (this._indenting) {
                            this._printer.breakLine();
                            this._printer.printText("                      ");
                        } else {
                            this._printer.printText(StringUtil.SPACE);
                        }
                    }
                    this._printer.printText('>');
                    this._printer.breakLine();
                } else if (this._docTypeSystemId != null) {
                    if (this._xhtml) {
                        printer = this._printer;
                        str2 = "<!DOCTYPE html SYSTEM ";
                    } else {
                        printer = this._printer;
                        str2 = "<!DOCTYPE HTML SYSTEM ";
                    }
                    printer.printText(str2);
                }
                printDoctypeURL(this._docTypeSystemId);
                this._printer.printText('>');
                this._printer.breakLine();
            }
        }
        this._started = true;
        serializePreRoot();
    }

    @Override // org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        String str4;
        boolean z;
        String prefix;
        String str5;
        Printer printer;
        Printer printer2;
        Printer printer3;
        try {
            String str6 = null;
            if (this._printer == null) {
                throw new IllegalStateException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "NoWriterSupplied", null));
            }
            ElementState elementState = getElementState();
            if (!isDocumentState()) {
                if (elementState.empty) {
                    this._printer.printText('>');
                }
                if (this._indenting && !elementState.preserveSpace && (elementState.empty || elementState.afterElement)) {
                    this._printer.breakLine();
                }
            } else if (!this._started) {
                startDocument((str2 == null || str2.length() == 0) ? str3 : str2);
            }
            boolean z2 = elementState.preserveSpace;
            boolean z3 = (str == null || str.length() == 0) ? false : true;
            if (str3 == null || str3.length() == 0) {
                str4 = (!z3 || (prefix = getPrefix(str)) == null || prefix.length() == 0) ? str2 : prefix + ":" + str2;
                z = true;
            } else {
                str4 = str3;
                z = false;
            }
            if (!z3) {
                str6 = str4;
            } else if (str.equals(XHTMLNamespace) || ((str5 = this.fUserXHTMLNamespace) != null && str5.equals(str))) {
                str6 = str2;
            }
            this._printer.printText('<');
            if (this._xhtml) {
                this._printer.printText(str4.toLowerCase(Locale.ENGLISH));
            } else {
                this._printer.printText(str4);
            }
            this._printer.indent();
            if (attributes != null) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    this._printer.printSpace();
                    String lowerCase = attributes.getQName(i).toLowerCase(Locale.ENGLISH);
                    String value = attributes.getValue(i);
                    if (!this._xhtml && !z3) {
                        if (value == null) {
                            value = "";
                        }
                        if (this._format.getPreserveEmptyAttributes() || value.length() != 0) {
                            if (HTMLdtd.isURI(str4, lowerCase)) {
                                this._printer.printText(lowerCase);
                                this._printer.printText("=\"");
                                this._printer.printText(escapeURI(value));
                                printer2 = this._printer;
                            } else if (HTMLdtd.isBoolean(str4, lowerCase)) {
                                printer3 = this._printer;
                            } else {
                                this._printer.printText(lowerCase);
                                this._printer.printText("=\"");
                                printEscaped(value);
                                printer2 = this._printer;
                            }
                            printer2.printText('\"');
                        } else {
                            printer3 = this._printer;
                        }
                        printer3.printText(lowerCase);
                    } else if (value == null) {
                        this._printer.printText(lowerCase);
                        this._printer.printText("=\"\"");
                    } else {
                        this._printer.printText(lowerCase);
                        this._printer.printText("=\"");
                        printEscaped(value);
                        printer2 = this._printer;
                        printer2.printText('\"');
                    }
                }
            }
            if (str6 != null && HTMLdtd.isPreserveSpace(str6)) {
                z2 = true;
            }
            if (z) {
                for (Map.Entry entry : this._prefixes.entrySet()) {
                    this._printer.printSpace();
                    String str7 = (String) entry.getKey();
                    String str8 = (String) entry.getValue();
                    if (str8.length() == 0) {
                        this._printer.printText("xmlns=\"");
                        printEscaped(str7);
                        printer = this._printer;
                    } else {
                        this._printer.printText("xmlns:");
                        this._printer.printText(str8);
                        this._printer.printText("=\"");
                        printEscaped(str7);
                        printer = this._printer;
                    }
                    printer.printText('\"');
                }
            }
            ElementState elementStateEnterElementState = enterElementState(str, str2, str4, z2);
            if (str6 != null && (str6.equalsIgnoreCase("A") || str6.equalsIgnoreCase("TD"))) {
                elementStateEnterElementState.empty = false;
                this._printer.printText('>');
            }
            if (str6 != null) {
                if (str4.equalsIgnoreCase("SCRIPT") || str4.equalsIgnoreCase("STYLE")) {
                    if (this._xhtml) {
                        elementStateEnterElementState.doCData = true;
                    } else {
                        elementStateEnterElementState.unescaped = true;
                    }
                }
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }

    @Override // org.xml.sax.DocumentHandler
    public void startElement(String str, AttributeList attributeList) throws SAXException {
        Printer printer;
        Printer printer2;
        try {
            if (this._printer == null) {
                throw new IllegalStateException(DOMMessageFormatter.formatMessage(DOMMessageFormatter.SERIALIZER_DOMAIN, "NoWriterSupplied", null));
            }
            ElementState elementState = getElementState();
            if (!isDocumentState()) {
                if (elementState.empty) {
                    this._printer.printText('>');
                }
                if (this._indenting && !elementState.preserveSpace && (elementState.empty || elementState.afterElement)) {
                    this._printer.breakLine();
                }
            } else if (!this._started) {
                startDocument(str);
            }
            boolean z = elementState.preserveSpace;
            this._printer.printText('<');
            if (this._xhtml) {
                this._printer.printText(str.toLowerCase(Locale.ENGLISH));
            } else {
                this._printer.printText(str);
            }
            this._printer.indent();
            if (attributeList != null) {
                for (int i = 0; i < attributeList.getLength(); i++) {
                    this._printer.printSpace();
                    String lowerCase = attributeList.getName(i).toLowerCase(Locale.ENGLISH);
                    String value = attributeList.getValue(i);
                    if (!this._xhtml) {
                        if (value == null) {
                            value = "";
                        }
                        if (this._format.getPreserveEmptyAttributes() || value.length() != 0) {
                            if (HTMLdtd.isURI(str, lowerCase)) {
                                this._printer.printText(lowerCase);
                                this._printer.printText("=\"");
                                this._printer.printText(escapeURI(value));
                                printer = this._printer;
                            } else if (HTMLdtd.isBoolean(str, lowerCase)) {
                                printer2 = this._printer;
                            } else {
                                this._printer.printText(lowerCase);
                                this._printer.printText("=\"");
                                printEscaped(value);
                                printer = this._printer;
                            }
                            printer.printText('\"');
                        } else {
                            printer2 = this._printer;
                        }
                        printer2.printText(lowerCase);
                    } else if (value == null) {
                        this._printer.printText(lowerCase);
                        this._printer.printText("=\"\"");
                    } else {
                        this._printer.printText(lowerCase);
                        this._printer.printText("=\"");
                        printEscaped(value);
                        printer = this._printer;
                        printer.printText('\"');
                    }
                }
            }
            if (HTMLdtd.isPreserveSpace(str)) {
                z = true;
            }
            ElementState elementStateEnterElementState = enterElementState(null, null, str, z);
            if (str.equalsIgnoreCase("A") || str.equalsIgnoreCase("TD")) {
                elementStateEnterElementState.empty = false;
                this._printer.printText('>');
            }
            if (str.equalsIgnoreCase("SCRIPT") || str.equalsIgnoreCase("STYLE")) {
                if (this._xhtml) {
                    elementStateEnterElementState.doCData = true;
                } else {
                    elementStateEnterElementState.unescaped = true;
                }
            }
        } catch (IOException e) {
            throw new SAXException(e);
        }
    }
}
