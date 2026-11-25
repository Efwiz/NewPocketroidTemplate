package ar.com.hjg.pngj;

import ar.com.hjg.pngj.chunks.ChunkRaw;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ChunkSeqSkipping extends ChunkSeqReader {
    private List<ChunkRaw> chunks;
    private boolean skip;

    @Override // ar.com.hjg.pngj.ChunkSeqReader
    protected boolean isIdatKind(String str) {
        return false;
    }

    protected void processChunkContent(ChunkRaw chunkRaw, int i, byte[] bArr, int i2, int i3) {
    }

    public ChunkSeqSkipping(boolean z) {
        super(true);
        this.chunks = new ArrayList();
        this.skip = z;
    }

    public ChunkSeqSkipping() {
        this(true);
    }

    @Override // ar.com.hjg.pngj.ChunkSeqReader
    protected ChunkReader createChunkReaderForNewChunk(String str, int i, long j, boolean z) {
        return new ChunkReader(i, str, j, z ? ChunkReader.ChunkReaderMode.SKIP : ChunkReader.ChunkReaderMode.PROCESS) { // from class: ar.com.hjg.pngj.ChunkSeqSkipping.1
            @Override // ar.com.hjg.pngj.ChunkReader
            protected void chunkDone() {
                ChunkSeqSkipping.this.postProcessChunk(this);
            }

            @Override // ar.com.hjg.pngj.ChunkReader
            protected void processData(int i2, byte[] bArr, int i3, int i4) {
                ChunkSeqSkipping.this.processChunkContent(getChunkRaw(), i2, bArr, i3, i4);
            }
        };
    }

    @Override // ar.com.hjg.pngj.ChunkSeqReader
    protected void postProcessChunk(ChunkReader chunkReader) {
        super.postProcessChunk(chunkReader);
        this.chunks.add(chunkReader.getChunkRaw());
    }

    @Override // ar.com.hjg.pngj.ChunkSeqReader
    protected boolean shouldSkipContent(int i, String str) {
        return this.skip;
    }

    public List<ChunkRaw> getChunks() {
        return this.chunks;
    }
}
