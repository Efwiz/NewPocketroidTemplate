package com.huawei.hms.mlsdk.internal.client.rest;

import android.content.Context;
import com.google.common.net.HttpHeaders;
import com.huawei.hms.ml.common.utils.CountryCodeBean;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: compiled from: RestClientContext.java */
/* JADX INFO: loaded from: classes10.dex */
public final class d {
    private final Context a;
    private final MLApplicationSetting b;

    public d(MLApplication mLApplication) {
        Context appContext = mLApplication.getAppContext();
        this.a = appContext;
        if (mLApplication.getAppSetting() != null) {
            this.b = mLApplication.getAppSetting();
        } else {
            this.b = MLApplicationSetting.fromResource(appContext);
        }
    }

    public Map<String, String> a(f.b bVar) {
        UUID uuidRandomUUID = UUID.randomUUID();
        bVar.a("Content-Type", "application/json");
        bVar.a(HttpHeaders.X_REQUEST_ID, String.valueOf(uuidRandomUUID));
        bVar.a("X-User-Agent", "X-User-Agent");
        bVar.a("appId", this.b.getAppId());
        bVar.a("HMS-APPLICATION-ID", this.b.getAppId());
        bVar.a("X-Package-Name", this.b.getPackageName());
        bVar.a("X-Country-Code", new CountryCodeBean(this.a, false).getCountryCode());
        bVar.a("supplierId", "supplierId");
        bVar.a("accept", "application/json");
        bVar.a("certFingerprint", this.b.getCertFingerprint());
        bVar.a("Authorization", "Bearer " + MLApplication.getInstance().getAuthorizationToken());
        bVar.a("X-Mlkit-Version", this.b.getMLSdkVersion());
        return bVar.a().a();
    }

    public MLApplicationSetting b() {
        return this.b;
    }

    public Context a() {
        return this.a;
    }
}
