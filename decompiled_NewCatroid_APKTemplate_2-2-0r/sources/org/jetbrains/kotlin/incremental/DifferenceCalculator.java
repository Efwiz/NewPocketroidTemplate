package org.jetbrains.kotlin.incremental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.protobuf.MessageLite;

/* JADX INFO: compiled from: protoDifferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J*\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J*\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0004J*\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00100\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00100\u000bH\u0004J\b\u0010\u0011\u001a\u00020\u0012H&J\u001c\u0010\u0013\u001a\u00020\u0014*\u00020\u00042\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\fH\u0002JH\u0010\u0017\u001a\u00020\u0010*\u00020\f2\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u00192\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u00192\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u001c0\u0019H\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/incremental/DifferenceCalculator;", "", "()V", "compareObject", "Lorg/jetbrains/kotlin/incremental/ProtoCompareGenerated;", "getCompareObject", "()Lorg/jetbrains/kotlin/incremental/ProtoCompareGenerated;", "calcDifferenceForEqualHashes", "", "", "oldList", "", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "newList", "calcDifferenceForMembers", "calcDifferenceForNames", "", "difference", "Lorg/jetbrains/kotlin/incremental/Difference;", "checkEquals", "", "old", PsiKeyword.NEW, "getHashCode", "stringIndexes", "Lkotlin/Function1;", "fqNameIndexes", "typeTable", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DifferenceCalculator {
    public abstract Difference difference();

    protected abstract ProtoCompareGenerated getCompareObject();

    protected final Collection<String> calcDifferenceForMembers(List<? extends MessageLite> oldList, List<? extends MessageLite> newList) {
        List<String> listCalcDifferenceForEqualHashes;
        Intrinsics.checkNotNullParameter(oldList, "oldList");
        Intrinsics.checkNotNullParameter(newList, "newList");
        HashSet hashSet = new HashSet();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : oldList) {
            Integer numValueOf = Integer.valueOf(getHashCode((MessageLite) obj, new DifferenceCalculator$calcDifferenceForMembers$oldMap$1$1(getCompareObject()), new DifferenceCalculator$calcDifferenceForMembers$oldMap$1$2(getCompareObject()), new DifferenceCalculator$calcDifferenceForMembers$oldMap$1$3(getCompareObject())));
            Object obj2 = linkedHashMap.get(numValueOf);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(numValueOf, obj2);
            }
            ((List) obj2).add(obj);
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Object obj3 : newList) {
            Integer numValueOf2 = Integer.valueOf(getHashCode((MessageLite) obj3, new DifferenceCalculator$calcDifferenceForMembers$newMap$1$1(getCompareObject()), new DifferenceCalculator$calcDifferenceForMembers$newMap$1$2(getCompareObject()), new DifferenceCalculator$calcDifferenceForMembers$newMap$1$3(getCompareObject())));
            Object obj4 = linkedHashMap2.get(numValueOf2);
            if (obj4 == null) {
                obj4 = (List) new ArrayList();
                linkedHashMap2.put(numValueOf2, obj4);
            }
            ((List) obj4).add(obj3);
        }
        Iterator it = SetsKt.plus(linkedHashMap.keySet(), (Iterable) linkedHashMap2.keySet()).iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            List<? extends MessageLite> list = (List) linkedHashMap.get(Integer.valueOf(iIntValue));
            List<? extends MessageLite> list2 = (List) linkedHashMap2.get(Integer.valueOf(iIntValue));
            if (list2 == null) {
                Intrinsics.checkNotNull(list);
                listCalcDifferenceForEqualHashes = ProtoDifferenceUtilsKt.names(list, getCompareObject().getOldNameResolver());
            } else if (list == null) {
                listCalcDifferenceForEqualHashes = ProtoDifferenceUtilsKt.names(list2, getCompareObject().getNewNameResolver());
            } else {
                listCalcDifferenceForEqualHashes = calcDifferenceForEqualHashes(list, list2);
            }
            hashSet.addAll(listCalcDifferenceForEqualHashes);
        }
        return hashSet;
    }

    private final Collection<String> calcDifferenceForEqualHashes(List<? extends MessageLite> oldList, List<? extends MessageLite> newList) {
        Object next;
        MessageLite messageLite;
        ProtoCompareGenerated compareObject;
        HashSet hashSet = new HashSet();
        HashSet<MessageLite> hashSet2 = new HashSet(newList);
        for (MessageLite messageLite2 : oldList) {
            Iterator it = hashSet2.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                messageLite = (MessageLite) next;
                compareObject = getCompareObject();
                Intrinsics.checkNotNull(messageLite);
            } while (!checkEquals(compareObject, messageLite2, messageLite));
            MessageLite messageLite3 = (MessageLite) next;
            if (messageLite3 == null) {
                hashSet.add(ProtoDifferenceUtilsKt.name(messageLite2, getCompareObject().getOldNameResolver()));
            } else {
                hashSet2.remove(messageLite3);
            }
        }
        for (MessageLite messageLite4 : hashSet2) {
            Intrinsics.checkNotNull(messageLite4);
            hashSet.add(ProtoDifferenceUtilsKt.name(messageLite4, getCompareObject().getNewNameResolver()));
        }
        return hashSet;
    }

    protected final Collection<String> calcDifferenceForNames(List<Integer> oldList, List<Integer> newList) {
        Intrinsics.checkNotNullParameter(oldList, "oldList");
        Intrinsics.checkNotNullParameter(newList, "newList");
        List<Integer> list = oldList;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(getCompareObject().getOldNameResolver().getString(((Number) it.next()).intValue()));
        }
        Set set = CollectionsKt.toSet(arrayList);
        List<Integer> list2 = newList;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(getCompareObject().getNewNameResolver().getString(((Number) it2.next()).intValue()));
        }
        Set set2 = set;
        Set set3 = CollectionsKt.toSet(arrayList2);
        return SetsKt.minus(CollectionsKt.union(set2, set3), (Iterable) CollectionsKt.intersect(set2, set3));
    }

    private final int getHashCode(MessageLite messageLite, Function1<? super Integer, Integer> function1, Function1<? super Integer, Integer> function2, Function1<? super Integer, ProtoBuf.Type> function3) {
        if (messageLite instanceof ProtoBuf.Constructor) {
            return ProtoCompareGeneratedKt.hashCode((ProtoBuf.Constructor) messageLite, function1, function2, function3);
        }
        if (messageLite instanceof ProtoBuf.Function) {
            return ProtoCompareGeneratedKt.hashCode((ProtoBuf.Function) messageLite, function1, function2, function3);
        }
        if (messageLite instanceof ProtoBuf.Property) {
            return ProtoCompareGeneratedKt.hashCode((ProtoBuf.Property) messageLite, function1, function2, function3);
        }
        if (messageLite instanceof ProtoBuf.TypeAlias) {
            return ProtoCompareGeneratedKt.hashCode((ProtoBuf.TypeAlias) messageLite, function1, function2, function3);
        }
        throw new IllegalStateException(("Unknown message: " + messageLite).toString());
    }

    private final boolean checkEquals(ProtoCompareGenerated protoCompareGenerated, MessageLite messageLite, MessageLite messageLite2) {
        if ((messageLite instanceof ProtoBuf.Constructor) && (messageLite2 instanceof ProtoBuf.Constructor)) {
            return protoCompareGenerated.checkEquals((ProtoBuf.Constructor) messageLite, (ProtoBuf.Constructor) messageLite2);
        }
        if ((messageLite instanceof ProtoBuf.Function) && (messageLite2 instanceof ProtoBuf.Function)) {
            return protoCompareGenerated.checkEquals((ProtoBuf.Function) messageLite, (ProtoBuf.Function) messageLite2);
        }
        if ((messageLite instanceof ProtoBuf.Property) && (messageLite2 instanceof ProtoBuf.Property)) {
            return protoCompareGenerated.checkEquals((ProtoBuf.Property) messageLite, (ProtoBuf.Property) messageLite2);
        }
        if ((messageLite instanceof ProtoBuf.TypeAlias) && (messageLite2 instanceof ProtoBuf.TypeAlias)) {
            return protoCompareGenerated.checkEquals((ProtoBuf.TypeAlias) messageLite, (ProtoBuf.TypeAlias) messageLite2);
        }
        throw new IllegalStateException(("Unknown message: " + protoCompareGenerated).toString());
    }
}
