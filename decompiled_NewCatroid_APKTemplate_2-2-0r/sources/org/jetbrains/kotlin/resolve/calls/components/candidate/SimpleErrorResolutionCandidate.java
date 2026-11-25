package org.jetbrains.kotlin.resolve.calls.components.candidate;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.ErrorDescriptorResolutionPart;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionPart;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;

/* JADX INFO: compiled from: SimpleErrorResolutionCandidate.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/candidate/SimpleErrorResolutionCandidate;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/SimpleResolutionCandidate;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "baseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCallAtom;", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCallAtom;)V", "resolutionSequence", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionPart;", "getResolutionSequence", "()Ljava/util/List;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleErrorResolutionCandidate extends SimpleResolutionCandidate {
    private final List<ResolutionPart> resolutionSequence;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimpleErrorResolutionCandidate(KotlinCallComponents callComponents, KotlinResolutionCallbacks resolutionCallbacks, ImplicitScopeTower scopeTower, ConstraintStorage baseSystem, MutableResolvedCallAtom resolvedCall) {
        super(callComponents, resolutionCallbacks, scopeTower, baseSystem, resolvedCall, null, 32, null);
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(baseSystem, "baseSystem");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        this.resolutionSequence = CollectionsKt.listOf(ErrorDescriptorResolutionPart.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public List<ResolutionPart> getResolutionSequence() {
        return this.resolutionSequence;
    }
}
