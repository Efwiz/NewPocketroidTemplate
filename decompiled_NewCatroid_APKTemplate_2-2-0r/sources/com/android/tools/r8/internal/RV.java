package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.RetraceStackTraceContext;
import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedFieldReference;
import com.android.tools.r8.retrace.RetracedMethodReference;
import com.android.tools.r8.retrace.RetracedTypeReference;
import com.android.tools.r8.retrace.StackTraceElementProxy;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class RV {
    private final StackTraceElementProxy a;
    private RetracedClassReference b;
    private RetracedMethodReference c;
    private RetracedFieldReference d;
    private RetracedTypeReference e;
    private List f;
    private String g;
    private int h;
    private boolean i;
    private boolean j;
    private RetraceStackTraceContext k;

    private RV(StackTraceElementProxy stackTraceElementProxy) {
        this.h = -1;
        this.a = stackTraceElementProxy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RV a(boolean z) {
        this.i = z || this.i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RV a(Consumer consumer) {
        consumer.accept(this);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RV a(boolean z, Consumer consumer) {
        if (z) {
            consumer.accept(this);
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SV a() {
        RetracedClassReference holderClass = this.b;
        RetracedMethodReference retracedMethodReference = this.c;
        if (retracedMethodReference != null) {
            holderClass = retracedMethodReference.getHolderClass();
        }
        return new SV(this.a, holderClass, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k);
    }
}
