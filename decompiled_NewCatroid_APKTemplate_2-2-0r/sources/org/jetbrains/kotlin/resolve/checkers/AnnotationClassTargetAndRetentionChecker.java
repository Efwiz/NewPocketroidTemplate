package org.jetbrains.kotlin.resolve.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactoryForDeprecation0;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.AnnotationChecker;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: AnnotationClassTargetAndRetentionChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/AnnotationClassTargetAndRetentionChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getPsi", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;)Lcom/intellij/psi/PsiElement;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationClassTargetAndRetentionChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Set<KotlinTarget> setApplicableTargetSetFromTargetAnnotationOrNull;
        KtDeclaration psi;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof ClassDescriptor) && (declaration instanceof KtClassOrObject) && DescriptorUtils.isAnnotationClass(descriptor) && (setApplicableTargetSetFromTargetAnnotationOrNull = AnnotationChecker.INSTANCE.applicableTargetSetFromTargetAnnotationOrNull((ClassDescriptor) descriptor)) != null) {
            KotlinRetention annotationRetention = DescriptorUtilsKt.getAnnotationRetention(descriptor);
            if (annotationRetention == null) {
                annotationRetention = KotlinRetention.RUNTIME;
            }
            if (!setApplicableTargetSetFromTargetAnnotationOrNull.contains(KotlinTarget.EXPRESSION) || annotationRetention == KotlinRetention.SOURCE) {
                return;
            }
            AnnotationDescriptor annotationDescriptorMo6752findAnnotation = descriptor.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.retention);
            AnnotationDescriptor annotationDescriptorMo6752findAnnotation2 = descriptor.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.target);
            BindingTrace trace = context.getTrace();
            DiagnosticFactoryForDeprecation0<PsiElement> diagnosticFactoryForDeprecation0 = Errors.RESTRICTED_RETENTION_FOR_EXPRESSION_ANNOTATION;
            LanguageVersionSettings languageVersionSettings = context.getLanguageVersionSettings();
            if (annotationDescriptorMo6752findAnnotation == null || (psi = getPsi(annotationDescriptorMo6752findAnnotation)) == null) {
                psi = annotationDescriptorMo6752findAnnotation2 != null ? getPsi(annotationDescriptorMo6752findAnnotation2) : null;
                if (psi == null) {
                    psi = declaration;
                }
            }
            trace.report(diagnosticFactoryForDeprecation0.on(languageVersionSettings, psi));
        }
    }

    private final PsiElement getPsi(AnnotationDescriptor annotationDescriptor) {
        return DescriptorToSourceUtils.getSourceFromAnnotation(annotationDescriptor);
    }
}
