package com.hcaptcha.sdk;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HCaptchaVerifyParams implements Serializable {

    @JsonProperty("mfa_phone")
    private String phoneNumber;

    @JsonProperty("mfa_phoneprefix")
    private String phonePrefix;

    @JsonProperty("rqdata")
    private String rqdata;

    @JsonProperty("userjourney")
    private Object userJourney;

    /* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
    public static class HCaptchaVerifyParamsBuilder {
        private String phoneNumber;
        private String phonePrefix;
        private String rqdata;
        private Object userJourney;

        public HCaptchaVerifyParams build() {
            return new HCaptchaVerifyParams(this.phonePrefix, this.phoneNumber, this.rqdata, this.userJourney);
        }

        @JsonProperty("mfa_phone")
        public HCaptchaVerifyParamsBuilder phoneNumber(String str) {
            this.phoneNumber = str;
            return this;
        }

        @JsonProperty("mfa_phoneprefix")
        public HCaptchaVerifyParamsBuilder phonePrefix(String str) {
            this.phonePrefix = str;
            return this;
        }

        @JsonProperty("rqdata")
        public HCaptchaVerifyParamsBuilder rqdata(String str) {
            this.rqdata = str;
            return this;
        }

        public String toString() {
            return "HCaptchaVerifyParams.HCaptchaVerifyParamsBuilder(phonePrefix=" + this.phonePrefix + ", phoneNumber=" + this.phoneNumber + ", rqdata=" + this.rqdata + ", userJourney=" + this.userJourney + Constant.AFTER_QUTO;
        }

        @JsonProperty("userjourney")
        public HCaptchaVerifyParamsBuilder userJourney(Object obj) {
            this.userJourney = obj;
            return this;
        }
    }

    public HCaptchaVerifyParams() {
    }

    public static HCaptchaVerifyParamsBuilder builder() {
        return new HCaptchaVerifyParamsBuilder();
    }

    public boolean canEqual(Object obj) {
        return obj instanceof HCaptchaVerifyParams;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HCaptchaVerifyParams)) {
            return false;
        }
        HCaptchaVerifyParams hCaptchaVerifyParams = (HCaptchaVerifyParams) obj;
        if (!hCaptchaVerifyParams.canEqual(this)) {
            return false;
        }
        String phonePrefix = getPhonePrefix();
        String phonePrefix2 = hCaptchaVerifyParams.getPhonePrefix();
        if (phonePrefix != null ? !phonePrefix.equals(phonePrefix2) : phonePrefix2 != null) {
            return false;
        }
        String phoneNumber = getPhoneNumber();
        String phoneNumber2 = hCaptchaVerifyParams.getPhoneNumber();
        if (phoneNumber != null ? !phoneNumber.equals(phoneNumber2) : phoneNumber2 != null) {
            return false;
        }
        String rqdata = getRqdata();
        String rqdata2 = hCaptchaVerifyParams.getRqdata();
        if (rqdata != null ? !rqdata.equals(rqdata2) : rqdata2 != null) {
            return false;
        }
        Object userJourney = getUserJourney();
        Object userJourney2 = hCaptchaVerifyParams.getUserJourney();
        return userJourney != null ? userJourney.equals(userJourney2) : userJourney2 == null;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getPhonePrefix() {
        return this.phonePrefix;
    }

    public String getRqdata() {
        return this.rqdata;
    }

    public Object getUserJourney() {
        return this.userJourney;
    }

    public int hashCode() {
        String phonePrefix = getPhonePrefix();
        int iHashCode = phonePrefix == null ? 43 : phonePrefix.hashCode();
        String phoneNumber = getPhoneNumber();
        int iHashCode2 = ((iHashCode + 59) * 59) + (phoneNumber == null ? 43 : phoneNumber.hashCode());
        String rqdata = getRqdata();
        int iHashCode3 = (iHashCode2 * 59) + (rqdata == null ? 43 : rqdata.hashCode());
        Object userJourney = getUserJourney();
        return (iHashCode3 * 59) + (userJourney != null ? userJourney.hashCode() : 43);
    }

    @JsonProperty("mfa_phone")
    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    @JsonProperty("mfa_phoneprefix")
    public void setPhonePrefix(String str) {
        this.phonePrefix = str;
    }

    @JsonProperty("rqdata")
    public void setRqdata(String str) {
        this.rqdata = str;
    }

    @JsonProperty("userjourney")
    public void setUserJourney(Object obj) {
        this.userJourney = obj;
    }

    public HCaptchaVerifyParamsBuilder toBuilder() {
        return new HCaptchaVerifyParamsBuilder().phonePrefix(this.phonePrefix).phoneNumber(this.phoneNumber).rqdata(this.rqdata).userJourney(this.userJourney);
    }

    public String toString() {
        return "HCaptchaVerifyParams(phonePrefix=" + getPhonePrefix() + ", phoneNumber=" + getPhoneNumber() + ", rqdata=" + getRqdata() + ", userJourney=" + getUserJourney() + Constant.AFTER_QUTO;
    }

    public HCaptchaVerifyParams(String str, String str2, String str3, Object obj) {
        this.phonePrefix = str;
        this.phoneNumber = str2;
        this.rqdata = str3;
        this.userJourney = obj;
    }
}
