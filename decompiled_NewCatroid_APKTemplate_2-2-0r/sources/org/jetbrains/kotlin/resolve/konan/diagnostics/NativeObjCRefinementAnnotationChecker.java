package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.AnnotationChecker;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;

/* JADX INFO: compiled from: NativeObjCRefinementAnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativeObjCRefinementAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "hidesFromObjCSupportedTargets", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "[Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "refinesInSwiftSupportedTargets", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeObjCRefinementAnnotationChecker implements DeclarationChecker {
    public static final NativeObjCRefinementAnnotationChecker INSTANCE = new NativeObjCRefinementAnnotationChecker();
    private static final KotlinTarget[] hidesFromObjCSupportedTargets = {KotlinTarget.FUNCTION, KotlinTarget.PROPERTY, KotlinTarget.CLASS};
    private static final KotlinTarget[] refinesInSwiftSupportedTargets = {KotlinTarget.FUNCTION, KotlinTarget.PROPERTY};

    private NativeObjCRefinementAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        ObjCExportMetaAnnotations objCExportMetaAnnotationsFindObjCExportMetaAnnotations;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
            if (classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS && (objCExportMetaAnnotationsFindObjCExportMetaAnnotations = NativeObjCRefinementAnnotationCheckerKt.findObjCExportMetaAnnotations(descriptor)) != null) {
                AnnotationDescriptor hidesFromObjCAnnotation = objCExportMetaAnnotationsFindObjCExportMetaAnnotations.getHidesFromObjCAnnotation();
                AnnotationDescriptor refinesInSwiftAnnotation = objCExportMetaAnnotationsFindObjCExportMetaAnnotations.getRefinesInSwiftAnnotation();
                if (hidesFromObjCAnnotation == null && refinesInSwiftAnnotation == null) {
                    return;
                }
                if (hidesFromObjCAnnotation != null && refinesInSwiftAnnotation != null) {
                    KtAnnotationEntry sourceFromAnnotation = DescriptorToSourceUtils.getSourceFromAnnotation(refinesInSwiftAnnotation);
                    context.getTrace().report(ErrorsNative.REDUNDANT_SWIFT_REFINEMENT.on(sourceFromAnnotation != null ? sourceFromAnnotation : declaration));
                }
                Set<KotlinTarget> setApplicableTargetSet = AnnotationChecker.INSTANCE.applicableTargetSet(classDescriptor);
                if (hidesFromObjCAnnotation != null && !SetsKt.minus((Set) setApplicableTargetSet, (Object[]) hidesFromObjCSupportedTargets).isEmpty()) {
                    KtAnnotationEntry sourceFromAnnotation2 = DescriptorToSourceUtils.getSourceFromAnnotation(hidesFromObjCAnnotation);
                    context.getTrace().report(ErrorsNative.INVALID_OBJC_HIDES_TARGETS.on(sourceFromAnnotation2 != null ? sourceFromAnnotation2 : declaration));
                }
                if (refinesInSwiftAnnotation == null || SetsKt.minus((Set) setApplicableTargetSet, (Object[]) refinesInSwiftSupportedTargets).isEmpty()) {
                    return;
                }
                KtAnnotationEntry sourceFromAnnotation3 = DescriptorToSourceUtils.getSourceFromAnnotation(refinesInSwiftAnnotation);
                context.getTrace().report(ErrorsNative.INVALID_REFINES_IN_SWIFT_TARGETS.on(sourceFromAnnotation3 != null ? sourceFromAnnotation3 : declaration));
            }
        }
    }
}
