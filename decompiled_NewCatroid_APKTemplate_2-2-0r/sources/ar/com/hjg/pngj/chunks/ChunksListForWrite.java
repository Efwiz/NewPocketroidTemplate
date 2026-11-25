package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.PngjOutputException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ChunksListForWrite extends ChunksList {
    private HashMap<String, Integer> alreadyWrittenKeys;
    private final List<PngChunk> queuedChunks;

    public ChunksListForWrite(ImageInfo imageInfo) {
        super(imageInfo);
        this.queuedChunks = new ArrayList();
        this.alreadyWrittenKeys = new HashMap<>();
    }

    public List<? extends PngChunk> getQueuedById(String str) {
        return getQueuedById(str, null);
    }

    public List<? extends PngChunk> getQueuedById(String str, String str2) {
        return getXById(this.queuedChunks, str, str2);
    }

    public PngChunk getQueuedById1(String str, String str2, boolean z) {
        List<? extends PngChunk> queuedById = getQueuedById(str, str2);
        if (queuedById.isEmpty()) {
            return null;
        }
        if (queuedById.size() > 1 && (z || !queuedById.get(0).allowsMultiple())) {
            throw new PngjException("unexpected multiple chunks id=" + str);
        }
        return queuedById.get(queuedById.size() - 1);
    }

    public PngChunk getQueuedById1(String str, boolean z) {
        return getQueuedById1(str, null, z);
    }

    public PngChunk getQueuedById1(String str) {
        return getQueuedById1(str, false);
    }

    public List<PngChunk> getQueuedEquivalent(final PngChunk pngChunk) {
        return ChunkHelper.filterList(this.queuedChunks, new ChunkPredicate() { // from class: ar.com.hjg.pngj.chunks.ChunksListForWrite.1
            @Override // ar.com.hjg.pngj.chunks.ChunkPredicate
            public boolean match(PngChunk pngChunk2) {
                return ChunkHelper.equivalent(pngChunk2, pngChunk);
            }
        });
    }

    public boolean removeChunk(PngChunk pngChunk) {
        if (pngChunk == null) {
            return false;
        }
        return this.queuedChunks.remove(pngChunk);
    }

    public boolean queue(PngChunk pngChunk) {
        this.queuedChunks.add(pngChunk);
        return true;
    }

    private static boolean shouldWrite(PngChunk pngChunk, int i) {
        int i2;
        int chunkGroup;
        if (i == 2) {
            return pngChunk.f62id.equals("PLTE");
        }
        if (i % 2 == 0) {
            throw new PngjOutputException("bad chunk group?");
        }
        if (pngChunk.getOrderingConstraint().mustGoBeforePLTE()) {
            chunkGroup = 1;
            i2 = 1;
        } else {
            if (pngChunk.getOrderingConstraint().mustGoBeforeIDAT()) {
                i2 = 3;
                if (pngChunk.getOrderingConstraint().mustGoAfterPLTE()) {
                    chunkGroup = 3;
                }
            } else {
                i2 = 5;
            }
            chunkGroup = 1;
        }
        if (!pngChunk.hasPriority()) {
            chunkGroup = i2;
        }
        if (ChunkHelper.isUnknown(pngChunk) && pngChunk.getChunkGroup() > 0) {
            chunkGroup = pngChunk.getChunkGroup();
        }
        if (i == chunkGroup) {
            return true;
        }
        return i > chunkGroup && i <= i2;
    }

    public int writeChunks(OutputStream outputStream, int i) {
        Iterator<PngChunk> it = this.queuedChunks.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            PngChunk next = it.next();
            if (shouldWrite(next, i)) {
                if (ChunkHelper.isCritical(next.f62id) && !next.f62id.equals("PLTE")) {
                    throw new PngjOutputException("bad chunk queued: " + next);
                }
                if (this.alreadyWrittenKeys.containsKey(next.f62id) && !next.allowsMultiple()) {
                    throw new PngjOutputException("duplicated chunk does not allow multiple: " + next);
                }
                next.write(outputStream);
                this.chunks.add(next);
                this.alreadyWrittenKeys.put(next.f62id, Integer.valueOf(this.alreadyWrittenKeys.containsKey(next.f62id) ? 1 + this.alreadyWrittenKeys.get(next.f62id).intValue() : 1));
                next.setChunkGroup(i);
                it.remove();
                i2++;
            }
        }
        return i2;
    }

    public List<PngChunk> getQueuedChunks() {
        return this.queuedChunks;
    }

    @Override // ar.com.hjg.pngj.chunks.ChunksList
    public String toString() {
        return "ChunkList: written: " + getChunks().size() + " queue: " + this.queuedChunks.size();
    }

    @Override // ar.com.hjg.pngj.chunks.ChunksList
    public String toStringFull() {
        StringBuilder sb = new StringBuilder(toString());
        sb.append("\n Written:\n");
        for (PngChunk pngChunk : getChunks()) {
            sb.append(pngChunk).append(" G=" + pngChunk.getChunkGroup() + "\n");
        }
        if (!this.queuedChunks.isEmpty()) {
            sb.append(" Queued:\n");
            Iterator<PngChunk> it = this.queuedChunks.iterator();
            while (it.hasNext()) {
                sb.append(it.next()).append("\n");
            }
        }
        return sb.toString();
    }
}
