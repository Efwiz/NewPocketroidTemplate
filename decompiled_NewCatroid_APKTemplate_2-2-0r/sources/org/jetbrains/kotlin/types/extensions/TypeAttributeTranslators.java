package org.jetbrains.kotlin.types.extensions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.types.TypeAttributeTranslator;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: TypeAttributeTranslators.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\"\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;", "", "translators", "", "Lorg/jetbrains/kotlin/types/TypeAttributeTranslator;", "(Ljava/util/List;)V", "getTranslators", "()Ljava/util/List;", "toAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "toAttributes", "annotations", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeAttributeTranslators {
    private final List<TypeAttributeTranslator> translators;

    /* JADX WARN: Multi-variable type inference failed */
    public TypeAttributeTranslators(List<? extends TypeAttributeTranslator> translators) {
        Intrinsics.checkNotNullParameter(translators, "translators");
        this.translators = translators;
    }

    public final List<TypeAttributeTranslator> getTranslators() {
        return this.translators;
    }

    public static /* synthetic */ TypeAttributes toAttributes$default(TypeAttributeTranslators typeAttributeTranslators, Annotations annotations, TypeConstructor typeConstructor, DeclarationDescriptor declarationDescriptor, int i, Object obj) {
        if ((i & 4) != 0) {
            declarationDescriptor = null;
        }
        return typeAttributeTranslators.toAttributes(annotations, typeConstructor, declarationDescriptor);
    }

    public final TypeAttributes toAttributes(Annotations annotations, TypeConstructor typeConstructor, DeclarationDescriptor containingDeclaration) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        List<TypeAttributeTranslator> list = this.translators;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeAttributeTranslator) it.next()).toAttributes(annotations, typeConstructor, containingDeclaration));
        }
        return TypeAttributes.INSTANCE.create(CollectionsKt.flatten(arrayList));
    }

    public final Annotations toAnnotations(TypeAttributes attributes) {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        List<TypeAttributeTranslator> list = this.translators;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeAttributeTranslator) it.next()).toAnnotations(attributes));
        }
        return Annotations.INSTANCE.create(CollectionsKt.flatten(arrayList));
    }
}
