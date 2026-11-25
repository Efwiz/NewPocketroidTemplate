package org.jetbrains.kotlin.resolve.descriptorUtil;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: annotationsForResolveUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0006\u0010\u0000\u001a\u00020\u0001\u001a\n\u0010\u0002\u001a\u00020\u0003*\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0003*\u00020\u0004\u001a\n\u0010\u0006\u001a\u00020\u0003*\u00020\u0007\u001a\n\u0010\b\u001a\u00020\u0003*\u00020\u0004\u001a\n\u0010\t\u001a\u00020\u0003*\u00020\u0001\u001a\n\u0010\n\u001a\u00020\u0003*\u00020\u0004\u001a\n\u0010\u000b\u001a\u00020\u0003*\u00020\u0007\u001a\n\u0010\f\u001a\u00020\u0003*\u00020\r\u001a\n\u0010\u000e\u001a\u00020\u0003*\u00020\u000f\u001a\n\u0010\u0010\u001a\u00020\u0003*\u00020\u0011¨\u0006\u0012"}, d2 = {"getExactInAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "hasBuilderInferenceAnnotation", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "hasDynamicExtensionAnnotation", "hasExactAnnotation", "Lorg/jetbrains/kotlin/types/KotlinType;", "hasHidesMembersAnnotation", "hasInternalAnnotationForResolve", "hasLowPriorityInOverloadResolution", "hasNoInferAnnotation", "hasOnlyInputTypesAnnotation", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "isExactAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "isInternalAnnotationForResolve", "Lorg/jetbrains/kotlin/name/FqName;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnnotationsForResolveUtilsKt {
    public static final boolean hasNoInferAnnotation(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return kotlinType.getAnnotations().hasAnnotation(AnnotationsForResolveKt.getNO_INFER_ANNOTATION_FQ_NAME());
    }

    public static final boolean hasExactAnnotation(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return kotlinType.getAnnotations().hasAnnotation(AnnotationsForResolveKt.getEXACT_ANNOTATION_FQ_NAME());
    }

    public static final boolean isExactAnnotation(AnnotationDescriptor annotationDescriptor) {
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        return Intrinsics.areEqual(annotationDescriptor.getFqName(), AnnotationsForResolveKt.getEXACT_ANNOTATION_FQ_NAME());
    }

    public static final boolean hasInternalAnnotationForResolve(Annotations annotations) {
        Intrinsics.checkNotNullParameter(annotations, "<this>");
        return annotations.hasAnnotation(AnnotationsForResolveKt.getNO_INFER_ANNOTATION_FQ_NAME()) || annotations.hasAnnotation(AnnotationsForResolveKt.getEXACT_ANNOTATION_FQ_NAME());
    }

    public static final boolean isInternalAnnotationForResolve(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        return Intrinsics.areEqual(fqName, AnnotationsForResolveKt.getNO_INFER_ANNOTATION_FQ_NAME()) || Intrinsics.areEqual(fqName, AnnotationsForResolveKt.getEXACT_ANNOTATION_FQ_NAME());
    }

    public static final boolean hasLowPriorityInOverloadResolution(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return callableDescriptor.getAnnotations().hasAnnotation(AnnotationsForResolveKt.getLOW_PRIORITY_IN_OVERLOAD_RESOLUTION_FQ_NAME());
    }

    public static final boolean hasHidesMembersAnnotation(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return callableDescriptor.getAnnotations().hasAnnotation(AnnotationsForResolveKt.getHIDES_MEMBERS_ANNOTATION_FQ_NAME());
    }

    public static final boolean hasDynamicExtensionAnnotation(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return callableDescriptor.getAnnotations().hasAnnotation(AnnotationsForResolveKt.getDYNAMIC_EXTENSION_FQ_NAME());
    }

    public static final boolean hasOnlyInputTypesAnnotation(TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "<this>");
        return typeParameterDescriptor.getAnnotations().hasAnnotation(AnnotationsForResolveKt.getONLY_INPUT_TYPES_FQ_NAME());
    }

    public static final boolean hasBuilderInferenceAnnotation(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return callableDescriptor.getAnnotations().hasAnnotation(AnnotationsForResolveKt.getBUILDER_INFERENCE_ANNOTATION_FQ_NAME());
    }

    public static final Annotations getExactInAnnotations() {
        return new AnnotationsWithOnly(AnnotationsForResolveKt.getEXACT_ANNOTATION_FQ_NAME());
    }
}
