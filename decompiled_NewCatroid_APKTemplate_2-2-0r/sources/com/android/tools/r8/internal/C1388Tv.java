package com.android.tools.r8.internal;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Tv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1388Tv extends AbstractC1256Pa {
    private final InterfaceC2118gw c;
    private String d;

    C1388Tv(InterfaceC2118gw interfaceC2118gw) {
        super(C2422kw.h);
        this.c = interfaceC2118gw;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, int i2, String str, String str2, String str3, String[] strArr) {
        super.a(i, i2, str, str2, str3, strArr);
        this.d = str.replace('/', '.');
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2509m2 a(String str, boolean z) {
        if (z) {
            return null;
        }
        if (str.equals(C2043g2.a.b)) {
            return new C1488Wv(this.c, new Consumer() { // from class: com.android.tools.r8.internal.Tv$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C2953rv) obj);
                }
            });
        }
        if (str.equals(C2043g2.f.a)) {
            return new C2340jw(this.c, new Consumer() { // from class: com.android.tools.r8.internal.Tv$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C2953rv) obj);
                }
            }, C0846Aw.b().a(C2424kx.a(this.d)).a());
        }
        if (str.equals(AbstractC2127h2.a)) {
            return new C1224Nv(this.c, new Consumer() { // from class: com.android.tools.r8.internal.Tv$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((C2953rv) obj);
                }
            }, this.d);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2953rv c2953rv) {
        c2953rv.a(AbstractC2497lw.a(this.d));
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC1899eF a(int i, String str, String str2, String str3, String[] strArr) {
        return new C1462Vv(this.c, this.d, str, str2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2252ii a(int i, String str, String str2, String str3, Object obj) {
        return new C1436Uv(this.c, this.d, str, str2);
    }
}
