package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractDouble2ObjectFunction<V> implements Double2ObjectFunction<V>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected V defRetValue;

    protected AbstractDouble2ObjectFunction() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.Double2ObjectFunction
    public V defaultReturnValue() {
        return this.defRetValue;
    }
}
