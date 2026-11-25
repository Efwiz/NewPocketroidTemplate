package org.jetbrains.kotlin.ir.declarations.lazy;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.ir.IrLock;

/* JADX INFO: compiled from: lazyUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0002\u0010\bJ$\u0010\u000f\u001a\u00028\u00002\b\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0096\u0002¢\u0006\u0002\u0010\u0014J,\u0010\u0015\u001a\u00020\u00162\b\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u000e\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00028\u00008BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/SynchronizedLazyVar;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/properties/ReadWriteProperty;", "", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "initializer", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/ir/IrLock;Lkotlin/jvm/functions/Function0;)V", "_value", "isInitialized", "", "getLock", "()Lorg/jetbrains/kotlin/ir/IrLock;", "value", "getValue", "()Ljava/lang/Object;", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SynchronizedLazyVar<T> implements ReadWriteProperty<Object, T> {
    private volatile Object _value;
    private Function0<? extends T> initializer;
    private volatile boolean isInitialized;
    private final IrLock lock;

    public SynchronizedLazyVar(IrLock lock, Function0<? extends T> initializer) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        this.lock = lock;
        this.initializer = initializer;
    }

    public final IrLock getLock() {
        return this.lock;
    }

    private final T getValue() {
        T t;
        if (this.isInitialized) {
            return (T) this._value;
        }
        synchronized (this.lock) {
            if (!this.isInitialized) {
                Function0<? extends T> function0 = this.initializer;
                Intrinsics.checkNotNull(function0);
                this._value = function0.invoke();
                this.isInitialized = true;
                this.initializer = null;
            }
            t = (T) this._value;
        }
        return t;
    }

    public String toString() {
        return this.isInitialized ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }

    @Override // kotlin.properties.ReadWriteProperty, kotlin.properties.ReadOnlyProperty
    public T getValue(Object thisRef, KProperty<?> property) {
        Intrinsics.checkNotNullParameter(property, "property");
        return getValue();
    }

    @Override // kotlin.properties.ReadWriteProperty
    public void setValue(Object thisRef, KProperty<?> property, T value) {
        Intrinsics.checkNotNullParameter(property, "property");
        synchronized (this.lock) {
            this._value = value;
            this.isInitialized = true;
            Unit unit = Unit.INSTANCE;
        }
    }
}
