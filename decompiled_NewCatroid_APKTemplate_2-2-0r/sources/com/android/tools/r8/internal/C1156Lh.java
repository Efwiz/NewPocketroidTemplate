package com.android.tools.r8.internal;

import com.android.tools.r8.inspector.FieldInspector;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.Reference;
import java.util.Optional;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1156Lh implements FieldInspector {
    private final C2315ja a;
    private final com.android.tools.r8.graph.T0 b;
    private FieldReference c = null;

    public C1156Lh(C2315ja c2315ja, com.android.tools.r8.graph.T0 t0) {
        this.a = c2315ja;
        this.b = t0;
    }

    @Override // com.android.tools.r8.inspector.FieldInspector
    public final FieldReference getFieldReference() {
        if (this.c == null) {
            this.c = Reference.field(this.a.getClassReference(), this.b.getReference().f.toString(), Reference.typeFromDescriptor(this.b.getReference().h.b1()));
        }
        return this.c;
    }

    @Override // com.android.tools.r8.inspector.FieldInspector
    public final Optional getInitialValue() {
        return (!this.b.E0() || this.b.Y0() == null) ? Optional.empty() : Optional.of(new W10(this.b.Y0(), this.b.getReference().h));
    }

    @Override // com.android.tools.r8.inspector.FieldInspector
    public final boolean isFinal() {
        return this.b.f.h();
    }

    @Override // com.android.tools.r8.inspector.FieldInspector
    public final boolean isStatic() {
        return this.b.f.o();
    }
}
