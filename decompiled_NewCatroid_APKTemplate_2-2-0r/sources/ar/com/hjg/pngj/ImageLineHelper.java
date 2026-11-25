package ar.com.hjg.pngj;

import ar.com.hjg.pngj.chunks.PngChunkPLTE;
import ar.com.hjg.pngj.chunks.PngChunkTRNS;

/* JADX INFO: loaded from: classes.dex */
public class ImageLineHelper {
    static int[][] DEPTH_UNPACK;
    static int[] DEPTH_UNPACK_1;
    static int[] DEPTH_UNPACK_2;
    static int[] DEPTH_UNPACK_4;

    public static int clampTo_0_255(int i) {
        if (i > 255) {
            return 255;
        }
        if (i < 0) {
            return 0;
        }
        return i;
    }

    public static int clampTo_0_65535(int i) {
        if (i > 65535) {
            return 65535;
        }
        if (i < 0) {
            return 0;
        }
        return i;
    }

    public static int clampTo_128_127(int i) {
        int i2 = 127;
        if (i <= 127) {
            i2 = -128;
            if (i >= -128) {
                return i;
            }
        }
        return i2;
    }

    static int getMaskForPackedFormats(int i) {
        if (i == 4) {
            return 240;
        }
        return i == 2 ? 192 : 128;
    }

    static int getMaskForPackedFormatsLs(int i) {
        if (i == 4) {
            return 15;
        }
        return i == 2 ? 3 : 1;
    }

    public static int interpol(int i, int i2, int i3, int i4, double d, double d2) {
        double d3 = 1.0d - d;
        return (int) ((((((double) i) * d3) + (((double) i2) * d)) * (1.0d - d2)) + (((((double) i3) * d3) + (((double) i4) * d)) * d2) + 0.5d);
    }

    public static byte scaleDown(int i, byte b) {
        return i < 8 ? (byte) (b >> (8 - i)) : b;
    }

    private static void initDepthScale() {
        DEPTH_UNPACK_1 = new int[2];
        for (int i = 0; i < 2; i++) {
            DEPTH_UNPACK_1[i] = i * 255;
        }
        DEPTH_UNPACK_2 = new int[4];
        for (int i2 = 0; i2 < 4; i2++) {
            DEPTH_UNPACK_2[i2] = (i2 * 255) / 3;
        }
        DEPTH_UNPACK_4 = new int[16];
        for (int i3 = 0; i3 < 16; i3++) {
            DEPTH_UNPACK_4[i3] = (i3 * 255) / 15;
        }
        DEPTH_UNPACK = new int[][]{null, DEPTH_UNPACK_1, DEPTH_UNPACK_2, null, DEPTH_UNPACK_4};
    }

    public static void scaleUp(IImageLineArray iImageLineArray) {
        if (iImageLineArray.getImageInfo().indexed || iImageLineArray.getImageInfo().bitDepth >= 8) {
            return;
        }
        if (DEPTH_UNPACK_1 == null || DEPTH_UNPACK == null) {
            initDepthScale();
        }
        int[] iArr = DEPTH_UNPACK[iImageLineArray.getImageInfo().bitDepth];
        int i = 0;
        if (iImageLineArray instanceof ImageLineInt) {
            ImageLineInt imageLineInt = (ImageLineInt) iImageLineArray;
            while (i < imageLineInt.getSize()) {
                imageLineInt.scanline[i] = iArr[imageLineInt.scanline[i]];
                i++;
            }
            return;
        }
        if (iImageLineArray instanceof ImageLineByte) {
            ImageLineByte imageLineByte = (ImageLineByte) iImageLineArray;
            while (i < imageLineByte.getSize()) {
                imageLineByte.scanline[i] = (byte) iArr[imageLineByte.scanline[i]];
                i++;
            }
            return;
        }
        throw new PngjException("not implemented");
    }

    public static void scaleDown(IImageLineArray iImageLineArray) {
        if (iImageLineArray.getImageInfo().indexed || iImageLineArray.getImageInfo().bitDepth >= 8) {
            return;
        }
        boolean z = iImageLineArray instanceof ImageLineInt;
        if (z) {
            int i = 8 - iImageLineArray.getImageInfo().bitDepth;
            int i2 = 0;
            if (z) {
                ImageLineInt imageLineInt = (ImageLineInt) iImageLineArray;
                while (i2 < iImageLineArray.getSize()) {
                    imageLineInt.scanline[i2] = imageLineInt.scanline[i2] >> i;
                    i2++;
                }
                return;
            }
            if (iImageLineArray instanceof ImageLineByte) {
                ImageLineByte imageLineByte = (ImageLineByte) iImageLineArray;
                while (i2 < iImageLineArray.getSize()) {
                    imageLineByte.scanline[i2] = (byte) ((imageLineByte.scanline[i2] & 255) >> i);
                    i2++;
                }
                return;
            }
            return;
        }
        throw new PngjException("not implemented");
    }

