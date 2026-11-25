package com.android.tools.r8.jetbrains.kotlin;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import com.android.tools.r8.internal.InterfaceC1423Ui;
import com.android.tools.r8.internal.UA;
import com.android.tools.r8.internal.V00;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class SafePublicationLazyImpl<T> implements UA, Serializable {
    private static final AtomicReferenceFieldUpdater c = AtomicReferenceFieldUpdater.newUpdater(SafePublicationLazyImpl.class, Object.class, JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME);
    private volatile InterfaceC1423Ui a;
    private volatile Object b = V00.a;

    public SafePublicationLazyImpl(InterfaceC1423Ui interfaceC1423Ui) {
        this.a = interfaceC1423Ui;
    }

    @Override // com.android.tools.r8.internal.UA
    public T getValue() {
        T t = (T) this.b;
        V00 v00 = V00.a;
        if (t != v00) {
            return t;
        }
        InterfaceC1423Ui interfaceC1423Ui = this.a;
        if (interfaceC1423Ui != null) {
            T t2 = (T) interfaceC1423Ui.a();
            if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(c, this, v00, t2)) {
                this.a = null;
                return t2;
            }
        }
        return (T) this.b;
    }

    public final String toString() {
        return this.b != V00.a ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }
}
