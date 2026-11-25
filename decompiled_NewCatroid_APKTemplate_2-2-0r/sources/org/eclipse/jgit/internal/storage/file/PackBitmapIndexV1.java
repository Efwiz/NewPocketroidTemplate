package org.eclipse.jgit.internal.storage.file;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;

/* JADX INFO: loaded from: classes13.dex */
class PackBitmapIndexV1 extends BasePackBitmapIndex {
    static final byte[] MAGIC = {66, 73, 84, 77};
    private static final int MAX_XOR_OFFSET = 126;
    static final int OPT_FULL = 1;
    private final ObjectIdOwnerMap<BasePackBitmapIndex.StoredBitmap> bitmaps;
    private final EWAHCompressedBitmap blobs;
    private final EWAHCompressedBitmap commits;
    private final PackIndex packIndex;
    private final PackReverseIndex reverseIndex;
    private final EWAHCompressedBitmap tags;
    private final EWAHCompressedBitmap trees;

    PackBitmapIndexV1(InputStream inputStream, PackIndex packIndex, PackReverseIndex packReverseIndex) throws IOException {
        BasePackBitmapIndex.StoredBitmap storedBitmap;
        super(new ObjectIdOwnerMap());
        this.packIndex = packIndex;
        this.reverseIndex = packReverseIndex;
        this.bitmaps = getBitmaps();
        byte[] bArr = new byte[32];
        IO.readFully(inputStream, bArr, 0, 32);
        int i = 0;
        while (true) {
            byte[] bArr2 = MAGIC;
            if (i < bArr2.length) {
                if (bArr[i] != bArr2[i]) {
                    byte[] bArr3 = new byte[bArr2.length];
                    System.arraycopy(bArr, 0, bArr3, 0, bArr2.length);
                    throw new IOException(MessageFormat.format(JGitText.get().expectedGot, Arrays.toString(bArr2), Arrays.toString(bArr3)));
                }
                i++;
            } else {
                char c = 4;
                int iDecodeUInt16 = NB.decodeUInt16(bArr, 4);
                if (iDecodeUInt16 != 1) {
                    throw new IOException(MessageFormat.format(JGitText.get().unsupportedPackIndexVersion, Integer.valueOf(iDecodeUInt16)));
                }
                int i2 = 6;
                int iDecodeUInt17 = NB.decodeUInt16(bArr, 6);
                if ((iDecodeUInt17 & 1) == 0) {
                    throw new IOException(MessageFormat.format(JGitText.get().expectedGot, 1, Integer.valueOf(iDecodeUInt17)));
                }
                long jDecodeUInt32 = NB.decodeUInt32(bArr, 8);
                if (jDecodeUInt32 > 2147483647L) {
                    throw new IOException(JGitText.get().indexFileIsTooLargeForJgit);
                }
                this.packChecksum = new byte[20];
                System.arraycopy(bArr, 12, this.packChecksum, 0, this.packChecksum.length);
                SimpleDataInput simpleDataInput = new SimpleDataInput(inputStream);
                this.commits = readBitmap(simpleDataInput);
                this.trees = readBitmap(simpleDataInput);
                this.blobs = readBitmap(simpleDataInput);
                this.tags = readBitmap(simpleDataInput);
                BasePackBitmapIndex.StoredBitmap[] storedBitmapArr = new BasePackBitmapIndex.StoredBitmap[126];
                int i3 = 0;
                while (i3 < ((int) jDecodeUInt32)) {
                    IO.readFully(inputStream, bArr, 0, i2);
                    int iDecodeInt32 = NB.decodeInt32(bArr, 0);
                    byte b = bArr[c];
                    byte b2 = bArr[5];
                    EWAHCompressedBitmap bitmap = readBitmap(simpleDataInput);
                    if (iDecodeInt32 < 0) {
                        throw new IOException(MessageFormat.format(JGitText.get().invalidId, String.valueOf(iDecodeInt32)));
                    }
                    if (b < 0) {
                        throw new IOException(MessageFormat.format(JGitText.get().invalidId, String.valueOf((int) b)));
                    }
                    if (b > 126) {
                        throw new IOException(MessageFormat.format(JGitText.get().expectedLessThanGot, String.valueOf(126), String.valueOf((int) b)));
                    }
                    if (b > i3) {
                        throw new IOException(MessageFormat.format(JGitText.get().expectedLessThanGot, String.valueOf(i3), String.valueOf((int) b)));
                    }
                    ObjectId objectId = packIndex.getObjectId(iDecodeInt32);
                    if (b > 0) {
                        storedBitmap = storedBitmapArr[(i3 - b) % 126];
                        if (storedBitmap == null) {
                            throw new IOException(MessageFormat.format(JGitText.get().invalidId, String.valueOf((int) b)));
                        }
                    } else {
                        storedBitmap = null;
                    }
                    BasePackBitmapIndex.StoredBitmap storedBitmap2 = new BasePackBitmapIndex.StoredBitmap(objectId, bitmap, storedBitmap, b2);
                    this.bitmaps.add(storedBitmap2);
                    storedBitmapArr[i3 % 126] = storedBitmap2;
                    i3++;
                    c = 4;
                    i2 = 6;
                }
                return;
            }
        }
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public int findPosition(AnyObjectId anyObjectId) {
        long jFindOffset = this.packIndex.findOffset(anyObjectId);
        if (jFindOffset == -1) {
            return -1;
        }
        return this.reverseIndex.findPostion(jFindOffset);
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public ObjectId getObject(int i) throws IllegalArgumentException {
        ObjectId objectIdFindObjectByPosition = this.reverseIndex.findObjectByPosition(i);
        if (objectIdFindObjectByPosition != null) {
            return objectIdFindObjectByPosition;
        }
        throw new IllegalArgumentException();
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public int getObjectCount() {
        return (int) this.packIndex.getObjectCount();
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public EWAHCompressedBitmap ofObjectType(EWAHCompressedBitmap eWAHCompressedBitmap, int i) {
        if (i == 1) {
            return this.commits.and(eWAHCompressedBitmap);
        }
        if (i == 2) {
            return this.trees.and(eWAHCompressedBitmap);
        }
        if (i == 3) {
            return this.blobs.and(eWAHCompressedBitmap);
        }
        if (i == 4) {
            return this.tags.and(eWAHCompressedBitmap);
        }
        throw new IllegalArgumentException();
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public int getBitmapCount() {
        return this.bitmaps.size();
    }

    public boolean equals(Object obj) {
        return (obj instanceof PackBitmapIndexV1) && getPackIndex() == ((PackBitmapIndexV1) obj).getPackIndex();
    }

    public int hashCode() {
        return getPackIndex().hashCode();
    }

    PackIndex getPackIndex() {
        return this.packIndex;
    }

    private static EWAHCompressedBitmap readBitmap(DataInput dataInput) throws IOException {
        EWAHCompressedBitmap eWAHCompressedBitmap = new EWAHCompressedBitmap();
        eWAHCompressedBitmap.deserialize(dataInput);
        return eWAHCompressedBitmap;
    }
}
