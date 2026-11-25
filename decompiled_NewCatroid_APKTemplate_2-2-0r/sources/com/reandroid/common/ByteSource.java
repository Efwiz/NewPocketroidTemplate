package com.reandroid.common;

/* JADX INFO: loaded from: classes10.dex */
public interface ByteSource {
    int length();

    byte read(int i);

    void read(int i, byte[] bArr, int i2, int i3);

    default int indexOf(int i, byte b) {
        int length = length();
        int i2 = length - 1;
        while (i < length) {
            if (b == read(i)) {
                return i;
            }
            if (i == i2) {
                length = length();
                i2 = length - 1;
            }
            i++;
        }
        return -1;
    }

    static ByteSource of(byte[] bArr) {
        return of(bArr, 0, bArr.length);
    }

    static ByteSource of(final byte[] bArr, final int i, final int i2) {
        return new ByteSource() { // from class: com.reandroid.common.ByteSource.1
            @Override // com.reandroid.common.ByteSource
            public byte read(int i3) {
                return bArr[i3];
            }

            @Override // com.reandroid.common.ByteSource
            public void read(int i3, byte[] bArr2, int i4, int i5) {
                System.arraycopy(bArr, i + i3, bArr2, i4, i5);
            }

            @Override // com.reandroid.common.ByteSource
            public int length() {
                return i2;
            }
        };
    }
}
