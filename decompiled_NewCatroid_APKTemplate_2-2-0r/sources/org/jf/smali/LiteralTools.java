package org.jf.smali;

import androidx.exifinterface.media.ExifInterface;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import name.antonsmirnov.firmata.FormatHelper;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: loaded from: classes6.dex */
public class LiteralTools {
    private static Pattern specialFloatRegex = Pattern.compile("((-)?infinityf)|(nanf)", 2);
    private static Pattern specialDoubleRegex = Pattern.compile("((-)?infinityd?)|(nand?)", 2);

    public static byte parseByte(String str) throws NumberFormatException {
        char[] charArray;
        if (str == null) {
            throw new NumberFormatException("string is null");
        }
        if (str.length() == 0) {
            throw new NumberFormatException("string is blank");
        }
        byte b = 0;
        if (str.toUpperCase().endsWith(ExifInterface.GPS_DIRECTION_TRUE)) {
            charArray = str.substring(0, str.length() - 1).toCharArray();
        } else {
            charArray = str.toCharArray();
        }
        int i = charArray[0] != '-' ? 0 : 1;
        int i2 = i;
        byte b2 = 10;
        if (charArray[i] == '0') {
            int i3 = i + 1;
            if (i3 == charArray.length) {
                return (byte) 0;
            }
            char c = charArray[i3];
            if (c == 'x' || c == 'X') {
                i += 2;
                b2 = 16;
            } else {
                b2 = Character.digit(c, 8) >= 0 ? (byte) 8 : (byte) 10;
                i = i3;
            }
        }
        byte b3 = (byte) (127 / (b2 / 2));
        while (i < charArray.length) {
            int iDigit = Character.digit(charArray[i], (int) b2);
            if (iDigit < 0) {
                throw new NumberFormatException("The string contains invalid an digit - '" + charArray[i] + FormatHelper.QUOTE);
            }
            byte b4 = (byte) (b * b2);
            if (b > b3) {
                throw new NumberFormatException(str + " cannot fit into a byte");
            }
            if (b4 < 0 && b4 >= (-iDigit)) {
                throw new NumberFormatException(str + " cannot fit into a byte");
            }
            b = (byte) (b4 + iDigit);
            i++;
        }
        if (i2 == 0 || b == -128) {
            return b;
        }
        if (b >= 0) {
            return (byte) (b * (-1));
        }
        throw new NumberFormatException(str + " cannot fit into a byte");
    }

    public static short parseShort(String str) throws NumberFormatException {
        char[] charArray;
        if (str == null) {
            throw new NumberFormatException("string is null");
        }
        if (str.length() == 0) {
            throw new NumberFormatException("string is blank");
        }
        short s = 0;
        if (str.toUpperCase().endsWith(ExifInterface.LATITUDE_SOUTH)) {
            charArray = str.substring(0, str.length() - 1).toCharArray();
        } else {
            charArray = str.toCharArray();
        }
        int i = charArray[0] != '-' ? 0 : 1;
        int i2 = i;
        short s2 = 10;
        if (charArray[i] == '0') {
            int i3 = i + 1;
            if (i3 == charArray.length) {
                return (short) 0;
            }
            char c = charArray[i3];
            if (c == 'x' || c == 'X') {
                i += 2;
                s2 = 16;
            } else {
                s2 = Character.digit(c, 8) >= 0 ? (short) 8 : (short) 10;
                i = i3;
            }
        }
        short s3 = (short) (32767 / (s2 / 2));
        while (i < charArray.length) {
            int iDigit = Character.digit(charArray[i], (int) s2);
            if (iDigit < 0) {
                throw new NumberFormatException("The string contains invalid an digit - '" + charArray[i] + FormatHelper.QUOTE);
            }
            short s4 = (short) (s * s2);
            if (s > s3) {
                throw new NumberFormatException(str + " cannot fit into a short");
            }
            if (s4 < 0 && s4 >= (-iDigit)) {
                throw new NumberFormatException(str + " cannot fit into a short");
            }
            s = (short) (s4 + iDigit);
            i++;
        }
        if (i2 == 0 || s == Short.MIN_VALUE) {
            return s;
        }
        if (s >= 0) {
            return (short) (s * (-1));
        }
        throw new NumberFormatException(str + " cannot fit into a short");
    }

    public static int parseInt(String str) throws NumberFormatException {
        if (str == null) {
            throw new NumberFormatException("string is null");
        }
        if (str.length() == 0) {
            throw new NumberFormatException("string is blank");
        }
        char[] charArray = str.toCharArray();
        int i = 0;
        int i2 = charArray[0] == '-' ? 1 : 0;
        int i3 = i2;
        int i4 = 10;
        if (charArray[i2] == '0') {
            int i5 = i2 + 1;
            if (i5 == charArray.length) {
                return 0;
            }
            char c = charArray[i5];
            if (c == 'x' || c == 'X') {
                i2 += 2;
                i4 = 16;
            } else {
                i4 = Character.digit(c, 8) >= 0 ? 8 : 10;
                i2 = i5;
            }
        }
        int i6 = Integer.MAX_VALUE / (i4 / 2);
        while (i2 < charArray.length) {
            int iDigit = Character.digit(charArray[i2], i4);
            if (iDigit < 0) {
                throw new NumberFormatException("The string contains an invalid digit - '" + charArray[i2] + FormatHelper.QUOTE);
            }
            int i7 = i * i4;
            if (i > i6) {
                throw new NumberFormatException(str + " cannot fit into an int");
            }
            if (i7 < 0 && i7 >= (-iDigit)) {
                throw new NumberFormatException(str + " cannot fit into an int");
            }
            i = i7 + iDigit;
            i2++;
        }
        if (i3 == 0 || i == Integer.MIN_VALUE) {
            return i;
        }
        if (i >= 0) {
            return i * (-1);
        }
        throw new NumberFormatException(str + " cannot fit into an int");
    }

