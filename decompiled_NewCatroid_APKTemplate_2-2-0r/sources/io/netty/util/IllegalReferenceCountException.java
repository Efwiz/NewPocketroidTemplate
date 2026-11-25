package io.netty.util;

/* JADX INFO: loaded from: classes11.dex */
public class IllegalReferenceCountException extends IllegalStateException {
    private static final long serialVersionUID = -2507492394288153468L;

    public IllegalReferenceCountException() {
    }

    public IllegalReferenceCountException(int i) {
        this("refCnt: " + i);
    }

    public IllegalReferenceCountException(int i, int i2) {
        StringBuilder sb;
        StringBuilder sbAppend = new StringBuilder("refCnt: ").append(i).append(", ");
        if (i2 > 0) {
            sb = new StringBuilder("increment: ");
        } else {
            sb = new StringBuilder("decrement: ");
            i2 = -i2;
        }
        this(sbAppend.append(sb.append(i2).toString()).toString());
    }

    public IllegalReferenceCountException(String str) {
        super(str);
    }

    public IllegalReferenceCountException(String str, Throwable th) {
        super(str, th);
    }

    public IllegalReferenceCountException(Throwable th) {
        super(th);
    }
}
