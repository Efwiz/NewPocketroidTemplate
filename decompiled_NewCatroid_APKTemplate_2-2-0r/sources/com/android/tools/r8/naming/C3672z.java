package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.m5;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiPredicate;

/* JADX INFO: renamed from: com.android.tools.r8.naming.z, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3672z extends A implements Cloneable {
    private final Q0 c;
    private final S d;
    private final BiPredicate e;

    public C3672z(C0702m c0702m, S s) {
        this(c0702m, s, new Q0(c0702m));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Q0 q0, C0759u2 c0759u2, m5 m5Var) {
        C0767v2 c0767v2 = m5Var.getReference().h;
        return !q0.a(c0759u2);
    }

    /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
    public final C3672z clone() {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        for (Map.Entry entry : this.b.entrySet()) {
            C0767v2 c0767v2 = (C0767v2) entry.getKey();
            C3671y c3671y = (C3671y) entry.getValue();
            identityHashMap.put(c0767v2, new C3671y(c3671y.c, c3671y.b, c3671y.a));
        }
        return new C3672z(this.a, this.d, this.c, identityHashMap);
    }

    public C3672z(C0702m c0702m, S s, Q0 q0) {
        this(c0702m, s, q0, new IdentityHashMap());
    }

    public final C3672z a(Q0 q0) {
        return new C3672z(this.a, this.d, q0, this.b);
    }

    private C3672z(C0702m c0702m, S s, final Q0 q0, Map map) {
        super(c0702m, map);
        this.c = q0;
        this.d = s;
        this.e = new BiPredicate() { // from class: com.android.tools.r8.naming.z$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return C3672z.a(q0, (C0759u2) obj, (m5) obj2);
            }
        };
    }

    public final C0759u2 a(m5 m5Var) {
        C0759u2 c0759u2A = this.d.a(m5Var.a(), m5Var.e());
        if (c0759u2A != null) {
            return c0759u2A;
        }
        C0767v2 c0767v2 = m5Var.getReference().h;
        C3671y c3671y = (C3671y) c();
        C3672z c3672z = c3671y.c;
        C0759u2 c0759u2A2 = c3672z.d.a(m5Var, c3671y, c3672z.e);
        if (!C3671y.d) {
            Q0 q0 = c3671y.c.c;
            C0767v2 c0767v3 = m5Var.getReference().h;
            if (q0.a(c0759u2A2)) {
                throw new AssertionError();
            }
        }
        return c0759u2A2;
    }

    @Override // com.android.tools.r8.naming.A
    public final Object a() {
        return new C3671y(this, 1, 0);
    }
}
