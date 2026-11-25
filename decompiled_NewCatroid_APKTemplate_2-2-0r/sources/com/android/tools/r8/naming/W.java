package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.utils.C3894f2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class W implements I {
    static final /* synthetic */ boolean f = true;
    private final W a;
    private HashMap b;
    private HashMap c;
    private int d;
    private int e;

    private W(W w) {
        this.b = new HashMap();
        this.c = new HashMap();
        this.a = w;
        this.e = w == null ? 0 : w.e;
        this.d = w == null ? 1 : w.d;
    }

    final void a(C0684j2 c0684j2, C0759u2 c0759u2) {
        C2173hh c2173hhC = C3894f2.a().c(c0684j2);
        this.b.put(c2173hhC, c0759u2);
        ((Set) this.c.computeIfAbsent(c0759u2, new Function() { // from class: com.android.tools.r8.naming.W$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return W.a((C0759u2) obj);
            }
        })).add(c2173hhC);
    }

    @Override // com.android.tools.r8.naming.I
    public final int b() {
        return this.e;
    }

    @Override // com.android.tools.r8.naming.I
    public final int c() {
        int i = this.e;
        this.e = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C0759u2 c0759u2) {
        return new HashSet();
    }

    @Override // com.android.tools.r8.naming.I
    public final int a() {
        if (!f) {
            int iMax = 0;
            for (W w = this.a; w != null; w = w.a) {
                iMax = Math.max(w.d, iMax);
            }
            if (!f && iMax > this.d) {
                throw new AssertionError();
            }
        }
        int i = this.d;
        this.d = i + 1;
        return i;
    }
}
