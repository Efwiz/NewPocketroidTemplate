package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkSTER extends PngChunkSingle {
    public static final String ID = "sTER";
    private byte mode;

    public PngChunkSTER(ImageInfo imageInfo) {
        super(ID, imageInfo);
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.BEFORE_IDAT;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(1, true);
        chunkRawCreateEmptyChunk.data[0] = this.mode;
        return chunkRawCreateEmptyChunk;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        if (chunkRaw.len != 1) {
            throw new PngjException("bad chunk length " + chunkRaw);
        }
        this.mode = chunkRaw.data[0];
    }

    public byte getMode() {
        return this.mode;
    }

    public void setMode(byte b) {
        this.mode = b;
    }
}
