package org.jetbrains.kotlin.resolve.calls.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation;
import org.jetbrains.kotlin.resolve.calls.inference.DescriptorRelatedInferenceUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.components.EmptySubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutorByConstructorMap;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutorKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.DeclaredUpperBoundConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.ExplicitTypeParameterConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.KnownTypeParameterConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionPart;
import org.jetbrains.kotlin.resolve.calls.model.SimpleTypeArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgumentPlaceholder;
import org.jetbrains.kotlin.types.FlexibleTypeWithEnhancement;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeWithEnhancementKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ResolutionParts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u001e\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0014\u0010\u0015\u001a\u00020\u0016*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J \u0010\u001a\u001a\u00020\u001b*\u00020\u001c2\u0014\b\u0002\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u001b0\u001e¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CreateFreshVariablesSubstitutor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionPart;", "()V", "createKnownParametersFromFreshVariablesSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "freshVariableSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "knownTypeParametersSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "createToFreshVariableSubstitutorAndAddInitialConstraints", "candidateDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemOperation;", "getTypePreservingFlexibilityWrtTypeVariable", "Lorg/jetbrains/kotlin/types/KotlinType;", ModuleXmlParser.TYPE, "typeVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableFromCallableDescriptor;", "process", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "workIndex", "", "shouldBeFlexible", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "flexibleCheck", "Lkotlin/Function1;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateFreshVariablesSubstitutor extends ResolutionPart {
    public static final CreateFreshVariablesSubstitutor INSTANCE = new CreateFreshVariablesSubstitutor();

    private CreateFreshVariablesSubstitutor() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
    public void process(ResolutionCandidate resolutionCandidate, int i) {
        FreshVariableNewTypeSubstitutor freshVariableNewTypeSubstitutorCreateToFreshVariableSubstitutorAndAddInitialConstraints;
        EmptySubstitutor emptySubstitutorCreateKnownParametersFromFreshVariablesSubstitutor;
        UnwrappedType unwrappedTypeSubstitute;
        Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
        ConstraintSystemBuilder builder = resolutionCandidate.getSystem().getBuilder();
        if (resolutionCandidate.getResolvedCall().get_candidateDescriptor().getTypeParameters().isEmpty()) {
            freshVariableNewTypeSubstitutorCreateToFreshVariableSubstitutorAndAddInitialConstraints = FreshVariableNewTypeSubstitutor.INSTANCE.getEmpty();
        } else {
            freshVariableNewTypeSubstitutorCreateToFreshVariableSubstitutorAndAddInitialConstraints = createToFreshVariableSubstitutorAndAddInitialConstraints(resolutionCandidate.getResolvedCall().get_candidateDescriptor(), resolutionCandidate.getResolvedCall().getAtom(), builder);
        }
        TypeSubstitutor knownTypeParametersResultingSubstitutor = resolutionCandidate.getKnownTypeParametersResultingSubstitutor();
        if (knownTypeParametersResultingSubstitutor == null || (emptySubstitutorCreateKnownParametersFromFreshVariablesSubstitutor = INSTANCE.createKnownParametersFromFreshVariablesSubstitutor(freshVariableNewTypeSubstitutorCreateToFreshVariableSubstitutorAndAddInitialConstraints, knownTypeParametersResultingSubstitutor)) == null) {
            emptySubstitutorCreateKnownParametersFromFreshVariablesSubstitutor = EmptySubstitutor.INSTANCE;
        }
        resolutionCandidate.getResolvedCall().setFreshVariablesSubstitutor(freshVariableNewTypeSubstitutorCreateToFreshVariableSubstitutorAndAddInitialConstraints);
        resolutionCandidate.getResolvedCall().setKnownParametersSubstitutor(emptySubstitutorCreateKnownParametersFromFreshVariablesSubstitutor);
        if (resolutionCandidate.getResolvedCall().get_candidateDescriptor().getTypeParameters().isEmpty() || builder.getHasContradiction()) {
            return;
        }
        if (Intrinsics.areEqual(resolutionCandidate.getResolvedCall().getTypeArgumentMappingByOriginal(), TypeArgumentsToParametersMapper.TypeArgumentsMapping.NoExplicitArguments.INSTANCE) && resolutionCandidate.getKnownTypeParametersResultingSubstitutor() == null) {
            return;
        }
        List<TypeParameterDescriptor> typeParameters = resolutionCandidate.getResolvedCall().get_candidateDescriptor().getOriginal().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        int size = typeParameters.size();
        for (int i2 = 0; i2 < size; i2++) {
            TypeParameterDescriptor typeParameterDescriptor = typeParameters.get(i2);
            TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor = freshVariableNewTypeSubstitutorCreateToFreshVariableSubstitutorAndAddInitialConstraints.getFreshVariables().get(i2);
            TypeSubstitutor knownTypeParametersResultingSubstitutor2 = resolutionCandidate.getKnownTypeParametersResultingSubstitutor();
            if (knownTypeParametersResultingSubstitutor2 != null) {
                SimpleType defaultType = typeParameterDescriptor.getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                unwrappedTypeSubstitute = DescriptorRelatedInferenceUtilsKt.substitute(knownTypeParametersResultingSubstitutor2, defaultType);
            } else {
                unwrappedTypeSubstitute = null;
            }
            if (unwrappedTypeSubstitute != null) {
                builder.addEqualityConstraint(typeVariableFromCallableDescriptor.getDefaultType(), getTypePreservingFlexibilityWrtTypeVariable(unwrappedTypeSubstitute.unwrap(), typeVariableFromCallableDescriptor), new KnownTypeParameterConstraintPositionImpl(unwrappedTypeSubstitute));
            } else {
                TypeArgumentsToParametersMapper.TypeArgumentsMapping typeArgumentMappingByOriginal = resolutionCandidate.getResolvedCall().getTypeArgumentMappingByOriginal();
                Intrinsics.checkNotNull(typeParameterDescriptor);
                TypeArgument typeArgument = typeArgumentMappingByOriginal.getTypeArgument(typeParameterDescriptor);
                if (typeArgument instanceof SimpleTypeArgument) {
                    SimpleTypeArgument simpleTypeArgument = (SimpleTypeArgument) typeArgument;
                    builder.addEqualityConstraint(typeVariableFromCallableDescriptor.getDefaultType(), getTypePreservingFlexibilityWrtTypeVariable(simpleTypeArgument.getType(), typeVariableFromCallableDescriptor), new ExplicitTypeParameterConstraintPositionImpl(simpleTypeArgument));
                } else {
                    Intrinsics.areEqual(typeArgument, TypeArgumentPlaceholder.INSTANCE);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ boolean shouldBeFlexible$default(CreateFreshVariablesSubstitutor createFreshVariablesSubstitutor, TypeParameterDescriptor typeParameterDescriptor, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = new Function1<KotlinType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CreateFreshVariablesSubstitutor.shouldBeFlexible.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KotlinType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(FlexibleTypesKt.isFlexible(it));
                }
            };
        }
        return createFreshVariablesSubstitutor.shouldBeFlexible(typeParameterDescriptor, function1);
    }

    public final boolean shouldBeFlexible(TypeParameterDescriptor typeParameterDescriptor, Function1<? super KotlinType, Boolean> flexibleCheck) {
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(flexibleCheck, "flexibleCheck");
        List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
        List<KotlinType> list = upperBounds;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (KotlinType kotlinType : list) {
            Intrinsics.checkNotNull(kotlinType);
            if (!flexibleCheck.invoke(kotlinType).booleanValue()) {
                ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().getThis$0();
                TypeParameterDescriptor typeParameterDescriptor2 = classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
                if (typeParameterDescriptor2 != null ? shouldBeFlexible$default(INSTANCE, typeParameterDescriptor2, null, 1, null) : false) {
                }
            }
            return true;
        }
        return false;
    }

    private static final UnwrappedType getTypePreservingFlexibilityWrtTypeVariable$createFlexibleType(KotlinType kotlinType) {
        return KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible(TypeUtilsKt.makeNotNullable(kotlinType)), FlexibleTypesKt.upperIfFlexible(TypeUtilsKt.makeNullable(kotlinType)));
    }

    private final KotlinType getTypePreservingFlexibilityWrtTypeVariable(KotlinType type, TypeVariableFromCallableDescriptor typeVariable) {
        if (shouldBeFlexible(typeVariable.getOriginalTypeParameter(), new Function1<KotlinType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CreateFreshVariablesSubstitutor.getTypePreservingFlexibilityWrtTypeVariable.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it instanceof FlexibleTypeWithEnhancement);
            }
        })) {
            return TypeWithEnhancementKt.wrapEnhancement(getTypePreservingFlexibilityWrtTypeVariable$createFlexibleType(type), type);
        }
        return shouldBeFlexible$default(this, typeVariable.getOriginalTypeParameter(), null, 1, null) ? getTypePreservingFlexibilityWrtTypeVariable$createFlexibleType(type) : type;
    }

    private final NewTypeSubstitutor createKnownParametersFromFreshVariablesSubstitutor(FreshVariableNewTypeSubstitutor freshVariableSubstitutor, TypeSubstitutor knownTypeParametersSubstitutor) {
        if (knownTypeParametersSubstitutor.isEmpty()) {
            return EmptySubstitutor.INSTANCE;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor : freshVariableSubstitutor.getFreshVariables()) {
            SimpleType defaultType = typeVariableFromCallableDescriptor.getOriginalTypeParameter().getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            UnwrappedType unwrappedTypeSubstitute = DescriptorRelatedInferenceUtilsKt.substitute(knownTypeParametersSubstitutor, defaultType);
            if (unwrappedTypeSubstitute != defaultType) {
                linkedHashMap.put(typeVariableFromCallableDescriptor.getDefaultType().getConstructor(), unwrappedTypeSubstitute);
            }
        }
        return NewTypeSubstitutorKt.composeWith(knownTypeParametersSubstitutor, new NewTypeSubstitutorByConstructorMap(linkedHashMap));
    }

    public final FreshVariableNewTypeSubstitutor createToFreshVariableSubstitutorAndAddInitialConstraints(CallableDescriptor candidateDescriptor, KotlinCall kotlinCall, ConstraintSystemOperation csBuilder) {
        Intrinsics.checkNotNullParameter(candidateDescriptor, "candidateDescriptor");
        Intrinsics.checkNotNullParameter(kotlinCall, "kotlinCall");
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        List<TypeParameterDescriptor> typeParameters = candidateDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            Intrinsics.checkNotNull(typeParameterDescriptor);
            arrayList.add(new TypeVariableFromCallableDescriptor(typeParameterDescriptor));
        }
        ArrayList arrayList2 = arrayList;
        FreshVariableNewTypeSubstitutor freshVariableNewTypeSubstitutor = new FreshVariableNewTypeSubstitutor(arrayList2);
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            csBuilder.registerVariable((TypeVariableFromCallableDescriptor) it.next());
        }
        int size = typeParameters.size();
        for (int i = 0; i < size; i++) {
            TypeParameterDescriptor typeParameterDescriptor2 = typeParameters.get(i);
            TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor = (TypeVariableFromCallableDescriptor) arrayList2.get(i);
            Intrinsics.checkNotNull(typeParameterDescriptor2);
            DeclaredUpperBoundConstraintPositionImpl declaredUpperBoundConstraintPositionImpl = new DeclaredUpperBoundConstraintPositionImpl(typeParameterDescriptor2, kotlinCall);
            for (KotlinType kotlinType : typeParameterDescriptor2.getUpperBounds()) {
                Intrinsics.checkNotNull(kotlinType);
                createToFreshVariableSubstitutorAndAddInitialConstraints$addSubtypeConstraint(typeVariableFromCallableDescriptor, csBuilder, freshVariableNewTypeSubstitutor, kotlinType, declaredUpperBoundConstraintPositionImpl);
            }
        }
        if (candidateDescriptor instanceof TypeAliasConstructorDescriptor) {
            TypeAliasConstructorDescriptor typeAliasConstructorDescriptor = (TypeAliasConstructorDescriptor) candidateDescriptor;
            List<TypeProjection> arguments = typeAliasConstructorDescriptor.getTypeAliasDescriptor().getUnderlyingType().getArguments();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
            Iterator<T> it2 = arguments.iterator();
            while (it2.hasNext()) {
                arrayList3.add(((TypeProjection) it2.next()).getNullableAnyType());
            }
            ArrayList arrayList4 = arrayList3;
            List<TypeParameterDescriptor> typeParameters2 = typeAliasConstructorDescriptor.getUnderlyingConstructorDescriptor().getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
            int size2 = typeParameters.size();
            for (int i2 = 0; i2 < size2; i2++) {
                TypeParameterDescriptor typeParameterDescriptor3 = typeParameters.get(i2);
                TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor2 = (TypeVariableFromCallableDescriptor) arrayList2.get(i2);
                ArrayList arrayList5 = new ArrayList();
                int i3 = 0;
                for (Object obj : arrayList4) {
                    int i4 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    Integer numValueOf = Intrinsics.areEqual((KotlinType) obj, typeParameterDescriptor3.getDefaultType()) ? Integer.valueOf(i3) : null;
                    if (numValueOf != null) {
                        arrayList5.add(numValueOf);
                    }
                    i3 = i4;
                }
                Iterator it3 = arrayList5.iterator();
                while (it3.hasNext()) {
                    TypeParameterDescriptor typeParameterDescriptor4 = (TypeParameterDescriptor) CollectionsKt.getOrNull(typeParameters2, ((Number) it3.next()).intValue());
                    if (typeParameterDescriptor4 != null) {
                        DeclaredUpperBoundConstraintPositionImpl declaredUpperBoundConstraintPositionImpl2 = new DeclaredUpperBoundConstraintPositionImpl(typeParameterDescriptor4, kotlinCall);
                        for (KotlinType kotlinType2 : typeParameterDescriptor4.getUpperBounds()) {
                            Intrinsics.checkNotNull(kotlinType2);
                            createToFreshVariableSubstitutorAndAddInitialConstraints$addSubtypeConstraint(typeVariableFromCallableDescriptor2, csBuilder, freshVariableNewTypeSubstitutor, kotlinType2, declaredUpperBoundConstraintPositionImpl2);
                        }
                    }
                }
            }
        }
        return freshVariableNewTypeSubstitutor;
    }

    private static final void createToFreshVariableSubstitutorAndAddInitialConstraints$addSubtypeConstraint(TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor, ConstraintSystemOperation constraintSystemOperation, FreshVariableNewTypeSubstitutor freshVariableNewTypeSubstitutor, KotlinType kotlinType, DeclaredUpperBoundConstraintPositionImpl declaredUpperBoundConstraintPositionImpl) {
        constraintSystemOperation.addSubtypeConstraint(typeVariableFromCallableDescriptor.getDefaultType(), freshVariableNewTypeSubstitutor.safeSubstitute(kotlinType.unwrap()), declaredUpperBoundConstraintPositionImpl);
    }
}
