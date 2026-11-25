package com.huawei.hms.mlsdk.asr.engine.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Pair;
import com.google.common.net.HttpHeaders;
import com.huawei.hms.mlsdk.asr.o.f;
import com.huawei.hms.mlsdk.common.MLApplication;
import com.huawei.hms.mlsdk.common.MLApplicationSetting;
import com.huawei.secure.android.common.ssl.SecureSSLSocketFactory;
import com.huawei.secure.android.common.ssl.SecureX509TrustManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import okio.ByteString;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: WebsocketClient.java */
/* JADX INFO: loaded from: classes10.dex */
public class e {
    private f a;
    private com.huawei.hms.mlsdk.asr.o.e b = null;
    private boolean c;
    private List<String> d;

    /* JADX INFO: compiled from: WebsocketClient.java */
    class a extends TimerTask {
        final /* synthetic */ long a;

        a(long j) {
            this.a = j;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            SmartLogger.i("WebsocketClient", com.huawei.hms.mlsdk.asr.o.a.a("reconnectServer after ").append(this.a).append(" millisecond").toString());
            if (e.this.a()) {
                return;
            }
            e eVar = e.this;
            eVar.a = eVar.e();
            if (e.this.b != null) {
                e eVar2 = e.this;
                eVar2.a(eVar2.b);
            }
            e.this.c();
        }
    }

    public e(boolean z, List<String> list) {
        this.a = null;
        this.d = list;
        this.c = z;
        this.a = e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public f e() {
        SecureSSLSocketFactory secureSSLSocketFactory;
        SecureX509TrustManager secureX509TrustManager;
        f.d dVar = new f.d(MLApplication.getInstance().getAppContext());
        Pair pair = null;
        try {
            secureSSLSocketFactory = SecureSSLSocketFactory.getInstance(MLApplication.getInstance().getAppContext());
        } catch (Exception e) {
            SmartLogger.e("WebsocketClient", e.getMessage());
            secureSSLSocketFactory = null;
        }
        try {
            secureX509TrustManager = new SecureX509TrustManager(MLApplication.getInstance().getAppContext());
        } catch (Exception e2) {
            SmartLogger.e("WebsocketClient", e2.getMessage());
            secureX509TrustManager = null;
        }
        f.d dVarA = dVar.a(secureSSLSocketFactory, secureX509TrustManager).a(false).a(this.d);
        HashMap map = new HashMap();
        MLApplication mLApplication = MLApplication.getInstance();
        MLApplicationSetting appSetting = mLApplication.getAppSetting();
        if (appSetting != null) {
            UUID uuidRandomUUID = UUID.randomUUID();
            map.put("Content-Type", "application/json;charset=UTF-8");
            map.put(HttpHeaders.X_REQUEST_ID, String.valueOf(uuidRandomUUID));
            SmartLogger.i("AsrProcessor", "X-Request-ID: " + uuidRandomUUID);
            map.put("X-User-Agent", "X-User-Agent");
            map.put("appId", appSetting.getAppId());
            map.put("HMS-APPLICATION-ID", appSetting.getAppId());
            map.put("X-Package-Name", appSetting.getPackageName());
            map.put("X-Country-Code", new c(mLApplication.getAppContext(), false).a());
            map.put("supplierId", "supplierId");
            map.put("accept", "application/json");
            map.put("certFingerprint", appSetting.getCertFingerprint());
            map.put("Authorization", "Bearer " + MLApplication.getInstance().getAuthorizationToken());
            map.put("X-Mlkit-Version", "3.7.0.301");
            SmartLogger.i("WebsocketClient", "X-Mlkit-Version: " + appSetting.getMLSdkVersion());
            List<ResolveInfo> listQueryIntentServices = MLApplication.getInstance().getAppContext().getPackageManager().queryIntentServices(new Intent("com.huawei.hms.core.aidlservice"), 128);
            if (listQueryIntentServices.size() != 0) {
                Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
                if (it.hasNext()) {
                    ResolveInfo next = it.next();
                    pair = new Pair(next.serviceInfo.applicationInfo.packageName, next);
                }
            }
            String str = SchemaSymbols.ATTVAL_TRUE_1;
            if (pair != null) {
                String str2 = (String) pair.first;
                SmartLogger.d("WebsocketClient", "HMScore PackageName is :" + str2);
                if (!a(mLApplication.getAppContext(), str2)) {
                    str = "0";
                }
                map.put("isHmsCore", str);
            } else {
                if (!a(mLApplication.getAppContext(), "com.huawei.hwid")) {
                    str = "0";
                }
                map.put("isHmsCore", str);
            }
        }
        return new f(dVarA.a(map).b(this.c));
    }

    public synchronized void c() {
        if (!a()) {
            this.a.d();
        }
    }

    public synchronized void d() {
        SmartLogger.i("WebsocketClient", "onStopConnect");
        f fVar = this.a;
        if (fVar != null) {
            fVar.a((com.huawei.hms.mlsdk.asr.o.e) null);
            this.a.e();
            this.b = null;
        }
    }

    public boolean b() {
        f fVar = this.a;
        return fVar != null && fVar.b();
    }

    public synchronized void a(com.huawei.hms.mlsdk.asr.o.e eVar) {
        this.b = eVar;
        if (eVar != null) {
            this.a.a(eVar);
        }
    }

    public synchronized boolean a() {
        f fVar;
        fVar = this.a;
        return fVar != null && fVar.c();
    }

    public synchronized void a(long j) {
        new Timer().schedule(new a(j), j);
    }

    public synchronized boolean a(String str) {
        return a() ? this.a.a(str) : false;
    }

    public synchronized boolean a(ByteString byteString) {
        return a() ? this.a.a(byteString) : false;
    }

    private boolean a(Context context, String str) {
        try {
            return (context.getPackageManager().getPackageInfo(str, 0).applicationInfo.flags & 1) != 0;
        } catch (RuntimeException e) {
            SmartLogger.e("WebsocketClient", "isPackageInternal Exception e: " + e);
            return false;
        } catch (Exception e2) {
            SmartLogger.e("WebsocketClient", "isPackageInternal Exception e: " + e2);
            return false;
        }
    }
}
