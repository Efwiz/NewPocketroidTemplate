package com.android.tools.r8.internal;

import java.util.function.BiPredicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1169Lv extends C1724bw {
    C2954rw d;

    public C1169Lv(String str, C2954rw c2954rw) {
        super(str, c2954rw);
        this.d = c2954rw;
    }

    @Override // com.android.tools.r8.internal.C1724bw, com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, String str2, String str3) {
        if (!str2.equals(C2043g2.b.a)) {
            super.a(str, str2, str3);
        }
        if (C1724bw.a(str3, new BiPredicate() { // from class: com.android.tools.r8.internal.Lv$$ExternalSyntheticLambda0
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
        if (str.equals("VOLATILE")) {
            this.d.e(bool.booleanValue());
            return true;
        }
        if (!str.equals("TRANSIENT")) {
            return false;
        }
        this.d.d(bool.booleanValue());
        return true;
    }
}
