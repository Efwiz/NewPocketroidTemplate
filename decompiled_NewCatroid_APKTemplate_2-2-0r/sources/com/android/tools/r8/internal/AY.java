package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class AY implements InterfaceC3442yY {
    volatile InterfaceC3442yY a;
    volatile boolean b;
    Object c;

    AY(InterfaceC3442yY interfaceC3442yY) {
        this.a = (InterfaceC3442yY) AbstractC3123uM.a(interfaceC3442yY);
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        if (!this.b) {
            synchronized (this) {
                if (!this.b) {
                    Object obj = this.a.get();
                    this.c = obj;
                    this.b = true;
                    this.a = null;
                    return obj;
                }
            }
        }
        return this.c;
    }

    public final String toString() {
        Object string = this.a;
        if (string == null) {
            String strValueOf = String.valueOf(this.c);
            string = new StringBuilder(strValueOf.length() + 25).append("<supplier that returned ").append(strValueOf).append(">").toString();
        }
        String strValueOf2 = String.valueOf(string);
        return new StringBuilder(strValueOf2.length() + 19).append("Suppliers.memoize(").append(strValueOf2).append(Constant.AFTER_QUTO).toString();
    }
}
