package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedFieldReference;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class QT extends RT {
    private final AbstractC1070Ih a;

    private QT(AbstractC1070Ih abstractC1070Ih) {
        super();
        this.a = abstractC1070Ih;
    }

    @Override // com.android.tools.r8.retrace.RetracedFieldReference
    public final /* bridge */ /* synthetic */ RetracedFieldReference.KnownRetracedFieldReference asKnown() {
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || QT.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((QT) obj).a);
    }

    @Override // com.android.tools.r8.retrace.RetracedFieldReference
    public final String getFieldName() {
        return this.a.getName();
    }

    @Override // com.android.tools.r8.retrace.RetracedClassMemberReference
    public final RetracedClassReference getHolderClass() {
        return OT.a(this.a.getHolderClass());
    }

    public final int hashCode() {
        return Objects.hash(this.a);
    }
}
