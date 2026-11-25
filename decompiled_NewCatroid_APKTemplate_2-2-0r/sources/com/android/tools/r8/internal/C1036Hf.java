package com.android.tools.r8.internal;

import com.android.tools.r8.CompilationMode;
import com.huawei.hms.framework.common.ContainerUtils;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Hf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1036Hf {
    private final com.android.tools.r8.dex.U.a a;
    private final com.android.tools.r8.dex.U.b b;
    private final CompilationMode c;
    private final int d;
    private final boolean e;
    private final int f;
    private final com.android.tools.r8.utils.A1.g g;
    private final Optional h;
    private final Optional i;
    private final Optional j;
    private final Optional k;
    private final Optional l;
    private final InterfaceC1206Ne m;
    private final C3451yh n;
    private final com.android.tools.r8.shaking.L2 o;
    private final List p;
    private final Collection q;
    private final Collection r;
    private final boolean s;
    private final boolean t;
    private final Map u;
    private final String v;
    private final boolean w;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.Hf$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean w = true;
        private com.android.tools.r8.dex.U.b b;
        private CompilationMode c;
        private int d;
        private boolean e;
        private int f;
        private com.android.tools.r8.utils.A1.g g;
        private InterfaceC1206Ne m;
        private C3451yh n;
        private com.android.tools.r8.shaking.L2 o;
        private List p;
        private Collection q;
        private Collection r;
        private com.android.tools.r8.dex.U.a a = com.android.tools.r8.dex.U.a.DEX;
        private Optional h = Optional.empty();
        private Optional i = Optional.empty();
        private Optional j = Optional.empty();
        private Optional k = Optional.empty();
        private Optional l = Optional.empty();
        private boolean s = false;
        private boolean t = false;
        private String u = null;
        private HashMap v = new HashMap();

        public a a(com.android.tools.r8.dex.U.a aVar) {
            this.a = aVar;
            return this;
        }

        public final a b(int i) {
            this.f = i;
            return this;
        }

        public final a c(boolean z) {
            this.h = Optional.of(Boolean.valueOf(z));
            return this;
        }

        public final a d(boolean z) {
            this.k = Optional.of(Boolean.valueOf(z));
            return this;
        }

        public final a e(boolean z) {
            this.e = z;
            return this;
        }

        public final a f(boolean z) {
            this.j = Optional.of(Boolean.valueOf(z));
            return this;
        }

        public final a a(String str) {
            this.u = str;
            return this;
        }

        public final a b(boolean z) {
            this.l = Optional.of(Boolean.valueOf(z));
            return this;
        }

        public final void c(List list) {
            this.r = list;
        }

        public final void d() {
            this.t = true;
        }

        public final a a(CompilationMode compilationMode) {
            this.c = compilationMode;
            return this;
        }

        public final a b(List list) {
            this.p = list;
            return this;
        }

        public final a c() {
            b().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Hf$a$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a((String) obj, (String) obj2);
                }
            });
            return this;
        }

        public static Map<String, String> b() {
            final TreeMap treeMap = new TreeMap();
            System.getProperties().stringPropertyNames().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Hf$a$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1036Hf.a.a(treeMap, (String) obj);
                }
            });
            return treeMap;
        }

        public final a a(int i) {
            this.d = i;
            return this;
        }

        public final a a(InterfaceC1206Ne interfaceC1206Ne) {
            this.m = interfaceC1206Ne;
            return this;
        }

        public final a a(com.android.tools.r8.utils.A1.g gVar) {
            this.g = gVar;
            return this;
        }

        public final a a(Optional optional) {
            this.i = optional;
            return this;
        }

        public final a a(C3451yh c3451yh) {
            this.n = c3451yh;
            return this;
        }

        public final a a(com.android.tools.r8.shaking.L2 l2) {
            this.o = l2;
            return this;
        }

        public final a a(List list) {
            this.q = list;
            return this;
        }

        public final a a(boolean z) {
            this.s = z;
            return this;
        }

        public final a a(String str, String str2) {
            this.v.put(str, str2);
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(Map map, String str) {
            if (str.startsWith("com.android.tools.r8.")) {
                map.put(str, System.getProperty(str));
            }
        }

        public C1036Hf a() {
            boolean z = w;
            if (!z && this.b == null) {
                throw new AssertionError();
            }
            if (z || this.b == com.android.tools.r8.dex.U.b.TraceReferences || this.a != null) {
                return new C1036Hf(this.a, this.b, this.c, this.d, this.m, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.v, this.u);
            }
            throw new AssertionError();
        }
    }

    private C1036Hf(com.android.tools.r8.dex.U.a aVar, com.android.tools.r8.dex.U.b bVar, CompilationMode compilationMode, int i, InterfaceC1206Ne interfaceC1206Ne, boolean z, int i2, com.android.tools.r8.utils.A1.g gVar, Optional optional, Optional optional2, Optional optional3, Optional optional4, Optional optional5, C3451yh c3451yh, com.android.tools.r8.shaking.L2 l2, List list, Collection collection, Collection collection2, boolean z2, boolean z3, HashMap map, String str) {
        this.a = aVar;
        this.b = bVar;
        this.c = compilationMode;
        this.d = i;
        this.m = interfaceC1206Ne;
        this.e = z;
        this.f = i2;
        this.g = gVar;
        this.h = optional;
        this.i = optional2;
        this.j = optional3;
        this.k = optional4;
        this.l = optional5;
        this.n = c3451yh;
        this.o = l2;
        this.p = list;
        this.q = collection;
        this.r = collection2;
        this.s = z2;
        this.t = z3;
        this.u = map;
        this.w = false;
        this.v = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, String str, String str2) {
        sb.append(str).append(ContainerUtils.KEY_VALUE_DELIMITER).append(str2).append("\n");
    }

    public static void b(final a aVar, String str) {
        com.android.tools.r8.utils.Y2.a(str, '\n', new Consumer() { // from class: com.android.tools.r8.internal.Hf$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1036Hf.a(aVar, (String) obj);
            }
        });
    }

    public final LinkedHashMap c() {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("tool", Objects.toString(this.b.name()));
        int i = this.f;
        if (i != -1) {
            linkedHashMap.put("thread-count", Objects.toString(Integer.valueOf(i)));
        }
        if (this.b != com.android.tools.r8.dex.U.b.TraceReferences) {
            linkedHashMap.put(Marker.BACKEND, Objects.toString(this.a.name()));
            linkedHashMap.put("mode", this.c == CompilationMode.DEBUG ? "debug" : "release");
            linkedHashMap.put(Marker.MIN_API, Objects.toString(Integer.valueOf(this.d)));
            linkedHashMap.put("optimize-multidex-for-linear-alloc", Objects.toString(Boolean.valueOf(this.e)));
            linkedHashMap.put("desugar-state", Objects.toString(this.g));
            linkedHashMap.put("enable-missing-library-api-modeling", Objects.toString(Boolean.valueOf(this.s)));
            boolean z = this.t;
            if (z) {
                linkedHashMap.put("android-platform-build", Objects.toString(Boolean.valueOf(z)));
            }
            a(linkedHashMap, "intermediate", this.h);
            a(linkedHashMap, "include-data-resources", this.i);
            a(linkedHashMap, "tree-shaking", this.j);
            a(linkedHashMap, "force-proguard-compatibility", this.l);
        } else {
            linkedHashMap.put("trace_references_consumer", Objects.toString(this.v));
        }
        a(linkedHashMap, "minification", this.k);
        ArrayList arrayList = new ArrayList(this.u.keySet());
        arrayList.sort(new Comparator() { // from class: com.android.tools.r8.internal.Hf$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((String) obj).compareTo((String) obj2);
            }
        });
        arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Hf$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(linkedHashMap, (String) obj);
            }
        });
        return linkedHashMap;
    }

    public final String d() {
        final StringBuilder sb = new StringBuilder();
        c().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Hf$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1036Hf.a(sb, (String) obj, (String) obj2);
            }
        });
        return sb.toString();
    }

    public final String e() {
        InterfaceC1206Ne interfaceC1206Ne = this.m;
        if (interfaceC1206Ne == null || interfaceC1206Ne.isEmpty()) {
            return null;
        }
        return this.m.b();
    }

    public final C3451yh f() {
        return this.n;
    }

    public final List g() {
        return this.p;
    }

    public int h() {
        return this.d;
    }

    public final String i() {
        com.android.tools.r8.shaking.L2 l2 = this.o;
        if (l2 == null) {
            return null;
        }
        return l2.q();
    }

    public final Collection j() {
        return this.r;
    }

    public final boolean k() {
        Collection collection = this.q;
        return (collection == null || collection.isEmpty()) ? false : true;
    }

    public final boolean l() {
        List list = this.p;
        return (list == null || list.isEmpty()) ? false : true;
    }

    public final boolean m() {
        Collection collection = this.r;
        return (collection == null || collection.isEmpty()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, String str) {
        map.put("system-property-" + str, Objects.toString(this.u.get(str)));
    }

    public final Collection b() {
        return this.q;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(a aVar, String str) {
        String strTrim = str.trim();
        if (strTrim.isEmpty()) {
            return;
        }
        int iIndexOf = strTrim.indexOf(61);
        if (iIndexOf >= 0) {
            byte b = 0;
            String strTrim2 = strTrim.substring(0, iIndexOf).trim();
            String strTrim3 = strTrim.substring(iIndexOf + 1).trim();
            strTrim2.getClass();
            strTrim2.hashCode();
            switch (strTrim2.hashCode()) {
                case -1714535540:
                    if (!strTrim2.equals("thread-count")) {
                        b = -1;
                    }
                    break;
                case -1572360679:
                    b = !strTrim2.equals("desugar-state") ? (byte) -1 : (byte) 1;
                    break;
                case -859717383:
                    b = !strTrim2.equals("intermediate") ? (byte) -1 : (byte) 2;
                    break;
                case -457962420:
                    b = !strTrim2.equals("minification") ? (byte) -1 : (byte) 3;
                    break;
                case -347208044:
                    b = !strTrim2.equals(Marker.BACKEND) ? (byte) -1 : (byte) 4;
                    break;
                case -145476656:
                    b = !strTrim2.equals("optimize-multidex-for-linear-alloc") ? (byte) -1 : (byte) 5;
                    break;
                case -82867811:
                    b = !strTrim2.equals("force-proguard-compatibility") ? (byte) -1 : (byte) 6;
                    break;
                case 3357091:
                    b = !strTrim2.equals("mode") ? (byte) -1 : (byte) 7;
                    break;
                case 3565976:
                    b = !strTrim2.equals("tool") ? (byte) -1 : (byte) 8;
                    break;
                case 223606663:
                    b = !strTrim2.equals("include-data-resources") ? (byte) -1 : (byte) 9;
                    break;
                case 1062738975:
                    b = !strTrim2.equals(Marker.MIN_API) ? (byte) -1 : (byte) 10;
                    break;
                case 1733466900:
                    b = !strTrim2.equals("tree-shaking") ? (byte) -1 : (byte) 11;
                    break;
                case 1763872211:
                    b = !strTrim2.equals("trace_references_consumer") ? (byte) -1 : (byte) 12;
                    break;
                default:
                    b = -1;
                    break;
            }
            switch (b) {
                case 0:
                    aVar.f = Integer.parseInt(strTrim3);
                    return;
                case 1:
                    aVar.g = (com.android.tools.r8.utils.A1.g) Enum.valueOf(com.android.tools.r8.utils.A1.g.class, strTrim3);
                    return;
                case 2:
                    aVar.c(Boolean.parseBoolean(strTrim3));
                    return;
                case 3:
                    aVar.d(Boolean.parseBoolean(strTrim3));
                    return;
                case 4:
                    aVar.a((com.android.tools.r8.dex.U.a) Enum.valueOf(com.android.tools.r8.dex.U.a.class, strTrim3));
                    return;
                case 5:
                    aVar.e = Boolean.parseBoolean(strTrim3);
                    return;
                case 6:
                    aVar.b(Boolean.parseBoolean(strTrim3));
                    return;
                case 7:
                    if (strTrim3.equals("debug")) {
                        aVar.c = CompilationMode.DEBUG;
                        return;
                    } else if (strTrim3.equals("release")) {
                        aVar.c = CompilationMode.RELEASE;
                        return;
                    } else {
                        a(strTrim2, strTrim3);
                        throw null;
                    }
                case 8:
                    aVar.b = (com.android.tools.r8.dex.U.b) Enum.valueOf(com.android.tools.r8.dex.U.b.class, strTrim3);
                    return;
                case 9:
                    aVar.i = Optional.of(Boolean.valueOf(Boolean.parseBoolean(strTrim3)));
                    return;
                case 10:
                    aVar.d = Integer.parseInt(strTrim3);
                    return;
                case 11:
                    aVar.f(Boolean.parseBoolean(strTrim3));
                    return;
                case 12:
                    aVar.u = strTrim3;
                    return;
                default:
                    if (strTrim2.startsWith("system-property-")) {
                        aVar.a(strTrim2.substring(16), strTrim3);
                        return;
                    } else {
                        a(strTrim2, strTrim3);
                        throw null;
                    }
            }
        }
        throw new RuntimeException("Invalid dump line. Expected = in line: '" + strTrim + FormatHelper.QUOTE);
    }

    private static void a(String str, String str2) {
        throw new RuntimeException("Unknown key value pair: " + str + " = " + str2);
    }

    private void a(final LinkedHashMap linkedHashMap, final String str, Optional optional) {
        optional.ifPresent(new Consumer() { // from class: com.android.tools.r8.internal.Hf$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(linkedHashMap, str, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, String str, Object obj) {
        map.put(str, Objects.toString(obj));
    }

    public final boolean a() {
        return this.w;
    }

    public static a a(com.android.tools.r8.dex.U.b bVar) {
        a aVar = new a();
        aVar.b = bVar;
        return aVar;
    }
}
