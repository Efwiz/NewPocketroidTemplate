package org.jetbrains.kotlin.fir.resolve.inference;

import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.components.TrivialConstraintTypeInferenceOracle;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: CompletionModeCalculator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\b\u0002\u0018\u00002\u00020\u0001:\u0002<=B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\u0010\u0006\u001a\u00060\u0007j\u0002`\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u001d\u001a\u00020\u001eJ\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u000eH\u0002J\u0010\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020$H\u0002J.\u0010%\u001a\u00020 *\u00060\u0007j\u0002`\b2\u0006\u0010&\u001a\u00020\u00192\u0006\u0010'\u001a\u00020(2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020$0\u001bH\u0002J(\u0010*\u001a\u00020(*\u00060\u0007j\u0002`\b2\u0006\u0010'\u001a\u00020(2\u0006\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020 *\u00060\u0007j\u0002`\bH\u0002J\u0010\u00100\u001a\u000201*\u00060\u0007j\u0002`\bH\u0002J \u00102\u001a\u000201*\u00060\u0007j\u0002`\b2\u0006\u0010!\u001a\u00020\u000e2\u0006\u00103\u001a\u00020\u000fH\u0002J\u0014\u00104\u001a\u000201*\u0002052\u0006\u00103\u001a\u00020\u000fH\u0002J \u00106\u001a\u000201*\u00060\u0007j\u0002`\b2\u0006\u00107\u001a\u0002052\u0006\u00108\u001a\u00020\u001cH\u0002J.\u00109\u001a\u00020 *\u00060\u0007j\u0002`\b2\u0006\u0010&\u001a\u00020\u00192\u0006\u0010*\u001a\u00020(2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020$0\u001bH\u0002J\f\u0010;\u001a\u00020(*\u00020(H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00060\u0007j\u0002`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/CalculatorForNestedCall;", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "returnType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "context", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;", "Lorg/jetbrains/kotlin/fir/resolve/inference/CsCompleterContext;", "oracle", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;)V", "fixationDirectionsForVariables", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "Lorg/jetbrains/kotlin/fir/resolve/inference/CalculatorForNestedCall$FixationDirection;", "postponedAtoms", "", "Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedResolvedAtom;", "getPostponedAtoms", "()Ljava/util/List;", "postponedAtoms$delegate", "Lkotlin/Lazy;", "typesToProcess", "Ljava/util/Queue;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "variablesWithQueuedConstraints", "", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "computeCompletionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "enqueueTypesFromConstraints", "", "variableWithConstraints", "updateDirection", "directionForVariable", "Lorg/jetbrains/kotlin/fir/resolve/inference/CalculatorForNestedCall$FixationDirectionForVariable;", "collectRequiredDirectionsForVariables", ModuleXmlParser.TYPE, "outerVariance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "fixationDirectionsCollector", "compositeVariance", "argument", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "parameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "computeDirections", "directionRequirementsForVariablesHold", "", "hasProperConstraint", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "hasRequiredKind", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "isLowerConstraintForPartiallyAnalyzedVariable", "constraint", "variable", "processTypeWithoutParameters", "newRequirementsCollector", "reversed", "FixationDirection", "FixationDirectionForVariable", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CalculatorForNestedCall {
    private final Candidate candidate;
    private final ConstraintSystemCompletionContext context;
    private final Map<VariableWithConstraints, FixationDirection> fixationDirectionsForVariables;
    private final TrivialConstraintTypeInferenceOracle oracle;

    /* JADX INFO: renamed from: postponedAtoms$delegate, reason: from kotlin metadata */
    private final Lazy postponedAtoms;
    private final ConeKotlinType returnType;
    private final Queue<KotlinTypeMarker> typesToProcess;
    private final Set<TypeVariableMarker> variablesWithQueuedConstraints;

    /* JADX INFO: compiled from: CompletionModeCalculator.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/CalculatorForNestedCall$FixationDirection;", "", "(Ljava/lang/String;I)V", "TO_SUBTYPE", "EQUALITY", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum FixationDirection {
        TO_SUBTYPE,
        EQUALITY;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<FixationDirection> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: CompletionModeCalculator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TypeVariance.values().length];
            try {
                iArr[TypeVariance.INV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeVariance.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeVariance.IN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[FixationDirection.values().length];
            try {
                iArr2[FixationDirection.TO_SUBTYPE.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[FixationDirection.EQUALITY.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public CalculatorForNestedCall(Candidate candidate, ConeKotlinType coneKotlinType, ConstraintSystemCompletionContext context, TrivialConstraintTypeInferenceOracle oracle) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(oracle, "oracle");
        this.candidate = candidate;
        this.returnType = coneKotlinType;
        this.context = context;
        this.oracle = oracle;
        this.fixationDirectionsForVariables = CollectionsKt.newLinkedHashMapWithExpectedSize(context.getNotFixedTypeVariables().size());
        this.variablesWithQueuedConstraints = new LinkedHashSet();
        this.typesToProcess = new ArrayDeque();
        this.postponedAtoms = LazyKt.lazy(new Function0<List<? extends PostponedResolvedAtom>>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.CalculatorForNestedCall$postponedAtoms$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends PostponedResolvedAtom> invoke() {
                return ConstraintSystemCompleter.INSTANCE.getOrderedNotAnalyzedPostponedArguments$resolve(this.this$0.candidate);
            }
        });
    }

    private final List<PostponedResolvedAtom> getPostponedAtoms() {
        return (List) this.postponedAtoms.getValue();
    }

    public final ConstraintSystemCompletionMode computeCompletionMode() {
        ConstraintSystemCompletionContext constraintSystemCompletionContext = this.context;
        this.typesToProcess.add(this.returnType);
        computeDirections(constraintSystemCompletionContext);
        if (directionRequirementsForVariablesHold(constraintSystemCompletionContext)) {
            return ConstraintSystemCompletionMode.FULL;
        }
        return ConstraintSystemCompletionMode.PARTIAL;
    }

    private final void computeDirections(final ConstraintSystemCompletionContext constraintSystemCompletionContext) {
        KotlinTypeMarker kotlinTypeMarkerPoll;
        while (!this.typesToProcess.isEmpty() && (kotlinTypeMarkerPoll = this.typesToProcess.poll()) != null) {
            if (constraintSystemCompletionContext.contains(kotlinTypeMarkerPoll, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.CalculatorForNestedCall.computeDirections.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KotlinTypeMarker it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(constraintSystemCompletionContext.getNotFixedTypeVariables().containsKey(constraintSystemCompletionContext.typeConstructor(it)));
                }
            })) {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                collectRequiredDirectionsForVariables(constraintSystemCompletionContext, kotlinTypeMarkerPoll, TypeVariance.OUT, linkedHashSet);
                for (FixationDirectionForVariable fixationDirectionForVariable : linkedHashSet) {
                    updateDirection(fixationDirectionForVariable);
                    enqueueTypesFromConstraints(fixationDirectionForVariable.getVariable());
                }
            }
        }
    }

    private final void enqueueTypesFromConstraints(VariableWithConstraints variableWithConstraints) {
        TypeVariableMarker typeVariable = variableWithConstraints.getTypeVariable();
        if (this.variablesWithQueuedConstraints.contains(typeVariable)) {
            return;
        }
        Iterator<Constraint> it = variableWithConstraints.getConstraints().iterator();
        while (it.hasNext()) {
            this.typesToProcess.add(it.next().getType());
        }
        this.variablesWithQueuedConstraints.add(typeVariable);
    }

    private final boolean directionRequirementsForVariablesHold(ConstraintSystemCompletionContext constraintSystemCompletionContext) {
        for (Map.Entry<VariableWithConstraints, FixationDirection> entry : this.fixationDirectionsForVariables.entrySet()) {
            if (!hasProperConstraint(constraintSystemCompletionContext, entry.getKey(), entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    private final void updateDirection(FixationDirectionForVariable directionForVariable) {
        Unit unit;
        VariableWithConstraints variable = directionForVariable.getVariable();
        FixationDirection direction = directionForVariable.getDirection();
        FixationDirection fixationDirection = this.fixationDirectionsForVariables.get(variable);
        if (fixationDirection != null) {
            if (fixationDirection != FixationDirection.EQUALITY && fixationDirection != direction) {
                this.fixationDirectionsForVariables.put(variable, FixationDirection.EQUALITY);
            }
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            this.fixationDirectionsForVariables.put(variable, direction);
        }
    }

    /* JADX INFO: compiled from: CompletionModeCalculator.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/CalculatorForNestedCall$FixationDirectionForVariable;", "", "variable", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lorg/jetbrains/kotlin/fir/resolve/inference/CalculatorForNestedCall$FixationDirection;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;Lorg/jetbrains/kotlin/fir/resolve/inference/CalculatorForNestedCall$FixationDirection;)V", "getDirection", "()Lorg/jetbrains/kotlin/fir/resolve/inference/CalculatorForNestedCall$FixationDirection;", "getVariable", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class FixationDirectionForVariable {
        private final FixationDirection direction;
        private final VariableWithConstraints variable;

        public static /* synthetic */ FixationDirectionForVariable copy$default(FixationDirectionForVariable fixationDirectionForVariable, VariableWithConstraints variableWithConstraints, FixationDirection fixationDirection, int i, Object obj) {
            if ((i & 1) != 0) {
                variableWithConstraints = fixationDirectionForVariable.variable;
            }
            if ((i & 2) != 0) {
                fixationDirection = fixationDirectionForVariable.direction;
            }
            return fixationDirectionForVariable.copy(variableWithConstraints, fixationDirection);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final VariableWithConstraints getVariable() {
            return this.variable;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final FixationDirection getDirection() {
            return this.direction;
        }

        public final FixationDirectionForVariable copy(VariableWithConstraints variable, FixationDirection direction) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            Intrinsics.checkNotNullParameter(direction, "direction");
            return new FixationDirectionForVariable(variable, direction);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FixationDirectionForVariable)) {
                return false;
            }
            FixationDirectionForVariable fixationDirectionForVariable = (FixationDirectionForVariable) other;
            return Intrinsics.areEqual(this.variable, fixationDirectionForVariable.variable) && this.direction == fixationDirectionForVariable.direction;
        }

        public int hashCode() {
            return (this.variable.hashCode() * 31) + this.direction.hashCode();
        }

        public String toString() {
            return "FixationDirectionForVariable(variable=" + this.variable + ", direction=" + this.direction + ')';
        }

        public FixationDirectionForVariable(VariableWithConstraints variable, FixationDirection direction) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            Intrinsics.checkNotNullParameter(direction, "direction");
            this.variable = variable;
            this.direction = direction;
        }

        public final FixationDirection getDirection() {
            return this.direction;
        }

        public final VariableWithConstraints getVariable() {
            return this.variable;
        }
    }

    private final void collectRequiredDirectionsForVariables(ConstraintSystemCompletionContext constraintSystemCompletionContext, KotlinTypeMarker kotlinTypeMarker, TypeVariance typeVariance, Set<FixationDirectionForVariable> set) {
        SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible = constraintSystemCompletionContext.lowerBoundIfFlexible(kotlinTypeMarker);
        SimpleTypeMarker simpleTypeMarker = simpleTypeMarkerLowerBoundIfFlexible;
        int iArgumentsCount = constraintSystemCompletionContext.argumentsCount(simpleTypeMarker);
        if (iArgumentsCount <= 0 || constraintSystemCompletionContext.isError(simpleTypeMarker)) {
            processTypeWithoutParameters(constraintSystemCompletionContext, kotlinTypeMarker, typeVariance, set);
            return;
        }
        for (int i = 0; i < iArgumentsCount; i++) {
            TypeArgumentMarker argument = constraintSystemCompletionContext.getArgument(simpleTypeMarker, i);
            TypeParameterMarker parameter = constraintSystemCompletionContext.getParameter(constraintSystemCompletionContext.typeConstructor(simpleTypeMarkerLowerBoundIfFlexible), i);
            if (!constraintSystemCompletionContext.isStarProjection(argument)) {
                collectRequiredDirectionsForVariables(constraintSystemCompletionContext, constraintSystemCompletionContext.getType(argument), compositeVariance(constraintSystemCompletionContext, typeVariance, argument, parameter), set);
            }
        }
    }

    private final TypeVariance compositeVariance(ConstraintSystemCompletionContext constraintSystemCompletionContext, TypeVariance typeVariance, TypeArgumentMarker typeArgumentMarker, TypeParameterMarker typeParameterMarker) {
        TypeVariance typeVarianceEffectiveVariance = AbstractTypeChecker.INSTANCE.effectiveVariance(constraintSystemCompletionContext.getVariance(typeParameterMarker), constraintSystemCompletionContext.getVariance(typeArgumentMarker));
        if (typeVarianceEffectiveVariance == null) {
            typeVarianceEffectiveVariance = TypeVariance.INV;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[typeVariance.ordinal()];
        if (i == 1) {
            return TypeVariance.INV;
        }
        if (i == 2) {
            return typeVarianceEffectiveVariance;
        }
        if (i == 3) {
            return reversed(typeVarianceEffectiveVariance);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final TypeVariance reversed(TypeVariance typeVariance) {
        int i = WhenMappings.$EnumSwitchMapping$0[typeVariance.ordinal()];
        if (i == 1) {
            return TypeVariance.INV;
        }
        if (i == 2) {
            return TypeVariance.IN;
        }
        if (i == 3) {
            return TypeVariance.OUT;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final void processTypeWithoutParameters(ConstraintSystemCompletionContext constraintSystemCompletionContext, KotlinTypeMarker kotlinTypeMarker, TypeVariance typeVariance, Set<FixationDirectionForVariable> set) {
        FixationDirection fixationDirection;
        VariableWithConstraints variableWithConstraints = constraintSystemCompletionContext.getNotFixedTypeVariables().get(constraintSystemCompletionContext.typeConstructor(kotlinTypeMarker));
        if (variableWithConstraints == null) {
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[typeVariance.ordinal()];
        if (i == 1) {
            fixationDirection = FixationDirection.EQUALITY;
        } else if (i == 2) {
            fixationDirection = FixationDirection.TO_SUBTYPE;
        } else if (i == 3) {
            fixationDirection = FixationDirection.EQUALITY;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        set.add(new FixationDirectionForVariable(variableWithConstraints, fixationDirection));
    }

    private final boolean hasProperConstraint(ConstraintSystemCompletionContext constraintSystemCompletionContext, VariableWithConstraints variableWithConstraints, FixationDirection fixationDirection) {
        List<Constraint> constraints = variableWithConstraints.getConstraints();
        TypeVariableMarker typeVariable = variableWithConstraints.getTypeVariable();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        for (Constraint constraint : constraints) {
            if (hasRequiredKind(constraint, fixationDirection) && constraintSystemCompletionContext.isProperType(constraint.getType())) {
                if (constraintSystemCompletionContext.isIntegerLiteralTypeConstructor(constraintSystemCompletionContext.typeConstructor(constraint.getType()))) {
                    z2 = true;
                } else if (this.oracle.isSuitableResultedType(constraint.getType())) {
                    z = true;
                    z3 = true;
                } else if (!isLowerConstraintForPartiallyAnalyzedVariable(constraintSystemCompletionContext, constraint, typeVariable)) {
                    z = true;
                }
            }
        }
        if (z) {
            return !z2 || z3;
        }
        return false;
    }

    private final boolean hasRequiredKind(Constraint constraint, FixationDirection fixationDirection) {
        int i = WhenMappings.$EnumSwitchMapping$1[fixationDirection.ordinal()];
        if (i == 1) {
            return constraint.getKind().isLower() || constraint.getKind().isEqual();
        }
        if (i == 2) {
            return constraint.getKind().isEqual();
        }
        throw new NoWhenBranchMatchedException();
    }

    private final boolean isLowerConstraintForPartiallyAnalyzedVariable(ConstraintSystemCompletionContext constraintSystemCompletionContext, Constraint constraint, TypeVariableMarker typeVariableMarker) {
        final SimpleTypeMarker simpleTypeMarkerDefaultType = constraintSystemCompletionContext.defaultType(typeVariableMarker);
        if (!constraint.getKind().isLower()) {
            return false;
        }
        List<PostponedResolvedAtom> postponedAtoms = getPostponedAtoms();
        if ((postponedAtoms instanceof Collection) && postponedAtoms.isEmpty()) {
            return false;
        }
        Iterator<T> it = postponedAtoms.iterator();
        while (it.hasNext()) {
            ConeKotlinType expectedType = ((PostponedResolvedAtom) it.next()).getExpectedType();
            if (expectedType != null ? constraintSystemCompletionContext.contains(expectedType, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.CalculatorForNestedCall$isLowerConstraintForPartiallyAnalyzedVariable$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KotlinTypeMarker type) {
                    Intrinsics.checkNotNullParameter(type, "type");
                    return Boolean.valueOf(Intrinsics.areEqual(simpleTypeMarkerDefaultType, type));
                }
            }) : false) {
                return true;
            }
        }
        return false;
    }
}
