package com.android.tools.r8.internal;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/* JADX INFO: renamed from: com.android.tools.r8.internal.d3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1813d3 implements FZ {
    C1813d3() {
    }

    @Override // com.android.tools.r8.internal.FZ
    public final EZ a(C1862dk c1862dk, I00 i00) {
        Type typeB = i00.b();
        boolean z = typeB instanceof GenericArrayType;
        if (!z && (!(typeB instanceof Class) || !((Class) typeB).isArray())) {
            return null;
        }
        Type genericComponentType = z ? ((GenericArrayType) typeB).getGenericComponentType() : ((Class) typeB).getComponentType();
        return new C1886e3(c1862dk, c1862dk.a(I00.a(genericComponentType)), AbstractC1879e.c(genericComponentType));
    }
}
