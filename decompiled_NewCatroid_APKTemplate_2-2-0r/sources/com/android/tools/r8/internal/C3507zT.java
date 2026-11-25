package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.retrace.RetraceClassElement;
import com.android.tools.r8.retrace.RetraceMethodElement;
import com.android.tools.r8.retrace.RetraceResult;
import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedMethodReference;
import com.android.tools.r8.retrace.RetracedSourceFile;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3507zT implements RetraceMethodElement {
    private final UT a;
    private final AT b;
    private final C2923rT c;
    private final C1676bE d;

    private C3507zT(AT at, C2923rT c2923rT, UT ut, C1676bE c1676bE) {
        this.c = c2923rT;
        this.b = at;
        this.a = ut;
        this.d = c1676bE;
    }

    @Override // com.android.tools.r8.retrace.RetraceMethodElement
    public final RetraceClassElement getClassElement() {
        return this.c;
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final RetraceResult getParentResult() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.RetraceMethodElement
    public final RetracedMethodReference getRetracedMethod() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.RetraceMethodElement
    public final RetracedSourceFile getSourceFile() {
        RetracedClassReference holderClass = this.a.getHolderClass();
        YT yt = this.b.c;
        boolean z = NT.b;
        ClassReference classReference = holderClass.getClassReference();
        return new WT(classReference, yt.a(classReference));
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final boolean isCompilerSynthesized() {
        C1676bE c1676bE = this.d;
        if (c1676bE == null) {
            return false;
        }
        if (c1676bE.c() != null && !this.b.isAmbiguous()) {
            return this.d.c().g();
        }
        List listA = this.d.a();
        return !listA.isEmpty() && ((C3634k.b) com.android.tools.r8.utils.V1.b(listA)).k();
    }

    @Override // com.android.tools.r8.retrace.RetraceMethodElement
    public final boolean isUnknown() {
        return this.a.isUnknown();
    }
}
