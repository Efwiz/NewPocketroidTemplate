package org.jetbrains.kotlin.fir.resolve.calls;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.references.FirNamedReferenceWithCandidateBase;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirNamedReferenceWithCandidate.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ5\u0010\u0016\u001a\u00020\u0017\"\u0004\b\u0000\u0010\u0018\"\u0004\b\u0001\u0010\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u00190\u001b2\u0006\u0010\u001c\u001a\u0002H\u0019H\u0016¢\u0006\u0002\u0010\u001dJ)\u0010\u001e\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u00192\f\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00190!2\u0006\u0010\u001c\u001a\u0002H\u0019H\u0016¢\u0006\u0002\u0010\"R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0018\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/FirNamedReferenceWithCandidate;", "Lorg/jetbrains/kotlin/fir/references/FirNamedReferenceWithCandidateBase;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "name", "Lorg/jetbrains/kotlin/name/Name;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;)V", "getCandidate", "()Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "candidateSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getCandidateSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "isError", "", "()Z", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "transformChildren", "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirNamedReferenceWithCandidate extends FirNamedReferenceWithCandidateBase {
    private final Candidate candidate;
    private final Name name;
    private final KtSourceElement source;

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    public boolean isError() {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirNamedReferenceWithCandidateBase, org.jetbrains.kotlin.fir.references.FirNamedReference, org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirNamedReferenceWithCandidateBase, org.jetbrains.kotlin.fir.references.FirNamedReference
    public Name getName() {
        return this.name;
    }

    public final Candidate getCandidate() {
        return this.candidate;
    }

    public FirNamedReferenceWithCandidate(KtSourceElement ktSourceElement, Name name2, Candidate candidate) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        this.source = ktSourceElement;
        this.name = name2;
        this.candidate = candidate;
    }

    @Override // org.jetbrains.kotlin.fir.references.FirNamedReferenceWithCandidateBase
    public FirBasedSymbol<?> getCandidateSymbol() {
        return this.candidate.getSymbol();
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirElement transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }
}
