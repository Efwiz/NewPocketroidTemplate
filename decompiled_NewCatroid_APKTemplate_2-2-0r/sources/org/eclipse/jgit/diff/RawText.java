package org.eclipse.jgit.diff;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import org.eclipse.jgit.errors.BinaryBlobException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.IntList;
import org.eclipse.jgit.util.RawParseUtils;

/* JADX INFO: loaded from: classes13.dex */
public class RawText extends Sequence {
    public static final RawText EMPTY_TEXT = new RawText(new byte[0]);
    static final int FIRST_FEW_BYTES = 8000;
    protected final byte[] content;
    protected final IntList lines;

    public RawText(byte[] bArr) {
        this(bArr, RawParseUtils.lineMap(bArr, 0, bArr.length));
    }

    public RawText(byte[] bArr, IntList intList) {
        this.content = bArr;
        this.lines = intList;
    }

    public RawText(File file) throws IOException {
        this(IO.readFully(file));
    }

    public byte[] getRawContent() {
        return this.content;
    }

    @Override // org.eclipse.jgit.diff.Sequence
    public int size() {
        return this.lines.size() - 2;
    }

    public void writeLine(OutputStream outputStream, int i) throws IOException {
        int start = getStart(i);
        int end = getEnd(i);
        byte[] bArr = this.content;
        if (bArr[end - 1] == 10) {
            end--;
        }
        outputStream.write(bArr, start, end - start);
    }

    public boolean isMissingNewlineAtEnd() {
        IntList intList = this.lines;
        int i = intList.get(intList.size() - 1);
        return i == 0 || this.content[i - 1] != 10;
    }

    public String getString(int i) {
        return getString(i, i + 1, true);
    }

    public ByteBuffer getRawString(int i) {
        int start = getStart(i);
        int end = getEnd(i);
        if (end > 0 && this.content[end - 1] == 10) {
            end--;
        }
        return ByteBuffer.wrap(this.content, start, end - start);
    }

    public String getString(int i, int i2, boolean z) {
        if (i == i2) {
            return "";
        }
        int start = getStart(i);
        int end = getEnd(i2 - 1);
        if (z && this.content[end - 1] == 10) {
            end--;
        }
        return decode(start, end);
    }

    protected String decode(int i, int i2) {
        return RawParseUtils.decode(this.content, i, i2);
    }

    private int getStart(int i) {
        return this.lines.get(i + 1);
    }

    private int getEnd(int i) {
        return this.lines.get(i + 2);
    }

    public static boolean isBinary(byte[] bArr) {
        return isBinary(bArr, bArr.length);
    }

    public static boolean isBinary(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[8000];
        int i = 0;
        while (i < 8000) {
            int i2 = inputStream.read(bArr, i, 8000 - i);
            if (i2 == -1) {
                break;
            }
            i += i2;
        }
        return isBinary(bArr, i);
    }

    public static boolean isBinary(byte[] bArr, int i) {
        if (i > 8000) {
            i = 8000;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (bArr[i2] == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCrLfText(byte[] bArr) {
        return isCrLfText(bArr, bArr.length);
    }

    public static boolean isCrLfText(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[8000];
        int i = 0;
        while (i < 8000) {
            int i2 = inputStream.read(bArr, i, 8000 - i);
            if (i2 == -1) {
                break;
            }
            i += i2;
        }
        return isCrLfText(bArr, i);
    }

    public static boolean isCrLfText(byte[] bArr, int i) {
        boolean z = false;
        for (int i2 = 0; i2 < i - 1; i2++) {
            byte b = bArr[i2];
            if (b == 0) {
                return false;
            }
            if (b == 13 && bArr[i2 + 1] == 10) {
                z = true;
            }
        }
        return z;
    }

    public String getLineDelimiter() {
        if (size() == 0) {
            return null;
        }
        int end = getEnd(0);
        byte[] bArr = this.content;
        if (bArr[end - 1] != 10) {
            return null;
        }
        if (bArr.length > 1 && end > 1 && bArr[end - 2] == 13) {
            return "\r\n";
        }
        return "\n";
    }

    public static RawText load(ObjectLoader objectLoader, int i) throws IOException, BinaryBlobException {
        long size = objectLoader.getSize();
        if (size > i) {
            throw new BinaryBlobException();
        }
        if (size <= 8000) {
            byte[] cachedBytes = objectLoader.getCachedBytes(8000);
            if (isBinary(cachedBytes)) {
                throw new BinaryBlobException();
            }
            return new RawText(cachedBytes);
        }
        byte[] bArr = new byte[8000];
        Throwable th = null;
        try {
            ObjectStream objectStreamOpenStream = objectLoader.openStream();
            int i2 = 8000;
            int i3 = 0;
            while (i2 > 0) {
                try {
                    int i4 = objectStreamOpenStream.read(bArr, i3, i2);
                    if (i4 < 0) {
                        throw new EOFException();
                    }
                    i2 -= i4;
                    while (i4 > 0) {
                        if (bArr[i3] == 0) {
                            throw new BinaryBlobException();
                        }
                        i3++;
                        i4--;
                    }
                } catch (Throwable th2) {
                    if (objectStreamOpenStream == null) {
                        throw th2;
                    }
                    objectStreamOpenStream.close();
                    throw th2;
                }
            }
            int i5 = (int) size;
            try {
                byte[] bArr2 = new byte[i5];
                System.arraycopy(bArr, 0, bArr2, 0, 8000);
                IO.readFully(objectStreamOpenStream, bArr2, i3, (int) (size - ((long) i3)));
                RawText rawText = new RawText(bArr2, RawParseUtils.lineMapOrBinary(bArr2, 0, i5));
                if (objectStreamOpenStream != null) {
                    objectStreamOpenStream.close();
                }
                return rawText;
            } catch (OutOfMemoryError e) {
                throw new LargeObjectException.OutOfMemory(e);
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
}
