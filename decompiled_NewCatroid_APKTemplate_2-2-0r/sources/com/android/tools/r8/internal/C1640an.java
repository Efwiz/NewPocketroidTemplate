package com.android.tools.r8.internal;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;

/* JADX INFO: renamed from: com.android.tools.r8.internal.an, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1640an extends RuntimeException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1640an(String str, Throwable th) {
        super(str, th);
        AbstractC0936Ds.c(str, CrashHianalyticsData.MESSAGE);
    }
}
