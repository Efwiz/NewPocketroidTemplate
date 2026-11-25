package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.al, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1633al {
    static final /* synthetic */ boolean d = true;
    private final C2409kl a;
    private final TB b;
    private final NB c;

    private C1633al(C0722p1 c0722p1, C2409kl c2409kl, TB tb) {
        this.a = c2409kl;
        this.b = tb;
        this.c = new NB(c2409kl.h().getReference(), tb, c0722p1).a(c2409kl.t());
    }

    public static RB a(C0722p1 c0722p1, C2409kl c2409kl, TB tb) {
        C1633al c1633al = new C1633al(c0722p1, c2409kl, tb);
        c2409kl.E();
        int i = 0;
        int i2 = 0;
        for (C3332x4 c3332x4 : c2409kl.d) {
            c1633al.b.a(c3332x4, i);
            for (ML ml : c3332x4.x()) {
                Object objA = c1633al.b.a(ml, i2);
                if (ml.A()) {
                    c1633al.c.a(objA, ml.s());
                }
                if (c1633al.b.b()) {
                    i2++;
                    i++;
                }
            }
            for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                if (abstractC2412ko.d1()) {
                    S10 s10B = abstractC2412ko.b();
                    Object objA2 = c1633al.b.a(s10B, i2);
                    if (s10B.A()) {
                        c1633al.c.a(objA2, s10B.s());
                    }
                }
                i2++;
                if (!abstractC2412ko.m1()) {
                    i++;
                }
            }
        }
        c1633al.a();
        return c1633al.c.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ InterfaceC0967Er b(C3332x4 c3332x4) {
        return new C2732or(1);
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00ba  */
    private void a() {
        B4 b4S = this.a.s();
        int iA = 0;
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            if (this.b.b()) {
                iA += a(next);
            }
            if (next.D()) {
                C3038t6 c3038t6N = next.n();
                NB nb = this.c;
                int iA2 = this.b.a(next);
                List listB = c3038t6N.b();
                List listA = c3038t6N.a();
                final TB tb = this.b;
                Objects.requireNonNull(tb);
                nb.a(iA2, new C3038t6(listB, com.android.tools.r8.utils.V1.a((Collection) listA, new Function() { // from class: com.android.tools.r8.internal.al$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return Integer.valueOf(tb.a((C3332x4) obj));
                    }
                })));
            }
            InterfaceC2488lo interfaceC2488loR = next.R();
            while (interfaceC2488loR.hasNext()) {
                boolean z = d;
                if (!z) {
                    this.c.e(iA);
                }
                AbstractC2412ko next2 = interfaceC2488loR.next();
                if (!z && next2.d1() && !this.b.b(next2.b(), iA)) {
                    throw new AssertionError();
                }
                this.c.b(next2.Y0());
                if (!next2.S0().isEmpty()) {
                    this.c.a(iA, next2.S0());
                }
                if (next2.M1()) {
                    if (next2.P().K2() == ((C3332x4) com.android.tools.r8.utils.L1.a((ListIterator) b4S))) {
                        this.c.b();
                    } else {
                        next2.a(this.c);
                    }
                } else {
                    next2.a(this.c);
                }
                iA++;
            }
            if (!d) {
                this.c.e(iA);
            }
        }
        if (this.b.b()) {
            return;
        }
        this.a.s().forEachRemaining(new Consumer() { // from class: com.android.tools.r8.internal.al$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C3332x4) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(C3332x4 c3332x4) {
        if (!c3332x4.G()) {
            return 0;
        }
        List<C3332x4> listY = c3332x4.y();
        final TB tb = this.b;
        Objects.requireNonNull(tb);
        int[] iArrA = a(listY, new InterfaceC1559Zk() { // from class: com.android.tools.r8.internal.al$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.internal.InterfaceC1559Zk
            public final int a(C3332x4 c3332x5) {
                return tb.a(c3332x5);
            }
        });
        S10[] s10Arr = new S10[c3332x4.y().size()];
        int i = 0;
        for (ML ml : c3332x4.x()) {
            List<S10> listK0 = ml.k0();
            for (int i2 = 0; i2 < listK0.size(); i2++) {
                s10Arr[iArrA[i2]] = listK0.get(i2);
            }
            this.c.a(ml.w(), Arrays.asList(s10Arr));
            i++;
        }
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static int[] a(List list, final InterfaceC1559Zk interfaceC1559Zk) {
        int size;
        int size2 = list.size();
        ArrayList arrayList = new ArrayList(list);
        Objects.requireNonNull(interfaceC1559Zk);
        arrayList.sort(Comparator.comparingInt(new ToIntFunction() { // from class: com.android.tools.r8.internal.al$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return interfaceC1559Zk.a((C3332x4) obj);
            }
        }));
        C2306jS c2306jS = new C2306jS(size2);
        int i = 0;
        for (int i2 = 0; i2 < size2; i2++) {
            ((InterfaceC0967Er) c2306jS.computeIfAbsent((C3332x4) list.get(i2), new Function() { // from class: com.android.tools.r8.internal.al$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C1633al.b((C3332x4) obj);
                }
            })).a(i2);
        }
        int[] iArr = new int[size2];
        while (i < size2) {
            InterfaceC0967Er interfaceC0967Er = (InterfaceC0967Er) c2306jS.get((C3332x4) arrayList.get(i));
            if (!d && (size = interfaceC0967Er.size()) != 1) {
                C3332x4 c3332x4 = (C3332x4) arrayList.get(i);
                for (int i3 = i + 1; i3 < i + size; i3++) {
                    C3332x4 c3332x5 = (C3332x4) arrayList.get(i3);
                    if (!d && c3332x4 != c3332x5) {
                        throw new AssertionError();
                    }
                }
            }
            InterfaceC0993Fr it = interfaceC0967Er.iterator();
            while (it.hasNext()) {
                iArr[((Integer) it.next()).intValue()] = i;
                i++;
            }
        }
        return iArr;
    }
}
