package org.jetbrains.kotlin.fir.resolve.transformers.mpp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.FirTransformerBasedResolveProcessor;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;

/* JADX INFO: compiled from: FirExpectActualMatcherTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/mpp/FirExpectActualMatcherProcessor;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirTransformerBasedResolveProcessor;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", CompilerOptions.ENABLED, "", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "", "getTransformer", "()Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "processFile", "", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExpectActualMatcherProcessor extends FirTransformerBasedResolveProcessor {
    private final boolean enabled;
    private final FirTransformer transformer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirExpectActualMatcherProcessor(FirSession session, ScopeSession scopeSession) {
        super(session, scopeSession, FirResolvePhase.EXPECT_ACTUAL_MATCHING);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.enabled = FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).supportsFeature(LanguageFeature.MultiPlatformProjects);
        this.transformer = new FirExpectActualMatcherTransformer(session, scopeSession);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirTransformerBasedResolveProcessor
    public FirTransformer getTransformer() {
        return this.transformer;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirTransformerBasedResolveProcessor
    public void processFile(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (this.enabled) {
            super.processFile(file);
        }
    }
}
