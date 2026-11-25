package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ResolvedCallAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002BI\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceCallAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCallAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceAtom;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "candidateDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "dispatchReceiverArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "extensionReceiverArgument", "reflectionCandidateType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;)V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedCallableReferenceCallAtom extends MutableResolvedCallAtom implements ResolvedCallableReferenceAtom {
    public /* synthetic */ ResolvedCallableReferenceCallAtom(KotlinCall kotlinCall, CallableDescriptor callableDescriptor, ExplicitReceiverKind explicitReceiverKind, SimpleKotlinCallArgument simpleKotlinCallArgument, SimpleKotlinCallArgument simpleKotlinCallArgument2, UnwrappedType unwrappedType, CallableReferenceResolutionCandidate callableReferenceResolutionCandidate, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinCall, callableDescriptor, explicitReceiverKind, simpleKotlinCallArgument, simpleKotlinCallArgument2, (i & 32) != 0 ? null : unwrappedType, (i & 64) != 0 ? null : callableReferenceResolutionCandidate);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResolvedCallableReferenceCallAtom(KotlinCall atom, CallableDescriptor candidateDescriptor, ExplicitReceiverKind explicitReceiverKind, SimpleKotlinCallArgument simpleKotlinCallArgument, SimpleKotlinCallArgument simpleKotlinCallArgument2, UnwrappedType unwrappedType, CallableReferenceResolutionCandidate callableReferenceResolutionCandidate) {
        super(atom, candidateDescriptor, explicitReceiverKind, simpleKotlinCallArgument, simpleKotlinCallArgument2, CollectionsKt.emptyList(), unwrappedType, callableReferenceResolutionCandidate);
        Intrinsics.checkNotNullParameter(atom, "atom");
        Intrinsics.checkNotNullParameter(candidateDescriptor, "candidateDescriptor");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
    }
}
