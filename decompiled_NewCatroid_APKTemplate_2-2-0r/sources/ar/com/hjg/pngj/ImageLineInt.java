package ar.com.hjg.pngj;

/* JADX INFO: loaded from: classes.dex */
public class ImageLineInt implements IImageLine, IImageLineArray {
    protected FilterType filterType;
    public final ImageInfo imgInfo;
    protected final int[] scanline;
    protected final int size;

    @Override // ar.com.hjg.pngj.IImageLine
    public void endReadFromPngRaw() {
    }

    public ImageLineInt(ImageInfo imageInfo) {
        this(imageInfo, null);
    }

    public ImageLineInt(ImageInfo imageInfo, int[] iArr) {
        this.filterType = FilterType.FILTER_UNKNOWN;
        this.imgInfo = imageInfo;
        this.filterType = FilterType.FILTER_UNKNOWN;
        int i = imageInfo.samplesPerRow;
        this.size = i;
        this.scanline = (iArr == null || iArr.length < i) ? new int[i] : iArr;
    }

    @Override // ar.com.hjg.pngj.IImageLineArray
    public FilterType getFilterType() {
        return this.filterType;
    }

    protected void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public String toString() {
        return " cols=" + this.imgInfo.cols + " bpc=" + this.imgInfo.bitDepth + " size=" + this.scanline.length;
    }

    @Override // ar.com.hjg.pngj.IImageLine
    public void readFromPngRaw(byte[] bArr, int i, int i2, int i3) {
        int i4 = 0;
        setFilterType(FilterType.getByVal(bArr[0]));
        int i5 = i - 1;
        int i6 = (i3 - 1) * this.imgInfo.channels;
        int i7 = 1;
        if (this.imgInfo.bitDepth == 8) {
            if (i3 == 1) {
                while (i4 < this.size) {
                    int i8 = i4 + 1;
                    this.scanline[i4] = bArr[i8] & 255;
                    i4 = i8;
                }
                return;
            }
            int i9 = i2 * this.imgInfo.channels;
            int i10 = 0;
            int i11 = 1;
            while (i11 <= i5) {
                this.scanline[i9] = bArr[i11] & 255;
                i10++;
                if (i10 == this.imgInfo.channels) {
                    i9 += i6;
                    i10 = 0;
                }
                i11++;
                i9++;
            }
            return;
        }
        if (this.imgInfo.bitDepth == 16) {
            if (i3 == 1) {
                while (i4 < this.size) {
                    int[] iArr = this.scanline;
                    int i12 = i7 + 1;
                    int i13 = (bArr[i7] & 255) << 8;
                    i7 += 2;
                    iArr[i4] = (bArr[i12] & 255) | i13;
                    i4++;
                }
                return;
            }
            int i14 = i2 != 0 ? i2 * this.imgInfo.channels : 0;
            int i15 = 0;
            int i16 = 1;
            while (i16 <= i5) {
                this.scanline[i14] = (bArr[i16 + 1] & 255) | ((bArr[i16] & 255) << 8);
                i15++;
                if (i15 == this.imgInfo.channels) {
                    i14 += i6;
                    i15 = 0;
                }
                i16 += 2;
                i14++;
            }
            return;
        }
        int i17 = this.imgInfo.bitDepth;
        int maskForPackedFormats = ImageLineHelper.getMaskForPackedFormats(i17);
        int i18 = i2 * this.imgInfo.channels;
        int i19 = 0;
        for (int i20 = 1; i20 < i; i20++) {
            int i21 = 8 - i17;
            int i22 = maskForPackedFormats;
            do {
                this.scanline[i18] = (bArr[i20] & i22) >> i21;
                i22 >>= i17;
                i21 -= i17;
                i18++;
                i19++;
                if (i19 == this.imgInfo.channels) {
                    i18 += i6;
                    i19 = 0;
                }
                if (i22 == 0) {
                    break;
                }
            } while (i18 < this.size);
        }
    }

    @Override // ar.com.hjg.pngj.IImageLine
    public void writeToPngRaw(byte[] bArr) {
        int i = 0;
        bArr[0] = (byte) this.filterType.val;
        if (this.imgInfo.bitDepth == 8) {
            while (i < this.size) {
                int i2 = i + 1;
                bArr[i2] = (byte) this.scanline[i];
                i = i2;
            }
            return;
        }
        int i3 = 1;
        if (this.imgInfo.bitDepth == 16) {
            while (i < this.size) {
                int i4 = i3 + 1;
                int i5 = this.scanline[i];
                bArr[i3] = (byte) (i5 >> 8);
                i3 += 2;
                bArr[i4] = (byte) (i5 & 255);
                i++;
            }
            return;
        }
        int i6 = this.imgInfo.bitDepth;
        int i7 = 8 - i6;
        int i8 = 0;
        int i9 = 0;
        int i10 = i7;
        while (true) {
            int i11 = this.size;
            if (i8 >= i11) {
                return;
            }
            i9 |= this.scanline[i8] << i10;
            i10 -= i6;
            if (i10 < 0 || i8 == i11 - 1) {
                bArr[i3] = (byte) i9;
                i9 = 0;
                i3++;
                i10 = i7;
            }
            i8++;
        }
    }

    @Override // ar.com.hjg.pngj.IImageLineArray
    public int getSize() {
        return this.size;
    }

    @Override // ar.com.hjg.pngj.IImageLineArray
    public int getElem(int i) {
        return this.scanline[i];
    }

    public int[] getScanline() {
        return this.scanline;
    }

    @Override // ar.com.hjg.pngj.IImageLineArray
    public ImageInfo getImageInfo() {
        return this.imgInfo;
    }

    public static IImageLineFactory<ImageLineInt> getFactory(ImageInfo imageInfo) {
        return new IImageLineFactory<ImageLineInt>() { // from class: ar.com.hjg.pngj.ImageLineInt.1
            @Override // ar.com.hjg.pngj.IImageLineFactory
            public ImageLineInt createImageLine(ImageInfo imageInfo2) {
                return new ImageLineInt(imageInfo2);
            }
        };
    }
}
