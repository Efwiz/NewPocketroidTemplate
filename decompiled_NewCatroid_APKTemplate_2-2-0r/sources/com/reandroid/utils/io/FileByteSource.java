package com.reandroid.utils.io;

import com.reandroid.common.ByteSource;
import com.reandroid.common.FileChannelInputStream;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class FileByteSource implements ByteSource {
    private byte[] array;
    private File file;
    private int length;

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) throws IOException {
        this.file = file;
        int length = (int) file.length();
        byte[] bArr = this.array;
        if (bArr == null || length > bArr.length) {
            bArr = new byte[length];
            this.array = bArr;
        }
        this.length = length;
        FileChannelInputStream.read(file, bArr, length);
    }

    @Override // com.reandroid.common.ByteSource
    public byte read(int i) {
        if (i > this.length) {
            throw new IndexOutOfBoundsException("Out of range: " + i + ", max = " + this.length);
        }
        return this.array[i];
    }

    @Override // com.reandroid.common.ByteSource
    public void read(int i, byte[] bArr, int i2, int i3) {
        int i4 = i + i3;
        if (i4 > this.length) {
            throw new IndexOutOfBoundsException("Out of range: " + i4 + ", max = " + this.length);
        }
        System.arraycopy(this.array, i, bArr, i2, i3);
    }

    @Override // com.reandroid.common.ByteSource
    public int length() {
        return this.length;
    }
}
