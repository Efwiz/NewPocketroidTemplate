package org.jetbrains.kotlin.resolve.calls.inference.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt;
import org.jetbrains.kotlin.types.AbbreviatedType;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.CustomTypeParameter;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.DynamicType;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.RawType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeWithEnhancement;
import org.jetbrains.kotlin.types.TypeWithEnhancementKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.IntersectionTypeKt;
import org.jetbrains.kotlin.types.checker.NewCapturedType;
import org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: NewTypeSubstitutor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\"\u0010\b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0003H\u0002J\"\u0010\b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0003H\u0002J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0010\u001a\u00020\u0011H&J\"\u0010\u0012\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0003H\u0002J \u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0003H\u0002J \u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u0006H\u0002R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "isEmpty", "", "()Z", "safeSubstitute", "Lorg/jetbrains/kotlin/types/UnwrappedType;", ModuleXmlParser.TYPE, "substitute", "Lorg/jetbrains/kotlin/types/SimpleType;", "keepAnnotation", "runCapturedChecks", "substituteArgumentProjection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "argument", "substituteNotNullTypeWithConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "substituteParametrizedType", "substituteTypeEnhancement", "Lorg/jetbrains/kotlin/types/KotlinType;", "enhancementType", "throwExceptionAboutInvalidCapturedSubstitution", "", "capturedType", "innerType", "substitutedInnerType", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface NewTypeSubstitutor extends TypeSubstitutorMarker {
    boolean isEmpty();

    default TypeProjection substituteArgumentProjection(TypeProjection argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        return null;
    }

    UnwrappedType substituteNotNullTypeWithConstructor(TypeConstructor constructor);

    default UnwrappedType safeSubstitute(UnwrappedType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        UnwrappedType unwrappedTypeSubstitute = substitute(type, true, true);
        return unwrappedTypeSubstitute == null ? type : unwrappedTypeSubstitute;
    }

    private default KotlinType substituteTypeEnhancement(KotlinType enhancementType, boolean keepAnnotation, boolean runCapturedChecks) {
        UnwrappedType unwrappedTypeUnwrap = enhancementType.unwrap();
        if (unwrappedTypeUnwrap instanceof SimpleType) {
            UnwrappedType unwrappedTypeSubstitute = substitute((SimpleType) unwrappedTypeUnwrap, keepAnnotation, runCapturedChecks);
            return unwrappedTypeSubstitute != null ? unwrappedTypeSubstitute : enhancementType;
        }
        if (!(unwrappedTypeUnwrap instanceof FlexibleType)) {
            throw new NoWhenBranchMatchedException();
        }
        FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
        SimpleType simpleTypeSubstitute = substitute(flexibleType.getLowerBound(), keepAnnotation, runCapturedChecks);
        if (simpleTypeSubstitute == null) {
            simpleTypeSubstitute = flexibleType.getLowerBound();
        }
        SimpleType simpleTypeSubstitute2 = substitute(flexibleType.getUpperBound(), keepAnnotation, runCapturedChecks);
        if (simpleTypeSubstitute2 == null) {
            simpleTypeSubstitute2 = flexibleType.getUpperBound();
        }
        return KotlinTypeFactory.flexibleType(FlexibleTypesKt.lowerIfFlexible(simpleTypeSubstitute), FlexibleTypesKt.upperIfFlexible(simpleTypeSubstitute2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [org.jetbrains.kotlin.types.UnwrappedType] */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v5, types: [org.jetbrains.kotlin.types.KotlinType] */
    private default UnwrappedType substitute(UnwrappedType type, boolean keepAnnotation, boolean runCapturedChecks) {
        Object obj;
        SimpleType lowerBound;
        SimpleType upperBound;
        KotlinType kotlinTypeSubstituteTypeEnhancement;
        if (type instanceof SimpleType) {
            return substitute((SimpleType) type, keepAnnotation, runCapturedChecks);
        }
        if (!(type instanceof FlexibleType)) {
            throw new NoWhenBranchMatchedException();
        }
        if (!(type instanceof DynamicType) && !(type instanceof RawType)) {
            FlexibleType flexibleType = (FlexibleType) type;
            UnwrappedType unwrappedTypeSubstitute = substitute(flexibleType.getLowerBound(), keepAnnotation, runCapturedChecks);
            UnwrappedType unwrappedTypeSubstitute2 = substitute(flexibleType.getUpperBound(), keepAnnotation, runCapturedChecks);
            if (type instanceof TypeWithEnhancement) {
                kotlinTypeSubstituteTypeEnhancement = substituteTypeEnhancement(((TypeWithEnhancement) type).getEnhancement(), keepAnnotation, runCapturedChecks);
            } else {
                obj = null;
            }
            if (unwrappedTypeSubstitute != null || unwrappedTypeSubstitute2 != null) {
                if (unwrappedTypeSubstitute == null || (lowerBound = FlexibleTypesKt.lowerIfFlexible(unwrappedTypeSubstitute)) == null) {
                    lowerBound = flexibleType.getLowerBound();
                }
                if (unwrappedTypeSubstitute2 == null || (upperBound = FlexibleTypesKt.upperIfFlexible(unwrappedTypeSubstitute2)) == null) {
                    upperBound = flexibleType.getUpperBound();
                }
                UnwrappedType unwrappedTypeFlexibleType = KotlinTypeFactory.flexibleType(lowerBound, upperBound);
                boolean z = obj instanceof TypeWithEnhancement;
                ?? enhancement = obj;
                if (z) {
                    enhancement = ((TypeWithEnhancement) obj).getEnhancement();
                }
                return TypeWithEnhancementKt.wrapEnhancement(unwrappedTypeFlexibleType, enhancement);
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:91:0x01ab A[PHI: r10
      0x01ab: PHI (r10v2 boolean) = (r10v1 boolean), (r10v4 boolean) binds: [B:88:0x01a6, B:90:0x01a9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    private default UnwrappedType substitute(SimpleType type, boolean keepAnnotation, boolean runCapturedChecks) {
        NewCapturedType newCapturedType;
        SimpleType simpleType = type;
        if (KotlinTypeKt.isError(simpleType)) {
            return null;
        }
        if (type instanceof AbbreviatedType) {
            AbbreviatedType abbreviatedType = (AbbreviatedType) type;
            UnwrappedType unwrappedTypeSubstitute = substitute(abbreviatedType.getExpandedType(), keepAnnotation, runCapturedChecks);
            UnwrappedType unwrappedTypeSubstitute2 = substitute(abbreviatedType.getAbbreviation(), keepAnnotation, runCapturedChecks);
            if (unwrappedTypeSubstitute == null && unwrappedTypeSubstitute2 == null) {
                return null;
            }
            if (unwrappedTypeSubstitute == null ? true : unwrappedTypeSubstitute instanceof SimpleType) {
                if (unwrappedTypeSubstitute2 != null ? unwrappedTypeSubstitute2 instanceof SimpleType : true) {
                    SimpleType expandedType = (SimpleType) unwrappedTypeSubstitute;
                    if (expandedType == null) {
                        expandedType = abbreviatedType.getExpandedType();
                    }
                    SimpleType abbreviation = (SimpleType) unwrappedTypeSubstitute2;
                    if (abbreviation == null) {
                        abbreviation = abbreviatedType.getAbbreviation();
                    }
                    return new AbbreviatedType(expandedType, abbreviation);
                }
            }
            return unwrappedTypeSubstitute;
        }
        if (!type.getArguments().isEmpty()) {
            return substituteParametrizedType(type, keepAnnotation, runCapturedChecks);
        }
        TypeConstructor constructor = type.getConstructor();
        boolean z = false;
        if (!(constructor instanceof NewCapturedTypeConstructor)) {
            if (constructor instanceof IntersectionTypeConstructor) {
                UnwrappedType unwrappedTypeSubstituteNotNullTypeWithConstructor = substituteNotNullTypeWithConstructor(constructor);
                if (unwrappedTypeSubstituteNotNullTypeWithConstructor != null) {
                    return substitute$updateNullability(type, unwrappedTypeSubstituteNotNullTypeWithConstructor);
                }
                Collection<KotlinType> collectionMo7438getSupertypes = ((IntersectionTypeConstructor) constructor).mo7438getSupertypes();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionMo7438getSupertypes, 10));
                for (KotlinType kotlinType : collectionMo7438getSupertypes) {
                    UnwrappedType unwrappedTypeSubstitute3 = substitute(kotlinType.unwrap(), keepAnnotation, runCapturedChecks);
                    if (unwrappedTypeSubstitute3 != null) {
                        z = true;
                        if (unwrappedTypeSubstitute3 == null) {
                            unwrappedTypeSubstitute3 = kotlinType.unwrap();
                        }
                    } else {
                        unwrappedTypeSubstitute3 = kotlinType.unwrap();
                    }
                    arrayList.add(unwrappedTypeSubstitute3);
                }
                ArrayList arrayList2 = arrayList;
                if (z) {
                    return substitute$updateNullability(type, IntersectionTypeKt.m7477intersectTypes((List<? extends UnwrappedType>) arrayList2));
                }
                return null;
            }
            UnwrappedType unwrappedTypeSubstituteNotNullTypeWithConstructor2 = substituteNotNullTypeWithConstructor(constructor);
            if (unwrappedTypeSubstituteNotNullTypeWithConstructor2 == null) {
                return null;
            }
            if (keepAnnotation) {
                unwrappedTypeSubstituteNotNullTypeWithConstructor2 = unwrappedTypeSubstituteNotNullTypeWithConstructor2.replaceAttributes(unwrappedTypeSubstituteNotNullTypeWithConstructor2.getAttributes().add(type.getAttributes()));
            }
            if (type.getIsMarkedNullable()) {
                unwrappedTypeSubstituteNotNullTypeWithConstructor2 = unwrappedTypeSubstituteNotNullTypeWithConstructor2.makeNullableAsSpecified(true);
            }
            if (SpecialTypesKt.isDefinitelyNotNullType(simpleType)) {
                unwrappedTypeSubstituteNotNullTypeWithConstructor2 = SpecialTypesKt.makeDefinitelyNotNullOrNotNull$default(unwrappedTypeSubstituteNotNullTypeWithConstructor2, false, 1, null);
            }
            return type instanceof CustomTypeParameter ? ((CustomTypeParameter) type).substitutionResult(unwrappedTypeSubstituteNotNullTypeWithConstructor2).unwrap() : unwrappedTypeSubstituteNotNullTypeWithConstructor2;
        }
        if (!runCapturedChecks) {
            return null;
        }
        if (!(type instanceof NewCapturedType) && (type instanceof DefinitelyNotNullType)) {
            boolean z2 = ((DefinitelyNotNullType) type).getOriginal() instanceof NewCapturedType;
        }
        if (type instanceof DefinitelyNotNullType) {
            SimpleType original = ((DefinitelyNotNullType) type).getOriginal();
            Intrinsics.checkNotNull(original, "null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedType");
            newCapturedType = (NewCapturedType) original;
        } else {
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedType");
            newCapturedType = (NewCapturedType) type;
        }
        UnwrappedType lowerType = newCapturedType.getLowerType();
        if (lowerType == null) {
            lowerType = newCapturedType.getConstructor().getProjection().getType().unwrap();
        }
        UnwrappedType unwrappedTypeSubstitute4 = substitute(lowerType, keepAnnotation, false);
        List<UnwrappedType> listMo7438getSupertypes = newCapturedType.getConstructor().mo7438getSupertypes();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listMo7438getSupertypes, 10));
        for (UnwrappedType unwrappedType : listMo7438getSupertypes) {
            UnwrappedType unwrappedTypeSubstitute5 = substitute(unwrappedType, keepAnnotation, false);
            if (unwrappedTypeSubstitute5 != null) {
                unwrappedType = unwrappedTypeSubstitute5;
            }
            arrayList3.add(unwrappedType);
        }
        ArrayList arrayList4 = arrayList3;
        if (unwrappedTypeSubstitute4 != null) {
            UnwrappedType unwrappedType2 = unwrappedTypeSubstitute4;
            if (CapturedTypeConstructorKt.isCaptured(unwrappedType2)) {
                return unwrappedTypeSubstitute4;
            }
            CaptureStatus captureStatus = newCapturedType.getCaptureStatus();
            NewCapturedTypeConstructor newCapturedTypeConstructor = (NewCapturedTypeConstructor) constructor;
            NewCapturedTypeConstructor newCapturedTypeConstructor2 = new NewCapturedTypeConstructor(new TypeProjectionImpl(newCapturedTypeConstructor.getProjection().getProjectionKind(), unwrappedType2), null, null, newCapturedTypeConstructor.getTypeParameter(), 6, null);
            newCapturedTypeConstructor2.initializeSupertypes(arrayList4);
            return new NewCapturedType(captureStatus, newCapturedTypeConstructor2, newCapturedType.getLowerType() != null ? unwrappedTypeSubstitute4 : null, null, type.getIsMarkedNullable(), false, 40, null);
        }
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            for (UnwrappedType unwrappedType3 : ((NewCapturedTypeConstructor) constructor).mo7438getSupertypes()) {
                UnwrappedType unwrappedTypeSubstitute6 = substitute(unwrappedType3, keepAnnotation, false);
                if (unwrappedTypeSubstitute6 != null) {
                    throwExceptionAboutInvalidCapturedSubstitution(newCapturedType, unwrappedType3, unwrappedTypeSubstitute6);
                    throw null;
                }
            }
        }
        return null;
    }

    private static UnwrappedType substitute$updateNullability(SimpleType simpleType, UnwrappedType unwrappedType) {
        return simpleType.getIsMarkedNullable() ? unwrappedType.makeNullableAsSpecified(true) : unwrappedType;
    }

    private default Void throwExceptionAboutInvalidCapturedSubstitution(SimpleType capturedType, UnwrappedType innerType, UnwrappedType substitutedInnerType) {
        throw new IllegalStateException("Illegal type substitutor: " + this + ", because for captured type '" + capturedType + "' supertype approximation should be null, but it is: '" + innerType + "',original supertype: '" + substitutedInnerType + '\'');
    }

    private default UnwrappedType substituteParametrizedType(SimpleType type, boolean keepAnnotation, boolean runCapturedChecks) {
        List<TypeParameterDescriptor> parameters = type.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<TypeProjection> arguments = type.getArguments();
        int i = 0;
        if (parameters.size() != arguments.size()) {
            return ErrorUtils.createErrorType(ErrorTypeKind.TYPE_WITH_MISMATCHED_TYPE_ARGUMENTS_AND_PARAMETERS, type.toString(), String.valueOf(parameters.size()), String.valueOf(arguments.size()));
        }
        int size = arguments.size();
        TypeProjection[] typeProjectionArr = new TypeProjection[size];
        int size2 = arguments.size();
        for (int i2 = 0; i2 < size2; i2++) {
            TypeProjection typeProjection = arguments.get(i2);
            if (!typeProjection.isStarProjection()) {
                TypeProjection typeProjectionSubstituteArgumentProjection = substituteArgumentProjection(typeProjection);
                if (typeProjectionSubstituteArgumentProjection != null) {
                    typeProjectionArr[i2] = typeProjectionSubstituteArgumentProjection;
                } else {
                    UnwrappedType unwrappedTypeSubstitute = substitute(typeProjection.getType().unwrap(), keepAnnotation, runCapturedChecks);
                    if (unwrappedTypeSubstitute != null) {
                        typeProjectionArr[i2] = new TypeProjectionImpl(typeProjection.getProjectionKind(), unwrappedTypeSubstitute);
                    }
                }
            }
        }
        for (int i3 = 0; i3 < size; i3++) {
            if (typeProjectionArr[i3] != null) {
                List<TypeProjection> list = arguments;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (Object obj : list) {
                    int i4 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    TypeProjection typeProjection2 = (TypeProjection) obj;
                    TypeProjection typeProjection3 = typeProjectionArr[i];
                    if (typeProjection3 != null) {
                        typeProjection2 = typeProjection3;
                    }
                    arrayList.add(typeProjection2);
                    i = i4;
                }
                return TypeSubstitutionKt.replace$default(type, arrayList, null, 2, null);
            }
        }
        return null;
    }
}
