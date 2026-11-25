package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.utils.C3970u0;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class r5 implements Iterable {
    static final /* synthetic */ boolean c = true;
    private final String a;
    private final Set b;

    protected r5(String str, Supplier supplier) {
        this.a = str;
        this.b = (Set) supplier.get();
    }

    public final boolean a(C0729q2 c0729q2) {
        if (c || c0729q2.getType().H0().equals(this.a)) {
            return this.b.add(c0729q2);
        }
        throw new AssertionError();
    }

    public final boolean b(C0729q2 c0729q2) {
        return this.b.contains(c0729q2);
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.b.iterator();
    }

    public final String toString() {
        return "ProgramPackage(" + C3970u0.n(this.a) + Constant.AFTER_QUTO;
    }

    public final String b() {
        return this.a;
    }

    public final void a(final Consumer consumer) {
        forEach(new Consumer() { // from class: com.android.tools.r8.graph.r5$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                r5.a(consumer, (C0729q2) obj);
            }
        });
    }

    public final void b(final Consumer consumer) {
        forEach(new Consumer() { // from class: com.android.tools.r8.graph.r5$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C0729q2) obj).l((Consumer<? super o5>) consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Consumer consumer, C0729q2 c0729q2) {
        c0729q2.getClass();
        c0729q2.e(consumer, CM.b());
    }

    public final AbstractC1048Hm a() {
        return AbstractC1048Hm.a(this.b);
    }
}
