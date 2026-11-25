package com.huawei.hms.mlsdk.common.lens;

import android.hardware.Camera;
import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.AnalyzerMonitor;
import com.huawei.hms.mlsdk.common.LensEngine;
import com.huawei.hms.mlsdk.common.a;
import com.huawei.hms.mlsdk.common.internal.client.event.MonitorEvent;
import com.huawei.hms.mlsdk.common.internal.client.event.MonitorResult;

/* JADX INFO: loaded from: classes10.dex */
public class MLAnalyzerMonitor implements AnalyzerMonitor {
    public static final String TAG = "LensAnalyzerMonitor";
    public LensEngine engine;

    public MLAnalyzerMonitor(LensEngine lensEngine) {
        this.engine = lensEngine;
    }

    @Override // com.huawei.hms.mlsdk.common.AnalyzerMonitor
    public MonitorResult receive(MonitorEvent monitorEvent) {
        if (monitorEvent instanceof a) {
            Camera lens = this.engine.getLens();
            if (lens == null) {
                SmartLog.e(TAG, "engine getLens() is null");
                return MonitorResult.ofFailureResult(MonitorResult.ZOOM_NOT_SUPPORT);
            }
            Camera.Parameters parameters = lens.getParameters();
            if (!parameters.isZoomSupported()) {
                SmartLog.w(TAG, "Zoom is not supported on this device");
                return MonitorResult.ofFailureResult(MonitorResult.ZOOM_NOT_SUPPORT);
            }
            int i = ((a) monitorEvent).a;
            int maxZoom = parameters.getMaxZoom();
            if (i > maxZoom) {
                i = maxZoom;
            }
            parameters.setZoom(i);
        }
        return MonitorResult.ofSuccessResult();
    }
}
