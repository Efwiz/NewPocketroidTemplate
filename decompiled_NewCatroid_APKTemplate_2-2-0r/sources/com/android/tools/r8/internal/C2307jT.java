package com.android.tools.r8.internal;

import com.android.tools.r8.utils.C3970u0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2307jT extends AbstractC2465lT {
    private final String b;
    static final /* synthetic */ boolean d = true;
    private static final C2307jT c = new C2307jT("LINVALID;");

    private C2307jT(String str) {
        if (!d && !C3970u0.A(str)) {
            throw new AssertionError();
        }
        this.b = str;
    }

    public static C2307jT a(com.android.tools.r8.graph.Y0 y0) {
        return new C2307jT(y0.getType().b1());
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final com.android.tools.r8.naming.mappinginformation.e b(com.android.tools.r8.naming.mappinginformation.e eVar) {
        if (!d) {
            eVar.getClass();
            if (!(eVar instanceof C2307jT)) {
                throw new AssertionError();
            }
        }
        return this;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final C2307jT f() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2465lT
    public final boolean q() {
        return this != c;
    }

    @Override // com.android.tools.r8.internal.AbstractC2465lT
    protected final String r() {
        return this.b;
    }

    public final String s() {
        return this.b;
    }

    public static C2307jT a(String str) {
        return C3970u0.A(str) ? new C2307jT(str) : c;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final boolean a(com.android.tools.r8.naming.mappinginformation.e eVar) {
        eVar.getClass();
        return !(eVar instanceof C2307jT) || this.b.equals(eVar.f().b);
    }
}
