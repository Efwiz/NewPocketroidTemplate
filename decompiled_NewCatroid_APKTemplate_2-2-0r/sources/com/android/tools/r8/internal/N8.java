package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class N8 {
    static final /* synthetic */ boolean c = true;
    private L8 a;
    private AbstractC1915eM b;

    public final void a(AbstractC1915eM abstractC1915eM) {
        this.a = new E8(0, 0);
        this.b = abstractC1915eM;
    }

    public final L8 b() {
        return this.a;
    }

    public final H8 c() {
        return this.a.c();
    }

    public final H8 d() {
        L8 l8 = this.a;
        return l8.b(l8.d() - 2);
    }

    public final H8 e() {
        H8 h8C = this.a.c();
        L8 f8 = new F8(this.a);
        if (f8.b >= 4) {
            E8 e8 = new E8(f8.b() + 1, f8.d());
            f8.a(e8);
            f8 = e8;
        }
        this.a = f8;
        return h8C;
    }

    public final String toString() {
        return new E8(this.a).toString();
    }

    public final void b(AbstractC1915eM abstractC1915eM) {
        if (!c && abstractC1915eM == null) {
            throw new AssertionError();
        }
        this.b = abstractC1915eM;
    }

    public final void a(L8 l8, boolean z, AbstractC1915eM abstractC1915eM) {
        if (!c && z && l8 == null) {
            throw new AssertionError("Must have snapshot for method entry.");
        }
        this.a = l8;
        this.b = abstractC1915eM;
    }

    public final E8 a(C0767v2[] c0767v2Arr, C0767v2[] c0767v2Arr2, AbstractC1915eM abstractC1915eM) {
        L8 l8;
        if (!c && (l8 = this.a) != null && l8.d() != c0767v2Arr2.length) {
            throw new AssertionError();
        }
        E8 e8 = new E8(c0767v2Arr, c0767v2Arr2, abstractC1915eM);
        this.a = e8;
        return e8;
    }

    public static L8 a(L8 l8, L8 l9, Origin origin) {
        if (c || l9 != null) {
            return l8 == null ? l9 : a(l8.a(), l9.a(), origin);
        }
        throw new AssertionError();
    }

    private static L8 a(E8 e8, E8 e9, Origin origin) {
        if (e8.d.length != e9.d.length) {
            throw new C1367Tb("Different stack heights at jump target: " + e8.d.length + " != " + e9.d.length, origin);
        }
        int i = 0;
        while (true) {
            K8[] k8Arr = e8.d;
            if (i >= k8Arr.length) {
                return e8;
            }
            if (k8Arr[i].a() != e9.d[i].a()) {
                throw new C1367Tb("Incompatible types in stack position " + i + ": " + e8.d[i] + " and " + e9.d[i], origin);
            }
            i++;
        }
    }

    public final H8 a(H8 h8) {
        L8 g8 = new G8(this.a, h8.d);
        if (g8.b >= 4) {
            E8 e8 = new E8(g8.b() + 1, g8.d());
            g8.a(e8);
            g8 = e8;
        }
        this.a = g8;
        return g8.c();
    }

    public final H8 a(C0767v2 c0767v2) {
        L8 g8 = new G8(this.a, new J8(c0767v2));
        if (g8.b >= 4) {
            E8 e8 = new E8(g8.b() + 1, g8.d());
            g8.a(e8);
            g8 = e8;
        }
        this.a = g8;
        return g8.c();
    }

    public final H8 a(EnumC1812d20 enumC1812d20) {
        L8 g8 = new G8(this.a, new I8(enumC1812d20));
        if (g8.b >= 4) {
            E8 e8 = new E8(g8.b() + 1, g8.d());
            g8.a(e8);
            g8 = e8;
        }
        this.a = g8;
        return g8.c();
    }

    public final H8 a(int i) {
        return this.a.a(i);
    }

    public final H8 a(int i, C0767v2 c0767v2) {
        L8 m8 = new M8(this.a, i, new J8(c0767v2));
        if (m8.b >= 4) {
            E8 e8 = new E8(m8.b() + 1, m8.d());
            m8.a(e8);
            m8 = e8;
        }
        this.a = m8;
        return m8.a(i);
    }

    public final H8 a(int i, H8 h8) {
        L8 m8 = new M8(this.a, i, h8.d);
        if (m8.b >= 4) {
            E8 e8 = new E8(m8.b() + 1, m8.d());
            m8.a(e8);
            m8 = e8;
        }
        this.a = m8;
        return m8.a(i);
    }

    public final AbstractC1915eM a() {
        return this.b;
    }
}
