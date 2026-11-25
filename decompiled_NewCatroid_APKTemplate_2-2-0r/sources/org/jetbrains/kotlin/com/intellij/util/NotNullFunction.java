package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
public interface NotNullFunction<Param, Result> extends NullableFunction<Param, Result> {
    @Override // org.jetbrains.kotlin.com.intellij.util.Function
    Result fun(Param param);
}
