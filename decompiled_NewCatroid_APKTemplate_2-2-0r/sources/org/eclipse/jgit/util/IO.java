package org.eclipse.jgit.util;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.io.SilentFileInputStream;

/* JADX INFO: loaded from: classes14.dex */
public class IO {
    public static final byte[] readFully(File file) throws IOException {
        return readFully(file, Integer.MAX_VALUE);
    }

    public static final byte[] readSome(File file, int i) throws IOException {
        Throwable th = null;
        try {
            SilentFileInputStream silentFileInputStream = new SilentFileInputStream(file);
            try {
                byte[] bArr = new byte[i];
                int i2 = 0;
                while (true) {
                    int i3 = silentFileInputStream.read(bArr, i2, i - i2);
                    if (i3 <= 0) {
                        break;
                    }
                    i2 += i3;
                }
                if (i2 == i) {
                    return bArr;
                }
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, 0, bArr2, 0, i2);
                return bArr2;
            } finally {
                silentFileInputStream.close();
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                th.addSuppressed(th2);
            }
            throw null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x005d A[LOOP:0: B:7:0x001a->B:25:0x005d, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:40:0x0050 A[EDGE_INSN: B:40:0x0050->B:19:0x0050 BREAK  A[LOOP:0: B:7:0x001a->B:25:0x005d], SYNTHETIC] */
    public static final byte[] readFully(File file, int i) throws IOException {
        int i2;
        Throwable th = null;
        try {
            SilentFileInputStream silentFileInputStream = new SilentFileInputStream(file);
            try {
                long jMax = Math.max(file.length(), 1L);
                if (jMax > i) {
                    throw new IOException(MessageFormat.format(JGitText.get().fileIsTooLarge, file));
                }
                byte[] bArr = new byte[(int) jMax];
                int i3 = 0;
                while (true) {
                    if (bArr.length == i3) {
                        if (bArr.length == i) {
                            if (silentFileInputStream.read() < 0) {
                                break;
                            }
                            throw new IOException(MessageFormat.format(JGitText.get().fileIsTooLarge, file));
                        }
                        byte[] bArr2 = new byte[Math.min(bArr.length * 2, i)];
                        System.arraycopy(bArr, 0, bArr2, 0, i3);
                        bArr = bArr2;
                        i2 = silentFileInputStream.read(bArr, i3, bArr.length - i3);
                        if (i2 < 0) {
                            break;
                            break;
                        }
                        i3 += i2;
                    } else {
                        i2 = silentFileInputStream.read(bArr, i3, bArr.length - i3);
                        if (i2 < 0) {
                            break;
                        }
                        i3 += i2;
                    }
                }
                if (i3 < bArr.length) {
                    byte[] bArr3 = new byte[i3];
                    System.arraycopy(bArr, 0, bArr3, 0, i3);
                    bArr = bArr3;
                }
                silentFileInputStream.close();
                return bArr;
            } catch (Throwable th2) {
                silentFileInputStream.close();
                throw th2;
            }
        } catch (Throwable th3) {
            if (0 == 0) {
                throw th3;
            }
            if (null != th3) {
                th.addSuppressed(th3);
            }
            throw null;
        }
    }

    public static ByteBuffer readWholeStream(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int i3 = inputStream.read(bArr, i2, i - i2);
            if (i3 < 0) {
                return ByteBuffer.wrap(bArr, 0, i2);
            }
            i2 += i3;
        }
        int i4 = inputStream.read();
        if (i4 < 0) {
            return ByteBuffer.wrap(bArr, 0, i2);
        }
        Throwable th = null;
        try {
            TemporaryBuffer.Heap heap = new TemporaryBuffer.Heap(Integer.MAX_VALUE);
            try {
                heap.write(bArr);
                heap.write(i4);
                heap.copy(inputStream);
                return ByteBuffer.wrap(heap.toByteArray());
            } finally {
                heap.close();
            }
        } catch (Throwable th2) {
            if (0 == 0) {
                throw th2;
            }
            if (null != th2) {
                th.addSuppressed(th2);
            }
            throw null;
        }
    }

    public static void readFully(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        while (i2 > 0) {
            int i3 = inputStream.read(bArr, i, i2);
            if (i3 <= 0) {
                throw new EOFException(JGitText.get().shortReadOfBlock);
            }
            i += i3;
            i2 -= i3;
        }
    }

    public static int read(ReadableByteChannel readableByteChannel, byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        if (i2 == 0) {
            return 0;
        }
        while (i2 > 0) {
            int i4 = readableByteChannel.read(ByteBuffer.wrap(bArr, i, i2));
            if (i4 <= 0) {
                break;
            }
            i += i4;
            i2 -= i4;
            i3 += i4;
        }
        if (i3 != 0) {
            return i3;
        }
        return -1;
    }

    public static int readFully(InputStream inputStream, byte[] bArr, int i) throws IOException {
        int i2;
        int i3 = 0;
        while (i < bArr.length && (i2 = inputStream.read(bArr, i, bArr.length - i)) >= 0) {
            i += i2;
            i3 += i2;
        }
        return i3;
    }

    public static void skipFully(InputStream inputStream, long j) throws IOException {
        while (j > 0) {
            long jSkip = inputStream.skip(j);
            if (jSkip <= 0) {
                throw new EOFException(JGitText.get().shortSkipOfBlock);
            }
            j -= jSkip;
        }
    }

    public static List<String> readLines(String str) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\n') {
                arrayList.add(sb.toString());
                sb.setLength(0);
            } else if (cCharAt == '\r') {
                i++;
                if (i < str.length()) {
                    char cCharAt2 = str.charAt(i);
                    arrayList.add(sb.toString());
                    sb.setLength(0);
                    if (cCharAt2 != '\n') {
                        sb.append(cCharAt2);
                    }
                } else {
                    arrayList.add(sb.toString());
                    break;
                }
            } else {
                sb.append(cCharAt);
            }
            i++;
        }
        arrayList.add(sb.toString());
        return arrayList;
    }

    public static String readLine(Reader reader, int i) throws IOException {
        StringBuilder sb;
        char c;
        if (reader.markSupported()) {
            if (i <= 0) {
                i = 1024;
            }
            int i2 = i;
            StringBuilder sb2 = new StringBuilder(i2);
            char[] cArr = new char[i2];
            while (true) {
                reader.mark(i2);
                int i3 = reader.read(cArr);
                if (i3 < 0) {
                    reader.reset();
                    return sb2.toString();
                }
                for (int i4 = 0; i4 < i3; i4++) {
                    if (cArr[i4] == '\n') {
                        int i5 = i4 + 1;
                        resetAndSkipFully(reader, i5);
                        sb2.append(cArr, 0, i5);
                        return sb2.toString();
                    }
                }
                if (i3 > 0) {
                    sb2.append(cArr, 0, i3);
                }
                resetAndSkipFully(reader, i3);
            }
        } else {
            if (i > 0) {
                sb = new StringBuilder(i);
            } else {
                sb = new StringBuilder();
            }
            do {
                int i6 = reader.read();
                if (i6 == -1) {
                    break;
                }
                c = (char) i6;
                sb.append(c);
            } while (c != '\n');
            return sb.toString();
        }
    }

    private static void resetAndSkipFully(Reader reader, long j) throws IOException {
        reader.reset();
        while (j > 0) {
            long jSkip = reader.skip(j);
            if (jSkip <= 0) {
                throw new EOFException(JGitText.get().shortSkipOfBlock);
            }
            j -= jSkip;
        }
    }

    private IO() {
    }
}
