package org.jetbrains.kotlin.fir.resolve.calls.tower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirPropertyAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConePropertyAsOperator;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0002\u001a8\u0010\u000e\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00062\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0002\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0011"}, d2 = {"fakeSourceForImplicitInvokeCallReceiver", "Lorg/jetbrains/kotlin/KtSourceElement;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "getFakeSourceForImplicitInvokeCallReceiver", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;)Lorg/jetbrains/kotlin/KtSourceElement;", "createExplicitReceiverForInvoke", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", CompilerOptions.INFO, "invokeBuiltinExtensionMode", "", "extensionReceiverExpression", "createExplicitReceiverForInvokeByCallable", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirInvokeResolveTowerExtensionKt {
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final FirExpression createExplicitReceiverForInvoke(BodyResolveComponents bodyResolveComponents, Candidate candidate, CallInfo callInfo, boolean z, FirExpression firExpression) {
        FirBasedSymbol<?> symbol = candidate.getSymbol();
        if (symbol instanceof FirCallableSymbol) {
            return createExplicitReceiverForInvokeByCallable(bodyResolveComponents, candidate, callInfo, z, firExpression, (FirCallableSymbol) symbol);
        }
        if (symbol instanceof FirRegularClassSymbol) {
            return ResolveUtilsKt.buildResolvedQualifierForClass(bodyResolveComponents, (FirClassLikeSymbol) symbol, (60 & 2) != 0 ? null : getFakeSourceForImplicitInvokeCallReceiver(callInfo), (60 & 4) != 0 ? CollectionsKt.emptyList() : null, (60 & 8) == 0 ? null : null, (60 & 16) != 0 ? CollectionsKt.emptyList() : null, (60 & 32) != 0 ? CollectionsKt.emptyList() : null);
        }
        if (symbol instanceof FirTypeAliasSymbol) {
            FirTypeAliasSymbol firTypeAliasSymbol = (FirTypeAliasSymbol) symbol;
            ConeKotlinType type = ((FirResolvedTypeRef) ((FirTypeAlias) firTypeAliasSymbol.getFir()).getExpandedTypeRef()).getType();
            if (type != null) {
                FirClassLikeSymbol<?> symbol2 = LookupTagUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) type, bodyResolveComponents.getSession(), null, 2, null).getLookupTag(), bodyResolveComponents.getSession());
                if (symbol2 == null) {
                    return null;
                }
                return ResolveUtilsKt.buildResolvedQualifierForClass(bodyResolveComponents, symbol2, (60 & 2) != 0 ? null : ((FirTypeAlias) firTypeAliasSymbol.getFir()).getSource(), (60 & 4) != 0 ? CollectionsKt.emptyList() : null, (60 & 8) == 0 ? null : null, (60 & 16) != 0 ? CollectionsKt.emptyList() : null, (60 & 32) != 0 ? CollectionsKt.emptyList() : null);
            }
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
        }
        throw new AssertionError();
    }

    private static final FirExpression createExplicitReceiverForInvokeByCallable(BodyResolveComponents bodyResolveComponents, Candidate candidate, CallInfo callInfo, boolean z, FirExpression firExpression, FirCallableSymbol<?> firCallableSymbol) {
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        firPropertyAccessExpressionBuilder.setCalleeReference(new FirNamedReferenceWithCandidate(null, firCallableSymbol.getCallableId().getCallableName(), candidate));
        firPropertyAccessExpressionBuilder.setDispatchReceiver(candidate.dispatchReceiverExpression());
        firPropertyAccessExpressionBuilder.setTypeRef(bodyResolveComponents.getReturnTypeCalculator().tryCalculateReturnType((FirCallableDeclaration) firCallableSymbol.getFir()));
        if (!z) {
            firPropertyAccessExpressionBuilder.setExtensionReceiver(firExpression);
            firPropertyAccessExpressionBuilder.setExplicitReceiver(callInfo.getExplicitReceiver());
        }
        if (candidate.getCurrentApplicability() == CandidateApplicability.K2_PROPERTY_AS_OPERATOR) {
            List<ConeDiagnostic> nonFatalDiagnostics = firPropertyAccessExpressionBuilder.getNonFatalDiagnostics();
            FirBasedSymbol<?> symbol = candidate.getSymbol();
            Intrinsics.checkNotNull(symbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol");
            nonFatalDiagnostics.add(new ConePropertyAsOperator((FirPropertySymbol) symbol));
        }
        firPropertyAccessExpressionBuilder.setSource(getFakeSourceForImplicitInvokeCallReceiver(callInfo));
        return ResolveUtilsKt.transformQualifiedAccessUsingSmartcastInfo(bodyResolveComponents, (FirPropertyAccessExpression) bodyResolveComponents.getCallCompleter().completeCall(firPropertyAccessExpressionBuilder.mo6809build(), ResolutionMode.ReceiverResolution.INSTANCE));
    }

    private static final KtSourceElement getFakeSourceForImplicitInvokeCallReceiver(CallInfo callInfo) {
        FirNamedReference calleeReference;
        KtSourceElement source;
        FirElement callSite = callInfo.getCallSite();
        FirFunctionCall firFunctionCall = callSite instanceof FirFunctionCall ? (FirFunctionCall) callSite : null;
        if (firFunctionCall == null || (calleeReference = firFunctionCall.getCalleeReference()) == null || (source = calleeReference.getSource()) == null) {
            return null;
        }
        return KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitInvokeCall.INSTANCE, 0, 0, 6, null);
    }
}
