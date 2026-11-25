package org.catrobat.catroid.embroidery;

import io.netty.util.internal.StringUtil;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import org.catrobat.catroid.ProjectManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/DSTHeader.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DSTHeader implements EmbroideryHeader {
    private int colorChangeCount;
    private float firstX;
    private float firstY;
    private float lastX;
    private float lastY;
    private float maxX;
    private float maxY;
    private float minX;
    private float minY;
    private int pointAmount;

    @Override // org.catrobat.catroid.embroidery.EmbroideryHeader
    public void initialize(float currentX, float currentY) {
        this.minX = DSTFileConstants.toEmbroideryUnit(currentX);
        this.maxX = DSTFileConstants.toEmbroideryUnit(currentX);
        this.minY = DSTFileConstants.toEmbroideryUnit(currentY);
        this.maxY = DSTFileConstants.toEmbroideryUnit(currentY);
        this.firstX = DSTFileConstants.toEmbroideryUnit(currentX);
        this.firstY = DSTFileConstants.toEmbroideryUnit(currentY);
        this.lastX = DSTFileConstants.toEmbroideryUnit(currentX);
        this.lastY = DSTFileConstants.toEmbroideryUnit(currentY);
        this.colorChangeCount = 1;
        this.pointAmount = 1;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryHeader
    public void update(float currentX, float currentY) {
        float embroideryUnit = DSTFileConstants.toEmbroideryUnit(currentX);
        float embroideryUnit2 = DSTFileConstants.toEmbroideryUnit(currentY);
        if (this.minX > embroideryUnit) {
            this.minX = embroideryUnit;
        }
        if (this.maxX < embroideryUnit) {
            this.maxX = embroideryUnit;
        }
        if (this.minY > embroideryUnit2) {
            this.minY = embroideryUnit2;
        }
        if (this.maxY < embroideryUnit2) {
            this.maxY = embroideryUnit2;
        }
        this.lastX = embroideryUnit;
        this.lastY = embroideryUnit2;
        this.pointAmount++;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryHeader
    public void addColorChange() {
        this.colorChangeCount++;
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryHeader
    public void appendToStream(FileOutputStream fileStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        String name2 = ProjectManager.getInstance().getCurrentProject().getName();
        if (name2.length() > 15) {
            name2 = name2.substring(0, 15);
        }
        sb.append(String.format(DSTFileConstants.DST_HEADER_LABEL, name2)).append(String.format(Locale.getDefault(), DSTFileConstants.DST_HEADER, Integer.valueOf(this.pointAmount), Integer.valueOf(this.colorChangeCount), Integer.valueOf((int) this.maxX), Integer.valueOf((int) this.minX), Integer.valueOf((int) this.maxY), Integer.valueOf((int) this.minY), Integer.valueOf((int) (this.lastX - this.firstX)), Integer.valueOf((int) (this.lastY - this.firstY)), 0, 0, "*****").replace(StringUtil.SPACE, (char) 0)).append(DSTFileConstants.HEADER_FILL);
        fileStream.write(sb.toString().getBytes());
    }

    @Override // org.catrobat.catroid.embroidery.EmbroideryHeader
    public void reset() {
        this.colorChangeCount = 1;
    }
}
