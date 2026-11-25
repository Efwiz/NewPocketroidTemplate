package com.android.tools.r8.dex;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.internal.C1525Yd;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.y3;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class r0 {
    static final /* synthetic */ boolean i = true;
    private final int a;
    public final q0 b;
    private final l0 c;
    private final FeatureSplit d;
    private final AbstractC3135uW e;
    private final C0759u2 f;
    private C1525Yd g;
    private C0645e5 h;

    r0(C0702m c0702m) {
        this(0, c0702m, null, null, AbstractC3135uW.b());
    }

    public final void a(C1525Yd c1525Yd) {
        if (!i && this.g != null) {
            throw new AssertionError();
        }
        this.g = c1525Yd;
    }

    public final void b(C0759u2 c0759u2) {
        this.c.a(c0759u2);
        this.c.a();
    }

    public final void c() {
        this.c.a();
    }

    public final HashSet d() {
        HashSet hashSet = new HashSet();
        Iterator it = this.b.a.iterator();
        while (it.hasNext()) {
            boolean zAdd = hashSet.add(((C0729q2) it.next()).d.e.toString());
            if (!i && !zAdd) {
                throw new AssertionError();
            }
        }
        return hashSet;
    }

    public final C1525Yd e() {
        if (i || this.g != null) {
            return this.g;
        }
        throw new AssertionError();
    }

    public final FeatureSplit f() {
        return this.d;
    }

    public int g() {
        return this.a;
    }

    public final int h() {
        l0 l0Var = this.c;
        return l0Var.b.e.size() + l0Var.e.size();
    }

    public final int i() {
        l0 l0Var = this.c;
        return l0Var.b.d.size() + l0Var.f.size();
    }

    public final C0645e5 j() {
        if (i || this.h != null) {
            return this.h;
        }
        throw new AssertionError();
    }

    public final String k() {
        C0759u2 c0759u2 = this.f;
        if (c0759u2 == null) {
            return null;
        }
        return c0759u2.toString();
    }

    public final AbstractC3135uW l() {
        return this.e;
    }

    public final boolean m() {
        return this.b.a.isEmpty();
    }

    final boolean n() {
        return a(65536);
    }

    r0(int i2, C0702m c0702m, FeatureSplit featureSplit) {
        this(i2, c0702m, null, featureSplit, AbstractC3135uW.b());
    }

    private r0(int i2, C0702m c0702m, C0729q2 c0729q2, FeatureSplit featureSplit, AbstractC3135uW abstractC3135uW) {
        this.h = null;
        this.a = i2;
        q0 q0Var = new q0(c0702m);
        this.b = q0Var;
        this.c = new l0(q0Var, c0702m);
        this.d = featureSplit;
        this.e = abstractC3135uW;
        if (c0729q2 == null) {
            this.f = null;
            return;
        }
        C0767v2 type = c0729q2.getType();
        this.f = c0702m.z().b(type);
        AbstractC1639am abstractC1639amB = c0702m.D().b(type);
        if (abstractC1639amB.size() == 1) {
            c0702m.z().b((C0767v2) abstractC1639amB.iterator().next());
        } else if (!i && !abstractC1639amB.isEmpty()) {
            throw new AssertionError();
        }
    }

    public final Set b() {
        return this.b.a;
    }

    public static String a(List<String> list) {
        Iterator<String> it = list.iterator();
        String next = it.next();
        if (next.toLowerCase().endsWith(".dex")) {
            String strA = AbstractC0402a.a(next, 4, 0);
            int i2 = 2;
            while (it.hasNext()) {
                String next2 = it.next();
                if (next2.toLowerCase().endsWith(".dex")) {
                    if (next2.startsWith(strA)) {
                        int i3 = i2 + 1;
                        if (Integer.parseInt(next2.substring(strA.length(), next2.length() - 4)) != i2) {
                            throw new RuntimeException("DEX files are not numbered consecutively.");
                        }
                        i2 = i3;
                    } else {
                        throw new RuntimeException("Input filenames lack common prefix.");
                    }
                } else {
                    throw new RuntimeException("Illegal suffix for dex file: `" + next + "`.");
                }
            }
            return strA;
        }
        throw new RuntimeException("Illegal suffix for dex file: `" + next + "`.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IdentityHashMap a(Collection collection, final AbstractC1476Wj abstractC1476Wj, final C3605b c3605b) {
        final IdentityHashMap identityHashMap = new IdentityHashMap(collection.size());
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.dex.r0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                r0.a(abstractC1476Wj, identityHashMap, c3605b, (C0729q2) obj);
            }
        });
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC1476Wj abstractC1476Wj, Map map, C3605b c3605b, C0729q2 c0729q2) {
        map.put(c0729q2, C3970u0.a(abstractC1476Wj.a(c0729q2.getType()).b1(), c3605b));
    }

    public final void a(C0702m c0702m, int i2, y3 y3Var, C0645e5 c0645e5) {
        boolean z = i;
        if (!z) {
            l0 l0Var = this.c;
            if (!l0Var.d.isEmpty() || !l0Var.e.isEmpty() || !l0Var.f.isEmpty() || !l0Var.h.isEmpty() || !l0Var.g.isEmpty() || !l0Var.i.isEmpty()) {
                throw new AssertionError();
            }
        }
        if (!z && this.h != null) {
            throw new AssertionError();
        }
        C2363kB c2363kB = this.c.c;
        q0 q0Var = this.b;
        this.h = new C0645e5(c0702m, c0645e5, c2363kB, q0Var.a, q0Var.b, q0Var.c, q0Var.d, q0Var.e, q0Var.f, q0Var.g, q0Var.h, i2, y3Var);
    }

    final void a(C0729q2 c0729q2) {
        l0 l0Var = this.c;
        c0729q2.a(l0Var.a, l0Var, l0Var.c);
        l0Var.m = null;
        l0Var.l.getClass();
        l0Var.l.getClass();
    }

    public final boolean a(int i2) {
        l0 l0Var = this.c;
        if (l0Var.b.d.size() + l0Var.f.size() <= i2) {
            l0 l0Var2 = this.c;
            if (l0Var2.b.e.size() + l0Var2.e.size() <= i2) {
                return false;
            }
        }
        return true;
    }

    public final void a() {
        l0 l0Var = this.c;
        l0Var.d.clear();
        l0Var.e.clear();
        l0Var.f.clear();
        l0Var.h.clear();
        l0Var.g.clear();
        l0Var.i.clear();
        l0Var.l.getClass();
    }

    public final boolean a(C0759u2 c0759u2) {
        return this.b.f.contains(c0759u2);
    }

    public final boolean a(C0767v2 c0767v2) {
        return this.b.c.contains(c0767v2);
    }
}
