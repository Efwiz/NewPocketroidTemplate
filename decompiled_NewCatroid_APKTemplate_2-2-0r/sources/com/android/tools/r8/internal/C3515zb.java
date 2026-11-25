package com.android.tools.r8.internal;

import java.lang.reflect.Type;
import java.util.Collection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3515zb implements FZ {
    private final C1854dd a;

    public C3515zb(C1854dd c1854dd) {
        this.a = c1854dd;
    }

    @Override // com.android.tools.r8.internal.FZ
    public final EZ a(C1862dk c1862dk, I00 i00) {
        Type typeB = i00.b();
        Class clsA = i00.a();
        if (!Collection.class.isAssignableFrom(clsA)) {
            return null;
        }
        Type typeA = AbstractC1879e.a(typeB, clsA);
        return new C3445yb(c1862dk, typeA, c1862dk.a(I00.a(typeA)), this.a.a(i00));
    }
}
