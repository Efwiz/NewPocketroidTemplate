package com.android.tools.r8.internal;

import java.io.Serializable;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class L10 implements UA, Serializable {
    private InterfaceC1423Ui a;
    private Object b = V00.a;

    public L10(InterfaceC1423Ui interfaceC1423Ui) {
        this.a = interfaceC1423Ui;
    }

    @Override // com.android.tools.r8.internal.UA
    public final Object getValue() {
        if (this.b == V00.a) {
            InterfaceC1423Ui interfaceC1423Ui = this.a;
            AbstractC0936Ds.a(interfaceC1423Ui);
            this.b = interfaceC1423Ui.a();
            this.a = null;
        }
        return this.b;
    }

    public final String toString() {
        return this.b != V00.a ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }
}
