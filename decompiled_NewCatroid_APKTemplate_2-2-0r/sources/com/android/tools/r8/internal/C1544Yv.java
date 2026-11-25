package com.android.tools.r8.internal;

import java.util.HashSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Yv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1544Yv extends AbstractC1057Hv {
    private final String b;
    private final InterfaceC2118gw c;
    private final HashSet d = new HashSet();

    public C1544Yv(String str, InterfaceC2118gw interfaceC2118gw) {
        this.b = str;
        this.c = interfaceC2118gw;
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, String str2, String str3) {
        C1651ax.a aVar;
        if (!str2.equals(AbstractC2352k2.a)) {
            super.a(str, str2, str3);
            throw null;
        }
        str3.getClass();
        str3.hashCode();
        switch (str3) {
            case "OBFUSCATION":
                aVar = C1651ax.a.OBFUSCATING;
                break;
            case "SHRINKING":
                aVar = C1651ax.a.SHRINKING;
                break;
            case "ACCESS_MODIFICATION":
                aVar = C1651ax.a.ACCESS_MODIFICATION;
                break;
            case "ANNOTATION_REMOVAL":
                aVar = C1651ax.a.ANNOTATION_REMOVAL;
                break;
            case "OPTIMIZATION":
                aVar = C1651ax.a.OPTIMIZING;
                break;
            default:
                super.a(str, str2, str3);
                throw null;
        }
        this.d.add(aVar);
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv
    public final String c() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        this.c.accept(this.d);
        super.a();
    }
}
