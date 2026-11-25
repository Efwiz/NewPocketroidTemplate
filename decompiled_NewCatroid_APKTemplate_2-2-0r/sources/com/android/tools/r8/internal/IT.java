package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.retrace.RetraceThrownExceptionResult;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class IT implements RetraceThrownExceptionResult {
    private final ClassReference a;
    private final C3634k b;

    IT(ClassReference classReference, C3634k c3634k) {
        this.a = classReference;
        this.b = c3634k;
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final boolean isEmpty() {
        return this.a == null;
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final Stream stream() {
        C3634k c3634k = this.b;
        return Stream.of(new HT(this, OT.a(c3634k == null ? this.a : Reference.classFromTypeName(c3634k.a)), this.b, this.a));
    }
}
