package com.huawei.hms.mlsdk.t;

import android.os.Bundle;
import com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent;
import com.huawei.hms.mlsdk.common.MLApplication;
import java.util.LinkedHashMap;

/* JADX INFO: compiled from: TtsAnalyticsEvent.java */
/* JADX INFO: loaded from: classes10.dex */
public class c extends BaseInfoGatherEvent {
    private String a;
    private String b;
    private long c;
    private long d;
    private int e;
    private int f;
    private int g;
    private int h;
    private boolean i = true;
    private String j;
    private String k;

    public long a() {
        return this.c;
    }

    public void b(long j) {
        this.c = j;
    }

    public void c(String str) {
        this.a = str;
    }

    public void d(String str) {
        this.b = str;
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getApiName() {
        return "";
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public Bundle getAppInfo() {
        Bundle bundle = MLApplication.getInstance().toBundle();
        bundle.putString("com.huawei.hms.client.service.name:ml-computer-vision", bundle.getString("com.huawei.hms.client.service.name:ml-voice-tts"));
        return bundle;
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public LinkedHashMap<String, String> getCustomizedData() {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("ttsLanguage", this.a);
        linkedHashMap.put("voiceName", this.b);
        linkedHashMap.put("synthesisStartTime", String.valueOf(a()));
        linkedHashMap.put("synthesisEndTime", String.valueOf(this.d));
        linkedHashMap.put("textLength", String.valueOf(this.e));
        linkedHashMap.put("ttsFirstCost", String.valueOf(b()));
        linkedHashMap.put("ttsDownloadCost", String.valueOf(this.g));
        linkedHashMap.put("sizeDownload", String.valueOf(this.h));
        linkedHashMap.put("ttsSmoothly", String.valueOf(this.i));
        linkedHashMap.put("result", this.j);
        linkedHashMap.put("resultDetail", this.k);
        return linkedHashMap;
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getEventId() {
        return "113001";
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
        return "MLKitTts";
    }

    @Override // com.huawei.hms.mlkit.common.ha.event.BaseInfoGatherEvent
    public String getModuleVersion() {
        return "3.7.0.303";
    }

    public void a(long j) {
        this.d = j;
    }

    public void b(int i) {
        this.e = i;
    }

    public void c(int i) {
        this.f = i;
    }

    public void a(int i) {
        this.h = i;
    }

    public int b() {
        return this.f;
    }

    public void a(String str) {
        this.j = str;
    }

    public void b(String str) {
        this.k = str;
    }
}
