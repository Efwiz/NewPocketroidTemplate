package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F00 extends G00 {
    static final /* synthetic */ boolean c = true;
    private final ConcurrentHashMap a;
    private final Map b;

    public F00(C2215iD c2215iD) {
        this.a = new ConcurrentHashMap(c2215iD.v());
        this.b = c2215iD.u();
    }

    @Override // com.android.tools.r8.internal.G00
    public final boolean a() {
        return true;
    }

    @Override // com.android.tools.r8.internal.G00
    public final C0767v2 b(C0702m c0702m, C0767v2 c0767v2) {
        if (!c0767v2.M0()) {
            return (C0767v2) this.a.get(c0767v2);
        }
        C0767v2 c0767v2B = b(c0702m, c0767v2.b(c0702m.a()));
        if (c0767v2B == null) {
            return null;
        }
        return c0702m.a().a(c0767v2.G0(), c0767v2B);
    }

    @Override // com.android.tools.r8.internal.G00
    public final C0767v2 a(C0767v2 c0767v2) {
        if (c || !c0767v2.M0()) {
            return this.a.containsKey(c0767v2) ? (C0767v2) this.a.get(c0767v2) : (C0767v2) this.b.get(c0767v2);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.G00
    public final void a(C0767v2 c0767v2, final C0767v2 c0767v3) {
        this.a.compute(c0767v2, new BiFunction() { // from class: com.android.tools.r8.internal.F00$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return F00.a(c0767v3, (C0767v2) obj, (C0767v2) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(C0767v2 c0767v2, C0767v2 c0767v3, C0767v2 c0767v4) {
        if (c || c0767v4 == null || c0767v4 == c0767v2) {
            return c0767v2;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.G00
    public final void a(Consumer consumer) {
        this.a.keySet().forEach(consumer);
    }
}
