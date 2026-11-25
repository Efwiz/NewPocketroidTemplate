package org.jetbrains.kotlin.resolve.calls.model;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintError;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintMismatch;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintWarning;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B'\b\u0004\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0004J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0016\u0010\n\u001a\u0004\u0018\u00010\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u0082\u0001\u0002\u001a\u001b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "resultCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "constraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;)V", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionAtom;", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionAtom;", "getConstraintSystem", "()Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "getDiagnostics", "()Ljava/util/List;", "completedDiagnostic", "substitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "setAnalyzedResults", "", "subResolvedAtoms", "toString", "", "Lorg/jetbrains/kotlin/resolve/calls/model/AllCandidatesResolutionResult;", "Lorg/jetbrains/kotlin/resolve/calls/model/SingleCallResolutionResult;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CallResolutionResult extends ResolvedAtom {
    private final NewConstraintSystem constraintSystem;
    private final List<KotlinCallDiagnostic> diagnostics;

    public /* synthetic */ CallResolutionResult(ResolvedCallAtom resolvedCallAtom, List list, NewConstraintSystem newConstraintSystem, DefaultConstructorMarker defaultConstructorMarker) {
        this(resolvedCallAtom, list, newConstraintSystem);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    public ResolutionAtom getAtom() {
        return null;
    }

    public final List<KotlinCallDiagnostic> getDiagnostics() {
        return this.diagnostics;
    }

    public final NewConstraintSystem getConstraintSystem() {
        return this.constraintSystem;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private CallResolutionResult(ResolvedCallAtom resolvedCallAtom, List<? extends KotlinCallDiagnostic> list, NewConstraintSystem newConstraintSystem) {
        super(null);
        this.diagnostics = list;
        this.constraintSystem = newConstraintSystem;
        setAnalyzedResults(CollectionsKt.listOfNotNull(resolvedCallAtom));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    protected final void setAnalyzedResults(List<? extends ResolvedAtom> subResolvedAtoms) {
        Intrinsics.checkNotNullParameter(subResolvedAtoms, "subResolvedAtoms");
        super.setAnalyzedResults(subResolvedAtoms);
    }

    public final List<KotlinCallDiagnostic> completedDiagnostic(NewTypeSubstitutor substitutor) {
        UnwrappedType unwrappedTypeUnwrap;
        KotlinConstraintSystemDiagnostic kotlinConstraintSystemDiagnosticAsDiagnostic;
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        List<KotlinCallDiagnostic> list = this.diagnostics;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KotlinConstraintSystemDiagnostic kotlinConstraintSystemDiagnostic : list) {
            Object constraintSystemError = KotlinCallDiagnosticsKt.getConstraintSystemError(kotlinConstraintSystemDiagnostic);
            if (constraintSystemError != null && (constraintSystemError instanceof NewConstraintMismatch)) {
                NewConstraintMismatch newConstraintMismatch = (NewConstraintMismatch) constraintSystemError;
                KotlinTypeMarker lowerType = newConstraintMismatch.getLowerType();
                KotlinType kotlinType = lowerType instanceof KotlinType ? (KotlinType) lowerType : null;
                if (kotlinType != null && (unwrappedTypeUnwrap = kotlinType.unwrap()) != null) {
                    UnwrappedType unwrappedTypeSafeSubstitute = substitutor.safeSubstitute(TypeUtilsKt.unCapture(unwrappedTypeUnwrap));
                    if (newConstraintMismatch instanceof NewConstraintError) {
                        kotlinConstraintSystemDiagnosticAsDiagnostic = KotlinCallDiagnosticsKt.asDiagnostic(new NewConstraintError(unwrappedTypeSafeSubstitute, newConstraintMismatch.getUpperType(), newConstraintMismatch.getPosition()));
                    } else {
                        if (!(newConstraintMismatch instanceof NewConstraintWarning)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        kotlinConstraintSystemDiagnosticAsDiagnostic = KotlinCallDiagnosticsKt.asDiagnostic(new NewConstraintWarning(unwrappedTypeSafeSubstitute, newConstraintMismatch.getUpperType(), newConstraintMismatch.getPosition()));
                    }
                    kotlinConstraintSystemDiagnostic = kotlinConstraintSystemDiagnosticAsDiagnostic;
                }
            }
            arrayList.add(kotlinConstraintSystemDiagnostic);
        }
        return arrayList;
    }

    public String toString() {
        return "diagnostics: (" + CollectionsKt.joinToString$default(this.diagnostics, null, null, null, 0, null, null, 63, null) + ')';
    }
}
