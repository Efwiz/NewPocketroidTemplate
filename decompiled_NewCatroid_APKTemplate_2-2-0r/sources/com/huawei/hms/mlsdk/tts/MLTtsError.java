package com.huawei.hms.mlsdk.tts;

import com.huawei.hms.mlsdk.t.a;

/* JADX INFO: loaded from: classes10.dex */
public class MLTtsError {
    public static final int ERR_AUDIO_PLAYER_FAILED = 11305;
    public static final int ERR_AUTHORIZE_FAILED = 11306;
    public static final int ERR_AUTHORIZE_TOKEN_INVALIDE = 11307;
    public static final int ERR_ILLEGAL_PARAMETER = 11301;
    public static final int ERR_INSUFFICIENT_BALANCE = 11303;
    public static final int ERR_INTERNAL = 11398;
    public static final int ERR_NET_CONNECT_FAILED = 11302;
    public static final int ERR_SPEECH_SYNTHESIS_FAILED = 11304;
    public static final int ERR_UNKNOWN = 11399;
    private int errorId;
    private String errorMsg;
    private Object extension;

    public static class Builder {
        private int errorId;
        private String errorMsg;
        private Object extension;

        public MLTtsError build() {
            return new MLTtsError(this.errorId, this.errorMsg, this.extension);
        }

        public Builder setErrorId(int i) {
            this.errorId = i;
            return this;
        }

        public Builder setErrorMsg(String str) {
            this.errorMsg = str;
            return this;
        }

        public Builder setExtension(Object obj) {
            this.extension = obj;
            return this;
        }
    }

    public int getErrorId() {
        return this.errorId;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public Object getExtension() {
        return this.extension;
    }

    public String toString() {
        return a.a(a.a("MLTtsError{errorId=").append(this.errorId).append(", errorMsg='"), this.errorMsg, '\'', ", extension=").append(this.extension).append('}').toString();
    }

    private MLTtsError(int i, String str, Object obj) {
        this.errorId = i;
        this.errorMsg = str;
        this.extension = obj;
    }
}
