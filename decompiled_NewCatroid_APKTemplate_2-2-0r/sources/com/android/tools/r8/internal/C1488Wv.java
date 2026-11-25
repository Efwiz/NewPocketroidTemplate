package com.android.tools.r8.internal;

import com.google.android.gms.cast.MediaTrack;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1488Wv extends AbstractC1057Hv {
    private final InterfaceC2118gw b;
    private final C2736ov.a c = C2736ov.a();
    private final C2953rv d;

    C1488Wv(InterfaceC2118gw interfaceC2118gw, Consumer consumer) {
        C2953rv c2953rvA = C3395xv.a();
        this.d = c2953rvA;
        this.b = interfaceC2118gw;
        consumer.accept(c2953rvA);
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
        return "KeepEdge";
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(String str) {
        if (str.equals("bindings")) {
            final C2736ov.a aVar = this.c;
            Objects.requireNonNull(aVar);
            return new C1308Qv(new InterfaceC2118gw() { // from class: com.android.tools.r8.internal.Wv$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.internal.InterfaceC2118gw
                public final void accept(Object obj) {
                    aVar.a((C2264iv) obj);
                }
            });
        }
        if (str.equals("preconditions")) {
            final C2736ov.a aVar2 = this.c;
            Objects.requireNonNull(aVar2);
            return new C1570Zv("KeepEdge", new InterfaceC2118gw() { // from class: com.android.tools.r8.internal.Wv$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.internal.InterfaceC2118gw
                public final void accept(Object obj) {
                    aVar2.a((AbstractC2190hx) obj);
                }
            });
        }
        if (str.equals("consequences")) {
            C2736ov.a aVar3 = this.c;
            Objects.requireNonNull(aVar3);
            return new C1361Sv("KeepEdge", new C1491Wv$$ExternalSyntheticLambda2(aVar3));
        }
        super.a(str);
        throw null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        this.b.accept(this.c.a(this.d.a()).a());
    }
}
