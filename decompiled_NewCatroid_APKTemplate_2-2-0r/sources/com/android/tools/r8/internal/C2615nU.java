package com.android.tools.r8.internal;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2615nU extends Y5 {
    private static final int[] i;
    private final int c;
    private final Y5 d;
    private final Y5 e;
    private final int f;
    private final int g;
    private int h;

    static {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        int i3 = 1;
        while (i2 > 0) {
            arrayList.add(Integer.valueOf(i2));
            int i4 = i3 + i2;
            i3 = i2;
            i2 = i4;
        }
        arrayList.add(Integer.MAX_VALUE);
        i = new int[arrayList.size()];
        int i5 = 0;
        while (true) {
            int[] iArr = i;
            if (i5 >= iArr.length) {
                return;
            }
            iArr[i5] = ((Integer) arrayList.get(i5)).intValue();
            i5++;
        }
    }

    /* synthetic */ C2615nU(Y5 y5, Y5 y6, int i2) {
        this(y5, y6);
    }

    @Override // com.android.tools.r8.internal.Y5
    public final boolean c() {
        int iB = this.d.b(0, 0, this.f);
        Y5 y5 = this.e;
        return y5.b(iB, 0, y5.size()) == 0;
    }

    @Override // com.android.tools.r8.internal.Y5
    protected final int e() {
        return this.h;
    }

    public final boolean equals(Object obj) {
        int iE;
        if (obj == this) {
            return true;
        }
        int i2 = 0;
        if (!(obj instanceof Y5)) {
            return false;
        }
        Y5 y5 = (Y5) obj;
        if (this.c != y5.size()) {
            return false;
        }
        if (this.c == 0) {
            return true;
        }
        if (this.h != 0 && (iE = y5.e()) != 0 && this.h != iE) {
            return false;
        }
        C2466lU c2466lU = new C2466lU(this, i2);
        C2904rC c2904rCA = c2466lU.next();
        C2466lU c2466lU2 = new C2466lU(y5, i2);
        C2904rC c2904rCA2 = c2466lU2.next();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int length = c2904rCA.c.length - i3;
            int length2 = c2904rCA2.c.length - i4;
            int iMin = Math.min(length, length2);
            if (!(i3 == 0 ? c2904rCA.a(c2904rCA2, i4, iMin) : c2904rCA2.a(c2904rCA, i3, iMin))) {
                return false;
            }
            i5 += iMin;
            int i6 = this.c;
            if (i5 >= i6) {
                if (i5 == i6) {
                    return true;
                }
                throw new IllegalStateException();
            }
            if (iMin == length) {
                c2904rCA = c2466lU.next();
                i3 = 0;
            } else {
                i3 += iMin;
            }
            if (iMin == length2) {
                c2904rCA2 = c2466lU2.next();
                i4 = 0;
            } else {
                i4 += iMin;
            }
        }
    }

    public final int hashCode() {
        int iA = this.h;
        if (iA == 0) {
            int i2 = this.c;
            iA = a(i2, 0, i2);
            if (iA == 0) {
                iA = 1;
            }
            this.h = iA;
        }
        return iA;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new C2540mU(this, 0);
    }

    @Override // com.android.tools.r8.internal.Y5
    public final int size() {
        return this.c;
    }

    private C2615nU(Y5 y5, Y5 y6) {
        this.h = 0;
        this.d = y5;
        this.e = y6;
        int size = y5.size();
        this.f = size;
        this.c = y6.size() + size;
        this.g = Math.max(y5.a(), y6.a()) + 1;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0071  */
    /* JADX WARN: Code duplicated, block: B:27:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:28:0x00b4  */
    static Y5 a(Y5 y5, Y5 y6) {
        C2615nU c2615nU = y5 instanceof C2615nU ? (C2615nU) y5 : null;
        if (y6.size() == 0) {
            return y5;
        }
        if (y5.size() != 0) {
            int size = y6.size() + y5.size();
            int i2 = 0;
            if (size < 128) {
                int size2 = y5.size();
                int size3 = y6.size();
                byte[] bArr = new byte[size2 + size3];
                y5.a(0, 0, size2, bArr);
                y6.a(0, size2, size3, bArr);
                return new C2904rC(bArr);
            }
            if (c2615nU == null) {
                if (c2615nU != null) {
                }
                if (size >= i[Math.max(y5.a(), y6.a()) + 1]) {
                }
            }
            if (y6.size() + c2615nU.e.size() < 128) {
                Y5 y7 = c2615nU.e;
                int size4 = y7.size();
                int size5 = y6.size();
                byte[] bArr2 = new byte[size4 + size5];
                y7.a(0, 0, size4, bArr2);
                y6.a(0, size4, size5, bArr2);
                y6 = new C2615nU(c2615nU.d, new C2904rC(bArr2));
            } else {
                if (c2615nU != null || c2615nU.d.a() <= c2615nU.e.a() || c2615nU.g <= y6.a()) {
                    return size >= i[Math.max(y5.a(), y6.a()) + 1] ? new C2615nU(y5, y6) : C2387kU.a(new C2387kU(i2), y5, y6);
                }
                y6 = new C2615nU(c2615nU.d, new C2615nU(c2615nU.e, y6));
            }
        }
        return y6;
    }

    @Override // com.android.tools.r8.internal.Y5
    protected final boolean b() {
        return this.c >= i[this.g];
    }

    @Override // com.android.tools.r8.internal.Y5
    protected final void b(int i2, int i3, int i4, byte[] bArr) {
        int i5 = i2 + i4;
        int i6 = this.f;
        if (i5 <= i6) {
            this.d.b(i2, i3, i4, bArr);
        } else {
            if (i2 >= i6) {
                this.e.b(i2 - i6, i3, i4, bArr);
                return;
            }
            int i7 = i6 - i2;
            this.d.b(i2, i3, i7, bArr);
            this.e.b(0, i3 + i7, i4 - i7, bArr);
        }
    }

    @Override // com.android.tools.r8.internal.Y5
    protected final int b(int i2, int i3, int i4) {
        int i5 = i3 + i4;
        int i6 = this.f;
        if (i5 <= i6) {
            return this.d.b(i2, i3, i4);
        }
        if (i3 >= i6) {
            return this.e.b(i2, i3 - i6, i4);
        }
        int i7 = i6 - i3;
        return this.e.b(this.d.b(i2, i3, i7), 0, i4 - i7);
    }

    @Override // com.android.tools.r8.internal.Y5
    protected final int a() {
        return this.g;
    }

    @Override // com.android.tools.r8.internal.Y5
    final void a(OutputStream outputStream, int i2, int i3) {
        int i4 = i2 + i3;
        int i5 = this.f;
        if (i4 <= i5) {
            this.d.a(outputStream, i2, i3);
        } else {
            if (i2 >= i5) {
                this.e.a(outputStream, i2 - i5, i3);
                return;
            }
            int i6 = i5 - i2;
            this.d.a(outputStream, i2, i6);
            this.e.a(outputStream, 0, i3 - i6);
        }
    }

    @Override // com.android.tools.r8.internal.Y5
    protected final int a(int i2, int i3, int i4) {
        int i5 = i3 + i4;
        int i6 = this.f;
        if (i5 <= i6) {
            return this.d.a(i2, i3, i4);
        }
        if (i3 >= i6) {
            return this.e.a(i2, i3 - i6, i4);
        }
        int i7 = i6 - i3;
        return this.e.a(this.d.a(i2, i3, i7), 0, i4 - i7);
    }

    @Override // com.android.tools.r8.internal.Y5
    public final String f() {
        byte[] bArr;
        int i2 = this.c;
        if (i2 == 0) {
            bArr = AbstractC3019ss.a;
        } else {
            byte[] bArr2 = new byte[i2];
            b(0, 0, i2, bArr2);
            bArr = bArr2;
        }
        return new String(bArr, "UTF-8");
    }
}
