package org.antlr.runtime;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes12.dex */
public class MismatchedSetException extends RecognitionException {
    public BitSet expecting;

    public MismatchedSetException() {
    }

    public MismatchedSetException(BitSet bitSet, IntStream intStream) {
        super(intStream);
        this.expecting = bitSet;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "MismatchedSetException(" + getUnexpectedType() + "!=" + this.expecting + Constant.AFTER_QUTO;
    }
}
