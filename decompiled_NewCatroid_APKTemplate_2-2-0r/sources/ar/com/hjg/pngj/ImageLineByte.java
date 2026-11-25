package ar.com.hjg.pngj;

/* JADX INFO: loaded from: classes.dex */
public class ImageLineByte implements IImageLine, IImageLineArray {
    protected FilterType filterType;
    public final ImageInfo imgInfo;
    final byte[] scanline;
    final byte[] scanline2;
    final int size;

    @Override // ar.com.hjg.pngj.IImageLine
    public void endReadFromPngRaw() {
    }

    public ImageLineByte(ImageInfo imageInfo) {
        this(imageInfo, null);
    }

    public ImageLineByte(ImageInfo imageInfo, byte[] bArr) {
        this.imgInfo = imageInfo;
        this.filterType = FilterType.FILTER_UNKNOWN;
        int i = imageInfo.samplesPerRow;
        this.size = i;
        this.scanline = (bArr == null || bArr.length < i) ? new byte[i] : bArr;
        this.scanline2 = imageInfo.bitDepth == 16 ? new byte[i] : null;
    }

    public static IImageLineFactory<ImageLineByte> getFactory(ImageInfo imageInfo) {
        return new IImageLineFactory<ImageLineByte>() { // from class: ar.com.hjg.pngj.ImageLineByte.1
            @Override // ar.com.hjg.pngj.IImageLineFactory
            public ImageLineByte createImageLine(ImageInfo imageInfo2) {
                return new ImageLineByte(imageInfo2);
            }
        };
    }

    public FilterType getFilterUsed() {
        return this.filterType;
    }

    public byte[] getScanlineByte() {
        return this.scanline;
    }

    public byte[] getScanlineByte2() {
        return this.scanline2;
    }

    public String toString() {
        return " cols=" + this.imgInfo.cols + " bpc=" + this.imgInfo.bitDepth + " size=" + this.scanline.length;
    }

    @Override // ar.com.hjg.pngj.IImageLine
    public void readFromPngRaw(byte[] bArr, int i, int i2, int i3) {
        this.filterType = FilterType.getByVal(bArr[0]);
        int i4 = i - 1;
        int i5 = (i3 - 1) * this.imgInfo.channels;
        int i6 = 1;
        if (this.imgInfo.bitDepth == 8) {
            if (i3 == 1) {
                System.arraycopy(bArr, 1, this.scanline, 0, i4);
                return;
            }
            int i7 = i2 * this.imgInfo.channels;
            int i8 = 0;
            int i9 = 1;
            while (i9 <= i4) {
                this.scanline[i7] = bArr[i9];
                i8++;
                if (i8 == this.imgInfo.channels) {
                    i7 += i5;
                    i8 = 0;
                }
                i9++;
                i7++;
            }
            return;
        }
        if (this.imgInfo.bitDepth != 16) {
            int i10 = this.imgInfo.bitDepth;
            int maskForPackedFormats = ImageLineHelper.getMaskForPackedFormats(i10);
            int i11 = i2 * this.imgInfo.channels;
            int i12 = 0;
            for (int i13 = 1; i13 < i; i13++) {
                int i14 = 8 - i10;
                int i15 = maskForPackedFormats;
                do {
                    this.scanline[i11] = (byte) ((bArr[i13] & i15) >> i14);
                    i15 >>= i10;
                    i14 -= i10;
                    i11++;
                    i12++;
                    if (i12 == this.imgInfo.channels) {
                        i11 += i5;
                        i12 = 0;
                    }
                    if (i15 == 0) {
                        break;
                    }
                } while (i11 < this.size);
            }
            return;
        }
        if (i3 == 1) {
            for (int i16 = 0; i16 < this.imgInfo.samplesPerRow; i16++) {
                int i17 = i6 + 1;
                this.scanline[i16] = bArr[i6];
                i6 += 2;
                this.scanline2[i16] = bArr[i17];
            }
            return;
        }
        int i18 = i2 != 0 ? i2 * this.imgInfo.channels : 0;
        int i19 = 0;
        int i20 = 1;
        while (i20 <= i4) {
            int i21 = i20 + 1;
            this.scanline[i18] = bArr[i20];
            i20 += 2;
            this.scanline2[i18] = bArr[i21];
            i19++;
            if (i19 == this.imgInfo.channels) {
                i18 += i5;
                i19 = 0;
            }
            i18++;
        }
    }

    @Override // ar.com.hjg.pngj.IImageLine
    public void writeToPngRaw(byte[] bArr) {
        int i = 0;
        bArr[0] = (byte) this.filterType.val;
        int i2 = 1;
        if (this.imgInfo.bitDepth == 8) {
            System.arraycopy(this.scanline, 0, bArr, 1, this.size);
            while (i < this.size) {
                int i3 = i + 1;
                bArr[i3] = this.scanline[i];
                i = i3;
            }
            return;
        }
        if (this.imgInfo.bitDepth == 16) {
            while (i < this.size) {
                int i4 = i2 + 1;
                bArr[i2] = this.scanline[i];
                i2 += 2;
                bArr[i4] = this.scanline2[i];
                i++;
            }
            return;
        }
        int i5 = this.imgInfo.bitDepth;
        int i6 = 8 - i5;
        int i7 = 0;
        int i8 = 0;
        int i9 = i6;
        while (true) {
            int i10 = this.size;
            if (i7 >= i10) {
                return;
            }
            i8 |= this.scanline[i7] << i9;
            i9 -= i5;
            if (i9 < 0 || i7 == i10 - 1) {
                bArr[i2] = (byte) i8;
                i8 = 0;
                i2++;
                i9 = i6;
            }
            i7++;
        }
    }

    @Override // ar.com.hjg.pngj.IImageLineArray
    public int getSize() {
        return this.size;
    }

    @Override // ar.com.hjg.pngj.IImageLineArray
    public int getElem(int i) {
        byte[] bArr = this.scanline2;
        if (bArr == null) {
            return this.scanline[i] & 255;
        }
        return (bArr[i] & 255) | ((this.scanline[i] & 255) << 8);
    }

    public byte[] getScanline() {
        return this.scanline;
    }

    @Override // ar.com.hjg.pngj.IImageLineArray
    public ImageInfo getImageInfo() {
        return this.imgInfo;
    }

    @Override // ar.com.hjg.pngj.IImageLineArray
    public FilterType getFilterType() {
        return this.filterType;
    }
}
