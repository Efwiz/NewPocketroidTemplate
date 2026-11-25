package com.android.tools.r8.internal;

import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedMethodReference;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ST extends UT implements RetracedMethodReference.KnownRetracedMethodReference {
    static final /* synthetic */ boolean d = true;
    private final MethodReference c;

    private ST(MethodReference methodReference, OptionalInt optionalInt) {
        super(optionalInt);
        if (!d && methodReference == null) {
            throw new AssertionError();
        }
        this.c = methodReference;
    }

    @Override // com.android.tools.r8.internal.UT
    public final ST a() {
        return this;
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference
    public final RetracedMethodReference.KnownRetracedMethodReference asKnown() {
        return this;
    }

    @Override // java.lang.Comparable
    public final int compareTo(RetracedMethodReference retracedMethodReference) {
        return UT.b.compare(this, retracedMethodReference);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ST.class != obj.getClass()) {
            return false;
        }
        ST st = (ST) obj;
        return this.a == st.a && this.c.equals(st.c);
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference.KnownRetracedMethodReference
    public final List getFormalTypes() {
        return this.c.getFormalTypes();
    }

    @Override // com.android.tools.r8.retrace.RetracedClassMemberReference
    public final RetracedClassReference getHolderClass() {
        return OT.a(this.c.getHolderClass());
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference
    public final String getMethodName() {
        return this.c.getMethodName();
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference.KnownRetracedMethodReference
    public final MethodReference getMethodReference() {
        return this.c;
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference.KnownRetracedMethodReference
    public final TypeReference getReturnType() {
        if (d || !isVoid()) {
            return this.c.getReturnType();
        }
        throw new AssertionError();
    }

    public final int hashCode() {
        return Objects.hash(this.c, this.a);
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference.KnownRetracedMethodReference
    public final boolean isVoid() {
        return this.c.getReturnType() == null;
    }
}
