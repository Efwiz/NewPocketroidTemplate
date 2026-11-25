package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.EnumC3891f;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ht, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2185ht {
    private final C0767v2 a;
    private final com.android.tools.r8.shaking.L b;
    private final com.android.tools.r8.utils.A1 c;

    public C2185ht(C0702m c0702m, com.android.tools.r8.shaking.L l) {
        this.a = c0702m.a().e("Landroid/hardware/camera2/CameraDevice;");
        this.b = l;
        this.c = c0702m.Y();
    }

    public static void a(C0702m c0702m, com.android.tools.r8.shaking.L l) {
        if (c0702m.Y().B0().a(EnumC3891f.L)) {
            return;
        }
        l.a(new C2185ht(c0702m, l));
    }

    public final void a(C0684j2 c0684j2, com.android.tools.r8.graph.o5 o5Var) {
        if (c0684j2.B0() == this.a) {
            com.android.tools.r8.utils.A1 a1 = this.c;
            if ((a1.f instanceof ClassFileConsumer) || a1.B0().e(EnumC3891f.L)) {
                this.b.h().a(o5Var, new Pj$$ExternalSyntheticLambda0());
            }
        }
    }
}
