package org.jetbrains.kotlin.extensions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: AnnotationBasedExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&J\u0016\u0010\u0007\u001a\u00020\b*\u00020\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J4\u0010\n\u001a\u00020\b*\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\bH\u0002ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/extensions/AnnotationBasedExtension;", "", "getAnnotationFqNames", "", "", "modifierListOwner", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "hasSpecialAnnotation", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isASpecialAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "specialAnnotations", "visitedAnnotations", "", "allowMetaAnnotations", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AnnotationBasedExtension {
    List<String> getAnnotationFqNames(KtModifierListOwner modifierListOwner);

    default boolean hasSpecialAnnotation(DeclarationDescriptor declarationDescriptor, KtModifierListOwner ktModifierListOwner) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        List<String> annotationFqNames = getAnnotationFqNames(ktModifierListOwner);
        if (annotationFqNames.isEmpty()) {
            annotationFqNames = null;
        }
        if (annotationFqNames == null) {
            return false;
        }
        Annotations annotations = declarationDescriptor.getAnnotations();
        if (!(annotations instanceof Collection) || !((Collection) annotations).isEmpty()) {
            Iterator<AnnotationDescriptor> it = annotations.iterator();
            while (it.hasNext()) {
                if (isASpecialAnnotation$default(this, it.next(), annotationFqNames, null, false, 6, null)) {
                    return true;
                }
            }
        }
        if (declarationDescriptor instanceof ClassDescriptor) {
            Iterator<KotlinType> it2 = TypeUtils.getAllSupertypes(((ClassDescriptor) declarationDescriptor).getDefaultType()).iterator();
            while (it2.hasNext()) {
                ClassifierDescriptor this$0 = it2.next().getConstructor().getThis$0();
                ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
                if (classDescriptor != null) {
                    Annotations annotations2 = classDescriptor.getAnnotations();
                    if (!(annotations2 instanceof Collection) || !((Collection) annotations2).isEmpty()) {
                        Iterator<AnnotationDescriptor> it3 = annotations2.iterator();
                        while (it3.hasNext()) {
                            if (isASpecialAnnotation$default(this, it3.next(), annotationFqNames, null, false, 6, null)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ boolean isASpecialAnnotation$default(AnnotationBasedExtension annotationBasedExtension, AnnotationDescriptor annotationDescriptor, List list, Set set, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: isASpecialAnnotation");
        }
        if ((i & 2) != 0) {
            set = new HashSet();
        }
        if ((i & 4) != 0) {
            z = true;
        }
        return annotationBasedExtension.isASpecialAnnotation(annotationDescriptor, list, set, z);
    }

    private default boolean isASpecialAnnotation(AnnotationDescriptor annotationDescriptor, List<String> list, Set<String> set, boolean z) {
        String strAsString;
        FqName fqName = annotationDescriptor.getFqName();
        if (fqName == null || (strAsString = fqName.asString()) == null || set.contains(strAsString)) {
            return false;
        }
        if (list.contains(strAsString)) {
            return true;
        }
        set.add(strAsString);
        if (z) {
            ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
            if (annotationClass == null) {
                return false;
            }
            Iterator<AnnotationDescriptor> it = annotationClass.getAnnotations().iterator();
            while (it.hasNext()) {
                if (isASpecialAnnotation(it.next(), list, set, true)) {
                    return true;
                }
            }
        }
        set.remove(strAsString);
        return false;
    }
}
