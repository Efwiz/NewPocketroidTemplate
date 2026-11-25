package com.android.tools.r8.internal;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Uv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1436Uv extends AbstractC2252ii {
    private final InterfaceC2118gw c;
    private final String d;
    private final String e;
    private final String f;

    C1436Uv(InterfaceC2118gw interfaceC2118gw, String str, String str2, String str3) {
        super(C2422kw.h);
        this.c = interfaceC2118gw;
        this.d = str;
        this.e = str2;
        this.f = str3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2953rv c2953rv) {
        c2953rv.a(AbstractC2497lw.a(this.d), this.e, this.f);
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final AbstractC2509m2 a(String str, boolean z) {
        if (z) {
            return null;
        }
        if (str.equals(C2043g2.a.b)) {
            return new C1488Wv(this.c, new Consumer() { // from class: com.android.tools.r8.internal.Uv$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C2953rv) obj);
                }
            });
        }
        if (str.equals(C2043g2.f.a)) {
            return new C2340jw(this.c, new Consumer() { // from class: com.android.tools.r8.internal.Uv$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C2953rv) obj);
                }
            }, C0846Aw.b().a(C2424kx.a(this.d)).a(C3319ww.k().a(AbstractC3244vw.a(this.e)).a(AbstractC3466yw.a(AbstractC3024sx.a(this.f))).a()).a());
        }
        if (!str.equals(AbstractC2127h2.a)) {
            return null;
        }
        return new C1251Ov(this.c, new Consumer() { // from class: com.android.tools.r8.internal.Uv$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C2953rv) obj);
            }
        }, C0846Aw.b().a(C2424kx.a(this.d)).a(C3319ww.k().a(AbstractC3244vw.a(this.e)).a(AbstractC3466yw.a(AbstractC3024sx.a(this.f))).a()).a());
    }
}
