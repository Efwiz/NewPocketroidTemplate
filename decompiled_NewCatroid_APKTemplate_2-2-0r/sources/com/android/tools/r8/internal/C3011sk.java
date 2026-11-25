package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3011sk extends OD implements G4, Serializable {
    private transient C2328jk[] a;
    private transient C2328jk[] b;
    private transient C2328jk c;
    private transient C2328jk d;
    private transient int e;
    private transient int f;
    private transient int g;
    private transient G4 h;

    private C3011sk(int i) {
        a(i);
    }

    public static C3011sk l() {
        return new C3011sk(16);
    }

    private void m() {
        C2328jk[] c2328jkArr = this.a;
        int i = this.e;
        int length = c2328jkArr.length;
        if (i <= ((double) length) * 1.0d || length >= 1073741824) {
            return;
        }
        int length2 = c2328jkArr.length * 2;
        this.a = new C2328jk[length2];
        this.b = new C2328jk[length2];
        this.f = length2 - 1;
        this.e = 0;
        for (C2328jk c2328jk = this.c; c2328jk != null; c2328jk = c2328jk.g) {
            a(c2328jk, c2328jk);
        }
        this.g++;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        this.e = 0;
        Arrays.fill(this.a, (Object) null);
        Arrays.fill(this.b, (Object) null);
        this.c = null;
        this.d = null;
        this.g++;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        return a(AbstractC0900Ck.a(obj), obj) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsValue(Object obj) {
        return b(AbstractC0900Ck.a(obj), obj) != null;
    }

    @Override // com.android.tools.r8.internal.OD
    final Iterator e() {
        return new C2254ik(this);
    }

    @Override // com.android.tools.r8.internal.OD, java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        return new ND(this);
    }

    @Override // java.util.Map
    public final void forEach(BiConsumer biConsumer) {
        int i = AbstractC3123uM.a;
        biConsumer.getClass();
        for (C2328jk c2328jk = this.c; c2328jk != null; c2328jk = c2328jk.g) {
            biConsumer.accept(c2328jk.a, c2328jk.b);
        }
    }

    @Override // com.android.tools.r8.internal.G4
    public final G4 g() {
        G4 g4 = this.h;
        if (g4 != null) {
            return g4;
        }
        C2725ok c2725ok = new C2725ok(this, 0);
        this.h = c2725ok;
        return c2725ok;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        C2328jk c2328jkA = a(AbstractC0900Ck.a(obj), obj);
        if (c2328jkA == null) {
            return null;
        }
        return c2328jkA.getValue();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        return new C2942rk(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        return a(obj, obj2, false);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        C2328jk c2328jkA = a(AbstractC0900Ck.a(obj), obj);
        if (c2328jkA == null) {
            return null;
        }
        a(c2328jkA);
        c2328jkA.h = null;
        c2328jkA.g = null;
        return c2328jkA.b;
    }

    @Override // java.util.Map
    public final void replaceAll(BiFunction biFunction) {
        int i = AbstractC3123uM.a;
        biFunction.getClass();
        clear();
        for (C2328jk c2328jk = this.c; c2328jk != null; c2328jk = c2328jk.g) {
            Object obj = c2328jk.a;
            a(obj, biFunction.apply(obj, c2328jk.b), false);
        }
    }

    @Override // com.android.tools.r8.internal.OD, java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.e;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection values() {
        return g().keySet();
    }

    @Override // java.util.AbstractMap, java.util.Map, com.android.tools.r8.internal.G4
    public final Set values() {
        return new C2636nk((C2725ok) g());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C2328jk b(int i, Object obj) {
        for (C2328jk c2328jk = this.b[this.f & i]; c2328jk != null; c2328jk = c2328jk.f) {
            if (i == c2328jk.d && AbstractC3428yK.a(obj, c2328jk.b)) {
                return c2328jk;
            }
        }
        return null;
    }

    static Object a(C3011sk c3011sk, Object obj, Object obj2, boolean z) {
        c3011sk.getClass();
        int iA = AbstractC0900Ck.a(obj);
        int iA2 = AbstractC0900Ck.a(obj2);
        C2328jk c2328jkB = c3011sk.b(iA, obj);
        C2328jk c2328jkA = c3011sk.a(iA2, obj2);
        if (c2328jkB != null && iA2 == c2328jkB.c && AbstractC3428yK.a(obj2, c2328jkB.a)) {
            return obj2;
        }
        if (c2328jkA != null && !z) {
            String strValueOf = String.valueOf(obj2);
            throw new IllegalArgumentException(new StringBuilder(strValueOf.length() + 21).append("key already present: ").append(strValueOf).toString());
        }
        if (c2328jkB != null) {
            c3011sk.a(c2328jkB);
        }
        if (c2328jkA != null) {
            c3011sk.a(c2328jkA);
        }
        c3011sk.a(new C2328jk(obj2, iA2, obj, iA), c2328jkA);
        if (c2328jkA != null) {
            c2328jkA.h = null;
            c2328jkA.g = null;
        }
        if (c2328jkB != null) {
            c2328jkB.h = null;
            c2328jkB.g = null;
        }
        c3011sk.m();
        return RD.a(c2328jkB);
    }

    public static C3011sk a(Map map) {
        C3011sk c3011sk = new C3011sk(map.size());
        c3011sk.putAll(map);
        return c3011sk;
    }

    private void a(int i) {
        AbstractC2625nb.a(i, "expectedSize");
        int iA = AbstractC0900Ck.a(i, 1.0d);
        this.a = new C2328jk[iA];
        this.b = new C2328jk[iA];
        this.c = null;
        this.d = null;
        this.e = 0;
        this.f = iA - 1;
        this.g = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2328jk c2328jk) {
        C2328jk c2328jk2;
        int i = c2328jk.c & this.f;
        C2328jk c2328jk3 = null;
        C2328jk c2328jk4 = null;
        for (C2328jk c2328jk5 = this.a[i]; c2328jk5 != c2328jk; c2328jk5 = c2328jk5.e) {
            c2328jk4 = c2328jk5;
        }
        if (c2328jk4 == null) {
            this.a[i] = c2328jk.e;
        } else {
            c2328jk4.e = c2328jk.e;
        }
        int i2 = c2328jk.d & this.f;
        C2328jk c2328jk6 = this.b[i2];
        while (true) {
            c2328jk2 = c2328jk3;
            c2328jk3 = c2328jk6;
            if (c2328jk3 == c2328jk) {
                break;
            } else {
                c2328jk6 = c2328jk3.f;
            }
        }
        if (c2328jk2 == null) {
            this.b[i2] = c2328jk.f;
        } else {
            c2328jk2.f = c2328jk.f;
        }
        C2328jk c2328jk7 = c2328jk.h;
        if (c2328jk7 == null) {
            this.c = c2328jk.g;
        } else {
            c2328jk7.g = c2328jk.g;
        }
        C2328jk c2328jk8 = c2328jk.g;
        if (c2328jk8 == null) {
            this.d = c2328jk7;
        } else {
            c2328jk8.h = c2328jk7;
        }
        this.e--;
        this.g++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2328jk c2328jk, C2328jk c2328jk2) {
        int i = c2328jk.c;
        int i2 = this.f;
        int i3 = i & i2;
        C2328jk[] c2328jkArr = this.a;
        c2328jk.e = c2328jkArr[i3];
        c2328jkArr[i3] = c2328jk;
        int i4 = c2328jk.d & i2;
        C2328jk[] c2328jkArr2 = this.b;
        c2328jk.f = c2328jkArr2[i4];
        c2328jkArr2[i4] = c2328jk;
        if (c2328jk2 == null) {
            C2328jk c2328jk3 = this.d;
            c2328jk.h = c2328jk3;
            c2328jk.g = null;
            if (c2328jk3 == null) {
                this.c = c2328jk;
            } else {
                c2328jk3.g = c2328jk;
            }
            this.d = c2328jk;
        } else {
            C2328jk c2328jk4 = c2328jk2.h;
            c2328jk.h = c2328jk4;
            if (c2328jk4 == null) {
                this.c = c2328jk;
            } else {
                c2328jk4.g = c2328jk;
            }
            C2328jk c2328jk5 = c2328jk2.g;
            c2328jk.g = c2328jk5;
            if (c2328jk5 == null) {
                this.d = c2328jk;
            } else {
                c2328jk5.h = c2328jk;
            }
        }
        this.e++;
        this.g++;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C2328jk a(int i, Object obj) {
        for (C2328jk c2328jk = this.a[this.f & i]; c2328jk != null; c2328jk = c2328jk.e) {
            if (i == c2328jk.c && AbstractC3428yK.a(obj, c2328jk.a)) {
                return c2328jk;
            }
        }
        return null;
    }

    private Object a(Object obj, Object obj2, boolean z) {
        int iA = AbstractC0900Ck.a(obj);
        int iA2 = AbstractC0900Ck.a(obj2);
        C2328jk c2328jkA = a(iA, obj);
        if (c2328jkA != null && iA2 == c2328jkA.d && AbstractC3428yK.a(obj2, c2328jkA.b)) {
            return obj2;
        }
        C2328jk c2328jkB = b(iA2, obj2);
        if (c2328jkB != null) {
            if (z) {
                a(c2328jkB);
            } else {
                String strValueOf = String.valueOf(obj2);
                throw new IllegalArgumentException(new StringBuilder(strValueOf.length() + 23).append("value already present: ").append(strValueOf).toString());
            }
        }
        C2328jk c2328jk = new C2328jk(obj, iA, obj2, iA2);
        if (c2328jkA != null) {
            a(c2328jkA);
            a(c2328jk, c2328jkA);
            c2328jkA.h = null;
            c2328jkA.g = null;
            return c2328jkA.b;
        }
        a(c2328jk, (C2328jk) null);
        m();
        return null;
    }

    @Override // com.android.tools.r8.internal.G4
    public final Object a(Object obj, Object obj2) {
        return a(obj, obj2, true);
    }
}
