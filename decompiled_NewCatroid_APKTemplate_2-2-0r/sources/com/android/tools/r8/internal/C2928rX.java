package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rX, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2928rX implements A1 {
    static final /* synthetic */ boolean b = true;
    private final RX a;

    public C2928rX(QX qx) {
        this.a = qx;
    }

    @Override // com.android.tools.r8.internal.A1
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C2855qX c(C3332x4 c3332x4, C2855qX c2855qX) {
        c2855qX.getClass();
        final C2784pX c2784pX = new C2784pX(c2855qX);
        c3332x4.x().forEach(new Consumer() { // from class: com.android.tools.r8.internal.rX$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2784pX, (ML) obj);
            }
        });
        return c2784pX.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2784pX c2784pX, ML ml) {
        QX qx = (QX) this.a;
        qx.getClass();
        if (ml.w().w() && qx.a(ml.w().b().R())) {
            c2784pX.b(ml);
        }
        for (S10 s10 : ml.k0()) {
            if (c2784pX.d().contains(s10)) {
                c2784pX.b(ml);
                c2784pX.a(ml, s10);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:67:0x0108  */
    @Override // com.android.tools.r8.internal.A1
    public final C2855qX a(AbstractC2412ko abstractC2412ko, C2855qX c2855qX) {
        c2855qX.getClass();
        C2784pX c2784pX = new C2784pX(c2855qX);
        boolean zA = ((QX) this.a).a(new lX$$ExternalSyntheticLambda0(c2855qX), abstractC2412ko);
        if (!zA && (abstractC2412ko.L1() || abstractC2412ko.U1() || abstractC2412ko.u2() || abstractC2412ko.r1())) {
            for (S10 s10 : (ArrayList) abstractC2412ko.h1()) {
                if (c2784pX.d().contains(s10)) {
                    c2784pX.a(s10);
                }
            }
        }
        if (zA) {
            if (abstractC2412ko.Y1()) {
                boolean z = b;
                if (!z && ((ArrayList) abstractC2412ko.h1()).isEmpty()) {
                    throw new AssertionError();
                }
                S10 s10T0 = abstractC2412ko.T0();
                if (!c2784pX.d().contains(s10T0)) {
                    if (!z) {
                        S10 s10M = s10T0.m();
                        if (!s10M.J() || !s10M.b.E().T2()) {
                            throw new AssertionError();
                        }
                    }
                    c2784pX.b(s10T0);
                }
            } else if (!b && !abstractC2412ko.p2()) {
                throw new AssertionError();
            }
        }
        if (!b && zA && !abstractC2412ko.p2() && !c2784pX.d().contains(abstractC2412ko.T0())) {
            throw new AssertionError();
        }
        S10 s10B = abstractC2412ko.b();
        if (s10B != null) {
            RX rx = this.a;
            if ((abstractC2412ko instanceof N3) || abstractC2412ko.w1() || ((QX) rx).b(abstractC2412ko)) {
                if (c2784pX.d().contains(abstractC2412ko.T0())) {
                    c2784pX.b(s10B);
                    c2784pX.a(s10B, abstractC2412ko.T0());
                } else {
                    QX qx = (QX) this.a;
                    qx.getClass();
                    if (s10B.w().w() && qx.a(s10B.w().b().R())) {
                        c2784pX.b(s10B);
                    }
                }
            } else {
                QX qx2 = (QX) this.a;
                qx2.getClass();
                if (s10B.w().w()) {
                    c2784pX.b(s10B);
                }
            }
            if (!zA) {
                if (c2784pX.d().contains(abstractC2412ko.b()) && (abstractC2412ko.m1() || abstractC2412ko.U1() || abstractC2412ko.J1() || abstractC2412ko.p1() || abstractC2412ko.w1())) {
                    c2784pX.a(abstractC2412ko.b());
                }
            }
        }
        return c2784pX.a();
    }
}
