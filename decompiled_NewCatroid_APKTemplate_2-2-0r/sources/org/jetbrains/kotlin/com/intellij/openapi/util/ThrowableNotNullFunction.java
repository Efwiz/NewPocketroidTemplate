package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.lang.Throwable;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface ThrowableNotNullFunction<T, R, E extends Throwable> {
    R fun(T t) throws Throwable;
}
