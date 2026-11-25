package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3970u0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Sk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1350Sk {
    static final /* synthetic */ boolean k = true;
    private final C0680j a;
    private final C2522mD b;
    private final String c;
    private final boolean d;
    private final AbstractC2178hm e;
    private final AbstractC1048Hm f;
    private final AbstractC1048Hm g;
    private final AbstractC2178hm h;
    private final Set i = OU.c();
    private final boolean j;

    public C1350Sk(C0680j c0680j, C2522mD c2522mD, String str, boolean z, String str2, C1297Qk c1297Qk) {
        this.a = c0680j;
        this.b = c2522mD;
        this.c = str;
        this.d = z;
        this.e = b(c1297Qk.s());
        this.g = a(c1297Qk.h());
        this.f = a(c1297Qk.k());
        this.h = a(c1297Qk.r());
        this.j = str2.startsWith("com.tools.android:desugar_jdk_libs:1.2.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0767v2 c0767v2) {
        d(c0767v2);
        C0759u2 c0759u2A = a(c0767v2, this.f);
        if (c0759u2A != null) {
            this.b.c(c0767v2);
            this.b.b(c0767v2, a(c0767v2));
            this.i.add(c0759u2A);
        }
        c(c0767v2);
    }

    private void c(Map map) {
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Sk$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0684j2) obj, (C0684j2[]) obj2);
            }
        });
    }

    private void d(Map map) {
        final C2522mD c2522mD = this.b;
        Objects.requireNonNull(c2522mD);
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Sk$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                c2522mD.b((C0767v2) obj, (C0767v2) obj2);
            }
        });
    }

    public final void a(C1297Qk c1297Qk, BiConsumer biConsumer) {
        a();
        Iterator<C0767v2> it = c1297Qk.p().values().iterator();
        while (it.hasNext()) {
            d(it.next());
        }
        Iterator it2 = c1297Qk.n().values().iterator();
        while (it2.hasNext()) {
            d((C0767v2) it2.next());
        }
        Iterator<C0767v2> it3 = c1297Qk.e().values().iterator();
        while (it3.hasNext()) {
            d(it3.next());
        }
        Iterator it4 = c1297Qk.o().values().iterator();
        while (it4.hasNext()) {
            d(((C0684j2) it4.next()).B0());
        }
        Iterator it5 = c1297Qk.m().values().iterator();
        while (it5.hasNext()) {
            d(((C0684j2) it5.next()).B0());
        }
        d(c1297Qk.i());
        for (C0684j2 c0684j2 : c1297Qk.n().keySet()) {
            this.b.b(c0684j2.e, a(c0684j2.e));
        }
        for (C0684j2 c0684j3 : c1297Qk.m().keySet()) {
            this.b.b(c0684j3.e, a(c0684j3.e));
        }
        c(c1297Qk.c());
        a(biConsumer);
    }

    private void c(final C0767v2 c0767v2) {
        final C0759u2 c0759u2A = a(c0767v2, this.h.keySet());
        if (c0759u2A == null) {
            return;
        }
        ((Map) this.h.get(c0759u2A)).forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Sk$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0767v2, c0759u2A, (C0759u2) obj, (C0759u2) obj2);
            }
        });
        this.i.add(c0759u2A);
    }

    private void d(C0767v2 c0767v2) {
        C0767v2 c0767v2C;
        C0759u2 c0759u2A = a(c0767v2, this.e.keySet());
        if (c0759u2A == null) {
            c0767v2C = null;
        } else {
            C0759u2 c0759u2A2 = c0767v2.e.a(c0759u2A, (C0759u2) this.e.get(c0759u2A), this.a.a());
            this.i.add(c0759u2A);
            c0767v2C = this.a.a().c(c0759u2A2);
        }
        if (c0767v2C == null || a(c0767v2, this.g) != null) {
            return;
        }
        this.b.c(c0767v2, c0767v2C);
    }

    private AbstractC2178hm b(Map map) {
        C1946em c1946emE = AbstractC2178hm.e();
        for (String str : map.keySet()) {
            c1946emE.a(a(str), a((String) map.get(str)));
        }
        return c1946emE.a();
    }

    private void a(BiConsumer biConsumer) {
        final Set<C0759u2> setC = OU.c();
        setC.addAll(this.e.keySet());
        setC.addAll(this.f);
        setC.addAll(this.h.keySet());
        setC.removeAll(this.i);
        if (this.j) {
            final ArrayList arrayList = new ArrayList();
            for (final C0759u2 c0759u2 : setC) {
                this.e.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Sk$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        C1350Sk.a(setC, c0759u2, arrayList, (C0759u2) obj, (C0759u2) obj2);
                    }
                });
            }
            arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Sk$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    setC.remove((C0759u2) obj);
                }
            });
        }
        biConsumer.accept("The following prefixes do not match any type: ", setC);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Set set, C0759u2 c0759u2, List list, C0759u2 c0759u3, C0759u2 c0759u4) {
        if (set.contains(c0759u3)) {
            return;
        }
        c0759u3.getClass();
        if (c0759u3.a(c0759u2.e) || c0759u2.a(c0759u3.e)) {
            list.add(c0759u2);
        }
    }

    public final C0767v2 a(C0767v2 c0767v2) {
        String strN = C3970u0.n(this.c);
        String string = c0767v2.toString();
        return this.a.a().e(C3970u0.H(strN + string.substring(string.indexOf(46) + 1)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0684j2 c0684j2, C0684j2[] c0684j2Arr) {
        for (C0684j2 c0684j3 : c0684j2Arr) {
            if (c0684j3 != null) {
                d(c0684j3.B0());
            }
        }
    }

    private void a() {
        this.a.b().a(new Consumer() { // from class: com.android.tools.r8.internal.Sk$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C0767v2) obj);
            }
        });
        if (this.d) {
            this.a.b().b(new Consumer() { // from class: com.android.tools.r8.internal.Sk$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((C0767v2) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2 c0767v2, C0759u2 c0759u2, C0759u2 c0759u3, C0759u2 c0759u4) {
        C0759u2 c0759u2A = c0767v2.e.a(c0759u2, c0759u3, this.a.a());
        C0759u2 c0759u2A2 = c0767v2.e.a(c0759u2, c0759u4, this.a.a());
        C0767v2 c0767v2C = this.a.a().c(c0759u2A);
        if (!k && this.a.d(c0767v2C) != null) {
            throw new AssertionError("Trying to rewrite a type " + c0767v2C + " with different prefix that already exists.");
        }
        this.b.c(c0767v2C, this.a.a().c(c0759u2A2));
    }

    private C0759u2 a(C0767v2 c0767v2, Set set) {
        C0759u2 c0759u2A = c0767v2.e.a(this.a.a());
        Iterator it = set.iterator();
        while (it.hasNext()) {
            C0759u2 c0759u2 = (C0759u2) it.next();
            c0759u2A.getClass();
            if (c0759u2A.a(c0759u2.e)) {
                return c0759u2;
            }
        }
        return null;
    }

    private AbstractC2178hm a(Map map) {
        C1946em c1946emE = AbstractC2178hm.e();
        for (String str : map.keySet()) {
            c1946emE.a(a(str), b((Map) map.get(str)));
        }
        return c1946emE.a();
    }

    private AbstractC1048Hm a(Set set) {
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            c0862BmG.a(a((String) it.next()));
        }
        return c0862BmG.a();
    }

    private C0759u2 a(String str) {
        return this.a.a().c("L" + C3970u0.g(str));
    }
}
