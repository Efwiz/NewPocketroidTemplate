package org.catrobat.catroid.fast2d;

import java.lang.ref.WeakReference;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/fast2d/Fast2DFormulaBridge.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DFormulaBridge {
    private static FastTwoDManager getMgr() {
        StageActivity stageActivity;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null || stageActivity.stageListener == null) {
            return null;
        }
        return stageActivity.stageListener.fastTwoDManager;
    }

    public static Double getX(String id2) {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getPositionX(id2) : 0.0d);
    }

    public static Double getY(String id2) {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getPositionY(id2) : 0.0d);
    }

    public static Double getRotation(String id2) {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getRotation(id2) : 0.0d);
    }

    public static Double getScaleX(String id2) {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getScaleX(id2) : 1.0d);
    }

    public static Double getScaleY(String id2) {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getScaleY(id2) : 1.0d);
    }

    public static Double getColorR(String id2) {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getColorR(id2) : 255.0d);
    }

    public static Double getColorG(String id2) {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getColorG(id2) : 255.0d);
    }

    public static Double getColorB(String id2) {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getColorB(id2) : 255.0d);
    }

    public static Double getAlpha(String id2) {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getAlpha(id2) : 100.0d);
    }

    public static String getTexture(String id2) {
        FastTwoDManager mgr = getMgr();
        return mgr != null ? mgr.getTextureName(id2) : "";
    }

    public static Double getCamX() {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getCamX() : 0.0d);
    }

    public static Double getCamY() {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getCamY() : 0.0d);
    }

    public static Double getCamZoom() {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf(mgr != null ? mgr.getCamZoom() : 1.0d);
    }

    public static Double isTouched(String id2) {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf((mgr == null || !mgr.isEntityTouched(id2, 0)) ? 0.0d : 1.0d);
    }

    public static Double isTouched(String id2, int pointer) {
        FastTwoDManager mgr = getMgr();
        return Double.valueOf((mgr == null || !mgr.isEntityTouched(id2, pointer)) ? 0.0d : 1.0d);
    }
}
