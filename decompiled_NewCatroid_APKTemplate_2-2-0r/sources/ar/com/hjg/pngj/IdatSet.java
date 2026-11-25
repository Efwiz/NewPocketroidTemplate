package ar.com.hjg.pngj;

import java.util.Arrays;
import java.util.zip.Checksum;
import java.util.zip.Inflater;

/* JADX INFO: loaded from: classes.dex */
public class IdatSet extends DeflatedChunksSet {
    protected final Deinterlacer deinterlacer;
    protected int[] filterUseStat;
    protected final ImageInfo imgInfo;
    protected byte[] rowUnfiltered;
    protected byte[] rowUnfilteredPrev;
    final RowInfo rowinfo;

    @Override // ar.com.hjg.pngj.DeflatedChunksSet
    protected void processDoneCallback() {
    }

    public IdatSet(String str, ImageInfo imageInfo, Deinterlacer deinterlacer) {
        this(str, imageInfo, deinterlacer, null, null);
    }

    public IdatSet(String str, ImageInfo imageInfo, Deinterlacer deinterlacer, Inflater inflater, byte[] bArr) {
        super(str, (deinterlacer != null ? deinterlacer.getBytesToRead() : imageInfo.bytesPerRow) + 1, imageInfo.bytesPerRow + 1, inflater, bArr);
        this.filterUseStat = new int[5];
        this.imgInfo = imageInfo;
        this.deinterlacer = deinterlacer;
        this.rowinfo = new RowInfo(imageInfo, deinterlacer);
    }

    public void unfilterRow() {
        unfilterRow(this.rowinfo.bytesRow);
    }

    protected void unfilterRow(int i) {
        byte[] bArr = this.rowUnfiltered;
        if (bArr == null || bArr.length < this.row.length) {
            this.rowUnfiltered = new byte[this.row.length];
            this.rowUnfilteredPrev = new byte[this.row.length];
        }
        if (this.rowinfo.rowNsubImg == 0) {
            Arrays.fill(this.rowUnfiltered, (byte) 0);
        }
        byte[] bArr2 = this.rowUnfiltered;
        this.rowUnfiltered = this.rowUnfilteredPrev;
        this.rowUnfilteredPrev = bArr2;
        byte b = this.row[0];
        FilterType byVal = FilterType.getByVal(b);
        if (byVal == null) {
            throw new PngjInputException("Filter type " + ((int) b) + " invalid");
        }
        int[] iArr = this.filterUseStat;
        iArr[b] = iArr[b] + 1;
        this.rowUnfiltered[0] = this.row[0];
        int i2 = AnonymousClass1.$SwitchMap$ar$com$hjg$pngj$FilterType[byVal.ordinal()];
        if (i2 == 1) {
            unfilterRowNone(i);
            return;
        }
        if (i2 == 2) {
            unfilterRowSub(i);
            return;
        }
        if (i2 == 3) {
            unfilterRowUp(i);
        } else if (i2 == 4) {
            unfilterRowAverage(i);
        } else {
            if (i2 == 5) {
                unfilterRowPaeth(i);
                return;
            }
            throw new PngjInputException("Filter type " + ((int) b) + " not implemented");
        }
    }

    /* JADX INFO: renamed from: ar.com.hjg.pngj.IdatSet$1, reason: invalid class name */
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
                $SwitchMap$ar$com$hjg$pngj$FilterType[FilterType.FILTER_SUB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$ar$com$hjg$pngj$FilterType[FilterType.FILTER_UP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$ar$com$hjg$pngj$FilterType[FilterType.FILTER_AVERAGE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$ar$com$hjg$pngj$FilterType[FilterType.FILTER_PAETH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private void unfilterRowAverage(int i) {
        int i2 = 1;
        int i3 = 1 - this.imgInfo.bytesPixel;
        while (i2 <= i) {
            this.rowUnfiltered[i2] = (byte) (this.row[i2] + (((i3 > 0 ? this.rowUnfiltered[i3] & 255 : 0) + (this.rowUnfilteredPrev[i2] & 255)) / 2));
            i2++;
            i3++;
        }
    }

    private void unfilterRowNone(int i) {
        for (int i2 = 1; i2 <= i; i2++) {
            this.rowUnfiltered[i2] = this.row[i2];
        }
    }

    private void unfilterRowPaeth(int i) {
        int i2 = 1;
        int i3 = 1 - this.imgInfo.bytesPixel;
        while (i2 <= i) {
            int i4 = 0;
            int i5 = i3 > 0 ? this.rowUnfiltered[i3] & 255 : 0;
            if (i3 > 0) {
                i4 = this.rowUnfilteredPrev[i3] & 255;
            }
            this.rowUnfiltered[i2] = (byte) (this.row[i2] + PngHelperInternal.filterPaethPredictor(i5, this.rowUnfilteredPrev[i2] & 255, i4));
            i2++;
            i3++;
        }
    }

    private void unfilterRowSub(int i) {
        for (int i2 = 1; i2 <= this.imgInfo.bytesPixel; i2++) {
            this.rowUnfiltered[i2] = this.row[i2];
        }
        int i3 = this.imgInfo.bytesPixel + 1;
        int i4 = 1;
        while (i3 <= i) {
            this.rowUnfiltered[i3] = (byte) (this.row[i3] + this.rowUnfiltered[i4]);
            i3++;
            i4++;
        }
    }

    private void unfilterRowUp(int i) {
        for (int i2 = 1; i2 <= i; i2++) {
            this.rowUnfiltered[i2] = (byte) (this.row[i2] + this.rowUnfilteredPrev[i2]);
        }
    }

    @Override // ar.com.hjg.pngj.DeflatedChunksSet
    protected void preProcessRow() {
        super.preProcessRow();
        this.rowinfo.update(getRown());
        unfilterRow();
        RowInfo rowInfo = this.rowinfo;
        rowInfo.updateBuf(this.rowUnfiltered, rowInfo.bytesRow + 1);
    }

    @Override // ar.com.hjg.pngj.DeflatedChunksSet
    protected int processRowCallback() {
        return advanceToNextRow();
    }

    public int advanceToNextRow() {
        int bytesToRead;
        Deinterlacer deinterlacer = this.deinterlacer;
        int i = 0;
        if (deinterlacer == null) {
            if (getRown() < this.imgInfo.rows - 1) {
                bytesToRead = this.imgInfo.bytesPerRow;
                i = bytesToRead + 1;
            }
        } else if (deinterlacer.nextRow()) {
            bytesToRead = this.deinterlacer.getBytesToRead();
            i = bytesToRead + 1;
        }
        if (!isCallbackMode()) {
            prepareForNextRow(i);
        }
        return i;
    }

    @Override // ar.com.hjg.pngj.DeflatedChunksSet
    public boolean isRowReady() {
        return !isWaitingForMoreInput();
    }

    public byte[] getUnfilteredRow() {
        return this.rowUnfiltered;
    }

    public Deinterlacer getDeinterlacer() {
        return this.deinterlacer;
    }

    void updateCrcs(Checksum... checksumArr) {
        for (Checksum checksum : checksumArr) {
            if (checksum != null) {
                checksum.update(getUnfilteredRow(), 1, getRowFilled() - 1);
            }
        }
    }

    @Override // ar.com.hjg.pngj.DeflatedChunksSet
    public void close() {
        super.close();
        this.rowUnfiltered = null;
        this.rowUnfilteredPrev = null;
    }

    public int[] getFilterUseStat() {
        return this.filterUseStat;
    }
}
