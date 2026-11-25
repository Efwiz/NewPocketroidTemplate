package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElement;

/* JADX INFO: compiled from: JsRuntimeAnnotationChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsRuntimeAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsRuntimeAnnotationChecker implements DeclarationChecker {
    public static final JsRuntimeAnnotationChecker INSTANCE = new JsRuntimeAnnotationChecker();

    private JsRuntimeAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        KtDeclaration psi;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        for (AnnotationDescriptor annotationDescriptor : descriptor.getAnnotations()) {
            ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
            if (annotationClass != null && DescriptorUtilsKt.getAnnotationRetention(annotationClass) == KotlinRetention.RUNTIME) {
                SourceElement source = annotationDescriptor.getSource();
                PsiSourceElement psiSourceElement = source instanceof PsiSourceElement ? (PsiSourceElement) source : null;
                if (psiSourceElement == null || (psi = psiSourceElement.getPsi()) == null) {
                    psi = declaration;
                }
                if ((descriptor instanceof MemberDescriptor) && DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) descriptor)) {
                    context.getTrace().report(ErrorsJs.RUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION.on(psi));
                } else {
                    context.getTrace().report(ErrorsJs.RUNTIME_ANNOTATION_NOT_SUPPORTED.on(psi));
                }
            }
        }
    }
}
