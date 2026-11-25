package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.lang.Throwable;

/* JADX INFO: loaded from: classes3.dex */
public interface ThrowableComputable<T, E extends Throwable> {
    T compute() throws Throwable;
}
