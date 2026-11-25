package com.android.tools.r8.internal;

import com.android.tools.r8.utils.AbstractC3938n2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ma, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1175Ma {
    static final C1175Ma c;
    static final C1175Ma d;
    static final C1175Ma e;
    static final C1175Ma f;
    static final /* synthetic */ boolean g = true;
    private AbstractC3938n2 a;
    private AbstractC3938n2 b;

    static {
        AbstractC3938n2 abstractC3938n2 = AbstractC3938n2.a;
        AbstractC3938n2 abstractC3938n3 = AbstractC3938n2.b;
        c = new C1175Ma(abstractC3938n2, abstractC3938n3);
        AbstractC3938n2 abstractC3938n4 = AbstractC3938n2.c;
        d = new C1175Ma(abstractC3938n4, abstractC3938n3);
        e = new C1175Ma(abstractC3938n3, abstractC3938n2);
        f = new C1175Ma(abstractC3938n3, abstractC3938n4);
    }

    public C1175Ma(AbstractC3938n2 abstractC3938n2, AbstractC3938n2 abstractC3938n3) {
        this.a = abstractC3938n2;
        this.b = abstractC3938n3;
        if (!g && a()) {
            throw new AssertionError();
        }
    }

    final boolean a() {
        return this.a.c() && this.b.c();
    }
}
