package org.jetbrains.kotlin.js.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.js.PredefinedAnnotation;
import org.jetbrains.kotlin.js.resolve.diagnostics.ErrorsJs;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: nativeAnnotationCheckers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/NativeSetterChecker;", "Lorg/jetbrains/kotlin/js/resolve/AbstractNativeIndexerChecker;", "()V", "additionalCheck", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeSetterChecker extends AbstractNativeIndexerChecker {
    public NativeSetterChecker() {
        super(PredefinedAnnotation.NATIVE_SETTER, "setter", 2);
    }

    @Override // org.jetbrains.kotlin.js.resolve.AbstractNativeIndexerChecker, org.jetbrains.kotlin.js.resolve.AbstractNativeAnnotationsChecker
    public void additionalCheck(KtNamedFunction declaration, FunctionDescriptor descriptor, DiagnosticSink diagnosticHolder) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        super.additionalCheck(declaration, descriptor, diagnosticHolder);
        KotlinType returnType = descriptor.getReturnType();
        if (returnType == null || KotlinBuiltIns.isUnit(returnType)) {
            return;
        }
        List<ValueParameterDescriptor> valueParameters = descriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        if (valueParameters.size() < 2) {
            return;
        }
        KotlinType type = valueParameters.get(1).getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        if (TypeUtilsKt.isSubtypeOf(type, returnType)) {
            return;
        }
        diagnosticHolder.report(ErrorsJs.NATIVE_SETTER_WRONG_RETURN_TYPE.on(declaration));
    }
}
