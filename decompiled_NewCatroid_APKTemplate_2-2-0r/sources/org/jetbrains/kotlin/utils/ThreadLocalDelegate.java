package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;

/* JADX INFO: compiled from: threadLocal.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J$\u0010\n\u001a\u00028\u00002\b\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0096\u0002¢\u0006\u0002\u0010\u000eJ,\u0010\u000f\u001a\u00020\u00102\b\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\r2\u0006\u0010\u0011\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00028\u00000\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/utils/ThreadLocalDelegate;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/properties/ReadWriteProperty;", "", "initializer", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)V", "map", "Ljava/util/concurrent/ConcurrentHashMap;", "Ljava/lang/Thread;", "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "value", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "toString", "", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ThreadLocalDelegate<T> implements ReadWriteProperty<Object, T> {
    private final Function0<T> initializer;
    private final ConcurrentHashMap<Thread, T> map;

    /* JADX WARN: Multi-variable type inference failed */
    public ThreadLocalDelegate(Function0<? extends T> initializer) {
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        this.initializer = initializer;
        this.map = new ConcurrentHashMap<>();
    }

    @Override // kotlin.properties.ReadWriteProperty, kotlin.properties.ReadOnlyProperty
    public T getValue(Object thisRef, KProperty<?> property) {
        Intrinsics.checkNotNullParameter(property, "property");
        ConcurrentHashMap<Thread, T> concurrentHashMap = this.map;
        Thread threadCurrentThread = Thread.currentThread();
        T t = (T) concurrentHashMap.get(threadCurrentThread);
        if (t != null) {
            return t;
        }
        T tInvoke = this.initializer.invoke();
        T t2 = (T) concurrentHashMap.putIfAbsent(threadCurrentThread, tInvoke);
        return t2 == null ? tInvoke : t2;
    }

    @Override // kotlin.properties.ReadWriteProperty
    public void setValue(Object thisRef, KProperty<?> property, T value) {
        Intrinsics.checkNotNullParameter(property, "property");
        ConcurrentHashMap<Thread, T> concurrentHashMap = this.map;
        Thread threadCurrentThread = Thread.currentThread();
        Intrinsics.checkNotNullExpressionValue(threadCurrentThread, "currentThread(...)");
        concurrentHashMap.put(threadCurrentThread, value);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ThreadLocalDelegate(");
        Set<Map.Entry<Thread, T>> setEntrySet = this.map.entrySet();
        Intrinsics.checkNotNullExpressionValue(setEntrySet, "<get-entries>(...)");
        return sb.append(kotlin.collections.CollectionsKt.joinToString$default(setEntrySet, null, null, null, 0, null, new Function1<Map.Entry<Thread, T>, CharSequence>() { // from class: org.jetbrains.kotlin.utils.ThreadLocalDelegate.toString.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(Map.Entry<Thread, T> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "#" + it.getKey().getId() + "=>" + it.getValue();
            }
        }, 31, null)).append(')').toString();
    }
}
