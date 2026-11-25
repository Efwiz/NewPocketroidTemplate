package com.huawei.hms.mlsdk.model.download;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class MLModelDownloadStrategy {

    @Deprecated
    public static final int REGION_DR_AFILA = 1003;
    public static final int REGION_DR_CHINA = 1002;

    @Deprecated
    public static final int REGION_DR_EUROPE = 1004;
    public static final int REGION_DR_GERMAN = 1006;
    public static final int REGION_DR_RUSSIA = 1005;
    public static final int REGION_DR_SINGAPORE = 1007;
    private boolean a;
    private boolean b;
    private boolean c;
    private int d;

    public static class Factory {
        private static final Map<Integer, String> e;
        private boolean a = false;
        private boolean b = false;
        private boolean c = false;
        private int d = 1001;

        static {
            HashMap map = new HashMap();
            e = map;
            map.put(1002, "CN");
            map.put(1003, "SG");
            map.put(1007, "SG");
            map.put(1004, "DE");
            map.put(1006, "DE");
            map.put(1005, "RU");
        }

        public MLModelDownloadStrategy create() {
            return new MLModelDownloadStrategy(this.a, this.b, this.c, this.d, null);
        }

        public Factory needCharging() {
            this.a = true;
            return this;
        }

        public Factory needDeviceIdle() {
            this.c = true;
            return this;
        }

        public Factory needWifi() {
            this.b = true;
            return this;
        }

        public Factory setRegion(int i) {
            if (!e.containsKey(Integer.valueOf(i))) {
                throw new IllegalArgumentException("region is not available");
            }
            this.d = i;
            return this;
        }
    }

    /* synthetic */ MLModelDownloadStrategy(boolean z, boolean z2, boolean z3, int i, a aVar) {
        this.a = z;
        this.b = z2;
        this.c = z3;
        this.d = i;
    }

    public String getRegion() {
        if (Factory.e.containsKey(Integer.valueOf(this.d))) {
            return (String) Factory.e.get(Integer.valueOf(this.d));
        }
        return null;
    }

    public boolean isChargingNeed() {
        return this.a;
    }

    public boolean isDeviceIdleNeed() {
        return this.c;
    }

    public boolean isWifiNeed() {
        return this.b;
    }
}
