package ar.com.hjg.pngj;

import ar.com.hjg.pngj.IImageLine;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class ImageLineSetDefault<T extends IImageLine> implements IImageLineSet<T> {
    protected int currentRow = -1;
    protected T imageLine;
    protected List<T> imageLines;
    protected final ImageInfo imgInfo;
    private final int nlines;
    private final int offset;
    private final boolean singleCursor;
    private final int step;

    protected abstract T createImageLine();

    public ImageLineSetDefault(ImageInfo imageInfo, boolean z, int i, int i2, int i3) {
        this.imgInfo = imageInfo;
        this.singleCursor = z;
        if (z) {
            this.nlines = 1;
            this.offset = 0;
            this.step = 1;
        } else {
            this.nlines = imageInfo.rows;
            this.offset = 0;
            this.step = 1;
        }
        createImageLines();
    }

    private void createImageLines() {
        if (this.singleCursor) {
            this.imageLine = (T) createImageLine();
            return;
        }
        this.imageLines = new ArrayList();
        for (int i = 0; i < this.nlines; i++) {
            this.imageLines.add((T) createImageLine());
        }
    }

    @Override // ar.com.hjg.pngj.IImageLineSet
    public T getImageLine(int i) {
        this.currentRow = i;
        if (this.singleCursor) {
            return this.imageLine;
        }
        return this.imageLines.get(imageRowToMatrixRowStrict(i));
    }

    @Override // ar.com.hjg.pngj.IImageLineSet
    public boolean hasImageLine(int i) {
        if (this.singleCursor) {
            if (this.currentRow == i) {
                return true;
            }
        } else if (imageRowToMatrixRowStrict(i) >= 0) {
            return true;
        }
        return false;
    }

    @Override // ar.com.hjg.pngj.IImageLineSet
    public int size() {
        return this.nlines;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x000e  */
    public int imageRowToMatrixRowStrict(int i) {
        int i2;
        int i3 = i - this.offset;
        if (i3 >= 0) {
            int i4 = this.step;
            if (i3 % i4 == 0) {
                i2 = i3 / i4;
            } else {
                i2 = -1;
            }
        } else {
            i2 = -1;
        }
        if (i2 < this.nlines) {
            return i2;
        }
        return -1;
    }

    public int matrixRowToImageRow(int i) {
        return (i * this.step) + this.offset;
    }

    public int imageRowToMatrixRow(int i) {
        int i2 = (i - this.offset) / this.step;
        if (i2 < 0) {
            return 0;
        }
        int i3 = this.nlines;
        return i2 < i3 ? i2 : i3 - 1;
    }

    public static IImageLineSetFactory<ImageLineInt> getFactoryInt() {
        return new IImageLineSetFactory<ImageLineInt>() { // from class: ar.com.hjg.pngj.ImageLineSetDefault.1
            @Override // ar.com.hjg.pngj.IImageLineSetFactory
            public IImageLineSet<ImageLineInt> create(ImageInfo imageInfo, boolean z, int i, int i2, int i3) {
                return new ImageLineSetDefault<ImageLineInt>(imageInfo, z, i, i2, i3) { // from class: ar.com.hjg.pngj.ImageLineSetDefault.1.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // ar.com.hjg.pngj.ImageLineSetDefault
                    public ImageLineInt createImageLine() {
                        return new ImageLineInt(this.imgInfo);
                    }
                };
            }
        };
    }

    public static IImageLineSetFactory<ImageLineByte> getFactoryByte() {
        return new IImageLineSetFactory<ImageLineByte>() { // from class: ar.com.hjg.pngj.ImageLineSetDefault.2
            @Override // ar.com.hjg.pngj.IImageLineSetFactory
            public IImageLineSet<ImageLineByte> create(ImageInfo imageInfo, boolean z, int i, int i2, int i3) {
                return new ImageLineSetDefault<ImageLineByte>(imageInfo, z, i, i2, i3) { // from class: ar.com.hjg.pngj.ImageLineSetDefault.2.1
                    /* JADX INFO: Access modifiers changed from: protected */
                    @Override // ar.com.hjg.pngj.ImageLineSetDefault
                    public ImageLineByte createImageLine() {
                        return new ImageLineByte(this.imgInfo);
                    }
                };
            }
        };
    }
}
