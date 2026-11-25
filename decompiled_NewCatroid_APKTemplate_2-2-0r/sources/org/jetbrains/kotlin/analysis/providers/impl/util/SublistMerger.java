package org.jetbrains.kotlin.analysis.providers.impl.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: SublistMerger.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002B!\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\u000e\u001a\u00020\u000fJ4\u0010\u0010\u001a\u00020\u000f\"\n\b\u0001\u0010\u0011\u0018\u0001*\u00028\u00002\u001a\u0010\u0012\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0004\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0013H\u0086\bø\u0001\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR \u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\t\"\u0004\b\f\u0010\r\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/util/SublistMerger;", "A", "", "initialElements", "", FirebaseAnalytics.Param.DESTINATION, "", "(Ljava/util/List;Ljava/util/List;)V", "getDestination", "()Ljava/util/List;", "remainingElements", "getRemainingElements", "setRemainingElements", "(Ljava/util/List;)V", "finish", "", "merge", "R", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lkotlin/Function1;", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SublistMerger<A> {
    private final List<A> destination;
    private List<? extends A> remainingElements;

    public SublistMerger(List<? extends A> initialElements, List<A> destination) {
        Intrinsics.checkNotNullParameter(initialElements, "initialElements");
        Intrinsics.checkNotNullParameter(destination, "destination");
        this.destination = destination;
        this.remainingElements = initialElements;
    }

    public final List<A> getDestination() {
        return this.destination;
    }

    public final List<A> getRemainingElements() {
        return this.remainingElements;
    }

    public final void setRemainingElements(List<? extends A> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.remainingElements = list;
    }

    public final /* synthetic */ <R extends A> void merge(Function1<? super List<? extends R>, ? extends A> create) {
        Intrinsics.checkNotNullParameter(create, "create");
        List<A> remainingElements = getRemainingElements();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : remainingElements) {
            Intrinsics.reifiedOperationMarker(3, "R");
            if (obj instanceof Object) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list = (List) pair.component1();
        List<? extends A> list2 = (List) pair.component2();
        CollectionsKt.addIfNotNull(getDestination(), create.invoke(list));
        setRemainingElements(list2);
    }

    public final void finish() {
        this.destination.addAll(this.remainingElements);
        this.remainingElements = kotlin.collections.CollectionsKt.emptyList();
    }
}
