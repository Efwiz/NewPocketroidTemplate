package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;

/* JADX INFO: compiled from: TypeAttributeTranslator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J(\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH&¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeAttributeTranslator;", "", "toAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "toAttributes", "annotations", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeAttributeTranslator {
    Annotations toAnnotations(TypeAttributes attributes);

    TypeAttributes toAttributes(Annotations annotations, TypeConstructor typeConstructor, DeclarationDescriptor containingDeclaration);

    /* JADX INFO: compiled from: TypeAttributeTranslator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static /* synthetic */ TypeAttributes toAttributes$default(TypeAttributeTranslator typeAttributeTranslator, Annotations annotations, TypeConstructor typeConstructor, DeclarationDescriptor declarationDescriptor, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toAttributes");
            }
            if ((i & 2) != 0) {
                typeConstructor = null;
            }
            if ((i & 4) != 0) {
                declarationDescriptor = null;
            }
            return typeAttributeTranslator.toAttributes(annotations, typeConstructor, declarationDescriptor);
        }
    }
}
