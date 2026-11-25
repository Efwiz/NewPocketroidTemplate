package com.android.tools.r8.errors;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.references.ClassReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ConstantPoolOverflowDiagnostic extends ClassFileOverflowDiagnostic {
    private final int b;
    private final ClassReference c;

    public ConstantPoolOverflowDiagnostic(Origin origin, ClassReference classReference, int i) {
        super(origin);
        this.c = classReference;
        this.b = i;
    }

    public int getConstantPoolSize() {
        return this.b;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return "Class " + this.c + " too large for class file. Constant pool size was " + getConstantPoolSize() + ".";
    }
}
