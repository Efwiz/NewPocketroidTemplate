package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkGAMA extends PngChunkSingle {
    public static final String ID = "gAMA";
    private double gamma;

    public PngChunkGAMA(ImageInfo imageInfo) {
        super("gAMA", imageInfo);
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(4, true);
        PngHelperInternal.writeInt4tobytes((int) ((this.gamma * 100000.0d) + 0.5d), chunkRawCreateEmptyChunk.data, 0);
        return chunkRawCreateEmptyChunk;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        if (chunkRaw.len != 4) {
            throw new PngjException("bad chunk " + chunkRaw);
        }
        this.gamma = ((double) PngHelperInternal.readInt4fromBytes(chunkRaw.data, 0)) / 100000.0d;
    }

    public double getGamma() {
        return this.gamma;
    }

    public void setGamma(double d) {
        this.gamma = d;
    }
}
