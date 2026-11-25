package com.huawei.hms.mlsdk.tts;

import com.huawei.hms.mlsdk.t.a;

/* JADX INFO: loaded from: classes10.dex */
public class MLTtsWarn {
    public static final int WARN_INSUFFICIENT_BANDWIDTH = 113001;
    private Object extension;
    private int warnId;
    private String warnMsg;

    public static class Builder {
        private Object extension;
        private int warnId;
        private String warnMsg;

        public MLTtsWarn build() {
            return new MLTtsWarn(this.warnId, this.warnMsg, this.extension);
        }

        public Builder setExtension(Object obj) {
            this.extension = obj;
            return this;
        }

        public Builder setWarnId(int i) {
            this.warnId = i;
            return this;
        }

        public Builder setWarnMsg(String str) {
            this.warnMsg = str;
            return this;
        }
    }

    public Object getExtension() {
        return this.extension;
    }

    public int getWarnId() {
        return this.warnId;
    }

    public String getWarnMsg() {
        return this.warnMsg;
    }

    public String toString() {
        return a.a(a.a("MLTtsWarn{warnId=").append(this.warnId).append(", warnMsg='"), this.warnMsg, '\'', ", extension=").append(this.extension).append('}').toString();
    }

    private MLTtsWarn(int i, String str, Object obj) {
        this.warnId = i;
        this.warnMsg = str;
        this.extension = obj;
    }
}
