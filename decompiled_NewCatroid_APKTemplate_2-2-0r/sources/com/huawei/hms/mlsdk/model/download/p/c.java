package com.huawei.hms.mlsdk.model.download.p;

import android.content.pm.PackageManager;
import android.os.Bundle;
import com.huawei.hms.framework.common.hianalytics.WiseOpenHianalyticsData;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent;
import com.huawei.hms.mlsdk.common.MLApplication;
import java.util.LinkedHashMap;

/* JADX INFO: compiled from: ModelAnalyticsEvent.java */
/* JADX INFO: loaded from: classes10.dex */
public class c extends BaseInfoGatherEvent {
    private String a;
    private long b;
    private long c;
    private int d;
    private String e;
    private String f;

    public long a() {
        return this.b;
    }

    public void b(long j) {
        this.b = j;
    }

    public void c(String str) {
        this.f = str;
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getApiName() {
        return this.a;
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public Bundle getAppInfo() {
        String string = "defaultValue";
        try {
            string = MLApplication.getInstance().getAppContext().getPackageManager().getApplicationInfo(MLApplication.getInstance().getAppContext().getPackageName(), 128).metaData.getString("com.huawei.hms.client.service.name:ml-computer-model-download", "defaultValue");
        } catch (PackageManager.NameNotFoundException unused) {
            SmartLog.e("AnalyticsEvent", "getMetadata PackageManager.NameNotFoundExp");
        }
        Bundle bundle = MLApplication.getInstance().toBundle();
        bundle.putString("com.huawei.hms.client.service.name:ml-computer-vision", string);
        return bundle;
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public LinkedHashMap<String, String> getCustomizedData() {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        long j = this.b;
        linkedHashMap.put("startTime", j == 0 ? "" : String.valueOf(j));
        long j2 = this.c;
        linkedHashMap.put("endTime", j2 == 0 ? "" : String.valueOf(j2));
        int i = this.d;
        linkedHashMap.put(WiseOpenHianalyticsData.UNION_COSTTIME, i != 0 ? String.valueOf(i) : "");
        linkedHashMap.put("result", this.e);
        linkedHashMap.put("resultDetail", this.f);
        return linkedHashMap;
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getEventId() {
        return "107003";
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getExtension(String str) {
        return "";
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getModelApkVersion() {
        return "";
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getModuleName() {
        return "MLKitDownload";
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getModuleVersion() {
        return "3.7.0.302";
    }

    public void a(long j) {
        this.c = j;
    }

    public void b(String str) {
        this.e = str;
    }

    public void a(int i) {
        this.d = i;
    }

    public void a(String str) {
        this.a = str;
    }
}
