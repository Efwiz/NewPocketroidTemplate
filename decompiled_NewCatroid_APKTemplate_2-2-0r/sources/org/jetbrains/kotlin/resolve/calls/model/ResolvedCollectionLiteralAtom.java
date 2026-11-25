package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCollectionLiteralAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/CollectionLiteralKotlinCallArgument;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CollectionLiteralKotlinCallArgument;Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/CollectionLiteralKotlinCallArgument;", "getExpectedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedCollectionLiteralAtom extends ResolvedAtom {
    private final CollectionLiteralKotlinCallArgument atom;
    private final UnwrappedType expectedType;

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    public CollectionLiteralKotlinCallArgument getAtom() {
        return this.atom;
    }

    public final UnwrappedType getExpectedType() {
        return this.expectedType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResolvedCollectionLiteralAtom(CollectionLiteralKotlinCallArgument atom, UnwrappedType unwrappedType) {
        super(null);
        Intrinsics.checkNotNullParameter(atom, "atom");
        this.atom = atom;
        this.expectedType = unwrappedType;
        setAnalyzedResults(CollectionsKt.emptyList());
    }
}
