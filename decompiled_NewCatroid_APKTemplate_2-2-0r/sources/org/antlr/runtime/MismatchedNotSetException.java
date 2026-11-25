package org.antlr.runtime;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes12.dex */
public class MismatchedNotSetException extends MismatchedSetException {
    public MismatchedNotSetException() {
    }

    public MismatchedNotSetException(BitSet bitSet, IntStream intStream) {
        super(bitSet, intStream);
    }

    @Override // org.antlr.runtime.MismatchedSetException, java.lang.Throwable
    public String toString() {
        return "MismatchedNotSetException(" + getUnexpectedType() + "!=" + this.expecting + Constant.AFTER_QUTO;
    }
}
