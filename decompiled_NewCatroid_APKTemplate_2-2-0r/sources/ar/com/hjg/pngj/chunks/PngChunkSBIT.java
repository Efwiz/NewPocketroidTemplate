package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkSBIT extends PngChunkSingle {
    public static final String ID = "sBIT";
    private int alphasb;
    private int bluesb;
    private int graysb;
    private int greensb;
    private int redsb;

    public PngChunkSBIT(ImageInfo imageInfo) {
        super("sBIT", imageInfo);
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.BEFORE_PLTE_AND_IDAT;
    }

    private int getCLen() {
        int i = this.imgInfo.greyscale ? 1 : 3;
        return this.imgInfo.alpha ? i + 1 : i;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        if (chunkRaw.len != getCLen()) {
            throw new PngjException("bad chunk length " + chunkRaw);
        }
        if (this.imgInfo.greyscale) {
            this.graysb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 0);
            if (this.imgInfo.alpha) {
                this.alphasb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 1);
                return;
            }
            return;
        }
        this.redsb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 0);
        this.greensb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 1);
        this.bluesb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 2);
        if (this.imgInfo.alpha) {
            this.alphasb = PngHelperInternal.readInt1fromByte(chunkRaw.data, 3);
        }
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(getCLen(), true);
        if (this.imgInfo.greyscale) {
            chunkRawCreateEmptyChunk.data[0] = (byte) this.graysb;
            if (this.imgInfo.alpha) {
                chunkRawCreateEmptyChunk.data[1] = (byte) this.alphasb;
            }
        } else {
            chunkRawCreateEmptyChunk.data[0] = (byte) this.redsb;
            chunkRawCreateEmptyChunk.data[1] = (byte) this.greensb;
            chunkRawCreateEmptyChunk.data[2] = (byte) this.bluesb;
            if (this.imgInfo.alpha) {
                chunkRawCreateEmptyChunk.data[3] = (byte) this.alphasb;
            }
        }
        return chunkRawCreateEmptyChunk;
    }

    public void setGraysb(int i) {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only greyscale images support this");
        }
        this.graysb = i;
    }

    public int getGraysb() {
        if (!this.imgInfo.greyscale) {
            throw new PngjException("only greyscale images support this");
        }
        return this.graysb;
    }

    public void setAlphasb(int i) {
        if (!this.imgInfo.alpha) {
            throw new PngjException("only images with alpha support this");
        }
        this.alphasb = i;
    }

    public int getAlphasb() {
        if (!this.imgInfo.alpha) {
            throw new PngjException("only images with alpha support this");
        }
        return this.alphasb;
    }

    public void setRGB(int i, int i2, int i3) {
        if (this.imgInfo.greyscale || this.imgInfo.indexed) {
            throw new PngjException("only rgb or rgba images support this");
        }
        this.redsb = i;
        this.greensb = i2;
        this.bluesb = i3;
    }

    public int[] getRGB() {
        if (this.imgInfo.greyscale || this.imgInfo.indexed) {
            throw new PngjException("only rgb or rgba images support this");
        }
        return new int[]{this.redsb, this.greensb, this.bluesb};
    }
}
