package com.google.mlkit.vision.text.internal;

import android.graphics.Point;
import android.graphics.Rect;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
final class zza {
    static Rect zza(List<Point> list) {
        int iMin = Integer.MAX_VALUE;
        int iMax = Integer.MIN_VALUE;
        int iMax2 = Integer.MIN_VALUE;
        int iMin2 = Integer.MAX_VALUE;
        for (Point point : list) {
            iMin = Math.min(iMin, point.x);
            iMax = Math.max(iMax, point.x);
            iMin2 = Math.min(iMin2, point.y);
            iMax2 = Math.max(iMax2, point.y);
        }
        return new Rect(iMin, iMin2, iMax, iMax2);
    }

    static List<Point> zzb(com.google.android.gms.internal.mlkit_vision_text.zzf zzfVar) {
        Point[] pointArr = new Point[4];
        double dSin = Math.sin(Math.toRadians(zzfVar.zze));
        double dCos = Math.cos(Math.toRadians(zzfVar.zze));
        pointArr[0] = new Point(zzfVar.zza, zzfVar.zzb);
        int i = zzfVar.zza;
        double d = zzfVar.zzc;
        Point point = new Point((int) (((double) i) + (d * dCos)), (int) (((double) zzfVar.zzb) + (d * dSin)));
        pointArr[1] = point;
        pointArr[2] = new Point((int) (((double) point.x) - (((double) zzfVar.zzd) * dSin)), (int) (((double) pointArr[1].y) + (((double) zzfVar.zzd) * dCos)));
        pointArr[3] = new Point(pointArr[0].x + (pointArr[2].x - pointArr[1].x), pointArr[0].y + (pointArr[2].y - pointArr[1].y));
        return Arrays.asList(pointArr);
    }
}
