package ar.com.hjg.pngj;

import com.gaurav.avnc.vnc.XKeySym;

/* JADX INFO: loaded from: classes.dex */
public class ImageInfo {
    public static final int MAX_COLS_ROW = 16777216;
    public final boolean alpha;
    public final int bitDepth;
    public final int bitspPixel;
    public final int bytesPerRow;
    public final int bytesPixel;
    public final int channels;
    public final int cols;
    public final boolean greyscale;
    public final boolean indexed;
    public final boolean packed;
    public final int rows;
    public final int samplesPerRow;
    public final int samplesPerRowPacked;
    private long totalPixels;
    private long totalRawBytes;

    public ImageInfo(int i, int i2, int i3, boolean z) {
        this(i, i2, i3, z, false, false);
    }

    public ImageInfo(int i, int i2, int i3, boolean z, boolean z2, boolean z3) {
        int i4;
        this.totalPixels = -1L;
        this.totalRawBytes = -1L;
        this.cols = i;
        this.rows = i2;
        this.alpha = z;
        this.indexed = z3;
        this.greyscale = z2;
        if (z2 && z3) {
            throw new PngjException("palette and greyscale are mutually exclusive");
        }
        if (z2 || z3) {
            i4 = z ? 2 : 1;
        } else {
            i4 = z ? 4 : 3;
        }
        this.channels = i4;
        this.bitDepth = i3;
        boolean z4 = i3 < 8;
        this.packed = z4;
        int i5 = i4 * i3;
        this.bitspPixel = i5;
        this.bytesPixel = (i5 + 7) / 8;
        int i6 = ((i5 * i) + 7) / 8;
        this.bytesPerRow = i6;
        int i7 = i4 * i;
        this.samplesPerRow = i7;
        this.samplesPerRowPacked = z4 ? i6 : i7;
        if (i3 == 1 || i3 == 2 || i3 == 4) {
            if (!z3 && !z2) {
                throw new PngjException("only indexed or grayscale can have bitdepth=" + i3);
            }
        } else if (i3 != 8) {
            if (i3 != 16) {
                throw new PngjException("invalid bitdepth=" + i3);
            }
            if (z3) {
                throw new PngjException("indexed can't have bitdepth=" + i3);
            }
        }
        if (i < 1 || i > 16777216) {
            throw new PngjException("invalid cols=" + i + " ???");
        }
        if (i2 < 1 || i2 > 16777216) {
            throw new PngjException("invalid rows=" + i2 + " ???");
        }
        if (i7 < 1) {
            throw new PngjException("invalid image parameters (overflow?)");
        }
    }

    public long getTotalPixels() {
        if (this.totalPixels < 0) {
            this.totalPixels = ((long) this.cols) * ((long) this.rows);
        }
        return this.totalPixels;
    }

    public long getTotalRawBytes() {
        if (this.totalRawBytes < 0) {
            this.totalRawBytes = ((long) (this.bytesPerRow + 1)) * ((long) this.rows);
        }
        return this.totalRawBytes;
    }

    public String toString() {
        return "ImageInfo [cols=" + this.cols + ", rows=" + this.rows + ", bitDepth=" + this.bitDepth + ", channels=" + this.channels + ", alpha=" + this.alpha + ", greyscale=" + this.greyscale + ", indexed=" + this.indexed + "]";
    }

    public String toStringBrief() {
        return String.valueOf(this.cols) + "x" + this.rows + (this.bitDepth != 8 ? "d" + this.bitDepth : "") + (this.alpha ? "a" : "") + (this.indexed ? "p" : "") + (this.greyscale ? "g" : "");
    }

    public String toStringDetail() {
        return "ImageInfo [cols=" + this.cols + ", rows=" + this.rows + ", bitDepth=" + this.bitDepth + ", channels=" + this.channels + ", bitspPixel=" + this.bitspPixel + ", bytesPixel=" + this.bytesPixel + ", bytesPerRow=" + this.bytesPerRow + ", samplesPerRow=" + this.samplesPerRow + ", samplesPerRowP=" + this.samplesPerRowPacked + ", alpha=" + this.alpha + ", greyscale=" + this.greyscale + ", indexed=" + this.indexed + ", packed=" + this.packed + "]";
    }

    public int hashCode() {
        boolean z = this.alpha;
        int i = XKeySym.XK_kana_MA;
        int i2 = ((((((((((z ? 1231 : 1237) + 31) * 31) + this.bitDepth) * 31) + this.channels) * 31) + this.cols) * 31) + (this.greyscale ? 1231 : 1237)) * 31;
        if (!this.indexed) {
            i = 1237;
        }
        return ((i2 + i) * 31) + this.rows;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ImageInfo imageInfo = (ImageInfo) obj;
        return this.alpha == imageInfo.alpha && this.bitDepth == imageInfo.bitDepth && this.channels == imageInfo.channels && this.cols == imageInfo.cols && this.greyscale == imageInfo.greyscale && this.indexed == imageInfo.indexed && this.rows == imageInfo.rows;
    }
}
