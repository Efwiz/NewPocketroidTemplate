package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2291jD {
    static final /* synthetic */ boolean j = true;
    private final C0722p1 a;
    private final C2448lD b;
    private final C2367kD c;
    private final com.android.tools.r8.utils.O2 d;
    private final boolean e;
    private final int f;
    private Origin g;
    private C1333Rt h;
    private AbstractC1214Nl i;

    public C2291jD(C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, boolean z, int i) {
        this.a = c0722p1;
        this.b = new C2448lD(c0722p1, new Function() { // from class: com.android.tools.r8.internal.jD$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((String) obj);
            }
        });
        this.c = new C2367kD(c0722p1, new Function() { // from class: com.android.tools.r8.internal.jD$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((String) obj);
            }
        });
        this.d = o2;
        this.f = i;
        this.e = z;
    }

    private C2404kg b(C1222Nt c1222Nt) {
        a(c1222Nt, 4, "emulated dispatch descriptor");
        C2630ne c2630neA = a(c1222Nt.j(0).c());
        C2630ne c2630neA2 = a(c1222Nt.j(1).c());
        C2630ne c2630neA3 = a(c1222Nt.j(2).c());
        C1333Rt c1333RtD = c1222Nt.j(3).d();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : c1333RtD.entrySet()) {
            linkedHashMap.put(b((String) entry.getKey()), a(((AbstractC1249Ot) entry.getValue()).c()));
        }
        return new C2404kg(c2630neA, c2630neA2, c2630neA3, linkedHashMap);
    }

    final AbstractC1249Ot a(C1333Rt c1333Rt, String str) {
        if (c1333Rt.b(str)) {
            return c1333Rt.a(str);
        }
        this.d.a(new StringDiagnostic("Invalid desugared library configuration. Expected required key '" + str + FormatHelper.QUOTE, this.g));
        throw null;
    }

    public final C2215iD a(Origin origin, String str, C1333Rt c1333Rt) {
        this.g = origin;
        this.h = c1333Rt;
        int iB = a(c1333Rt, "configuration_format_version").b();
        if (iB >= 200 && iB <= 200) {
            C2835qD c2835qD = new C2835qD(EnumC3891f.c(a(this.h, "required_compilation_api_level").b()), a(this.h, "synthesized_library_classes_package_prefix").f(), a(this.h, "identifier").f(), str, this.h.a("support_all_callbacks_from_library").a(), AbstractC1639am.c(a(this.h, "shrinker_config").f()));
            a();
            C2522mD c2522mD = new C2522mD();
            AbstractC1249Ot abstractC1249OtA = a(this.h, "common_flags");
            AbstractC1249Ot abstractC1249OtA2 = a(this.h, "library_flags");
            AbstractC1249Ot abstractC1249OtA3 = a(this.h, "program_flags");
            Iterator it = abstractC1249OtA.c().iterator();
            while (it.hasNext()) {
                C1333Rt c1333RtD = ((AbstractC1249Ot) it.next()).d();
                if (this.f <= a(c1333RtD, "api_level_below_or_equal").b()) {
                    if (c1333RtD.b("api_level_greater_or_equal")) {
                        if (this.f >= c1333RtD.a("api_level_greater_or_equal").b()) {
                            a(c1333RtD, c2522mD);
                        }
                    } else {
                        a(c1333RtD, c2522mD);
                    }
                }
            }
            Iterator it2 = (this.e ? abstractC1249OtA2.c() : abstractC1249OtA3.c()).iterator();
            while (it2.hasNext()) {
                C1333Rt c1333RtD2 = ((AbstractC1249Ot) it2.next()).d();
                if (this.f <= a(c1333RtD2, "api_level_below_or_equal").b()) {
                    if (c1333RtD2.b("api_level_greater_or_equal")) {
                        if (this.f >= c1333RtD2.a("api_level_greater_or_equal").b()) {
                            a(c1333RtD2, c2522mD);
                        }
                    } else {
                        a(c1333RtD2, c2522mD);
                    }
                }
            }
            C2215iD c2215iD = new C2215iD(this.e, c2835qD, c2522mD.a());
            this.g = null;
            return c2215iD;
        }
        this.d.a(new StringDiagnostic("Unsupported machine version number " + iB + " not in [200,200]", origin));
        throw null;
    }

    public final C0767v2 b(String str) {
        if (str.charAt(1) != '$') {
            return this.a.e(C3970u0.H(str));
        }
        String strSubstring = str.substring(2);
        String str2 = (String) this.i.get(str.substring(0, 2));
        if (str2 != null) {
            return this.a.e(C3970u0.H(str2 + "." + strSubstring));
        }
        throw this.d.b("Invalid desugared library specification: Missing package mapping for " + str.substring(0, 2));
    }

    private void a() {
        C1333Rt c1333RtD = a(this.h, "package_map").d();
        final C1187Ml c1187Ml = new C1187Ml();
        c1333RtD.entrySet().forEach(new Consumer() { // from class: com.android.tools.r8.internal.jD$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2291jD.a(c1187Ml, (Map.Entry) obj);
            }
        });
        this.i = c1187Ml.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1187Ml c1187Ml, Map.Entry entry) {
        c1187Ml.a(((AbstractC1249Ot) entry.getValue()).f(), (String) entry.getKey());
    }

    public C2215iD a(com.android.tools.r8.d0 d0Var) {
        Origin origin = d0Var.getOrigin();
        this.g = origin;
        if (!j && origin == null) {
            throw new AssertionError();
        }
        try {
            String strA = d0Var.a();
            C1333Rt c1333RtD = C1386Tt.a(strA).d();
            this.h = c1333RtD;
            return a(this.g, strA, c1333RtD);
        } catch (Exception e) {
            this.d.a(new ExceptionDiagnostic(e, this.g));
            throw null;
        }
    }

    final void a(C1333Rt c1333Rt, C2522mD c2522mD) {
        C0684j2 c0684j2D;
        if (c1333Rt.b("rewrite_type")) {
            Iterator itA = AbstractC1159Lk.a(c1333Rt, "rewrite_type");
            while (itA.hasNext()) {
                Map.Entry entry = (Map.Entry) itA.next();
                c2522mD.c(b((String) entry.getKey()), b(((AbstractC1249Ot) entry.getValue()).f()));
            }
        }
        if (c1333Rt.b("maintain_type")) {
            Iterator it = c1333Rt.a("maintain_type").c().iterator();
            while (it.hasNext()) {
                c2522mD.c(b(((AbstractC1249Ot) it.next()).f()));
            }
        }
        if (c1333Rt.b("rewrite_derived_type_only")) {
            Iterator itA2 = AbstractC1159Lk.a(c1333Rt, "rewrite_derived_type_only");
            while (itA2.hasNext()) {
                Map.Entry entry2 = (Map.Entry) itA2.next();
                c2522mD.b(b((String) entry2.getKey()), b(((AbstractC1249Ot) entry2.getValue()).f()));
            }
        }
        if (c1333Rt.b("static_field_retarget")) {
            Iterator itA3 = AbstractC1159Lk.a(c1333Rt, "static_field_retarget");
            while (itA3.hasNext()) {
                Map.Entry entry3 = (Map.Entry) itA3.next();
                this.c.b((String) entry3.getKey());
                com.android.tools.r8.graph.Y0 y0C = this.c.c();
                this.c.b(((AbstractC1249Ot) entry3.getValue()).f());
                c2522mD.a(y0C, this.c.c());
            }
        }
        if (c1333Rt.b("covariant_retarget")) {
            Iterator itA4 = AbstractC1159Lk.a(c1333Rt, "covariant_retarget");
            while (itA4.hasNext()) {
                Map.Entry entry4 = (Map.Entry) itA4.next();
                c2522mD.a(a((String) entry4.getKey()), a(((AbstractC1249Ot) entry4.getValue()).f()));
            }
        }
        if (c1333Rt.b("static_retarget")) {
            Iterator itA5 = AbstractC1159Lk.a(c1333Rt, "static_retarget");
            while (itA5.hasNext()) {
                Map.Entry entry5 = (Map.Entry) itA5.next();
                c2522mD.d(a((String) entry5.getKey()), a(((AbstractC1249Ot) entry5.getValue()).f()));
            }
        }
        if (c1333Rt.b("non_emulated_virtual_retarget")) {
            Iterator itA6 = AbstractC1159Lk.a(c1333Rt, "non_emulated_virtual_retarget");
            while (itA6.hasNext()) {
                Map.Entry entry6 = (Map.Entry) itA6.next();
                c2522mD.c(a((String) entry6.getKey()), a(((AbstractC1249Ot) entry6.getValue()).f()));
            }
        }
        if (c1333Rt.b("emulated_virtual_retarget")) {
            Iterator itA7 = AbstractC1159Lk.a(c1333Rt, "emulated_virtual_retarget");
            while (itA7.hasNext()) {
                Map.Entry entry7 = (Map.Entry) itA7.next();
                c2522mD.a(a((String) entry7.getKey()), b(((AbstractC1249Ot) entry7.getValue()).c()));
            }
        }
        if (c1333Rt.b("emulated_virtual_retarget_through_emulated_interface")) {
            Iterator itA8 = AbstractC1159Lk.a(c1333Rt, "emulated_virtual_retarget_through_emulated_interface");
            while (itA8.hasNext()) {
                Map.Entry entry8 = (Map.Entry) itA8.next();
                c2522mD.b(a((String) entry8.getKey()), a(((AbstractC1249Ot) entry8.getValue()).f()));
            }
        }
        if (c1333Rt.b("api_generic_types_conversion")) {
            Iterator itA9 = AbstractC1159Lk.a(c1333Rt, "api_generic_types_conversion");
            while (itA9.hasNext()) {
                Map.Entry entry9 = (Map.Entry) itA9.next();
                C0684j2 c0684j2A = a((String) entry9.getKey());
                C1222Nt c1222NtC = ((AbstractC1249Ot) entry9.getValue()).c();
                C0684j2[] c0684j2Arr = new C0684j2[c1222NtC.size()];
                for (int i = 0; i < c1222NtC.size(); i++) {
                    String strF = c1222NtC.j(i).f();
                    if (strF.isEmpty()) {
                        c0684j2D = null;
                    } else {
                        this.b.b(strF);
                        c0684j2D = this.b.d();
                    }
                    c0684j2Arr[i] = c0684j2D;
                }
                c2522mD.a(c0684j2A, c0684j2Arr);
            }
        }
        if (c1333Rt.b("emulated_interface")) {
            Iterator itA10 = AbstractC1159Lk.a(c1333Rt, "emulated_interface");
            while (itA10.hasNext()) {
                Map.Entry entry10 = (Map.Entry) itA10.next();
                C0767v2 c0767v2B = b((String) entry10.getKey());
                C1222Nt c1222NtC2 = ((AbstractC1249Ot) entry10.getValue()).c();
                a(c1222NtC2, 2, "emulated interface descriptor");
                C0767v2 c0767v2B2 = b(c1222NtC2.j(0).f());
                C1333Rt c1333RtD = c1222NtC2.j(1).d();
                IdentityHashMap identityHashMap = new IdentityHashMap();
                for (Map.Entry entry11 : c1333RtD.entrySet()) {
                    identityHashMap.put(a((String) entry11.getKey()), b(((AbstractC1249Ot) entry11.getValue()).c()));
                }
                c2522mD.a(c0767v2B, new C2556mg(c0767v2B2, identityHashMap));
            }
        }
        if (c1333Rt.b("wrapper")) {
            Iterator itA11 = AbstractC1159Lk.a(c1333Rt, "wrapper");
            while (itA11.hasNext()) {
                Map.Entry entry12 = (Map.Entry) itA11.next();
                C0767v2 c0767v2B3 = b((String) entry12.getKey());
                C1222Nt c1222NtC3 = ((AbstractC1249Ot) entry12.getValue()).c();
                C1222Nt c1222NtC4 = c1222NtC3.j(0).c();
                ArrayList arrayList = new ArrayList();
                Iterator it2 = c1222NtC4.iterator();
                while (it2.hasNext()) {
                    arrayList.add(a(((AbstractC1249Ot) it2.next()).f()));
                }
                boolean zA = c1222NtC3.j(1).a();
                C1222Nt c1222NtC5 = c1222NtC3.j(2).c();
                ArrayList arrayList2 = new ArrayList();
                Iterator it3 = c1222NtC5.iterator();
                while (it3.hasNext()) {
                    arrayList2.add(b(((AbstractC1249Ot) it3.next()).f()));
                }
                c2522mD.a(c0767v2B3, new P20(arrayList, arrayList2, zA));
            }
        }
        if (c1333Rt.b("legacy_backport")) {
            Iterator itA12 = AbstractC1159Lk.a(c1333Rt, "legacy_backport");
            while (itA12.hasNext()) {
                Map.Entry entry13 = (Map.Entry) itA12.next();
                c2522mD.a(b((String) entry13.getKey()), b(((AbstractC1249Ot) entry13.getValue()).f()));
            }
        }
        if (c1333Rt.b("dont_retarget")) {
            Iterator it4 = c1333Rt.a("dont_retarget").c().iterator();
            while (it4.hasNext()) {
                c2522mD.a(b(((AbstractC1249Ot) it4.next()).f()));
            }
        }
        if (c1333Rt.b("custom_conversion")) {
            Iterator itA13 = AbstractC1159Lk.a(c1333Rt, "custom_conversion");
            while (itA13.hasNext()) {
                Map.Entry entry14 = (Map.Entry) itA13.next();
                C0767v2 c0767v2B4 = b((String) entry14.getKey());
                C1222Nt c1222NtC6 = ((AbstractC1249Ot) entry14.getValue()).c();
                c2522mD.a(c0767v2B4, new C3004sd(a(c1222NtC6.j(0).f()), a(c1222NtC6.j(1).f())));
            }
        }
        if (c1333Rt.b("amend_library_method")) {
            Iterator it5 = c1333Rt.a("amend_library_method").c().iterator();
            while (it5.hasNext()) {
                this.b.b(((AbstractC1249Ot) it5.next()).f());
                c2522mD.a(this.b.d(), this.b.c());
            }
        }
        if (c1333Rt.b("amend_library_field")) {
            Iterator it6 = c1333Rt.a("amend_library_field").c().iterator();
            while (it6.hasNext()) {
                this.c.b(((AbstractC1249Ot) it6.next()).f());
                c2522mD.a(this.c.c(), this.c.d());
            }
        }
    }

    private void a(C1222Nt c1222Nt, int i, String str) {
        if (c1222Nt.size() == i) {
            return;
        }
        throw this.d.b("Invalid desugared library specification: " + str + "(Json array of size " + c1222Nt.size() + Constant.AFTER_QUTO);
    }

    private C2630ne a(C1222Nt c1222Nt) {
        a(c1222Nt, 2, "derived method");
        C0684j2 c0684j2A = a(c1222Nt.j(0).f());
        int iB = c1222Nt.j(1).b();
        if (iB == -1) {
            return new C2630ne(c0684j2A, null);
        }
        return new C2630ne(c0684j2A, AbstractC2764pD.a(iB));
    }

    private C0684j2 a(String str) {
        this.b.b(str);
        return this.b.d();
    }
}
