package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: JvmSyntheticApplicabilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/JvmSyntheticApplicabilityChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSyntheticApplicabilityChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtAnnotationEntry sourceFromAnnotation;
        FieldDescriptor delegateField;
        Annotations annotations;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = null;
        PropertyDescriptor propertyDescriptor = descriptor instanceof PropertyDescriptor ? (PropertyDescriptor) descriptor : null;
        if (propertyDescriptor != null && (delegateField = propertyDescriptor.getDelegateField()) != null && (annotations = delegateField.getAnnotations()) != null) {
            annotationDescriptorMo6752findAnnotation = annotations.mo6752findAnnotation(JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME);
        }
        if (annotationDescriptorMo6752findAnnotation == null || (sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation(annotationDescriptorMo6752findAnnotation)) == null) {
            return;
        }
        context.getTrace().report(ErrorsJvm.JVM_SYNTHETIC_ON_DELEGATE.on(sourceFromAnnotation));
    }
}
