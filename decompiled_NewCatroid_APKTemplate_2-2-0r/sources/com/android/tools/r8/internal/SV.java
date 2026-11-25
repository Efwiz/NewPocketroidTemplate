package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.RetraceStackTraceContext;
import com.android.tools.r8.retrace.RetraceStackTraceElementProxy;
import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedFieldReference;
import com.android.tools.r8.retrace.RetracedMethodReference;
import com.android.tools.r8.retrace.RetracedTypeReference;
import com.android.tools.r8.retrace.StackTraceElementProxy;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class SV implements RetraceStackTraceElementProxy {
    static final /* synthetic */ boolean l = true;
    private final StackTraceElementProxy a;
    private final RetracedClassReference b;
    private final RetracedMethodReference c;
    private final RetracedFieldReference d;
    private final RetracedTypeReference e;
    private final List f;
    private final String g;
    private final int h;
    private final boolean i;
    private final boolean j;
    private final RetraceStackTraceContext k;

    private SV(StackTraceElementProxy stackTraceElementProxy, RetracedClassReference retracedClassReference, RetracedMethodReference retracedMethodReference, RetracedFieldReference retracedFieldReference, RetracedTypeReference retracedTypeReference, List list, String str, int i, boolean z, boolean z2, RetraceStackTraceContext retraceStackTraceContext) {
        if (!l && stackTraceElementProxy == null) {
            throw new AssertionError();
        }
        this.a = stackTraceElementProxy;
        this.b = retracedClassReference;
        this.c = retracedMethodReference;
        this.d = retracedFieldReference;
        this.e = retracedTypeReference;
        this.f = list;
        this.g = str;
        this.h = i;
        this.i = z;
        this.j = z2;
        this.k = retraceStackTraceContext;
    }

    final RV a() {
        RV rv = new RV(this.a);
        rv.b = this.b;
        rv.c = this.c;
        rv.d = this.d;
        rv.e = this.e;
        rv.f = this.f;
        rv.g = this.g;
        rv.h = this.h;
        rv.i = this.i;
        rv.j = this.j;
        rv.k = this.k;
        return rv;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        int iCompareTo;
        int iCompareTo2;
        int iCompareTo3;
        RetraceStackTraceElementProxy retraceStackTraceElementProxy = (RetraceStackTraceElementProxy) obj;
        if (this != retraceStackTraceElementProxy) {
            int iCompare = Boolean.compare(hasRetracedClass(), retraceStackTraceElementProxy.hasRetracedClass());
            if (iCompare != 0) {
                return iCompare;
            }
            if (hasRetracedClass() && (iCompareTo3 = this.b.getTypeName().compareTo(retraceStackTraceElementProxy.getRetracedClass().getTypeName())) != 0) {
                return iCompareTo3;
            }
            int iCompare2 = Boolean.compare(hasRetracedMethod(), retraceStackTraceElementProxy.hasRetracedMethod());
            if (iCompare2 != 0) {
                return iCompare2;
            }
            if (hasRetracedMethod() && (iCompareTo2 = this.c.compareTo(retraceStackTraceElementProxy.getRetracedMethod())) != 0) {
                return iCompareTo2;
            }
            int iCompare3 = Boolean.compare(hasSourceFile(), retraceStackTraceElementProxy.hasSourceFile());
            if (iCompare3 != 0) {
                return iCompare3;
            }
            if (hasSourceFile() && (iCompareTo = this.g.compareTo(retraceStackTraceElementProxy.getSourceFile())) != 0) {
                return iCompareTo;
            }
            int iCompare4 = Boolean.compare(hasLineNumber(), retraceStackTraceElementProxy.hasLineNumber());
            if (iCompare4 != 0) {
                return iCompare4;
            }
            if (hasLineNumber()) {
                return Integer.compare(this.h, retraceStackTraceElementProxy.getLineNumber());
            }
        }
        return 0;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final RetraceStackTraceContext getContext() {
        return this.k;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final int getLineNumber() {
        return this.h;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final StackTraceElementProxy getOriginalItem() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final RetracedClassReference getRetracedClass() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final RetracedFieldReference getRetracedField() {
        return this.d;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final RetracedTypeReference getRetracedFieldOrReturnType() {
        return this.e;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final RetracedMethodReference getRetracedMethod() {
        return this.c;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final List getRetracedMethodArguments() {
        return this.f;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final String getSourceFile() {
        return this.g;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final boolean hasLineNumber() {
        return this.h != -1;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final boolean hasRetracedClass() {
        return this.b != null;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final boolean hasRetracedField() {
        return this.d != null;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final boolean hasRetracedFieldOrReturnType() {
        return this.e != null;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final boolean hasRetracedMethod() {
        return this.c != null;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final boolean hasRetracedMethodArguments() {
        return this.f != null;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final boolean hasSourceFile() {
        return this.g != null;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final boolean isAmbiguous() {
        return this.i;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxy
    public final boolean isTopFrame() {
        return this.j;
    }
}
