package org.jetbrains.kotlin.resolve.konan.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;

/* JADX INFO: compiled from: NativeThreadLocalChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\u001aN\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0000¨\u0006\u0011"}, d2 = {"check", "", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "annotationFqName", "Lorg/jetbrains/kotlin/name/FqName;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "error", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory0;", "Lorg/jetbrains/kotlin/psi/KtElement;", "successCondition", "Lkotlin/Function1;", "", "frontend.native"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NativeThreadLocalCheckerKt {
    public static final void check(DeclarationChecker declarationChecker, FqName annotationFqName, KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context, DiagnosticFactory0<KtElement> error, Function1<? super DeclarationDescriptor, Boolean> successCondition) {
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation;
        Intrinsics.checkNotNullParameter(declarationChecker, "<this>");
        Intrinsics.checkNotNullParameter(annotationFqName, "annotationFqName");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(error, "error");
        Intrinsics.checkNotNullParameter(successCondition, "successCondition");
        if (successCondition.invoke(descriptor).booleanValue() || (annotationDescriptorMo6752findAnnotation = descriptor.getAnnotations().mo6752findAnnotation(annotationFqName)) == null) {
            return;
        }
        KtAnnotationEntry sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation(annotationDescriptorMo6752findAnnotation);
        context.getTrace().report(error.on(sourceFromAnnotation != null ? sourceFromAnnotation : declaration));
    }
}
