package org.jetbrains.kotlin.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: typeParameterUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002\u001a \u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006H\u0002\u001a\u0010\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000e*\u00020\u0004¨\u0006\u000f"}, d2 = {"buildPossiblyInnerType", "Lorg/jetbrains/kotlin/descriptors/PossiblyInnerType;", "Lorg/jetbrains/kotlin/types/KotlinType;", "classifierDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "index", "", "capturedCopyForInnerDeclaration", "Lorg/jetbrains/kotlin/descriptors/CapturedTypeParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "declarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "declaredTypeParametersCount", "computeConstructorTypeParameters", "", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeParameterUtilsKt {
    public static final List<TypeParameterDescriptor> computeConstructorTypeParameters(ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters) {
        List<TypeParameterDescriptor> listEmptyList;
        DeclarationDescriptor next;
        TypeConstructor typeConstructor;
        Intrinsics.checkNotNullParameter(classifierDescriptorWithTypeParameters, "<this>");
        List<TypeParameterDescriptor> declaredTypeParameters = classifierDescriptorWithTypeParameters.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        if (!classifierDescriptorWithTypeParameters.getIsInner() && !(classifierDescriptorWithTypeParameters.getDeclarationDescriptor() instanceof CallableDescriptor)) {
            return declaredTypeParameters;
        }
        ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters2 = classifierDescriptorWithTypeParameters;
        List list = SequencesKt.toList(SequencesKt.flatMap(SequencesKt.filter(SequencesKt.takeWhile(DescriptorUtilsKt.getParents(classifierDescriptorWithTypeParameters2), new Function1<DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt$computeConstructorTypeParameters$parametersFromContainingFunctions$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it instanceof CallableDescriptor);
            }
        }), new Function1<DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt$computeConstructorTypeParameters$parametersFromContainingFunctions$2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!(it instanceof ConstructorDescriptor));
            }
        }), new Function1<DeclarationDescriptor, Sequence<? extends TypeParameterDescriptor>>() { // from class: org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt$computeConstructorTypeParameters$parametersFromContainingFunctions$3
            @Override // kotlin.jvm.functions.Function1
            public final Sequence<TypeParameterDescriptor> invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                List<TypeParameterDescriptor> typeParameters = ((CallableDescriptor) it).getTypeParameters();
                Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
                return CollectionsKt.asSequence(typeParameters);
            }
        }));
        Iterator<DeclarationDescriptor> it = DescriptorUtilsKt.getParents(classifierDescriptorWithTypeParameters2).iterator();
        do {
            listEmptyList = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!(next instanceof ClassDescriptor));
        ClassDescriptor classDescriptor = (ClassDescriptor) next;
        if (classDescriptor != null && (typeConstructor = classDescriptor.getTypeConstructor()) != null) {
            listEmptyList = typeConstructor.getParameters();
        }
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        if (list.isEmpty() && listEmptyList.isEmpty()) {
            List<TypeParameterDescriptor> declaredTypeParameters2 = classifierDescriptorWithTypeParameters.getDeclaredTypeParameters();
            Intrinsics.checkNotNullExpressionValue(declaredTypeParameters2, "getDeclaredTypeParameters(...)");
            return declaredTypeParameters2;
        }
        List<TypeParameterDescriptor> listPlus = CollectionsKt.plus((Collection) list, (Iterable) listEmptyList);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPlus, 10));
        for (TypeParameterDescriptor typeParameterDescriptor : listPlus) {
            Intrinsics.checkNotNull(typeParameterDescriptor);
            arrayList.add(capturedCopyForInnerDeclaration(typeParameterDescriptor, classifierDescriptorWithTypeParameters2, declaredTypeParameters.size()));
        }
        return CollectionsKt.plus((Collection) declaredTypeParameters, (Iterable) arrayList);
    }

    private static final CapturedTypeParameterDescriptor capturedCopyForInnerDeclaration(TypeParameterDescriptor typeParameterDescriptor, DeclarationDescriptor declarationDescriptor, int i) {
        return new CapturedTypeParameterDescriptor(typeParameterDescriptor, declarationDescriptor, i);
    }

    public static final PossiblyInnerType buildPossiblyInnerType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        return buildPossiblyInnerType(kotlinType, this$0 instanceof ClassifierDescriptorWithTypeParameters ? (ClassifierDescriptorWithTypeParameters) this$0 : null, 0);
    }

    private static final PossiblyInnerType buildPossiblyInnerType(KotlinType kotlinType, ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters, int i) {
        if (classifierDescriptorWithTypeParameters != null) {
            ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters2 = classifierDescriptorWithTypeParameters;
            if (!ErrorUtils.isError(classifierDescriptorWithTypeParameters2)) {
                int size = classifierDescriptorWithTypeParameters.getDeclaredTypeParameters().size() + i;
                if (!classifierDescriptorWithTypeParameters.getIsInner()) {
                    if (size != kotlinType.getArguments().size()) {
                        DescriptorUtils.isLocal(classifierDescriptorWithTypeParameters2);
                    }
                    return new PossiblyInnerType(classifierDescriptorWithTypeParameters, kotlinType.getArguments().subList(i, kotlinType.getArguments().size()), null);
                }
                List<TypeProjection> listSubList = kotlinType.getArguments().subList(i, size);
                DeclarationDescriptor containingDeclaration = classifierDescriptorWithTypeParameters.getDeclarationDescriptor();
                return new PossiblyInnerType(classifierDescriptorWithTypeParameters, listSubList, buildPossiblyInnerType(kotlinType, containingDeclaration instanceof ClassifierDescriptorWithTypeParameters ? (ClassifierDescriptorWithTypeParameters) containingDeclaration : null, size));
            }
        }
        return null;
    }
}
