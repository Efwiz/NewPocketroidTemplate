package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Z4 {
    private static final boolean a;
    static final /* synthetic */ boolean b = true;

    private static /* synthetic */ void a(int i) {
        String str = (i == 1 || i == 3 || i == 6 || i == 8 || i == 10 || i == 12 || i == 14) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 6 || i == 8 || i == 10 || i == 12 || i == 14) ? 2 : 3];
        if (i == 1 || i == 3 || i == 6 || i == 8 || i == 10 || i == 12 || i == 14) {
            objArr[0] = "kotlinx/metadata/internal/metadata/jvm/deserialization/BitEncoding";
        } else {
            objArr[0] = "data";
        }
        if (i == 1) {
            objArr[1] = "encodeBytes";
        } else if (i == 3) {
            objArr[1] = "encode8to7";
        } else if (i == 6) {
            objArr[1] = "splitBytesToStringArray";
        } else if (i == 8) {
            objArr[1] = "decodeBytes";
        } else if (i == 10) {
            objArr[1] = "dropMarker";
        } else if (i == 12) {
            objArr[1] = "combineStringArrayIntoBytes";
        } else if (i != 14) {
            objArr[1] = "kotlinx/metadata/internal/metadata/jvm/deserialization/BitEncoding";
        } else {
            objArr[1] = "decode7to8";
        }
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 8:
            case 10:
            case 12:
            case 14:
                break;
            case 2:
                objArr[2] = "encode8to7";
                break;
            case 4:
                objArr[2] = "addModuloByte";
                break;
            case 5:
                objArr[2] = "splitBytesToStringArray";
                break;
            case 7:
                objArr[2] = "decodeBytes";
                break;
            case 9:
                objArr[2] = "dropMarker";
                break;
            case 11:
                objArr[2] = "combineStringArrayIntoBytes";
                break;
            case 13:
                objArr[2] = "decode7to8";
                break;
            default:
                objArr[2] = "encodeBytes";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 6 && i != 8 && i != 10 && i != 12 && i != 14) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static String[] a(byte[] bArr) {
        int i;
        if (bArr == null) {
            a(0);
            throw null;
        }
        int i2 = 2;
        if (!a) {
            ArrayList arrayList = new ArrayList(1);
            StringBuilder sb = new StringBuilder();
            sb.append((char) 0);
            int length = bArr.length;
            for (int i3 = 0; i3 < length; i3++) {
                byte b2 = bArr[i3];
                sb.append((char) (b2 & 255));
                i2 = (1 > b2 || b2 >= 128) ? i2 + 2 : i2 + 1;
                if (i2 >= 65534) {
                    arrayList.add(sb.toString());
                    sb.setLength(0);
                    i2 = 0;
                }
            }
            if (sb.length() != 0) {
                arrayList.add(sb.toString());
            }
            String[] strArr = (String[]) arrayList.toArray(new String[0]);
            if (strArr != null) {
                return strArr;
            }
            a(1);
            throw null;
        }
        int length2 = ((bArr.length * 8) + 6) / 7;
        byte[] bArr2 = new byte[length2];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            i = length2 - 1;
            if (i4 >= i) {
                break;
            }
            if (i5 == 0) {
                bArr2[i4] = (byte) (bArr[i6] & 127);
                i5 = 7;
            } else {
                int i7 = (bArr[i6] & 255) >>> i5;
                int i8 = (i5 + 7) & 7;
                i6++;
                bArr2[i4] = (byte) (i7 + ((bArr[i6] & ((1 << i8) - 1)) << (8 - i5)));
                i5 = i8;
            }
            i4++;
        }
        if (length2 > 0) {
            boolean z = b;
            if (!z && i5 == 0) {
                throw new AssertionError("The last chunk cannot start from the input byte since otherwise at least one bit will remain unprocessed");
            }
            if (!z && i6 != bArr.length - 1) {
                throw new AssertionError(AbstractC1588a5.a("The last 7-bit chunk should be encoded from the last input byte: ", i6, " != ").append(bArr.length - 1).toString());
            }
            bArr2[i] = (byte) ((bArr[i6] & 255) >>> i5);
        }
        for (int i9 = 0; i9 < length2; i9++) {
            bArr2[i9] = (byte) ((bArr2[i9] + 1) & 127);
        }
        ArrayList arrayList2 = new ArrayList();
        int i10 = 0;
        boolean z2 = false;
        for (int i11 = 0; i11 < length2; i11++) {
            if (i2 >= 65534) {
                if (!b && i2 > 65535) {
                    throw new AssertionError(AbstractC1857dg.a("Produced strings cannot contain more than 65535 bytes: ", i2));
                }
                String str = new String(bArr2, i10, i11 - i10);
                if (z2) {
                    arrayList2.add(str);
                } else {
                    arrayList2.add("\uffff".concat(str));
                    z2 = true;
                }
                i2 = 0;
                i10 = i11;
            }
            i2 = bArr2[i11] == 0 ? i2 + 2 : i2 + 1;
        }
        if (i2 >= 0) {
            arrayList2.add(new String(bArr2, i10, length2 - i10));
        }
        String[] strArr2 = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
        if (strArr2 != null) {
            return strArr2;
        }
        a(6);
        throw null;
    }

    static {
        String property;
        try {
            property = System.getProperty("kotlin.jvm.serialization.use8to7");
        } catch (SecurityException unused) {
            property = null;
        }
        a = "true".equals(property);
    }

    public static byte[] a(String[] strArr) {
        if (strArr.length > 0 && !strArr[0].isEmpty()) {
            char cCharAt = strArr[0].charAt(0);
            if (cCharAt == 0) {
                String[] strArr2 = (String[]) strArr.clone();
                strArr2[0] = strArr2[0].substring(1);
                int length = 0;
                for (String str : strArr2) {
                    length += str.length();
                }
                byte[] bArr = new byte[length];
                int i = 0;
                for (String str2 : strArr2) {
                    int length2 = str2.length();
                    int i2 = 0;
                    while (i2 < length2) {
                        bArr[i] = (byte) str2.charAt(i2);
                        i2++;
                        i++;
                    }
                }
                boolean z = i == length;
                if (!Q30.a || z) {
                    return bArr;
                }
                throw new AssertionError("Should have reached the end");
            }
            if (cCharAt == 65535) {
                strArr = (String[]) strArr.clone();
                strArr[0] = strArr[0].substring(1);
            }
        }
        int length3 = 0;
        for (String str3 : strArr) {
            if (!b && str3.length() > 65535) {
                throw new AssertionError("String is too long: " + str3.length());
            }
            length3 += str3.length();
        }
        byte[] bArr2 = new byte[length3];
        int i3 = 0;
        for (String str4 : strArr) {
            int length4 = str4.length();
            int i4 = 0;
            while (i4 < length4) {
                bArr2[i3] = (byte) str4.charAt(i4);
                i4++;
                i3++;
            }
        }
        for (int i5 = 0; i5 < length3; i5++) {
            bArr2[i5] = (byte) ((bArr2[i5] + 127) & 127);
        }
        int i6 = (length3 * 7) / 8;
        byte[] bArr3 = new byte[i6];
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 < i6; i9++) {
            int i10 = i8 + 1;
            int i11 = i7 + 1;
            bArr3[i9] = (byte) (((bArr2[i8] & 255) >>> i7) + ((bArr2[i10] & ((1 << i11) - 1)) << (7 - i7)));
            if (i7 == 6) {
                i8 += 2;
                i7 = 0;
            } else {
                i8 = i10;
                i7 = i11;
            }
        }
        return bArr3;
    }
}
