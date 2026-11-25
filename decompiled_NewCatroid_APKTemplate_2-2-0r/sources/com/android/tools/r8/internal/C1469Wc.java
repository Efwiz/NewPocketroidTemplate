package com.android.tools.r8.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1469Wc implements InterfaceC1833dK {
    final /* synthetic */ Constructor a;

    C1469Wc(Constructor constructor) {
        this.a = constructor;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1833dK
    public final Object a() {
        try {
            return this.a.newInstance(null);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException("Failed to invoke " + this.a + " with no args", e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException("Failed to invoke " + this.a + " with no args", e3.getTargetException());
        }
    }
}
