package org.jetbrains.kotlin.resolve.calls.inference.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: MutableConstraintStorage.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R \u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR \u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\bR>\u0010\f\u001a,\u0012\"\u0012 \u0012\u0004\u0012\u00020\u0005\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000f0\r0\u000e0\r\u0012\u0004\u0012\u00020\n0\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\bRF\u0010\u0011\u001a4\u00120\u0012.\u0012\u0004\u0012\u00020\u0013\u0012$\u0012\"\u0012\u001a\u0012\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00150\r0\u0014j\u0002`\u00160\u000ej\u0002`\u00170\r0\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R \u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\bR\u0014\u0010\u001f\u001a\u00020 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u001a\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0019R\u001a\u0010&\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R8\u0010+\u001a&\u0012\"\u0012 \u0012\u0004\u0012\u00020\u0013\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00150\r0\u00120\r0\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0019R \u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020.0\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u0010\bR\u001a\u00100\u001a\b\u0012\u0004\u0012\u00020\u00060\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u0019¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/MutableConstraintStorage;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "()V", "allTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "getAllTypeVariables", "()Ljava/util/Map;", "builtFunctionalTypesForPostponedArgumentsByExpectedTypeVariables", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getBuiltFunctionalTypesForPostponedArgumentsByExpectedTypeVariables", "builtFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables", "Lkotlin/Pair;", "", "", "getBuiltFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables", "constraintsFromAllForkPoints", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointBranchDescription;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointData;", "getConstraintsFromAllForkPoints", "()Ljava/util/List;", "errors", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "getErrors", "fixedTypeVariables", "getFixedTypeVariables", "hasContradiction", "", "getHasContradiction", "()Z", "initialConstraints", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/InitialConstraint;", "getInitialConstraints", "maxTypeDepthFromInitialConstraints", "getMaxTypeDepthFromInitialConstraints", "()I", "setMaxTypeDepthFromInitialConstraints", "(I)V", "missedConstraints", "getMissedConstraints", "notFixedTypeVariables", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/MutableVariableWithConstraints;", "getNotFixedTypeVariables", "postponedTypeVariables", "getPostponedTypeVariables", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableConstraintStorage implements ConstraintStorage {
    private final Map<TypeConstructorMarker, TypeVariableMarker> allTypeVariables = new LinkedHashMap();
    private final Map<TypeConstructorMarker, MutableVariableWithConstraints> notFixedTypeVariables = new LinkedHashMap();
    private final List<Pair<IncorporationConstraintPosition, List<Pair<TypeVariableMarker, Constraint>>>> missedConstraints = new SmartList();
    private final List<InitialConstraint> initialConstraints = new SmartList();
    private int maxTypeDepthFromInitialConstraints = 1;
    private final List<ConstraintSystemError> errors = new SmartList();
    private final Map<TypeConstructorMarker, KotlinTypeMarker> fixedTypeVariables = new LinkedHashMap();
    private final List<TypeVariableMarker> postponedTypeVariables = new SmartList();
    private final Map<Pair<TypeConstructorMarker, List<Pair<TypeConstructorMarker, Integer>>>, KotlinTypeMarker> builtFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables = new LinkedHashMap();
    private final Map<TypeConstructorMarker, KotlinTypeMarker> builtFunctionalTypesForPostponedArgumentsByExpectedTypeVariables = new LinkedHashMap();
    private final List<Pair<IncorporationConstraintPosition, List<Set<Pair<TypeVariableMarker, Constraint>>>>> constraintsFromAllForkPoints = new SmartList();

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public Map<TypeConstructorMarker, TypeVariableMarker> getAllTypeVariables() {
        return this.allTypeVariables;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public Map<TypeConstructorMarker, MutableVariableWithConstraints> getNotFixedTypeVariables() {
        return this.notFixedTypeVariables;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public List<Pair<IncorporationConstraintPosition, List<Pair<TypeVariableMarker, Constraint>>>> getMissedConstraints() {
        return this.missedConstraints;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public List<InitialConstraint> getInitialConstraints() {
        return this.initialConstraints;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public int getMaxTypeDepthFromInitialConstraints() {
        return this.maxTypeDepthFromInitialConstraints;
    }

    public void setMaxTypeDepthFromInitialConstraints(int i) {
        this.maxTypeDepthFromInitialConstraints = i;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public List<ConstraintSystemError> getErrors() {
        return this.errors;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public boolean getHasContradiction() {
        List<ConstraintSystemError> errors = getErrors();
        if ((errors instanceof Collection) && errors.isEmpty()) {
            return false;
        }
        Iterator<T> it = errors.iterator();
        while (it.hasNext()) {
            if (!CandidateApplicabilityKt.isSuccess(((ConstraintSystemError) it.next()).getApplicability())) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public Map<TypeConstructorMarker, KotlinTypeMarker> getFixedTypeVariables() {
        return this.fixedTypeVariables;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public List<TypeVariableMarker> getPostponedTypeVariables() {
        return this.postponedTypeVariables;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public Map<Pair<TypeConstructorMarker, List<Pair<TypeConstructorMarker, Integer>>>, KotlinTypeMarker> getBuiltFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables() {
        return this.builtFunctionalTypesForPostponedArgumentsByTopLevelTypeVariables;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public Map<TypeConstructorMarker, KotlinTypeMarker> getBuiltFunctionalTypesForPostponedArgumentsByExpectedTypeVariables() {
        return this.builtFunctionalTypesForPostponedArgumentsByExpectedTypeVariables;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage
    public List<Pair<IncorporationConstraintPosition, List<Set<Pair<TypeVariableMarker, Constraint>>>>> getConstraintsFromAllForkPoints() {
        return this.constraintsFromAllForkPoints;
    }
}
