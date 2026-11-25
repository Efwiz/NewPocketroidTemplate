package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.retrace.RetracedSourceFile;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class WT implements RetracedSourceFile {
    static final /* synthetic */ boolean c = true;
    private final ClassReference a;
    private final String b;

    WT(ClassReference classReference, String str) {
        if (!c && classReference == null) {
            throw new AssertionError();
        }
        this.a = classReference;
        this.b = str;
    }

    @Override // com.android.tools.r8.retrace.RetracedSourceFile
    public final String getOrInferSourceFile() {
        String str = this.b;
        return str != null ? str : NT.a(this.a.getTypeName(), "", true);
    }

    @Override // com.android.tools.r8.retrace.RetracedSourceFile
    public final String getSourceFile() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.RetracedSourceFile
    public final boolean hasRetraceResult() {
        return this.b != null;
    }
}
