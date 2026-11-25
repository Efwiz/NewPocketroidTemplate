package com.android.tools.r8.naming;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1214Nl;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2211i9;
import com.android.tools.r8.internal.AbstractC2963s10;
import com.android.tools.r8.internal.C1187Ml;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.O2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.naming.b, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3605b {
    static final /* synthetic */ boolean g = true;
    private final AbstractC2178hm a;
    private com.android.tools.r8.utils.G b;
    private final ConcurrentHashMap c;
    private final LinkedHashSet d;
    private final Map e;
    private final List f;

    /* JADX INFO: renamed from: com.android.tools.r8.naming.b$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends AbstractC3665u0 {
        private boolean a = false;
        private final ArrayList b = new ArrayList();
        private final HashMap c = new HashMap();
        private final LinkedHashSet d = new LinkedHashSet();
        private final HashMap e = new HashMap();

        @Override // com.android.tools.r8.naming.AbstractC3665u0
        /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
        public C3634k.a a(String str, String str2, Position position) {
            final HashMap map = this.e;
            Objects.requireNonNull(map);
            C3634k.a aVarA = C3634k.a(str, str2, new BiConsumer() { // from class: com.android.tools.r8.naming.b$a$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    map.put((String) obj, (String) obj2);
                }
            });
            this.c.put(str, aVarA);
            return aVarA;
        }

        @Override // com.android.tools.r8.naming.AbstractC3665u0
        final void a(String str) {
            if (this.a) {
                this.b.add(str);
            }
        }

        public C3605b a() {
            return new C3605b(b(), this.d, this.e, this.b);
        }

        public final boolean b(String str) {
            return this.c.containsKey(str);
        }

        private AbstractC2178hm b() {
            final C1946em c1946emE = AbstractC2178hm.e();
            this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.naming.b$a$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C3605b.a.a(c1946emE, (String) obj, (C3634k.a) obj2);
                }
            });
            return c1946emE.a();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(C1946em c1946em, String str, C3634k.a aVar) {
            c1946em.a(str, aVar.a());
        }

        @Override // com.android.tools.r8.naming.AbstractC3665u0
        public final AbstractC3665u0 a(com.android.tools.r8.naming.mappinginformation.b bVar) {
            this.d.add(bVar);
            return this;
        }
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: renamed from: com.android.tools.r8.naming.b$b, reason: collision with other inner class name */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class EnumC0023b {
        public static final EnumC0023b a = new EnumC0023b("MISSING_FILE_IS_EMPTY_MAP", 0);
        public static final EnumC0023b b = new EnumC0023b("MISSING_FILE_IS_ERROR", 1);

        private EnumC0023b(String str, int i) {
            super(str, i);
        }
    }

    private C3605b(AbstractC2178hm abstractC2178hm, LinkedHashSet linkedHashSet, Map map, List list) {
        this.c = new ConcurrentHashMap();
        this.a = abstractC2178hm;
        this.d = linkedHashSet;
        this.e = map;
        this.f = list;
    }

    public static a a() {
        return new a();
    }

    public static C3605b d(String str) throws IOException {
        Reader readerA = AbstractC2211i9.a(str).a();
        return a(readerA instanceof BufferedReader ? (BufferedReader) readerA : new BufferedReader(readerA), (DiagnosticsHandler) null, false, false, false);
    }

    public static C3605b e(String str) throws IOException {
        Reader readerA = AbstractC2211i9.a(str).a();
        return a(readerA instanceof BufferedReader ? (BufferedReader) readerA : new BufferedReader(readerA), (DiagnosticsHandler) null, false, false, true);
    }

    public final AbstractC2178hm b() {
        return this.a;
    }

    public final String c(String str) {
        return (String) this.e.get(str);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C3605b) && this.a.equals(((C3605b) obj).a);
    }

    public final List f() {
        return this.f;
    }

    public C3605b g() {
        final C1946em c1946emE = AbstractC2178hm.e();
        c1946emE.a(Comparator.comparing(new Function() { // from class: com.android.tools.r8.naming.b$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C3634k) obj).a;
            }
        }));
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.naming.b$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                c1946emE.a((String) obj, (C3634k) obj2);
            }
        });
        return new C3605b(c1946emE.a(), this.d, this.e, this.f);
    }

    public final int hashCode() {
        return this.a.hashCode() * 31;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        a(com.android.tools.r8.utils.V.a(new Consumer() { // from class: com.android.tools.r8.naming.b$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                sb.append((String) obj);
            }
        }));
        return sb.toString();
    }

    public static C3605b a(Path path) throws IOException {
        return a(path, EnumC0023b.b);
    }

    public C3634k b(String str) {
        return (C3634k) this.a.get(str);
    }

    public final com.android.tools.r8.naming.mappinginformation.b c() {
        if (this.d.isEmpty()) {
            return null;
        }
        return (com.android.tools.r8.naming.mappinginformation.b) this.d.iterator().next();
    }

    public static C3605b a(Path path, EnumC0023b enumC0023b) throws IOException {
        if (!g && enumC0023b != EnumC0023b.a && enumC0023b != EnumC0023b.b) {
            throw new AssertionError();
        }
        if (enumC0023b == EnumC0023b.a && !path.toFile().exists()) {
            return d("");
        }
        return a(Files.newBufferedReader(path, StandardCharsets.UTF_8), (DiagnosticsHandler) null, false, false, false);
    }

    public Set<com.android.tools.r8.naming.mappinginformation.b> d() {
        return this.d;
    }

    public com.android.tools.r8.utils.G<String, String> e() {
        if (this.b == null) {
            C1187Ml c1187Ml = new C1187Ml();
            AbstractC2963s10 it = this.a.keySet().iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                c1187Ml.a(str, ((C3634k) this.a.get(str)).a);
            }
            AbstractC1214Nl abstractC1214NlB = c1187Ml.a();
            this.b = new com.android.tools.r8.utils.G(abstractC1214NlB, abstractC1214NlB.g());
        }
        return this.b;
    }

    public static C3605b a(String str, DiagnosticsHandler diagnosticsHandler) throws IOException {
        BufferedReader bufferedReader;
        Reader readerA = AbstractC2211i9.a(str).a();
        if (readerA instanceof BufferedReader) {
            bufferedReader = (BufferedReader) readerA;
        } else {
            bufferedReader = new BufferedReader(readerA);
        }
        return a(bufferedReader, diagnosticsHandler, false, false, false);
    }

    public static C3605b a(String str, DiagnosticsHandler diagnosticsHandler, boolean z, boolean z2, boolean z3) throws IOException {
        BufferedReader bufferedReader;
        Reader readerA = AbstractC2211i9.a(str).a();
        if (readerA instanceof BufferedReader) {
            bufferedReader = (BufferedReader) readerA;
        } else {
            bufferedReader = new BufferedReader(readerA);
        }
        L lA = M.a(bufferedReader);
        if (diagnosticsHandler == null) {
            diagnosticsHandler = new O2();
        }
        G0 g0 = new G0(diagnosticsHandler, lA, MapVersion.MAP_VERSION_NONE, z, z2);
        try {
            a aVarA = a();
            aVarA.a = z3;
            g0.a(aVarA);
            C3605b c3605bA = aVarA.a();
            g0.close();
            return c3605bA;
        } catch (Throwable th) {
            try {
                g0.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static C3605b a(BufferedReader bufferedReader, DiagnosticsHandler diagnosticsHandler, boolean z, boolean z2, boolean z3) throws IOException {
        L lA = M.a(bufferedReader);
        if (diagnosticsHandler == null) {
            diagnosticsHandler = new O2();
        }
        G0 g0 = new G0(diagnosticsHandler, lA, MapVersion.MAP_VERSION_NONE, z, z2);
        try {
            a aVarA = a();
            aVarA.a = z3;
            g0.a(aVarA);
            C3605b c3605bA = aVarA.a();
            g0.close();
            return c3605bA;
        } catch (Throwable th) {
            try {
                g0.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static C3605b a(DiagnosticsHandler diagnosticsHandler, M m, MapVersion mapVersion, boolean z, boolean z2) {
        if (diagnosticsHandler == null) {
            diagnosticsHandler = new O2();
        }
        G0 g0 = new G0(diagnosticsHandler, m, mapVersion, z, z2);
        try {
            a aVarA = a();
            g0.a(aVarA);
            C3605b c3605bA = aVarA.a();
            g0.close();
            return c3605bA;
        } catch (Throwable th) {
            try {
                g0.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public String a(String str) {
        C3634k c3634k = (C3634k) this.a.get(str);
        return c3634k == null ? str : c3634k.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C1946em c1946em, String str, C3634k c3634k) {
        if (this.a.containsKey(str)) {
            return;
        }
        c1946em.a(str, c3634k);
    }

    public final C3605b a(C3605b c3605b) {
        List list;
        if (c3605b == null || c3605b.a.isEmpty()) {
            return this;
        }
        if (this.a.isEmpty()) {
            return c3605b;
        }
        final C1946em c1946emE = AbstractC2178hm.e();
        AbstractC2178hm abstractC2178hm = c3605b.a;
        AbstractC2963s10 it = this.a.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            C3634k c3634k = (C3634k) abstractC2178hm.get(entry.getKey());
            if (c3634k == null) {
                c1946emE.a(entry.getKey(), entry.getValue());
            } else {
                c1946emE.a((String) entry.getKey(), ((C3634k) entry.getValue()).a(c3634k));
            }
        }
        abstractC2178hm.forEach(new BiConsumer() { // from class: com.android.tools.r8.naming.b$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c1946emE, (String) obj, (C3634k) obj2);
            }
        });
        LinkedHashSet linkedHashSet = new LinkedHashSet(d());
        linkedHashSet.addAll(c3605b.d());
        HashMap map = new HashMap(this.e);
        map.putAll(c3605b.e);
        List listEmptyList = Collections.emptyList();
        if (!this.f.isEmpty() || !c3605b.f.isEmpty()) {
            list = listEmptyList;
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.f);
            arrayList.addAll(c3605b.f);
            list = arrayList;
        }
        list = listEmptyList;
        return new C3605b(c1946emE.a(), linkedHashSet, map, list);
    }

    public final void a(com.android.tools.r8.utils.V v) {
        if (!g) {
            Iterator it = g().a.entrySet().iterator();
            for (Map.Entry entry : this.a.entrySet()) {
                Map.Entry entry2 = (Map.Entry) it.next();
                boolean z = g;
                if (!z && !((String) entry.getKey()).equals(entry2.getKey())) {
                    throw new AssertionError();
                }
                if (!z && entry.getValue() != entry2.getValue()) {
                    throw new AssertionError();
                }
            }
        }
        Iterator it2 = this.a.values().iterator();
        while (it2.hasNext()) {
            ((C3634k) it2.next()).a(v);
        }
    }

    public final Q.b a(C0684j2 c0684j2) {
        Q qA;
        String strB = C3970u0.b(c0684j2.e.e.toString());
        C0767v2[] c0767v2Arr = c0684j2.h.f.a;
        String[] strArr = new String[c0767v2Arr.length];
        for (int i = 0; i < c0767v2Arr.length; i++) {
            strArr[i] = C3970u0.a(c0767v2Arr[i].b1(), this);
        }
        Q.c bVar = new Q.b(c0684j2.f.toString(), C3970u0.a(c0684j2.h.e.b1(), this), strArr);
        Q.c cVar = (Q.c) this.c.get(bVar);
        if (cVar != null) {
            bVar = cVar;
        } else {
            this.c.put(bVar, bVar);
        }
        Q.b bVar2 = (Q.b) bVar;
        C3634k c3634kB = b(strB);
        return (c3634kB == null || (qA = c3634kB.a(bVar2)) == null) ? bVar2 : qA.b().b();
    }

    public String a(C0767v2 c0767v2) {
        return C3970u0.a(c0767v2.e.toString(), this);
    }
}
