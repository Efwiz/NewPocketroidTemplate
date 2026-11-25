package com.android.tools.r8.utils;

import java.lang.Exception;
import java.util.function.IntConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.j3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3918j3<E extends Exception> {
    public void a(IntConsumer intConsumer) throws Exception {
        while (a()) {
            intConsumer.accept(b());
        }
    }

    public abstract boolean a();

    public abstract int b();

    public final int a(InterfaceC3923k3 interfaceC3923k3) {
        if (a()) {
            return b();
        }
        return interfaceC3923k3.a();
    }
}
