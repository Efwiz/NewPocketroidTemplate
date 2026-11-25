package org.jetbrains.kotlin.fir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;

/* JADX INFO: compiled from: FirLazyDeclarationResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/FirLazyResolveContractViolationException;", "Ljava/lang/IllegalStateException;", "Lkotlin/IllegalStateException;", "currentPhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "requestedPhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirLazyResolveContractViolationException extends IllegalStateException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirLazyResolveContractViolationException(FirResolvePhase currentPhase, FirResolvePhase requestedPhase) {
        super(StringsKt.trimIndent("\n        `lazyResolveToPhase(" + requestedPhase + ")` cannot be called from a transformer with a phase " + currentPhase + ".\n        `lazyResolveToPhase` can be called only from a transformer with a phase which is strictly greater than a requested phase;\n         i.e., `lazyResolveToPhase(A)` may be only called from a lazy transformer with a phase B, where A < B. This is a contract of lazy resolve\n     "));
        Intrinsics.checkNotNullParameter(currentPhase, "currentPhase");
        Intrinsics.checkNotNullParameter(requestedPhase, "requestedPhase");
    }
}
