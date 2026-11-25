package com.android.tools.r8.internal;

import com.android.tools.r8.references.Reference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.retrace.RetracedTypeReference;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class XT implements RetracedTypeReference {
    static final /* synthetic */ boolean b = true;
    private final TypeReference a;

    private XT(TypeReference typeReference) {
        this.a = typeReference;
    }

    static XT a(TypeReference typeReference) {
        return new XT(typeReference);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || XT.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((XT) obj).a);
    }

    @Override // com.android.tools.r8.retrace.RetracedTypeReference
    public final String getTypeName() {
        if (b || !isVoid()) {
            return this.a.getTypeName();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.retrace.RetracedTypeReference
    public final TypeReference getTypeReference() {
        return this.a;
    }

    public final int hashCode() {
        return Objects.hash(this.a);
    }

    @Override // com.android.tools.r8.retrace.RetracedTypeReference
    public final boolean isVoid() {
        return this.a == null;
    }

    @Override // com.android.tools.r8.retrace.RetracedTypeReference
    public final TypeReference toArray(int i) {
        return Reference.array(this.a, i);
    }

    static XT a() {
        return new XT(null);
    }
}
