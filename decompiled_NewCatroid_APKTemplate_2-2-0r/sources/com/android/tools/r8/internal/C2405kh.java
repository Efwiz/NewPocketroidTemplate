package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2405kh {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final InterfaceC2481lh b;
    private final Set c = OU.c();
    private final ArrayDeque d = new ArrayDeque();

    public C2405kh(C0702m<?> c0702m, InterfaceC2481lh interfaceC2481lh) {
        this.a = c0702m;
        this.b = interfaceC2481lh;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.utils.N n, AbstractC2412ko abstractC2412ko) {
        n.a(abstractC2412ko);
        return true;
    }

    public final boolean b(C2409kl c2409kl, S10 s10) {
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        a(c2409kl, s10, new Predicate() { // from class: com.android.tools.r8.internal.kh$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2405kh.a(n, (AbstractC2412ko) obj);
            }
        });
        return n.c();
    }

    public Set<AbstractC2412ko> a(C2409kl c2409kl, S10 s10) {
        final C0862Bm c0862BmG = AbstractC1048Hm.g();
        a(c2409kl, s10, new Predicate() { // from class: com.android.tools.r8.internal.kh$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2405kh.a(c0862BmG, (AbstractC2412ko) obj);
            }
        });
        return c0862BmG.a();
    }

    public final boolean b(S10 s10) {
        return this.c.contains(s10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0862Bm c0862Bm, AbstractC2412ko abstractC2412ko) {
        c0862Bm.a(abstractC2412ko);
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:90:0x0166  */
    private void a(C2409kl c2409kl, S10 s10, Predicate predicate) {
        C3332x4 c3332x4H;
        S10 s10E;
        boolean z = e;
        if (!z && !s10.w().I()) {
            throw new AssertionError();
        }
        if (!z && !this.c.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !this.d.isEmpty()) {
            throw new AssertionError();
        }
        if (s10.i()) {
            c3332x4H = s10.l().h();
        } else {
            c3332x4H = s10.b.h();
        }
        if (!z && !c2409kl.d.contains(c3332x4H)) {
            throw new AssertionError();
        }
        List<S10> listC = c2409kl.c();
        a(s10);
        loop0: while (!this.d.isEmpty()) {
            S10 s11 = (S10) this.d.poll();
            boolean z2 = e;
            if (!z2 && s11 == null) {
                throw new AssertionError();
            }
            if (!z2 && !this.c.contains(s11)) {
                throw new AssertionError();
            }
            Iterator it = s11.g0().iterator();
            while (it.hasNext()) {
                a((S10) it.next());
            }
            for (AbstractC2412ko abstractC2412ko : s11.h0()) {
                if (abstractC2412ko.h() == c3332x4H && !s10.i()) {
                    LinkedList<AbstractC2412ko> linkedListP = c3332x4H.p();
                    if (linkedListP.indexOf(abstractC2412ko) < linkedListP.indexOf(s10.b)) {
                        continue;
                    }
                }
                if (!this.b.a(this.a, this, abstractC2412ko, c2409kl.h())) {
                    com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
                    boolean zEquals = true;
                    if (!abstractC2412ko.u2() && !abstractC2412ko.B2() && !abstractC2412ko.c()) {
                        if (abstractC2412ko.Y1()) {
                            if (abstractC2412ko.b0().S2() == o5VarH.getReference()) {
                                zEquals = true ^ ((ArrayList) abstractC2412ko.h1()).equals(listC);
                            }
                        } else if (abstractC2412ko.r1()) {
                            S10 s10M = abstractC2412ko.v().J2().m();
                            if (!s10M.i() && s10M.b.D1()) {
                                zEquals = false;
                            }
                        } else if (abstractC2412ko.S1()) {
                            S10 s10M2 = abstractC2412ko.V().e().m();
                            if (!s10M2.i() && s10M2.b.p2()) {
                                zEquals = false;
                            }
                        } else {
                            zEquals = false;
                        }
                    }
                    if (zEquals && predicate.test(abstractC2412ko)) {
                        break loop0;
                    }
                }
                Iterator it2 = this.c.iterator();
                while (it2.hasNext()) {
                    if (abstractC2412ko.a(this.a, (S10) it2.next())) {
                        S10 s10B = abstractC2412ko.b();
                        if (!e && (s10B == null || !s10B.w().I())) {
                            throw new AssertionError();
                        }
                        a(s10B);
                        break;
                    }
                }
                if (abstractC2412ko.r1()) {
                    s10E = abstractC2412ko.v().J2();
                } else {
                    s10E = abstractC2412ko.S1() ? abstractC2412ko.V().e() : null;
                }
                if (s10E != null && s10E != s11) {
                    if (!e && !s10E.w().I()) {
                        throw new AssertionError();
                    }
                    a(s10E);
                }
            }
        }
        this.c.clear();
        this.d.clear();
    }

    private void a(S10 s10) {
        if (!e && s10 == null) {
            throw new AssertionError();
        }
        if (this.c.add(s10)) {
            this.d.push(s10);
        }
    }
}
