package org.catrobat.paintroid.ui.zoomwindow;

import android.graphics.Bitmap;
import android.graphics.PointF;
import kotlin.Metadata;
import org.catrobat.paintroid.tools.Tool;

/* JADX INFO: compiled from: ZoomWindowController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\u0007H&J\n\u0010\t\u001a\u0004\u0018\u00010\nH&J\u0018\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH&J\u0012\u0010\u000f\u001a\u00020\u00072\b\u0010\u0010\u001a\u0004\u0018\u00010\nH&J\u0018\u0010\u0011\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH&¨\u0006\u0012"}, d2 = {"Lorg/catrobat/paintroid/ui/zoomwindow/ZoomWindowController;", "", "checkIfToolCompatibleWithZoomWindow", "Lorg/catrobat/paintroid/ui/zoomwindow/DefaultZoomWindowController$Constants;", "tool", "Lorg/catrobat/paintroid/tools/Tool;", "dismiss", "", "dismissOnPinch", "getBitmap", "Landroid/graphics/Bitmap;", "onMove", "drawingSurfaceCoordinates", "Landroid/graphics/PointF;", "displayCoordinates", "setBitmap", "bitmap", "show", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface ZoomWindowController {
    DefaultZoomWindowController.Constants checkIfToolCompatibleWithZoomWindow(Tool tool);

    void dismiss();

    void dismissOnPinch();

    /* JADX INFO: renamed from: getBitmap */
    Bitmap getZoomWindowBitmap();

    void onMove(PointF drawingSurfaceCoordinates, PointF displayCoordinates);

    void setBitmap(Bitmap bitmap);

    void show(PointF drawingSurfaceCoordinates, PointF displayCoordinates);
}
