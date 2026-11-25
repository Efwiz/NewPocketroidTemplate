package org.eclipse.jgit.internal.storage.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.InflaterCache;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.MutableInteger;
import org.eclipse.jgit.util.RawParseUtils;

/* JADX INFO: loaded from: classes13.dex */
public class UnpackedObject {
    private static final int BUFFER_SIZE = 8192;

    public static ObjectLoader parse(byte[] bArr, AnyObjectId anyObjectId) throws IOException {
        Throwable th = null;
        try {
            WindowCursor windowCursor = new WindowCursor(null);
            try {
                return open(new ByteArrayInputStream(bArr), null, anyObjectId, windowCursor);
            } finally {
                windowCursor.close();
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

    static ObjectLoader open(InputStream inputStream, File file, AnyObjectId anyObjectId, WindowCursor windowCursor) throws IOException {
        try {
            BufferedInputStream bufferedInputStreamBuffer = buffer(inputStream);
            bufferedInputStreamBuffer.mark(20);
            byte[] bArr = new byte[64];
            int i = 2;
            int i2 = 0;
            IO.readFully(bufferedInputStreamBuffer, bArr, 0, 2);
            if (isStandardFormat(bArr)) {
                bufferedInputStreamBuffer.reset();
                Inflater inflater = windowCursor.inflater();
                InflaterInputStream inflaterInputStreamInflate = inflate(bufferedInputStreamBuffer, inflater);
                int some = readSome(inflaterInputStreamInflate, bArr, 0, 64);
                if (some < 5) {
                    throw new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectNoHeader);
                }
                MutableInteger mutableInteger = new MutableInteger();
                int iDecodeTypeString = Constants.decodeTypeString(anyObjectId, bArr, (byte) 32, mutableInteger);
                long longBase10 = RawParseUtils.parseLongBase10(bArr, mutableInteger.value, mutableInteger);
                if (longBase10 < 0) {
                    throw new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectNegativeSize);
                }
                int i3 = mutableInteger.value;
                mutableInteger.value = i3 + 1;
                if (bArr[i3] != 0) {
                    throw new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectGarbageAfterSize);
                }
                if (file == null && 2147483647L < longBase10) {
                    LargeObjectException.ExceedsByteArrayLimit exceedsByteArrayLimit = new LargeObjectException.ExceedsByteArrayLimit();
                    exceedsByteArrayLimit.setObjectId(anyObjectId);
                    throw exceedsByteArrayLimit;
                }
                if (longBase10 >= windowCursor.getStreamFileThreshold() && file != null) {
                    return new LargeObject(iDecodeTypeString, longBase10, file, anyObjectId, windowCursor.db);
                }
                int i4 = (int) longBase10;
                byte[] bArr2 = new byte[i4];
                int i5 = some - mutableInteger.value;
                if (i5 > 0) {
                    System.arraycopy(bArr, mutableInteger.value, bArr2, 0, i5);
                }
                IO.readFully(inflaterInputStreamInflate, bArr2, i5, i4 - i5);
                checkValidEndOfStream(bufferedInputStreamBuffer, inflater, anyObjectId, bArr);
                return new ObjectLoader.SmallObject(iDecodeTypeString, bArr2);
            }
            readSome(bufferedInputStreamBuffer, bArr, 2, 18);
            byte b = bArr[0];
            int i6 = b & 255;
            int i7 = (i6 >> 4) & 7;
            long j = b & 15;
            int i8 = 4;
            int i9 = 4;
            int i10 = 1;
            while ((i6 & 128) != 0) {
                int i11 = i10 + 1;
                byte b2 = bArr[i10];
                int i12 = b2 & 255;
                j += ((long) (b2 & 127)) << i9;
                i9 += 7;
                i10 = i11;
                i = 2;
                i2 = 0;
                i6 = i12;
                i8 = 4;
            }
            if (i7 != 1 && i7 != i && i7 != 3 && i7 != i8) {
                throw new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectInvalidType);
            }
            if (file == null && 2147483647L < j) {
                LargeObjectException.ExceedsByteArrayLimit exceedsByteArrayLimit2 = new LargeObjectException.ExceedsByteArrayLimit();
                exceedsByteArrayLimit2.setObjectId(anyObjectId);
                throw exceedsByteArrayLimit2;
            }
            if (j >= windowCursor.getStreamFileThreshold() && file != null) {
                return new LargeObject(i7, j, file, anyObjectId, windowCursor.db);
            }
            bufferedInputStreamBuffer.reset();
            IO.skipFully(bufferedInputStreamBuffer, i10);
            Inflater inflater2 = windowCursor.inflater();
            int i13 = (int) j;
            byte[] bArr3 = new byte[i13];
            IO.readFully(inflate(bufferedInputStreamBuffer, inflater2), bArr3, i2, i13);
            checkValidEndOfStream(bufferedInputStreamBuffer, inflater2, anyObjectId, bArr);
            return new ObjectLoader.SmallObject(i7, bArr3);
        } catch (ZipException e) {
            CorruptObjectException corruptObjectException = new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectBadStream);
            corruptObjectException.initCause(e);
            throw corruptObjectException;
        }
    }

    static long getSize(InputStream inputStream, AnyObjectId anyObjectId, WindowCursor windowCursor) throws IOException {
        try {
            BufferedInputStream bufferedInputStreamBuffer = buffer(inputStream);
            bufferedInputStreamBuffer.mark(20);
            byte[] bArr = new byte[64];
            IO.readFully(bufferedInputStreamBuffer, bArr, 0, 2);
            if (isStandardFormat(bArr)) {
                bufferedInputStreamBuffer.reset();
                if (readSome(inflate(bufferedInputStreamBuffer, windowCursor.inflater()), bArr, 0, 64) < 5) {
                    throw new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectNoHeader);
                }
                MutableInteger mutableInteger = new MutableInteger();
                Constants.decodeTypeString(anyObjectId, bArr, (byte) 32, mutableInteger);
                long longBase10 = RawParseUtils.parseLongBase10(bArr, mutableInteger.value, mutableInteger);
                if (longBase10 >= 0) {
                    return longBase10;
                }
                throw new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectNegativeSize);
            }
            readSome(bufferedInputStreamBuffer, bArr, 2, 18);
            byte b = bArr[0];
            int i = b & 255;
            long j = b & 15;
            int i2 = 1;
            int i3 = 4;
            while ((i & 128) != 0) {
                int i4 = i2 + 1;
                byte b2 = bArr[i2];
                int i5 = b2 & 255;
                j += ((long) (b2 & 127)) << i3;
                i3 += 7;
                i2 = i4;
                i = i5;
            }
            return j;
        } catch (ZipException e) {
            CorruptObjectException corruptObjectException = new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectBadStream);
            corruptObjectException.initCause(e);
            throw corruptObjectException;
        }
    }

    static void checkValidEndOfStream(InputStream inputStream, Inflater inflater, AnyObjectId anyObjectId, byte[] bArr) throws IOException {
        while (inflater.inflate(bArr) == 0) {
            try {
                if (inflater.finished()) {
                    if (inflater.getRemaining() != 0 || inputStream.read() != -1) {
                        throw new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectBadStream);
                    }
                    return;
                } else {
                    if (!inflater.needsInput()) {
                        throw new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectBadStream);
                    }
                    int i = inputStream.read(bArr);
                    if (i <= 0) {
                        throw new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectBadStream);
                    }
                    inflater.setInput(bArr, 0, i);
                }
            } catch (DataFormatException e) {
                CorruptObjectException corruptObjectException = new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectBadStream);
                corruptObjectException.initCause(e);
                throw corruptObjectException;
            }
        }
        throw new CorruptObjectException(anyObjectId, JGitText.get().corruptObjectIncorrectLength);
    }

    static boolean isStandardFormat(byte[] bArr) {
        byte b = bArr[0];
        int i = b & 255;
        if ((b & Opcodes.OPC_d2l) == 8) {
            if (((bArr[1] & 255) | (i << 8)) % 31 == 0) {
                return true;
            }
        }
        return false;
    }

    static InputStream inflate(InputStream inputStream, long j, ObjectId objectId) {
        return new InflaterInputStream(inputStream, InflaterCache.get(), j, objectId) { // from class: org.eclipse.jgit.internal.storage.file.UnpackedObject.1
            private long remaining;
            private final /* synthetic */ ObjectId val$id;

            {
                this.val$id = objectId;
                this.remaining = j;
            }

            @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream, java.io.InputStream
            public int read(byte[] bArr, int i, int i2) throws IOException {
                try {
                    int i3 = super.read(bArr, i, i2);
                    if (i3 > 0) {
                        this.remaining -= (long) i3;
                    }
                    return i3;
                } catch (ZipException e) {
                    CorruptObjectException corruptObjectException = new CorruptObjectException(this.val$id, JGitText.get().corruptObjectBadStream);
                    corruptObjectException.initCause(e);
                    throw corruptObjectException;
                }
            }

            @Override // java.util.zip.InflaterInputStream, java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                try {
                    if (this.remaining <= 0) {
                        UnpackedObject.checkValidEndOfStream(this.in, this.inf, this.val$id, new byte[64]);
                    }
                } finally {
                    InflaterCache.release(this.inf);
                    super.close();
                }
            }
        };
    }

    private static InflaterInputStream inflate(InputStream inputStream, Inflater inflater) {
        return new InflaterInputStream(inputStream, inflater, 8192);
    }

    static BufferedInputStream buffer(InputStream inputStream) {
        return new BufferedInputStream(inputStream, 8192);
    }

    static int readSome(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        while (i2 > 0) {
            int i4 = inputStream.read(bArr, i, i2);
            if (i4 < 0) {
                break;
            }
            i3 += i4;
            i += i4;
            i2 -= i4;
        }
        return i3;
    }

    private static final class LargeObject extends ObjectLoader {

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        private final ObjectId f651id;
        private final File path;
        private final long size;
        private final FileObjectDatabase source;
        private final int type;

        @Override // org.eclipse.jgit.lib.ObjectLoader
        public boolean isLarge() {
            return true;
        }

        LargeObject(int i, long j, File file, AnyObjectId anyObjectId, FileObjectDatabase fileObjectDatabase) {
            this.type = i;
            this.size = j;
            this.path = file;
            this.f651id = anyObjectId.copy();
            this.source = fileObjectDatabase;
        }

        @Override // org.eclipse.jgit.lib.ObjectLoader
        public int getType() {
            return this.type;
        }

        @Override // org.eclipse.jgit.lib.ObjectLoader
        public long getSize() {
            return this.size;
        }

        @Override // org.eclipse.jgit.lib.ObjectLoader
        public byte[] getCachedBytes() throws LargeObjectException {
            throw new LargeObjectException(this.f651id);
        }

        @Override // org.eclipse.jgit.lib.ObjectLoader
        public ObjectStream openStream() throws Throwable {
            try {
                BufferedInputStream bufferedInputStreamBuffer = UnpackedObject.buffer(new FileInputStream(this.path));
                boolean z = false;
                try {
                    byte[] bArr = new byte[64];
                    bufferedInputStreamBuffer.mark(20);
                    IO.readFully(bufferedInputStreamBuffer, bArr, 0, 2);
                    if (UnpackedObject.isStandardFormat(bArr)) {
                        bufferedInputStreamBuffer.reset();
                        bufferedInputStreamBuffer = UnpackedObject.buffer(UnpackedObject.inflate(bufferedInputStreamBuffer, this.size, this.f651id));
                        while (bufferedInputStreamBuffer.read() > 0) {
                        }
                    } else {
                        UnpackedObject.readSome(bufferedInputStreamBuffer, bArr, 2, 18);
                        int i = bArr[0] & 255;
                        int i2 = 1;
                        while ((i & 128) != 0) {
                            int i3 = i2 + 1;
                            int i4 = bArr[i2] & 255;
                            i2 = i3;
                            i = i4;
                        }
                        bufferedInputStreamBuffer.reset();
                        IO.skipFully(bufferedInputStreamBuffer, i2);
                        bufferedInputStreamBuffer = UnpackedObject.buffer(UnpackedObject.inflate(bufferedInputStreamBuffer, this.size, this.f651id));
                    }
                    try {
                        return new ObjectStream.Filter(this.type, this.size, bufferedInputStreamBuffer);
                    } catch (Throwable th) {
                        th = th;
                        z = true;
                        if (!z) {
                            bufferedInputStreamBuffer.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (FileNotFoundException e) {
                if (this.path.exists()) {
                    throw e;
                }
                return this.source.open(this.f651id, this.type).openStream();
            }
        }
    }
}
