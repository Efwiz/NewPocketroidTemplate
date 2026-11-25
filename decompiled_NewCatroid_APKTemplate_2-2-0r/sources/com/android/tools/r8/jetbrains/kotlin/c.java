package com.android.tools.r8.jetbrains.kotlin;

import com.android.tools.r8.internal.AbstractC0936Ds;
import com.android.tools.r8.internal.InterfaceC1423Ui;
import com.android.tools.r8.internal.UA;
import com.android.tools.r8.internal.V00;
import java.io.Serializable;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class c implements UA, Serializable {
    private InterfaceC1423Ui a;
    private volatile Object b = V00.a;
    private final Object c;

    public c(InterfaceC1423Ui interfaceC1423Ui, Object obj) {
        this.a = interfaceC1423Ui;
        this.c = obj == null ? this : obj;
    }

    @Override // com.android.tools.r8.internal.UA
    public final Object getValue() {
        Object objA;
        Object obj = this.b;
        V00 v00 = V00.a;
        if (obj != v00) {
            return obj;
        }
        synchronized (this.c) {
            objA = this.b;
            if (objA == v00) {
                InterfaceC1423Ui interfaceC1423Ui = this.a;
                AbstractC0936Ds.a(interfaceC1423Ui);
                objA = interfaceC1423Ui.a();
                this.b = objA;
                this.a = null;
            }
        }
        return objA;
    }

    public final String toString() {
        return this.b != V00.a ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }
}
