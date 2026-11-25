package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.RetraceResult;
import com.android.tools.r8.retrace.RetraceTypeElement;
import com.android.tools.r8.retrace.RetraceTypeResult;
import com.android.tools.r8.retrace.RetracedTypeReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class JT implements RetraceTypeElement {
    private final RetraceTypeResult a;
    private final RetracedTypeReference b;

    private JT(RetraceTypeResult retraceTypeResult, RetracedTypeReference retracedTypeReference) {
        this.a = retraceTypeResult;
        this.b = retracedTypeReference;
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final RetraceResult getParentResult() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.RetraceTypeElement
    public final RetracedTypeReference getType() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final boolean isCompilerSynthesized() {
        return false;
    }
}
