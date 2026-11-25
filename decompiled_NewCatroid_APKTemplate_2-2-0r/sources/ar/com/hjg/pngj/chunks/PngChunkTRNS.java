package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkTRNS extends PngChunkSingle {
    public static final String ID = "tRNS";
    private int blue;
    private int gray;
    private int green;
    private int[] paletteAlpha;
    private int red;

    public PngChunkTRNS(ImageInfo imageInfo) {
        super("tRNS", imageInfo);
        this.paletteAlpha = new int[0];
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
            ChunkRaw chunkRawCreateEmptyChunk2 = createEmptyChunk(this.paletteAlpha.length, true);
            for (int i = 0; i < chunkRawCreateEmptyChunk2.len; i++) {
                chunkRawCreateEmptyChunk2.data[i] = (byte) this.paletteAlpha[i];
            }
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
            return;
        }
        if (this.imgInfo.indexed) {
            int length = chunkRaw.data.length;
            this.paletteAlpha = new int[length];
            for (int i = 0; i < length; i++) {
                this.paletteAlpha[i] = chunkRaw.data[i] & 255;
            }
            return;
        }
        this.red = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 0);
        this.green = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 2);
        this.blue = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 4);
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

    public int getRGB888() {
        if (this.imgInfo.greyscale || this.imgInfo.indexed) {
            throw new PngjException("only rgb or rgba images support this");
        }
        return (this.red << 16) | (this.green << 8) | this.blue;
    }

    public void setGray(int i) {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only grayscale images support this");
        }
        this.gray = i;
    }

    public int getGray() {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only grayscale images support this");
        }
        return this.gray;
    }

    public void setPalletteAlpha(int[] iArr) {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed images support this");
        }
        this.paletteAlpha = iArr;
    }

    public void setIndexEntryAsTransparent(int i) {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed images support this");
        }
        this.paletteAlpha = new int[]{i + 1};
        for (int i2 = 0; i2 < i; i2++) {
            this.paletteAlpha[i2] = 255;
        }
        this.paletteAlpha[i] = 0;
    }

    public int[] getPalletteAlpha() {
        return this.paletteAlpha;
    }
}
