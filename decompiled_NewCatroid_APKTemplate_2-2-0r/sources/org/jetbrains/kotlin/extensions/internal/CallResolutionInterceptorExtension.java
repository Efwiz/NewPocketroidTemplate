package org.jetbrains.kotlin.extensions.internal;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.CallResolver;
import org.jetbrains.kotlin.resolve.calls.CandidateResolver;
import org.jetbrains.kotlin.resolve.calls.context.BasicCallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.resolve.calls.tower.NewResolutionOldInference;
import org.jetbrains.kotlin.resolve.calls.tower.PSICallResolver;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: NonStableExtensionPoints.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001JL\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016JL\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J`\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\n\u001a\u00020\u001b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001dH\u0016J:\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010&\u001a\u0004\u0018\u00010'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u0003H\u0016JL\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020+0\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J`\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020+0\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\n\u001a\u00020\u001b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001dH\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006,À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/extensions/internal/CallResolutionInterceptorExtension;", "", "interceptCandidates", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "candidates", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "candidateResolver", "Lorg/jetbrains/kotlin/resolve/calls/CandidateResolver;", "callResolver", "Lorg/jetbrains/kotlin/resolve/calls/CallResolver;", "name", "Lorg/jetbrains/kotlin/name/Name;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "interceptFunctionCandidates", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "resolutionContext", "resolutionScope", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;", "dispatchReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "extensionReceiver", "interceptResolvedCallAtomCandidate", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "candidateDescriptor", "completedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "resultSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "diagnostics", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "interceptVariableCandidates", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CallResolutionInterceptorExtension {
    default Collection<NewResolutionOldInference.MyCandidate> interceptCandidates(Collection<NewResolutionOldInference.MyCandidate> candidates, BasicCallResolutionContext context, CandidateResolver candidateResolver, CallResolver callResolver, Name name2, NewResolutionOldInference.ResolutionKind kind, TracingStrategy tracing) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(candidateResolver, "candidateResolver");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(tracing, "tracing");
        return candidates;
    }

    /* JADX WARN: Multi-variable type inference failed */
    default Collection<FunctionDescriptor> interceptFunctionCandidates(Collection<? extends FunctionDescriptor> candidates, ImplicitScopeTower scopeTower, BasicCallResolutionContext resolutionContext, ResolutionScope resolutionScope, CallResolver callResolver, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return candidates;
    }

    /* JADX WARN: Multi-variable type inference failed */
    default Collection<FunctionDescriptor> interceptFunctionCandidates(Collection<? extends FunctionDescriptor> candidates, ImplicitScopeTower scopeTower, BasicCallResolutionContext resolutionContext, ResolutionScope resolutionScope, PSICallResolver callResolver, Name name2, LookupLocation location, ReceiverValueWithSmartCastInfo dispatchReceiver, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return candidates;
    }

    default CallableDescriptor interceptResolvedCallAtomCandidate(CallableDescriptor candidateDescriptor, ResolvedCallAtom completedCallAtom, BindingTrace trace, NewTypeSubstitutor resultSubstitutor, Collection<? extends KotlinCallDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(candidateDescriptor, "candidateDescriptor");
        Intrinsics.checkNotNullParameter(completedCallAtom, "completedCallAtom");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        return candidateDescriptor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    default Collection<VariableDescriptor> interceptVariableCandidates(Collection<? extends VariableDescriptor> candidates, ImplicitScopeTower scopeTower, BasicCallResolutionContext resolutionContext, ResolutionScope resolutionScope, CallResolver callResolver, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return candidates;
    }

    /* JADX WARN: Multi-variable type inference failed */
    default Collection<VariableDescriptor> interceptVariableCandidates(Collection<? extends VariableDescriptor> candidates, ImplicitScopeTower scopeTower, BasicCallResolutionContext resolutionContext, ResolutionScope resolutionScope, PSICallResolver callResolver, Name name2, LookupLocation location, ReceiverValueWithSmartCastInfo dispatchReceiver, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return candidates;
    }
}
