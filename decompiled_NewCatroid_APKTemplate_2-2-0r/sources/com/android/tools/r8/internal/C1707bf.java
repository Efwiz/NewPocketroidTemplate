package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1707bf extends AbstractC1939ef {
    private final com.android.tools.r8.dex.code.B1 e;

    public C1707bf(AbstractC2412ko abstractC2412ko, com.android.tools.r8.dex.code.B1 b1) {
        super(abstractC2412ko);
        this.e = b1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int a(C2322jf c2322jf) {
        this.e.g(c());
        return this.e.z();
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int d() {
        return this.e.z();
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int e() {
        return this.e.z();
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final int f() {
        return this.e.z();
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final void a(C2322jf c2322jf, ArrayList arrayList) {
        arrayList.add(this.e);
    }

    @Override // com.android.tools.r8.internal.AbstractC1939ef
    public final boolean a(AbstractC1939ef abstractC1939ef, C2322jf c2322jf) {
        return (abstractC1939ef instanceof C1707bf) && this.e.equals(((C1707bf) abstractC1939ef).e);
    }
}
