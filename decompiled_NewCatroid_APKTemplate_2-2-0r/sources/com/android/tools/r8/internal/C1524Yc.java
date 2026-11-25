package com.android.tools.r8.internal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Yc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1524Yc implements InterfaceC1833dK {
    final /* synthetic */ Type a;

    C1524Yc(Type type) {
        this.a = type;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1833dK
    public final Object a() {
        Type type = this.a;
        if (!(type instanceof ParameterizedType)) {
            throw new C1279Pt("Invalid EnumSet type: " + this.a.toString());
        }
        Type type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
        if (type2 instanceof Class) {
            return EnumSet.noneOf((Class) type2);
        }
        throw new C1279Pt("Invalid EnumSet type: " + this.a.toString());
    }
}
