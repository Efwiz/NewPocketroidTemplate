package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1507Xl extends AbstractC1241Ol {
    Object[] a;
    private int b;
    private boolean c;

    public C1507Xl() {
        this(4);
    }

    public final void a(Object... objArr) {
        XJ.a(objArr.length, objArr);
        int length = objArr.length;
        a(this.b + length);
        System.arraycopy(objArr, 0, this.a, this.b, length);
        this.b += length;
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C1507Xl a(Object obj) {
        int i = AbstractC3123uM.a;
        obj.getClass();
        a(this.b + 1);
        Object[] objArr = this.a;
        int i2 = this.b;
        this.b = i2 + 1;
        objArr[i2] = obj;
        return this;
    }

    C1507Xl(int i) {
        this.a = new Object[i];
        this.b = 0;
    }

    private void a(int i) {
        Object[] objArr = this.a;
        if (objArr.length < i) {
            this.a = Arrays.copyOf(objArr, AbstractC1241Ol.a(objArr.length, i));
            this.c = false;
        } else if (this.c) {
            this.a = Arrays.copyOf(objArr, objArr.length);
            this.c = false;
        }
    }

    public final C1507Xl a(Iterable iterable) {
        int i = AbstractC3123uM.a;
        iterable.getClass();
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            a(collection.size() + this.b);
            if (collection instanceof AbstractC1271Pl) {
                this.b = ((AbstractC1271Pl) collection).a(this.b, this.a);
                return this;
            }
        }
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            a(it.next());
        }
        return this;
    }

    public final AbstractC1639am a() {
        this.c = true;
        return AbstractC1639am.b(this.b, this.a);
    }
}
