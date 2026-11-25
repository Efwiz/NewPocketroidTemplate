package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolver;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirDummyCompilerLazyDeclarationResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001c\u0010\u0007\u001a\u00020\u00042\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\n\u001a\u00020\u0006H\u0016J\u001c\u0010\u000b\u001a\u00020\u00042\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\n\u001a\u00020\u0006H\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirDummyCompilerLazyDeclarationResolver;", "Lorg/jetbrains/kotlin/fir/symbols/FirLazyDeclarationResolver;", "()V", "finishResolvingPhase", "", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "lazyResolveToPhase", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "toPhase", "lazyResolveToPhaseWithCallableMembers", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "startResolvingPhase", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDummyCompilerLazyDeclarationResolver extends FirLazyDeclarationResolver {
    public static final FirDummyCompilerLazyDeclarationResolver INSTANCE = new FirDummyCompilerLazyDeclarationResolver();

    @Override // org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolver
    public void finishResolvingPhase(FirResolvePhase phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
    }

    @Override // org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolver
    public void lazyResolveToPhase(FirBasedSymbol<?> symbol, FirResolvePhase toPhase) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(toPhase, "toPhase");
    }

    @Override // org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolver
    public void lazyResolveToPhaseWithCallableMembers(FirClassSymbol<?> symbol, FirResolvePhase toPhase) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(toPhase, "toPhase");
    }

    @Override // org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolver
    public void startResolvingPhase(FirResolvePhase phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
    }

    private FirDummyCompilerLazyDeclarationResolver() {
    }
}
