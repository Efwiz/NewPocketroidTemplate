package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirUnstableSmartcastTypeScope;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.InferenceUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.types.AbstractNullabilityChecker;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckDispatchReceiver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckDispatchReceiver extends ResolutionStage {
    public static final CheckDispatchReceiver INSTANCE = new CheckDispatchReceiver();

    private CheckDispatchReceiver() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        Object objYield;
        Object objYield2;
        ConeSimpleKotlinType dispatchReceiverType;
        FirExpression explicitReceiver = callInfo.getExplicitReceiver();
        ConeKotlinType coneType2 = null;
        if (ResolutionStagesKt.isSuperCall(explicitReceiver)) {
            FirDeclaration fir = candidate.getSymbol().getFir();
            FirMemberDeclaration firMemberDeclaration = fir instanceof FirMemberDeclaration ? (FirMemberDeclaration) fir : null;
            if ((firMemberDeclaration != null ? firMemberDeclaration.getStatus().getModality() : null) == Modality.ABSTRACT) {
                checkerSink.reportDiagnostic(ResolvedWithLowPriority.INSTANCE);
            }
        }
        FirExpression dispatchReceiver = candidate.getDispatchReceiver();
        if (dispatchReceiver == null || (typeRef = dispatchReceiver.getTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(typeRef)) == null) {
            return Unit.INSTANCE;
        }
        boolean zIsSubtypeOfAny = AbstractNullabilityChecker.INSTANCE.isSubtypeOfAny(TypeComponentsKt.getTypeContext(resolutionContext.getSession()), coneType);
        FirScope originScope = candidate.getOriginScope();
        FirUnstableSmartcastTypeScope firUnstableSmartcastTypeScope = originScope instanceof FirUnstableSmartcastTypeScope ? (FirUnstableSmartcastTypeScope) originScope : null;
        boolean z = false;
        if (firUnstableSmartcastTypeScope != null && firUnstableSmartcastTypeScope.isSymbolFromUnstableSmartcast(candidate.getSymbol())) {
            z = true;
        }
        boolean z2 = explicitReceiver instanceof FirCheckNotNullCall;
        Object obj = explicitReceiver;
        if (z2) {
            obj = (FirExpression) CollectionsKt.first((List) ((FirCall) explicitReceiver).getArgumentList().getArguments());
        }
        FirSmartCastExpression firSmartCastExpression = obj instanceof FirSmartCastExpression ? (FirSmartCastExpression) obj : null;
        if (firSmartCastExpression == null || firSmartCastExpression.isStable() || (!z && (zIsSubtypeOfAny || FirTypeUtilsKt.getCanBeNull(firSmartCastExpression.getSmartcastType())))) {
            if (zIsSubtypeOfAny) {
                return Unit.INSTANCE;
            }
            checkerSink.reportDiagnostic(new UnsafeCall(coneType));
            return (checkerSink.getNeedYielding() && (objYield = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield : Unit.INSTANCE;
        }
        FirBasedSymbol<?> symbol = candidate.getSymbol();
        FirCallableSymbol firCallableSymbol = symbol instanceof FirCallableSymbol ? (FirCallableSymbol) symbol : null;
        if (firCallableSymbol != null && (dispatchReceiverType = firCallableSymbol.getDispatchReceiverType()) != null) {
            ConeSimpleKotlinType coneSimpleKotlinType = dispatchReceiverType;
            ConeKotlinType coneKotlinTypeApproximateToSuperType = TypeComponentsKt.getTypeApproximator(resolutionContext.getSession()).approximateToSuperType((ConeKotlinType) coneSimpleKotlinType, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.FinalApproximationAfterResolutionAndInference.INSTANCE);
            coneType2 = coneKotlinTypeApproximateToSuperType == null ? coneSimpleKotlinType : coneKotlinTypeApproximateToSuperType;
        }
        if (coneType2 == null) {
            coneType2 = FirTypeUtilsKt.getConeType(firSmartCastExpression.getSmartcastType());
        }
        checkerSink.reportDiagnostic(new UnstableSmartCast(firSmartCastExpression, coneType2, InferenceUtilsKt.isTypeMismatchDueToNullability(TypeComponentsKt.getTypeContext(resolutionContext.getSession()), FirTypeUtilsKt.getConeType(firSmartCastExpression.getOriginalExpression().getTypeRef()), coneType2)));
        return (checkerSink.getNeedYielding() && (objYield2 = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield2 : Unit.INSTANCE;
    }
}
