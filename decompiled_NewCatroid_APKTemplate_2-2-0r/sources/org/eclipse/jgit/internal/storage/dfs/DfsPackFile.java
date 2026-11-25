package org.eclipse.jgit.internal.storage.dfs;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.PackInvalidException;
import org.eclipse.jgit.errors.StoredObjectRepresentationNotAvailableException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.PackBitmapIndex;
import org.eclipse.jgit.internal.storage.file.PackIndex;
import org.eclipse.jgit.internal.storage.file.PackReverseIndex;
import org.eclipse.jgit.internal.storage.pack.BinaryDelta;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.internal.storage.pack.PackOutputStream;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.LongList;

/* JADX INFO: loaded from: classes13.dex */
public final class DfsPackFile extends BlockBasedFile {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int REC_SIZE = 28;
    private static final long REF_POSITION = 0;
    private volatile PackBitmapIndex bitmapIndex;
    private volatile LongList corruptObjects;
    private volatile PackIndex index;
    private final Object initLock;
    private volatile PackReverseIndex reverseIndex;

    static /* synthetic */ ReadableChannel lambda$3(ReadableChannel readableChannel) throws IOException {
        return readableChannel;
    }

    DfsPackFile(DfsBlockCache dfsBlockCache, DfsPackDescription dfsPackDescription) {
        super(dfsBlockCache, dfsPackDescription, PackExt.PACK);
        this.initLock = new Object();
        int blockSize = dfsPackDescription.getBlockSize(PackExt.PACK);
        if (blockSize > 0) {
            setBlockSize(blockSize);
        }
        long fileSize = dfsPackDescription.getFileSize(PackExt.PACK);
        this.length = fileSize <= 0 ? -1L : fileSize;
    }

    public DfsPackDescription getPackDescription() {
        return this.desc;
    }

    public boolean isIndexLoaded() {
        return this.index != null;
    }

    void setPackIndex(PackIndex packIndex) {
        this.cache.putRef(this.desc.getStreamKey(PackExt.INDEX), packIndex.getObjectCount() * ((long) 28), packIndex);
        this.index = packIndex;
    }

    public PackIndex getPackIndex(DfsReader dfsReader) throws IOException {
        return idx(dfsReader);
    }

    private PackIndex idx(final DfsReader dfsReader) throws IOException {
        if (this.index != null) {
            return this.index;
        }
        if (this.invalid) {
            throw new PackInvalidException(getFileName(), this.invalidatingCause);
        }
        Repository.getGlobalListenerList().dispatch(new BeforeDfsPackIndexLoadedEvent(this));
        synchronized (this.initLock) {
            if (this.index != null) {
                return this.index;
            }
            try {
                final DfsStreamKey streamKey = this.desc.getStreamKey(PackExt.INDEX);
                final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
                DfsBlockCache.Ref orLoadRef = this.cache.getOrLoadRef(streamKey, 0L, new DfsBlockCache.RefLoader() { // from class: org.eclipse.jgit.internal.storage.dfs.DfsPackFile$$ExternalSyntheticLambda1
                    @Override // org.eclipse.jgit.internal.storage.dfs.DfsBlockCache.RefLoader
                    public final DfsBlockCache.Ref load() {
                        return this.f$0.m6027lambda$0$orgeclipsejgitinternalstoragedfsDfsPackFile(atomicBoolean, dfsReader, streamKey);
                    }
                });
                if (atomicBoolean.get()) {
                    dfsReader.stats.idxCacheHit++;
                }
                PackIndex packIndex = (PackIndex) orLoadRef.get();
                if (this.index == null && packIndex != null) {
                    this.index = packIndex;
                }
                return this.index;
            } catch (IOException e) {
                this.invalid = true;
                this.invalidatingCause = e;
                throw e;
            }
        }
    }

    /* JADX INFO: renamed from: lambda$0$org-eclipse-jgit-internal-storage-dfs-DfsPackFile, reason: not valid java name */
    /* synthetic */ DfsBlockCache.Ref m6027lambda$0$orgeclipsejgitinternalstoragedfsDfsPackFile(AtomicBoolean atomicBoolean, DfsReader dfsReader, DfsStreamKey dfsStreamKey) throws IOException {
        atomicBoolean.set(false);
        return loadPackIndex(dfsReader, dfsStreamKey);
    }

    final boolean isGarbage() {
        return this.desc.getPackSource() == DfsObjDatabase.PackSource.UNREACHABLE_GARBAGE;
    }

    PackBitmapIndex getBitmapIndex(final DfsReader dfsReader) throws IOException {
        if (this.invalid || isGarbage() || !this.desc.hasFileExt(PackExt.BITMAP_INDEX)) {
            return null;
        }
        if (this.bitmapIndex != null) {
            return this.bitmapIndex;
        }
        synchronized (this.initLock) {
            if (this.bitmapIndex != null) {
                return this.bitmapIndex;
            }
            final PackIndex packIndexIdx = idx(dfsReader);
            final PackReverseIndex reverseIdx = getReverseIdx(dfsReader);
            final DfsStreamKey streamKey = this.desc.getStreamKey(PackExt.BITMAP_INDEX);
            final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            DfsBlockCache.Ref orLoadRef = this.cache.getOrLoadRef(streamKey, 0L, new DfsBlockCache.RefLoader() { // from class: org.eclipse.jgit.internal.storage.dfs.DfsPackFile$$ExternalSyntheticLambda3
                @Override // org.eclipse.jgit.internal.storage.dfs.DfsBlockCache.RefLoader
                public final DfsBlockCache.Ref load() {
                    return this.f$0.m6028lambda$1$orgeclipsejgitinternalstoragedfsDfsPackFile(atomicBoolean, dfsReader, streamKey, packIndexIdx, reverseIdx);
                }
            });
            if (atomicBoolean.get()) {
                dfsReader.stats.bitmapCacheHit++;
            }
            PackBitmapIndex packBitmapIndex = (PackBitmapIndex) orLoadRef.get();
            if (this.bitmapIndex == null && packBitmapIndex != null) {
                this.bitmapIndex = packBitmapIndex;
            }
            return this.bitmapIndex;
        }
    }

