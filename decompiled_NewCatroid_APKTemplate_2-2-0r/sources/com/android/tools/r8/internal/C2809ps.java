package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ps, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2809ps extends AbstractC2645ns {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final C2409kl b;
    private final IdentityHashMap c = new IdentityHashMap();

    /* JADX INFO: renamed from: $r8$lambda$qSzNch9Nn3UeD4gqg-RoDp7DiM8, reason: not valid java name */
    public static /* synthetic */ ArrayDeque m1634$r8$lambda$qSzNch9Nn3UeD4gqgRoDp7DiM8() {
        return new ArrayDeque();
    }

    public C2809ps(C0702m c0702m, C2409kl c2409kl) {
        this.a = c0702m;
        this.b = c2409kl;
    }

    @Override // com.android.tools.r8.internal.AbstractC2645ns
    public final void a(AbstractC1305Qs abstractC1305Qs, AbstractC1305Qs abstractC1305Qs2, KE ke, C3332x4 c3332x4) {
        C0684j2 c0684j2S2 = abstractC1305Qs.S2();
        C0684j2 c0684j2S3 = abstractC1305Qs2.S2();
        if (ke.e().a().e()) {
            if (!d) {
                throw new AssertionError();
            }
            return;
        }
        boolean z = abstractC1305Qs instanceof C1721bt;
        if (((ArrayList) abstractC1305Qs.J2()).size() != c0684j2S2.a(z)) {
            return;
        }
        for (int i = 0; i < ((ArrayList) abstractC1305Qs.J2()).size(); i++) {
            S10 s10C = abstractC1305Qs2.c(i);
            C0767v2 c0767v2A = c0684j2S2.a(i, z);
            C0767v2 c0767v2A2 = c0684j2S3.a(i, abstractC1305Qs2 instanceof C1721bt);
            if (a(s10C, c3332x4, c0767v2A, c0767v2A2, false).c()) {
                a(abstractC1305Qs2, i, c0767v2A, c0767v2A2);
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2645ns
    public final void a(C1616aU c1616aU, C3332x4 c3332x4) {
        if (!d && c1616aU.K2()) {
            throw new AssertionError();
        }
        C0767v2 c0767v2I0 = this.a.E().a((AbstractC1476Wj) null, this.b.h().getReference()).I0();
        C0767v2 c0767v2G = this.b.h().G();
        if (a(c1616aU.L2(), c3332x4, c0767v2I0, c0767v2G, false).c()) {
            a(c1616aU, 0, c0767v2I0, c0767v2G);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.AbstractC2645ns
    public final void a(InterfaceC1474Wh interfaceC1474Wh, InterfaceC1474Wh interfaceC1474Wh2, C3332x4 c3332x4) {
        C0767v2 type = interfaceC1474Wh.getField().getType();
        C0767v2 type2 = interfaceC1474Wh2.getField().getType();
        if (a(interfaceC1474Wh2.value(), c3332x4, type, type2, false).c()) {
            a((AbstractC1182Mh) interfaceC1474Wh2, interfaceC1474Wh2.f(), type, type2);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2645ns
    public final void a() {
        if (this.c.isEmpty()) {
            return;
        }
        B4 b4S = this.b.s();
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            InterfaceC2641no interfaceC2641noA = next.a(this.b);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next2 = interfaceC2641noA.next();
                Deque<C2733os> deque = (Deque) this.c.get(next2);
                if (deque != null) {
                    InterfaceC2641no interfaceC2641noA2 = interfaceC2641noA;
                    for (C2733os c2733os : deque) {
                        S10 s10A = next2.a(c2733os.a);
                        C0767v2 c0767v2 = c2733os.b;
                        C0767v2 c0767v3 = c2733os.c;
                        AbstractC3938n2 abstractC3938n2A = a(s10A, next, c0767v2, c0767v3, true);
                        boolean z = d;
                        if (!z && abstractC3938n2A.e()) {
                            throw new AssertionError();
                        }
                        if (abstractC3938n2A.d()) {
                            AbstractC2412ko abstractC2412koPrevious = interfaceC2641noA2.previous();
                            if (!z && abstractC2412koPrevious != next2) {
                                throw new AssertionError();
                            }
                            int i = C2972s9.k;
                            C2901r9 c2901r9B = new C2901r9().a(c0767v3).b(s10A);
                            C2409kl c2409kl = this.b;
                            C0702m c0702m = this.a;
                            WH whN = s10A.w().N();
                            c0767v3.getClass();
                            C2972s9 c2972s9B = ((C2901r9) ((C2901r9) c2901r9B.a(c2409kl, AbstractC3472z00.a(c0767v3, whN, (C0702m<?>) c0702m), s10A.s())).b(next2)).b();
                            if (next.D()) {
                                C3332x4 c3332x4A = interfaceC2641noA2.a(this.b, b4S, this.a.Y());
                                interfaceC2641noA2.previous();
                                interfaceC2641noA2.add(c2972s9B);
                                interfaceC2641noA2 = c3332x4A.a(this.b);
                            } else {
                                interfaceC2641noA2.add(c2972s9B);
                            }
                            next2.b(s10A, c2972s9B.b());
                            AbstractC2412ko next3 = interfaceC2641noA2.next();
                            if (!z && next3 != next2) {
                                throw new AssertionError();
                            }
                        }
                    }
                    interfaceC2641noA = interfaceC2641noA2;
                }
            }
        }
    }

    private void a(AbstractC2412ko abstractC2412ko, int i, C0767v2 c0767v2, C0767v2 c0767v3) {
        ((Deque) this.c.computeIfAbsent(abstractC2412ko, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.ps$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return C2809ps.m1634$r8$lambda$qSzNch9Nn3UeD4gqgRoDp7DiM8();
            }
        }))).addLast(new C2733os(i, c0767v2, c0767v3));
    }

    private AbstractC3938n2 a(S10 s10, C3332x4 c3332x4, C0767v2 c0767v2, C0767v2 c0767v3, boolean z) {
        if (c0767v2.O0() && c0767v3.O0()) {
            C0729q2 c0729q2A = C0729q2.a(this.a.c(c0767v2));
            if (c0729q2A != null && c0729q2A.isInterface()) {
                C0729q2 c0729q2A2 = C0729q2.a(this.a.c(c0767v3));
                if (c0729q2A2 != null && !c0729q2A2.isInterface()) {
                    if (!z && (s10.i() || (!s10.q().m1() && s10.h() != c3332x4))) {
                        if (d || !z) {
                            return AbstractC3938n2.c;
                        }
                        throw new AssertionError();
                    }
                    return AbstractC3938n2.a(!s10.w().b(c0767v3.c(this.a), this.a));
                }
                return AbstractC3938n2.b;
            }
            return AbstractC3938n2.b;
        }
        return AbstractC3938n2.b;
    }
}
