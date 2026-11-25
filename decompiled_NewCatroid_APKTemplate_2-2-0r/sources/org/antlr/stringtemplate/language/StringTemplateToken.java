package org.antlr.stringtemplate.language;

import antlr.CommonToken;
import java.util.List;

/* JADX INFO: loaded from: classes12.dex */
public class StringTemplateToken extends CommonToken {
    public List args;

    public StringTemplateToken() {
    }

    public StringTemplateToken(int i, String str) {
        super(i, str);
    }

    public StringTemplateToken(String str) {
        super(str);
    }

    public StringTemplateToken(int i, String str, List list) {
        super(i, str);
        this.args = list;
    }

    @Override // antlr.CommonToken, antlr.Token
    public String toString() {
        return new StringBuffer().append(super.toString()).append("; args=").append(this.args).toString();
    }
}
