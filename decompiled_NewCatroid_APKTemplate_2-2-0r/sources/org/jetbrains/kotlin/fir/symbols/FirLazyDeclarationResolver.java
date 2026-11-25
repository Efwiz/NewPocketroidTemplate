package org.jetbrains.kotlin.fir.symbols;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirLazyDeclarationResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ%\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\f0\u000eH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H&J\u001c\u0010\u0013\u001a\u00020\n2\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\u0016\u001a\u00020\u0012H&J\u001c\u0010\u0017\u001a\u00020\n2\n\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u00182\u0006\u0010\u0016\u001a\u00020\u0012H&J\u0010\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H&R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/FirLazyDeclarationResolver;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "lazyResolveContractChecksEnabled", "", "getLazyResolveContractChecksEnabled", "()Z", "setLazyResolveContractChecksEnabled", "(Z)V", "disableLazyResolveContractChecks", "", "disableLazyResolveContractChecksInside", ExifInterface.GPS_DIRECTION_TRUE, "action", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "finishResolvingPhase", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "lazyResolveToPhase", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "toPhase", "lazyResolveToPhaseWithCallableMembers", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "startResolvingPhase", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirLazyDeclarationResolver implements FirSessionComponent {
    private boolean lazyResolveContractChecksEnabled = true;

    public abstract void finishResolvingPhase(FirResolvePhase phase);

    public abstract void lazyResolveToPhase(FirBasedSymbol<?> symbol, FirResolvePhase toPhase);

    public abstract void lazyResolveToPhaseWithCallableMembers(FirClassSymbol<?> symbol, FirResolvePhase toPhase);

    public abstract void startResolvingPhase(FirResolvePhase phase);

    public final boolean getLazyResolveContractChecksEnabled() {
        return this.lazyResolveContractChecksEnabled;
    }

    public final void setLazyResolveContractChecksEnabled(boolean z) {
        this.lazyResolveContractChecksEnabled = z;
    }

    public final void disableLazyResolveContractChecks() {
        this.lazyResolveContractChecksEnabled = false;
    }

    public final <T> T disableLazyResolveContractChecksInside(Function0<? extends T> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        boolean lazyResolveContractChecksEnabled = getLazyResolveContractChecksEnabled();
        setLazyResolveContractChecksEnabled(false);
        try {
            return action.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setLazyResolveContractChecksEnabled(lazyResolveContractChecksEnabled);
            InlineMarker.finallyEnd(1);
        }
    }
}