    /* JADX INFO: renamed from: lambda$1$org-eclipse-jgit-internal-storage-dfs-DfsPackFile, reason: not valid java name */
    /* synthetic */ DfsBlockCache.Ref m6028lambda$1$orgeclipsejgitinternalstoragedfsDfsPackFile(AtomicBoolean atomicBoolean, DfsReader dfsReader, DfsStreamKey dfsStreamKey, PackIndex packIndex, PackReverseIndex packReverseIndex) throws IOException {
        atomicBoolean.set(false);
        return loadBitmapIndex(dfsReader, dfsStreamKey, packIndex, packReverseIndex);
    }

    PackReverseIndex getReverseIdx(final DfsReader dfsReader) throws IOException {
        if (this.reverseIndex != null) {
            return this.reverseIndex;
        }
        synchronized (this.initLock) {
            if (this.reverseIndex != null) {
                return this.reverseIndex;
            }
            final PackIndex packIndexIdx = idx(dfsReader);
            final DfsStreamKey.ForReverseIndex forReverseIndex = new DfsStreamKey.ForReverseIndex(this.desc.getStreamKey(PackExt.INDEX));
            final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            DfsBlockCache.Ref orLoadRef = this.cache.getOrLoadRef(forReverseIndex, 0L, new DfsBlockCache.RefLoader() { // from class: org.eclipse.jgit.internal.storage.dfs.DfsPackFile$$ExternalSyntheticLambda0
                @Override // org.eclipse.jgit.internal.storage.dfs.DfsBlockCache.RefLoader
                public final DfsBlockCache.Ref load() {
                    return this.f$0.m6029lambda$2$orgeclipsejgitinternalstoragedfsDfsPackFile(atomicBoolean, dfsReader, forReverseIndex, packIndexIdx);
                }
            });
            if (atomicBoolean.get()) {
                dfsReader.stats.ridxCacheHit++;
            }
            PackReverseIndex packReverseIndex = (PackReverseIndex) orLoadRef.get();
            if (this.reverseIndex == null && packReverseIndex != null) {
                this.reverseIndex = packReverseIndex;
            }
            return this.reverseIndex;
        }
    }

    /* JADX INFO: renamed from: lambda$2$org-eclipse-jgit-internal-storage-dfs-DfsPackFile, reason: not valid java name */
    /* synthetic */ DfsBlockCache.Ref m6029lambda$2$orgeclipsejgitinternalstoragedfsDfsPackFile(AtomicBoolean atomicBoolean, DfsReader dfsReader, DfsStreamKey dfsStreamKey, PackIndex packIndex) throws IOException {
        atomicBoolean.set(false);
        return loadReverseIdx(dfsReader, dfsStreamKey, packIndex);
    }

    public boolean hasObject(DfsReader dfsReader, AnyObjectId anyObjectId) throws IOException {
        long jFindOffset = idx(dfsReader).findOffset(anyObjectId);
        return 0 < jFindOffset && !isCorrupt(jFindOffset);
    }

    ObjectLoader get(DfsReader dfsReader, AnyObjectId anyObjectId) throws IOException {
        long jFindOffset = idx(dfsReader).findOffset(anyObjectId);
        if (0 >= jFindOffset || isCorrupt(jFindOffset)) {
            return null;
        }
        return load(dfsReader, jFindOffset);
    }

    long findOffset(DfsReader dfsReader, AnyObjectId anyObjectId) throws IOException {
        return idx(dfsReader).findOffset(anyObjectId);
    }

    void resolve(DfsReader dfsReader, Set<ObjectId> set, AbbreviatedObjectId abbreviatedObjectId, int i) throws IOException {
        idx(dfsReader).resolve(set, abbreviatedObjectId, i);
    }

    long getObjectCount(DfsReader dfsReader) throws IOException {
        return idx(dfsReader).getObjectCount();
    }

    private byte[] decompress(long j, int i, DfsReader dfsReader) throws DataFormatException, IOException {
        try {
            byte[] bArr = new byte[i];
            if (dfsReader.inflate(this, j, bArr, false) == i) {
                return bArr;
            }
            throw new EOFException(MessageFormat.format(JGitText.get().shortCompressedStreamAt, Long.valueOf(j)));
        } catch (OutOfMemoryError unused) {
            return null;
        }
    }

