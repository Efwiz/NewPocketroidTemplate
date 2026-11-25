package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.b4$$ExternalSyntheticLambda3;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.C3979v2;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1747cB {
    private final Map a;
    private final Map b;
    private final Map c;
    private final Map d;
    private final Map e;
    private final List f;
    private final Set g;
    private final Set h;

    C1747cB(AbstractC2178hm abstractC2178hm, AbstractC2178hm abstractC2178hm2, AbstractC2178hm abstractC2178hm3, AbstractC2178hm abstractC2178hm4, AbstractC2178hm abstractC2178hm5, AbstractC1639am abstractC1639am, AbstractC1048Hm abstractC1048Hm, AbstractC1048Hm abstractC1048Hm2) {
        this.a = abstractC2178hm;
        this.b = abstractC2178hm2;
        this.c = abstractC2178hm3;
        this.d = abstractC2178hm4;
        this.e = abstractC2178hm5;
        this.f = abstractC1639am;
        this.g = abstractC1048Hm;
        this.h = abstractC1048Hm2;
    }

    public static a a(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, Origin origin) {
        return new a(c0722p1, o2, origin, new HashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new ArrayList(), OU.c(), OU.c());
    }

    public final a b(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, Origin origin) {
        return new a(c0722p1, o2, origin, this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
    }

    public final Set c() {
        return this.g;
    }

    public final List d() {
        return this.f;
    }

    public final Map e() {
        return this.b;
    }

    public final Map f() {
        return this.c;
    }

    public final Map g() {
        return this.a;
    }

    public final Set h() {
        return this.h;
    }

    public final boolean i() {
        return this.a.isEmpty() && this.b.isEmpty() && this.c.isEmpty();
    }

    public final Map b() {
        return this.e;
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.cB$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean l = true;
        private final C0722p1 a;
        private final com.android.tools.r8.utils.O2 b;
        private final Origin c;
        private final HashMap d;
        private final IdentityHashMap e;
        private final IdentityHashMap f;
        private final IdentityHashMap g;
        private final IdentityHashMap h;
        private final ArrayList i;
        private final Set j;
        private final Set k;

        a(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, Origin origin, Map map, Map map2, Map map3, Map map4, Map map5, List list, Set set, Set set2) {
            this.a = c0722p1;
            this.b = o2;
            this.c = origin;
            this.d = new HashMap(map);
            this.e = new IdentityHashMap(map2);
            this.f = new IdentityHashMap(map3);
            this.g = new IdentityHashMap(map4);
            this.h = new IdentityHashMap(map5);
            this.i = new ArrayList(list);
            Set setC = OU.c();
            this.j = setC;
            setC.addAll(set);
            Set setC2 = OU.c();
            this.k = setC2;
            setC2.addAll(set2);
        }

        private void a(Map map, Object obj, Object obj2, String str) {
            if (map.containsKey(obj)) {
                this.b.a(new StringDiagnostic("Invalid desugared library configuration.  Duplicate assignment of key: '" + obj + "' in sections for '" + str + FormatHelper.QUOTE, this.c));
                throw null;
            }
            map.put(obj, obj2);
        }

        private static int f(String str, String str2) {
            int iLastIndexOf = str.lastIndexOf(35);
            if (iLastIndexOf <= 0 || iLastIndexOf >= str.length() - 1) {
                throw new C1367Tb("Invalid " + str2 + " specification (# position) in " + str + ".");
            }
            return iLastIndexOf;
        }

        public final void b(String str, String str2) {
            a(this.h, d(str), d(str2), "custom_conversion");
        }

        public final void c(String str, String str2) {
            a(this.e, d(str), d(str2), "emulate_interface");
        }

        public final void d(String str, String str2) {
            int iF = f(str, "retarget core library member");
            C0759u2 c0759u2C = this.a.c(str.substring(iF + 1));
            this.f.putIfAbsent(c0759u2C, new IdentityHashMap());
            Map map = (Map) this.f.get(c0759u2C);
            C0767v2 c0767v2D = d(str.substring(0, iF));
            C0767v2 c0767v2D2 = d(str2);
            if (!l && map.containsKey(c0767v2D)) {
                throw new AssertionError();
            }
            a(map, c0767v2D, c0767v2D2, "retarget_lib_member");
        }

        public a e(String str, String str2) {
            a(this.d, str, str2, "rewrite_prefix");
            return this;
        }

        public final void b(String str) {
            int iF = f(str, "don't rewrite");
            this.i.add(new C3979v2(d(str.substring(0, iF)), this.a.c(str.substring(iF + 1))));
        }

        public final void c(String str) {
            this.k.add(d(str));
        }

        private C0767v2 d(String str) {
            return this.a.e(C3970u0.H(str));
        }

        private void b() {
            NU nuB = OU.b(this.h.keySet(), this.k);
            if (nuB.isEmpty()) {
                return;
            }
            this.b.a(new StringDiagnostic("Invalid desugared library configuration. Duplicate types in custom conversions and wrapper conversions: " + String.join(", ", (Iterable<? extends CharSequence>) nuB.stream().map(new b4$$ExternalSyntheticLambda3()).collect(Collectors.toSet())), this.c));
            throw null;
        }

        public a a(String str, String str2) {
            a(this.g, d(str), d(str2), "backport");
            return this;
        }

        public final void a(String str) {
            this.j.add(d(str));
        }

        public C1747cB a() {
            b();
            return new C1747cB(AbstractC2178hm.a(this.d), AbstractC2178hm.a(this.e), AbstractC2178hm.a(this.f), AbstractC2178hm.a(this.g), AbstractC2178hm.a(this.h), AbstractC1639am.a(this.i), AbstractC1048Hm.a(this.j), AbstractC1048Hm.a(this.k));
        }
    }

    public final Map a() {
        return this.d;
    }
}
