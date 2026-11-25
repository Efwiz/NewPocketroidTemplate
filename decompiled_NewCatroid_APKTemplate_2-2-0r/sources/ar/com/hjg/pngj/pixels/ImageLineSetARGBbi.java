package ar.com.hjg.pngj.pixels;

import ar.com.hjg.pngj.IImageLineSet;
import ar.com.hjg.pngj.ImageInfo;
import java.awt.image.BufferedImage;

/* JADX INFO: loaded from: classes.dex */
public class ImageLineSetARGBbi implements IImageLineSet<ImageLineARGBbi> {
    BufferedImage image;
    private ImageInfo imginfo;
    private ImageLineARGBbi line;

    @Override // ar.com.hjg.pngj.IImageLineSet
    public int size() {
        return 1;
    }

    public ImageLineSetARGBbi(BufferedImage bufferedImage, ImageInfo imageInfo) {
        this.image = bufferedImage;
        this.imginfo = imageInfo;
        this.line = new ImageLineARGBbi(imageInfo, bufferedImage, this.image.getRaster().getDataBuffer().getData());
    }

    @Override // ar.com.hjg.pngj.IImageLineSet
    public ImageLineARGBbi getImageLine(int i) {
        this.line.setRowNumber(i);
        return this.line;
    }

    @Override // ar.com.hjg.pngj.IImageLineSet
    public boolean hasImageLine(int i) {
        return i >= 0 && i < this.imginfo.rows;
    }
}
