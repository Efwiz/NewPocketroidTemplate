package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkIEND extends PngChunkSingle {
    public static final String ID = "IEND";

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
    }

    public PngChunkIEND(ImageInfo imageInfo) {
        super("IEND", imageInfo);
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.NA;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        return new ChunkRaw(0, ChunkHelper.b_IEND, false);
    }
}
