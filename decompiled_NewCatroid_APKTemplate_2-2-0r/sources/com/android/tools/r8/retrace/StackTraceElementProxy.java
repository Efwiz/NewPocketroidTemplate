package com.android.tools.r8.retrace;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.retrace.StackTraceElementProxy;
import java.util.Arrays;
import java.util.function.Consumer;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class StackTraceElementProxy<T, ST extends StackTraceElementProxy<T, ST>> {
    /* JADX INFO: Access modifiers changed from: private */
    public static void a(MappingSupplier mappingSupplier, DiagnosticsHandler diagnosticsHandler, String str) {
        TypeReference typeReferenceTypeFromTypeName = Reference.typeFromTypeName(str);
        if (typeReferenceTypeFromTypeName.isArray()) {
            typeReferenceTypeFromTypeName = typeReferenceTypeFromTypeName.asArray().getBaseType();
        }
        if (typeReferenceTypeFromTypeName.isClass()) {
            mappingSupplier.registerClassUse(diagnosticsHandler, typeReferenceTypeFromTypeName.asClass());
        }
    }

    public abstract ClassReference getClassReference();

    public abstract String getFieldName();

    public abstract String getFieldOrReturnType();

    public abstract int getLineNumber();

    public abstract String getMethodArguments();

    public abstract String getMethodName();

    public abstract String getSourceFile();

    public abstract boolean hasClassName();

    public abstract boolean hasFieldName();

    public abstract boolean hasFieldOrReturnType();

    public abstract boolean hasLineNumber();

    public abstract boolean hasMethodArguments();

    public abstract boolean hasMethodName();

    public abstract boolean hasSourceFile();

    public void registerUses(final MappingSupplier<?> mappingSupplier, final DiagnosticsHandler diagnosticsHandler) {
        if (hasClassName()) {
            mappingSupplier.registerClassUse(diagnosticsHandler, getClassReference());
        }
        if (hasMethodArguments()) {
            Arrays.stream(getMethodArguments().split(",")).forEach(new Consumer() { // from class: com.android.tools.r8.retrace.StackTraceElementProxy$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StackTraceElementProxy.a(mappingSupplier, diagnosticsHandler, (String) obj);
                }
            });
        }
        if (!hasFieldOrReturnType() || getFieldOrReturnType().equals(PsiKeyword.VOID)) {
            return;
        }
        TypeReference typeReferenceTypeFromTypeName = Reference.typeFromTypeName(getFieldOrReturnType());
        if (typeReferenceTypeFromTypeName.isArray()) {
            typeReferenceTypeFromTypeName = typeReferenceTypeFromTypeName.asArray().getBaseType();
        }
        if (typeReferenceTypeFromTypeName.isClass()) {
            mappingSupplier.registerClassUse(diagnosticsHandler, typeReferenceTypeFromTypeName.asClass());
        }
    }

    public abstract T toRetracedItem(RetraceStackTraceElementProxy<T, ST> retraceStackTraceElementProxy, boolean z);
}
