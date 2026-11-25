package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.RetraceClassElement;
import com.android.tools.r8.retrace.RetraceFieldElement;
import com.android.tools.r8.retrace.RetraceResult;
import com.android.tools.r8.retrace.RetracedFieldReference;
import com.android.tools.r8.retrace.RetracedSourceFile;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3061tT implements RetraceFieldElement {
    static final /* synthetic */ boolean e = true;
    private final RT a;
    private final C3130uT b;
    private final C2923rT c;
    private final com.android.tools.r8.naming.Q d;

    private C3061tT(C3130uT c3130uT, C2923rT c2923rT, RT rt, com.android.tools.r8.naming.Q q) {
        this.c = c2923rT;
        this.a = rt;
        this.b = c3130uT;
        this.d = q;
    }

    @Override // com.android.tools.r8.retrace.RetraceFieldElement
    public final RetraceClassElement getClassElement() {
        return this.c;
    }

    @Override // com.android.tools.r8.retrace.RetraceFieldElement
    public final RetracedFieldReference getField() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final RetraceResult getParentResult() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.RetraceFieldElement
    public final RetracedSourceFile getSourceFile() {
        return this.c.getSourceFile();
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final boolean isCompilerSynthesized() {
        com.android.tools.r8.naming.Q q = this.d;
        return q != null && q.g();
    }

    @Override // com.android.tools.r8.retrace.RetraceFieldElement
    public final boolean isUnknown() {
        if (!e) {
            if ((this.d == null) != this.a.isUnknown()) {
                throw new AssertionError();
            }
        }
        return this.a.isUnknown();
    }
}
