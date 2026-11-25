package org.jetbrains.kotlin.org.jdom.output;

import com.huawei.hms.framework.common.ContainerUtils;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import javax.xml.transform.Result;
import org.jetbrains.kotlin.org.jdom.Attribute;
import org.jetbrains.kotlin.org.jdom.CDATA;
import org.jetbrains.kotlin.org.jdom.Comment;
import org.jetbrains.kotlin.org.jdom.Content;
import org.jetbrains.kotlin.org.jdom.DocType;
import org.jetbrains.kotlin.org.jdom.Document;
import org.jetbrains.kotlin.org.jdom.Element;
import org.jetbrains.kotlin.org.jdom.EntityRef;
import org.jetbrains.kotlin.org.jdom.IllegalDataException;
import org.jetbrains.kotlin.org.jdom.Namespace;
import org.jetbrains.kotlin.org.jdom.ProcessingInstruction;
import org.jetbrains.kotlin.org.jdom.Text;
import org.jetbrains.kotlin.org.jdom.Verifier;

/* JADX INFO: loaded from: classes4.dex */
public class XMLOutputter implements Cloneable {
    private static final String CVS_ID = "@(#) $RCSfile: XMLOutputter.java,v $ $Revision: 1.117 $ $Date: 2009/07/23 05:54:23 $ $Name:  $";
    protected static final Format preserveFormat = Format.getRawFormat();
    protected Format currentFormat;
    private boolean escapeOutput;
    private Format userFormat;

    public XMLOutputter() {
        Format rawFormat = Format.getRawFormat();
        this.userFormat = rawFormat;
        this.currentFormat = rawFormat;
        this.escapeOutput = true;
    }

    public XMLOutputter(Format format) {
        Format rawFormat = Format.getRawFormat();
        this.userFormat = rawFormat;
        this.currentFormat = rawFormat;
        this.escapeOutput = true;
        Format formatClone = format.clone();
        this.userFormat = formatClone;
        this.currentFormat = formatClone;
    }

    public XMLOutputter(XMLOutputter xMLOutputter) {
        Format rawFormat = Format.getRawFormat();
        this.userFormat = rawFormat;
        this.currentFormat = rawFormat;
        this.escapeOutput = true;
        Format formatClone = xMLOutputter.userFormat.clone();
        this.userFormat = formatClone;
        this.currentFormat = formatClone;
    }

    public void setFormat(Format format) {
        Format formatClone = format.clone();
        this.userFormat = formatClone;
        this.currentFormat = formatClone;
    }

    public Format getFormat() {
        return this.userFormat.clone();
    }

    public void output(Document document, OutputStream outputStream) throws IOException {
        output(document, makeWriter(outputStream));
    }

    public void output(DocType docType, OutputStream outputStream) throws IOException {
        output(docType, makeWriter(outputStream));
    }

    public void output(Element element, OutputStream outputStream) throws IOException {
        output(element, makeWriter(outputStream));
    }

    public void outputElementContent(Element element, OutputStream outputStream) throws IOException {
        outputElementContent(element, makeWriter(outputStream));
    }

    public void output(List list, OutputStream outputStream) throws IOException {
        output(list, makeWriter(outputStream));
    }

    public void output(CDATA cdata, OutputStream outputStream) throws IOException {
        output(cdata, makeWriter(outputStream));
    }

    public void output(Text text, OutputStream outputStream) throws IOException {
        output(text, makeWriter(outputStream));
    }

    public void output(Comment comment, OutputStream outputStream) throws IOException {
        output(comment, makeWriter(outputStream));
    }

    public void output(ProcessingInstruction processingInstruction, OutputStream outputStream) throws IOException {
        output(processingInstruction, makeWriter(outputStream));
    }

    public void output(EntityRef entityRef, OutputStream outputStream) throws IOException {
        output(entityRef, makeWriter(outputStream));
    }

