package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.platform.TargetPlatformKt;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.IsCommonSourceKt;
import org.jetbrains.kotlin.resolve.multiplatform.OptionalAnnotationUtil;

/* JADX INFO: compiled from: OptionalExpectationUsageChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptionalExpectationUsageChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageChecker;", "()V", "check", "", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OptionalExpectationUsageChecker implements ClassifierUsageChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker
    public void check(ClassifierDescriptor targetDescriptor, PsiElement element, ClassifierUsageCheckerContext context) {
        Intrinsics.checkNotNullParameter(targetDescriptor, "targetDescriptor");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(context, "context");
        ClassifierDescriptor classifierDescriptor = targetDescriptor;
        if (OptionalAnnotationUtil.isOptionalAnnotationClass(classifierDescriptor)) {
            if (!UsageCheckerUtilsKt.isUsageAsAnnotationOrImport(element)) {
                context.getTrace().report(Errors.OPTIONAL_DECLARATION_OUTSIDE_OF_ANNOTATION_ENTRY.on(element));
            }
            PsiFile containingFile = element.getContainingFile();
            Intrinsics.checkNotNull(containingFile, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFile");
            if (Intrinsics.areEqual((Object) IsCommonSourceKt.isCommonSource((KtFile) containingFile), (Object) true) || TargetPlatformKt.isCommon(DescriptorUtilsKt.getPlatform(classifierDescriptor))) {
                return;
            }
            context.getTrace().report(Errors.OPTIONAL_DECLARATION_USAGE_IN_NON_COMMON_SOURCE.on(element));
        }
    }
}
