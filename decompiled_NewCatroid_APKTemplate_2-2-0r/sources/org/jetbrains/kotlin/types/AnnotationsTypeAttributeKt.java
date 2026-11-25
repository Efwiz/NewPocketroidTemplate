package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;

/* JADX INFO: compiled from: AnnotationsTypeAttribute.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"}, d2 = {"annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "getAnnotations", "(Lorg/jetbrains/kotlin/types/TypeAttributes;)Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "annotationsAttribute", "Lorg/jetbrains/kotlin/types/AnnotationsTypeAttribute;", "getAnnotationsAttribute", "(Lorg/jetbrains/kotlin/types/TypeAttributes;)Lorg/jetbrains/kotlin/types/AnnotationsTypeAttribute;", "annotationsAttribute$delegate", "Lkotlin/properties/ReadOnlyProperty;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnnotationsTypeAttributeKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(AnnotationsTypeAttributeKt.class, "descriptors"), "annotationsAttribute", "getAnnotationsAttribute(Lorg/jetbrains/kotlin/types/TypeAttributes;)Lorg/jetbrains/kotlin/types/AnnotationsTypeAttribute;"))};
    private static final ReadOnlyProperty annotationsAttribute$delegate;

    static {
        ReadOnlyProperty readOnlyPropertyGenerateNullableAccessor = TypeAttributes.INSTANCE.generateNullableAccessor(Reflection.getOrCreateKotlinClass(AnnotationsTypeAttribute.class));
        Intrinsics.checkNotNull(readOnlyPropertyGenerateNullableAccessor, "null cannot be cast to non-null type kotlin.properties.ReadOnlyProperty<org.jetbrains.kotlin.types.TypeAttributes, T of org.jetbrains.kotlin.types.TypeAttributes.Companion.attributeAccessor?>");
        annotationsAttribute$delegate = readOnlyPropertyGenerateNullableAccessor;
    }

    public static final AnnotationsTypeAttribute getAnnotationsAttribute(TypeAttributes typeAttributes) {
        Intrinsics.checkNotNullParameter(typeAttributes, "<this>");
        return (AnnotationsTypeAttribute) annotationsAttribute$delegate.getValue(typeAttributes, $$delegatedProperties[0]);
    }

    public static final Annotations getAnnotations(TypeAttributes typeAttributes) {
        Annotations annotations;
        Intrinsics.checkNotNullParameter(typeAttributes, "<this>");
        AnnotationsTypeAttribute annotationsAttribute = getAnnotationsAttribute(typeAttributes);
        return (annotationsAttribute == null || (annotations = annotationsAttribute.getAnnotations()) == null) ? Annotations.INSTANCE.getEMPTY() : annotations;
    }
}
