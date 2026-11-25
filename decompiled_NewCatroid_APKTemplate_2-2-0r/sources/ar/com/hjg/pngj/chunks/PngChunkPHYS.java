package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkPHYS extends PngChunkSingle {
    public static final String ID = "pHYs";
    private long pixelsxUnitX;
    private long pixelsxUnitY;
    private int units;

    public PngChunkPHYS(ImageInfo imageInfo) {
        super("pHYs", imageInfo);
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.BEFORE_IDAT;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(9, true);
        PngHelperInternal.writeInt4tobytes((int) this.pixelsxUnitX, chunkRawCreateEmptyChunk.data, 0);
        PngHelperInternal.writeInt4tobytes((int) this.pixelsxUnitY, chunkRawCreateEmptyChunk.data, 4);
        chunkRawCreateEmptyChunk.data[8] = (byte) this.units;
        return chunkRawCreateEmptyChunk;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        if (chunkRaw.len != 9) {
            throw new PngjException("bad chunk length " + chunkRaw);
        }
        long int4fromBytes = PngHelperInternal.readInt4fromBytes(chunkRaw.data, 0);
        this.pixelsxUnitX = int4fromBytes;
        if (int4fromBytes < 0) {
            this.pixelsxUnitX = int4fromBytes + 4294967296L;
        }
        long int4fromBytes2 = PngHelperInternal.readInt4fromBytes(chunkRaw.data, 4);
        this.pixelsxUnitY = int4fromBytes2;
        if (int4fromBytes2 < 0) {
            this.pixelsxUnitY = int4fromBytes2 + 4294967296L;
        }
        this.units = PngHelperInternal.readInt1fromByte(chunkRaw.data, 8);
    }

    public long getPixelsxUnitX() {
        return this.pixelsxUnitX;
    }

    public void setPixelsxUnitX(long j) {
        this.pixelsxUnitX = j;
    }

    public long getPixelsxUnitY() {
        return this.pixelsxUnitY;
    }

    public void setPixelsxUnitY(long j) {
        this.pixelsxUnitY = j;
    }

    public int getUnits() {
        return this.units;
    }

    public void setUnits(int i) {
        this.units = i;
    }

    public double getAsDpi() {
        if (this.units != 1) {
            return -1.0d;
        }
        long j = this.pixelsxUnitX;
        if (j != this.pixelsxUnitY) {
            return -1.0d;
        }
        return j * 0.0254d;
    }

    public double[] getAsDpi2() {
        if (this.units != 1) {
            return new double[]{-1.0d, -1.0d};
        }
        return new double[]{this.pixelsxUnitX * 0.0254d, this.pixelsxUnitY * 0.0254d};
    }

    public void setAsDpi(double d) {
        this.units = 1;
        long j = (long) ((d / 0.0254d) + 0.5d);
        this.pixelsxUnitX = j;
        this.pixelsxUnitY = j;
    }

    public void setAsDpi2(double d, double d2) {
        this.units = 1;
        this.pixelsxUnitX = (long) ((d / 0.0254d) + 0.5d);
        this.pixelsxUnitY = (long) ((d2 / 0.0254d) + 0.5d);
    }
}
