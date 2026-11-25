package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.extensions.FirExtensionService;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer;

/* JADX INFO: compiled from: FirCompilerRequiredAnnotationsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b&\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J)\u0010\u0014\u001a\u0002H\u0015\"\b\b\u0000\u0010\u0015*\u00020\u00162\u0006\u0010\u0017\u001a\u0002H\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\u0019J\u001a\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020#2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0002H\u0016J\f\u0010$\u001a\u00020%*\u00020\u001bH\u0002R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirCompilerRequiredAnnotationsResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractPhaseTransformer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "computationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;)V", "annotationTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer;", "getAnnotationTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer;", "extensionService", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;", "getExtensionService", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;", "importTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirPartialImportResolveTransformer;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Void;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "transformRegularClass", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "transformTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "resolveAnnotations", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractFirCompilerRequiredAnnotationsResolveTransformer extends FirAbstractPhaseTransformer {
    private final FirExtensionService extensionService;
    private final FirPartialImportResolveTransformer importTransformer;
    private final FirSession session;

    public abstract AbstractFirSpecificAnnotationResolveTransformer getAnnotationTransformer();

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer
    public final FirSession getSession() {
        return this.session;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractFirCompilerRequiredAnnotationsResolveTransformer(FirSession session, CompilerRequiredAnnotationsComputationSession computationSession) {
        super(FirResolvePhase.COMPILER_REQUIRED_ANNOTATIONS);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(computationSession, "computationSession");
        this.session = session;
        this.importTransformer = new FirPartialImportResolveTransformer(session, computationSession);
        this.extensionService = FirExtensionServiceKt.getExtensionService(session);
    }

    public final FirExtensionService getExtensionService() {
        return this.extensionService;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, Void data) {
        Intrinsics.checkNotNullParameter(element, "element");
        throw new IllegalStateException("Should not be here");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(FirFile file, Void data) {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            checkSessionConsistency(file);
            resolveAnnotations(file);
            Unit unit = Unit.INSTANCE;
            return file;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformRegularClass(FirRegularClass regularClass, Void data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        return getAnnotationTransformer().transformRegularClass(regularClass, (Void) null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformTypeAlias(FirTypeAlias typeAlias, Void data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        return getAnnotationTransformer().transformTypeAlias(typeAlias, (Void) null);
    }

    private final void resolveAnnotations(FirFile firFile) {
        firFile.transformImports(this.importTransformer, null);
        firFile.transform(getAnnotationTransformer(), null);
    }
}
