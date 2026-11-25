package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0649f0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0663g5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1766cT {
    static final /* synthetic */ boolean c = true;
    private final AbstractC1476Wj a;
    private final C2157hT b;

    public C1766cT(C0702m c0702m, C2157hT c2157hT) {
        this.a = c0702m.E();
        this.b = c2157hT;
    }

    public final void a(C0658g0 c0658g0) {
        c0658g0.forEach(new cT$$ExternalSyntheticLambda3(this));
    }

    public final void a(C0663g5 c0663g5) {
        c0663g5.d(new cT$$ExternalSyntheticLambda3(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0633d0 c0633d0) {
        a(c0633d0.b);
    }

    private void a(com.android.tools.r8.graph.R0 r0) {
        this.b.a(this.a, r0.a);
        r0.d(new Consumer() { // from class: com.android.tools.r8.internal.cT$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0649f0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0649f0 c0649f0) {
        a(c0649f0.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.B2 b2) {
        switch (b2.N0().ordinal()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 16:
            case 17:
                return;
            case 7:
                ((C0737r2) b2.H0().j1()).d(new Consumer() { // from class: com.android.tools.r8.internal.cT$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a((C0767v2) obj);
                    }
                });
                return;
            case 8:
                C0699l2 c0699l2 = (C0699l2) b2.G0().j1();
                if (c0699l2.d.b()) {
                    this.b.i(c0699l2.t0());
                    return;
                } else {
                    if (!c && !c0699l2.d.h()) {
                        throw new AssertionError();
                    }
                    this.b.i(c0699l2.u0());
                    return;
                }
            case 10:
                this.b.a(this.a, (C0767v2) b2.L0().j1());
                return;
            case 11:
                this.b.i((com.android.tools.r8.graph.Y0) b2.B0().j1());
                return;
            case 12:
                this.b.i((C0684j2) b2.F0().j1());
                return;
            case 13:
                this.b.i((com.android.tools.r8.graph.Y0) b2.A0().j1());
                return;
            case 14:
                b2.v0().d(new Consumer() { // from class: com.android.tools.r8.internal.cT$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a((com.android.tools.r8.graph.B2) obj);
                    }
                });
                return;
            case 15:
                a(b2.u0().b);
                return;
            default:
                throw new C3405y10();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2 c0767v2) {
        this.b.a(this.a, c0767v2);
    }
}
