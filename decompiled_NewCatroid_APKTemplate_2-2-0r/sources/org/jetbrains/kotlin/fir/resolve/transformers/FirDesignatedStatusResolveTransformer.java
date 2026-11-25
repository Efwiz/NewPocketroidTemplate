package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;

/* JADX INFO: compiled from: FirStatusResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\u001a\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0018H\u0014J\f\u0010\u0019\u001a\u00020\u0017*\u00020\u0018H\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirDesignatedStatusResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/AbstractFirStatusResolveTransformer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "designation", "Lorg/jetbrains/kotlin/fir/resolve/transformers/DesignationState;", "statusComputationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/StatusComputationSession;", "designationMapForLocalClasses", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "scopeForLocalClass", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/DesignationState;Lorg/jetbrains/kotlin/fir/resolve/transformers/StatusComputationSession;Ljava/util/Map;Lorg/jetbrains/kotlin/fir/scopes/FirScope;)V", "transformClassContent", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "data", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "needResolveMembers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "needResolveNestedClassifiers", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirDesignatedStatusResolveTransformer extends AbstractFirStatusResolveTransformer {
    private final DesignationState designation;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirDesignatedStatusResolveTransformer(FirSession session, ScopeSession scopeSession, DesignationState designation, StatusComputationSession statusComputationSession, Map<FirClassLikeDeclaration, ? extends FirClassLikeDeclaration> designationMapForLocalClasses, FirScope firScope) {
        super(session, scopeSession, statusComputationSession, designationMapForLocalClasses, firScope);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(designation, "designation");
        Intrinsics.checkNotNullParameter(statusComputationSession, "statusComputationSession");
        Intrinsics.checkNotNullParameter(designationMapForLocalClasses, "designationMapForLocalClasses");
        this.designation = designation;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.AbstractFirStatusResolveTransformer
    protected boolean needResolveMembers(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return this.designation.getClassLocated();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.AbstractFirStatusResolveTransformer
    protected boolean needResolveNestedClassifiers(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return !this.designation.getClassLocated();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.AbstractFirStatusResolveTransformer
    public FirStatement transformClassContent(FirClass firClass, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        FirSession session = getSession();
        try {
            if (this.designation.shouldSkipClass(firClass)) {
                return firClass;
            }
            FirLazyDeclarationResolverKt.lazyResolveToPhase(firClass.getSymbol(), FirResolvePhase.TYPES);
            boolean classLocated = this.designation.getClassLocated();
            if (classLocated) {
                Intrinsics.areEqual(firClass, this.designation.getTargetClass());
                StatusComputationSession.StatusComputationStatus statusComputationStatusStartComputing = getStatusComputationSession().startComputing(firClass);
                forceResolveStatusesOfSupertypes(firClass);
                if (statusComputationStatusStartComputing != StatusComputationSession.StatusComputationStatus.Computed) {
                    firClass.transformStatus((FirTransformer<? super FirResolvedDeclarationStatus>) this, getStatusResolver().resolveStatus(firClass, getContainingClass(), false));
                }
            } else if (!(firClass.getStatus() instanceof FirResolvedDeclarationStatus)) {
                firClass.transformStatus((FirTransformer<? super FirResolvedDeclarationStatus>) this, getStatusResolver().resolveStatus(firClass, getContainingClass(), false));
                getStatusComputationSession().computeOnlyClassStatus(firClass);
            }
            FirStatement firStatementTransformClass = transformClass(firClass, data);
            if (classLocated) {
                getStatusComputationSession().endComputing(firClass);
            }
            return firStatementTransformClass;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(firClass, th);
            throw null;
        }
    }
}
