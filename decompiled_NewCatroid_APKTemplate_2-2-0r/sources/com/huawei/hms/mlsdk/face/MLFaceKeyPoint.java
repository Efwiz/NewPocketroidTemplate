package com.huawei.hms.mlsdk.face;

import android.graphics.PointF;
import com.huawei.hms.mlsdk.common.MLPosition;
import com.huawei.hms.mlsdk.internal.client.Objects;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: loaded from: classes10.dex */
public class MLFaceKeyPoint {
    public static final int TYPE_BOTTOM_OF_MOUTH = 1;
    public static final int TYPE_LEFT_CHEEK = 2;
    public static final int TYPE_LEFT_EAR = 3;
    public static final int TYPE_LEFT_EYE = 5;
    public static final int TYPE_LEFT_SIDE_OF_MOUTH = 6;
    public static final int TYPE_RIGHT_CHEEK = 8;
    public static final int TYPE_RIGHT_EAR = 9;
    public static final int TYPE_RIGHT_EYE = 11;
    public static final int TYPE_RIGHT_SIDE_OF_MOUTH = 12;
    public static final int TYPE_TIP_OF_LEFT_EAR = 4;
    public static final int TYPE_TIP_OF_NOSE = 7;
    public static final int TYPE_TIP_OF_RIGHT_EAR = 10;
    private PointF coordinatePoint;
    private MLPosition point;
    private int type;

    @Retention(RetentionPolicy.CLASS)
    public @interface Type {
    }

    public MLFaceKeyPoint(MLPosition mLPosition, int i) {
        this.coordinatePoint = new PointF(mLPosition.getX().floatValue(), mLPosition.getY().floatValue());
        this.point = mLPosition;
        this.type = i;
    }

    public PointF getCoordinatePoint() {
        return this.coordinatePoint;
    }

    public MLPosition getPoint() {
        return this.point;
    }

    public int getType() {
        return this.type;
    }

    public String toString() {
        return Objects.toStringHelper(this).attribute(ModuleXmlParser.TYPE, Integer.valueOf(this.type)).attribute("position", this.point).toString();
    }
}
