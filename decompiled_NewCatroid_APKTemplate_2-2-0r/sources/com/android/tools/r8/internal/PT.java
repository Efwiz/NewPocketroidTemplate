package com.android.tools.r8.internal;

import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedFieldReference;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class PT extends RT implements RetracedFieldReference.KnownRetracedFieldReference {
    private final FieldReference a;

    private PT(FieldReference fieldReference) {
        super();
        this.a = fieldReference;
    }

    @Override // com.android.tools.r8.retrace.RetracedFieldReference
    public final RetracedFieldReference.KnownRetracedFieldReference asKnown() {
        return this;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || PT.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((PT) obj).a);
    }

    @Override // com.android.tools.r8.retrace.RetracedFieldReference
    public final String getFieldName() {
        return this.a.getFieldName();
    }

    @Override // com.android.tools.r8.retrace.RetracedFieldReference.KnownRetracedFieldReference
    public final FieldReference getFieldReference() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.RetracedFieldReference.KnownRetracedFieldReference
    public final TypeReference getFieldType() {
        return this.a.getFieldType();
    }

    @Override // com.android.tools.r8.retrace.RetracedClassMemberReference
    public final RetracedClassReference getHolderClass() {
        return OT.a(this.a.getHolderClass());
    }

    public final int hashCode() {
        return Objects.hash(this.a);
    }
}
