package com.hcaptcha.sdk;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
class HCaptchaInternalConfig implements Serializable {
    private IHCaptchaHtmlProvider htmlProvider;

    /* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
    public static class HCaptchaInternalConfigBuilder {
        private boolean htmlProvider$set;
        private IHCaptchaHtmlProvider htmlProvider$value;

        public HCaptchaInternalConfig build() {
            IHCaptchaHtmlProvider iHCaptchaHtmlProvider$default$htmlProvider = this.htmlProvider$value;
            if (!this.htmlProvider$set) {
                iHCaptchaHtmlProvider$default$htmlProvider = HCaptchaInternalConfig.$default$htmlProvider();
            }
            return new HCaptchaInternalConfig(iHCaptchaHtmlProvider$default$htmlProvider);
        }

        public HCaptchaInternalConfigBuilder htmlProvider(IHCaptchaHtmlProvider iHCaptchaHtmlProvider) {
            this.htmlProvider$value = iHCaptchaHtmlProvider;
            this.htmlProvider$set = true;
            return this;
        }

        public String toString() {
            return "HCaptchaInternalConfig.HCaptchaInternalConfigBuilder(htmlProvider$value=" + this.htmlProvider$value + Constant.AFTER_QUTO;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IHCaptchaHtmlProvider $default$htmlProvider() {
        return new HCaptchaHtml();
    }

    public HCaptchaInternalConfig(IHCaptchaHtmlProvider iHCaptchaHtmlProvider) {
        this.htmlProvider = iHCaptchaHtmlProvider;
    }

    public static HCaptchaInternalConfigBuilder builder() {
        return new HCaptchaInternalConfigBuilder();
    }

    public boolean canEqual(Object obj) {
        return obj instanceof HCaptchaInternalConfig;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HCaptchaInternalConfig)) {
            return false;
        }
        HCaptchaInternalConfig hCaptchaInternalConfig = (HCaptchaInternalConfig) obj;
        if (!hCaptchaInternalConfig.canEqual(this)) {
            return false;
        }
        IHCaptchaHtmlProvider htmlProvider = getHtmlProvider();
        IHCaptchaHtmlProvider htmlProvider2 = hCaptchaInternalConfig.getHtmlProvider();
        return htmlProvider != null ? htmlProvider.equals(htmlProvider2) : htmlProvider2 == null;
    }

    public IHCaptchaHtmlProvider getHtmlProvider() {
        return this.htmlProvider;
    }

    public int hashCode() {
        IHCaptchaHtmlProvider htmlProvider = getHtmlProvider();
        return (htmlProvider == null ? 43 : htmlProvider.hashCode()) + 59;
    }

    public void setHtmlProvider(IHCaptchaHtmlProvider iHCaptchaHtmlProvider) {
        this.htmlProvider = iHCaptchaHtmlProvider;
    }

    public HCaptchaInternalConfigBuilder toBuilder() {
        return new HCaptchaInternalConfigBuilder().htmlProvider(this.htmlProvider);
    }

    public String toString() {
        return "HCaptchaInternalConfig(htmlProvider=" + getHtmlProvider() + Constant.AFTER_QUTO;
    }
}
