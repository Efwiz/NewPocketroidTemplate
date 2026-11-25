package com.android.tools.r8.internal;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class AN extends AbstractC3501zN {
    private static final AN b = new AN(new AN$$ExternalSyntheticLambda2());

    public static /* synthetic */ ConcurrentHashMap $r8$lambda$QAT0o7F8T79RZ3bOugQmmUXiMwE() {
        return new ConcurrentHashMap();
    }

    public static /* synthetic */ HashMap $r8$lambda$zL9zIj_AuRar_VZKE_7we4Hn4rk() {
        return new HashMap();
    }

    private AN(Supplier supplier) {
        super(supplier);
    }

    public static AN a(int i) {
        return new AN(new HashMap(i));
    }

    public static AN c() {
        return new AN(new Supplier() { // from class: com.android.tools.r8.internal.AN$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return AN.$r8$lambda$zL9zIj_AuRar_VZKE_7we4Hn4rk();
            }
        });
    }

    public static AN e() {
        return b;
    }

    @Override // com.android.tools.r8.internal.AbstractC3501zN
    final C2173hh d(com.android.tools.r8.graph.n5 n5Var) {
        return com.android.tools.r8.utils.D2.a().c((com.android.tools.r8.graph.o5) n5Var);
    }

    private AN(HashMap map) {
        super(map);
    }

    public static AN d() {
        return new AN(new Supplier() { // from class: com.android.tools.r8.internal.AN$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return AN.$r8$lambda$QAT0o7F8T79RZ3bOugQmmUXiMwE();
            }
        });
    }
}
