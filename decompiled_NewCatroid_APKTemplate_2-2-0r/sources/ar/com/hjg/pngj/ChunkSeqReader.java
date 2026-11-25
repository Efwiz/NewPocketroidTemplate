package ar.com.hjg.pngj;

import ar.com.hjg.pngj.chunks.ChunkHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class ChunkSeqReader implements IBytesConsumer {
    protected static final int SIGNATURE_LEN = 8;
    private byte[] buf0;
    private int buf0len;
    private long bytesCount;
    private int chunkCount;
    private ChunkReader curChunkReader;
    private DeflatedChunksSet curReaderDeflatedSet;
    private boolean done;
    private long idatBytes;
    private boolean signatureDone;
    protected final boolean withSignature;

    protected boolean isIdatKind(String str) {
        return false;
    }

    protected boolean shouldCheckCrc(int i, String str) {
        return true;
    }

    protected boolean shouldSkipContent(int i, String str) {
        return false;
    }

    public ChunkSeqReader() {
        this(true);
    }

    public ChunkSeqReader(boolean z) {
        this.buf0 = new byte[8];
        this.buf0len = 0;
        this.done = false;
        this.chunkCount = 0;
        this.bytesCount = 0L;
        this.withSignature = z;
        this.signatureDone = !z;
    }

    @Override // ar.com.hjg.pngj.IBytesConsumer
    public int consume(byte[] bArr, int i, int i2) {
        if (this.done) {
            return -1;
        }
        if (i2 == 0) {
            return 0;
        }
        if (i2 < 0) {
            throw new PngjInputException("Bad len: " + i2);
        }
        if (this.signatureDone) {
            ChunkReader chunkReader = this.curChunkReader;
            if (chunkReader == null || chunkReader.isDone()) {
                int i3 = this.buf0len;
                int i4 = 8 - i3;
                if (i4 <= i2) {
                    i2 = i4;
                }
                System.arraycopy(bArr, i, this.buf0, i3, i2);
                int i5 = this.buf0len + i2;
                this.buf0len = i5;
                this.bytesCount += (long) i2;
                if (i5 == 8) {
                    this.chunkCount++;
                    startNewChunk(PngHelperInternal.readInt4fromBytes(this.buf0, 0), ChunkHelper.toString(this.buf0, 4, 4), this.bytesCount - 8);
                    this.buf0len = 0;
                }
            } else {
                int iFeedBytes = this.curChunkReader.feedBytes(bArr, i, i2);
                this.bytesCount += (long) iFeedBytes;
                return iFeedBytes;
            }
        } else {
            int i6 = this.buf0len;
            int i7 = 8 - i6;
            if (i7 <= i2) {
                i2 = i7;
            }
            System.arraycopy(bArr, i, this.buf0, i6, i2);
            int i8 = this.buf0len + i2;
            this.buf0len = i8;
            if (i8 == 8) {
                checkSignature(this.buf0);
                this.buf0len = 0;
                this.signatureDone = true;
            }
            this.bytesCount += (long) i2;
        }
        return i2;
    }

    public boolean feedAll(byte[] bArr, int i, int i2) {
        while (i2 > 0) {
            int iConsume = consume(bArr, i, i2);
            if (iConsume < 1) {
                return false;
            }
            i2 -= iConsume;
            i += iConsume;
        }
        return true;
    }

    protected void startNewChunk(int i, String str, long j) {
        if (str.equals("IDAT")) {
            this.idatBytes += (long) i;
        }
        boolean zShouldCheckCrc = shouldCheckCrc(i, str);
        boolean zShouldSkipContent = shouldSkipContent(i, str);
        boolean zIsIdatKind = isIdatKind(str);
        DeflatedChunksSet deflatedChunksSet = this.curReaderDeflatedSet;
        boolean zAckNextChunkId = deflatedChunksSet != null ? deflatedChunksSet.ackNextChunkId(str) : false;
        if (zIsIdatKind && !zShouldSkipContent) {
            if (!zAckNextChunkId) {
                if (this.curReaderDeflatedSet != null) {
                    throw new PngjInputException("too many IDAT (or idatlike) chunks");
                }
                this.curReaderDeflatedSet = createIdatSet(str);
            }
            this.curChunkReader = new DeflatedChunkReader(i, str, zShouldCheckCrc, j, this.curReaderDeflatedSet) { // from class: ar.com.hjg.pngj.ChunkSeqReader.1
                @Override // ar.com.hjg.pngj.DeflatedChunkReader, ar.com.hjg.pngj.ChunkReader
                protected void chunkDone() {
                    ChunkSeqReader.this.postProcessChunk(this);
                }
            };
            return;
        }
        ChunkReader chunkReaderCreateChunkReaderForNewChunk = createChunkReaderForNewChunk(str, i, j, zShouldSkipContent);
        this.curChunkReader = chunkReaderCreateChunkReaderForNewChunk;
        if (zShouldCheckCrc) {
            return;
        }
        chunkReaderCreateChunkReaderForNewChunk.setCrcCheck(false);
    }

    protected ChunkReader createChunkReaderForNewChunk(String str, int i, long j, boolean z) {
        return new ChunkReader(i, str, j, z ? ChunkReader.ChunkReaderMode.SKIP : ChunkReader.ChunkReaderMode.BUFFER) { // from class: ar.com.hjg.pngj.ChunkSeqReader.2
            @Override // ar.com.hjg.pngj.ChunkReader
            protected void chunkDone() {
                ChunkSeqReader.this.postProcessChunk(this);
            }

            @Override // ar.com.hjg.pngj.ChunkReader
            protected void processData(int i2, byte[] bArr, int i3, int i4) {
                throw new PngjExceptionInternal("should never happen");
            }
        };
    }

    protected void postProcessChunk(ChunkReader chunkReader) {
        String strFirstChunkId;
        if (this.chunkCount == 1 && (strFirstChunkId = firstChunkId()) != null && !strFirstChunkId.equals(chunkReader.getChunkRaw().f61id)) {
            throw new PngjInputException("Bad first chunk: " + chunkReader.getChunkRaw().f61id + " expected: " + firstChunkId());
        }
        if (chunkReader.getChunkRaw().f61id.equals(endChunkId())) {
            this.done = true;
        }
    }

    protected DeflatedChunksSet createIdatSet(String str) {
        return new DeflatedChunksSet(str, 1024, 1024);
    }

    protected void checkSignature(byte[] bArr) {
        if (!Arrays.equals(bArr, PngHelperInternal.getPngIdSignature())) {
            throw new PngjInputException("Bad PNG signature");
        }
    }

    public boolean isSignatureDone() {
        return this.signatureDone;
    }

    public boolean isDone() {
        return this.done;
    }

    public long getBytesCount() {
        return this.bytesCount;
    }

    public int getChunkCount() {
        return this.chunkCount;
    }

    public ChunkReader getCurChunkReader() {
        return this.curChunkReader;
    }

    public DeflatedChunksSet getCurReaderDeflatedSet() {
        return this.curReaderDeflatedSet;
    }

    public void close() {
        DeflatedChunksSet deflatedChunksSet = this.curReaderDeflatedSet;
        if (deflatedChunksSet != null) {
            deflatedChunksSet.close();
        }
        this.done = true;
    }

    public boolean isAtChunkBoundary() {
        ChunkReader chunkReader;
        long j = this.bytesCount;
        return j == 0 || j == 8 || this.done || (chunkReader = this.curChunkReader) == null || chunkReader.isDone();
    }

    protected String firstChunkId() {
        return "IHDR";
    }

    public long getIdatBytes() {
        return this.idatBytes;
    }

    protected String endChunkId() {
        return "IEND";
    }

    public void feedFromFile(File file) {
        try {
            feedFromInputStream(new FileInputStream(file), true);
        } catch (FileNotFoundException e) {
            throw new PngjInputException(e.getMessage());
        }
    }

    public void feedFromInputStream(InputStream inputStream, boolean z) {
        BufferedStreamFeeder bufferedStreamFeeder = new BufferedStreamFeeder(inputStream);
        bufferedStreamFeeder.setCloseStream(z);
        while (bufferedStreamFeeder.hasMoreToFeed()) {
            try {
                bufferedStreamFeeder.feed(this);
            } catch (Throwable th) {
                close();
                bufferedStreamFeeder.close();
                throw th;
            }
        }
        close();
        bufferedStreamFeeder.close();
    }

    public void feedFromInputStream(InputStream inputStream) {
        feedFromInputStream(inputStream, true);
    }
}
