package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class GL {
    private ArrayList a;
    private final EL[] b;
    private final boolean c;

    private GL(boolean z, EL... elArr) {
        this.a = new ArrayList(elArr.length);
        for (int i = 0; i < elArr.length; i++) {
            this.a.add(new ArrayList());
            elArr[i].a(i);
        }
        this.c = z;
        this.b = elArr;
    }

    public static GL a(EL... elArr) {
        return new GL(true, elArr);
    }

    public static GL b(EL... elArr) {
        return new GL(false, elArr);
    }

    public final VD a(final C3486zB c3486zB) {
        return this.c ? a(new Supplier() { // from class: com.android.tools.r8.internal.GL$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return Boolean.valueOf(c3486zB.hasPrevious());
            }
        }, new Supplier() { // from class: com.android.tools.r8.internal.GL$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return c3486zB.previous();
            }
        }, new Runnable() { // from class: com.android.tools.r8.internal.GL$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                c3486zB.next();
            }
        }) : a(new Supplier() { // from class: com.android.tools.r8.internal.GL$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return Boolean.valueOf(c3486zB.hasNext());
            }
        }, new Supplier() { // from class: com.android.tools.r8.internal.GL$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return c3486zB.next();
            }
        }, new Runnable() { // from class: com.android.tools.r8.internal.GL$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                c3486zB.previous();
            }
        });
    }

    private VD a(Supplier supplier, Supplier supplier2, Runnable runnable) {
        if (!((Boolean) supplier.get()).booleanValue()) {
            return null;
        }
        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) supplier2.get();
        int i = 0;
        boolean z = true;
        while (true) {
            EL[] elArr = this.b;
            if (i >= elArr.length) {
                break;
            }
            EL el = elArr[i];
            List list = (List) this.a.get(i);
            list.clear();
            if (abstractC2412ko != null) {
                for (int i2 = 0; i2 < el.c() && el.b().test(abstractC2412ko); i2++) {
                    list.add(abstractC2412ko);
                    if (!((Boolean) supplier.get()).booleanValue()) {
                        abstractC2412ko = null;
                        break;
                    }
                    abstractC2412ko = (AbstractC2412ko) supplier2.get();
                }
            }
            z &= list.size() >= el.a() && list.size() <= el.c();
            if (!z) {
                break;
            }
            i++;
        }
        for (int i3 = 0; i3 < i; i3++) {
            for (int i4 = 0; i4 < ((List) this.a.get(i3)).size(); i4++) {
                runnable.run();
            }
        }
        if (abstractC2412ko != null) {
            runnable.run();
        }
        if (z) {
            return new VD(this.a);
        }
        return null;
    }
}