    void copyPackAsIs(PackOutputStream packOutputStream, DfsReader dfsReader) throws IOException {
        if (this.length == -1) {
            dfsReader.pin(this, 0L);
            dfsReader.unpin();
        }
        Throwable th = null;
        try {
            ReadableChannel readableChannelOpenFile = dfsReader.db.openFile(this.desc, PackExt.PACK);
            try {
                int streamPackBufferSize = dfsReader.getOptions().getStreamPackBufferSize();
                if (streamPackBufferSize > 0) {
                    readableChannelOpenFile.setReadAheadBytes(streamPackBufferSize);
                }
                if (this.cache.shouldCopyThroughCache(this.length)) {
                    copyPackThroughCache(packOutputStream, dfsReader, readableChannelOpenFile);
                } else {
                    copyPackBypassCache(packOutputStream, readableChannelOpenFile);
                }
            } finally {
                if (readableChannelOpenFile != null) {
                    readableChannelOpenFile.close();
                }
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

    private void copyPackThroughCache(PackOutputStream packOutputStream, DfsReader dfsReader, final ReadableChannel readableChannel) throws IOException {
        long j = this.length - 32;
        long j2 = 12;
        while (0 < j) {
            DfsBlock orLoad = this.cache.getOrLoad(this, j2, dfsReader, new DfsBlockCache.ReadableChannelSupplier() { // from class: org.eclipse.jgit.internal.storage.dfs.DfsPackFile$$ExternalSyntheticLambda2
                @Override // org.eclipse.jgit.internal.storage.dfs.DfsBlockCache.ReadableChannelSupplier
                public final ReadableChannel get() {
                    return DfsPackFile.lambda$3(readableChannel);
                }
            });
            int i = (int) (j2 - orLoad.start);
            if (orLoad.size() <= i) {
                throw packfileIsTruncated();
            }
            int iMin = (int) Math.min(orLoad.size() - i, j);
            orLoad.write(packOutputStream, j2, iMin);
            long j3 = iMin;
            j2 += j3;
            j -= j3;
        }
    }

    private long copyPackBypassCache(PackOutputStream packOutputStream, ReadableChannel readableChannel) throws IOException {
        ByteBuffer byteBufferNewCopyBuffer = newCopyBuffer(packOutputStream, readableChannel);
        long j = this.length - 32;
        long j2 = 12;
        boolean z = false;
        while (0 < j) {
            DfsBlock dfsBlock = (DfsBlock) this.cache.get(this.key, alignToBlock(j2));
            if (dfsBlock != null) {
                int i = (int) (j2 - dfsBlock.start);
                if (dfsBlock.size() <= i) {
                    throw packfileIsTruncated();
                }
                int iMin = (int) Math.min(dfsBlock.size() - i, j);
                dfsBlock.write(packOutputStream, j2, iMin);
                long j3 = iMin;
                j2 += j3;
                j -= j3;
                readableChannel.position(j2);
            } else {
                int i2 = z ? 0 : 12;
                byteBufferNewCopyBuffer.position(0);
                int i3 = read(readableChannel, byteBufferNewCopyBuffer);
                if (i3 <= i2) {
                    throw packfileIsTruncated();
                }
                int iMin2 = (int) Math.min(i3 - i2, j);
                packOutputStream.write(byteBufferNewCopyBuffer.array(), i2, iMin2);
                long j4 = iMin2;
                j2 += j4;
                j -= j4;
            }
            z = true;
        }
        return j2;
    }

    private ByteBuffer newCopyBuffer(PackOutputStream packOutputStream, ReadableChannel readableChannel) {
        int iBlockSize = blockSize(readableChannel);
        byte[] copyBuffer = packOutputStream.getCopyBuffer();
        if (iBlockSize > copyBuffer.length) {
            copyBuffer = new byte[iBlockSize];
        }
        return ByteBuffer.wrap(copyBuffer, 0, iBlockSize);
    }

    /* JADX WARN: Code duplicated, block: B:102:0x0230 A[LOOP:2: B:99:0x022b->B:102:0x0230, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:104:0x025a  */
    /* JADX WARN: Code duplicated, block: B:114:0x028c  */
    /* JADX WARN: Code duplicated, block: B:116:0x02a3  */
    /* JADX WARN: Code duplicated, block: B:146:0x02a6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:94:0x020b  */
    /* JADX WARN: Code duplicated, block: B:95:0x021a  */
    /* JADX WARN: Code duplicated, block: B:97:0x0226 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:98:0x0228  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v2, types: [long] */
    /* JADX WARN: Type inference failed for: r10v3, types: [long] */
    /* JADX WARN: Type inference failed for: r10v5, types: [long] */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r14v8, types: [long] */
    /* JADX WARN: Type inference failed for: r29v0 */
    /* JADX WARN: Type inference failed for: r29v1 */
    /* JADX WARN: Type inference failed for: r29v2 */
    /* JADX WARN: Type inference failed for: r29v3 */
    /* JADX WARN: Type inference failed for: r29v4 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v21 */
    /* JADX WARN: Type inference failed for: r8v22, types: [long] */
    /* JADX WARN: Type inference failed for: r8v23, types: [org.eclipse.jgit.internal.storage.dfs.DfsObjectToPack] */
    /* JADX WARN: Type inference failed for: r8v26 */
    /* JADX WARN: Type inference failed for: r8v27 */
    /* JADX WARN: Type inference failed for: r8v28 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5, types: [org.eclipse.jgit.internal.storage.dfs.DfsObjectToPack, org.eclipse.jgit.internal.storage.pack.ObjectToPack] */
    /* JADX WARN: Type inference failed for: r8v6, types: [org.eclipse.jgit.internal.storage.pack.ObjectToPack] */
    void copyAsIs(PackOutputStream packOutputStream, DfsObjectToPack dfsObjectToPack, boolean z, DfsReader dfsReader) throws StoredObjectRepresentationNotAvailableException, IOException {
        long j;
        ?? r8;
        long jFindCRC32;
        long j2;
        ?? r29;
        DfsBlock dfsBlock;
        DfsObjectToPack dfsObjectToPack2;
        long j3;
        byte[] bArr;
        long j4;
        ?? r10;
        long j5;
        long j6;
        ?? r11;
        int iMin;
        CRC32 crc32;
        long j7;
        ?? r14;
        ?? r9;
        int i;
        DfsObjectToPack dfsObjectToPack3 = dfsObjectToPack;
        CRC32 crc33 = z ? new CRC32() : null;
        CRC32 crc34 = z ? new CRC32() : null;
        byte[] copyBuffer = packOutputStream.getCopyBuffer();
        try {
            readFully(dfsObjectToPack3.offset, copyBuffer, 0, 20, dfsReader);
            int i2 = 0;
            byte b = copyBuffer[0];
            int i3 = b & 255;
            int i4 = (i3 >> 4) & 7;
            long j8 = b & 15;
            int i5 = 4;
            int i6 = 1;
            long j9 = j8;
            while ((i3 & 128) != 0) {
                byte[] bArr2 = copyBuffer;
                byte b2 = bArr2[i6];
                long j10 = ((long) (b2 & 127)) << i5;
                i5 += 7;
                crc34 = crc34;
                copyBuffer = bArr2;
                i6++;
                i3 = b2 & 255;
                i2 = 0;
                j9 = j10 + j9;
                dfsObjectToPack3 = dfsObjectToPack3;
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
                    crc33.update(copyBuffer, i2, i);
                    crc34.update(copyBuffer, i2, i);
                }
                i6 = i;
                j = j9;
            } else if (i4 == 7) {
                if (z) {
                    crc33.update(copyBuffer, i2, i6);
                    crc34.update(copyBuffer, i2, i6);
                }
                j = j9;
                readFully(dfsObjectToPack3.offset + ((long) i6), copyBuffer, 0, 20, dfsReader);
                if (z) {
                    crc33.update(copyBuffer, i2, 20);
                    crc34.update(copyBuffer, i2, 20);
                }
                i6 += 20;
            } else {
                j = j9;
                if (z) {
                    crc33.update(copyBuffer, i2, i6);
                    crc34.update(copyBuffer, i2, i6);
                }
            }
            long j11 = dfsObjectToPack3.offset + ((long) i6);
            long j12 = dfsObjectToPack3.length;
            CRC32 crc35 = crc34;
            byte[] bArr3 = copyBuffer;
            long j13 = j11;
            try {
                DfsBlock dfsBlockQuickCopy = dfsReader.quickCopy(this, j11, j12);
                if (z) {
                    try {
                        if (idx(dfsReader).hasCRC32Support()) {
                            jFindCRC32 = idx(dfsReader).findCRC32(dfsObjectToPack3);
                            if (dfsBlockQuickCopy != null) {
                                j2 = j12;
                                dfsBlockQuickCopy.crc32(crc33, j13, (int) j2);
                            } else {
                                j2 = j12;
                                long j14 = j2;
                                long j15 = j13;
                                while (j14 > 0) {
                                    byte[] bArr4 = bArr3;
                                    long j16 = j15;
                                    int iMin2 = (int) Math.min(j14, bArr4.length);
                                    readFully(j16, bArr4, 0, iMin2, dfsReader);
                                    crc33.update(bArr4, 0, iMin2);
                                    long j17 = iMin2;
                                    bArr3 = bArr4;
                                    j2 = j2;
                                    dfsBlockQuickCopy = dfsBlockQuickCopy;
                                    this = this;
                                    dfsObjectToPack3 = dfsObjectToPack;
                                    j15 = j16 + j17;
                                    j14 -= j17;
                                }
                            }
                            if (crc33.getValue() != jFindCRC32) {
                                this.setCorrupt(dfsObjectToPack3.offset);
                                throw new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(dfsObjectToPack3.offset), getFileName()));
                            }
                            r29 = j2;
                            dfsBlock = dfsBlockQuickCopy;
                            dfsObjectToPack2 = dfsObjectToPack3;
                            j3 = j13;
                            bArr = bArr3;
                            throw new StoredObjectRepresentationNotAvailableException(r8, e);
                        }
                        if (dfsBlock != null) {
                            packOutputStream.writeHeader(dfsObjectToPack2, j);
                            dfsBlock.write(packOutputStream, j3, (int) r29);
                            return;
                        }
                        j4 = j;
                        r10 = r29;
                        j5 = j3;
                        if (r10 <= bArr.length) {
                            if (!z) {
                                j7 = j5;
                                r14 = r10;
                                while (r14 > 0) {
                                    int iMin3 = (int) Math.min((long) r14, bArr.length);
                                    readFully(j7, bArr, 0, iMin3, dfsReader);
                                    long j18 = iMin3;
                                    j7 += j18;
                                    bArr = bArr;
                                    r14 -= j18;
                                }
                            }
                            packOutputStream.writeHeader(dfsObjectToPack2, j4);
                            packOutputStream.write(bArr, 0, (int) r10);
                            return;
                        }
                        packOutputStream.writeHeader(dfsObjectToPack2, j4);
                        j6 = j5;
                        r11 = r10;
                        while (r11 > 0) {
                            iMin = (int) Math.min((long) r11, bArr.length);
                            readFully(j6, bArr, 0, iMin, dfsReader);
                            crc32 = crc35;
                            if (z) {
                                crc32.update(bArr, 0, iMin);
                            }
                            packOutputStream.write(bArr, 0, iMin);
                            long j19 = iMin;
                            j6 += j19;
                            crc35 = crc32;
                            r11 -= j19;
                        }
                        if (!z && crc35.getValue() != jFindCRC32) {
                            throw new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(dfsObjectToPack2.offset), getFileName()));
                        }
                    } catch (IOException e) {
                        e = e;
                        r8 = dfsObjectToPack;
                        throw new StoredObjectRepresentationNotAvailableException(r8, e);
                    } catch (DataFormatException e2) {
                        e = e2;
                        r8 = dfsObjectToPack;
                        setCorrupt(r8.offset);
                        throw new StoredObjectRepresentationNotAvailableException(r8, new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(r8.offset), getFileName()), e));
                    }
                }
                long j20 = j12;
                dfsBlock = dfsBlockQuickCopy;
                byte[] bArr5 = bArr3;
                if (z) {
                    Inflater inflater = dfsReader.inflater();
                    byte[] bArr6 = new byte[1024];
                    try {
                        try {
                            if (dfsBlock != null) {
                                dfsBlock.check(inflater, bArr6, j13, (int) j20);
                                r9 = j20;
                            } else {
                                long j21 = j20;
                                long j22 = j13;
                                r8 = j20;
                                while (true) {
                                    r9 = r8;
                                    if (j21 <= 0) {
                                        break;
                                    }
                                    try {
                                        ?? r210 = r8;
                                        byte[] bArr7 = bArr5;
                                        int iMin4 = (int) Math.min(j21, bArr5.length);
                                        long j23 = j21;
                                        byte[] bArr8 = bArr6;
                                        long j24 = j13;
                                        readFully(j22, bArr7, 0, iMin4, dfsReader);
                                        crc33.update(bArr7, 0, iMin4);
                                        inflater.setInput(bArr7, 0, iMin4);
                                        for (int i7 = 0; inflater.inflate(bArr8, i7, 1024) > 0; i7 = 0) {
                                        }
                                        long j25 = iMin4;
                                        j22 += j25;
                                        j21 = j23 - j25;
                                        bArr5 = bArr7;
                                        bArr6 = bArr8;
                                        r8 = r210;
                                        j13 = j24;
                                    } catch (IOException e3) {
                                        e = e3;
                                    } catch (DataFormatException e4) {
                                        e = e4;
                                        setCorrupt(r8.offset);
                                        throw new StoredObjectRepresentationNotAvailableException(r8, new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(r8.offset), getFileName()), e));
                                    }
                                }
                                if (inflater.finished() || inflater.getBytesRead() != r9) {
                                    r8 = dfsObjectToPack;
                                    setCorrupt(r8.offset);
                                    throw new EOFException(MessageFormat.format(JGitText.get().shortCompressedStreamAt, Long.valueOf(r8.offset)));
                                }
                                jFindCRC32 = crc33.getValue();
                                r29 = r9;
                                j3 = j13;
                                dfsObjectToPack2 = dfsObjectToPack;
                                bArr = bArr5;
                            }
                            setCorrupt(r8.offset);
                            throw new EOFException(MessageFormat.format(JGitText.get().shortCompressedStreamAt, Long.valueOf(r8.offset)));
                        } catch (IOException e5) {
                            e = e5;
                        } catch (DataFormatException e6) {
                            e = e6;
                            setCorrupt(r8.offset);
                            throw new StoredObjectRepresentationNotAvailableException(r8, new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(r8.offset), getFileName()), e));
                        }
                        if (inflater.finished()) {
                        }
                        r8 = dfsObjectToPack;
                    } catch (IOException e7) {
                        e = e7;
                        r8 = dfsObjectToPack;
                    } catch (DataFormatException e8) {
                        e = e8;
                        r8 = dfsObjectToPack;
                        setCorrupt(r8.offset);
                        throw new StoredObjectRepresentationNotAvailableException(r8, new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(r8.offset), getFileName()), e));
                    }
                } else {
                    r29 = j20;
                    j3 = j13;
                    dfsObjectToPack2 = dfsObjectToPack;
                    bArr = bArr5;
                    jFindCRC32 = -1;
                }
                if (dfsBlock != null) {
                    packOutputStream.writeHeader(dfsObjectToPack2, j);
                    dfsBlock.write(packOutputStream, j3, (int) r29);
                    return;
                }
                j4 = j;
                r10 = r29;
                j5 = j3;
                if (r10 <= bArr.length) {
                    if (!z) {
                        j7 = j5;
                        r14 = r10;
                        while (r14 > 0) {
                            int iMin5 = (int) Math.min((long) r14, bArr.length);
                            readFully(j7, bArr, 0, iMin5, dfsReader);
                            long j110 = iMin5;
                            j7 += j110;
                            bArr = bArr;
                            r14 -= j110;
                        }
                    }
                    packOutputStream.writeHeader(dfsObjectToPack2, j4);
                    packOutputStream.write(bArr, 0, (int) r10);
                    return;
                }
                packOutputStream.writeHeader(dfsObjectToPack2, j4);
                j6 = j5;
                r11 = r10;
                while (r11 > 0) {
                    iMin = (int) Math.min((long) r11, bArr.length);
                    readFully(j6, bArr, 0, iMin, dfsReader);
                    crc32 = crc35;
                    if (z) {
                        crc32.update(bArr, 0, iMin);
                    }
                    packOutputStream.write(bArr, 0, iMin);
                    long j111 = iMin;
                    j6 += j111;
                    crc35 = crc32;
                    r11 -= j111;
                }
                if (!z) {
                }
            } catch (IOException e9) {
                e = e9;
                r8 = dfsObjectToPack3;
            } catch (DataFormatException e10) {
                e = e10;
                r8 = dfsObjectToPack3;
            }
        } catch (IOException e11) {
            throw new StoredObjectRepresentationNotAvailableException(dfsObjectToPack3, e11);
        }
    }

    private IOException packfileIsTruncated() {
        this.invalid = true;
        IOException iOException = new IOException(MessageFormat.format(JGitText.get().packfileIsTruncated, getFileName()));
        this.invalidatingCause = iOException;
        return iOException;
    }

    private void readFully(long j, byte[] bArr, int i, int i2, DfsReader dfsReader) throws IOException {
        while (i2 > 0) {
            int iCopy = dfsReader.copy(this, j, bArr, i, i2);
            if (iCopy == 0) {
                throw new EOFException();
            }
            j += (long) iCopy;
            i += iCopy;
            i2 -= iCopy;
        }
    }

    ObjectLoader load(DfsReader dfsReader, long j) throws IOException {
        long j2;
        byte[] bArrDecompress;
        int i;
        long jFindDeltaBase;
        Delta delta;
        int i2;
        try {
            byte[] bArr = dfsReader.tempId;
            byte[] bArrDecompress2 = null;
            j2 = j;
            Delta delta2 = null;
            while (true) {
                try {
                    readFully(j2, bArr, 0, 20, dfsReader);
                    byte b = bArr[0];
                    int i3 = b & 255;
                    int i4 = 7;
                    int i5 = (i3 >> 4) & 7;
                    int i6 = 1;
                    long j3 = b & 15;
                    int i7 = 1;
                    int i8 = 4;
                    while ((i3 & 128) != 0) {
                        long j4 = j3;
                        int i9 = i6;
                        int i10 = i7 + 1;
                        byte b2 = bArr[i7];
                        int i11 = b2 & 255;
                        j3 = (((long) (b2 & 127)) << i8) + j4;
                        i4 = 7;
                        i8 += 7;
                        i6 = i9;
                        i7 = i10;
                        i3 = i11;
                    }
                    if (i5 == i6 || i5 == 2 || i5 == 3 || i5 == 4) {
                        long j5 = j3;
                        if (delta2 == null) {
                            if (j5 < dfsReader.getStreamFileThreshold() && (bArrDecompress = decompress(j2 + ((long) i7), (int) j5, dfsReader)) != null) {
                                return new ObjectLoader.SmallObject(i5, bArrDecompress);
                            }
                            return new LargePackedWholeObject(i5, j5, j2, i7, this, dfsReader.db);
                        }
                        bArrDecompress2 = decompress(j2 + ((long) i7), (int) j5, dfsReader);
                        i = 0;
                    } else if (i5 == 6) {
                        long j6 = j3;
                        byte b3 = bArr[i7];
                        int i12 = b3 & 255;
                        long j7 = b3 & 127;
                        int i13 = i7 + 1;
                        while ((i12 & 128) != 0) {
                            byte b4 = bArr[i13];
                            i12 = b4 & 255;
                            j7 = ((j7 + 1) << i4) + ((long) (b4 & 127));
                            i13++;
                            i4 = 7;
                        }
                        jFindDeltaBase = j2 - j7;
                        delta = new Delta(delta2, j2, (int) j6, i13, jFindDeltaBase);
                        if (j6 != delta.deltaSize) {
                            delta2 = delta;
                            i = 0;
                            i5 = -1;
                        } else {
                            DeltaBaseCache.Entry entry = dfsReader.getDeltaBaseCache().get(this.key, jFindDeltaBase);
                            if (entry != null) {
                                i2 = entry.type;
                                bArrDecompress2 = entry.data;
                                delta2 = delta;
                                i = i6;
                                i5 = i2;
                            } else {
                                j2 = jFindDeltaBase;
                                delta2 = delta;
                            }
                        }
                    } else if (i5 == i4) {
                        readFully(j2 + ((long) i7), bArr, 0, 20, dfsReader);
                        jFindDeltaBase = findDeltaBase(dfsReader, ObjectId.fromRaw(bArr));
                        long j8 = j3;
                        delta = new Delta(delta2, j2, (int) j3, i7 + 20, jFindDeltaBase);
                        if (j8 != delta.deltaSize) {
                            delta2 = delta;
                            i = 0;
                            i5 = -1;
                        } else {
                            DeltaBaseCache.Entry entry2 = dfsReader.getDeltaBaseCache().get(this.key, jFindDeltaBase);
                            if (entry2 != null) {
                                i2 = entry2.type;
                                bArrDecompress2 = entry2.data;
                                delta2 = delta;
                                i = i6;
                                i5 = i2;
                            } else {
                                j2 = jFindDeltaBase;
                                delta2 = delta;
                            }
                        }
                    } else {
                        throw new IOException(MessageFormat.format(JGitText.get().unknownObjectType, Integer.valueOf(i5)));
                    }
                    if (bArrDecompress2 == null) {
                        throw new LargeObjectException();
                    }
                    int i14 = i;
                    while (true) {
                        if (i14 != 0) {
                            i14 = 0;
                        } else if (delta2.next == null) {
                            dfsReader.getDeltaBaseCache().put(this.key, delta2.basePos, i5, bArrDecompress2);
                        }
                        long j9 = delta2.deltaPos;
                        try {
                            byte[] bArrDecompress3 = decompress(((long) delta2.hdrLen) + j9, delta2.deltaSize, dfsReader);
                            if (bArrDecompress3 == null) {
                                throw new LargeObjectException();
                            }
                            long resultSize = BinaryDelta.getResultSize(bArrDecompress3);
                            if (2147483647L <= resultSize) {
                                throw new LargeObjectException.ExceedsByteArrayLimit();
                            }
                            try {
                                byte[] bArr2 = new byte[(int) resultSize];
                                BinaryDelta.apply(bArrDecompress2, bArrDecompress3, bArr2);
                                delta2 = delta2.next;
                                if (delta2 == null) {
                                    return new ObjectLoader.SmallObject(i5, bArr2);
                                }
                                bArrDecompress2 = bArr2;
                            } catch (OutOfMemoryError e) {
                                throw new LargeObjectException.OutOfMemory(e);
                            }
                        } catch (DataFormatException e2) {
                            e = e2;
                            j2 = j9;
                            throw new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(j2), getFileName()), e);
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

    private long findDeltaBase(DfsReader dfsReader, ObjectId objectId) throws IOException {
        long jFindOffset = idx(dfsReader).findOffset(objectId);
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

    byte[] getDeltaHeader(DfsReader dfsReader, long j) throws DataFormatException, IOException {
        byte[] bArr = new byte[32];
        dfsReader.inflate(this, j, bArr, true);
        return bArr;
    }

    int getObjectType(DfsReader dfsReader, long j) throws IOException {
        int i;
        byte[] bArr = dfsReader.tempId;
        while (true) {
            readFully(j, bArr, 0, 20, dfsReader);
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
                readFully(j + ((long) i3), bArr, 0, 20, dfsReader);
                j = findDeltaBase(dfsReader, ObjectId.fromRaw(bArr));
            } else {
                throw new IOException(MessageFormat.format(JGitText.get().unknownObjectType, Integer.valueOf(i)));
            }
        }
        return i;
    }

    long getObjectSize(DfsReader dfsReader, AnyObjectId anyObjectId) throws IOException {
        long jFindOffset = idx(dfsReader).findOffset(anyObjectId);
        if (0 < jFindOffset) {
            return getObjectSize(dfsReader, jFindOffset);
        }
        return -1L;
    }

    long getObjectSize(DfsReader dfsReader, long j) throws IOException {
        long j2;
        byte[] bArr = dfsReader.tempId;
        readFully(j, bArr, 0, 20, dfsReader);
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
            return BinaryDelta.getResultSize(getDeltaHeader(dfsReader, j2));
        } catch (DataFormatException e) {
            throw new CorruptObjectException(MessageFormat.format(JGitText.get().objectAtHasBadZlibStream, Long.valueOf(j), getFileName()), e);
        }
    }

    void representation(DfsObjectRepresentation dfsObjectRepresentation, long j, DfsReader dfsReader, PackReverseIndex packReverseIndex) throws IOException {
        dfsObjectRepresentation.offset = j;
        byte[] bArr = dfsReader.tempId;
        readFully(j, bArr, 0, 20, dfsReader);
        int i = bArr[0] & 255;
        int i2 = 7;
        int i3 = (i >> 4) & 7;
        int i4 = 1;
        while ((i & 128) != 0) {
            int i5 = bArr[i4] & 255;
            i4++;
            i = i5;
            i2 = 7;
        }
        long jFindNextOffset = packReverseIndex.findNextOffset(j, this.length - 20) - j;
        if (i3 == 1 || i3 == 2 || i3 == 3 || i3 == 4) {
            dfsObjectRepresentation.format = 1;
            dfsObjectRepresentation.baseId = null;
            dfsObjectRepresentation.length = jFindNextOffset - ((long) i4);
            return;
        }
        if (i3 != 6) {
            if (i3 == i2) {
                long j2 = i4;
                readFully(j + j2, bArr, 0, 20, dfsReader);
                dfsObjectRepresentation.format = 0;
                dfsObjectRepresentation.baseId = ObjectId.fromRaw(bArr);
                dfsObjectRepresentation.length = (jFindNextOffset - j2) - 20;
                return;
            }
            throw new IOException(MessageFormat.format(JGitText.get().unknownObjectType, Integer.valueOf(i3)));
        }
        int i6 = i4 + 1;
        byte b = bArr[i4];
        int i7 = b & 255;
        long j3 = b & 127;
        while ((i7 & 128) != 0) {
            int i8 = i6 + 1;
            byte b2 = bArr[i6];
            int i9 = b2 & 255;
            j3 = ((j3 + 1) << i2) + ((long) (b2 & 127));
            i6 = i8;
            i7 = i9;
            i2 = 7;
        }
        dfsObjectRepresentation.format = 0;
        dfsObjectRepresentation.baseId = packReverseIndex.findObject(j - j3);
        dfsObjectRepresentation.length = jFindNextOffset - ((long) i6);
    }

    boolean isCorrupt(long j) {
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
            synchronized (this.initLock) {
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

    private DfsBlockCache.Ref<PackIndex> loadPackIndex(DfsReader dfsReader, DfsStreamKey dfsStreamKey) throws IOException {
        try {
            dfsReader.stats.readIdx++;
            long jNanoTime = System.nanoTime();
            Throwable th = null;
            try {
                ReadableChannel readableChannelOpenFile = dfsReader.db.openFile(this.desc, PackExt.INDEX);
                try {
                    InputStream inputStreamNewInputStream = Channels.newInputStream(readableChannelOpenFile);
                    int iBlockSize = readableChannelOpenFile.blockSize();
                    if (iBlockSize > 0 && iBlockSize < 8192) {
                        iBlockSize *= 8192 / iBlockSize;
                    } else if (iBlockSize <= 0) {
                        iBlockSize = 8192;
                    }
                    PackIndex packIndex = PackIndex.read(new BufferedInputStream(inputStreamNewInputStream, iBlockSize));
                    dfsReader.stats.readIdxBytes += readableChannelOpenFile.position();
                    this.index = packIndex;
                    DfsBlockCache.Ref<PackIndex> ref = new DfsBlockCache.Ref<>(dfsStreamKey, 0L, packIndex.getObjectCount() * 28, packIndex);
                    if (readableChannelOpenFile != null) {
                        readableChannelOpenFile.close();
                    }
                    dfsReader.stats.readIdxMicros += elapsedMicros(jNanoTime);
                    return ref;
                } catch (Throwable th2) {
                    if (readableChannelOpenFile == null) {
                        throw th2;
                    }
                    readableChannelOpenFile.close();
                    throw th2;
                }
            } catch (Throwable th3) {
                if (0 == 0) {
                    throw th3;
                }
                if (null == th3) {
                    throw null;
                }
                try {
                    th.addSuppressed(th3);
                    throw null;
                } catch (Throwable th4) {
                    dfsReader.stats.readIdxMicros += elapsedMicros(jNanoTime);
                    throw th4;
                }
            }
        } catch (EOFException e) {
            throw new IOException(MessageFormat.format(DfsText.get().shortReadOfIndex, this.desc.getFileName(PackExt.INDEX)), e);
        } catch (IOException e2) {
            throw new IOException(MessageFormat.format(DfsText.get().cannotReadIndex, this.desc.getFileName(PackExt.INDEX)), e2);
        }
    }

    private DfsBlockCache.Ref<PackReverseIndex> loadReverseIdx(DfsReader dfsReader, DfsStreamKey dfsStreamKey, PackIndex packIndex) {
        dfsReader.stats.readReverseIdx++;
        long jNanoTime = System.nanoTime();
        PackReverseIndex packReverseIndex = new PackReverseIndex(packIndex);
        this.reverseIndex = packReverseIndex;
        dfsReader.stats.readReverseIdxMicros += elapsedMicros(jNanoTime);
        return new DfsBlockCache.Ref<>(dfsStreamKey, 0L, packIndex.getObjectCount() * 8, packReverseIndex);
    }

    private DfsBlockCache.Ref<PackBitmapIndex> loadBitmapIndex(DfsReader dfsReader, DfsStreamKey dfsStreamKey, PackIndex packIndex, PackReverseIndex packReverseIndex) throws IOException {
        dfsReader.stats.readBitmap++;
        long jNanoTime = System.nanoTime();
        Throwable th = null;
        try {
            ReadableChannel readableChannelOpenFile = dfsReader.db.openFile(this.desc, PackExt.BITMAP_INDEX);
            try {
                try {
                    InputStream inputStreamNewInputStream = Channels.newInputStream(readableChannelOpenFile);
                    int iBlockSize = readableChannelOpenFile.blockSize();
                    if (iBlockSize > 0 && iBlockSize < 8192) {
                        iBlockSize *= 8192 / iBlockSize;
                    } else if (iBlockSize <= 0) {
                        iBlockSize = 8192;
                    }
                    PackBitmapIndex packBitmapIndex = PackBitmapIndex.read(new BufferedInputStream(inputStreamNewInputStream, iBlockSize), packIndex, packReverseIndex);
                    long jPosition = readableChannelOpenFile.position();
                    dfsReader.stats.readBitmapIdxBytes += jPosition;
                    dfsReader.stats.readBitmapIdxMicros += elapsedMicros(jNanoTime);
                    this.bitmapIndex = packBitmapIndex;
                    DfsBlockCache.Ref<PackBitmapIndex> ref = new DfsBlockCache.Ref<>(dfsStreamKey, 0L, jPosition, packBitmapIndex);
                    if (readableChannelOpenFile != null) {
                        readableChannelOpenFile.close();
                    }
                    return ref;
                } catch (Throwable th2) {
                    dfsReader.stats.readBitmapIdxBytes += readableChannelOpenFile.position();
                    dfsReader.stats.readBitmapIdxMicros += elapsedMicros(jNanoTime);
                    throw th2;
                }
            } catch (Throwable th3) {
                if (readableChannelOpenFile == null) {
                    throw th3;
                }
                readableChannelOpenFile.close();
                throw th3;
            }
        } catch (Throwable th4) {
            if (0 == 0) {
                throw th4;
            }
            if (null == th4) {
                throw null;
            }
            try {
                th.addSuppressed(th4);
                throw null;
            } catch (EOFException e) {
                throw new IOException(MessageFormat.format(DfsText.get().shortReadOfIndex, this.desc.getFileName(PackExt.BITMAP_INDEX)), e);
            } catch (IOException e2) {
                throw new IOException(MessageFormat.format(DfsText.get().cannotReadIndex, this.desc.getFileName(PackExt.BITMAP_INDEX)), e2);
            }
        }
    }
}
