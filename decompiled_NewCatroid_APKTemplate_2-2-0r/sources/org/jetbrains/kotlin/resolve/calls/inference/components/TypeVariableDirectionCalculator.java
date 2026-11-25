package org.jetbrains.kotlin.resolve.calls.inference.components;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: TypeVariableDirectionCalculator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002&'B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001c\u0010\u0010\u001a\u00020\u00112\n\u0010\u0012\u001a\u00060\fj\u0002`\r2\u0006\u0010\u0013\u001a\u00020\u000eH\u0002J\"\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00052\n\u0010\u0012\u001a\u00060\fj\u0002`\r2\u0006\u0010\u0013\u001a\u00020\u000eH\u0002J\u0012\u0010\u0016\u001a\u00020\u000e2\n\u0010\u0017\u001a\u00060\fj\u0002`\rJ\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\bH\u0002J\f\u0010\u001e\u001a\u00020\u000e*\u00020\u000eH\u0002JP\u0010\u001f\u001a\u00020\u0011*\u00020\b2\u0006\u0010 \u001a\u00020\u000e2:\u0010!\u001a6\u0012\u0017\u0012\u00150\fj\u0002`\r¢\u0006\f\b#\u0012\b\b$\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b#\u0012\b\b$\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u00110\"H\u0002JP\u0010\u001f\u001a\u00020\u0011*\u00020%2\u0006\u0010 \u001a\u00020\u000e2:\u0010!\u001a6\u0012\u0017\u0012\u00150\fj\u0002`\r¢\u0006\f\b#\u0012\b\b$\u0012\u0004\b\b(\u0012\u0012\u0013\u0012\u00110\u000e¢\u0006\f\b#\u0012\b\b$\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u00110\"H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R2\u0010\n\u001a&\u0012\b\u0012\u00060\fj\u0002`\r\u0012\u0004\u0012\u00020\u000e0\u000bj\u0012\u0012\b\u0012\u00060\fj\u0002`\r\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDirectionCalculator;", "", "c", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder$Context;", "postponedKtPrimitives", "", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtomMarker;", "topLevelType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder$Context;Ljava/util/List;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)V", "directions", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/Variable;", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDirectionCalculator$ResolveDirection;", "Lkotlin/collections/HashMap;", "enterToNode", "", "variable", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "getConstraintDependencies", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDirectionCalculator$NodeWithDirection;", "getDirection", "typeVariable", "isInterestingConstraint", "", "constraint", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "setupDirections", "topReturnType", "opposite", "visitType", "startDirection", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "NodeWithDirection", "ResolveDirection", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeVariableDirectionCalculator {
    private final VariableFixationFinder.Context c;
    private final HashMap<VariableWithConstraints, ResolveDirection> directions;
    private final List<PostponedResolvedAtomMarker> postponedKtPrimitives;

    /* JADX INFO: compiled from: TypeVariableDirectionCalculator.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDirectionCalculator$ResolveDirection;", "", "(Ljava/lang/String;I)V", "TO_SUBTYPE", "TO_SUPERTYPE", GrsBaseInfo.CountryCodeSource.UNKNOWN, "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum ResolveDirection {
        TO_SUBTYPE,
        TO_SUPERTYPE,
        UNKNOWN;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ResolveDirection> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: TypeVariableDirectionCalculator.kt */
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
            int[] iArr2 = new int[ResolveDirection.values().length];
            try {
                iArr2[ResolveDirection.UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ResolveDirection.TO_SUPERTYPE.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ResolveDirection.TO_SUBTYPE.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TypeVariableDirectionCalculator(VariableFixationFinder.Context c, List<? extends PostponedResolvedAtomMarker> postponedKtPrimitives, KotlinTypeMarker topLevelType) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(postponedKtPrimitives, "postponedKtPrimitives");
        Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
        this.c = c;
        this.postponedKtPrimitives = postponedKtPrimitives;
        this.directions = new HashMap<>();
        setupDirections(topLevelType);
    }

    /* JADX INFO: compiled from: TypeVariableDirectionCalculator.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDirectionCalculator$NodeWithDirection;", "", "variableWithConstraints", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDirectionCalculator$ResolveDirection;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDirectionCalculator$ResolveDirection;)V", "getDirection", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDirectionCalculator$ResolveDirection;", "getVariableWithConstraints", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class NodeWithDirection {
        private final ResolveDirection direction;
        private final VariableWithConstraints variableWithConstraints;

        public static /* synthetic */ NodeWithDirection copy$default(NodeWithDirection nodeWithDirection, VariableWithConstraints variableWithConstraints, ResolveDirection resolveDirection, int i, Object obj) {
            if ((i & 1) != 0) {
                variableWithConstraints = nodeWithDirection.variableWithConstraints;
            }
            if ((i & 2) != 0) {
                resolveDirection = nodeWithDirection.direction;
            }
            return nodeWithDirection.copy(variableWithConstraints, resolveDirection);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final VariableWithConstraints getVariableWithConstraints() {
            return this.variableWithConstraints;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ResolveDirection getDirection() {
            return this.direction;
        }

        public final NodeWithDirection copy(VariableWithConstraints variableWithConstraints, ResolveDirection direction) {
            Intrinsics.checkNotNullParameter(variableWithConstraints, "variableWithConstraints");
            Intrinsics.checkNotNullParameter(direction, "direction");
            return new NodeWithDirection(variableWithConstraints, direction);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof NodeWithDirection)) {
                return false;
            }
            NodeWithDirection nodeWithDirection = (NodeWithDirection) other;
            return Intrinsics.areEqual(this.variableWithConstraints, nodeWithDirection.variableWithConstraints) && this.direction == nodeWithDirection.direction;
        }

        public int hashCode() {
            return (this.variableWithConstraints.hashCode() * 31) + this.direction.hashCode();
        }

        public NodeWithDirection(VariableWithConstraints variableWithConstraints, ResolveDirection direction) {
            Intrinsics.checkNotNullParameter(variableWithConstraints, "variableWithConstraints");
            Intrinsics.checkNotNullParameter(direction, "direction");
            this.variableWithConstraints = variableWithConstraints;
            this.direction = direction;
        }

        public final ResolveDirection getDirection() {
            return this.direction;
        }

        public final VariableWithConstraints getVariableWithConstraints() {
            return this.variableWithConstraints;
        }

        public String toString() {
            return this.variableWithConstraints + " to " + this.direction;
        }
    }

    public final ResolveDirection getDirection(VariableWithConstraints typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        ResolveDirection orDefault = this.directions.getOrDefault(typeVariable, ResolveDirection.UNKNOWN);
        Intrinsics.checkNotNullExpressionValue(orDefault, "getOrDefault(...)");
        return orDefault;
    }

    private final void setupDirections(KotlinTypeMarker topReturnType) {
        visitType(topReturnType, ResolveDirection.TO_SUBTYPE, new Function2<VariableWithConstraints, ResolveDirection, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDirectionCalculator.setupDirections.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(VariableWithConstraints variableWithConstraints, ResolveDirection resolveDirection) {
                invoke2(variableWithConstraints, resolveDirection);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(VariableWithConstraints variableWithConstraints, ResolveDirection direction) {
                Intrinsics.checkNotNullParameter(variableWithConstraints, "variableWithConstraints");
                Intrinsics.checkNotNullParameter(direction, "direction");
                TypeVariableDirectionCalculator.this.enterToNode(variableWithConstraints, direction);
            }
        });
        Iterator<PostponedResolvedAtomMarker> it = this.postponedKtPrimitives.iterator();
        while (it.hasNext()) {
            Iterator<KotlinTypeMarker> it2 = it.next().getInputTypes().iterator();
            while (it2.hasNext()) {
                visitType(it2.next(), ResolveDirection.TO_SUBTYPE, new Function2<VariableWithConstraints, ResolveDirection, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDirectionCalculator.setupDirections.2
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(VariableWithConstraints variableWithConstraints, ResolveDirection resolveDirection) {
                        invoke2(variableWithConstraints, resolveDirection);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(VariableWithConstraints variableWithConstraints, ResolveDirection direction) {
                        Intrinsics.checkNotNullParameter(variableWithConstraints, "variableWithConstraints");
                        Intrinsics.checkNotNullParameter(direction, "direction");
                        TypeVariableDirectionCalculator.this.enterToNode(variableWithConstraints, direction);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void enterToNode(VariableWithConstraints variable, ResolveDirection direction) {
        if (direction == ResolveDirection.UNKNOWN) {
            return;
        }
        ResolveDirection resolveDirection = this.directions.get(variable);
        if (resolveDirection != null) {
            if (resolveDirection != direction) {
                this.directions.put(variable, ResolveDirection.UNKNOWN);
            }
        } else {
            this.directions.put(variable, direction);
            for (NodeWithDirection nodeWithDirection : getConstraintDependencies(variable, direction)) {
                enterToNode(nodeWithDirection.getVariableWithConstraints(), nodeWithDirection.getDirection());
            }
        }
    }

    private final List<NodeWithDirection> getConstraintDependencies(VariableWithConstraints variable, ResolveDirection direction) {
        final SmartList smartList = new SmartList();
        for (Constraint constraint : variable.getConstraints()) {
            if (isInterestingConstraint(direction, constraint)) {
                visitType(constraint.getType(), direction, new Function2<VariableWithConstraints, ResolveDirection, Unit>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TypeVariableDirectionCalculator$getConstraintDependencies$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(VariableWithConstraints variableWithConstraints, TypeVariableDirectionCalculator.ResolveDirection resolveDirection) {
                        invoke2(variableWithConstraints, resolveDirection);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(VariableWithConstraints nodeVariable, TypeVariableDirectionCalculator.ResolveDirection nodeDirection) {
                        Intrinsics.checkNotNullParameter(nodeVariable, "nodeVariable");
                        Intrinsics.checkNotNullParameter(nodeDirection, "nodeDirection");
                        smartList.add(new TypeVariableDirectionCalculator.NodeWithDirection(nodeVariable, nodeDirection));
                    }
                });
            }
        }
        return smartList;
    }

    private final boolean isInterestingConstraint(ResolveDirection direction, Constraint constraint) {
        return ((direction == ResolveDirection.TO_SUBTYPE && constraint.getKind() == ConstraintKind.UPPER) || (direction == ResolveDirection.TO_SUPERTYPE && constraint.getKind() == ConstraintKind.LOWER)) ? false : true;
    }

    private final void visitType(KotlinTypeMarker kotlinTypeMarker, ResolveDirection resolveDirection, Function2<? super VariableWithConstraints, ? super ResolveDirection, Unit> function2) {
        if (kotlinTypeMarker instanceof SimpleTypeMarker) {
            visitType((SimpleTypeMarker) kotlinTypeMarker, resolveDirection, function2);
        } else {
            if (kotlinTypeMarker instanceof FlexibleTypeMarker) {
                VariableFixationFinder.Context context = this.c;
                FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) kotlinTypeMarker;
                visitType(context.lowerBound(flexibleTypeMarker), resolveDirection, function2);
                visitType(context.upperBound(flexibleTypeMarker), resolveDirection, function2);
                return;
            }
            throw new IllegalStateException("?!".toString());
        }
    }

    private final void visitType(SimpleTypeMarker simpleTypeMarker, ResolveDirection resolveDirection, Function2<? super VariableWithConstraints, ? super ResolveDirection, Unit> function2) {
        ResolveDirection resolveDirectionOpposite;
        VariableFixationFinder.Context context = this.c;
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = context.typeConstructor(simpleTypeMarker);
        if (!context.isIntersection(typeConstructorMarkerTypeConstructor)) {
            SimpleTypeMarker simpleTypeMarker2 = simpleTypeMarker;
            if (context.argumentsCount(simpleTypeMarker2) == 0) {
                VariableWithConstraints variableWithConstraints = this.c.getNotFixedTypeVariables().get(typeConstructorMarkerTypeConstructor);
                if (variableWithConstraints != null) {
                    function2.invoke(variableWithConstraints, resolveDirection);
                    return;
                }
                return;
            }
            if (context.parametersCount(typeConstructorMarkerTypeConstructor) != context.argumentsCount(simpleTypeMarker2)) {
                return;
            }
            int iParametersCount = context.parametersCount(typeConstructorMarkerTypeConstructor);
            for (int i = 0; i < iParametersCount; i++) {
                TypeParameterMarker parameter = context.getParameter(typeConstructorMarkerTypeConstructor, i);
                TypeArgumentMarker argument = context.getArgument(simpleTypeMarker2, i);
                if (!context.isStarProjection(argument)) {
                    TypeVariance typeVarianceEffectiveVariance = AbstractTypeChecker.INSTANCE.effectiveVariance(context.getVariance(parameter), context.getVariance(argument));
                    if (typeVarianceEffectiveVariance == null) {
                        typeVarianceEffectiveVariance = TypeVariance.INV;
                    }
                    int i2 = WhenMappings.$EnumSwitchMapping$0[typeVarianceEffectiveVariance.ordinal()];
                    if (i2 == 1) {
                        resolveDirectionOpposite = ResolveDirection.UNKNOWN;
                    } else if (i2 == 2) {
                        resolveDirectionOpposite = resolveDirection;
                    } else {
                        if (i2 != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        resolveDirectionOpposite = opposite(resolveDirection);
                    }
                    visitType(context.getType(argument), resolveDirectionOpposite, function2);
                }
            }
            return;
        }
        Iterator<T> it = context.supertypes(typeConstructorMarkerTypeConstructor).iterator();
        while (it.hasNext()) {
            visitType((KotlinTypeMarker) it.next(), resolveDirection, function2);
        }
    }

    private final ResolveDirection opposite(ResolveDirection resolveDirection) {
        int i = WhenMappings.$EnumSwitchMapping$1[resolveDirection.ordinal()];
        if (i == 1) {
            return ResolveDirection.UNKNOWN;
        }
        if (i == 2) {
            return ResolveDirection.TO_SUBTYPE;
        }
        if (i == 3) {
            return ResolveDirection.TO_SUPERTYPE;
        }
        throw new NoWhenBranchMatchedException();
    }
}
