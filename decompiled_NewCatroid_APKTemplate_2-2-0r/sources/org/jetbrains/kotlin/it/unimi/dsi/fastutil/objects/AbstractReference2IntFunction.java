package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractReference2IntFunction<K> implements Reference2IntFunction<K>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected int defRetValue;

    protected AbstractReference2IntFunction() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Reference2IntFunction
    public int defaultReturnValue() {
        return this.defRetValue;
    }
}
