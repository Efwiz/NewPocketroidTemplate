package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.description.EventOccurrencesRange;
import org.jetbrains.kotlin.contracts.description.KtCallsEffectDeclaration;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.contracts.ContractUtilsKt;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;

/* JADX INFO: compiled from: InvocationKindTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u000f\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u0007H\u0086\u0010¨\u0006\b"}, d2 = {"replaceLambdaArgumentInvocationKinds", "", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "unwrapAnonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InvocationKindTransformerKt {
    public static final FirAnonymousFunction unwrapAnonymousFunctionExpression(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        while (!(firExpression instanceof FirAnonymousFunctionExpression)) {
            if (!(firExpression instanceof FirWrappedArgumentExpression)) {
                return null;
            }
            firExpression = ((FirWrappedArgumentExpression) firExpression).getExpression();
        }
        return ((FirAnonymousFunctionExpression) firExpression).getAnonymousFunction();
    }

    public static final void replaceLambdaArgumentInvocationKinds(FirFunctionCall firFunctionCall, FirSession session) {
        LinkedHashMap<FirExpression, FirValueParameter> argumentMapping;
        FirValueParameter firValueParameter;
        Intrinsics.checkNotNullParameter(firFunctionCall, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirNamedReference calleeReference = firFunctionCall.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        if (firNamedReferenceWithCandidate == null || (argumentMapping = firNamedReferenceWithCandidate.getCandidate().getArgumentMapping()) == null) {
            return;
        }
        FirDeclaration fir = firNamedReferenceWithCandidate.getCandidateSymbol().getFir();
        FirSimpleFunction firSimpleFunction = fir instanceof FirSimpleFunction ? (FirSimpleFunction) fir : null;
        if (firSimpleFunction == null) {
            return;
        }
        boolean zIsInline = firSimpleFunction.getStatus().isInline();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List<FirEffectDeclaration> effects = ContractUtilsKt.getEffects(firSimpleFunction.getContractDescription());
        if (effects != null) {
            Iterator<T> it = effects.iterator();
            while (it.hasNext()) {
                KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> effect = ((FirEffectDeclaration) it.next()).getEffect();
                KtCallsEffectDeclaration ktCallsEffectDeclaration = effect instanceof KtCallsEffectDeclaration ? (KtCallsEffectDeclaration) effect : null;
                if (ktCallsEffectDeclaration != null && (firValueParameter = (FirValueParameter) CollectionsKt.getOrNull(firSimpleFunction.getValueParameters(), ktCallsEffectDeclaration.getValueParameterReference().getParameterIndex())) != null) {
                    linkedHashMap.put(firValueParameter, ktCallsEffectDeclaration.getKind());
                }
            }
        }
        if (!linkedHashMap.isEmpty() || zIsInline) {
            for (Map.Entry<FirExpression, FirValueParameter> entry : argumentMapping.entrySet()) {
                FirExpression key = entry.getKey();
                FirValueParameter value = entry.getValue();
                FirAnonymousFunction firAnonymousFunctionUnwrapAnonymousFunctionExpression = unwrapAnonymousFunctionExpression(key);
                if (firAnonymousFunctionUnwrapAnonymousFunctionExpression != null) {
                    EventOccurrencesRange eventOccurrencesRange = (EventOccurrencesRange) linkedHashMap.get(value);
                    if (eventOccurrencesRange == null) {
                        eventOccurrencesRange = EventOccurrencesRange.UNKNOWN;
                        if (!zIsInline || value.getIsNoinline() || value.getIsCrossinline() || !FunctionalTypeUtilsKt.isNonReflectFunctionType(FirTypeUtilsKt.getConeType(value.getReturnTypeRef()), session)) {
                            eventOccurrencesRange = null;
                        }
                        if (eventOccurrencesRange == null) {
                        }
                    }
                    firAnonymousFunctionUnwrapAnonymousFunctionExpression.replaceInvocationKind(eventOccurrencesRange);
                }
            }
        }
    }
}
