package org.jetbrains.kotlin.types.model;

import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: TypeSystemContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b.\n\u0002\u0010(\n\u0002\b\t\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH&J\u001a\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H&J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u000e\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0014H&J\u0016\u0010\u0015\u001a\u00020\u00122\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u0017H&J\u0016\u0010\u0015\u001a\u00020\r2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\r0\u0017H&J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\rH&J\u001c\u0010\u001a\u001a\u00020\u00142\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00120\u001cH&J\f\u0010\u001d\u001a\u00020\u001e*\u00020\u0012H&J\f\u0010\u001f\u001a\u00020 *\u00020\rH&J\u000e\u0010!\u001a\u0004\u0018\u00010\"*\u00020\rH&J\u000e\u0010#\u001a\u0004\u0018\u00010$*\u00020\rH&J\u000e\u0010%\u001a\u0004\u0018\u00010&*\u00020'H&J\u000e\u0010(\u001a\u0004\u0018\u00010'*\u00020\u0012H&J\u000e\u0010)\u001a\u0004\u0018\u00010\r*\u00020\u0012H&J\f\u0010*\u001a\u00020+*\u00020\u0012H&J\f\u0010,\u001a\u00020\u0010*\u00020\"H&J\u001c\u0010-\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u0017*\u00020\r2\u0006\u0010.\u001a\u00020\nH\u0016J\u0015\u0010/\u001a\u00020+*\u00020 2\u0006\u00100\u001a\u00020\u001eH\u0096\u0002J\u0014\u00101\u001a\u00020+*\u00020\u00122\u0006\u00100\u001a\u00020\u001eH&J\u0016\u00102\u001a\u0004\u0018\u00010+*\u00020\r2\u0006\u00100\u001a\u00020\u001eH\u0016J\u0012\u00103\u001a\b\u0012\u0004\u0012\u00020+0\u0017*\u00020\u0012H&J\u0012\u00104\u001a\b\u0012\u0004\u0012\u0002050\u0017*\u00020\u0012H&J\u0012\u00106\u001a\b\u0012\u0004\u0012\u0002050\u0017*\u00020\u0012H&J\u0014\u00107\u001a\u00020\u0003*\u00020\n2\u0006\u00100\u001a\u00020\u001eH&J\u0012\u00108\u001a\b\u0012\u0004\u0012\u00020\u00030\u0017*\u00020\nH&J\f\u00109\u001a\u00020\u0012*\u00020+H&J\f\u0010:\u001a\u00020\n*\u00020\u0003H&J\u000e\u0010;\u001a\u0004\u0018\u00010\u0003*\u00020\nH&J\u0014\u0010<\u001a\u00020\u0012*\u00020\u00032\u0006\u00100\u001a\u00020\u001eH&J\u0012\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00120\u0017*\u00020\u0003H&J\f\u0010>\u001a\u00020?*\u00020+H&J\f\u0010>\u001a\u00020?*\u00020\u0003H&J\f\u0010@\u001a\u00020\b*\u00020\u0012H&J\f\u0010A\u001a\u00020\b*\u00020\u0012H\u0016J\u0018\u0010B\u001a\u00020\b*\u00020\u00032\n\b\u0002\u0010C\u001a\u0004\u0018\u00010\nH&J\f\u0010D\u001a\u00020\b*\u00020\nH&J\f\u0010E\u001a\u00020\b*\u00020\nH&J\f\u0010F\u001a\u00020\b*\u00020\u0012H\u0016J\f\u0010G\u001a\u00020\b*\u00020\u0012H\u0016J\f\u0010H\u001a\u00020\b*\u00020\rH\u0016J\f\u0010I\u001a\u00020\b*\u00020\nH&J\f\u0010J\u001a\u00020\b*\u00020\nH&J\f\u0010K\u001a\u00020\b*\u00020\nH\u0016J\f\u0010L\u001a\u00020\b*\u00020\u0012H\u0016J\f\u0010M\u001a\u00020\b*\u00020\nH&J\f\u0010N\u001a\u00020\b*\u00020\u0012H\u0016J\f\u0010O\u001a\u00020\b*\u00020\u0012H&J\f\u0010O\u001a\u00020\b*\u00020\nH&J\f\u0010P\u001a\u00020\b*\u00020\u0012H\u0016J\f\u0010Q\u001a\u00020\b*\u00020\u0012H\u0016J\f\u0010R\u001a\u00020\b*\u00020\nH&J\f\u0010S\u001a\u00020\b*\u00020\nH&J\f\u0010T\u001a\u00020\b*\u00020\rH\u0016J\f\u0010U\u001a\u00020\b*\u00020\nH&J\f\u0010V\u001a\u00020\b*\u00020\nH&J\f\u0010W\u001a\u00020\b*\u00020\nH&J\f\u0010X\u001a\u00020\b*\u00020\nH&J\f\u0010Y\u001a\u00020\b*\u00020\u0012H\u0016J\f\u0010Y\u001a\u00020\b*\u00020\rH&J\f\u0010Z\u001a\u00020\b*\u00020\u0012H\u0017J\f\u0010[\u001a\u00020\b*\u00020\u0012H\u0016J\f\u0010\\\u001a\u00020\b*\u00020\nH&J\f\u0010]\u001a\u00020\b*\u00020\u0012H\u0016J\f\u0010^\u001a\u00020\b*\u00020\u0012H\u0016J\f\u0010_\u001a\u00020\b*\u00020\u0012H&J\f\u0010`\u001a\u00020\b*\u00020\"H&J\f\u0010a\u001a\u00020\b*\u00020\rH&J\f\u0010b\u001a\u00020\b*\u00020\"H&J\f\u0010c\u001a\u00020\b*\u00020\u0012H&J\f\u0010d\u001a\u00020\b*\u00020\u0012H\u0016J\f\u0010e\u001a\u00020\b*\u00020\rH&J\f\u0010f\u001a\u00020\b*\u00020+H&J\f\u0010g\u001a\u00020\b*\u00020\rH&J\f\u0010h\u001a\u00020\b*\u00020\rH&J\f\u0010i\u001a\u00020\b*\u00020\rH&J\f\u0010j\u001a\u00020\b*\u00020\nH&J\f\u0010k\u001a\u00020\b*\u00020\u0012H&J\f\u0010l\u001a\u00020\b*\u00020\u0012H&J\u0013\u0010m\u001a\b\u0012\u0004\u0012\u00020+0n*\u00020 H\u0096\u0002J\f\u0010o\u001a\u00020\r*\u00020'H&J\f\u0010p\u001a\u00020\r*\u00020\u0012H\u0016J\u000e\u0010q\u001a\u0004\u0018\u00010\u0012*\u00020\"H&J\f\u0010r\u001a\u00020\u0012*\u00020\u0012H&J\f\u0010s\u001a\u00020\r*\u00020\rH&J\f\u0010t\u001a\u00020\r*\u00020$H&J\f\u0010u\u001a\u00020\r*\u00020\rH\u0016J\f\u0010v\u001a\u00020\u001e*\u00020\nH&J\u0012\u0010w\u001a\b\u0012\u0004\u0012\u00020\u00120x*\u00020\rH&J\f\u0010y\u001a\u00020+*\u00020zH&J\u0014\u0010{\u001a\u00020+*\u00020+2\u0006\u0010|\u001a\u00020\u0012H&J\u0014\u0010}\u001a\u00020\u0012*\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u0012H&J\f\u0010~\u001a\u00020\u001e*\u00020 H\u0016J\u0012\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020\u00120x*\u00020\nH&J\r\u0010\u0080\u0001\u001a\u00020z*\u00020\"H&J\r\u0010\u0080\u0001\u001a\u00020\n*\u00020\u0012H\u0016J\r\u0010\u0080\u0001\u001a\u00020\n*\u00020\rH&J\r\u0010\u0081\u0001\u001a\u00020\n*\u00020\nH&J\r\u0010\u0082\u0001\u001a\u00020\r*\u00020'H&J\r\u0010\u0083\u0001\u001a\u00020\u001e*\u00020\u0003H&J\r\u0010\u0084\u0001\u001a\u00020\r*\u00020\u0012H\u0016J\u0016\u0010\u0085\u0001\u001a\u00020\u0012*\u00020\u00122\u0007\u0010\u0086\u0001\u001a\u00020\bH&J\u0016\u0010\u0085\u0001\u001a\u00020\r*\u00020\r2\u0007\u0010\u0086\u0001\u001a\u00020\bH&R\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u0003*\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0087\u0001À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemOptimizationContext;", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "areEqualTypeConstructors", "", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "captureFromArguments", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "intersectTypes", SpecTypePair.NAME_types, "", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "", "argumentsCount", "", "asArgumentList", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "asCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "captureStatus", "fastCorrespondingSupertypes", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "get", "index", "getArgument", "getArgumentOrNull", "getArguments", "getAttributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "getCustomAttributes", "getParameter", "getParameters", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUpperBound", "getUpperBounds", "getVariance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "hasCustomAttributes", "hasFlexibleNullability", "hasRecursiveBounds", "selfConstructor", "isAnonymous", "isAnyConstructor", "isCapturedDynamic", "isCapturedType", "isClassType", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isDefinitelyClassTypeConstructor", "isDefinitelyNotNullType", "isDenotable", "isDynamic", "isError", "isFlexible", "isFlexibleNothing", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isIntersection", "isLocalType", "isMarkedNullable", "isNotNullTypeParameter", "isNothing", "isNothingConstructor", "isNullableAny", "isNullableNothing", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isSimpleType", "isSingleClassifierType", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTypeParameterTypeConstructor", "isTypeVariableType", "isUninferredParameter", "iterator", "", "lowerBound", "lowerBoundIfFlexible", "lowerType", "makeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "originalIfDefinitelyNotNullable", "parametersCount", "possibleIntegerTypes", "", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "replaceType", "newType", "safeSubstitute", "size", "supertypes", "typeConstructor", "unwrapStubTypeVariableConstructor", "upperBound", "upperBoundCount", "upperBoundIfFlexible", "withNullability", "nullable", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeSystemContext extends TypeSystemOptimizationContext {
    boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2);

    int argumentsCount(KotlinTypeMarker kotlinTypeMarker);

    TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker);

    CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker);

    DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker);

    DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker);

    FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker);

    TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status);

    KotlinTypeMarker captureFromExpression(KotlinTypeMarker type);

    CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker);

    TypeSubstitutorMarker createEmptySubstitutor();

    default List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return null;
    }

    TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i);

    List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker);

    List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker);

    List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker);

    TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i);

    List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker);

    KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker);

    TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker);

    TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker);

    TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker);

    KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i);

    List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker);

    TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker);

    TypeVariance getVariance(TypeParameterMarker typeParameterMarker);

    boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker);

    boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker);

    KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types);

    /* JADX INFO: renamed from: intersectTypes */
    SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types);

    boolean isAnonymous(TypeConstructorMarker typeConstructorMarker);

    boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isDenotable(TypeConstructorMarker typeConstructorMarker);

    boolean isError(KotlinTypeMarker kotlinTypeMarker);

    boolean isError(TypeConstructorMarker typeConstructorMarker);

    boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isInterface(TypeConstructorMarker typeConstructorMarker);

    boolean isIntersection(TypeConstructorMarker typeConstructorMarker);

    boolean isLocalType(TypeConstructorMarker typeConstructorMarker);

    boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker);

    @ObsoleteTypeKind
    default boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return false;
    }

    boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isNullableType(KotlinTypeMarker kotlinTypeMarker);

    boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker);

    boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker);

    boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker);

    boolean isRawType(KotlinTypeMarker kotlinTypeMarker);

    boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker);

    boolean isStarProjection(TypeArgumentMarker typeArgumentMarker);

    boolean isStubType(SimpleTypeMarker simpleTypeMarker);

    boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker);

    boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker);

    boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker);

    boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker);

    KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker);

    KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker);

    SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker);

    int parametersCount(TypeConstructorMarker typeConstructorMarker);

    Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker);

    TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker);

    TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker kotlinTypeMarker);

    TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type);

    Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker);

    CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker);

    TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker);

    TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map);

    TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker);

    SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker);

    int upperBoundCount(TypeParameterMarker typeParameterMarker);

    KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z);

    SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z);

    default boolean isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = asSimpleType(kotlinTypeMarker);
        return (simpleTypeMarkerAsSimpleType != null ? asCapturedType(simpleTypeMarkerAsSimpleType) : null) != null;
    }

    default SimpleTypeMarker originalIfDefinitelyNotNullable(SimpleTypeMarker simpleTypeMarker) {
        SimpleTypeMarker simpleTypeMarkerOriginal;
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        DefinitelyNotNullTypeMarker definitelyNotNullTypeMarkerAsDefinitelyNotNullType = asDefinitelyNotNullType(simpleTypeMarker);
        return (definitelyNotNullTypeMarkerAsDefinitelyNotNullType == null || (simpleTypeMarkerOriginal = original(definitelyNotNullTypeMarkerAsDefinitelyNotNullType)) == null) ? simpleTypeMarker : simpleTypeMarkerOriginal;
    }

    default boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return (kotlinTypeMarker instanceof SimpleTypeMarker) && isMarkedNullable((SimpleTypeMarker) kotlinTypeMarker);
    }

    default TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        if (i < 0 || i >= argumentsCount(simpleTypeMarker)) {
            return null;
        }
        return getArgument(simpleTypeMarker, i);
    }

    static /* synthetic */ boolean hasRecursiveBounds$default(TypeSystemContext typeSystemContext, TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: hasRecursiveBounds");
        }
        if ((i & 1) != 0) {
            typeConstructorMarker = null;
        }
        return typeSystemContext.hasRecursiveBounds(typeParameterMarker, typeConstructorMarker);
    }

    default SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        SimpleTypeMarker simpleTypeMarkerLowerBound;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = asFlexibleType(kotlinTypeMarker);
        if (flexibleTypeMarkerAsFlexibleType != null && (simpleTypeMarkerLowerBound = lowerBound(flexibleTypeMarkerAsFlexibleType)) != null) {
            return simpleTypeMarkerLowerBound;
        }
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = asSimpleType(kotlinTypeMarker);
        Intrinsics.checkNotNull(simpleTypeMarkerAsSimpleType);
        return simpleTypeMarkerAsSimpleType;
    }

    default SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        SimpleTypeMarker simpleTypeMarkerUpperBound;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = asFlexibleType(kotlinTypeMarker);
        if (flexibleTypeMarkerAsFlexibleType != null && (simpleTypeMarkerUpperBound = upperBound(flexibleTypeMarkerAsFlexibleType)) != null) {
            return simpleTypeMarkerUpperBound;
        }
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = asSimpleType(kotlinTypeMarker);
        Intrinsics.checkNotNull(simpleTypeMarkerAsSimpleType);
        return simpleTypeMarkerAsSimpleType;
    }

    default boolean isDefinitelyClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return isClassTypeConstructor(typeConstructorMarker) && !isInterface(typeConstructorMarker);
    }

    default boolean isFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return asFlexibleType(kotlinTypeMarker) != null;
    }

    default boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = asFlexibleType(kotlinTypeMarker);
        return (flexibleTypeMarkerAsFlexibleType != null ? asDynamicType(flexibleTypeMarkerAsFlexibleType) : null) != null;
    }

    default boolean isCapturedDynamic(KotlinTypeMarker kotlinTypeMarker) {
        CapturedTypeMarker capturedTypeMarkerAsCapturedType;
        CapturedTypeConstructorMarker capturedTypeConstructorMarkerTypeConstructor;
        TypeArgumentMarker typeArgumentMarkerProjection;
        KotlinTypeMarker type;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = asSimpleType(kotlinTypeMarker);
        if (simpleTypeMarkerAsSimpleType == null || (capturedTypeMarkerAsCapturedType = asCapturedType(simpleTypeMarkerAsSimpleType)) == null || (capturedTypeConstructorMarkerTypeConstructor = typeConstructor(capturedTypeMarkerAsCapturedType)) == null || (typeArgumentMarkerProjection = projection(capturedTypeConstructorMarkerTypeConstructor)) == null) {
            return false;
        }
        if (isStarProjection(typeArgumentMarkerProjection)) {
            typeArgumentMarkerProjection = null;
        }
        return (typeArgumentMarkerProjection == null || (type = getType(typeArgumentMarkerProjection)) == null || !isDynamic(type)) ? false : true;
    }

    default boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = asSimpleType(kotlinTypeMarker);
        return (simpleTypeMarkerAsSimpleType != null ? asDefinitelyNotNullType(simpleTypeMarkerAsSimpleType) : null) != null;
    }

    default boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return isMarkedNullable(lowerBoundIfFlexible(kotlinTypeMarker)) != isMarkedNullable(upperBoundIfFlexible(kotlinTypeMarker));
    }

    default TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = asSimpleType(kotlinTypeMarker);
        if (simpleTypeMarkerAsSimpleType == null) {
            simpleTypeMarkerAsSimpleType = lowerBoundIfFlexible(kotlinTypeMarker);
        }
        return typeConstructor(simpleTypeMarkerAsSimpleType);
    }

    default boolean isNullableAny(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return isAnyConstructor(typeConstructor(kotlinTypeMarker)) && isNullableType(kotlinTypeMarker);
    }

    default boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return isNothingConstructor(typeConstructor(kotlinTypeMarker)) && !isNullableType(kotlinTypeMarker);
    }

    default boolean isFlexibleNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        if (kotlinTypeMarker instanceof FlexibleTypeMarker) {
            FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) kotlinTypeMarker;
            if (isNothing(lowerBound(flexibleTypeMarker)) && isNullableNothing(upperBound(flexibleTypeMarker))) {
                return true;
            }
        }
        return false;
    }

    default boolean isNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return isNothingConstructor(typeConstructor(kotlinTypeMarker)) && isNullableType(kotlinTypeMarker);
    }

    default boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return isClassTypeConstructor(typeConstructor(simpleTypeMarker));
    }

    default boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return isIntegerLiteralTypeConstructor(typeConstructor(simpleTypeMarker));
    }

    default TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        if (typeArgumentListMarker instanceof SimpleTypeMarker) {
            return getArgument((KotlinTypeMarker) typeArgumentListMarker, i);
        }
        if (!(typeArgumentListMarker instanceof ArgumentList)) {
            throw new IllegalStateException(("unknown type argument list type: " + typeArgumentListMarker + ", " + Reflection.getOrCreateKotlinClass(typeArgumentListMarker.getClass())).toString());
        }
        TypeArgumentMarker typeArgumentMarker = ((ArgumentList) typeArgumentListMarker).get(i);
        Intrinsics.checkNotNullExpressionValue(typeArgumentMarker, "get(...)");
        return typeArgumentMarker;
    }

    default int size(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        if (typeArgumentListMarker instanceof SimpleTypeMarker) {
            return argumentsCount((KotlinTypeMarker) typeArgumentListMarker);
        }
        if (typeArgumentListMarker instanceof ArgumentList) {
            return ((ArgumentList) typeArgumentListMarker).size();
        }
        throw new IllegalStateException(("unknown type argument list type: " + typeArgumentListMarker + ", " + Reflection.getOrCreateKotlinClass(typeArgumentListMarker.getClass())).toString());
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.types.model.TypeSystemContext$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: TypeSystemContext.kt */
    @Metadata(d1 = {"\u0000\u001d\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\u0005\u001a\u00020\u0006H\u0096\u0002J\t\u0010\u0007\u001a\u00020\u0002H\u0096\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"org/jetbrains/kotlin/types/model/TypeSystemContext$iterator$1", "", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "argumentIndex", "", "hasNext", "", ES6Iterator.NEXT_METHOD, "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 implements Iterator<TypeArgumentMarker>, KMappedMarker {
        final /* synthetic */ TypeArgumentListMarker $this_iterator;
        private int argumentIndex;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        AnonymousClass1(TypeArgumentListMarker typeArgumentListMarker) {
            this.$this_iterator = typeArgumentListMarker;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.argumentIndex < TypeSystemContext.this.size(this.$this_iterator);
        }

        @Override // java.util.Iterator
        public TypeArgumentMarker next() {
            TypeArgumentMarker typeArgumentMarker = TypeSystemContext.this.get(this.$this_iterator, this.argumentIndex);
            this.argumentIndex++;
            return typeArgumentMarker;
        }
    }

    default Iterator<TypeArgumentMarker> iterator(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return new AnonymousClass1(typeArgumentListMarker);
    }

    default boolean isSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return asSimpleType(kotlinTypeMarker) != null;
    }
}
