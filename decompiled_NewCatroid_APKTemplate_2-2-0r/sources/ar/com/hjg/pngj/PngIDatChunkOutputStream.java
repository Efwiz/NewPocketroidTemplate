package ar.com.hjg.pngj;

import ar.com.hjg.pngj.chunks.ChunkHelper;
import ar.com.hjg.pngj.chunks.ChunkRaw;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class PngIDatChunkOutputStream extends ProgressiveOutputStream {
    private static final int SIZE_DEFAULT = 32768;
    private final OutputStream outputStream;
    private byte[] prefix;

    @Override // ar.com.hjg.pngj.ProgressiveOutputStream
    public /* bridge */ /* synthetic */ long getCountFlushed() {
        return super.getCountFlushed();
    }

    @Override // ar.com.hjg.pngj.ProgressiveOutputStream
    public /* bridge */ /* synthetic */ void setSize(int i) {
        super.setSize(i);
    }

    public PngIDatChunkOutputStream(OutputStream outputStream) {
        this(outputStream, 0);
    }

    public PngIDatChunkOutputStream(OutputStream outputStream, int i) {
        super(i <= 0 ? 32768 : i);
        this.prefix = null;
        this.outputStream = outputStream;
    }

    @Override // ar.com.hjg.pngj.ProgressiveOutputStream
    protected final void flushBuffer(byte[] bArr, int i) {
        byte[] bArr2 = this.prefix;
        int length = bArr2 == null ? i : bArr2.length + i;
        ChunkRaw chunkRaw = new ChunkRaw(length, ChunkHelper.b_IDAT, false);
        if (i == length) {
            chunkRaw.data = bArr;
        }
        chunkRaw.writeChunk(this.outputStream);
    }

    void setPrefix(byte[] bArr) {
        if (bArr == null) {
            this.prefix = null;
            return;
        }
        byte[] bArr2 = new byte[bArr.length];
        this.prefix = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
    }
}
