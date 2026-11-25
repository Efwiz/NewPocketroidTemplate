package com.huawei.hms.mlsdk.face;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MLFaceAnalyzerSetting {
    public static final int MODE_TRACING_FAST = 1;
    public static final int MODE_TRACING_ROBUST = 2;
    public static final int TYPE_FEATURES = 1;
    public static final int TYPE_FEATURE_AGE = 256;
    public static final int TYPE_FEATURE_BEARD = 32;
    public static final int TYPE_FEATURE_EMOTION = 4;
    public static final int TYPE_FEATURE_EYEGLASS = 8;
    public static final int TYPE_FEATURE_GENDAR = 128;
    public static final int TYPE_FEATURE_HAT = 16;
    public static final int TYPE_FEATURE_OPENCLOSEEYE = 64;
    public static final int TYPE_KEYPOINTS = 1;
    public static final int TYPE_PRECISION = 1;
    public static final int TYPE_SHAPES = 2;
    public static final int TYPE_SPEED = 2;
    public static final int TYPE_UNSUPPORT_FEATURES = 2;
    public static final int TYPE_UNSUPPORT_KEYPOINTS = 0;
    public static final int TYPE_UNSUPPORT_SHAPES = 3;
    private final int featureType;
    private final boolean isMaxSizeFaceOnly;
    private final boolean isPoseDisabled;
    private final boolean isTracingAllowed;
    private final int keyPointType;
    private final float minFaceProportion;
    private final int performanceType;
    private final int shapeType;
    private final int tracingMode;

    public static class Factory {
        private int keyPointType = 1;
        private int shapeType = 2;
        private int featureType = 1;
        private int performanceType = 1;
        private boolean isPoseDisabled = false;
        private boolean isTracingAllowed = false;
        private int tracingMode = 2;
        private float minFaceProportion = 0.1f;
        private boolean isMaxSizeFaceOnly = false;

        public Factory allowTracing() {
            return allowTracing(2);
        }

        public MLFaceAnalyzerSetting create() {
            return new MLFaceAnalyzerSetting(this.keyPointType, this.shapeType, this.featureType, this.performanceType, this.isPoseDisabled, this.isTracingAllowed, this.tracingMode, this.isMaxSizeFaceOnly, this.minFaceProportion);
        }

        public Factory setFeatureType(int i) {
            if (i != 1 && i != 2) {
                i = 1;
            }
            this.featureType = i;
            return this;
        }

        public Factory setKeyPointType(int i) {
            this.keyPointType = i;
            return this;
        }

        public Factory setMaxSizeFaceOnly(boolean z) {
            this.isMaxSizeFaceOnly = z;
            return this;
        }

        public Factory setMinFaceProportion(float f) {
            this.minFaceProportion = f;
            return this;
        }

        public Factory setPerformanceType(int i) {
            this.performanceType = i;
            return this;
        }

        public Factory setPoseDisabled(boolean z) {
            this.isPoseDisabled = z;
            return this;
        }

        public Factory setShapeType(int i) {
            this.shapeType = i;
            return this;
        }

        public Factory setTracingAllowed(boolean z) {
            return setTracingAllowed(z, 2);
        }

        public Factory allowTracing(int i) {
            this.isTracingAllowed = true;
            this.tracingMode = i;
            return this;
        }

        public Factory setTracingAllowed(boolean z, int i) {
            this.isTracingAllowed = z;
            if (z) {
                this.tracingMode = i;
            }
            return this;
        }
    }

    @Retention(RetentionPolicy.CLASS)
    public @interface FeatureType {
    }

    @Retention(RetentionPolicy.CLASS)
    public @interface KeyPointType {
    }

    @Retention(RetentionPolicy.CLASS)
    public @interface PerformanceType {
    }

    @Retention(RetentionPolicy.CLASS)
    public @interface ShapeType {
    }

    public MLFaceAnalyzerSetting(int i, int i2, int i3, int i4, boolean z, boolean z2, float f) {
        this(i, i2, i3, i4, false, z, 2, z2, f);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLFaceAnalyzerSetting)) {
            return false;
        }
        MLFaceAnalyzerSetting mLFaceAnalyzerSetting = (MLFaceAnalyzerSetting) obj;
        return Float.floatToIntBits(this.minFaceProportion) == Float.floatToIntBits(mLFaceAnalyzerSetting.minFaceProportion) && this.keyPointType == mLFaceAnalyzerSetting.keyPointType && this.shapeType == mLFaceAnalyzerSetting.shapeType && this.featureType == mLFaceAnalyzerSetting.featureType && this.isPoseDisabled == mLFaceAnalyzerSetting.isPoseDisabled && this.isTracingAllowed == mLFaceAnalyzerSetting.isTracingAllowed && this.tracingMode == mLFaceAnalyzerSetting.tracingMode && this.performanceType == mLFaceAnalyzerSetting.performanceType && this.isMaxSizeFaceOnly == mLFaceAnalyzerSetting.isMaxSizeFaceOnly;
    }

    public int getFeatureType() {
        return this.featureType;
    }

    public int getKeyPointType() {
        return this.keyPointType;
    }

    public float getMinFaceProportion() {
        return this.minFaceProportion;
    }

    public int getPerformanceType() {
        return this.performanceType;
    }

    public int getShapeType() {
        return this.shapeType;
    }

    public int getTracingMode() {
        return this.tracingMode;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{Integer.valueOf(Float.floatToIntBits(this.minFaceProportion)), Integer.valueOf(this.keyPointType), Integer.valueOf(this.shapeType), Integer.valueOf(this.featureType), Boolean.valueOf(this.isPoseDisabled), Boolean.valueOf(this.isTracingAllowed), Integer.valueOf(this.tracingMode), Boolean.valueOf(this.isMaxSizeFaceOnly), Integer.valueOf(this.performanceType)});
    }

    public boolean isMaxSizeFaceOnly() {
        return this.isMaxSizeFaceOnly;
    }

    public boolean isPoseDisabled() {
        return this.isPoseDisabled;
    }

    public boolean isTracingAllowed() {
        return this.isTracingAllowed;
    }

    public String toString() {
        return com.huawei.hms.mlsdk.internal.client.Objects.toStringHelper(this).attribute("keyPointType", Integer.valueOf(this.keyPointType)).attribute("shapeMode", Integer.valueOf(this.shapeType)).attribute("featureType", Integer.valueOf(this.featureType)).attribute("performanceType", Integer.valueOf(this.performanceType)).attribute("isPoseDisabled", Boolean.valueOf(this.isPoseDisabled)).attribute("isTracingAllowed", Boolean.valueOf(this.isTracingAllowed)).attribute("tracingMode", Integer.valueOf(this.tracingMode)).attribute("minFaceProportion", Float.valueOf(this.minFaceProportion)).attribute("isMaxSizeFaceOnly", Boolean.valueOf(this.isMaxSizeFaceOnly)).toString();
    }

    public MLFaceAnalyzerSetting(int i, int i2, int i3, int i4, boolean z, boolean z2, int i5, boolean z3, float f) {
        this.keyPointType = i;
        this.shapeType = i2;
        this.featureType = i3;
        this.performanceType = i4;
        this.isPoseDisabled = z;
        this.isTracingAllowed = z2;
        this.tracingMode = i5;
        this.isMaxSizeFaceOnly = z3;
        this.minFaceProportion = f;
    }
}
