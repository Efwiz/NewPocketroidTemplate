package org.catrobat.catroid.utils;

import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/LiveDataExtensionsKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: LiveDataExtensions.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aZ\u0010\u0000\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0001\u001aV\u0010\t\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00050\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0004\"\u0004\b\u0002\u0010\u0005*\b\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0001\u001a/\u0010\f\u001a\u0002H\r\"\u0004\b\u0000\u0010\r*\b\u0012\u0004\u0012\u0002H\r0\u00012\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012¨\u0006\u0013"}, d2 = {"combine", "Landroidx/lifecycle/LiveData;", "Lkotlin/Triple;", "A", "B", "C", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "c", "combineWith", "other", "secondOther", "getOrAwaitValue", ExifInterface.GPS_DIRECTION_TRUE, "time", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "(Landroidx/lifecycle/LiveData;JLjava/util/concurrent/TimeUnit;)Ljava/lang/Object;", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LiveDataExtensionsKt {
    public static final <A, B, C> LiveData<Triple<A, B, C>> combine(final LiveData<A> a, final LiveData<B> b, final LiveData<C> c) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Intrinsics.checkNotNullParameter(c, "c");
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(a, new LiveDataExtensionsKt$sam$androidx_lifecycle_Observer$0(new Function1<A, Unit>() { // from class: org.catrobat.catroid.utils.LiveDataExtensionsKt$combine$1$1
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
            public final void invoke2(A a2) {
                LiveDataExtensionsKt.combine$lambda$0$combine(a, b, c, mediatorLiveData);
            }
        }));
        mediatorLiveData.addSource(b, new LiveDataExtensionsKt$sam$androidx_lifecycle_Observer$0(new Function1<B, Unit>() { // from class: org.catrobat.catroid.utils.LiveDataExtensionsKt$combine$1$2
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
            public final void invoke2(B b2) {
                LiveDataExtensionsKt.combine$lambda$0$combine(a, b, c, mediatorLiveData);
            }
        }));
        mediatorLiveData.addSource(c, new LiveDataExtensionsKt$sam$androidx_lifecycle_Observer$0(new Function1<C, Unit>() { // from class: org.catrobat.catroid.utils.LiveDataExtensionsKt$combine$1$3
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
            public final void invoke2(C c2) {
                LiveDataExtensionsKt.combine$lambda$0$combine(a, b, c, mediatorLiveData);
            }
        }));
        combine$lambda$0$combine(a, b, c, mediatorLiveData);
        return mediatorLiveData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <A, B, C> void combine$lambda$0$combine(LiveData<A> liveData, LiveData<B> liveData2, LiveData<C> liveData3, MediatorLiveData<Triple<A, B, C>> mediatorLiveData) {
        A value = liveData.getValue();
        B value2 = liveData2.getValue();
        C value3 = liveData3.getValue();
        if (value == null || value2 == null || value3 == null) {
            return;
        }
        mediatorLiveData.postValue(new Triple<>(value, value2, value3));
    }

    public static final <A, B, C> LiveData<Triple<A, B, C>> combineWith(LiveData<A> liveData, LiveData<B> other, LiveData<C> secondOther) {
        Intrinsics.checkNotNullParameter(liveData, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(secondOther, "secondOther");
        return combine(liveData, other, secondOther);
    }

    public static /* synthetic */ Object getOrAwaitValue$default(LiveData liveData, long j, TimeUnit timeUnit, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 2;
        }
        if ((i & 2) != 0) {
            timeUnit = TimeUnit.SECONDS;
        }
        return getOrAwaitValue(liveData, j, timeUnit);
    }

    public static final <T> T getOrAwaitValue(final LiveData<T> liveData, long j, TimeUnit timeUnit) throws TimeoutException {
        Intrinsics.checkNotNullParameter(liveData, "<this>");
        Intrinsics.checkNotNullParameter(timeUnit, "timeUnit");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        liveData.observeForever(new Observer<T>() { // from class: org.catrobat.catroid.utils.LiveDataExtensionsKt$getOrAwaitValue$observer$1
            @Override // androidx.lifecycle.Observer
            public void onChanged(T value) {
                objectRef.element = value;
                countDownLatch.countDown();
                liveData.removeObserver(this);
            }
        });
        if (!countDownLatch.await(j, timeUnit)) {
            throw new TimeoutException("LiveData value was never set.");
        }
        return objectRef.element;
    }
}
