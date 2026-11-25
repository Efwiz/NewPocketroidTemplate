package com.android.tools.r8.internal;

import com.android.tools.r8.errors.UnsupportedDesugaredLibraryConfigurationVersionDiagnostic;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1672bB {
    public static final com.android.tools.r8.utils.S2 g = com.android.tools.r8.utils.S2.a(1, 0, 9);
    static final /* synthetic */ boolean h = true;
    private final C0722p1 a;
    private final com.android.tools.r8.utils.O2 b;
    private final boolean c;
    private final int d;
    private Origin e;
    private C1333Rt f;

    public C1672bB(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, boolean z, int i) {
        this.a = c0722p1;
        this.b = o2;
        this.d = i;
        this.c = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(InterfaceC2089gZ interfaceC2089gZ) {
    }

    public final C1333Rt b() {
        return this.f;
    }

    public final Origin c() {
        if (h || this.e != null) {
            return this.e;
        }
        throw new AssertionError();
    }

    public final com.android.tools.r8.utils.O2 d() {
        return this.b;
    }

    public final C0722p1 a() {
        return this.a;
    }

    final String b(com.android.tools.r8.d0 d0Var) {
        Origin origin = d0Var.getOrigin();
        this.e = origin;
        if (!h && origin == null) {
            throw new AssertionError();
        }
        try {
            String strA = d0Var.a();
            this.f = C1386Tt.a(strA).d();
            return strA;
        } catch (Exception e) {
            this.b.a(new ExceptionDiagnostic(e, this.e));
            throw null;
        }
    }

    final AbstractC1249Ot a(C1333Rt c1333Rt, String str) {
        if (c1333Rt.b(str)) {
            return c1333Rt.a(str);
        }
        this.b.a(new StringDiagnostic("Invalid desugared library configuration. Expected required key '" + str + FormatHelper.QUOTE, this.e));
        throw null;
    }

    public C1594aB a(com.android.tools.r8.d0 d0Var) {
        return a(this.e, b(d0Var), this.f, new Consumer() { // from class: com.android.tools.r8.internal.bB$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1672bB.a((InterfaceC2089gZ) obj);
            }
        });
    }

    public final C1594aB a(Origin origin, String str, C1333Rt c1333Rt, Consumer consumer) {
        if (C1233Oe.a(c1333Rt, this.b, origin)) {
            this.b.a("Attempt to parse a desugared library human specification as a legacy specification.");
        }
        this.e = origin;
        this.f = c1333Rt;
        C1982fB c1982fBA = a(consumer, str);
        C1747cB.a aVarA = C1747cB.a(this.a, this.b, this.e);
        AbstractC1249Ot abstractC1249OtA = a(this.f, "common_flags");
        AbstractC1249Ot abstractC1249OtA2 = a(this.f, "library_flags");
        AbstractC1249Ot abstractC1249OtA3 = a(this.f, "program_flags");
        Iterator it = abstractC1249OtA.c().iterator();
        while (it.hasNext()) {
            C1333Rt c1333RtD = ((AbstractC1249Ot) it.next()).d();
            if (this.d <= a(c1333RtD, "api_level_below_or_equal").b()) {
                a(c1333RtD, aVarA);
            }
        }
        Iterator it2 = (this.c ? abstractC1249OtA2.c() : abstractC1249OtA3.c()).iterator();
        while (it2.hasNext()) {
            C1333Rt c1333RtD2 = ((AbstractC1249Ot) it2.next()).d();
            if (this.d <= a(c1333RtD2, "api_level_below_or_equal").b()) {
                a(c1333RtD2, aVarA);
            }
        }
        C1594aB c1594aB = new C1594aB(c1982fBA, aVarA.a(), this.c);
        this.e = null;
        return c1594aB;
    }

    final C1982fB a(Consumer consumer, String str) {
        C1895eB c1895eBA = C1982fB.a();
        c1895eBA.b(str);
        if (a(this.f, "configuration_format_version").b() <= 5) {
            String strF = a(this.f, "version").f();
            com.android.tools.r8.utils.S2 s2A = com.android.tools.r8.utils.S2.a(strF);
            com.android.tools.r8.utils.S2 s2 = g;
            if (s2A.b(s2)) {
                c1895eBA.a(String.join(":", a(this.f, FirebaseAnalytics.Param.GROUP_ID).f(), a(this.f, "artifact_id").f(), strF));
                c1895eBA.c(a(this.f, "synthesized_library_classes_package_prefix").f());
                c1895eBA.a(EnumC3891f.c(a(this.f, "required_compilation_api_level").b()));
                if (this.f.b("shrinker_config")) {
                    C1222Nt c1222NtC = this.f.a("shrinker_config").c();
                    ArrayList arrayList = new ArrayList(c1222NtC.size());
                    Iterator it = c1222NtC.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((AbstractC1249Ot) it.next()).f());
                    }
                    c1895eBA.a(arrayList);
                }
                if (this.f.b("support_all_callbacks_from_library")) {
                    c1895eBA.b(this.f.a("support_all_callbacks_from_library").a());
                }
                consumer.accept(c1895eBA);
                return c1895eBA.a();
            }
            this.b.a(new StringDiagnostic("Unsupported desugared library version: " + strF + ", please upgrade the desugared library to at least version " + s2 + ".", this.e));
            throw null;
        }
        this.b.a(new UnsupportedDesugaredLibraryConfigurationVersionDiagnostic(this.e));
        throw null;
    }

    static void a(C1333Rt c1333Rt, C1747cB.a aVar) {
        if (c1333Rt.b("rewrite_prefix")) {
            Iterator itA = AbstractC1159Lk.a(c1333Rt, "rewrite_prefix");
            while (itA.hasNext()) {
                Map.Entry entry = (Map.Entry) itA.next();
                aVar.e((String) entry.getKey(), ((AbstractC1249Ot) entry.getValue()).f());
            }
        }
        if (c1333Rt.b("retarget_lib_member")) {
            Iterator itA2 = AbstractC1159Lk.a(c1333Rt, "retarget_lib_member");
            while (itA2.hasNext()) {
                Map.Entry entry2 = (Map.Entry) itA2.next();
                aVar.d((String) entry2.getKey(), ((AbstractC1249Ot) entry2.getValue()).f());
            }
        }
        if (c1333Rt.b("backport")) {
            Iterator itA3 = AbstractC1159Lk.a(c1333Rt, "backport");
            while (itA3.hasNext()) {
                Map.Entry entry3 = (Map.Entry) itA3.next();
                aVar.a((String) entry3.getKey(), ((AbstractC1249Ot) entry3.getValue()).f());
            }
        }
        if (c1333Rt.b("emulate_interface")) {
            Iterator itA4 = AbstractC1159Lk.a(c1333Rt, "emulate_interface");
            while (itA4.hasNext()) {
                Map.Entry entry4 = (Map.Entry) itA4.next();
                aVar.c((String) entry4.getKey(), ((AbstractC1249Ot) entry4.getValue()).f());
            }
        }
        if (c1333Rt.b("custom_conversion")) {
            Iterator itA5 = AbstractC1159Lk.a(c1333Rt, "custom_conversion");
            while (itA5.hasNext()) {
                Map.Entry entry5 = (Map.Entry) itA5.next();
                aVar.b((String) entry5.getKey(), ((AbstractC1249Ot) entry5.getValue()).f());
            }
        }
        if (c1333Rt.b("wrapper_conversion")) {
            Iterator it = c1333Rt.a("wrapper_conversion").c().iterator();
            while (it.hasNext()) {
                aVar.c(((AbstractC1249Ot) it.next()).f());
            }
        }
        if (c1333Rt.b("dont_rewrite")) {
            Iterator it2 = c1333Rt.a("dont_rewrite").c().iterator();
            while (it2.hasNext()) {
                aVar.b(((AbstractC1249Ot) it2.next()).f());
            }
        }
        if (c1333Rt.b("dont_retarget_lib_member")) {
            Iterator it3 = c1333Rt.a("dont_retarget_lib_member").c().iterator();
            while (it3.hasNext()) {
                aVar.a(((AbstractC1249Ot) it3.next()).f());
            }
        }
    }
}
