package org.eclipse.jgit.internal.storage.dfs;

/* JADX INFO: loaded from: classes13.dex */
public class DfsReaderIoStats {
    private final Accumulator stats;

    public static class Accumulator {
        long bitmapCacheHit;
        long blockCacheHit;
        long idxCacheHit;
        long inflatedBytes;
        long inflationMicros;
        long readBitmap;
        long readBitmapIdxBytes;
        long readBitmapIdxMicros;
        long readBlock;
        long readBlockBytes;
        long readBlockMicros;
        long readIdx;
        long readIdxBytes;
        long readIdxMicros;
        long readReverseIdx;
        long readReverseIdxMicros;
        long ridxCacheHit;
        long scanPacks;

        Accumulator() {
        }
    }

    DfsReaderIoStats(Accumulator accumulator) {
        this.stats = accumulator;
    }

    public long getScanPacks() {
        return this.stats.scanPacks;
    }

    public long getPackIndexCacheHits() {
        return this.stats.idxCacheHit;
    }

    public long getReverseIndexCacheHits() {
        return this.stats.ridxCacheHit;
    }

    public long getBitmapIndexCacheHits() {
        return this.stats.bitmapCacheHit;
    }

    public long getReadPackIndexCount() {
        return this.stats.readIdx;
    }

    public long getReadReverseIndexCount() {
        return this.stats.readReverseIdx;
    }

    public long getReadBitmapIndexCount() {
        return this.stats.readBitmap;
    }

    public long getReadIndexBytes() {
        return this.stats.readIdxBytes;
    }

    public long getReadIndexMicros() {
        return this.stats.readIdxMicros;
    }

    public long getReadReverseIndexMicros() {
        return this.stats.readReverseIdxMicros;
    }

    public long getReadBitmapIndexBytes() {
        return this.stats.readBitmapIdxBytes;
    }

    public long getReadBitmapIndexMicros() {
        return this.stats.readBitmapIdxMicros;
    }

    public long getBlockCacheHits() {
        return this.stats.blockCacheHit;
    }

    public long getReadBlocksCount() {
        return this.stats.readBlock;
    }

    public long getReadBlocksBytes() {
        return this.stats.readBlockBytes;
    }

    public long getReadBlocksMicros() {
        return this.stats.readBlockMicros;
    }

    public long getInflatedBytes() {
        return this.stats.inflatedBytes;
    }

    public long getInflationMicros() {
        return this.stats.inflationMicros;
    }
}
