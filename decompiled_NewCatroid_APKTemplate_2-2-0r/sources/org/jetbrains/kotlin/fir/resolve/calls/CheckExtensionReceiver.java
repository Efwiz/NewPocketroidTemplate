package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.inference.ConstraintSystemCompleterKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.SimpleConstraintSystemConstraintPosition;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\rJ<\u0010\u000e\u001a\u00020\u00042\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0082@¢\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\u0004\u0018\u00010\u0013*\u00020\u0006H\u0002¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckExtensionReceiver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolveExtensionReceiver", "receivers", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ReceiverDescription;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getExpectedReceiverType", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckExtensionReceiver extends ResolutionStage {
    public static final CheckExtensionReceiver INSTANCE = new CheckExtensionReceiver();

    private CheckExtensionReceiver() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        Object objYield;
        Object objYield2;
        ConeKotlinType expectedReceiverType = getExpectedReceiverType(candidate);
        if (expectedReceiverType == null) {
            return Unit.INSTANCE;
        }
        ConeKotlinType coneKotlinTypeSubstituteOrSelf = candidate.getSubstitutor().substituteOrSelf(expectedReceiverType.getType());
        if (candidate.getGivenExtensionReceiverOptions().isEmpty()) {
            return Unit.INSTANCE;
        }
        List<FirExpression> givenExtensionReceiverOptions = candidate.getGivenExtensionReceiverOptions();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(givenExtensionReceiverOptions, 10));
        Iterator<T> it = givenExtensionReceiverOptions.iterator();
        while (it.hasNext()) {
            arrayList.add(ResolutionStagesKt.prepareReceivers(candidate, (FirExpression) it.next(), coneKotlinTypeSubstituteOrSelf, resolutionContext));
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.size() == 1) {
            Object objResolveExtensionReceiver = resolveExtensionReceiver(arrayList2, candidate, coneKotlinTypeSubstituteOrSelf, checkerSink, resolutionContext, continuation);
            return objResolveExtensionReceiver == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objResolveExtensionReceiver : Unit.INSTANCE;
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            if (ConstraintSystemBuilderKt.isSubtypeConstraintCompatible(candidate.getSystem(), ((ReceiverDescription) obj).getType(), coneKotlinTypeSubstituteOrSelf, SimpleConstraintSystemConstraintPosition.INSTANCE)) {
                arrayList3.add(obj);
            }
        }
        ArrayList arrayList4 = arrayList3;
        int size = arrayList4.size();
        if (size == 0) {
            checkerSink.reportDiagnostic(new InapplicableWrongReceiver(null, null, 3, null));
            return (checkerSink.getNeedYielding() && (objYield = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield : Unit.INSTANCE;
        }
        if (size == 1) {
            Object objResolveExtensionReceiver2 = resolveExtensionReceiver(arrayList4, candidate, coneKotlinTypeSubstituteOrSelf, checkerSink, resolutionContext, continuation);
            return objResolveExtensionReceiver2 == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objResolveExtensionReceiver2 : Unit.INSTANCE;
        }
        checkerSink.reportDiagnostic(new MultipleContextReceiversApplicableForExtensionReceivers());
        return (checkerSink.getNeedYielding() && (objYield2 = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield2 : Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object resolveExtensionReceiver(List<ReceiverDescription> list, Candidate candidate, ConeKotlinType coneKotlinType, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        Object objYield;
        ReceiverDescription receiverDescription = (ReceiverDescription) CollectionsKt.single((List) list);
        ArgumentsKt.resolvePlainArgumentType(candidate, ConstraintSystemCompleterKt.getCsBuilder(candidate), receiverDescription.getExpression(), receiverDescription.getType(), coneKotlinType, checkerSink, resolutionContext, true, false, (256 & 256) != 0 ? false : false);
        return (checkerSink.getNeedYielding() && (objYield = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield : Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ConeKotlinType getExpectedReceiverType(Candidate candidate) {
        FirReceiverParameter receiverParameter;
        FirTypeRef typeRef;
        FirBasedSymbol<?> symbol = candidate.getSymbol();
        FirCallableSymbol firCallableSymbol = symbol instanceof FirCallableSymbol ? (FirCallableSymbol) symbol : null;
        if (firCallableSymbol == null || (receiverParameter = ((FirCallableDeclaration) firCallableSymbol.getFir()).getReceiverParameter()) == null || (typeRef = receiverParameter.getTypeRef()) == null) {
            return null;
        }
        return FirTypeUtilsKt.getConeType(typeRef);
    }
}
