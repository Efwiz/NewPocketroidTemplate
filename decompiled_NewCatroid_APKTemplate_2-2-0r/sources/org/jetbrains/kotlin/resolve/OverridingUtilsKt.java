package org.jetbrains.kotlin.resolve;

import android.Manifest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.utils.DFS;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: overridingUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001f\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u0002¢\u0006\u0002\u0010\u0004\u001a\u001f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u0002¢\u0006\u0002\u0010\u0007\u001a9\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\b\b\u0000\u0010\n*\u00020\u000b*\b\u0012\u0004\u0012\u0002H\n0\t2\u0017\u0010\f\u001a\u0013\u0012\u0004\u0012\u0002H\n\u0012\u0004\u0012\u00020\u00030\r¢\u0006\u0002\b\u000e¨\u0006\u000f"}, d2 = {"findOriginalTopMostOverriddenDescriptors", "", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Ljava/util/Set;", "findTopMostOverriddenDescriptors", "", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Ljava/util/List;", "selectMostSpecificInEachOverridableGroup", "", "H", "", "descriptorByHandle", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OverridingUtilsKt {
    public static final <D extends CallableDescriptor> List<D> findTopMostOverriddenDescriptors(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        Object objDfs = DFS.dfs(CollectionsKt.listOf(d), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.resolve.OverridingUtilsKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return OverridingUtilsKt.findTopMostOverriddenDescriptors$lambda$0((CallableDescriptor) obj);
            }
        }, new DFS.CollectingNodeHandler<CallableDescriptor, CallableDescriptor, ArrayList<D>>(new ArrayList()) { // from class: org.jetbrains.kotlin.resolve.OverridingUtilsKt.findTopMostOverriddenDescriptors.2
            {
                super(arrayList);
            }

            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public void afterChildren(CallableDescriptor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                if (current.getOverriddenDescriptors().isEmpty()) {
                    ((ArrayList) this.result).add(current);
                }
            }
        });
        Intrinsics.checkNotNullExpressionValue(objDfs, "dfs(...)");
        return (List) objDfs;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable findTopMostOverriddenDescriptors$lambda$0(CallableDescriptor callableDescriptor) {
        return callableDescriptor.getOverriddenDescriptors();
    }

    public static final <D extends CallableDescriptor> Set<D> findOriginalTopMostOverriddenDescriptors(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        List listFindTopMostOverriddenDescriptors = findTopMostOverriddenDescriptors(d);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = listFindTopMostOverriddenDescriptors.iterator();
        while (it.hasNext()) {
            CallableDescriptor original = ((CallableDescriptor) it.next()).getOriginal();
            Intrinsics.checkNotNull(original, "null cannot be cast to non-null type D of org.jetbrains.kotlin.resolve.OverridingUtilsKt.findOriginalTopMostOverriddenDescriptors$lambda$1");
            linkedHashSet.add(original);
        }
        return linkedHashSet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <H> Collection<H> selectMostSpecificInEachOverridableGroup(Collection<? extends H> collection, Function1<? super H, ? extends CallableDescriptor> descriptorByHandle) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(descriptorByHandle, "descriptorByHandle");
        if (collection.size() <= 1) {
            return collection;
        }
        LinkedList linkedList = new LinkedList(collection);
        SmartSet smartSetCreate = SmartSet.INSTANCE.create();
        while (true) {
            LinkedList linkedList2 = linkedList;
            if (!linkedList2.isEmpty()) {
                Object objFirst = CollectionsKt.first((List<? extends Object>) linkedList);
                final SmartSet smartSetCreate2 = SmartSet.INSTANCE.create();
                Collection<Manifest> collectionExtractMembersOverridableInBothWays = OverridingUtil.extractMembersOverridableInBothWays(objFirst, linkedList2, descriptorByHandle, new Function1<H, Unit>() { // from class: org.jetbrains.kotlin.resolve.OverridingUtilsKt$selectMostSpecificInEachOverridableGroup$overridableGroup$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                        invoke2(obj);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(H h) {
                        SmartSet<H> smartSet = smartSetCreate2;
                        Intrinsics.checkNotNull(h);
                        smartSet.add(h);
                    }
                });
                Intrinsics.checkNotNullExpressionValue(collectionExtractMembersOverridableInBothWays, "extractMembersOverridableInBothWays(...)");
                if (collectionExtractMembersOverridableInBothWays.size() == 1 && smartSetCreate2.isEmpty()) {
                    Object objSingle = CollectionsKt.single(collectionExtractMembersOverridableInBothWays);
                    Intrinsics.checkNotNullExpressionValue(objSingle, "single(...)");
                    smartSetCreate.add(objSingle);
                } else {
                    Manifest manifest = (Object) OverridingUtil.selectMostSpecificMember(collectionExtractMembersOverridableInBothWays, descriptorByHandle);
                    Intrinsics.checkNotNullExpressionValue(manifest, "selectMostSpecificMember(...)");
                    CallableDescriptor callableDescriptorInvoke = descriptorByHandle.invoke(manifest);
                    for (Manifest manifest2 : collectionExtractMembersOverridableInBothWays) {
                        Intrinsics.checkNotNull(manifest2);
                        if (!OverridingUtil.isMoreSpecific(callableDescriptorInvoke, descriptorByHandle.invoke(manifest2))) {
                            smartSetCreate2.add(manifest2);
                        }
                    }
                    SmartSet smartSet = smartSetCreate2;
                    if (!smartSet.isEmpty()) {
                        smartSetCreate.addAll(smartSet);
                    }
                    smartSetCreate.add(manifest);
                }
            } else {
                return smartSetCreate;
            }
        }
    }
}
