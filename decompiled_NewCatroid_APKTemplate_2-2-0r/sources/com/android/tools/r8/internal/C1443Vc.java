package com.android.tools.r8.internal;

import java.lang.reflect.Type;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1443Vc implements InterfaceC1833dK {
    private final K10 a = K10.a();
    final /* synthetic */ Class b;
    final /* synthetic */ Type c;

    C1443Vc(Class cls, Type type) {
        this.b = cls;
        this.c = type;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1833dK
    public final Object a() {
        try {
            return this.a.b(this.b);
        } catch (Exception e) {
            throw new RuntimeException("Unable to invoke no-args constructor for " + this.c + ". Register an InstanceCreator with Gson for this type may fix this problem.", e);
        }
    }
}
