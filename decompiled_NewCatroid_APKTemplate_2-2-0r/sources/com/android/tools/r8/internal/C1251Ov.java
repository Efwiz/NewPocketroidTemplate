package com.android.tools.r8.internal;

import com.google.android.gms.cast.MediaTrack;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ov, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1251Ov extends AbstractC1057Hv {
    private final InterfaceC2118gw b;
    private final C2736ov.a c = C2736ov.a();
    private final C2953rv d;
    private final C2649nv.a e;

    C1251Ov(InterfaceC2118gw interfaceC2118gw, Consumer consumer, C0846Aw c0846Aw) {
        C2953rv c2953rvA = C3395xv.a();
        this.d = c2953rvA;
        C2649nv.a aVarA = C2649nv.a();
        this.e = aVarA;
        this.b = interfaceC2118gw;
        consumer.accept(c2953rvA);
        aVarA.a(C2814px.a().a(C0846Aw.b().a(c0846Aw).a(EnumC3537zw.c).a()).a());
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, Object obj) {
        if (str.equals(MediaTrack.ROLE_DESCRIPTION) && (obj instanceof String)) {
            this.d.b((String) obj);
        } else {
            super.a(str, obj);
            throw null;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv
    public final String c() {
        String str = AbstractC2127h2.a;
        return "KeepForApi";
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str) {
        if (str.equals("additionalTargets")) {
            String str2 = AbstractC2127h2.a;
            return new C1361Sv("KeepForApi", new InterfaceC2118gw() { // from class: com.android.tools.r8.internal.Ov$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.internal.InterfaceC2118gw
                public final void accept(Object obj) {
                    this.f$0.a((C2649nv) obj);
                }
            });
        }
        super.a(str);
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2649nv c2649nv) {
        C2649nv.a aVar = this.e;
        Objects.requireNonNull(aVar);
        c2649nv.a(new Nv$$ExternalSyntheticLambda0(aVar));
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        this.b.accept(this.c.a(this.d.a()).a(this.e.a()).a());
    }
}
