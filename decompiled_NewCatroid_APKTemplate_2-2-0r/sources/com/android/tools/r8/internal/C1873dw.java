package com.android.tools.r8.internal;

import java.util.function.BiPredicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1873dw extends C1724bw {
    C1198Mw d;

    public C1873dw(String str, C1198Mw c1198Mw) {
        super(str, c1198Mw);
        this.d = c1198Mw;
    }

    @Override // com.android.tools.r8.internal.C1724bw, com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, String str2, String str3) {
        if (!str2.equals(C2043g2.d.a)) {
            super.a(str, str2, str3);
        }
        if (C1724bw.a(str3, new BiPredicate() { // from class: com.android.tools.r8.internal.dw$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return this.f$0.a((String) obj, (Boolean) obj2);
            }
        })) {
            return;
        }
        super.a(str, C2043g2.c.a, str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(String str, Boolean bool) {
        str.getClass();
        str.hashCode();
        switch (str) {
            case "NATIVE":
                this.d.f(bool.booleanValue());
                return true;
            case "ABSTRACT":
                this.d.d(bool.booleanValue());
                return true;
            case "STRICT_FP":
                this.d.g(bool.booleanValue());
                return true;
            case "SYNCHRONIZED":
                this.d.h(bool.booleanValue());
                return true;
            case "BRIDGE":
                this.d.e(bool.booleanValue());
                return true;
            default:
                return false;
        }
    }
}
