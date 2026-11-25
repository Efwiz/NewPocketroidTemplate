package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.FilterType;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjOutputException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class PixelsWriter {
    protected final int buflen;
    protected final int bytesPixel;
    protected final int bytesRow;
    private CompressorStream compressorStream;
    protected int currentRow;
    protected FilterType filterType;
    protected final ImageInfo imgInfo;
    private OutputStream os;
    protected int deflaterCompLevel = 6;
    protected int deflaterStrategy = 0;
    protected boolean initdone = false;
    private int[] filtersUsed = new int[5];

    protected abstract void filterAndWrite(byte[] bArr);

    public abstract byte[] getRowb();

    public PixelsWriter(ImageInfo imageInfo) {
        this.imgInfo = imageInfo;
        int i = imageInfo.bytesPerRow;
        this.bytesRow = i;
        this.buflen = i + 1;
        this.bytesPixel = imageInfo.bytesPixel;
        this.currentRow = -1;
        this.filterType = FilterType.FILTER_DEFAULT;
    }

    public final void processRow(byte[] bArr) {
        if (!this.initdone) {
            init();
        }
        this.currentRow++;
        filterAndWrite(bArr);
    }

    protected void sendToCompressedStream(byte[] bArr) {
        this.compressorStream.write(bArr, 0, bArr.length);
        int[] iArr = this.filtersUsed;
        byte b = bArr[0];
        iArr[b] = iArr[b] + 1;
    }

    protected final byte[] filterRowWithFilterType(FilterType filterType, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int i;
        int i2;
        int i3;
        if (filterType == FilterType.FILTER_NONE) {
            bArr3 = bArr;
        }
        bArr3[0] = (byte) filterType.val;
        int i4 = AnonymousClass1.$SwitchMap$ar$com$hjg$pngj$FilterType[filterType.ordinal()];
        if (i4 != 1) {
            if (i4 == 2) {
                int i5 = 1;
                while (true) {
                    i = this.bytesPixel;
                    if (i5 > i) {
                        break;
                    }
                    bArr3[i5] = (byte) PngHelperInternal.filterRowPaeth(bArr[i5], 0, bArr2[i5] & 255, 0);
                    i5++;
                }
                int i6 = i + 1;
                int i7 = 1;
                while (i6 <= this.bytesRow) {
                    bArr3[i6] = (byte) PngHelperInternal.filterRowPaeth(bArr[i6], bArr[i7] & 255, bArr2[i6] & 255, bArr2[i7] & 255);
                    i6++;
                    i7++;
                }
            } else if (i4 == 3) {
                int i8 = 1;
                while (true) {
                    i2 = this.bytesPixel;
                    if (i8 > i2) {
                        break;
                    }
                    bArr3[i8] = bArr[i8];
                    i8++;
                }
                int i9 = i2 + 1;
                int i10 = 1;
                while (i9 <= this.bytesRow) {
                    bArr3[i9] = (byte) (bArr[i9] - bArr[i10]);
                    i9++;
                    i10++;
                }
            } else if (i4 == 4) {
                int i11 = 1;
                while (true) {
                    i3 = this.bytesPixel;
                    if (i11 > i3) {
                        break;
                    }
                    bArr3[i11] = (byte) (bArr[i11] - ((bArr2[i11] & 255) / 2));
                    i11++;
                }
                int i12 = i3 + 1;
                int i13 = 1;
                while (i12 <= this.bytesRow) {
                    bArr3[i12] = (byte) (bArr[i12] - (((bArr2[i12] & 255) + (bArr[i13] & 255)) / 2));
                    i12++;
                    i13++;
                }
            } else if (i4 == 5) {
                for (int i14 = 1; i14 <= this.bytesRow; i14++) {
                    bArr3[i14] = (byte) (bArr[i14] - bArr2[i14]);
                }
            } else {
                throw new PngjOutputException("Filter type not recognized: " + filterType);
            }
        }
        return bArr3;
    }

    /* JADX INFO: renamed from: ar.com.hjg.pngj.pixels.PixelsWriter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$ar$com$hjg$pngj$FilterType;

        static {
            int[] iArr = new int[FilterType.values().length];
            $SwitchMap$ar$com$hjg$pngj$FilterType = iArr;
            try {
                iArr[FilterType.FILTER_NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$ar$com$hjg$pngj$FilterType[FilterType.FILTER_PAETH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$ar$com$hjg$pngj$FilterType[FilterType.FILTER_SUB.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$ar$com$hjg$pngj$FilterType[FilterType.FILTER_AVERAGE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$ar$com$hjg$pngj$FilterType[FilterType.FILTER_UP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    protected final void init() {
        if (this.initdone) {
            return;
        }
        initParams();
        this.initdone = true;
    }

    protected void initParams() {
        if (this.compressorStream == null) {
            this.compressorStream = new CompressorStreamDeflater(this.os, this.buflen, this.imgInfo.getTotalRawBytes(), this.deflaterCompLevel, this.deflaterStrategy);
        }
    }

    public void close() {
        CompressorStream compressorStream = this.compressorStream;
        if (compressorStream != null) {
            compressorStream.close();
        }
    }

    public void setDeflaterStrategy(Integer num) {
        this.deflaterStrategy = num.intValue();
    }

    public void setDeflaterCompLevel(Integer num) {
        this.deflaterCompLevel = num.intValue();
    }

    public Integer getDeflaterCompLevel() {
        return Integer.valueOf(this.deflaterCompLevel);
    }

    public final void setOs(OutputStream outputStream) {
        this.os = outputStream;
    }

    public OutputStream getOs() {
        return this.os;
    }

    public final FilterType getFilterType() {
        return this.filterType;
    }

    public final void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public double getCompression() {
        if (this.compressorStream.isDone()) {
            return this.compressorStream.getCompressionRatio();
        }
        return 1.0d;
    }

    public void setCompressorStream(CompressorStream compressorStream) {
        this.compressorStream = compressorStream;
    }

    public long getTotalBytesToWrite() {
        return this.imgInfo.getTotalRawBytes();
    }

    protected FilterType getDefaultFilter() {
        if (this.imgInfo.indexed || this.imgInfo.bitDepth < 8) {
            return FilterType.FILTER_NONE;
        }
        if (this.imgInfo.getTotalPixels() < 1024) {
            return FilterType.FILTER_NONE;
        }
        if (this.imgInfo.rows == 1) {
            return FilterType.FILTER_SUB;
        }
        if (this.imgInfo.cols == 1) {
            return FilterType.FILTER_UP;
        }
        return FilterType.FILTER_PAETH;
    }

    public final String getFiltersUsed() {
        return String.format("%d,%d,%d,%d,%d", Integer.valueOf((int) (((((double) this.filtersUsed[0]) * 100.0d) / ((double) this.imgInfo.rows)) + 0.5d)), Integer.valueOf((int) (((((double) this.filtersUsed[1]) * 100.0d) / ((double) this.imgInfo.rows)) + 0.5d)), Integer.valueOf((int) (((((double) this.filtersUsed[2]) * 100.0d) / ((double) this.imgInfo.rows)) + 0.5d)), Integer.valueOf((int) (((((double) this.filtersUsed[3]) * 100.0d) / ((double) this.imgInfo.rows)) + 0.5d)), Integer.valueOf((int) (((((double) this.filtersUsed[4]) * 100.0d) / ((double) this.imgInfo.rows)) + 0.5d)));
    }
}
