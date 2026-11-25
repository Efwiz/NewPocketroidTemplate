package com.huawei.hms.mlsdk.productvisionsearch.cloud;

import android.text.TextUtils;
import com.huawei.hms.mlsdk.internal.client.Objects;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class MLRemoteProductVisionSearchAnalyzerSetting {

    @Deprecated
    public static final int REGION_DR_AFILA = 1003;
    public static final int REGION_DR_CHINA = 1002;

    @Deprecated
    public static final int REGION_DR_EUROPE = 1004;
    public static final int REGION_DR_GERMAN = 1006;
    public static final int REGION_DR_RUSSIA = 1005;
    public static final int REGION_DR_SINGAPORE = 1007;
    public static final int REGION_DR_UNKNOWN = 1001;
    private final float a;
    private final int b;
    private final boolean c;
    private final String d;
    private final boolean e;
    private final Integer f;
    private final int g;

    public static final class Factory {
        private static final Map<Integer, String> h = new HashMap();
        private float a;
        private boolean c;
        private String d;
        private Integer f;
        private int b = 20;
        private boolean e = true;
        private int g = 1001;

        public Factory() {
            Map<Integer, String> map = h;
            map.put(1002, "CN");
            map.put(1003, "SG");
            map.put(1007, "SG");
            map.put(1004, "DE");
            map.put(1006, "DE");
            map.put(1005, "RU");
        }

        public MLRemoteProductVisionSearchAnalyzerSetting create() {
            return new MLRemoteProductVisionSearchAnalyzerSetting(this.a, this.b, this.c, this.d, this.e, this.f, this.g, null);
        }

        public Factory enableFingerprintVerification() {
            this.c = true;
            return this;
        }

        public Factory setBorderExtract(boolean z) {
            this.e = z;
            return this;
        }

        public Factory setClassType(Integer num) {
            this.f = num;
            return this;
        }

        public Factory setLargestNumOfReturns(int i) {
            if (i < 1 || i > 100) {
                throw new IllegalArgumentException(String.format(Locale.ENGLISH, "maxResults must be between %d and %d", 1, 100));
            }
            this.b = i;
            return this;
        }

        public Factory setMinAcceptablePossibility(float f) {
            this.a = f;
            return this;
        }

        public Factory setProductSetId(String str) {
            this.d = str;
            return this;
        }

        public Factory setRegion(int i) {
            if (!h.containsKey(Integer.valueOf(i))) {
                throw new IllegalArgumentException("region is not available");
            }
            this.g = i;
            return this;
        }
    }

    /* synthetic */ MLRemoteProductVisionSearchAnalyzerSetting(float f, int i, boolean z, String str, boolean z2, Integer num, int i2, a aVar) {
        this.a = f;
        this.b = i;
        this.c = z;
        this.d = str;
        this.e = z2;
        this.f = num;
        this.g = i2;
    }

    protected String a() {
        if (Factory.h.containsKey(Integer.valueOf(this.g))) {
            return (String) Factory.h.get(Integer.valueOf(this.g));
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MLRemoteProductVisionSearchAnalyzerSetting)) {
            return false;
        }
        MLRemoteProductVisionSearchAnalyzerSetting mLRemoteProductVisionSearchAnalyzerSetting = (MLRemoteProductVisionSearchAnalyzerSetting) obj;
        if (this.b == mLRemoteProductVisionSearchAnalyzerSetting.b) {
            float f = this.a;
            if (f == f && this.c == mLRemoteProductVisionSearchAnalyzerSetting.c && TextUtils.equals(this.d, mLRemoteProductVisionSearchAnalyzerSetting.d) && this.e == mLRemoteProductVisionSearchAnalyzerSetting.e && this.f == mLRemoteProductVisionSearchAnalyzerSetting.f && this.g == mLRemoteProductVisionSearchAnalyzerSetting.g) {
                return true;
            }
        }
        return false;
    }

    public Integer getClassType() {
        return this.f;
    }

    public int getLargestNumOfReturns() {
        return this.b;
    }

    public float getMinAcceptablePossibility() {
        return this.a;
    }

    public String getProductSetId() {
        return this.d;
    }

    public int getRegion() {
        return this.g;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.b), Float.valueOf(this.a), Boolean.valueOf(this.c), this.d, Boolean.valueOf(this.e), this.f, Integer.valueOf(this.g));
    }

    public boolean isEnableBorderExtract() {
        return this.e;
    }

    public final boolean isEnableFingerprintVerification() {
        return this.c;
    }
}
