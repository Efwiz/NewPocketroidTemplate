package org.jetbrains.kotlin.resolve.sam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: SamConversionResolverImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n\u001a \u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011\u001a\"\u0010\u000b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001a\u0010\u0010\u0012\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0013\u001a\u00020\u0004\u001a\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u0006\u001a\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016*\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0011¨\u0006\u0018"}, d2 = {"getAbstractMembers", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getFunctionTypeForAbstractMethod", "Lorg/jetbrains/kotlin/types/SimpleType;", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "shouldConvertFirstParameterToDescriptor", "", "getFunctionTypeForSamType", "Lorg/jetbrains/kotlin/types/KotlinType;", "samType", "samResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "samConversionOracle", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "getSingleAbstractMethodOrNull", "klass", "nonProjectionParametrization", "getFunctionTypeForPossibleSamType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "possibleSamType", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SamConversionResolverImplKt {
    public static final FunctionDescriptor getSingleAbstractMethodOrNull(ClassDescriptor klass) {
        CallableMemberDescriptor callableMemberDescriptor;
        Intrinsics.checkNotNullParameter(klass, "klass");
        String strAsString = DescriptorUtilsKt.getFqNameSafe(klass).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (StringsKt.endsWith$default(strAsString, ".databinding.DataBindingComponent", false, 2, (Object) null) || klass.isDefinitelyNotSamInterface() || (callableMemberDescriptor = (CallableMemberDescriptor) CollectionsKt.singleOrNull((List) getAbstractMembers(klass))) == null || !(callableMemberDescriptor instanceof SimpleFunctionDescriptor) || !((SimpleFunctionDescriptor) callableMemberDescriptor).getTypeParameters().isEmpty()) {
            return null;
        }
        return (FunctionDescriptor) callableMemberDescriptor;
    }

    public static final List<CallableMemberDescriptor> getAbstractMembers(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Collection<DeclarationDescriptor> allDescriptors = DescriptorUtils.getAllDescriptors(classDescriptor.getUnsubstitutedMemberScope());
        Intrinsics.checkNotNullExpressionValue(allDescriptors, "getAllDescriptors(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : allDescriptors) {
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) obj;
            if ((declarationDescriptor instanceof CallableMemberDescriptor) && ((CallableMemberDescriptor) declarationDescriptor).getModality() == Modality.ABSTRACT) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0082  */
    public static final SimpleType getFunctionTypeForAbstractMethod(FunctionDescriptor function, boolean z) {
        KotlinType type;
        Name name2;
        Intrinsics.checkNotNullParameter(function, "function");
        KotlinType returnType = function.getReturnType();
        if (returnType == null) {
            throw new IllegalStateException(("function is not initialized: " + function).toString());
        }
        List<ValueParameterDescriptor> valueParameters = function.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ArrayList arrayList = new ArrayList(valueParameters.size());
        ArrayList arrayList2 = new ArrayList(valueParameters.size());
        List<ReceiverParameterDescriptor> contextReceiverParameters = function.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ReceiverParameterDescriptor> list = contextReceiverParameters;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList3.add(((ReceiverParameterDescriptor) it.next()).getType());
        }
        ArrayList arrayList4 = arrayList3;
        ReceiverParameterDescriptor extensionReceiverParameter = function.getExtensionReceiverParameter();
        int i = 0;
        if (extensionReceiverParameter != null) {
            type = extensionReceiverParameter.getType();
        } else if (z) {
            List<ValueParameterDescriptor> valueParameters2 = function.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
            if (valueParameters2.isEmpty()) {
                type = null;
            } else {
                type = valueParameters.get(0).getType();
                i = 1;
            }
        } else {
            type = null;
        }
        int size = valueParameters.size();
        while (i < size) {
            ValueParameterDescriptor valueParameterDescriptor = valueParameters.get(i);
            arrayList.add(valueParameterDescriptor.getType());
            if (function.hasSynthesizedParameterNames()) {
                name2 = SpecialNames.NO_NAME_PROVIDED;
            } else {
                name2 = valueParameterDescriptor.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            }
            arrayList2.add(name2);
            i++;
        }
        return FunctionTypesKt.createFunctionType(DescriptorUtilsKt.getBuiltIns(function), Annotations.INSTANCE.getEMPTY(), type, arrayList4, arrayList, arrayList2, returnType, function.isSuspend());
    }

    public static final UnwrappedType getFunctionTypeForPossibleSamType(SamConversionResolver samConversionResolver, UnwrappedType possibleSamType, SamConversionOracle samConversionOracle) {
        Intrinsics.checkNotNullParameter(samConversionResolver, "<this>");
        Intrinsics.checkNotNullParameter(possibleSamType, "possibleSamType");
        Intrinsics.checkNotNullParameter(samConversionOracle, "samConversionOracle");
        KotlinType functionTypeForSamType = getFunctionTypeForSamType(possibleSamType, samConversionResolver, samConversionOracle);
        if (functionTypeForSamType != null) {
            return functionTypeForSamType.unwrap();
        }
        return null;
    }

    public static final KotlinType getFunctionTypeForSamType(KotlinType samType, SamConversionResolver samResolver, SamConversionOracle samConversionOracle) {
        Intrinsics.checkNotNullParameter(samType, "samType");
        Intrinsics.checkNotNullParameter(samResolver, "samResolver");
        Intrinsics.checkNotNullParameter(samConversionOracle, "samConversionOracle");
        UnwrappedType unwrappedTypeUnwrap = samType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
            SimpleType functionTypeForSamType = getFunctionTypeForSamType(flexibleType.getLowerBound(), samResolver, samConversionOracle);
            SimpleType functionTypeForSamType2 = getFunctionTypeForSamType(flexibleType.getUpperBound(), samResolver, samConversionOracle);
            if (functionTypeForSamType == null || functionTypeForSamType2 == null) {
                return null;
            }
            return KotlinTypeFactory.flexibleType(functionTypeForSamType, functionTypeForSamType2);
        }
        Intrinsics.checkNotNull(unwrappedTypeUnwrap, "null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        return getFunctionTypeForSamType((SimpleType) unwrappedTypeUnwrap, samResolver, samConversionOracle);
    }

    private static final SimpleType getFunctionTypeForSamType(SimpleType simpleType, SamConversionResolver samConversionResolver, SamConversionOracle samConversionOracle) {
        SimpleType simpleTypeResolveFunctionTypeIfSamInterface;
        SimpleType simpleTypeNonProjectionParametrization;
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = simpleType.getConstructor().getThis$0();
        if (!(classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) || !samConversionOracle.isPossibleSamType(simpleType) || (simpleTypeResolveFunctionTypeIfSamInterface = samConversionResolver.resolveFunctionTypeIfSamInterface((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor)) == null || (simpleTypeNonProjectionParametrization = nonProjectionParametrization(simpleType)) == null) {
            return null;
        }
        KotlinType kotlinTypeSubstitute = TypeSubstitutor.create(simpleTypeNonProjectionParametrization).substitute(simpleTypeResolveFunctionTypeIfSamInterface, Variance.IN_VARIANCE);
        Intrinsics.checkNotNull(kotlinTypeSubstitute);
        return TypeSubstitutionKt.asSimpleType(kotlinTypeSubstitute).makeNullableAsSpecified(simpleType.getIsMarkedNullable());
    }

    public static final SimpleType nonProjectionParametrization(SimpleType samType) {
        Intrinsics.checkNotNullParameter(samType, "samType");
        List<TypeProjection> arguments = samType.getArguments();
        if ((arguments instanceof Collection) && arguments.isEmpty()) {
            return samType;
        }
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            if (((TypeProjection) it.next()).getProjectionKind() != Variance.INVARIANT) {
                List<TypeParameterDescriptor> parameters = samType.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                List<TypeParameterDescriptor> list = parameters;
                final Set set = CollectionsKt.toSet(list);
                List<Pair> listZip = CollectionsKt.zip(samType.getArguments(), list);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
                for (Pair pair : listZip) {
                    TypeProjection typeProjectionAsTypeProjection = (TypeProjection) pair.component1();
                    TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) pair.component2();
                    if (typeProjectionAsTypeProjection.getProjectionKind() != Variance.INVARIANT) {
                        if (typeProjectionAsTypeProjection.isStarProjection()) {
                            List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
                            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
                            Object objFirst = CollectionsKt.first((List<? extends Object>) upperBounds);
                            KotlinType kotlinType = (KotlinType) objFirst;
                            Intrinsics.checkNotNull(kotlinType);
                            if (TypeUtilsKt.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.sam.SamConversionResolverImplKt$nonProjectionParametrization$2$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                /* JADX WARN: Multi-variable type inference failed */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final Boolean invoke(UnwrappedType it2) {
                                    Intrinsics.checkNotNullParameter(it2, "it");
                                    return Boolean.valueOf(CollectionsKt.contains(set, it2.getConstructor().getThis$0()));
                                }
                            })) {
                                objFirst = null;
                            }
                            KotlinType kotlinType2 = (KotlinType) objFirst;
                            if (kotlinType2 == null || (typeProjectionAsTypeProjection = TypeUtilsKt.asTypeProjection(kotlinType2)) == null) {
                                return null;
                            }
                        } else {
                            KotlinType type = typeProjectionAsTypeProjection.getNullableAnyType();
                            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                            typeProjectionAsTypeProjection = TypeUtilsKt.asTypeProjection(type);
                        }
                    }
                    arrayList.add(typeProjectionAsTypeProjection);
                }
                return TypeSubstitutionKt.replace$default(samType, arrayList, null, 2, null);
            }
        }
        return samType;
    }
}
