package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0403b;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class S extends AbstractC2963s10 {
    private int a = 2;
    private Object b;

    protected S() {
    }

    protected abstract Object a();

    protected final void b() {
        this.a = 3;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i = this.a;
        boolean z = i != 4;
        int i2 = AbstractC3123uM.a;
        if (!z) {
            throw new IllegalStateException();
        }
        int iB = AbstractC0403b.b(i);
        if (iB == 0) {
            return true;
        }
        if (iB == 2) {
            return false;
        }
        this.a = 4;
        this.b = a();
        if (this.a == 3) {
            return false;
        }
        this.a = 1;
        return true;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.a = 2;
        Object obj = this.b;
        this.b = null;
        return obj;
    }
}
