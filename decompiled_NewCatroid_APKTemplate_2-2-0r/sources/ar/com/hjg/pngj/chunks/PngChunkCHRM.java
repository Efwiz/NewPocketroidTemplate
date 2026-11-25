package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkCHRM extends PngChunkSingle {
    public static final String ID = "cHRM";
    private double bluex;
    private double bluey;
    private double greenx;
    private double greeny;
    private double redx;
    private double redy;
    private double whitex;
    private double whitey;

    public PngChunkCHRM(ImageInfo imageInfo) {
        super("cHRM", imageInfo);
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(32, true);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.whitex), chunkRawCreateEmptyChunk.data, 0);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.whitey), chunkRawCreateEmptyChunk.data, 4);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.redx), chunkRawCreateEmptyChunk.data, 8);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.redy), chunkRawCreateEmptyChunk.data, 12);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.greenx), chunkRawCreateEmptyChunk.data, 16);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.greeny), chunkRawCreateEmptyChunk.data, 20);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.bluex), chunkRawCreateEmptyChunk.data, 24);
        PngHelperInternal.writeInt4tobytes(PngHelperInternal.doubleToInt100000(this.bluey), chunkRawCreateEmptyChunk.data, 28);
        return chunkRawCreateEmptyChunk;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        if (chunkRaw.len != 32) {
            throw new PngjException("bad chunk " + chunkRaw);
        }
        this.whitex = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 0));
        this.whitey = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 4));
        this.redx = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 8));
        this.redy = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 12));
        this.greenx = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 16));
        this.greeny = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 20));
        this.bluex = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 24));
        this.bluey = PngHelperInternal.intToDouble100000(PngHelperInternal.readInt4fromBytes(chunkRaw.data, 28));
    }

    public void setChromaticities(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        this.whitex = d;
        this.redx = d3;
        this.greenx = d5;
        this.bluex = d7;
        this.whitey = d2;
        this.redy = d4;
        this.greeny = d6;
        this.bluey = d8;
    }

    public double[] getChromaticities() {
        return new double[]{this.whitex, this.whitey, this.redx, this.redy, this.greenx, this.greeny, this.bluex, this.bluey};
    }
}
