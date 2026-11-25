package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.resolve.SinceKotlinAccessibility;
import org.jetbrains.kotlin.resolve.SinceKotlinUtilKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;

/* JADX INFO: compiled from: ApiVersionCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/ApiVersionCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApiVersionCallChecker implements CallChecker {
    public static final ApiVersionCallChecker INSTANCE = new ApiVersionCallChecker();

    private ApiVersionCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        check(resultingDescriptor, context, reportOn);
    }

    private final void check(CallableDescriptor targetDescriptor, CallCheckerContext context, PsiElement element) {
        PropertyGetterDescriptor getter;
        if (targetDescriptor instanceof FakeCallableDescriptorForObject) {
            return;
        }
        SinceKotlinAccessibility sinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility = SinceKotlinUtilKt.checkSinceKotlinVersionAccessibility(targetDescriptor, context.getLanguageVersionSettings());
        if (sinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility instanceof SinceKotlinAccessibility.NotAccessible) {
            context.getTrace().report(Errors.API_NOT_AVAILABLE.on(element, ((SinceKotlinAccessibility.NotAccessible) sinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility).getVersion().getVersionString(), context.getLanguageVersionSettings().getApiVersion().getVersionString()));
        }
        if (Intrinsics.areEqual(sinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility, SinceKotlinAccessibility.Accessible.INSTANCE) && (targetDescriptor instanceof PropertyDescriptor) && DeprecatedCallChecker.INSTANCE.shouldCheckPropertyGetter$frontend(element) && (getter = ((PropertyDescriptor) targetDescriptor).getGetter()) != null) {
            INSTANCE.check(getter, context, element);
        }
    }
}
