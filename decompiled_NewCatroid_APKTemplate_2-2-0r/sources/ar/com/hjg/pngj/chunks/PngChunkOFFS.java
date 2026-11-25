package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkOFFS extends PngChunkSingle {
    public static final String ID = "oFFs";
    private long posX;
    private long posY;
    private int units;

    public PngChunkOFFS(ImageInfo imageInfo) {
        super(ID, imageInfo);
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.BEFORE_IDAT;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(9, true);
        PngHelperInternal.writeInt4tobytes((int) this.posX, chunkRawCreateEmptyChunk.data, 0);
        PngHelperInternal.writeInt4tobytes((int) this.posY, chunkRawCreateEmptyChunk.data, 4);
        chunkRawCreateEmptyChunk.data[8] = (byte) this.units;
        return chunkRawCreateEmptyChunk;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        if (chunkRaw.len != 9) {
            throw new PngjException("bad chunk length " + chunkRaw);
        }
        long int4fromBytes = PngHelperInternal.readInt4fromBytes(chunkRaw.data, 0);
        this.posX = int4fromBytes;
        if (int4fromBytes < 0) {
            this.posX = int4fromBytes + 4294967296L;
        }
        long int4fromBytes2 = PngHelperInternal.readInt4fromBytes(chunkRaw.data, 4);
        this.posY = int4fromBytes2;
        if (int4fromBytes2 < 0) {
            this.posY = int4fromBytes2 + 4294967296L;
        }
        this.units = PngHelperInternal.readInt1fromByte(chunkRaw.data, 8);
    }

    public int getUnits() {
        return this.units;
    }

    public void setUnits(int i) {
        this.units = i;
    }

    public long getPosX() {
        return this.posX;
    }

    public void setPosX(long j) {
        this.posX = j;
    }

    public long getPosY() {
        return this.posY;
    }

    public void setPosY(long j) {
        this.posY = j;
    }
}
