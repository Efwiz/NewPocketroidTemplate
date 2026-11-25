package org.jf.smali;

import org.antlr.runtime.CommonToken;

/* JADX INFO: loaded from: classes6.dex */
public class InvalidToken extends CommonToken {
    private final String message;

    public InvalidToken(String str) {
        super(96);
        this.message = str;
        this.channel = 100;
    }

    public InvalidToken(String str, String str2) {
        super(96, str2);
        this.message = str;
        this.channel = 100;
    }

    public String getMessage() {
        return this.message;
    }
}
