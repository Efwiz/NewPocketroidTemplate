package com.huawei.hms.ml.language.common.utils;

import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.ml.grs.GrsRegionUtils;
import com.huawei.hms.ml.grs.GrsUtils;
import com.huawei.hms.mlsdk.common.MLApplication;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class ResultDomainList {
    private static final String TAG = "ResultDomainList";

    public static List<String> getDomainListUrl() {
        String grsCountryCode = GrsRegionUtils.getGrsCountryCode();
        SmartLog.i(TAG, "GRS countryCode is " + grsCountryCode);
        if (grsCountryCode != null) {
            return GrsUtils.getVisionSearchUrls(GrsUtils.initGrsVisionSearchClientWithCountry(MLApplication.getInstance().getAppContext(), grsCountryCode));
        }
        return GrsUtils.getUrls(MLApplication.getInstance().getAppContext(), false);
    }
}
