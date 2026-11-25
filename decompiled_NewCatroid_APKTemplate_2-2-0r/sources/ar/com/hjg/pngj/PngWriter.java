package ar.com.hjg.pngj;

import ar.com.hjg.pngj.chunks.ChunkCopyBehaviour;
import ar.com.hjg.pngj.chunks.ChunkPredicate;
import ar.com.hjg.pngj.chunks.ChunksList;
import ar.com.hjg.pngj.chunks.ChunksListForWrite;
import ar.com.hjg.pngj.chunks.PngChunk;
import ar.com.hjg.pngj.chunks.PngChunkIEND;
import ar.com.hjg.pngj.chunks.PngChunkIHDR;
import ar.com.hjg.pngj.chunks.PngMetadata;
import ar.com.hjg.pngj.pixels.PixelsWriter;
import ar.com.hjg.pngj.pixels.PixelsWriterDefault;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PngWriter {
    private final ChunksListForWrite chunksList;
    private ChunksList copyFromList;
    private ChunkPredicate copyFromPredicate;
    protected int currentChunkGroup;
    private int currentpass;
    private PngIDatChunkOutputStream datStream;
    protected StringBuilder debuginfo;
    private int idatMaxSize;
    public final ImageInfo imgInfo;
    private final PngMetadata metadata;
    private final OutputStream os;
    private int passes;
    protected PixelsWriter pixelsWriter;
    protected int rowNum;
    private boolean shouldCloseStream;

    public PngWriter(File file, ImageInfo imageInfo, boolean z) {
        this(PngHelperInternal.ostreamFromFile(file, z), imageInfo);
        setShouldCloseStream(true);
    }

    public PngWriter(File file, ImageInfo imageInfo) {
        this(file, imageInfo, true);
    }

    public PngWriter(OutputStream outputStream, ImageInfo imageInfo) {
        this.rowNum = -1;
        this.currentChunkGroup = -1;
        this.passes = 1;
        this.currentpass = 0;
        this.shouldCloseStream = true;
        this.idatMaxSize = 0;
        this.copyFromPredicate = null;
        this.copyFromList = null;
        this.debuginfo = new StringBuilder();
        this.os = outputStream;
        this.imgInfo = imageInfo;
        ChunksListForWrite chunksListForWrite = new ChunksListForWrite(imageInfo);
        this.chunksList = chunksListForWrite;
        this.metadata = new PngMetadata(chunksListForWrite);
        this.pixelsWriter = createPixelsWriter(imageInfo);
        setCompLevel(9);
    }

    private void initIdat() {
        PngIDatChunkOutputStream pngIDatChunkOutputStream = new PngIDatChunkOutputStream(this.os, this.idatMaxSize);
        this.datStream = pngIDatChunkOutputStream;
        this.pixelsWriter.setOs(pngIDatChunkOutputStream);
        writeSignatureAndIHDR();
        writeFirstChunks();
    }

    private void writeEndChunk() {
        PngChunkIEND pngChunkIEND = new PngChunkIEND(this.imgInfo);
        pngChunkIEND.createRawChunk().writeChunk(this.os);
        this.chunksList.getChunks().add(pngChunkIEND);
    }

    private void writeFirstChunks() {
        if (this.currentChunkGroup >= 4) {
            return;
        }
        this.currentChunkGroup = 1;
        queueChunksFromOther();
        this.chunksList.writeChunks(this.os, this.currentChunkGroup);
        this.currentChunkGroup = 2;
        int iWriteChunks = this.chunksList.writeChunks(this.os, 2);
        if (iWriteChunks > 0 && this.imgInfo.greyscale) {
            throw new PngjOutputException("cannot write palette for this format");
        }
        if (iWriteChunks == 0 && this.imgInfo.indexed) {
            throw new PngjOutputException("missing palette");
        }
        this.currentChunkGroup = 3;
        this.chunksList.writeChunks(this.os, 3);
        this.currentChunkGroup = 4;
    }

    private void writeLastChunks() {
        queueChunksFromOther();
        this.currentChunkGroup = 5;
        this.chunksList.writeChunks(this.os, 5);
        List<PngChunk> queuedChunks = this.chunksList.getQueuedChunks();
        if (!queuedChunks.isEmpty()) {
            throw new PngjOutputException(queuedChunks.size() + " chunks were not written! Eg: " + queuedChunks.get(0).toString());
        }
        this.currentChunkGroup = 6;
    }

    private void writeSignatureAndIHDR() {
        this.currentChunkGroup = 0;
        PngHelperInternal.writeBytes(this.os, PngHelperInternal.getPngIdSignature());
        PngChunkIHDR pngChunkIHDR = new PngChunkIHDR(this.imgInfo);
        pngChunkIHDR.setCols(this.imgInfo.cols);
        pngChunkIHDR.setRows(this.imgInfo.rows);
        pngChunkIHDR.setBitspc(this.imgInfo.bitDepth);
        int i = this.imgInfo.alpha ? 4 : 0;
        if (this.imgInfo.indexed) {
            i++;
        }
        if (!this.imgInfo.greyscale) {
            i += 2;
        }
        pngChunkIHDR.setColormodel(i);
        pngChunkIHDR.setCompmeth(0);
        pngChunkIHDR.setFilmeth(0);
        pngChunkIHDR.setInterlaced(0);
        pngChunkIHDR.createRawChunk().writeChunk(this.os);
        this.chunksList.getChunks().add(pngChunkIHDR);
    }

    private void queueChunksFromOther() {
        int chunkGroup;
        ChunksList chunksList = this.copyFromList;
        if (chunksList == null || this.copyFromPredicate == null) {
            return;
        }
        boolean z = this.currentChunkGroup >= 4;
        for (PngChunk pngChunk : chunksList.getChunks()) {
            if (pngChunk.getRaw().data != null && ((chunkGroup = pngChunk.getChunkGroup()) > 4 || !z)) {
                if (chunkGroup < 4 || z) {
                    if (!pngChunk.crit || pngChunk.f62id.equals("PLTE")) {
                        if (this.copyFromPredicate.match(pngChunk) && this.chunksList.getEquivalent(pngChunk).isEmpty() && this.chunksList.getQueuedEquivalent(pngChunk).isEmpty()) {
                            this.chunksList.queue(pngChunk);
                        }
                    }
                }
            }
        }
    }

    public void queueChunk(PngChunk pngChunk) {
        Iterator<PngChunk> it = this.chunksList.getQueuedEquivalent(pngChunk).iterator();
        while (it.hasNext()) {
            getChunksList().removeChunk(it.next());
        }
        this.chunksList.queue(pngChunk);
    }

    public void copyChunksFrom(ChunksList chunksList, int i) {
        copyChunksFrom(chunksList, ChunkCopyBehaviour.createPredicate(i, this.imgInfo));
    }

    public void copyChunksFrom(ChunksList chunksList) {
        copyChunksFrom(chunksList, 8);
    }

    public void copyChunksFrom(ChunksList chunksList, ChunkPredicate chunkPredicate) {
        if (this.copyFromList != null && chunksList != null) {
            PngHelperInternal.LOGGER.warning("copyChunksFrom should only be called once");
        }
        if (chunkPredicate == null) {
            throw new PngjOutputException("copyChunksFrom requires a predicate");
        }
        this.copyFromList = chunksList;
        this.copyFromPredicate = chunkPredicate;
    }

    public double computeCompressionRatio() {
        if (this.currentChunkGroup < 6) {
            throw new PngjOutputException("must be called after end()");
        }
        return this.datStream.getCountFlushed() / ((double) ((this.imgInfo.bytesPerRow + 1) * this.imgInfo.rows));
    }

    public void end() {
        try {
            if (this.rowNum != this.imgInfo.rows - 1) {
                throw new PngjOutputException("all rows have not been written");
            }
            try {
                this.datStream.flush();
                writeLastChunks();
                writeEndChunk();
                close();
            } catch (IOException e) {
                throw new PngjOutputException(e);
            }
        } catch (Throwable th) {
            close();
            throw th;
        }
    }

    public void close() {
        OutputStream outputStream;
        try {
            PngIDatChunkOutputStream pngIDatChunkOutputStream = this.datStream;
            if (pngIDatChunkOutputStream != null) {
                pngIDatChunkOutputStream.close();
            }
        } catch (Exception unused) {
        }
        PixelsWriter pixelsWriter = this.pixelsWriter;
        if (pixelsWriter != null) {
            pixelsWriter.close();
        }
        if (!this.shouldCloseStream || (outputStream = this.os) == null) {
            return;
        }
        try {
            outputStream.close();
        } catch (Exception e) {
            PngHelperInternal.LOGGER.warning("Error closing writer " + e.toString());
        }
    }

    public ChunksListForWrite getChunksList() {
        return this.chunksList;
    }

    public PngMetadata getMetadata() {
        return this.metadata;
    }

    public void setFilterType(FilterType filterType) {
        this.pixelsWriter.setFilterType(filterType);
    }

    public void setCompLevel(int i) {
        this.pixelsWriter.setDeflaterCompLevel(Integer.valueOf(i));
    }

    public void setFilterPreserve(boolean z) {
        if (z) {
            this.pixelsWriter.setFilterType(FilterType.FILTER_PRESERVE);
        } else if (this.pixelsWriter.getFilterType() == null) {
            this.pixelsWriter.setFilterType(FilterType.FILTER_DEFAULT);
        }
    }

    public void setIdatMaxSize(int i) {
        this.idatMaxSize = i;
    }

    public void setShouldCloseStream(boolean z) {
        this.shouldCloseStream = z;
    }

    public void writeRow(IImageLine iImageLine) {
        writeRow(iImageLine, this.rowNum + 1);
    }

    public void writeRows(IImageLineSet<? extends IImageLine> iImageLineSet) {
        for (int i = 0; i < this.imgInfo.rows; i++) {
            writeRow(iImageLineSet.getImageLine(i));
        }
    }

    public void writeRow(IImageLine iImageLine, int i) {
        int i2 = this.rowNum + 1;
        this.rowNum = i2;
        if (i2 == this.imgInfo.rows) {
            this.rowNum = 0;
        }
        if (i == this.imgInfo.rows) {
            i = 0;
        }
        if (i >= 0 && this.rowNum != i) {
            throw new PngjOutputException("rows must be written in order: expected:" + this.rowNum + " passed:" + i);
        }
        if (this.rowNum == 0) {
            this.currentpass++;
        }
        if (i == 0 && this.currentpass == this.passes) {
            initIdat();
            writeFirstChunks();
        }
        byte[] rowb = this.pixelsWriter.getRowb();
        iImageLine.writeToPngRaw(rowb);
        this.pixelsWriter.processRow(rowb);
    }

    public void writeRowInt(int[] iArr) {
        writeRow(new ImageLineInt(this.imgInfo, iArr));
    }

    protected PixelsWriter createPixelsWriter(ImageInfo imageInfo) {
        return new PixelsWriterDefault(imageInfo);
    }

    public final PixelsWriter getPixelsWriter() {
        return this.pixelsWriter;
    }

    public String getDebuginfo() {
        return this.debuginfo.toString();
    }
}
