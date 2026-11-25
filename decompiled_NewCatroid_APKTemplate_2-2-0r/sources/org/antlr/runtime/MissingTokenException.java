package org.antlr.runtime;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes12.dex */
public class MissingTokenException extends MismatchedTokenException {
    public Object inserted;

    public MissingTokenException() {
    }

    public MissingTokenException(int i, IntStream intStream, Object obj) {
        super(i, intStream);
        this.inserted = obj;
    }

    public int getMissingType() {
        return this.expecting;
    }

    @Override // org.antlr.runtime.MismatchedTokenException, java.lang.Throwable
    public String toString() {
        if (this.inserted != null && this.token != null) {
            return "MissingTokenException(inserted " + this.inserted + " at " + this.token.getText() + Constant.AFTER_QUTO;
        }
        if (this.token != null) {
            return "MissingTokenException(at " + this.token.getText() + Constant.AFTER_QUTO;
        }
        return "MissingTokenException";
    }
}
