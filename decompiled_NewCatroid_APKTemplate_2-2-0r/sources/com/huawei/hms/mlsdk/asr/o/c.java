package com.huawei.hms.mlsdk.asr.o;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.mlkit.common.ha.HianalyticsLogProvider;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import com.huawei.hms.mlsdk.common.MLApplication;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: AsrHaAdapter.java */
/* JADX INFO: loaded from: classes10.dex */
public class c {
    private volatile boolean a = false;
    private Map<String, com.huawei.hms.mlsdk.asr.o.b> b = new HashMap();
    private boolean c;

    /* JADX INFO: compiled from: AsrHaAdapter.java */
    private static class b {
        private static final c a = new c(null);
    }

    /* synthetic */ c(a aVar) {
    }

    public static c b() {
        return b.a;
    }

    public void a(Context context, boolean z) {
        if (this.a) {
            SmartLogger.i("HaAdapter_MLKitAsr", "has already startHaAnalysis");
            return;
        }
        if (HianalyticsLogProvider.getInstance().sdkForbiddenHiLog(context.getApplicationContext())) {
            SmartLogger.i("HaAdapter_MLKitAsr", "return startHaAnalysis,sdkForbiddenHiLog");
            return;
        }
        this.c = z;
        SmartLogger.i("HaAdapter_MLKitAsr", "Start ha moudle");
        if (z) {
            HianalyticsLogProvider.getInstance().initTimer("MLKitRtt");
        } else {
            HianalyticsLogProvider.getInstance().initTimer("MLKitAsr");
        }
        this.a = true;
    }

    public long c(String str) {
        if (TextUtils.isEmpty(str) || !this.a || !this.b.containsKey(str)) {
            SmartLogger.i("HaAdapter_MLKitAsr", com.huawei.hms.mlsdk.asr.o.a.a("getLastWordCost return !isInfoGatherStart:").append(!this.a).append("!mEventsToBeReported.containsKey(taskId): ").append(!this.b.containsKey(str)).toString());
            return 0L;
        }
        com.huawei.hms.mlsdk.asr.o.b bVar = this.b.get(str);
        if (bVar != null) {
            return bVar.g().longValue();
        }
        SmartLogger.e("HaAdapter_MLKitAsr", "getSendFinalDataTime taskId " + str + " 's asrAnalyticsEvent is null");
        return 0L;
    }

    public Long d(String str) {
        if (TextUtils.isEmpty(str) || !this.a || !this.b.containsKey(str)) {
            SmartLogger.i("HaAdapter_MLKitAsr", com.huawei.hms.mlsdk.asr.o.a.a("getSpeechEndTime return !isInfoGatherStart:!isInfoGatherStart !mEventsToBeReported.containsKey(taskId)").append(!this.b.containsKey(str)).toString());
            return null;
        }
        com.huawei.hms.mlsdk.asr.o.b bVar = this.b.get(str);
        if (bVar != null) {
            return Long.valueOf(bVar.h());
        }
        SmartLogger.e("HaAdapter_MLKitAsr", "getSpeechEndTime taskId " + str + " 's asrAnalyticsEvent is null");
        return null;
    }

    public Long e(String str) {
        if (TextUtils.isEmpty(str) || !this.a || !this.b.containsKey(str)) {
            SmartLogger.i("HaAdapter_MLKitAsr", com.huawei.hms.mlsdk.asr.o.a.a("getSpeechStartTime return !isInfoGatherStart:").append(!this.a).append("!mEventsToBeReported.containsKey(taskId): ").append(!this.b.containsKey(str)).toString());
            return null;
        }
        com.huawei.hms.mlsdk.asr.o.b bVar = this.b.get(str);
        if (bVar != null) {
            return Long.valueOf(bVar.i());
        }
        SmartLogger.e("HaAdapter_MLKitAsr", "getSpeechStartTime taskId " + str + " 's asrAnalyticsEvent is null");
        return null;
    }

