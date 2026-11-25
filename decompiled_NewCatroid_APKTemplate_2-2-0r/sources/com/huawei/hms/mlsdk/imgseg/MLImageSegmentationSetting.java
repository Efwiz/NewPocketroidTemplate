package com.huawei.hms.mlsdk.imgseg;

import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public class MLImageSegmentationSetting {
    public static final int BODY_SEG = 0;
    public static final int HAIR_SEG = 2;
    public static final int IMAGE_SEG = 1;
    private final int analyzerType;
    private final boolean isExact;
    private final int scene;

    public static class Factory {
        private boolean isExact = true;
        private int analyzerType = 0;
        private int scene = 0;

        public MLImageSegmentationSetting create() {
            return new MLImageSegmentationSetting(this.analyzerType, this.isExact, this.scene);
        }

        public Factory setAnalyzerType(int i) {
            this.analyzerType = i;
            return this;
        }

        public Factory setExact(boolean z) {
            this.isExact = z;
            return this;
        }

        public Factory setScene(int i) {
            this.scene = i;
            return this;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MLImageSegmentationSetting)) {
            return false;
        }
        MLImageSegmentationSetting mLImageSegmentationSetting = (MLImageSegmentationSetting) obj;
        return mLImageSegmentationSetting.analyzerType == this.analyzerType && mLImageSegmentationSetting.isExact == this.isExact && mLImageSegmentationSetting.scene == this.scene;
    }

    public int getAnalyzerType() {
        return this.analyzerType;
    }

    public int getScene() {
        return this.scene;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.analyzerType), Boolean.valueOf(this.isExact), Integer.valueOf(this.scene)});
    }

    public boolean isExact() {
        return this.isExact;
    }

    private MLImageSegmentationSetting(int i, boolean z, int i2) {
        this.analyzerType = i;
        this.isExact = z;
        this.scene = i2;
    }
}
