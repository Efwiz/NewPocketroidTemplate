package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class CharsetToolkit {
    private static final Map<Charset, byte[]> CHARSET_TO_MANDATORY_BOM;
    public static final byte[] UTF16BE_BOM;
    public static final byte[] UTF16LE_BOM;
    private static final byte[] UTF32BE_BOM;
    private static final byte[] UTF32LE_BOM;
    public static final byte[] UTF8_BOM;
    public static final Charset UTF_32BE_CHARSET;
    public static final Charset UTF_32LE_CHARSET;
    public static final Charset WIN_1251_CHARSET;
    private final byte[] buffer;
    private final Charset defaultCharset;
    private final boolean enforce8Bit;

    @Deprecated
    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    @Deprecated
    public static final Charset UTF_16LE_CHARSET = StandardCharsets.UTF_16LE;

    @Deprecated
    public static final Charset UTF_16BE_CHARSET = StandardCharsets.UTF_16BE;

    public enum GuessedEncoding {
        SEVEN_BIT,
        VALID_UTF8,
        INVALID_UTF8,
        BINARY
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0019 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 27 && i != 45 && i != 38 && i != 39) {
            switch (i) {
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 27 && i != 45 && i != 38 && i != 39) {
            switch (i) {
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 35:
                objArr[0] = "buffer";
                break;
            case 2:
                objArr[0] = "stream";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 27:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 38:
            case 39:
            case 45:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/CharsetToolkit";
                break;
            case 22:
            case 23:
            case 37:
            default:
                objArr[0] = "defaultCharset";
                break;
            case 24:
            case 26:
            case 29:
                objArr[0] = "bytes";
                break;
            case 25:
            case 28:
            case 46:
            case 48:
            case 49:
            case 50:
                objArr[0] = "charset";
                break;
            case 36:
                objArr[0] = "f";
                break;
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 51:
                objArr[0] = "bom";
                break;
            case 47:
                objArr[0] = FirebaseAnalytics.Param.CONTENT;
                break;
        }
        if (i == 27) {
            objArr[1] = "decodeString";
        } else if (i == 45) {
            objArr[1] = "getAvailableCharsets";
        } else if (i == 38) {
            objArr[1] = "getDefaultSystemCharset";
        } else if (i != 39) {
            switch (i) {
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    objArr[1] = "inputStreamSkippingBOM";
                    break;
                case 21:
                    objArr[1] = "getDefaultCharset";
                    break;
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                            objArr[1] = "guessFromContent";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/CharsetToolkit";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getPlatformCharset";
        }
        switch (i) {
            case 2:
                objArr[2] = "inputStreamSkippingBOM";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 27:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 38:
            case 39:
            case 45:
                break;
            case 22:
            case 36:
            case 37:
                objArr[2] = "guessEncoding";
                break;
            case 23:
            case 24:
                objArr[2] = "bytesToString";
                break;
            case 25:
            case 26:
                objArr[2] = "decodeString";
                break;
            case 28:
            case 29:
                objArr[2] = "tryDecodeString";
                break;
            case 35:
                objArr[2] = "guessFromBOM";
                break;
            case 40:
                objArr[2] = "hasUTF8Bom";
                break;
            case 41:
                objArr[2] = "hasUTF16LEBom";
                break;
            case 42:
                objArr[2] = "hasUTF16BEBom";
                break;
            case 43:
                objArr[2] = "hasUTF32BEBom";
                break;
            case 44:
                objArr[2] = "hasUTF32LEBom";
                break;
            case 46:
            case 47:
                objArr[2] = "getBOMLength";
                break;
            case 48:
                objArr[2] = "getMandatoryBom";
                break;
            case 49:
                objArr[2] = "getPossibleBom";
                break;
            case 50:
            case 51:
                objArr[2] = "canHaveBom";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 27 && i != 45 && i != 38 && i != 39) {
            switch (i) {
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                    break;
                default:
                    switch (i) {
                        case 30:
                        case 31:
                        case 32:
                        case 33:
                        case 34:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    private static boolean isContinuationChar(byte b) {
        return b <= -65;
    }

    private static boolean isFiveBytesSequence(byte b) {
        return -8 <= b && b <= -5;
    }

    private static boolean isFourBytesSequence(byte b) {
        return -16 <= b && b <= -9;
    }

    private static boolean isSixBytesSequence(byte b) {
        return -4 <= b && b <= -3;
    }

    private static boolean isThreeBytesSequence(byte b) {
        return -32 <= b && b <= -17;
    }

    private static boolean isTwoBytesSequence(byte b) {
        return -64 <= b && b <= -33;
    }

    static {
        Charset charsetForName = Charset.forName("UTF-32BE");
        UTF_32BE_CHARSET = charsetForName;
        Charset charsetForName2 = Charset.forName("UTF-32LE");
        UTF_32LE_CHARSET = charsetForName2;
        WIN_1251_CHARSET = Charset.forName("windows-1251");
        UTF8_BOM = new byte[]{ByteSourceJsonBootstrapper.UTF8_BOM_1, -69, -65};
        byte[] bArr = {-1, -2};
        UTF16LE_BOM = bArr;
        byte[] bArr2 = {-2, -1};
        UTF16BE_BOM = bArr2;
        byte[] bArr3 = {0, 0, -2, -1};
        UTF32BE_BOM = bArr3;
        byte[] bArr4 = {-1, -2, 0, 0};
        UTF32LE_BOM = bArr4;
        HashMap map = new HashMap(4);
        CHARSET_TO_MANDATORY_BOM = map;
        map.put(StandardCharsets.UTF_16LE, bArr);
        map.put(StandardCharsets.UTF_16BE, bArr2);
        map.put(charsetForName, bArr3);
        map.put(charsetForName2, bArr4);
    }

    public CharsetToolkit(byte[] bArr, Charset charset, boolean z) {
        if (charset == null) {
            $$$reportNull$$$0(0);
        }
        if (bArr == null) {
            $$$reportNull$$$0(1);
        }
        this.buffer = bArr;
        this.defaultCharset = charset;
        this.enforce8Bit = z;
        if (bArr.length == 0) {
            throw new IllegalArgumentException("Can't analyze empty buffer");
        }
    }

    public static InputStream inputStreamSkippingBOM(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(2);
        }
        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStream(inputStream);
        }
        inputStream.mark(4);
        try {
            int i = inputStream.read();
            if (i == -1) {
                inputStream.reset();
                if (inputStream == null) {
                    $$$reportNull$$$0(3);
                }
                return inputStream;
            }
            byte b = (byte) i;
            if (b != -17 && b != -1 && b != -2 && b != 0) {
                inputStream.reset();
                if (inputStream == null) {
                    $$$reportNull$$$0(4);
                }
                return inputStream;
            }
            int i2 = inputStream.read();
            if (i2 == -1) {
                inputStream.reset();
                if (inputStream == null) {
                    $$$reportNull$$$0(5);
                }
                return inputStream;
            }
            byte b2 = (byte) i2;
            if (b == -1 && b2 == -2) {
                inputStream.mark(2);
                int i3 = inputStream.read();
                if (i3 == -1) {
                    inputStream.reset();
                    if (inputStream == null) {
                        $$$reportNull$$$0(6);
                    }
                    return inputStream;
                }
                if (((byte) i3) != 0) {
                    inputStream.reset();
                    if (inputStream == null) {
                        $$$reportNull$$$0(7);
                    }
                    return inputStream;
                }
                int i4 = inputStream.read();
                if (i4 == -1) {
                    inputStream.reset();
                    if (inputStream == null) {
                        $$$reportNull$$$0(8);
                    }
                    return inputStream;
                }
                if (((byte) i4) == 0) {
                    if (inputStream == null) {
                        $$$reportNull$$$0(10);
                    }
                    return inputStream;
                }
                inputStream.reset();
                if (inputStream == null) {
                    $$$reportNull$$$0(9);
                }
                return inputStream;
            }
            if (b == -2 && b2 == -1) {
                if (inputStream == null) {
                    $$$reportNull$$$0(11);
                }
                return inputStream;
            }
            if (b == -17 && b2 == -69) {
                int i5 = inputStream.read();
                if (i5 == -1) {
                    inputStream.reset();
                    if (inputStream == null) {
                        $$$reportNull$$$0(12);
                    }
                    return inputStream;
                }
                if (((byte) i5) == -65) {
                    if (inputStream == null) {
                        $$$reportNull$$$0(13);
                    }
                    return inputStream;
                }
                inputStream.reset();
                if (inputStream == null) {
                    $$$reportNull$$$0(14);
                }
                return inputStream;
            }
            if (b != 0 || b2 != 0) {
                inputStream.reset();
                if (inputStream == null) {
                    $$$reportNull$$$0(20);
                }
                return inputStream;
            }
            int i6 = inputStream.read();
            if (i6 == -1) {
                inputStream.reset();
                if (inputStream == null) {
                    $$$reportNull$$$0(15);
                }
                return inputStream;
            }
            if (((byte) i6) != -2) {
                inputStream.reset();
                if (inputStream == null) {
                    $$$reportNull$$$0(16);
                }
                return inputStream;
            }
            int i7 = inputStream.read();
            if (i7 == -1) {
                inputStream.reset();
                if (inputStream == null) {
                    $$$reportNull$$$0(17);
                }
                return inputStream;
            }
            if (((byte) i7) == -1) {
                if (inputStream == null) {
                    $$$reportNull$$$0(19);
                }
                return inputStream;
            }
            inputStream.reset();
            if (inputStream == null) {
                $$$reportNull$$$0(18);
            }
            return inputStream;
        } catch (Throwable th) {
            inputStream.reset();
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x004d  */
    public GuessedEncoding guessFromContent(int i, int i2) {
        boolean z;
        int iMin = Math.min(this.buffer.length, i2);
        int i3 = i;
        boolean z2 = true;
        boolean z3 = false;
        while (true) {
            if (i3 < iMin) {
                byte[] bArr = this.buffer;
                byte b = bArr[i3];
                int i4 = i3 + 1;
                byte b2 = i4 >= iMin ? (byte) 0 : bArr[i4];
                int i5 = i3 + 2;
                byte b3 = i5 >= iMin ? (byte) 0 : bArr[i5];
                int i6 = i3 + 3;
                byte b4 = i6 >= iMin ? (byte) 0 : bArr[i6];
                int i7 = i3 + 4;
                byte b5 = i7 >= iMin ? (byte) 0 : bArr[i7];
                int i8 = i3 + 5;
                byte b6 = i8 >= iMin ? (byte) 0 : bArr[i8];
                if (b >= 0) {
                    if (b < 9) {
                        z = true;
                        break;
                    }
                } else if (isTwoBytesSequence(b)) {
                    if (isContinuationChar(b2)) {
                        z3 = true;
                        i3 = i4;
                    } else {
                        z3 = true;
                        z2 = false;
                    }
                } else if (isThreeBytesSequence(b)) {
                    if (isContinuationChar(b2) && isContinuationChar(b3)) {
                        z3 = true;
                        i3 = i5;
                    } else {
                        z3 = true;
                        z2 = false;
                    }
                } else if (isFourBytesSequence(b)) {
                    if (isContinuationChar(b2) && isContinuationChar(b3) && isContinuationChar(b4)) {
                        z3 = true;
                        i3 = i6;
                    } else {
                        z3 = true;
                        z2 = false;
                    }
                } else if (isFiveBytesSequence(b)) {
                    if (isContinuationChar(b2) && isContinuationChar(b3) && isContinuationChar(b4) && isContinuationChar(b5)) {
                        z3 = true;
                        i3 = i7;
                    } else {
                        z3 = true;
                        z2 = false;
                    }
                } else if (isSixBytesSequence(b) && isContinuationChar(b2) && isContinuationChar(b3) && isContinuationChar(b4) && isContinuationChar(b5) && isContinuationChar(b6)) {
                    i3 = i8;
                    z3 = true;
                } else {
                    z3 = true;
                    z2 = false;
                }
                if (z2) {
                    i3++;
                }
            }
            z = false;
            break;
        }
        if (!z3 && !z) {
            GuessedEncoding guessedEncoding = GuessedEncoding.SEVEN_BIT;
            if (guessedEncoding == null) {
                $$$reportNull$$$0(31);
            }
            return guessedEncoding;
        }
        if (!z2) {
            GuessedEncoding guessedEncoding2 = GuessedEncoding.INVALID_UTF8;
            if (guessedEncoding2 == null) {
                $$$reportNull$$$0(32);
            }
            return guessedEncoding2;
        }
        if (z) {
            GuessedEncoding guessedEncoding3 = GuessedEncoding.BINARY;
            if (guessedEncoding3 == null) {
                $$$reportNull$$$0(33);
            }
            return guessedEncoding3;
        }
        GuessedEncoding guessedEncoding4 = GuessedEncoding.VALID_UTF8;
        if (guessedEncoding4 == null) {
            $$$reportNull$$$0(34);
        }
        return guessedEncoding4;
    }

    public Charset guessFromBOM() {
        return guessFromBOM(this.buffer);
    }

    public static Charset guessFromBOM(byte[] bArr) {
        if (bArr == null) {
            $$$reportNull$$$0(35);
        }
        if (hasUTF8Bom(bArr)) {
            return StandardCharsets.UTF_8;
        }
        if (hasUTF32BEBom(bArr)) {
            return UTF_32BE_CHARSET;
        }
        if (hasUTF32LEBom(bArr)) {
            return UTF_32LE_CHARSET;
        }
        if (hasUTF16LEBom(bArr)) {
            return StandardCharsets.UTF_16LE;
        }
        if (hasUTF16BEBom(bArr)) {
            return StandardCharsets.UTF_16BE;
        }
        return null;
    }

    public static Charset getDefaultSystemCharset() {
        Charset charsetDefaultCharset = Charset.defaultCharset();
        if (charsetDefaultCharset == null) {
            $$$reportNull$$$0(38);
        }
        return charsetDefaultCharset;
    }

    public static Charset getPlatformCharset() {
        Charset charsetForName = forName(System.getProperty("sun.jnu.encoding"));
        if (charsetForName == null) {
            charsetForName = getDefaultSystemCharset();
        }
        if (charsetForName == null) {
            $$$reportNull$$$0(39);
        }
        return charsetForName;
    }

    public static boolean hasUTF8Bom(byte[] bArr) {
        if (bArr == null) {
            $$$reportNull$$$0(40);
        }
        return ArrayUtil.startsWith(bArr, UTF8_BOM);
    }

    public static boolean hasUTF16LEBom(byte[] bArr) {
        if (bArr == null) {
            $$$reportNull$$$0(41);
        }
        return ArrayUtil.startsWith(bArr, UTF16LE_BOM);
    }

    public static boolean hasUTF16BEBom(byte[] bArr) {
        if (bArr == null) {
            $$$reportNull$$$0(42);
        }
        return ArrayUtil.startsWith(bArr, UTF16BE_BOM);
    }

    public static boolean hasUTF32BEBom(byte[] bArr) {
        if (bArr == null) {
            $$$reportNull$$$0(43);
        }
        return ArrayUtil.startsWith(bArr, UTF32BE_BOM);
    }

    public static boolean hasUTF32LEBom(byte[] bArr) {
        if (bArr == null) {
            $$$reportNull$$$0(44);
        }
        return ArrayUtil.startsWith(bArr, UTF32LE_BOM);
    }

    public static byte[] getMandatoryBom(Charset charset) {
        if (charset == null) {
            $$$reportNull$$$0(48);
        }
        return CHARSET_TO_MANDATORY_BOM.get(charset);
    }

    public static boolean canHaveBom(Charset charset, byte[] bArr) {
        if (charset == null) {
            $$$reportNull$$$0(50);
        }
        if (bArr == null) {
            $$$reportNull$$$0(51);
        }
        return (charset.equals(StandardCharsets.UTF_8) && Arrays.equals(bArr, UTF8_BOM)) || Arrays.equals(getMandatoryBom(charset), bArr);
    }

    public static Charset forName(String str) {
        if (str != null) {
            try {
                return Charset.forName(str);
            } catch (IllegalCharsetNameException | UnsupportedCharsetException unused) {
            }
        }
        return null;
    }
}
