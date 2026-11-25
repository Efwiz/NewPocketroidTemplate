package org.jetbrains.kotlin.org.jline.reader;

/* JADX INFO: loaded from: classes4.dex */
public class EOFError extends SyntaxError {
    private static final long serialVersionUID = 1;
    private final String missing;

    public EOFError(int i, int i2, String str, String str2) {
        super(i, i2, str);
        this.missing = str2;
    }

    public String getMissing() {
        return this.missing;
    }
}
