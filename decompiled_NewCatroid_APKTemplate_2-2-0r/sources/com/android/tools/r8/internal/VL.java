package com.android.tools.r8.internal;

import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class VL implements EL {
    static final /* synthetic */ boolean c = true;
    private final Predicate a;
    private int b = -1;

    public VL(Predicate predicate) {
        this.a = predicate;
    }

    @Override // com.android.tools.r8.internal.EL
    public final int a() {
        return 1;
    }

    @Override // com.android.tools.r8.internal.EL
    public final Predicate b() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.EL
    public final int c() {
        return 1;
    }

    @Override // com.android.tools.r8.internal.EL
    public final void a(int i) {
        if (!c && this.b != -1) {
            throw new AssertionError();
        }
        this.b = i;
    }

    public final AbstractC2412ko a(VD vd) {
        return (AbstractC2412ko) ((List) vd.a.get(this.b)).get(0);
    }
}
