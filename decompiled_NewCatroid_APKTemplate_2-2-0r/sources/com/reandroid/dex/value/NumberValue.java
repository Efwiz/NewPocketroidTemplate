package com.reandroid.dex.value;

import com.reandroid.dex.base.DexBlockItem;
import com.reandroid.utils.HexUtil;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: loaded from: classes11.dex */
public class NumberValue extends DexBlockItem {
    private static int calculateUnsignedSize(long j) {
        if (j == 0) {
            return 1;
        }
        int i = 0;
        while (j != 0) {
            j >>>= 8;
            i++;
        }
        return i;
    }

    public NumberValue() {
        super(1);
    }

    public int getSize() {
        return countBytes();
    }

    public void setSize(int i) {
        setBytesLength(i, false);
    }

    public long getSignedNumber() {
        return getSignedNumber(getBytesInternal(), 0, getSize());
    }

    public long getUnsignedNumber() {
        return getUnsignedNumber(getBytesInternal(), 0, getSize());
    }

    public void setNumberValue(byte b) {
        setSize(1);
        getBytesInternal()[0] = b;
    }

    public void setNumberValue(short s) {
        setSignedNumberValue(((long) s) & WebSocketProtocol.PAYLOAD_SHORT_MAX, s < 0);
    }

    public void setNumberValue(int i) {
        setSignedNumberValue(((long) i) & 4294967295L, i < 0);
    }

    public void setNumberValue(long j) {
        setSignedNumberValue(j, j < 0);
    }

    public void setUnsignedNumber(long j) {
        int iCalculateUnsignedSize = calculateUnsignedSize(j);
        setSize(iCalculateUnsignedSize);
        putNumber(getBytesInternal(), 0, iCalculateUnsignedSize, j);
    }

    public void setNumber(long j, int i) {
        setSize(i);
        putNumber(getBytesInternal(), 0, i, j);
    }

    private void setSignedNumberValue(long j, boolean z) {
        int iCalculateSignedSize = calculateSignedSize(j, z);
        setSize(iCalculateSignedSize);
        putNumber(getBytesInternal(), 0, iCalculateSignedSize, j);
    }

    public String toHex() {
        return HexUtil.toHex(getUnsignedNumber(), getSize());
    }

    public void merge(NumberValue numberValue) {
        byte[] bytes = numberValue.getBytes();
        int length = bytes.length;
        setBytesLength(length, false);
        byte[] bytesInternal = getBytesInternal();
        for (int i = 0; i < length; i++) {
            bytesInternal[i] = bytes[i];
        }
    }

    public int hashCode() {
        return Long.hashCode(getUnsignedNumber());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && getUnsignedNumber() == ((NumberValue) obj).getUnsignedNumber();
    }

    public String toString() {
        return getSize() + ":" + toHex();
    }

    private static int calculateSignedSize(long j, boolean z) {
        if (j == 0) {
            return 1;
        }
        long j2 = j;
        int i = 0;
        int i2 = 0;
        while (j2 != 0) {
            i2 = (int) j2;
            j2 >>>= 8;
            i++;
        }
        if (!z && i2 >= 128) {
            return i + 1;
        }
        if (!z) {
            return i;
        }
        byte[] bArr = new byte[i];
        putNumber(bArr, 0, i, j);
        for (int i3 = i - 1; i3 > 0; i3--) {
            int i4 = bArr[i3] & 255;
            int i5 = bArr[i3 - 1] & 255;
            if (i4 != 255 || i5 <= 128) {
                return i;
            }
            i--;
        }
        return i;
    }
}
