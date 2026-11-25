package org.jetbrains.kotlin.analysis.providers.impl.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: SublistMerger.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aG\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00062\u001d\u0010\u0007\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\t\u0012\u0004\u0012\u00020\u00010\b¢\u0006\u0002\b\n\u001aP\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\n\b\u0001\u0010\f\u0018\u0001*\u0002H\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u001c\b\u0004\u0010\r\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\f0\u0004\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\bH\u0086\bø\u0001\u0000\u001a?\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\"\b\b\u0000\u0010\u0002*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u001d\u0010\u0007\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\t\u0012\u0004\u0012\u00020\u00010\b¢\u0006\u0002\b\n\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000f"}, d2 = {"mergeInto", "", "A", "", "", FirebaseAnalytics.Param.DESTINATION, "", "f", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/analysis/providers/impl/util/SublistMerger;", "Lkotlin/ExtensionFunctionType;", "mergeOnly", "R", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "mergeWith", "analysis-api-providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SublistMergerKt {
    public static final <A> void mergeInto(List<? extends A> list, List<A> destination, Function1<? super SublistMerger<A>, Unit> f) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(f, "f");
        SublistMerger sublistMerger = new SublistMerger(list, destination);
        f.invoke(sublistMerger);
        sublistMerger.finish();
    }

    public static final <A> List<A> mergeWith(List<? extends A> list, Function1<? super SublistMerger<A>, Unit> f) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(f, "f");
        ArrayList arrayList = new ArrayList();
        mergeInto(list, arrayList, f);
        return arrayList;
    }

    public static final /* synthetic */ <A, R extends A> List<A> mergeOnly(List<? extends A> list, final Function1<? super List<? extends R>, ? extends A> create) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(create, "create");
        Intrinsics.needClassReification();
        return mergeWith(list, new Function1<SublistMerger<A>, Unit>() { // from class: org.jetbrains.kotlin.analysis.providers.impl.util.SublistMergerKt.mergeOnly.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                invoke((SublistMerger) obj);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Type inference incomplete: some casts might be missing */
            public final void invoke(SublistMerger<A> mergeWith) {
                Intrinsics.checkNotNullParameter(mergeWith, "$this$mergeWith");
                Function1<List<? extends R>, A> function1 = create;
                List<A> remainingElements = mergeWith.getRemainingElements();
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
                List list2 = (List) pair.component1();
                List<? extends A> list3 = (List) pair.component2();
                CollectionsKt.addIfNotNull(mergeWith.getDestination(), function1.invoke((List<? extends R>) list2));
                mergeWith.setRemainingElements(list3);
            }
        });
    }
}
