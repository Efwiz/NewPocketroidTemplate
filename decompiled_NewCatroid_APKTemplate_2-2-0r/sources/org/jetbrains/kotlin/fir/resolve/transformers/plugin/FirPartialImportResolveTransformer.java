package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotationsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.FirImportResolveTransformer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirCompilerRequiredAnnotationsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001a\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\n\u001a\u00020\u000b*\u00020\t8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\f¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirPartialImportResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirImportResolveTransformer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "computationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;)V", "acceptableFqNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "isAcceptable", "", "(Lorg/jetbrains/kotlin/name/FqName;)Z", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "data", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirPartialImportResolveTransformer extends FirImportResolveTransformer {
    private final Set<FqName> acceptableFqNames;
    private final CompilerRequiredAnnotationsComputationSession computationSession;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirPartialImportResolveTransformer(FirSession session, CompilerRequiredAnnotationsComputationSession computationSession) {
        super(session, FirResolvePhase.COMPILER_REQUIRED_ANNOTATIONS);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(computationSession, "computationSession");
        this.computationSession = computationSession;
        this.acceptableFqNames = FirRegisteredPluginAnnotationsKt.getRegisteredPluginAnnotations(session).getAnnotations();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirImportResolveTransformer
    protected boolean isAcceptable(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        return this.acceptableFqNames.contains(fqName);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirImportResolveTransformer, org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(FirFile file, Object data) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (this.computationSession.importsAreResolved(file)) {
            return file;
        }
        FirFile firFileTransformFile = super.transformFile(file, data);
        this.computationSession.recordThatImportsAreResolved(file);
        return firFileTransformFile;
    }
}
