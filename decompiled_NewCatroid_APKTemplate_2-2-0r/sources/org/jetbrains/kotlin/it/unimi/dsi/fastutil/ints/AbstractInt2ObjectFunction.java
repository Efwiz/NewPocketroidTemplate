package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.Serializable;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractInt2ObjectFunction<V> implements Int2ObjectFunction<V>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    protected AbstractInt2ObjectFunction() {
    }

    public void defaultReturnValue(V v) {
        this.defRetValue = v;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectFunction
    public V defaultReturnValue() {
        return this.defRetValue;
    }
}
