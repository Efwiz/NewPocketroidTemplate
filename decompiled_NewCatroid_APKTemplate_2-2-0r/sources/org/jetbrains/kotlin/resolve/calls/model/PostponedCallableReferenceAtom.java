package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\"\u0010\b\u001a\u0004\u0018\u00010\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/PostponedCallableReferenceAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/AbstractPostponedCallableReferenceAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedCallableReferenceMarker;", "eagerCallableReferenceAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/EagerCallableReferenceAtom;", "(Lorg/jetbrains/kotlin/resolve/calls/model/EagerCallableReferenceAtom;)V", "<set-?>", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "revisedExpectedType", "getRevisedExpectedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "reviseExpectedType", "", "expectedType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PostponedCallableReferenceAtom extends AbstractPostponedCallableReferenceAtom implements PostponedCallableReferenceMarker {
    private UnwrappedType revisedExpectedType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostponedCallableReferenceAtom(EagerCallableReferenceAtom eagerCallableReferenceAtom) {
        super(eagerCallableReferenceAtom.getAtom(), eagerCallableReferenceAtom.getExpectedType(), null);
        Intrinsics.checkNotNullParameter(eagerCallableReferenceAtom, "eagerCallableReferenceAtom");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType
    public UnwrappedType getRevisedExpectedType() {
        return this.revisedExpectedType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedAtomWithRevisableExpectedType
    public void reviseExpectedType(KotlinTypeMarker expectedType) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        if (!(expectedType instanceof UnwrappedType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        this.revisedExpectedType = (UnwrappedType) expectedType;
    }
}
