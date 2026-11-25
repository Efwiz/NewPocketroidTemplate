package org.eclipse.jgit.internal.storage.file;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.BitmapCommit;
import org.eclipse.jgit.internal.storage.pack.ObjectToPack;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BitmapIndex;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;
import org.eclipse.jgit.util.BlockList;

/* JADX INFO: loaded from: classes13.dex */
public class PackBitmapIndexBuilder extends BasePackBitmapIndex {
    private static final int MAX_XOR_OFFSET_SEARCH = 10;
    private List<StoredEntry> bitmapsToWrite;
    private final LinkedList<BasePackBitmapIndex.StoredBitmap> bitmapsToWriteXorBuffer;
    private final EWAHCompressedBitmap blobs;
    private final BlockList<PositionEntry> byOffset;
    private final EWAHCompressedBitmap commits;
    final ObjectIdOwnerMap<PositionEntry> positionEntries;
    private final EWAHCompressedBitmap tags;
    private final EWAHCompressedBitmap trees;

    @Override // org.eclipse.jgit.internal.storage.file.BasePackBitmapIndex, org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public /* bridge */ /* synthetic */ EWAHCompressedBitmap getBitmap(AnyObjectId anyObjectId) {
        return super.getBitmap(anyObjectId);
    }

    public int getOptions() {
        return 1;
    }

    public PackBitmapIndexBuilder(List<ObjectToPack> list) {
        super(new ObjectIdOwnerMap());
        this.bitmapsToWriteXorBuffer = new LinkedList<>();
        this.bitmapsToWrite = new ArrayList();
        ObjectIdOwnerMap<PositionEntry> objectIdOwnerMap = new ObjectIdOwnerMap<>();
        this.positionEntries = objectIdOwnerMap;
        BlockList<PositionEntry> blockList = new BlockList<>(list.size());
        this.byOffset = blockList;
        sortByOffsetAndIndex(blockList, objectIdOwnerMap, list);
        int iMax = Math.max(4, (blockList.size() / 64) / 3);
        this.commits = new EWAHCompressedBitmap(iMax);
        this.trees = new EWAHCompressedBitmap(iMax);
        this.blobs = new EWAHCompressedBitmap(iMax);
        this.tags = new EWAHCompressedBitmap(iMax);
        for (int i = 0; i < list.size(); i++) {
            int type = list.get(i).getType();
            if (type == 1) {
                this.commits.set(i);
            } else if (type == 2) {
                this.trees.set(i);
            } else if (type == 3) {
                this.blobs.set(i);
            } else if (type == 4) {
                this.tags.set(i);
            } else {
                throw new IllegalArgumentException(MessageFormat.format(JGitText.get().badObjectType, String.valueOf(type)));
            }
        }
        this.commits.trim();
        this.trees.trim();
        this.blobs.trim();
        this.tags.trim();
    }

    private static void sortByOffsetAndIndex(BlockList<PositionEntry> blockList, ObjectIdOwnerMap<PositionEntry> objectIdOwnerMap, List<ObjectToPack> list) {
        for (int i = 0; i < list.size(); i++) {
            objectIdOwnerMap.add(new PositionEntry(list.get(i), i));
        }
        Collections.sort(list, new Comparator() { // from class: org.eclipse.jgit.internal.storage.file.PackBitmapIndexBuilder$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Long.signum(((ObjectToPack) obj).getOffset() - ((ObjectToPack) obj2).getOffset());
            }
        });
        for (int i2 = 0; i2 < list.size(); i2++) {
            PositionEntry positionEntry = (PositionEntry) objectIdOwnerMap.get(list.get(i2));
            positionEntry.offsetPosition = i2;
            blockList.add(positionEntry);
        }
    }

    public ObjectIdOwnerMap<ObjectIdOwnerMap.Entry> getObjectSet() {
        ObjectIdOwnerMap<ObjectIdOwnerMap.Entry> objectIdOwnerMap = new ObjectIdOwnerMap<>();
        Iterator<PositionEntry> it = this.byOffset.iterator();
        while (it.hasNext()) {
            objectIdOwnerMap.add(new ObjectIdOwnerMap.Entry(it.next()) { // from class: org.eclipse.jgit.internal.storage.file.PackBitmapIndexBuilder.1
            });
        }
        return objectIdOwnerMap;
    }

    public void addBitmap(AnyObjectId anyObjectId, BitmapIndex.Bitmap bitmap, int i) {
        addBitmap(anyObjectId, bitmap.retrieveCompressed(), i);
    }

    public void processBitmapForWrite(BitmapCommit bitmapCommit, BitmapIndex.Bitmap bitmap, int i) {
        EWAHCompressedBitmap eWAHCompressedBitmapRetrieveCompressed = bitmap.retrieveCompressed();
        eWAHCompressedBitmapRetrieveCompressed.trim();
        this.bitmapsToWriteXorBuffer.add(new BasePackBitmapIndex.StoredBitmap(bitmapCommit, eWAHCompressedBitmapRetrieveCompressed, null, i));
        if (this.bitmapsToWriteXorBuffer.size() > 10) {
            this.bitmapsToWrite.add(generateStoredEntry(this.bitmapsToWriteXorBuffer.pollFirst()));
        }
        if (bitmapCommit.isAddToIndex()) {
            addBitmap(bitmapCommit, bitmap, i);
        }
    }

    private StoredEntry generateStoredEntry(BasePackBitmapIndex.StoredBitmap storedBitmap) {
        EWAHCompressedBitmap bitmap = storedBitmap.getBitmap();
        Iterator<BasePackBitmapIndex.StoredBitmap> it = this.bitmapsToWriteXorBuffer.iterator();
        EWAHCompressedBitmap eWAHCompressedBitmap = bitmap;
        int i = 0;
        int i2 = 1;
        while (it.hasNext()) {
            EWAHCompressedBitmap eWAHCompressedBitmapXor = it.next().getBitmap().xor(storedBitmap.getBitmap());
            if (eWAHCompressedBitmapXor.sizeInBytes() < eWAHCompressedBitmap.sizeInBytes()) {
                i = i2;
                eWAHCompressedBitmap = eWAHCompressedBitmapXor;
            }
            i2++;
        }
        PositionEntry positionEntry = (PositionEntry) this.positionEntries.get(storedBitmap);
        if (positionEntry == null) {
            throw new IllegalStateException();
        }
        eWAHCompressedBitmap.trim();
        return new StoredEntry(positionEntry.namePosition, eWAHCompressedBitmap, i, storedBitmap.getFlags());
    }

    public void addBitmap(AnyObjectId anyObjectId, EWAHCompressedBitmap eWAHCompressedBitmap, int i) {
        eWAHCompressedBitmap.trim();
        getBitmaps().add(new BasePackBitmapIndex.StoredBitmap(anyObjectId, eWAHCompressedBitmap, null, i));
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public EWAHCompressedBitmap ofObjectType(EWAHCompressedBitmap eWAHCompressedBitmap, int i) {
        if (i == 1) {
            return getCommits().and(eWAHCompressedBitmap);
        }
        if (i == 2) {
            return getTrees().and(eWAHCompressedBitmap);
        }
        if (i == 3) {
            return getBlobs().and(eWAHCompressedBitmap);
        }
        if (i == 4) {
            return getTags().and(eWAHCompressedBitmap);
        }
        throw new IllegalArgumentException();
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public int findPosition(AnyObjectId anyObjectId) {
        PositionEntry positionEntry = (PositionEntry) this.positionEntries.get(anyObjectId);
        if (positionEntry == null) {
            return -1;
        }
        return positionEntry.offsetPosition;
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public ObjectId getObject(int i) throws IllegalArgumentException {
        PositionEntry positionEntry = this.byOffset.get(i);
        if (positionEntry != null) {
            return positionEntry;
        }
        throw new IllegalArgumentException();
    }

    public EWAHCompressedBitmap getCommits() {
        return this.commits;
    }

    public EWAHCompressedBitmap getTrees() {
        return this.trees;
    }

    public EWAHCompressedBitmap getBlobs() {
        return this.blobs;
    }

    public EWAHCompressedBitmap getTags() {
        return this.tags;
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public int getBitmapCount() {
        return this.bitmapsToWriteXorBuffer.size() + this.bitmapsToWrite.size();
    }

    public void resetBitmaps(int i) {
        getBitmaps().clear();
        this.bitmapsToWrite = new ArrayList(i);
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public int getObjectCount() {
        return this.byOffset.size();
    }

    public List<StoredEntry> getCompressedBitmaps() {
        while (!this.bitmapsToWriteXorBuffer.isEmpty()) {
            this.bitmapsToWrite.add(generateStoredEntry(this.bitmapsToWriteXorBuffer.pollFirst()));
        }
        Collections.reverse(this.bitmapsToWrite);
        return this.bitmapsToWrite;
    }

    public static final class StoredEntry {
        private final EWAHCompressedBitmap bitmap;
        private final int flags;
        private final long objectId;
        private final int xorOffset;

        StoredEntry(long j, EWAHCompressedBitmap eWAHCompressedBitmap, int i, int i2) {
            this.objectId = j;
            this.bitmap = eWAHCompressedBitmap;
            this.xorOffset = i;
            this.flags = i2;
        }

        public EWAHCompressedBitmap getBitmap() {
            return this.bitmap;
        }

        public int getXorOffset() {
            return this.xorOffset;
        }

        public int getFlags() {
            return this.flags;
        }

        public long getObjectId() {
            return this.objectId;
        }
    }

    private static final class PositionEntry extends ObjectIdOwnerMap.Entry {
        final int namePosition;
        int offsetPosition;

        PositionEntry(AnyObjectId anyObjectId, int i) {
            super(anyObjectId);
            this.namePosition = i;
        }
    }
}
