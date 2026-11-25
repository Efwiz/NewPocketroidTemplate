package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.AccessorForConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.types.DescriptorSubstitutor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: typeMapperUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u001a\u0010\b\u001a\u00020\t*\u00020\u00072\u0006\u0010\u0002\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"isInlineClassConstructorAccessor", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "patchTypeParametersForDefaultImplMethod", "Lorg/jetbrains/kotlin/codegen/state/ReceiverTypeAndTypeParameters;", "function", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "createTypeParameterWithNewName", "Lorg/jetbrains/kotlin/descriptors/impl/TypeParameterDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "newName", "", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeMapperUtilsKt {
    public static final ReceiverTypeAndTypeParameters patchTypeParametersForDefaultImplMethod(CallableMemberDescriptor function) {
        Number number;
        Intrinsics.checkNotNullParameter(function, "function");
        DeclarationDescriptor containingDeclaration = function.getContainingModule();
        Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
        List<TypeParameterDescriptor> typeParameters = function.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeParameterDescriptor) it.next()).getShortName().asString());
        }
        ArrayList arrayList2 = arrayList;
        List<TypeParameterDescriptor> declaredTypeParameters = classDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        List<TypeParameterDescriptor> list2 = declaredTypeParameters;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : list2) {
            if (arrayList2.contains(((TypeParameterDescriptor) obj).getShortName().asString())) {
                arrayList3.add(obj);
            }
        }
        ArrayList arrayList4 = arrayList3;
        if (arrayList4.isEmpty()) {
            SimpleType defaultType = classDescriptor.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            return new ReceiverTypeAndTypeParameters(defaultType, declaredTypeParameters);
        }
        ArrayList arrayList5 = arrayList2;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList6.add(((TypeParameterDescriptor) it2.next()).getShortName().asString());
        }
        Set mutableSet = CollectionsKt.toMutableSet(CollectionsKt.plus((Collection) arrayList5, (Iterable) arrayList6));
        ArrayList<TypeParameterDescriptor> arrayList7 = arrayList4;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList7, 10)), 16));
        for (TypeParameterDescriptor typeParameterDescriptor : arrayList7) {
            String str = typeParameterDescriptor.getShortName().asString() + "_I";
            StringBuilder sbAppend = new StringBuilder().append(str);
            Iterator it3 = SequencesKt.generateSequence(1, new Function1<Integer, Integer>() { // from class: org.jetbrains.kotlin.codegen.state.TypeMapperUtilsKt$patchTypeParametersForDefaultImplMethod$mappingForInterfaceTypeParameters$2$newName$1
                public final Integer invoke(int i) {
                    return Integer.valueOf(i + 1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Integer invoke(Integer num) {
                    return invoke(num.intValue());
                }
            }).iterator();
            do {
                if (!it3.hasNext()) {
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                number = (Number) it3.next();
            } while (mutableSet.contains(str + number.intValue()));
            String string = sbAppend.append(number.intValue()).toString();
            mutableSet.add(string);
            Intrinsics.checkNotNull(typeParameterDescriptor);
            linkedHashMap.put(typeParameterDescriptor, createTypeParameterWithNewName(function, typeParameterDescriptor, string));
        }
        TypeConstructorSubstitution.Companion companion = TypeConstructorSubstitution.INSTANCE;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(linkedHashMap.size()));
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            Object key = entry.getKey();
            SimpleType defaultType2 = ((TypeParameterDescriptorImpl) entry.getValue()).getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType2, "getDefaultType(...)");
            linkedHashMap2.put(key, TypeUtilsKt.asTypeProjection(defaultType2));
        }
        TypeConstructorSubstitution typeConstructorSubstitutionCreateByParametersMap = companion.createByParametersMap(linkedHashMap2);
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(typeConstructorSubstitutionCreateByParametersMap);
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (TypeParameterDescriptorImpl typeParameterDescriptorImpl : list2) {
            TypeParameterDescriptorImpl typeParameterDescriptorImpl2 = (TypeParameterDescriptorImpl) linkedHashMap.get(typeParameterDescriptorImpl);
            if (typeParameterDescriptorImpl2 != null) {
                typeParameterDescriptorImpl = typeParameterDescriptorImpl2;
            }
            arrayList8.add(typeParameterDescriptorImpl);
        }
        ArrayList arrayList9 = new ArrayList();
        DescriptorSubstitutor.substituteTypeParameters(arrayList8, typeConstructorSubstitutionCreateByParametersMap, classDescriptor, arrayList9);
        KotlinType kotlinTypeSubstitute = typeSubstitutorCreate.substitute(classDescriptor.getDefaultType(), Variance.INVARIANT);
        Intrinsics.checkNotNull(kotlinTypeSubstitute);
        return new ReceiverTypeAndTypeParameters(kotlinTypeSubstitute, arrayList9);
    }

    public static final TypeParameterDescriptorImpl createTypeParameterWithNewName(CallableMemberDescriptor callableMemberDescriptor, TypeParameterDescriptor descriptor, String newName) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(newName, "newName");
        TypeParameterDescriptorImpl typeParameterDescriptorImplCreateForFurtherModification = TypeParameterDescriptorImpl.createForFurtherModification(callableMemberDescriptor, descriptor.getAnnotations(), descriptor.isReified(), descriptor.getVariance(), Name.identifier(newName), descriptor.getIndex(), descriptor.getSourceElement(), descriptor.getStorageManager());
        List<KotlinType> upperBounds = descriptor.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
        Iterator<T> it = upperBounds.iterator();
        while (it.hasNext()) {
            typeParameterDescriptorImplCreateForFurtherModification.addUpperBound((KotlinType) it.next());
        }
        typeParameterDescriptorImplCreateForFurtherModification.setInitialized();
        Intrinsics.checkNotNull(typeParameterDescriptorImplCreateForFurtherModification);
        return typeParameterDescriptorImplCreateForFurtherModification;
    }

    public static final boolean isInlineClassConstructorAccessor(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor instanceof AccessorForConstructorDescriptor) {
            ClassDescriptor constructedClass = ((AccessorForConstructorDescriptor) descriptor).getCalleeDescriptor().getConstructedClass();
            Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
            if (InlineClassesUtilsKt.isInlineClass(constructedClass)) {
                return true;
            }
        }
        return false;
    }
}
