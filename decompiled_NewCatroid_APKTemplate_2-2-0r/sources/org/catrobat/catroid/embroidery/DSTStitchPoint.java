package org.catrobat.catroid.embroidery;

import com.badlogic.gdx.graphics.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import org.luaj.vm2.Lua;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/DSTStitchPoint.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DSTStitchPoint implements StitchPoint {
    private Color color;
    private int relativeX;
    private int relativeY;
    private float xCoord;
    private float yCoord;
    private boolean jumpPoint = false;
    private boolean colorChangePoint = false;

    public DSTStitchPoint(float x, float y, Color color) {
        this.xCoord = x;
        this.yCoord = y;
        this.color = color;
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public float getX() {
        return this.xCoord;
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public float getY() {
        return this.yCoord;
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public Color getColor() {
        return this.color;
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public void appendToStream(FileOutputStream fileStream) throws IOException {
        fileStream.write(setDSTPointBytes());
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public void setJump(boolean jumpPoint) {
        this.jumpPoint = jumpPoint;
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public boolean isJumpPoint() {
        return this.jumpPoint;
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public void setColorChange(boolean colorChangePoint) {
        this.colorChangePoint = colorChangePoint;
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public void setColor(Color color) {
        this.color = color;
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public boolean isColorChangePoint() {
        return this.colorChangePoint;
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public void setRelativeCoordinatesToPreviousPoint(float previousX, float previousY) {
        this.relativeX = DSTFileConstants.toEmbroideryUnit(this.xCoord) - DSTFileConstants.toEmbroideryUnit(previousX);
        this.relativeY = DSTFileConstants.toEmbroideryUnit(this.yCoord) - DSTFileConstants.toEmbroideryUnit(previousY);
    }

    private int mapToConversionTable(int value) {
        if (value < 0) {
            return DSTFileConstants.CONVERSION_TABLE[(value * (-1)) + 121];
        }
        return DSTFileConstants.CONVERSION_TABLE[value];
    }

    private byte[] setDSTPointBytes() {
        byte[] bArr = {0, 0, 3};
        int iMapToConversionTable = mapToConversionTable(this.relativeX);
        int iMapToConversionTable2 = mapToConversionTable(this.relativeY);
        int i = iMapToConversionTable >>> 2;
        bArr[0] = (byte) (((char) ((((((iMapToConversionTable2 & 1) << 3) | ((iMapToConversionTable2 & 2) << 1)) | ((iMapToConversionTable2 & 16) >>> 3)) | ((iMapToConversionTable2 & 32) >>> 5)) << 4)) | ((char) ((i & 12) | (iMapToConversionTable & 3))));
        bArr[1] = (byte) (((char) ((((((iMapToConversionTable2 & 4) << 1) | ((iMapToConversionTable2 & 8) >>> 1)) | ((iMapToConversionTable2 & 64) >>> 5)) | ((iMapToConversionTable2 & 128) >>> 7)) << 4)) | ((char) (((iMapToConversionTable >>> 4) & 12) | (i & 3))));
        byte b = (byte) (((char) (((iMapToConversionTable2 >>> 3) & 32) | ((iMapToConversionTable2 >>> 5) & 16))) | bArr[2] | ((char) ((iMapToConversionTable >>> 6) & 12)));
        bArr[2] = b;
        if (this.jumpPoint) {
            bArr[2] = (byte) (b | 128);
        }
        if (this.colorChangePoint) {
            bArr[2] = (byte) (((char) bArr[2]) | 192);
        }
        return bArr;
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public boolean isConnectingPoint() {
        return (this.jumpPoint || this.colorChangePoint) ? false : true;
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public boolean equals(Object object) {
        if (!(object instanceof DSTStitchPoint)) {
            return false;
        }
        DSTStitchPoint dSTStitchPoint = (DSTStitchPoint) object;
        return dSTStitchPoint.getX() == this.xCoord && dSTStitchPoint.getY() == this.yCoord && isConnectingPoint() && dSTStitchPoint.isConnectingPoint();
    }

    @Override // org.catrobat.catroid.embroidery.StitchPoint
    public int hashCode() {
        int i = ((int) this.xCoord) & Lua.MASK_NOT_Bx;
        int i2 = (((int) this.yCoord) & Lua.MASK_NOT_Bx) << 15;
        int i3 = this.jumpPoint ? 536870912 : 0;
        if (this.colorChangePoint) {
            i3 = 1610612736;
        }
        return i | i2 | i3;
    }
}
