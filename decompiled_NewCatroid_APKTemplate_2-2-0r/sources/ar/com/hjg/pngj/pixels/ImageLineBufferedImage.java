package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.IImageLine;
import ar.com.hjg.pngj.IImageLineFactory;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineByte;
import java.awt.image.BufferedImage;

/* JADX INFO: loaded from: classes.dex */
public class ImageLineBufferedImage implements IImageLine {
    private boolean bgrOrder;
    private byte[] bytes;
    private boolean hasAlpha;
    private final BufferedImage image;
    public final ImageInfo imgInfo;
    private int rowLength;
    private int rowNumber = -1;

    public ImageLineBufferedImage(ImageInfo imageInfo, BufferedImage bufferedImage, byte[] bArr) {
        this.imgInfo = imageInfo;
        this.image = bufferedImage;
        this.bytes = bArr;
        boolean zHasAlpha = bufferedImage.getColorModel().hasAlpha();
        this.hasAlpha = zHasAlpha;
        if (zHasAlpha) {
            this.rowLength = bufferedImage.getWidth() * 4;
        } else {
            this.rowLength = bufferedImage.getWidth() * 3;
        }
        this.bgrOrder = bufferedImage.getSampleModel().getBandOffsets()[0] != 0;
    }

    public static IImageLineFactory<ImageLineByte> getFactory(ImageInfo imageInfo) {
        return new IImageLineFactory<ImageLineByte>() { // from class: ar.com.hjg.pngj.pixels.ImageLineBufferedImage.1
            @Override // ar.com.hjg.pngj.IImageLineFactory
            public ImageLineByte createImageLine(ImageInfo imageInfo2) {
                return new ImageLineByte(imageInfo2);
            }
        };
    }

    @Override // ar.com.hjg.pngj.IImageLine
    public void readFromPngRaw(byte[] bArr, int i, int i2, int i3) {
        throw new RuntimeException("not implemented");
    }

    @Override // ar.com.hjg.pngj.IImageLine
    public void writeToPngRaw(byte[] bArr) {
        if (this.imgInfo.bytesPerRow != this.rowLength) {
            throw new RuntimeException("??");
        }
        int i = this.rowNumber;
        if (i < 0 || i >= this.imgInfo.rows) {
            throw new RuntimeException("???");
        }
        int i2 = this.rowLength * this.rowNumber;
        int i3 = 1;
        if (this.hasAlpha) {
            if (this.bgrOrder) {
                while (i3 <= this.rowLength) {
                    byte[] bArr2 = this.bytes;
                    byte b = bArr2[i2];
                    byte b2 = bArr2[i2 + 1];
                    int i4 = i2 + 3;
                    byte b3 = bArr2[i2 + 2];
                    i2 += 4;
                    bArr[i3] = bArr2[i4];
                    bArr[i3 + 1] = b3;
                    int i5 = i3 + 3;
                    bArr[i3 + 2] = b2;
                    i3 += 4;
                    bArr[i5] = b;
                }
                return;
            }
            while (i3 <= this.rowLength) {
                byte[] bArr3 = this.bytes;
                bArr[i3] = bArr3[i2];
                bArr[i3 + 1] = bArr3[i2 + 1];
                int i6 = i3 + 3;
                int i7 = i2 + 3;
                bArr[i3 + 2] = bArr3[i2 + 2];
                i3 += 4;
                i2 += 4;
                bArr[i6] = bArr3[i7];
            }
            return;
        }
        if (this.bgrOrder) {
            while (i3 <= this.rowLength) {
                byte[] bArr4 = this.bytes;
                byte b4 = bArr4[i2];
                int i8 = i2 + 2;
                byte b5 = bArr4[i2 + 1];
                i2 += 3;
                bArr[i3] = bArr4[i8];
                int i9 = i3 + 2;
                bArr[i3 + 1] = b5;
                i3 += 3;
                bArr[i9] = b4;
            }
            return;
        }
        while (i3 <= this.rowLength) {
            byte[] bArr5 = this.bytes;
            bArr[i3] = bArr5[i2];
            int i10 = i3 + 2;
            int i11 = i2 + 2;
            bArr[i3 + 1] = bArr5[i2 + 1];
            i3 += 3;
            i2 += 3;
            bArr[i10] = bArr5[i11];
        }
    }

    @Override // ar.com.hjg.pngj.IImageLine
    public void endReadFromPngRaw() {
        throw new RuntimeException("not implemented");
    }

    public int getRowNumber() {
        return this.rowNumber;
    }

    public void setRowNumber(int i) {
        this.rowNumber = i;
    }
}
