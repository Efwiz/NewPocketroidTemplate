package name.antonsmirnov.firmata;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes12.dex */
public class BytesHelper {
    public static final int BITS_IN_BYTE = 8;
    public static final int BYTE_MAX_VALUE = 255;

    public static int DECODE_BYTE(int i, int i2) {
        return (i2 << 7) + i;
    }

    public static int DECODE_CHANNEL(int i) {
        return i & 15;
    }

    public static int DECODE_COMMAND(int i) {
        return i < 240 ? i & 240 : i;
    }

    public static int ENCODE_CHANNEL(int i) {
        return i & 15;
    }

    public static int LSB(int i) {
        return i & 127;
    }

    public static int MSB(int i) {
        return (i >> 7) & 127;
    }

    private static int bitMaskHigh(int i) {
        return 1 << i;
    }

    private static int bitMaskLow(int i) {
        int i2 = 7;
        int i3 = 0;
        while (i2 >= 0) {
            i3 |= i2 == i ? 0 : 1;
            if (i2 > 0) {
                i3 <<= 1;
            }
            i2--;
        }
        return i3;
    }

    public static String DECODE_STRING(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        int i3 = ((i2 - i) + 1) / 2;
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i + 1;
            byte b = bArr[i];
            i += 2;
            sb.append((char) DECODE_BYTE(b, bArr[i5]));
        }
        return sb.toString();
    }

    public static int[] DECODE_INT_ARRAY(byte[] bArr, int i, int i2) {
        int i3 = ((i2 - i) + 1) / 2;
        int[] iArr = new int[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i + 1;
            byte b = bArr[i];
            i += 2;
            iArr[i4] = DECODE_BYTE(b, bArr[i5]);
        }
        return iArr;
    }

    public static byte[] ENCODE_STRING(String str) {
        byte[] bytes = str.getBytes();
        byte[] bArr = new byte[bytes.length * 2];
        ENCODE_STRING(bytes, ByteBuffer.wrap(bArr), 0);
        return bArr;
    }

    public static byte[] ENCODE_INT_ARRAY(int[] iArr) {
        byte[] bArr = new byte[iArr.length * 2];
        ENCODE_STRING(iArr, ByteBuffer.wrap(bArr), 0);
        return bArr;
    }

    public static void ENCODE_STRING(byte[] bArr, ByteBuffer byteBuffer, int i) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = i + 1;
            byteBuffer.put(i, (byte) LSB(bArr[i2]));
            i += 2;
            byteBuffer.put(i3, (byte) MSB(bArr[i2]));
        }
    }

    public static void ENCODE_STRING(int[] iArr, ByteBuffer byteBuffer, int i) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int i3 = i + 1;
            byteBuffer.put(i, (byte) LSB(iArr[i2]));
            i += 2;
            byteBuffer.put(i3, (byte) MSB(iArr[i2]));
        }
    }

    public static int portByPin(int i) {
        return i / 8;
    }

    private static int pinMaskHigh(int i) {
        return bitMaskHigh(i);
    }

    private static int pinMaskLow(int i) {
        return bitMaskLow(i);
    }

    public static int pinInPort(int i) {
        return i % 8;
    }

    public static int setPin(int i, int i2, boolean z) {
        if (z) {
            return i | pinMaskHigh(i2);
        }
        return i & pinMaskLow(i2);
    }

    public static int setBit(int i, int i2, boolean z) {
        if (z) {
            return i | pinMaskHigh(i2);
        }
        return i & pinMaskLow(i2);
    }

    public static boolean getPin(int i, int i2) {
        return (i & pinMaskHigh(i2)) > 0;
    }
}
