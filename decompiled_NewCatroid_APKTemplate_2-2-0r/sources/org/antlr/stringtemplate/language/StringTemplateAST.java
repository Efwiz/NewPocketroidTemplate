package org.antlr.stringtemplate.language;

import antlr.CommonAST;
import org.antlr.stringtemplate.StringTemplate;

/* JADX INFO: loaded from: classes12.dex */
public class StringTemplateAST extends CommonAST {
    protected StringTemplate st = null;

    public StringTemplateAST() {
    }

    public StringTemplateAST(int i, String str) {
        setType(i);
        setText(str);
    }

    public StringTemplate getStringTemplate() {
        return this.st;
    }

    public void setStringTemplate(StringTemplate stringTemplate) {
        this.st = stringTemplate;
    }
}
