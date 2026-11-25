package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: ScopeTowerProcessors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003BM\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u00128\u0010\u0006\u001a4\u0012\u0004\u0012\u00020\b\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0007j\u0002`\u000f¢\u0006\u0002\b\u0010¢\u0006\u0002\u0010\u0011J\u001e\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\r2\u0006\u0010\u000b\u001a\u00020\u0018H\u0016J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010\u001a\u001a\u00020\u0017H\u0016RC\u0010\u0006\u001a4\u0012\u0004\u0012\u00020\b\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0007j\u0002`\u000f¢\u0006\u0002\b\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NoExplicitReceiverScopeTowerProcessor;", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractSimpleScopeTowerProcessor;", "context", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;", "collectCandidates", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "Lkotlin/ParameterName;", "name", "extensionReceiver", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidatesCollector;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateFactory;Lkotlin/jvm/functions/Function2;)V", "getCollectCandidates", "()Lkotlin/jvm/functions/Function2;", "recordLookups", "", "skippedData", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "Lorg/jetbrains/kotlin/name/Name;", "simpleProcess", "data", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class NoExplicitReceiverScopeTowerProcessor<C extends Candidate> extends AbstractSimpleScopeTowerProcessor<C> {
    private final Function2<ScopeTowerLevel, ReceiverValueWithSmartCastInfo, Collection<CandidateWithBoundDispatchReceiver>> collectCandidates;

    public final Function2<ScopeTowerLevel, ReceiverValueWithSmartCastInfo, Collection<CandidateWithBoundDispatchReceiver>> getCollectCandidates() {
        return this.collectCandidates;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public NoExplicitReceiverScopeTowerProcessor(CandidateFactory<? extends C> context, Function2<? super ScopeTowerLevel, ? super ReceiverValueWithSmartCastInfo, ? extends Collection<CandidateWithBoundDispatchReceiver>> collectCandidates) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(collectCandidates, "collectCandidates");
        this.collectCandidates = collectCandidates;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.SimpleScopeTowerProcessor
    public Collection<C> simpleProcess(TowerData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        if (data instanceof TowerData.TowerLevel) {
            return createCandidates(this.collectCandidates.invoke(((TowerData.TowerLevel) data).getLevel(), null), ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, null);
        }
        if (data instanceof TowerData.BothTowerLevelAndImplicitReceiver) {
            TowerData.BothTowerLevelAndImplicitReceiver bothTowerLevelAndImplicitReceiver = (TowerData.BothTowerLevelAndImplicitReceiver) data;
            return createCandidates(this.collectCandidates.invoke(bothTowerLevelAndImplicitReceiver.getLevel(), bothTowerLevelAndImplicitReceiver.getImplicitReceiver()), ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, bothTowerLevelAndImplicitReceiver.getImplicitReceiver());
        }
        if (data instanceof TowerData.BothTowerLevelAndContextReceiversGroup) {
            TowerData.BothTowerLevelAndContextReceiversGroup bothTowerLevelAndContextReceiversGroup = (TowerData.BothTowerLevelAndContextReceiversGroup) data;
            List<ReceiverValueWithSmartCastInfo> contextReceiversGroup = bothTowerLevelAndContextReceiversGroup.getContextReceiversGroup();
            ArrayList arrayList = new ArrayList();
            for (ReceiverValueWithSmartCastInfo receiverValueWithSmartCastInfo : contextReceiversGroup) {
                Collection<CandidateWithBoundDispatchReceiver> collectionInvoke = this.collectCandidates.invoke(bothTowerLevelAndContextReceiversGroup.getLevel(), receiverValueWithSmartCastInfo);
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionInvoke, 10));
                Iterator<T> it = collectionInvoke.iterator();
                while (it.hasNext()) {
                    arrayList2.add(TuplesKt.to((CandidateWithBoundDispatchReceiver) it.next(), receiverValueWithSmartCastInfo));
                }
                CollectionsKt.addAll(arrayList, arrayList2);
            }
            ArrayList arrayList3 = new ArrayList();
            for (Object obj : arrayList) {
                if (TowerUtilsKt.getRequiresExtensionReceiver((CandidateWithBoundDispatchReceiver) ((Pair) obj).component1())) {
                    arrayList3.add(obj);
                }
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj2 : arrayList3) {
                CallableDescriptor descriptor = ((CandidateWithBoundDispatchReceiver) ((Pair) obj2).getFirst()).getDescriptor();
                Object obj3 = linkedHashMap.get(descriptor);
                if (obj3 == null) {
                    obj3 = (List) new ArrayList();
                    linkedHashMap.put(descriptor, obj3);
                }
                ((List) obj3).add(obj2);
            }
            Collection<List> collectionValues = linkedHashMap.values();
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues, 10));
            for (List list : collectionValues) {
                CandidateWithBoundDispatchReceiver candidateWithBoundDispatchReceiver = (CandidateWithBoundDispatchReceiver) ((Pair) CollectionsKt.first(list)).getFirst();
                List list2 = list;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator it2 = list2.iterator();
                while (it2.hasNext()) {
                    arrayList5.add((ReceiverValueWithSmartCastInfo) ((Pair) it2.next()).getSecond());
                }
                arrayList4.add(TuplesKt.to(candidateWithBoundDispatchReceiver, arrayList5));
            }
            ArrayList<Pair> arrayList6 = arrayList4;
            ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList6, 10));
            for (Pair pair : arrayList6) {
                arrayList7.add(getCandidateFactory().createCandidate((CandidateWithBoundDispatchReceiver) pair.getFirst(), ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, (List<ReceiverValueWithSmartCastInfo>) pair.getSecond()));
            }
            return arrayList7;
        }
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor
    public void recordLookups(Collection<? extends TowerData> skippedData, Name name2) {
        Intrinsics.checkNotNullParameter(skippedData, "skippedData");
        Intrinsics.checkNotNullParameter(name2, "name");
        for (TowerData towerData : skippedData) {
            if (towerData instanceof TowerData.TowerLevel) {
                ((TowerData.TowerLevel) towerData).getLevel().recordLookup(name2);
            } else if (towerData instanceof TowerData.BothTowerLevelAndImplicitReceiver) {
                ((TowerData.BothTowerLevelAndImplicitReceiver) towerData).getLevel().recordLookup(name2);
            } else if (towerData instanceof TowerData.BothTowerLevelAndContextReceiversGroup) {
                ((TowerData.BothTowerLevelAndContextReceiversGroup) towerData).getLevel().recordLookup(name2);
            } else if (towerData instanceof TowerData.ForLookupForNoExplicitReceiver) {
                ((TowerData.ForLookupForNoExplicitReceiver) towerData).getLevel().recordLookup(name2);
            }
        }
    }
}
