package org.catrobat.catroid.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NativeLookOptimizer.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NativeLookOptimizer {
    public static Boolean isWorking;

    public static native int[] checkAllCollisions(float[][][] allSpritesPolygons);

    public static native boolean checkSingleCollision(float[][] firstLookPolygons, float[][] secondLookPolygons);

    public static native float[] getTransformedBoundingBox(float x, float y, float width, float height, float scaleX, float scaleY, float rotation, float originX, float originY);

    public static native float[] transformPolygon(float[] vertices, float x, float y, float scaleX, float scaleY, float rotation, float originX, float originY);

    static {
        isWorking = true;
        if (NativeLibraryManager.INSTANCE.isLoaded(NativeLibraryManager.Feature.CORE)) {
            return;
        }
        isWorking = false;
    }
}
