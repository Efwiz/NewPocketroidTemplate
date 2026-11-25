package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractObject2ShortFunction<K> implements Object2ShortFunction<K>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected short defRetValue;

    protected AbstractObject2ShortFunction() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2ShortFunction
    public short defaultReturnValue() {
        return this.defRetValue;
    }
}
