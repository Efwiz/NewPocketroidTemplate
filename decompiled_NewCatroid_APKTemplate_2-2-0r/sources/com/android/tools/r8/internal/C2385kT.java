package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.utils.C3970u0;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2385kT extends AbstractC2465lT {
    private final String b;
    private final String[] c;
    static final /* synthetic */ boolean e = true;
    private static final C2385kT d = new C2385kT("LINVALID;", new String[0]);

    private C2385kT(String str, String[] strArr) {
        if (!e && !C3970u0.A(str) && !C3970u0.G(str)) {
            throw new AssertionError();
        }
        this.b = str;
        this.c = strArr;
    }

    public static C2385kT a(C0684j2 c0684j2) {
        return new C2385kT(c0684j2.I0().b1(), com.android.tools.r8.utils.B.a(c0684j2.G0().a, new C2386kT$$ExternalSyntheticLambda0()));
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final com.android.tools.r8.naming.mappinginformation.e b(com.android.tools.r8.naming.mappinginformation.e eVar) {
        if (!e) {
            eVar.getClass();
            if (!(eVar instanceof C2385kT)) {
                throw new AssertionError();
            }
        }
        return this;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final C2385kT g() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2465lT
    public final boolean q() {
        return this != d;
    }

    @Override // com.android.tools.r8.internal.AbstractC2465lT
    protected final String r() {
        return com.android.tools.r8.utils.Y2.a("", Arrays.asList(this.c), com.android.tools.r8.utils.Y2.a.PARENS) + this.b;
    }

    public final String[] s() {
        return this.c;
    }

    public final String t() {
        return this.b;
    }

    public static C2385kT a(String str) {
        String[] strArrE = C3970u0.e(str);
        String strU = C3970u0.u(str);
        boolean zA = C3970u0.A(strU) || C3970u0.G(strU);
        for (String str2 : strArrE) {
            zA &= C3970u0.A(str2);
        }
        if (zA) {
            return new C2385kT(strU, strArrE);
        }
        return d;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final boolean a(com.android.tools.r8.naming.mappinginformation.e eVar) {
        eVar.getClass();
        if (!(eVar instanceof C2385kT)) {
            return true;
        }
        C2385kT c2385kTG = eVar.g();
        return this.b.equals(c2385kTG.b) && Arrays.equals(this.c, c2385kTG.c);
    }
}
