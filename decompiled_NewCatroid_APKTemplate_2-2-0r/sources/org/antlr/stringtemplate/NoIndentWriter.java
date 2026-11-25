package org.antlr.stringtemplate;

import java.io.IOException;
import java.io.Writer;

/* JADX INFO: loaded from: classes12.dex */
public class NoIndentWriter extends AutoIndentWriter {
    public NoIndentWriter(Writer writer) {
        super(writer);
    }

    @Override // org.antlr.stringtemplate.AutoIndentWriter, org.antlr.stringtemplate.StringTemplateWriter
    public int write(String str) throws IOException {
        this.out.write(str);
        return str.length();
    }
}
