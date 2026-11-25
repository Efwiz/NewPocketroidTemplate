package org.jetbrains.kotlin.extensions.internal;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
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

/* JADX INFO: compiled from: CandidateInterceptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 22\u00020\u0001:\u00012B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JJ\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J^\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001aJ8\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\tJJ\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020(0\t2\u0006\u0010)\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020+2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/JJ\u00100\u001a\b\u0012\u0004\u0012\u0002010\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002010\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J^\u00100\u001a\b\u0012\u0004\u0012\u0002010\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002010\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00182\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001aR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/extensions/internal/CandidateInterceptor;", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "extensions", "", "Lorg/jetbrains/kotlin/extensions/internal/CallResolutionInterceptorExtension;", "interceptFunctionCandidates", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "candidates", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "resolutionContext", "Lorg/jetbrains/kotlin/resolve/calls/context/BasicCallResolutionContext;", "resolutionScope", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "callResolver", "Lorg/jetbrains/kotlin/resolve/calls/CallResolver;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;", "dispatchReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "extensionReceiver", "interceptResolvedCallAtomCandidate", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "candidateDescriptor", "completedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "resultSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "diagnostics", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "interceptResolvedCandidates", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$MyCandidate;", "context", "candidateResolver", "Lorg/jetbrains/kotlin/resolve/calls/CandidateResolver;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolutionOldInference$ResolutionKind;", "tracing", "Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy;", "interceptVariableCandidates", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CandidateInterceptor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<CallResolutionInterceptorExtension> extensions;

    public CandidateInterceptor(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.extensions = INSTANCE.getInstances(project);
    }

    public final CallableDescriptor interceptResolvedCallAtomCandidate(CallableDescriptor candidateDescriptor, ResolvedCallAtom completedCallAtom, BindingTrace trace, NewTypeSubstitutor resultSubstitutor, Collection<? extends KotlinCallDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(candidateDescriptor, "candidateDescriptor");
        Intrinsics.checkNotNullParameter(completedCallAtom, "completedCallAtom");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        Iterator<T> it = this.extensions.iterator();
        CallableDescriptor callableDescriptorInterceptResolvedCallAtomCandidate = candidateDescriptor;
        while (it.hasNext()) {
            callableDescriptorInterceptResolvedCallAtomCandidate = ((CallResolutionInterceptorExtension) it.next()).interceptResolvedCallAtomCandidate(callableDescriptorInterceptResolvedCallAtomCandidate, completedCallAtom, trace, resultSubstitutor, diagnostics);
        }
        return callableDescriptorInterceptResolvedCallAtomCandidate;
    }

    public final Collection<NewResolutionOldInference.MyCandidate> interceptResolvedCandidates(Collection<NewResolutionOldInference.MyCandidate> candidates, BasicCallResolutionContext context, CandidateResolver candidateResolver, CallResolver callResolver, Name name2, NewResolutionOldInference.ResolutionKind kind, TracingStrategy tracing) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(candidateResolver, "candidateResolver");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(tracing, "tracing");
        Iterator<T> it = this.extensions.iterator();
        Collection<NewResolutionOldInference.MyCandidate> collectionInterceptCandidates = candidates;
        while (it.hasNext()) {
            collectionInterceptCandidates = ((CallResolutionInterceptorExtension) it.next()).interceptCandidates(collectionInterceptCandidates, context, candidateResolver, callResolver, name2, kind, tracing);
        }
        return collectionInterceptCandidates;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Collection<FunctionDescriptor> interceptFunctionCandidates(Collection<? extends FunctionDescriptor> candidates, ImplicitScopeTower scopeTower, BasicCallResolutionContext resolutionContext, ResolutionScope resolutionScope, CallResolver callResolver, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Iterator<T> it = this.extensions.iterator();
        Collection collectionInterceptFunctionCandidates = candidates;
        while (it.hasNext()) {
            collectionInterceptFunctionCandidates = ((CallResolutionInterceptorExtension) it.next()).interceptFunctionCandidates(collectionInterceptFunctionCandidates, scopeTower, resolutionContext, resolutionScope, callResolver, name2, location);
        }
        return collectionInterceptFunctionCandidates;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Collection<FunctionDescriptor> interceptFunctionCandidates(Collection<? extends FunctionDescriptor> candidates, ImplicitScopeTower scopeTower, BasicCallResolutionContext resolutionContext, ResolutionScope resolutionScope, PSICallResolver callResolver, Name name2, LookupLocation location, ReceiverValueWithSmartCastInfo dispatchReceiver, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Iterator<T> it = this.extensions.iterator();
        Collection collectionInterceptFunctionCandidates = candidates;
        while (it.hasNext()) {
            collectionInterceptFunctionCandidates = ((CallResolutionInterceptorExtension) it.next()).interceptFunctionCandidates(collectionInterceptFunctionCandidates, scopeTower, resolutionContext, resolutionScope, callResolver, name2, location, dispatchReceiver, extensionReceiver);
        }
        return collectionInterceptFunctionCandidates;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Collection<VariableDescriptor> interceptVariableCandidates(Collection<? extends VariableDescriptor> candidates, ImplicitScopeTower scopeTower, BasicCallResolutionContext resolutionContext, ResolutionScope resolutionScope, CallResolver callResolver, Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Iterator<T> it = this.extensions.iterator();
        Collection collectionInterceptVariableCandidates = candidates;
        while (it.hasNext()) {
            collectionInterceptVariableCandidates = ((CallResolutionInterceptorExtension) it.next()).interceptVariableCandidates(collectionInterceptVariableCandidates, scopeTower, resolutionContext, resolutionScope, callResolver, name2, location);
        }
        return collectionInterceptVariableCandidates;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Collection<VariableDescriptor> interceptVariableCandidates(Collection<? extends VariableDescriptor> candidates, ImplicitScopeTower scopeTower, BasicCallResolutionContext resolutionContext, ResolutionScope resolutionScope, PSICallResolver callResolver, Name name2, LookupLocation location, ReceiverValueWithSmartCastInfo dispatchReceiver, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(resolutionScope, "resolutionScope");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Iterator<T> it = this.extensions.iterator();
        Collection collectionInterceptVariableCandidates = candidates;
        while (it.hasNext()) {
            collectionInterceptVariableCandidates = ((CallResolutionInterceptorExtension) it.next()).interceptVariableCandidates(collectionInterceptVariableCandidates, scopeTower, resolutionContext, resolutionScope, callResolver, name2, location, dispatchReceiver, extensionReceiver);
        }
        return collectionInterceptVariableCandidates;
    }

    /* JADX INFO: compiled from: CandidateInterceptor.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/extensions/internal/CandidateInterceptor$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/extensions/internal/CallResolutionInterceptorExtension;", "()V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<CallResolutionInterceptorExtension> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
            super("org.jetbrains.kotlin.extensions.internal.callResolutionInterceptorExtension", CallResolutionInterceptorExtension.class);
        }
    }
}
