package org.jetbrains.kotlin.org.jdom.output;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.jetbrains.kotlin.org.jdom.Comment;
import org.jetbrains.kotlin.org.jdom.DocType;
import org.jetbrains.kotlin.org.jdom.ProcessingInstruction;
import org.jetbrains.kotlin.org.jdom.output.support.AbstractXMLOutputProcessor;
import org.jetbrains.kotlin.org.jdom.output.support.XMLOutputProcessor;

/* JADX INFO: loaded from: classes4.dex */
public final class XMLOutputter2 implements Cloneable {
    private static final DefaultXMLProcessor DEFAULTPROCESSOR = new DefaultXMLProcessor();
    private Format myFormat;
    private XMLOutputProcessor myProcessor;

    private static final class DefaultXMLProcessor extends AbstractXMLOutputProcessor {
        private DefaultXMLProcessor() {
        }
    }

    public XMLOutputter2(Format format, XMLOutputProcessor xMLOutputProcessor) {
        this.myFormat = null;
        this.myProcessor = null;
        this.myFormat = format == null ? Format.getRawFormat() : format.clone();
        this.myProcessor = xMLOutputProcessor == null ? DEFAULTPROCESSOR : xMLOutputProcessor;
    }

    public XMLOutputter2() {
        this(null, null);
    }

    public final String outputString(DocType docType) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(docType, stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final String outputString(Comment comment) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(comment, stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final String outputString(ProcessingInstruction processingInstruction) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(processingInstruction, stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final void output(DocType docType, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, docType);
        writer.flush();
    }

    public final void output(Comment comment, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, comment);
        writer.flush();
    }

    public final void output(ProcessingInstruction processingInstruction, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, processingInstruction);
        writer.flush();
    }

    public XMLOutputter2 clone() {
        try {
            return (XMLOutputter2) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XMLOutputter[omitDeclaration = ");
        sb.append(this.myFormat.omitDeclaration);
        sb.append(", encoding = ");
        sb.append(this.myFormat.encoding);
        sb.append(", omitEncoding = ");
        sb.append(this.myFormat.omitEncoding);
        sb.append(", indent = '");
        sb.append(this.myFormat.indent);
        sb.append("', expandEmptyElements = ");
        sb.append(this.myFormat.expandEmptyElements);
        sb.append(", lineSeparator = '");
        for (char c : this.myFormat.lineSeparator.toCharArray()) {
            if (c == '\t') {
                sb.append("\\t");
            } else if (c == '\n') {
                sb.append("\\n");
            } else if (c == '\r') {
                sb.append("\\r");
            } else {
                sb.append("[" + ((int) c) + "]");
            }
        }
        sb.append("', textMode = ");
        sb.append(this.myFormat.mode + "]");
        return sb.toString();
    }
}
