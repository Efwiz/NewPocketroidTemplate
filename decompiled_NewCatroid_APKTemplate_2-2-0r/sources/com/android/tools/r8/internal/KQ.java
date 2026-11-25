package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class KQ extends AbstractC2525mG {
    static final /* synthetic */ boolean b = true;
    final com.android.tools.r8.graph.Y0[] a;

    protected KQ(com.android.tools.r8.graph.Y0[] y0Arr) {
        this.a = y0Arr;
    }

    public static KQ a(C0759u2 c0759u2, com.android.tools.r8.graph.Y0[] y0Arr) {
        String string = c0759u2.toString();
        String[] strArrSplit = string.isEmpty() ? new String[0] : string.split(";");
        if (!b && strArrSplit.length != y0Arr.length) {
            throw new AssertionError();
        }
        for (int i = 0; i < strArrSplit.length; i++) {
            if (!y0Arr[i].f.toString().equals(strArrSplit[i])) {
                return new JQ(strArrSplit, y0Arr);
            }
        }
        return new IQ(y0Arr);
    }

    public abstract C0759u2 a(C0767v2 c0767v2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0);

    @Override // com.android.tools.r8.internal.AbstractC2525mG
    public final KQ c() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2525mG
    public final boolean d() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2525mG
    public final boolean e() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2525mG
    public final boolean f() {
        return false;
    }

    public final C0759u2 a(final C0767v2 c0767v2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, IntFunction intFunction) {
        boolean z = b;
        if (!z && !Arrays.stream(this.a).allMatch(new Predicate() { // from class: com.android.tools.r8.internal.KQ$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return KQ.a(c0767v2, (com.android.tools.r8.graph.Y0) obj);
            }
        })) {
            throw new AssertionError();
        }
        AbstractC0727q0 abstractC0727q0A = c0702m.a(c0767v2);
        if (!z && abstractC0727q0A == null) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList(this.a.length);
        int i = 0;
        while (true) {
            com.android.tools.r8.graph.Y0[] y0Arr = this.a;
            if (i < y0Arr.length) {
                if (abstractC0727q0A.b(abstractC1476Wj.b((AbstractC1476Wj) null, y0Arr[i])) != null) {
                    arrayList.add((String) intFunction.apply(i));
                }
                i++;
            } else {
                return c0702m.a().c(com.android.tools.r8.utils.Y2.a(";", arrayList));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0767v2 c0767v2, com.android.tools.r8.graph.Y0 y0) {
        return y0.e == c0767v2;
    }
}
