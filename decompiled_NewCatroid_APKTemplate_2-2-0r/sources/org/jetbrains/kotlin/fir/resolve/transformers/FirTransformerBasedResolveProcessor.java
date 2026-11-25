package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;

/* JADX INFO: compiled from: FirResolveProcessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirTransformerBasedResolveProcessor;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirResolveProcessor;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "", "getTransformer", "()Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "processFile", "", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTransformerBasedResolveProcessor extends FirResolveProcessor {
    public abstract FirTransformer getTransformer();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirTransformerBasedResolveProcessor(FirSession session, ScopeSession scopeSession, FirResolvePhase firResolvePhase) {
        super(session, scopeSession, firResolvePhase, null);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
    }

    public void processFile(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        file.transform(getTransformer(), null);
    }
}
