package org.jetbrains.kotlin.fir.lightTree;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;

/* JADX INFO: compiled from: LightTreeParsingErrorListener.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bæ\u0080\u0001\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/LightTreeParsingErrorListener;", "", "onError", "", "startOffset", "", "endOffset", CrashHianalyticsData.MESSAGE, "", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LightTreeParsingErrorListener {
    void onError(int startOffset, int endOffset, String message);
}
