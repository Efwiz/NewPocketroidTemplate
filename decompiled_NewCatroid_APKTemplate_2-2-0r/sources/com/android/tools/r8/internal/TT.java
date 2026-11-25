package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedMethodReference;
import java.util.OptionalInt;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class TT extends UT {
    private final GE c;

    private TT(GE ge, OptionalInt optionalInt) {
        super(optionalInt);
        this.c = ge;
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference
    public final /* bridge */ /* synthetic */ RetracedMethodReference.KnownRetracedMethodReference asKnown() {
        return null;
    }

    @Override // java.lang.Comparable
    public final int compareTo(RetracedMethodReference retracedMethodReference) {
        return UT.b.compare(this, retracedMethodReference);
    }

    @Override // com.android.tools.r8.retrace.RetracedClassMemberReference
    public final RetracedClassReference getHolderClass() {
        return OT.a(this.c.getHolderClass());
    }

    @Override // com.android.tools.r8.retrace.RetracedMethodReference
    public final String getMethodName() {
        return this.c.getName();
    }
}
