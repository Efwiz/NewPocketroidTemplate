package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.FilterType;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.PngHelperInternal;
import ar.com.hjg.pngj.PngjExceptionInternal;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class FiltersPerformance {
    public static final double[] FILTER_WEIGHTS_DEFAULT = {0.73d, 1.03d, 0.97d, 1.11d, 1.22d};
    private static final double LOG2NI = (-1.0d) / Math.log(2.0d);
    private final ImageInfo iminfo;
    private double memoryA = 0.7d;
    private int lastrow = -1;
    private double[] absum = new double[5];
    private double[] entropy = new double[5];
    private double[] cost = new double[5];
    private int[] histog = new int[256];
    private int lastprefered = -1;
    private boolean initdone = false;
    private double preferenceForNone = 1.0d;
    private double[] filter_weights = {-1.0d, -1.0d, -1.0d, -1.0d, -1.0d};

    public FiltersPerformance(ImageInfo imageInfo) {
        this.iminfo = imageInfo;
    }

    private void init() {
        double[] dArr = this.filter_weights;
        if (dArr[0] < 0.0d) {
            System.arraycopy(FILTER_WEIGHTS_DEFAULT, 0, dArr, 0, 5);
            double d = this.filter_weights[0];
            if (this.iminfo.bitDepth == 16) {
                d = 1.2d;
            } else if (this.iminfo.alpha) {
                d = 0.8d;
            } else if (this.iminfo.indexed || this.iminfo.bitDepth < 8) {
                d = 0.4d;
            }
            this.filter_weights[0] = d / this.preferenceForNone;
        }
        Arrays.fill(this.cost, 1.0d);
        this.initdone = true;
    }

    public void updateFromFiltered(FilterType filterType, byte[] bArr, int i) {
        updateFromRawOrFiltered(filterType, bArr, null, null, i);
    }

    public void updateFromRaw(FilterType filterType, byte[] bArr, byte[] bArr2, int i) {
        updateFromRawOrFiltered(filterType, null, bArr, bArr2, i);
    }

    private void updateFromRawOrFiltered(FilterType filterType, byte[] bArr, byte[] bArr2, byte[] bArr3, int i) {
        if (!this.initdone) {
            init();
        }
        if (i != this.lastrow) {
            Arrays.fill(this.absum, Double.NaN);
            Arrays.fill(this.entropy, Double.NaN);
        }
        this.lastrow = i;
        if (bArr != null) {
            computeHistogram(bArr);
        } else {
            computeHistogramForFilter(filterType, bArr2, bArr3);
        }
        if (filterType == FilterType.FILTER_NONE) {
            this.entropy[filterType.val] = computeEntropyFromHistogram();
        } else {
            this.absum[filterType.val] = computeAbsFromHistogram();
        }
    }

    public FilterType getPreferred() {
        double dPow;
        double d = Double.MAX_VALUE;
        int i = 0;
        for (int i2 = 0; i2 < 5; i2++) {
            if (!Double.isNaN(this.absum[i2])) {
                dPow = this.absum[i2];
            } else {
                if (!Double.isNaN(this.entropy[i2])) {
                    dPow = (Math.pow(2.0d, this.entropy[i2]) - 1.0d) * 0.5d;
                }
            }
            double d2 = dPow * this.filter_weights[i2];
            double[] dArr = this.cost;
            double d3 = dArr[i2];
            double d4 = this.memoryA;
            double d5 = (d3 * d4) + ((1.0d - d4) * d2);
            dArr[i2] = d5;
            if (d5 < d) {
                i = i2;
                d = d5;
            }
        }
        this.lastprefered = i;
        return FilterType.getByVal(i);
    }

    public final void computeHistogramForFilter(FilterType filterType, byte[] bArr, byte[] bArr2) {
        Arrays.fill(this.histog, 0);
        int i = this.iminfo.bytesPerRow;
        int i2 = AnonymousClass1.$SwitchMap$ar$com$hjg$pngj$FilterType[filterType.ordinal()];
        if (i2 == 1) {
            for (int i3 = 1; i3 <= i; i3++) {
                int[] iArr = this.histog;
                int i4 = bArr[i3] & 255;
                iArr[i4] = iArr[i4] + 1;
            }
            return;
        }
        if (i2 == 2) {
            for (int i5 = 1; i5 <= i; i5++) {
                int[] iArr2 = this.histog;
                int iFilterRowPaeth = PngHelperInternal.filterRowPaeth(bArr[i5], 0, bArr2[i5] & 255, 0);
                iArr2[iFilterRowPaeth] = iArr2[iFilterRowPaeth] + 1;
            }
            int i6 = this.iminfo.bytesPixel + 1;
            int i7 = 1;
            while (i6 <= i) {
                int[] iArr3 = this.histog;
                int iFilterRowPaeth2 = PngHelperInternal.filterRowPaeth(bArr[i6], bArr[i7] & 255, bArr2[i6] & 255, bArr2[i7] & 255);
                iArr3[iFilterRowPaeth2] = iArr3[iFilterRowPaeth2] + 1;
                i6++;
                i7++;
            }
            return;
        }
        if (i2 == 3) {
            for (int i8 = 1; i8 <= this.iminfo.bytesPixel; i8++) {
                int[] iArr4 = this.histog;
                int i9 = bArr[i8] & 255;
                iArr4[i9] = iArr4[i9] + 1;
            }
            int i10 = this.iminfo.bytesPixel + 1;
            int i11 = 1;
            while (i10 <= i) {
                int[] iArr5 = this.histog;
                int i12 = (bArr[i10] - bArr[i11]) & 255;
                iArr5[i12] = iArr5[i12] + 1;
                i10++;
                i11++;
            }
            return;
        }
        if (i2 == 4) {
            for (int i13 = 1; i13 <= this.iminfo.bytesPerRow; i13++) {
                int[] iArr6 = this.histog;
                int i14 = (bArr[i13] - bArr2[i13]) & 255;
                iArr6[i14] = iArr6[i14] + 1;
            }
            return;
        }
        if (i2 == 5) {
            for (int i15 = 1; i15 <= this.iminfo.bytesPixel; i15++) {
                int[] iArr7 = this.histog;
                int i16 = ((bArr[i15] & 255) - ((bArr2[i15] & 255) / 2)) & 255;
                iArr7[i16] = iArr7[i16] + 1;
            }
            int i17 = this.iminfo.bytesPixel + 1;
            int i18 = 1;
            while (i17 <= i) {
                int[] iArr8 = this.histog;
                int i19 = ((bArr[i17] & 255) - (((bArr2[i17] & 255) + (bArr[i18] & 255)) / 2)) & 255;
                iArr8[i19] = iArr8[i19] + 1;
                i17++;
                i18++;
            }
            return;
        }
        throw new PngjExceptionInternal("Bad filter:" + filterType);
    }

    /* JADX INFO: renamed from: ar.com.hjg.pngj.pixels.FiltersPerformance$1, reason: invalid class name */
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
                $SwitchMap$ar$com$hjg$pngj$FilterType[FilterType.FILTER_UP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$ar$com$hjg$pngj$FilterType[FilterType.FILTER_AVERAGE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public void computeHistogram(byte[] bArr) {
        Arrays.fill(this.histog, 0);
        for (int i = 1; i < this.iminfo.bytesPerRow; i++) {
            int[] iArr = this.histog;
            int i2 = bArr[i] & 255;
            iArr[i2] = iArr[i2] + 1;
        }
    }

    public double computeAbsFromHistogram() {
        int i;
        int i2 = 0;
        int i3 = 1;
        while (true) {
            if (i3 >= 128) {
                break;
            }
            i2 += this.histog[i3] * i3;
            i3++;
        }
        int i4 = 128;
        for (i = 128; i > 0; i--) {
            i2 += this.histog[i4] * i;
            i4++;
        }
        return ((double) i2) / ((double) this.iminfo.bytesPerRow);
    }

    public final double computeEntropyFromHistogram() {
        double d = 1.0d / ((double) this.iminfo.bytesPerRow);
        double dLog = Math.log(d);
        double dLog2 = 0.0d;
        for (int i : this.histog) {
            if (i > 0) {
                double d2 = i;
                dLog2 += (Math.log(d2) + dLog) * d2;
            }
        }
        double d3 = dLog2 * d * LOG2NI;
        if (d3 < 0.0d) {
            return 0.0d;
        }
        return d3;
    }

    public void setPreferenceForNone(double d) {
        this.preferenceForNone = d;
    }

    public void tuneMemory(double d) {
        if (d == 0.0d) {
            this.memoryA = 0.0d;
        } else {
            this.memoryA = Math.pow(this.memoryA, 1.0d / d);
        }
    }

    public void setFilterWeights(double[] dArr) {
        System.arraycopy(dArr, 0, this.filter_weights, 0, 5);
    }
}
