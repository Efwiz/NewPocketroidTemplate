package org.jetbrains.kotlin.resolve.konan.diagnostics;

import java.util.Iterator;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: NativeHiddenFromObjCInheritanceChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0003H\u0002¨\u0006\u0006"}, d2 = {"checkClassIsHiddenFromObjC", "", "clazz", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "checkContainingClassIsHidden", "currentClass", "frontend.native"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NativeHiddenFromObjCInheritanceCheckerKt {
    private static final boolean checkContainingClassIsHidden(ClassDescriptor classDescriptor) {
        DeclarationDescriptor packageFragment = classDescriptor.getContainingDeclaration();
        ClassDescriptor classDescriptor2 = packageFragment instanceof ClassDescriptor ? (ClassDescriptor) packageFragment : null;
        if (classDescriptor2 == null) {
            return false;
        }
        if (checkClassIsHiddenFromObjC(classDescriptor2)) {
            return true;
        }
        return checkContainingClassIsHidden(classDescriptor2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean checkClassIsHiddenFromObjC(ClassDescriptor classDescriptor) {
        ObjCExportMetaAnnotations objCExportMetaAnnotationsFindObjCExportMetaAnnotations;
        Iterator<AnnotationDescriptor> it = classDescriptor.getAnnotations().iterator();
        while (it.hasNext()) {
            ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(it.next());
            if (annotationClass != null && (objCExportMetaAnnotationsFindObjCExportMetaAnnotations = NativeObjCRefinementAnnotationCheckerKt.findObjCExportMetaAnnotations(annotationClass)) != null && objCExportMetaAnnotationsFindObjCExportMetaAnnotations.getHidesFromObjCAnnotation() != null) {
                return true;
            }
        }
        return checkContainingClassIsHidden(classDescriptor);
    }
}
