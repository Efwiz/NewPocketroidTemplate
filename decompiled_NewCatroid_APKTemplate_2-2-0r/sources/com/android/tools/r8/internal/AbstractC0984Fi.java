package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Fi, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0984Fi implements Iterable {
    private final LK a = LK.a();

    protected AbstractC0984Fi() {
    }

    public static AbstractC0984Fi a(Iterable iterable, Iterable iterable2) {
        Iterable[] iterableArr = {iterable, iterable2};
        for (int i = 0; i < 2; i++) {
            Iterable iterable3 = iterableArr[i];
            int i2 = AbstractC3123uM.a;
            iterable3.getClass();
        }
        return new C0957Ei(iterableArr);
    }

    public final String toString() {
        Iterator it = ((Iterable) this.a.a(this)).iterator();
        StringBuilder sb = new StringBuilder("[");
        boolean z = true;
        while (it.hasNext()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append(it.next());
            z = false;
        }
        return sb.append(']').toString();
    }

    public static AbstractC0984Fi a(Collection collection, Collection collection2, Collection collection3) {
        Iterable[] iterableArr = {collection, collection2, collection3};
        for (int i = 0; i < 3; i++) {
            Iterable iterable = iterableArr[i];
            int i2 = AbstractC3123uM.a;
            iterable.getClass();
        }
        return new C0957Ei(iterableArr);
    }

    public static AbstractC0984Fi a(ArrayList arrayList, Iterable iterable, Iterable iterable2, Iterable iterable3) {
        Iterable[] iterableArr = {arrayList, iterable, iterable2, iterable3};
        for (int i = 0; i < 4; i++) {
            Iterable iterable4 = iterableArr[i];
            int i2 = AbstractC3123uM.a;
            iterable4.getClass();
        }
        return new C0957Ei(iterableArr);
    }

    public static AbstractC0984Fi a(Iterable... iterableArr) {
        Iterable[] iterableArr2 = (Iterable[]) Arrays.copyOf(iterableArr, iterableArr.length);
        for (Iterable iterable : iterableArr2) {
            int i = AbstractC3123uM.a;
            iterable.getClass();
        }
        return new C0957Ei(iterableArr2);
    }
}
