package com.android.tools.r8.dex;

import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0676i2;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class A {
    private int a;
    private final AbstractC0676i2[] b;
    private final Supplier c;

    private A(AbstractC0676i2[] abstractC0676i2Arr, Supplier supplier) {
        this.a = 0;
        this.b = abstractC0676i2Arr;
        this.c = supplier;
    }

    final AbstractC0594a1 a(AbstractC0642e2 abstractC0642e2) {
        while (true) {
            int i = this.a;
            AbstractC0676i2[] abstractC0676i2Arr = this.b;
            if (i >= abstractC0676i2Arr.length || abstractC0676i2Arr[i].a.compareTo(abstractC0642e2) >= 0) {
                break;
            }
            this.a++;
        }
        int i2 = this.a;
        AbstractC0676i2[] abstractC0676i2Arr2 = this.b;
        return (i2 >= abstractC0676i2Arr2.length || !abstractC0676i2Arr2[i2].a.equals(abstractC0642e2)) ? (AbstractC0594a1) this.c.get() : this.b[this.a].b;
    }
}
