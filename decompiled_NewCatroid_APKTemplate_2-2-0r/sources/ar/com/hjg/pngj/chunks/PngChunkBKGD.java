package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkBKGD extends PngChunkSingle {
    public static final String ID = "bKGD";
    private int blue;
    private int gray;
    private int green;
    private int paletteIndex;
    private int red;

    public PngChunkBKGD(ImageInfo imageInfo) {
        super("bKGD", imageInfo);
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        if (this.imgInfo.greyscale) {
            ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(2, true);
            PngHelperInternal.writeInt2tobytes(this.gray, chunkRawCreateEmptyChunk.data, 0);
            return chunkRawCreateEmptyChunk;
        }
        if (this.imgInfo.indexed) {
            ChunkRaw chunkRawCreateEmptyChunk2 = createEmptyChunk(1, true);
            chunkRawCreateEmptyChunk2.data[0] = (byte) this.paletteIndex;
            return chunkRawCreateEmptyChunk2;
        }
        ChunkRaw chunkRawCreateEmptyChunk3 = createEmptyChunk(6, true);
        PngHelperInternal.writeInt2tobytes(this.red, chunkRawCreateEmptyChunk3.data, 0);
        PngHelperInternal.writeInt2tobytes(this.green, chunkRawCreateEmptyChunk3.data, 0);
        PngHelperInternal.writeInt2tobytes(this.blue, chunkRawCreateEmptyChunk3.data, 0);
        return chunkRawCreateEmptyChunk3;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        if (this.imgInfo.greyscale) {
            this.gray = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 0);
        } else {
            if (this.imgInfo.indexed) {
                this.paletteIndex = chunkRaw.data[0] & 255;
                return;
            }
            this.red = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 0);
            this.green = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 2);
            this.blue = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 4);
        }
    }

    public void setGray(int i) {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only gray images support this");
        }
        this.gray = i;
    }

    public int getGray() {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only gray images support this");
        }
        return this.gray;
    }

    public void setPaletteIndex(int i) {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed (pallete) images support this");
        }
        this.paletteIndex = i;
    }

    public int getPaletteIndex() {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed (pallete) images support this");
        }
        return this.paletteIndex;
    }

    public void setRGB(int i, int i2, int i3) {
        if (this.imgInfo.greyscale || this.imgInfo.indexed) {
            throw new PngjException("only rgb or rgba images support this");
        }
        this.red = i;
        this.green = i2;
        this.blue = i3;
    }

    public int[] getRGB() {
        if (this.imgInfo.greyscale || this.imgInfo.indexed) {
            throw new PngjException("only rgb or rgba images support this");
        }
        return new int[]{this.red, this.green, this.blue};
    }
}
