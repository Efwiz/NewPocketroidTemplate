package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer;

/* JADX INFO: compiled from: FirSupertypesResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u001d\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001a\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0002J)\u0010\u001b\u001a\u0002H\u001c\"\b\b\u0000\u0010\u001c*\u00020\u001d2\u0006\u0010\u001e\u001a\u0002H\u001c2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\u001fJ\u001a\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010#\u001a\u00020\u00142\u0006\u0010$\u001a\u00020%2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010&\u001a\u00020\u00142\u0006\u0010'\u001a\u00020(2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirApplySupertypesTransformer;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultTransformer;", "", "supertypeComputationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationSession;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "applyResolvedSupertypesToClass", "", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getResolvedSupertypeRefs", "", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "classLikeDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "transformAnonymousObject", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "data", "transformDeclarationContent", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "declaration", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "transformRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "transformTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirApplySupertypesTransformer extends FirDefaultTransformer<Object> {
    private final ScopeSession scopeSession;
    private final FirSession session;
    private final SupertypeComputationSession supertypeComputationSession;

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, Object data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return element;
    }

    public FirApplySupertypesTransformer(SupertypeComputationSession supertypeComputationSession, FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(supertypeComputationSession, "supertypeComputationSession");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.supertypeComputationSession = supertypeComputationSession;
        this.session = session;
        this.scopeSession = scopeSession;
    }

    private final FirDeclaration transformDeclarationContent(FirDeclaration declaration, Object data) {
        FirElement firElementTransformChildren = declaration.transformChildren(this, data);
        Intrinsics.checkNotNull(firElementTransformChildren, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirDeclaration");
        return (FirDeclaration) firElementTransformChildren;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(FirFile file, Object data) {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            FirDeclaration firDeclarationTransformDeclarationContent = transformDeclarationContent(file, null);
            Intrinsics.checkNotNull(firDeclarationTransformDeclarationContent, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirFile");
            return (FirFile) firDeclarationTransformDeclarationContent;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformRegularClass(FirRegularClass regularClass, Object data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        applyResolvedSupertypesToClass(regularClass);
        FirDeclaration firDeclarationTransformDeclarationContent = transformDeclarationContent(regularClass, null);
        Intrinsics.checkNotNull(firDeclarationTransformDeclarationContent, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirRegularClass");
        return (FirRegularClass) firDeclarationTransformDeclarationContent;
    }

    private final void applyResolvedSupertypesToClass(FirClass firClass) {
        List<FirTypeRef> superTypeRefs = firClass.getSuperTypeRefs();
        if (!(superTypeRefs instanceof Collection) || !superTypeRefs.isEmpty()) {
            for (FirTypeRef firTypeRef : superTypeRefs) {
                if (!(firTypeRef instanceof FirResolvedTypeRef) || (firTypeRef instanceof FirImplicitBuiltinTypeRef)) {
                    firClass.replaceSuperTypeRefs(getResolvedSupertypeRefs(firClass));
                    break;
                }
            }
        }
        PlatformSupertypeUpdater platformSupertypeUpdater = PlatformSupertypeUpdaterKt.getPlatformSupertypeUpdater(this.session);
        if (platformSupertypeUpdater != null) {
            platformSupertypeUpdater.updateSupertypesIfNeeded(firClass, this.scopeSession);
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousObject(FirAnonymousObject anonymousObject, Object data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        applyResolvedSupertypesToClass(anonymousObject);
        FirElement firElementTransformChildren = anonymousObject.transformChildren(this, data);
        Intrinsics.checkNotNull(firElementTransformChildren, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirAnonymousObject");
        return (FirAnonymousObject) firElementTransformChildren;
    }

    private final List<FirResolvedTypeRef> getResolvedSupertypeRefs(FirClassLikeDeclaration classLikeDeclaration) {
        SupertypeComputationStatus supertypesComputationStatus = this.supertypeComputationSession.getSupertypesComputationStatus(classLikeDeclaration);
        if (!(supertypesComputationStatus instanceof SupertypeComputationStatus.Computed)) {
            throw new IllegalArgumentException(("Unexpected status at FirApplySupertypesTransformer: " + supertypesComputationStatus + " for " + classLikeDeclaration.getSymbol().getClassId()).toString());
        }
        return ((SupertypeComputationStatus.Computed) supertypesComputationStatus).getSupertypeRefs();
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformTypeAlias(FirTypeAlias typeAlias, Object data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        if (typeAlias.getExpandedTypeRef() instanceof FirResolvedTypeRef) {
            return typeAlias;
        }
        List<FirResolvedTypeRef> resolvedSupertypeRefs = getResolvedSupertypeRefs(typeAlias);
        resolvedSupertypeRefs.size();
        typeAlias.replaceExpandedTypeRef(resolvedSupertypeRefs.get(0));
        return typeAlias;
    }
}
