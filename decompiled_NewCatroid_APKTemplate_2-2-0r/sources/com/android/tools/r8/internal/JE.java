package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.C0684j2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class JE extends ZD {
    static final /* synthetic */ boolean e = true;
    private com.android.tools.r8.graph.proto.k c;
    private EnumC1953et d;

    private JE() {
        this.c = com.android.tools.r8.graph.proto.k.h();
    }

    public final JE a(com.android.tools.r8.graph.proto.k kVar) {
        this.c = kVar;
        return this;
    }

    public final ZD b(AbstractC0642e2 abstractC0642e2) {
        this.a = abstractC0642e2;
        return this;
    }

    public final JE a(EnumC1953et enumC1953et) {
        this.d = enumC1953et;
        return this;
    }

    public final ZD a(AbstractC0642e2 abstractC0642e2) {
        this.b = abstractC0642e2;
        return this;
    }

    public final KE a() {
        if (e || this.a != null) {
            return new KE((C0684j2) this.a, (C0684j2) this.b, this.d, this.c);
        }
        throw new AssertionError();
    }
}
