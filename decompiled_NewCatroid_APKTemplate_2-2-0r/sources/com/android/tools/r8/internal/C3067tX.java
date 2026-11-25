package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3067tX implements InterfaceC2762pB {
    static final /* synthetic */ boolean c = true;
    final C2537mR a = new C2537mR();
    final /* synthetic */ C3138uX b;

    C3067tX(C3138uX c3138uX) {
        this.b = c3138uX;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(S10 s10) {
        final boolean z;
        if (!c && this.a.containsKey(s10)) {
            throw new AssertionError();
        }
        Set setC = OU.c();
        com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(s10);
        loop0: while (true) {
            if (!o3H.c()) {
                z = true;
                break;
            }
            S10 s11 = (S10) o3H.f();
            setC.add(s11);
            if (this.a.containsKey(s11)) {
                if (!c && this.a.b(s11)) {
                    throw new AssertionError();
                }
            } else if (!s11.C() && !s11.i()) {
                AbstractC2412ko abstractC2412ko = s11.b;
                int iG2 = abstractC2412ko.G2();
                if (iG2 == 9) {
                    o3H.a((Iterable) abstractC2412ko.h1());
                } else if (iG2 == 40) {
                    C2115gt c2115gtI0 = abstractC2412ko.i0();
                    if (this.b.d.a(c2115gtI0.S2())) {
                        o3H.c(c2115gtI0.U2());
                    }
                } else if (iG2 == 49) {
                    if (!c && abstractC2412ko.t0().h != this.b.b.v2) {
                        throw new AssertionError();
                    }
                }
                Iterator<AbstractC2412ko> it = s11.h0().iterator();
                while (true) {
                    if (it.hasNext()) {
                        AbstractC2412ko next = it.next();
                        int iG3 = next.G2();
                        if (iG3 == 9) {
                            o3H.c(next.b());
                        } else if (iG3 == 25) {
                            continue;
                        } else if (iG3 == 33) {
                            C1166Ls c1166LsZ = next.Z();
                            if (((ArrayList) c1166LsZ.J2()).lastIndexOf(s11) <= 0) {
                                com.android.tools.r8.graph.Z1 z1 = this.b.d;
                                if (!z1.u.contains(c1166LsZ.S2())) {
                                }
                            }
                        } else if (iG3 == 38) {
                            C1721bt c1721btG0 = next.g0();
                            C0684j2 c0684j2S2 = c1721btG0.S2();
                            C0722p1 c0722p1 = this.b.b;
                            if ((c0684j2S2 == c0722p1.f4.j || c0684j2S2 == c0722p1.k4.s) && (!c1721btG0.d1() || !c1721btG0.b().B())) {
                            }
                        } else if (iG3 == 40) {
                            C2115gt c2115gtI1 = next.i0();
                            if (((ArrayList) c2115gtI1.J2()).lastIndexOf(s11) <= 0) {
                                C0684j2 c0684j2S3 = c2115gtI1.S2();
                                if (!this.b.d.a(c0684j2S3)) {
                                    C3138uX c3138uX = this.b;
                                    if ((c0684j2S3 == c3138uX.b.g4.i || c0684j2S3 == c3138uX.d.r) && (!c2115gtI1.d1() || !c2115gtI1.b().B())) {
                                    }
                                } else if (c2115gtI1.d1()) {
                                    o3H.c(c2115gtI1.b());
                                }
                            }
                        }
                    }
                }
            }
            z = false;
            break;
        }
        setC.forEach(new Consumer() { // from class: com.android.tools.r8.internal.tX$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(z, (S10) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(boolean z, S10 s10) {
        this.a.a(s10, z);
    }
}
