package ar.com.hjg.pngj;

import ar.com.hjg.pngj.chunks.ChunkFactory;
import ar.com.hjg.pngj.chunks.ChunkHelper;
import ar.com.hjg.pngj.chunks.ChunkLoadBehaviour;
import ar.com.hjg.pngj.chunks.ChunksList;
import ar.com.hjg.pngj.chunks.PngChunk;
import ar.com.hjg.pngj.chunks.PngChunkIHDR;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class ChunkSeqReaderPng extends ChunkSeqReader {
    protected final boolean callbackMode;
    protected Deinterlacer deinterlacer;
    protected ImageInfo imageInfo;
    protected int currentChunkGroup = -1;
    protected ChunksList chunksList = null;
    private long bytesChunksLoaded = 0;
    private boolean checkCrc = true;
    private boolean includeNonBufferedChunks = false;
    private Set<String> chunksToSkip = new HashSet();
    private long maxTotalBytesRead = 0;
    private long skipChunkMaxSize = 0;
    private long maxBytesMetadata = 0;
    private ChunkLoadBehaviour chunkLoadBehaviour = ChunkLoadBehaviour.LOAD_CHUNK_ALWAYS;
    private IChunkFactory chunkFactory = new ChunkFactory();

    protected void processEndPng() {
    }

    public ChunkSeqReaderPng(boolean z) {
        this.callbackMode = z;
    }

    private void updateAndCheckChunkGroup(String str) {
        if (str.equals("IHDR")) {
            if (this.currentChunkGroup < 0) {
                this.currentChunkGroup = 0;
                return;
            }
            throw new PngjInputException("unexpected chunk " + str);
        }
        if (str.equals("PLTE")) {
            int i = this.currentChunkGroup;
            if (i == 0 || i == 1) {
                this.currentChunkGroup = 2;
                return;
            }
            throw new PngjInputException("unexpected chunk " + str);
        }
        if (str.equals("IDAT")) {
            int i2 = this.currentChunkGroup;
            if (i2 >= 0 && i2 <= 4) {
                this.currentChunkGroup = 4;
                return;
            }
            throw new PngjInputException("unexpected chunk " + str);
        }
        if (str.equals("IEND")) {
            if (this.currentChunkGroup >= 4) {
                this.currentChunkGroup = 6;
                return;
            }
            throw new PngjInputException("unexpected chunk " + str);
        }
        int i3 = this.currentChunkGroup;
        if (i3 <= 1) {
            this.currentChunkGroup = 1;
        } else if (i3 <= 3) {
            this.currentChunkGroup = 3;
        } else {
            this.currentChunkGroup = 5;
        }
    }

    @Override // ar.com.hjg.pngj.ChunkSeqReader
    public boolean shouldSkipContent(int i, String str) {
        if (super.shouldSkipContent(i, str)) {
            return true;
        }
        if (ChunkHelper.isCritical(str)) {
            return false;
        }
        if (this.maxTotalBytesRead > 0 && ((long) i) + getBytesCount() > this.maxTotalBytesRead) {
            throw new PngjInputException("Maximum total bytes to read exceeeded: " + this.maxTotalBytesRead + " offset:" + getBytesCount() + " len=" + i);
        }
        if (this.chunksToSkip.contains(str)) {
            return true;
        }
        long j = this.skipChunkMaxSize;
        if (j > 0 && i > j) {
            return true;
        }
        long j2 = this.maxBytesMetadata;
        if (j2 > 0 && i > j2 - this.bytesChunksLoaded) {
            return true;
        }
        int i2 = AnonymousClass1.$SwitchMap$ar$com$hjg$pngj$chunks$ChunkLoadBehaviour[this.chunkLoadBehaviour.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                return true;
            }
        } else if (!ChunkHelper.isSafeToCopy(str)) {
            return true;
        }
        return false;
    }

    /* JADX INFO: renamed from: ar.com.hjg.pngj.ChunkSeqReaderPng$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$ar$com$hjg$pngj$chunks$ChunkLoadBehaviour;

        static {
            int[] iArr = new int[ChunkLoadBehaviour.values().length];
            $SwitchMap$ar$com$hjg$pngj$chunks$ChunkLoadBehaviour = iArr;
            try {
                iArr[ChunkLoadBehaviour.LOAD_CHUNK_IF_SAFE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$ar$com$hjg$pngj$chunks$ChunkLoadBehaviour[ChunkLoadBehaviour.LOAD_CHUNK_NEVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public long getBytesChunksLoaded() {
        return this.bytesChunksLoaded;
    }

    public int getCurrentChunkGroup() {
        return this.currentChunkGroup;
    }

    public void setChunksToSkip(String... strArr) {
        this.chunksToSkip.clear();
        for (String str : strArr) {
            this.chunksToSkip.add(str);
        }
    }

    public void addChunkToSkip(String str) {
        this.chunksToSkip.add(str);
    }

    public boolean firstChunksNotYetRead() {
        return getCurrentChunkGroup() < 4;
    }

    @Override // ar.com.hjg.pngj.ChunkSeqReader
    protected void postProcessChunk(ChunkReader chunkReader) {
        super.postProcessChunk(chunkReader);
        if (chunkReader.getChunkRaw().f61id.equals("IHDR")) {
            PngChunkIHDR pngChunkIHDR = new PngChunkIHDR(null);
            pngChunkIHDR.parseFromRaw(chunkReader.getChunkRaw());
            this.imageInfo = pngChunkIHDR.createImageInfo();
            if (pngChunkIHDR.isInterlaced()) {
                this.deinterlacer = new Deinterlacer(this.imageInfo);
            }
            this.chunksList = new ChunksList(this.imageInfo);
        }
        if (chunkReader.mode == ChunkReader.ChunkReaderMode.BUFFER || this.includeNonBufferedChunks) {
            this.chunksList.appendReadChunk(this.chunkFactory.createChunk(chunkReader.getChunkRaw(), getImageInfo()), this.currentChunkGroup);
        }
        if (isDone()) {
            processEndPng();
        }
    }

    @Override // ar.com.hjg.pngj.ChunkSeqReader
    protected DeflatedChunksSet createIdatSet(String str) {
        IdatSet idatSet = new IdatSet(str, this.imageInfo, this.deinterlacer);
        idatSet.setCallbackMode(this.callbackMode);
        return idatSet;
    }

    public IdatSet getIdatSet() {
        DeflatedChunksSet curReaderDeflatedSet = getCurReaderDeflatedSet();
        if (curReaderDeflatedSet instanceof IdatSet) {
            return (IdatSet) curReaderDeflatedSet;
        }
        return null;
    }

    @Override // ar.com.hjg.pngj.ChunkSeqReader
    protected boolean isIdatKind(String str) {
        return str.equals("IDAT");
    }

    @Override // ar.com.hjg.pngj.ChunkSeqReader, ar.com.hjg.pngj.IBytesConsumer
    public int consume(byte[] bArr, int i, int i2) {
        return super.consume(bArr, i, i2);
    }

    public void setChunkFactory(IChunkFactory iChunkFactory) {
        this.chunkFactory = iChunkFactory;
    }

    public ImageInfo getImageInfo() {
        return this.imageInfo;
    }

    public boolean isInterlaced() {
        return this.deinterlacer != null;
    }

    public Deinterlacer getDeinterlacer() {
        return this.deinterlacer;
    }

    @Override // ar.com.hjg.pngj.ChunkSeqReader
    protected void startNewChunk(int i, String str, long j) {
        updateAndCheckChunkGroup(str);
        super.startNewChunk(i, str, j);
    }

    @Override // ar.com.hjg.pngj.ChunkSeqReader
    public void close() {
        if (this.currentChunkGroup != 6) {
            this.currentChunkGroup = 6;
        }
        super.close();
    }

    public List<PngChunk> getChunks() {
        return this.chunksList.getChunks();
    }

    public void setMaxTotalBytesRead(long j) {
        this.maxTotalBytesRead = j;
    }

    public long getSkipChunkMaxSize() {
        return this.skipChunkMaxSize;
    }

    public void setSkipChunkMaxSize(long j) {
        this.skipChunkMaxSize = j;
    }

    public long getMaxBytesMetadata() {
        return this.maxBytesMetadata;
    }

    public void setMaxBytesMetadata(long j) {
        this.maxBytesMetadata = j;
    }

    public long getMaxTotalBytesRead() {
        return this.maxTotalBytesRead;
    }

    @Override // ar.com.hjg.pngj.ChunkSeqReader
    protected boolean shouldCheckCrc(int i, String str) {
        return this.checkCrc;
    }

    public boolean isCheckCrc() {
        return this.checkCrc;
    }

    public void setCheckCrc(boolean z) {
        this.checkCrc = z;
    }

    public boolean isCallbackMode() {
        return this.callbackMode;
    }

    public Set<String> getChunksToSkip() {
        return this.chunksToSkip;
    }

    public void setChunkLoadBehaviour(ChunkLoadBehaviour chunkLoadBehaviour) {
        this.chunkLoadBehaviour = chunkLoadBehaviour;
    }

    public void setIncludeNonBufferedChunks(boolean z) {
        this.includeNonBufferedChunks = z;
    }
}
