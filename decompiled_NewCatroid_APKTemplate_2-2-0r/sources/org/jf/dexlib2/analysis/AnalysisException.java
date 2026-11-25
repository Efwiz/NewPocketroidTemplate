package org.jf.dexlib2.analysis;

import org.jf.util.ExceptionWithContext;

/* JADX INFO: loaded from: classes6.dex */
public class AnalysisException extends ExceptionWithContext {
    public int codeAddress;

    public AnalysisException(Throwable th) {
        super(th);
    }

    public AnalysisException(Throwable th, String str, Object... objArr) {
        super(th, str, objArr);
    }

    public AnalysisException(String str, Object... objArr) {
        super(str, objArr);
    }
}
