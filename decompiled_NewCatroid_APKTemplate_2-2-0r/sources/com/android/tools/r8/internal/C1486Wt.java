package com.android.tools.r8.internal;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import okio.internal.BufferKt;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1486Wt implements Closeable {
    private static final char[] p = ")]}'\n".toCharArray();
    private final Reader a;
    private long i;
    private int j;
    private String k;
    private int[] l;
    private String[] n;
    private int[] o;
    private boolean b = false;
    private final char[] c = new char[1024];
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    int h = 0;
    private int m = 1;

    static {
        AbstractC1515Xt.a = new C1460Vt();
    }

    public C1486Wt(StringReader stringReader) {
        int[] iArr = new int[32];
        this.l = iArr;
        iArr[0] = 6;
        this.n = new String[32];
        this.o = new int[32];
        this.a = stringReader;
    }

    private void e() throws C2905rD {
        if (this.b) {
            return;
        }
        b("Use JsonReader.setLenient(true) to accept malformed JSON");
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String k() {
        int i = this.f + 1;
        StringBuilder sbAppend = new StringBuilder(" at line ").append(i).append(" column ").append((this.d - this.g) + 1).append(" path ");
        StringBuilder sb = new StringBuilder("$");
        int i2 = this.m;
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = this.l[i3];
            if (i4 == 1 || i4 == 2) {
                sb.append('[').append(this.o[i3]).append(']');
            } else if (i4 == 3 || i4 == 4 || i4 == 5) {
                sb.append('.');
                String str = this.n[i3];
                if (str != null) {
                    sb.append(str);
                }
            }
        }
        return sbAppend.append(sb.toString()).toString();
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:32:0x0042. Please report as an issue. */
    private String s() throws C2905rD {
        String string;
        StringBuilder sb = null;
        int i = 0;
        while (true) {
            int i2 = 0;
            while (true) {
                int i3 = this.d + i2;
                if (i3 < this.e) {
                    char c = this.c[i3];
                    if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                        if (c != '#') {
                            if (c != ',') {
                                if (c != '/' && c != '=') {
                                    if (c != '{' && c != '}' && c != ':') {
                                        if (c != ';') {
                                            switch (c) {
                                                case '[':
                                                case ']':
                                                    break;
                                                case '\\':
                                                    break;
                                                default:
                                                    i2++;
                                                    break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        e();
                    }
                    i = i2;
                } else if (i2 >= this.c.length) {
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    sb.append(this.c, this.d, i2);
                    this.d += i2;
                    if (!a(1)) {
                    }
                } else if (!a(i2 + 1)) {
                    i = i2;
                }
                if (sb == null) {
                    string = new String(this.c, this.d, i);
                } else {
                    sb.append(this.c, this.d, i);
                    string = sb.toString();
                }
                this.d += i;
                return string;
            }
        }
    }

    private char u() throws C2905rD {
        int i;
        if (this.d == this.e && !a(1)) {
            b("Unterminated escape sequence");
            throw null;
        }
        char[] cArr = this.c;
        int i2 = this.d;
        int i3 = i2 + 1;
        this.d = i3;
        char c = cArr[i2];
        if (c == '\n') {
            this.f++;
            this.g = i3;
        } else if (c != '\"' && c != '\'' && c != '/' && c != '\\') {
            if (c == 'b') {
                return '\b';
            }
            if (c == 'f') {
                return '\f';
            }
            if (c == 'n') {
                return '\n';
            }
            if (c == 'r') {
                return '\r';
            }
            if (c == 't') {
                return '\t';
            }
            if (c != 'u') {
                b("Invalid escape sequence");
                throw null;
            }
            if (i2 + 5 > this.e && !a(4)) {
                b("Unterminated escape sequence");
                throw null;
            }
            int i4 = this.d;
            int i5 = i4 + 4;
            char c2 = 0;
            while (i4 < i5) {
                char c3 = this.c[i4];
                char c4 = (char) (c2 << 4);
                if (c3 >= '0' && c3 <= '9') {
                    i = c3 - '0';
                } else if (c3 >= 'a' && c3 <= 'f') {
                    i = c3 - 'W';
                } else {
                    if (c3 < 'A' || c3 > 'F') {
                        throw new NumberFormatException("\\u".concat(new String(this.c, this.d, 4)));
                    }
                    i = c3 - '7';
                }
                c2 = (char) (i + c4);
                i4++;
            }
            this.d += 4;
            return c2;
        }
        return c;
    }

    public final void b(boolean z) {
        this.b = z;
    }

    public final void c() throws IOException {
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        if (iF != 3) {
            throw new IllegalStateException("Expected BEGIN_ARRAY but was " + AbstractC1568Zt.a(t()) + k());
        }
        b(1);
        this.o[this.m - 1] = 0;
        this.h = 0;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.h = 0;
        this.l[0] = 8;
        this.m = 1;
        this.a.close();
    }

    public final void d() throws IOException {
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        if (iF != 1) {
            throw new IllegalStateException("Expected BEGIN_OBJECT but was " + AbstractC1568Zt.a(t()) + k());
        }
        b(3);
        this.h = 0;
    }

    /* JADX WARN: Code duplicated, block: B:127:0x0196  */
    /* JADX WARN: Code duplicated, block: B:130:0x01a2 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:131:0x01a3  */
    /* JADX WARN: Code duplicated, block: B:134:0x01b5  */
    /* JADX WARN: Code duplicated, block: B:137:0x01ba  */
    /* JADX WARN: Code duplicated, block: B:140:0x01c4  */
    /* JADX WARN: Code duplicated, block: B:141:0x01c8 A[PHI: r2 r4
      0x01c8: PHI (r2v53 int) = (r2v52 int), (r2v69 int) binds: [B:133:0x01b3, B:140:0x01c4] A[DONT_GENERATE, DONT_INLINE]
      0x01c8: PHI (r4v6 int) = (r4v5 int), (r4v11 int) binds: [B:133:0x01b3, B:140:0x01c4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:143:0x01d0  */
    /* JADX WARN: Code duplicated, block: B:145:0x01d4  */
    /* JADX WARN: Code duplicated, block: B:205:0x026c  */
    /* JADX WARN: Code duplicated, block: B:206:0x026f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:207:0x0271 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:215:0x028d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:216:0x028f  */
    /* JADX WARN: Code duplicated, block: B:229:0x02b1  */
    /* JADX WARN: Code duplicated, block: B:231:0x02b6  */
    /* JADX WARN: Code duplicated, block: B:234:0x02bb  */
    /* JADX WARN: Code duplicated, block: B:240:0x02cd A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:241:0x02ce  */
    /* JADX WARN: Code duplicated, block: B:243:0x02d8  */
    /* JADX WARN: Code duplicated, block: B:253:0x02ec  */
    /* JADX WARN: Code duplicated, block: B:254:0x02ee  */
    /* JADX WARN: Code duplicated, block: B:256:0x02f2 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:268:0x030f  */
    /* JADX WARN: Code duplicated, block: B:339:0x03c5  */
    /* JADX WARN: Code duplicated, block: B:351:0x02ca A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:352:0x026f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:359:0x02ca A[SYNTHETIC] */
    final int f() throws IOException {
        int iA;
        int i;
        String str;
        String str2;
        int i2;
        char c;
        char c2;
        char[] cArr;
        int i3;
        int i4;
        char c3;
        int i5;
        char c4;
        int i6;
        char c5;
        int i7;
        char c6;
        int[] iArr = this.l;
        boolean z = true;
        int i8 = this.m - 1;
        int i9 = iArr[i8];
        if (i9 == 1) {
            iArr[i8] = 2;
        } else if (i9 == 2) {
            int iA2 = a(true);
            if (iA2 != 44) {
                if (iA2 != 59) {
                    if (iA2 == 93) {
                        this.h = 4;
                        return 4;
                    }
                    b("Unterminated array");
                    throw null;
                }
                e();
            }
        } else {
            if (i9 == 3 || i9 == 5) {
                iArr[i8] = 4;
                if (i9 == 5 && (iA = a(true)) != 44) {
                    if (iA != 59) {
                        if (iA == 125) {
                            this.h = 2;
                            return 2;
                        }
                        b("Unterminated object");
                        throw null;
                    }
                    e();
                }
                int iA3 = a(true);
                if (iA3 == 34) {
                    this.h = 13;
                    return 13;
                }
                if (iA3 == 39) {
                    e();
                    this.h = 12;
                    return 12;
                }
                if (iA3 == 125) {
                    if (i9 != 5) {
                        this.h = 2;
                        return 2;
                    }
                    b("Expected name");
                    throw null;
                }
                e();
                this.d--;
                char c7 = (char) iA3;
                if (c7 != '\t' && c7 != '\n' && c7 != '\f' && c7 != '\r' && c7 != ' ') {
                    if (c7 == '#') {
                        e();
                    } else if (c7 != ',') {
                        if (c7 == '/' || c7 == '=') {
                            e();
                        } else if (c7 != '{' && c7 != '}' && c7 != ':') {
                            if (c7 != ';') {
                                switch (c7) {
                                    case '[':
                                    case ']':
                                        break;
                                    case '\\':
                                        e();
                                        break;
                                    default:
                                        this.h = 14;
                                        return 14;
                                }
                            } else {
                                e();
                            }
                        }
                    }
                }
                b("Expected name");
                throw null;
            }
            if (i9 == 4) {
                iArr[i8] = 5;
                int iA4 = a(true);
                if (iA4 != 58) {
                    if (iA4 != 61) {
                        b("Expected ':'");
                        throw null;
                    }
                    e();
                    if (this.d < this.e || a(1)) {
                        char[] cArr2 = this.c;
                        int i10 = this.d;
                        if (cArr2[i10] == '>') {
                            this.d = i10 + 1;
                        }
                    }
                }
            } else if (i9 == 6) {
                if (this.b) {
                    a(true);
                    int i11 = this.d - 1;
                    this.d = i11;
                    char[] cArr3 = p;
                    if (i11 + cArr3.length <= this.e || a(cArr3.length)) {
                        int i12 = 0;
                        while (true) {
                            char[] cArr4 = p;
                            if (i12 >= cArr4.length) {
                                this.d += cArr4.length;
                                break;
                            }
                            if (this.c[this.d + i12] != cArr4[i12]) {
                                break;
                            }
                            i12++;
                        }
                    }
                }
                this.l[this.m - 1] = 7;
            } else if (i9 == 7) {
                if (a(false) == -1) {
                    this.h = 17;
                    return 17;
                }
                e();
                this.d--;
            } else if (i9 == 8) {
                throw new IllegalStateException("JsonReader is closed");
            }
        }
        int iA5 = a(true);
        if (iA5 == 34) {
            this.h = 9;
            return 9;
        }
        if (iA5 == 39) {
            e();
            this.h = 8;
            return 8;
        }
        if (iA5 == 44 || iA5 == 59) {
            i = 1;
        } else {
            if (iA5 == 91) {
                this.h = 3;
                return 3;
            }
            if (iA5 != 93) {
                if (iA5 == 123) {
                    this.h = 1;
                    return 1;
                }
                int i13 = this.d - 1;
                this.d = i13;
                char c8 = this.c[i13];
                if (c8 == 't' || c8 == 'T') {
                    str = "true";
                    str2 = "TRUE";
                    i2 = 5;
                } else {
                    if (c8 != 'f' && c8 != 'F') {
                        if (c8 == 'n' || c8 == 'N') {
                            str = "null";
                            str2 = "NULL";
                            i2 = 7;
                        } else {
                            i2 = 0;
                        }
                        if (i2 != 0) {
                            return i2;
                        }
                        cArr = this.c;
                        i3 = this.d;
                        i4 = this.e;
                        long j = 0;
                        boolean z2 = true;
                        c3 = 0;
                        i5 = 0;
                        boolean z3 = false;
                        while (true) {
                            if (i3 + i5 != i4) {
                                c4 = cArr[i3 + i5];
                                if (c4 != '+') {
                                    if (c4 != 'E' || c4 == 'e') {
                                        i6 = i4;
                                        if (c3 != 2 || c3 == 4) {
                                            c3 = 5;
                                            i5++;
                                            i4 = i6;
                                            z = true;
                                        }
                                    } else if (c4 == '-') {
                                        i6 = i4;
                                        c5 = 6;
                                        if (c3 == 0) {
                                            c3 = 1;
                                            z3 = true;
                                        } else {
                                            if (c3 != 5) {
                                            }
                                            c3 = c5;
                                        }
                                        i5++;
                                        i4 = i6;
                                        z = true;
                                    } else if (c4 == '.') {
                                        i6 = i4;
                                        if (c3 == 2) {
                                            c3 = 3;
                                            i5++;
                                            i4 = i6;
                                            z = true;
                                        }
                                    } else if (c4 >= '0' && c4 <= '9') {
                                        if (c3 == z || c3 == 0) {
                                            i6 = i4;
                                            j = -(c4 - '0');
                                            c3 = 2;
                                        } else if (c3 != 2) {
                                            i6 = i4;
                                            if (c3 == 3) {
                                                c3 = 4;
                                            } else if (c3 == 5 || c3 == 6) {
                                                c3 = 7;
                                            }
                                        } else if (j != 0) {
                                            i6 = i4;
                                            long j2 = (10 * j) - ((long) (c4 - '0'));
                                            z2 &= j > BufferKt.OVERFLOW_ZONE || (j == BufferKt.OVERFLOW_ZONE && j2 < j);
                                            j = j2;
                                        }
                                        i5++;
                                        i4 = i6;
                                        z = true;
                                    } else if (c4 == '\t' || c4 == '\n' || c4 == '\f' || c4 == '\r' || c4 == ' ') {
                                        if (c3 != 2 && z2 && (j != Long.MIN_VALUE || z3)) {
                                            if (!z3) {
                                                j = -j;
                                            }
                                            this.i = j;
                                            this.d += i5;
                                            this.h = 15;
                                            i7 = 15;
                                        } else if (c3 != 2 || c3 == 4 || c3 == 7) {
                                            this.j = i5;
                                            this.h = 16;
                                            i7 = 16;
                                        }
                                    } else if (c4 == '#') {
                                        e();
                                        if (c3 != 2) {
                                            if (c3 != 2) {
                                            }
                                            this.j = i5;
                                            this.h = 16;
                                            i7 = 16;
                                        } else {
                                            if (c3 != 2) {
                                            }
                                            this.j = i5;
                                            this.h = 16;
                                            i7 = 16;
                                        }
                                    } else if (c4 == ',') {
                                        if (c3 != 2) {
                                            if (c3 != 2) {
                                            }
                                            this.j = i5;
                                            this.h = 16;
                                            i7 = 16;
                                        } else {
                                            if (c3 != 2) {
                                            }
                                            this.j = i5;
                                            this.h = 16;
                                            i7 = 16;
                                        }
                                    } else if (c4 == '/' || c4 == '=') {
                                        e();
                                        if (c3 != 2) {
                                            if (c3 != 2) {
                                            }
                                            this.j = i5;
                                            this.h = 16;
                                            i7 = 16;
                                        } else {
                                            if (c3 != 2) {
                                            }
                                            this.j = i5;
                                            this.h = 16;
                                            i7 = 16;
                                        }
                                    } else if (c4 == '{' || c4 == '}' || c4 == ':') {
                                        if (c3 != 2) {
                                            if (c3 != 2) {
                                            }
                                            this.j = i5;
                                            this.h = 16;
                                            i7 = 16;
                                        } else {
                                            if (c3 != 2) {
                                            }
                                            this.j = i5;
                                            this.h = 16;
                                            i7 = 16;
                                        }
                                    } else if (c4 != ';') {
                                        switch (c4) {
                                            case '\\':
                                                e();
                                            case '[':
                                            case ']':
                                                if (c3 != 2) {
                                                    if (c3 != 2) {
                                                    }
                                                    this.j = i5;
                                                    this.h = 16;
                                                    i7 = 16;
                                                    break;
                                                } else {
                                                    if (c3 != 2) {
                                                    }
                                                    this.j = i5;
                                                    this.h = 16;
                                                    i7 = 16;
                                                    break;
                                                }
                                            default:
                                                i7 = 0;
                                                break;
                                        }
                                    } else {
                                        e();
                                        if (c3 != 2) {
                                            if (c3 != 2) {
                                            }
                                            this.j = i5;
                                            this.h = 16;
                                            i7 = 16;
                                        } else {
                                            if (c3 != 2) {
                                            }
                                            this.j = i5;
                                            this.h = 16;
                                            i7 = 16;
                                        }
                                    }
                                    if (i7 != 0) {
                                        return i7;
                                    }
                                    c6 = this.c[this.d];
                                    if (c6 != '\t' && c6 != '\n' && c6 != '\f' && c6 != '\r' && c6 != ' ') {
                                        if (c6 == '#') {
                                            e();
                                        } else if (c6 != ',') {
                                            if (c6 != '/' || c6 == '=') {
                                                e();
                                            } else if (c6 != '{' && c6 != '}' && c6 != ':') {
                                                if (c6 != ';') {
                                                    switch (c6) {
                                                        case '[':
                                                        case ']':
                                                            break;
                                                        case '\\':
                                                            e();
                                                            break;
                                                        default:
                                                            e();
                                                            this.h = 10;
                                                            return 10;
                                                    }
                                                } else {
                                                    e();
                                                }
                                            }
                                        }
                                    }
                                    b("Expected value");
                                    throw null;
                                }
                                i6 = i4;
                                c5 = 6;
                                if (c3 != 5) {
                                }
                                c3 = c5;
                                i5++;
                                i4 = i6;
                                z = true;
                            } else if (i5 != cArr.length) {
                                if (!a(i5 + 1)) {
                                    i3 = this.d;
                                    i4 = this.e;
                                    c4 = cArr[i3 + i5];
                                    if (c4 != '+') {
                                        if (c4 != 'E') {
                                            i6 = i4;
                                            if (c3 != 2) {
                                            }
                                            c3 = 5;
                                            i5++;
                                            i4 = i6;
                                            z = true;
                                        } else {
                                            i6 = i4;
                                            if (c3 != 2) {
                                            }
                                            c3 = 5;
                                            i5++;
                                            i4 = i6;
                                            z = true;
                                        }
                                        if (i7 != 0) {
                                            return i7;
                                        }
                                        c6 = this.c[this.d];
                                        if (c6 != '\t') {
                                            if (c6 == '#') {
                                                e();
                                            } else if (c6 != ',') {
                                                if (c6 != '/') {
                                                    e();
                                                } else {
                                                    e();
                                                }
                                            }
                                        }
                                        b("Expected value");
                                        throw null;
                                    }
                                    i6 = i4;
                                    c5 = 6;
                                    if (c3 != 5) {
                                    }
                                    c3 = c5;
                                    i5++;
                                    i4 = i6;
                                    z = true;
                                }
                                if (c3 != 2) {
                                    if (c3 != 2) {
                                    }
                                    this.j = i5;
                                    this.h = 16;
                                    i7 = 16;
                                } else {
                                    if (c3 != 2) {
                                    }
                                    this.j = i5;
                                    this.h = 16;
                                    i7 = 16;
                                }
                                if (i7 != 0) {
                                    return i7;
                                }
                                c6 = this.c[this.d];
                                if (c6 != '\t') {
                                    if (c6 == '#') {
                                        e();
                                    } else if (c6 != ',') {
                                        if (c6 != '/') {
                                            e();
                                        } else {
                                            e();
                                        }
                                    }
                                }
                                b("Expected value");
                                throw null;
                            }
                            i7 = 0;
                            if (i7 != 0) {
                                return i7;
                            }
                            c6 = this.c[this.d];
                            if (c6 != '\t') {
                                if (c6 == '#') {
                                    e();
                                } else if (c6 != ',') {
                                    if (c6 != '/') {
                                        e();
                                    } else {
                                        e();
                                    }
                                }
                            }
                            b("Expected value");
                            throw null;
                        }
                    }
                    str = "false";
                    str2 = "FALSE";
                    i2 = 6;
                }
                int length = str.length();
                int i14 = 1;
                while (true) {
                    if (i14 >= length) {
                        if ((this.d + length < this.e || a(length + 1)) && (c = this.c[this.d + length]) != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                            if (c == '#') {
                                e();
                            } else if (c != ',') {
                                if (c == '/' || c == '=') {
                                    e();
                                } else if (c != '{' && c != '}' && c != ':') {
                                    if (c != ';') {
                                        switch (c) {
                                            case '\\':
                                                e();
                                            case '[':
                                            case ']':
                                                this.d += length;
                                                this.h = i2;
                                                break;
                                        }
                                    } else {
                                        e();
                                    }
                                }
                            }
                        }
                        this.d += length;
                        this.h = i2;
                    } else if ((this.d + i14 < this.e || a(i14 + 1)) && ((c2 = this.c[this.d + i14]) == str.charAt(i14) || c2 == str2.charAt(i14))) {
                        i14++;
                    }
                    i2 = 0;
                }
                if (i2 != 0) {
                    return i2;
                }
                cArr = this.c;
                i3 = this.d;
                i4 = this.e;
                long j3 = 0;
                boolean z4 = true;
                c3 = 0;
                i5 = 0;
                boolean z5 = false;
                while (true) {
                    if (i3 + i5 != i4) {
                        c4 = cArr[i3 + i5];
                        if (c4 != '+') {
                            if (c4 != 'E') {
                                i6 = i4;
                                if (c3 != 2) {
                                }
                                c3 = 5;
                                i5++;
                                i4 = i6;
                                z = true;
                            } else {
                                i6 = i4;
                                if (c3 != 2) {
                                }
                                c3 = 5;
                                i5++;
                                i4 = i6;
                                z = true;
                            }
                            if (i7 != 0) {
                                return i7;
                            }
                            c6 = this.c[this.d];
                            if (c6 != '\t') {
                                if (c6 == '#') {
                                    e();
                                } else if (c6 != ',') {
                                    if (c6 != '/') {
                                        e();
                                    } else {
                                        e();
                                    }
                                }
                            }
                            b("Expected value");
                            throw null;
                        }
                        i6 = i4;
                        c5 = 6;
                        if (c3 != 5) {
                        }
                        c3 = c5;
                        i5++;
                        i4 = i6;
                        z = true;
                    } else if (i5 != cArr.length) {
                        if (!a(i5 + 1)) {
                            i3 = this.d;
                            i4 = this.e;
                            c4 = cArr[i3 + i5];
                            if (c4 != '+') {
                                if (c4 != 'E') {
                                    i6 = i4;
                                    if (c3 != 2) {
                                    }
                                    c3 = 5;
                                    i5++;
                                    i4 = i6;
                                    z = true;
                                } else {
                                    i6 = i4;
                                    if (c3 != 2) {
                                    }
                                    c3 = 5;
                                    i5++;
                                    i4 = i6;
                                    z = true;
                                }
                                if (i7 != 0) {
                                    return i7;
                                }
                                c6 = this.c[this.d];
                                if (c6 != '\t') {
                                    if (c6 == '#') {
                                        e();
                                    } else if (c6 != ',') {
                                        if (c6 != '/') {
                                            e();
                                        } else {
                                            e();
                                        }
                                    }
                                }
                                b("Expected value");
                                throw null;
                            }
                            i6 = i4;
                            c5 = 6;
                            if (c3 != 5) {
                            }
                            c3 = c5;
                            i5++;
                            i4 = i6;
                            z = true;
                        }
                        if (c3 != 2) {
                            if (c3 != 2) {
                            }
                            this.j = i5;
                            this.h = 16;
                            i7 = 16;
                        } else {
                            if (c3 != 2) {
                            }
                            this.j = i5;
                            this.h = 16;
                            i7 = 16;
                        }
                        if (i7 != 0) {
                            return i7;
                        }
                        c6 = this.c[this.d];
                        if (c6 != '\t') {
                            if (c6 == '#') {
                                e();
                            } else if (c6 != ',') {
                                if (c6 != '/') {
                                    e();
                                } else {
                                    e();
                                }
                            }
                        }
                        b("Expected value");
                        throw null;
                    }
                    i7 = 0;
                    if (i7 != 0) {
                        return i7;
                    }
                    c6 = this.c[this.d];
                    if (c6 != '\t') {
                        if (c6 == '#') {
                            e();
                        } else if (c6 != ',') {
                            if (c6 != '/') {
                                e();
                            } else {
                                e();
                            }
                        }
                    }
                    b("Expected value");
                    throw null;
                }
            }
            i = 1;
            if (i9 == 1) {
                this.h = 4;
                return 4;
            }
        }
        if (i9 != i && i9 != 2) {
            b("Unexpected value");
            throw null;
        }
        e();
        this.d -= i;
        this.h = 7;
        return 7;
    }

    public final void g() throws IOException {
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        if (iF != 4) {
            throw new IllegalStateException("Expected END_ARRAY but was " + AbstractC1568Zt.a(t()) + k());
        }
        int i = this.m;
        this.m = i - 1;
        int[] iArr = this.o;
        int i2 = i - 2;
        iArr[i2] = iArr[i2] + 1;
        this.h = 0;
    }

    public final void h() throws IOException {
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        if (iF != 2) {
            throw new IllegalStateException("Expected END_OBJECT but was " + AbstractC1568Zt.a(t()) + k());
        }
        int i = this.m;
        int i2 = i - 1;
        this.m = i2;
        this.n[i2] = null;
        int[] iArr = this.o;
        int i3 = i - 2;
        iArr[i3] = iArr[i3] + 1;
        this.h = 0;
    }

    public final boolean i() throws IOException {
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        return (iF == 2 || iF == 4) ? false : true;
    }

    public final boolean j() {
        return this.b;
    }

    public final boolean l() throws IOException {
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        if (iF == 5) {
            this.h = 0;
            int[] iArr = this.o;
            int i = this.m - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        }
        if (iF != 6) {
            throw new IllegalStateException("Expected a boolean but was " + AbstractC1568Zt.a(t()) + k());
        }
        this.h = 0;
        int[] iArr2 = this.o;
        int i2 = this.m - 1;
        iArr2[i2] = iArr2[i2] + 1;
        return false;
    }

    public final double m() throws IOException {
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        if (iF == 15) {
            this.h = 0;
            int[] iArr = this.o;
            int i = this.m - 1;
            iArr[i] = iArr[i] + 1;
            return this.i;
        }
        if (iF == 16) {
            this.k = new String(this.c, this.d, this.j);
            this.d += this.j;
        } else if (iF == 8 || iF == 9) {
            this.k = a(iF == 8 ? '\'' : '\"');
        } else if (iF == 10) {
            this.k = s();
        } else if (iF != 11) {
            throw new IllegalStateException("Expected a double but was " + AbstractC1568Zt.a(t()) + k());
        }
        this.h = 11;
        double d = Double.parseDouble(this.k);
        if (!this.b && (Double.isNaN(d) || Double.isInfinite(d))) {
            throw new C2905rD("JSON forbids NaN and infinities: " + d + k());
        }
        this.k = null;
        this.h = 0;
        int[] iArr2 = this.o;
        int i2 = this.m - 1;
        iArr2[i2] = iArr2[i2] + 1;
        return d;
    }

    public final int n() throws IOException {
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        if (iF == 15) {
            long j = this.i;
            int i = (int) j;
            if (j != i) {
                throw new NumberFormatException("Expected an int but was " + this.i + k());
            }
            this.h = 0;
            int[] iArr = this.o;
            int i2 = this.m - 1;
            iArr[i2] = iArr[i2] + 1;
            return i;
        }
        if (iF == 16) {
            this.k = new String(this.c, this.d, this.j);
            this.d += this.j;
        } else {
            if (iF != 8 && iF != 9 && iF != 10) {
                throw new IllegalStateException("Expected an int but was " + AbstractC1568Zt.a(t()) + k());
            }
            if (iF == 10) {
                this.k = s();
            } else {
                this.k = a(iF == 8 ? '\'' : '\"');
            }
            try {
                int i3 = Integer.parseInt(this.k);
                this.h = 0;
                int[] iArr2 = this.o;
                int i4 = this.m - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return i3;
            } catch (NumberFormatException unused) {
            }
        }
        this.h = 11;
        double d = Double.parseDouble(this.k);
        int i5 = (int) d;
        if (i5 != d) {
            throw new NumberFormatException("Expected an int but was " + this.k + k());
        }
        this.k = null;
        this.h = 0;
        int[] iArr3 = this.o;
        int i6 = this.m - 1;
        iArr3[i6] = iArr3[i6] + 1;
        return i5;
    }

    public final long o() throws IOException {
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        if (iF == 15) {
            this.h = 0;
            int[] iArr = this.o;
            int i = this.m - 1;
            iArr[i] = iArr[i] + 1;
            return this.i;
        }
        if (iF == 16) {
            this.k = new String(this.c, this.d, this.j);
            this.d += this.j;
        } else {
            if (iF != 8 && iF != 9 && iF != 10) {
                throw new IllegalStateException("Expected a long but was " + AbstractC1568Zt.a(t()) + k());
            }
            if (iF == 10) {
                this.k = s();
            } else {
                this.k = a(iF == 8 ? '\'' : '\"');
            }
            try {
                long j = Long.parseLong(this.k);
                this.h = 0;
                int[] iArr2 = this.o;
                int i2 = this.m - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return j;
            } catch (NumberFormatException unused) {
            }
        }
        this.h = 11;
        double d = Double.parseDouble(this.k);
        long j2 = (long) d;
        if (j2 != d) {
            throw new NumberFormatException("Expected a long but was " + this.k + k());
        }
        this.k = null;
        this.h = 0;
        int[] iArr3 = this.o;
        int i3 = this.m - 1;
        iArr3[i3] = iArr3[i3] + 1;
        return j2;
    }

    public final String p() throws IOException {
        String strA;
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        if (iF == 14) {
            strA = s();
        } else if (iF == 12) {
            strA = a('\'');
        } else {
            if (iF != 13) {
                throw new IllegalStateException("Expected a name but was " + AbstractC1568Zt.a(t()) + k());
            }
            strA = a('\"');
        }
        this.h = 0;
        this.n[this.m - 1] = strA;
        return strA;
    }

    public final void q() throws IOException {
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        if (iF != 7) {
            throw new IllegalStateException("Expected null but was " + AbstractC1568Zt.a(t()) + k());
        }
        this.h = 0;
        int[] iArr = this.o;
        int i = this.m - 1;
        iArr[i] = iArr[i] + 1;
    }

    public final String r() throws IOException {
        String str;
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        if (iF == 10) {
            str = s();
        } else if (iF == 8) {
            str = a('\'');
        } else if (iF == 9) {
            str = a('\"');
        } else if (iF == 11) {
            str = this.k;
            this.k = null;
        } else if (iF == 15) {
            str = Long.toString(this.i);
        } else {
            if (iF != 16) {
                throw new IllegalStateException("Expected a string but was " + AbstractC1568Zt.a(t()) + k());
            }
            str = new String(this.c, this.d, this.j);
            this.d += this.j;
        }
        this.h = 0;
        int[] iArr = this.o;
        int i = this.m - 1;
        iArr[i] = iArr[i] + 1;
        return str;
    }

    public final int t() throws IOException {
        int iF = this.h;
        if (iF == 0) {
            iF = f();
        }
        switch (iF) {
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
            case 6:
                return 8;
            case 7:
                return 9;
            case 8:
            case 9:
            case 10:
            case 11:
                return 6;
            case 12:
            case 13:
            case 14:
                return 5;
            case 15:
            case 16:
                return 7;
            case 17:
                return 10;
            default:
                throw new AssertionError();
        }
    }

    public final String toString() {
        return "Wt" + k();
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:61:0x009b. Please report as an issue. */
    public final void v() throws IOException {
        int i = 0;
        do {
            int iF = this.h;
            if (iF == 0) {
                iF = f();
            }
            if (iF == 3) {
                b(1);
            } else {
                if (iF == 1) {
                    b(3);
                } else if (iF == 4 || iF == 2) {
                    this.m--;
                    i--;
                } else if (iF == 14 || iF == 10) {
                    while (true) {
                        int i2 = 0;
                        while (true) {
                            int i3 = this.d + i2;
                            if (i3 < this.e) {
                                char c = this.c[i3];
                                if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                                    if (c != '#') {
                                        if (c != ',') {
                                            if (c != '/' && c != '=') {
                                                if (c != '{' && c != '}' && c != ':') {
                                                    if (c != ';') {
                                                        switch (c) {
                                                            case '[':
                                                            case ']':
                                                                break;
                                                            case '\\':
                                                                break;
                                                            default:
                                                                i2++;
                                                                break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    e();
                                }
                                this.d += i2;
                            } else {
                                this.d = i3;
                                if (!a(1)) {
                                }
                            }
                        }
                    }
                } else if (iF == 8 || iF == 12) {
                    b('\'');
                } else if (iF == 9 || iF == 13) {
                    b('\"');
                } else if (iF == 16) {
                    this.d += this.j;
                }
                this.h = 0;
            }
            i++;
            this.h = 0;
        } while (i != 0);
        int[] iArr = this.o;
        int i4 = this.m - 1;
        iArr[i4] = iArr[i4] + 1;
        this.n[i4] = "null";
    }

    private String a(char c) throws C2905rD {
        char[] cArr = this.c;
        StringBuilder sb = new StringBuilder();
        do {
            int i = this.d;
            int i2 = this.e;
            int i3 = i;
            while (i < i2) {
                int i4 = i + 1;
                char c2 = cArr[i];
                if (c2 == c) {
                    this.d = i4;
                    sb.append(cArr, i3, (i4 - i3) - 1);
                    return sb.toString();
                }
                if (c2 == '\\') {
                    this.d = i4;
                    sb.append(cArr, i3, (i4 - i3) - 1);
                    sb.append(u());
                    i3 = this.d;
                    i2 = this.e;
                    i = i3;
                } else {
                    if (c2 == '\n') {
                        this.f++;
                        this.g = i4;
                    }
                    i = i4;
                }
            }
            sb.append(cArr, i3, i - i3);
            this.d = i;
        } while (a(1));
        b("Unterminated string");
        throw null;
    }

    private void b(char c) throws C2905rD {
        char[] cArr = this.c;
        do {
            int i = this.d;
            int i2 = this.e;
            while (i < i2) {
                int i3 = i + 1;
                char c2 = cArr[i];
                if (c2 == c) {
                    this.d = i3;
                    return;
                }
                if (c2 == '\\') {
                    this.d = i3;
                    u();
                    i = this.d;
                    i2 = this.e;
                } else {
                    if (c2 == '\n') {
                        this.f++;
                        this.g = i3;
                    }
                    i = i3;
                }
            }
            this.d = i;
        } while (a(1));
        b("Unterminated string");
        throw null;
    }

    private void b(int i) {
        int i2 = this.m;
        int[] iArr = this.l;
        if (i2 == iArr.length) {
            int i3 = i2 * 2;
            int[] iArr2 = new int[i3];
            int[] iArr3 = new int[i3];
            String[] strArr = new String[i3];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            System.arraycopy(this.o, 0, iArr3, 0, this.m);
            System.arraycopy(this.n, 0, strArr, 0, this.m);
            this.l = iArr2;
            this.o = iArr3;
            this.n = strArr;
        }
        int[] iArr4 = this.l;
        int i4 = this.m;
        this.m = i4 + 1;
        iArr4[i4] = i;
    }

    private boolean a(int i) throws IOException {
        int i2;
        int i3;
        char[] cArr = this.c;
        int i4 = this.g;
        int i5 = this.d;
        this.g = i4 - i5;
        int i6 = this.e;
        if (i6 != i5) {
            int i7 = i6 - i5;
            this.e = i7;
            System.arraycopy(cArr, i5, cArr, 0, i7);
        } else {
            this.e = 0;
        }
        this.d = 0;
        do {
            Reader reader = this.a;
            int i8 = this.e;
            int i9 = reader.read(cArr, i8, cArr.length - i8);
            if (i9 == -1) {
                return false;
            }
            i2 = this.e + i9;
            this.e = i2;
            if (this.f == 0 && (i3 = this.g) == 0 && i2 > 0 && cArr[0] == 65279) {
                this.d++;
                this.g = i3 + 1;
                i++;
            }
        } while (i2 < i);
        return true;
    }

    private void b(String str) throws C2905rD {
        throw new C2905rD(str + k());
    }

    private int a(boolean z) throws IOException {
        char c;
        char c2;
        char[] cArr = this.c;
        int i = this.d;
        int i2 = this.e;
        while (true) {
            if (i == i2) {
                this.d = i;
                if (!a(1)) {
                    if (z) {
                        throw new EOFException("End of input" + k());
                    }
                    return -1;
                }
                i = this.d;
                i2 = this.e;
            }
            int i3 = i + 1;
            char c3 = cArr[i];
            if (c3 == '\n') {
                this.f++;
                this.g = i3;
            } else if (c3 != ' ' && c3 != '\r' && c3 != '\t') {
                if (c3 == '/') {
                    this.d = i3;
                    if (i3 == i2) {
                        this.d = i;
                        boolean zA = a(2);
                        this.d++;
                        if (!zA) {
                            return c3;
                        }
                    }
                    e();
                    int i4 = this.d;
                    char c4 = cArr[i4];
                    if (c4 == '*') {
                        this.d = i4 + 1;
                        while (true) {
                            if (this.d + 2 <= this.e || a(2)) {
                                char[] cArr2 = this.c;
                                int i5 = this.d;
                                if (cArr2[i5] != '\n') {
                                    int i6 = 0;
                                    while (true) {
                                        if (i6 < 2) {
                                            if (this.c[this.d + i6] != "*/".charAt(i6)) {
                                                break;
                                            }
                                            i6++;
                                        } else {
                                            i = this.d + 2;
                                            i2 = this.e;
                                            break;
                                        }
                                    }
                                } else {
                                    this.f++;
                                    this.g = i5 + 1;
                                }
                                this.d++;
                            } else {
                                b("Unterminated comment");
                                throw null;
                            }
                        }
                    } else {
                        if (c4 != '/') {
                            return c3;
                        }
                        this.d = i4 + 1;
                        do {
                            if (this.d >= this.e && !a(1)) {
                                break;
                            }
                            char[] cArr3 = this.c;
                            int i7 = this.d;
                            int i8 = i7 + 1;
                            this.d = i8;
                            c = cArr3[i7];
                            if (c == '\n') {
                                this.f++;
                                this.g = i8;
                                break;
                            }
                        } while (c != '\r');
                        i = this.d;
                        i2 = this.e;
                    }
                } else if (c3 == '#') {
                    this.d = i3;
                    e();
                    do {
                        if (this.d >= this.e && !a(1)) {
                            break;
                        }
                        char[] cArr4 = this.c;
                        int i9 = this.d;
                        int i10 = i9 + 1;
                        this.d = i10;
                        c2 = cArr4[i9];
                        if (c2 == '\n') {
                            this.f++;
                            this.g = i10;
                            break;
                        }
                    } while (c2 != '\r');
                    i = this.d;
                    i2 = this.e;
                } else {
                    this.d = i3;
                    return c3;
                }
            }
            i = i3;
        }
    }
}
