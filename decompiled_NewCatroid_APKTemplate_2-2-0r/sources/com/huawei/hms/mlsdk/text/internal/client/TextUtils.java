package com.huawei.hms.mlsdk.text.internal.client;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.SparseArray;
import com.huawei.hms.ml.common.ocr.ElementParcel;
import com.huawei.hms.ml.common.ocr.LineParcel;
import com.huawei.hms.ml.common.ocr.TextBlockParcel;
import com.huawei.hms.ml.common.ocr.TextDetectorFrameParcel;
import com.huawei.hms.ml.common.ocr.TextParcel;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.text.MLText;
import com.huawei.hms.mlsdk.text.TextLanguage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
final class TextUtils {
    TextUtils() {
    }

    private static Point[] compare(int i, int i2, int i3, int i4, MLText.TextLine textLine) {
        int i5 = textLine.getVertexes()[0].x;
        int i6 = textLine.getVertexes()[0].y;
        double dSin = Math.sin(Math.toRadians(textLine.getRotatingDegree()));
        double dCos = Math.cos(Math.toRadians(textLine.getRotatingDegree()));
        Point[] pointArr = {new Point(i, i2), new Point(i3, i2), new Point(i3, i4), new Point(i, i4)};
        for (int i7 = 0; i7 < 4; i7++) {
            int i8 = (int) ((((double) pointArr[i7].x) * dCos) - (((double) pointArr[i7].y) * dSin));
            int i9 = (int) ((((double) pointArr[i7].y) * dCos) + (((double) pointArr[i7].x) * dSin));
            pointArr[i7].x = i8;
            pointArr[i7].y = i9;
            pointArr[i7].offset(i5, i6);
        }
        return pointArr;
    }

    public static TextDetectorFrameParcel convert(MLFrame mLFrame) {
        TextDetectorFrameParcel textDetectorFrameParcel = new TextDetectorFrameParcel();
        MLFrame.Property propertyAcquireProperty = mLFrame.acquireProperty();
        textDetectorFrameParcel.width = propertyAcquireProperty.getWidth();
        textDetectorFrameParcel.height = propertyAcquireProperty.getHeight();
        textDetectorFrameParcel.format = propertyAcquireProperty.getFormatType();
        textDetectorFrameParcel.rotation = propertyAcquireProperty.getQuadrant();
        ByteBuffer byteBuffer = mLFrame.getByteBuffer();
        if (byteBuffer != null) {
            textDetectorFrameParcel.bytes = byteBuffer.array();
        }
        textDetectorFrameParcel.bitmap = mLFrame.readBitmap();
        return textDetectorFrameParcel;
    }