    public static long parseLong(String str) throws NumberFormatException {
        char[] charArray;
        if (str == null) {
            throw new NumberFormatException("string is null");
        }
        if (str.length() == 0) {
            throw new NumberFormatException("string is blank");
        }
        int i = 0;
        boolean z = true;
        if (str.toUpperCase().endsWith("L")) {
            charArray = str.substring(0, str.length() - 1).toCharArray();
        } else {
            charArray = str.toCharArray();
        }
        if (charArray[0] == '-') {
            i = 1;
        } else {
            z = false;
        }
        int i2 = 10;
        if (charArray[i] == '0') {
            int i3 = i + 1;
            if (i3 == charArray.length) {
                return 0L;
            }
            char c = charArray[i3];
            if (c == 'x' || c == 'X') {
                i += 2;
                i2 = 16;
            } else {
                i2 = Character.digit(c, 8) >= 0 ? 8 : 10;
                i = i3;
            }
        }
        long j = Long.MAX_VALUE / ((long) (i2 / 2));
        long j2 = 0;
        while (i < charArray.length) {
            int iDigit = Character.digit(charArray[i], i2);
            if (iDigit < 0) {
                throw new NumberFormatException("The string contains an invalid digit - '" + charArray[i] + FormatHelper.QUOTE);
            }
            long j3 = ((long) i2) * j2;
            if (j2 > j) {
                throw new NumberFormatException(str + " cannot fit into a long");
            }
            if (j3 < 0 && j3 >= (-iDigit)) {
                throw new NumberFormatException(str + " cannot fit into a long");
            }
            j2 = ((long) iDigit) + j3;
            i++;
        }
        if (!z || j2 == Long.MIN_VALUE) {
            return j2;
        }
        if (j2 >= 0) {
            return j2 * (-1);
        }
        throw new NumberFormatException(str + " cannot fit into a long");
    }

    public static float parseFloat(String str) {
        Matcher matcher = specialFloatRegex.matcher(str);
        if (matcher.matches()) {
            if (matcher.start(1) != -1) {
                return matcher.start(2) != -1 ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
            }
            return Float.NaN;
        }
        return Float.parseFloat(str);
    }

    public static double parseDouble(String str) {
        Matcher matcher = specialDoubleRegex.matcher(str);
        if (matcher.matches()) {
            if (matcher.start(1) != -1) {
                return matcher.start(2) != -1 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
            return Double.NaN;
        }
        return Double.parseDouble(str);
    }

    public static byte[] longToBytes(long j) {
        byte[] bArr = new byte[8];
        int i = 0;
        while (j != 0) {
            bArr[i] = (byte) j;
            j >>>= 8;
            i++;
        }
        return bArr;
    }

    public static byte[] intToBytes(int i) {
        byte[] bArr = new byte[4];
        int i2 = 0;
        while (i != 0) {
            bArr[i2] = (byte) i;
            i >>>= 8;
            i2++;
        }
        return bArr;
    }

    public static byte[] shortToBytes(short s) {
        return new byte[]{(byte) s, (byte) (s >>> 8)};
    }

    public static byte[] floatToBytes(float f) {
        return intToBytes(Float.floatToRawIntBits(f));
    }

    public static byte[] doubleToBytes(double d) {
        return longToBytes(Double.doubleToRawLongBits(d));
    }

    public static byte[] charToBytes(char c) {
        return shortToBytes((short) c);
    }

    public static byte[] boolToBytes(boolean z) {
        if (z) {
            return new byte[]{1};
        }
        return new byte[]{0};
    }

    public static void checkInt(long j) {
        if (j > -1 || j < -2147483648L) {
            throw new NumberFormatException(Long.toString(j) + " cannot fit into an int");
        }
    }

    public static void checkShort(long j) {
        if ((j > WebSocketProtocol.PAYLOAD_SHORT_MAX) || (j < -32768)) {
            throw new NumberFormatException(Long.toString(j) + " cannot fit into a short");
        }
    }

    public static void checkByte(long j) {
        if ((j > 255) || (j < -128)) {
            throw new NumberFormatException(Long.toString(j) + " cannot fit into a byte");
        }
    }

    public static void checkNibble(long j) {
        if ((j > 15) || (j < -8)) {
            throw new NumberFormatException(Long.toString(j) + " cannot fit into a nibble");
        }
    }
}
