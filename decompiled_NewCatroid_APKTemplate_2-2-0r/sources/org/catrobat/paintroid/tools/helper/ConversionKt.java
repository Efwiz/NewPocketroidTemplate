package org.catrobat.paintroid.tools.helper;

import android.graphics.Point;
import android.graphics.PointF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;

/* JADX INFO: compiled from: Conversion.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"toPoint", "Landroid/graphics/Point;", KHRLightsPunctual.GLTFLight.TYPE_POINT, "Landroid/graphics/PointF;", "Paintroid_signedRelease"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class ConversionKt {
    public static final Point toPoint(PointF point) {
        Intrinsics.checkNotNullParameter(point, "point");
        return new Point((int) point.x, (int) point.y);
    }
}
