package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.utils.DFS;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: findLoopsInSupertypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JR\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\r0\nH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/SupertypeLoopCheckerImpl;", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "()V", "findLoopsInSupertypesAndDisconnect", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "currentTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "superTypes", "neighbors", "Lkotlin/Function1;", "", "reportLoop", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SupertypeLoopCheckerImpl implements SupertypeLoopChecker {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable findLoopsInSupertypesAndDisconnect$lambda$1(Function1 neighbors, TypeConstructor typeConstructor) {
        Intrinsics.checkNotNullParameter(neighbors, "$neighbors");
        Intrinsics.checkNotNull(typeConstructor);
        Iterable iterable = (Iterable) neighbors.invoke(typeConstructor);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(((KotlinType) it.next()).getConstructor());
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.descriptors.SupertypeLoopChecker
    public Collection<KotlinType> findLoopsInSupertypesAndDisconnect(TypeConstructor currentTypeConstructor, Collection<? extends KotlinType> superTypes, final Function1<? super TypeConstructor, ? extends Iterable<? extends KotlinType>> neighbors, Function1<? super KotlinType, Unit> reportLoop) {
        Intrinsics.checkNotNullParameter(currentTypeConstructor, "currentTypeConstructor");
        Intrinsics.checkNotNullParameter(superTypes, "superTypes");
        Intrinsics.checkNotNullParameter(neighbors, "neighbors");
        Intrinsics.checkNotNullParameter(reportLoop, "reportLoop");
        DFS.Neighbors neighbors2 = new DFS.Neighbors() { // from class: org.jetbrains.kotlin.resolve.SupertypeLoopCheckerImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return SupertypeLoopCheckerImpl.findLoopsInSupertypesAndDisconnect$lambda$1(neighbors, (TypeConstructor) obj);
            }
        };
        SmartList smartList = new SmartList();
        for (KotlinType kotlinType : superTypes) {
            if (FindLoopsInSupertypes.isReachable(kotlinType.getConstructor(), currentTypeConstructor, neighbors2)) {
                smartList.add(kotlinType);
                reportLoop.invoke(kotlinType);
                ClassifierDescriptor this$0 = currentTypeConstructor.getThis$0();
                if (this$0 != null && DescriptorUtilsKt.isCompanionObject(this$0)) {
                    SimpleType defaultType = this$0.getDefaultType();
                    Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                    reportLoop.invoke(defaultType);
                }
            }
        }
        return smartList.isEmpty() ? superTypes : CollectionsKt.minus((Iterable) superTypes, (Iterable) smartList);
    }
}
