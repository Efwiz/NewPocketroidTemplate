package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.C3979v2;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1822dB {
    private static final EnumC3891f d = EnumC3891f.N_MR1;
    static final /* synthetic */ boolean e = true;
    private final com.android.tools.r8.utils.y3 a;
    private final HashSet b = new HashSet();
    private final HashSet c = new HashSet();

    public C1822dB(com.android.tools.r8.utils.y3 y3Var) {
        this.a = y3Var;
    }

    private static void c(final C1297Qk.a aVar, String str, String str2) {
        if (str.startsWith("j$")) {
            if (!e && !str2.startsWith(SuffixConstants.EXTENSION_java)) {
                throw new AssertionError();
            }
            aVar.a(str2, str, str2);
            return;
        }
        if (str.equals("__wrapper__.")) {
            AbstractC2178hm.a("java.nio.", "j$.nio.", "java.io.", "j$.io.").forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.dB$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C1822dB.b(aVar, (String) obj, (String) obj2);
                }
            });
        } else {
            aVar.a(str, str2);
        }
    }

    public EF a(JF jf, AbstractC0681j0 abstractC0681j0) {
        this.a.a("Legacy to human all API convert");
        Origin originC = jf.c();
        C1982fB c1982fBE = jf.e();
        C1533Yk c1533YkA = C1533Yk.a().a(c1982fBE.c()).a(c1982fBE.b()).b(c1982fBE.d()).a(c1982fBE.e()).b(c1982fBE.g()).c(c1982fBE.f()).a();
        HashMap mapA = a(jf.a(), abstractC0681j0, originC);
        HashMap mapA2 = a(jf.d(), abstractC0681j0, originC);
        HashMap mapA3 = a(jf.b(), abstractC0681j0, originC);
        String strC = c1533YkA.c();
        EnumC3891f enumC3891f = d;
        C2822q2 c2822q2 = new C2822q2(enumC3891f.d());
        C1297Qk c1297Qk = (C1297Qk) mapA3.get(c2822q2);
        if (c1297Qk != null) {
            C1297Qk.a aVarB = c1297Qk.b(abstractC0681j0.d.e, originC);
            a(strC, abstractC0681j0.e, enumC3891f, aVarB);
            mapA3.put(c2822q2, aVarB.a());
        }
        a(abstractC0681j0.d.e);
        EF ef = new EF(originC, c1533YkA, mapA, mapA3, mapA2);
        FF.a(ef, abstractC0681j0.d.e);
        this.a.c();
        return ef;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C1297Qk.a aVar, String str, String str2) {
        aVar.a(str, "__wrapper__." + str, str);
        aVar.a(str, "__wrapper__." + str2, str2);
    }

    public final C1133Kk a(C1594aB c1594aB, AbstractC0681j0 abstractC0681j0) {
        this.a.a("Legacy to Human convert");
        AbstractC3112uB.a(abstractC0681j0, c1594aB.d(), c1594aB.j().e());
        C1982fB c1982fBJ = c1594aB.j();
        C1533Yk c1533YkA = C1533Yk.a().a(c1982fBJ.c()).a(c1982fBJ.b()).b(c1982fBJ.d()).a(c1982fBJ.e()).b(c1982fBJ.g()).c(c1982fBJ.f()).a();
        Origin originUnknown = Origin.unknown();
        C1297Qk c1297QkA = a(c1594aB.i(), abstractC0681j0, originUnknown);
        if (c1594aB.d()) {
            this.a.a("Legacy hacks");
            C1297Qk.a aVarB = c1297QkA.b(abstractC0681j0.d.e, originUnknown);
            a(c1594aB.h(), abstractC0681j0.e, abstractC0681j0.d.B0(), aVarB);
            c1297QkA = aVarB.a();
            this.a.c();
        }
        a(abstractC0681j0.d.e);
        this.a.c();
        return new C1133Kk(c1533YkA, c1297QkA, c1594aB.d());
    }

    private void a(com.android.tools.r8.utils.O2 o2) {
        if (!this.b.isEmpty()) {
            o2.c("Cannot retarget core lib member for missing classes: " + this.b + ". This usually means that the compilation SDK is absent or too old.");
        }
        if (this.c.isEmpty()) {
            return;
        }
        o2.c("Should have found a method (library specifications) for " + this.c + ". This usually means that the compilation SDK is absent or too old.");
    }

    private static void a(String str, C0722p1 c0722p1, EnumC3891f enumC3891f, C1297Qk.a aVar) {
        if (enumC3891f.b(d)) {
            C0759u2 c0759u2C = c0722p1.c("deepEquals0");
            C0767v2 c0767v2 = c0722p1.v1;
            C0767v2 c0767v3 = c0722p1.Z1;
            C0684j2 c0684j2A = c0722p1.a(c0722p1.c(c0722p1.R0), c0722p1.b(c0767v2, c0767v3, c0767v3), c0759u2C);
            aVar.c(c0684j2A, c0722p1.e("Ljava/util/DesugarArrays;"));
            aVar.a(c0684j2A, C0725p4.b(10, false));
            aVar.c(c0722p1.a(c0722p1.e("Ljava/util/TimeZone;"), c0722p1.b(c0722p1.e("Ljava/util/TimeZone;"), c0722p1.e("Ljava/time/ZoneId;")), c0722p1.c("getTimeZone")), c0722p1.e("Ljava/util/DesugarTimeZone;"));
        }
        if (str.contains(":1.2")) {
            aVar.a("sun.misc.Desugar", "jdk.internal.misc.", "j$.sun.misc.Desugar");
        }
    }

    private HashMap a(InterfaceC2947rp interfaceC2947rp, final AbstractC0681j0 abstractC0681j0, final Origin origin) {
        final HashMap map = new HashMap();
        interfaceC2947rp.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.dB$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(map, abstractC0681j0, origin, (Integer) obj, (C1747cB) obj2);
            }
        });
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map map, AbstractC0681j0 abstractC0681j0, Origin origin, Integer num, C1747cB c1747cB) {
        map.put(new C2822q2(num.intValue()), a(c1747cB, abstractC0681j0, origin));
    }

    private C1297Qk a(C1747cB c1747cB, final AbstractC0681j0 abstractC0681j0, Origin origin) {
        this.a.a("Convert rewriting flags");
        final C1297Qk.a aVarA = C1297Qk.a(abstractC0681j0.d.e, origin);
        c1747cB.g().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.dB$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(aVarA, (String) obj, (String) obj2);
            }
        });
        Map mapE = c1747cB.e();
        Objects.requireNonNull(aVarA);
        mapE.forEach(new FF$$ExternalSyntheticLambda8(aVarA));
        c1747cB.a().forEach(new FF$$ExternalSyntheticLambda13(aVarA));
        c1747cB.b().forEach(new FF$$ExternalSyntheticLambda14(aVarA));
        c1747cB.c().forEach(new FF$$ExternalSyntheticLambda10(aVarA));
        c1747cB.h().forEach(new Consumer() { // from class: com.android.tools.r8.internal.dB$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                aVarA.b((C0767v2) obj);
            }
        });
        c1747cB.f().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.dB$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b(aVarA, abstractC0681j0, (C0759u2) obj, (Map) obj2);
            }
        });
        c1747cB.d().forEach(new Consumer() { // from class: com.android.tools.r8.internal.dB$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(aVarA, abstractC0681j0, (C3979v2) obj);
            }
        });
        C1297Qk c1297QkA = aVarA.a();
        this.a.c();
        return c1297QkA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C1297Qk.a aVar, String str, String str2) {
        c(aVar, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C1297Qk.a aVar, AbstractC0681j0 abstractC0681j0, C3979v2 c3979v2) {
        AbstractC0727q0 abstractC0727q0C = abstractC0681j0.c((C0767v2) c3979v2.a());
        if (!e && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        Iterator it = a((C0759u2) c3979v2.b(), abstractC0727q0C, aVar, abstractC0681j0).iterator();
        while (it.hasNext()) {
            aVar.a(((AbstractC0748t0) it.next()).getReference());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(final C1297Qk.a aVar, final AbstractC0681j0 abstractC0681j0, final C0759u2 c0759u2, Map map) {
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.dB$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(abstractC0681j0, c0759u2, aVar, (C0767v2) obj, (C0767v2) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC0681j0 abstractC0681j0, C0759u2 c0759u2, C1297Qk.a aVar, C0767v2 c0767v2, C0767v2 c0767v3) {
        AbstractC0727q0 abstractC0727q0C = abstractC0681j0.c(c0767v2);
        if (abstractC0727q0C == null) {
            if (e) {
                this.b.add(c0767v2.r0());
                return;
            }
            throw new AssertionError("Cannot retarget core lib member for missing class " + c0767v2);
        }
        for (AbstractC0748t0 abstractC0748t0 : a(c0759u2, abstractC0727q0C, aVar, abstractC0681j0)) {
            com.android.tools.r8.graph.W0 w0E = abstractC0748t0.e();
            if (!w0E.E0() && !w0E.z1() && !abstractC0748t0.a().C1()) {
                aVar.b(abstractC0748t0.getReference(), c0767v3);
            } else {
                aVar.c(abstractC0748t0.getReference(), c0767v3);
            }
        }
    }

    private List a(final C0759u2 c0759u2, AbstractC0727q0 abstractC0727q0, C1297Qk.a aVar, AbstractC0681j0 abstractC0681j0) {
        final ArrayList arrayList = new ArrayList();
        abstractC0727q0.b(new Consumer() { // from class: com.android.tools.r8.internal.dB$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add((AbstractC0748t0) obj);
            }
        }, new Predicate() { // from class: com.android.tools.r8.internal.dB$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1822dB.a(c0759u2, (com.android.tools.r8.graph.W0) obj);
            }
        });
        if (arrayList.isEmpty() && c0759u2.toString().equals("transferTo") && abstractC0727q0.d.toString().equals("java.io.InputStream")) {
            C0722p1 c0722p1A = abstractC0681j0.a();
            C0684j2 c0684j2A = c0722p1A.a(abstractC0727q0.d, c0722p1A.b(c0722p1A.B1, c0722p1A.e("Ljava/io/OutputStream;")), c0759u2);
            C0725p4 c0725p4B = C0725p4.b(1, false);
            aVar.a(c0684j2A, c0725p4B);
            return AbstractC1639am.c(AbstractC0748t0.a(abstractC0727q0, com.android.tools.r8.graph.W0.S0().a(c0684j2A).a(c0725p4B).a()));
        }
        if (arrayList.isEmpty()) {
            String str = abstractC0727q0.r0() + "." + c0759u2;
            if (e) {
                this.c.add(str);
            } else {
                throw new AssertionError("Should have found a method (library specifications) for " + str);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0759u2 c0759u2, com.android.tools.r8.graph.W0 w0) {
        return w0.K0() == c0759u2;
    }
}
