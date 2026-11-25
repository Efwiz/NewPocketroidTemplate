package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirResolvedErrorReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;

/* JADX INFO: compiled from: FirArrayOfCallTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0002¨\u0006\u0003"}, d2 = {"getOriginalFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirArrayOfCallTransformerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final FirCallableDeclaration getOriginalFunction(FirFunctionCall firFunctionCall) {
        FirBasedSymbol<?> candidateSymbol;
        FirNamedReference calleeReference = firFunctionCall.getCalleeReference();
        if (calleeReference instanceof FirResolvedErrorReference) {
            candidateSymbol = ((FirResolvedErrorReference) calleeReference).getResolvedSymbol();
        } else if (calleeReference instanceof FirResolvedNamedReference) {
            candidateSymbol = ((FirResolvedNamedReference) calleeReference).getResolvedSymbol();
        } else {
            candidateSymbol = calleeReference instanceof FirNamedReferenceWithCandidate ? ((FirNamedReferenceWithCandidate) calleeReference).getCandidateSymbol() : null;
        }
        FirDeclaration fir = candidateSymbol != null ? candidateSymbol.getFir() : null;
        if (fir instanceof FirCallableDeclaration) {
            return (FirCallableDeclaration) fir;
        }
        return null;
    }
}
