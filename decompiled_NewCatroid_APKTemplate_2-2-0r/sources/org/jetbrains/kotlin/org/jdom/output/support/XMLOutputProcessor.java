package org.jetbrains.kotlin.org.jdom.output.support;

import java.io.IOException;
import java.io.Writer;
import org.jetbrains.kotlin.org.jdom.Comment;
import org.jetbrains.kotlin.org.jdom.DocType;
import org.jetbrains.kotlin.org.jdom.ProcessingInstruction;
import org.jetbrains.kotlin.org.jdom.output.Format;

/* JADX INFO: loaded from: classes4.dex */
public interface XMLOutputProcessor {
    void process(Writer writer, Format format, Comment comment) throws IOException;

    void process(Writer writer, Format format, DocType docType) throws IOException;

    void process(Writer writer, Format format, ProcessingInstruction processingInstruction) throws IOException;
}