    public static byte scaleUp(int i, byte b) {
        return i < 8 ? (byte) DEPTH_UNPACK[i][b] : b;
    }

    public static int[] palette2rgb(ImageLineInt imageLineInt, PngChunkPLTE pngChunkPLTE, PngChunkTRNS pngChunkTRNS, int[] iArr) {
        return palette2rgb(imageLineInt, pngChunkPLTE, pngChunkTRNS, iArr, false);
    }

    static int[] lineToARGB32(ImageLineByte imageLineByte, PngChunkPLTE pngChunkPLTE, PngChunkTRNS pngChunkTRNS, int[] iArr) {
        int rgb888;
        int i;
        int i2;
        boolean z = imageLineByte.imgInfo.alpha;
        int i3 = imageLineByte.getImageInfo().cols;
        if (iArr == null || iArr.length < i3) {
            iArr = new int[i3];
        }
        if (imageLineByte.getImageInfo().indexed) {
            int length = pngChunkTRNS != null ? pngChunkTRNS.getPalletteAlpha().length : 0;
            for (int i4 = 0; i4 < i3; i4++) {
                int i5 = imageLineByte.scanline[i4] & 255;
                iArr[i4] = ((i5 < length ? pngChunkTRNS.getPalletteAlpha()[i5] : 255) << 24) | pngChunkPLTE.getEntry(i5);
            }
        } else if (imageLineByte.imgInfo.greyscale) {
            rgb888 = pngChunkTRNS != null ? pngChunkTRNS.getGray() : -1;
            int i6 = 0;
            for (int i7 = 0; i7 < i3; i7++) {
                int i8 = i6 + 1;
                int i9 = imageLineByte.scanline[i6] & 255;
                if (z) {
                    i6 += 2;
                    i2 = imageLineByte.scanline[i8] & 255;
                } else {
                    i6 = i8;
                    i2 = i9 != rgb888 ? 255 : 0;
                }
                iArr[i7] = (i9 << 16) | (i2 << 24) | i9 | (i9 << 8);
            }
        } else {
            rgb888 = pngChunkTRNS != null ? pngChunkTRNS.getRGB888() : -1;
            int i10 = 0;
            for (int i11 = 0; i11 < i3; i11++) {
                int i12 = i10 + 3;
                int i13 = ((imageLineByte.scanline[i10] & 255) << 16) | ((imageLineByte.scanline[i10 + 1] & 255) << 8) | (imageLineByte.scanline[i10 + 2] & 255);
                if (z) {
                    i10 += 4;
                    i = imageLineByte.scanline[i12] & 255;
                } else {
                    i = i13 != rgb888 ? 255 : 0;
                    i10 = i12;
                }
                iArr[i11] = i13 | (i << 24);
            }
        }
        return iArr;
    }

