package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;

/* JADX INFO: compiled from: TypeAttributeTranslator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00042\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/types/DefaultTypeAttributeTranslator;", "Lorg/jetbrains/kotlin/types/TypeAttributeTranslator;", "()V", "toAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "toAttributes", "annotations", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultTypeAttributeTranslator implements TypeAttributeTranslator {
    public static final DefaultTypeAttributeTranslator INSTANCE = new DefaultTypeAttributeTranslator();

    private DefaultTypeAttributeTranslator() {
    }

    @Override // org.jetbrains.kotlin.types.TypeAttributeTranslator
    public TypeAttributes toAttributes(Annotations annotations, TypeConstructor typeConstructor, DeclarationDescriptor containingDeclaration) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        if (annotations.isEmpty()) {
            return TypeAttributes.INSTANCE.getEmpty();
        }
        return TypeAttributes.INSTANCE.create(CollectionsKt.listOf(new AnnotationsTypeAttribute(annotations)));
    }

    @Override // org.jetbrains.kotlin.types.TypeAttributeTranslator
    public Annotations toAnnotations(TypeAttributes attributes) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        return AnnotationsTypeAttributeKt.getAnnotations(attributes);
    }
}
