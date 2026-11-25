package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1899eF {
    protected final int h;
    protected AbstractC1899eF i;

    protected AbstractC1899eF(int i) {
        this(i, null);
    }

    public AbstractC2509m2 a() {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            return abstractC1899eF.a();
        }
        return null;
    }

    public void b(int i, String str) {
        if (this.h < 327680) {
            throw new UnsupportedOperationException("This feature requires ASM5");
        }
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.b(i, str);
        }
    }

    public AbstractC2509m2 c(int i, C00 c00, String str, boolean z) {
        if (this.h < 327680) {
            throw new UnsupportedOperationException("This feature requires ASM5");
        }
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            return abstractC1899eF.c(i, c00, str, z);
        }
        return null;
    }

    public void d(int i, int i2) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.d(i, i2);
        }
    }

    protected AbstractC1899eF(int i, AbstractC1899eF abstractC1899eF) {
        if (i != 589824 && i != 524288 && i != 458752 && i != 393216 && i != 327680 && i != 262144 && i != 17432576) {
            throw new IllegalArgumentException(AbstractC1857dg.a("Unsupported api ", i));
        }
        if (i == 17432576) {
            AbstractC1258Pc.a(this);
        }
        this.h = i;
        this.i = abstractC1899eF;
    }

    public AbstractC2509m2 a(String str, boolean z) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            return abstractC1899eF.a(str, z);
        }
        return null;
    }

    public void a(int i, boolean z) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(i, z);
        }
    }

    public AbstractC2509m2 a(int i, String str, boolean z) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            return abstractC1899eF.a(i, str, z);
        }
        return null;
    }

    public void b() {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.b();
        }
    }

    public void c(int i, String str) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.c(i, str);
        }
    }

    public void a(T3 t3) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(t3);
        }
    }

    public void b(int i, int i2) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.b(i, i2);
        }
    }

    public void c(int i, int i2) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.c(i, i2);
        }
    }

    public void a(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(i, i2, objArr, i3, objArr2);
        }
    }

    public AbstractC2509m2 b(int i, C00 c00, String str, boolean z) {
        if (this.h >= 327680) {
            AbstractC1899eF abstractC1899eF = this.i;
            if (abstractC1899eF != null) {
                return abstractC1899eF.b(i, c00, str, z);
            }
            return null;
        }
        throw new UnsupportedOperationException("This feature requires ASM5");
    }

    public void c() {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.c();
        }
    }

    public void a(int i) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(i);
        }
    }

    public void a(int i, String str, String str2, String str3) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(i, str, str2, str3);
        }
    }

    public void a(int i, String str, String str2, String str3, boolean z) {
        int i2 = this.h;
        if (i2 < 327680 && (i & 256) == 0) {
            if (z == (i == 185)) {
                a((i2 < 327680 ? 256 : 0) | i, str, str2, str3, i == 185);
                return;
            }
            throw new UnsupportedOperationException("INVOKESPECIAL/STATIC on interfaces requires ASM5");
        }
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(i & (-257), str, str2, str3, z);
        }
    }

    public void b(int i, C3263wA c3263wA) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.b(i, c3263wA);
        }
    }

    public void a(String str, String str2, C1944ek c1944ek, Object... objArr) {
        if (this.h >= 327680) {
            AbstractC1899eF abstractC1899eF = this.i;
            if (abstractC1899eF != null) {
                abstractC1899eF.a(str, str2, c1944ek, objArr);
                return;
            }
            return;
        }
        throw new UnsupportedOperationException("This feature requires ASM5");
    }

    public void a(int i, C3263wA c3263wA) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(i, c3263wA);
        }
    }

    public void a(C3263wA c3263wA) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(c3263wA);
        }
    }

    public void a(Object obj) {
        if (this.h < 327680 && ((obj instanceof C1944ek) || ((obj instanceof CZ) && ((CZ) obj).e() == 11))) {
            throw new UnsupportedOperationException("This feature requires ASM5");
        }
        if (this.h < 458752 && (obj instanceof C1064Ic)) {
            throw new UnsupportedOperationException("This feature requires ASM7");
        }
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(obj);
        }
    }

    public void a(int i, int i2) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(i, i2);
        }
    }

    public void a(int i, int i2, C3263wA c3263wA, C3263wA... c3263wAArr) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(i, i2, c3263wA, c3263wAArr);
        }
    }

    public void a(C3263wA c3263wA, int[] iArr, C3263wA[] c3263wAArr) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(c3263wA, iArr, c3263wAArr);
        }
    }

    public void a(int i, String str) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(i, str);
        }
    }

    public AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        if (this.h >= 327680) {
            AbstractC1899eF abstractC1899eF = this.i;
            if (abstractC1899eF != null) {
                return abstractC1899eF.a(i, c00, str, z);
            }
            return null;
        }
        throw new UnsupportedOperationException("This feature requires ASM5");
    }

    public void a(C3263wA c3263wA, C3263wA c3263wA2, C3263wA c3263wA3, String str) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(c3263wA, c3263wA2, c3263wA3, str);
        }
    }

    public void a(String str, String str2, String str3, C3263wA c3263wA, C3263wA c3263wA2, int i) {
        AbstractC1899eF abstractC1899eF = this.i;
        if (abstractC1899eF != null) {
            abstractC1899eF.a(str, str2, str3, c3263wA, c3263wA2, i);
        }
    }

    public AbstractC2509m2 a(int i, C00 c00, C3263wA[] c3263wAArr, C3263wA[] c3263wAArr2, int[] iArr, String str, boolean z) {
        if (this.h >= 327680) {
            AbstractC1899eF abstractC1899eF = this.i;
            if (abstractC1899eF != null) {
                return abstractC1899eF.a(i, c00, c3263wAArr, c3263wAArr2, iArr, str, z);
            }
            return null;
        }
        throw new UnsupportedOperationException("This feature requires ASM5");
    }
}
