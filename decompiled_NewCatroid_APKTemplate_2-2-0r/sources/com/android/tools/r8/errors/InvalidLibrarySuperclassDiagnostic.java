package com.android.tools.r8.errors;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.Y2;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class InvalidLibrarySuperclassDiagnostic implements DesugarDiagnostic {
    static final /* synthetic */ boolean f = true;
    private final Origin a;
    private final List b;
    private final ClassReference c;
    private final ClassReference d;
    private final String e;

    public InvalidLibrarySuperclassDiagnostic(Origin origin, ClassReference classReference, ClassReference classReference2, String str, List<MethodReference> list) {
        boolean z = f;
        if (!z && origin == null) {
            throw new AssertionError();
        }
        if (!z && classReference == null) {
            throw new AssertionError();
        }
        if (!z && classReference2 == null) {
            throw new AssertionError();
        }
        if (!z && str == null) {
            throw new AssertionError();
        }
        this.a = origin;
        this.c = classReference;
        this.d = classReference2;
        this.e = str;
        this.b = list;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        StringBuilder sbAppend = new StringBuilder().append("Superclass `").append(this.d.getTypeName()).append("` of library class `").append(this.c.getTypeName()).append("` is ").append(this.e).append(". A superclass of a library class should be a library class. This is required for the desugaring of ");
        Y2.a(sbAppend, this.b, ", ", Y2.a.NONE);
        return sbAppend.toString();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.a;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return Position.UNKNOWN;
    }
}
