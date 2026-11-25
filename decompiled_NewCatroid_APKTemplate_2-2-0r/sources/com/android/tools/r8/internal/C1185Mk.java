package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1185Mk {
    static final /* synthetic */ boolean i = true;
    private final C0722p1 a;
    private final C1240Ok b;
    private final C1213Nk c;
    private final com.android.tools.r8.utils.O2 d;
    private final boolean e;
    private final int f;
    private Origin g;
    private C1333Rt h;

    public C1185Mk(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, boolean z, int i2) {
        this.a = c0722p1;
        this.b = new C1240Ok(c0722p1);
        this.c = new C1213Nk(c0722p1);
        this.d = o2;
        this.f = i2;
        this.e = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(InterfaceC2089gZ interfaceC2089gZ) {
    }

    public final Origin b() {
        if (i || this.g != null) {
            return this.g;
        }
        throw new AssertionError();
    }

    public final com.android.tools.r8.utils.O2 c() {
        return this.d;
    }

    public final C1333Rt a() {
        return this.h;
    }

    final AbstractC1249Ot a(C1333Rt c1333Rt, String str) {
        if (c1333Rt.b(str)) {
            return c1333Rt.a(str);
        }
        this.d.a(new StringDiagnostic("Invalid desugared library configuration. Expected required key '" + str + FormatHelper.QUOTE, this.g));
        throw null;
    }

    final String b(com.android.tools.r8.d0 d0Var) {
        Origin origin = d0Var.getOrigin();
        this.g = origin;
        if (!i && origin == null) {
            throw new AssertionError();
        }
        try {
            String strA = d0Var.a();
            this.h = C1386Tt.a(strA).d();
            return strA;
        } catch (Exception e) {
            this.d.a(new ExceptionDiagnostic(e, this.g));
            throw null;
        }
    }

    private C0767v2 b(String str) {
        return this.a.e(C3970u0.H(str));
    }

    public C1133Kk a(com.android.tools.r8.d0 d0Var) {
        return a(this.g, b(d0Var), this.h, new Consumer() { // from class: com.android.tools.r8.internal.Mk$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1185Mk.a((InterfaceC2089gZ) obj);
            }
        });
    }

    public final C1133Kk a(Origin origin, String str, C1333Rt c1333Rt, Consumer consumer) {
        if (!C1233Oe.a(c1333Rt, this.d, origin)) {
            this.d.a("Attempt to parse a non desugared library human specification as a human specification.");
        }
        this.g = origin;
        this.h = c1333Rt;
        C1533Yk c1533YkA = a(consumer, str);
        C1297Qk.a aVarA = C1297Qk.a(this.d, this.g);
        AbstractC1249Ot abstractC1249OtA = a(this.h, "common_flags");
        AbstractC1249Ot abstractC1249OtA2 = a(this.h, "library_flags");
        AbstractC1249Ot abstractC1249OtA3 = a(this.h, "program_flags");
        Iterator it = abstractC1249OtA.c().iterator();
        while (it.hasNext()) {
            C1333Rt c1333RtD = ((AbstractC1249Ot) it.next()).d();
            if (this.f <= a(c1333RtD, "api_level_below_or_equal").b()) {
                if (c1333RtD.b("api_level_greater_or_equal")) {
                    if (this.f >= c1333RtD.a("api_level_greater_or_equal").b()) {
                        a(c1333RtD, aVarA);
                    }
                } else {
                    a(c1333RtD, aVarA);
                }
            }
        }
        Iterator it2 = (this.e ? abstractC1249OtA2.c() : abstractC1249OtA3.c()).iterator();
        while (it2.hasNext()) {
            C1333Rt c1333RtD2 = ((AbstractC1249Ot) it2.next()).d();
            if (this.f <= a(c1333RtD2, "api_level_below_or_equal").b()) {
                if (c1333RtD2.b("api_level_greater_or_equal")) {
                    if (this.f >= c1333RtD2.a("api_level_greater_or_equal").b()) {
                        a(c1333RtD2, aVarA);
                    }
                } else {
                    a(c1333RtD2, aVarA);
                }
            }
        }
        C1133Kk c1133Kk = new C1133Kk(c1533YkA, aVarA.a(), this.e);
        this.g = null;
        return c1133Kk;
    }

    final C1533Yk a(Consumer consumer, String str) {
        C1506Xk c1506XkA = C1533Yk.a();
        c1506XkA.b(str);
        int iB = a(this.h, "configuration_format_version").b();
        if (iB != 100) {
            this.d.warning(new StringDiagnostic("Human desugared library specification format version " + iB + " mismatches the parser expected version (100). This is allowed and should happen only while extending the specifications.", this.g));
        }
        c1506XkA.a(a(this.h, "identifier").f());
        c1506XkA.c(a(this.h, "synthesized_library_classes_package_prefix").f());
        c1506XkA.a(EnumC3891f.c(a(this.h, "required_compilation_api_level").b()));
        if (this.h.b("shrinker_config")) {
            C1222Nt c1222NtC = this.h.a("shrinker_config").c();
            ArrayList arrayList = new ArrayList(c1222NtC.size());
            Iterator it = c1222NtC.iterator();
            while (it.hasNext()) {
                arrayList.add(((AbstractC1249Ot) it.next()).f());
            }
            c1506XkA.a(arrayList);
        }
        if (this.h.b("support_all_callbacks_from_library")) {
            c1506XkA.b(this.h.a("support_all_callbacks_from_library").a());
        }
        consumer.accept(c1506XkA);
        return c1506XkA.a();
    }

    final void a(C1333Rt c1333Rt, C1297Qk.a aVar) {
        if (c1333Rt.b("rewrite_prefix")) {
            Iterator itA = AbstractC1159Lk.a(c1333Rt, "rewrite_prefix");
            while (itA.hasNext()) {
                Map.Entry entry = (Map.Entry) itA.next();
                aVar.a((String) entry.getKey(), ((AbstractC1249Ot) entry.getValue()).f());
            }
        }
        if (c1333Rt.b("maintain_prefix")) {
            Iterator it = c1333Rt.a("maintain_prefix").c().iterator();
            while (it.hasNext()) {
                aVar.c(((AbstractC1249Ot) it.next()).f());
            }
        }
        if (c1333Rt.b("dont_rewrite_prefix")) {
            Iterator it2 = c1333Rt.a("dont_rewrite_prefix").c().iterator();
            while (it2.hasNext()) {
                aVar.b(((AbstractC1249Ot) it2.next()).f());
            }
        }
        if (c1333Rt.b("never_outline_api")) {
            Iterator it3 = c1333Rt.a("never_outline_api").c().iterator();
            while (it3.hasNext()) {
                aVar.b(a(((AbstractC1249Ot) it3.next()).f()));
            }
        }
        if (c1333Rt.b("api_generic_types_conversion")) {
            Iterator itA2 = AbstractC1159Lk.a(c1333Rt, "api_generic_types_conversion");
            while (itA2.hasNext()) {
                Map.Entry entry2 = (Map.Entry) itA2.next();
                C1222Nt c1222NtC = ((AbstractC1249Ot) entry2.getValue()).c();
                for (int i2 = 0; i2 < c1222NtC.size(); i2 += 2) {
                    aVar.a(a((String) entry2.getKey()), c1222NtC.j(i2).b(), a(c1222NtC.j(i2 + 1).f()));
                }
            }
        }
        if (c1333Rt.b("rewrite_derived_prefix")) {
            Iterator itA3 = AbstractC1159Lk.a(c1333Rt, "rewrite_derived_prefix");
            while (itA3.hasNext()) {
                Map.Entry entry3 = (Map.Entry) itA3.next();
                for (Map.Entry entry4 : ((AbstractC1249Ot) entry3.getValue()).d().entrySet()) {
                    aVar.a((String) entry3.getKey(), (String) entry4.getKey(), ((AbstractC1249Ot) entry4.getValue()).f());
                }
            }
        }
        if (c1333Rt.b("retarget_static_field")) {
            Iterator itA4 = AbstractC1159Lk.a(c1333Rt, "retarget_static_field");
            while (itA4.hasNext()) {
                Map.Entry entry5 = (Map.Entry) itA4.next();
                this.c.b((String) entry5.getKey());
                com.android.tools.r8.graph.Y0 y0C = this.c.c();
                this.c.b(((AbstractC1249Ot) entry5.getValue()).f());
                aVar.a(y0C, this.c.c());
            }
        }
        if (c1333Rt.b("retarget_method")) {
            Iterator itA5 = AbstractC1159Lk.a(c1333Rt, "retarget_method");
            while (itA5.hasNext()) {
                Map.Entry entry6 = (Map.Entry) itA5.next();
                String str = (String) entry6.getKey();
                String strF = ((AbstractC1249Ot) entry6.getValue()).f();
                if (strF.contains("#")) {
                    C0684j2 c0684j2A = a(str);
                    this.b.b(strF);
                    aVar.c(c0684j2A, this.b.d());
                } else {
                    aVar.c(a(str), b(strF));
                }
            }
        }
        if (c1333Rt.b("retarget_method_with_emulated_dispatch")) {
            Iterator itA6 = AbstractC1159Lk.a(c1333Rt, "retarget_method_with_emulated_dispatch");
            while (itA6.hasNext()) {
                Map.Entry entry7 = (Map.Entry) itA6.next();
                String str2 = (String) entry7.getKey();
                String strF2 = ((AbstractC1249Ot) entry7.getValue()).f();
                if (strF2.contains("#")) {
                    C0684j2 c0684j2A2 = a(str2);
                    this.b.b(strF2);
                    aVar.b(c0684j2A2, this.b.d());
                } else {
                    aVar.b(a(str2), b(strF2));
                }
            }
        }
        if (c1333Rt.b("covariant_retarget_method")) {
            Iterator itA7 = AbstractC1159Lk.a(c1333Rt, "covariant_retarget_method");
            while (itA7.hasNext()) {
                Map.Entry entry8 = (Map.Entry) itA7.next();
                aVar.a(a((String) entry8.getKey()), b(((AbstractC1249Ot) entry8.getValue()).f()));
            }
        }
        if (c1333Rt.b("backport")) {
            Iterator itA8 = AbstractC1159Lk.a(c1333Rt, "backport");
            while (itA8.hasNext()) {
                Map.Entry entry9 = (Map.Entry) itA8.next();
                aVar.c(b((String) entry9.getKey()), b(((AbstractC1249Ot) entry9.getValue()).f()));
            }
        }
        if (c1333Rt.b("emulate_interface")) {
            Iterator itA9 = AbstractC1159Lk.a(c1333Rt, "emulate_interface");
            while (itA9.hasNext()) {
                Map.Entry entry10 = (Map.Entry) itA9.next();
                aVar.b(b((String) entry10.getKey()), b(((AbstractC1249Ot) entry10.getValue()).f()));
            }
        }
        if (c1333Rt.b("custom_conversion")) {
            Iterator itA10 = AbstractC1159Lk.a(c1333Rt, "custom_conversion");
            while (itA10.hasNext()) {
                Map.Entry entry11 = (Map.Entry) itA10.next();
                aVar.a(b((String) entry11.getKey()), b(((AbstractC1249Ot) entry11.getValue()).f()));
            }
        }
        if (c1333Rt.b("wrapper_conversion")) {
            Iterator it4 = c1333Rt.a("wrapper_conversion").c().iterator();
            while (it4.hasNext()) {
                C0767v2 c0767v2B = b(((AbstractC1249Ot) it4.next()).f());
                aVar.getClass();
                aVar.a(c0767v2B, Collections.emptySet());
            }
        }
        if (c1333Rt.b("wrapper_conversion_excluding")) {
            Iterator itA11 = AbstractC1159Lk.a(c1333Rt, "wrapper_conversion_excluding");
            while (itA11.hasNext()) {
                Map.Entry entry12 = (Map.Entry) itA11.next();
                C0767v2 c0767v2B2 = b((String) entry12.getKey());
                C1222Nt c1222NtC2 = ((AbstractC1249Ot) entry12.getValue()).c();
                Set setC = OU.c();
                Iterator it5 = c1222NtC2.iterator();
                while (it5.hasNext()) {
                    setC.add(a(((AbstractC1249Ot) it5.next()).f()));
                }
                aVar.a(c0767v2B2, setC);
            }
        }
        if (c1333Rt.b("dont_rewrite")) {
            Iterator it6 = c1333Rt.a("dont_rewrite").c().iterator();
            while (it6.hasNext()) {
                aVar.a(a(((AbstractC1249Ot) it6.next()).f()));
            }
        }
        if (c1333Rt.b("dont_retarget")) {
            Iterator it7 = c1333Rt.a("dont_retarget").c().iterator();
            while (it7.hasNext()) {
                aVar.a(b(((AbstractC1249Ot) it7.next()).f()));
            }
        }
        if (c1333Rt.b("amend_library_method")) {
            Iterator it8 = c1333Rt.a("amend_library_method").c().iterator();
            while (it8.hasNext()) {
                this.b.b(((AbstractC1249Ot) it8.next()).f());
                aVar.a(this.b.d(), this.b.c());
            }
        }
        if (c1333Rt.b("amend_library_field")) {
            Iterator it9 = c1333Rt.a("amend_library_field").c().iterator();
            while (it9.hasNext()) {
                this.c.b(((AbstractC1249Ot) it9.next()).f());
                aVar.a(this.c.c(), this.c.d());
            }
        }
    }

    private C0684j2 a(String str) {
        this.b.b(str);
        return this.b.d();
    }
}
