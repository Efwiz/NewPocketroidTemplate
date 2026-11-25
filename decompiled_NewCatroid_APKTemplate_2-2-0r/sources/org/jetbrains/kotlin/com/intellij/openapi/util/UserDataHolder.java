package org.jetbrains.kotlin.com.intellij.openapi.util;

/* JADX INFO: loaded from: classes3.dex */
public interface UserDataHolder {
    <T> T getUserData(Key<T> key);

    <T> void putUserData(Key<T> key, T t);
}
