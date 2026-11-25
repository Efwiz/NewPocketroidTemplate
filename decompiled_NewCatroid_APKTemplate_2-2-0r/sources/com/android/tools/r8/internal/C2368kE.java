package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2368kE {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final IdentityHashMap b;
    private final IdentityHashMap c;

    private C2368kE(C0702m c0702m) {
        this.b = new IdentityHashMap();
        this.c = new IdentityHashMap();
        this.a = c0702m;
    }

    public final void a(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et) {
        if (c0684j2 == c0684j3) {
            if (!d && this.b.containsKey(enumC1953et) && ((Map) this.b.get(enumC1953et)).getOrDefault(c0684j2, c0684j3) != c0684j3) {
                throw new AssertionError();
            }
            return;
        }
        Map map = (Map) this.b.computeIfAbsent(enumC1953et, new Function() { // from class: com.android.tools.r8.internal.kE$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2368kE.a((EnumC1953et) obj);
            }
        });
        if (!d && map.getOrDefault(c0684j2, c0684j3) != c0684j3) {
            throw new AssertionError();
        }
        map.put(c0684j2, c0684j3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(EnumC1953et enumC1953et) {
        return new IdentityHashMap();
    }

    final void a(com.android.tools.r8.graph.R2 r2) {
        com.android.tools.r8.graph.U2 u2 = (com.android.tools.r8.graph.U2) r2;
        final com.android.tools.r8.graph.Y0 y0E = u2.e();
        u2.b(new Consumer() { // from class: com.android.tools.r8.internal.kE$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(y0E, (com.android.tools.r8.graph.Y0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        this.c.put(y1, y0);
    }

    public final C2449lE a() {
        return new C2449lE(this.a, this.b, this.c);
    }
}
