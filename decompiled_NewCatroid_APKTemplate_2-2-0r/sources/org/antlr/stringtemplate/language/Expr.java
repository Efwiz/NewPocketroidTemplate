package org.antlr.stringtemplate.language;

import java.io.IOException;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateWriter;

/* JADX INFO: loaded from: classes12.dex */
public abstract class Expr {
    protected StringTemplate enclosingTemplate;
    protected String indentation = null;

    public abstract int write(StringTemplate stringTemplate, StringTemplateWriter stringTemplateWriter) throws IOException;

    public Expr(StringTemplate stringTemplate) {
        this.enclosingTemplate = stringTemplate;
    }

    public StringTemplate getEnclosingTemplate() {
        return this.enclosingTemplate;
    }

    public String getIndentation() {
        return this.indentation;
    }

    public void setIndentation(String str) {
        this.indentation = str;
    }
}
