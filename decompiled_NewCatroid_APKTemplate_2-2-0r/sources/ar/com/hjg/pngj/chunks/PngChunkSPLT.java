package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class PngChunkSPLT extends PngChunkMultiple {
    public static final String ID = "sPLT";
    private String palName;
    private int[] palette;
    private int sampledepth;

    public PngChunkSPLT(ImageInfo imageInfo) {
        super("sPLT", imageInfo);
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public PngChunk.ChunkOrderingConstraint getOrderingConstraint() {
        return PngChunk.ChunkOrderingConstraint.BEFORE_IDAT;
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public ChunkRaw createRawChunk() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(ChunkHelper.toBytes(this.palName));
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write((byte) this.sampledepth);
            int nentries = getNentries();
            for (int i = 0; i < nentries; i++) {
                for (int i2 = 0; i2 < 4; i2++) {
                    if (this.sampledepth == 8) {
                        PngHelperInternal.writeByte(byteArrayOutputStream, (byte) this.palette[(i * 5) + i2]);
                    } else {
                        PngHelperInternal.writeInt2(byteArrayOutputStream, this.palette[(i * 5) + i2]);
                    }
                }
                PngHelperInternal.writeInt2(byteArrayOutputStream, this.palette[(i * 5) + 4]);
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            ChunkRaw chunkRawCreateEmptyChunk = createEmptyChunk(byteArray.length, false);
            chunkRawCreateEmptyChunk.data = byteArray;
            return chunkRawCreateEmptyChunk;
        } catch (IOException e) {
            throw new PngjException(e);
        }
    }

    @Override // ar.com.hjg.pngj.chunks.PngChunk
    public void parseFromRaw(ChunkRaw chunkRaw) {
        int int2fromBytes;
        int int2fromBytes2;
        int int2fromBytes3;
        int int2fromBytes4;
        int i;
        int i2 = 0;
        while (true) {
            if (i2 >= chunkRaw.data.length) {
                i2 = -1;
                break;
            } else if (chunkRaw.data[i2] == 0) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 <= 0 || i2 > chunkRaw.data.length - 2) {
            throw new PngjException("bad sPLT chunk: no separator found");
        }
        this.palName = ChunkHelper.toString(chunkRaw.data, 0, i2);
        this.sampledepth = PngHelperInternal.readInt1fromByte(chunkRaw.data, i2 + 1);
        int i3 = i2 + 2;
        int length = (chunkRaw.data.length - i3) / (this.sampledepth == 8 ? 6 : 10);
        this.palette = new int[length * 5];
        int i4 = i3;
        int i5 = 0;
        for (int i6 = 0; i6 < length; i6++) {
            if (this.sampledepth == 8) {
                int2fromBytes = PngHelperInternal.readInt1fromByte(chunkRaw.data, i4);
                int2fromBytes2 = PngHelperInternal.readInt1fromByte(chunkRaw.data, i4 + 1);
                int i7 = i4 + 3;
                int2fromBytes3 = PngHelperInternal.readInt1fromByte(chunkRaw.data, i4 + 2);
                i = i4 + 4;
                int2fromBytes4 = PngHelperInternal.readInt1fromByte(chunkRaw.data, i7);
            } else {
                int2fromBytes = PngHelperInternal.readInt2fromBytes(chunkRaw.data, i4);
                int2fromBytes2 = PngHelperInternal.readInt2fromBytes(chunkRaw.data, i4 + 2);
                int2fromBytes3 = PngHelperInternal.readInt2fromBytes(chunkRaw.data, i4 + 4);
                int2fromBytes4 = PngHelperInternal.readInt2fromBytes(chunkRaw.data, i4 + 6);
                i = i4 + 8;
            }
            int int2fromBytes5 = PngHelperInternal.readInt2fromBytes(chunkRaw.data, i);
            i4 = i + 2;
            int[] iArr = this.palette;
            iArr[i5] = int2fromBytes;
            iArr[i5 + 1] = int2fromBytes2;
            iArr[i5 + 2] = int2fromBytes3;
            int i8 = i5 + 4;
            iArr[i5 + 3] = int2fromBytes4;
            i5 += 5;
            iArr[i8] = int2fromBytes5;
        }
    }

    public int getNentries() {
        return this.palette.length / 5;
    }

    public String getPalName() {
        return this.palName;
    }

    public void setPalName(String str) {
        this.palName = str;
    }

    public int getSampledepth() {
        return this.sampledepth;
    }

    public void setSampledepth(int i) {
        this.sampledepth = i;
    }

    public int[] getPalette() {
        return this.palette;
    }

    public void setPalette(int[] iArr) {
        this.palette = iArr;
    }
}
