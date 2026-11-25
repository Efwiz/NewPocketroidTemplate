package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1954eu extends AbstractC2412ko {
    static final /* synthetic */ boolean h = true;

    public AbstractC1954eu() {
        super(null);
    }

    public C3332x4 J2() {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.L a(C0702m c0702m, C2409kl c2409kl) {
        return com.android.tools.r8.ir.optimize.L.e();
    }

    public void b(C3332x4 c3332x4) {
        if (!h) {
            throw new AssertionError("We should not change the fallthrough of a JumpInstruction with no fallthrough.");
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean g2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC1954eu j0() {
        return this;
    }

    public AbstractC1954eu(S10 s10) {
        super((S10) null, s10);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    public AbstractC1954eu(List list) {
        super((S10) null, list);
    }
}
