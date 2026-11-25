package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class C6 extends B7 {
    static final /* synthetic */ boolean c = true;
    private final EnumC2523mE b;

    C6(EnumC2523mE enumC2523mE) {
        if (!c && !enumC2523mE.a()) {
            throw new AssertionError();
        }
        this.b = enumC2523mE;
    }

    public final EnumC2523mE T() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean x() {
        return true;
    }

    final C0767v2 a(C0722p1 c0722p1) {
        switch (this.b) {
            case OBJECT:
                return c0722p1.c2;
            case BOOLEAN_OR_BYTE:
                return c0722p1.J1;
            case CHAR:
                return c0722p1.G1;
            case SHORT:
                return c0722p1.L1;
            case INT:
                return c0722p1.J1;
            case FLOAT:
                return c0722p1.I1;
            case LONG:
                return c0722p1.K1;
            case DOUBLE:
                return c0722p1.H1;
            default:
                throw new C3405y10("Unexpected type: " + this.b);
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c2) {
        com.android.tools.r8.graph.C.a(this, b7);
        return 0;
    }
}
