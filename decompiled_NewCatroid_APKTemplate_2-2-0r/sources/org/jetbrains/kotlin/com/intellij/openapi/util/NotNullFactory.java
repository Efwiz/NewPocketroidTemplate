package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface NotNullFactory<T> extends Factory<T> {
    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Factory
    T create();
}
