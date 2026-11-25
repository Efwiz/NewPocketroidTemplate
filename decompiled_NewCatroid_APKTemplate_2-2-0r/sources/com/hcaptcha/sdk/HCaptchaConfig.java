package com.hcaptcha.sdk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Locale;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public class HCaptchaConfig implements Serializable {

    @JsonIgnore
    @Deprecated
    private String apiEndpoint;
    private String assethost;
    private String customTheme;
    private Boolean diagnosticLog;
    private Boolean disableHardwareAcceleration;
    private String endpoint;

    @Deprecated
    private Boolean hideDialog;
    private String host;
    private String imghost;
    private String jsSrc;
    private Boolean loading;
    private String locale;
    private HCaptchaOrientation orientation;

    @JsonIgnore
    private HCaptchaRenderMode renderMode;
    private String reportapi;

    @Deprecated
    private Boolean resetOnTimeout;

    @JsonIgnore
    private IHCaptchaRetryPredicate retryPredicate;

    @Deprecated
    private String rqdata;
    private Boolean sentry;
    private String siteKey;
    private HCaptchaSize size;
    private HCaptchaTheme theme;
    private long tokenExpiration;
    private Boolean userJourney;

    /* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
    public static class HCaptchaConfigBuilder {
        private boolean apiEndpoint$set;
        private String apiEndpoint$value;
        private String assethost;
        private boolean customTheme$set;
        private String customTheme$value;
        private boolean diagnosticLog$set;
        private Boolean diagnosticLog$value;
        private boolean disableHardwareAcceleration$set;
        private Boolean disableHardwareAcceleration$value;
        private String endpoint;
        private boolean hideDialog$set;
        private Boolean hideDialog$value;
        private boolean host$set;
        private String host$value;
        private String imghost;
        private boolean jsSrc$set;
        private String jsSrc$value;
        private boolean loading$set;
        private Boolean loading$value;
        private boolean locale$set;
        private String locale$value;
        private boolean orientation$set;
        private HCaptchaOrientation orientation$value;
        private boolean renderMode$set;
        private HCaptchaRenderMode renderMode$value;
        private String reportapi;
        private boolean resetOnTimeout$set;
        private Boolean resetOnTimeout$value;
        private boolean retryPredicate$set;
        private IHCaptchaRetryPredicate retryPredicate$value;
        private String rqdata;
        private boolean sentry$set;
        private Boolean sentry$value;
        private String siteKey;
        private boolean size$set;
        private HCaptchaSize size$value;
        private boolean theme$set;
        private HCaptchaTheme theme$value;
        private boolean tokenExpiration$set;
        private long tokenExpiration$value;
        private boolean userJourney$set;
        private Boolean userJourney$value;

        @Deprecated
        public HCaptchaConfigBuilder apiEndpoint(String str) {
            jsSrc(str);
            return this;
        }

        public HCaptchaConfigBuilder assethost(String str) {
            this.assethost = str;
            return this;
        }

        public HCaptchaConfig build() {
            Boolean bool$default$sentry = this.sentry$value;
            if (!this.sentry$set) {
                bool$default$sentry = HCaptchaConfig.$default$sentry();
            }
            Boolean bool = bool$default$sentry;
            Boolean bool$default$loading = this.loading$value;
            if (!this.loading$set) {
                bool$default$loading = HCaptchaConfig.$default$loading();
            }
            Boolean bool2 = bool$default$loading;
            Boolean bool$default$hideDialog = this.hideDialog$value;
            if (!this.hideDialog$set) {
                bool$default$hideDialog = HCaptchaConfig.$default$hideDialog();
            }
            Boolean bool3 = bool$default$hideDialog;
            String str$default$apiEndpoint = this.apiEndpoint$value;
            if (!this.apiEndpoint$set) {
                str$default$apiEndpoint = HCaptchaConfig.$default$apiEndpoint();
            }
            String str = str$default$apiEndpoint;
            String str$default$jsSrc = this.jsSrc$value;
            if (!this.jsSrc$set) {
                str$default$jsSrc = HCaptchaConfig.$default$jsSrc();
            }
            String str2 = str$default$jsSrc;
            String str$default$locale = this.locale$value;
            if (!this.locale$set) {
                str$default$locale = HCaptchaConfig.$default$locale();
            }
            String str3 = str$default$locale;
            HCaptchaSize hCaptchaSize$default$size = this.size$value;
            if (!this.size$set) {
                hCaptchaSize$default$size = HCaptchaConfig.$default$size();
            }
            HCaptchaSize hCaptchaSize = hCaptchaSize$default$size;
            HCaptchaOrientation hCaptchaOrientation$default$orientation = this.orientation$value;
            if (!this.orientation$set) {
                hCaptchaOrientation$default$orientation = HCaptchaConfig.$default$orientation();
            }
            HCaptchaOrientation hCaptchaOrientation = hCaptchaOrientation$default$orientation;
            HCaptchaTheme hCaptchaTheme$default$theme = this.theme$value;
            if (!this.theme$set) {
                hCaptchaTheme$default$theme = HCaptchaConfig.$default$theme();
            }
            HCaptchaTheme hCaptchaTheme = hCaptchaTheme$default$theme;
            HCaptchaRenderMode hCaptchaRenderMode$default$renderMode = this.renderMode$value;
            if (!this.renderMode$set) {
                hCaptchaRenderMode$default$renderMode = HCaptchaConfig.$default$renderMode();
            }
            HCaptchaRenderMode hCaptchaRenderMode = hCaptchaRenderMode$default$renderMode;
            String str$default$host = this.host$value;
            if (!this.host$set) {
                str$default$host = HCaptchaConfig.$default$host();
            }
            String str4 = str$default$host;
            String str$default$customTheme = this.customTheme$value;
            if (!this.customTheme$set) {
                str$default$customTheme = HCaptchaConfig.$default$customTheme();
            }
            String str5 = str$default$customTheme;
            Boolean bool$default$resetOnTimeout = this.resetOnTimeout$value;
            if (!this.resetOnTimeout$set) {
                bool$default$resetOnTimeout = HCaptchaConfig.$default$resetOnTimeout();
            }
            Boolean bool4 = bool$default$resetOnTimeout;
            IHCaptchaRetryPredicate iHCaptchaRetryPredicate$default$retryPredicate = this.retryPredicate$value;
            if (!this.retryPredicate$set) {
                iHCaptchaRetryPredicate$default$retryPredicate = HCaptchaConfig.$default$retryPredicate();
            }
            IHCaptchaRetryPredicate iHCaptchaRetryPredicate = iHCaptchaRetryPredicate$default$retryPredicate;
            long j$default$tokenExpiration = this.tokenExpiration$value;
            if (!this.tokenExpiration$set) {
                j$default$tokenExpiration = HCaptchaConfig.$default$tokenExpiration();
            }
            long j = j$default$tokenExpiration;
            Boolean bool$default$diagnosticLog = this.diagnosticLog$value;
            if (!this.diagnosticLog$set) {
                bool$default$diagnosticLog = HCaptchaConfig.$default$diagnosticLog();
            }
            Boolean bool5 = bool$default$diagnosticLog;
            Boolean bool$default$disableHardwareAcceleration = this.disableHardwareAcceleration$value;
            if (!this.disableHardwareAcceleration$set) {
                bool$default$disableHardwareAcceleration = HCaptchaConfig.$default$disableHardwareAcceleration();
            }
            Boolean bool6 = bool$default$disableHardwareAcceleration;
            Boolean bool$default$userJourney = this.userJourney$value;
            if (!this.userJourney$set) {
                bool$default$userJourney = HCaptchaConfig.$default$userJourney();
            }
            return new HCaptchaConfig(this.siteKey, bool, bool2, bool3, this.rqdata, str, str2, this.endpoint, this.reportapi, this.assethost, this.imghost, str3, hCaptchaSize, hCaptchaOrientation, hCaptchaTheme, hCaptchaRenderMode, str4, str5, bool4, iHCaptchaRetryPredicate, j, bool5, bool6, bool$default$userJourney);
        }

        public HCaptchaConfigBuilder customTheme(String str) {
            this.customTheme$value = str;
            this.customTheme$set = true;
            return this;
        }

        public HCaptchaConfigBuilder diagnosticLog(Boolean bool) {
            this.diagnosticLog$value = bool;
            this.diagnosticLog$set = true;
            return this;
        }

        public HCaptchaConfigBuilder disableHardwareAcceleration(Boolean bool) {
            if (bool == null) {
                throw new NullPointerException("disableHardwareAcceleration is marked non-null but is null");
            }
            this.disableHardwareAcceleration$value = bool;
            this.disableHardwareAcceleration$set = true;
            return this;
        }

        public HCaptchaConfigBuilder endpoint(String str) {
            this.endpoint = str;
            return this;
        }

        @Deprecated
        public HCaptchaConfigBuilder hideDialog(Boolean bool) {
            this.hideDialog$value = bool;
            this.hideDialog$set = true;
            return this;
        }

        public HCaptchaConfigBuilder host(String str) {
            if (str != null) {
                String strTrim = str.trim();
                if (strTrim.isEmpty()) {
                    this.host$value = null;
                } else {
                    if (strTrim.contains("://")) {
                        throw new IllegalArgumentException("Config 'host' must be a hostname, not a URL. Remove scheme from: '" + strTrim + FormatHelper.QUOTE);
                    }
                    this.host$value = strTrim;
                }
            }
            this.host$set = true;
            return this;
        }

        public HCaptchaConfigBuilder imghost(String str) {
            this.imghost = str;
            return this;
        }

        public HCaptchaConfigBuilder jsSrc(String str) {
            this.jsSrc$value = str;
            this.jsSrc$set = true;
            return this;
        }

        public HCaptchaConfigBuilder loading(Boolean bool) {
            this.loading$value = bool;
            this.loading$set = true;
            return this;
        }

        public HCaptchaConfigBuilder locale(String str) {
            this.locale$value = str;
            this.locale$set = true;
            return this;
        }

        public HCaptchaConfigBuilder orientation(HCaptchaOrientation hCaptchaOrientation) {
            this.orientation$value = hCaptchaOrientation;
            this.orientation$set = true;
            return this;
        }

        @JsonIgnore
        public HCaptchaConfigBuilder renderMode(HCaptchaRenderMode hCaptchaRenderMode) {
            this.renderMode$value = hCaptchaRenderMode;
            this.renderMode$set = true;
            return this;
        }

        public HCaptchaConfigBuilder reportapi(String str) {
            this.reportapi = str;
            return this;
        }

        @Deprecated
        public HCaptchaConfigBuilder resetOnTimeout(Boolean bool) {
            this.resetOnTimeout$value = bool;
            this.resetOnTimeout$set = true;
            return this;
        }

        @JsonIgnore
        public HCaptchaConfigBuilder retryPredicate(IHCaptchaRetryPredicate iHCaptchaRetryPredicate) {
            this.retryPredicate$value = iHCaptchaRetryPredicate;
            this.retryPredicate$set = true;
            return this;
        }

        @Deprecated
        public HCaptchaConfigBuilder rqdata(String str) {
            this.rqdata = str;
            return this;
        }

        public HCaptchaConfigBuilder sentry(Boolean bool) {
            this.sentry$value = bool;
            this.sentry$set = true;
            return this;
        }

        public HCaptchaConfigBuilder siteKey(String str) {
            if (str == null) {
                throw new NullPointerException("siteKey is marked non-null but is null");
            }
            this.siteKey = str;
            return this;
        }

        public HCaptchaConfigBuilder size(HCaptchaSize hCaptchaSize) {
            this.size$value = hCaptchaSize;
            this.size$set = true;
            return this;
        }

        public HCaptchaConfigBuilder theme(HCaptchaTheme hCaptchaTheme) {
            this.theme$value = hCaptchaTheme;
            this.theme$set = true;
            return this;
        }

        public String toString() {
            return "HCaptchaConfig.HCaptchaConfigBuilder(siteKey=" + this.siteKey + ", sentry$value=" + this.sentry$value + ", loading$value=" + this.loading$value + ", hideDialog$value=" + this.hideDialog$value + ", rqdata=" + this.rqdata + ", apiEndpoint$value=" + this.apiEndpoint$value + ", jsSrc$value=" + this.jsSrc$value + ", endpoint=" + this.endpoint + ", reportapi=" + this.reportapi + ", assethost=" + this.assethost + ", imghost=" + this.imghost + ", locale$value=" + this.locale$value + ", size$value=" + this.size$value + ", orientation$value=" + this.orientation$value + ", theme$value=" + this.theme$value + ", renderMode$value=" + this.renderMode$value + ", host$value=" + this.host$value + ", customTheme$value=" + this.customTheme$value + ", resetOnTimeout$value=" + this.resetOnTimeout$value + ", retryPredicate$value=" + this.retryPredicate$value + ", tokenExpiration$value=" + this.tokenExpiration$value + ", diagnosticLog$value=" + this.diagnosticLog$value + ", disableHardwareAcceleration$value=" + this.disableHardwareAcceleration$value + ", userJourney$value=" + this.userJourney$value + Constant.AFTER_QUTO;
        }

        public HCaptchaConfigBuilder tokenExpiration(long j) {
            this.tokenExpiration$value = j;
            this.tokenExpiration$set = true;
            return this;
        }

        public HCaptchaConfigBuilder userJourney(Boolean bool) {
            this.userJourney$value = bool;
            this.userJourney$set = true;
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String $default$apiEndpoint() {
        return "https://js.hcaptcha.com/1/api.js";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String $default$customTheme() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Boolean $default$diagnosticLog() {
        return Boolean.FALSE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Boolean $default$disableHardwareAcceleration() {
        return Boolean.TRUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Boolean $default$hideDialog() {
        return Boolean.FALSE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String $default$host() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String $default$jsSrc() {
        return "https://js.hcaptcha.com/1/api.js";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Boolean $default$loading() {
        return Boolean.TRUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String $default$locale() {
        return Locale.getDefault().getLanguage();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HCaptchaOrientation $default$orientation() {
        return HCaptchaOrientation.PORTRAIT;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HCaptchaRenderMode $default$renderMode() {
        return HCaptchaRenderMode.DIALOG;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Boolean $default$resetOnTimeout() {
        return Boolean.FALSE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IHCaptchaRetryPredicate $default$retryPredicate() {
        return new HCaptchaConfig$$ExternalSyntheticLambda0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Boolean $default$sentry() {
        return Boolean.TRUE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HCaptchaSize $default$size() {
        return HCaptchaSize.INVISIBLE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static HCaptchaTheme $default$theme() {
        return HCaptchaTheme.LIGHT;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long $default$tokenExpiration() {
        return 120L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Boolean $default$userJourney() {
        return Boolean.FALSE;
    }

    public HCaptchaConfig(String str, Boolean bool, Boolean bool2, Boolean bool3, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, HCaptchaSize hCaptchaSize, HCaptchaOrientation hCaptchaOrientation, HCaptchaTheme hCaptchaTheme, HCaptchaRenderMode hCaptchaRenderMode, String str10, String str11, Boolean bool4, IHCaptchaRetryPredicate iHCaptchaRetryPredicate, long j, Boolean bool5, Boolean bool6, Boolean bool7) {
        if (str == null) {
            throw new NullPointerException("siteKey is marked non-null but is null");
        }
        if (bool6 == null) {
            throw new NullPointerException("disableHardwareAcceleration is marked non-null but is null");
        }
        this.siteKey = str;
        this.sentry = bool;
        this.loading = bool2;
        this.hideDialog = bool3;
        this.rqdata = str2;
        this.apiEndpoint = str3;
        this.jsSrc = str4;
        this.endpoint = str5;
        this.reportapi = str6;
        this.assethost = str7;
        this.imghost = str8;
        this.locale = str9;
        this.size = hCaptchaSize;
        this.orientation = hCaptchaOrientation;
        this.theme = hCaptchaTheme;
        this.renderMode = hCaptchaRenderMode;
        this.host = str10;
        this.customTheme = str11;
        this.resetOnTimeout = bool4;
        this.retryPredicate = iHCaptchaRetryPredicate;
        this.tokenExpiration = j;
        this.diagnosticLog = bool5;
        this.disableHardwareAcceleration = bool6;
        this.userJourney = bool7;
    }

    public static HCaptchaConfigBuilder builder() {
        return new HCaptchaConfigBuilder();
    }

    static /* synthetic */ boolean lambda$$default$retryPredicate$41a513e9$1(HCaptchaConfig hCaptchaConfig, HCaptchaException hCaptchaException) {
        return Boolean.TRUE.equals(hCaptchaConfig.resetOnTimeout) && hCaptchaException.getHCaptchaError() == HCaptchaError.SESSION_TIMEOUT;
    }

    public boolean canEqual(Object obj) {
        return obj instanceof HCaptchaConfig;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HCaptchaConfig)) {
            return false;
        }
        HCaptchaConfig hCaptchaConfig = (HCaptchaConfig) obj;
        if (!hCaptchaConfig.canEqual(this) || getTokenExpiration() != hCaptchaConfig.getTokenExpiration()) {
            return false;
        }
        Boolean sentry = getSentry();
        Boolean sentry2 = hCaptchaConfig.getSentry();
        if (sentry != null ? !sentry.equals(sentry2) : sentry2 != null) {
            return false;
        }
        Boolean loading = getLoading();
        Boolean loading2 = hCaptchaConfig.getLoading();
        if (loading != null ? !loading.equals(loading2) : loading2 != null) {
            return false;
        }
        Boolean hideDialog = getHideDialog();
        Boolean hideDialog2 = hCaptchaConfig.getHideDialog();
        if (hideDialog != null ? !hideDialog.equals(hideDialog2) : hideDialog2 != null) {
            return false;
        }
        Boolean resetOnTimeout = getResetOnTimeout();
        Boolean resetOnTimeout2 = hCaptchaConfig.getResetOnTimeout();
        if (resetOnTimeout != null ? !resetOnTimeout.equals(resetOnTimeout2) : resetOnTimeout2 != null) {
            return false;
        }
        Boolean diagnosticLog = getDiagnosticLog();
        Boolean diagnosticLog2 = hCaptchaConfig.getDiagnosticLog();
        if (diagnosticLog != null ? !diagnosticLog.equals(diagnosticLog2) : diagnosticLog2 != null) {
            return false;
        }
        Boolean disableHardwareAcceleration = getDisableHardwareAcceleration();
        Boolean disableHardwareAcceleration2 = hCaptchaConfig.getDisableHardwareAcceleration();
        if (disableHardwareAcceleration != null ? !disableHardwareAcceleration.equals(disableHardwareAcceleration2) : disableHardwareAcceleration2 != null) {
            return false;
        }
        Boolean userJourney = getUserJourney();
        Boolean userJourney2 = hCaptchaConfig.getUserJourney();
        if (userJourney != null ? !userJourney.equals(userJourney2) : userJourney2 != null) {
            return false;
        }
        String siteKey = getSiteKey();
        String siteKey2 = hCaptchaConfig.getSiteKey();
        if (siteKey != null ? !siteKey.equals(siteKey2) : siteKey2 != null) {
            return false;
        }
        String rqdata = getRqdata();
        String rqdata2 = hCaptchaConfig.getRqdata();
        if (rqdata != null ? !rqdata.equals(rqdata2) : rqdata2 != null) {
            return false;
        }
        String apiEndpoint = getApiEndpoint();
        String apiEndpoint2 = hCaptchaConfig.getApiEndpoint();
        if (apiEndpoint != null ? !apiEndpoint.equals(apiEndpoint2) : apiEndpoint2 != null) {
            return false;
        }
        String jsSrc = getJsSrc();
        String jsSrc2 = hCaptchaConfig.getJsSrc();
        if (jsSrc != null ? !jsSrc.equals(jsSrc2) : jsSrc2 != null) {
            return false;
        }
        String endpoint = getEndpoint();
        String endpoint2 = hCaptchaConfig.getEndpoint();
        if (endpoint != null ? !endpoint.equals(endpoint2) : endpoint2 != null) {
            return false;
        }
        String reportapi = getReportapi();
        String reportapi2 = hCaptchaConfig.getReportapi();
        if (reportapi != null ? !reportapi.equals(reportapi2) : reportapi2 != null) {
            return false;
        }
        String assethost = getAssethost();
        String assethost2 = hCaptchaConfig.getAssethost();
        if (assethost != null ? !assethost.equals(assethost2) : assethost2 != null) {
            return false;
        }
        String imghost = getImghost();
        String imghost2 = hCaptchaConfig.getImghost();
        if (imghost != null ? !imghost.equals(imghost2) : imghost2 != null) {
            return false;
        }
        String locale = getLocale();
        String locale2 = hCaptchaConfig.getLocale();
        if (locale != null ? !locale.equals(locale2) : locale2 != null) {
            return false;
        }
        HCaptchaSize size = getSize();
        HCaptchaSize size2 = hCaptchaConfig.getSize();
        if (size != null ? !size.equals(size2) : size2 != null) {
            return false;
        }
        HCaptchaOrientation orientation = getOrientation();
        HCaptchaOrientation orientation2 = hCaptchaConfig.getOrientation();
        if (orientation != null ? !orientation.equals(orientation2) : orientation2 != null) {
            return false;
        }
        HCaptchaTheme theme = getTheme();
        HCaptchaTheme theme2 = hCaptchaConfig.getTheme();
        if (theme != null ? !theme.equals(theme2) : theme2 != null) {
            return false;
        }
        HCaptchaRenderMode renderMode = getRenderMode();
        HCaptchaRenderMode renderMode2 = hCaptchaConfig.getRenderMode();
        if (renderMode != null ? !renderMode.equals(renderMode2) : renderMode2 != null) {
            return false;
        }
        String host = getHost();
        String host2 = hCaptchaConfig.getHost();
        if (host != null ? !host.equals(host2) : host2 != null) {
            return false;
        }
        String customTheme = getCustomTheme();
        String customTheme2 = hCaptchaConfig.getCustomTheme();
        if (customTheme != null ? !customTheme.equals(customTheme2) : customTheme2 != null) {
            return false;
        }
        IHCaptchaRetryPredicate retryPredicate = getRetryPredicate();
        IHCaptchaRetryPredicate retryPredicate2 = hCaptchaConfig.getRetryPredicate();
        return retryPredicate != null ? retryPredicate.equals(retryPredicate2) : retryPredicate2 == null;
    }

    @Deprecated
    public String getApiEndpoint() {
        return this.jsSrc;
    }

    public String getAssethost() {
        return this.assethost;
    }

    @JsonIgnore
    public String getBaseUrl() {
        String str = this.host;
        if (str == null || str.isEmpty()) {
            return null;
        }
        return "https://".concat(str);
    }

    public String getCustomTheme() {
        return this.customTheme;
    }

    public Boolean getDiagnosticLog() {
        return this.diagnosticLog;
    }

    public Boolean getDisableHardwareAcceleration() {
        return this.disableHardwareAcceleration;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    @Deprecated
    public Boolean getHideDialog() {
        return this.hideDialog;
    }

    public String getHost() {
        return this.host;
    }

    public String getImghost() {
        return this.imghost;
    }

    public String getJsSrc() {
        return this.jsSrc;
    }

    public Boolean getLoading() {
        return this.loading;
    }

    public String getLocale() {
        return this.locale;
    }

    public HCaptchaOrientation getOrientation() {
        return this.orientation;
    }

    public HCaptchaRenderMode getRenderMode() {
        return this.renderMode;
    }

    public String getReportapi() {
        return this.reportapi;
    }

    @Deprecated
    public Boolean getResetOnTimeout() {
        return this.resetOnTimeout;
    }

    public IHCaptchaRetryPredicate getRetryPredicate() {
        return this.retryPredicate;
    }

    @Deprecated
    public String getRqdata() {
        return this.rqdata;
    }

    public Boolean getSentry() {
        return this.sentry;
    }

    public String getSiteKey() {
        return this.siteKey;
    }

    public HCaptchaSize getSize() {
        return this.size;
    }

    public HCaptchaTheme getTheme() {
        return this.theme;
    }

    public long getTokenExpiration() {
        return this.tokenExpiration;
    }

    public Boolean getUserJourney() {
        return this.userJourney;
    }

    public int hashCode() {
        long tokenExpiration = getTokenExpiration();
        Boolean sentry = getSentry();
        int iHashCode = ((((int) (tokenExpiration ^ (tokenExpiration >>> 32))) + 59) * 59) + (sentry == null ? 43 : sentry.hashCode());
        Boolean loading = getLoading();
        int iHashCode2 = (iHashCode * 59) + (loading == null ? 43 : loading.hashCode());
        Boolean hideDialog = getHideDialog();
        int iHashCode3 = (iHashCode2 * 59) + (hideDialog == null ? 43 : hideDialog.hashCode());
        Boolean resetOnTimeout = getResetOnTimeout();
        int iHashCode4 = (iHashCode3 * 59) + (resetOnTimeout == null ? 43 : resetOnTimeout.hashCode());
        Boolean diagnosticLog = getDiagnosticLog();
        int iHashCode5 = (iHashCode4 * 59) + (diagnosticLog == null ? 43 : diagnosticLog.hashCode());
        Boolean disableHardwareAcceleration = getDisableHardwareAcceleration();
        int iHashCode6 = (iHashCode5 * 59) + (disableHardwareAcceleration == null ? 43 : disableHardwareAcceleration.hashCode());
        Boolean userJourney = getUserJourney();
        int iHashCode7 = (iHashCode6 * 59) + (userJourney == null ? 43 : userJourney.hashCode());
        String siteKey = getSiteKey();
        int iHashCode8 = (iHashCode7 * 59) + (siteKey == null ? 43 : siteKey.hashCode());
        String rqdata = getRqdata();
        int iHashCode9 = (iHashCode8 * 59) + (rqdata == null ? 43 : rqdata.hashCode());
        String apiEndpoint = getApiEndpoint();
        int iHashCode10 = (iHashCode9 * 59) + (apiEndpoint == null ? 43 : apiEndpoint.hashCode());
        String jsSrc = getJsSrc();
        int iHashCode11 = (iHashCode10 * 59) + (jsSrc == null ? 43 : jsSrc.hashCode());
        String endpoint = getEndpoint();
        int iHashCode12 = (iHashCode11 * 59) + (endpoint == null ? 43 : endpoint.hashCode());
        String reportapi = getReportapi();
        int iHashCode13 = (iHashCode12 * 59) + (reportapi == null ? 43 : reportapi.hashCode());
        String assethost = getAssethost();
        int iHashCode14 = (iHashCode13 * 59) + (assethost == null ? 43 : assethost.hashCode());
        String imghost = getImghost();
        int iHashCode15 = (iHashCode14 * 59) + (imghost == null ? 43 : imghost.hashCode());
        String locale = getLocale();
        int iHashCode16 = (iHashCode15 * 59) + (locale == null ? 43 : locale.hashCode());
        HCaptchaSize size = getSize();
        int iHashCode17 = (iHashCode16 * 59) + (size == null ? 43 : size.hashCode());
        HCaptchaOrientation orientation = getOrientation();
        int iHashCode18 = (iHashCode17 * 59) + (orientation == null ? 43 : orientation.hashCode());
        HCaptchaTheme theme = getTheme();
        int iHashCode19 = (iHashCode18 * 59) + (theme == null ? 43 : theme.hashCode());
        HCaptchaRenderMode renderMode = getRenderMode();
        int iHashCode20 = (iHashCode19 * 59) + (renderMode == null ? 43 : renderMode.hashCode());
        String host = getHost();
        int iHashCode21 = (iHashCode20 * 59) + (host == null ? 43 : host.hashCode());
        String customTheme = getCustomTheme();
        int iHashCode22 = (iHashCode21 * 59) + (customTheme == null ? 43 : customTheme.hashCode());
        IHCaptchaRetryPredicate retryPredicate = getRetryPredicate();
        return (iHashCode22 * 59) + (retryPredicate != null ? retryPredicate.hashCode() : 43);
    }

    @JsonIgnore
    public boolean isHeadlessMode() {
        return this.renderMode == HCaptchaRenderMode.HEADLESS || Boolean.TRUE.equals(this.hideDialog);
    }

    @JsonIgnore
    @Deprecated
    public void setApiEndpoint(String str) {
        this.apiEndpoint = str;
    }

    public void setAssethost(String str) {
        this.assethost = str;
    }

    public void setCustomTheme(String str) {
        this.customTheme = str;
    }

    public void setDiagnosticLog(Boolean bool) {
        this.diagnosticLog = bool;
    }

    public void setDisableHardwareAcceleration(Boolean bool) {
        if (bool == null) {
            throw new NullPointerException("disableHardwareAcceleration is marked non-null but is null");
        }
        this.disableHardwareAcceleration = bool;
    }

    public void setEndpoint(String str) {
        this.endpoint = str;
    }

    @Deprecated
    public void setHideDialog(Boolean bool) {
        this.hideDialog = bool;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setImghost(String str) {
        this.imghost = str;
    }

    public void setJsSrc(String str) {
        this.jsSrc = str;
    }

    public void setLoading(Boolean bool) {
        this.loading = bool;
    }

    public void setLocale(String str) {
        this.locale = str;
    }

    public void setOrientation(HCaptchaOrientation hCaptchaOrientation) {
        this.orientation = hCaptchaOrientation;
    }

    @JsonIgnore
    public void setRenderMode(HCaptchaRenderMode hCaptchaRenderMode) {
        this.renderMode = hCaptchaRenderMode;
    }

    public void setReportapi(String str) {
        this.reportapi = str;
    }

    @Deprecated
    public void setResetOnTimeout(Boolean bool) {
        this.resetOnTimeout = bool;
    }

    @JsonIgnore
    public void setRetryPredicate(IHCaptchaRetryPredicate iHCaptchaRetryPredicate) {
        this.retryPredicate = iHCaptchaRetryPredicate;
    }

    @Deprecated
    public void setRqdata(String str) {
        this.rqdata = str;
    }

    public void setSentry(Boolean bool) {
        this.sentry = bool;
    }

    public void setSiteKey(String str) {
        if (str == null) {
            throw new NullPointerException("siteKey is marked non-null but is null");
        }
        this.siteKey = str;
    }

    public void setSize(HCaptchaSize hCaptchaSize) {
        this.size = hCaptchaSize;
    }

    public void setTheme(HCaptchaTheme hCaptchaTheme) {
        this.theme = hCaptchaTheme;
    }

    public void setTokenExpiration(long j) {
        this.tokenExpiration = j;
    }

    public void setUserJourney(Boolean bool) {
        this.userJourney = bool;
    }

    public HCaptchaConfigBuilder toBuilder() {
        return new HCaptchaConfigBuilder().siteKey(this.siteKey).sentry(this.sentry).loading(this.loading).hideDialog(this.hideDialog).rqdata(this.rqdata).apiEndpoint(this.apiEndpoint).jsSrc(this.jsSrc).endpoint(this.endpoint).reportapi(this.reportapi).assethost(this.assethost).imghost(this.imghost).locale(this.locale).size(this.size).orientation(this.orientation).theme(this.theme).renderMode(this.renderMode).host(this.host).customTheme(this.customTheme).resetOnTimeout(this.resetOnTimeout).retryPredicate(this.retryPredicate).tokenExpiration(this.tokenExpiration).diagnosticLog(this.diagnosticLog).disableHardwareAcceleration(this.disableHardwareAcceleration).userJourney(this.userJourney);
    }

    public String toString() {
        return "HCaptchaConfig(siteKey=" + getSiteKey() + ", sentry=" + getSentry() + ", loading=" + getLoading() + ", hideDialog=" + getHideDialog() + ", rqdata=" + getRqdata() + ", apiEndpoint=" + getApiEndpoint() + ", jsSrc=" + getJsSrc() + ", endpoint=" + getEndpoint() + ", reportapi=" + getReportapi() + ", assethost=" + getAssethost() + ", imghost=" + getImghost() + ", locale=" + getLocale() + ", size=" + getSize() + ", orientation=" + getOrientation() + ", theme=" + getTheme() + ", renderMode=" + getRenderMode() + ", host=" + getHost() + ", customTheme=" + getCustomTheme() + ", resetOnTimeout=" + getResetOnTimeout() + ", retryPredicate=" + getRetryPredicate() + ", tokenExpiration=" + getTokenExpiration() + ", diagnosticLog=" + getDiagnosticLog() + ", disableHardwareAcceleration=" + getDisableHardwareAcceleration() + ", userJourney=" + getUserJourney() + Constant.AFTER_QUTO;
    }
}
