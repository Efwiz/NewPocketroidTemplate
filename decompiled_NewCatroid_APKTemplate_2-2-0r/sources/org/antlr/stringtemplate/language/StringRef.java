package org.antlr.stringtemplate.language;

import java.io.IOException;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateWriter;

/* JADX INFO: loaded from: classes12.dex */
public class StringRef extends Expr {
    String str;

    public StringRef(StringTemplate stringTemplate, String str) {
        super(stringTemplate);
        this.str = str;
    }

    @Override // org.antlr.stringtemplate.language.Expr
    public int write(StringTemplate stringTemplate, StringTemplateWriter stringTemplateWriter) throws IOException {
        String str = this.str;
        if (str != null) {
            return stringTemplateWriter.write(str);
        }
        return 0;
    }

    public String toString() {
        String str = this.str;
        return str != null ? str : "";
    }
}
