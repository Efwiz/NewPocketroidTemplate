package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngjException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkPLTE extends PngChunkSingle {
    public static final String ID = "PLTE";
    private int[] entries;
    private int nentries;

    public PngChunkPLTE(ImageInfo imageInfo) {
        super("PLTE", imageInfo);
        this.nentries = 0;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.NA;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        int[] iArr = new int[3];
        ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(this.nentries * 3, true);
        int i = 0;
        for (int i2 = 0; i2 < this.nentries; i2++) {
            getEntryRgb(i2, iArr);
            chunkRawCreateEmptyChunk.data[i] = (byte) iArr[0];
            int i3 = i + 2;
            chunkRawCreateEmptyChunk.data[i + 1] = (byte) iArr[1];
            i += 3;
            chunkRawCreateEmptyChunk.data[i3] = (byte) iArr[2];
        }
        return chunkRawCreateEmptyChunk;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        setNentries(chunkRaw.len / 3);
        int i = 0;
        for (int i2 = 0; i2 < this.nentries; i2++) {
            int i3 = chunkRaw.data[i] & 255;
            int i4 = i + 2;
            int i5 = chunkRaw.data[i + 1] & 255;
            i += 3;
            setEntry(i2, i3, i5, chunkRaw.data[i4] & 255);
        }
    }

    public void setNentries(int i) {
        this.nentries = i;
        if (i < 1 || i > 256) {
            throw new PngjException("invalid pallette - nentries=" + this.nentries);
        }
        int[] iArr = this.entries;
        if (iArr == null || iArr.length != i) {
            this.entries = new int[i];
        }
    }

    public int getNentries() {
        return this.nentries;
    }

    public void setEntry(int i, int i2, int i3, int i4) {
        this.entries[i] = (i2 << 16) | (i3 << 8) | i4;
    }

    public int getEntry(int i) {
        return this.entries[i];
    }

    public void getEntryRgb(int i, int[] iArr) {
        getEntryRgb(i, iArr, 0);
    }

    public void getEntryRgb(int i, int[] iArr, int i2) {
        int i3 = this.entries[i];
        iArr[i2] = (16711680 & i3) >> 16;
        iArr[i2 + 1] = (65280 & i3) >> 8;
        iArr[i2 + 2] = i3 & 255;
    }

    public int minBitDepth() {
        int i = this.nentries;
        if (i <= 2) {
            return 1;
        }
        if (i <= 4) {
            return 2;
        }
        return i <= 16 ? 4 : 8;
    }
}
