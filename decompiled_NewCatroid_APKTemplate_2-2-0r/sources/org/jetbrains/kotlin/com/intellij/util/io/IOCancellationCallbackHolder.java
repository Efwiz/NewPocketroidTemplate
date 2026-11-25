package org.jetbrains.kotlin.com.intellij.util.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IOCancellationCallbackHolder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0007J\b\u0010\u000b\u001a\u00020\nH\u0007J\b\u0010\f\u001a\u00020\u0004H\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/util/io/IOCancellationCallbackHolder;", "", "()V", "usedIoCallback", "Lorg/jetbrains/kotlin/com/intellij/util/io/IOCancellationCallback;", "getUsedIoCallback", "()Lcom/intellij/util/io/IOCancellationCallback;", "usedIoCallback$delegate", "Lkotlin/Lazy;", "checkCancelled", "", "interactWithUI", "loadSingleCallback", "intellij.platform.util"}, k = 1, mv = {1, 5, 1})
public final class IOCancellationCallbackHolder {
    public static final IOCancellationCallbackHolder INSTANCE = new IOCancellationCallbackHolder();

    /* JADX INFO: renamed from: usedIoCallback$delegate, reason: from kotlin metadata */
    private static final Lazy usedIoCallback = LazyKt.lazy(new Function0<IOCancellationCallback>() { // from class: org.jetbrains.kotlin.com.intellij.util.io.IOCancellationCallbackHolder$usedIoCallback$2
        @Override // kotlin.jvm.functions.Function0
        public final IOCancellationCallback invoke() {
            return IOCancellationCallbackHolder.INSTANCE.loadSingleCallback();
        }
    });

    public final IOCancellationCallback getUsedIoCallback() {
        return (IOCancellationCallback) usedIoCallback.getValue();
    }

    private IOCancellationCallbackHolder() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IOCancellationCallback loadSingleCallback() {
        ServiceLoader serviceLoader = ServiceLoader.load(IOCancellationCallback.class, IOCancellationCallback.class.getClassLoader());
        Intrinsics.checkNotNullExpressionValue(serviceLoader, "serviceLoader");
        List list = CollectionsKt.toList(serviceLoader);
        if (list.size() > 1) {
            StringBuilder sb = new StringBuilder("Few io cancellation callbacks are registered: ");
            List list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(((IOCancellationCallback) it.next()).getClass().getName());
            }
            throw new IllegalStateException(sb.append(arrayList).toString());
        }
        IOCancellationCallback iOCancellationCallback = (IOCancellationCallback) CollectionsKt.firstOrNull(list);
        return iOCancellationCallback != null ? iOCancellationCallback : new IOCancellationCallback() { // from class: org.jetbrains.kotlin.com.intellij.util.io.IOCancellationCallbackHolder.loadSingleCallback.2
            @Override // org.jetbrains.kotlin.com.intellij.util.io.IOCancellationCallback
            public void checkCancelled() {
            }

            @Override // org.jetbrains.kotlin.com.intellij.util.io.IOCancellationCallback
            public void interactWithUI() {
            }
        };
    }

    @JvmStatic
    public static final void interactWithUI() {
        INSTANCE.getUsedIoCallback().interactWithUI();
    }
}
