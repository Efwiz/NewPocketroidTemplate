package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1 {
    private ConcurrentHashMap a = new ConcurrentHashMap();
    private ConcurrentHashMap b = new ConcurrentHashMap();
    private ConcurrentHashMap c = new ConcurrentHashMap();
    private ConcurrentHashMap d = new ConcurrentHashMap();

    public final C2388kV a(C0767v2 c0767v2) {
        return (C2388kV) this.a.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.internal.C1$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new C2388kV((C0767v2) obj);
            }
        });
    }

    public final C1573Zy a(int i) {
        return (C1573Zy) this.d.computeIfAbsent(Integer.valueOf(i), new Function() { // from class: com.android.tools.r8.internal.C1$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new C1573Zy(((Integer) obj).intValue());
            }
        });
    }

    public static C1910eI a(long j, long j2) {
        return new C1910eI(j, j2);
    }

    public static AbstractC2706oV a(com.android.tools.r8.graph.Y0 y0, AbstractC3121uK abstractC3121uK) {
        if (abstractC3121uK.e()) {
            return new C3134uV(y0);
        }
        return new C3064tV(y0, abstractC3121uK);
    }

    public final C2853qV a(long j) {
        return (C2853qV) this.b.computeIfAbsent(Long.valueOf(j), new Function() { // from class: com.android.tools.r8.internal.C1$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new C2853qV(((Long) obj).longValue());
            }
        });
    }

    public C2853qV a() {
        return a(0L);
    }

    public final C3214vV a(C0759u2 c0759u2) {
        return (C3214vV) this.c.computeIfAbsent(c0759u2, new Function() { // from class: com.android.tools.r8.internal.C1$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new C3214vV((C0759u2) obj);
            }
        });
    }

    public static C2616nV a(AbstractC0744s2 abstractC0744s2, AbstractC2525mG abstractC2525mG) {
        return new C2616nV(abstractC0744s2, abstractC2525mG);
    }
}
