package org.antlr.stringtemplate.language;

import antlr.CommonToken;

/* JADX INFO: loaded from: classes12.dex */
public class ChunkToken extends CommonToken {
    protected String indentation;

    public ChunkToken() {
    }

    public ChunkToken(int i, String str, String str2) {
        super(i, str);
        setIndentation(str2);
    }

    public String getIndentation() {
        return this.indentation;
    }

    public void setIndentation(String str) {
        this.indentation = str;
    }

    @Override // antlr.CommonToken, antlr.Token
    public String toString() {
        return new StringBuffer().append(super.toString()).append("<indent='").append(this.indentation).append("'>").toString();
    }
}
