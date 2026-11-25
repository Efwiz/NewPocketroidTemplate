package com.android.tools.r8.internal;

import java.lang.reflect.Type;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3045tD implements FZ {
    private final C1854dd a;
    final boolean b = false;

    public C3045tD(C1854dd c1854dd) {
        this.a = c1854dd;
    }

    @Override // com.android.tools.r8.internal.FZ
    public final EZ a(C1862dk c1862dk, I00 i00) {
        Type typeB = i00.b();
        if (!Map.class.isAssignableFrom(i00.a())) {
            return null;
        }
        Type[] typeArrB = AbstractC1879e.b(typeB, AbstractC1879e.c(typeB));
        Type type = typeArrB[0];
        return new C2976sD(this, c1862dk, typeArrB[0], (type == Boolean.TYPE || type == Boolean.class) ? AbstractC2961s00.c : c1862dk.a(I00.a(type)), typeArrB[1], c1862dk.a(I00.a(typeArrB[1])), this.a.a(i00));
    }
}
