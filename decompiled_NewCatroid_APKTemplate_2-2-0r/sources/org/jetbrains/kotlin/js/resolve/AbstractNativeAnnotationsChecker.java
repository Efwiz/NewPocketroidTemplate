package org.jetbrains.kotlin.js.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.js.PredefinedAnnotation;
import org.jetbrains.kotlin.js.resolve.diagnostics.ErrorsJs;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;

/* JADX INFO: compiled from: nativeAnnotationCheckers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b \u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/AbstractNativeAnnotationsChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "requiredAnnotation", "Lorg/jetbrains/kotlin/js/PredefinedAnnotation;", "(Lorg/jetbrains/kotlin/js/PredefinedAnnotation;)V", "additionalCheck", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "check", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractNativeAnnotationsChecker implements DeclarationChecker {
    private final PredefinedAnnotation requiredAnnotation;

    public void additionalCheck(KtNamedFunction declaration, FunctionDescriptor descriptor, DiagnosticSink diagnosticHolder) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
    }

    public AbstractNativeAnnotationsChecker(PredefinedAnnotation requiredAnnotation) {
        Intrinsics.checkNotNullParameter(requiredAnnotation, "requiredAnnotation");
        this.requiredAnnotation = requiredAnnotation;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = descriptor.getAnnotations().mo6752findAnnotation(this.requiredAnnotation.getFqName());
        if (annotationDescriptorMo6752findAnnotation != null && (declaration instanceof KtNamedFunction) && (descriptor instanceof FunctionDescriptor)) {
            boolean z = (DescriptorUtils.isTopLevelDeclaration(descriptor) || Intrinsics.areEqual(((FunctionDescriptor) descriptor).getVisibility(), DescriptorVisibilities.LOCAL)) ? false : true;
            boolean zIsExtension = DescriptorUtils.isExtension((CallableDescriptor) descriptor);
            if ((z && (zIsExtension || !AnnotationsUtils.isNativeObject(descriptor))) || (!z && !zIsExtension)) {
                context.getTrace().report(ErrorsJs.NATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN.on(declaration, annotationDescriptorMo6752findAnnotation.getType()));
            }
            additionalCheck((KtNamedFunction) declaration, (FunctionDescriptor) descriptor, context.getTrace());
        }
    }
}
