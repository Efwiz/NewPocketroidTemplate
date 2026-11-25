package com.android.tools.r8.utils;

import java.lang.Throwable;

/* JADX INFO: renamed from: com.android.tools.r8.utils.h3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public interface InterfaceC3905h3<T, E extends Throwable> {
    /* JADX WARN: Multi-variable type inference failed */
    default void a(Object obj) {
        try {
            accept(obj);
        } catch (Throwable th) {
            if (!(th instanceof RuntimeException)) {
                throw new RuntimeException(th);
            }
        }
    }

    void accept(T t) throws Throwable;
}
