package org.jetbrains.kotlin.org.jdom.output.support;

import java.io.IOException;
import java.io.Writer;
import javax.xml.transform.Result;
import org.jetbrains.kotlin.org.jdom.Comment;
import org.jetbrains.kotlin.org.jdom.DocType;
import org.jetbrains.kotlin.org.jdom.ProcessingInstruction;
import org.jetbrains.kotlin.org.jdom.output.Format;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractXMLOutputProcessor extends AbstractOutputProcessor implements XMLOutputProcessor {
    @Override // org.jetbrains.kotlin.org.jdom.output.support.XMLOutputProcessor
    public void process(Writer writer, Format format, DocType docType) throws IOException {
        printDocType(writer, new FormatStack(format), docType);
        writer.flush();
    }

    @Override // org.jetbrains.kotlin.org.jdom.output.support.XMLOutputProcessor
    public void process(Writer writer, Format format, Comment comment) throws IOException {
        printComment(writer, new FormatStack(format), comment);
        writer.flush();
    }

    @Override // org.jetbrains.kotlin.org.jdom.output.support.XMLOutputProcessor
    public void process(Writer writer, Format format, ProcessingInstruction processingInstruction) throws IOException {
        FormatStack formatStack = new FormatStack(format);
        formatStack.setIgnoreTrAXEscapingPIs(true);
        printProcessingInstruction(writer, formatStack, processingInstruction);
        writer.flush();
    }

    protected void write(Writer writer, String str) throws IOException {
        if (str == null) {
            return;
        }
        writer.write(str);
    }

    protected void printDocType(Writer writer, FormatStack formatStack, DocType docType) throws IOException {
        boolean z;
        String publicID = docType.getPublicID();
        String systemID = docType.getSystemID();
        String internalSubset = docType.getInternalSubset();
        write(writer, "<!DOCTYPE ");
        write(writer, docType.getElementName());
        if (publicID != null) {
            write(writer, " PUBLIC \"");
            write(writer, publicID);
            write(writer, "\"");
            z = true;
        } else {
            z = false;
        }
        if (systemID != null) {
            if (!z) {
                write(writer, " SYSTEM");
            }
            write(writer, " \"");
            write(writer, systemID);
            write(writer, "\"");
        }
        if (internalSubset != null && !internalSubset.equals("")) {
            write(writer, " [");
            write(writer, formatStack.getLineSeparator());
            write(writer, docType.getInternalSubset());
            write(writer, "]");
        }
        write(writer, ">");
    }

    protected void printProcessingInstruction(Writer writer, FormatStack formatStack, ProcessingInstruction processingInstruction) throws IOException {
        String target = processingInstruction.getTarget();
        if (!formatStack.isIgnoreTrAXEscapingPIs()) {
            if (target.equals(Result.PI_DISABLE_OUTPUT_ESCAPING)) {
                formatStack.setEscapeOutput(false);
                return;
            } else if (target.equals(Result.PI_ENABLE_OUTPUT_ESCAPING)) {
                formatStack.setEscapeOutput(true);
                return;
            }
        }
        String data = processingInstruction.getData();
        if (!"".equals(data)) {
            write(writer, "<?");
            write(writer, target);
            write(writer, " ");
            write(writer, data);
            write(writer, "?>");
            return;
        }
        write(writer, "<?");
        write(writer, target);
        write(writer, "?>");
    }

    protected void printComment(Writer writer, FormatStack formatStack, Comment comment) throws IOException {
        write(writer, "<!--");
        write(writer, comment.getText());
        write(writer, "-->");
    }
}
