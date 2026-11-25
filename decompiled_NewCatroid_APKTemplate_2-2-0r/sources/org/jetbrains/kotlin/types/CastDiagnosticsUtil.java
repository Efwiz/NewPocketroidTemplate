package org.jetbrains.kotlin.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PlatformSpecificCastChecker;
import org.jetbrains.kotlin.builtins.PlatformToKotlinClassMapper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.Maps;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.checker.TypeCheckingProcedure;
import org.jetbrains.kotlin.types.expressions.DataFlowAnalyzer;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: CastDiagnosticsUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J&\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J \u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J(\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0018\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006H\u0002J\u0010\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u0006H\u0002J$\u0010\"\u001a\u00020\u00042\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00060$2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0004J \u0010&\u001a\u00020\u00042\u0006\u0010'\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010)\u001a\u00020\u00042\u0006\u0010!\u001a\u00020\u0006H\u0002J\u0018\u0010*\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006H\u0002J\u001e\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,2\u0006\u0010!\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0018\u0010%\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010.\u001a\u00020\u0006H\u0002¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/types/CastDiagnosticsUtil;", "", "()V", "allParametersReified", "", "subtype", "Lorg/jetbrains/kotlin/types/KotlinType;", "castIsUseless", "expression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpressionWithTypeRHS;", "context", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "targetType", "actualType", "checkExactTypeForUselessCast", "findStaticallyKnownSubtype", "Lorg/jetbrains/kotlin/types/TypeReconstructionResult;", "supertype", "subtypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "isCastErased", "typeChecker", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeChecker;", "isCastPossible", "lhsType", "rhsType", "platformToKotlinClassMapper", "Lorg/jetbrains/kotlin/builtins/PlatformToKotlinClassMapper;", "platformSpecificCastChecker", "Lorg/jetbrains/kotlin/builtins/PlatformSpecificCastChecker;", "isExactTypeCast", "candidateType", "isFinal", ModuleXmlParser.TYPE, "isRefinementUseless", "possibleTypes", "", "shouldCheckForExactType", "isRelated", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "isTrait", "isUpcast", "mapToPlatformIndependentClasses", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "expectedType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CastDiagnosticsUtil {
    public static final CastDiagnosticsUtil INSTANCE = new CastDiagnosticsUtil();

    private CastDiagnosticsUtil() {
    }

    @JvmStatic
    public static final boolean isCastPossible(KotlinType lhsType, KotlinType rhsType, PlatformToKotlinClassMapper platformToKotlinClassMapper, PlatformSpecificCastChecker platformSpecificCastChecker) {
        Intrinsics.checkNotNullParameter(lhsType, "lhsType");
        Intrinsics.checkNotNullParameter(rhsType, "rhsType");
        Intrinsics.checkNotNullParameter(platformToKotlinClassMapper, "platformToKotlinClassMapper");
        Intrinsics.checkNotNullParameter(platformSpecificCastChecker, "platformSpecificCastChecker");
        TypeConstructor constructor = lhsType.getConstructor();
        if (constructor instanceof IntersectionTypeConstructor) {
            Collection<KotlinType> collectionMo7438getSupertypes = ((IntersectionTypeConstructor) constructor).mo7438getSupertypes();
            if ((collectionMo7438getSupertypes instanceof Collection) && collectionMo7438getSupertypes.isEmpty()) {
                return false;
            }
            Iterator<T> it = collectionMo7438getSupertypes.iterator();
            while (it.hasNext()) {
                if (isCastPossible((KotlinType) it.next(), rhsType, platformToKotlinClassMapper, platformSpecificCastChecker)) {
                    return true;
                }
            }
            return false;
        }
        boolean zIsNullableType = TypeUtils.isNullableType(rhsType);
        boolean zIsNullableType2 = TypeUtils.isNullableType(lhsType);
        if (KotlinBuiltIns.isNothing(lhsType)) {
            return true;
        }
        if ((KotlinBuiltIns.isNullableNothing(lhsType) && !zIsNullableType) || KotlinBuiltIns.isNothing(rhsType)) {
            return false;
        }
        if (KotlinBuiltIns.isNullableNothing(rhsType)) {
            return zIsNullableType2;
        }
        if ((zIsNullableType2 && zIsNullableType) || KotlinTypeKt.isError(lhsType)) {
            return true;
        }
        CastDiagnosticsUtil castDiagnosticsUtil = INSTANCE;
        if (castDiagnosticsUtil.isRelated(lhsType, rhsType, platformToKotlinClassMapper) || TypeUtils.isTypeParameter(lhsType) || TypeUtils.isTypeParameter(rhsType) || platformSpecificCastChecker.isCastPossible(lhsType, rhsType)) {
            return true;
        }
        if (castDiagnosticsUtil.isFinal(lhsType) || castDiagnosticsUtil.isFinal(rhsType)) {
            return false;
        }
        return castDiagnosticsUtil.isTrait(lhsType) || castDiagnosticsUtil.isTrait(rhsType);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0041  */
    /* JADX WARN: Code duplicated, block: B:21:0x004b  */
    /* JADX WARN: Code duplicated, block: B:27:0x0059 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:28:? A[LOOP:0: B:19:0x0045->B:28:?, LOOP_END, SYNTHETIC] */
    private final boolean isRelated(KotlinType a, KotlinType b, PlatformToKotlinClassMapper platformToKotlinClassMapper) {
        List<ClassDescriptor> list;
        Iterator<T> it;
        List<ClassDescriptor> listMapToPlatformIndependentClasses = mapToPlatformIndependentClasses(a, platformToKotlinClassMapper);
        List<ClassDescriptor> listMapToPlatformIndependentClasses2 = mapToPlatformIndependentClasses(b, platformToKotlinClassMapper);
        List<ClassDescriptor> list2 = listMapToPlatformIndependentClasses;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            list = listMapToPlatformIndependentClasses2;
            if (list instanceof Collection) {
                it = list.iterator();
                while (it.hasNext()) {
                    if (DescriptorUtils.isSubtypeOfClass(a, (ClassDescriptor) it.next())) {
                    }
                }
            } else {
                it = list.iterator();
                while (it.hasNext()) {
                    if (DescriptorUtils.isSubtypeOfClass(a, (ClassDescriptor) it.next())) {
                    }
                }
            }
            return false;
        }
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            if (DescriptorUtils.isSubtypeOfClass(b, (ClassDescriptor) it2.next())) {
            }
        }
        list = listMapToPlatformIndependentClasses2;
        if ((list instanceof Collection) || !list.isEmpty()) {
            it = list.iterator();
            while (it.hasNext()) {
                if (DescriptorUtils.isSubtypeOfClass(a, (ClassDescriptor) it.next())) {
                }
            }
        }
        return false;
        return true;
    }

    private final List<ClassDescriptor> mapToPlatformIndependentClasses(KotlinType type, PlatformToKotlinClassMapper platformToKotlinClassMapper) {
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null;
        if (classDescriptor == null) {
            return CollectionsKt.emptyList();
        }
        Collection<ClassDescriptor> collectionMapPlatformClass = platformToKotlinClassMapper.mapPlatformClass(classDescriptor);
        Intrinsics.checkNotNullExpressionValue(collectionMapPlatformClass, "mapPlatformClass(...)");
        return CollectionsKt.plus((Collection<? extends ClassDescriptor>) collectionMapPlatformClass, classDescriptor);
    }

    private final boolean isFinal(KotlinType type) {
        return !TypeUtils.canHaveSubtypes(KotlinTypeChecker.DEFAULT, type);
    }

    private final boolean isTrait(KotlinType type) {
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = type.getConstructor().getThis$0();
        return (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) && ((ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor).getClassKind() == ClassKind.INTERFACE;
    }

    @JvmStatic
    public static final boolean isCastErased(KotlinType supertype, KotlinType subtype, KotlinTypeChecker typeChecker) {
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        Intrinsics.checkNotNullParameter(typeChecker, "typeChecker");
        boolean zIsNonReifiedTypeParameter = TypeUtils.isNonReifiedTypeParameter(subtype);
        boolean zIsSubtypeOf = typeChecker.isSubtypeOf(supertype, subtype);
        if (zIsNonReifiedTypeParameter && !zIsSubtypeOf && (TypeUtils.isNullableType(subtype) || !Intrinsics.areEqual(TypeUtilsKt.makeNotNullable(supertype), subtype))) {
            return true;
        }
        if (supertype.getIsMarkedNullable() || subtype.getIsMarkedNullable()) {
            KotlinType kotlinTypeMakeNotNullable = TypeUtils.makeNotNullable(supertype);
            Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNotNullable, "makeNotNullable(...)");
            KotlinType kotlinTypeMakeNotNullable2 = TypeUtils.makeNotNullable(subtype);
            Intrinsics.checkNotNullExpressionValue(kotlinTypeMakeNotNullable2, "makeNotNullable(...)");
            return isCastErased(kotlinTypeMakeNotNullable, kotlinTypeMakeNotNullable2, typeChecker);
        }
        if (zIsSubtypeOf) {
            return false;
        }
        if (zIsNonReifiedTypeParameter) {
            return true;
        }
        if (INSTANCE.allParametersReified(subtype)) {
            return false;
        }
        KotlinType resultingType = findStaticallyKnownSubtype(supertype, subtype.getConstructor()).getResultingType();
        if (resultingType == null) {
            return true;
        }
        return !typeChecker.isSubtypeOf(resultingType, subtype);
    }

    @JvmStatic
    public static final TypeReconstructionResult findStaticallyKnownSubtype(KotlinType supertype, TypeConstructor subtypeConstructor) {
        HashMap map;
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        Intrinsics.checkNotNullParameter(subtypeConstructor, "subtypeConstructor");
        supertype.getIsMarkedNullable();
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = subtypeConstructor.getThis$0();
        if (classifierDescriptorMo7434getDeclarationDescriptor == null) {
            throw new IllegalStateException(("Can't create default type for " + subtypeConstructor).toString());
        }
        SimpleType defaultType = classifierDescriptorMo7434getDeclarationDescriptor.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        SimpleType simpleType = defaultType;
        KotlinType kotlinTypeFindCorrespondingSupertype = TypeCheckingProcedure.findCorrespondingSupertype(simpleType, supertype);
        List<TypeParameterDescriptor> parameters = defaultType.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<TypeParameterDescriptor> list = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeParameterDescriptor) it.next()).getTypeConstructor());
        }
        final Set set = CollectionsKt.toSet(arrayList);
        if (kotlinTypeFindCorrespondingSupertype != null) {
            TypeUnifier.UnificationResult unificationResultUnify = TypeUnifier.unify(new TypeProjectionImpl(supertype), new TypeProjectionImpl(kotlinTypeFindCorrespondingSupertype), new Predicate() { // from class: org.jetbrains.kotlin.types.CastDiagnosticsUtil$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return set.contains((TypeConstructor) obj);
                }
            });
            Intrinsics.checkNotNullExpressionValue(unificationResultUnify, "unify(...)");
            HashMap mapNewHashMap = Maps.newHashMap(unificationResultUnify.getSubstitution());
            Intrinsics.checkNotNull(mapNewHashMap);
            map = mapNewHashMap;
        } else {
            HashMap mapNewHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(parameters.size());
            Intrinsics.checkNotNull(mapNewHashMapWithExpectedSize);
            map = mapNewHashMapWithExpectedSize;
        }
        boolean z = true;
        for (TypeParameterDescriptor typeParameterDescriptor : parameters) {
            if (((TypeProjection) map.get(typeParameterDescriptor.getTypeConstructor())) == null) {
                TypeConstructor typeConstructor = typeParameterDescriptor.getTypeConstructor();
                Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
                TypeProjection typeProjectionMakeStarProjection = TypeUtils.makeStarProjection(typeParameterDescriptor);
                Intrinsics.checkNotNullExpressionValue(typeProjectionMakeStarProjection, "makeStarProjection(...)");
                map.put(typeConstructor, typeProjectionMakeStarProjection);
                z = false;
            }
        }
        return new TypeReconstructionResult(TypeSubstitutor.create(map).substitute(simpleType, Variance.INVARIANT), z);
    }

    private final boolean allParametersReified(KotlinType subtype) {
        List<TypeParameterDescriptor> parameters = subtype.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<TypeParameterDescriptor> list = parameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!((TypeParameterDescriptor) it.next()).isReified()) {
                return false;
            }
        }
        return true;
    }

    public final boolean castIsUseless(KtBinaryExpressionWithTypeRHS expression, ExpressionTypingContext context, KotlinType targetType, KotlinType actualType) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(targetType, "targetType");
        Intrinsics.checkNotNullParameter(actualType, "actualType");
        if (KtPsiUtil.isSafeCast(expression)) {
            targetType = TypeUtils.makeNullable(targetType);
        }
        Intrinsics.checkNotNull(targetType);
        Collection<KotlinType> allPossibleTypes = DataFlowAnalyzer.getAllPossibleTypes(expression.getLeft(), actualType, context);
        Intrinsics.checkNotNullExpressionValue(allPossibleTypes, "getAllPossibleTypes(...)");
        KotlinType expectedType = context.expectedType;
        Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
        return isRefinementUseless(allPossibleTypes, targetType, shouldCheckForExactType(expression, expectedType));
    }

    public final boolean isRefinementUseless(Collection<? extends KotlinType> possibleTypes, KotlinType targetType, boolean shouldCheckForExactType) {
        Intrinsics.checkNotNullParameter(possibleTypes, "possibleTypes");
        Intrinsics.checkNotNullParameter(targetType, "targetType");
        Collection<? extends KotlinType> collection = possibleTypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(FlexibleTypesKt.upperIfFlexible((KotlinType) it.next()));
        }
        KotlinType kotlinTypeIntersectTypes = TypeIntersector.intersectTypes(arrayList);
        if (kotlinTypeIntersectTypes == null) {
            return false;
        }
        if (shouldCheckForExactType) {
            return isExactTypeCast(kotlinTypeIntersectTypes, targetType);
        }
        return isUpcast(kotlinTypeIntersectTypes, targetType);
    }

    private final boolean shouldCheckForExactType(KtBinaryExpressionWithTypeRHS expression, KotlinType expectedType) {
        if (TypeUtils.noExpectedType(expectedType)) {
            return checkExactTypeForUselessCast(expression);
        }
        return TypeUtils.isDontCarePlaceholder(expectedType);
    }

    private final boolean isExactTypeCast(KotlinType candidateType, KotlinType targetType) {
        return Intrinsics.areEqual(candidateType, targetType) && FunctionTypesKt.isExtensionFunctionType(candidateType) == FunctionTypesKt.isExtensionFunctionType(targetType);
    }

    private final boolean isUpcast(KotlinType candidateType, KotlinType targetType) {
        if (KotlinTypeChecker.DEFAULT.isSubtypeOf(candidateType, targetType)) {
            return (FunctionTypesKt.isFunctionType(candidateType) && FunctionTypesKt.isFunctionType(targetType) && FunctionTypesKt.isExtensionFunctionType(candidateType) != FunctionTypesKt.isExtensionFunctionType(targetType)) ? false : true;
        }
        return false;
    }

    private final boolean checkExactTypeForUselessCast(KtBinaryExpressionWithTypeRHS expression) {
        PsiElement parent = expression.getLightParent();
        while (true) {
            if (!(parent instanceof KtParenthesizedExpression) && !(parent instanceof KtLabeledExpression) && !(parent instanceof KtAnnotatedExpression)) {
                break;
            }
            parent = parent.getLightParent();
        }
        if (parent instanceof KtValueArgument) {
            return true;
        }
        if (parent instanceof KtQualifiedExpression) {
            return PsiTreeUtil.isAncestor(((KtQualifiedExpression) parent).getReceiverExpression(), expression, false);
        }
        if (parent instanceof KtBinaryExpression ? true : parent instanceof KtUnaryExpression) {
            return true;
        }
        return parent instanceof KtProperty ? true : parent instanceof KtPropertyAccessor ? true : parent instanceof KtNamedFunction ? true : parent instanceof KtFunctionLiteral;
    }
}
