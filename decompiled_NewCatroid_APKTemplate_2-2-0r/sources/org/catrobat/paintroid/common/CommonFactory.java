package org.catrobat.paintroid.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.mgsx.gltf.data.extensions.KHRLightsPunctual;
import org.catrobat.paintroid.command.serialization.SerializablePath;

/* JADX INFO: compiled from: CommonFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\rJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0006J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014J\u0010\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0017J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0016¨\u0006\u001c"}, d2 = {"Lorg/catrobat/paintroid/common/CommonFactory;", "", "()V", "createBitmap", "Landroid/graphics/Bitmap;", "width", "", "height", "config", "Landroid/graphics/Bitmap$Config;", "createCanvas", "Landroid/graphics/Canvas;", "createPaint", "Landroid/graphics/Paint;", "paint", "createPoint", "Landroid/graphics/Point;", "x", "y", "createPointF", "Landroid/graphics/PointF;", KHRLightsPunctual.GLTFLight.TYPE_POINT, "createRectF", "Landroid/graphics/RectF;", "rect", "createSerializablePath", "Lorg/catrobat/paintroid/command/serialization/SerializablePath;", "path", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class CommonFactory {
    public Canvas createCanvas() {
        return new Canvas();
    }

    public Bitmap createBitmap(int width, int height, Bitmap.Config config) {
        Intrinsics.checkNotNullParameter(config, "config");
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, config);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(width, height, config)");
        return bitmapCreateBitmap;
    }

    public final Paint createPaint(Paint paint) {
        return new Paint(paint);
    }

    public final PointF createPointF(PointF point) {
        Intrinsics.checkNotNullParameter(point, "point");
        return new PointF(point.x, point.y);
    }

    public final Point createPoint(int x, int y) {
        return new Point(x, y);
    }

    public SerializablePath createSerializablePath(SerializablePath path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new SerializablePath(path);
    }

    public final RectF createRectF(RectF rect) {
        return new RectF(rect);
    }
}
