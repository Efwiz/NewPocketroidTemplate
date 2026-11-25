package com.android.tools.r8.internal;

import com.google.android.gms.cast.MediaTrack;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2340jw extends AbstractC1057Hv {
    private final InterfaceC2118gw b;
    private final C2736ov.a c = C2736ov.a();
    private final AbstractC2190hx.a d;
    private final C2953rv e;

    C2340jw(InterfaceC2118gw interfaceC2118gw, Consumer consumer, C0846Aw c0846Aw) {
        AbstractC2190hx.a aVarB = AbstractC2190hx.b();
        this.d = aVarB;
        C2953rv c2953rvA = C3395xv.a();
        this.e = c2953rvA;
        this.b = interfaceC2118gw;
        aVarB.a(C2572mv.a().a(c0846Aw).a());
        consumer.accept(c2953rvA);
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, Object obj) {
        if (str.equals(MediaTrack.ROLE_DESCRIPTION) && (obj instanceof String)) {
            this.e.b((String) obj);
        } else {
            super.a(str, obj);
            throw null;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv
    public final String c() {
        String str = C2043g2.f.a;
        return "UsesReflection";
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str) {
        if (str.equals("value")) {
            String str2 = C2043g2.f.a;
            C2736ov.a aVar = this.c;
            Objects.requireNonNull(aVar);
            return new C1361Sv("UsesReflection", new C1491Wv$$ExternalSyntheticLambda2(aVar));
        }
        if (str.equals("additionalPreconditions")) {
            String str3 = C2043g2.f.a;
            return new C1570Zv("UsesReflection", new InterfaceC2118gw() { // from class: com.android.tools.r8.internal.jw$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.internal.InterfaceC2118gw
                public final void accept(Object obj) {
                    this.f$0.a((AbstractC2190hx) obj);
                }
            });
        }
        super.a(str);
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC2190hx abstractC2190hx) {
        final AbstractC2190hx.a aVar = this.d;
        Objects.requireNonNull(aVar);
        abstractC2190hx.a(new Consumer() { // from class: com.android.tools.r8.internal.jw$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                aVar.a((C2572mv) obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        this.b.accept(this.c.a(this.e.a()).a(this.d.a()).a());
    }
}
