package org.jetbrains.kotlin.resolve.calls.components;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceCallCandidatesAmbiguity;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.CompatibilityWarning;
import org.jetbrains.kotlin.resolve.calls.model.CompatibilityWarningOnArgument;
import org.jetbrains.kotlin.resolve.calls.model.EagerCallableReferenceAtom;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.LHSResult;
import org.jetbrains.kotlin.resolve.calls.model.NoneCallableReferenceCallCandidates;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallableReferenceArgumentAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedExpressionAtom;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SubKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.tower.TowerUtilsKt;
import org.jetbrains.kotlin.resolve.calls.tower.VisibilityError;
import org.jetbrains.kotlin.resolve.calls.tower.VisibilityErrorOnArgument;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: CallableReferenceArgumentResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0002J&\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceArgumentResolver;", "", "callableReferenceOverloadConflictResolver", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceOverloadConflictResolver;", "(Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceOverloadConflictResolver;)V", "getCallableReferenceOverloadConflictResolver", "()Lorg/jetbrains/kotlin/resolve/calls/components/CallableReferenceOverloadConflictResolver;", "buildResolvedKtArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "lhsResult", "Lorg/jetbrains/kotlin/resolve/calls/model/LHSResult;", "processCallableReferenceArgument", "", "csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "resolvedAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceArgumentAtom;", "diagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceArgumentResolver {
    private final CallableReferenceOverloadConflictResolver callableReferenceOverloadConflictResolver;

    public CallableReferenceArgumentResolver(CallableReferenceOverloadConflictResolver callableReferenceOverloadConflictResolver) {
        Intrinsics.checkNotNullParameter(callableReferenceOverloadConflictResolver, "callableReferenceOverloadConflictResolver");
        this.callableReferenceOverloadConflictResolver = callableReferenceOverloadConflictResolver;
    }

    public final CallableReferenceOverloadConflictResolver getCallableReferenceOverloadConflictResolver() {
        return this.callableReferenceOverloadConflictResolver;
    }

    public final void processCallableReferenceArgument(ConstraintSystemBuilder csBuilder, ResolvedCallableReferenceArgumentAtom resolvedAtom, KotlinDiagnosticsHolder diagnosticsHolder, KotlinResolutionCallbacks resolutionCallbacks) {
        UnwrappedType unwrappedTypeSafeSubstitute;
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        CallableReferenceKotlinCallArgument atom = resolvedAtom.getAtom();
        UnwrappedType expectedType = resolvedAtom.getExpectedType();
        if (expectedType != null) {
            TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = csBuilder.buildCurrentSubstitutor();
            Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor");
            unwrappedTypeSafeSubstitute = ((NewTypeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor).safeSubstitute(expectedType);
        } else {
            unwrappedTypeSafeSubstitute = null;
        }
        Collection<CallableReferenceResolutionCandidate> collectionResolveCallableReferenceArgument = resolutionCallbacks.resolveCallableReferenceArgument(resolvedAtom.getAtom(), unwrappedTypeSafeSubstitute, csBuilder.currentStorage());
        if (collectionResolveCallableReferenceArgument.size() > 1 && (resolvedAtom instanceof EagerCallableReferenceAtom)) {
            Collection<CallableReferenceResolutionCandidate> collection = collectionResolveCallableReferenceArgument;
            if ((collection instanceof Collection) && collection.isEmpty()) {
                diagnosticsHolder.addDiagnostic(new CallableReferenceCallCandidatesAmbiguity(atom, collectionResolveCallableReferenceArgument));
            } else {
                Iterator<T> it = collection.iterator();
                while (it.hasNext()) {
                    if (!TowerUtilsKt.isInapplicable(((CallableReferenceResolutionCandidate) it.next()).getResultingApplicability())) {
                    }
                }
                diagnosticsHolder.addDiagnostic(new CallableReferenceCallCandidatesAmbiguity(atom, collectionResolveCallableReferenceArgument));
            }
            resolvedAtom.setAnalyzedResults(null, CollectionsKt.listOf(((EagerCallableReferenceAtom) resolvedAtom).transformToPostponed()));
            return;
        }
        CallableReferenceResolutionCandidate callableReferenceResolutionCandidate = (CallableReferenceResolutionCandidate) CollectionsKt.singleOrNull(collectionResolveCallableReferenceArgument);
        if (callableReferenceResolutionCandidate != null) {
            ConstraintSystemBuilder constraintSystemBuilder = csBuilder;
            FreshVariableNewTypeSubstitutor freshVariableNewTypeSubstitutorCreateToFreshVariableSubstitutorAndAddInitialConstraints = CreateFreshVariablesSubstitutor.INSTANCE.createToFreshVariableSubstitutorAndAddInitialConstraints(callableReferenceResolutionCandidate.getCandidate(), resolvedAtom.getAtom().getCall(), constraintSystemBuilder);
            CallableReferenceResolutionKt.addConstraints(callableReferenceResolutionCandidate, constraintSystemBuilder, freshVariableNewTypeSubstitutorCreateToFreshVariableSubstitutorAndAddInitialConstraints, atom);
            for (VisibilityErrorOnArgument visibilityErrorOnArgument : callableReferenceResolutionCandidate.getDiagnostics()) {
                if (visibilityErrorOnArgument instanceof CompatibilityWarning) {
                    visibilityErrorOnArgument = new CompatibilityWarningOnArgument(atom, ((CompatibilityWarning) visibilityErrorOnArgument).getCandidate());
                } else if (visibilityErrorOnArgument instanceof VisibilityError) {
                    visibilityErrorOnArgument = new VisibilityErrorOnArgument(atom, ((VisibilityError) visibilityErrorOnArgument).getInvisibleMember());
                }
                diagnosticsHolder.addDiagnostic(visibilityErrorOnArgument);
            }
            callableReferenceResolutionCandidate.setFreshVariablesSubstitutor$resolution(freshVariableNewTypeSubstitutorCreateToFreshVariableSubstitutorAndAddInitialConstraints);
        } else if (collectionResolveCallableReferenceArgument.isEmpty()) {
            diagnosticsHolder.addDiagnostic(new NoneCallableReferenceCallCandidates(atom));
        } else {
            diagnosticsHolder.addDiagnostic(new CallableReferenceCallCandidatesAmbiguity(atom, collectionResolveCallableReferenceArgument));
        }
        resolvedAtom.setAnalyzedResults(callableReferenceResolutionCandidate, CollectionsKt.listOfNotNull(buildResolvedKtArgument(atom.getLhsResult())));
    }

    private final ResolvedAtom buildResolvedKtArgument(LHSResult lhsResult) {
        if (!(lhsResult instanceof LHSResult.Expression)) {
            return null;
        }
        SimpleKotlinCallArgument lshCallArgument = ((LHSResult.Expression) lhsResult).getLshCallArgument();
        if (lshCallArgument instanceof SubKotlinCallArgument) {
            return ((SubKotlinCallArgument) lshCallArgument).getCallResult();
        }
        if (lshCallArgument instanceof ExpressionKotlinCallArgument) {
            return new ResolvedExpressionAtom((ExpressionKotlinCallArgument) lshCallArgument);
        }
        ArgumentsUtilsKt.unexpectedArgument(lshCallArgument);
        throw null;
    }
}
