package org.jetbrains.kotlin.com.intellij.util.xmlb;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: classes3.dex */
public interface Accessor {
    <T extends Annotation> T getAnnotation(Class<T> cls);

    Type getGenericType();

    String getName();

    boolean isWritable();

    Object read(Object obj);
}
