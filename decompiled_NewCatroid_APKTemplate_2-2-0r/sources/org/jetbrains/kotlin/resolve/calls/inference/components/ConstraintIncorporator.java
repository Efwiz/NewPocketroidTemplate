package org.jetbrains.kotlin.resolve.calls.inference.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.DeclaredUpperBoundConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.FixVariableConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.OnlyInputTypeConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.types.AbstractTypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: ConstraintIncorporator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u00011B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u001e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bJ<\u0010\u001c\u001a\u00020\u0015*\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\u0013H\u0002J>\u0010#\u001a\u00020\u0015*\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u001b2\b\b\u0002\u0010$\u001a\u00020\u0013H\u0002J\u001c\u0010%\u001a\u00020\u0013*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u001c\u0010&\u001a\u00020\u0013*\u00020\u00172\u0006\u0010!\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u001bH\u0002J\u001c\u0010'\u001a\u00020\u0015*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J,\u0010(\u001a\u00020\u0015*\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u001bH\u0002J\u001a\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00190**\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u0010H\u0002J\u001c\u0010+\u001a\u00020\u0015*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J$\u0010,\u001a\u00020\u0013*\u00020\u00172\u0006\u0010!\u001a\u00020\u00102\u0006\u0010 \u001a\u00020\u00102\u0006\u0010-\u001a\u00020.H\u0002J$\u0010/\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u0010H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintIncorporator;", "", "typeApproximator", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "trivialConstraintTypeInferenceOracle", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;", "utilContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemUtilContext;", "(Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemUtilContext;)V", "getTrivialConstraintTypeInferenceOracle", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "getUtilContext", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemUtilContext;", "approximateCapturedTypes", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", ModuleXmlParser.TYPE, "toSuper", "", "incorporate", "", "c", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintIncorporator$Context;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "constraint", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "addNewConstraint", "targetVariable", "baseConstraint", "otherVariable", "otherConstraint", "newConstraint", "isSubtype", "approximateIfNeededAndAddNewConstraint", "needApproximation", "areThereRecursiveConstraints", "containsConstrainingTypeWithoutProjection", "directWithVariable", "generateNewConstraint", "getNestedTypeVariables", "", "insideOtherConstraint", "isPotentialUsefulNullabilityConstraint", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintKind;", "substitute", "value", "Context", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstraintIncorporator {
    private final TrivialConstraintTypeInferenceOracle trivialConstraintTypeInferenceOracle;
    private final AbstractTypeApproximator typeApproximator;
    private final ConstraintSystemUtilContext utilContext;

    /* JADX INFO: compiled from: ConstraintIncorporator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstraintKind.values().length];
            try {
                iArr[ConstraintKind.EQUALITY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstraintKind.UPPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConstraintKind.LOWER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ConstraintIncorporator(AbstractTypeApproximator typeApproximator, TrivialConstraintTypeInferenceOracle trivialConstraintTypeInferenceOracle, ConstraintSystemUtilContext utilContext) {
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(trivialConstraintTypeInferenceOracle, "trivialConstraintTypeInferenceOracle");
        Intrinsics.checkNotNullParameter(utilContext, "utilContext");
        this.typeApproximator = typeApproximator;
        this.trivialConstraintTypeInferenceOracle = trivialConstraintTypeInferenceOracle;
        this.utilContext = utilContext;
    }

    public final AbstractTypeApproximator getTypeApproximator() {
        return this.typeApproximator;
    }

    public final TrivialConstraintTypeInferenceOracle getTrivialConstraintTypeInferenceOracle() {
        return this.trivialConstraintTypeInferenceOracle;
    }

    public final ConstraintSystemUtilContext getUtilContext() {
        return this.utilContext;
    }

    /* JADX INFO: compiled from: ConstraintIncorporator.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J4\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\rH&J \u0010\u0007\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u0014H&J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00032\u0006\u0010\u0010\u001a\u00020\u0011H&J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0018\u001a\u00020\u0019H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001aÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintIncorporator$Context;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "allTypeVariablesWithConstraints", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "getAllTypeVariablesWithConstraints", "()Ljava/util/Collection;", "addNewIncorporatedConstraint", "", "lowerType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "upperType", "shouldTryUseDifferentFlexibilityForUpperType", "", "isFromNullabilityConstraint", "isFromDeclaredUpperBound", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", ModuleXmlParser.TYPE, "constraintContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintContext;", "getConstraintsForVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "getTypeVariable", "typeConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Context extends TypeSystemInferenceExtensionContext {
        void addNewIncorporatedConstraint(KotlinTypeMarker lowerType, KotlinTypeMarker upperType, boolean shouldTryUseDifferentFlexibilityForUpperType, boolean isFromNullabilityConstraint, boolean isFromDeclaredUpperBound);

        void addNewIncorporatedConstraint(TypeVariableMarker typeVariable, KotlinTypeMarker type, ConstraintContext constraintContext);

        Collection<VariableWithConstraints> getAllTypeVariablesWithConstraints();

        Collection<Constraint> getConstraintsForVariable(TypeVariableMarker typeVariable);

        TypeVariableMarker getTypeVariable(TypeConstructorMarker typeConstructor);

        static /* synthetic */ void addNewIncorporatedConstraint$default(Context context, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z, boolean z2, boolean z3, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addNewIncorporatedConstraint");
            }
            context.addNewIncorporatedConstraint(kotlinTypeMarker, kotlinTypeMarker2, z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3);
        }
    }

    public final void incorporate(Context c, TypeVariableMarker typeVariable, Constraint constraint) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(constraint, "constraint");
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        if (areThereRecursiveConstraints(c, typeVariable, constraint)) {
            return;
        }
        directWithVariable(c, typeVariable, constraint);
        insideOtherConstraint(c, typeVariable, constraint);
    }

    private final boolean areThereRecursiveConstraints(final Context context, final TypeVariableMarker typeVariableMarker, Constraint constraint) {
        return context.contains(constraint.getType(), new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintIncorporator.areThereRecursiveConstraints.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Context context2 = context;
                return Boolean.valueOf(Intrinsics.areEqual(context2.unwrapStubTypeVariableConstructor(context2.typeConstructor(it)), context.freshTypeConstructor(typeVariableMarker)));
            }
        });
    }

    private final void directWithVariable(Context context, TypeVariableMarker typeVariableMarker, Constraint constraint) {
        boolean zShouldBeFlexible = context.useRefinedBoundsForTypeVariableInFlexiblePosition() ? false : this.utilContext.shouldBeFlexible(typeVariableMarker);
        if (constraint.getKind() != ConstraintKind.LOWER) {
            for (Constraint constraint2 : context.getConstraintsForVariable(typeVariableMarker)) {
                if (constraint2.getKind() != ConstraintKind.UPPER) {
                    Context.addNewIncorporatedConstraint$default(context, constraint2.getType(), constraint.getType(), zShouldBeFlexible, constraint2.getIsNullabilityConstraint(), false, 16, null);
                }
            }
        }
        if (constraint.getKind() != ConstraintKind.UPPER) {
            for (Constraint constraint3 : context.getConstraintsForVariable(typeVariableMarker)) {
                if (constraint3.getKind() != ConstraintKind.LOWER) {
                    Context.addNewIncorporatedConstraint$default(context, constraint.getType(), constraint3.getType(), zShouldBeFlexible, false, (constraint3.getPosition().getFrom() instanceof DeclaredUpperBoundConstraintPosition) && !context.isTypeVariable(context.typeConstructor(constraint3.getType())), 8, null);
                }
            }
        }
    }

    private final void insideOtherConstraint(Context context, TypeVariableMarker typeVariableMarker, Constraint constraint) {
        TypeConstructorMarker typeConstructorMarkerFreshTypeConstructor = context.freshTypeConstructor(typeVariableMarker);
        for (VariableWithConstraints variableWithConstraints : context.getAllTypeVariablesWithConstraints()) {
            List<Constraint> constraints = variableWithConstraints.getConstraints();
            ArrayList arrayList = new ArrayList();
            for (Object obj : constraints) {
                if (VariableFixationFinderKt.containsTypeVariable(context, ((Constraint) obj).getType(), typeConstructorMarkerFreshTypeConstructor)) {
                    arrayList.add(obj);
                }
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                generateNewConstraint(context, variableWithConstraints.getTypeVariable(), (Constraint) it.next(), typeVariableMarker, constraint);
            }
        }
    }

    private final void approximateIfNeededAndAddNewConstraint(Context context, Constraint constraint, KotlinTypeMarker kotlinTypeMarker, TypeVariableMarker typeVariableMarker, TypeVariableMarker typeVariableMarker2, Constraint constraint2, final boolean z) {
        final KotlinTypeMarker kotlinTypeMarkerSubstitute = substitute(constraint.getType(), context, typeVariableMarker2, kotlinTypeMarker);
        Function1<Boolean, KotlinTypeMarker> function1 = new Function1<Boolean, KotlinTypeMarker>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintIncorporator$approximateIfNeededAndAddNewConstraint$prepareType$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ KotlinTypeMarker invoke(Boolean bool) {
                return invoke(bool.booleanValue());
            }

            public final KotlinTypeMarker invoke(boolean z2) {
                return z ? this.approximateCapturedTypes(kotlinTypeMarkerSubstitute, z2) : kotlinTypeMarkerSubstitute;
            }
        };
        if (constraint.getKind() != ConstraintKind.LOWER) {
            addNewConstraint(context, typeVariableMarker, constraint, typeVariableMarker2, constraint2, function1.invoke(true), false);
        }
        if (constraint.getKind() != ConstraintKind.UPPER) {
            addNewConstraint(context, typeVariableMarker, constraint, typeVariableMarker2, constraint2, function1.invoke(false), true);
        }
    }

    private final void generateNewConstraint(Context context, TypeVariableMarker typeVariableMarker, Constraint constraint, TypeVariableMarker typeVariableMarker2, Constraint constraint2) {
        Pair pair;
        boolean z = context.argumentsCount(constraint.getType()) != 0;
        boolean z2 = context.isCapturedType(constraint.getType()) || context.isCapturedType(constraint2.getType());
        int i = WhenMappings.$EnumSwitchMapping$0[constraint2.getKind().ordinal()];
        if (i == 1) {
            pair = TuplesKt.to(constraint2.getType(), false);
        } else if (i != 2) {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            if (constraint.getKind() == ConstraintKind.UPPER && !z && !z2) {
                pair = TuplesKt.to(context.nullableAnyType(), false);
            } else {
                pair = (constraint.getKind() != ConstraintKind.LOWER || z || z2) ? TuplesKt.to(context.createCapturedType(context.createTypeArgument(constraint2.getType(), TypeVariance.IN), CollectionsKt.emptyList(), constraint2.getType(), CaptureStatus.FOR_INCORPORATION), true) : TuplesKt.to(constraint2.getType(), false);
            }
        } else if (constraint.getKind() == ConstraintKind.LOWER && !z && !z2) {
            pair = TuplesKt.to(context.nothingType(), false);
        } else {
            pair = (constraint.getKind() != ConstraintKind.UPPER || z || z2) ? TuplesKt.to(context.createCapturedType(context.createTypeArgument(constraint2.getType(), TypeVariance.OUT), CollectionsKt.listOf(constraint2.getType()), null, CaptureStatus.FOR_INCORPORATION), true) : TuplesKt.to(constraint2.getType(), false);
        }
        approximateIfNeededAndAddNewConstraint(context, constraint, (KotlinTypeMarker) pair.component1(), typeVariableMarker, typeVariableMarker2, constraint2, ((Boolean) pair.component2()).booleanValue());
    }

    private final void addNewConstraint(Context context, TypeVariableMarker typeVariableMarker, Constraint constraint, TypeVariableMarker typeVariableMarker2, Constraint constraint2, KotlinTypeMarker kotlinTypeMarker, boolean z) {
        if (getNestedTypeVariables(context, kotlinTypeMarker).contains(typeVariableMarker)) {
            return;
        }
        boolean zIsPotentialUsefulNullabilityConstraint = isPotentialUsefulNullabilityConstraint(context, kotlinTypeMarker, constraint2.getType(), constraint2.getKind());
        boolean z2 = (constraint.getPosition().getFrom() instanceof FixVariableConstraintPosition) || (constraint2.getPosition().getFrom() instanceof FixVariableConstraintPosition);
        if ((constraint2.getKind().isEqual() || zIsPotentialUsefulNullabilityConstraint || z2 || containsConstrainingTypeWithoutProjection(context, kotlinTypeMarker, constraint2)) && !this.trivialConstraintTypeInferenceOracle.isGeneratedConstraintTrivial(constraint, constraint2, kotlinTypeMarker, z)) {
            SmartSet smartSetCreate = SmartSet.INSTANCE.create(constraint.getDerivedFrom());
            smartSetCreate.addAll(constraint2.getDerivedFrom());
            if (smartSetCreate.contains(typeVariableMarker2)) {
                return;
            }
            smartSetCreate.add(typeVariableMarker2);
            ConstraintKind constraintKind = z ? ConstraintKind.LOWER : ConstraintKind.UPPER;
            Object from = constraint.getPosition().getFrom();
            OnlyInputTypeConstraintPosition inputTypePositionBeforeIncorporation = from instanceof OnlyInputTypeConstraintPosition ? (OnlyInputTypeConstraintPosition) from : null;
            if (inputTypePositionBeforeIncorporation == null) {
                inputTypePositionBeforeIncorporation = constraint.getInputTypePositionBeforeIncorporation();
            }
            context.addNewIncorporatedConstraint(typeVariableMarker, kotlinTypeMarker, new ConstraintContext(constraintKind, smartSetCreate, inputTypePositionBeforeIncorporation, (zIsPotentialUsefulNullabilityConstraint && context.isNullableNothing(kotlinTypeMarker)) || (constraint2.getIsNullabilityConstraint() && context.isNullableNothing(constraint2.getType()))));
        }
    }

    private final boolean containsConstrainingTypeWithoutProjection(Context context, KotlinTypeMarker kotlinTypeMarker, Constraint constraint) {
        List<TypeArgumentMarker> nestedArguments = ConstraintIncorporatorKt.getNestedArguments(context, kotlinTypeMarker);
        if ((nestedArguments instanceof Collection) && nestedArguments.isEmpty()) {
            return false;
        }
        for (TypeArgumentMarker typeArgumentMarker : nestedArguments) {
            if (Intrinsics.areEqual(context.typeConstructor(context.getType(typeArgumentMarker)), context.typeConstructor(constraint.getType())) && context.getVariance(typeArgumentMarker) == TypeVariance.INV) {
                return true;
            }
        }
        return false;
    }

    private final boolean isPotentialUsefulNullabilityConstraint(Context context, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, ConstraintKind constraintKind) {
        if (this.trivialConstraintTypeInferenceOracle.isSuitableResultedType(kotlinTypeMarker)) {
            return false;
        }
        return (!context.isNullableType(kotlinTypeMarker) && context.isNullableType(kotlinTypeMarker2) && constraintKind == ConstraintKind.LOWER) || (context.isNullableType(kotlinTypeMarker) && !context.isNullableType(kotlinTypeMarker2) && constraintKind == ConstraintKind.UPPER);
    }

    private final List<TypeVariableMarker> getNestedTypeVariables(Context context, KotlinTypeMarker kotlinTypeMarker) {
        List nestedArguments = ConstraintIncorporatorKt.getNestedArguments(context, kotlinTypeMarker);
        SmartList smartList = new SmartList();
        Iterator it = nestedArguments.iterator();
        while (it.hasNext()) {
            TypeVariableMarker typeVariable = context.getTypeVariable(context.unwrapStubTypeVariableConstructor(context.typeConstructor(context.getType((TypeArgumentMarker) it.next()))));
            if (typeVariable != null) {
                smartList.add(typeVariable);
            }
        }
        return smartList;
    }

    private final KotlinTypeMarker substitute(KotlinTypeMarker kotlinTypeMarker, Context context, TypeVariableMarker typeVariableMarker, KotlinTypeMarker kotlinTypeMarker2) {
        Context context2 = context;
        return MarkerExtensionsKt.safeSubstitute(context.typeSubstitutorByTypeConstructor(MapsKt.mapOf(TuplesKt.to(MarkerExtensionsKt.freshTypeConstructor(typeVariableMarker, context2), kotlinTypeMarker2))), context2, kotlinTypeMarker);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final KotlinTypeMarker approximateCapturedTypes(KotlinTypeMarker type, boolean toSuper) {
        KotlinTypeMarker kotlinTypeMarkerApproximateToSubType;
        if (toSuper) {
            kotlinTypeMarkerApproximateToSubType = this.typeApproximator.approximateToSuperType(type, TypeApproximatorConfiguration.IncorporationConfiguration.INSTANCE);
            if (kotlinTypeMarkerApproximateToSubType == null) {
                return type;
            }
        } else {
            kotlinTypeMarkerApproximateToSubType = this.typeApproximator.approximateToSubType(type, TypeApproximatorConfiguration.IncorporationConfiguration.INSTANCE);
            if (kotlinTypeMarkerApproximateToSubType == null) {
                return type;
            }
        }
        return kotlinTypeMarkerApproximateToSubType;
    }
}
