package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.diagnostics.ConeIntermediateDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirReceivers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ExpressionReceiverValue;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverValue;", "receiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getReceiverExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpressionReceiverValue extends ReceiverValue {
    private final FirExpression receiverExpression;

    public ExpressionReceiverValue(FirExpression receiverExpression) {
        Intrinsics.checkNotNullParameter(receiverExpression, "receiverExpression");
        this.receiverExpression = receiverExpression;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ReceiverValue
    public FirExpression getReceiverExpression() {
        return this.receiverExpression;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ReceiverValue
    public ConeKotlinType getType() {
        FirTypeRef typeRef = getReceiverExpression().getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
        return coneKotlinType == null ? new ConeErrorType(new ConeIntermediateDiagnostic("No type calculated for: " + UtilsKt.renderWithType(getReceiverExpression())), false, null, null, 14, null) : coneKotlinType;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ReceiverValue
    public FirTypeScope scope(FirSession useSiteSession, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        FirAnnotationContainer receiverExpression = getReceiverExpression();
        if (receiverExpression instanceof FirCheckNotNullCall) {
            receiverExpression = (FirExpression) CollectionsKt.firstOrNull((List) ((FirCall) receiverExpression).getArgumentList().getArguments());
        }
        if (receiverExpression instanceof FirSmartCastExpression) {
            return ScopeUtilsKt.smartcastScope((FirSmartCastExpression) receiverExpression, useSiteSession, scopeSession, FirResolvePhase.STATUS);
        }
        return ScopeUtilsKt.scope(getType(), useSiteSession, scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
    }
}
