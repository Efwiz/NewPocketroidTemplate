package org.jf.baksmali.Adaptors;

import java.io.IOException;
import java.io.Writer;
import org.jf.util.IndentingWriter;

/* JADX INFO: loaded from: classes6.dex */
public class CommentingIndentingWriter extends IndentingWriter {
    public CommentingIndentingWriter(Writer writer) {
        super(writer);
    }

    @Override // org.jf.util.IndentingWriter
    protected void writeIndent() throws IOException {
        this.writer.write("# ");
        super.writeIndent();
    }
}
