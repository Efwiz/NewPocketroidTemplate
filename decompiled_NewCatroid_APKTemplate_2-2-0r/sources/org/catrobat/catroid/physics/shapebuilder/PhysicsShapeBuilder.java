package org.catrobat.catroid.physics.shapebuilder;

import android.util.Log;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.physics.box2d.Shape;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/shapebuilder/PhysicsShapeBuilder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class PhysicsShapeBuilder {
    private static final String TAG = "PhysicsShapeBuilder";
    private static final float[] ACCURACY_LEVELS = {0.125f, 0.25f, 0.5f, 0.75f, 1.0f};
    private static PhysicsShapeBuilder instance = null;
    private PhysicsShapeBuilderStrategy strategy = new PhysicsShapeBuilderStrategyFastHull();
    private Map<String, ImageShapes> imageShapesMap = new HashMap();

    public static PhysicsShapeBuilder getInstance() {
        if (instance == null) {
            instance = new PhysicsShapeBuilder();
        }
        return instance;
    }

    private PhysicsShapeBuilder() {
    }

    public void reset() {
        this.strategy = new PhysicsShapeBuilderStrategyFastHull();
        this.imageShapesMap = new HashMap();
    }

    public synchronized Shape[] getScaledShapes(LookData lookData, float scaleFactor) throws RuntimeException {
        try {
            if (scaleFactor < 0.0f) {
                throw new RuntimeException("scaleFactor can not be smaller than 0");
            }
            if (lookData == null) {
                throw new RuntimeException("get shape for null lookData not possible");
            }
            Pixmap pixmap = lookData.getPixmap();
            if (pixmap == null) {
                Log.e(TAG, "pixmap should not be null");
                return null;
            }
            String strMd5Checksum = Utils.md5Checksum(lookData.getFile());
            if (!this.imageShapesMap.containsKey(strMd5Checksum)) {
                this.imageShapesMap.put(strMd5Checksum, new ImageShapes(pixmap));
            }
            Shape[] shapes = this.imageShapesMap.get(strMd5Checksum).getShapes(getAccuracyLevel(scaleFactor));
            if (shapes == null) {
                Log.e(TAG, "shapes should not be null");
                return null;
            }
            return PhysicsShapeScaleUtils.scaleShapes(shapes, scaleFactor);
        } catch (Throwable th) {
            throw th;
        }
    }

    private static float getAccuracyLevel(float scaleFactor) {
        float[] fArr = ACCURACY_LEVELS;
        if (fArr.length == 0) {
            return 0.0f;
        }
        int i = 0;
        if (fArr.length == 1) {
            return fArr[0];
        }
        while (true) {
            float[] fArr2 = ACCURACY_LEVELS;
            if (i < fArr2.length - 1) {
                float f = fArr2[i];
                if (scaleFactor < (f + f) / 2.0f) {
                    return f;
                }
                i++;
            } else {
                return fArr2[fArr2.length - 1];
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/shapebuilder/PhysicsShapeBuilder$ImageShapes.class
      classes14.dex
     */
    private class ImageShapes {
        private static final int MAX_ORIGINAL_PIXMAP_SIZE = 512;
        private Pixmap pixmap;
        private Map<String, Shape[]> shapeMap = new HashMap();
        private float sizeAdjustmentScaleFactor;

        ImageShapes(Pixmap pixmap) {
            this.sizeAdjustmentScaleFactor = 1.0f;
            if (pixmap == null) {
                throw new RuntimeException("Pixmap must not null");
            }
            this.pixmap = pixmap;
            int width = pixmap.getWidth();
            int height = this.pixmap.getHeight();
            if (width > 512 || height > 512) {
                if (width > height) {
                    this.sizeAdjustmentScaleFactor = 512.0f / width;
                } else {
                    this.sizeAdjustmentScaleFactor = 512.0f / height;
                }
            }
        }

        private String getShapeKey(float accuracyLevel) {
            return String.valueOf((int) (accuracyLevel * 100.0f));
        }

        private Shape[] computeNewShape(float accuracy) {
            int width = this.pixmap.getWidth();
            int height = this.pixmap.getHeight();
            int iRound = Math.round(width * this.sizeAdjustmentScaleFactor * accuracy);
            int iRound2 = Math.round(height * this.sizeAdjustmentScaleFactor * accuracy);
            int i = iRound < 1 ? 1 : iRound;
            int i2 = iRound2 < 1 ? 1 : iRound2;
            Pixmap pixmap = new Pixmap(i, i2, this.pixmap.getFormat());
            pixmap.setFilter(Pixmap.Filter.NearestNeighbour);
            pixmap.drawPixmap(this.pixmap, 0, 0, width, height, 0, 0, i, i2);
            return PhysicsShapeScaleUtils.scaleShapes(PhysicsShapeBuilder.this.strategy.build(pixmap, 1.0f), 1.0f, this.sizeAdjustmentScaleFactor * accuracy);
        }

        public Shape[] getShapes(float accuracyLevel) throws RuntimeException {
            String shapeKey = getShapeKey(accuracyLevel);
            if (!this.shapeMap.containsKey(shapeKey)) {
                Shape[] shapeArrComputeNewShape = computeNewShape(accuracyLevel);
                if (shapeArrComputeNewShape == null) {
                    return null;
                }
                this.shapeMap.put(shapeKey, shapeArrComputeNewShape);
            }
            return this.shapeMap.get(shapeKey);
        }
    }
}
