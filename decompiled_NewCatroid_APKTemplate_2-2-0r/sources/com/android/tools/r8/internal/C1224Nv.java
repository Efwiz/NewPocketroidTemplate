package com.android.tools.r8.internal;

import com.google.android.gms.cast.MediaTrack;
import com.reandroid.apkeditor.Util;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1224Nv extends AbstractC1517Xv {
    private final String h;
    private final InterfaceC2118gw i;
    private final C2736ov.a j = C2736ov.a();
    private final C2649nv.a k = C2649nv.a();
    private final C2953rv l;

    C1224Nv(InterfaceC2118gw interfaceC2118gw, Consumer consumer, String str) {
        C2953rv c2953rvA = C3395xv.a();
        this.l = c2953rvA;
        this.h = str;
        this.i = interfaceC2118gw;
        consumer.accept(c2953rvA);
        a("className", (Object) str);
        a(null, AbstractC2277j2.a, "CLASS_AND_MEMBERS");
    }

    @Override // com.android.tools.r8.internal.AbstractC1517Xv, com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, Object obj) {
        if (str.equals(MediaTrack.ROLE_DESCRIPTION) && (obj instanceof String)) {
            this.l.b((String) obj);
        } else {
            super.a(str, obj);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv
    public final String c() {
        String str = AbstractC2127h2.a;
        return "KeepForApi";
    }

    @Override // com.android.tools.r8.internal.AbstractC1517Xv, com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str) {
        if (str.equals("additionalTargets")) {
            String str2 = AbstractC2127h2.a;
            return new C1361Sv("KeepForApi", new InterfaceC2118gw() { // from class: com.android.tools.r8.internal.Nv$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.internal.InterfaceC2118gw
                public final void accept(Object obj) {
                    this.f$0.a((C2649nv) obj);
                }
            });
        }
        return super.a(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2649nv c2649nv) {
        C2649nv.a aVar = this.k;
        Objects.requireNonNull(aVar);
        c2649nv.a(new Nv$$ExternalSyntheticLambda0(aVar));
    }

    @Override // com.android.tools.r8.internal.AbstractC1517Xv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        if (!e().equals(EnumC3537zw.a) && f()) {
            AbstractC2509m2 abstractC2509m2A = a("memberAccess");
            String str = C2043g2.c.a;
            abstractC2509m2A.a(null, str, "PUBLIC");
            abstractC2509m2A.a(null, str, Util.EDIT_TYPE_PROTECTED);
        }
        super.a();
        AbstractC0940Dw abstractC0940DwD = d();
        if (!abstractC0940DwD.c()) {
            C0846Aw c0846AwB = abstractC0940DwD.b();
            if (C2043g2.a(this.h).equals(c0846AwB.d().b().b())) {
                if (!c0846AwB.f().equals(EnumC3537zw.b)) {
                    if (c0846AwB.e().d()) {
                        this.k.a(C2814px.a().a(c0846AwB).a());
                        this.i.accept(this.j.a(this.l.a()).a(this.k.a()).a());
                        return;
                    }
                    throw new C2883qv("@KeepForApi cannot define an 'extends' pattern.");
                }
                throw new C2883qv("@KeepForApi kind must include its class");
            }
            throw new C2883qv("@KeepForApi must reference its class context " + this.h);
        }
        throw new C2883qv("@KeepForApi cannot reference bindings");
    }
}
