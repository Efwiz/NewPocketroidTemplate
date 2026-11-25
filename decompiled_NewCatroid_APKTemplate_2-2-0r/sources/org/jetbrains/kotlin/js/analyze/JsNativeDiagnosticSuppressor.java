package org.jetbrains.kotlin.js.analyze;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor;

/* JADX INFO: compiled from: suppressWarnings.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/js/analyze/JsNativeDiagnosticSuppressor;", "Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;", "()V", "shouldReportNoBody", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "shouldReportUnusedParameter", "parameter", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsNativeDiagnosticSuppressor implements PlatformDiagnosticSuppressor {
    public static final JsNativeDiagnosticSuppressor INSTANCE = new JsNativeDiagnosticSuppressor();

    private JsNativeDiagnosticSuppressor() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor
    public boolean shouldReportUnusedParameter(VariableDescriptor parameter, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        return !SuppressWarningsKt.isLexicallyInsideJsNative(parameter);
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor
    public boolean shouldReportNoBody(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return !SuppressWarningsKt.isLexicallyInsideJsNative(descriptor);
    }
}
