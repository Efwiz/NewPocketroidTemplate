package org.jetbrains.kotlin.resolve.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;

/* JADX INFO: compiled from: VolatileAnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/VolatileAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "CONCURRENT_VOLATILE_ANNOTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "JVM_VOLATILE_ANNOTATION_FQ_NAME", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "findVolatileAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FieldDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VolatileAnnotationChecker implements DeclarationChecker {
    public static final VolatileAnnotationChecker INSTANCE = new VolatileAnnotationChecker();
    private static final FqName JVM_VOLATILE_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.Volatile");
    private static final FqName CONCURRENT_VOLATILE_ANNOTATION_FQ_NAME = new FqName("kotlin.concurrent.Volatile");

    private VolatileAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtAnnotationEntry sourceFromAnnotation;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof PropertyDescriptor) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
            FieldDescriptor backingField = propertyDescriptor.getBackingField();
            AnnotationDescriptor annotationDescriptorFindVolatileAnnotation = backingField != null ? findVolatileAnnotation(backingField) : null;
            if (annotationDescriptorFindVolatileAnnotation != null && !propertyDescriptor.isVar()) {
                KtAnnotationEntry sourceFromAnnotation2 = DescriptorToSourceUtils.getSourceFromAnnotation(annotationDescriptorFindVolatileAnnotation);
                if (sourceFromAnnotation2 == null) {
                    return;
                } else {
                    context.getTrace().report(Errors.VOLATILE_ON_VALUE.on(sourceFromAnnotation2));
                }
            }
            FieldDescriptor delegateField = propertyDescriptor.getDelegateField();
            AnnotationDescriptor annotationDescriptorFindVolatileAnnotation2 = delegateField != null ? findVolatileAnnotation(delegateField) : null;
            if (annotationDescriptorFindVolatileAnnotation2 == null || (sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation(annotationDescriptorFindVolatileAnnotation2)) == null) {
                return;
            }
            context.getTrace().report(Errors.VOLATILE_ON_DELEGATE.on(sourceFromAnnotation));
        }
    }

    private final AnnotationDescriptor findVolatileAnnotation(FieldDescriptor fieldDescriptor) {
        AnnotationDescriptor next;
        Iterator<AnnotationDescriptor> it = fieldDescriptor.getAnnotations().iterator();
        while (it.hasNext()) {
            next = it.next();
            AnnotationDescriptor annotationDescriptor = next;
            if (Intrinsics.areEqual(annotationDescriptor.getFqName(), JVM_VOLATILE_ANNOTATION_FQ_NAME) || Intrinsics.areEqual(annotationDescriptor.getFqName(), CONCURRENT_VOLATILE_ANNOTATION_FQ_NAME)) {
                return next;
            }
        }
        next = null;
        return next;
    }
}
