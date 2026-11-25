package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.Objects;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1769cV {
    static final /* synthetic */ boolean e = true;
    private final C2005fV a;
    private final com.android.tools.r8.graph.o5 b;
    private final com.android.tools.r8.utils.A1 c;
    private final Set d = OU.c();

    public C1769cV(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        this.a = c0702m.d0();
        this.b = o5Var;
        this.c = c0702m.Y();
        Objects.requireNonNull(c0702m.Y());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC1617aV b(C1015Gl c1015Gl, int i) {
        C3332x4 c3332x4J2 = c1015Gl.J2();
        return a(c3332x4J2, i, c3332x4J2.R());
    }

    public final AbstractC1617aV a(C2409kl c2409kl) {
        if (this.b.getReference().F0() == 0) {
            return C3493zG.b;
        }
        if (this.c.W0) {
            return C3493zG.b;
        }
        return a(c2409kl.j(), 0, c2409kl.j().d(com.android.tools.r8.utils.M.a(!c2409kl.h().e().E0()) + c2409kl.h().getReference().F0()));
    }

    private AbstractC1617aV a(C3332x4 c3332x4, final int i, InterfaceC2488lo interfaceC2488lo) {
        if (!this.d.add(c3332x4)) {
            return C3493zG.b;
        }
        AbstractC2412ko next = interfaceC2488lo.next();
        while (true) {
            AbstractC2412ko abstractC2412ko = next;
            if (abstractC2412ko.g2()) {
                if (i > 0) {
                    return C3493zG.b;
                }
                int iG2 = abstractC2412ko.G2();
                if (iG2 != 24) {
                    if (iG2 == 25) {
                        final C1015Gl c1015GlQ = abstractC2412ko.Q();
                        S10 s10O2 = c1015GlQ.O2();
                        if (!s10O2.m().H()) {
                            s10O2 = null;
                        }
                        if (!c1015GlQ.N2()) {
                            S10 s10P2 = c1015GlQ.P2();
                            if (s10P2.m().H()) {
                                s10O2 = s10O2 != null ? null : s10P2;
                            }
                        }
                        if (s10O2 != null && !s10O2.Q()) {
                            S10 s10A = c1015GlQ.N2() ? null : c1015GlQ.a(1 - c1015GlQ.h1().indexOf(s10O2));
                            int iJ2 = s10O2.m().q().q().J2();
                            C0767v2 c0767v2J = this.b.e().j(iJ2);
                            AbstractC1617aV abstractC1617aVA = a(iJ2, c0767v2J, s10A, c1015GlQ.L2()).a(new Supplier() { // from class: com.android.tools.r8.internal.cV$$ExternalSyntheticLambda0
                                @Override // java.util.function.Supplier
                                public final Object get() {
                                    return this.f$0.a(c1015GlQ, i);
                                }
                            });
                            AbstractC1617aV abstractC1617aVA2 = a(iJ2, c0767v2J, s10A, c1015GlQ.L2().b()).a(new Supplier() { // from class: com.android.tools.r8.internal.cV$$ExternalSyntheticLambda1
                                @Override // java.util.function.Supplier
                                public final Object get() {
                                    return this.f$0.b(c1015GlQ, i);
                                }
                            });
                            abstractC1617aVA.getClass();
                            if (!(abstractC1617aVA instanceof P1)) {
                                abstractC1617aVA2.getClass();
                                if (!(abstractC1617aVA2 instanceof P1)) {
                                    if (abstractC1617aVA instanceof C3493zG) {
                                        return abstractC1617aVA2;
                                    }
                                    if (abstractC1617aVA2 instanceof C3493zG) {
                                        return abstractC1617aVA;
                                    }
                                    if (abstractC1617aVA instanceof C1928eV) {
                                        return abstractC1617aVA.b().a(abstractC1617aVA2);
                                    }
                                    if (abstractC1617aVA2 instanceof C1928eV) {
                                        return abstractC1617aVA2.b().a(abstractC1617aVA);
                                    }
                                    boolean z = AbstractC1617aV.a;
                                    if (!z && !abstractC1617aVA.d() && !(abstractC1617aVA instanceof C1846dV)) {
                                        throw new AssertionError();
                                    }
                                    if (!z && !abstractC1617aVA2.d() && !(abstractC1617aVA2 instanceof C1846dV)) {
                                        throw new AssertionError();
                                    }
                                    return new C1928eV(AbstractC1639am.a(abstractC1617aVA, abstractC1617aVA2));
                                }
                            }
                            return P1.b;
                        }
                    } else {
                        if (iG2 == 56) {
                            return P1.b;
                        }
                        if (iG2 == 65) {
                            if (c3332x4.D()) {
                                return C3493zG.b;
                            }
                            return P1.b;
                        }
                    }
                    return C3493zG.b;
                }
                C3332x4 c3332x4K2 = abstractC2412ko.P().K2();
                return a(c3332x4K2, i, c3332x4K2.R());
            }
            boolean z2 = e;
            if (!z2 && abstractC2412ko.m1()) {
                throw new AssertionError();
            }
            if (!z2 && abstractC2412ko.F1()) {
                throw new AssertionError();
            }
            if (!(abstractC2412ko instanceof N3)) {
                i++;
            }
            next = interfaceC2488lo.next();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractC1617aV a(C1015Gl c1015Gl, int i) {
        C3332x4 c3332x4K2 = c1015Gl.K2();
        return a(c3332x4K2, i, c3332x4K2.R());
    }

    private AbstractC1617aV a(int i, C0767v2 c0767v2, S10 s10, EnumC1076Il enumC1076Il) {
        boolean z = s10 == null;
        int i2 = AbstractC1697bV.a[enumC1076Il.ordinal()];
        if (i2 == 1) {
            if (z) {
                if (c0767v2.X0()) {
                    C2005fV c2005fV = this.a;
                    c2005fV.getClass();
                    return c2005fV.a(i, WH.c());
                }
                if (c0767v2.N0()) {
                    return this.a.a(i, false);
                }
            } else if (c0767v2.W0()) {
                OptionalLong optionalLongA = a(s10);
                if (optionalLongA.isPresent()) {
                    C2005fV c2005fV2 = this.a;
                    long asLong = optionalLongA.getAsLong();
                    c2005fV2.getClass();
                    return C2018fh.a(i, asLong, c2005fV2);
                }
            }
            return C3493zG.b;
        }
        if (i2 != 2) {
            return C3493zG.b;
        }
        if (z) {
            if (c0767v2.X0()) {
                C2005fV c2005fV3 = this.a;
                c2005fV3.getClass();
                return c2005fV3.a(i, WH.b());
            }
            if (c0767v2.N0()) {
                return this.a.a(i, true);
            }
        } else if (c0767v2.W0()) {
            OptionalLong optionalLongA2 = a(s10);
            if (optionalLongA2.isPresent()) {
                C2005fV c2005fV4 = this.a;
                long asLong2 = optionalLongA2.getAsLong();
                c2005fV4.getClass();
                return MH.a(i, asLong2, c2005fV4);
            }
        }
        return C3493zG.b;
    }

    private static OptionalLong a(S10 s10) {
        S10 s10M = s10.m();
        if (s10M.b(new C1406Ug$$ExternalSyntheticLambda13())) {
            return OptionalLong.of(s10M.q().E().P2());
        }
        return OptionalLong.empty();
    }
}
