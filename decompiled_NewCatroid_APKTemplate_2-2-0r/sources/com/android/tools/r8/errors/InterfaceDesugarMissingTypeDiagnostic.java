package com.android.tools.r8.errors;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.references.ClassReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class InterfaceDesugarMissingTypeDiagnostic implements DesugarDiagnostic {
    static final /* synthetic */ boolean f = true;
    private final Origin a;
    private final Position b;
    private final ClassReference c;
    private final ClassReference d;
    private final ClassReference e;

    public InterfaceDesugarMissingTypeDiagnostic(Origin origin, Position position, ClassReference classReference, ClassReference classReference2, ClassReference classReference3) {
        boolean z = f;
        if (!z && origin == null) {
            throw new AssertionError();
        }
        if (!z && position == null) {
            throw new AssertionError();
        }
        if (!z && classReference == null) {
            throw new AssertionError();
        }
        if (!z && classReference2 == null) {
            throw new AssertionError();
        }
        this.a = origin;
        this.b = position;
        this.c = classReference;
        this.d = classReference2;
        this.e = classReference3;
    }

    public ClassReference getContextType() {
        return this.d;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        StringBuilder sbAppend = new StringBuilder("Type `").append(this.c.getTypeName()).append("` was not found, it is required for default or static interface methods desugaring of `");
        Position position = this.b;
        if (position != Position.UNKNOWN) {
            sbAppend.append(position.getDescription());
        } else {
            sbAppend.append(this.d.getTypeName());
        }
        sbAppend.append("`");
        if (this.e != null) {
            sbAppend.append(" This missing interface is declared in the direct hierarchy of `").append(this.e).append("`");
        }
        return sbAppend.toString();
    }

    public ClassReference getMissingType() {
        return this.c;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.a;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.b;
    }
}