    static MLText.Word elementParcelToElement(ElementParcel elementParcel) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TextLanguage(elementParcel.getLanguage()));
        return new MLText.Word(elementParcel.getText(), elementParcel.getElementRect(), arrayList, pointsToArray(elementParcel.getCornerPoints()), elementParcel.getConfidence());
    }

    static Point[] getCornerPoints(List<MLText.TextLine> list) {
        if (list.size() == 0) {
            return new Point[0];
        }
        int iMax = Integer.MIN_VALUE;
        int iMin = Integer.MAX_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        int iMax2 = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            for (Point point : compare(list.get(i), list.get(0))) {
                iMin = Math.min(iMin, point.x);
                iMax = Math.max(iMax, point.x);
                iMin2 = Math.min(iMin2, point.y);
                iMax2 = Math.max(iMax2, point.y);
            }
        }
        return compare(iMin, iMin2, iMax, iMax2, list.get(0));
    }

    static MLText.TextLine lineParcelToLine(LineParcel lineParcel) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new TextLanguage(lineParcel.getLanguage()));
        ArrayList arrayList2 = new ArrayList();
        Iterator<ElementParcel> it = lineParcel.getElements().iterator();
        while (it.hasNext()) {
            arrayList2.add(elementParcelToElement(it.next()));
        }
        return new MLText.TextLine(lineParcel.getText(), lineParcel.getLineRect(), arrayList, arrayList2, pointsToArray(lineParcel.getCornerPoints()), lineParcel.getConfidence());
    }

    static MLText.Block lineSparseArrayToBlock(SparseArray<LineParcel> sparseArray) {
        if (sparseArray == null || sparseArray.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int size = sparseArray.size();
        float confidence = 0.0f;
        for (int i = 0; i < size; i++) {
            LineParcel lineParcelValueAt = sparseArray.valueAt(i);
            MLText.TextLine textLineLineParcelToLine = lineParcelToLine(lineParcelValueAt);
            arrayList2.add(textLineLineParcelToLine);
            confidence += lineParcelValueAt.getConfidence();
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb = sb.append(textLineLineParcelToLine.getStringValue());
            arrayList.add(new TextLanguage(lineParcelValueAt.getLanguage()));
        }
        float f = confidence / size;
        return new MLText.Block(sb.toString(), pointsToRect(arrayList2), arrayList, arrayList2, getCornerPoints(arrayList2), f);
    }

    static Point[] pointsToArray(List<Point> list) {
        if (list == null || list.size() <= 0) {
            return new Point[0];
        }
        Point[] pointArr = new Point[list.size()];
        for (int i = 0; i < list.size(); i++) {
            pointArr[i] = list.get(i);
        }
        return pointArr;
    }

    static Rect pointsToRect(List<MLText.TextLine> list) {
        Iterator<MLText.TextLine> it = list.iterator();
        int iMin = Integer.MAX_VALUE;
        int iMax = Integer.MIN_VALUE;
        int iMax2 = Integer.MIN_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        while (it.hasNext()) {
            for (Point point : it.next().getVertexes()) {
                iMin = Math.min(iMin, point.x);
                iMax = Math.max(iMax, point.x);
                iMin2 = Math.min(iMin2, point.y);
                iMax2 = Math.max(iMax2, point.y);
            }
        }
        return new Rect(iMin, iMin2, iMax, iMax2);
    }

    static SparseArray<MLText.Block> textParcelToBlock(TextParcel textParcel) {
        if (textParcel == null || textParcel.getBlocks() == null || textParcel.getBlocks().size() == 0) {
            return new SparseArray<>();
        }
        SparseArray sparseArray = new SparseArray();
        List<TextBlockParcel> blocks = textParcel.getBlocks();
        ArrayList arrayList = new ArrayList();
        Iterator<TextBlockParcel> it = blocks.iterator();
        while (it.hasNext()) {
            List<LineParcel> lines = it.next().getLines();
            if (lines != null && lines.size() != 0) {
                arrayList.addAll(lines);
            }
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            LineParcel lineParcel = (LineParcel) arrayList.get(i);
            SparseArray sparseArray2 = (SparseArray) sparseArray.get(lineParcel.getBlockId());
            if (sparseArray2 == null) {
                sparseArray2 = new SparseArray();
                sparseArray.append(lineParcel.getBlockId(), sparseArray2);
            }
            sparseArray2.append(lineParcel.getLineNum(), lineParcel);
        }
        SparseArray<MLText.Block> sparseArray3 = new SparseArray<>(sparseArray.size());
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            sparseArray3.append(sparseArray.keyAt(i2), lineSparseArrayToBlock((SparseArray) sparseArray.valueAt(i2)));
        }
        return sparseArray3;
    }

    private static Point[] compare(MLText.TextLine textLine, MLText.TextLine textLine2) {
        int i = -textLine2.getVertexes()[0].x;
        int i2 = -textLine2.getVertexes()[0].y;
        double dSin = Math.sin(Math.toRadians(textLine2.getRotatingDegree()));
        double dCos = Math.cos(Math.toRadians(textLine2.getRotatingDegree()));
        Point[] vertexes = textLine.getVertexes();
        Point[] pointArr = new Point[vertexes.length];
        for (int i3 = 0; i3 < vertexes.length; i3++) {
            Point point = new Point(vertexes[i3].x, vertexes[i3].y);
            pointArr[i3] = point;
            point.offset(i, i2);
            int i4 = (int) ((((double) pointArr[i3].y) * dSin) + (((double) pointArr[i3].x) * dCos));
            int i5 = (int) ((((double) pointArr[i3].y) * dCos) + (((double) (-pointArr[i3].x)) * dSin));
            pointArr[i3].x = i4;
            pointArr[i3].y = i5;
        }
        return pointArr;
    }
}
