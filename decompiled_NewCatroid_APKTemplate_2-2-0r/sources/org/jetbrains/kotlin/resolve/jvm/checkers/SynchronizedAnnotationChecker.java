package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;

/* JADX INFO: compiled from: declarationCheckers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\rH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/SynchronizedAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "isInInterface", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SynchronizedAnnotationChecker implements DeclarationChecker {
    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtAnnotationEntry sourceFromAnnotation;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        AnnotationDescriptor annotationDescriptorFindSynchronizedAnnotation = JvmAnnotationUtilKt.findSynchronizedAnnotation(descriptor);
        if (annotationDescriptorFindSynchronizedAnnotation == null || !(descriptor instanceof FunctionDescriptor) || (sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation(annotationDescriptorFindSynchronizedAnnotation)) == null) {
            return;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
        if (isInInterface(functionDescriptor)) {
            context.getTrace().report(ErrorsJvm.SYNCHRONIZED_IN_INTERFACE.on(sourceFromAnnotation));
            return;
        }
        if (functionDescriptor.getModality() == Modality.ABSTRACT) {
            context.getTrace().report(ErrorsJvm.SYNCHRONIZED_ON_ABSTRACT.on(sourceFromAnnotation));
            return;
        }
        if (functionDescriptor.isInline()) {
            context.getTrace().report(ErrorsJvm.SYNCHRONIZED_ON_INLINE.on(sourceFromAnnotation));
            return;
        }
        if (functionDescriptor.isSuspend()) {
            context.getTrace().report(ErrorsJvm.SYNCHRONIZED_ON_SUSPEND.on(context.getLanguageVersionSettings(), sourceFromAnnotation));
            return;
        }
        DeclarationDescriptor containingDeclaration = functionDescriptor.getDeclarationDescriptor();
        if ((containingDeclaration instanceof ClassDescriptor) && ((ClassDescriptor) containingDeclaration).isValue()) {
            context.getTrace().report(ErrorsJvm.SYNCHRONIZED_ON_VALUE_CLASS.on(sourceFromAnnotation));
        }
    }

    private final boolean isInInterface(FunctionDescriptor descriptor) {
        if (!DescriptorUtils.isInterface(descriptor.getDeclarationDescriptor())) {
            FunctionDescriptor functionDescriptor = descriptor;
            if (!DeclarationCheckersKt.isInsideCompanionObjectOfInterface(DescriptorUtilsKt.getPropertyIfAccessor(functionDescriptor)) || (!AnnotationUtilKt.hasJvmStaticAnnotation(descriptor) && !AnnotationUtilKt.hasJvmStaticAnnotation(DescriptorUtilsKt.getPropertyIfAccessor(functionDescriptor)))) {
                return false;
            }
        }
        return true;
    }
}
