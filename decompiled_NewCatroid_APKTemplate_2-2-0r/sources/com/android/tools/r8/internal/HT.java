package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.retrace.RetraceResult;
import com.android.tools.r8.retrace.RetraceStackTraceContext;
import com.android.tools.r8.retrace.RetraceThrownExceptionElement;
import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedSourceFile;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class HT implements RetraceThrownExceptionElement {
    private final IT a;
    private final OT b;
    private final C3634k c;
    private final ClassReference d;

    private HT(IT it, OT ot, C3634k c3634k, ClassReference classReference) {
        this.a = it;
        this.b = ot;
        this.c = c3634k;
        this.d = classReference;
    }

    @Override // com.android.tools.r8.retrace.RetraceThrownExceptionElement
    public final RetraceStackTraceContext getContext() {
        return CT.b().a(this.d).a();
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final RetraceResult getParentResult() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.RetraceThrownExceptionElement
    public final RetracedClassReference getRetracedClass() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.RetraceThrownExceptionElement
    public final RetracedSourceFile getSourceFile() {
        String strQ;
        C3634k c3634k = this.c;
        if (c3634k != null) {
            for (com.android.tools.r8.naming.mappinginformation.e eVar : c3634k.c()) {
                eVar.getClass();
                if (eVar instanceof com.android.tools.r8.naming.mappinginformation.a) {
                    strQ = eVar.a().q();
                }
            }
            strQ = null;
        } else {
            strQ = null;
        }
        return new WT(this.b.getClassReference(), strQ);
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final boolean isCompilerSynthesized() {
        return false;
    }
}
