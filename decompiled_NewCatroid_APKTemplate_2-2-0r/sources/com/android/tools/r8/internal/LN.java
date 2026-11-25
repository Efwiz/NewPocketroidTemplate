package com.android.tools.r8.internal;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.AbstractC0403b;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class LN implements com.android.tools.r8.naming.M {
    private static final byte[] h = "sourceFile".getBytes();
    private final Predicate c;
    private final boolean d;
    private int a = 0;
    private int b = 0;
    private boolean e = false;
    private boolean f = false;
    private int g = 9;

    protected LN(Predicate predicate, boolean z) {
        this.c = predicate;
        this.d = z;
    }

    /* JADX WARN: Code duplicated, block: B:116:0x013f A[PHI: r1
      0x013f: PHI (r1v14 int) = (r1v13 int), (r1v30 int), (r1v32 int), (r1v51 int), (r1v54 int) binds: [B:166:0x01b2, B:127:0x0157, B:101:0x011e, B:82:0x00ea, B:99:0x0118] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:147:0x018b A[PHI: r1
      0x018b: PHI (r1v16 int) = (r1v15 int), (r1v22 int) binds: [B:130:0x0161, B:140:0x017b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:172:0x01c3 A[PHI: r2
      0x01c3: PHI (r2v16 int) = (r2v14 int), (r2v19 int), (r2v21 int), (r2v26 int), (r2v30 int), (r2v32 int) binds: [B:238:0x01c3, B:116:0x013f, B:149:0x018e, B:114:0x013a, B:68:0x00c8, B:75:0x00d8] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.android.tools.r8.naming.M
    public final String a() {
        int i;
        int i2;
        int i3;
        boolean z;
        int i4;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        while (true) {
            this.a = 0;
            this.b = 0;
            byte[] bArr = null;
            do {
                byte[] bArrF = f();
                if (bArrF == null) {
                    break;
                }
                if (b() || bArr != null) {
                    int iC = c() - d();
                    int length = bArr == null ? 0 : bArr.length;
                    int i5 = iC + length;
                    byte[] bArr2 = new byte[i5];
                    if (bArr != null) {
                        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    }
                    System.arraycopy(bArrF, d(), bArr2, length, iC);
                    this.b = i5;
                    bArr = bArr2;
                } else {
                    this.a = d();
                    this.b = c();
                    bArr = bArrF;
                }
            } while (b());
            if (bArr == null) {
                return null;
            }
            if (this.c == null) {
                int i6 = this.a;
                return new String(bArr, i6, this.b - i6, StandardCharsets.UTF_8);
            }
            int i7 = this.a;
            int i8 = this.b;
            int i9 = 1;
            while (i9 != 9 && i9 != 6 && i9 != 8) {
                boolean z6 = IN.a;
                if (!z6 && i9 == 9) {
                    throw new AssertionError();
                }
                int iB = AbstractC0403b.b(i9);
                if (iB == 0) {
                    while (true) {
                        if (i7 >= i8) {
                            i9 = 9;
                            break;
                            break;
                        }
                        if (!Character.isWhitespace(bArr[i7])) {
                            i9 = 2;
                            break;
                        }
                        i7++;
                    }
                } else if (iB == 1) {
                    if (bArr[i7] != 35) {
                        int i10 = i7;
                        while (true) {
                            if (i10 < i8) {
                                byte b = bArr[i10];
                                if (b == 32) {
                                    i = i10 - i7;
                                    break;
                                }
                                if (!Character.isWhitespace(b)) {
                                    i10++;
                                }
                            }
                            i = -1;
                            break;
                        }
                        if (i <= 0) {
                            i2 = i10;
                            i7 = i2;
                            i9 = 9;
                            break;
                            break;
                        }
                        i7 = i10;
                        i9 = 3;
                    } else {
                        i9 = 7;
                    }
                } else if (iB == 2) {
                    int i11 = i7 + 1;
                    if (bArr[i7] == 32) {
                        i3 = i7 + 2;
                        if (bArr[i11] == 45) {
                            i11 = i7 + 3;
                            if (bArr[i3] == 62) {
                                i3 = i7 + 4;
                                if (bArr[i11] == 32) {
                                    z = true;
                                    i7 = i3;
                                }
                            } else {
                                i3 = i11;
                            }
                            i7 = i3;
                            z = false;
                        } else {
                            i7 = i3;
                            z = false;
                        }
                    } else {
                        i3 = i11;
                        i7 = i3;
                        z = false;
                    }
                    if (!z) {
                        i9 = 9;
                        break;
                        break;
                    }
                    i9 = 4;
                } else if (iB == 3) {
                    i2 = i7;
                    while (true) {
                        if (i2 < i8) {
                            byte b2 = bArr[i2];
                            if (b2 == 58) {
                                i4 = i2 - i7;
                                break;
                            }
                            if (!Character.isWhitespace(b2)) {
                                i2++;
                            }
                        }
                        i4 = -1;
                        break;
                    }
                    if (i4 <= 0) {
                        i7 = i2;
                        i9 = 9;
                        break;
                        break;
                    }
                    i9 = 5;
                    i7 = i2;
                } else {
                    if (iB == 4) {
                        i2 = i7 + 1;
                        if (bArr[i7] == 58) {
                            i7 = i2;
                            while (true) {
                                if (i7 >= i8) {
                                    z2 = false;
                                    break;
                                }
                                if (!Character.isWhitespace(bArr[i7])) {
                                    z2 = true;
                                    break;
                                }
                                i7++;
                            }
                            if (z2) {
                                if (bArr[i7] == 35) {
                                }
                            }
                            i9 = 6;
                        } else {
                            i7 = i2;
                        }
                        i9 = 9;
                        break;
                        break;
                    }
                    if (iB == 6) {
                        while (true) {
                            if (i7 >= i8) {
                                z3 = false;
                                break;
                            }
                            int i12 = i7 + 1;
                            if (bArr[i7] == 123) {
                                i7 = i12;
                                z3 = true;
                                break;
                            }
                            i7 = i12;
                        }
                        if (!z3) {
                            i9 = 9;
                            break;
                            break;
                        }
                        while (true) {
                            if (i7 >= i8) {
                                z4 = false;
                                break;
                            }
                            int i13 = i7 + 1;
                            if (bArr[i7] == 58) {
                                i7 = i13;
                                z4 = true;
                                break;
                            }
                            i7 = i13;
                        }
                        if (!z4) {
                            i9 = 9;
                            break;
                            break;
                        }
                        i2 = i7 + 1;
                        byte b3 = bArr[i7];
                        if (b3 == 39 || b3 == 34) {
                            int i14 = i8 - i2;
                            byte[] bArr3 = h;
                            if (i14 < bArr3.length) {
                                i7 = i2;
                            } else {
                                int length2 = bArr3.length + i2;
                                int i15 = 0;
                                while (true) {
                                    if (i2 < length2) {
                                        int i16 = i15 + 1;
                                        if (h[i15] != bArr[i2]) {
                                            i7 = i2;
                                        } else {
                                            i2++;
                                            i15 = i16;
                                        }
                                    } else {
                                        i7 = i2 + 1;
                                        byte b4 = bArr[i2];
                                        z5 = b4 == 39 || b4 == 34;
                                    }
                                }
                            }
                            if (z5) {
                                i9 = 8;
                            } else {
                                i2 = i7;
                            }
                        }
                        i7 = i2;
                        i9 = 9;
                        break;
                    }
                    if (!z6) {
                        if (!(i9 == 9 || i9 == 6 || i9 == 8)) {
                            throw new AssertionError();
                        }
                    }
                    throw new C3405y10("Should not compute next state on terminal state");
                }
            }
            this.g = i9;
            if (i9 == 6) {
                this.f = true;
                int i17 = this.a;
                String str = new String(bArr, i17, this.b - i17, StandardCharsets.UTF_8);
                boolean zTest = this.c.test(AbstractC0402a.a(str, 1, str.indexOf(">") + 2));
                this.e = zTest;
                if (zTest || this.d) {
                    return str;
                }
            } else {
                if (i9 == 8 && this.d) {
                    int i18 = this.a;
                    return new String(bArr, i18, this.b - i18, StandardCharsets.UTF_8);
                }
                if (this.e || (!this.f && this.d)) {
                    int i19 = this.a;
                    return new String(bArr, i19, this.b - i19, StandardCharsets.UTF_8);
                }
            }
        }
    }

    public abstract boolean b();

    public abstract int c();

    public abstract int d();

    public final boolean e() {
        return this.g == 6;
    }

    public abstract byte[] f();
}
