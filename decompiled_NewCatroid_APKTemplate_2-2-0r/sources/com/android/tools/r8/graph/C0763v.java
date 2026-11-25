package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1899eF;
import com.android.tools.r8.internal.C3263wA;

/* JADX INFO: renamed from: com.android.tools.r8.graph.v, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0763v extends AbstractC1899eF {
    static final /* synthetic */ boolean l = true;
    private final C0702m j;
    private boolean k;

    public C0763v(AbstractC1899eF abstractC1899eF, C0702m c0702m) {
        super(589824, abstractC1899eF);
        this.k = true;
        this.j = c0702m;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, C3263wA c3263wA) {
        if (i == 0) {
            if (this.k) {
                this.k = false;
                return;
            } else if (!l && !this.j.q()) {
                throw new AssertionError();
            }
        }
        super.b(i, c3263wA);
    }
}
