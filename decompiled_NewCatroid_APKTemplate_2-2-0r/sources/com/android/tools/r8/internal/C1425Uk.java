package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0579p$$ExternalSyntheticLambda0;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.e5$$ExternalSyntheticLambda4;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Uk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1425Uk {
    private C0680j a;
    private com.android.tools.r8.utils.O2 b;
    private final Set c = OU.c();
    private final com.android.tools.r8.utils.y3 d;

    public C1425Uk(com.android.tools.r8.utils.y3 y3Var) {
        this.d = y3Var;
    }

    public LF a(EF ef, AbstractC0681j0 abstractC0681j0) {
        this.d.a("Legacy to human all API convert");
        this.b = abstractC0681j0.d.e;
        this.a = C0680j.a(C0664h.a(abstractC0681j0, com.android.tools.r8.synthesis.y.d()));
        C1533Yk c1533YkE = ef.e();
        C2835qD c2835qD = new C2835qD(c1533YkE.e(), c1533YkE.f(), c1533YkE.c(), c1533YkE.d(), c1533YkE.g(), c1533YkE.b());
        String strF = c2835qD.f();
        String strC = c2835qD.c();
        LF lf = new LF(c2835qD, a((Map) ef.a(), strF, true, strC), a((Map) ef.b(), strF, true, strC), a((Map) ef.d(), strF, false, strC));
        this.d.c();
        return lf;
    }

    final void b(String str, Set set) {
        ArrayList arrayList = new ArrayList(set);
        arrayList.sort(new C0579p$$ExternalSyntheticLambda0());
        a(str, arrayList);
    }

    private HashMap a(Map map, final String str, final boolean z, final String str2) {
        final HashMap map2 = new HashMap();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(map2, str, z, str2, (C2822q2) obj, (C1297Qk) obj2);
            }
        });
        return map2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map map, String str, boolean z, String str2, C2822q2 c2822q2, C1297Qk c1297Qk) {
        map.put(c2822q2, a(c1297Qk, str, z, str2));
    }

    public C2215iD a(C1133Kk c1133Kk, AbstractC0681j0 abstractC0681j0) {
        this.d.a("Human to machine convert");
        this.b = abstractC0681j0.d.e;
        this.a = C0680j.a(C0664h.a(abstractC0681j0, com.android.tools.r8.synthesis.y.d()));
        AbstractC3112uB.a(abstractC0681j0, c1133Kk.d(), c1133Kk.j().e());
        C2597nD c2597nDA = a(c1133Kk.i(), c1133Kk.f(), c1133Kk.d(), c1133Kk.h());
        C1533Yk c1533YkJ = c1133Kk.j();
        C2835qD c2835qD = new C2835qD(c1533YkJ.e(), c1533YkJ.f(), c1533YkJ.c(), c1533YkJ.d(), c1533YkJ.g(), c1533YkJ.b());
        this.d.c();
        return new C2215iD(c1133Kk.d(), c2835qD, c2597nDA);
    }

    private C2597nD a(C1297Qk c1297Qk, String str, boolean z, String str2) {
        this.d.a("convert rewriting flags");
        final C2522mD c2522mDA = C2597nD.a();
        C3448ye.a(c1297Qk.b(), c1297Qk.a(), this.a, this.b, com.android.tools.r8.androidapi.h.f());
        c1297Qk.b().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                c2522mDA.a((C0684j2) obj, (C0725p4) obj2);
            }
        });
        c1297Qk.a().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                c2522mDA.a((com.android.tools.r8.graph.Y0) obj, (com.android.tools.r8.graph.Q2) obj2);
            }
        });
        c1297Qk.c().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                c2522mDA.a((C0684j2) obj, (C0684j2[]) obj2);
            }
        });
        c1297Qk.l().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2522mDA.a((C0684j2) obj);
            }
        });
        new C1376Tk(this.a).a(c1297Qk, c2522mDA, new BiConsumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b((String) obj, (Set) obj2);
            }
        });
        new C1324Rk(this.a).a(c1297Qk, this.a, c2522mDA, new BiConsumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b((String) obj, (Set) obj2);
            }
        });
        new C1350Sk(this.a, c2522mDA, str, z, str2, c1297Qk).a(c1297Qk, new BiConsumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((String) obj, (Set) obj2);
            }
        });
        new C1477Wk(this.a).a(c1297Qk, c2522mDA, new BiConsumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.b((String) obj, (Set) obj2);
            }
        });
        c1297Qk.e().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c2522mDA, (C0767v2) obj, (C0767v2) obj2);
            }
        });
        b("Cannot register custom conversion due to missing type: ", this.c);
        c1297Qk.f().forEach(new Consumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2522mDA.a((C0767v2) obj);
            }
        });
        c1297Qk.j().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Uk$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                c2522mDA.a((C0767v2) obj, (C0767v2) obj2);
            }
        });
        C2597nD c2597nDA = c2522mDA.a();
        this.d.c();
        return c2597nDA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2522mD c2522mD, C0767v2 c0767v2, C0767v2 c0767v3) {
        C0680j c0680j = this.a;
        C0767v2 c0767v2B = c2522mD.b(c0767v2);
        if (c0767v2B == null) {
            this.c.add(c0767v2);
        } else {
            c2522mD.a(c0767v2, new C3004sd(c0680j.a().a(c0767v3, c0680j.a().b(c0767v2, c0767v2B), c0680j.a().j0), c0680j.a().a(c0767v3, c0680j.a().b(c0767v2B, c0767v2), c0680j.a().j0)));
        }
    }

    final void a(String str, Set set) {
        ArrayList arrayList = new ArrayList(set);
        arrayList.sort(new e5$$ExternalSyntheticLambda4());
        a(str, arrayList);
    }

    private void a(String str, ArrayList arrayList) {
        if (arrayList.isEmpty()) {
            return;
        }
        this.b.c("Specification conversion: " + str + arrayList);
    }
}
