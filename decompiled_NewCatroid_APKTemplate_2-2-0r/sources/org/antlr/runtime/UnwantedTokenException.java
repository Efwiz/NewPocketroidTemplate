package org.antlr.runtime;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes12.dex */
public class UnwantedTokenException extends MismatchedTokenException {
    public UnwantedTokenException() {
    }

    public UnwantedTokenException(int i, IntStream intStream) {
        super(i, intStream);
    }

    public Token getUnexpectedToken() {
        return this.token;
    }

    @Override // org.antlr.runtime.MismatchedTokenException, java.lang.Throwable
    public String toString() {
        String str = ", expected " + this.expecting;
        if (this.expecting == 0) {
            str = "";
        }
        if (this.token == null) {
            return "UnwantedTokenException(found=null" + str + Constant.AFTER_QUTO;
        }
        return "UnwantedTokenException(found=" + this.token.getText() + str + Constant.AFTER_QUTO;
    }
}
