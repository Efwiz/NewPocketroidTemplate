package org.jetbrains.kotlin.types.typeUtil;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.PossiblyInnerType;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterUtilsKt;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.AbbreviatedType;
import org.jetbrains.kotlin.types.AbstractStubType;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.FlexibleTypeImpl;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.IntersectionTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.StarProjectionImpl;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.StubTypeForTypeVariablesInSubtyping;
import org.jetbrains.kotlin.types.TypeAttributesKt;
import org.jetbrains.kotlin.types.TypeCapabilitiesKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.TypeWithEnhancementKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.IntersectionTypeKt;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.checker.NewCapturedType;
import org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor;
import org.jetbrains.kotlin.types.error.ErrorType;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;

/* JADX INFO: compiled from: TypeUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\n\u001a\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\n\u001a$\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\u000f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\nH\u0002\u001a \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0006\u001a.\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00062\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0010\b\u0002\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u001cH\u0007\u001a\u0016\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u0002\u001a\u001f\u0010 \u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u0002\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0001(\u0000\u001a\u000e\u0010\"\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u0011\u001a\n\u0010$\u001a\u00020\u0011*\u00020\u0002\u001a\n\u0010%\u001a\u00020\u0017*\u00020\u0002\u001a@\u0010&\u001a\b\u0012\u0004\u0012\u0002H'0\n\"\u0004\b\u0000\u0010'*\b\u0012\u0004\u0012\u0002H'0\n2\b\b\u0002\u0010(\u001a\u00020\u00172\u0018\u0010)\u001a\u0014\u0012\u0004\u0012\u0002H'\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0\n0*\u001a\u0010\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\n*\u00020\u0002\u001a\u001e\u0010+\u001a\u00020\u0017*\u00020\u00022\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u00170*\u001a\n\u0010.\u001a\u00020\u0017*\u00020\u0002\u001a$\u0010/\u001a\u00020\u0017*\u00020\u00022\u0006\u00100\u001a\u00020\u001a2\u000e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u001cH\u0002\u001a\n\u00101\u001a\u00020\u0017*\u00020\u0002\u001a\n\u00102\u001a\u00020\u0017*\u00020\u0002\u001a\n\u00103\u001a\u00020\u0017*\u00020\u0002\u001a\n\u00104\u001a\u00020\u0017*\u00020\u0002\u001a\u0016\u00105\u001a\b\u0012\u0004\u0012\u00020\u001106*\b\u0012\u0004\u0012\u00020\u000206\u001a\u001e\u00107\u001a\u00020\u0017*\u0002082\b\u00109\u001a\u0004\u0018\u00010\u00022\b\u0010:\u001a\u0004\u0018\u00010\u0002\u001a\u0010\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00020\n*\u00020\u0002\u001a \u0010<\u001a\b\u0012\u0004\u0012\u00020\u00060\u001c*\u00020\u00022\u000e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u001c\u001a2\u0010<\u001a\u00020\r*\u00020\u00022\u0006\u0010=\u001a\u00020\u00022\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00060\u000f2\u000e\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u001cH\u0002\u001a\f\u0010?\u001a\u0004\u0018\u00010\u0002*\u00020\u0002\u001a\u0010\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00020\n*\u00020\u0002\u001a\n\u0010A\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010B\u001a\u00020\u0017*\u00020\u0002\u001a\f\u0010C\u001a\u00020\u0017*\u0004\u0018\u00010\u0002\u001a\n\u0010D\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010E\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010F\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010G\u001a\u00020\u0017*\u00020\u0002\u001a\u0019\u0010H\u001a\u00020\u0017\"\n\b\u0000\u0010I\u0018\u0001*\u00020J*\u00020\u0002H\u0082\b\u001a\n\u0010K\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010L\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010M\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010N\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010O\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010P\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010Q\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010R\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010S\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010T\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010U\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010V\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010W\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010X\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010Y\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010Z\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010[\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010\\\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010]\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010^\u001a\u00020\u0017*\u00020\u0002\u001a\u0012\u0010_\u001a\u00020\u0017*\u00020\u00022\u0006\u0010`\u001a\u00020\u0002\u001a\n\u0010a\u001a\u00020\u0017*\u00020b\u001a\n\u0010c\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010d\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010e\u001a\u00020\u0017*\u00020\u0002\u001a\n\u0010f\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010g\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010h\u001a\u00020i*\u00020\u0002\u001a\u0012\u0010j\u001a\u00020\u0002*\u00020\u00022\u0006\u0010k\u001a\u00020l\u001a$\u0010m\u001a\u00020n*\u00020n2\u0012\u0010o\u001a\u000e\u0012\u0004\u0012\u00020p\u0012\u0004\u0012\u00020p0*H\u0086\bø\u0001\u0000\u001a$\u0010q\u001a\u00020\u0002*\u00020\u00022\u0012\u0010o\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00110*H\u0086\bø\u0001\u0000\u001a$\u0010q\u001a\u00020n*\u00020n2\u0012\u0010o\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00110*H\u0086\bø\u0001\u0000\u001a\n\u0010r\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010s\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010t\u001a\u00020\u0017*\u00020\u0002\u001a\f\u0010u\u001a\u00020\u0017*\u0004\u0018\u00010\u0002\u001a\u001e\u0010v\u001a\u00020\u0011*\u00020\u00112\u0012\u0010w\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020*\u001a\u0010\u0010x\u001a\b\u0012\u0004\u0012\u00020\u00020\n*\u00020\u0002\u001a\n\u0010y\u001a\u00020n*\u00020z\u001a\n\u0010y\u001a\u00020{*\u00020{\u001a\n\u0010y\u001a\u00020\u0002*\u00020\u0002\u001a\n\u0010y\u001a\u00020-*\u00020n\u001a\n\u0010y\u001a\u00020-*\u00020-\u001a\f\u0010|\u001a\u00020-*\u00020}H\u0002\u001a\n\u0010~\u001a\u00020\u0017*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0002*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bò\u0001\u0004\n\u00020!\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u007f"}, d2 = {"builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "Lorg/jetbrains/kotlin/types/KotlinType;", "getBuiltIns", "(Lorg/jetbrains/kotlin/types/KotlinType;)Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "representativeUpperBound", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getRepresentativeUpperBound", "(Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;)Lorg/jetbrains/kotlin/types/KotlinType;", "boundClosure", "", SpecTypePair.NAME_types, "constituentTypes", "", "result", "", "createProjection", "Lorg/jetbrains/kotlin/types/TypeProjection;", ModuleXmlParser.TYPE, "projectionKind", "Lorg/jetbrains/kotlin/types/Variance;", "typeParameterDescriptor", "hasTypeParameterRecursiveBounds", "", "typeParameter", "selfConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "visitedTypeParameters", "", "isNullabilityMismatch", "expected", "actual", "isUnresolvedType", "Lorg/jetbrains/kotlin/types/error/ErrorType;", "unCaptureProjection", "projection", "asTypeProjection", "cannotBeReified", "closure", ExifInterface.GPS_DIRECTION_TRUE, "preserveOrder", "f", "Lkotlin/Function1;", "contains", "predicate", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "containsError", "containsSelfTypeParameter", "baseConstructor", "containsTypeAliasParameters", "containsTypeAliases", "containsTypeParameter", "containsTypeProjectionsInTopLevelArguments", "defaultProjections", "", "equalTypesOrNulls", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeChecker;", "type1", "type2", "expandIntersectionTypeIfNecessary", "extractTypeParametersFromUpperBounds", "baseType", "to", "getImmediateSuperclassNotAny", "immediateSupertypes", "isAny", "isAnyOrNullableAny", "isArrayOfNothing", "isBoolean", "isBooleanOrNullableBoolean", "isByte", Namer.IS_CHAR, "isDefNotNullStubType", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/types/AbstractStubType;", "isDefaultBound", "isDouble", "isEnum", "isFloat", "isGenericArrayOfTypeParameter", "isInt", "isInterface", "isLong", "isNotNullThrowable", "isNothing", "isNothingOrNullableNothing", "isNullableAny", "isNullableNothing", "isPrimitiveNumberOrNullableType", "isPrimitiveNumberType", "isShort", "isSignedOrUnsignedNumberType", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isSubtypeOf", "superType", "isTypeAliasParameter", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "isTypeParameter", "isUnit", "isUnsignedNumberType", "makeNotNullable", "makeNullable", "nullability", "Lorg/jetbrains/kotlin/types/typeUtil/TypeNullability;", "replaceAnnotations", "newAnnotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "replaceArgumentsByExistingArgumentsWith", "Lorg/jetbrains/kotlin/types/SimpleType;", "replacement", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "replaceArgumentsByParametersWith", "replaceArgumentsWithNothing", "replaceArgumentsWithStarProjections", "requiresTypeAliasExpansion", "shouldBeUpdated", "substitute", "doSubstitute", "supertypes", "unCapture", "Lorg/jetbrains/kotlin/types/AbbreviatedType;", "Lorg/jetbrains/kotlin/types/FlexibleType;", "unCaptureTopLevelType", "Lorg/jetbrains/kotlin/types/checker/NewCapturedType;", "upperBoundedByPrimitiveNumberOrNullableType", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeUtilsKt {
    public static final boolean hasTypeParameterRecursiveBounds(TypeParameterDescriptor typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return hasTypeParameterRecursiveBounds$default(typeParameter, null, null, 6, null);
    }

    public static final boolean hasTypeParameterRecursiveBounds(TypeParameterDescriptor typeParameter, TypeConstructor typeConstructor) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        return hasTypeParameterRecursiveBounds$default(typeParameter, typeConstructor, null, 4, null);
    }

    public static final TypeNullability nullability(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        if (FlexibleTypesKt.isNullabilityFlexible(kotlinType)) {
            return TypeNullability.FLEXIBLE;
        }
        return TypeUtils.isNullableType(kotlinType) ? TypeNullability.NULLABLE : TypeNullability.NOT_NULL;
    }

    public static final KotlinBuiltIns getBuiltIns(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        KotlinBuiltIns builtIns = kotlinType.getConstructor().getBuiltIns();
        Intrinsics.checkNotNullExpressionValue(builtIns, "getBuiltIns(...)");
        return builtIns;
    }

    public static final KotlinType makeNullable(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        KotlinType kotlinTypeMakeNullable = TypeUtils.makeNullable(kotlinType);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNullable, "makeNullable(...)");
        return kotlinTypeMakeNullable;
    }

    public static final KotlinType makeNotNullable(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(kotlinType);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNotNullable, "makeNotNullable(...)");
        return kotlinTypeMakeNotNullable;
    }

    public static final Collection<KotlinType> immediateSupertypes(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        List<KotlinType> immediateSupertypes = TypeUtils.getImmediateSupertypes(kotlinType);
        Intrinsics.checkNotNullExpressionValue(immediateSupertypes, "getImmediateSupertypes(...)");
        return immediateSupertypes;
    }

    public static final Collection<KotlinType> supertypes(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Set<KotlinType> allSupertypes = TypeUtils.getAllSupertypes(kotlinType);
        Intrinsics.checkNotNullExpressionValue(allSupertypes, "getAllSupertypes(...)");
        return allSupertypes;
    }

    public static final boolean isNothing(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isNothing(kotlinType);
    }

    public static final boolean isNullableNothing(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isNullableNothing(kotlinType);
    }

    public static final boolean isNothingOrNullableNothing(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isNothingOrNullableNothing(kotlinType);
    }

    public static final boolean isUnit(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isUnit(kotlinType);
    }

    public static final boolean isAnyOrNullableAny(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isAnyOrNullableAny(kotlinType);
    }

    public static final boolean isAny(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isAny(kotlinType);
    }

    public static final boolean isNullableAny(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isNullableAny(kotlinType);
    }

    public static final boolean isBoolean(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isBoolean(kotlinType);
    }

    public static final boolean isPrimitiveNumberType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isPrimitiveType(kotlinType) && !isBoolean(kotlinType);
    }

    public static final boolean isUnsignedNumberType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return UnsignedTypes.isUnsignedType(kotlinType);
    }

    public static final boolean isSignedOrUnsignedNumberType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return isPrimitiveNumberType(kotlinType) || isUnsignedNumberType(kotlinType);
    }

    public static final boolean isBooleanOrNullableBoolean(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isBooleanOrNullableBoolean(kotlinType);
    }

    public static final boolean isNotNullThrowable(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isThrowableOrNullableThrowable(kotlinType) && !kotlinType.getIsMarkedNullable();
    }

    public static final boolean isByte(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isByte(kotlinType);
    }

    public static final boolean isChar(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isChar(kotlinType);
    }

    public static final boolean isShort(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isShort(kotlinType);
    }

    public static final boolean isInt(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isInt(kotlinType);
    }

    public static final boolean isLong(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isLong(kotlinType);
    }

    public static final boolean isFloat(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isFloat(kotlinType);
    }

    public static final boolean isDouble(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isDouble(kotlinType);
    }

    public static final boolean isPrimitiveNumberOrNullableType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return (!KotlinBuiltIns.isPrimitiveTypeOrNullablePrimitiveType(kotlinType) || KotlinBuiltIns.isBooleanOrNullableBoolean(kotlinType) || KotlinBuiltIns.isCharOrNullableChar(kotlinType)) ? false : true;
    }

    public static final boolean isTypeParameter(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return TypeUtils.isTypeParameter(kotlinType);
    }

    public static final boolean containsTypeParameter(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return TypeUtils.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt.containsTypeParameter.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType unwrappedType) {
                return Boolean.valueOf(TypeUtils.isTypeParameter(unwrappedType));
            }
        });
    }

    public static final boolean upperBoundedByPrimitiveNumberOrNullableType(KotlinType kotlinType) {
        List<KotlinType> upperBounds;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        TypeParameterDescriptor typeParameterDescriptorOrNull = TypeUtils.getTypeParameterDescriptorOrNull(kotlinType);
        if (typeParameterDescriptorOrNull == null || (upperBounds = typeParameterDescriptorOrNull.getUpperBounds()) == null) {
            return false;
        }
        List<KotlinType> list = upperBounds;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (KotlinType kotlinType2 : list) {
            Intrinsics.checkNotNull(kotlinType2);
            if (isPrimitiveNumberOrNullableType(kotlinType2) || upperBoundedByPrimitiveNumberOrNullableType(kotlinType2)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isInterface(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
        return (classDescriptor != null ? classDescriptor.getKind() : null) == ClassKind.INTERFACE;
    }

    public static final boolean isEnum(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
        return (classDescriptor != null ? classDescriptor.getKind() : null) == ClassKind.ENUM_CLASS;
    }

    public static final boolean isArrayOfNothing(KotlinType kotlinType) {
        if (kotlinType == null || !KotlinBuiltIns.isArray(kotlinType)) {
            return false;
        }
        TypeProjection typeProjection = (TypeProjection) CollectionsKt.firstOrNull((List) kotlinType.getArguments());
        KotlinType type = typeProjection != null ? typeProjection.getNullableAnyType() : null;
        return type != null && KotlinBuiltIns.isNothingOrNullableNothing(type);
    }

    public static final boolean isGenericArrayOfTypeParameter(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        if (!KotlinBuiltIns.isArray(kotlinType)) {
            return false;
        }
        TypeProjection typeProjection = kotlinType.getArguments().get(0);
        if (typeProjection.isStarProjection()) {
            return false;
        }
        KotlinType type = typeProjection.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return isTypeParameter(type) || isGenericArrayOfTypeParameter(type);
    }

    public static final boolean isSubtypeOf(KotlinType kotlinType, KotlinType superType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return KotlinTypeChecker.DEFAULT.isSubtypeOf(kotlinType, superType);
    }

    public static final boolean isNullabilityMismatch(KotlinType expected, KotlinType actual) {
        Intrinsics.checkNotNullParameter(expected, "expected");
        Intrinsics.checkNotNullParameter(actual, "actual");
        if (!expected.getIsMarkedNullable() && actual.getIsMarkedNullable()) {
            KotlinType kotlinTypeMakeNullable = TypeUtils.makeNullable(expected);
            Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNullable, "makeNullable(...)");
            if (isSubtypeOf(actual, kotlinTypeMakeNullable)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean cannotBeReified(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isNothingOrNullableNothing(kotlinType) || DynamicTypesKt.isDynamic(kotlinType) || CapturedTypeConstructorKt.isCaptured(kotlinType);
    }

    public static final TypeProjection substitute(TypeProjection typeProjection, Function1<? super KotlinType, ? extends KotlinType> doSubstitute) {
        Intrinsics.checkNotNullParameter(typeProjection, "<this>");
        Intrinsics.checkNotNullParameter(doSubstitute, "doSubstitute");
        if (typeProjection.isStarProjection()) {
            return typeProjection;
        }
        Variance projectionKind = typeProjection.getProjectionKind();
        KotlinType type = typeProjection.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return new TypeProjectionImpl(projectionKind, doSubstitute.invoke(type));
    }

    public static final KotlinType replaceAnnotations(KotlinType kotlinType, Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return (kotlinType.getAnnotations().isEmpty() && newAnnotations.isEmpty()) ? kotlinType : kotlinType.unwrap().replaceAttributes(TypeAttributesKt.replaceAnnotations(kotlinType.getAttributes(), newAnnotations));
    }

    public static final boolean equalTypesOrNulls(KotlinTypeChecker kotlinTypeChecker, KotlinType kotlinType, KotlinType kotlinType2) {
        Intrinsics.checkNotNullParameter(kotlinTypeChecker, "<this>");
        if (kotlinType == kotlinType2) {
            return true;
        }
        if (kotlinType == null || kotlinType2 == null) {
            return false;
        }
        return kotlinTypeChecker.equalTypes(kotlinType, kotlinType2);
    }

    public static final boolean containsError(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return ErrorUtils.INSTANCE.containsErrorType(kotlinType);
    }

    public static final List<TypeProjection> defaultProjections(List<? extends KotlinType> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        List<? extends KotlinType> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(new TypeProjectionImpl((KotlinType) it.next()));
        }
        return arrayList;
    }

    public static final boolean isDefaultBound(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return KotlinBuiltIns.isDefaultBound(TypeCapabilitiesKt.getSupertypeRepresentative(kotlinType));
    }

    public static final TypeProjection createProjection(KotlinType type, Variance projectionKind, TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(projectionKind, "projectionKind");
        if ((typeParameterDescriptor != null ? typeParameterDescriptor.getVariance() : null) == projectionKind) {
            projectionKind = Variance.INVARIANT;
        }
        return new TypeProjectionImpl(projectionKind, type);
    }

    public static /* synthetic */ Collection closure$default(Collection collection, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return closure(collection, z, function1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> Collection<T> closure(Collection<? extends T> collection, boolean z, Function1<? super T, ? extends Collection<? extends T>> f) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        if (collection.size() == 0) {
            return collection;
        }
        LinkedHashSet linkedHashSet = z ? new LinkedHashSet(collection) : new HashSet(collection);
        int size = 0;
        HashSet hashSet = linkedHashSet;
        while (linkedHashSet.size() > size) {
            size = linkedHashSet.size();
            LinkedHashSet linkedHashSet2 = z ? new LinkedHashSet() : new HashSet();
            Iterator<T> it = hashSet.iterator();
            while (it.hasNext()) {
                linkedHashSet2.addAll(f.invoke(it.next()));
            }
            linkedHashSet.addAll(linkedHashSet2);
            hashSet = linkedHashSet2;
        }
        return linkedHashSet;
    }

    public static final Collection<KotlinType> boundClosure(Collection<? extends KotlinType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return closure$default(types, false, new Function1<KotlinType, Collection<? extends KotlinType>>() { // from class: org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt.boundClosure.1
            @Override // kotlin.jvm.functions.Function1
            public final Collection<KotlinType> invoke(KotlinType type) {
                Intrinsics.checkNotNullParameter(type, "type");
                TypeParameterDescriptor typeParameterDescriptorOrNull = TypeUtils.getTypeParameterDescriptorOrNull(type);
                Collection<KotlinType> upperBounds = typeParameterDescriptorOrNull != null ? typeParameterDescriptorOrNull.getUpperBounds() : null;
                if (upperBounds == null) {
                    upperBounds = SetsKt.emptySet();
                }
                return upperBounds;
            }
        }, 1, null);
    }

    public static final Collection<KotlinType> constituentTypes(Collection<? extends KotlinType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        HashSet hashSet = new HashSet();
        constituentTypes(hashSet, types);
        return hashSet;
    }

    public static final Collection<KotlinType> constituentTypes(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return constituentTypes(CollectionsKt.listOf(kotlinType));
    }

    private static final void constituentTypes(Set<KotlinType> set, Collection<? extends KotlinType> collection) {
        set.addAll(collection);
        for (KotlinType kotlinType : collection) {
            if (FlexibleTypesKt.isFlexible(kotlinType)) {
                FlexibleType flexibleTypeAsFlexibleType = FlexibleTypesKt.asFlexibleType(kotlinType);
                constituentTypes(set, SetsKt.setOf((Object[]) new SimpleType[]{flexibleTypeAsFlexibleType.getLowerBound(), flexibleTypeAsFlexibleType.getUpperBound()}));
            } else {
                List<TypeProjection> arguments = kotlinType.getArguments();
                ArrayList arrayList = new ArrayList();
                for (TypeProjection typeProjection : arguments) {
                    KotlinType type = !typeProjection.isStarProjection() ? typeProjection.getNullableAnyType() : null;
                    if (type != null) {
                        arrayList.add(type);
                    }
                }
                constituentTypes(set, arrayList);
            }
        }
    }

    public static final KotlinType getImmediateSuperclassNotAny(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Collection<KotlinType> collectionMo7438getSupertypes = kotlinType.getConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : collectionMo7438getSupertypes) {
            KotlinType kotlinType2 = (KotlinType) obj;
            if (DescriptorUtils.isClassOrEnumClass(kotlinType2.getConstructor().getThis$0()) && !KotlinBuiltIns.isAnyOrNullableAny(kotlinType2)) {
                arrayList.add(obj);
            }
        }
        KotlinType kotlinType3 = (KotlinType) CollectionsKt.singleOrNull((List) arrayList);
        if (kotlinType3 != null) {
            return TypeUtils.createSubstitutedSupertype(kotlinType, kotlinType3, TypeSubstitutor.create(kotlinType));
        }
        return null;
    }

    public static final TypeProjection asTypeProjection(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return new TypeProjectionImpl(kotlinType);
    }

    public static final boolean contains(KotlinType kotlinType, Function1<? super UnwrappedType, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return TypeUtils.contains(kotlinType, (Function1<UnwrappedType, Boolean>) predicate);
    }

    public static final Set<TypeParameterDescriptor> extractTypeParametersFromUpperBounds(KotlinType kotlinType, Set<? extends TypeParameterDescriptor> set) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        extractTypeParametersFromUpperBounds(kotlinType, kotlinType, linkedHashSet, set);
        return linkedHashSet;
    }

    private static final void extractTypeParametersFromUpperBounds(KotlinType kotlinType, KotlinType kotlinType2, Set<TypeParameterDescriptor> set, Set<? extends TypeParameterDescriptor> set2) {
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().getThis$0();
        if (classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor) {
            if (!Intrinsics.areEqual(kotlinType.getConstructor(), kotlinType2.getConstructor())) {
                set.add(classifierDescriptorMo7434getDeclarationDescriptor);
                return;
            }
            for (KotlinType kotlinType3 : ((TypeParameterDescriptor) classifierDescriptorMo7434getDeclarationDescriptor).getUpperBounds()) {
                Intrinsics.checkNotNull(kotlinType3);
                extractTypeParametersFromUpperBounds(kotlinType3, kotlinType2, set, set2);
            }
            return;
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor2 = kotlinType.getConstructor().getThis$0();
        ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters = classifierDescriptorMo7434getDeclarationDescriptor2 instanceof ClassifierDescriptorWithTypeParameters ? (ClassifierDescriptorWithTypeParameters) classifierDescriptorMo7434getDeclarationDescriptor2 : null;
        List<TypeParameterDescriptor> declaredTypeParameters = classifierDescriptorWithTypeParameters != null ? classifierDescriptorWithTypeParameters.getDeclaredTypeParameters() : null;
        int i = 0;
        for (TypeProjection typeProjection : kotlinType.getArguments()) {
            int i2 = i + 1;
            TypeParameterDescriptor typeParameterDescriptor = declaredTypeParameters != null ? (TypeParameterDescriptor) CollectionsKt.getOrNull(declaredTypeParameters, i) : null;
            if ((typeParameterDescriptor == null || set2 == null || !set2.contains(typeParameterDescriptor)) && !typeProjection.isStarProjection() && !CollectionsKt.contains(set, typeProjection.getNullableAnyType().getConstructor().getThis$0()) && !Intrinsics.areEqual(typeProjection.getNullableAnyType().getConstructor(), kotlinType2.getConstructor())) {
                KotlinType type = typeProjection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                extractTypeParametersFromUpperBounds(type, kotlinType2, set, set2);
            }
            i = i2;
        }
    }

    public static /* synthetic */ boolean hasTypeParameterRecursiveBounds$default(TypeParameterDescriptor typeParameterDescriptor, TypeConstructor typeConstructor, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            typeConstructor = null;
        }
        if ((i & 4) != 0) {
            set = null;
        }
        return hasTypeParameterRecursiveBounds(typeParameterDescriptor, typeConstructor, set);
    }

    public static final boolean hasTypeParameterRecursiveBounds(TypeParameterDescriptor typeParameter, TypeConstructor typeConstructor, Set<? extends TypeParameterDescriptor> set) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        List<KotlinType> upperBounds = typeParameter.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
        List<KotlinType> list = upperBounds;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (KotlinType kotlinType : list) {
            Intrinsics.checkNotNull(kotlinType);
            if (containsSelfTypeParameter(kotlinType, typeParameter.getDefaultType().getConstructor(), set) && (typeConstructor == null || Intrinsics.areEqual(kotlinType.getConstructor(), typeConstructor))) {
                return true;
            }
        }
        return false;
    }

    private static final boolean containsSelfTypeParameter(KotlinType kotlinType, TypeConstructor typeConstructor, Set<? extends TypeParameterDescriptor> set) {
        boolean zContainsSelfTypeParameter;
        if (Intrinsics.areEqual(kotlinType.getConstructor(), typeConstructor)) {
            return true;
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().getThis$0();
        ClassifierDescriptorWithTypeParameters classifierDescriptorWithTypeParameters = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassifierDescriptorWithTypeParameters ? (ClassifierDescriptorWithTypeParameters) classifierDescriptorMo7434getDeclarationDescriptor : null;
        List<TypeParameterDescriptor> declaredTypeParameters = classifierDescriptorWithTypeParameters != null ? classifierDescriptorWithTypeParameters.getDeclaredTypeParameters() : null;
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(kotlinType.getArguments());
        if (!(iterableWithIndex instanceof Collection) || !((Collection) iterableWithIndex).isEmpty()) {
            for (IndexedValue indexedValue : iterableWithIndex) {
                int index = indexedValue.getIndex();
                TypeProjection typeProjection = (TypeProjection) indexedValue.component2();
                TypeParameterDescriptor typeParameterDescriptor = declaredTypeParameters != null ? (TypeParameterDescriptor) CollectionsKt.getOrNull(declaredTypeParameters, index) : null;
                if ((typeParameterDescriptor == null || set == null || !set.contains(typeParameterDescriptor)) && !typeProjection.isStarProjection()) {
                    KotlinType type = typeProjection.getNullableAnyType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    zContainsSelfTypeParameter = containsSelfTypeParameter(type, typeConstructor, set);
                } else {
                    zContainsSelfTypeParameter = false;
                }
                if (zContainsSelfTypeParameter) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final KotlinType replaceArgumentsByParametersWith(KotlinType kotlinType, Function1<? super TypeParameterDescriptor, ? extends TypeProjection> replacement) {
        SimpleType simpleTypeFlexibleType;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
            SimpleType lowerBound = flexibleType.getLowerBound();
            if (!lowerBound.getConstructor().getParameters().isEmpty() && lowerBound.getConstructor().getThis$0() != null) {
                List<TypeParameterDescriptor> parameters = lowerBound.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                List<TypeParameterDescriptor> list = parameters;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(replacement.invoke(it.next()));
                }
                lowerBound = TypeSubstitutionKt.replace$default(lowerBound, arrayList, null, 2, null);
            }
            SimpleType upperBound = flexibleType.getUpperBound();
            if (!upperBound.getConstructor().getParameters().isEmpty() && upperBound.getConstructor().getThis$0() != null) {
                List<TypeParameterDescriptor> parameters2 = upperBound.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
                List<TypeParameterDescriptor> list2 = parameters2;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(replacement.invoke(it2.next()));
                }
                upperBound = TypeSubstitutionKt.replace$default(upperBound, arrayList2, null, 2, null);
            }
            simpleTypeFlexibleType = KotlinTypeFactory.flexibleType(lowerBound, upperBound);
        } else if (unwrappedTypeUnwrap instanceof SimpleType) {
            SimpleType simpleTypeReplace$default = (SimpleType) unwrappedTypeUnwrap;
            if (!simpleTypeReplace$default.getConstructor().getParameters().isEmpty() && simpleTypeReplace$default.getConstructor().getThis$0() != null) {
                List<TypeParameterDescriptor> parameters3 = simpleTypeReplace$default.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters3, "getParameters(...)");
                List<TypeParameterDescriptor> list3 = parameters3;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator<T> it3 = list3.iterator();
                while (it3.hasNext()) {
                    arrayList3.add(replacement.invoke(it3.next()));
                }
                simpleTypeReplace$default = TypeSubstitutionKt.replace$default(simpleTypeReplace$default, arrayList3, null, 2, null);
            }
            simpleTypeFlexibleType = simpleTypeReplace$default;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return TypeWithEnhancementKt.inheritEnhancement(simpleTypeFlexibleType, unwrappedTypeUnwrap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final SimpleType replaceArgumentsByParametersWith(SimpleType simpleType, Function1<? super TypeParameterDescriptor, ? extends TypeProjection> replacement) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        if (simpleType.getConstructor().getParameters().isEmpty() || simpleType.getConstructor().getThis$0() == null) {
            return simpleType;
        }
        List<TypeParameterDescriptor> parameters = simpleType.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<TypeParameterDescriptor> list = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(replacement.invoke(it.next()));
        }
        return TypeSubstitutionKt.replace$default(simpleType, arrayList, null, 2, null);
    }

    public static final SimpleType replaceArgumentsByExistingArgumentsWith(SimpleType simpleType, Function1<? super TypeArgumentMarker, ? extends TypeArgumentMarker> replacement) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        if (simpleType.getArguments().isEmpty()) {
            return simpleType;
        }
        List<TypeProjection> arguments = simpleType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            TypeArgumentMarker typeArgumentMarkerInvoke = replacement.invoke((TypeProjection) it.next());
            Intrinsics.checkNotNull(typeArgumentMarkerInvoke, "null cannot be cast to non-null type org.jetbrains.kotlin.types.TypeProjection");
            arrayList.add((TypeProjection) typeArgumentMarkerInvoke);
        }
        return TypeSubstitutionKt.replace$default(simpleType, arrayList, null, 2, null);
    }

    public static final boolean containsTypeAliasParameters(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt.containsTypeAliasParameters.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = it.getConstructor().getThis$0();
                return Boolean.valueOf(classifierDescriptorMo7434getDeclarationDescriptor != null ? TypeUtilsKt.isTypeAliasParameter(classifierDescriptorMo7434getDeclarationDescriptor) : false);
            }
        });
    }

    public static final boolean containsTypeAliases(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt.containsTypeAliases.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getConstructor().getThis$0() instanceof TypeAliasDescriptor);
            }
        });
    }

    public static final boolean isTypeAliasParameter(ClassifierDescriptor classifierDescriptor) {
        Intrinsics.checkNotNullParameter(classifierDescriptor, "<this>");
        return (classifierDescriptor instanceof TypeParameterDescriptor) && (((TypeParameterDescriptor) classifierDescriptor).getContainingDeclaration() instanceof TypeAliasDescriptor);
    }

    public static final boolean requiresTypeAliasExpansion(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt.requiresTypeAliasExpansion.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = it.getConstructor().getThis$0();
                boolean z = false;
                if (classifierDescriptorMo7434getDeclarationDescriptor != null && ((classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeAliasDescriptor) || (classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor))) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    public static final boolean containsTypeProjectionsInTopLevelArguments(KotlinType kotlinType) {
        PossiblyInnerType possiblyInnerTypeBuildPossiblyInnerType;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        if (KotlinTypeKt.isError(kotlinType) || (possiblyInnerTypeBuildPossiblyInnerType = TypeParameterUtilsKt.buildPossiblyInnerType(kotlinType)) == null) {
            return false;
        }
        List<TypeProjection> arguments = possiblyInnerTypeBuildPossiblyInnerType.getArguments();
        if ((arguments instanceof Collection) && arguments.isEmpty()) {
            return false;
        }
        for (TypeProjection typeProjection : arguments) {
            if (typeProjection.isStarProjection() || typeProjection.getProjectionKind() != Variance.INVARIANT) {
                return true;
            }
        }
        return false;
    }

    public static final KotlinType getRepresentativeUpperBound(TypeParameterDescriptor typeParameterDescriptor) {
        Object obj;
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "<this>");
        List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
        upperBounds.isEmpty();
        List<KotlinType> upperBounds2 = typeParameterDescriptor.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds2, "getUpperBounds(...)");
        Iterator<T> it = upperBounds2.iterator();
        while (true) {
            obj = null;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = ((KotlinType) next).getConstructor().getThis$0();
            ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
            if (classDescriptor != null && classDescriptor.getKind() != ClassKind.INTERFACE && classDescriptor.getKind() != ClassKind.ANNOTATION_CLASS) {
                obj = next;
                break;
            }
        }
        KotlinType kotlinType = (KotlinType) obj;
        if (kotlinType != null) {
            return kotlinType;
        }
        List<KotlinType> upperBounds3 = typeParameterDescriptor.getUpperBounds();
        Intrinsics.checkNotNullExpressionValue(upperBounds3, "getUpperBounds(...)");
        Object objFirst = CollectionsKt.first((List<? extends Object>) upperBounds3);
        Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
        return (KotlinType) objFirst;
    }

    public static final Collection<KotlinType> expandIntersectionTypeIfNecessary(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        if (!(kotlinType.getConstructor() instanceof IntersectionTypeConstructor)) {
            return CollectionsKt.listOf(kotlinType);
        }
        Collection<KotlinType> collectionMo7438getSupertypes = kotlinType.getConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        if (!kotlinType.getIsMarkedNullable()) {
            return collectionMo7438getSupertypes;
        }
        Collection<KotlinType> collection = collectionMo7438getSupertypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        for (KotlinType kotlinType2 : collection) {
            Intrinsics.checkNotNull(kotlinType2);
            arrayList.add(makeNullable(kotlinType2));
        }
        return arrayList;
    }

    public static final KotlinType unCapture(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return unCapture(kotlinType.unwrap());
    }

    public static final UnwrappedType unCapture(UnwrappedType unwrappedType) {
        Intrinsics.checkNotNullParameter(unwrappedType, "<this>");
        if (unwrappedType instanceof AbbreviatedType) {
            return unCapture((AbbreviatedType) unwrappedType);
        }
        if (unwrappedType instanceof SimpleType) {
            return unCapture((SimpleType) unwrappedType);
        }
        if (unwrappedType instanceof FlexibleType) {
            return unCapture((FlexibleType) unwrappedType);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final UnwrappedType unCapture(SimpleType simpleType) {
        Intrinsics.checkNotNullParameter(simpleType, "<this>");
        if (simpleType instanceof ErrorType) {
            return simpleType;
        }
        if (simpleType instanceof NewCapturedType) {
            return unCaptureTopLevelType((NewCapturedType) simpleType);
        }
        List<TypeProjection> arguments = simpleType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(unCaptureProjection((TypeProjection) it.next()));
        }
        return TypeSubstitutionKt.replace$default(simpleType, arrayList, null, 2, null).unwrap();
    }

    public static final TypeProjection unCaptureProjection(TypeProjection projection) {
        TypeProjection projection2;
        Intrinsics.checkNotNullParameter(projection, "projection");
        TypeConstructor constructor = projection.getNullableAnyType().getConstructor();
        NewCapturedTypeConstructor newCapturedTypeConstructor = constructor instanceof NewCapturedTypeConstructor ? (NewCapturedTypeConstructor) constructor : null;
        if (newCapturedTypeConstructor != null && (projection2 = newCapturedTypeConstructor.getProjection()) != null) {
            projection = projection2;
        }
        if (projection.isStarProjection() || (projection.getNullableAnyType() instanceof ErrorType)) {
            return projection;
        }
        List<TypeProjection> arguments = projection.getNullableAnyType().getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(unCaptureProjection((TypeProjection) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        Variance projectionKind = projection.getProjectionKind();
        KotlinType type = projection.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return new TypeProjectionImpl(projectionKind, TypeSubstitutionKt.replace$default(type, arrayList2, null, null, 6, null));
    }

    public static final SimpleType unCapture(AbbreviatedType abbreviatedType) {
        Intrinsics.checkNotNullParameter(abbreviatedType, "<this>");
        UnwrappedType unwrappedTypeUnCapture = unCapture(abbreviatedType.getExpandedType());
        SimpleType expandedType = unwrappedTypeUnCapture instanceof SimpleType ? (SimpleType) unwrappedTypeUnCapture : null;
        if (expandedType == null) {
            expandedType = abbreviatedType.getExpandedType();
        }
        return new AbbreviatedType(expandedType, abbreviatedType.getAbbreviation());
    }

    public static final FlexibleType unCapture(FlexibleType flexibleType) {
        SimpleType lowerBound;
        SimpleType upperBound;
        Intrinsics.checkNotNullParameter(flexibleType, "<this>");
        UnwrappedType unwrappedTypeUnCapture = unCapture(flexibleType.getLowerBound());
        if (unwrappedTypeUnCapture instanceof SimpleType) {
            lowerBound = (SimpleType) unwrappedTypeUnCapture;
        } else {
            if (!(unwrappedTypeUnCapture instanceof FlexibleType)) {
                throw new NoWhenBranchMatchedException();
            }
            lowerBound = ((FlexibleType) unwrappedTypeUnCapture).getLowerBound();
        }
        UnwrappedType unwrappedTypeUnCapture2 = unCapture(flexibleType.getUpperBound());
        if (unwrappedTypeUnCapture2 instanceof SimpleType) {
            upperBound = (SimpleType) unwrappedTypeUnCapture2;
        } else {
            if (!(unwrappedTypeUnCapture2 instanceof FlexibleType)) {
                throw new NoWhenBranchMatchedException();
            }
            upperBound = ((FlexibleType) unwrappedTypeUnCapture2).getUpperBound();
        }
        return new FlexibleTypeImpl(lowerBound, upperBound);
    }

    private static final UnwrappedType unCaptureTopLevelType(NewCapturedType newCapturedType) {
        if (newCapturedType.getLowerType() != null) {
            return newCapturedType.getLowerType();
        }
        List<UnwrappedType> listMo7438getSupertypes = newCapturedType.getConstructor().mo7438getSupertypes();
        return !listMo7438getSupertypes.isEmpty() ? IntersectionTypeKt.m7477intersectTypes((List<? extends UnwrappedType>) listMo7438getSupertypes) : newCapturedType.getConstructor().getProjection().getNullableAnyType().unwrap();
    }

    public static final boolean shouldBeUpdated(KotlinType kotlinType) {
        return kotlinType == null || contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt.shouldBeUpdated.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof StubTypeForBuilderInference) || (it.getConstructor() instanceof TypeVariableTypeConstructorMarker) || KotlinTypeKt.isError(it));
            }
        });
    }

    public static final boolean isStubType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return (kotlinType instanceof AbstractStubType) || ((kotlinType instanceof DefinitelyNotNullType) && (((DefinitelyNotNullType) kotlinType).getOriginal() instanceof AbstractStubType));
    }

    public static final boolean isStubTypeForVariableInSubtyping(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return (kotlinType instanceof StubTypeForTypeVariablesInSubtyping) || ((kotlinType instanceof DefinitelyNotNullType) && (((DefinitelyNotNullType) kotlinType).getOriginal() instanceof StubTypeForTypeVariablesInSubtyping));
    }

    public static final boolean isStubTypeForBuilderInference(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return (kotlinType instanceof StubTypeForBuilderInference) || ((kotlinType instanceof DefinitelyNotNullType) && (((DefinitelyNotNullType) kotlinType).getOriginal() instanceof StubTypeForBuilderInference));
    }

    public static final boolean isUnresolvedType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return (type instanceof ErrorType) && ((ErrorType) type).getKind().getIsUnresolved();
    }

    public static final KotlinType replaceArgumentsWithStarProjections(KotlinType kotlinType) {
        SimpleType simpleTypeFlexibleType;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
            SimpleType lowerBound = flexibleType.getLowerBound();
            if (!lowerBound.getConstructor().getParameters().isEmpty() && lowerBound.getConstructor().getThis$0() != null) {
                List<TypeParameterDescriptor> parameters = lowerBound.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                List<TypeParameterDescriptor> list = parameters;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(new StarProjectionImpl((TypeParameterDescriptor) it.next()));
                }
                lowerBound = TypeSubstitutionKt.replace$default(lowerBound, arrayList, null, 2, null);
            }
            SimpleType upperBound = flexibleType.getUpperBound();
            if (!upperBound.getConstructor().getParameters().isEmpty() && upperBound.getConstructor().getThis$0() != null) {
                List<TypeParameterDescriptor> parameters2 = upperBound.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
                List<TypeParameterDescriptor> list2 = parameters2;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(new StarProjectionImpl((TypeParameterDescriptor) it2.next()));
                }
                upperBound = TypeSubstitutionKt.replace$default(upperBound, arrayList2, null, 2, null);
            }
            simpleTypeFlexibleType = KotlinTypeFactory.flexibleType(lowerBound, upperBound);
        } else {
            if (!(unwrappedTypeUnwrap instanceof SimpleType)) {
                throw new NoWhenBranchMatchedException();
            }
            SimpleType simpleTypeReplace$default = (SimpleType) unwrappedTypeUnwrap;
            if (!simpleTypeReplace$default.getConstructor().getParameters().isEmpty() && simpleTypeReplace$default.getConstructor().getThis$0() != null) {
                List<TypeParameterDescriptor> parameters3 = simpleTypeReplace$default.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters3, "getParameters(...)");
                List<TypeParameterDescriptor> list3 = parameters3;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator<T> it3 = list3.iterator();
                while (it3.hasNext()) {
                    arrayList3.add(new StarProjectionImpl((TypeParameterDescriptor) it3.next()));
                }
                simpleTypeReplace$default = TypeSubstitutionKt.replace$default(simpleTypeReplace$default, arrayList3, null, 2, null);
            }
            simpleTypeFlexibleType = simpleTypeReplace$default;
        }
        return TypeWithEnhancementKt.inheritEnhancement(simpleTypeFlexibleType, unwrappedTypeUnwrap);
    }

    public static final KotlinType replaceArgumentsWithNothing(KotlinType kotlinType) {
        SimpleType simpleTypeFlexibleType;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        UnwrappedType unwrappedTypeUnwrap = kotlinType.unwrap();
        if (unwrappedTypeUnwrap instanceof FlexibleType) {
            FlexibleType flexibleType = (FlexibleType) unwrappedTypeUnwrap;
            SimpleType lowerBound = flexibleType.getLowerBound();
            if (!lowerBound.getConstructor().getParameters().isEmpty() && lowerBound.getConstructor().getThis$0() != null) {
                List<TypeParameterDescriptor> parameters = lowerBound.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                List<TypeParameterDescriptor> list = parameters;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    SimpleType nothingType = DescriptorUtilsKt.getBuiltIns((TypeParameterDescriptor) it.next()).getNothingType();
                    Intrinsics.checkNotNullExpressionValue(nothingType, "getNothingType(...)");
                    arrayList.add(asTypeProjection(nothingType));
                }
                lowerBound = TypeSubstitutionKt.replace$default(lowerBound, arrayList, null, 2, null);
            }
            SimpleType upperBound = flexibleType.getUpperBound();
            if (!upperBound.getConstructor().getParameters().isEmpty() && upperBound.getConstructor().getThis$0() != null) {
                List<TypeParameterDescriptor> parameters2 = upperBound.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
                List<TypeParameterDescriptor> list2 = parameters2;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    SimpleType nothingType2 = DescriptorUtilsKt.getBuiltIns((TypeParameterDescriptor) it2.next()).getNothingType();
                    Intrinsics.checkNotNullExpressionValue(nothingType2, "getNothingType(...)");
                    arrayList2.add(asTypeProjection(nothingType2));
                }
                upperBound = TypeSubstitutionKt.replace$default(upperBound, arrayList2, null, 2, null);
            }
            simpleTypeFlexibleType = KotlinTypeFactory.flexibleType(lowerBound, upperBound);
        } else {
            if (!(unwrappedTypeUnwrap instanceof SimpleType)) {
                throw new NoWhenBranchMatchedException();
            }
            SimpleType simpleTypeReplace$default = (SimpleType) unwrappedTypeUnwrap;
            if (!simpleTypeReplace$default.getConstructor().getParameters().isEmpty() && simpleTypeReplace$default.getConstructor().getThis$0() != null) {
                List<TypeParameterDescriptor> parameters3 = simpleTypeReplace$default.getConstructor().getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters3, "getParameters(...)");
                List<TypeParameterDescriptor> list3 = parameters3;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator<T> it3 = list3.iterator();
                while (it3.hasNext()) {
                    SimpleType nothingType3 = DescriptorUtilsKt.getBuiltIns((TypeParameterDescriptor) it3.next()).getNothingType();
                    Intrinsics.checkNotNullExpressionValue(nothingType3, "getNothingType(...)");
                    arrayList3.add(asTypeProjection(nothingType3));
                }
                simpleTypeReplace$default = TypeSubstitutionKt.replace$default(simpleTypeReplace$default, arrayList3, null, 2, null);
            }
            simpleTypeFlexibleType = simpleTypeReplace$default;
        }
        return TypeWithEnhancementKt.inheritEnhancement(simpleTypeFlexibleType, unwrappedTypeUnwrap);
    }
}
