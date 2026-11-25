package org.jetbrains.kotlin.resolve.calls.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0010\u001a\u00020\u00112\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00000\fH\u0014J\u0006\u0010\u0012\u001a\u00020\u0011R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u0004\u0018\u00010\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR.\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\f2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u0082\u0001\u0007\u0013\u0014\u0015\u0016\u0017\u0018\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "", "()V", "<set-?>", "", "analyzed", "getAnalyzed", "()Z", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionAtom;", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionAtom;", "", "subResolvedAtoms", "getSubResolvedAtoms", "()Ljava/util/List;", "setAnalyzedResults", "", "setEmptyAnalyzedResults", "Lorg/jetbrains/kotlin/resolve/calls/model/CallResolutionResult;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCollectionLiteralAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedExpressionAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedSubCallArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/StubResolvedAtom;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResolvedAtom {
    private boolean analyzed;
    private List<? extends ResolvedAtom> subResolvedAtoms;

    public /* synthetic */ ResolvedAtom(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ResolutionAtom getAtom();

    private ResolvedAtom() {
    }

    public final boolean getAnalyzed() {
        return this.analyzed;
    }

    public final List<ResolvedAtom> getSubResolvedAtoms() {
        return this.subResolvedAtoms;
    }

    protected void setAnalyzedResults(List<? extends ResolvedAtom> subResolvedAtoms) {
        Intrinsics.checkNotNullParameter(subResolvedAtoms, "subResolvedAtoms");
        this.analyzed = true;
        this.subResolvedAtoms = subResolvedAtoms;
    }

    public final void setEmptyAnalyzedResults() {
        setAnalyzedResults(CollectionsKt.emptyList());
    }
}
