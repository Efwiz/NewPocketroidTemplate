package org.jf.util.io;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes6.dex */
public class ByteStreams {
    private static final byte[] DUMMY_BUFFER = new byte[4096];

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            int i = inputStream.read(bArr);
            if (i == -1) {
                return j;
            }
            outputStream.write(bArr, 0, i);
            j += (long) i;
        }
    }

    public static void skipFully(InputStream inputStream, long j) throws IOException {
        long jSkipUpTo = skipUpTo(inputStream, j);
        if (jSkipUpTo < j) {
            throw new EOFException("reached end of stream after skipping " + jSkipUpTo + " bytes; " + j + " bytes expected");
        }
    }

    static long skipUpTo(InputStream inputStream, long j) throws IOException {
        byte[] bArr = DUMMY_BUFFER;
        long j2 = 0;
        while (j2 < j) {
            long j3 = inputStream.read(bArr, 0, (int) Math.min(j - j2, bArr.length));
            if (j3 == -1) {
                break;
            }
            j2 += j3;
        }
        return j2;
    }

    public static void readFully(InputStream inputStream, byte[] bArr) throws IOException {
        readFully(inputStream, bArr, 0, bArr.length);
    }

    public static void readFully(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        int i3 = inputStream.read(bArr, i, i2);
        if (i3 != i2) {
            throw new EOFException("reached end of stream after reading " + i3 + " bytes; " + i2 + " bytes expected");
        }
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[40960];
        while (true) {
            int i = inputStream.read(bArr);
            if (i > 0) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                byteArrayOutputStream.close();
                inputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static byte[] toByteArray(File file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) file.length());
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[8192];
        while (true) {
            int i = fileInputStream.read(bArr);
            if (i > 0) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                byteArrayOutputStream.close();
                fileInputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }
}
