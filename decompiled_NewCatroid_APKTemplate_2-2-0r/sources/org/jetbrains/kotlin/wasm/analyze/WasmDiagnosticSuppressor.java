package org.jetbrains.kotlin.wasm.analyze;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor;
import org.jetbrains.kotlin.wasm.util.JsCodeUtilsKt;

/* JADX INFO: compiled from: WasmDiagnosticSuppressor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/wasm/analyze/WasmDiagnosticSuppressor;", "Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;", "()V", "shouldReportNoBody", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "shouldReportUnusedParameter", "parameter", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "wasm.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmDiagnosticSuppressor implements PlatformDiagnosticSuppressor {
    public static final WasmDiagnosticSuppressor INSTANCE = new WasmDiagnosticSuppressor();

    @Override // org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor
    public boolean shouldReportNoBody(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return true;
    }

    private WasmDiagnosticSuppressor() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor
    public boolean shouldReportUnusedParameter(VariableDescriptor parameter, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        DeclarationDescriptor containingDeclaration = parameter.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (containingDeclaration instanceof FunctionDescriptor) {
            return !JsCodeUtilsKt.hasValidJsCodeBody((FunctionDescriptor) containingDeclaration, bindingContext);
        }
        return true;
    }
}
