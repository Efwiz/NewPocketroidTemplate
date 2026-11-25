package com.leff.midi.util;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.InputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:com/leff/midi/util/VariableLengthInt.class
  classes10.dex
 */
/* JADX INFO: loaded from: classes10.dex */
public class VariableLengthInt {
    private byte[] mBytes;
    private int mSizeInBytes;
    private int mValue;

    public VariableLengthInt(int value) {
        setValue(value);
    }

    public VariableLengthInt(InputStream in) throws IOException {
        parseBytes(in);
    }

    public void setValue(int value) {
        this.mValue = value;
        buildBytes();
    }

    public int getValue() {
        return this.mValue;
    }

    public int getByteCount() {
        return this.mSizeInBytes;
    }

    public byte[] getBytes() {
        return this.mBytes;
    }

    private void parseBytes(InputStream in) throws IOException {
        int i;
        int[] iArr = new int[4];
        this.mSizeInBytes = 0;
        this.mValue = 0;
        int i2 = in.read();
        while (true) {
            int i3 = this.mSizeInBytes;
            if (i3 >= 4) {
                break;
            }
            this.mSizeInBytes = i3 + 1;
            if ((i2 & 128) <= 0) {
                iArr[i3] = i2 & 127;
                break;
            } else {
                iArr[i3] = i2 & 127;
                i2 = in.read();
            }
        }
        int i4 = 1;
        int i5 = 0;
        while (true) {
            i = this.mSizeInBytes;
            if (i4 >= i) {
                break;
            }
            i5 += 7;
            i4++;
        }
        this.mBytes = new byte[i];
        for (int i6 = 0; i6 < this.mSizeInBytes; i6++) {
            byte[] bArr = this.mBytes;
            int i7 = iArr[i6];
            bArr[i6] = (byte) i7;
            this.mValue += i7 << i5;
            i5 -= 7;
        }
    }

    private void buildBytes() {
        int i;
        int i2 = this.mValue;
        int i3 = 0;
        if (i2 == 0) {
            this.mBytes = new byte[]{0};
            this.mSizeInBytes = 1;
            return;
        }
        this.mSizeInBytes = 0;
        int[] iArr = new int[4];
        while (true) {
            int i4 = this.mSizeInBytes;
            if (i4 >= 4 || i2 <= 0) {
                break;
            }
            iArr[i4] = i2 & 127;
            this.mSizeInBytes = i4 + 1;
            i2 >>= 7;
        }
        int i5 = 1;
        while (true) {
            i = this.mSizeInBytes;
            if (i5 >= i) {
                break;
            }
            iArr[i5] = iArr[i5] | 128;
            i5++;
        }
        this.mBytes = new byte[i];
        while (true) {
            int i6 = this.mSizeInBytes;
            if (i3 >= i6) {
                return;
            }
            this.mBytes[i3] = (byte) iArr[(i6 - i3) - 1];
            i3++;
        }
    }

    public String toString() {
        return MidiUtil.bytesToHex(this.mBytes) + " (" + this.mValue + Constant.AFTER_QUTO;
    }
}
