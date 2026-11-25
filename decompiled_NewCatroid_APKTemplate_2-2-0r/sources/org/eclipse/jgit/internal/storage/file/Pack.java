package org.eclipse.jgit.internal.storage.file;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoPackSignatureException;
import org.eclipse.jgit.errors.PackInvalidException;
import org.eclipse.jgit.errors.PackMismatchException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.errors.UnpackException;
import org.eclipse.jgit.errors.UnsupportedPackIndexVersionException;
import org.eclipse.jgit.errors.UnsupportedPackVersionException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.BinaryDelta;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.pack.PackOutputStream;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.util.LongList;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.RawParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* JADX INFO: loaded from: classes13.dex */
public class Pack implements Iterable<PackIndex.MutableEntry> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = LoggerFactory.getLogger((Class<?>) Pack.class);
    public static final Comparator<Pack> SORT = new Comparator() { // from class: org.eclipse.jgit.internal.storage.file.Pack$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((Pack) obj2).packLastModified.compareTo(((Pack) obj).packLastModified);
        }
    };
    private int activeCopyRawData;
    private int activeWindows;
    private PackBitmapIndex bitmapIdx;

    @Nullable
    private PackFile bitmapIdxFile;
    private volatile LongList corruptObjects;
    private RandomAccessFile fd;
    private PackFileSnapshot fileSnapshot;
    final int hash;
    private volatile boolean invalid;
    private volatile Exception invalidatingCause;
    private PackFile keepFile;
    long length;
    private volatile PackIndex loadedIdx;
    private byte[] packChecksum;
    private final PackFile packFile;
    Instant packLastModified;
    private PackReverseIndex reverseIdx;
    private final Object readLock = new Object();
    private AtomicInteger transientErrorCount = new AtomicInteger();

    public Pack(File file, @Nullable PackFile packFile) {
        this.packFile = new PackFile(file);
        PackFileSnapshot packFileSnapshotSave = PackFileSnapshot.save(file);
        this.fileSnapshot = packFileSnapshotSave;
        this.packLastModified = packFileSnapshotSave.lastModifiedInstant();
        this.bitmapIdxFile = packFile;
        this.hash = System.identityHashCode(this) * 31;
        this.length = Long.MAX_VALUE;
    }

    private PackIndex idx() throws IOException {
        PackIndex packIndex = this.loadedIdx;
        if (packIndex == null) {
            synchronized (this) {
                packIndex = this.loadedIdx;
                if (packIndex == null) {
                    if (this.invalid) {
                        throw new PackInvalidException(this.packFile, this.invalidatingCause);
                    }
                    try {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        PackFile packFileCreate = this.packFile.create(PackExt.INDEX);
                        PackIndex packIndexOpen = PackIndex.open(packFileCreate);
                        Logger logger = LOG;
                        if (logger.isDebugEnabled()) {
                            logger.debug(String.format("Opening pack index %s, size %.3f MB took %d ms", packFileCreate.getAbsolutePath(), Float.valueOf(packFileCreate.length() / 1048576.0f), Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis)));
                        }
                        byte[] bArr = this.packChecksum;
                        if (bArr == null) {
                            byte[] bArr2 = packIndexOpen.packChecksum;
                            this.packChecksum = bArr2;
                            this.fileSnapshot.setChecksum(ObjectId.fromRaw(bArr2));
                        } else if (!Arrays.equals(bArr, packIndexOpen.packChecksum)) {
                            throw new PackMismatchException(MessageFormat.format(JGitText.get().packChecksumMismatch, this.packFile.getPath(), ObjectId.fromRaw(this.packChecksum).name(), ObjectId.fromRaw(packIndexOpen.packChecksum).name()));
                        }
                        this.loadedIdx = packIndexOpen;
                        packIndex = packIndexOpen;
                    } catch (InterruptedIOException e) {
                        throw e;
                    } catch (IOException e2) {
                        this.invalid = true;
                        this.invalidatingCause = e2;
                        throw e2;
                    }
                }
            }
        }
        return packIndex;
    }

    public PackFile getPackFile() {
        return this.packFile;
    }

    public PackIndex getIndex() throws IOException {
        return idx();
    }

    public String getPackName() {
        return this.packFile.getId();
    }

    public boolean hasObject(AnyObjectId anyObjectId) throws IOException {
        long jFindOffset = idx().findOffset(anyObjectId);
        return 0 < jFindOffset && !isCorrupt(jFindOffset);
    }

    public boolean shouldBeKept() {
        if (this.keepFile == null) {
            this.keepFile = this.packFile.create(PackExt.KEEP);
        }
        return this.keepFile.exists();
    }

    ObjectLoader get(WindowCursor windowCursor, AnyObjectId anyObjectId) throws IOException {
        long jFindOffset = idx().findOffset(anyObjectId);
        if (0 >= jFindOffset || isCorrupt(jFindOffset)) {
            return null;
        }
        return load(windowCursor, jFindOffset);
    }

    void resolve(Set<ObjectId> set, AbbreviatedObjectId abbreviatedObjectId, int i) throws IOException {
        idx().resolve(set, abbreviatedObjectId, i);
    }

    public void close() {
        WindowCache.purge(this);
        synchronized (this) {
            this.loadedIdx = null;
            this.reverseIdx = null;
        }
    }

    @Override // java.lang.Iterable
    public Iterator<PackIndex.MutableEntry> iterator() {
        try {
            return idx().iterator();
        } catch (IOException unused) {
            return Collections.emptyList().iterator();
        }
    }

    long getObjectCount() throws IOException {
        return idx().getObjectCount();
    }

    ObjectId findObjectForOffset(long j) throws IOException {
        return getReverseIdx().findObject(j);
    }

    PackFileSnapshot getFileSnapshot() {
        return this.fileSnapshot;
    }

    AnyObjectId getPackChecksum() {
        return ObjectId.fromRaw(this.packChecksum);
    }

    private final byte[] decompress(long j, int i, WindowCursor windowCursor) throws DataFormatException, IOException {
        try {
            byte[] bArr = new byte[i];
            if (windowCursor.inflate(this, j, bArr, false) == i) {
                return bArr;
            }
            throw new EOFException(MessageFormat.format(JGitText.get().shortCompressedStreamAt, Long.valueOf(j)));
        } catch (OutOfMemoryError unused) {
            return null;
        }
    }

    void copyPackAsIs(PackOutputStream packOutputStream, WindowCursor windowCursor) throws IOException {
        windowCursor.pin(this, 0L);
        windowCursor.copyPackAsIs(this, this.length, packOutputStream);
    }

    final void copyAsIs(PackOutputStream packOutputStream, LocalObjectToPack localObjectToPack, boolean z, WindowCursor windowCursor) throws StoredObjectRepresentationNotAvailableException, IOException {
        beginCopyAsIs(localObjectToPack);
        try {
            copyAsIs2(packOutputStream, localObjectToPack, z, windowCursor);
        } finally {
            endCopyAsIs();
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0246  */
    /* JADX WARN: Code duplicated, block: B:110:0x0278  */
    /* JADX WARN: Code duplicated, block: B:112:0x028f  */
    /* JADX WARN: Code duplicated, block: B:137:0x0292 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:90:0x0200  */
    /* JADX WARN: Code duplicated, block: B:91:0x020f  */
    /* JADX WARN: Code duplicated, block: B:93:0x021b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:94:0x021d  */
    /* JADX WARN: Code duplicated, block: B:98:0x0224 A[LOOP:2: B:95:0x021f->B:98:0x0224, LOOP_END] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r17v0, types: [long] */
    /* JADX WARN: Type inference failed for: r28v0 */
    /* JADX WARN: Type inference failed for: r28v1 */
    /* JADX WARN: Type inference failed for: r28v2 */
    /* JADX WARN: Type inference failed for: r28v3 */
    /* JADX WARN: Type inference failed for: r28v4 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11, types: [long] */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v18 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v21, types: [long] */
    /* JADX WARN: Type inference failed for: r8v22, types: [org.eclipse.jgit.internal.storage.file.LocalObjectToPack] */
    /* JADX WARN: Type inference failed for: r8v25 */
    /* JADX WARN: Type inference failed for: r8v26 */
    /* JADX WARN: Type inference failed for: r8v27 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [org.eclipse.jgit.internal.storage.file.LocalObjectToPack, org.eclipse.jgit.internal.storage.pack.ObjectToPack] */
    /* JADX WARN: Type inference failed for: r8v5, types: [org.eclipse.jgit.internal.storage.pack.ObjectToPack] */
    /* JADX WARN: Type inference failed for: r9v10, types: [long] */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARN: Type inference failed for: r9v7, types: [long] */
    /* JADX WARN: Type inference failed for: r9v8, types: [long] */
    private void copyAsIs2(PackOutputStream packOutputStream, LocalObjectToPack localObjectToPack, boolean z, WindowCursor windowCursor) throws StoredObjectRepresentationNotAvailableException, IOException {
        ?? r8;
        long jFindCRC32;
        long j;
        ?? r28;
        LocalObjectToPack localObjectToPack2;
        CRC32 crc32;
        long j2;
        long j3;
        ?? r9;
        long j4;
        ?? r10;
        int iMin;
        CRC32 crc33;
        long j5;
        ?? r6;
        ?? r11;
        int i;
        LocalObjectToPack localObjectToPack3 = localObjectToPack;
        CRC32 crc34 = z ? new CRC32() : null;
        CRC32 crc35 = z ? new CRC32() : null;
        byte[] copyBuffer = packOutputStream.getCopyBuffer();
        readFully(localObjectToPack3.offset, copyBuffer, 0, 20, windowCursor);
        int i2 = 0;
        byte b = copyBuffer[0];
        int i3 = b & 255;
        int i4 = (i3 >> 4) & 7;
        long j6 = b & 15;
        int i5 = 4;
        int i6 = 1;
        long j7 = j6;
        while ((i3 & 128) != 0) {
            LocalObjectToPack localObjectToPack4 = localObjectToPack3;
            CRC32 crc36 = crc35;
            int i7 = i6 + 1;
            byte b2 = copyBuffer[i6];
            int i8 = b2 & 255;
            j7 += ((long) (b2 & 127)) << i5;
            i5 += 7;
            i6 = i7;
            i2 = 0;
            localObjectToPack3 = localObjectToPack4;
            crc35 = crc36;
            i3 = i8;
        }
        if (i4 == 6) {
            while (true) {
                i = i6 + 1;
                if ((copyBuffer[i6] & 128) == 0) {
                    break;
                } else {
                    i6 = i;
                }
            }
            if (z) {
                crc34.update(copyBuffer, i2, i);
                crc35.update(copyBuffer, i2, i);
            }
            i6 = i;
        } else if (i4 == 7) {
            if (z) {
                crc34.update(copyBuffer, i2, i6);
                crc35.update(copyBuffer, i2, i6);
            }
            int i9 = i6;
            readFully(localObjectToPack3.offset + ((long) i6), copyBuffer, 0, 20, windowCursor);
            if (z) {
                crc34.update(copyBuffer, i2, 20);
                crc35.update(copyBuffer, i2, 20);
            }
            i6 = i9 + 20;
        } else if (z) {
            crc34.update(copyBuffer, i2, i6);
            crc35.update(copyBuffer, i2, i6);
        }
        long j8 = localObjectToPack3.offset + ((long) i6);
        long j9 = localObjectToPack3.length;
        long j10 = j7;
        long j11 = j8;
        try {
            ByteArrayWindow byteArrayWindowQuickCopy = windowCursor.quickCopy(this, j8, j9);
            if (z) {
                try {
                    if (idx().hasCRC32Support()) {
                        jFindCRC32 = idx().findCRC32(localObjectToPack3);
                        if (byteArrayWindowQuickCopy != null) {
                            j = j9;
                            byteArrayWindowQuickCopy.crc32(crc34, j11, (int) j);
                        } else {
                            j = j9;
                            long j12 = j;
                            long j13 = j11;
                            while (j12 > 0) {
                                int iMin2 = (int) Math.min(j12, copyBuffer.length);
                                readFully(j13, copyBuffer, 0, iMin2, windowCursor);
                                crc34.update(copyBuffer, 0, iMin2);
                                long j14 = iMin2;
                                j13 += j14;
                                j12 -= j14;
                                j = j;
                                this = this;
                                localObjectToPack3 = localObjectToPack;
                            }
                        }
                        if (crc34.getValue() != jFindCRC32) {
                            this.setCorrupt(localObjectToPack3.offset);
                            throw new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(localObjectToPack3.offset), getPackFile()));
                        }
                        r28 = j;
                        localObjectToPack2 = localObjectToPack3;
                        crc32 = crc35;
                        j2 = j11;
                        throw new StoredObjectRepresentationNotAvailableException(r8, e);
                    }
                    if (byteArrayWindowQuickCopy != null) {
                        packOutputStream.writeHeader(localObjectToPack2, j10);
                        byteArrayWindowQuickCopy.write(packOutputStream, j2, (int) r28);
                        return;
                    }
                    j3 = j2;
                    r9 = r28;
                    if (r9 <= copyBuffer.length) {
                        if (!z) {
                            j5 = j3;
                            r6 = r9;
                            while (r6 > 0) {
                                int iMin3 = (int) Math.min((long) r6, copyBuffer.length);
                                readFully(j5, copyBuffer, 0, iMin3, windowCursor);
                                long j15 = iMin3;
                                j5 += j15;
                                r6 -= j15;
                            }
                        }
                        packOutputStream.writeHeader(localObjectToPack2, j10);
                        packOutputStream.write(copyBuffer, 0, (int) r9);
                        return;
                    }
                    packOutputStream.writeHeader(localObjectToPack2, j10);
                    j4 = j3;
                    r10 = r9;
                    while (r10 > 0) {
                        iMin = (int) Math.min((long) r10, copyBuffer.length);
                        readFully(j4, copyBuffer, 0, iMin, windowCursor);
                        crc33 = crc32;
                        if (z) {
                            crc33.update(copyBuffer, 0, iMin);
                        }
                        packOutputStream.write(copyBuffer, 0, iMin);
                        long j16 = iMin;
                        j4 += j16;
                        crc32 = crc33;
                        r10 -= j16;
                    }
                    if (!z && crc32.getValue() != jFindCRC32) {
                        throw new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(localObjectToPack2.offset), getPackFile()));
                    }
                } catch (IOException e) {
                    e = e;
                    r8 = localObjectToPack;
                    throw new StoredObjectRepresentationNotAvailableException(r8, e);
                } catch (DataFormatException e2) {
                    e = e2;
                    r8 = localObjectToPack;
                    setCorrupt(r8.offset);
                    throw new StoredObjectRepresentationNotAvailableException(r8, new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(r8.offset), getPackFile()), e));
                }
            }
            long j17 = j9;
            if (z) {
                Inflater inflater = windowCursor.inflater();
                byte[] bArr = new byte[1024];
                try {
                    try {
                        if (byteArrayWindowQuickCopy != null) {
                            byteArrayWindowQuickCopy.check(inflater, bArr, j11, (int) j17);
                            r11 = j17;
                        } else {
                            long j18 = j17;
                            long j19 = j11;
                            r8 = j17;
                            while (true) {
                                r11 = r8;
                                if (j18 <= 0) {
                                    break;
                                }
                                try {
                                    ?? r29 = r8;
                                    Inflater inflater2 = inflater;
                                    int iMin4 = (int) Math.min(j18, copyBuffer.length);
                                    long j20 = j18;
                                    byte[] bArr2 = bArr;
                                    CRC32 crc37 = crc35;
                                    long j21 = j11;
                                    readFully(j19, copyBuffer, 0, iMin4, windowCursor);
                                    crc34.update(copyBuffer, 0, iMin4);
                                    inflater2.setInput(copyBuffer, 0, iMin4);
                                    for (int i10 = 0; inflater2.inflate(bArr2, i10, 1024) > 0; i10 = 0) {
                                    }
                                    long j22 = iMin4;
                                    j19 += j22;
                                    j18 = j20 - j22;
                                    bArr = bArr2;
                                    inflater = inflater2;
                                    crc35 = crc37;
                                    j11 = j21;
                                    r8 = r29;
                                } catch (IOException e3) {
                                    e = e3;
                                } catch (DataFormatException e4) {
                                    e = e4;
                                    setCorrupt(r8.offset);
                                    throw new StoredObjectRepresentationNotAvailableException(r8, new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(r8.offset), getPackFile()), e));
                                }
                            }
                            if (inflater.finished() || inflater.getBytesRead() != r11) {
                                r8 = localObjectToPack;
                                setCorrupt(r8.offset);
                                throw new EOFException(MessageFormat.format(JGitText.get().shortCompressedStreamAt, Long.valueOf(r8.offset)));
                            }
                            jFindCRC32 = crc34.getValue();
                            r28 = r11;
                            crc32 = crc35;
                            j2 = j11;
                            localObjectToPack2 = localObjectToPack;
                        }
                        setCorrupt(r8.offset);
                        throw new EOFException(MessageFormat.format(JGitText.get().shortCompressedStreamAt, Long.valueOf(r8.offset)));
                    } catch (IOException e5) {
                        e = e5;
                    } catch (DataFormatException e6) {
                        e = e6;
                        setCorrupt(r8.offset);
                        throw new StoredObjectRepresentationNotAvailableException(r8, new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(r8.offset), getPackFile()), e));
                    }
                    if (inflater.finished()) {
                    }
                    r8 = localObjectToPack;
                } catch (IOException e7) {
                    e = e7;
                    r8 = localObjectToPack;
                } catch (DataFormatException e8) {
                    e = e8;
                    r8 = localObjectToPack;
                    setCorrupt(r8.offset);
                    throw new StoredObjectRepresentationNotAvailableException(r8, new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(r8.offset), getPackFile()), e));
                }
            } else {
                r28 = j17;
                crc32 = crc35;
                j2 = j11;
                localObjectToPack2 = localObjectToPack;
                jFindCRC32 = -1;
            }
            if (byteArrayWindowQuickCopy != null) {
                packOutputStream.writeHeader(localObjectToPack2, j10);
                byteArrayWindowQuickCopy.write(packOutputStream, j2, (int) r28);
                return;
            }
            j3 = j2;
            r9 = r28;
            if (r9 <= copyBuffer.length) {
                if (!z) {
                    j5 = j3;
                    r6 = r9;
                    while (r6 > 0) {
                        int iMin5 = (int) Math.min((long) r6, copyBuffer.length);
                        readFully(j5, copyBuffer, 0, iMin5, windowCursor);
                        long j110 = iMin5;
                        j5 += j110;
                        r6 -= j110;
                    }
                }
                packOutputStream.writeHeader(localObjectToPack2, j10);
                packOutputStream.write(copyBuffer, 0, (int) r9);
                return;
            }
            packOutputStream.writeHeader(localObjectToPack2, j10);
            j4 = j3;
            r10 = r9;
            while (r10 > 0) {
                iMin = (int) Math.min((long) r10, copyBuffer.length);
                readFully(j4, copyBuffer, 0, iMin, windowCursor);
                crc33 = crc32;
                if (z) {
                    crc33.update(copyBuffer, 0, iMin);
                }
                packOutputStream.write(copyBuffer, 0, iMin);
                long j111 = iMin;
                j4 += j111;
                crc32 = crc33;
                r10 -= j111;
            }
            if (!z) {
            }
        } catch (IOException e9) {
            e = e9;
            r8 = localObjectToPack3;
        } catch (DataFormatException e10) {
            e = e10;
            r8 = localObjectToPack3;
        }
    }

    boolean invalid() {
        return this.invalid;
    }

    void setInvalid() {
        this.invalid = true;
    }

    int incrementTransientErrorCount() {
        return this.transientErrorCount.incrementAndGet();
    }

    void resetTransientErrorCount() {
        this.transientErrorCount.set(0);
    }

    private void readFully(long j, byte[] bArr, int i, int i2, WindowCursor windowCursor) throws IOException {
        if (windowCursor.copy(this, j, bArr, i, i2) != i2) {
            throw new EOFException();
        }
    }

    private synchronized void beginCopyAsIs(ObjectToPack objectToPack) throws StoredObjectRepresentationNotAvailableException {
        int i = this.activeCopyRawData + 1;
        this.activeCopyRawData = i;
        if (i == 1 && this.activeWindows == 0) {
            try {
                doOpen();
            } catch (IOException e) {
                throw new StoredObjectRepresentationNotAvailableException(objectToPack, e);
            }
        }
    }

    private synchronized void endCopyAsIs() {
        int i = this.activeCopyRawData - 1;
        this.activeCopyRawData = i;
        if (i == 0 && this.activeWindows == 0) {
            doClose();
        }
    }

    synchronized boolean beginWindowCache() throws IOException {
        int i = this.activeWindows + 1;
        this.activeWindows = i;
        if (i != 1) {
            return false;
        }
        if (this.activeCopyRawData == 0) {
            doOpen();
        }
        return true;
    }

    synchronized boolean endWindowCache() {
        boolean z;
        z = true;
        int i = this.activeWindows - 1;
        this.activeWindows = i;
        if (i != 0) {
            z = false;
        }
        if (z && this.activeCopyRawData == 0) {
            doClose();
        }
        return z;
    }

    private void doOpen() throws Exception {
        if (this.invalid) {
            openFail(true, this.invalidatingCause);
            throw new PackInvalidException(this.packFile, this.invalidatingCause);
        }
        try {
            synchronized (this.readLock) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(this.packFile, "r");
                this.fd = randomAccessFile;
                this.length = randomAccessFile.length();
                onOpenPack();
            }
        } catch (EOFException e) {
            e = e;
            openFail(true, e);
            throw e;
        } catch (FileNotFoundException e2) {
            openFail(true ^ this.packFile.exists(), e2);
            throw e2;
        } catch (InterruptedIOException e3) {
            openFail(false, e3);
            throw e3;
        } catch (RuntimeException e4) {
            e = e4;
            openFail(false, e);
            throw e;
        } catch (AccessDeniedException e5) {
            e = e5;
            openFail(true, e);
            throw e;
        } catch (NoSuchFileException e6) {
            e = e6;
            openFail(true, e);
            throw e;
        } catch (CorruptObjectException e7) {
            e = e7;
            openFail(true, e);
            throw e;
        } catch (NoPackSignatureException e8) {
            e = e8;
            openFail(true, e);
            throw e;
        } catch (PackMismatchException e9) {
            e = e9;
            openFail(true, e);
            throw e;
        } catch (UnpackException e10) {
            e = e10;
            openFail(true, e);
            throw e;
        } catch (UnsupportedPackIndexVersionException e11) {
            e = e11;
            openFail(true, e);
            throw e;
        } catch (UnsupportedPackVersionException e12) {
            e = e12;
            openFail(true, e);
            throw e;
        } catch (IOException e13) {
            e = e13;
            openFail(false, e);
            throw e;
        }
    }

    private void openFail(boolean z, Exception exc) {
        this.activeWindows = 0;
        this.activeCopyRawData = 0;
        this.invalid = z;
        this.invalidatingCause = exc;
        doClose();
    }

    private void doClose() {
        synchronized (this.readLock) {
            RandomAccessFile randomAccessFile = this.fd;
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.close();
                } catch (IOException unused) {
                }
                this.fd = null;
            }
        }
    }

    ByteArrayWindow read(long j, int i) throws IOException {
        RandomAccessFile randomAccessFile;
        ByteArrayWindow byteArrayWindow;
        synchronized (this.readLock) {
            if (this.invalid || (randomAccessFile = this.fd) == null) {
                throw new PackInvalidException(this.packFile, this.invalidatingCause);
            }
            long j2 = this.length;
            if (j2 < ((long) i) + j) {
                i = (int) (j2 - j);
            }
            byte[] bArr = new byte[i];
            randomAccessFile.seek(j);
            this.fd.readFully(bArr, 0, i);
            byteArrayWindow = new ByteArrayWindow(this, j, bArr);
        }
        return byteArrayWindow;
    }

    ByteWindow mmap(long j, int i) throws IOException {
        MappedByteBuffer map;
        synchronized (this.readLock) {
            long j2 = this.length;
            if (j2 < ((long) i) + j) {
                i = (int) (j2 - j);
            }
            try {
                map = this.fd.getChannel().map(FileChannel.MapMode.READ_ONLY, j, i);
            } catch (IOException unused) {
                System.gc();
                System.runFinalization();
                map = this.fd.getChannel().map(FileChannel.MapMode.READ_ONLY, j, i);
            }
            if (map.hasArray()) {
                return new ByteArrayWindow(this, j, map.array());
            }
            return new ByteBufferWindow(this, j, map);
        }
    }

    private void onOpenPack() throws IOException {
        PackIndex packIndexIdx = idx();
        byte[] bArr = new byte[20];
        this.fd.seek(0L);
        this.fd.readFully(bArr, 0, 12);
        if (RawParseUtils.match(bArr, 0, Constants.PACK_SIGNATURE) != 4) {
            throw new NoPackSignatureException(JGitText.get().notAPACKFile);
        }
        long jDecodeUInt32 = NB.decodeUInt32(bArr, 4);
        long jDecodeUInt33 = NB.decodeUInt32(bArr, 8);
        if (jDecodeUInt32 != 2 && jDecodeUInt32 != 3) {
            throw new UnsupportedPackVersionException(jDecodeUInt32);
        }
        if (jDecodeUInt33 != packIndexIdx.getObjectCount()) {
            throw new PackMismatchException(MessageFormat.format(JGitText.get().packObjectCountMismatch, Long.valueOf(jDecodeUInt33), Long.valueOf(packIndexIdx.getObjectCount()), getPackFile()));
        }
        this.fd.seek(this.length - 20);
        this.fd.readFully(bArr, 0, 20);
        if (!Arrays.equals(bArr, this.packChecksum)) {
            throw new PackMismatchException(MessageFormat.format(JGitText.get().packChecksumMismatch, getPackFile(), ObjectId.fromRaw(bArr).name(), ObjectId.fromRaw(packIndexIdx.packChecksum).name()));
        }
    }

    ObjectLoader load(WindowCursor windowCursor, long j) throws LargeObjectException, IOException {
        long j2;
        int i;
        int i2;
        long jFindDeltaBase;
        Delta delta;
        int i3;
        try {
            byte[] bArr = windowCursor.tempId;
            byte[] bArrDecompress = null;
            j2 = j;
            Delta delta2 = null;
            while (true) {
                try {
                    readFully(j2, bArr, 0, 20, windowCursor);
                    byte b = bArr[0];
                    int i4 = b & 255;
                    int i5 = 7;
                    int i6 = (i4 >> 4) & 7;
                    int i7 = 1;
                    long j3 = b & 15;
                    int i8 = 1;
                    int i9 = 4;
                    while ((i4 & 128) != 0) {
                        long j4 = j3;
                        int i10 = i7;
                        int i11 = i8 + 1;
                        byte b2 = bArr[i8];
                        int i12 = b2 & 255;
                        j3 = (((long) (b2 & 127)) << i9) + j4;
                        i5 = 7;
                        i9 += 7;
                        i7 = i10;
                        i8 = i11;
                        i4 = i12;
                    }
                    if (i6 == i7 || i6 == 2 || i6 == 3 || i6 == 4) {
                        long j5 = j3;
                        bArrDecompress = (delta2 != null || j5 < ((long) windowCursor.getStreamFileThreshold())) ? decompress(j2 + ((long) i8), (int) j5, windowCursor) : null;
                        if (delta2 == null) {
                            if (bArrDecompress != null) {
                                return new ObjectLoader.SmallObject(i6, bArrDecompress);
                            }
                            return new LargePackedWholeObject(i6, j5, j2, i8, this, windowCursor.db);
                        }
                        i = i6;
                        i2 = 0;
                    } else if (i6 == 6) {
                        long j6 = j3;
                        byte b3 = bArr[i8];
                        int i13 = b3 & 255;
                        long j7 = b3 & 127;
                        int i14 = i8 + 1;
                        while ((i13 & 128) != 0) {
                            byte b4 = bArr[i14];
                            i13 = b4 & 255;
                            j7 = ((j7 + 1) << i5) + ((long) (b4 & 127));
                            i14++;
                            i5 = 7;
                        }
                        jFindDeltaBase = j2 - j7;
                        delta = new Delta(delta2, j2, (int) j6, i14, jFindDeltaBase);
                        if (j6 != delta.deltaSize) {
                            delta2 = delta;
                            i2 = 0;
                            i = -1;
                        } else {
                            DeltaBaseCache.Entry entry = windowCursor.getDeltaBaseCache().get(this, jFindDeltaBase);
                            if (entry != null) {
                                i3 = entry.type;
                                bArrDecompress = entry.data;
                                i = i3;
                                delta2 = delta;
                                i2 = i7;
                            } else {
                                j2 = jFindDeltaBase;
                                delta2 = delta;
                            }
                        }
                    } else if (i6 == i5) {
                        readFully(j2 + ((long) i8), bArr, 0, 20, windowCursor);
                        jFindDeltaBase = findDeltaBase(ObjectId.fromRaw(bArr));
                        long j8 = j3;
                        delta = new Delta(delta2, j2, (int) j3, i8 + 20, jFindDeltaBase);
                        if (j8 != delta.deltaSize) {
                            delta2 = delta;
                            i2 = 0;
                            i = -1;
                        } else {
                            DeltaBaseCache.Entry entry2 = windowCursor.getDeltaBaseCache().get(this, jFindDeltaBase);
                            if (entry2 != null) {
                                i3 = entry2.type;
                                bArrDecompress = entry2.data;
                                i = i3;
                                delta2 = delta;
                                i2 = i7;
                            } else {
                                j2 = jFindDeltaBase;
                                delta2 = delta;
                            }
                        }
                    } else {
                        throw new IOException(MessageFormat.format(JGitText.get().unknownObjectType, Integer.valueOf(i6)));
                    }
                    if (bArrDecompress == null) {
                        throw new IOException(JGitText.get().inMemoryBufferLimitExceeded);
                    }
                    while (true) {
                        if (i2 != 0) {
                            i2 = 0;
                        } else if (delta2.next == null) {
                            windowCursor.getDeltaBaseCache().store(this, delta2.basePos, bArrDecompress, i);
                        }
                        long j9 = delta2.deltaPos;
                        try {
                            byte[] bArrDecompress2 = decompress(((long) delta2.hdrLen) + j9, delta2.deltaSize, windowCursor);
                            if (bArrDecompress2 == null) {
                                throw new LargeObjectException.OutOfMemory(new OutOfMemoryError());
                            }
                            long resultSize = BinaryDelta.getResultSize(bArrDecompress2);
                            if (2147483647L <= resultSize) {
                                throw new LargeObjectException.ExceedsByteArrayLimit();
                            }
                            try {
                                byte[] bArr2 = new byte[(int) resultSize];
                                BinaryDelta.apply(bArrDecompress, bArrDecompress2, bArr2);
                                delta2 = delta2.next;
                                if (delta2 == null) {
                                    return new ObjectLoader.SmallObject(i, bArr2);
                                }
                                bArrDecompress = bArr2;
                            } catch (OutOfMemoryError e) {
                                throw new LargeObjectException.OutOfMemory(e);
                            }
                        } catch (DataFormatException e2) {
                            e = e2;
                            j2 = j9;
                            throw new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(j2), getPackFile()), e);
                        }
                    }
                } catch (DataFormatException e3) {
                    e = e3;
                }
            }
        } catch (DataFormatException e4) {
            e = e4;
            j2 = j;
        }
    }

    private long findDeltaBase(ObjectId objectId) throws IOException {
        long jFindOffset = idx().findOffset(objectId);
        if (jFindOffset >= 0) {
            return jFindOffset;
        }
        throw new MissingObjectException(objectId, JGitText.get().missingDeltaBase);
    }

    private static class Delta {
        final long basePos;
        final long deltaPos;
        final int deltaSize;
        final int hdrLen;
        final Delta next;

        Delta(Delta delta, long j, int i, int i2, long j2) {
            this.next = delta;
            this.deltaPos = j;
            this.deltaSize = i;
            this.hdrLen = i2;
            this.basePos = j2;
        }
    }

    byte[] getDeltaHeader(WindowCursor windowCursor, long j) throws DataFormatException, IOException {
        byte[] bArr = new byte[18];
        windowCursor.inflate(this, j, bArr, true);
        return bArr;
    }

    int getObjectType(WindowCursor windowCursor, long j) throws IOException {
        int i;
        byte[] bArr = windowCursor.tempId;
        while (true) {
            readFully(j, bArr, 0, 20, windowCursor);
            int i2 = bArr[0] & 255;
            i = (i2 >> 4) & 7;
            int i3 = 1;
            if (i == 1 || i == 2 || i == 3 || i == 4) {
                break;
            }
            if (i == 6) {
                while ((i2 & 128) != 0) {
                    int i4 = bArr[i3] & 255;
                    i3++;
                    i2 = i4;
                }
                int i5 = i3 + 1;
                byte b = bArr[i3];
                int i6 = b & 255;
                long j2 = b & 127;
                while ((i6 & 128) != 0) {
                    int i7 = i5 + 1;
                    byte b2 = bArr[i5];
                    i6 = b2 & 255;
                    j2 = ((j2 + 1) << 7) + ((long) (b2 & 127));
                    i5 = i7;
                }
                j -= j2;
            } else if (i == 7) {
                while ((i2 & 128) != 0) {
                    int i8 = bArr[i3] & 255;
                    i3++;
                    i2 = i8;
                }
                readFully(j + ((long) i3), bArr, 0, 20, windowCursor);
                j = findDeltaBase(ObjectId.fromRaw(bArr));
            } else {
                throw new IOException(MessageFormat.format(JGitText.get().unknownObjectType, Integer.valueOf(i)));
            }
        }
        return i;
    }

    long getObjectSize(WindowCursor windowCursor, AnyObjectId anyObjectId) throws IOException {
        long jFindOffset = idx().findOffset(anyObjectId);
        if (0 < jFindOffset) {
            return getObjectSize(windowCursor, jFindOffset);
        }
        return -1L;
    }

    long getObjectSize(WindowCursor windowCursor, long j) throws IOException {
        long j2;
        byte[] bArr = windowCursor.tempId;
        readFully(j, bArr, 0, 20, windowCursor);
        byte b = bArr[0];
        int i = b & 255;
        int i2 = (i >> 4) & 7;
        long j3 = b & 15;
        int i3 = 4;
        int i4 = 1;
        while ((i & 128) != 0) {
            int i5 = i4 + 1;
            byte b2 = bArr[i4];
            int i6 = b2 & 255;
            j3 += ((long) (b2 & 127)) << i3;
            i3 += 7;
            i4 = i5;
            i = i6;
        }
        if (i2 == 1 || i2 == 2 || i2 == 3 || i2 == 4) {
            return j3;
        }
        if (i2 == 6) {
            int i7 = i4 + 1;
            int i8 = bArr[i4] & 255;
            while ((i8 & 128) != 0) {
                i8 = bArr[i7] & 255;
                i7++;
            }
            j2 = j + ((long) i7);
        } else {
            if (i2 != 7) {
                throw new IOException(MessageFormat.format(JGitText.get().unknownObjectType, Integer.valueOf(i2)));
            }
            j2 = j + ((long) i4) + 20;
        }
        try {
            return BinaryDelta.getResultSize(getDeltaHeader(windowCursor, j2));
        } catch (DataFormatException e) {
            throw new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(j), getPackFile()), e);
        }
    }

    LocalObjectRepresentation representation(WindowCursor windowCursor, AnyObjectId anyObjectId) throws IOException {
        long jFindOffset = idx().findOffset(anyObjectId);
        if (jFindOffset < 0) {
            return null;
        }
        byte[] bArr = windowCursor.tempId;
        readFully(jFindOffset, bArr, 0, 20, windowCursor);
        int i = bArr[0] & 255;
        int i2 = (i >> 4) & 7;
        int i3 = 1;
        while ((i & 128) != 0) {
            int i4 = i3 + 1;
            int i5 = bArr[i3] & 255;
            i3 = i4;
            i = i5;
        }
        long jFindEndOffset = findEndOffset(jFindOffset) - jFindOffset;
        if (i2 == 1 || i2 == 2 || i2 == 3 || i2 == 4) {
            return LocalObjectRepresentation.newWhole(this, jFindOffset, jFindEndOffset - ((long) i3));
        }
        if (i2 != 6) {
            if (i2 == 7) {
                long j = i3;
                readFully(jFindOffset + j, bArr, 0, 20, windowCursor);
                return LocalObjectRepresentation.newDelta(this, jFindOffset, (jFindEndOffset - j) - 20, ObjectId.fromRaw(bArr));
            }
            throw new IOException(MessageFormat.format(JGitText.get().unknownObjectType, Integer.valueOf(i2)));
        }
        int i6 = i3 + 1;
        byte b = bArr[i3];
        int i7 = b & 255;
        long j2 = b & 127;
        while ((i7 & 128) != 0) {
            int i8 = i6 + 1;
            byte b2 = bArr[i6];
            i7 = b2 & 255;
            j2 = ((j2 + 1) << 7) + ((long) (b2 & 127));
            i6 = i8;
        }
        return LocalObjectRepresentation.newDelta(this, jFindOffset, jFindEndOffset - ((long) i6), jFindOffset - j2);
    }

    private long findEndOffset(long j) throws IOException {
        return getReverseIdx().findNextOffset(j, this.length - 20);
    }

    synchronized PackBitmapIndex getBitmapIndex() throws IOException {
        PackFile packFile;
        if (!this.invalid && (packFile = this.bitmapIdxFile) != null) {
            if (this.bitmapIdx == null) {
                try {
                    PackBitmapIndex packBitmapIndexOpen = PackBitmapIndex.open(packFile, idx(), getReverseIdx());
                    if (Arrays.equals(this.packChecksum, packBitmapIndexOpen.packChecksum)) {
                        this.bitmapIdx = packBitmapIndexOpen;
                    } else {
                        this.bitmapIdxFile = null;
                    }
                } catch (FileNotFoundException unused) {
                    this.bitmapIdxFile = null;
                    return null;
                }
            }
            return this.bitmapIdx;
        }
        return null;
    }

    private synchronized PackReverseIndex getReverseIdx() throws IOException {
        if (this.reverseIdx == null) {
            this.reverseIdx = new PackReverseIndex(idx());
        }
        return this.reverseIdx;
    }

    private boolean isCorrupt(long j) {
        boolean zContains;
        LongList longList = this.corruptObjects;
        if (longList == null) {
            return false;
        }
        synchronized (longList) {
            zContains = longList.contains(j);
        }
        return zContains;
    }

    private void setCorrupt(long j) {
        LongList longList = this.corruptObjects;
        if (longList == null) {
            synchronized (this.readLock) {
                longList = this.corruptObjects;
                if (longList == null) {
                    longList = new LongList();
                    this.corruptObjects = longList;
                }
            }
        }
        synchronized (longList) {
            longList.add(j);
        }
    }

    public String toString() {
        return "Pack [packFileName=" + this.packFile.getName() + ", length=" + this.packFile.length() + ", packChecksum=" + ObjectId.fromRaw(this.packChecksum).name() + "]";
    }
}
