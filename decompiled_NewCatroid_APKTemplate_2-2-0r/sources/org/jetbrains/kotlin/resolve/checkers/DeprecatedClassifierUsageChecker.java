package org.jetbrains.kotlin.resolve.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEnumEntrySuperclassReferenceExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationUtilKt;
import org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo;

/* JADX INFO: compiled from: DeprecatedClassifierUsageChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/DeprecatedClassifierUsageChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageChecker;", "()V", "check", "", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecatedClassifierUsageChecker implements ClassifierUsageChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker
    public void check(ClassifierDescriptor targetDescriptor, PsiElement element, ClassifierUsageCheckerContext context) {
        Intrinsics.checkNotNullParameter(targetDescriptor, "targetDescriptor");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(context, "context");
        if (element.getMyParent() instanceof KtThisExpression) {
            return;
        }
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.NoDeprecationOnDeprecatedEnumEntries) && (element instanceof KtEnumEntrySuperclassReferenceExpression)) {
            BindingContext context2 = context.getTrace().getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall((KtElement) element, context2);
            CallableDescriptor resultingDescriptor = resolvedCall != null ? resolvedCall.getResultingDescriptor() : null;
            ConstructorDescriptor constructorDescriptor = resultingDescriptor instanceof ConstructorDescriptor ? (ConstructorDescriptor) resultingDescriptor : null;
            if (Intrinsics.areEqual(constructorDescriptor != null ? constructorDescriptor.getConstructedClass() : null, targetDescriptor)) {
                return;
            }
        }
        Iterator<DescriptorBasedDeprecationInfo> it = context.getDeprecationResolver().getDeprecations(targetDescriptor).iterator();
        while (it.hasNext()) {
            context.getTrace().report(DeprecationUtilKt.createDeprecationDiagnostic(element, it.next(), context.getLanguageVersionSettings()));
        }
    }
}
