package com.android.tools.r8.internal;

import com.android.dx.cf.attrib.AttBootstrapMethods;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class NY {
    final C1549Za a;
    private int c;
    private String d;
    private int e;
    private int i;
    private C1667b6 j;
    private int k;
    private MY[] l;
    private final C1031Ha b = null;
    private MY[] f = new MY[256];
    private int g = 1;
    private C1667b6 h = new C1667b6();

    NY(C1549Za c1549Za) {
        this.a = c1549Za;
    }

    final int a() {
        if (this.j == null) {
            return 0;
        }
        d(AttBootstrapMethods.ATTRIBUTE_NAME);
        return this.j.b + 8;
    }

    final String b() {
        return this.d;
    }

    final int c(int i, String str) {
        this.c = i;
        this.d = str;
        return a(7, str).a;
    }

    final int d() {
        return this.h.b;
    }

    final int e() {
        return this.c;
    }

    final C1031Ha f() {
        return this.b;
    }

    final void b(C1667b6 c1667b6) {
        C1667b6 c1667b6D = c1667b6.d(this.g);
        C1667b6 c1667b7 = this.h;
        c1667b6D.a(c1667b7.a, 0, c1667b7.b);
    }

    final int d(String str) {
        int iHashCode = (str.hashCode() + 1) & Integer.MAX_VALUE;
        for (MY myB = b(iHashCode); myB != null; myB = myB.i) {
            if (myB.b == 1 && myB.h == iHashCode && myB.e.equals(str)) {
                return myB.a;
            }
        }
        this.h.b(1).a(str);
        int i = this.g;
        this.g = i + 1;
        return a(new MY(i, 1, iHashCode, str)).a;
    }

    final int e(String str) {
        int iHashCode = (str.hashCode() + 128) & Integer.MAX_VALUE;
        for (MY myB = b(iHashCode); myB != null; myB = myB.i) {
            if (myB.b == 128 && myB.h == iHashCode && myB.e.equals(str)) {
                return myB.a;
            }
        }
        int i = this.k;
        MY my = new MY(i, 128, iHashCode, str);
        if (this.l == null) {
            this.l = new MY[16];
        }
        MY[] myArr = this.l;
        if (i == myArr.length) {
            MY[] myArr2 = new MY[myArr.length * 2];
            System.arraycopy(myArr, 0, myArr2, 0, myArr.length);
            this.l = myArr2;
        }
        MY[] myArr3 = this.l;
        int i2 = this.k;
        this.k = i2 + 1;
        myArr3[i2] = my;
        return a(my).a;
    }

    private MY b(int i) {
        MY[] myArr = this.f;
        return myArr[i % myArr.length];
    }

    final void a(C1667b6 c1667b6) {
        if (this.j != null) {
            C1667b6 c1667b6D = c1667b6.d(d(AttBootstrapMethods.ATTRIBUTE_NAME)).c(this.j.b + 2).d(this.i);
            C1667b6 c1667b7 = this.j;
            c1667b6D.a(c1667b7.a, 0, c1667b7.b);
        }
    }

    final MY b(String str) {
        return a(19, str);
    }

    final int b(int i, int i2) {
        long j;
        long j2;
        if (i < i2) {
            j = i;
            j2 = i2;
        } else {
            j = i2;
            j2 = i;
        }
        long j3 = j | (j2 << 32);
        int i3 = (i + i2 + 130) & Integer.MAX_VALUE;
        for (MY myB = b(i3); myB != null; myB = myB.i) {
            if (myB.b == 130 && myB.h == i3 && myB.f == j3) {
                return myB.g;
            }
        }
        MY[] myArr = this.l;
        String strReplace = myArr[i].e;
        String str = myArr[i2].e;
        this.a.getClass();
        ClassLoader classLoader = C1549Za.class.getClassLoader();
        try {
            Class<?> cls = Class.forName(strReplace.replace('/', '.'), false, classLoader);
            try {
                Class<?> cls2 = Class.forName(str.replace('/', '.'), false, classLoader);
                if (!cls.isAssignableFrom(cls2)) {
                    if (cls2.isAssignableFrom(cls)) {
                        strReplace = str;
                    } else if (!cls.isInterface() && !cls2.isInterface()) {
                        do {
                            cls = cls.getSuperclass();
                        } while (!cls.isAssignableFrom(cls2));
                        strReplace = cls.getName().replace('.', '/');
                    } else {
                        strReplace = TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME;
                    }
                }
                int iE = e(strReplace);
                a(new MY(this.k, 130, j3, i3)).g = iE;
                return iE;
            } catch (ClassNotFoundException e) {
                throw new TypeNotPresentException(str, e);
            }
        } catch (ClassNotFoundException e2) {
            throw new TypeNotPresentException(strReplace, e2);
        }
    }

    final int c() {
        return this.g;
    }

    final MY c(String str) {
        return a(20, str);
    }

    final MY c(int i) {
        return this.l[i];
    }

    private MY a(MY my) {
        int i = this.e;
        MY[] myArr = this.f;
        if (i > (myArr.length * 3) / 4) {
            int length = myArr.length;
            int i2 = (length * 2) + 1;
            MY[] myArr2 = new MY[i2];
            for (int i3 = length - 1; i3 >= 0; i3--) {
                MY my2 = this.f[i3];
                while (my2 != null) {
                    int i4 = my2.h % i2;
                    MY my3 = my2.i;
                    my2.i = myArr2[i4];
                    myArr2[i4] = my2;
                    my2 = my3;
                }
            }
            this.f = myArr2;
        }
        this.e++;
        int i5 = my.h;
        MY[] myArr3 = this.f;
        int length2 = i5 % myArr3.length;
        my.i = myArr3[length2];
        myArr3[length2] = my;
        return my;
    }

    final MY a(Object obj) {
        if (obj instanceof Integer) {
            return a(3, ((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return a(3, ((Byte) obj).intValue());
        }
        if (obj instanceof Character) {
            return a(3, (int) ((Character) obj).charValue());
        }
        if (obj instanceof Short) {
            return a(3, ((Short) obj).intValue());
        }
        if (obj instanceof Boolean) {
            return a(3, ((Boolean) obj).booleanValue() ? 1 : 0);
        }
        if (obj instanceof Float) {
            return a(((Float) obj).floatValue());
        }
        if (obj instanceof Long) {
            return a(5, ((Long) obj).longValue());
        }
        if (obj instanceof Double) {
            return a(((Double) obj).doubleValue());
        }
        if (obj instanceof String) {
            return a(8, (String) obj);
        }
        if (obj instanceof CZ) {
            CZ cz = (CZ) obj;
            int iE = cz.e();
            if (iE == 10) {
                return a(7, cz.c());
            }
            if (iE == 11) {
                return a(16, cz.b());
            }
            return a(7, cz.b());
        }
        if (obj instanceof C1944ek) {
            C1944ek c1944ek = (C1944ek) obj;
            return a(c1944ek.d(), c1944ek.c(), c1944ek.b(), c1944ek.a(), c1944ek.e());
        }
        if (obj instanceof C1064Ic) {
            C1064Ic c1064Ic = (C1064Ic) obj;
            return a(17, a(c1064Ic.a(), c1064Ic.c()).a, c1064Ic.e(), c1064Ic.d());
        }
        throw new IllegalArgumentException("value " + obj);
    }

    final int b(int i, String str) {
        int iHashCode = (str.hashCode() + 129 + i) & Integer.MAX_VALUE;
        for (MY myB = b(iHashCode); myB != null; myB = myB.i) {
            if (myB.b == 129 && myB.h == iHashCode && myB.f == i && myB.e.equals(str)) {
                return myB.a;
            }
        }
        int i2 = this.k;
        MY my = new MY(i2, str, i, iHashCode);
        if (this.l == null) {
            this.l = new MY[16];
        }
        MY[] myArr = this.l;
        if (i2 == myArr.length) {
            MY[] myArr2 = new MY[myArr.length * 2];
            System.arraycopy(myArr, 0, myArr2, 0, myArr.length);
            this.l = myArr2;
        }
        MY[] myArr3 = this.l;
        int i3 = this.k;
        this.k = i3 + 1;
        myArr3[i3] = my;
        return a(my).a;
    }

    final MY a(String str) {
        return a(7, str);
    }

    final MY a(String str, String str2, String str3) {
        return a(9, str, str2, str3);
    }

    final MY a(String str, String str2, String str3, boolean z) {
        return a(z ? 11 : 10, str, str2, str3);
    }

    final MY a(int i) {
        return a(3, i);
    }

    final MY a(float f) {
        return a(4, Float.floatToRawIntBits(f));
    }

    private MY a(int i, int i2) {
        int i3 = (i + i2) & Integer.MAX_VALUE;
        for (MY myB = b(i3); myB != null; myB = myB.i) {
            if (myB.b == i && myB.h == i3 && myB.f == i2) {
                return myB;
            }
        }
        this.h.b(i).c(i2);
        int i4 = this.g;
        this.g = i4 + 1;
        return a(new MY(i4, i, i2, i3));
    }

    final MY a(long j) {
        return a(5, j);
    }

    final MY a(double d) {
        return a(6, Double.doubleToRawLongBits(d));
    }

    private MY a(int i, long j) {
        int i2 = (((int) j) + i + ((int) (j >>> 32))) & Integer.MAX_VALUE;
        for (MY myB = b(i2); myB != null; myB = myB.i) {
            if (myB.b == i && myB.h == i2 && myB.f == j) {
                return myB;
            }
        }
        int i3 = this.g;
        this.h.b(i).a(j);
        this.g += 2;
        return a(new MY(i3, i, j, i2));
    }

    final MY a(String str, String str2, C1944ek c1944ek, Object... objArr) {
        return a(18, a(c1944ek, objArr).a, str, str2);
    }

    final MY a(C1944ek c1944ek, Object... objArr) {
        C1667b6 c1667b6 = this.j;
        if (c1667b6 == null) {
            c1667b6 = new C1667b6();
            this.j = c1667b6;
        }
        int length = objArr.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = a(objArr[i]).a;
        }
        int i2 = c1667b6.b;
        c1667b6.d(a(c1944ek.d(), c1944ek.c(), c1944ek.b(), c1944ek.a(), c1944ek.e()).a);
        c1667b6.d(length);
        for (int i3 = 0; i3 < length; i3++) {
            c1667b6.d(iArr[i3]);
        }
        int i4 = c1667b6.b - i2;
        int iHashCode = c1944ek.hashCode();
        for (Object obj : objArr) {
            iHashCode ^= obj.hashCode();
        }
        int i5 = iHashCode & Integer.MAX_VALUE;
        byte[] bArr = this.j.a;
        for (MY myB = b(i5); myB != null; myB = myB.i) {
            if (myB.b == 64 && myB.h == i5) {
                int i6 = (int) myB.f;
                int i7 = 0;
                while (true) {
                    if (i7 < i4) {
                        if (bArr[i2 + i7] != bArr[i6 + i7]) {
                            break;
                        }
                        i7++;
                    } else {
                        this.j.b = i2;
                        return myB;
                    }
                }
            }
        }
        int i8 = this.i;
        this.i = i8 + 1;
        return a(new MY(i8, 64, i2, i5));
    }

    private MY a(int i, String str) {
        int iHashCode = (str.hashCode() + i) & Integer.MAX_VALUE;
        for (MY myB = b(iHashCode); myB != null; myB = myB.i) {
            if (myB.b == i && myB.h == iHashCode && myB.e.equals(str)) {
                return myB;
            }
        }
        this.h.c(i, d(str));
        int i2 = this.g;
        this.g = i2 + 1;
        return a(new MY(i2, i, iHashCode, str));
    }

    final int a(String str, String str2) {
        int iHashCode = ((str2.hashCode() * str.hashCode()) + 12) & Integer.MAX_VALUE;
        for (MY myB = b(iHashCode); myB != null; myB = myB.i) {
            if (myB.b == 12 && myB.h == iHashCode && myB.d.equals(str) && myB.e.equals(str2)) {
                return myB.a;
            }
        }
        this.h.a(12, d(str), d(str2));
        int i = this.g;
        this.g = i + 1;
        return a(new MY(i, iHashCode, str, str2)).a;
    }

    private MY a(int i, int i2, String str, String str2) {
        int iHashCode = (((i2 + 1) * str2.hashCode() * str.hashCode()) + i) & Integer.MAX_VALUE;
        for (MY myB = b(iHashCode); myB != null; myB = myB.i) {
            if (myB.b == i && myB.h == iHashCode && myB.f == i2 && myB.d.equals(str) && myB.e.equals(str2)) {
                return myB;
            }
        }
        this.h.a(i, i2, a(str, str2));
        int i3 = this.g;
        this.g = i3 + 1;
        return a(new MY(i3, i, null, str, str2, i2, iHashCode));
    }

    private MY a(int i, String str, String str2, String str3) {
        int iHashCode = ((str3.hashCode() * str2.hashCode() * str.hashCode()) + i) & Integer.MAX_VALUE;
        for (MY myB = b(iHashCode); myB != null; myB = myB.i) {
            if (myB.b == i && myB.h == iHashCode && myB.c.equals(str) && myB.d.equals(str2) && myB.e.equals(str3)) {
                return myB;
            }
        }
        this.h.a(i, a(7, str).a, a(str2, str3));
        int i2 = this.g;
        this.g = i2 + 1;
        return a(new MY(i2, i, str, str2, str3, 0L, iHashCode));
    }

    final MY a(int i, String str, String str2, String str3, boolean z) {
        int iHashCode = ((str3.hashCode() * str2.hashCode() * str.hashCode() * i) + 15) & Integer.MAX_VALUE;
        for (MY myB = b(iHashCode); myB != null; myB = myB.i) {
            if (myB.b == 15 && myB.h == iHashCode && myB.f == i && myB.c.equals(str) && myB.d.equals(str2) && myB.e.equals(str3)) {
                return myB;
            }
        }
        if (i <= 4) {
            this.h.b(i, a(9, str, str2, str3).a);
        } else {
            this.h.b(i, a(z ? 11 : 10, str, str2, str3).a);
        }
        int i2 = this.g;
        this.g = i2 + 1;
        return a(new MY(i2, 15, str, str2, str3, i, iHashCode));
    }
}
