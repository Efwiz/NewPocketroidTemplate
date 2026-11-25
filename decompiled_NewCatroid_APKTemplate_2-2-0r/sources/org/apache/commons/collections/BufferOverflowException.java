package org.apache.commons.collections;

/* JADX INFO: loaded from: classes12.dex */
public class BufferOverflowException extends RuntimeException {
    private final Throwable throwable;

    public BufferOverflowException() {
        this.throwable = null;
    }

    public BufferOverflowException(String str) {
        this(str, null);
    }

    public BufferOverflowException(String str, Throwable th) {
        super(str);
        this.throwable = th;
    }

    @Override // java.lang.Throwable
    public final Throwable getCause() {
        return this.throwable;
    }
}
