package ar.com.hjg.pngj.chunks;

import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjBadCrcException;
import ar.com.hjg.pngj.PngjException;
import ar.com.hjg.pngj.PngjOutputException;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.zip.CRC32;

/* JADX INFO: loaded from: classes.dex */
public class ChunkRaw {
    private CRC32 crcengine;
    public byte[] crcval;
    public byte[] data;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public final String f61id;
    public final byte[] idbytes;
    public final int len;
    private long offset;

    public ChunkRaw(int i, String str, boolean z) {
        this.data = null;
        this.offset = 0L;
        this.crcval = new byte[4];
        this.len = i;
        this.f61id = str;
        this.idbytes = ChunkHelper.toBytes(str);
        for (int i2 = 0; i2 < 4; i2++) {
            byte b = this.idbytes[i2];
            if (b < 65 || b > 122 || (b > 90 && b < 97)) {
                throw new PngjException("Bad id chunk: must be ascii letters " + str);
            }
        }
        if (z) {
            allocData();
        }
    }

    public ChunkRaw(int i, byte[] bArr, boolean z) {
        this(i, ChunkHelper.toString(bArr), z);
    }

    public void allocData() {
        byte[] bArr = this.data;
        if (bArr == null || bArr.length < this.len) {
            this.data = new byte[this.len];
        }
    }

    private void computeCrcForWriting() {
        CRC32 crc32 = new CRC32();
        this.crcengine = crc32;
        crc32.update(this.idbytes, 0, 4);
        int i = this.len;
        if (i > 0) {
            this.crcengine.update(this.data, 0, i);
        }
        PngHelperInternal.writeInt4tobytes((int) this.crcengine.getValue(), this.crcval, 0);
    }

    public void writeChunk(OutputStream outputStream) {
        writeChunkHeader(outputStream);
        int i = this.len;
        if (i > 0) {
            byte[] bArr = this.data;
            if (bArr == null) {
                throw new PngjOutputException("cannot write chunk, raw chunk data is null [" + this.f61id + "]");
            }
            PngHelperInternal.writeBytes(outputStream, bArr, 0, i);
        }
        computeCrcForWriting();
        writeChunkCrc(outputStream);
    }

    public void writeChunkHeader(OutputStream outputStream) {
        if (this.idbytes.length != 4) {
            throw new PngjOutputException("bad chunkid [" + this.f61id + "]");
        }
        PngHelperInternal.writeInt4(outputStream, this.len);
        PngHelperInternal.writeBytes(outputStream, this.idbytes);
    }

    public void writeChunkCrc(OutputStream outputStream) {
        PngHelperInternal.writeBytes(outputStream, this.crcval, 0, 4);
    }

    public void checkCrc() {
        int value = (int) this.crcengine.getValue();
        int int4fromBytes = PngHelperInternal.readInt4fromBytes(this.crcval, 0);
        if (value != int4fromBytes) {
            throw new PngjBadCrcException("chunk: " + toString() + " expected=" + int4fromBytes + " read=" + value);
        }
    }

    public void updateCrc(byte[] bArr, int i, int i2) {
        if (this.crcengine == null) {
            this.crcengine = new CRC32();
        }
        this.crcengine.update(bArr, i, i2);
    }

    ByteArrayInputStream getAsByteStream() {
        return new ByteArrayInputStream(this.data);
    }

    public long getOffset() {
        return this.offset;
    }

    public void setOffset(long j) {
        this.offset = j;
    }

    public String toString() {
        return "chunkid=" + ChunkHelper.toString(this.idbytes) + " len=" + this.len;
    }

    public int hashCode() {
        String str = this.f61id;
        int iHashCode = str == null ? 0 : str.hashCode();
        long j = this.offset;
        return ((iHashCode + 31) * 31) + ((int) (j ^ (j >>> 32)));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChunkRaw chunkRaw = (ChunkRaw) obj;
        String str = this.f61id;
        if (str == null) {
            if (chunkRaw.f61id != null) {
                return false;
            }
        } else if (!str.equals(chunkRaw.f61id)) {
            return false;
        }
        return this.offset == chunkRaw.offset;
    }
}
