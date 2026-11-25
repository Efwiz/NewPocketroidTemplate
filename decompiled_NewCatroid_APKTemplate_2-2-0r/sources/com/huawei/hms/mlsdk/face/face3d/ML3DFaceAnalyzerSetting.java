package com.huawei.hms.mlsdk.face.face3d;

import com.huawei.hms.mlsdk.face.MLFaceAnalyzerSetting;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class ML3DFaceAnalyzerSetting {
    public static final int TYPE_PRECISION = 2;
    public static final int TYPE_SPEED = 1;
    private final boolean face3dEnabled;
    private final boolean isTracingAllowed;
    private final int performanceType;

    public static class Factory {
        private int performanceType = 1;
        private boolean face3dEnabled = true;
        private boolean isTracingAllowed = false;

        public ML3DFaceAnalyzerSetting create() {
            return new ML3DFaceAnalyzerSetting(this.performanceType, this.face3dEnabled, this.isTracingAllowed);
        }

        public Factory setPerformanceType(int i) {
            this.performanceType = i;
            return this;
        }

        public Factory setTracingAllowed(boolean z) {
            this.isTracingAllowed = z;
            return this;
        }
    }

    @Retention(RetentionPolicy.CLASS)
    public @interface PerformanceType {
    }

    public ML3DFaceAnalyzerSetting(int i, boolean z) {
        this(i, true, z);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLFaceAnalyzerSetting)) {
            return false;
        }
        ML3DFaceAnalyzerSetting mL3DFaceAnalyzerSetting = (ML3DFaceAnalyzerSetting) obj;
        return this.face3dEnabled == mL3DFaceAnalyzerSetting.face3dEnabled && this.isTracingAllowed == mL3DFaceAnalyzerSetting.isTracingAllowed && this.performanceType == mL3DFaceAnalyzerSetting.performanceType;
    }

    public int getPerformanceType() {
        return this.performanceType;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{Boolean.valueOf(this.face3dEnabled), Boolean.valueOf(this.isTracingAllowed), Integer.valueOf(this.performanceType)});
    }

    public boolean isTracingAllowed() {
        return this.isTracingAllowed;
    }

    public String toString() {
        return com.huawei.hms.mlsdk.internal.client.Objects.toStringHelper(this).attribute("performanceType", Integer.valueOf(this.performanceType)).attribute("face3dEnabled", Boolean.valueOf(this.face3dEnabled)).attribute("isTracingAllowed", Boolean.valueOf(this.isTracingAllowed)).toString();
    }

    public ML3DFaceAnalyzerSetting(int i, boolean z, boolean z2) {
        this.performanceType = i;
        this.face3dEnabled = z;
        this.isTracingAllowed = z2;
    }
}
