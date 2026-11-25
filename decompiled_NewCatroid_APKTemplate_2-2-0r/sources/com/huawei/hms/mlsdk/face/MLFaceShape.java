package com.huawei.hms.mlsdk.face;

import android.graphics.PointF;
import com.huawei.hms.mlsdk.common.MLPosition;
import com.huawei.hms.mlsdk.internal.client.Objects;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: loaded from: classes10.dex */
public class MLFaceShape {
    public static final int TYPE_ALL = 0;
    public static final int TYPE_BOTTOM_OF_LEFT_EYEBROW = 4;
    public static final int TYPE_BOTTOM_OF_LOWER_LIP = 8;
    public static final int TYPE_BOTTOM_OF_NOSE = 12;
    public static final int TYPE_BOTTOM_OF_RIGHT_EYEBROW = 5;
    public static final int TYPE_BOTTOM_OF_UPPER_LIP = 10;
    public static final int TYPE_BRIDGE_OF_NOSE = 13;
    public static final int TYPE_FACE = 1;
    public static final int TYPE_LEFT_EYE = 2;
    public static final int TYPE_RIGHT_EYE = 3;
    public static final int TYPE_TOP_OF_LEFT_EYEBROW = 6;
    public static final int TYPE_TOP_OF_LOWER_LIP = 9;
    public static final int TYPE_TOP_OF_RIGHT_EYEBROW = 7;
    public static final int TYPE_TOP_OF_UPPER_LIP = 11;
    private PointF[] coordinatePoints;
    private int faceShapeType;
    private List<MLPosition> points;

    @Retention(RetentionPolicy.CLASS)
    public @interface ShapeType {
    }

    public MLFaceShape(int i, List<MLPosition> list) {
        this.coordinatePoints = new PointF[list.size()];
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.coordinatePoints[i2] = new PointF(list.get(i2).getX().floatValue(), list.get(i2).getY().floatValue());
        }
        this.faceShapeType = i;
        this.points = list;
    }

    public PointF[] getCoordinatePoints() {
        PointF[] pointFArr = this.coordinatePoints;
        if (pointFArr != null && pointFArr.length > 0) {
            return (PointF[]) Arrays.copyOf(pointFArr, pointFArr.length);
        }
        List<MLPosition> list = this.points;
        if (list == null || list.size() <= 0) {
            return new PointF[0];
        }
        PointF[] pointFArr2 = new PointF[this.points.size()];
        for (int i = 0; i < this.points.size(); i++) {
            if (this.points.get(i) != null) {
                pointFArr2[i] = new PointF(this.points.get(i).getX().floatValue(), this.points.get(i).getY().floatValue());
            }
        }
        return pointFArr2;
    }

    public int getFaceShapeType() {
        return this.faceShapeType;
    }

    public List<MLPosition> getPoints() {
        return this.points;
    }

    public String toString() {
        return Objects.toStringHelper(this).attribute(ModuleXmlParser.TYPE, Integer.valueOf(this.faceShapeType)).attribute("position", this.points).toString();
    }
}
