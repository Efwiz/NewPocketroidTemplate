package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3494zH extends AbstractC3204vL {
    static final /* synthetic */ boolean g = true;
    private Set a;
    private Set b;
    private InterfaceC1600aG c;
    private boolean d;
    private boolean e;
    private boolean f;

    C3494zH(Set set, Set set2, AbstractC0836Am abstractC0836Am, boolean z, boolean z2, boolean z3) {
        if (!g && set.isEmpty() && set2.isEmpty() && abstractC0836Am.isEmpty() && !z && !z2 && !z3) {
            throw new AssertionError();
        }
        this.a = set;
        this.b = set2;
        this.c = abstractC0836Am;
        this.d = z;
        this.e = z2;
        this.f = z3;
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL a(C0767v2 c0767v2) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    public final C3494zH b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL d() {
        throw new C3405y10();
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != C3494zH.class) {
            return false;
        }
        C3494zH c3494zH = (C3494zH) obj;
        return this.d == c3494zH.d && this.e == c3494zH.e && this.f == c3494zH.f && this.a.equals(c3494zH.a) && this.b.equals(c3494zH.b) && this.c.equals(c3494zH.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    public final boolean f() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL h() {
        throw new C3405y10();
    }

    public final int hashCode() {
        int iHashCode = this.c.hashCode() + ((this.b.hashCode() + ((this.a.hashCode() + 31) * 31)) * 31);
        if (g || iHashCode == Objects.hash(this.a, this.b, this.c)) {
            return (((((iHashCode << 1) | com.android.tools.r8.utils.M.a(this.d)) << 1) | com.android.tools.r8.utils.M.a(this.e)) << 1) | com.android.tools.r8.utils.M.a(this.f);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL i() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL j() {
        throw new C3405y10();
    }

    public final Set l() {
        return this.a;
    }

    public final Set m() {
        return this.b;
    }

    public final InterfaceC1600aG n() {
        return this.c;
    }

    public final boolean o() {
        return this.d;
    }

    public final boolean p() {
        return this.f;
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL a(com.android.tools.r8.graph.Y0 y0) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL a(AbstractC1332Rs abstractC1332Rs) {
        throw new C3405y10();
    }
}