    public void f(String str) {
        if (TextUtils.isEmpty(str) || !this.a || !this.b.containsKey(str)) {
            SmartLogger.i("HaAdapter_MLKitAsr", com.huawei.hms.mlsdk.asr.o.a.a("postEvent return !isInfoGatherStart: ").append(!this.a).append("!mEventsToBeReported.containsKey(taskId): ").append(!this.b.containsKey(str)).toString());
            return;
        }
        SmartLogger.i("HaAdapter_MLKitAsr", "Post Event [" + str + "]");
        com.huawei.hms.mlsdk.asr.o.b bVar = this.b.get(str);
        if (bVar == null) {
            SmartLogger.e("HaAdapter_MLKitAsr", "eventDetail taskId " + str + " 's asrAnalyticsEvent is null");
        } else {
            StringBuilder sb = new StringBuilder("applanguage: ");
            sb.append(bVar.a()).append(" speechStartTime:").append(bVar.i()).append(",speechEndTime: ").append(bVar.h()).append(",firstWordCost: ").append(bVar.c()).append(",lastWordCost: ").append(bVar.d()).append(",voiceStreamTime:").append(bVar.l()).append(",uploadVoiceSize: ").append(bVar.k()).append(",textLength: ").append(bVar.j()).append(",chainBuildingDelay: ").append(bVar.b()).append(",result: ").append(bVar.e()).append(",errMsg: ").append(bVar.f());
            SmartLogger.i("HaAdapter_MLKitAsr", sb.toString());
        }
        com.huawei.hms.mlsdk.asr.o.b bVar2 = this.b.get(str);
        if (bVar2 == null) {
            SmartLogger.e("HaAdapter_MLKitAsr", "postEvent taskId " + str + " 's asrAnalyticsEvent is null");
        } else {
            HianalyticsLogProvider.getInstance().postEvent(MLApplication.getInstance().getAppContext(), 1, bVar2);
        }
    }

    public int b(String str) {
        if (TextUtils.isEmpty(str) || !this.a || !this.b.containsKey(str)) {
            SmartLogger.i("HaAdapter_MLKitAsr", com.huawei.hms.mlsdk.asr.o.a.a("getLastWordCost return !isInfoGatherStart:").append(!this.a).append("!mEventsToBeReported.containsKey(taskId): ").append(!this.b.containsKey(str)).toString());
            return 0;
        }
        com.huawei.hms.mlsdk.asr.o.b bVar = this.b.get(str);
        if (bVar != null) {
            return bVar.d();
        }
        SmartLogger.e("HaAdapter_MLKitAsr", "getLastWordCost taskId " + str + " 's asrAnalyticsEvent is null");
        return 0;
    }

    public void a(String str) {
        if (!TextUtils.isEmpty(str) && this.a) {
            SmartLogger.i("HaAdapter_MLKitAsr", "taskId: " + str);
            this.b.put(str, new com.huawei.hms.mlsdk.asr.o.b(this.c));
            return;
        }
        SmartLogger.i("HaAdapter_MLKitAsr", com.huawei.hms.mlsdk.asr.o.a.a("return createEvent: !isInfoGatherStart: ").append(!this.a).toString());
    }

    public void a() {
        if (this.a) {
            SmartLogger.i("HaAdapter_MLKitAsr", "Stop ha moudle");
            if (this.c) {
                HianalyticsLogProvider.getInstance().reportAndCancelTimer("MLKitRtt");
            } else {
                HianalyticsLogProvider.getInstance().reportAndCancelTimer("MLKitAsr");
            }
            this.b.clear();
            this.a = false;
        }
    }

    public void a(String str, int i, Bundle bundle) {
        SmartLogger.i("HaAdapter_MLKitAsr", "handleMsg, taskId: " + str + "msg: " + i);
        if (!TextUtils.isEmpty(str) && this.a && this.b.containsKey(str)) {
            com.huawei.hms.mlsdk.asr.o.b bVar = this.b.get(str);
            if (bVar == null) {
                SmartLogger.e("HaAdapter_MLKitAsr", "handleMsg taskId " + str + " 's asrAnalyticsEvent is null");
                return;
            }
            switch (i) {
                case 0:
                    bVar.a(bundle.getString("applanguage"));
                    break;
                case 1:
                    bVar.b(bundle.getLong("speechStartTime"));
                    break;
                case 2:
                    if (bVar.h() == 0) {
                        bVar.a(bundle.getLong("speechEndTime"));
                    } else {
                        SmartLogger.i("HaAdapter_MLKitAsr", "already set speechEndTime");
                    }
                    break;
                case 3:
                    bVar.b(bundle.getInt("firstWordCost"));
                    break;
                case 4:
                    bVar.c(bundle.getInt("lastWordCost"));
                    break;
                case 5:
                    bVar.f(bundle.getInt("voiceStreamTime"));
                    break;
                case 6:
                    bVar.e(bundle.getInt("uploadVoiceSize"));
                    break;
                case 7:
                    bVar.d(bundle.getInt("textLength"));
                    break;
                case 8:
                    bVar.a(bundle.getInt("chainBuildingDelay"));
                    break;
                case 9:
                    bVar.b(String.valueOf(bundle.getInt("result")));
                    break;
                case 10:
                    bVar.c(bundle.getString("errMsg"));
                    break;
                case 11:
                    bVar.a(Long.valueOf(bundle.getLong("sendFinalDataTime")));
                    break;
            }
            return;
        }
        SmartLogger.e("HaAdapter_MLKitAsr", com.huawei.hms.mlsdk.asr.o.a.a("mEventsToBeReported: has no response event isInfoGatherStart:").append(this.a).append("msg: ").append(i).toString());
    }
}
