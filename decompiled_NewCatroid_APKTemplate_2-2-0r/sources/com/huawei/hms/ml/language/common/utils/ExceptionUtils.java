package com.huawei.hms.ml.language.common.utils;

import com.huawei.hms.mlsdk.common.MLException;

/* JADX INFO: loaded from: classes10.dex */
public final class ExceptionUtils {
    private ExceptionUtils() {
    }

    public static MLException getMlException(Exception exc) {
        if (exc instanceof MLException) {
            return (MLException) exc;
        }
        if (exc instanceof IllegalArgumentException) {
            MLException mLException = new MLException(exc.getMessage(), 5);
            mLException.initCause(exc);
            return mLException;
        }
        MLException mLException2 = new MLException(exc.getMessage(), 2);
        mLException2.initCause(exc);
        return mLException2;
    }
}
