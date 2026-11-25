package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkHIST extends PngChunkSingle {
    public static final String ID = "hIST";
    private int[] hist;

    public PngChunkHIST(ImageInfo imageInfo) {
        super("hIST", imageInfo);
        this.hist = new int[0];
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.AFTER_PLTE_BEFORE_IDAT;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed images accept a HIST chunk");
        }
        this.hist = new int[chunkRaw.data.length / 2];
        int i = 0;
        while (true) {
            int[] iArr = this.hist;
            if (i >= iArr.length) {
                return;
            }
            iArr[i] = PngHelperInternal.readInt2fromBytes(chunkRaw.data, i * 2);
            i++;
        }
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        if (!this.imgInfo.indexed) {
            throw new PngjException("only indexed images accept a HIST chunk");
        }
        ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(this.hist.length * 2, true);
        int i = 0;
        while (true) {
            int[] iArr = this.hist;
            if (i >= iArr.length) {
                return chunkRawCreateEmptyChunk;
            }
            PngHelperInternal.writeInt2tobytes(iArr[i], chunkRawCreateEmptyChunk.data, i * 2);
            i++;
        }
    }

    public int[] getHist() {
        return this.hist;
    }

    public void setHist(int[] iArr) {
        this.hist = iArr;
    }
}
