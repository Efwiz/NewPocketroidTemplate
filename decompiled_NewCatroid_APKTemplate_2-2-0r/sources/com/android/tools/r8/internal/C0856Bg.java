package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Bg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0856Bg {
    static final /* synthetic */ boolean d = true;
    private C0702m a;
    private VP b;
    private Set c = OU.c();

    public C0856Bg(C0702m c0702m, VP vp) {
        this.a = c0702m;
        this.b = vp;
    }

    public final void a() {
        C0729q2 c0729q2A;
        if (!d && !this.a.Y().x1().e) {
            throw new AssertionError();
        }
        if (this.a.Y().w && this.a.Y().o1()) {
            for (C0729q2 c0729q2 : ((C3733i) this.a.f()).d()) {
                if (c0729q2.f1().a(this.b.c)) {
                    C0729q2 c0729q3 = null;
                    if (c0729q2.Y().k() == 2) {
                        Iterator<com.android.tools.r8.graph.W0> it = c0729q2.P1().iterator();
                        C0767v2 c0767v2V1 = null;
                        while (true) {
                            if (!it.hasNext()) {
                                if (c0767v2V1 == null || (c0729q2A = C0729q2.a(this.a.b(c0729q2, c0767v2V1))) == null || !c0729q2A.B1() || !c0729q2A.g.a(this.b.b)) {
                                    break;
                                    break;
                                    break;
                                    break;
                                }
                                c0729q3 = c0729q2A;
                                break;
                            }
                            com.android.tools.r8.graph.W0 next = it.next();
                            C0684j2 reference = next.getReference();
                            if (reference.f != this.b.A || reference.F0() != 1 || reference.G0().a[0] != this.a.a().A1) {
                                break;
                            }
                            if (next.V1() != this.b.b) {
                                if (c0767v2V1 != null) {
                                    break;
                                } else {
                                    c0767v2V1 = next.V1();
                                }
                            }
                        }
                    }
                    if (c0729q3 != null) {
                        C0767v2 type = c0729q3.getType();
                        C0722p1 c0722p1A = this.a.a();
                        VP vp = this.b;
                        com.android.tools.r8.graph.T0 t0A = c0729q3.a(c0722p1A.a(type, vp.c, vp.w));
                        if (t0A != null && !((C3733i) this.a.f()).a(t0A) && (!((C3733i) this.a.f()).b(t0A) || ((C3733i) this.a.f()).f(t0A))) {
                            this.c.add(c0729q2.getType());
                            c0729q2.b(com.android.tools.r8.graph.W0.s);
                        }
                    }
                }
            }
        }
    }

    public final Set b() {
        return this.c;
    }

    public final void c() {
        for (C0767v2 c0767v2 : this.c) {
            if (((C3733i) this.a.f()).d(c0767v2) != null) {
                throw new C1367Tb("EnumLite Proto Shrinker failure: Type " + c0767v2 + " was assumed to be dead during optimizations, but it is not.");
            }
        }
    }
}
