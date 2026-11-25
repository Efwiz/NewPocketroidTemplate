package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.scopes.FirScope;

/* JADX INFO: compiled from: FirStatusResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rJ\u001a\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016J\f\u0010\u0014\u001a\u00020\u0015*\u00020\u0016H\u0014J\f\u0010\u0017\u001a\u00020\u0015*\u00020\u0016H\u0014¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirStatusResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/AbstractFirStatusResolveTransformer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "statusComputationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/StatusComputationSession;", "designationMapForLocalClasses", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "scopeForLocalClass", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/StatusComputationSession;Ljava/util/Map;Lorg/jetbrains/kotlin/fir/scopes/FirScope;)V", "transformClassContent", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "data", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "needResolveMembers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "needResolveNestedClassifiers", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirStatusResolveTransformer extends AbstractFirStatusResolveTransformer {
    @Override // org.jetbrains.kotlin.fir.resolve.transformers.AbstractFirStatusResolveTransformer
    protected boolean needResolveNestedClassifiers(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return true;
    }

    public /* synthetic */ FirStatusResolveTransformer(FirSession firSession, ScopeSession scopeSession, StatusComputationSession statusComputationSession, Map map, FirScope firScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession, statusComputationSession, (i & 8) != 0 ? MapsKt.emptyMap() : map, (i & 16) != 0 ? null : firScope);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirStatusResolveTransformer(FirSession session, ScopeSession scopeSession, StatusComputationSession statusComputationSession, Map<FirClassLikeDeclaration, ? extends FirClassLikeDeclaration> designationMapForLocalClasses, FirScope firScope) {
        super(session, scopeSession, statusComputationSession, designationMapForLocalClasses, firScope);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(statusComputationSession, "statusComputationSession");
        Intrinsics.checkNotNullParameter(designationMapForLocalClasses, "designationMapForLocalClasses");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.AbstractFirStatusResolveTransformer
    protected boolean needResolveMembers(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return ((firDeclaration instanceof FirRegularClass) && getStatusComputationSession().get((FirClass) firDeclaration) == StatusComputationSession.StatusComputationStatus.Computed) ? false : true;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.AbstractFirStatusResolveTransformer
    public FirStatement transformClassContent(FirClass firClass, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        StatusComputationSession.StatusComputationStatus statusComputationStatusStartComputing = getStatusComputationSession().startComputing(firClass);
        forceResolveStatusesOfSupertypes(firClass);
        if (statusComputationStatusStartComputing != StatusComputationSession.StatusComputationStatus.Computed) {
            transformClassStatus(firClass);
            transformValueClassRepresentation(firClass);
        }
        FirStatement firStatementTransformClass = transformClass(firClass, data);
        getStatusComputationSession().endComputing(firClass);
        return firStatementTransformClass;
    }
}
