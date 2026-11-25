package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractObject2LongFunction<K> implements Object2LongFunction<K>, Serializable {
    private static final long serialVersionUID = -4940583368468432370L;
    protected long defRetValue;

    protected AbstractObject2LongFunction() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongFunction
    public long defaultReturnValue() {
        return this.defRetValue;
    }
}
