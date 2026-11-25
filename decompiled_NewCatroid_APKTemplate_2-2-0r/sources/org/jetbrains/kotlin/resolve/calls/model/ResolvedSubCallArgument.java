package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedSubCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/SubKotlinCallArgument;", "resolveIndependently", "", "(Lorg/jetbrains/kotlin/resolve/calls/model/SubKotlinCallArgument;Z)V", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/SubKotlinCallArgument;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedSubCallArgument extends ResolvedAtom {
    private final SubKotlinCallArgument atom;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResolvedSubCallArgument(SubKotlinCallArgument atom, boolean z) {
        super(null);
        Intrinsics.checkNotNullParameter(atom, "atom");
        this.atom = atom;
        if (z) {
            setAnalyzedResults(CollectionsKt.emptyList());
        } else {
            setAnalyzedResults(CollectionsKt.listOf(getAtom().getCallResult()));
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    public SubKotlinCallArgument getAtom() {
        return this.atom;
    }
}
