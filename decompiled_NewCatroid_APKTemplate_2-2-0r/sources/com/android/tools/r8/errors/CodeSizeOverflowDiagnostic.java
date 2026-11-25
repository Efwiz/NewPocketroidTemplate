package com.android.tools.r8.errors;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.references.MethodReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class CodeSizeOverflowDiagnostic extends ClassFileOverflowDiagnostic {
    private final MethodReference b;
    private final int c;
    private final MethodPosition d;

    public CodeSizeOverflowDiagnostic(Origin origin, MethodReference methodReference, int i) {
        super(origin);
        this.b = methodReference;
        this.c = i;
        this.d = new MethodPosition(methodReference);
    }

    public int getCodeSize() {
        return this.c;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return "Method " + this.b + " too large for class file. Code size was " + getCodeSize() + ".";
    }

    @Override // com.android.tools.r8.errors.ClassFileOverflowDiagnostic, com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.d;
    }
}
