package org.antlr.runtime;

/* JADX INFO: loaded from: classes12.dex */
public class MismatchedRangeException extends RecognitionException {
    public int a;
    public int b;

    public MismatchedRangeException() {
    }

    public MismatchedRangeException(int i, int i2, IntStream intStream) {
        super(intStream);
        this.a = i;
        this.b = i2;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "MismatchedNotSetException(" + getUnexpectedType() + " not in [" + this.a + "," + this.b + "])";
    }
}
