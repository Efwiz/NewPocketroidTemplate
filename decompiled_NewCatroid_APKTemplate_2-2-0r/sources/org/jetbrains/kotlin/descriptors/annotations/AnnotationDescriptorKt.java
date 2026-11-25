package org.jetbrains.kotlin.descriptors.annotations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: AnnotationDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"abbreviationFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "getAbbreviationFqName", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;)Lorg/jetbrains/kotlin/name/FqName;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnnotationDescriptorKt {
    public static final FqName getAbbreviationFqName(AnnotationDescriptor annotationDescriptor) {
        TypeConstructor constructor;
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor;
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        SimpleType abbreviation = SpecialTypesKt.getAbbreviation(annotationDescriptor.getType());
        if (abbreviation == null || (constructor = abbreviation.getConstructor()) == null || (classifierDescriptorMo7434getDeclarationDescriptor = constructor.mo7434getDeclarationDescriptor()) == null) {
            return null;
        }
        return DescriptorUtilsKt.fqNameOrNull(classifierDescriptorMo7434getDeclarationDescriptor);
    }
}
