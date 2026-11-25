package org.jetbrains.kotlin.com.intellij.serialization;

import org.jetbrains.kotlin.com.intellij.util.xmlb.Accessor;

/* JADX INFO: loaded from: classes3.dex */
public interface MutableAccessor extends Accessor {
    void set(Object obj, Object obj2);

    void setBoolean(Object obj, boolean z);

    void setDouble(Object obj, double d);

    void setFloat(Object obj, float f);

    void setInt(Object obj, int i);

    void setLong(Object obj, long j);

    void setShort(Object obj, short s);
}
