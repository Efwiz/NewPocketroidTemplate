package com.android.tools.r8.internal;

import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Set;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class DC extends HW {
    static final /* synthetic */ boolean j = true;
    private final C0702m a;
    private final C0767v2 b;
    private final C0684j2 c;
    private final C0684j2 d;
    private final C0684j2 e;
    private final C0684j2 f;
    private final C0684j2 g;
    private final C0684j2 h;
    private final C0684j2 i;

    DC(C0702m c0702m) {
        this.a = c0702m;
        C0722p1 c0722p1A = c0702m.a();
        C0767v2 c0767v2 = c0722p1A.N3;
        this.b = c0767v2;
        this.c = c0722p1A.a(c0767v2, c0722p1A.b(c0722p1A.v1, c0722p1A.X1, c0722p1A.A1), "isLoggable");
        C0767v2 c0767v3 = c0722p1A.A1;
        C0767v2 c0767v4 = c0722p1A.X1;
        this.d = c0722p1A.a(c0767v2, c0722p1A.b(c0767v3, c0767v4, c0767v4), RegisterSpec.PREFIX);
        C0767v2 c0767v5 = c0722p1A.A1;
        C0767v2 c0767v6 = c0722p1A.X1;
        this.e = c0722p1A.a(c0767v2, c0722p1A.b(c0767v5, c0767v6, c0767v6), "d");
        C0767v2 c0767v7 = c0722p1A.A1;
        C0767v2 c0767v8 = c0722p1A.X1;
        this.f = c0722p1A.a(c0767v2, c0722p1A.b(c0767v7, c0767v8, c0767v8), ASTExpr.DEFAULT_INDEX_VARIABLE_NAME);
        C0767v2 c0767v9 = c0722p1A.A1;
        C0767v2 c0767v10 = c0722p1A.X1;
        this.g = c0722p1A.a(c0767v2, c0722p1A.b(c0767v9, c0767v10, c0767v10), "w");
        C0767v2 c0767v11 = c0722p1A.A1;
        C0767v2 c0767v12 = c0722p1A.X1;
        this.h = c0722p1A.a(c0767v2, c0722p1A.b(c0767v11, c0767v12, c0767v12), "e");
        C0767v2 c0767v13 = c0722p1A.A1;
        C0767v2 c0767v14 = c0722p1A.X1;
        this.i = c0722p1A.a(c0767v2, c0722p1A.b(c0767v13, c0767v14, c0767v14), "wtf");
    }

    public static boolean a(C0702m c0702m) {
        com.android.tools.r8.shaking.L2 l2D0 = c0702m.Y().D0();
        return l2D0 != null && l2D0.l() >= 2;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2833qB
    public final C0767v2 getType() {
        return this.b;
    }

    /* JADX WARN: Code duplicated, block: B:48:0x008e  */
    @Override // com.android.tools.r8.internal.HW
    public final void a(AbstractC0748t0 abstractC0748t0, B4 b4, C2409kl c2409kl, InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs, Set set, Set set2) {
        int i;
        C0684j2 reference = abstractC0748t0.getReference();
        char cW0 = reference.C0().w0();
        if (cW0 != 'd') {
            if (cW0 != 'e') {
                i = 7;
                if (cW0 != 'i') {
                    if (cW0 != 'v') {
                        if (cW0 != 'w') {
                            i = -1;
                        } else if (reference == this.g) {
                            i = 5;
                        } else if (reference != this.i) {
                            i = -1;
                        }
                    } else if (reference == this.d) {
                        i = 2;
                    } else {
                        i = -1;
                    }
                } else if (reference == this.f) {
                    i = 4;
                } else if (reference == this.c) {
                    S10 s10M = ((S10) ((ArrayList) abstractC1305Qs.J2()).get(1)).m();
                    if (s10M.i() || s10M.A()) {
                        i = -1;
                    } else {
                        AbstractC2412ko abstractC2412koQ = s10M.q();
                        if (abstractC2412koQ.B1()) {
                            int iN2 = abstractC2412koQ.E().N2();
                            if (2 > iN2 || iN2 > 7) {
                                if (!j) {
                                    throw new AssertionError();
                                }
                                i = -1;
                            } else {
                                i = iN2;
                            }
                        } else {
                            i = -1;
                        }
                    }
                } else {
                    i = -1;
                }
            } else if (reference == this.h) {
                i = 6;
            } else {
                i = -1;
            }
        } else if (reference == this.e) {
            i = 3;
        } else {
            i = -1;
        }
        int iL = this.a.Y().D0().l();
        if (2 > i || i > iL) {
            return;
        }
        interfaceC2641no.b(c2409kl);
    }
}
