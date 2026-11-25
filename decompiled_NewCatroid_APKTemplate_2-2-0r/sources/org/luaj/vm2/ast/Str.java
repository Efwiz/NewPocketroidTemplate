package org.luaj.vm2.ast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import org.luaj.vm2.LuaString;

/* JADX INFO: loaded from: classes6.dex */
public class Str {
    private Str() {
    }

    public static LuaString charString(String str) {
        return LuaString.valueUsing(unquote(str.substring(1, str.length() - 1)));
    }

    public static byte[] iso88591bytes(String str) {
        try {
            return str.getBytes("ISO8859-1");
        } catch (UnsupportedEncodingException unused) {
            throw new IllegalStateException("ISO8859-1 not supported");
        }
    }

    public static LuaString longString(String str) {
        int iIndexOf = str.indexOf(91, str.indexOf(91) + 1) + 1;
        return LuaString.valueUsing(iso88591bytes(str.substring(iIndexOf, str.length() - iIndexOf)));
    }

    public static LuaString quoteString(String str) {
        return LuaString.valueUsing(unquote(str.substring(1, str.length() - 1)));
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0085 A[PHI: r7
      0x0085: PHI (r7v1 int) = (r7v0 int), (r7v2 int) binds: [B:8:0x001c, B:10:0x0020] A[DONT_GENERATE, DONT_INLINE]] */
    public static byte[] unquote(String str) {
        byte b;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = 0;
        while (i < length) {
            char c = charArray[i];
            if (c != '\\' || i >= length) {
                byteArrayOutputStream.write((byte) c);
            } else {
                int i2 = i + 1;
                char c2 = charArray[i2];
                int i3 = 34;
                if (c2 != '\"') {
                    i3 = 39;
                    if (c2 == '\'') {
                        byteArrayOutputStream.write(i3);
                    } else if (c2 != '\\') {
                        if (c2 == 'f') {
                            b = 12;
                        } else if (c2 == 'n') {
                            b = 10;
                        } else if (c2 == 'r') {
                            b = 13;
                        } else if (c2 == 't') {
                            b = 9;
                        } else if (c2 == 'v') {
                            b = 11;
                        } else if (c2 == 'a') {
                            b = 7;
                        } else if (c2 != 'b') {
                            switch (c2) {
                                case '0':
                                case '1':
                                case '2':
                                case '3':
                                case '4':
                                case '5':
                                case '6':
                                case '7':
                                case '8':
                                case '9':
                                    int i4 = i + 2;
                                    int i5 = c2 - '0';
                                    for (int i6 = 0; i4 < length && i6 < 2; i6++) {
                                        char c3 = charArray[i4];
                                        if (c3 < '0' || c3 > '9') {
                                            byteArrayOutputStream.write((byte) i5);
                                            i = i4 - 1;
                                            continue;
                                        } else {
                                            i5 = (i5 * 10) + (c3 - '0');
                                            i4++;
                                        }
                                        break;
                                    }
                                    byteArrayOutputStream.write((byte) i5);
                                    i = i4 - 1;
                                    continue;
                                default:
                                    b = (byte) c2;
                                    break;
                            }
                        } else {
                            b = 8;
                        }
                        byteArrayOutputStream.write(b);
                    } else {
                        byteArrayOutputStream.write(92);
                    }
                    i = i2;
                } else {
                    byteArrayOutputStream.write(i3);
                    i = i2;
                }
            }
            i++;
        }
        return byteArrayOutputStream.toByteArray();
    }
}
