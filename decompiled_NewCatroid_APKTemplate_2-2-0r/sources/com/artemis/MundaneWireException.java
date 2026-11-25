package com.artemis;

import com.artemis.utils.reflect.ClassReflection;

/* JADX INFO: loaded from: classes8.dex */
public class MundaneWireException extends RuntimeException {
    public MundaneWireException(Class<? extends BaseSystem> cls) {
        super("Not added to world: " + ClassReflection.getSimpleName(cls));
    }

    public MundaneWireException(String str, Throwable th) {
        super(str, th);
    }

    public MundaneWireException(String str) {
        super(str);
    }
}
