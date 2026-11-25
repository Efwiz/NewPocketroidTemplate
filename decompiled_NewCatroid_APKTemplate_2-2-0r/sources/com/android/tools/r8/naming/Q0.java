package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Q0 extends A {
    static final /* synthetic */ boolean d = true;
    private Q0 c;

    Q0(C0702m c0702m) {
        super(c0702m, new IdentityHashMap());
        this.c = null;
    }

    final boolean a(C0759u2 c0759u2) {
        C0759u2 c0759u3;
        P0 p0;
        P0 p1;
        P0 p2 = (P0) b();
        C0759u2 c0759u4 = null;
        if (p2 == null) {
            c0759u3 = null;
        } else {
            c0759u3 = (C0759u2) p2.a.get(c0759u2);
            if (c0759u3 == null) {
                c0759u3 = (C0759u2) p2.a.get(c0759u2);
            }
        }
        if (c0759u3 == null) {
            Q0 q0 = this.c;
            if (q0 == null || (p1 = (P0) q0.b()) == null) {
                c0759u3 = null;
            } else {
                c0759u3 = (C0759u2) p1.a.get(c0759u2);
                if (c0759u3 == null) {
                    c0759u3 = (C0759u2) p1.a.get(c0759u2);
                }
            }
        }
        if (c0759u3 == null) {
            Q0 q1 = this.c;
            if (q1 != null && (p0 = (P0) q1.b()) != null && (c0759u4 = (C0759u2) p0.a.get(c0759u2)) == null) {
                c0759u4 = (C0759u2) p0.a.get(c0759u2);
            }
            if (c0759u4 == null) {
                return false;
            }
        }
        return true;
    }

    final void b(Q0 q0) {
        Q0 q1;
        boolean z = d;
        if (!z && q0 == null) {
            throw new AssertionError();
        }
        if (!z && (q1 = this.c) != null && q1 != q0) {
            throw new AssertionError();
        }
        this.c = q0;
    }

    final void a(C0759u2 c0759u2, C0759u2 c0759u3) {
        ((P0) c()).a.put(c0759u2, c0759u3);
    }

    final void a(Q0 q0) {
        Q0 q1;
        for (Map.Entry entry : q0.b.entrySet()) {
            ((P0) c()).a.putAll(((P0) entry.getValue()).a);
        }
        Q0 q2 = q0.c;
        if (q2 != null) {
            if (!d && (q1 = this.c) != null && q1 != q2) {
                throw new AssertionError();
            }
            this.c = q2;
        }
    }

    @Override // com.android.tools.r8.naming.A
    final Object a() {
        return new P0();
    }
}
