package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vs, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3239vs extends AbstractC3204vL {
    static final /* synthetic */ boolean g = true;
    private Set a;
    private Set b;
    private Set c;
    private boolean d;
    private boolean e;
    private boolean f;

    C3239vs(Set set, Set set2, Set set3, boolean z, boolean z2, boolean z3) {
        if (!g && set.isEmpty() && set2.isEmpty() && set3.isEmpty() && !z && !z2 && !z3) {
            throw new AssertionError();
        }
        this.a = set;
        this.b = set2;
        this.c = set3;
        this.d = z;
        this.e = z2;
        this.f = z3;
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    public final C3239vs a() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL d() {
        final C3386xm c3386xmG = AbstractC0836Am.g();
        this.c.forEach(new Consumer() { // from class: com.android.tools.r8.internal.vs$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3239vs.a(c3386xmG, (AbstractC1332Rs) obj);
            }
        });
        return new C3494zH(this.a, this.b, c3386xmG.a(), this.d, this.e, this.f);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != C3239vs.class) {
            return false;
        }
        C3239vs c3239vs = (C3239vs) obj;
        return this.d == c3239vs.d && this.e == c3239vs.e && this.f == c3239vs.f && this.a.equals(c3239vs.a) && this.b.equals(c3239vs.b) && this.c.equals(c3239vs.c);
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    public final boolean f() {
        return this.e;
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL h() {
        return this.d ? this : new C3239vs(this.a, this.b, this.c, true, this.e, this.f);
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
        return this.e ? this : new C3239vs(this.a, this.b, this.c, this.d, true, this.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL j() {
        return this.f ? this : new C3239vs(this.a, this.b, this.c, this.d, this.e, true);
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL a(C0767v2 c0767v2) {
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        c0862BmG.a(this.a);
        c0862BmG.a(c0767v2);
        return new C3239vs(c0862BmG.a(), this.b, this.c, this.d, this.e, this.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL a(com.android.tools.r8.graph.Y0 y0) {
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        c0862BmG.a(this.b);
        c0862BmG.a(y0);
        return new C3239vs(this.a, c0862BmG.a(), this.c, this.d, this.e, this.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC3204vL
    final AbstractC3204vL a(AbstractC1332Rs abstractC1332Rs) {
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        c0862BmG.a(this.c);
        c0862BmG.a(abstractC1332Rs);
        return new C3239vs(this.a, this.b, c0862BmG.a(), this.d, this.e, this.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3386xm c3386xm, AbstractC1332Rs abstractC1332Rs) {
        c3386xm.a(abstractC1332Rs.S2());
    }

    final C3239vs a(C3239vs c3239vs) {
        C0862Bm c0862BmA = AbstractC1048Hm.g().a(this.a);
        C0862Bm c0862BmA2 = AbstractC1048Hm.g().a(this.b);
        C0862Bm c0862BmA3 = AbstractC1048Hm.g().a(this.c);
        boolean z = this.d;
        boolean z2 = this.e;
        boolean z3 = this.f;
        Iterator it = c3239vs.a.iterator();
        while (it.hasNext()) {
            c0862BmA.a(it.next());
        }
        Iterator it2 = c3239vs.b.iterator();
        while (it2.hasNext()) {
            c0862BmA2.a(it2.next());
        }
        Iterator it3 = c3239vs.c.iterator();
        while (it3.hasNext()) {
            c0862BmA3.a(it3.next());
        }
        return new C3239vs(c0862BmA.a(), c0862BmA2.a(), c0862BmA3.a(), z | c3239vs.d, z2 | c3239vs.e, z3 | c3239vs.f);
    }
}
