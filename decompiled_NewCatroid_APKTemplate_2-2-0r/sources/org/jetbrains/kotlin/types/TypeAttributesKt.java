package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;

/* JADX INFO: compiled from: TypeAttributes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\n\u0010\u0004\u001a\u00020\u0003*\u00020\u0001\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0003¨\u0006\u0006"}, d2 = {"replaceAnnotations", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "newAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "toDefaultAnnotations", "toDefaultAttributes", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeAttributesKt {
    public static final Annotations toDefaultAnnotations(TypeAttributes typeAttributes) {
        Intrinsics.checkNotNullParameter(typeAttributes, "<this>");
        return DefaultTypeAttributeTranslator.INSTANCE.toAnnotations(typeAttributes);
    }

    public static final TypeAttributes toDefaultAttributes(Annotations annotations) {
        Intrinsics.checkNotNullParameter(annotations, "<this>");
        return TypeAttributeTranslator.DefaultImpls.toAttributes$default(DefaultTypeAttributeTranslator.INSTANCE, annotations, null, null, 6, null);
    }

    public static final TypeAttributes replaceAnnotations(TypeAttributes typeAttributes, Annotations newAnnotations) {
        TypeAttributes typeAttributesRemove;
        Intrinsics.checkNotNullParameter(typeAttributes, "<this>");
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        if (AnnotationsTypeAttributeKt.getAnnotations(typeAttributes) == newAnnotations) {
            return typeAttributes;
        }
        AnnotationsTypeAttribute annotationsAttribute = AnnotationsTypeAttributeKt.getAnnotationsAttribute(typeAttributes);
        if (annotationsAttribute != null && (typeAttributesRemove = typeAttributes.remove(annotationsAttribute)) != null) {
            typeAttributes = typeAttributesRemove;
        }
        return (newAnnotations.iterator().hasNext() || !newAnnotations.isEmpty()) ? typeAttributes.plus(new AnnotationsTypeAttribute(newAnnotations)) : typeAttributes;
    }
}
