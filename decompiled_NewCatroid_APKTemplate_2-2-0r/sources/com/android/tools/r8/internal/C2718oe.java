package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oe, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2718oe {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final Function b;

    public C2718oe(C0702m c0702m, final AbstractC1476Wj abstractC1476Wj, final AbstractC1476Wj abstractC1476Wj2) {
        this(c0702m, new Function() { // from class: com.android.tools.r8.internal.oe$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return abstractC1476Wj.a(abstractC1476Wj2, (C0767v2) obj);
            }
        });
    }

    public C2718oe(C0702m c0702m, Function function) {
        this.a = c0702m;
        this.b = function;
    }

    public final void a(C2409kl c2409kl, Set set) {
        ArrayDeque arrayDeque = new ArrayDeque(set);
        while (!arrayDeque.isEmpty()) {
            ML ml = (ML) arrayDeque.poll();
            ml.a(AbstractC3472z00.f());
            for (ML ml2 : ml.g0()) {
                if (set.add(ml2)) {
                    arrayDeque.add(ml2);
                }
            }
        }
        if (!c) {
            B4 b4S = c2409kl.s();
            while (b4S.hasNext()) {
                for (ML ml3 : b4S.next().x()) {
                    AbstractC3472z00 abstractC3472z00W = ml3.w();
                    C0702m c0702m = this.a;
                    Function function = this.b;
                    abstractC3472z00W.getClass();
                    AbstractC3472z00 abstractC3472z00A = abstractC3472z00W.a(c0702m, function, Collections.emptySet());
                    if (!c && abstractC3472z00A != abstractC3472z00W && !set.contains(ml3)) {
                        throw new AssertionError();
                    }
                }
            }
        }
        if (!c) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                for (S10 s10 : ((ML) it.next()).k0()) {
                    if (s10.i()) {
                        ML mlL = s10.l();
                        AbstractC3472z00 abstractC3472z00W2 = mlL.w();
                        boolean z = c;
                        if (!z && set.contains(mlL)) {
                            abstractC3472z00W2.getClass();
                            if (!(abstractC3472z00W2 instanceof G5)) {
                                throw new AssertionError();
                            }
                        }
                        if (!z && !set.contains(mlL) && !abstractC3472z00W2.H() && !(abstractC3472z00W2 instanceof AS) && (!abstractC3472z00W2.I() || abstractC3472z00W2.a(this.a, this.b, Collections.emptySet()) != abstractC3472z00W2)) {
                            throw new AssertionError();
                        }
                    }
                }
            }
        }
        Set setC = OU.c();
        arrayDeque.addAll(set);
        while (!arrayDeque.isEmpty()) {
            ML ml4 = (ML) arrayDeque.poll();
            AbstractC3472z00 abstractC3472z00A2 = ml4.a(this.a);
            if (!ml4.w().equals(abstractC3472z00A2)) {
                if (!c) {
                    abstractC3472z00A2.getClass();
                    if (abstractC3472z00A2 instanceof G5) {
                        throw new AssertionError();
                    }
                }
                ml4.a(abstractC3472z00A2);
                arrayDeque.addAll(ml4.g0());
                setC.addAll(ml4.a());
            }
        }
        if (!c) {
            new C3030t00(this.a, false).b(set);
        }
        if (setC.isEmpty()) {
            return;
        }
        new C3030t00(this.a, false).a((Iterable) setC);
    }
}
