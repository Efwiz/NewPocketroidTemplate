package org.jetbrains.kotlin.resolve.calls.inference.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConstraintStorage.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0001.R\u001e\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0007R<\u0010\u000b\u001a,\u0012\"\u0012 \u0012\u0004\u0012\u00020\u0004\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e0\f0\r0\f\u0012\u0004\u0012\u00020\t0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0007RD\u0010\u0010\u001a4\u00120\u0012.\u0012\u0004\u0012\u00020\u0011\u0012$\u0012\"\u0012\u001a\u0012\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00130\f0\u0012j\u0002`\u00140\rj\u0002`\u00150\f0\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0018\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0017R\u001e\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\t0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0007R\u0012\u0010\u001d\u001a\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0018\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\rX¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0017R\u0012\u0010$\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R6\u0010'\u001a&\u0012\"\u0012 \u0012\u0004\u0012\u00020\u0011\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00130\f0\r0\f0\rX¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\u0017R\u001e\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020*0\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\u0007R\u0018\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\rX¦\u0004¢\u0006\u0006\u001a\u0004\b-\u0010\u0017ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006/À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "", "allTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "getAllTypeVariables", "()Ljava/util/Map;", "builtFunctionalTypesForPostponedArgumentsByExpectedTypeVariables", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getBuiltFunctionalTypesForPostponedArgumentsByExpectedTypeVariables", "builtFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables", "Lkotlin/Pair;", "", "", "getBuiltFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables", "constraintsFromAllForkPoints", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointBranchDescription;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointData;", "getConstraintsFromAllForkPoints", "()Ljava/util/List;", "errors", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "getErrors", "fixedTypeVariables", "getFixedTypeVariables", "hasContradiction", "", "getHasContradiction", "()Z", "initialConstraints", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "getInitialConstraints", "maxTypeDepthFromInitialConstraints", "getMaxTypeDepthFromInitialConstraints", "()I", "missedConstraints", "getMissedConstraints", "notFixedTypeVariables", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "getNotFixedTypeVariables", "postponedTypeVariables", "getPostponedTypeVariables", "Empty", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ConstraintStorage {
    Map<TypeConstructorMarker, TypeVariableMarker> getAllTypeVariables();

    Map<TypeConstructorMarker, KotlinTypeMarker> getBuiltFunctionalTypesForPostponedArgumentsByExpectedTypeVariables();

    Map<Pair<TypeConstructorMarker, List<Pair<TypeConstructorMarker, Integer>>>, KotlinTypeMarker> getBuiltFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables();

    List<Pair<IncorporationConstraintPosition, List<Set<Pair<TypeVariableMarker, Constraint>>>>> getConstraintsFromAllForkPoints();

    List<ConstraintSystemError> getErrors();

    Map<TypeConstructorMarker, KotlinTypeMarker> getFixedTypeVariables();

    boolean getHasContradiction();

    List<InitialConstraint> getInitialConstraints();

    int getMaxTypeDepthFromInitialConstraints();

    List<Pair<IncorporationConstraintPosition, List<Pair<TypeVariableMarker, Constraint>>>> getMissedConstraints();

    Map<TypeConstructorMarker, VariableWithConstraints> getNotFixedTypeVariables();

    List<TypeVariableMarker> getPostponedTypeVariables();

    /* JADX INFO: compiled from: ConstraintStorage.kt */
    @Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R \u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR \u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\bR>\u0010\f\u001a,\u0012\"\u0012 \u0012\u0004\u0012\u00020\u0005\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000f0\r0\u000e0\r\u0012\u0004\u0012\u00020\n0\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bRF\u0010\u0011\u001a4\u00120\u0012.\u0012\u0004\u0012\u00020\u0012\u0012$\u0012\"\u0012\u001a\u0012\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00140\r0\u0013j\u0002`\u00150\u000ej\u0002`\u00160\r0\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0018R \u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\bR\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\u0018R\u0014\u0010%\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R8\u0010(\u001a&\u0012\"\u0012 \u0012\u0004\u0012\u00020\u0012\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00140\r0\u000e0\r0\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\u0018R \u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020+0\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\bR\u001a\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010\u0018¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage$Empty;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "()V", "allTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "getAllTypeVariables", "()Ljava/util/Map;", "builtFunctionalTypesForPostponedArgumentsByExpectedTypeVariables", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getBuiltFunctionalTypesForPostponedArgumentsByExpectedTypeVariables", "builtFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables", "Lkotlin/Pair;", "", "", "getBuiltFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables", "constraintsFromAllForkPoints", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointBranchDescription;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointData;", "getConstraintsFromAllForkPoints", "()Ljava/util/List;", "errors", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "getErrors", "fixedTypeVariables", "getFixedTypeVariables", "hasContradiction", "", "getHasContradiction", "()Z", "initialConstraints", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "getInitialConstraints", "maxTypeDepthFromInitialConstraints", "getMaxTypeDepthFromInitialConstraints", "()I", "missedConstraints", "getMissedConstraints", "notFixedTypeVariables", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "getNotFixedTypeVariables", "postponedTypeVariables", "getPostponedTypeVariables", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty implements ConstraintStorage {
        public static final Empty INSTANCE = new Empty();
        private static final Map<Pair<TypeConstructorMarker, List<Pair<TypeConstructorMarker, Integer>>>, KotlinTypeMarker> builtFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables = MapsKt.emptyMap();
        private static final Map<TypeConstructorMarker, KotlinTypeMarker> builtFunctionalTypesForPostponedArgumentsByExpectedTypeVariables = MapsKt.emptyMap();
        private static final List<Pair<IncorporationConstraintPosition, List<Set<Pair<TypeVariableMarker, Constraint>>>>> constraintsFromAllForkPoints = CollectionsKt.emptyList();

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public boolean getHasContradiction() {
            return false;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public int getMaxTypeDepthFromInitialConstraints() {
            return 1;
        }

        private Empty() {
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public Map<TypeConstructorMarker, TypeVariableMarker> getAllTypeVariables() {
            return MapsKt.emptyMap();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public Map<TypeConstructorMarker, VariableWithConstraints> getNotFixedTypeVariables() {
            return MapsKt.emptyMap();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public List<Pair<IncorporationConstraintPosition, List<Pair<TypeVariableMarker, Constraint>>>> getMissedConstraints() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public List<InitialConstraint> getInitialConstraints() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public List<ConstraintSystemError> getErrors() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public Map<TypeConstructorMarker, KotlinTypeMarker> getFixedTypeVariables() {
            return MapsKt.emptyMap();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public List<TypeVariableMarker> getPostponedTypeVariables() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public Map<Pair<TypeConstructorMarker, List<Pair<TypeConstructorMarker, Integer>>>, KotlinTypeMarker> getBuiltFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables() {
            return builtFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public Map<TypeConstructorMarker, KotlinTypeMarker> getBuiltFunctionalTypesForPostponedArgumentsByExpectedTypeVariables() {
            return builtFunctionalTypesForPostponedArgumentsByExpectedTypeVariables;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
        public List<Pair<IncorporationConstraintPosition, List<Set<Pair<TypeVariableMarker, Constraint>>>>> getConstraintsFromAllForkPoints() {
            return constraintsFromAllForkPoints;
        }
    }
}
