package com.android.tools.r8.errors;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.Y2;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class DuplicateTypesDiagnostic implements Diagnostic {
    static final /* synthetic */ boolean c = true;
    private final ClassReference a;
    private final Collection b;

    public DuplicateTypesDiagnostic(ClassReference classReference, Collection<Origin> collection) {
        boolean z = c;
        if (!z && classReference == null) {
            throw new AssertionError();
        }
        if (!z && collection.size() <= 1) {
            throw new AssertionError();
        }
        this.a = classReference;
        this.b = collection;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return "Type " + C3970u0.b(this.a.getDescriptor()) + " is defined multiple times: " + Y2.a(", ", this.b);
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return (Origin) this.b.iterator().next();
    }

    public Collection<Origin> getOrigins() {
        return this.b;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return Position.UNKNOWN;
    }

    public ClassReference getType() {
        return this.a;
    }
}
