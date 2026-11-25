package com.android.tools.r8.internal;

import java.util.function.BiPredicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C1724bw extends AbstractC1057Hv {
    private final String b;
    AbstractC0998Fw c;

    public C1724bw(String str, AbstractC0998Fw abstractC0998Fw) {
        this.b = str;
        this.c = abstractC0998Fw;
    }

    static boolean a(String str, BiPredicate biPredicate) {
        return !str.startsWith("NON_") ? biPredicate.test(str, Boolean.TRUE) : biPredicate.test(str.substring(4), Boolean.FALSE);
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv
    public final String c() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public void a(String str, String str2, String str3) {
        if (str2.equals(C2043g2.c.a)) {
            if (a(str3, new BiPredicate() { // from class: com.android.tools.r8.internal.bw$$ExternalSyntheticLambda0
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return this.f$0.a((String) obj, (Boolean) obj2);
                }
            })) {
                return;
            }
            super.a(str, str2, str3);
            throw null;
        }
        super.a(str, str2, str3);
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(String str, Boolean bool) {
        G1 g1;
        str.getClass();
        str.hashCode();
        switch (str) {
            case "PROTECTED":
                g1 = G1.PROTECTED;
                break;
            case "PUBLIC":
                g1 = G1.PUBLIC;
                break;
            case "PRIVATE":
                g1 = G1.PRIVATE;
                break;
            case "PACKAGE_PRIVATE":
                g1 = G1.PACKAGE_PRIVATE;
                break;
            default:
                g1 = null;
                break;
        }
        if (g1 != null) {
            this.c.a(g1, bool.booleanValue());
            return true;
        }
        str.hashCode();
        switch (str) {
            case "STATIC":
                this.c.b(bool.booleanValue());
                return true;
            case "SYNTHETIC":
                this.c.c(bool.booleanValue());
                return true;
            case "FINAL":
                this.c.a(bool.booleanValue());
                return true;
            default:
                return false;
        }
    }
}
