package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.B2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0757u1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0816z2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC0833Al;
import com.android.tools.r8.internal.AbstractC1084Is;
import com.android.tools.r8.internal.AbstractC1182Mh;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC1699bX;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.C1033Hc;
import com.android.tools.r8.internal.C1793co;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2477lf;
import com.android.tools.r8.internal.C2769pI;
import com.android.tools.r8.internal.C3141ua;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.RW;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.ZW;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.TextPosition;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final com.android.tools.r8.internal.J0 b;

    public C(C0702m c0702m) {
        this.a = c0702m;
        this.b = ((C3733i) c0702m.f()).M;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C1033Hc c1033Hc, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == c1033Hc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC1305Qs abstractC1305Qs, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == abstractC1305Qs;
    }

    public final void a(ExecutorService executorService) {
        C3879c3.a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.naming.C$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        B2.j jVarK0;
        for (com.android.tools.r8.graph.T0 t0 : c0729q2.N1()) {
            if (!c && !t0.f.o()) {
                throw new AssertionError();
            }
            if (((C2769pI) this.b).containsKey(t0.getReference()) && (jVarK0 = t0.Y0().K0()) != null) {
                AbstractC0744s2 abstractC0744s2A = D.a(this.a, (C0759u2) jVarK0.j1());
                if (abstractC0744s2A != null) {
                    t0.a((B2) new C0816z2(abstractC0744s2A, C3141ua.g()));
                }
            }
        }
    }

    public final void a(C2409kl c2409kl) {
        a(c2409kl, (Set) null);
    }

    public final void a(C2409kl c2409kl, Set set) {
        if (c2409kl.t().h()) {
            B4 b4S = c2409kl.s();
            while (b4S.hasNext()) {
                C3332x4 next = b4S.next();
                if (set == null || set.contains(next)) {
                    InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
                    while (interfaceC2641noA.hasNext()) {
                        AbstractC2412ko next2 = interfaceC2641noA.next();
                        if (!next2.c() && !next2.S1()) {
                            if (next2.Y1()) {
                                interfaceC2641noA = a(c2409kl, b4S, interfaceC2641noA, next2.b0());
                            }
                        } else {
                            AbstractC1182Mh abstractC1182MhN = next2.N();
                            boolean z = c;
                            if (!z && !abstractC1182MhN.S1() && !abstractC1182MhN.c()) {
                                throw new AssertionError();
                            }
                            abstractC1182MhN.getClass();
                            Y0 field = abstractC1182MhN.getField();
                            if (((C2769pI) this.b).containsKey(field)) {
                                S10 s10Value = abstractC1182MhN.value();
                                if (s10Value.M()) {
                                    continue;
                                } else if (!s10Value.K()) {
                                    a(field, c2409kl.h(), abstractC1182MhN, (C0759u2) null);
                                } else {
                                    C0759u2 c0759u2J2 = s10Value.n().F().J2();
                                    AbstractC0744s2 abstractC0744s2A = D.a(this.a, c0759u2J2);
                                    if (abstractC0744s2A == null) {
                                        a(field, c2409kl.h(), abstractC1182MhN, c0759u2J2);
                                    } else {
                                        if (!z && interfaceC2641noA.j() != abstractC1182MhN) {
                                            throw new AssertionError();
                                        }
                                        interfaceC2641noA.previous();
                                        S10 s10A = c2409kl.a(s10Value.s(), s10Value.w());
                                        C2477lf c2477lf = new C2477lf(s10A, abstractC0744s2A, C3141ua.g());
                                        c2477lf.b(abstractC1182MhN.Y0());
                                        C3332x4 c3332x4H = abstractC1182MhN.h();
                                        C3332x4 c3332x4A = c3332x4H.D() ? interfaceC2641noA.a(c2409kl, b4S) : c3332x4H;
                                        if (c3332x4A != c3332x4H) {
                                            c3332x4H.a(c2409kl, c3332x4H.p().size() - 1).add(c2477lf);
                                            interfaceC2641noA = c3332x4A.a(c2409kl);
                                            if (!z && interfaceC2641noA.o() != abstractC1182MhN) {
                                                throw new AssertionError();
                                            }
                                            interfaceC2641noA.next();
                                        } else {
                                            interfaceC2641noA.add(c2477lf);
                                            if (!z && interfaceC2641noA.o() != abstractC1182MhN) {
                                                throw new AssertionError();
                                            }
                                            interfaceC2641noA.next();
                                        }
                                        if (abstractC1182MhN.c()) {
                                            interfaceC2641noA.d(new RW(field, s10A));
                                        } else {
                                            if (!z && !abstractC1182MhN.S1()) {
                                                throw new AssertionError();
                                            }
                                            interfaceC2641noA.d(new C1793co(field, abstractC1182MhN.V().e(), s10A));
                                        }
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:104:0x0200  */
    /* JADX WARN: Code duplicated, block: B:11:0x0047  */
    /* JADX WARN: Code duplicated, block: B:156:0x0306  */
    /* JADX WARN: Code duplicated, block: B:82:0x01ae  */
    /* JADX WARN: Code duplicated, block: B:84:0x01b8  */
    /* JADX WARN: Code duplicated, block: B:86:0x01bc  */
    /* JADX WARN: Code duplicated, block: B:94:0x01d9  */
    /* JADX WARN: Code duplicated, block: B:96:0x01e3  */
    private InterfaceC2641no a(C2409kl c2409kl, B4 b4, InterfaceC2641no interfaceC2641no, final AbstractC1305Qs abstractC1305Qs) {
        boolean z;
        int i;
        C0757u1 c0757u1;
        C0684j2 c0684j2S2;
        InterfaceC2641no interfaceC2641no2;
        InterfaceC2641no interfaceC2641noA = interfaceC2641no;
        C0684j2 c0684j2S3 = abstractC1305Qs.S2();
        C0722p1 c0722p1A = this.a.a();
        if (abstractC1305Qs.f2()) {
            C2115gt c2115gtI0 = abstractC1305Qs.i0();
            if (c2115gtI0.S2() == c0722p1A.k4.i && (D.a(c0722p1A, c2115gtI0.U2()) || D.a(c0722p1A, (S10) ((ArrayList) c2115gtI0.h1()).get(1)))) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        if (!((C2769pI) this.b).containsKey(c0684j2S3) && !z) {
            return interfaceC2641noA;
        }
        ArrayList arrayList = (ArrayList) abstractC1305Qs.J2();
        final S10[] s10Arr = new S10[arrayList.size()];
        if (!D.a(this.a.a(), c0684j2S3) && !z) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                S10 s10 = (S10) arrayList.get(i2);
                if (!s10.K()) {
                    a(c0684j2S3, c2409kl.h(), abstractC1305Qs, (C0759u2) null);
                } else {
                    C0759u2 c0759u2J2 = s10.n().F().J2();
                    AbstractC0744s2 abstractC0744s2A = D.a(this.a, c0759u2J2);
                    if (abstractC0744s2A == null) {
                        a(c0684j2S3, c2409kl.h(), abstractC1305Qs, c0759u2J2);
                    } else {
                        boolean z2 = c;
                        if (!z2 && interfaceC2641noA.j() != abstractC1305Qs) {
                            throw new AssertionError();
                        }
                        interfaceC2641noA.previous();
                        S10 s10A = c2409kl.a(s10.s(), s10.w());
                        C2477lf c2477lf = new C2477lf(s10A, abstractC0744s2A, C3141ua.g());
                        c2477lf.b(abstractC1305Qs.Y0());
                        s10Arr[i2] = s10A;
                        C3332x4 c3332x4H = abstractC1305Qs.h();
                        C3332x4 c3332x4A = c3332x4H.D() ? interfaceC2641noA.a(c2409kl, b4) : c3332x4H;
                        if (c3332x4A != c3332x4H) {
                            InterfaceC2641no interfaceC2641noA2 = c3332x4H.a(c2409kl, c3332x4H.p().size());
                            interfaceC2641noA2.previous();
                            interfaceC2641noA2.add(c2477lf);
                            interfaceC2641noA = c3332x4A.a(c2409kl);
                            if (!z2 && interfaceC2641noA.o() != abstractC1305Qs) {
                                throw new AssertionError();
                            }
                            interfaceC2641noA.next();
                        } else {
                            interfaceC2641noA.add(c2477lf);
                            if (!z2 && interfaceC2641noA.o() != abstractC1305Qs) {
                                throw new AssertionError();
                            }
                            interfaceC2641noA.next();
                        }
                    }
                }
            }
        } else {
            AbstractC0833Al abstractC0833AlA = D.a(abstractC1305Qs, this.a, c2409kl.h());
            if (abstractC0833AlA == null) {
                a(c0684j2S3, c2409kl.h(), abstractC1305Qs, (C0759u2) null);
                return interfaceC2641noA;
            }
            C0767v2 c0767v2L2 = abstractC1305Qs.L2();
            C0722p1 c0722p1A2 = this.a.a();
            if (abstractC1305Qs.f2()) {
                C2115gt c2115gtI1 = abstractC1305Qs.i0();
                if (c2115gtI1.S2() == c0722p1A2.k4.i && (D.a(c0722p1A2, c2115gtI1.U2()) || D.a(c0722p1A2, (S10) ((ArrayList) c2115gtI1.h1()).get(1)))) {
                    List listH1 = abstractC1305Qs.h1();
                    int i3 = 0;
                    while (true) {
                        ArrayList arrayList2 = (ArrayList) listH1;
                        if (i3 >= arrayList2.size()) {
                            i = -1;
                            break;
                        }
                        S10 s10M = ((S10) arrayList2.get(i3)).m();
                        if (s10M.K() || s10M.M()) {
                            i = i3;
                            break;
                        }
                        i3++;
                    }
                } else if (c0767v2L2 == this.a.a().m2) {
                    if (!c) {
                        c0757u1 = this.a.a().p4;
                        c0684j2S2 = abstractC1305Qs.S2();
                        if (c0684j2S2 != c0757u1.b && c0684j2S2 != c0757u1.c) {
                            throw new AssertionError();
                        }
                    }
                    i = 0;
                } else if (c0767v2L2 != this.a.a().l2) {
                    i = 1;
                } else {
                    if (c && abstractC1305Qs.S2() != this.a.a().y4.a) {
                        throw new AssertionError();
                    }
                    i = 2;
                }
            } else if (c0767v2L2 == this.a.a().m2) {
                if (!c) {
                    c0757u1 = this.a.a().p4;
                    c0684j2S2 = abstractC1305Qs.S2();
                    if (c0684j2S2 != c0757u1.b) {
                        throw new AssertionError();
                    }
                }
                i = 0;
            } else if (c0767v2L2 != this.a.a().l2) {
                if (c) {
                }
                i = 2;
            } else {
                i = 1;
            }
            boolean z3 = c;
            if (!z3 && i < 0) {
                throw new AssertionError();
            }
            S10 s11 = (S10) ((ArrayList) abstractC1305Qs.J2()).get(i);
            AbstractC2412ko abstractC2412ko = s11.b;
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof C2477lf) {
                return interfaceC2641noA;
            }
            S10 s10A2 = c2409kl.a(s11.s(), s11.w());
            C2477lf c2477lf2 = new C2477lf(s10A2, abstractC0833AlA.b(), C3141ua.g());
            s10Arr[i] = s10A2;
            if (s11.Z() == 1) {
                final C1033Hc c1033HcF = s11.b.F();
                if (c1033HcF.h() == abstractC1305Qs.h()) {
                    interfaceC2641noA.c(new Predicate() { // from class: com.android.tools.r8.naming.C$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return C.a(c1033HcF, (AbstractC2412ko) obj);
                        }
                    });
                    AbstractC2412ko next = interfaceC2641no.next();
                    if (!z3 && next != c1033HcF) {
                        throw new AssertionError();
                    }
                    interfaceC2641noA.d(c2477lf2);
                    interfaceC2641noA.a(new Predicate() { // from class: com.android.tools.r8.naming.C$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return C.a(abstractC1305Qs, (AbstractC2412ko) obj);
                        }
                    });
                } else {
                    AbstractC2412ko abstractC2412ko2 = s11.b;
                    abstractC2412ko2.h().a(c2409kl, abstractC2412ko2).a(c2477lf2, (Set) null);
                }
            } else {
                c2477lf2.b(abstractC1305Qs.Y0());
                if (!z3 && interfaceC2641no.j() != abstractC1305Qs) {
                    throw new AssertionError();
                }
                interfaceC2641no.previous();
                C3332x4 c3332x4H2 = abstractC1305Qs.h();
                C3332x4 c3332x4A2 = c3332x4H2.D() ? interfaceC2641noA.a(c2409kl, b4) : c3332x4H2;
                if (c3332x4A2 != c3332x4H2) {
                    InterfaceC2641no interfaceC2641noA3 = c3332x4H2.a(c2409kl, c3332x4H2.p().size());
                    interfaceC2641noA3.previous();
                    interfaceC2641noA3.add(c2477lf2);
                    InterfaceC2641no interfaceC2641noA4 = c3332x4A2.a(c2409kl);
                    if (!z3 && interfaceC2641noA4.o() != abstractC1305Qs) {
                        throw new AssertionError();
                    }
                    interfaceC2641noA4.next();
                    interfaceC2641no2 = interfaceC2641noA4;
                } else {
                    interfaceC2641noA.add(c2477lf2);
                    if (!z3 && interfaceC2641no.o() != abstractC1305Qs) {
                        throw new AssertionError();
                    }
                    interfaceC2641no.next();
                }
                if (!Arrays.stream(s10Arr).allMatch(new Predicate() { // from class: com.android.tools.r8.naming.C$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return Objects.isNull((S10) obj);
                    }
                })) {
                    interfaceC2641no2.d(AbstractC1084Is.a(abstractC1305Qs.M2(), c0684j2S3, c0684j2S3.h, abstractC1305Qs.b(), (List) AbstractC1699bX.a(arrayList.stream(), new ZW() { // from class: com.android.tools.r8.naming.C$$ExternalSyntheticLambda4
                        @Override // com.android.tools.r8.internal.ZW
                        public final Object a(Object obj, long j) {
                            return C.a(s10Arr, (S10) obj, j);
                        }
                    }).collect(Collectors.toList()), false));
                }
                return interfaceC2641no2;
            }
        }
        interfaceC2641no2 = interfaceC2641noA;
        if (!Arrays.stream(s10Arr).allMatch(new Predicate() { // from class: com.android.tools.r8.naming.C$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.isNull((S10) obj);
            }
        })) {
            interfaceC2641no2.d(AbstractC1084Is.a(abstractC1305Qs.M2(), c0684j2S3, c0684j2S3.h, abstractC1305Qs.b(), (List) AbstractC1699bX.a(arrayList.stream(), new ZW() { // from class: com.android.tools.r8.naming.C$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.internal.ZW
                public final Object a(Object obj, long j) {
                    return C.a(s10Arr, (S10) obj, j);
                }
            }).collect(Collectors.toList()), false));
        }
        return interfaceC2641no2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ S10 a(S10[] s10Arr, S10 s10, long j) {
        S10 s11 = s10Arr[(int) j];
        return s11 != null ? s11 : s10;
    }

    private void a(AbstractC0642e2 abstractC0642e2, o5 o5Var, AbstractC2412ko abstractC2412ko, C0759u2 c0759u2) {
        String str;
        StringDiagnostic stringDiagnostic;
        if (!c && !abstractC0642e2.x0() && !abstractC0642e2.y0()) {
            throw new AssertionError();
        }
        if (((C2769pI) this.b).b(abstractC0642e2) && this.a.Y().k1()) {
            Origin origin = o5Var.getOrigin();
            String str2 = abstractC0642e2.x0() ? "field" : "method";
            if (c0759u2 == null) {
                str = "what identifier string flows to ";
            } else {
                str = "what '" + c0759u2.toString() + "' refers to, which flows to ";
            }
            String str3 = "Cannot determine " + str + abstractC0642e2.r0() + " that is specified in -identifiernamestring rules. Thus, not all identifier strings flowing to that " + str2 + " are renamed, which can cause resolution failures at runtime.";
            if (abstractC2412ko.Y0().e() >= 1) {
                stringDiagnostic = new StringDiagnostic(str3, origin, new TextPosition(0L, abstractC2412ko.Y0().e(), 1));
            } else {
                stringDiagnostic = new StringDiagnostic(str3, origin);
            }
            this.a.Y().e.warning(stringDiagnostic);
        }
    }
}
