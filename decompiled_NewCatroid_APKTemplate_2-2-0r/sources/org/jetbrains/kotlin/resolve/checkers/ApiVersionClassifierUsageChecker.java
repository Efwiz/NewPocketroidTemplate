package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.resolve.SinceKotlinAccessibility;
import org.jetbrains.kotlin.resolve.SinceKotlinUtilKt;

/* JADX INFO: compiled from: ApiVersionClassifierUsageChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ApiVersionClassifierUsageChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageChecker;", "()V", "check", "", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApiVersionClassifierUsageChecker implements ClassifierUsageChecker {
    public static final ApiVersionClassifierUsageChecker INSTANCE = new ApiVersionClassifierUsageChecker();

    private ApiVersionClassifierUsageChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker
    public void check(ClassifierDescriptor targetDescriptor, PsiElement element, ClassifierUsageCheckerContext context) {
        Intrinsics.checkNotNullParameter(targetDescriptor, "targetDescriptor");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(context, "context");
        SinceKotlinAccessibility sinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility = SinceKotlinUtilKt.checkSinceKotlinVersionAccessibility(targetDescriptor, context.getLanguageVersionSettings());
        if (sinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility instanceof SinceKotlinAccessibility.NotAccessible) {
            context.getTrace().report(Errors.API_NOT_AVAILABLE.on(element, ((SinceKotlinAccessibility.NotAccessible) sinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility).getVersion().getVersionString(), context.getLanguageVersionSettings().getApiVersion().getVersionString()));
        }
    }
}
