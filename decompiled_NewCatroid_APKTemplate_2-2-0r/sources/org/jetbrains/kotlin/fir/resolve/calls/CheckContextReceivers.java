package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataElement;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.SimpleConstraintSystemConstraintPosition;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckContextReceivers;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckContextReceivers extends ResolutionStage {
    public static final CheckContextReceivers INSTANCE = new CheckContextReceivers();

    private CheckContextReceivers() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        FirCallableDeclaration firCallableDeclaration;
        List<FirContextReceiver> contextReceivers;
        ArrayList arrayListListOf;
        FirExpression receiverExpression;
        FirBasedSymbol<?> symbol = candidate.getSymbol();
        FirCallableSymbol firCallableSymbol = symbol instanceof FirCallableSymbol ? (FirCallableSymbol) symbol : null;
        if (firCallableSymbol != null && (firCallableDeclaration = (FirCallableDeclaration) firCallableSymbol.getFir()) != null && (contextReceivers = firCallableDeclaration.getContextReceivers()) != null) {
            List<FirContextReceiver> list = contextReceivers;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(candidate.getSubstitutor().substituteOrSelf(FirTypeUtilsKt.getConeType(((FirContextReceiver) it.next()).getTypeRef())));
            }
            ArrayList<ConeKotlinType> arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                arrayList2 = null;
            }
            if (arrayList2 != null) {
                if (!FirLanguageSettingsComponentKt.getLanguageVersionSettings(resolutionContext.getSession()).supportsFeature(LanguageFeature.ContextReceivers)) {
                    checkerSink.reportDiagnostic(UnsupportedContextualDeclarationCall.INSTANCE);
                    return Unit.INSTANCE;
                }
                List<FirTowerDataElement> listAsReversed = CollectionsKt.asReversed(resolutionContext.getBodyResolveContext().getTowerDataContext().getTowerDataElements());
                ArrayList arrayList3 = new ArrayList();
                for (FirTowerDataElement firTowerDataElement : listAsReversed) {
                    ImplicitReceiverValue<?> implicitReceiver = firTowerDataElement.getImplicitReceiver();
                    if (implicitReceiver == null || (receiverExpression = implicitReceiver.getReceiverExpression()) == null || (arrayListListOf = CollectionsKt.listOf(receiverExpression)) == null) {
                        List<ContextReceiverValue<?>> contextReceiverGroup = firTowerDataElement.getContextReceiverGroup();
                        if (contextReceiverGroup != null) {
                            List<ContextReceiverValue<?>> list2 = contextReceiverGroup;
                            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                            Iterator<T> it2 = list2.iterator();
                            while (it2.hasNext()) {
                                arrayList4.add(((ContextReceiverValue) it2.next()).getReceiverExpression());
                            }
                            arrayListListOf = arrayList4;
                        } else {
                            arrayListListOf = null;
                        }
                    }
                    if (arrayListListOf != null) {
                        arrayList3.add(arrayListListOf);
                    }
                }
                ArrayList arrayList5 = arrayList3;
                ArrayList arrayList6 = new ArrayList();
                for (ConeKotlinType coneKotlinType : arrayList2) {
                    List listFindClosestMatchingReceivers = ResolutionStagesKt.findClosestMatchingReceivers(candidate, coneKotlinType, arrayList5, resolutionContext);
                    int size = listFindClosestMatchingReceivers.size();
                    if (size == 0) {
                        checkerSink.reportDiagnostic(new NoApplicableValueForContextReceiver(coneKotlinType));
                        return Unit.INSTANCE;
                    }
                    if (size == 1) {
                        ReceiverDescription receiverDescription = (ReceiverDescription) CollectionsKt.single(listFindClosestMatchingReceivers);
                        arrayList6.add(receiverDescription.getExpression());
                        candidate.getSystem().addSubtypeConstraint(receiverDescription.getType(), coneKotlinType, SimpleConstraintSystemConstraintPosition.INSTANCE);
                    } else {
                        checkerSink.reportDiagnostic(new AmbiguousValuesForContextReceiverParameter(coneKotlinType));
                        return Unit.INSTANCE;
                    }
                }
                candidate.setContextReceiverArguments(arrayList6);
                return Unit.INSTANCE;
            }
        }
        return Unit.INSTANCE;
    }
}
