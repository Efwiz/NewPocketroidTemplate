package org.catrobat.paintroid.tools.helper;

import android.graphics.PointF;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import org.catrobat.paintroid.command.serialization.SerializablePath;

/* JADX INFO: compiled from: AdvancedSettingsAlgorithms.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/paintroid/tools/helper/AdvancedSettingsAlgorithms;", "", "()V", "divider", "", "smoothing", "", "threshold", "", "smoothingAlgorithm", "Lorg/catrobat/paintroid/command/serialization/SerializablePath;", "pointArray", "", "Landroid/graphics/PointF;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class AdvancedSettingsAlgorithms {
    public static final int divider = 3;
    public static final double threshold = 0.2d;
    public static final AdvancedSettingsAlgorithms INSTANCE = new AdvancedSettingsAlgorithms();
    public static boolean smoothing = true;

    private AdvancedSettingsAlgorithms() {
    }

    @JvmStatic
    public static final SerializablePath smoothingAlgorithm(List<? extends PointF> pointArray) {
        int size;
        Intrinsics.checkNotNullParameter(pointArray, "pointArray");
        ArrayList arrayList = new ArrayList();
        if (pointArray.size() > 1 && (size = pointArray.size() - 1) >= 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                if (i >= 0) {
                    PointF pointF = pointArray.get(i);
                    if (i == 0) {
                        PointF pointF2 = pointArray.get(i2);
                        float f = 3;
                        arrayList.add(new PointF((pointF2.x - pointF.x) / f, (pointF2.y - pointF.y) / f));
                    } else if (i == pointArray.size() - 1) {
                        PointF pointF3 = pointArray.get(i - 1);
                        float f2 = 3;
                        arrayList.add(new PointF((pointF.x - pointF3.x) / f2, (pointF.y - pointF3.y) / f2));
                    } else {
                        PointF pointF4 = pointArray.get(i2);
                        PointF pointF5 = pointArray.get(i - 1);
                        float f3 = 3;
                        arrayList.add(new PointF((pointF4.x - pointF5.x) / f3, (pointF4.y - pointF5.y) / f3));
                    }
                }
                if (i2 > size) {
                    break;
                }
                i = i2;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new PointF(pointArray.get(0).x, pointArray.get(0).y));
        int size2 = pointArray.size();
        if (1 < size2) {
            int i3 = 1;
            while (true) {
                int i4 = i3 + 1;
                PointF pointF6 = pointArray.get(i3);
                PointF pointF7 = (PointF) arrayList.get(i3);
                arrayList2.add(new PointF(pointF6.x + pointF7.x, pointF6.y + pointF7.y));
                if (i4 >= size2) {
                    break;
                }
                i3 = i4;
            }
        }
        SerializablePath serializablePath = new SerializablePath();
        serializablePath.incReserve(1);
        serializablePath.moveTo(((PointF) arrayList2.get(0)).x, ((PointF) arrayList2.get(0)).y);
        IntProgression intProgressionStep = RangesKt.step(RangesKt.until(1, arrayList2.size() - 1), 2);
        int first = intProgressionStep.getFirst();
        int last = intProgressionStep.getLast();
        int step = intProgressionStep.getStep();
        if ((step > 0 && first <= last) || (step < 0 && last <= first)) {
            while (true) {
                int i5 = first + step;
                PointF pointF8 = (PointF) arrayList2.get(first);
                PointF pointF9 = (PointF) arrayList2.get(first - 1);
                PointF pointF10 = (PointF) arrayList2.get(first + 1);
                serializablePath.cubicTo(pointF9.x, pointF9.y, pointF8.x, pointF8.y, pointF10.x, pointF10.y);
                serializablePath.incReserve(1);
                if (first == last) {
                    break;
                }
                first = i5;
            }
        }
        arrayList2.clear();
        arrayList.clear();
        return serializablePath;
    }
}
