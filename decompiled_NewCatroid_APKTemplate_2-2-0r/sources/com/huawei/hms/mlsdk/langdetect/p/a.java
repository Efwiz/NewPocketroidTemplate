package com.huawei.hms.mlsdk.langdetect.p;

import android.content.pm.PackageManager;
import android.os.Bundle;
import com.huawei.hms.framework.common.hianalytics.WiseOpenHianalyticsData;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent;
import com.huawei.hms.mlsdk.common.MLApplication;
import java.util.LinkedHashMap;

/* JADX INFO: compiled from: AnalyticsEvent.java */
/* JADX INFO: loaded from: classes10.dex */
public class a extends BaseInfoGatherEvent {
    private String a;
    private long b;
    private long c;
    private int d;
    private int e;
    private String f;
    private String g;

    public void a(String str) {
        this.a = str;
    }

    public void b(long j) {
        this.b = j;
    }

    public void c(String str) {
        this.g = str;
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getApiName() {
        return "MLKitTranslate-MLKitLangDetect";
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public Bundle getAppInfo() {
        String string = "defaultValue";
        try {
            string = MLApplication.getInstance().getAppContext().getPackageManager().getApplicationInfo(MLApplication.getInstance().getAppContext().getPackageName(), 128).metaData.getString("com.huawei.hms.client.service.name:mlkitlanguagedetect", "defaultValue");
        } catch (PackageManager.NameNotFoundException unused) {
            SmartLog.e("AnalyticsEvent", "getMetadata PackageManager.NameNotFoundExp");
        }
        Bundle bundle = MLApplication.getInstance().toBundle();
        bundle.putString("com.huawei.hms.client.service.name:ml-computer-vision", string);
        bundle.putString("apiName", "MLKitTranslate-MLKitLangDetect");
        return bundle;
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public LinkedHashMap<String, String> getCustomizedData() {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("language", this.a);
        long j = this.b;
        linkedHashMap.put("startTime", j == 0 ? "" : String.valueOf(j));
        long j2 = this.c;
        linkedHashMap.put("endTime", j2 == 0 ? "" : String.valueOf(j2));
        int i = this.d;
        linkedHashMap.put("charNum", i == 0 ? "" : String.valueOf(i));
        int i2 = this.e;
        linkedHashMap.put(WiseOpenHianalyticsData.UNION_COSTTIME, i2 != 0 ? String.valueOf(i2) : "");
        linkedHashMap.put("result", this.f);
        linkedHashMap.put("resultDetail", this.g);
        return linkedHashMap;
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getEventId() {
        return "107002";
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
        return Constant.MOUDLE_NAME;
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getModuleVersion() {
        return "3.7.0.301";
    }

    public long a() {
        return this.b;
    }

    public void b(int i) {
        this.e = i;
    }

    public void a(long j) {
        this.c = j;
    }

    public void b(String str) {
        this.f = str;
    }

    public void a(int i) {
        this.d = i;
    }
}
