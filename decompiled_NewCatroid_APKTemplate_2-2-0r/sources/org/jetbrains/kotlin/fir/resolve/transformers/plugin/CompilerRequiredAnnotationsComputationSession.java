package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.DesignationState;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirCompilerRequiredAnnotationsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\tJ\u000e\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0007J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u0005J\u000e\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\u0007J\u0018\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0016\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;", "", "()V", "declarationsWithResolvedAnnotations", "", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "filesWithResolvedImports", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "useCacheForImportScope", "", "getUseCacheForImportScope", "()Z", "annotationsAreResolved", "declaration", "treatNonSourceDeclarationsAsResolved", "importsAreResolved", "file", "recordThatAnnotationsAreResolved", "", "recordThatImportsAreResolved", "resolveAnnotationSymbol", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "resolveAnnotationsOnAnnotationIfNeeded", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CompilerRequiredAnnotationsComputationSession {
    private final Set<FirFile> filesWithResolvedImports = new LinkedHashSet();
    private final Set<FirAnnotationContainer> declarationsWithResolvedAnnotations = new LinkedHashSet();

    public boolean getUseCacheForImportScope() {
        return false;
    }

    public final boolean importsAreResolved(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return this.filesWithResolvedImports.contains(file);
    }

    public final void recordThatImportsAreResolved(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (!this.filesWithResolvedImports.add(file)) {
            throw new IllegalStateException("Imports are resolved twice".toString());
        }
    }

    public final boolean annotationsAreResolved(FirAnnotationContainer declaration, boolean treatNonSourceDeclarationsAsResolved) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof FirFile) {
            return false;
        }
        if (treatNonSourceDeclarationsAsResolved && (declaration instanceof FirDeclaration) && !Intrinsics.areEqual(((FirDeclaration) declaration).getOrigin(), FirDeclarationOrigin.Source.INSTANCE)) {
            return true;
        }
        return this.declarationsWithResolvedAnnotations.contains(declaration);
    }

    public final void recordThatAnnotationsAreResolved(FirAnnotationContainer declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (!this.declarationsWithResolvedAnnotations.add(declaration)) {
            throw new IllegalStateException("Annotations are resolved twice".toString());
        }
    }

    public final void resolveAnnotationsOnAnnotationIfNeeded(FirRegularClassSymbol symbol, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        if (annotationsAreResolved((FirRegularClass) symbol.getFir(), true)) {
            return;
        }
        resolveAnnotationSymbol(symbol, scopeSession);
    }

    public void resolveAnnotationSymbol(FirRegularClassSymbol symbol, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        DesignationState designationStateCreate = DesignationState.INSTANCE.create(symbol, MapsKt.emptyMap(), true);
        if (designationStateCreate == null) {
            return;
        }
        FirTransformerUtilKt.transformSingle(designationStateCreate.getFirstDeclaration(), new FirDesignatedCompilerRequiredAnnotationsResolveTransformer(designationStateCreate.getFirstDeclaration().getModuleData().getSession(), scopeSession, this, designationStateCreate), null);
    }
}
