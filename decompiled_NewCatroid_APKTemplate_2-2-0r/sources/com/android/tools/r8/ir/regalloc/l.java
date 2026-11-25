package com.android.tools.r8.ir.regalloc;

import java.util.BitSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class l extends j {
    static final /* synthetic */ boolean c = true;
    private final j a;
    private final BitSet b;

    public l(k kVar) {
        this.a = kVar;
        this.b = new BitSet(kVar.a());
    }

    @Override // com.android.tools.r8.ir.regalloc.j
    public final boolean a(int i, i iVar) {
        if (c || !this.b.get(i)) {
            return this.a.a(i, iVar);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.ir.regalloc.j
    public final boolean b(int i) {
        return this.a.b(i) || this.b.get(i);
    }

    public final void c(int i) {
        this.b.set(i);
    }

    @Override // com.android.tools.r8.ir.regalloc.j
    public final int a(int i) {
        if (!c && this.b.get(i)) {
            throw new AssertionError();
        }
        return this.a.a(i);
    }
}
