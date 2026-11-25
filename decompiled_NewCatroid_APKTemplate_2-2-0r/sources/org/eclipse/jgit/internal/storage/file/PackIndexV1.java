package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;

/* JADX INFO: loaded from: classes13.dex */
class PackIndexV1 extends PackIndex {
    private static final int IDX_HDR_LEN = 1024;
    private final long[] idxHeader;
    byte[][] idxdata;
    private long objectCnt;

    private static int idOffset(int i) {
        return (i * 24) + 4;
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackIndex
    public boolean hasCRC32Support() {
        return false;
    }

    PackIndexV1(InputStream inputStream, byte[] bArr) throws IOException {
        long[] jArr;
        long j;
        byte[] bArr2 = new byte[1024];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        IO.readFully(inputStream, bArr2, bArr.length, 1024 - bArr.length);
        this.idxHeader = new long[256];
        int i = 0;
        while (true) {
            jArr = this.idxHeader;
            if (i >= jArr.length) {
                break;
            }
            jArr[i] = NB.decodeUInt32(bArr2, i * 4);
            i++;
        }
        this.idxdata = new byte[jArr.length][];
        int i2 = 0;
        while (true) {
            long[] jArr2 = this.idxHeader;
            if (i2 >= jArr2.length) {
                this.objectCnt = jArr2[255];
                this.packChecksum = new byte[20];
                IO.readFully(inputStream, this.packChecksum, 0, this.packChecksum.length);
                return;
            }
            if (i2 == 0) {
                j = jArr2[i2];
            } else {
                j = jArr2[i2] - jArr2[i2 - 1];
            }
            if (j > 0) {
                long j2 = j * 24;
                if (j2 > 2147483639) {
                    throw new IOException(JGitText.get().indexFileIsTooLargeForJgit);
                }
                byte[] bArr3 = new byte[(int) j2];
                this.idxdata[i2] = bArr3;
                IO.readFully(inputStream, bArr3, 0, bArr3.length);
            }
            i2++;
        }
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackIndex
    public long getObjectCount() {
        return this.objectCnt;
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackIndex
    public long getOffset64Count() {
        Iterator<PackIndex.MutableEntry> it = iterator();
        long j = 0;
        while (it.hasNext()) {
            if (it.next().getOffset() >= 2147483647L) {
                j++;
            }
        }
        return j;
    }

    private int findLevelOne(long j) {
        int iBinarySearch = Arrays.binarySearch(this.idxHeader, j + 1);
        if (iBinarySearch < 0) {
            return -(iBinarySearch + 1);
        }
        long j2 = this.idxHeader[iBinarySearch];
        while (iBinarySearch > 0 && j2 == this.idxHeader[iBinarySearch - 1]) {
            iBinarySearch--;
        }
        return iBinarySearch;
    }

    private int getLevelTwo(long j, int i) {
        return (int) (j - (i > 0 ? this.idxHeader[i - 1] : 0L));
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackIndex
    public ObjectId getObjectId(long j) {
        int iFindLevelOne = findLevelOne(j);
        return ObjectId.fromRaw(this.idxdata[iFindLevelOne], idOffset(getLevelTwo(j, iFindLevelOne)));
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackIndex
    long getOffset(long j) {
        int iFindLevelOne = findLevelOne(j);
        return NB.decodeUInt32(this.idxdata[iFindLevelOne], getLevelTwo(j, iFindLevelOne) * 24);
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackIndex
    public long findOffset(AnyObjectId anyObjectId) {
        byte[] bArr = this.idxdata[anyObjectId.getFirstByte()];
        if (bArr == null) {
            return -1L;
        }
        int length = bArr.length / 24;
        int i = 0;
        do {
            int i2 = (i + length) >>> 1;
            int iIdOffset = idOffset(i2);
            int iCompareTo = anyObjectId.compareTo(bArr, iIdOffset);
            if (iCompareTo < 0) {
                length = i2;
            } else {
                if (iCompareTo == 0) {
                    return ((long) ((bArr[iIdOffset - 2] & 255) << 8)) | (((long) (bArr[iIdOffset - 4] & 255)) << 24) | ((long) ((bArr[iIdOffset - 3] & 255) << 16)) | ((long) (bArr[iIdOffset - 1] & 255));
                }
                i = i2 + 1;
            }
        } while (i < length);
        return -1L;
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackIndex
    public long findCRC32(AnyObjectId anyObjectId) {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackIndex, java.lang.Iterable
    public Iterator<PackIndex.MutableEntry> iterator() {
        return new IndexV1Iterator(this, null);
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackIndex
    public void resolve(Set<ObjectId> set, AbbreviatedObjectId abbreviatedObjectId, int i) throws IOException {
        byte[] bArr = this.idxdata[abbreviatedObjectId.getFirstByte()];
        if (bArr == null) {
            return;
        }
        int length = bArr.length / 24;
        int i2 = 0;
        int i3 = length;
        do {
            int i4 = (i2 + i3) >>> 1;
            int iPrefixCompare = abbreviatedObjectId.prefixCompare(bArr, idOffset(i4));
            if (iPrefixCompare < 0) {
                i3 = i4;
            } else {
                if (iPrefixCompare == 0) {
                    while (i4 > 0 && abbreviatedObjectId.prefixCompare(bArr, idOffset(i4 - 1)) == 0) {
                        i4--;
                    }
                    while (i4 < length && abbreviatedObjectId.prefixCompare(bArr, idOffset(i4)) == 0) {
                        set.add(ObjectId.fromRaw(bArr, idOffset(i4)));
                        if (set.size() > i) {
                            return;
                        } else {
                            i4++;
                        }
                    }
                    return;
                }
                i2 = i4 + 1;
            }
        } while (i2 < i3);
    }

    private class IndexV1Iterator extends PackIndex.EntriesIterator {
        int levelOne;
        int levelTwo;

        private IndexV1Iterator() {
            super();
        }

        /* synthetic */ IndexV1Iterator(PackIndexV1 packIndexV1, IndexV1Iterator indexV1Iterator) {
            this();
        }

        @Override // org.eclipse.jgit.internal.storage.file.PackIndex.EntriesIterator
        protected PackIndex.MutableEntry initEntry() {
            return new PackIndex.MutableEntry() { // from class: org.eclipse.jgit.internal.storage.file.PackIndexV1.IndexV1Iterator.1
                @Override // org.eclipse.jgit.internal.storage.file.PackIndex.MutableEntry
                protected void ensureId() {
                    this.idBuffer.fromRaw(PackIndexV1.this.idxdata[IndexV1Iterator.this.levelOne], IndexV1Iterator.this.levelTwo - 20);
                }
            };
        }

        @Override // org.eclipse.jgit.internal.storage.file.PackIndex.EntriesIterator, java.util.Iterator
        public PackIndex.MutableEntry next() {
            while (this.levelOne < PackIndexV1.this.idxdata.length) {
                if (PackIndexV1.this.idxdata[this.levelOne] != null) {
                    if (this.levelTwo < PackIndexV1.this.idxdata[this.levelOne].length) {
                        this.entry.offset = NB.decodeUInt32(PackIndexV1.this.idxdata[this.levelOne], this.levelTwo);
                        this.levelTwo += 24;
                        this.returnedNumber++;
                        return this.entry;
                    }
                    this.levelTwo = 0;
                }
                this.levelOne++;
            }
            throw new NoSuchElementException();
        }
    }
}
