package org.jetbrains.kotlin.resolve.sam;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.TypeParameterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: samConstructorUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000^\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u001a\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r\u001a(\u0010\u0010\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a>\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u001e\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00192\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003\u001a*\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00192\b\u0010\u001f\u001a\u0004\u0018\u00010\u0003¨\u0006!"}, d2 = {"createSamConstructorFunction", "Lorg/jetbrains/kotlin/resolve/sam/SamConstructorDescriptor;", "owner", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "samInterface", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "samResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "samConversionOracle", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "createSubstitutorForTypeParameters", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "originalToAltTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/TypeParameterDescriptorImpl;", "createTypeAliasSamConstructorFunction", "typeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "underlyingSamConstructor", "initializeSamConstructorDescriptor", "", "samConstructor", "Lorg/jetbrains/kotlin/descriptors/impl/SimpleFunctionDescriptorImpl;", "samTypeParameters", "", "unsubstitutedSamType", "Lorg/jetbrains/kotlin/types/KotlinType;", "recreateAndInitializeTypeParameters", "Lorg/jetbrains/kotlin/resolve/sam/SamConstructorTypeParameters;", "originalParameters", "newOwner", "recreateTypeParametersAndReturnMapping", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SamConstructorUtilsKt {
    public static final SamConstructorDescriptor createSamConstructorFunction(DeclarationDescriptor owner, ClassDescriptor samInterface, SamConversionResolver samResolver, SamConversionOracle samConversionOracle) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(samInterface, "samInterface");
        Intrinsics.checkNotNullParameter(samResolver, "samResolver");
        Intrinsics.checkNotNullParameter(samConversionOracle, "samConversionOracle");
        SamConversionResolverImplKt.getSingleAbstractMethodOrNull(samInterface);
        SamConstructorDescriptorImpl samConstructorDescriptorImpl = new SamConstructorDescriptorImpl(owner, samInterface);
        List<TypeParameterDescriptor> parameters = samInterface.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        SimpleType defaultType = samInterface.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        initializeSamConstructorDescriptor(samInterface, samConstructorDescriptorImpl, parameters, defaultType, samResolver, samConversionOracle);
        return samConstructorDescriptorImpl;
    }

    public static final SamConstructorDescriptor createTypeAliasSamConstructorFunction(TypeAliasDescriptor typeAliasDescriptor, SamConstructorDescriptor underlyingSamConstructor, SamConversionResolver samResolver, SamConversionOracle samConversionOracle) {
        Intrinsics.checkNotNullParameter(typeAliasDescriptor, "typeAliasDescriptor");
        Intrinsics.checkNotNullParameter(underlyingSamConstructor, "underlyingSamConstructor");
        Intrinsics.checkNotNullParameter(samResolver, "samResolver");
        Intrinsics.checkNotNullParameter(samConversionOracle, "samConversionOracle");
        SamTypeAliasConstructorDescriptorImpl samTypeAliasConstructorDescriptorImpl = new SamTypeAliasConstructorDescriptorImpl(typeAliasDescriptor, underlyingSamConstructor);
        ClassDescriptor baseDescriptorForSynthetic = underlyingSamConstructor.getSamInterface();
        List<TypeParameterDescriptor> parameters = typeAliasDescriptor.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        initializeSamConstructorDescriptor(baseDescriptorForSynthetic, samTypeAliasConstructorDescriptorImpl, parameters, typeAliasDescriptor.getExpandedType(), samResolver, samConversionOracle);
        return samTypeAliasConstructorDescriptorImpl;
    }

    private static final void initializeSamConstructorDescriptor(ClassDescriptor classDescriptor, SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl, List<? extends TypeParameterDescriptor> list, KotlinType kotlinType, SamConversionResolver samConversionResolver, SamConversionOracle samConversionOracle) {
        SamConstructorTypeParameters samConstructorTypeParametersRecreateAndInitializeTypeParameters = recreateAndInitializeTypeParameters(list, simpleFunctionDescriptorImpl);
        KotlinType functionTypeForSamType = SamConversionResolverImplKt.getFunctionTypeForSamType(kotlinType, samConversionResolver, samConversionOracle);
        if (functionTypeForSamType == null) {
            throw new IllegalStateException(("couldn't get function type for SAM type " + kotlinType).toString());
        }
        KotlinType kotlinTypeSubstitute = samConstructorTypeParametersRecreateAndInitializeTypeParameters.getSubstitutor().substitute(functionTypeForSamType, Variance.IN_VARIANCE);
        if (kotlinTypeSubstitute == null) {
            throw new IllegalStateException(("couldn't substitute type: " + functionTypeForSamType + ", substitutor = " + samConstructorTypeParametersRecreateAndInitializeTypeParameters.getSubstitutor()).toString());
        }
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        Name nameIdentifier = Name.identifier("function");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        ValueParameterDescriptorImpl valueParameterDescriptorImpl = new ValueParameterDescriptorImpl(simpleFunctionDescriptorImpl, null, 0, empty, nameIdentifier, kotlinTypeSubstitute, false, false, false, null, NO_SOURCE);
        KotlinType kotlinTypeSubstitute2 = samConstructorTypeParametersRecreateAndInitializeTypeParameters.getSubstitutor().substitute(kotlinType, Variance.OUT_VARIANCE);
        if (kotlinTypeSubstitute2 == null) {
            throw new IllegalStateException(("couldn't substitute type: " + kotlinType + ", substitutor = " + samConstructorTypeParametersRecreateAndInitializeTypeParameters.getSubstitutor()).toString());
        }
        simpleFunctionDescriptorImpl.initialize((ReceiverParameterDescriptor) null, (ReceiverParameterDescriptor) null, CollectionsKt.emptyList(), (List<? extends TypeParameterDescriptor>) samConstructorTypeParametersRecreateAndInitializeTypeParameters.getDescriptors(), CollectionsKt.listOf(valueParameterDescriptorImpl), kotlinTypeSubstitute2, Modality.FINAL, classDescriptor.getVisibility());
    }

    public static final SamConstructorTypeParameters recreateAndInitializeTypeParameters(List<? extends TypeParameterDescriptor> originalParameters, DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(originalParameters, "originalParameters");
        Map<TypeParameterDescriptor, TypeParameterDescriptorImpl> mapRecreateTypeParametersAndReturnMapping = recreateTypeParametersAndReturnMapping(originalParameters, declarationDescriptor);
        TypeSubstitutor typeSubstitutorCreateSubstitutorForTypeParameters = createSubstitutorForTypeParameters(mapRecreateTypeParametersAndReturnMapping);
        for (Map.Entry<TypeParameterDescriptor, TypeParameterDescriptorImpl> entry : mapRecreateTypeParametersAndReturnMapping.entrySet()) {
            TypeParameterDescriptor key = entry.getKey();
            TypeParameterDescriptorImpl value = entry.getValue();
            for (KotlinType kotlinType : key.getUpperBounds()) {
                KotlinType kotlinTypeSubstitute = typeSubstitutorCreateSubstitutorForTypeParameters.substitute(kotlinType, Variance.INVARIANT);
                if (kotlinTypeSubstitute == null) {
                    throw new IllegalStateException(("couldn't substitute type: " + kotlinType + ", substitutor = " + typeSubstitutorCreateSubstitutorForTypeParameters).toString());
                }
                value.addUpperBound(kotlinTypeSubstitute);
            }
            value.setInitialized();
        }
        return new SamConstructorTypeParameters(CollectionsKt.toList(mapRecreateTypeParametersAndReturnMapping.values()), typeSubstitutorCreateSubstitutorForTypeParameters);
    }

    public static final Map<TypeParameterDescriptor, TypeParameterDescriptorImpl> recreateTypeParametersAndReturnMapping(List<? extends TypeParameterDescriptor> originalParameters, DeclarationDescriptor declarationDescriptor) {
        DeclarationDescriptor declarationDescriptor2;
        Intrinsics.checkNotNullParameter(originalParameters, "originalParameters");
        List<? extends TypeParameterDescriptor> list = originalParameters;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) obj;
            if (declarationDescriptor == null) {
                DeclarationDescriptor containingDeclaration = typeParameterDescriptor.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                declarationDescriptor2 = containingDeclaration;
            } else {
                declarationDescriptor2 = declarationDescriptor;
            }
            linkedHashMap2.put(obj, TypeParameterDescriptorImpl.createForFurtherModification(declarationDescriptor2, typeParameterDescriptor.getAnnotations(), typeParameterDescriptor.isReified(), typeParameterDescriptor.getVariance(), typeParameterDescriptor.getShortName(), typeParameterDescriptor.getIndex(), SourceElement.NO_SOURCE, typeParameterDescriptor.getStorageManager()));
        }
        return linkedHashMap;
    }

    public static final TypeSubstitutor createSubstitutorForTypeParameters(Map<TypeParameterDescriptor, ? extends TypeParameterDescriptorImpl> originalToAltTypeParameters) {
        Intrinsics.checkNotNullParameter(originalToAltTypeParameters, "originalToAltTypeParameters");
        ArrayList arrayList = new ArrayList(originalToAltTypeParameters.size());
        for (Map.Entry<TypeParameterDescriptor, ? extends TypeParameterDescriptorImpl> entry : originalToAltTypeParameters.entrySet()) {
            TypeParameterDescriptor key = entry.getKey();
            TypeParameterDescriptorImpl value = entry.getValue();
            TypeConstructor typeConstructor = key.getTypeConstructor();
            SimpleType defaultType = value.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            arrayList.add(TuplesKt.to(typeConstructor, TypeUtilsKt.asTypeProjection(defaultType)));
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create((Map<TypeConstructor, TypeProjection>) MapsKt.toMap(arrayList));
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        return typeSubstitutorCreate;
    }
}