    static byte[] lineToRGBA8888(ImageLineByte imageLineByte, PngChunkPLTE pngChunkPLTE, PngChunkTRNS pngChunkTRNS, byte[] bArr) {
        byte b;
        boolean z = imageLineByte.imgInfo.alpha;
        int i = imageLineByte.imgInfo.cols;
        int i2 = i * 4;
        if (bArr == null || bArr.length < i2) {
            bArr = new byte[i2];
        }
        if (imageLineByte.imgInfo.indexed) {
            int length = pngChunkTRNS != null ? pngChunkTRNS.getPalletteAlpha().length : 0;
            int i3 = 0;
            for (int i4 = 0; i4 < i; i4++) {
                int i5 = 255;
                int i6 = imageLineByte.scanline[i4] & 255;
                int entry = pngChunkPLTE.getEntry(i6);
                bArr[i3] = (byte) ((entry >> 16) & 255);
                bArr[i3 + 1] = (byte) ((entry >> 8) & 255);
                int i7 = i3 + 3;
                bArr[i3 + 2] = (byte) (entry & 255);
                i3 += 4;
                if (i6 < length) {
                    i5 = pngChunkTRNS.getPalletteAlpha()[i6];
                }
                bArr[i7] = (byte) i5;
            }
        } else if (imageLineByte.imgInfo.greyscale) {
            int gray = pngChunkTRNS != null ? pngChunkTRNS.getGray() : -1;
            int i8 = 0;
            int i9 = 0;
            while (i8 < i2) {
                int i10 = i9 + 1;
                byte b2 = imageLineByte.scanline[i9];
                bArr[i8] = b2;
                bArr[i8 + 1] = b2;
                int i11 = i8 + 3;
                bArr[i8 + 2] = b2;
                i8 += 4;
                if (z) {
                    i9 += 2;
                    b = imageLineByte.scanline[i10];
                } else {
                    b = (b2 & 255) == gray ? (byte) 0 : (byte) -1;
                    i9 = i10;
                }
                bArr[i11] = b;
            }
        } else if (z) {
            System.arraycopy(imageLineByte.scanline, 0, bArr, 0, i2);
        } else {
            int i12 = 0;
            int i13 = 0;
            while (i12 < i2) {
                bArr[i12] = imageLineByte.scanline[i13];
                int i14 = i13 + 2;
                bArr[i12 + 1] = imageLineByte.scanline[i13 + 1];
                i13 += 3;
                bArr[i12 + 2] = imageLineByte.scanline[i14];
                int i15 = i12 + 4;
                bArr[i12 + 3] = -1;
                if (pngChunkTRNS != null && bArr[i12 + 1] == ((byte) pngChunkTRNS.getRGB()[0]) && bArr[i12 + 2] == ((byte) pngChunkTRNS.getRGB()[1])) {
                    int i16 = i12 + 3;
                    if (bArr[i16] == ((byte) pngChunkTRNS.getRGB()[2])) {
                        bArr[i16] = 0;
                    }
                }
                i12 = i15;
            }
        }
        return bArr;
    }

