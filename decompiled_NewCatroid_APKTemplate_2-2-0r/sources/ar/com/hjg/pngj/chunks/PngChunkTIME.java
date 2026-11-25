package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkTIME extends PngChunkSingle {
    public static final String ID = "tIME";
    private int day;
    private int hour;
    private int min;
    private int mon;
    private int sec;
    private int year;

    public PngChunkTIME(ImageInfo imageInfo) {
        super("tIME", imageInfo);
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.NONE;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(7, true);
        PngHelperInternal.writeInt2tobytes(this.year, chunkRawCreateEmptyChunk.data, 0);
        chunkRawCreateEmptyChunk.data[2] = (byte) this.mon;
        chunkRawCreateEmptyChunk.data[3] = (byte) this.day;
        chunkRawCreateEmptyChunk.data[4] = (byte) this.hour;
        chunkRawCreateEmptyChunk.data[5] = (byte) this.min;
        chunkRawCreateEmptyChunk.data[6] = (byte) this.sec;
        return chunkRawCreateEmptyChunk;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        if (chunkRaw.len != 7) {
            throw new PngjException("bad chunk " + chunkRaw);
        }
        this.year = PngHelperInternal.readInt2fromBytes(chunkRaw.data, 0);
        this.mon = PngHelperInternal.readInt1fromByte(chunkRaw.data, 2);
        this.day = PngHelperInternal.readInt1fromByte(chunkRaw.data, 3);
        this.hour = PngHelperInternal.readInt1fromByte(chunkRaw.data, 4);
        this.min = PngHelperInternal.readInt1fromByte(chunkRaw.data, 5);
        this.sec = PngHelperInternal.readInt1fromByte(chunkRaw.data, 6);
    }

    public void setNow(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() - (((long) i) * 1000));
        this.year = calendar.get(1);
        this.mon = calendar.get(2) + 1;
        this.day = calendar.get(5);
        this.hour = calendar.get(11);
        this.min = calendar.get(12);
        this.sec = calendar.get(13);
    }

    public void setYMDHMS(int i, int i2, int i3, int i4, int i5, int i6) {
        this.year = i;
        this.mon = i2;
        this.day = i3;
        this.hour = i4;
        this.min = i5;
        this.sec = i6;
    }

    public int[] getYMDHMS() {
        return new int[]{this.year, this.mon, this.day, this.hour, this.min, this.sec};
    }

    public String getAsString() {
        return String.format("%04d/%02d/%02d %02d:%02d:%02d", Integer.valueOf(this.year), Integer.valueOf(this.mon), Integer.valueOf(this.day), Integer.valueOf(this.hour), Integer.valueOf(this.min), Integer.valueOf(this.sec));
    }
}
