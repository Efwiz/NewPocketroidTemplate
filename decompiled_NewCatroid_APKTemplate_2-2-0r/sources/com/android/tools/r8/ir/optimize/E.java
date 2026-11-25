package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.C1458Vr;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2807pq;
import com.android.tools.r8.internal.C3155ul;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.DI;
import com.android.tools.r8.internal.GI;
import com.android.tools.r8.internal.InterfaceC1912eK;
import com.android.tools.r8.internal.InterfaceC3018sr;
import com.android.tools.r8.internal.S10;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class E extends C {
    private S10 c;
    private final C2807pq d;
    private C3332x4 e;

    public E(AbstractC1915eM abstractC1915eM) {
        super(abstractC1915eM);
        this.d = new C2807pq();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer b(GI gi, C3332x4 c3332x4) {
        return Integer.valueOf(gi.size());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final C3332x4 a(C3155ul c3155ul) {
        int i = 0;
        final DI di = new DI(0);
        di.e(-1);
        int[] iArr = new int[this.d.size()];
        int[] iArr2 = new int[this.d.size()];
        InterfaceC3018sr it = this.d.keySet().iterator();
        while (it.hasNext()) {
            int iT = it.t();
            Integer num = (Integer) di.computeIfAbsent((C3332x4) this.d.get(iT), new Function() { // from class: com.android.tools.r8.ir.optimize.E$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return E.a(di, (C3332x4) obj);
                }
            });
            iArr[i] = iT;
            iArr2[i] = num.intValue();
            i++;
        }
        Integer num2 = (Integer) di.computeIfAbsent(this.e, new Function() { // from class: com.android.tools.r8.ir.optimize.E$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return E.b(di, (C3332x4) obj);
            }
        });
        C1458Vr c1458Vr = new C1458Vr(num2.intValue(), this.c, iArr, iArr2);
        c1458Vr.b(this.b);
        int i2 = this.a;
        C3332x4 c3332x4 = new C3332x4();
        c3332x4.a(c1458Vr, c3155ul);
        c3332x4.a((C2102gl) null);
        c3332x4.g(i2);
        InterfaceC1912eK it2 = di.keySet().iterator();
        while (it2.hasNext()) {
            c3332x4.h((C3332x4) it2.next());
        }
        return c3332x4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer a(GI gi, C3332x4 c3332x4) {
        return Integer.valueOf(gi.size());
    }
}
