package org.jf.dexlib2.analysis;

import org.jf.util.ExceptionWithContext;

/* JADX INFO: loaded from: classes6.dex */
public class UnresolvedClassException extends ExceptionWithContext {
    public UnresolvedClassException(Throwable th) {
        super(th);
    }

    public UnresolvedClassException(Throwable th, String str, Object... objArr) {
        super(th, str, objArr);
    }

    public UnresolvedClassException(String str, Object... objArr) {
        super(str, objArr);
    }
}
