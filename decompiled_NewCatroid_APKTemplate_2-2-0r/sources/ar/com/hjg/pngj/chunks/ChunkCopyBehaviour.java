package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;

/* JADX INFO: loaded from: classes.dex */
public class ChunkCopyBehaviour {
    public static final int COPY_ALL = 8;
    public static final int COPY_ALL_SAFE = 4;
    public static final int COPY_ALMOSTALL = 256;
    public static final int COPY_NONE = 0;
    public static final int COPY_PALETTE = 1;
    public static final int COPY_PHYS = 16;
    public static final int COPY_TEXTUAL = 32;
    public static final int COPY_TRANSPARENCY = 64;
    public static final int COPY_UNKNOWN = 128;

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean maskMatch(int i, int i2) {
        return (i & i2) != 0;
    }

    public static ChunkPredicate createPredicate(final int i, final ImageInfo imageInfo) {
        return new ChunkPredicate() { // from class: ar.com.hjg.pngj.chunks.ChunkCopyBehaviour.1
            @Override // ar.com.hjg.pngj.chunks.ChunkPredicate
            public boolean match(PngChunk pngChunk) {
                if (pngChunk.crit) {
                    if (!pngChunk.f62id.equals("PLTE")) {
                        return false;
                    }
                    if (imageInfo.indexed && ChunkCopyBehaviour.maskMatch(i, 1)) {
                        return true;
                    }
                    return !imageInfo.greyscale && ChunkCopyBehaviour.maskMatch(i, 8);
                }
                boolean z = pngChunk instanceof PngChunkTextVar;
                boolean z2 = pngChunk.safe;
                if (ChunkCopyBehaviour.maskMatch(i, 8)) {
                    return true;
                }
                if (z2 && ChunkCopyBehaviour.maskMatch(i, 4)) {
                    return true;
                }
                if (pngChunk.f62id.equals("tRNS") && ChunkCopyBehaviour.maskMatch(i, 64)) {
                    return true;
                }
                if (pngChunk.f62id.equals("pHYs") && ChunkCopyBehaviour.maskMatch(i, 16)) {
                    return true;
                }
                if (z && ChunkCopyBehaviour.maskMatch(i, 32)) {
                    return true;
                }
                if (!ChunkCopyBehaviour.maskMatch(i, 256) || ChunkHelper.isUnknown(pngChunk) || z || pngChunk.f62id.equals("hIST") || pngChunk.f62id.equals("tIME")) {
                    return ChunkCopyBehaviour.maskMatch(i, 128) && ChunkHelper.isUnknown(pngChunk);
                }
                return true;
            }
        };
    }
}
