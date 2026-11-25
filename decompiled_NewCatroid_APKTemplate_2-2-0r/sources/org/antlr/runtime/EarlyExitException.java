package org.antlr.runtime;

/* JADX INFO: loaded from: classes12.dex */
public class EarlyExitException extends RecognitionException {
    public int decisionNumber;

    public EarlyExitException() {
    }

    public EarlyExitException(int i, IntStream intStream) {
        super(intStream);
        this.decisionNumber = i;
    }
}