    static byte[] lineToRGB888(ImageLineByte imageLineByte, PngChunkPLTE pngChunkPLTE, byte[] bArr) {
        boolean z = imageLineByte.imgInfo.alpha;
        int i = imageLineByte.imgInfo.cols;
        int i2 = i * 3;
        if (bArr == null || bArr.length < i2) {
            bArr = new byte[i2];
        }
        int[] iArr = new int[3];
        int i3 = 0;
        if (imageLineByte.imgInfo.indexed) {
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                pngChunkPLTE.getEntryRgb(imageLineByte.scanline[i5] & 255, iArr);
                bArr[i4] = (byte) iArr[0];
                int i6 = i4 + 2;
                bArr[i4 + 1] = (byte) iArr[1];
                i4 += 3;
                bArr[i6] = (byte) iArr[2];
            }
        } else if (imageLineByte.imgInfo.greyscale) {
            int i7 = 0;
            while (i3 < i2) {
                int i8 = i7 + 1;
                byte b = imageLineByte.scanline[i7];
                bArr[i3] = b;
                int i9 = i3 + 2;
                bArr[i3 + 1] = b;
                i3 += 3;
                bArr[i9] = b;
                i7 = z ? i7 + 2 : i8;
            }
        } else if (z) {
            int i10 = 0;
            while (i3 < i2) {
                bArr[i3] = imageLineByte.scanline[i10];
                int i11 = i3 + 2;
                bArr[i3 + 1] = imageLineByte.scanline[i10 + 1];
                i3 += 3;
                bArr[i11] = imageLineByte.scanline[i10 + 2];
                i10 += 4;
            }
        } else {
            System.arraycopy(imageLineByte.scanline, 0, bArr, 0, i2);
        }
        return bArr;
    }

    public static int[] palette2rgba(ImageLineInt imageLineInt, PngChunkPLTE pngChunkPLTE, PngChunkTRNS pngChunkTRNS, int[] iArr) {
        return palette2rgb(imageLineInt, pngChunkPLTE, pngChunkTRNS, iArr, true);
    }

    public static int[] palette2rgb(ImageLineInt imageLineInt, PngChunkPLTE pngChunkPLTE, int[] iArr) {
        return palette2rgb(imageLineInt, pngChunkPLTE, null, iArr, false);
    }

    private static int[] palette2rgb(IImageLine iImageLine, PngChunkPLTE pngChunkPLTE, PngChunkTRNS pngChunkTRNS, int[] iArr, boolean z) {
        boolean z2 = pngChunkTRNS != null;
        int i = z2 ? 4 : 3;
        ImageLineInt imageLineInt = (ImageLineInt) (iImageLine instanceof ImageLineInt ? iImageLine : null);
        if (!(iImageLine instanceof ImageLineByte)) {
            iImageLine = null;
        }
        ImageLineByte imageLineByte = (ImageLineByte) iImageLine;
        boolean z3 = imageLineByte != null;
        int i2 = (imageLineInt != null ? imageLineInt.imgInfo : imageLineByte.imgInfo).cols;
        int i3 = i2 * i;
        if (iArr == null || iArr.length < i3) {
            iArr = new int[i3];
        }
        int length = pngChunkTRNS != null ? pngChunkTRNS.getPalletteAlpha().length : 0;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = z3 ? imageLineByte.scanline[i4] & 255 : imageLineInt.scanline[i4];
            int i6 = i4 * i;
            pngChunkPLTE.getEntryRgb(i5, iArr, i6);
            if (z2) {
                iArr[i6 + 3] = i5 < length ? pngChunkTRNS.getPalletteAlpha()[i5] : 255;
            }
        }
        return iArr;
    }

    public static String infoFirstLastPixels(ImageLineInt imageLineInt) {
        return imageLineInt.imgInfo.channels == 1 ? String.format("first=(%d) last=(%d)", Integer.valueOf(imageLineInt.scanline[0]), Integer.valueOf(imageLineInt.scanline[imageLineInt.scanline.length - 1])) : String.format("first=(%d %d %d) last=(%d %d %d)", Integer.valueOf(imageLineInt.scanline[0]), Integer.valueOf(imageLineInt.scanline[1]), Integer.valueOf(imageLineInt.scanline[2]), Integer.valueOf(imageLineInt.scanline[imageLineInt.scanline.length - imageLineInt.imgInfo.channels]), Integer.valueOf(imageLineInt.scanline[(imageLineInt.scanline.length - imageLineInt.imgInfo.channels) + 1]), Integer.valueOf(imageLineInt.scanline[(imageLineInt.scanline.length - imageLineInt.imgInfo.channels) + 2]));
    }

    public static int getPixelRGB8(IImageLine iImageLine, int i) {
        int i2;
        int i3;
        if (iImageLine instanceof ImageLineInt) {
            ImageLineInt imageLineInt = (ImageLineInt) iImageLine;
            int i4 = i * imageLineInt.imgInfo.channels;
            int[] scanline = imageLineInt.getScanline();
            i2 = (scanline[i4] << 16) | (scanline[i4 + 1] << 8);
            i3 = scanline[i4 + 2];
        } else if (iImageLine instanceof ImageLineByte) {
            ImageLineByte imageLineByte = (ImageLineByte) iImageLine;
            int i5 = i * imageLineByte.imgInfo.channels;
            byte[] scanline2 = imageLineByte.getScanline();
            i2 = ((scanline2[i5] & 255) << 16) | ((scanline2[i5 + 1] & 255) << 8);
            i3 = scanline2[i5 + 2] & 255;
        } else {
            throw new PngjException("Not supported " + iImageLine.getClass());
        }
        return i3 | i2;
    }

    public static int getPixelARGB8(IImageLine iImageLine, int i) {
        int i2;
        int i3;
        if (iImageLine instanceof ImageLineInt) {
            ImageLineInt imageLineInt = (ImageLineInt) iImageLine;
            int i4 = i * imageLineInt.imgInfo.channels;
            int[] scanline = imageLineInt.getScanline();
            i2 = (scanline[i4 + 3] << 24) | (scanline[i4] << 16) | (scanline[i4 + 1] << 8);
            i3 = scanline[i4 + 2];
        } else if (iImageLine instanceof ImageLineByte) {
            ImageLineByte imageLineByte = (ImageLineByte) iImageLine;
            int i5 = i * imageLineByte.imgInfo.channels;
            byte[] scanline2 = imageLineByte.getScanline();
            i2 = ((scanline2[i5 + 3] & 255) << 24) | ((scanline2[i5] & 255) << 16) | ((scanline2[i5 + 1] & 255) << 8);
            i3 = scanline2[i5 + 2] & 255;
        } else {
            throw new PngjException("Not supported " + iImageLine.getClass());
        }
        return i3 | i2;
    }

    public static void setPixelsRGB8(ImageLineInt imageLineInt, int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < imageLineInt.imgInfo.cols; i2++) {
            imageLineInt.scanline[i] = (iArr[i2] >> 16) & 255;
            int i3 = i + 2;
            imageLineInt.scanline[i + 1] = (iArr[i2] >> 8) & 255;
            i += 3;
            imageLineInt.scanline[i3] = iArr[i2] & 255;
        }
    }

    public static void setPixelRGB8(ImageLineInt imageLineInt, int i, int i2, int i3, int i4) {
        int i5 = i * imageLineInt.imgInfo.channels;
        imageLineInt.scanline[i5] = i2;
        imageLineInt.scanline[i5 + 1] = i3;
        imageLineInt.scanline[i5 + 2] = i4;
    }

    public static void setPixelRGB8(ImageLineInt imageLineInt, int i, int i2) {
        setPixelRGB8(imageLineInt, i, (i2 >> 16) & 255, (i2 >> 8) & 255, i2 & 255);
    }

    public static void setPixelsRGBA8(ImageLineInt imageLineInt, int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < imageLineInt.imgInfo.cols; i2++) {
            imageLineInt.scanline[i] = (iArr[i2] >> 16) & 255;
            imageLineInt.scanline[i + 1] = (iArr[i2] >> 8) & 255;
            int i3 = i + 3;
            imageLineInt.scanline[i + 2] = iArr[i2] & 255;
            i += 4;
            imageLineInt.scanline[i3] = (iArr[i2] >> 24) & 255;
        }
    }

    public static void setPixelRGBA8(ImageLineInt imageLineInt, int i, int i2, int i3, int i4, int i5) {
        int i6 = i * imageLineInt.imgInfo.channels;
        imageLineInt.scanline[i6] = i2;
        imageLineInt.scanline[i6 + 1] = i3;
        imageLineInt.scanline[i6 + 2] = i4;
        imageLineInt.scanline[i6 + 3] = i5;
    }

    public static void setPixelRGBA8(ImageLineInt imageLineInt, int i, int i2) {
        setPixelRGBA8(imageLineInt, i, (i2 >> 16) & 255, (i2 >> 8) & 255, i2 & 255, (i2 >> 24) & 255);
    }

    public static void setValD(ImageLineInt imageLineInt, int i, double d) {
        imageLineInt.scanline[i] = double2int(imageLineInt, d);
    }

    public static double int2double(ImageLineInt imageLineInt, int i) {
        double d;
        double d2;
        if (imageLineInt.imgInfo.bitDepth == 16) {
            d = i;
            d2 = 65535.0d;
        } else {
            d = i;
            d2 = 255.0d;
        }
        return d / d2;
    }

    public static double int2doubleClamped(ImageLineInt imageLineInt, int i) {
        double d;
        double d2;
        if (imageLineInt.imgInfo.bitDepth == 16) {
            d = i;
            d2 = 65535.0d;
        } else {
            d = i;
            d2 = 255.0d;
        }
        double d3 = d / d2;
        double d4 = 0.0d;
        if (d3 > 0.0d) {
            d4 = 1.0d;
            if (d3 < 1.0d) {
                return d3;
            }
        }
        return d4;
    }

    /* JADX WARN: Code duplicated, block: B:4:0x0006 A[PHI: r0
      0x0006: PHI (r0v3 double) = (r0v0 double), (r0v1 double) binds: [B:3:0x0004, B:6:0x000c] A[DONT_GENERATE, DONT_INLINE]] */
    public static int double2int(ImageLineInt imageLineInt, double d) {
        double d2 = 0.0d;
        if (d <= 0.0d) {
            d = d2;
        } else {
            d2 = 1.0d;
            if (d >= 1.0d) {
                d = d2;
            }
        }
        return (int) ((d * (imageLineInt.imgInfo.bitDepth == 16 ? 65535.0d : 255.0d)) + 0.5d);
    }

    /* JADX WARN: Code duplicated, block: B:4:0x0006 A[PHI: r0
      0x0006: PHI (r0v3 double) = (r0v0 double), (r0v1 double) binds: [B:3:0x0004, B:6:0x000c] A[DONT_GENERATE, DONT_INLINE]] */
    public static int double2intClamped(ImageLineInt imageLineInt, double d) {
        double d2 = 0.0d;
        if (d <= 0.0d) {
            d = d2;
        } else {
            d2 = 1.0d;
            if (d >= 1.0d) {
                d = d2;
            }
        }
        return (int) ((d * (imageLineInt.imgInfo.bitDepth == 16 ? 65535.0d : 255.0d)) + 0.5d);
    }
}
