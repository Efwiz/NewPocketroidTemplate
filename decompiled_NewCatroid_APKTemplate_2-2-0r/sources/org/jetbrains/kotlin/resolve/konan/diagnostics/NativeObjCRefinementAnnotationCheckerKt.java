package org.jetbrains.kotlin.resolve.konan.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: NativeObjCRefinementAnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0000¨\u0006\u0003"}, d2 = {"findObjCExportMetaAnnotations", "Lorg/jetbrains/kotlin/resolve/konan/diagnostics/ObjCExportMetaAnnotations;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "frontend.native"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NativeObjCRefinementAnnotationCheckerKt {
    public static final ObjCExportMetaAnnotations findObjCExportMetaAnnotations(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        AnnotationDescriptor annotationDescriptor = null;
        if (!(declarationDescriptor instanceof ClassDescriptor) || ((ClassDescriptor) declarationDescriptor).getKind() != ClassKind.ANNOTATION_CLASS) {
            return null;
        }
        AnnotationDescriptor annotationDescriptor2 = null;
        for (AnnotationDescriptor annotationDescriptor3 : declarationDescriptor.getAnnotations()) {
            FqName fqName = annotationDescriptor3.getFqName();
            if (Intrinsics.areEqual(fqName, NativeObjCRefinementChecker.INSTANCE.getHidesFromObjCFqName())) {
                annotationDescriptor = annotationDescriptor3;
            } else if (Intrinsics.areEqual(fqName, NativeObjCRefinementChecker.INSTANCE.getRefinesInSwiftFqName())) {
                annotationDescriptor2 = annotationDescriptor3;
            }
            if (annotationDescriptor != null && annotationDescriptor2 != null) {
                break;
            }
        }
        return new ObjCExportMetaAnnotations(annotationDescriptor, annotationDescriptor2);
    }
}
