package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.utils.C3921k1;
import com.android.tools.r8.utils.C3926l1;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1607aM extends AbstractC1915eM.a {
    static final /* synthetic */ boolean k = true;
    private final C3921k1 h;
    private C0684j2 i;
    private boolean j;

    private C1607aM() {
        this.h = C3926l1.a();
    }

    public final C1607aM b(C0684j2 c0684j2) {
        this.i = c0684j2;
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM.a
    final AbstractC1915eM.a c() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1915eM.a
    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public final C1687bM a() {
        boolean z = k;
        if (!z && !this.f && this.a < 0) {
            throw new AssertionError();
        }
        if (z || this.g || this.b != null) {
            return new C1687bM(this.a, this.b, this.c, this.d, this.e, this.h.a(), this.i, this.j);
        }
        throw new AssertionError();
    }

    public final boolean e() {
        return !this.h.b();
    }

    public final C1607aM a(int i, AbstractC1915eM abstractC1915eM) {
        this.h.a(i, abstractC1915eM);
        return this;
    }
}
