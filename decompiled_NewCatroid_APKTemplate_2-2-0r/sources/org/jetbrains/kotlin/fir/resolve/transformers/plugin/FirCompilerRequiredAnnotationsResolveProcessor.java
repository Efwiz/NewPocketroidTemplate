package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProvider;
import org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirCachingCompositeSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.transformers.FirGlobalResolveProcessor;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;

/* JADX INFO: compiled from: FirCompilerRequiredAnnotationsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\u0016\u0010\n\u001a\u00020\b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirCompilerRequiredAnnotationsResolveProcessor;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirGlobalResolveProcessor;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "afterPhase", "", "beforePhase", "process", "files", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCompilerRequiredAnnotationsResolveProcessor extends FirGlobalResolveProcessor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirCompilerRequiredAnnotationsResolveProcessor(FirSession session, ScopeSession scopeSession) {
        super(session, scopeSession, FirResolvePhase.COMPILER_REQUIRED_ANNOTATIONS);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirGlobalResolveProcessor
    public void process(Collection<? extends FirFile> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        FirCompilerRequiredAnnotationsResolveTransformer firCompilerRequiredAnnotationsResolveTransformer = new FirCompilerRequiredAnnotationsResolveTransformer(getSession(), getScopeSession(), new CompilerRequiredAnnotationsComputationSession());
        for (FirFile firFile : files) {
            try {
                FirTransformerUtilKt.transformSingle(firFile, firCompilerRequiredAnnotationsResolveTransformer, null);
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(firFile.getModuleData().getSession()).handleExceptionOnFileAnalysis(firFile, th);
                throw null;
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirResolveProcessor
    public void beforePhase() {
        super.beforePhase();
        FirSwitchableExtensionDeclarationsSymbolProvider generatedDeclarationsSymbolProvider = FirSwitchableExtensionDeclarationsSymbolProviderKt.getGeneratedDeclarationsSymbolProvider(getSession());
        if (generatedDeclarationsSymbolProvider != null) {
            generatedDeclarationsSymbolProvider.disable();
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirResolveProcessor
    public void afterPhase() {
        super.afterPhase();
        FirSwitchableExtensionDeclarationsSymbolProvider generatedDeclarationsSymbolProvider = FirSwitchableExtensionDeclarationsSymbolProviderKt.getGeneratedDeclarationsSymbolProvider(getSession());
        if (generatedDeclarationsSymbolProvider != null) {
            generatedDeclarationsSymbolProvider.enable();
        }
        FirSymbolProvider symbolProvider = FirSymbolProviderKt.getSymbolProvider(getSession());
        if (generatedDeclarationsSymbolProvider == null || !(symbolProvider instanceof FirCachingCompositeSymbolProvider)) {
            return;
        }
        getSession().register(Reflection.getOrCreateKotlinClass(FirSymbolProvider.class), ((FirCachingCompositeSymbolProvider) symbolProvider).createCopyWithCleanCaches());
    }
}
