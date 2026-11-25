package org.jetbrains.kotlin.com.intellij.util.xmlb;

import java.lang.reflect.Type;
import org.jetbrains.kotlin.com.intellij.serialization.MutableAccessor;

/* JADX INFO: loaded from: classes3.dex */
public interface Serializer {
    Binding getBinding(Class<?> cls, Type type);

    Binding getBinding(MutableAccessor mutableAccessor);

    Binding getRootBinding(Class<?> cls, Type type);
}
