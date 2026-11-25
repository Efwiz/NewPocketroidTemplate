package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1297Qk {
    private final Map a;
    private final Set b;
    private final Set c;
    private final Map d;
    private final Map e;
    private final Map f;
    private final Map g;
    private final Map h;
    private final Map i;
    private final Map j;
    private final Map k;
    private final Map l;
    private final Map m;
    private final Map n;
    private final Set o;
    private final Set p;
    private final Map q;
    private final Set r;
    private final Map s;
    private final Map t;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.Qk$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean w = true;
        private final com.android.tools.r8.utils.O2 a;
        private final Origin b;
        private final HashMap c;
        private final HashSet d;
        private final HashSet e;
        private final HashMap f;
        private final IdentityHashMap g;
        private final IdentityHashMap h;
        private final IdentityHashMap i;
        private final IdentityHashMap j;
        private final IdentityHashMap k;
        private final IdentityHashMap l;
        private final IdentityHashMap m;
        private final IdentityHashMap n;
        private final IdentityHashMap o;
        private final IdentityHashMap p;
        private final Set q;
        private final Set r;
        private final IdentityHashMap s;
        private final Set t;
        private final IdentityHashMap u;
        private final IdentityHashMap v;

        a(com.android.tools.r8.utils.O2 o2, Origin origin, Map map, Set set, Set set2, Map map2, Map map3, Map map4, Map map5, Map map6, Map map7, Map map8, Map map9, Map map10, Map map11, Map map12, Set set3, Set set4, Map map13, Set set5, Map map14, Map map15) {
            this.a = o2;
            this.b = origin;
            this.c = new HashMap(map);
            this.d = OU.a((Iterable) set);
            this.e = OU.a((Iterable) set2);
            this.f = new HashMap(map2);
            this.g = new IdentityHashMap(map3);
            this.h = new IdentityHashMap(map4);
            this.i = new IdentityHashMap(map5);
            this.j = new IdentityHashMap(map6);
            this.k = new IdentityHashMap(map7);
            this.l = new IdentityHashMap(map8);
            this.m = new IdentityHashMap(map9);
            this.n = new IdentityHashMap(map10);
            this.o = new IdentityHashMap(map11);
            this.p = new IdentityHashMap(map12);
            Set setC = OU.c();
            this.q = setC;
            setC.addAll(set3);
            Set setC2 = OU.c();
            this.r = setC2;
            setC2.addAll(set4);
            this.s = new IdentityHashMap(map13);
            Set setC3 = OU.c();
            this.t = setC3;
            setC3.addAll(set5);
            this.u = new IdentityHashMap(map14);
            this.v = new IdentityHashMap(map15);
        }

        private void a(Map map, Object obj, Object obj2, String str) {
            if (!map.containsKey(obj) || map.get(obj).equals(obj2)) {
                map.put(obj, obj2);
            } else {
                this.a.a(new StringDiagnostic("Invalid desugared library configuration.  Duplicate assignment of key: '" + obj + "' in sections for '" + str + FormatHelper.QUOTE, this.b));
                throw null;
            }
        }

        public final void b(String str) {
            this.d.add(str);
        }

        public a c(String str) {
            this.e.add(str);
            return this;
        }

        public final a b(C0767v2 c0767v2, C0767v2 c0767v3) {
            a(this.g, c0767v2, c0767v3, "emulate_interface");
            return this;
        }

        public final a c(C0684j2 c0684j2, C0767v2 c0767v2) {
            a(this.j, c0684j2, c0767v2, "retarget_method");
            return this;
        }

        public final a b(C0767v2 c0767v2) {
            return a(c0767v2, Collections.emptySet());
        }

        public final void c(C0684j2 c0684j2, C0684j2 c0684j3) {
            a(this.l, c0684j2, c0684j3, "retarget_method");
        }

        public final a b(C0684j2 c0684j2, C0767v2 c0767v2) {
            a(this.k, c0684j2, c0767v2, "retarget_method_with_emulated_dispatch");
            return this;
        }

        public final a c(C0767v2 c0767v2, C0767v2 c0767v3) {
            a(this.o, c0767v2, c0767v3, "backport");
            return this;
        }

        public final void b(C0684j2 c0684j2, C0684j2 c0684j3) {
            a(this.m, c0684j2, c0684j3, "retarget_method_with_emulated_dispatch");
        }

        public final void b(C0684j2 c0684j2) {
            this.t.add(c0684j2);
        }

        public a a(String str, String str2) {
            a(this.c, str, str2, "rewrite_prefix");
            return this;
        }

        public final void a(String str, String str2, String str3) {
            a((Map) this.f.computeIfAbsent(str, new Function() { // from class: com.android.tools.r8.internal.Qk$a$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C1297Qk.a.a((String) obj);
                }
            }), str2, str3, "rewrite_derived_prefix");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ Map a(String str) {
            return new HashMap();
        }

        public final a a(C0767v2 c0767v2, C0767v2 c0767v3) {
            a(this.p, c0767v2, c0767v3, "custom_conversion");
            return this;
        }

        public final a a(C0767v2 c0767v2, Set set) {
            this.s.put(c0767v2, set);
            return this;
        }

        public final void a(C0684j2 c0684j2, C0767v2 c0767v2) {
            a(this.i, c0684j2, c0767v2, "covariant_retarget_method");
        }

        public a a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
            a(this.h, y0, y1, "retarget_static_field");
            return this;
        }

        public final void a(final C0684j2 c0684j2, int i, C0684j2 c0684j3) {
            C0684j2[] c0684j2Arr = (C0684j2[]) this.n.computeIfAbsent(c0684j2, new Function() { // from class: com.android.tools.r8.internal.Qk$a$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C1297Qk.a.a(c0684j2, (C0684j2) obj);
                }
            });
            if (i == -1) {
                i = c0684j2.F0();
            }
            if (!w && c0684j2Arr[i] != null) {
                throw new AssertionError();
            }
            c0684j2Arr[i] = c0684j3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ C0684j2[] a(C0684j2 c0684j2, C0684j2 c0684j3) {
            return new C0684j2[c0684j2.F0() + 1];
        }

        public final a a(C0684j2 c0684j2) {
            this.q.add(c0684j2);
            return this;
        }

        public final a a(C0767v2 c0767v2) {
            this.r.add(c0767v2);
            return this;
        }

        public final void a(C0684j2 c0684j2, C0725p4 c0725p4) {
            this.u.put(c0684j2, c0725p4);
        }

        public a a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Q2 q2) {
            this.v.put(y0, q2);
            return this;
        }

        public C1297Qk a() {
            return new C1297Qk(AbstractC2178hm.a(this.c), AbstractC1048Hm.a(this.d), AbstractC1048Hm.a(this.e), AbstractC2178hm.a(this.f), AbstractC2178hm.a(this.g), AbstractC2178hm.a(this.h), AbstractC2178hm.a(this.i), AbstractC2178hm.a(this.j), AbstractC2178hm.a(this.k), AbstractC2178hm.a(this.l), AbstractC2178hm.a(this.m), AbstractC2178hm.a(this.n), AbstractC2178hm.a(this.o), AbstractC2178hm.a(this.p), AbstractC1048Hm.a(this.q), AbstractC1048Hm.a(this.r), AbstractC2178hm.a(this.s), AbstractC1048Hm.a(this.t), AbstractC2178hm.a(this.u), AbstractC2178hm.a(this.v));
        }
    }

    C1297Qk(AbstractC2178hm abstractC2178hm, AbstractC1048Hm abstractC1048Hm, AbstractC1048Hm abstractC1048Hm2, AbstractC2178hm abstractC2178hm2, AbstractC2178hm abstractC2178hm3, AbstractC2178hm abstractC2178hm4, AbstractC2178hm abstractC2178hm5, AbstractC2178hm abstractC2178hm6, AbstractC2178hm abstractC2178hm7, AbstractC2178hm abstractC2178hm8, AbstractC2178hm abstractC2178hm9, AbstractC2178hm abstractC2178hm10, AbstractC2178hm abstractC2178hm11, AbstractC2178hm abstractC2178hm12, AbstractC1048Hm abstractC1048Hm3, AbstractC1048Hm abstractC1048Hm4, AbstractC2178hm abstractC2178hm13, AbstractC1048Hm abstractC1048Hm5, AbstractC2178hm abstractC2178hm14, AbstractC2178hm abstractC2178hm15) {
        this.a = abstractC2178hm;
        this.b = abstractC1048Hm;
        this.c = abstractC1048Hm2;
        this.d = abstractC2178hm2;
        this.e = abstractC2178hm3;
        this.f = abstractC2178hm4;
        this.g = abstractC2178hm5;
        this.h = abstractC2178hm6;
        this.i = abstractC2178hm7;
        this.j = abstractC2178hm8;
        this.k = abstractC2178hm9;
        this.l = abstractC2178hm10;
        this.m = abstractC2178hm11;
        this.n = abstractC2178hm12;
        this.o = abstractC1048Hm3;
        this.p = abstractC1048Hm4;
        this.q = abstractC2178hm13;
        this.r = abstractC1048Hm5;
        this.s = abstractC2178hm14;
        this.t = abstractC2178hm15;
    }

    public static a a(com.android.tools.r8.utils.O2 o2, Origin origin) {
        return new a(o2, origin, new HashMap(), OU.c(), OU.c(), new HashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), OU.c(), OU.c(), new IdentityHashMap(), OU.c(), new IdentityHashMap(), new IdentityHashMap());
    }

    public final a b(com.android.tools.r8.utils.O2 o2, Origin origin) {
        return new a(o2, origin, this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t);
    }

    public final Map c() {
        return this.l;
    }

    public final Map d() {
        return this.g;
    }

    public Map<C0767v2, C0767v2> e() {
        return this.n;
    }

    public Set<C0767v2> f() {
        return this.p;
    }

    public Set<C0684j2> g() {
        return this.o;
    }

    public final Set h() {
        return this.b;
    }

    public Map<C0767v2, C0767v2> i() {
        return this.e;
    }

    public Map<C0767v2, C0767v2> j() {
        return this.m;
    }

    public final Set k() {
        return this.c;
    }

    public final Set l() {
        return this.r;
    }

    public final Map m() {
        return this.k;
    }

    public final Map n() {
        return this.i;
    }

    public final Map o() {
        return this.j;
    }

    public Map<C0684j2, C0767v2> p() {
        return this.h;
    }

    public final Map q() {
        return this.f;
    }

    public Map<String, Map<String, String>> r() {
        return this.d;
    }

    public Map<String, String> s() {
        return this.a;
    }

    public Map<C0767v2, Set<C0684j2>> t() {
        return this.q;
    }

    public final boolean u() {
        return this.a.isEmpty() && this.d.isEmpty() && this.c.isEmpty() && this.e.isEmpty() && this.g.isEmpty() && this.h.isEmpty() && this.i.isEmpty() && this.f.isEmpty();
    }

    public Map<C0684j2, C0725p4> b() {
        return this.s;
    }

    public final Map a() {
        return this.t;
    }
}
