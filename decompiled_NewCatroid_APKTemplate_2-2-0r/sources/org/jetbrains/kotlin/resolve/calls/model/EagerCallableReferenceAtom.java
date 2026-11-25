package org.jetbrains.kotlin.resolve.calls.model;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fR\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/EagerCallableReferenceAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceArgumentAtom;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "inputTypes", "", "getInputTypes", "()Ljava/util/Collection;", "outputType", "getOutputType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "transformToPostponed", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedCallableReferenceAtom;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EagerCallableReferenceAtom extends ResolvedCallableReferenceArgumentAtom {
    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    /* JADX INFO: renamed from: getOutputType */
    public UnwrappedType getReturnType() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EagerCallableReferenceAtom(CallableReferenceKotlinCallArgument atom, UnwrappedType unwrappedType) {
        super(atom, unwrappedType);
        Intrinsics.checkNotNullParameter(atom, "atom");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtom, org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public Collection<UnwrappedType> getInputTypes() {
        return CollectionsKt.emptyList();
    }

    public final PostponedCallableReferenceAtom transformToPostponed() {
        return new PostponedCallableReferenceAtom(this);
    }
}
