package org.jetbrains.kotlin.resolve.calls.inference.components;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator;
import org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPositionAndErrorsKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorageKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.DeclaredUpperBoundConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.types.AbstractTypeApproximator;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;

/* JADX INFO: compiled from: ResultTypeResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001:\u00018B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\u0010\u001a\u00020\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u001e\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cJ\"\u0010\u001d\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\u0013H\u0002J\u0016\u0010 \u001a\u00020\n2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J\u001a\u0010!\u001a\u00020\u0015*\u00020\u00172\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012H\u0002J\u001a\u0010#\u001a\u00020\u0015*\u00020\u00172\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J\u0016\u0010%\u001a\u0004\u0018\u00010\u0015*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0016\u0010&\u001a\u0004\u0018\u00010\u0015*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J*\u0010'\u001a\u00020\u0015*\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010(\u001a\u00020)H\u0002J$\u0010*\u001a\u0004\u0018\u00010\u0015*\u00020\u00172\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010(\u001a\u00020)H\u0002J\u001a\u0010+\u001a\u00020\n*\u00020\u00172\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J\u0014\u0010,\u001a\u00020\n*\u00020\u00172\u0006\u0010-\u001a\u00020\u0015H\u0002J\u001c\u0010.\u001a\u00020\n*\u00020\u00172\u0006\u0010/\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J&\u00100\u001a\u0004\u0018\u00010\u0015*\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J \u00101\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012*\u00020\u00172\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J\u001c\u00102\u001a\u0004\u0018\u00010\u0015*\u00020\u00172\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J*\u0010/\u001a\u0004\u0018\u00010\u0015*\u00020\u00172\b\u00103\u001a\u0004\u0018\u00010\u00152\b\u00104\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J \u00105\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012*\u00020\u00172\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00150\u0012H\u0002J\u001e\u00106\u001a\u0004\u0018\u00010\u0015*\u00020\u00172\u0006\u00103\u001a\u00020\u00152\u0006\u00104\u001a\u00020\u0015H\u0002J\f\u00107\u001a\u00020\u0015*\u00020\u0015H\u0002R\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver;", "", "typeApproximator", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "trivialConstraintTypeInferenceOracle", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "isTypeInferenceForSelfTypesSupported", "", "()Z", "getTrivialConstraintTypeInferenceOracle", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "allUpperConstraintsAreFromBounds", "constraints", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "findResultIfThereIsEqualsConstraint", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "c", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver$Context;", "variableWithConstraints", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "findResultType", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDirectionCalculator$ResolveDirection;", "findResultTypeOrNull", "isFromTypeParameterUpperBound", "constraint", "isThereSingleLowerNullabilityConstraint", "computeCommonSuperType", SpecTypePair.NAME_types, "computeUpperType", "upperConstraints", "findSubType", "findSuperType", "getDefaultType", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "getDefaultTypeForSelfType", "isNullableNothingMayBeConsideredAsSuitableResultType", "isProperTypeForFixation", ModuleXmlParser.TYPE, "isSuitableType", "resultType", "makeFlexibleIfNecessary", "prepareLowerConstraints", "representativeFromEqualityConstraints", "firstCandidate", "secondCandidate", "sinkIntegerLiteralTypes", "specialResultForIntersectionType", "toPublicType", "Context", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResultTypeResolver {
    private final LanguageVersionSettings languageVersionSettings;
    private final TrivialConstraintTypeInferenceOracle trivialConstraintTypeInferenceOracle;
    private final AbstractTypeApproximator typeApproximator;

    /* JADX INFO: compiled from: ResultTypeResolver.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/ResultTypeResolver$Context;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "buildNotFixedVariablesToStubTypesSubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "isProperType", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "isReified", "variable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Context extends TypeSystemInferenceExtensionContext {
        TypeSubstitutorMarker buildNotFixedVariablesToStubTypesSubstitutor();

        boolean isProperType(KotlinTypeMarker type);

        boolean isReified(TypeVariableMarker variable);
    }

    public ResultTypeResolver(AbstractTypeApproximator typeApproximator, TrivialConstraintTypeInferenceOracle trivialConstraintTypeInferenceOracle, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(trivialConstraintTypeInferenceOracle, "trivialConstraintTypeInferenceOracle");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.typeApproximator = typeApproximator;
        this.trivialConstraintTypeInferenceOracle = trivialConstraintTypeInferenceOracle;
        this.languageVersionSettings = languageVersionSettings;
    }

    public final AbstractTypeApproximator getTypeApproximator() {
        return this.typeApproximator;
    }

    public final TrivialConstraintTypeInferenceOracle getTrivialConstraintTypeInferenceOracle() {
        return this.trivialConstraintTypeInferenceOracle;
    }

    private final boolean isTypeInferenceForSelfTypesSupported() {
        return this.languageVersionSettings.supportsFeature(LanguageFeature.TypeInferenceOnCallsWithSelfTypes);
    }

    private final KotlinTypeMarker getDefaultTypeForSelfType(Context context, List<Constraint> list, TypeVariableMarker typeVariableMarker) {
        TypeParameterMarker typeParameter;
        TypeConstructorMarker typeConstructorMarkerFreshTypeConstructor = context.freshTypeConstructor(typeVariableMarker);
        Intrinsics.checkNotNull(typeConstructorMarkerFreshTypeConstructor, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker");
        TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker = (TypeVariableTypeConstructorMarker) typeConstructorMarkerFreshTypeConstructor;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (true) {
            KotlinTypeMarker kotlinTypeMarkerExtractTypeForGivenRecursiveTypeParameter = null;
            if (!it.hasNext()) {
                break;
            }
            Constraint constraint = (Constraint) it.next();
            if ((constraint.getPosition().getFrom() instanceof DeclaredUpperBoundConstraintPosition) && (typeParameter = context.getTypeParameter(typeVariableTypeConstructorMarker)) != null) {
                kotlinTypeMarkerExtractTypeForGivenRecursiveTypeParameter = InferenceUtilsKt.extractTypeForGivenRecursiveTypeParameter(context, constraint.getType(), typeParameter);
            }
            if (kotlinTypeMarkerExtractTypeForGivenRecursiveTypeParameter != null) {
                arrayList.add(kotlinTypeMarkerExtractTypeForGivenRecursiveTypeParameter);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            arrayList2 = null;
        }
        if (arrayList2 == null) {
            return null;
        }
        return context.createCapturedStarProjectionForSelfType(typeVariableTypeConstructorMarker, arrayList2);
    }

    private final KotlinTypeMarker getDefaultType(Context context, TypeVariableDirectionCalculator.ResolveDirection resolveDirection, List<Constraint> list, TypeVariableMarker typeVariableMarker) {
        KotlinTypeMarker defaultTypeForSelfType;
        if (!isTypeInferenceForSelfTypesSupported() || (defaultTypeForSelfType = getDefaultTypeForSelfType(context, list, typeVariableMarker)) == null) {
            return resolveDirection == TypeVariableDirectionCalculator.ResolveDirection.TO_SUBTYPE ? context.nothingType() : context.nullableAnyType();
        }
        return defaultTypeForSelfType;
    }

    public final KotlinTypeMarker findResultType(Context c, VariableWithConstraints variableWithConstraints, TypeVariableDirectionCalculator.ResolveDirection direction) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(variableWithConstraints, "variableWithConstraints");
        Intrinsics.checkNotNullParameter(direction, "direction");
        KotlinTypeMarker kotlinTypeMarkerFindResultTypeOrNull = findResultTypeOrNull(c, variableWithConstraints, direction);
        return kotlinTypeMarkerFindResultTypeOrNull != null ? kotlinTypeMarkerFindResultTypeOrNull : getDefaultType(c, direction, variableWithConstraints.getConstraints(), variableWithConstraints.getTypeVariable());
    }

    private final KotlinTypeMarker findResultTypeOrNull(final Context c, VariableWithConstraints variableWithConstraints, TypeVariableDirectionCalculator.ResolveDirection direction) {
        KotlinTypeMarker kotlinTypeMarkerResultType;
        KotlinTypeMarker kotlinTypeMarkerFindResultIfThereIsEqualsConstraint = findResultIfThereIsEqualsConstraint(c, variableWithConstraints);
        if (kotlinTypeMarkerFindResultIfThereIsEqualsConstraint != null && (!c.isK2() || !c.contains(kotlinTypeMarkerFindResultIfThereIsEqualsConstraint, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver$findResultTypeOrNull$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker type) {
                Intrinsics.checkNotNullParameter(type, "type");
                ResultTypeResolver.Context context = c;
                return Boolean.valueOf(context.isIntegerLiteralConstantTypeConstructor(context.typeConstructor(type)));
            }
        }))) {
            return kotlinTypeMarkerFindResultIfThereIsEqualsConstraint;
        }
        KotlinTypeMarker kotlinTypeMarkerFindSubType = findSubType(c, variableWithConstraints);
        KotlinTypeMarker kotlinTypeMarkerMakeFlexibleIfNecessary = makeFlexibleIfNecessary(findSuperType(c, variableWithConstraints), c, variableWithConstraints.getConstraints());
        if (direction == TypeVariableDirectionCalculator.ResolveDirection.TO_SUBTYPE || direction == TypeVariableDirectionCalculator.ResolveDirection.UNKNOWN) {
            kotlinTypeMarkerResultType = resultType(c, kotlinTypeMarkerFindSubType, kotlinTypeMarkerMakeFlexibleIfNecessary, variableWithConstraints);
        } else {
            kotlinTypeMarkerResultType = resultType(c, kotlinTypeMarkerMakeFlexibleIfNecessary, kotlinTypeMarkerFindSubType, variableWithConstraints);
        }
        return (kotlinTypeMarkerFindResultIfThereIsEqualsConstraint != null && (kotlinTypeMarkerResultType == null || c.isNothingConstructor(c.typeConstructor(kotlinTypeMarkerResultType)) || !AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) c, kotlinTypeMarkerResultType, kotlinTypeMarkerFindResultIfThereIsEqualsConstraint, false, 8, (Object) null))) ? kotlinTypeMarkerFindResultIfThereIsEqualsConstraint : kotlinTypeMarkerResultType;
    }

    private final KotlinTypeMarker makeFlexibleIfNecessary(KotlinTypeMarker kotlinTypeMarker, Context context, List<Constraint> list) {
        if (!(kotlinTypeMarker instanceof SimpleTypeMarker)) {
            return kotlinTypeMarker;
        }
        List<Constraint> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return kotlinTypeMarker;
        }
        for (Constraint constraint : list2) {
            if (context.isTypeVariable(context.typeConstructor(constraint.getType())) && context.hasFlexibleNullability(constraint.getType())) {
                SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker) kotlinTypeMarker;
                return context.createFlexibleType(context.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker), context.withNullability(simpleTypeMarker, true));
            }
        }
        return kotlinTypeMarker;
    }

    private final KotlinTypeMarker resultType(Context context, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, VariableWithConstraints variableWithConstraints) {
        if (kotlinTypeMarker == null || kotlinTypeMarker2 == null) {
            return kotlinTypeMarker == null ? kotlinTypeMarker2 : kotlinTypeMarker;
        }
        KotlinTypeMarker kotlinTypeMarkerSpecialResultForIntersectionType = specialResultForIntersectionType(context, kotlinTypeMarker, kotlinTypeMarker2);
        if (kotlinTypeMarkerSpecialResultForIntersectionType != null) {
            return kotlinTypeMarkerSpecialResultForIntersectionType;
        }
        return (!isSuitableType(context, kotlinTypeMarker, variableWithConstraints) && isSuitableType(context, kotlinTypeMarker2, variableWithConstraints)) ? kotlinTypeMarker2 : kotlinTypeMarker;
    }

    private final KotlinTypeMarker specialResultForIntersectionType(Context context, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2) {
        if (!context.isIntersection(context.typeConstructor(kotlinTypeMarker)) || AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) context, toPublicType(kotlinTypeMarker), toPublicType(kotlinTypeMarker2), false, 8, (Object) null)) {
            return null;
        }
        return context.createTypeWithAlternativeForIntersectionResult(kotlinTypeMarker, kotlinTypeMarker2);
    }

    private final KotlinTypeMarker toPublicType(KotlinTypeMarker kotlinTypeMarker) {
        KotlinTypeMarker kotlinTypeMarkerApproximateToSuperType = this.typeApproximator.approximateToSuperType(kotlinTypeMarker, TypeApproximatorConfiguration.PublicDeclaration.SaveAnonymousTypes.INSTANCE);
        return kotlinTypeMarkerApproximateToSuperType == null ? kotlinTypeMarker : kotlinTypeMarkerApproximateToSuperType;
    }

    private final boolean isSuitableType(Context context, KotlinTypeMarker kotlinTypeMarker, VariableWithConstraints variableWithConstraints) {
        List<Constraint> constraints = variableWithConstraints.getConstraints();
        ArrayList arrayList = new ArrayList();
        for (Object obj : constraints) {
            if (isProperTypeForFixation(context, ((Constraint) obj).getType())) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        for (Constraint constraint : arrayList2) {
            if (!ConstraintStorageKt.checkConstraint(context, constraint.getType(), constraint.getKind(), kotlinTypeMarker)) {
                return false;
            }
        }
        if (this.trivialConstraintTypeInferenceOracle.isSuitableResultedType(kotlinTypeMarker)) {
            return true;
        }
        if (context.isReified(variableWithConstraints.getTypeVariable())) {
            return false;
        }
        if (context.isNullableType(kotlinTypeMarker)) {
            return isNullableNothingMayBeConsideredAsSuitableResultType(context, arrayList2);
        }
        return true;
    }

    private final boolean isNullableNothingMayBeConsideredAsSuitableResultType(Context context, List<Constraint> list) {
        if (context.isK2()) {
            return allUpperConstraintsAreFromBounds(list);
        }
        return !isThereSingleLowerNullabilityConstraint(list);
    }

    private final boolean allUpperConstraintsAreFromBounds(List<Constraint> constraints) {
        List<Constraint> list = constraints;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        for (Constraint constraint : list) {
            if (constraint.getKind().isUpper() && !isFromTypeParameterUpperBound(constraint)) {
                return false;
            }
        }
        return true;
    }

    private final boolean isFromTypeParameterUpperBound(Constraint constraint) {
        return constraint.getPosition().isFromDeclaredUpperBound() || (constraint.getPosition().getFrom() instanceof DeclaredUpperBoundConstraintPosition);
    }

    private final boolean isThereSingleLowerNullabilityConstraint(List<Constraint> constraints) {
        Iterator<T> it = constraints.iterator();
        Object obj = null;
        Object obj2 = null;
        boolean z = false;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                obj = obj2;
                break;
            }
            Object next = it.next();
            if (((Constraint) next).getKind().isLower()) {
                if (z) {
                    break;
                }
                z = true;
                obj2 = next;
            }
        }
        Constraint constraint = (Constraint) obj;
        if (constraint != null) {
            return constraint.getIsNullabilityConstraint();
        }
        return false;
    }

    private final KotlinTypeMarker findSubType(final Context context, VariableWithConstraints variableWithConstraints) {
        List<KotlinTypeMarker> listPrepareLowerConstraints = prepareLowerConstraints(context, variableWithConstraints.getConstraints());
        if (listPrepareLowerConstraints.isEmpty()) {
            return null;
        }
        List<KotlinTypeMarker> listSinkIntegerLiteralTypes = sinkIntegerLiteralTypes(context, listPrepareLowerConstraints);
        KotlinTypeMarker kotlinTypeMarkerComputeCommonSuperType = computeCommonSuperType(context, listSinkIntegerLiteralTypes);
        if (context.contains(kotlinTypeMarkerComputeCommonSuperType, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver.findSubType.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                SimpleTypeMarker simpleTypeMarkerAsSimpleType = context.asSimpleType(it);
                boolean z = false;
                if (simpleTypeMarkerAsSimpleType != null && context.isStubTypeForVariableInSubtyping(simpleTypeMarkerAsSimpleType)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        })) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : listSinkIntegerLiteralTypes) {
                if (!context.contains((KotlinTypeMarker) obj, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver$findSubType$typesWithoutStubs$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(KotlinTypeMarker it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        SimpleTypeMarker simpleTypeMarkerAsSimpleType = context.asSimpleType(it);
                        boolean z = false;
                        if (simpleTypeMarkerAsSimpleType != null && context.isStubTypeForVariableInSubtyping(simpleTypeMarkerAsSimpleType)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    }
                })) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (!arrayList2.isEmpty()) {
                kotlinTypeMarkerComputeCommonSuperType = computeCommonSuperType(context, arrayList2);
            }
        }
        KotlinTypeMarker kotlinTypeMarkerApproximateToSuperType = this.typeApproximator.approximateToSuperType(kotlinTypeMarkerComputeCommonSuperType, TypeApproximatorConfiguration.InternalTypesApproximation.INSTANCE);
        return kotlinTypeMarkerApproximateToSuperType == null ? kotlinTypeMarkerComputeCommonSuperType : kotlinTypeMarkerApproximateToSuperType;
    }

    private final KotlinTypeMarker computeCommonSuperType(Context context, List<? extends KotlinTypeMarker> list) {
        return NewCommonSuperTypeCalculator.INSTANCE.commonSuperType(context, list);
    }

    private final List<KotlinTypeMarker> prepareLowerConstraints(Context context, List<Constraint> list) {
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        boolean z2 = false;
        for (Constraint constraint : list) {
            if (constraint.getKind() == ConstraintKind.LOWER) {
                KotlinTypeMarker type = constraint.getType();
                arrayList.add(type);
                if (isProperTypeForFixation(context, type)) {
                    z = true;
                } else {
                    z2 = true;
                }
            }
        }
        if (!z) {
            return CollectionsKt.emptyList();
        }
        if (!z2) {
            return arrayList;
        }
        TypeSubstitutorMarker typeSubstitutorMarkerBuildNotFixedVariablesToStubTypesSubstitutor = context.buildNotFixedVariablesToStubTypesSubstitutor();
        ArrayList<KotlinTypeMarker> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (KotlinTypeMarker kotlinTypeMarkerSafeSubstitute : arrayList2) {
            if (!isProperTypeForFixation(context, kotlinTypeMarkerSafeSubstitute)) {
                kotlinTypeMarkerSafeSubstitute = context.safeSubstitute(typeSubstitutorMarkerBuildNotFixedVariablesToStubTypesSubstitutor, kotlinTypeMarkerSafeSubstitute);
            }
            arrayList3.add(kotlinTypeMarkerSafeSubstitute);
        }
        return arrayList3;
    }

    private final List<KotlinTypeMarker> sinkIntegerLiteralTypes(final Context context, List<? extends KotlinTypeMarker> list) {
        return CollectionsKt.sortedWith(list, new Comparator() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver$sinkIntegerLiteralTypes$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                ResultTypeResolver.Context context2 = context;
                final ResultTypeResolver.Context context3 = context;
                Integer numValueOf = Integer.valueOf(context2.contains((KotlinTypeMarker) t, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver$sinkIntegerLiteralTypes$1$containsILT$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(KotlinTypeMarker it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        SimpleTypeMarker simpleTypeMarkerAsSimpleType = context3.asSimpleType(it);
                        return Boolean.valueOf(simpleTypeMarkerAsSimpleType != null ? context3.isIntegerLiteralType(simpleTypeMarkerAsSimpleType) : false);
                    }
                }) ? 1 : 0);
                ResultTypeResolver.Context context4 = context;
                final ResultTypeResolver.Context context5 = context;
                return ComparisonsKt.compareValues(numValueOf, Integer.valueOf(context4.contains((KotlinTypeMarker) t2, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ResultTypeResolver$sinkIntegerLiteralTypes$1$containsILT$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(KotlinTypeMarker it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        SimpleTypeMarker simpleTypeMarkerAsSimpleType = context5.asSimpleType(it);
                        return Boolean.valueOf(simpleTypeMarkerAsSimpleType != null ? context5.isIntegerLiteralType(simpleTypeMarkerAsSimpleType) : false);
                    }
                }) ? 1 : 0));
            }
        });
    }

    private final KotlinTypeMarker computeUpperType(Context context, List<Constraint> list) {
        int i;
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.AllowEmptyIntersectionsInResultTypeResolver)) {
            List<Constraint> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(((Constraint) it.next()).getType());
            }
            return context.intersectTypes((List<? extends KotlinTypeMarker>) arrayList);
        }
        List<Constraint> list3 = list;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        Iterator<T> it2 = list3.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((Constraint) it2.next()).getType());
        }
        KotlinTypeMarker kotlinTypeMarkerIntersectTypes = context.intersectTypes((List<? extends KotlinTypeMarker>) arrayList2);
        if (context.isIntersection(context.typeConstructor(kotlinTypeMarkerIntersectTypes))) {
            Collection<KotlinTypeMarker> collectionSupertypes = context.supertypes(context.typeConstructor(kotlinTypeMarkerIntersectTypes));
            boolean z = collectionSupertypes instanceof Collection;
            boolean z2 = false;
            if (z && collectionSupertypes.isEmpty()) {
                i = 0;
            } else {
                i = 0;
                for (KotlinTypeMarker kotlinTypeMarker : collectionSupertypes) {
                    if (context.isClassTypeConstructor(context.typeConstructor(kotlinTypeMarker)) && !context.isInterface(context.typeConstructor(kotlinTypeMarker)) && (i = i + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            if (!z || !collectionSupertypes.isEmpty()) {
                Iterator<T> it3 = collectionSupertypes.iterator();
                while (it3.hasNext()) {
                    if (context.isCommonFinalClassConstructor(context.typeConstructor((KotlinTypeMarker) it3.next()))) {
                        z2 = true;
                        break;
                    }
                }
            }
            if (i > 1 || z2) {
                ArrayList arrayList3 = new ArrayList();
                for (Object obj : list3) {
                    if (!ConstraintPositionAndErrorsKt.isExpectedTypePosition((Constraint) obj)) {
                        arrayList3.add(obj);
                    }
                }
                ArrayList arrayList4 = arrayList3;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
                Iterator it4 = arrayList4.iterator();
                while (it4.hasNext()) {
                    arrayList5.add(((Constraint) it4.next()).getType());
                }
                ArrayList arrayList6 = arrayList5;
                if (!arrayList6.isEmpty()) {
                    return context.intersectTypes((List<? extends KotlinTypeMarker>) arrayList6);
                }
            }
        }
        return kotlinTypeMarkerIntersectTypes;
    }

    private final KotlinTypeMarker findSuperType(Context context, VariableWithConstraints variableWithConstraints) {
        List<Constraint> constraints = variableWithConstraints.getConstraints();
        ArrayList arrayList = new ArrayList();
        for (Object obj : constraints) {
            Constraint constraint = (Constraint) obj;
            if (constraint.getKind() == ConstraintKind.UPPER && isProperTypeForFixation(context, constraint.getType())) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return null;
        }
        KotlinTypeMarker kotlinTypeMarkerComputeUpperType = computeUpperType(context, arrayList2);
        KotlinTypeMarker kotlinTypeMarkerApproximateToSubType = this.typeApproximator.approximateToSubType(kotlinTypeMarkerComputeUpperType, TypeApproximatorConfiguration.InternalTypesApproximation.INSTANCE);
        return kotlinTypeMarkerApproximateToSubType == null ? kotlinTypeMarkerComputeUpperType : kotlinTypeMarkerApproximateToSubType;
    }

    private final boolean isProperTypeForFixation(Context context, KotlinTypeMarker kotlinTypeMarker) {
        Context context2 = context;
        if (context.isProperType(kotlinTypeMarker)) {
            Set<KotlinTypeMarker> setExtractProjectionsForAllCapturedTypes = VariableFixationFinderKt.extractProjectionsForAllCapturedTypes(context2, kotlinTypeMarker);
            if (!(setExtractProjectionsForAllCapturedTypes instanceof Collection) || !setExtractProjectionsForAllCapturedTypes.isEmpty()) {
                Iterator<T> it = setExtractProjectionsForAllCapturedTypes.iterator();
                while (it.hasNext()) {
                    if (!context.isProperType((KotlinTypeMarker) it.next())) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    private final KotlinTypeMarker findResultIfThereIsEqualsConstraint(Context c, VariableWithConstraints variableWithConstraints) {
        List<Constraint> constraints = variableWithConstraints.getConstraints();
        ArrayList arrayList = new ArrayList();
        for (Object obj : constraints) {
            Constraint constraint = (Constraint) obj;
            if (constraint.getKind() == ConstraintKind.EQUALITY && isProperTypeForFixation(c, constraint.getType())) {
                arrayList.add(obj);
            }
        }
        return representativeFromEqualityConstraints(c, arrayList);
    }

    private final KotlinTypeMarker representativeFromEqualityConstraints(Context context, List<Constraint> list) {
        if (list.isEmpty()) {
            return null;
        }
        List<Constraint> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((Constraint) it.next()).getType());
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            if (!context.isIntegerLiteralTypeConstructor(context.typeConstructor((KotlinTypeMarker) obj))) {
                arrayList3.add(obj);
            }
        }
        KotlinTypeMarker kotlinTypeMarkerSingleBestRepresentative = context.singleBestRepresentative(arrayList3);
        if (kotlinTypeMarkerSingleBestRepresentative != null) {
            return kotlinTypeMarkerSingleBestRepresentative;
        }
        KotlinTypeMarker kotlinTypeMarkerSingleBestRepresentative2 = context.singleBestRepresentative(arrayList2);
        return kotlinTypeMarkerSingleBestRepresentative2 == null ? (KotlinTypeMarker) CollectionsKt.first((List) arrayList2) : kotlinTypeMarkerSingleBestRepresentative2;
    }
}
