package com.android.tools.r8.internal;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.em, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1946em {
    Comparator a;
    Map.Entry[] b;
    int c;
    boolean d;

    public C1946em() {
        this(4);
    }

    public C1946em a(Object obj, Object obj2) {
        int i = this.c + 1;
        Map.Entry[] entryArr = this.b;
        if (i > entryArr.length) {
            this.b = (Map.Entry[]) Arrays.copyOf(entryArr, AbstractC1241Ol.a(entryArr.length, i));
            this.d = false;
        }
        AbstractC2625nb.a(obj, obj2);
        AbstractMap.SimpleImmutableEntry simpleImmutableEntry = new AbstractMap.SimpleImmutableEntry(obj, obj2);
        Map.Entry[] entryArr2 = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        entryArr2[i2] = simpleImmutableEntry;
        return this;
    }

    C1946em(int i) {
        this.b = new Map.Entry[i];
        this.c = 0;
        this.d = false;
    }

    public C1946em a(Comparator comparator) {
        AbstractC3123uM.b("valueComparator was already set", this.a == null);
        if (comparator != null) {
            this.a = comparator;
            return this;
        }
        throw new NullPointerException("valueComparator");
    }

    public AbstractC2178hm a() {
        OK c1063Ib;
        if (this.a != null) {
            if (this.d) {
                this.b = (Map.Entry[]) Arrays.copyOf(this.b, this.c);
            }
            Map.Entry[] entryArr = this.b;
            int i = this.c;
            Comparator comparator = this.a;
            if (comparator instanceof OK) {
                c1063Ib = (OK) comparator;
            } else {
                c1063Ib = new C1063Ib(comparator);
            }
            KD kd = LD.a;
            c1063Ib.getClass();
            Arrays.sort(entryArr, 0, i, new P5(kd, c1063Ib));
        }
        int i2 = this.c;
        if (i2 == 0) {
            return PS.h;
        }
        if (i2 != 1) {
            this.d = true;
            return PS.a(i2, this.b);
        }
        return new C3439yV(this.b[0].getKey(), this.b[0].getValue());
    }
}
