package org.jetbrains.kotlin.load.java;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaAnnotations;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.structure.JavaWildcardType;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u000e\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f\u001a\f\u0010\r\u001a\u00020\u000e*\u00020\u000fH\u0000¨\u0006\u0010"}, d2 = {"extractNullabilityAnnotationOnBoundedWildcard", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "wildcardType", "Lorg/jetbrains/kotlin/load/java/structure/JavaWildcardType;", "hasErasedValueParameters", "", "memberDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "isJspecifyEnabledInStrictMode", "javaTypeEnhancementState", "Lorg/jetbrains/kotlin/load/java/JavaTypeEnhancementState;", "toDescriptorVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "descriptors.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final DescriptorVisibility toDescriptorVisibility(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "<this>");
        DescriptorVisibility descriptorVisibility = JavaDescriptorVisibilities.toDescriptorVisibility(visibility);
        Intrinsics.checkNotNullExpressionValue(descriptorVisibility, "toDescriptorVisibility(...)");
        return descriptorVisibility;
    }

    public static final boolean isJspecifyEnabledInStrictMode(JavaTypeEnhancementState javaTypeEnhancementState) {
        Intrinsics.checkNotNullParameter(javaTypeEnhancementState, "javaTypeEnhancementState");
        return javaTypeEnhancementState.getGetReportLevelForAnnotation().invoke(JavaNullabilityAnnotationSettingsKt.getJSPECIFY_ANNOTATIONS_PACKAGE()) == ReportLevel.STRICT;
    }

    public static final boolean hasErasedValueParameters(CallableMemberDescriptor memberDescriptor) {
        Intrinsics.checkNotNullParameter(memberDescriptor, "memberDescriptor");
        return (memberDescriptor instanceof FunctionDescriptor) && Intrinsics.areEqual(memberDescriptor.getUserData(JavaMethodDescriptor.HAS_ERASED_VALUE_PARAMETERS), (Object) true);
    }

    public static final AnnotationDescriptor extractNullabilityAnnotationOnBoundedWildcard(LazyJavaResolverContext c, JavaWildcardType wildcardType) {
        AnnotationDescriptor next;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(wildcardType, "wildcardType");
        if (wildcardType.getBound() == null) {
            throw new IllegalArgumentException("Nullability annotations on unbounded wildcards aren't supported".toString());
        }
        Iterator<AnnotationDescriptor> it = new LazyJavaAnnotations(c, wildcardType, false, 4, null).iterator();
        while (it.hasNext()) {
            next = it.next();
            AnnotationDescriptor annotationDescriptor = next;
            for (FqName fqName : JavaNullabilityAnnotationSettingsKt.getRXJAVA3_ANNOTATIONS()) {
                if (Intrinsics.areEqual(annotationDescriptor.getFqName(), fqName)) {
                    return next;
                }
            }
        }
        next = null;
        return next;
    }
}
