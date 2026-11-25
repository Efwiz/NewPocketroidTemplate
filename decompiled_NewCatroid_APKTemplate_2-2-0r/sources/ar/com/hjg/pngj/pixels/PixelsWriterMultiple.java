package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.FilterType;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import java.util.LinkedList;
import net.bytebuddy.description.type.TypeDescription;

/* JADX INFO: loaded from: classes.dex */
public class PixelsWriterMultiple extends PixelsWriter {
    protected static final int HINT_MEMORY_DEFAULT_KB = 100;
    protected int bandNum;
    protected CompressorStream[] filterBank;
    protected byte[] filteredRowTmp;
    protected byte[][] filteredRows;
    protected FiltersPerformance filtersPerf;
    protected int firstRowInThisBand;
    protected int hintMemoryKb;
    private int hintRowsPerBand;
    protected int lastRowInThisBand;
    protected int rowInBand;
    protected LinkedList<byte[]> rows;
    protected int rowsPerBand;
    protected int rowsPerBandCurrent;
    private boolean tryAdaptive;
    private boolean useLz4;

    public PixelsWriterMultiple(ImageInfo imageInfo) {
        super(imageInfo);
        this.filterBank = new CompressorStream[6];
        this.filteredRows = new byte[5][];
        this.rowsPerBand = 0;
        this.rowsPerBandCurrent = 0;
        this.rowInBand = -1;
        this.bandNum = -1;
        this.tryAdaptive = true;
        this.hintMemoryKb = 100;
        this.hintRowsPerBand = 1000;
        this.useLz4 = true;
        this.filtersPerf = new FiltersPerformance(imageInfo);
        this.rows = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            this.rows.add(new byte[this.buflen]);
        }
        this.filteredRowTmp = new byte[this.buflen];
    }

    @Override // ar.com.hjg.pngj.pixels.PixelsWriter
    protected void filterAndWrite(byte[] bArr) {
        byte[] bArrFilterRowWithFilterType;
        if (!this.initdone) {
            init();
        }
        int i = 0;
        if (bArr != this.rows.get(0)) {
            throw new RuntimeException(TypeDescription.Generic.OfWildcardType.SYMBOL);
        }
        setBandFromNewRown();
        byte[] bArr2 = this.rows.get(1);
        for (FilterType filterType : FilterType.getAllStandardNoneLast()) {
            if (this.currentRow != 0 || filterType == FilterType.FILTER_NONE || filterType == FilterType.FILTER_SUB) {
                byte[] bArrFilterRowWithFilterType2 = filterRowWithFilterType(filterType, bArr, bArr2, this.filteredRows[filterType.val]);
                this.filterBank[filterType.val].write(bArrFilterRowWithFilterType2);
                if (this.currentRow == 0 && filterType == FilterType.FILTER_SUB) {
                    this.filterBank[FilterType.FILTER_PAETH.val].write(bArrFilterRowWithFilterType2);
                    this.filterBank[FilterType.FILTER_AVERAGE.val].write(bArrFilterRowWithFilterType2);
                    this.filterBank[FilterType.FILTER_UP.val].write(bArrFilterRowWithFilterType2);
                }
                if (this.tryAdaptive) {
                    this.filtersPerf.updateFromFiltered(filterType, bArrFilterRowWithFilterType2, this.currentRow);
                }
            }
        }
        this.filteredRows[0] = bArr;
        if (this.tryAdaptive) {
            this.filterBank[5].write(this.filteredRows[this.filtersPerf.getPreferred().val]);
        }
        if (this.currentRow == this.lastRowInThisBand) {
            byte[] firstBytes = this.filterBank[getBestCompressor()].getFirstBytes();
            int i2 = this.firstRowInThisBand;
            int i3 = this.lastRowInThisBand - i2;
            while (true) {
                int i4 = this.lastRowInThisBand;
                if (i2 > i4) {
                    break;
                }
                byte b = firstBytes[i];
                if (i2 != i4) {
                    bArrFilterRowWithFilterType = filterRowWithFilterType(FilterType.getByVal(b), this.rows.get(i3), this.rows.get(i3 + 1), this.filteredRowTmp);
                } else {
                    bArrFilterRowWithFilterType = this.filteredRows[b];
                }
                sendToCompressedStream(bArrFilterRowWithFilterType);
                i2++;
                i3--;
                i++;
            }
        }
        if (this.rows.size() > this.rowsPerBandCurrent) {
            LinkedList<byte[]> linkedList = this.rows;
            linkedList.addFirst(linkedList.removeLast());
        } else {
            this.rows.addFirst(new byte[this.buflen]);
        }
    }

    @Override // ar.com.hjg.pngj.pixels.PixelsWriter
    public byte[] getRowb() {
        return this.rows.get(0);
    }

    private void setBandFromNewRown() {
        boolean z = false;
        boolean z2 = this.currentRow == 0 || this.currentRow > this.lastRowInThisBand;
        if (this.currentRow == 0) {
            this.bandNum = -1;
        }
        if (z2) {
            this.bandNum++;
            this.rowInBand = 0;
        } else {
            this.rowInBand++;
        }
        if (z2) {
            int i = this.currentRow;
            this.firstRowInThisBand = i;
            int i2 = this.rowsPerBand;
            this.lastRowInThisBand = (i + i2) - 1;
            if ((i + (i2 * 2)) - 1 >= this.imgInfo.rows) {
                this.lastRowInThisBand = this.imgInfo.rows - 1;
            }
            int i3 = (this.lastRowInThisBand + 1) - this.firstRowInThisBand;
            this.rowsPerBandCurrent = i3;
            if (i3 > 3 && (i3 >= 10 || this.imgInfo.bytesPerRow >= 64)) {
                z = true;
            }
            this.tryAdaptive = z;
            rebuildFiltersBank();
        }
    }

    private void rebuildFiltersBank() {
        long j = ((long) this.rowsPerBandCurrent) * ((long) this.buflen);
        for (int i = 0; i <= 5; i++) {
            CompressorStream compressorStreamDeflater = this.filterBank[i];
            if (compressorStreamDeflater == null || compressorStreamDeflater.totalbytes != j) {
                if (compressorStreamDeflater != null) {
                    compressorStreamDeflater.close();
                }
                if (this.useLz4) {
                    compressorStreamDeflater = new CompressorStreamLz4(null, this.buflen, j);
                } else {
                    compressorStreamDeflater = new CompressorStreamDeflater(null, this.buflen, j, 4, 0);
                }
                this.filterBank[i] = compressorStreamDeflater;
            } else {
                compressorStreamDeflater.reset();
            }
            compressorStreamDeflater.setStoreFirstByte(true, this.rowsPerBandCurrent);
        }
    }

    private int computeInitialRowsPerBand() {
        int i = (int) (((((double) this.hintMemoryKb) * 1024.0d) / ((double) (this.imgInfo.bytesPerRow + 1))) - 5.0d);
        int i2 = i >= 1 ? i : 1;
        int i3 = this.hintRowsPerBand;
        if (i3 > 0 && i2 > i3) {
            i2 = i3;
        }
        if (i2 > this.imgInfo.rows) {
            i2 = this.imgInfo.rows;
        }
        if (i2 > 2 && i2 > this.imgInfo.rows / 8) {
            int i4 = (this.imgInfo.rows + (i2 - 1)) / i2;
            i2 = (this.imgInfo.rows + (i4 / 2)) / i4;
        }
        PngHelperInternal.debug("rows :" + i2 + "/" + this.imgInfo.rows);
        return i2;
    }

    private int getBestCompressor() {
        double d = Double.MAX_VALUE;
        int i = -1;
        for (int i2 = this.tryAdaptive ? 5 : 4; i2 >= 0; i2--) {
            double compressionRatio = this.filterBank[i2].getCompressionRatio();
            if (compressionRatio <= d) {
                i = i2;
                d = compressionRatio;
            }
        }
        return i;
    }

    @Override // ar.com.hjg.pngj.pixels.PixelsWriter
    protected void initParams() {
        if (this.imgInfo.cols < 3 && !FilterType.isValidStandard(this.filterType)) {
            this.filterType = FilterType.FILTER_DEFAULT;
        }
        if (this.imgInfo.rows < 3 && !FilterType.isValidStandard(this.filterType)) {
            this.filterType = FilterType.FILTER_DEFAULT;
        }
        for (int i = 1; i <= 4; i++) {
            byte[] bArr = this.filteredRows[i];
            if (bArr == null || bArr.length < this.buflen) {
                this.filteredRows[i] = new byte[this.buflen];
            }
        }
        if (this.rowsPerBand == 0) {
            this.rowsPerBand = computeInitialRowsPerBand();
        }
    }

    @Override // ar.com.hjg.pngj.pixels.PixelsWriter
    public void close() {
        super.close();
        this.rows.clear();
        for (CompressorStream compressorStream : this.filterBank) {
            compressorStream.close();
        }
    }

    public void setHintMemoryKb(int i) {
        if (i <= 0) {
            i = 100;
        } else if (i > 10000) {
            i = 10000;
        }
        this.hintMemoryKb = i;
    }

    public void setHintRowsPerBand(int i) {
        this.hintRowsPerBand = i;
    }

    public void setUseLz4(boolean z) {
        this.useLz4 = z;
    }

    public FiltersPerformance getFiltersPerf() {
        return this.filtersPerf;
    }
}
