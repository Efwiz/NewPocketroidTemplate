package com.android.tools.r8.internal;

import androidx.exifinterface.media.ExifInterface;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1462Vv extends AbstractC1899eF {
    private final InterfaceC2118gw j;
    private final String k;
    private final String l;
    private final String m;

    C1462Vv(InterfaceC2118gw interfaceC2118gw, String str, String str2, String str3) {
        super(C2422kw.h);
        this.j = interfaceC2118gw;
        this.k = str;
        this.l = str2;
        this.m = str3;
    }

    private C0846Aw d() {
        String strB = CZ.e(this.m).b();
        CZ[] czArrA = CZ.a(this.m);
        C1362Sw c1362SwC = AbstractC1437Uw.c();
        for (CZ cz : czArrA) {
            c1362SwC.a(AbstractC3024sx.a(cz.b()));
        }
        return C0846Aw.b().a(C2424kx.a(this.k)).a(C1463Vw.k().a(AbstractC1309Qw.a(this.l)).a(ExifInterface.GPS_MEASUREMENT_INTERRUPTED.equals(strB) ? AbstractC1545Yw.e() : AbstractC1545Yw.a(AbstractC3024sx.a(strB))).a(c1362SwC.a()).a()).a();
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(String str, boolean z) {
        if (z) {
            return null;
        }
        if (str.equals(C2043g2.a.b)) {
            return new C1488Wv(this.j, new Consumer() { // from class: com.android.tools.r8.internal.Vv$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C2953rv) obj);
                }
            });
        }
        if (str.equals(C2043g2.f.a)) {
            return new C2340jw(this.j, new Consumer() { // from class: com.android.tools.r8.internal.Vv$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C2953rv) obj);
                }
            }, d());
        }
        if (str.equals(AbstractC2127h2.a)) {
            return new C1251Ov(this.j, new Consumer() { // from class: com.android.tools.r8.internal.Vv$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C2953rv) obj);
                }
            }, d());
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2953rv c2953rv) {
        c2953rv.b(AbstractC2497lw.a(this.k), this.l, this.m);
    }
}
