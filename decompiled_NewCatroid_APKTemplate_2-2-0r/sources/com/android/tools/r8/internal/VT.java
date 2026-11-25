package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.RetracedMethodReference;
import com.android.tools.r8.retrace.RetracedSingleFrame;
import com.android.tools.r8.retrace.RetracedSourceFile;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class VT implements RetracedSingleFrame {
    private final C3290wT a;
    private final RetracedMethodReference b;
    private final int c;

    private VT(C3290wT c3290wT, UT ut, int i) {
        this.a = c3290wT;
        this.b = ut;
        this.c = i;
    }

    static VT a(C3290wT c3290wT, UT ut, int i) {
        return new VT(c3290wT, ut, i);
    }

    @Override // com.android.tools.r8.retrace.RetracedSingleFrame
    public final int getIndex() {
        return this.c;
    }

    @Override // com.android.tools.r8.retrace.RetracedSingleFrame
    public final RetracedMethodReference getMethodReference() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.RetracedSingleFrame
    public final RetracedSourceFile getSourceFile() {
        return this.a.getSourceFile(this.b);
    }
}
