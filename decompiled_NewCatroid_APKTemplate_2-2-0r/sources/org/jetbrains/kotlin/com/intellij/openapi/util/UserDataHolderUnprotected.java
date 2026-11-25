package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public interface UserDataHolderUnprotected {
    @Deprecated
    <T> T getUserDataUnprotected(Key<T> key);

    @Deprecated
    <T> void putUserDataUnprotected(Key<T> key, T t);
}
