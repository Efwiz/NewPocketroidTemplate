package org.antlr.runtime;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes12.dex */
public class MismatchedTokenException extends RecognitionException {
    public int expecting;

    public MismatchedTokenException() {
        this.expecting = 0;
    }

    public MismatchedTokenException(int i, IntStream intStream) {
        super(intStream);
        this.expecting = i;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "MismatchedTokenException(" + getUnexpectedType() + "!=" + this.expecting + Constant.AFTER_QUTO;
    }
}
