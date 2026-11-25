package com.huawei.hms.mlsdk.model.download.impl;

import com.huawei.hms.ml.common.utils.SmartLog;
import com.huawei.hms.mlsdk.common.MLException;
import java.io.IOException;
import okhttp3.Response;

/* JADX INFO: loaded from: classes10.dex */
public class ModelResponse {
    private int a;
    private String b;

    public ModelResponse(Response response) throws MLException {
        if (response.body() == null) {
            SmartLog.e("MLSDK_MODEL_ModelResponse", "Get response failed.");
            throw new MLException("Get response failed.", 2);
        }
        try {
            try {
                this.a = response.code();
                this.b = response.body().string();
                SmartLog.d("MLSDK_MODEL_ModelResponse", "responseBody: " + this.b);
                response.close();
            } catch (IOException e) {
                SmartLog.e("MLSDK_MODEL_ModelResponse", "get responseBody failed" + e.getMessage());
                throw new MLException("get responseBody failed", 2);
            }
        } catch (Throwable th) {
            response.close();
            throw th;
        }
    }

    public String getResponseBody() {
        return this.b;
    }

    public boolean isSuccessful() {
        return this.a == 200;
    }
}
