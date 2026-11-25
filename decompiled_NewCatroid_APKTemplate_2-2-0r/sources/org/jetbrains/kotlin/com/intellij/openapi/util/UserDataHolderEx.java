package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
public interface UserDataHolderEx extends UserDataHolder {
    <T> T putUserDataIfAbsent(Key<T> key, T t);

    <T> boolean replace(Key<T> key, T t, T t2);
}
