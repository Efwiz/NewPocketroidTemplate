package com.android.tools.r8.retrace;

import com.android.tools.r8.internal.AbstractC2251ih;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class e extends AbstractC2251ih {
    static final /* synthetic */ boolean b = true;
    private final boolean a;

    public e(boolean z) {
        this.a = z;
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final /* bridge */ /* synthetic */ int a(Object obj) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.tools.r8.internal.AbstractC2251ih
    public final boolean a(RetraceStackTraceElementProxy retraceStackTraceElementProxy, RetraceStackTraceElementProxy retraceStackTraceElementProxy2) {
        if (retraceStackTraceElementProxy == retraceStackTraceElementProxy2) {
            return true;
        }
        if (a(retraceStackTraceElementProxy, retraceStackTraceElementProxy2, new Function() { // from class: com.android.tools.r8.retrace.e$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((RetraceStackTraceElementProxy) obj).hasRetracedClass());
            }
        }, new Function() { // from class: com.android.tools.r8.retrace.e$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return e.a((RetraceStackTraceElementProxy) obj);
            }
        }) || a(retraceStackTraceElementProxy, retraceStackTraceElementProxy2, new Function() { // from class: com.android.tools.r8.retrace.e$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((RetraceStackTraceElementProxy) obj).hasSourceFile());
            }
        }, new Function() { // from class: com.android.tools.r8.retrace.e$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((RetraceStackTraceElementProxy) obj).getSourceFile();
            }
        })) {
            return false;
        }
        if (!b && retraceStackTraceElementProxy.getOriginalItem() != retraceStackTraceElementProxy2.getOriginalItem()) {
            throw new AssertionError();
        }
        if (((this.a || (retraceStackTraceElementProxy.getOriginalItem().hasLineNumber() && retraceStackTraceElementProxy.getOriginalItem().getLineNumber() > 0)) && a(retraceStackTraceElementProxy, retraceStackTraceElementProxy2, new Function() { // from class: com.android.tools.r8.retrace.e$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((RetraceStackTraceElementProxy) obj).hasLineNumber());
            }
        }, new Function() { // from class: com.android.tools.r8.retrace.e$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(((RetraceStackTraceElementProxy) obj).getLineNumber());
            }
        })) || retraceStackTraceElementProxy.hasRetracedMethod() != retraceStackTraceElementProxy2.hasRetracedMethod()) {
            return false;
        }
        if (retraceStackTraceElementProxy.hasRetracedMethod()) {
            RetracedMethodReference retracedMethod = retraceStackTraceElementProxy.getRetracedMethod();
            RetracedMethodReference retracedMethod2 = retraceStackTraceElementProxy2.getRetracedMethod();
            if (retracedMethod.isKnown() != retracedMethod2.isKnown() || !retracedMethod.getMethodName().equals(retracedMethod2.getMethodName())) {
                return false;
            }
            if (this.a && ((retracedMethod.isKnown() && !retracedMethod.asKnown().getMethodReference().toString().equals(retracedMethod2.asKnown().getMethodReference().toString())) || (!retracedMethod.isKnown() && !retracedMethod.getMethodName().equals(retracedMethod2.getMethodName())))) {
                return false;
            }
        }
        if (retraceStackTraceElementProxy.hasRetracedField() != retraceStackTraceElementProxy2.hasRetracedField()) {
            return false;
        }
        if (retraceStackTraceElementProxy.hasRetracedField()) {
            RetracedFieldReference retracedField = retraceStackTraceElementProxy.getRetracedField();
            RetracedFieldReference retracedField2 = retraceStackTraceElementProxy2.getRetracedField();
            if (retracedField.isKnown() != retracedField2.isKnown() || !retracedField.getFieldName().equals(retracedField2.getFieldName())) {
                return false;
            }
            if (this.a && ((retracedField.isKnown() && !retracedField.asKnown().getFieldReference().toString().equals(retracedField2.asKnown().getFieldReference().toString())) || (retracedField.isUnknown() && !retracedField.getFieldName().equals(retracedField2.getFieldName())))) {
                return false;
            }
        }
        if (retraceStackTraceElementProxy.hasRetracedFieldOrReturnType() != retraceStackTraceElementProxy2.hasRetracedFieldOrReturnType()) {
            return false;
        }
        if (retraceStackTraceElementProxy.hasRetracedFieldOrReturnType()) {
            RetracedTypeReference retracedFieldOrReturnType = retraceStackTraceElementProxy.getRetracedFieldOrReturnType();
            RetracedTypeReference retracedFieldOrReturnType2 = retraceStackTraceElementProxy2.getRetracedFieldOrReturnType();
            if (retracedFieldOrReturnType.isVoid() != retracedFieldOrReturnType2.isVoid() || (!retracedFieldOrReturnType.isVoid() && !retracedFieldOrReturnType.getTypeName().equals(retracedFieldOrReturnType2.getTypeName()))) {
                return false;
            }
        }
        if (retraceStackTraceElementProxy.hasRetracedMethodArguments() != retraceStackTraceElementProxy2.hasRetracedMethodArguments()) {
            return false;
        }
        if (retraceStackTraceElementProxy.hasRetracedMethodArguments()) {
            List<RetracedTypeReference> retracedMethodArguments = retraceStackTraceElementProxy.getRetracedMethodArguments();
            List<RetracedTypeReference> retracedMethodArguments2 = retraceStackTraceElementProxy2.getRetracedMethodArguments();
            if (retracedMethodArguments.size() != retracedMethodArguments2.size()) {
                return false;
            }
            for (int i = 0; i < retracedMethodArguments.size(); i++) {
                RetracedTypeReference retracedTypeReference = retracedMethodArguments.get(i);
                RetracedTypeReference retracedTypeReference2 = retracedMethodArguments2.get(i);
                if (retracedTypeReference.isVoid() == retracedTypeReference2.isVoid() && (retracedTypeReference.isVoid() || retracedTypeReference.getTypeName().equals(retracedTypeReference2.getTypeName()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(RetraceStackTraceElementProxy retraceStackTraceElementProxy) {
        return retraceStackTraceElementProxy.getRetracedClass().getTypeName();
    }

    private static boolean a(RetraceStackTraceElementProxy retraceStackTraceElementProxy, RetraceStackTraceElementProxy retraceStackTraceElementProxy2, Function function, Function function2) {
        return Comparator.comparing(function).thenComparing(function2, Comparator.nullsFirst(new e$$ExternalSyntheticLambda6())).compare(retraceStackTraceElementProxy, retraceStackTraceElementProxy2) != 0;
    }
}
