package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;

/* JADX INFO: compiled from: FirTotalResolveProcessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirTotalResolveProcessor;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "processors", "", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirResolveProcessor;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "process", "", "files", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTotalResolveProcessor {
    private final List<FirResolveProcessor> processors;
    private final ScopeSession scopeSession;
    private final FirSession session;

    public FirTotalResolveProcessor(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        ScopeSession scopeSession = new ScopeSession();
        this.scopeSession = scopeSession;
        this.processors = FirTotalResolveProcessorKt.createAllCompilerResolveProcessors(session, scopeSession);
    }

    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    public final void process(List<? extends FirFile> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        for (FirResolveProcessor firResolveProcessor : this.processors) {
            firResolveProcessor.beforePhase();
            try {
                if (firResolveProcessor instanceof FirTransformerBasedResolveProcessor) {
                    for (FirFile firFile : files) {
                        try {
                            ((FirTransformerBasedResolveProcessor) firResolveProcessor).processFile(firFile);
                            Unit unit = Unit.INSTANCE;
                        } catch (Throwable th) {
                            UtilsKt.getExceptionHandler(firFile.getModuleData().getSession()).handleExceptionOnFileAnalysis(firFile, th);
                            throw null;
                        }
                    }
                } else if (firResolveProcessor instanceof FirGlobalResolveProcessor) {
                    ((FirGlobalResolveProcessor) firResolveProcessor).process(files);
                }
                firResolveProcessor.afterPhase();
            } catch (Throwable th2) {
                firResolveProcessor.afterPhase();
                throw th2;
            }
        }
    }
}