    private Writer makeWriter(OutputStream outputStream) throws UnsupportedEncodingException {
        return makeWriter(outputStream, this.userFormat.encoding);
    }

    private static Writer makeWriter(OutputStream outputStream, String str) throws UnsupportedEncodingException {
        if ("UTF-8".equals(str)) {
            str = "UTF8";
        }
        return new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(outputStream), str));
    }

    public void output(Document document, Writer writer) throws IOException {
        printDeclaration(writer, document, this.userFormat.encoding);
        List<Content> content = document.getContent();
        int size = content.size();
        for (int i = 0; i < size; i++) {
            Content content2 = content.get(i);
            if (content2 instanceof Element) {
                printElement(writer, document.getRootElement(), 0, createNamespaceStack());
            } else if (content2 instanceof Comment) {
                printComment(writer, (Comment) content2);
            } else if (content2 instanceof ProcessingInstruction) {
                printProcessingInstruction(writer, (ProcessingInstruction) content2);
            } else if (content2 instanceof DocType) {
                printDocType(writer, document.getDocType());
                writeLineSeparator(writer);
            }
            newline(writer);
            indent(writer, 0);
        }
        writeLineSeparator(writer);
        writer.flush();
    }

    private void writeLineSeparator(Writer writer) throws IOException {
        if (this.currentFormat.lineSeparator != null) {
            writer.write(this.currentFormat.lineSeparator);
        }
    }

    public void output(DocType docType, Writer writer) throws IOException {
        printDocType(writer, docType);
        writer.flush();
    }

    public void output(Element element, Writer writer) throws IOException {
        printElement(writer, element, 0, createNamespaceStack());
        writer.flush();
    }

    public void outputElementContent(Element element, Writer writer) throws IOException {
        List<Content> content = element.getContent();
        printContentRange(writer, content, 0, content.size(), 0, createNamespaceStack());
        writer.flush();
    }

    public void output(List list, Writer writer) throws IOException {
        printContentRange(writer, list, 0, list.size(), 0, createNamespaceStack());
        writer.flush();
    }

    public void output(CDATA cdata, Writer writer) throws IOException {
        printCDATA(writer, cdata);
        writer.flush();
    }

    public void output(Text text, Writer writer) throws IOException {
        printText(writer, text);
        writer.flush();
    }

    public void output(Comment comment, Writer writer) throws IOException {
        printComment(writer, comment);
        writer.flush();
    }

    public void output(ProcessingInstruction processingInstruction, Writer writer) throws IOException {
        boolean z = this.currentFormat.ignoreTrAXEscapingPIs;
        this.currentFormat.setIgnoreTrAXEscapingPIs(true);
        printProcessingInstruction(writer, processingInstruction);
        this.currentFormat.setIgnoreTrAXEscapingPIs(z);
        writer.flush();
    }

    public void output(EntityRef entityRef, Writer writer) throws IOException {
        printEntityRef(writer, entityRef);
        writer.flush();
    }

    public String outputString(Document document) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(document, stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public String outputString(DocType docType) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(docType, stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public String outputString(Element element) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(element, stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public String outputString(List list) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(list, stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public String outputString(CDATA cdata) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(cdata, (Writer) stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public String outputString(Text text) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(text, stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public String outputString(Comment comment) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(comment, stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public String outputString(ProcessingInstruction processingInstruction) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(processingInstruction, stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public String outputString(EntityRef entityRef) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(entityRef, stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    protected void printDeclaration(Writer writer, Document document, String str) throws IOException {
        if (this.userFormat.omitDeclaration) {
            return;
        }
        writer.write("<?xml version=\"1.0\"");
        if (!this.userFormat.omitEncoding) {
            writer.write(" encoding=\"" + str + "\"");
        }
        writer.write("?>");
        writeLineSeparator(writer);
    }

    protected void printDocType(Writer writer, DocType docType) throws IOException {
        boolean z;
        String publicID = docType.getPublicID();
        String systemID = docType.getSystemID();
        String internalSubset = docType.getInternalSubset();
        writer.write("<!DOCTYPE ");
        writer.write(docType.getElementName());
        if (publicID != null) {
            writer.write(" PUBLIC \"");
            writer.write(publicID);
            writer.write("\"");
            z = true;
        } else {
            z = false;
        }
        if (systemID != null) {
            if (!z) {
                writer.write(" SYSTEM");
            }
            writer.write(" \"");
            writer.write(systemID);
            writer.write("\"");
        }
        if (internalSubset != null && !internalSubset.equals("")) {
            writer.write(" [");
            writeLineSeparator(writer);
            writer.write(docType.getInternalSubset());
            writer.write("]");
        }
        writer.write(">");
    }

    protected void printComment(Writer writer, Comment comment) throws IOException {
        writer.write("<!--");
        writer.write(comment.getText());
        writer.write("-->");
    }

    protected void printProcessingInstruction(Writer writer, ProcessingInstruction processingInstruction) throws IOException {
        String target = processingInstruction.getTarget();
        if (!this.currentFormat.ignoreTrAXEscapingPIs) {
            if (target.equals(Result.PI_DISABLE_OUTPUT_ESCAPING)) {
                this.escapeOutput = false;
                return;
            } else if (target.equals(Result.PI_ENABLE_OUTPUT_ESCAPING)) {
                this.escapeOutput = true;
                return;
            }
        }
        String data = processingInstruction.getData();
        if (!"".equals(data)) {
            writer.write("<?");
            writer.write(target);
            writer.write(" ");
            writer.write(data);
            writer.write("?>");
            return;
        }
        writer.write("<?");
        writer.write(target);
        writer.write("?>");
    }

    protected void printEntityRef(Writer writer, EntityRef entityRef) throws IOException {
        writer.write(ContainerUtils.FIELD_DELIMITER);
        writer.write(entityRef.getName());
        writer.write(";");
    }

    protected void printCDATA(Writer writer, CDATA cdata) throws IOException {
        String text;
        if (this.currentFormat.mode == Format.TextMode.NORMALIZE) {
            text = cdata.getTextNormalize();
        } else {
            Format.TextMode textMode = this.currentFormat.mode;
            Format.TextMode textMode2 = Format.TextMode.TRIM;
            text = cdata.getText();
            if (textMode == textMode2) {
                text = text.trim();
            }
        }
        writer.write("<![CDATA[");
        writer.write(text);
        writer.write("]]>");
    }

    protected void printText(Writer writer, Text text) throws IOException {
        String text2;
        if (this.currentFormat.mode == Format.TextMode.NORMALIZE) {
            text2 = text.getTextNormalize();
        } else {
            Format.TextMode textMode = this.currentFormat.mode;
            Format.TextMode textMode2 = Format.TextMode.TRIM;
            text2 = text.getText();
            if (textMode == textMode2) {
                text2 = text2.trim();
            }
        }
        writer.write(escapeElementEntities(text2));
    }

    private void printString(Writer writer, String str) throws IOException {
        if (this.currentFormat.mode == Format.TextMode.NORMALIZE) {
            str = Text.normalizeString(str);
        } else if (this.currentFormat.mode == Format.TextMode.TRIM) {
            str = str.trim();
        }
        writer.write(escapeElementEntities(str));
    }

    protected void printElement(Writer writer, Element element, int i, NamespaceStack namespaceStack) throws IOException {
        List<Attribute> attributes = element.getAttributes();
        List<Content> content = element.getContent();
        String attributeValue = attributes != null ? element.getAttributeValue("space", Namespace.XML_NAMESPACE) : null;
        Format format = this.currentFormat;
        if ("default".equals(attributeValue)) {
            this.currentFormat = this.userFormat;
        } else if ("preserve".equals(attributeValue)) {
            this.currentFormat = preserveFormat;
        }
        writer.write("<");
        printQualifiedName(writer, element);
        int size = namespaceStack.size();
        printElementNamespace(writer, element, namespaceStack);
        printAdditionalNamespaces(writer, element, namespaceStack);
        if (attributes != null) {
            printAttributes(writer, attributes, element, namespaceStack);
        }
        int iSkipLeadingWhite = skipLeadingWhite(content, 0);
        int size2 = content.size();
        if (iSkipLeadingWhite >= size2) {
            if (this.currentFormat.expandEmptyElements) {
                writer.write("></");
                printQualifiedName(writer, element);
                writer.write(">");
            } else {
                writer.write(" />");
            }
        } else {
            writer.write(">");
            if (nextNonText(content, iSkipLeadingWhite) < size2) {
                newline(writer);
                printContentRange(writer, content, iSkipLeadingWhite, size2, i + 1, namespaceStack);
                newline(writer);
                indent(writer, i);
            } else {
                printTextRange(writer, content, iSkipLeadingWhite, size2);
            }
            writer.write("</");
            printQualifiedName(writer, element);
            writer.write(">");
        }
        while (namespaceStack.size() > size) {
            namespaceStack.pop();
        }
        this.currentFormat = format;
    }

    private void printContentRange(Writer writer, List list, int i, int i2, int i3, NamespaceStack namespaceStack) throws IOException {
        int i4 = i;
        while (i4 < i2) {
            boolean z = i4 == i;
            Object obj = list.get(i4);
            if ((obj instanceof Text) || (obj instanceof EntityRef)) {
                int iSkipLeadingWhite = skipLeadingWhite(list, i4);
                int iNextNonText = nextNonText(list, iSkipLeadingWhite);
                if (iSkipLeadingWhite < iNextNonText) {
                    if (!z) {
                        newline(writer);
                    }
                    indent(writer, i3);
                    printTextRange(writer, list, iSkipLeadingWhite, iNextNonText);
                }
                i4 = iNextNonText;
            } else {
                if (!z) {
                    newline(writer);
                }
                indent(writer, i3);
                if (obj instanceof Comment) {
                    printComment(writer, (Comment) obj);
                } else if (obj instanceof Element) {
                    printElement(writer, (Element) obj, i3, namespaceStack);
                } else if (obj instanceof ProcessingInstruction) {
                    printProcessingInstruction(writer, (ProcessingInstruction) obj);
                }
                i4++;
            }
        }
    }

    private void printTextRange(Writer writer, List list, int i, int i2) throws IOException {
        String text;
        int iSkipLeadingWhite = skipLeadingWhite(list, i);
        if (iSkipLeadingWhite < list.size()) {
            int iSkipTrailingWhite = skipTrailingWhite(list, i2);
            String str = null;
            while (iSkipLeadingWhite < iSkipTrailingWhite) {
                Object obj = list.get(iSkipLeadingWhite);
                if (obj instanceof Text) {
                    text = ((Text) obj).getText();
                } else if (obj instanceof EntityRef) {
                    text = ContainerUtils.FIELD_DELIMITER + ((EntityRef) obj).getValue() + ";";
                } else {
                    throw new IllegalStateException("Should see only CDATA, Text, or EntityRef");
                }
                if (text != null && !"".equals(text)) {
                    if (str != null && ((this.currentFormat.mode == Format.TextMode.NORMALIZE || this.currentFormat.mode == Format.TextMode.TRIM) && (endsWithWhite(str) || startsWithWhite(text)))) {
                        writer.write(" ");
                    }
                    if (obj instanceof CDATA) {
                        printCDATA(writer, (CDATA) obj);
                    } else if (obj instanceof EntityRef) {
                        printEntityRef(writer, (EntityRef) obj);
                    } else {
                        printString(writer, text);
                    }
                    str = text;
                }
                iSkipLeadingWhite++;
            }
        }
    }

    private void printNamespace(Writer writer, Namespace namespace, NamespaceStack namespaceStack) throws IOException {
        String prefix = namespace.getPrefix();
        String uri = namespace.getURI();
        if (uri.equals(namespaceStack.getURI(prefix))) {
            return;
        }
        writer.write(" xmlns");
        if (!prefix.equals("")) {
            writer.write(":");
            writer.write(prefix);
        }
        writer.write("=\"");
        writer.write(escapeAttributeEntities(uri));
        writer.write("\"");
        namespaceStack.push(namespace);
    }

    protected void printAttributes(Writer writer, List list, Element element, NamespaceStack namespaceStack) throws IOException {
        for (int i = 0; i < list.size(); i++) {
            Attribute attribute = (Attribute) list.get(i);
            Namespace namespace = attribute.getNamespace();
            if (namespace != Namespace.NO_NAMESPACE && namespace != Namespace.XML_NAMESPACE) {
                printNamespace(writer, namespace, namespaceStack);
            }
            writer.write(" ");
            printQualifiedName(writer, attribute);
            writer.write(ContainerUtils.KEY_VALUE_DELIMITER);
            writer.write("\"");
            writer.write(escapeAttributeEntities(attribute.getValue()));
            writer.write("\"");
        }
    }

    private void printElementNamespace(Writer writer, Element element, NamespaceStack namespaceStack) throws IOException {
        Namespace namespace = element.getNamespace();
        if (namespace == Namespace.XML_NAMESPACE) {
            return;
        }
        if (namespace == Namespace.NO_NAMESPACE && namespaceStack.getURI("") == null) {
            return;
        }
        printNamespace(writer, namespace, namespaceStack);
    }

    private void printAdditionalNamespaces(Writer writer, Element element, NamespaceStack namespaceStack) throws IOException {
        List<Namespace> additionalNamespaces = element.getAdditionalNamespaces();
        if (additionalNamespaces != null) {
            for (int i = 0; i < additionalNamespaces.size(); i++) {
                printNamespace(writer, additionalNamespaces.get(i), namespaceStack);
            }
        }
    }

    private void newline(Writer writer) throws IOException {
        if (this.currentFormat.indent != null) {
            writeLineSeparator(writer);
        }
    }

    private void indent(Writer writer, int i) throws IOException {
        if (this.currentFormat.indent == null || this.currentFormat.indent.equals("")) {
            return;
        }
        for (int i2 = 0; i2 < i; i2++) {
            writer.write(this.currentFormat.indent);
        }
    }

    private int skipLeadingWhite(List list, int i) {
        if (i < 0) {
            i = 0;
        }
        int size = list.size();
        if (this.currentFormat.mode == Format.TextMode.TRIM_FULL_WHITE || this.currentFormat.mode == Format.TextMode.NORMALIZE || this.currentFormat.mode == Format.TextMode.TRIM) {
            while (i < size) {
                if (!isAllWhitespace(list.get(i))) {
                    return i;
                }
                i++;
            }
        }
        return i;
    }

    private int skipTrailingWhite(List list, int i) {
        int size = list.size();
        if (i > size) {
            i = size;
        }
        if (this.currentFormat.mode == Format.TextMode.TRIM_FULL_WHITE || this.currentFormat.mode == Format.TextMode.NORMALIZE || this.currentFormat.mode == Format.TextMode.TRIM) {
            while (i >= 0 && isAllWhitespace(list.get(i - 1))) {
                i--;
            }
        }
        return i;
    }

    private static int nextNonText(List list, int i) {
        if (i < 0) {
            i = 0;
        }
        int size = list.size();
        while (i < size) {
            Object obj = list.get(i);
            if (!(obj instanceof Text) && !(obj instanceof EntityRef)) {
                return i;
            }
            i++;
        }
        return size;
    }

    private boolean isAllWhitespace(Object obj) {
        String text;
        if (obj instanceof String) {
            text = (String) obj;
        } else if (obj instanceof Text) {
            text = ((Text) obj).getText();
        } else {
            boolean z = obj instanceof EntityRef;
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if (!Verifier.isXMLWhitespace(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean startsWithWhite(String str) {
        return str != null && str.length() > 0 && Verifier.isXMLWhitespace(str.charAt(0));
    }

    private boolean endsWithWhite(String str) {
        return str != null && str.length() > 0 && Verifier.isXMLWhitespace(str.charAt(str.length() - 1));
    }

    /* JADX WARN: Code duplicated, block: B:44:0x00cc A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:45:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:46:0x00e4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:47:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:48:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:58:0x00ee A[SYNTHETIC] */
    public String escapeAttributeEntities(String str) {
        String str2;
        int i;
        EscapeStrategy escapeStrategy = this.currentFormat.escapeStrategy;
        StringBuffer stringBuffer = null;
        int i2 = 0;
        while (i2 < str.length()) {
            int iCharAt = str.charAt(i2);
            if (iCharAt == 9) {
                str2 = "&#x9;";
            } else if (iCharAt == 10) {
                str2 = "&#xA;";
            } else if (iCharAt == 13) {
                str2 = "&#xD;";
            } else if (iCharAt == 34) {
                str2 = "&quot;";
            } else if (iCharAt == 38) {
                str2 = "&amp;";
            } else if (iCharAt == 60) {
                str2 = "&lt;";
            } else if (iCharAt == 62) {
                str2 = "&gt;";
            } else {
                char c = (char) iCharAt;
                if (escapeStrategy.shouldEscape(c)) {
                    if (Verifier.isHighSurrogate(c)) {
                        i = i2 + 1;
                        if (i < str.length()) {
                            char cCharAt = str.charAt(i);
                            if (!Verifier.isLowSurrogate(cCharAt)) {
                                throw new IllegalDataException("Could not decode surrogate pair 0x" + Integer.toHexString(iCharAt) + " / 0x" + Integer.toHexString(cCharAt));
                            }
                            iCharAt = Verifier.decodeSurrogatePair(c, cCharAt);
                        } else {
                            throw new IllegalDataException("Surrogate pair 0x" + Integer.toHexString(iCharAt) + " truncated");
                        }
                    } else {
                        i = i2;
                    }
                    str2 = "&#x" + Integer.toHexString(iCharAt) + ";";
                } else {
                    str2 = null;
                }
                if (stringBuffer == null) {
                    if (str2 != null) {
                        stringBuffer = new StringBuffer(str.length() + 20);
                        stringBuffer.append(str.substring(0, i2));
                        stringBuffer.append(str2);
                    }
                } else if (str2 == null) {
                    stringBuffer.append((char) iCharAt);
                } else {
                    stringBuffer.append(str2);
                }
                i2 = i + 1;
            }
            i = i2;
            if (stringBuffer == null) {
                if (str2 != null) {
                    stringBuffer = new StringBuffer(str.length() + 20);
                    stringBuffer.append(str.substring(0, i2));
                    stringBuffer.append(str2);
                }
            } else if (str2 == null) {
                stringBuffer.append((char) iCharAt);
            } else {
                stringBuffer.append(str2);
            }
            i2 = i + 1;
        }
        return stringBuffer == null ? str : stringBuffer.toString();
    }

    /* JADX WARN: Code duplicated, block: B:41:0x00c5 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:42:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:43:0x00dd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:44:0x00df  */
    /* JADX WARN: Code duplicated, block: B:45:0x00e4  */
    /* JADX WARN: Code duplicated, block: B:55:0x00e7 A[SYNTHETIC] */
    public String escapeElementEntities(String str) {
        String str2;
        int i;
        if (!this.escapeOutput) {
            return str;
        }
        EscapeStrategy escapeStrategy = this.currentFormat.escapeStrategy;
        int i2 = 0;
        StringBuffer stringBuffer = null;
        while (i2 < str.length()) {
            int iCharAt = str.charAt(i2);
            if (iCharAt == 10) {
                str2 = this.currentFormat.lineSeparator;
            } else if (iCharAt == 13) {
                str2 = "&#xD;";
            } else if (iCharAt == 38) {
                str2 = "&amp;";
            } else if (iCharAt == 60) {
                str2 = "&lt;";
            } else if (iCharAt == 62) {
                str2 = "&gt;";
            } else {
                char c = (char) iCharAt;
                if (escapeStrategy.shouldEscape(c)) {
                    if (Verifier.isHighSurrogate(c)) {
                        i = i2 + 1;
                        if (i < str.length()) {
                            char cCharAt = str.charAt(i);
                            if (!Verifier.isLowSurrogate(cCharAt)) {
                                throw new IllegalDataException("Could not decode surrogate pair 0x" + Integer.toHexString(iCharAt) + " / 0x" + Integer.toHexString(cCharAt));
                            }
                            iCharAt = Verifier.decodeSurrogatePair(c, cCharAt);
                        } else {
                            throw new IllegalDataException("Surrogate pair 0x" + Integer.toHexString(iCharAt) + " truncated");
                        }
                    } else {
                        i = i2;
                    }
                    str2 = "&#x" + Integer.toHexString(iCharAt) + ";";
                } else {
                    str2 = null;
                }
                if (stringBuffer == null) {
                    if (str2 != null) {
                        stringBuffer = new StringBuffer(str.length() + 20);
                        stringBuffer.append(str.substring(0, i2));
                        stringBuffer.append(str2);
                    }
                } else if (str2 == null) {
                    stringBuffer.append((char) iCharAt);
                } else {
                    stringBuffer.append(str2);
                }
                i2 = i + 1;
            }
            i = i2;
            if (stringBuffer == null) {
                if (str2 != null) {
                    stringBuffer = new StringBuffer(str.length() + 20);
                    stringBuffer.append(str.substring(0, i2));
                    stringBuffer.append(str2);
                }
            } else if (str2 == null) {
                stringBuffer.append((char) iCharAt);
            } else {
                stringBuffer.append(str2);
            }
            i2 = i + 1;
        }
        return stringBuffer == null ? str : stringBuffer.toString();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.userFormat.lineSeparator != null) {
            for (int i = 0; i < this.userFormat.lineSeparator.length(); i++) {
                char cCharAt = this.userFormat.lineSeparator.charAt(i);
                if (cCharAt == '\t') {
                    stringBuffer.append("\\t");
                } else if (cCharAt == '\n') {
                    stringBuffer.append("\\n");
                } else if (cCharAt == '\r') {
                    stringBuffer.append("\\r");
                } else {
                    stringBuffer.append("[" + ((int) cCharAt) + "]");
                }
            }
        } else {
            stringBuffer.append("null");
        }
        return "XMLOutputter[omitDeclaration = " + this.userFormat.omitDeclaration + ", encoding = " + this.userFormat.encoding + ", omitEncoding = " + this.userFormat.omitEncoding + ", indent = '" + this.userFormat.indent + "', expandEmptyElements = " + this.userFormat.expandEmptyElements + ", lineSeparator = '" + stringBuffer.toString() + "', textMode = " + this.userFormat.mode + "]";
    }

    private NamespaceStack createNamespaceStack() {
        return new NamespaceStack();
    }

    protected class NamespaceStack extends org.jetbrains.kotlin.org.jdom.output.NamespaceStack {
        protected NamespaceStack() {
        }
    }

    private void printQualifiedName(Writer writer, Element element) throws IOException {
        if (element.getNamespace().getPrefix().length() == 0) {
            writer.write(element.getName());
            return;
        }
        writer.write(element.getNamespace().getPrefix());
        writer.write(58);
        writer.write(element.getName());
    }

    private void printQualifiedName(Writer writer, Attribute attribute) throws IOException {
        String prefix = attribute.getNamespace().getPrefix();
        if (prefix != null && !prefix.equals("")) {
            writer.write(prefix);
            writer.write(58);
            writer.write(attribute.getName());
            return;
        }
        writer.write(attribute.getName());
    }
}
