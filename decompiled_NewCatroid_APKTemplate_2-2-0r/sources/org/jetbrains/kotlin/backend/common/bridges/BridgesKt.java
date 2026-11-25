package org.jetbrains.kotlin.backend.common.bridges;

import android.Manifest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: bridges.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u0002¢\u0006\u0002\u0010\u0005\u001a\u001f\u0010\u0006\u001a\u0004\u0018\u0001H\u0002\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u0002¢\u0006\u0002\u0010\u0007\u001aI\u0010\b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u00020\n0\t\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0004\b\u0001\u0010\u000b2\u0006\u0010\u0004\u001a\u0002H\u00022\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000b0\r¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"findAllReachableDeclarations", "", "Function", "Lorg/jetbrains/kotlin/backend/common/bridges/FunctionHandle;", "function", "(Lorg/jetbrains/kotlin/backend/common/bridges/FunctionHandle;)Ljava/util/Set;", "findConcreteSuperDeclaration", "(Lorg/jetbrains/kotlin/backend/common/bridges/FunctionHandle;)Lorg/jetbrains/kotlin/backend/common/bridges/FunctionHandle;", "generateBridges", "", "Lorg/jetbrains/kotlin/backend/common/bridges/Bridge;", "Signature", "signature", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/backend/common/bridges/FunctionHandle;Lkotlin/jvm/functions/Function1;)Ljava/util/Set;", "backend-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BridgesKt {
    public static final <Function extends FunctionHandle, Signature> Set<Bridge<Signature, Function>> generateBridges(Function function, Function1<? super Function, ? extends Signature> signature) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(signature, "signature");
        if (function.isAbstract()) {
            return SetsKt.emptySet();
        }
        boolean zIsDeclaration = function.isDeclaration();
        if (!zIsDeclaration) {
            Iterable<FunctionHandle> overridden = function.getOverridden();
            if (!(overridden instanceof Collection) || !((Collection) overridden).isEmpty()) {
                Iterator<FunctionHandle> it = overridden.iterator();
                do {
                    if (it.hasNext()) {
                    }
                } while (!it.next().isAbstract());
            }
            return SetsKt.emptySet();
        }
        FunctionHandle functionHandleFindConcreteSuperDeclaration = findConcreteSuperDeclaration(function);
        if (functionHandleFindConcreteSuperDeclaration == null) {
            return SetsKt.emptySet();
        }
        Set<Manifest.permission> setFindAllReachableDeclarations = findAllReachableDeclarations(function);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Manifest.permission permissionVar : setFindAllReachableDeclarations) {
            Signature signatureInvoke = signature.invoke(permissionVar);
            Object obj = linkedHashMap.get(signatureInvoke);
            if (obj == null) {
                obj = (List) new ArrayList();
                linkedHashMap.put(signatureInvoke, obj);
            }
            ((List) obj).add(permissionVar);
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        if (!zIsDeclaration) {
            Iterable<FunctionHandle> overridden2 = function.getOverridden();
            Intrinsics.checkNotNull(overridden2, "null cannot be cast to non-null type kotlin.collections.Iterable<Function of org.jetbrains.kotlin.backend.common.bridges.BridgesKt.generateBridges>");
            for (FunctionHandle functionHandle : overridden2) {
                if (!functionHandle.isAbstract()) {
                    Iterator it2 = findAllReachableDeclarations(functionHandle).iterator();
                    while (it2.hasNext()) {
                        linkedHashMap2.remove(signature.invoke((FunctionHandle) it2.next()));
                    }
                }
            }
        }
        Signature signatureInvoke2 = signature.invoke(functionHandleFindConcreteSuperDeclaration);
        linkedHashMap2.remove(signatureInvoke2);
        Set setEntrySet = linkedHashMap2.entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
        Set<Map.Entry> set = setEntrySet;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        for (Map.Entry entry : set) {
            Intrinsics.checkNotNull(entry);
            Object key = entry.getKey();
            List list = (List) entry.getValue();
            Intrinsics.checkNotNull(list);
            arrayList.add(new Bridge(key, signatureInvoke2, CollectionsKt.toSet(list)));
        }
        return CollectionsKt.toSet(arrayList);
    }

    public static final <Function extends FunctionHandle> Set<Function> findAllReachableDeclarations(Function function) {
        Intrinsics.checkNotNullParameter(function, "function");
        DFS.NodeHandler nodeHandler = new DFS.NodeHandlerWithListResult<Function, Function>() { // from class: org.jetbrains.kotlin.backend.common.bridges.BridgesKt$findAllReachableDeclarations$collector$1
            /* JADX WARN: Incorrect types in method signature: (TFunction;)V */
            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public void afterChildren(FunctionHandle current) {
                Intrinsics.checkNotNullParameter(current, "current");
                if (current.isDeclaration()) {
                    ((LinkedList) this.result).add(current);
                }
            }
        };
        DFS.dfs(CollectionsKt.listOf(function), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.backend.common.bridges.BridgesKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return BridgesKt.findAllReachableDeclarations$lambda$2((FunctionHandle) obj);
            }
        }, nodeHandler);
        return new LinkedHashSet((Collection) nodeHandler.result());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable findAllReachableDeclarations$lambda$2(FunctionHandle functionHandle) {
        Iterable<FunctionHandle> overridden = functionHandle.getOverridden();
        Intrinsics.checkNotNull(overridden, "null cannot be cast to non-null type kotlin.collections.Iterable<Function of org.jetbrains.kotlin.backend.common.bridges.BridgesKt.findAllReachableDeclarations$lambda$2>");
        return overridden;
    }

    public static final <Function extends FunctionHandle> Function findConcreteSuperDeclaration(Function function) {
        Intrinsics.checkNotNullParameter(function, "function");
        if (function.isAbstract()) {
            throw new IllegalArgumentException(("Only concrete functions have implementations: " + function).toString());
        }
        if (function.isDeclaration()) {
            return function;
        }
        Set<FunctionHandle> setFindAllReachableDeclarations = findAllReachableDeclarations(function);
        HashSet hashSet = new HashSet();
        for (FunctionHandle functionHandle : setFindAllReachableDeclarations) {
            Set setFindAllReachableDeclarations2 = findAllReachableDeclarations(functionHandle);
            setFindAllReachableDeclarations2.remove(functionHandle);
            hashSet.addAll(setFindAllReachableDeclarations2);
        }
        setFindAllReachableDeclarations.removeAll(hashSet);
        ArrayList arrayList = new ArrayList();
        for (Object obj : setFindAllReachableDeclarations) {
            FunctionHandle functionHandle2 = (FunctionHandle) obj;
            if (!functionHandle2.isAbstract() && functionHandle2.getMayBeUsedAsSuperImplementation()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.size() != 1) {
            if (function.getMightBeIncorrectCode()) {
                return null;
            }
            throw new IllegalStateException(("Concrete fake override " + function + " should have exactly one concrete super-declaration: " + arrayList2).toString());
        }
        return (Function) arrayList2.get(0);
    }
}
