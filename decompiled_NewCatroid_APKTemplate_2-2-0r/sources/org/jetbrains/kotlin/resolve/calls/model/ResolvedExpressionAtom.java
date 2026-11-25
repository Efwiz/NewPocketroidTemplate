package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedExpressionAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;)V", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedExpressionAtom extends ResolvedAtom {
    private final ExpressionKotlinCallArgument atom;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResolvedExpressionAtom(ExpressionKotlinCallArgument atom) {
        super(null);
        Intrinsics.checkNotNullParameter(atom, "atom");
        this.atom = atom;
        setAnalyzedResults(CollectionsKt.emptyList());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    public ExpressionKotlinCallArgument getAtom() {
        return this.atom;
    }
}
