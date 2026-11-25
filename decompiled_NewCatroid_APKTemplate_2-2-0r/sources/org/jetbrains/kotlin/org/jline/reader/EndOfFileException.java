package org.jetbrains.kotlin.org.jline.reader;

/* JADX INFO: loaded from: classes4.dex */
public class EndOfFileException extends RuntimeException {
    private static final long serialVersionUID = 528485360925144689L;

    public EndOfFileException() {
    }

    public EndOfFileException(String str) {
        super(str);
    }

    public EndOfFileException(String str, Throwable th) {
        super(str, th);
    }

    public EndOfFileException(Throwable th) {
        super(th);
    }

    public EndOfFileException(String str, Throwable th, boolean z, boolean z2) {
        super(str, th, z, z2);
    }
}
