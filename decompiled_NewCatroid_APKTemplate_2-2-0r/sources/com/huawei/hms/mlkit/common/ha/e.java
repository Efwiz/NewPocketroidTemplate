package com.huawei.hms.mlkit.common.ha;

import java.util.TimerTask;

/* JADX INFO: compiled from: HianalyticsOnReportTask.java */
/* JADX INFO: loaded from: classes10.dex */
public class e extends TimerTask {
    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        try {
            c.a.a();
        } catch (Exception unused) {
            b.a("HaLogOnReport", "Failed to report hianalytics data");
        }
    }
}
