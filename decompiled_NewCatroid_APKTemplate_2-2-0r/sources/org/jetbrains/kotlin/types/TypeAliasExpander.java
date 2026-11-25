package org.jetbrains.kotlin.types;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: TypeAliasExpander.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 ,2\u00020\u0001:\u0001,B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002J\u0018\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002J\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J \u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J0\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u0005H\u0002J*\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0016\u0010\"\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0014\u0010#\u001a\u00020$*\u00020$2\u0006\u0010%\u001a\u00020\u0015H\u0002J\u0014\u0010#\u001a\u00020\u0011*\u00020\u00112\u0006\u0010%\u001a\u00020\u0015H\u0002J\u0014\u0010&\u001a\u00020\u0011*\u00020\u00112\u0006\u0010'\u001a\u00020\u000eH\u0002J\u0014\u0010(\u001a\u00020\u0011*\u00020\u00112\u0006\u0010'\u001a\u00020\u000eH\u0002J\u001c\u0010)\u001a\u00020\u0011*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u0005H\u0002J\u0014\u0010*\u001a\u00020\u0015*\u00020\u000e2\u0006\u0010%\u001a\u00020\u0015H\u0002J\u001c\u0010+\u001a\u00020\u0011*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001aH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeAliasExpander;", "", "reportStrategy", "Lorg/jetbrains/kotlin/types/TypeAliasExpansionReportStrategy;", "shouldCheckBounds", "", "(Lorg/jetbrains/kotlin/types/TypeAliasExpansionReportStrategy;Z)V", "checkRepeatedAnnotations", "", "existingAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "newAnnotations", "checkTypeArgumentsSubstitution", "unsubstitutedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "substitutedType", "expand", "Lorg/jetbrains/kotlin/types/SimpleType;", "typeAliasExpansion", "Lorg/jetbrains/kotlin/types/TypeAliasExpansion;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "expandNonArgumentTypeProjection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "originalProjection", "recursionDepth", "", "expandRecursively", "isNullable", "withAbbreviatedType", "expandTypeProjection", "underlyingProjection", "typeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "expandWithoutAbbreviation", "combineAttributes", "Lorg/jetbrains/kotlin/types/DynamicType;", "newAttributes", "combineNullability", "fromType", "combineNullabilityAndAnnotations", "createAbbreviation", "createdCombinedAttributes", "substituteArguments", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeAliasExpander {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final TypeAliasExpander NON_REPORTING = new TypeAliasExpander(TypeAliasExpansionReportStrategy.DO_NOTHING.INSTANCE, false);
    private final TypeAliasExpansionReportStrategy reportStrategy;
    private final boolean shouldCheckBounds;

    public TypeAliasExpander(TypeAliasExpansionReportStrategy reportStrategy, boolean z) {
        Intrinsics.checkNotNullParameter(reportStrategy, "reportStrategy");
        this.reportStrategy = reportStrategy;
        this.shouldCheckBounds = z;
    }

    public final SimpleType expand(TypeAliasExpansion typeAliasExpansion, TypeAttributes attributes) {
        Intrinsics.checkNotNullParameter(typeAliasExpansion, "typeAliasExpansion");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        return expandRecursively(typeAliasExpansion, attributes, false, 0, true);
    }

    public final SimpleType expandWithoutAbbreviation(TypeAliasExpansion typeAliasExpansion, TypeAttributes attributes) {
        Intrinsics.checkNotNullParameter(typeAliasExpansion, "typeAliasExpansion");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        return expandRecursively(typeAliasExpansion, attributes, false, 0, false);
    }

    private final SimpleType expandRecursively(TypeAliasExpansion typeAliasExpansion, TypeAttributes attributes, boolean isNullable, int recursionDepth, boolean withAbbreviatedType) {
        TypeProjection typeProjectionExpandTypeProjection = expandTypeProjection(new TypeProjectionImpl(Variance.INVARIANT, typeAliasExpansion.getDescriptor().getUnderlyingType()), typeAliasExpansion, null, recursionDepth);
        KotlinType nullableAnyType = typeProjectionExpandTypeProjection.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
        SimpleType simpleTypeAsSimpleType = TypeSubstitutionKt.asSimpleType(nullableAnyType);
        if (KotlinTypeKt.isError(simpleTypeAsSimpleType)) {
            return simpleTypeAsSimpleType;
        }
        typeProjectionExpandTypeProjection.getProjectionKind();
        Variance variance = Variance.INVARIANT;
        checkRepeatedAnnotations(simpleTypeAsSimpleType.getAnnotations(), AnnotationsTypeAttributeKt.getAnnotations(attributes));
        SimpleType simpleTypeMakeNullableIfNeeded = TypeUtils.makeNullableIfNeeded(combineAttributes(simpleTypeAsSimpleType, attributes), isNullable);
        Intrinsics.checkNotNullExpressionValue(simpleTypeMakeNullableIfNeeded, "let(...)");
        return withAbbreviatedType ? SpecialTypesKt.withAbbreviation(simpleTypeMakeNullableIfNeeded, createAbbreviation(typeAliasExpansion, attributes, isNullable)) : simpleTypeMakeNullableIfNeeded;
    }

    private final SimpleType createAbbreviation(TypeAliasExpansion typeAliasExpansion, TypeAttributes typeAttributes, boolean z) {
        TypeConstructor typeConstructor = typeAliasExpansion.getDescriptor().getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(typeAttributes, typeConstructor, typeAliasExpansion.getArguments(), z, MemberScope.Empty.INSTANCE);
    }

    private final TypeProjection expandTypeProjection(TypeProjection underlyingProjection, TypeAliasExpansion typeAliasExpansion, TypeParameterDescriptor typeParameterDescriptor, int recursionDepth) {
        Variance variance;
        SimpleType simpleTypeCombineNullabilityAndAnnotations;
        INSTANCE.assertRecursionDepth(recursionDepth, typeAliasExpansion.getDescriptor());
        if (underlyingProjection.isStarProjection()) {
            Intrinsics.checkNotNull(typeParameterDescriptor);
            TypeProjection typeProjectionMakeStarProjection = TypeUtils.makeStarProjection(typeParameterDescriptor);
            Intrinsics.checkNotNullExpressionValue(typeProjectionMakeStarProjection, "makeStarProjection(...)");
            return typeProjectionMakeStarProjection;
        }
        KotlinType nullableAnyType = underlyingProjection.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
        TypeProjection replacement = typeAliasExpansion.getReplacement(nullableAnyType.getConstructor());
        if (replacement == null) {
            return expandNonArgumentTypeProjection(underlyingProjection, typeAliasExpansion, recursionDepth);
        }
        if (replacement.isStarProjection()) {
            Intrinsics.checkNotNull(typeParameterDescriptor);
            TypeProjection typeProjectionMakeStarProjection2 = TypeUtils.makeStarProjection(typeParameterDescriptor);
            Intrinsics.checkNotNullExpressionValue(typeProjectionMakeStarProjection2, "makeStarProjection(...)");
            return typeProjectionMakeStarProjection2;
        }
        UnwrappedType unwrappedTypeUnwrap = replacement.getNullableAnyType().unwrap();
        Variance projectionKind = replacement.getProjectionKind();
        Intrinsics.checkNotNullExpressionValue(projectionKind, "getProjectionKind(...)");
        Variance projectionKind2 = underlyingProjection.getProjectionKind();
        Intrinsics.checkNotNullExpressionValue(projectionKind2, "getProjectionKind(...)");
        if (projectionKind2 != projectionKind && projectionKind2 != Variance.INVARIANT) {
            if (projectionKind == Variance.INVARIANT) {
                projectionKind = projectionKind2;
            } else {
                this.reportStrategy.conflictingProjection(typeAliasExpansion.getDescriptor(), typeParameterDescriptor, unwrappedTypeUnwrap);
            }
        }
        if (typeParameterDescriptor == null || (variance = typeParameterDescriptor.getVariance()) == null) {
            variance = Variance.INVARIANT;
        }
        Intrinsics.checkNotNull(variance);
        if (variance != projectionKind && variance != Variance.INVARIANT) {
            if (projectionKind == Variance.INVARIANT) {
                projectionKind = Variance.INVARIANT;
            } else {
                this.reportStrategy.conflictingProjection(typeAliasExpansion.getDescriptor(), typeParameterDescriptor, unwrappedTypeUnwrap);
            }
        }
        checkRepeatedAnnotations(nullableAnyType.getAnnotations(), unwrappedTypeUnwrap.getAnnotations());
        if (unwrappedTypeUnwrap instanceof DynamicType) {
            simpleTypeCombineNullabilityAndAnnotations = combineAttributes((DynamicType) unwrappedTypeUnwrap, nullableAnyType.getAttributes());
        } else {
            simpleTypeCombineNullabilityAndAnnotations = combineNullabilityAndAnnotations(TypeSubstitutionKt.asSimpleType(unwrappedTypeUnwrap), nullableAnyType);
        }
        return new TypeProjectionImpl(projectionKind, simpleTypeCombineNullabilityAndAnnotations);
    }

    private final DynamicType combineAttributes(DynamicType dynamicType, TypeAttributes typeAttributes) {
        return dynamicType.replaceAttributes(createdCombinedAttributes(dynamicType, typeAttributes));
    }

    private final SimpleType combineAttributes(SimpleType simpleType, TypeAttributes typeAttributes) {
        SimpleType simpleType2 = simpleType;
        return KotlinTypeKt.isError(simpleType2) ? simpleType : TypeSubstitutionKt.replace$default(simpleType, null, createdCombinedAttributes(simpleType2, typeAttributes), 1, null);
    }

    private final TypeAttributes createdCombinedAttributes(KotlinType kotlinType, TypeAttributes typeAttributes) {
        return KotlinTypeKt.isError(kotlinType) ? kotlinType.getAttributes() : typeAttributes.add(kotlinType.getAttributes());
    }

    private final void checkRepeatedAnnotations(Annotations existingAnnotations, Annotations newAnnotations) {
        HashSet hashSet = new HashSet();
        Iterator<AnnotationDescriptor> it = existingAnnotations.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().getFqName());
        }
        HashSet hashSet2 = hashSet;
        for (AnnotationDescriptor annotationDescriptor : newAnnotations) {
            if (hashSet2.contains(annotationDescriptor.getFqName())) {
                this.reportStrategy.repeatedAnnotation(annotationDescriptor);
            }
        }
    }

    private final SimpleType combineNullability(SimpleType simpleType, KotlinType kotlinType) {
        SimpleType simpleTypeMakeNullableIfNeeded = TypeUtils.makeNullableIfNeeded(simpleType, kotlinType.getIsMarkedNullable());
        Intrinsics.checkNotNullExpressionValue(simpleTypeMakeNullableIfNeeded, "makeNullableIfNeeded(...)");
        return simpleTypeMakeNullableIfNeeded;
    }

    private final SimpleType combineNullabilityAndAnnotations(SimpleType simpleType, KotlinType kotlinType) {
        return combineAttributes(combineNullability(simpleType, kotlinType), kotlinType.getAttributes());
    }

    private final TypeProjection expandNonArgumentTypeProjection(TypeProjection originalProjection, TypeAliasExpansion typeAliasExpansion, int recursionDepth) {
        UnwrappedType unwrappedTypeUnwrap = originalProjection.getNullableAnyType().unwrap();
        if (DynamicTypesKt.isDynamic(unwrappedTypeUnwrap)) {
            return originalProjection;
        }
        SimpleType simpleTypeAsSimpleType = TypeSubstitutionKt.asSimpleType(unwrappedTypeUnwrap);
        SimpleType simpleType = simpleTypeAsSimpleType;
        if (KotlinTypeKt.isError(simpleType) || !TypeUtilsKt.requiresTypeAliasExpansion(simpleType)) {
            return originalProjection;
        }
        TypeConstructor constructor = simpleTypeAsSimpleType.getConstructor();
        ClassifierDescriptor this$0 = constructor.getThis$0();
        constructor.getParameters().size();
        simpleTypeAsSimpleType.getArguments().size();
        if (this$0 instanceof TypeParameterDescriptor) {
            return originalProjection;
        }
        if (this$0 instanceof TypeAliasDescriptor) {
            TypeAliasDescriptor typeAliasDescriptor = (TypeAliasDescriptor) this$0;
            int i = 0;
            if (typeAliasExpansion.isRecursion(typeAliasDescriptor)) {
                this.reportStrategy.recursiveTypeAlias(typeAliasDescriptor);
                Variance variance = Variance.INVARIANT;
                ErrorTypeKind errorTypeKind = ErrorTypeKind.RECURSIVE_TYPE_ALIAS;
                String string = typeAliasDescriptor.getShortName().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return new TypeProjectionImpl(variance, org.jetbrains.kotlin.types.error.ErrorUtils.createErrorType(errorTypeKind, string));
            }
            List<TypeProjection> arguments = simpleTypeAsSimpleType.getArguments();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
            for (Object obj : arguments) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                arrayList.add(expandTypeProjection((TypeProjection) obj, typeAliasExpansion, constructor.getParameters().get(i), recursionDepth + 1));
                i = i2;
            }
            SimpleType simpleTypeExpandRecursively = expandRecursively(TypeAliasExpansion.INSTANCE.create(typeAliasExpansion, typeAliasDescriptor, arrayList), simpleTypeAsSimpleType.getAttributes(), simpleTypeAsSimpleType.getIsMarkedNullable(), recursionDepth + 1, false);
            SimpleType simpleTypeSubstituteArguments = substituteArguments(simpleTypeAsSimpleType, typeAliasExpansion, recursionDepth);
            if (!DynamicTypesKt.isDynamic(simpleTypeExpandRecursively)) {
                simpleTypeExpandRecursively = SpecialTypesKt.withAbbreviation(simpleTypeExpandRecursively, simpleTypeSubstituteArguments);
            }
            return new TypeProjectionImpl(originalProjection.getProjectionKind(), simpleTypeExpandRecursively);
        }
        SimpleType simpleTypeSubstituteArguments2 = substituteArguments(simpleTypeAsSimpleType, typeAliasExpansion, recursionDepth);
        checkTypeArgumentsSubstitution(simpleType, simpleTypeSubstituteArguments2);
        return new TypeProjectionImpl(originalProjection.getProjectionKind(), simpleTypeSubstituteArguments2);
    }

    private final SimpleType substituteArguments(SimpleType simpleType, TypeAliasExpansion typeAliasExpansion, int i) {
        TypeConstructor constructor = simpleType.getConstructor();
        List<TypeProjection> arguments = simpleType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        int i2 = 0;
        for (Object obj : arguments) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection = (TypeProjection) obj;
            TypeProjectionImpl typeProjectionImplExpandTypeProjection = expandTypeProjection(typeProjection, typeAliasExpansion, constructor.getParameters().get(i2), i + 1);
            if (!typeProjectionImplExpandTypeProjection.isStarProjection()) {
                typeProjectionImplExpandTypeProjection = new TypeProjectionImpl(typeProjectionImplExpandTypeProjection.getProjectionKind(), TypeUtils.makeNullableIfNeeded(typeProjectionImplExpandTypeProjection.getNullableAnyType(), typeProjection.getNullableAnyType().getIsMarkedNullable()));
            }
            arrayList.add(typeProjectionImplExpandTypeProjection);
            i2 = i3;
        }
        return TypeSubstitutionKt.replace$default(simpleType, arrayList, null, 2, null);
    }

    private final void checkTypeArgumentsSubstitution(KotlinType unsubstitutedType, KotlinType substitutedType) {
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(substitutedType);
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        int i = 0;
        for (Object obj : substitutedType.getArguments()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TypeProjection typeProjection = (TypeProjection) obj;
            if (!typeProjection.isStarProjection()) {
                KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                if (!TypeUtilsKt.containsTypeAliasParameters(nullableAnyType)) {
                    TypeProjection typeProjection2 = unsubstitutedType.getArguments().get(i);
                    TypeParameterDescriptor typeParameterDescriptor = unsubstitutedType.getConstructor().getParameters().get(i);
                    if (this.shouldCheckBounds) {
                        TypeAliasExpansionReportStrategy typeAliasExpansionReportStrategy = this.reportStrategy;
                        KotlinType nullableAnyType2 = typeProjection2.getNullableAnyType();
                        Intrinsics.checkNotNullExpressionValue(nullableAnyType2, "getType(...)");
                        KotlinType nullableAnyType3 = typeProjection.getNullableAnyType();
                        Intrinsics.checkNotNullExpressionValue(nullableAnyType3, "getType(...)");
                        Intrinsics.checkNotNull(typeParameterDescriptor);
                        typeAliasExpansionReportStrategy.boundsViolationInSubstitution(typeSubstitutorCreate, nullableAnyType2, nullableAnyType3, typeParameterDescriptor);
                    }
                }
            }
            i = i2;
        }
    }

    /* JADX INFO: compiled from: TypeAliasExpander.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeAliasExpander$Companion;", "", "()V", "MAX_RECURSION_DEPTH", "", "NON_REPORTING", "Lorg/jetbrains/kotlin/types/TypeAliasExpander;", "getNON_REPORTING", "()Lorg/jetbrains/kotlin/types/TypeAliasExpander;", "assertRecursionDepth", "", "recursionDepth", "typeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void assertRecursionDepth(int recursionDepth, TypeAliasDescriptor typeAliasDescriptor) {
            if (recursionDepth > 100) {
                throw new AssertionError("Too deep recursion while expanding type alias " + typeAliasDescriptor.getShortName());
            }
        }

        public final TypeAliasExpander getNON_REPORTING() {
            return TypeAliasExpander.NON_REPORTING;
        }
    }
}
